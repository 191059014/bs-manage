package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.web.common.enums.ErrorCode;
import com.hb.bsmanage.web.common.util.BsWebUtils;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.dao.po.SysMerchantPO;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.bsmanage.web.service.ISysMerchantService;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.util.LogHelper;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.easybuild.MapBuilder;
import com.hb.unic.util.util.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商户controller
 *
 * @version v0.1, 2020/9/14 16:16, create by huangbiao.
 */
@RestController
@RequestMapping("bs/auth/merchant")
public class MerchantController extends BaseController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessController.class);

    /**
     * 商户service
     */
    @Autowired
    private ISysMerchantService iSysMerchantService;

    /**
     * 用户service
     */
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 分页条件查询商户列表
     *
     * @param merchant 查询条件
     * @param pageNum  当前第几页
     * @param pageSize 每页条数
     * @return 商户分页列表
     */
    @PostMapping("/queryPages")
    public Result<Pagination<SysMerchantPO>> queryPages(@RequestBody SysMerchantPO merchant,
                                                        @RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam("pageSize") Integer pageSize) {
        String baseLog = LogHelper.getBaseLog("分页条件查询商户列表");
        LOGGER.info("{}入参={}={}={}", baseLog, merchant, pageNum, pageSize);
        if (!Pagination.verify(pageNum, pageSize)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "merchant_id", merchant.getMerchantId());
        where.andAdd(QueryType.LIKE, "merchant_name", merchant.getMerchantName());
        if (SecurityUtils.getCurrentUserParentId() != null) {
            // 非最高系统管理员，只能查询用户所属商户，及商户下的所有下级商户
            Set<String> merchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(SecurityUtils.getCurrentUserTenantId());
            where.andAdd(QueryType.IN, "merchant_id", merchantIdSet);
        }
        Pagination<SysMerchantPO> pagination = iSysMerchantService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);
        iSysUserService.formatCreateByAndUpdateBy(pagination.getData());
        LOGGER.info("{}出参={}", baseLog, pagination);
        return Result.of(ErrorCode.SUCCESS, pagination);
    }

    /**
     * 添加商户
     *
     * @param merchant 查询条件
     * @return 结果
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysMerchantPO merchant) {
        String baseLog = LogHelper.getBaseLog("添加商户");
        LOGGER.info("{}入参={}", baseLog, merchant);
        if (StringUtils.isBlank(merchant.getMerchantName())) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        SysMerchantPO currentUserMerchant = iSysMerchantService.selectByBk(SecurityUtils.getCurrentUserTenantId());
        merchant.setPath(BsWebUtils.getCurrentPath(currentUserMerchant.getPath(), currentUserMerchant.getId()));
        merchant.setParentId(SecurityUtils.getCurrentUserTenantId());
        merchant.setMerchantId(BsWebUtils.getTenantId());
        merchant.setCreateBy(SecurityUtils.getCurrentUserId());
        merchant.setUpdateBy(SecurityUtils.getCurrentUserId());
        LOGGER.info("{}准备入库={}", baseLog, merchant);
        int addRows = iSysMerchantService.insert(merchant);
        LOGGER.info("{}出参={}", baseLog, addRows);
        return Result.of(ErrorCode.SUCCESS, addRows);
    }

    /**
     * 修改商户
     *
     * @param merchant 查询条件
     * @return 结果
     */
    @InOutLog("修改商户")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody SysMerchantPO merchant, @RequestParam("merchantId") String merchantId) {
        if (StringUtils.isBlank(merchantId)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        merchant.setUpdateBy(SecurityUtils.getCurrentUserId());
        int updateRows = iSysMerchantService.updateByBk(merchantId, merchant);
        return Result.of(ErrorCode.SUCCESS, updateRows);
    }

    /**
     * 删除商户
     *
     * @param merchantId 商户ID
     * @return 结果
     */
    @GetMapping("/delete")
    @InOutLog("删除商户")
    public Result<Integer> delete(@RequestParam("merchantId") String merchantId) {
        if (StringUtils.isBlank(merchantId)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        int deleteRows = iSysMerchantService.logicDeleteByBk(merchantId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        return Result.of(ErrorCode.SUCCESS, deleteRows);
    }

    /**
     * 获取所有下级商户
     *
     * @return Result
     */
    @GetMapping("/getAllSubMerchants")
    @InOutLog("获取所有下级商户")
    public Result<List> getAllSubMerchants() {
        List<SysMerchantPO> merchantList = iSysMerchantService.getCurrentSubMerchantList(SecurityUtils.getCurrentUserTenantId());
        List<SysMerchantPO> result = merchantList.stream().map(merchant -> SysMerchantPO.builder().merchantId(merchant.getMerchantId()).merchantName(merchant.getMerchantName()).build()).collect(Collectors.toList());
        return Result.of(ErrorCode.SUCCESS, result);
    }

}

    