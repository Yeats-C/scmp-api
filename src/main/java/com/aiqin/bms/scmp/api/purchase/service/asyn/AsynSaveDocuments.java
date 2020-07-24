package com.aiqin.bms.scmp.api.purchase.service.asyn;

import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuCheckoutDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuCheckoutRespVo;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseBatch;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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


    @Resource
    private ReturnOrderInfoMapper returnOrderInfoMapper;


    @Async("myTaskAsyncPool")
    public void savePurchase(String orderCode) {
        OrderInfo order = orderInfoMapper.selectByOrderCode2(orderCode);
        if (Objects.isNull(order)) {
            log.info("未找到对应的销售单信息={}", orderCode);
            return;
        }
        Date now = new Date();
        Integer orderTypeCode = order.getOrderTypeCode();
        //类型编码（1.配送 2.直送 3.货架直送  4.采购直送）
        //订单类型编码为 货架直送和采购直送的需要生成采购单存储
        if (orderTypeCode.equals(Global.ORDER_TYPE_3) || orderTypeCode.equals(Global.ORDER_TYPE_4)) {
            PurchaseOrder savePurchaseOrder = new PurchaseOrder();
            BeanUtils.copyProperties(order, savePurchaseOrder);
            //状态直接已经完成
            savePurchaseOrder.setPurchaseSource(2);
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
            savePurchaseOrder.setPurchaseMode(order.getOrderTypeCode() == 1 ? 0 : 1);
            //查询订单商品表
            List<OrderInfoItem> orderInfoItemList = this.orderInfoItemMapper.selectListByOrderCode(orderCode);
            if (CollectionUtils.isEmpty(orderInfoItemList)) {
                log.info("销售单发运存储未找到对应的商品列表:{}", orderCode);
                return;
            }
            //商品含税金额
            BigDecimal productTotalAmount = BigDecimal.ZERO;
            //赠品含税金额
            BigDecimal giftTaxSum = BigDecimal.ZERO;
            //实际最小单位数量
            Long actualTotalCount = 0L;
            //实际商品金额
            BigDecimal actualProductAmount = BigDecimal.ZERO;
            //实际赠品金额
            BigDecimal actualGiftAmount = BigDecimal.ZERO;
            List<PurchaseOrderProduct> purchaseOrderProduct = new ArrayList<>();
            for (int i = 0; i < orderInfoItemList.size(); i++) {
                OrderInfoItem orderInfoItem = orderInfoItemList.get(i);
                String skuCode = orderInfoItem.getSkuCode();
                PurchaseOrderProduct product = new PurchaseOrderProduct();
                Integer givePromotion = orderInfoItem.getGivePromotion();
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
                            savePurchaseOrder.setSupplierCode(supplyCompany.getSupplierCode());
                            savePurchaseOrder.setSupplierName(supplyCompany.getSupplierName());
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
                ProductSkuSupplyUnit productSkuSupplyUnit = this.productSkuSupplyUnitDao.selectOneBySkuCode(skuCode);
                if (Objects.isNull(productSkuSupplyUnit)) {
                    log.info("未找到sku对应的供应商信息", skuCode);
                    return;
                }
                //sku对应的供应商采购价格
                BigDecimal taxIncludedPrice = productSkuSupplyUnit.getTaxIncludedPrice();
                BeanUtils.copyProperties(orderInfoItem, product);
                product.setPurchaseOrderCode(orderCode);
                product.setSkuCode(orderInfoItem.getSkuCode());
                product.setSkuName(orderInfoItem.getSkuName());
                product.setProductSpec(orderInfoItem.getSpec());
                product.setColorName(orderInfoItem.getColorName());
                product.setModelNumber(orderInfoItem.getModelCode());
                product.setProductType(orderInfoItem.getGivePromotion());
                product.setSingleCount(Integer.parseInt(orderInfoItem.getNum() + ""));
                product.setTaxRate(orderInfoItem.getTax());
                product.setActualSingleCount(Integer.parseInt(orderInfoItem.getActualDeliverNum() + ""));
                // 是否是赠品(0否1是)
                Long productLineNum = orderInfoItem.getProductLineNum();
                Long promotionLineNum = orderInfoItem.getPromotionLineNum();
                product.setLinnum(givePromotion == 0 ? Integer.parseInt(productLineNum + "") : Integer.parseInt(promotionLineNum + ""));
                product.setCreateByName(order.getCreateByName());
                product.setUpdateByName(order.getUpdateByName());
                product.setProductAmount(taxIncludedPrice);
                Long num = orderInfoItem.getActualDeliverNum();
                actualTotalCount = actualTotalCount + num;
                //实发数量
                BigDecimal actualDeliverNum = new BigDecimal(num + "");
                //含税总价
                BigDecimal actualAmont = taxIncludedPrice.multiply(actualDeliverNum);
                if (givePromotion.equals(0)) {
                    //活动分摊
                    BigDecimal activityApportionment = orderInfoItem.getActivityApportionment() != null ? orderInfoItem.getActivityApportionment() : BigDecimal.ZERO;
                    //优惠分摊
                    BigDecimal preferentialAllocation = orderInfoItem.getPreferentialAllocation() != null ? orderInfoItem.getPreferentialAllocation() : BigDecimal.ZERO;
                    //累加商品含税总价
                    productTotalAmount = productTotalAmount.add(actualAmont);
                    //累加实际商品金额
                    actualProductAmount = actualProductAmount.add(actualAmont).subtract(activityApportionment).subtract(preferentialAllocation);


                } else {
                    //累加赠品含税总价
                    giftTaxSum = giftTaxSum.add(actualAmont);
                    actualGiftAmount = actualGiftAmount.add(actualAmont);
                }
                product.setProductTotalAmount(actualAmont);
                product.setCreateTime(now);
                purchaseOrderProduct.add(product);
            }
            savePurchaseOrder.setProductTotalAmount(productTotalAmount);
            savePurchaseOrder.setActualProductAmount(actualProductAmount);
            savePurchaseOrder.setGiftTaxSum(giftTaxSum);
            savePurchaseOrder.setActualGiftAmount(actualGiftAmount);
            savePurchaseOrder.setCreateTime(now);
            //设置批次信息
            List<OrderInfoItemProductBatch> detailBatchList = order.getDetailBatchList();
            if (!CollectionUtils.isEmpty(detailBatchList)) {
                List<PurchaseBatch> purchaseBatchList = new ArrayList<>();
                detailBatchList.forEach(op -> {
                    PurchaseBatch purchaseBatch = new PurchaseBatch();
                    BeanUtils.copyProperties(op, purchaseBatch);
                    purchaseBatch.setPurchaseOderCode(orderCode);
                    purchaseBatch.setCreateTime(now);
                    purchaseBatchList.add(purchaseBatch);

                });
            }

            //调用保存接口 保存数据
        }
    }

    /**
     * 收货后保存退供单
     */
    @Async("myTaskAsyncPool")
    public void saveReject(String returnOrderCode) {
        ReturnOrderInfo returnOrderInfo = this.returnOrderInfoMapper.selectByCode(returnOrderCode);
        if (Objects.nonNull(returnOrderInfo)) {
            log.info("未找到对应的退货物信息={}", returnOrderCode);
            return;
        }

        Date now = new Date();
        Integer orderTypeCode = returnOrderInfo.getOrderType();
        //类型编码（1.配送 2.直送 3.货架直送  4.采购直送）
        //订单类型编码为 货架直送和采购直送的需要生成采购单存储
        if (orderTypeCode.equals(Global.ORDER_TYPE_3) || orderTypeCode.equals(Global.ORDER_TYPE_4)) {


        }


    }
}
