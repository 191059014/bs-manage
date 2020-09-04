package com.hb.bsmanage.web.controller;

import com.hb.bsmanage.model.enums.ResourceType;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.EnumUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 通用controller
 *
 * @version v0.1, 2020/9/3 18:01, create by huangbiao.
 */
@RestController
@RequestMapping("bs/noauth/tools")
public class ToolsController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolsController.class);

    /**
     * 获取下拉框
     *
     * @param type 下拉框标识
     * @return 下拉框列表
     */
    @GetMapping("/combobox/{type}")
    public Result<List<Map<String, Object>>> getCombobox(@PathVariable("type") String type) {
        String baseLog = "[CommonController-getCombobox-获取下拉框]";
        LOGGER.info("{}入参={}", baseLog, type);
        List<Map<String, Object>> combobox = null;
        switch (type) {
            case "ResourceType":
                combobox = EnumUtils.combobox(ResourceType.class, EnumUtils.KeysEnum.name.toString(), EnumUtils.KeysEnum.value.toString());
                break;
            default:
                LOGGER.info("{}无此类型", baseLog);
                break;

        }
        LOGGER.info("{}出参={}", baseLog, combobox);
        return Result.of(ResponseEnum.SUCCESS, combobox);
    }

    /**
     * 从redis中获取key对应value
     *
     * @param key 缓存key
     * @return 缓存值
     */
    @GetMapping("/redis/get/{key}")
    public Result<Object> getFromRedis(@PathVariable("key") String key) {
        return Result.of(ResponseEnum.SUCCESS, ToolsWapper.redis().get(key));
    }

    /**
     * 从redis中删除key
     *
     * @param key 缓存key
     * @return 缓存值
     */
    @GetMapping("/redis/clear/{key}")
    public Result<Object> clearFromRedis(@PathVariable("key") String key) {
        return Result.of(ResponseEnum.SUCCESS, ToolsWapper.redis().delete(key));
    }

}

    