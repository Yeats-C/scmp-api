package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.PurchaseOrderArrivalSubscribe;
import com.aiqin.bms.scmp.api.purchase.domain.request.purchase.QueryPurchaseOrderArrivalSubscribeVo;
import com.aiqin.bms.scmp.api.purchase.domain.response.purchase.QueryPurchaseOrderArrivalSubscribeRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseOrderArrivalSubscribeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseOrderArrivalSubscribe record);

    int insertSelective(PurchaseOrderArrivalSubscribe record);

    PurchaseOrderArrivalSubscribe selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseOrderArrivalSubscribe record);

    int updateByPrimaryKey(PurchaseOrderArrivalSubscribe record);

    List<QueryPurchaseOrderArrivalSubscribeRespVo> getList(QueryPurchaseOrderArrivalSubscribeVo reqVo);

    PurchaseOrderArrivalSubscribe selectByInboundAndPurchaseOrderCode(@Param("inboundOrderCode") String inboundOrderCode,
                                                                      @Param("purchaseOrderCode") String purchaseOrderCode);

}