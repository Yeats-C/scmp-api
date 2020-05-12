package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordTransportCenter;

public interface RejectApplyRecordTransportCenterDao {

    Integer insert(RejectApplyRecordTransportCenter record);

    Integer update(RejectApplyRecordTransportCenter record);
}