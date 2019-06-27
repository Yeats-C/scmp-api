package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.event.ProductEvent;
import com.aiqin.bms.scmp.api.product.service.ProductEventTriggeringServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProductEventTriggeringServieImpl implements ProductEventTriggeringServie {
    @Autowired
    private ApplicationContext context;
    @Override
    @Async("myTaskAsyncPool")
    public void pushSuplier(DataBaseType type, String code, String name) {
        context.publishEvent(ProductEvent.builder().type(type).code(code).name(name).build());
    }
}
