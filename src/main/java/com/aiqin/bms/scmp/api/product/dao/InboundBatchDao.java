package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchPurchaseReq;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.ReturnInboundBatch;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundBatchWmsReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboundBatchDao {

    List<InboundBatch> selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch);

    Integer countInboundBatchInfoByInboundOderCode(String inboundOderCode);

    Integer insertInfo(@Param("list")List<InboundBatch> inboundBatches);

    List<InboundBatchWmsReqVO> selectWmsStockByInboundOderCode(String inboundOderCode);

    ReturnInboundBatch selectByLinenum(@Param("inboundOderCode") String inboundOderCode, @Param("skuCode") String skuCode, @Param("linenum") Long linenum);

    Integer updateBatchInfoByInboundOderCodeAndLineNum(InboundBatch inboundBatch);

    List<InboundBatch> listBySourceCodes(@Param("list") List<String> orderCodes);

    Integer insertList(List<InboundBatchReqVo> batchList);
}