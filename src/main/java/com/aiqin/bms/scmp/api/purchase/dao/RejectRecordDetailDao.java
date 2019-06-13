package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;

public interface RejectRecordDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectRecordDetail record);

    int insertSelective(RejectRecordDetail record);

    RejectRecordDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectRecordDetail record);

    int updateByPrimaryKey(RejectRecordDetail record);
}