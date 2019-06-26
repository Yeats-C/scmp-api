package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.ReturnOutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.OutboundBatchWmsResVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundBatchDao {

    List<OutboundBatch> selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch);

    Integer countOutboundBatchInfoByOutboundOderCode(String outboundOderCode);

    Integer insertInfo(@Param("list")List<OutboundBatch> outboundBatches);

    List<OutboundBatchWmsResVO> selectOutboundBatchWmsResponse(String outboundOderCode);

    List<OutboundBatch> selectByOutboundBatchOderCode(String outboundOderCode);

    ReturnOutboundBatch selectBatchInfoByLinenum(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode, @Param("linenum") Long linenum);

    Integer updateBatchInfoByOutboundOderCodeAndLineNum(OutboundBatch outboundBatch);
}