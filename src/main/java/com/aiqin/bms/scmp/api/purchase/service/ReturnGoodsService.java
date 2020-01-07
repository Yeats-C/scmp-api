package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnDLReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.ReturnReceiptReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ReturnDLResp;
import com.aiqin.bms.scmp.api.product.domain.response.ReturnResp;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ChangeOrderStatusReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnInspectionReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnInspectionReq;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnOrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;

import java.util.List;

/**
 * Description:
 * 退货接口
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:35
 */
public interface ReturnGoodsService {
    /**
     * 退货单保存
     * @author NullPointException
     * @date 2019/6/19
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean save(List<ReturnOrderInfoReqVO> reqVO);

    void saveLog(List<ReturnOrderInfoLog> logs);

    /**
     * 保存数据
     * @author NullPointException
     * @date 2019/6/19
     * @param orderItems
     * @param orders
     * @return void
     */
    void saveData(List<ReturnOrderInfoItem> orderItems, List<ReturnOrderInfo> orders);
    /**
     * 退货管理
     * @author NullPointException
     * @date 2019/6/19
     * @param reqVO
     * @return com.aiqin.bms.scmp.api.base.BasePage<com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.QueryReturnOrderManagementRespVO>
     */
    BasePage<QueryReturnOrderManagementRespVO> returnOrderManagement(QueryReturnOrderManagementReqVO reqVO);
    /**
     * 退货详情
     * @author NullPointException
     * @date 2019/6/19
     * @param code
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderDetailRespVO
     */
    ReturnOrderDetailRespVO returnOrderDetail(String code);

    /**
     *
     * @author NullPointException
     * @date 2019/7/3
     * @param code
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderInfoApplyInboundRespVO>
     */
    List<ReturnOrderInfoApplyInboundRespVO> inboundInfo(String code);

    /**
     * 退货验货
     * @author NullPointException
     * @date 2019/6/20
     * @param reqVO
     * @return com.aiqin.bms.scmp.api.base.BasePage<com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.QueryReturnInspectionRespVO>
     */
    BasePage<QueryReturnInspectionRespVO> returnInspection(QueryReturnInspectionReqVO reqVO);
    /**
     * 验货详情
     * @author NullPointException
     * @date 2019/6/20
     * @param code
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.InspectionDetailRespVO
     */
    InspectionDetailRespVO inspectionDetail(String code);
    /**
     * 验货信息保存
     * @author NullPointException
     * @date 2019/6/24
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean saveReturnInspection(List<ReturnInspectionReq> reqVO,String remark);
    /**
     * 调用库房入库接口生成入库单
     * @author NullPointException
     * @date 2019/6/24
     * @param items
     * @return void
     */
    void sendToInBound(List<ReturnOrderInfoInspectionItem> items);
    /**
     * 验货查看
     * @author NullPointException
     * @date 2019/6/24
     * @param code
     * @return InspectionViewRespVO
     */
    InspectionViewRespVO inspectionView(String code);
    /**
     * 直送订单管理
     * @author NullPointException
     * @date 2019/7/3
     * @param reqVO
     * @return com.aiqin.bms.scmp.api.base.BasePage<com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.QueryReturnOrderManagementRespVO>
     */
    BasePage<QueryReturnOrderManagementRespVO> directReturnOrderManagement(QueryReturnOrderManagementReqVO reqVO);
    /**
     * 直送退后详情
     * @author NullPointException
     * @date 2019/7/3
     * @param code
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderDetailRespVO
     */
    ReturnOrderDetailRespVO directReturnOrderDetail(String code);
    /**
     * 退货直送订单保存入库数量
     * @author NullPointException
     * @date 2019/7/4
     * @param reqVO
     * @param code
     * @return java.lang.Boolean
     */
    Boolean returnReceipt(List<ReturnReceiptReqVO> reqVO, String code);
    /**
     * 修改订单状态
     * @author NullPointException
     * @date 2019/7/4
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean changeStatus(ChangeOrderStatusReqVO reqVO);

    /**
     * 保存退货收货信息
     * @author NullPointException
     * @date 2019/7/4
     * @param reqVO
     * @return int
     */
    void saveReturnReceipt(List<ReturnReceiptReqVO> reqVO);
    /**
     * 修改订单状态
     * @author NullPointException
     * @date 2019/7/4
     * @param code
     * @param status
     * @return java.lang.Boolean
     */
    Boolean changeOrderStatus(String code, Integer status);
    /**
     * 进行订单记录
     * @author NullPointException
     * @date 2019/7/4
     * @return java.lang.Boolean
     */
    String record(ReturnReq reqVO);

    Boolean recordDL(ReturnDLReq reqVO);
}
