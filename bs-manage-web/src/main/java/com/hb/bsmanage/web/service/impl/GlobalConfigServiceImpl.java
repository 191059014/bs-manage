package com.hb.bsmanage.web.service.impl;

import com.hb.bsmanage.web.dao.po.GlobalConfigPO;
import com.hb.bsmanage.web.service.IGlobalConfigService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hb.bsmanage.web.dao.base.impl.BaseDaoImpl;

/**
 * 全局配置表服务层实现类
 *
 * @version v0.1, 2021-02-03 09:48:30, create by Mr.Huang.
 */
@Service
public class GlobalConfigServiceImpl extends BaseDaoImpl<GlobalConfigPO> implements IGlobalConfigService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfigServiceImpl.class);


}
