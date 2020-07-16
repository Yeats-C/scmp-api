package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectRecordDetailDao {

    List<RejectRecordDetail> list(RejectQueryRequest request);

    Integer listCount(RejectQueryRequest request);

    Integer update(RejectRecordDetail record);

    Integer insertAll(@Param("list") List<RejectRecordDetail> detailList);

    List<RejectRecordDetail> selectByRejectId(@Param("rejectRecordId") String rejectRecordId);

    RejectRecordDetail rejectRecordByLineCode(@Param("rejectRecordCode") String rejectRecordCode,
                                              @Param("lineCode") Integer lineCode);

    List<String> selectByRejectCodeList(@Param("list") List<String> purchaseOrderCodeList);
}