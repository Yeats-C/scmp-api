package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectRecordDetailDao {

    List<RejectRecordDetail> list(String rejectRecordCode);

    Integer listCount(String rejectRecordCode);

    Integer update(RejectRecordDetail record);

    Integer insertAll(@Param("list")List<RejectRecordDetail> detailList);

    List<RejectRecordDetail> selectByRejectId(@Param("rejectRecordId") String rejectRecordId);

}