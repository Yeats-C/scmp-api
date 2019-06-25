package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundBatchReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundBatchDao {

    List<OutboundBatch> selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch);

    Integer countOutboundBatchInfoByOutboundOderCode(String outboundOderCode);

    Integer insertInfo(@Param("list")List<OutboundBatch> outboundBatches);

    List<OutboundBatch> selectByOutboundBatchOderCode(String outboundOderCode);
}