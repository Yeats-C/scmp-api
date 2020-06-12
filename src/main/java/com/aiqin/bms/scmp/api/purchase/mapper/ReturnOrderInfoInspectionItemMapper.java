package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnGoodsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderInboundBatchResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnOrderInfoInspectionItemMapper {

    Integer insert(ReturnOrderInfoInspectionItem record);

    Integer update(ReturnOrderInfoInspectionItem record);

    Integer insertBatch(@Param("list") List<ReturnOrderInfoInspectionItem> items);

    List<ReturnOrderInfoInspectionItem> returnBatchList(@Param("skuCode") String skuCode,
                                                        @Param("returnOrderCode") String returnOrderCode,
                                                        @Param("lineCode") Integer lineCode);

    ReturnOrderInfoInspectionItem returnOrderInfo(@Param("batchInfoCode") String batchInfoCode,
                                                  @Param("returnOderCode") String returnOderCode,
                                                  @Param("lineCode") Integer lineCode);

    List<ReturnOrderInfoInspectionItem> returnOrderBatchList(String returnOderCode);

    List<ReturnOrderInfoInspectionItem> list(ReturnGoodsRequest request);

    Integer listCount(ReturnGoodsRequest request);

    List<ReturnOrderInboundBatchResponse> inboundBatchByReturnOrderList(InboundBatchReqVo request);

    Integer inboundBatchByReturnOrderCount(InboundBatchReqVo request);

    List<ReturnOrderInfoInspectionItem> returnOrderByWarehouse(@Param("returnOrderCode") String returnOrderCode);

    List<ReturnOrderInfoInspectionItem> returnOrderBatchListByWarehouse(@Param("returnOrderCode") String returnOrderCode,
                                                                        @Param("warehouseCode") String warehouseCode);
}