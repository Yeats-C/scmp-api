package com.aiqin.bms.scmp.api.purchase.dao.bi;

import com.aiqin.bms.scmp.api.purchase.domain.bi.BiGrossProfitMargin;
import org.apache.ibatis.annotations.Param;

public interface BiGrossProfitMarginDao {

    BiGrossProfitMargin grossProfitMargin(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);

}