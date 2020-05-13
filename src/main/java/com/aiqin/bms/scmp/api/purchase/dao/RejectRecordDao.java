package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectQueryRequest;

import java.util.List;

public interface RejectRecordDao {

    int insert(RejectRecord record);

    //int updateByPrimaryKeySelective(RejectRecord record);

    int updateByPrimaryKey(RejectRecord record);

    List<RejectRecord> list(RejectQueryRequest rejectApplyQueryRequest);

    Integer listCount(RejectQueryRequest rejectApplyQueryRequest);

    Integer updateStatus(RejectRecord rejectRecord);

    RejectRecord selectByRejectId(String rejectRecordId);

    RejectRecord selectByRejectCode(String rejectRecordCode);

    void updateByOrderCodes(List<String> orderCodes);

    List<RejectRecord> listForSap(SapOrderRequest sapOrderRequest);
}