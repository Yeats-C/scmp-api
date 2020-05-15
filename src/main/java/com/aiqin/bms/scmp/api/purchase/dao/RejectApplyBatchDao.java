package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyBatch;

import java.util.List;

public interface RejectApplyBatchDao {

    Integer insert(RejectApplyBatch record);

    Integer update(RejectApplyBatch record);

    List<RejectApplyBatch> rejectApplyRecordBatchList(String rejectApplyRecordCode);

    Integer rejectApplyRecordBatchCount(String rejectApplyRecordCode);
}