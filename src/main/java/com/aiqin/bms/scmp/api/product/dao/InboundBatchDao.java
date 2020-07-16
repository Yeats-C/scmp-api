package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboundBatchDao {

    List<InboundBatch> selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch);

    Integer countInboundBatchInfoByInboundOderCode(String inboundOderCode);

    Integer insertAll(@Param("list")List<InboundBatch> inboundBatches);

    List<InboundBatch> listBySourceCodes(@Param("list") List<String> orderCodes);

    Integer insertList(@Param("list") List<InboundBatchReqVo> batchList);

    List<InboundBatch> listByOrderCode(SapOrderRequest sapOrderRequest);

    Integer update(InboundBatch inboundBatch);

    InboundBatch inboundBatchByInfoCode(@Param("batchCode") String batchCode,
                                        @Param("inboundOderCode") String inboundOderCode,
                                        @Param("lineCode") Long lineCode);

    List<InboundBatch> selectInboundBatchList(String inboundOderCode);

    List<InboundBatch> inboundListBySku(@Param("skuCode") String skuCode,
                                        @Param("inboundOderCode") String inboundOderCode,
                                        @Param("lineCode") Long lineCode);

}