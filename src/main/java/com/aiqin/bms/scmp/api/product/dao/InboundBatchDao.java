package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboundBatchDao {

    List<InboundBatch> selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch);

    Integer countInboundBatchInfoByInboundOderCode(String inboundOderCode);

    Integer insertInfo(@Param("list")List<InboundBatch> inboundBatches);
}