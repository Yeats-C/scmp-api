package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.BiGrossProfitMargin;
import org.apache.ibatis.annotations.Param;

public interface BiGrossProfitMarginDao {

    BiGrossProfitMargin grossProfitMargin(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);

}