package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectApplyQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectApplyRecordDao {

    Integer insert(RejectApplyRecord record);

    Integer update(RejectApplyRecord record);

    List<RejectApplyRecord> list(RejectApplyQueryRequest rejectApplyQueryRequest);

    Integer listCount(RejectApplyQueryRequest rejectApplyQueryRequest);

    RejectApplyRecord selectByRejectCode(@Param("rejectApplyRecordCode") String rejectApplyRecordCode);

}