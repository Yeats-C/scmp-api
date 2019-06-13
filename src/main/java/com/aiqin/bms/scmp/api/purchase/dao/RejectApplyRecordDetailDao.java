package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;

public interface RejectApplyRecordDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectApplyRecordDetail record);

    int insertSelective(RejectApplyRecordDetail record);

    RejectApplyRecordDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectApplyRecordDetail record);

    int updateByPrimaryKey(RejectApplyRecordDetail record);
}