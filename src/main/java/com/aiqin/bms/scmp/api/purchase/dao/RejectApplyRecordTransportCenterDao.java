package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordTransportCenter;

import java.util.List;

public interface RejectApplyRecordTransportCenterDao {

    Integer insert(RejectApplyRecordTransportCenter record);

    Integer update(RejectApplyRecordTransportCenter record);

    List<RejectApplyRecordTransportCenter> rejectApplyTransportCenterInfo(RejectApplyRecordTransportCenter record);
}