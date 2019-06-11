package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.common.DataBaseType;
import com.aiqin.bms.scmp.api.supplier.domain.event.SupplierEvent;
import com.aiqin.bms.scmp.api.supplier.service.SupplierEventTriggeringServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 事件触发
 */
@Service
public class SupplierEventTriggeringServieImpl implements SupplierEventTriggeringServie {
    @Autowired
    private ApplicationContext context;
    @Override
    @Async("myTaskAsyncPool")
    public void pushSuplier(DataBaseType type, String code, String name) {
      context.publishEvent(SupplierEvent.builder().type(type).code(code).name(name).build());
    }
}
