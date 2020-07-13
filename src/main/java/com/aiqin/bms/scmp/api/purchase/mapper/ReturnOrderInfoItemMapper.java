package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.ReturnReceiptReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnGoodsRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnOrderInfoItemMapper {

    ReturnOrderInfoItem selectByPrimaryKey(Long id);

    List<ReturnOrderInfoItem> selectByReturnOrderCode(@Param("returnOrderCode") String returnOrderCode);

    Integer update(ReturnOrderInfoItem record);

    Integer insertList(@Param("list") List<ReturnOrderInfoItem> list);

    List<ReturnOrderInfoItem> listDetailForSap(SapOrderRequest sapOrderRequest);

    ReturnOrderInfoItem returnOrderOne(@Param("returnOrderCode") String returnOrderCode,
                                       @Param("skuCode") String skuCode,
                                       @Param("lineCode") Long lineCode);

    List<ReturnOrderInfoItem> list(ReturnGoodsRequest request);

    Integer listCount(ReturnGoodsRequest request);

    Long returnOrderByLastLineCode(String returnOrderCode);

    List<String> selectByReturnOrderList(@Param("list") List<String> returnOrderList);
}