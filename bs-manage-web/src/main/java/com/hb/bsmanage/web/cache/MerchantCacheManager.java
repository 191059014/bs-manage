package com.hb.bsmanage.web.cache;

import com.hb.bsmanage.model.dobj.SysMerchantDO;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guoll
 * @date 2020/9/16
 */
public class MerchantCacheManager implements InitializingBean {

    /**
     * 所有商户
     */
    private static List<SysMerchantDO> allMerchants = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
