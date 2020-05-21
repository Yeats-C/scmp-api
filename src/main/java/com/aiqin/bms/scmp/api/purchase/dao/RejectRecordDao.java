package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectQueryRequest;

import java.util.List;

public interface RejectRecordDao {

    Integer insert(RejectRecord record);

    Integer update(RejectRecord record);

    List<RejectRecord> list(RejectQueryRequest rejectApplyQueryRequest);

    Integer listCount(RejectQueryRequest rejectApplyQueryRequest);

    RejectRecord selectByRejectCode(String rejectRecordCode);

    String rejectRecordByCode(String code);
}