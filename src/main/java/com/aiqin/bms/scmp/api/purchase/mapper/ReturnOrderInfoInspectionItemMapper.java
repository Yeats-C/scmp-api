package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
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
}