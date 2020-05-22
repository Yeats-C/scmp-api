package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.ReturnOutboundBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundBatchDao {

    List<OutboundBatch> selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch);

    Integer countOutboundBatchInfoByOutboundOderCode(String outboundOderCode);

    Integer insertAll(@Param("list")List<OutboundBatch> list);

    List<OutboundBatch> listByOrderCode(SapOrderRequest sapOrderRequest);

    OutboundBatch selectBatchInfoByLineCode(@Param("outboundOderCode") String outboundOderCode, @Param("batchInfoCode") String batchInfoCode);

    Integer updateBatchInfoByOutboundOderCodeAndLineNum(OutboundBatch outboundBatch);

    List<OutboundBatch> selectByOutboundBatchOderCode(String outboundOderCode);

    List<OutboundBatch> outboundBatchBySap(@Param("skuCode") String skuCode,
                                           @Param("outboundOderCode") String outboundOderCode,
                                           @Param("lineCode") Long lineCode);

}