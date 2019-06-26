package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectFile;

public interface RejectFileDao {
    int deleteByPrimaryKey(Long id);

    int insert(RejectFile record);

    int insertSelective(RejectFile record);

    RejectFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RejectFile record);

    int updateByPrimaryKey(RejectFile record);
}