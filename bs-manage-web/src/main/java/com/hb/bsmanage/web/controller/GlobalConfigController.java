package com.hb.bsmanage.web.controller;

import com.hb.bsmanage.web.dao.po.GlobalConfigPO;
import com.hb.bsmanage.web.service.IGlobalConfigService;
import com.hb.unic.util.util.Pagination;
import com.hb.unic.base.common.Result;
import com.hb.bsmanage.web.common.enums.ErrorCode;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javax.annotation.Resource;

import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.toolkit.Where;

/**
 * 全局配置表控制层
 *
 * @version v0.1, 2021-02-03 09:48:30, create by Mr.Huang.
 */
@RestController
@RequestMapping("bs/auth/globalConfig")
public class GlobalConfigController extends BaseController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfigController.class);

    /**
     * 全局配置表服务层
     */
    @Resource
    private IGlobalConfigService iglobalConfigService;

    /**
     * 分页查询全局配置表
     *
     * @param globalConfig
     *              查询条件对象
     * @param pageNum
     *              当前第几页
     * @param pageSize
     *              每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Pagination<GlobalConfigPO>> queryPages(@RequestBody GlobalConfigPO globalConfig,
                                                         @RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize) {
        String baseLog = "[分页查询全局配置表controller]";
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}入参={}={}={}", baseLog, globalConfig, pageNum, pageSize);
        }
        if (!Pagination.verify(pageNum, pageSize)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        Where where = Where.build();
        where.andCondition(QueryType.EQUAL, "id", globalConfig.getId());
        where.andCondition(QueryType.EQUAL, "cfg_group_id", globalConfig.getCfgGroupId());
        where.andCondition(QueryType.EQUAL, "cfg_id", globalConfig.getCfgId());
        where.andCondition(QueryType.EQUAL, "cfg_value", globalConfig.getCfgValue());
        where.andCondition(QueryType.EQUAL, "cfg_remark", globalConfig.getCfgRemark());
        where.andCondition(QueryType.EQUAL, "create_by", globalConfig.getCreateBy());
        where.andCondition(QueryType.EQUAL, "create_time", globalConfig.getCreateTime());
        where.andCondition(QueryType.EQUAL, "update_by", globalConfig.getUpdateBy());
        where.andCondition(QueryType.EQUAL, "update_time", globalConfig.getUpdateTime());
        where.andCondition(QueryType.EQUAL, "record_status", globalConfig.getRecordStatus());
        where.andCondition(QueryType.EQUAL, "tenant_id", globalConfig.getTenantId());
        Pagination<GlobalConfigPO> page = iglobalConfigService.selectPages(where, (pageNum - 1) * pageSize, pageSize);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}出参={}", baseLog, page);
        }
        return Result.of(ErrorCode.SUCCESS, page);
    }

    /**
     * 新增全局配置表
     *
     * @param globalConfig
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    public Result save(@RequestBody GlobalConfigPO globalConfig) {
        String baseLog = "[新增全局配置表controller]";
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}入参={}", baseLog, globalConfig);
        }
        int addRows = iglobalConfigService.insert(globalConfig);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}出参={}", baseLog, addRows);
        }
        return Result.of(ErrorCode.SUCCESS, addRows);
    }

    /**
     * 通过主键修改全局配置表
     *
     * @param globalConfig
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody GlobalConfigPO globalConfig) {
        String baseLog = "[通过主键修改全局配置表controller]";
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}入参={}", baseLog, globalConfig);
        }
        int updateRows = iglobalConfigService.update(Where.build().and().equal("id", globalConfig.getId()), globalConfig);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}出参={}", baseLog, updateRows);
        }
        return Result.of(ErrorCode.SUCCESS, updateRows);
    }

    /**
     * 通过主键删除全局配置表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Integer id) {
        String baseLog = "[通过主键删除全局配置表controller]";
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}入参={}", baseLog, id);
        }
        int deleteRows = iglobalConfigService.deleteById(id);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}出参={}", baseLog, deleteRows);
        }
        return Result.of(ErrorCode.SUCCESS, deleteRows);
    }

}
