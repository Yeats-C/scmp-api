package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnDLReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.ReturnReceiptReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ChangeOrderStatusReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface ReturnGoodsService {

    HttpResponse record(ReturnReq reqVO);

    HttpResponse<PageResData<ReturnOrderInfo>> returnOrderList(ReturnGoodsRequest request);

    HttpResponse<PageResData<ReturnOrderInfoItem>> returnOrderProductList(ReturnGoodsRequest request);

    HttpResponse<PageResData<ReturnOrderInfoInspectionItem>> returnOrderBatchList(ReturnGoodsRequest request);

    HttpResponse<ReturnOrderDetailResponse> returnOrderDetail(String returnOrderCode);

    HttpResponse inboundBatch(InboundBatchReqVo request);

    HttpResponse returnOrderCancel(String returnOrderCode);

    HttpResponse saveReturnInspection(ReturnInspectionRequest request);

    HttpResponse returnReceipt(List<ReturnOrderInfoItem> itemList);





    void saveLog(List<ReturnOrderInfoLog> logs);

    void saveData(List<ReturnOrderInfoItem> orderItems, List<ReturnOrderInfo> orders);

    List<ReturnOrderInfoApplyInboundRespVO> inboundInfo(String code);

    InspectionDetailRespVO inspectionDetail(String code);

    InspectionViewRespVO inspectionView(String code);

    ReturnOrderDetailRespVO directReturnOrderDetail(String code);

    Boolean changeStatus(ChangeOrderStatusReqVO reqVO);

    void saveReturnReceipt(List<ReturnReceiptReqVO> reqVO);

    Boolean changeOrderStatus(String code, Integer status);

    HttpResponse recordWMS(String inboundOderCode);

    Boolean save(List<ReturnOrderInfoReqVO> reqVO);

    Boolean recordDL(ReturnDLReq reqVO);
}
