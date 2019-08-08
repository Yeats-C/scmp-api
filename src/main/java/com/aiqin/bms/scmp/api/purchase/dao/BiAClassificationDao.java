package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.BiAClassification;
import org.apache.ibatis.annotations.Param;

public interface BiAClassificationDao {

    BiAClassification aCategoryInfo(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);
}