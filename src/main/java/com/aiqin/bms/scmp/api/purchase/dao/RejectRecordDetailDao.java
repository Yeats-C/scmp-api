package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectRecordDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectRecordDetail record);

    int insertSelective(RejectRecordDetail record);

    RejectRecordDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectRecordDetail record);

    int updateByPrimaryKey(RejectRecordDetail record);

    Integer insertAll(@Param("list") List<RejectApplyDetailResponse> detailList,@Param("rejectRecordId") String rejectId,@Param("rejectRecordCode") String rejectCode,@Param("createById") String createId,@Param("createByName") String createName);
}