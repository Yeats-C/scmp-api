package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;

public interface RejectRecordBatchDao {

    int insert(RejectRecordBatch record);

    int update(RejectRecordBatch record);
}