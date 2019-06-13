package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;

public interface RejectRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectRecord record);

    int insertSelective(RejectRecord record);

    RejectRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectRecord record);

    int updateByPrimaryKey(RejectRecord record);
}