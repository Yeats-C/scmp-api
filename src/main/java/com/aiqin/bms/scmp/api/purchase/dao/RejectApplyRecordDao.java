package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;

import java.util.List;

public interface RejectApplyRecordDao {

    int insert(RejectApplyRecord record);

    int insertSelective(RejectApplyRecord record);

    int updateByPrimaryKeySelective(RejectApplyRecord record);

    List<RejectApplyRecord> list(RejectApplyQueryRequest rejectApplyQueryRequest);

    Integer listCount(RejectApplyQueryRequest rejectApplyQueryRequest);
}