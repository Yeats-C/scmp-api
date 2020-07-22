package com.aiqin.bms.scmp.api.abutment.dao;

import com.aiqin.bms.scmp.api.abutment.domain.DlOrderBill;

public interface DlOrderBillDao {

    Integer insert(DlOrderBill record);

    Integer update(DlOrderBill record);

    DlOrderBill selectByCode(DlOrderBill info);

}