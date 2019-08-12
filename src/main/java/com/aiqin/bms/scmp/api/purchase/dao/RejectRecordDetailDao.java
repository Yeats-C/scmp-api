package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectDetailStockRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectRecordDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectRecordDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectRecordDetail record);

    int insertSelective(RejectRecordDetail record);

    RejectRecordDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectRecordDetail record);

    int updateByPrimaryKey(RejectRecordDetail record);

    Integer insertAll(@Param("list") List<RejectRecordDetail> detailList, @Param("rejectRecordId") String rejectId, @Param("rejectRecordCode") String rejectCode, @Param("createById") String createId, @Param("createByName") String createName);

    List<RejectRecordDetail> selectByRejectId(@Param("rejectRecordId") String rejectRecordId);

    List<RejectRecordDetailResponse> selectProductByRejectId(String rejectRecordId);

    void updateByDetailId(RejectDetailStockRequest detailResponse);

    List<RejectRecordDetail> selectByRejectDetailIdList(List<Long> list);
}