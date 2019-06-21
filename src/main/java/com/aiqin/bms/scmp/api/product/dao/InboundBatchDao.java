package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;

import java.util.List;

public interface InboundBatchDao {

    List<InboundBatch> selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch);

    Integer countInboundBatchInfoByInboundOderCode(String inboundOderCode);
}