package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.ReturnOutboundBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundBatchDao {

    List<OutboundBatch> selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch);

    Integer countOutboundBatchInfoByOutboundOderCode(String outboundOderCode);

    Integer insertInfo(@Param("list")List<OutboundBatch> outboundBatches);

    Integer insertList(List<OutboundBatch> list);

    List<OutboundBatch> listByOrderCode(SapOrderRequest sapOrderRequest);

    ReturnOutboundBatch selectBatchInfoByLinenum(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode, @Param("lineCode") Long lineCode);

    Integer updateBatchInfoByOutboundOderCodeAndLineNum(OutboundBatch outboundBatch);

    List<OutboundBatch> selectByOutboundBatchOderCode(String outboundOderCode);
}