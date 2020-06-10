package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnGoodsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnOrderInfoMapper {

    Integer insert(ReturnOrderInfo record);

    ReturnOrderInfo selectByPrimaryKey(Long id);

    Integer update(ReturnOrderInfo record);

    int insertBatch(List<ReturnOrderInfo> orders);

    List<QueryReturnOrderManagementRespVO> selectReturnOrderManagementList(QueryReturnOrderManagementReqVO reqVO);

    ReturnOrderDetailRespVO selectReturnOrderDetail(String code);

    InspectionDetailRespVO selectInspectionDetail(String code);

    List<ReturnOrderInfoInspectionItemRespVO> selectInspectionItemList(@Param("code") String code, @Param("orderCode") String orderCode);

    InspectionViewRespVO selectInspectionView(String code);

    ReturnOrderInfoDTO selectByCode(String returnOrderCode);

    List<ReturnOrderInfoApplyInboundRespVO> selectInbound(String code);

    ReturnOrderInfo selectByCode1(String orderCode);

    int updateByOrderCode(ReturnOrderInfo order);

    int updateByReturnOrderCodeSelective(ReturnOrderInfo returnOrderInfo);

    List<ReturnOrderInfo> listForSap(SapOrderRequest sapOrderRequest);

    int updateByOrderCodes(@Param(value = "list")List<String> orderCodes);

    Integer insertList(List<ReturnOrderInfo> returnOrderInfoList);

    /** 调拨sap返回sap同步时间 */
    void updateReturnOrderSynchrSap(ReturnOrderInfo returnOrderInfo);

    List<ReturnOrderInfo> list(ReturnGoodsRequest request);

    Integer listCount(ReturnGoodsRequest request);
}