package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnInspectionReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnInspectionReq;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnOrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.InspectionDetailRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.QueryReturnInspectionRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.QueryReturnOrderManagementRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderDetailRespVO;

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
    Boolean saveReturnInspection(List<ReturnInspectionReq> reqVO);
    /**
     * 调用库房入库接口生成入库单
     * @author NullPointException
     * @date 2019/6/24
     * @param items
     * @return void
     */
    void sendToOutBound(List<ReturnOrderInfoInspectionItem> items);
}
