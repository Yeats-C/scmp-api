package com.aiqin.bms.scmp.api.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 10:50
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }
}
