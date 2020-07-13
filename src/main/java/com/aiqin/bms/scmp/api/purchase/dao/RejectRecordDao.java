package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectRecordDao {

    Integer insert(RejectRecord record);

    Integer update(RejectRecord record);

    List<RejectRecord> list(RejectQueryRequest rejectApplyQueryRequest);

    Integer listCount(RejectQueryRequest rejectApplyQueryRequest);

    RejectRecord selectByRejectCode(String rejectRecordCode);

    String rejectRecordByCode(String code);

    List<RejectRecord> selectByRejectCodeList(@Param("list") List<String> rejectCodeList);

    int insertMany(@Param("list") List<RejectRecord> saves);
}