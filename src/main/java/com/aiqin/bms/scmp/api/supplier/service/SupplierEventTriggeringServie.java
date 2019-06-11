package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.common.DataBaseType;

/**
 *
 */
public interface SupplierEventTriggeringServie {

    void  pushSuplier(DataBaseType type, String code, String name);
}
