package com.aiqin.bms.scmp.api.purchase.dao.bi;

import com.aiqin.bms.scmp.api.purchase.domain.bi.BiAClassification;
import org.apache.ibatis.annotations.Param;

public interface BiAClassificationDao {

    BiAClassification aCategoryInfo(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);
}