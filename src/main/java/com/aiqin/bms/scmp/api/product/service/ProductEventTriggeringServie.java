package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.common.DataBaseType;

public interface ProductEventTriggeringServie {
    void  pushSuplier(DataBaseType type, String code, String name);
}
