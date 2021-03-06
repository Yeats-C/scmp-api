package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnGoodsRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnOrderInfoMapper {

    Integer insert(ReturnOrderInfo record);

    //ReturnOrderInfo selectByPrimaryKey(Long id);

    Integer update(ReturnOrderInfo record);

    ReturnOrderInfo selectByCode(String returnOrderCode);

    List<ReturnOrderInfo> listForSap(SapOrderRequest sapOrderRequest);

    int updateByOrderCodes(@Param(value = "list")List<String> orderCodes);

    Integer insertList(List<ReturnOrderInfo> returnOrderInfoList);

    /** 调拨sap返回sap同步时间 */
    void updateReturnOrderSynchrSap(ReturnOrderInfo returnOrderInfo);

    List<ReturnOrderInfo> list(ReturnGoodsRequest request);

    Integer listCount(ReturnGoodsRequest request);

    List<String> selectByReturnOrderCodeList(@Param("list") List<String> retrunOrderCodeList);

    int insertMany(@Param("list") List<ReturnOrderInfo> returnOrderInfos);
}