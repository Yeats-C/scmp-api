package com.aiqin.bms.scmp.api.purchase.service.asyn;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuCheckoutDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuCheckoutRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 异步执行保存单据
 */
@Component
@Slf4j
public class AsynSaveDocuments {

    @Resource
    private OrderInfoMapper orderInfoMapper;


    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;

    @Resource
    private ProductSkuDao productSkuDao;

    @Resource
    private ProductSkuCheckoutDao productSkuCheckoutDao;

    @Resource
    private SupplyCompanyDao supplyCompanyDao;

    @Resource
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;


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
            PurchaseOrder savePurchaseOrder = new PurchaseOrder();
            //状态直接已经完成
            savePurchaseOrder.setPurchaseOrderStatus(8);
            savePurchaseOrder.setTransportCenterCode(Global.HB_CODE);
            savePurchaseOrder.setTransportCenterName("华北仓");
            savePurchaseOrder.setWarehouseCode("1048");
            savePurchaseOrder.setWarehouseName("采购直送库");
            savePurchaseOrder.setCreateByName(order.getCreateByName());
            savePurchaseOrder.setUpdateByName(order.getUpdateByName());
            savePurchaseOrder.setInboundLine(1);
            savePurchaseOrder.setPurchaseSource(1);
            //采购单号直接使用订单号
            savePurchaseOrder.setPurchaseOrderCode(orderCode);
            //查询订单商品表
            List<OrderInfoItem> orderInfoItemList = this.orderInfoItemMapper.selectListByOrderCode(orderCode);
            if (CollectionUtils.isEmpty(orderInfoItemList)) {
                log.info("销售单发运存储未找到对应的商品列表:{}", orderCode);
                return;
            }

            for (int i = 0; i < orderInfoItemList.size(); i++) {
                OrderInfoItem orderInfoItem = orderInfoItemList.get(i);
                String skuCode = orderInfoItem.getSkuCode();
                if (i == 0) {
                    ProductSku productSku = this.productSkuDao.selectOneBySkuCode(skuCode);
                    if (Objects.nonNull(productSku)) {
                        String supCode = productSku.getProcurementSectionCode();
                        savePurchaseOrder.setPurchaseGroupCode(supCode);
                        savePurchaseOrder.setPurchaseGroupName(productSku.getProcurementSectionName());
                        //通过供应商信息拿到联系电话和联系人
                        SupplyCompany supplyCompany = this.supplyCompanyDao.selectBySupplierCode(supCode);
                        if (Objects.nonNull(supplyCompany)) {
                            savePurchaseOrder.setSupplierMobile(supplyCompany.getMobilePhone());
                            savePurchaseOrder.setSupplierPerson(supplyCompany.getContactName());
                        }

                    }
                    //通过结账信息拿到结算方式和编码
                    ProductSkuCheckoutRespVo bySkuCode = this.productSkuCheckoutDao.getBySkuCode(skuCode);
                    if (Objects.nonNull(bySkuCode)) {
                        savePurchaseOrder.setSettlementMethodCode(bySkuCode.getSettlementMethodCode());
                        savePurchaseOrder.setSettlementMethodName(bySkuCode.getSettlementMethodName());
                    }
                }
                //获取含税单价取sku与供应商对应的采购价。
                ProductSkuSupplyUnitDraft productSkuSupplyUnit = this.productSkuSupplyUnitDao.selectOneBySkuCode(skuCode);

            }

            //子表
            //调用保存接口


        }


    }
}
