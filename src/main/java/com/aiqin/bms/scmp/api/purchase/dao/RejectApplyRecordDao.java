package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;

public interface RejectApplyRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectApplyRecord record);

    int insertSelective(RejectApplyRecord record);

    RejectApplyRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectApplyRecord record);

    int updateByPrimaryKey(RejectApplyRecord record);
}