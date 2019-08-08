package com.aiqin.bms.scmp.api.purchase.dao.apply;

import com.aiqin.bms.scmp.api.purchase.domain.apply.ApplyRejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectRecordDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyRejectRecordDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyRejectRecordDetail record);

    int insertSelective(ApplyRejectRecordDetail record);

    ApplyRejectRecordDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyRejectRecordDetail record);

    int updateByPrimaryKey(ApplyRejectRecordDetail record);

    List<RejectRecordDetailResponse> selectProductByRejectId(String rejectRecordId);

    List<RejectRecordDetail> selectByRejectId(String rejectRecordId);

    Integer insertAll(@Param("list") List<RejectRecordDetail> detailList, @Param("rejectRecordId") String rejectId, @Param("rejectRecordCode") String rejectCode, @Param("createById") String createId, @Param("createByName") String createName);
}