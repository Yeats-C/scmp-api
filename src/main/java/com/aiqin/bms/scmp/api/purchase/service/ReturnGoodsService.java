package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnDLReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
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

/**
 * 退货接口
 * @date: 2019-06-13
 * @time: 17:35
 */
public interface ReturnGoodsService {

    Boolean save(List<ReturnOrderInfoReqVO> reqVO);

    void saveLog(List<ReturnOrderInfoLog> logs);

    void saveData(List<ReturnOrderInfoItem> orderItems, List<ReturnOrderInfo> orders);

    BasePage<QueryReturnOrderManagementRespVO> returnOrderManagement(QueryReturnOrderManagementReqVO reqVO);

    ReturnOrderDetailRespVO returnOrderDetail(String code);

    List<ReturnOrderInfoApplyInboundRespVO> inboundInfo(String code);

    HttpResponse<PageResData<ReturnOrderInfo>> returnOrderList(ReturnGoodsRequest request);

    InspectionDetailRespVO inspectionDetail(String code);

    HttpResponse saveReturnInspection(ReturnInspectionRequest request);

    void sendToInBound(List<ReturnOrderInfoInspectionItem> items);

    InspectionViewRespVO inspectionView(String code);

    BasePage<QueryReturnOrderManagementRespVO> directReturnOrderManagement(QueryReturnOrderManagementReqVO reqVO);

    ReturnOrderDetailRespVO directReturnOrderDetail(String code);

    Boolean returnReceipt(List<ReturnReceiptReqVO> reqVO, String code);

    Boolean changeStatus(ChangeOrderStatusReqVO reqVO);

    void saveReturnReceipt(List<ReturnReceiptReqVO> reqVO);

    Boolean changeOrderStatus(String code, Integer status);

    HttpResponse record(ReturnReq reqVO);

    Boolean recordDL(ReturnDLReq reqVO);

    HttpResponse recordWMS(String inboundOderCode);
}
