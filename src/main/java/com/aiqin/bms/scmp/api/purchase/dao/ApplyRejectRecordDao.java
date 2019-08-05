package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.ApplyRejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;

public interface ApplyRejectRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyRejectRecord record);

    int insertSelective(ApplyRejectRecord record);

    ApplyRejectRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyRejectRecord record);

    int updateByPrimaryKey(ApplyRejectRecord record);

    ApplyRejectRecord selectByRejectCode(String approvalCode);
}