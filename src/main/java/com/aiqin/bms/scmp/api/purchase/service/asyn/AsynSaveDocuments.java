package com.aiqin.bms.scmp.api.purchase.service.asyn;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 异步执行保存单据
 */
@Component
public class AsynSaveDocuments {

    @Resource
    private OrderInfoMapper orderInfoMapper;


    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;


    @Async("myTaskAsyncPool")
    public void save(String orderCode) {
        OrderInfo order = orderInfoMapper.selectByOrderCode2(orderCode);
        if (Objects.isNull(order)) {
            throw new BizException(ResultCode.CAN_NOT_FIND_ORDER);
        }
        Integer orderTypeCode = order.getOrderTypeCode();
        //类型编码（1.配送 2.直送 3.货架直送  4.采购直送）
        //订单类型编码为 货架直送和采购直送的需要生成采购单存储
        if (orderTypeCode.equals(Global.ORDER_TYPE_3) || orderTypeCode.equals(Global.ORDER_TYPE_4)) {
            //封装参数
            PurchaseOrder savePurchaseOrder = new PurchaseOrder();
            savePurchaseOrder.setTransportCenterCode(Global.HB_CODE);
            savePurchaseOrder.setTransportCenterName("华北仓");
            savePurchaseOrder.setWarehouseCode("1048");
            savePurchaseOrder.setWarehouseName("采购直送库");
            //查询订单商品表
            OrderInfoItem queryOrderInfoItem=new OrderInfoItem();

           // queryOrderInfoItem.set
            //this.orderInfoItemMapper


        }


    }
}
