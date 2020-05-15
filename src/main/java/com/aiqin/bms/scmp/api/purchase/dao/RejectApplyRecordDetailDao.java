package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectApplyRecordDetailDao {

    List<RejectApplyRecordDetail> rejectApplyRecordDetailList(String rejectApplyRecordCode);

    Integer rejectApplyRecordDetailCount(String rejectApplyRecordCode);

    List<RejectApplyRecordDetail> rejectApplyRecordBatchList(String rejectApplyRecordCode);

    Integer rejectApplyRecordBatchCount(String rejectApplyRecordCode);

    List<RejectApplyDetailHandleResponse> rejectApplyRecordDetailByEdit(String rejectApplyRecordCode);

    Integer insertAll(@Param("list") List<RejectApplyRecordDetail> list);

    Integer update(RejectApplyRecordDetail record);

    Integer delete(String rejectApplyRecordCode);

    void deleteAll(String rejectApplyRecordCode);

    List<RejectApplyDetailResponse> selectByRejectCode(String rejectApplyCode);

    void updateStatus(String rejectApplyCode);

}