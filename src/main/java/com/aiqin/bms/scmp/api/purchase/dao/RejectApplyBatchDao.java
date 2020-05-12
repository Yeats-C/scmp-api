package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyBatch;

public interface RejectApplyBatchDao {

    Integer insert(RejectApplyBatch record);

    Integer update(RejectApplyBatch record);
}