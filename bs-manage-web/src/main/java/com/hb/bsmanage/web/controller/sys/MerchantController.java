package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.common.BsApiUtils;
import com.hb.bsmanage.api.service.ISysMerchantService;
import com.hb.bsmanage.api.service.ISysUserService;
import com.hb.bsmanage.model.po.SysMerchantPO;
import com.hb.bsmanage.model.po.SysUserPO;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.easybuild.MapBuilder;
import com.hb.unic.util.util.KeyUtils;
import com.hb.unic.util.util.Pagination;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        if (!Pagination.verify(pageNum, pageSize)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        SysMerchantPO currentUserMerchant = iSysMerchantService.selectByBk(SecurityUtils.getCurrentUserTenantId());
        Pagination<SysMerchantPO> pagination = null;
        int startRow = Pagination.getStartRow(pageNum, pageSize);
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "merchant_id", merchant.getMerchantId());
        where.andAdd(QueryType.LIKE, "merchant_name", merchant.getMerchantName());
        if (currentUserMerchant.getParentId() != null) {
            // 非最高系统管理员，只能查询下级（包含自己）
            BsApiUtils.getSubPathWhere(where, currentUserMerchant);
        }
        pagination = iSysMerchantService.selectPages(where, "create_time desc", startRow, pageSize);
        List<SysMerchantPO> merchantList = pagination.getData();
        if (CollectionUtils.isNotEmpty(merchantList)) {
            Set<String> userIdSet = new HashSet<>();
            merchantList.forEach(merchantDO -> {
                userIdSet.add(merchantDO.getCreateBy());
                userIdSet.add(merchantDO.getUpdateBy());
            });
            Map<String, SysUserPO> userMap = iSysUserService.getUserMapByUserIdSet(userIdSet);
            merchantList.forEach(merchantDO -> {
                SysUserPO createBy = userMap.get(merchantDO.getCreateBy());
                merchantDO.setCreateBy(createBy == null ? null : createBy.getUserName());
                SysUserPO updateBy = userMap.get(merchantDO.getUpdateBy());
                merchantDO.setUpdateBy(updateBy == null ? null : updateBy.getUserName());
            });
        }
        return Result.of(ResponseEnum.SUCCESS, pagination);
    }

    /**
     * 添加商户
     *
     * @param merchant 查询条件
     * @return 结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysMerchantPO merchant) {
        if (StringUtils.isBlank(merchant.getMerchantName())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        SysMerchantPO currentUserMerchant = iSysMerchantService.selectByBk(SecurityUtils.getCurrentUserTenantId());
        merchant.setPath(BsApiUtils.getCurrentPath(currentUserMerchant.getPath(), currentUserMerchant.getId()));
        merchant.setParentId(currentUserMerchant.getMerchantId());
        merchant.setMerchantId(KeyUtils.getTenantId());
        merchant.setCreateBy(SecurityUtils.getCurrentUserId());
        merchant.setUpdateBy(SecurityUtils.getCurrentUserId());
        iSysMerchantService.insert(merchant);
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 修改商户
     *
     * @param merchant 查询条件
     * @return 结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysMerchantPO merchant, @RequestParam("merchantId") String merchantId) {
        if (StringUtils.isBlank(merchantId)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        merchant.setUpdateBy(SecurityUtils.getCurrentUserId());
        iSysMerchantService.updateByBk(merchantId, merchant);
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 删除商户
     *
     * @param merchantId 商户ID
     * @return 结果
     */
    @GetMapping("/delete")
    public Result delete(@RequestParam("merchantId") String merchantId) {
        if (StringUtils.isBlank(merchantId)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        iSysMerchantService.logicDeleteByBk(merchantId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 获取所有下级商户
     *
     * @return Result
     */
    @GetMapping("/getAllSubMerchants")
    public Result<List> getAllSubMerchants() {
        List<SysMerchantPO> merchantList = iSysMerchantService.getCurrentSubMerchantList(SecurityUtils.getCurrentUserTenantId());
        List<SysMerchantPO> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(merchantList)) {
            merchantList.forEach(merchantDO -> {
                result.add(SysMerchantPO.builder().merchantId(merchantDO.getMerchantId()).merchantName(merchantDO.getMerchantName()).build());
            });
        }
        return Result.of(ResponseEnum.SUCCESS, result);
    }

}

    