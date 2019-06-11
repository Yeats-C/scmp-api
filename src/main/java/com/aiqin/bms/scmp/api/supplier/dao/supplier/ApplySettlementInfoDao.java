package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySettlementInformation;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySettlementInfoReqDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 18:30
 */
public interface ApplySettlementInfoDao {

    Long insertApply(ApplySettlementInfoReqDTO applySettlementInfoReqDTO);

    int updateApply(ApplySettlementInfoReqDTO applySettlementInfoReqDTO);

    int updateAndCode(@Param("name") String name, @Param("code") String code);

    ApplySettlementInformation getApplySettInfo(String applySupplyComCode);

}
