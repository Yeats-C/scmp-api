package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyQueryResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectApplyRecordDao {

    Integer insert(RejectApplyRecord record);

    Integer insertSelective(RejectApplyRecord record);

    Integer updateByPrimaryKeySelective(RejectApplyRecord record);

    List<RejectApplyRecord> list(RejectApplyQueryRequest rejectApplyQueryRequest);

    Integer listCount(RejectApplyQueryRequest rejectApplyQueryRequest);

    RejectApplyRecord selectByRejectCode(@Param("rejectApplyCode") String rejectApplyCode);

    Integer updateByRejectCode(RejectApplyRecord rejectApplyRequest);

    void updateStatus(String rejectApplyRecordId);

    Integer delete(String rejectApplyRecordCode);
}