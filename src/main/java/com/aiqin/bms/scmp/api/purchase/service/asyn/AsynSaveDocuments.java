package com.aiqin.bms.scmp.api.purchase.service.asyn;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuCheckoutDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuCheckoutRespVo;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.mapper.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.util.CodeUtils;
import com.aiqin.bms.scmp.api.util.RedisTool;
import com.aiqin.ground.util.id.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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


    @Resource
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;

    @Resource
    private ReturnOrderInfoInspectionItemMapper returnOrderInfoInspectionItemMapper;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private PurchaseOrderProductDao purchaseOrderProductDao;

    @Resource
    private PurchaseBatchDao purchaseBatchDao;

    @Resource
    private RejectRecordDao rejectRecordDao;

    @Resource
    private RejectRecordDetailDao rejectRecordDetailDao;

    @Resource
    private RejectRecordBatchDao rejectRecordBatchDao;

    @Resource
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;

    @Resource
    private RedisTool redisTool;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private EncodingRuleDao encodingRuleDao;

     @Resource
    private CodeUtils codeUtils;


    /**
     * @param code 销售单发货时候生成采购单保存
     */
    @Async("myTaskAsyncPool")
    public  void savePurchase(String code) {
        if (StringUtils.isBlank(code)) {
            log.info("异步保存销售发货单号为空");
            return;
        }
        String lockKey = "lock:savePurchase:" + code;
        String requestId = UUID.randomUUID().toString();
        try {
        AsynSaveDocuments bean = applicationContext.getBean(AsynSaveDocuments.class);
        bean.savePurchaseOrder(code);
        } finally {
            redisTool.releaseDistributedLock(lockKey, requestId);
        }

    }


    /**
     * @param code 退货单收获时生成退购单保存
     */
    @Async("myTaskAsyncPool")
    public void saveReject(String code) {
        if (StringUtils.isBlank(code)) {
            log.info("异步保存退货收货单号为空");
            return;

        }
        String lockKey = "lock:saveReject:" + code;
        String requestId = UUID.randomUUID().toString();
        try {
            if (!redisTool.tryGetDistributedLock(lockKey, requestId)) {
                log.info("异步保存退货收货单重复请求单号={}", code);
                return;
            }
            AsynSaveDocuments bean = applicationContext.getBean(AsynSaveDocuments.class);
            bean.saveRejectOrder(code);
        } finally {
            redisTool.releaseDistributedLock(lockKey, requestId);
        }

    }


    @Transactional(rollbackFor = Exception.class)
    public void savePurchaseOrder(String orderCode) {
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
            List<String> codes = new ArrayList<>();
            codes.add(orderCode);
            List<PurchaseOrder> purchaseOrders = this.purchaseOrderDao.selectByPurchaseApplyCode(orderCode);
            if (!CollectionUtils.isEmpty(purchaseOrders)) {
                log.info("此销售单数据已经存在={}", orderCode);
                return;
            }
            PurchaseOrder savePurchaseOrder = new PurchaseOrder();
            BeanUtils.copyProperties(order, savePurchaseOrder);

            // 获取采购单编码
            //String purchaseOrderCode = baseService.getCode(null, EncodingRuleType.PURCHASE_ORDER_CODE);
//            // 变更采购单号
//            String purchaseOrderCode = String.valueOf(encodingRule.getNumberingValue());
//            encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
            String purchaseOrderCode = codeUtils.getRedisCode(EncodingRuleType.PURCHASE_ORDER_CODE);
            savePurchaseOrder.setPurchaseOrderCode(purchaseOrderCode);
            //状态直接已经完成
            String purchaseId = IdUtil.purchaseId();
            savePurchaseOrder.setPurchaseOrderId(purchaseId);
            savePurchaseOrder.setPurchaseApplyCode(orderCode);
            savePurchaseOrder.setPurchaseSource(1);
            savePurchaseOrder.setPurchaseOrderStatus(8);
            savePurchaseOrder.setTransportCenterCode(Global.HB_CODE);
            savePurchaseOrder.setTransportCenterName("华北仓");
            savePurchaseOrder.setWarehouseCode("1048");
            savePurchaseOrder.setWarehouseName("采购直送库");
            savePurchaseOrder.setInboundLine(1);
            savePurchaseOrder.setPurchaseSource(1);
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

            //最小单位数量
            Long totalCount = 0L;
            //实际最小单位数量
            Long actualTotalCount = 0L;
            //实际商品金额
            BigDecimal actualProductAmount = BigDecimal.ZERO;
            //实际赠品金额
            BigDecimal actualGiftAmount = BigDecimal.ZERO;
            //进项税率
            BigDecimal tax = BigDecimal.ZERO;
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

                        SupplyCompany supplyCompany = this.supplyCompanyDao.selectBySupplierCode(order.getSupplierCode());
                        if (Objects.nonNull(supplyCompany)) {
                            savePurchaseOrder.setSupplierMobile(supplyCompany.getMobilePhone());
                            savePurchaseOrder.setSupplierPerson(supplyCompany.getContactName());
                            savePurchaseOrder.setSupplierCode(supplyCompany.getSupplyCode());
                            savePurchaseOrder.setSupplierName(supplyCompany.getSupplyName());
                            savePurchaseOrder.setSupplierCompanyCode(supplyCompany.getSupplierCode());
                            savePurchaseOrder.setSupplierCompanyName(supplyCompany.getSupplierName());
                        }

                    }


                    //通过结账信息拿到结算方式和编码
                    ProductSkuCheckoutRespVo bySkuCode = this.productSkuCheckoutDao.getBySkuCode(skuCode);
                    if (Objects.nonNull(bySkuCode)) {
                        savePurchaseOrder.setSettlementMethodCode(bySkuCode.getSettlementMethodCode());
                        savePurchaseOrder.setSettlementMethodName(bySkuCode.getSettlementMethodName());
                        tax = bySkuCode.getInputTaxRate();
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
                product.setPurchaseOrderId(purchaseId);
                String id = UUID.randomUUID().toString().replaceAll("-", "");
                product.setOrderProductId(id);
                product.setPurchaseOrderCode(purchaseOrderCode);
                product.setProductSpec(orderInfoItem.getSpec());
                product.setColorName(orderInfoItem.getColorName());
                product.setModelNumber(orderInfoItem.getModelCode());
                product.setProductType(orderInfoItem.getGivePromotion());
                product.setSingleCount(Integer.parseInt(orderInfoItem.getNum() + ""));
                product.setTaxRate(orderInfoItem.getTax());
                product.setActualSingleCount(Integer.parseInt(orderInfoItem.getActualDeliverNum() + ""));
                // 是否是赠品(0否1是)
                Long productLineNum = orderInfoItem.getProductLineNum();
                product.setLinnum(Integer.parseInt(productLineNum + ""));
                product.setCreateByName(order.getCreateByName());
                product.setUpdateByName(order.getUpdateByName());
                product.setProductAmount(taxIncludedPrice);
                product.setTaxRate(tax);
                //数量
                BigDecimal num = new BigDecimal(orderInfoItem.getNum() + "");
                //累加数量
                totalCount = totalCount + orderInfoItem.getNum();
                //实发数量
                Long actNum = orderInfoItem.getActualDeliverNum();
                //累加实际数量
                actualTotalCount = actualTotalCount + actNum;
                BigDecimal actualDeliverNum = new BigDecimal(actNum + "");
                //含税总价
                BigDecimal amount = taxIncludedPrice.multiply(num);
                //实际含税总价
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
                product.setProductTotalAmount(amount);
                product.setCreateTime(now);
                purchaseOrderProduct.add(product);
            }

            savePurchaseOrder.setProductTotalAmount(productTotalAmount);
            savePurchaseOrder.setActualProductAmount(actualProductAmount);
            savePurchaseOrder.setGiftTaxSum(giftTaxSum);
            savePurchaseOrder.setActualGiftAmount(actualGiftAmount);
            savePurchaseOrder.setSingleCount(Integer.parseInt(totalCount + ""));
            savePurchaseOrder.setActualTotalCount(actualTotalCount);
            savePurchaseOrder.setCreateTime(now);
            //设置批次信息
            List<OrderInfoItemProductBatch> detailBatchList = this.orderInfoItemProductBatchMapper.selectByPrimaryKey(orderCode);
            if (!CollectionUtils.isEmpty(detailBatchList)) {
                List<PurchaseBatch> purchaseBatchList = new ArrayList<>();
                detailBatchList.forEach(op -> {
                    PurchaseBatch purchaseBatch = new PurchaseBatch();
                    BeanUtils.copyProperties(op, purchaseBatch);
                    purchaseBatch.setPurchaseOderCode(purchaseOrderCode);
                    purchaseBatch.setCreateTime(now);
                    purchaseBatchList.add(purchaseBatch);

                });
                //保存批次表数据
                this.purchaseBatchDao.insertAll(purchaseBatchList);
            }
            //保存主表数据
            this.purchaseOrderDao.insert(savePurchaseOrder);


            //保存明细表数据
            this.purchaseOrderProductDao.insertAll(purchaseOrderProduct);

        }
    }

    /**
     * 收货后保存退供单
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveRejectOrder(String returnOrderCode) {
        ReturnOrderInfo returnOrderInfo = this.returnOrderInfoMapper.selectByCode(returnOrderCode);
        if (Objects.isNull(returnOrderInfo)) {
            log.info("未找到对应的退货物信息={}", returnOrderCode);
            return;
        }

        Date now = new Date();
        Integer orderTypeCode = returnOrderInfo.getOrderType();
        //类型编码（1.配送 2.直送 3.货架直送  4.采购直送）
        //订单类型编码为 货架直送和采购直送的需要生成采购单存储
        if (orderTypeCode.equals(Global.ORDER_TYPE_3) || orderTypeCode.equals(Global.ORDER_TYPE_4)) {

            RejectRecord rejectRecord = this.rejectRecordDao.selectByRejectCode(returnOrderCode);
            if (Objects.nonNull(rejectRecord)) {
                log.info("此退货单数据已经存在={}", returnOrderCode);
                return;
            }
            RejectRecord saveRejectRecord = new RejectRecord();
            BeanUtils.copyProperties(returnOrderInfo, saveRejectRecord);
            String rejectRecordId = IdUtil.rejectRecordId();
            saveRejectRecord.setRejectRecordId(rejectRecordId);
            saveRejectRecord.setRejectRecordCode(returnOrderCode);
            saveRejectRecord.setSourceCode(returnOrderCode);
            //状态直接已经完成
            saveRejectRecord.setRejectStatus(3);
            saveRejectRecord.setTransportCenterCode(Global.HB_CODE);
            saveRejectRecord.setTransportCenterName("华北仓");
            saveRejectRecord.setWarehouseCode("1048");
            saveRejectRecord.setWarehouseName("采购直送库");
            saveRejectRecord.setCreateByName(returnOrderInfo.getCreateByName());
            saveRejectRecord.setUpdateByName(returnOrderInfo.getUpdateByName());
            saveRejectRecord.setSourceType(1);


            //查询订单商品表
            List<ReturnOrderInfoItem> returnOrderInfoItems = this.returnOrderInfoItemMapper.selectByReturnOrderCode(returnOrderCode);
            if (CollectionUtils.isEmpty(returnOrderInfoItems)) {
                log.info("退货单收货存储未找到对应的商品列表:{}", returnOrderCode);
                return;
            }
            //商品含税金额
            BigDecimal productTotalAmount = BigDecimal.ZERO;
            //赠品含税金额
            BigDecimal giftTaxSum = BigDecimal.ZERO;
            //实际商品金额
            BigDecimal actualProductAmount = BigDecimal.ZERO;
            //实际赠品金额
            BigDecimal actualGiftAmount = BigDecimal.ZERO;
            //最小单位数量
            Long totalCount = 0L;
            //实际最小单位数量
            Integer actualTotalCount = 0;
            //实际商品数量
            Integer actualProductCount = 0;
            //实际赠品数量
            Integer actualGiftCount = 0;
            //product_count 商品数量
            Long productCount = 0L;
            //gift_count 赠品数量
            Long giftCount = 0L;
            //进项税率
            BigDecimal tax = BigDecimal.ZERO;
            List<RejectRecordDetail> RejectRecordDetailList = new ArrayList<>();
            for (int i = 0; i < returnOrderInfoItems.size(); i++) {
                ReturnOrderInfoItem returnDetail = returnOrderInfoItems.get(i);
                String skuCode = returnDetail.getSkuCode();
                RejectRecordDetail detail = new RejectRecordDetail();
                Integer givePromotion = returnDetail.getGivePromotion();
                if (i == 0) {
                    ProductSku productSku = this.productSkuDao.selectOneBySkuCode(skuCode);
                    if (Objects.nonNull(productSku)) {
                        String supCode = productSku.getProcurementSectionCode();
                        saveRejectRecord.setPurchaseGroupCode(supCode);
                        saveRejectRecord.setPurchaseGroupName(productSku.getProcurementSectionName());
                        //通过供应商信息拿到联系电话和联系人
                        SupplyCompany supplyCompany = this.supplyCompanyDao.selectBySupplierCode(supCode);
                        if (Objects.nonNull(supplyCompany)) {
                            saveRejectRecord.setSupplierMobile(supplyCompany.getMobilePhone());
                            saveRejectRecord.setSupplierPerson(supplyCompany.getContactName());
                            saveRejectRecord.setSupplierCode(supplyCompany.getSupplyCode());
                            saveRejectRecord.setSupplierName(supplyCompany.getSupplyName());
                            saveRejectRecord.setSupplierCompanyCode(supplyCompany.getSupplierCode());
                        }

                    }
                    //通过结账信息拿到结算方式和编码
                    ProductSkuCheckoutRespVo bySkuCode = this.productSkuCheckoutDao.getBySkuCode(skuCode);
                    if (Objects.nonNull(bySkuCode)) {
                        saveRejectRecord.setSettlementMethodCode(bySkuCode.getSettlementMethodCode());
                        saveRejectRecord.setSettlementMethodName(bySkuCode.getSettlementMethodName());
                        tax = bySkuCode.getInputTaxRate();
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
                BeanUtils.copyProperties(returnDetail, detail);
                detail.setRejectRecordCode(returnOrderCode);
                detail.setRejectRecordId(rejectRecordId);
                detail.setRejectRecordDetailId(returnOrderCode);
                detail.setProductSpec(returnDetail.getSpec());
                detail.setColorName(returnDetail.getColorName());
                detail.setModelNumber(returnDetail.getModelCode());
                detail.setProductType(returnDetail.getGivePromotion());
                detail.setTaxRate(tax);
                // 是否是赠品(0否1是)
                Long productLineNum = returnDetail.getProductLineNum();
                detail.setLineCode(Integer.parseInt(productLineNum + ""));
                detail.setCreateByName(returnOrderInfo.getCreateByName());
                detail.setUpdateByName(returnOrderInfo.getUpdateByName());
                //数量
                BigDecimal num = new BigDecimal(returnDetail.getNum() + "");
                //累加数量
                totalCount = totalCount + returnDetail.getNum();
                //累加实际数量
                Integer actNum = returnDetail.getActualInboundNum();
                //实发数量
                BigDecimal actualDeliverNum = new BigDecimal(actNum + "");
                actualTotalCount = actualTotalCount + actNum;
                //实际含税总价
                BigDecimal actualAmont = taxIncludedPrice.multiply(actualDeliverNum);
                //含税总价
                BigDecimal amount = taxIncludedPrice.multiply(num);
                if (givePromotion.equals(0)) {
                    //累加商品含税总价
                    productTotalAmount = productTotalAmount.add(actualAmont);
                    //累加实际商品金额
                    actualProductAmount = actualProductAmount.add(actualAmont);
                    //累加实际商品数量
                    actualProductCount = actualProductCount + actNum;
                    productCount = productCount + returnDetail.getNum();
                } else {
                    //累加赠品含税总价
                    giftTaxSum = giftTaxSum.add(actualAmont);
                    actualGiftAmount = actualGiftAmount.add(actualAmont);
                    //累加实际赠品数量
                    actualGiftCount = actualGiftCount + actNum;
                    giftCount = giftCount + returnDetail.getNum();

                }
                detail.setTotalCount(returnDetail.getNum());
                detail.setActualTotalCount(Long.parseLong(returnDetail.getActualInboundNum() + ""));
                detail.setProductAmount(taxIncludedPrice);
                detail.setProductTotalAmount(amount);
                detail.setActualProductTotalAmount(actualAmont);
                detail.setCreateTime(now);
                detail.setTaxRate(returnDetail.getTax());
                RejectRecordDetailList.add(detail);
            }
            saveRejectRecord.setTotalCount(totalCount);
            saveRejectRecord.setProductCount(productCount);
            saveRejectRecord.setGiftCount(giftCount);
            saveRejectRecord.setActualTotalCount(Long.parseLong(actualTotalCount + ""));
            saveRejectRecord.setActualProductCount(Long.parseLong(actualProductCount + ""));
            saveRejectRecord.setActualGiftCount(Long.parseLong(actualGiftCount + ""));
            saveRejectRecord.setProductTaxAmount(productTotalAmount);
            saveRejectRecord.setActualProductTaxAmount(actualProductAmount);
            saveRejectRecord.setGiftTaxAmount(giftTaxSum);
            saveRejectRecord.setActualGiftTaxAmount(actualGiftAmount);
            saveRejectRecord.setCreateTime(now);
            //设置批次信息
            List<ReturnOrderInfoInspectionItem> batchList = returnOrderInfoInspectionItemMapper.returnOrderBatchList(returnOrderCode);
            if (!CollectionUtils.isEmpty(batchList)) {
                List<RejectRecordBatch> purchaseBatchList = new ArrayList<>();
                batchList.forEach(op -> {
                    RejectRecordBatch rejectRecordBatch = new RejectRecordBatch();
                    BeanUtils.copyProperties(op, rejectRecordBatch);
                    rejectRecordBatch.setRejectRecordCode(returnOrderCode);
                    rejectRecordBatch.setCreateTime(now);
                    purchaseBatchList.add(rejectRecordBatch);
                });
                //保存批次表数据
                this.rejectRecordBatchDao.insertAll(purchaseBatchList);
            }

            //保存主表数据
            this.rejectRecordDao.insert(saveRejectRecord);
            //保存明细表数据
            this.rejectRecordDetailDao.insertAll(RejectRecordDetailList);
        }


    }
}
