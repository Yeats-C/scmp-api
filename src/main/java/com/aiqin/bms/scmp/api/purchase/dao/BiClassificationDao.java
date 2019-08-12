package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.BiClassification;
import org.apache.ibatis.annotations.Param;

public interface BiClassificationDao {

    BiClassification categoryInfo(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);
}