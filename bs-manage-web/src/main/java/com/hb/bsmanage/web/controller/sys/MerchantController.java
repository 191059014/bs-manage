package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.ISysMerchantService;
import com.hb.bsmanage.model.dobj.SysMerchantDO;
import com.hb.bsmanage.web.common.BaseController;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.KeyUtils;
import com.hb.unic.util.util.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 分页条件查询商户列表
     *
     * @param merchant 查询条件
     * @param pageNum  当前第几页
     * @param pageSize 每页条数
     * @return 商户分页列表
     */
    @PostMapping("/queryPages")
    public Result<Pagination<SysMerchantDO>> queryPages(@RequestBody SysMerchantDO merchant,
                                                        @RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam("pageSize") Integer pageSize) {
        if (!Pagination.verify(pageNum, pageSize)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        return Result.of(ResponseEnum.SUCCESS, iSysMerchantService.selectPages(merchant, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize));
    }

    /**
     * 添加商户
     *
     * @param merchant 查询条件
     * @return 结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysMerchantDO merchant) {
        if (StringUtils.isBlank(merchant.getMerchantName())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        merchant.setMerchantId(KeyUtils.getTenantId());
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
    public Result update(@RequestBody SysMerchantDO merchant, @RequestParam("merchantId") String merchantId) {
        if (StringUtils.isBlank(merchantId)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
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
        iSysMerchantService.logicDeleteByBk(merchantId);
        return Result.of(ResponseEnum.SUCCESS);
    }

}

    