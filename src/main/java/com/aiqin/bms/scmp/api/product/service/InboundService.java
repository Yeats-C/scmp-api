package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.*;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundResVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundResponse;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.QueryInboundResVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface InboundService {

    BasePage<QueryInboundResVo> getInboundList(QueryInboundReqVo vo);

    List<InboundResponse> selectInBoundInfoByBoundSearch(BoundRequest boundRequest);

    InboundResVo view(Long id);

    String saveInbound(InboundReqSave reqVo);

    List<EnumReqVo> getInboundType();

    List<EnumReqVo> getInboundOutboundStatus();

    String saveReturnGoodsToInbound(SupplyReturnOrderMainReqVO reqVo);

    void pushWms(String code);

    void workFlowCallBack(InboundCallBackRequest request);

    void returnSource(Long id);

    void inBoundReturn(String allocationCode);

    HttpResponse selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch);

    Boolean saveList(List<InboundReqSave> list);

    void saveData(List<Inbound> inboundList, List<InboundProduct> productList, List<InboundBatch> batchList);

    String repealOrder(String orderId, String createById, String createByName, String cancel);

}
