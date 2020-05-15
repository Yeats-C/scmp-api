package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;

import java.util.List;

public interface RejectRecordBatchDao {

    Integer insert(RejectRecordBatch record);

    Integer update(RejectRecordBatch record);

    List<RejectRecordBatch> list(String rejectRecordCode);

    Integer listCount(String rejectRecordCode);
}