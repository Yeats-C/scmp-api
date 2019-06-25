package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectQueryRequest;

import java.util.List;

public interface RejectRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectRecord record);

    int insertSelective(RejectRecord record);

    RejectRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectRecord record);

    int updateByPrimaryKey(RejectRecord record);

    List<RejectRecord> list(RejectQueryRequest rejectApplyQueryRequest);

    Integer listCount(RejectQueryRequest rejectApplyQueryRequest);

    Integer updateStatus(String rejectApplyQueryRequest, Integer s);

    Integer updateStatus(RejectRecord rejectRecord);

    RejectRecord selectByRejectId(String rejectRecordId);
}