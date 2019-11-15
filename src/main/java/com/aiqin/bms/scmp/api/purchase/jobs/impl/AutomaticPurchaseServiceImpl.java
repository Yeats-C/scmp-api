package com.aiqin.bms.scmp.api.purchase.jobs.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.bms.scmp.api.common.PurchaseOrderLogEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacity;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSupplyUnitCapacityMapper;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.jobs.AutomaticPurchaseService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.PurchaseGroup;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierRuleMapper;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhao shuai
 * @create: 2019-07-24
 **/
@Component
public class AutomaticPurchaseServiceImpl implements AutomaticPurchaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutomaticPurchaseServiceImpl.class);

    @Resource
    private BiSmartReplenishmentDao biSmartReplenishmentDao;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private PurchaseApplyDao purchaseApplyDao;
    @Resource
    private PurchaseApplyProductDao purchaseApplyProductDao;
    @Resource
    private PurchaseOrderDao purchaseOrderDao;
    @Resource
    private BiAutomaticOrderDao biAutomaticOrderDao;
    @Resource
    private SupplierRuleMapper supplierRuleDao;
    @Resource
    private ProductSkuSupplyUnitCapacityMapper productSkuSupplyUnitCapacityDao;
    @Resource
    private PurchaseOrderDetailsDao purchaseOrderDetailsDao;
    @Resource
    private OperationLogDao operationLogDao;

    @Scheduled(cron = "0 0 10 1 * ?")  //每月1号的10:00执行
    public void automatic(){
        DateTime dateTime = new DateTime(Calendar.getInstance().getTime());
        String data = dateTime.toString("yyyy-MM-dd");
        this.automaticPurchase(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse automaticPurchase(String data){
        String beginTime = data + " 00:00:00";
        String finishTime = data + " 23:59:59";
        // 查询采购组
        List<PurchaseApply> applyList = Lists.newArrayList();
        PurchaseApply purchaseApply;
        List<PurchaseGroup> purchaseGroup = biSmartReplenishmentDao.getPurchaseGroup(beginTime, finishTime);
        if(CollectionUtils.isNotEmpty(purchaseGroup)){
          for(PurchaseGroup group:purchaseGroup){
              if(StringUtils.isBlank(group.getPurchaseGroupCode())){
                  continue;
              }
              purchaseApply = new PurchaseApply();
              purchaseApply.setPurchaseApplyId(IdUtil.purchaseId());
              purchaseApply.setApplyType(Global.PURCHASE_APPLY_TYPE_1);
              purchaseApply.setApplyStatus(Global.PURCHASE_APPLY_STATUS_0);
              purchaseApply.setPurchaseGroupCode(group.getPurchaseGroupCode());
              purchaseApply.setPurchaseGroupName(group.getPurchaseGroupName());
              purchaseApply.setCreateByName("系统");
              purchaseApply.setCreateById("0");
              // 生成申请采购单号
              EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_APPLY_CODE);
              String purchaseApplyCode = "CS" + String.valueOf(encodingRule.getNumberingValue());
              purchaseApply.setPurchaseApplyCode(purchaseApplyCode);
              // 查询sku 的相关数据
              List<PurchaseApplyProduct> applyProducts = biSmartReplenishmentDao.skuInfo(beginTime, finishTime, group.getPurchaseGroupCode());
              PurchaseApplyProduct applyProduct;
              List<PurchaseApplyProduct> productList = Lists.newArrayList();
              if(CollectionUtils.isNotEmpty(applyProducts)){
                  for(PurchaseApplyProduct product:applyProducts){
                      applyProduct = new PurchaseApplyProduct();
                      BeanUtils.copyProperties(product, applyProduct);
                      applyProduct.setApplyProductId(IdUtil.purchaseId());
                      applyProduct.setPurchaseApplyId(purchaseApply.getPurchaseApplyId());
                      applyProduct.setPurchaseApplyCode(purchaseApply.getPurchaseApplyCode());
                      applyProduct.setProductType(Global.PRODUCT_TYPE_0);
                      Long price  = product.getNewPurchasePrice() == null ? 0 : product.getNewPurchasePrice();
                      applyProduct.setProductPurchaseAmount(price);
                      applyProduct.setNewPurchasePrice(price);
                      applyProduct.setCreateByName("系统");
                      applyProduct.setCreateById("0");
                      applyProduct.setInfoStatus(Global.PURCHASE_APPLY_STATUS_0);
                      applyProduct.setApplyProductStatus(Global.USER_ON);
                      // 获取最高采购价(价格管理中供应商的含税价格)
                      String key;
                      Map<String, Long> productTax = new HashMap<>();
                      if (StringUtils.isNotBlank(product.getSkuCode()) && StringUtils.isNotBlank(product.getSupplierCode())) {
                          key = String.format("%s,%s", product.getSkuCode(), product.getSupplierCode());
                          Long priceTax = productTax.get(key);
                          applyProduct.setPurchaseMax(priceTax == null ? 0 : priceTax);
                      }
                      // 报表取数据(预测采购件数， 预测到货时间， 近90天销量 )
                      Map<String, PurchaseApplyRespVo> purchase = new HashMap<>();
                      key = String.format("%s,%s,%s", product.getSkuCode(), product.getSupplierCode(), product.getTransportCenterCode());
                      PurchaseApplyRespVo vo = purchase.get(key);
                      if(vo != null){
                          applyProduct.setPurchaseNumber(vo.getAdviceOrders() == null ? 0: vo.getAdviceOrders().intValue());
                          applyProduct.setReceiptTime(DateUtils.getDate(vo.getPredictedArrival()));
                          applyProduct.setSalesVolume(vo.getAverageAmount() == null ? 0: vo.getAverageAmount().intValue() * 90);
                          applyProduct.setShortageNumber(vo.getOutStockAffectMoney() == null ? 0: vo.getOutStockAffectMoney().intValue());
                          applyProduct.setShortageDay(vo.getOutStockContinuousDays() == null ? 0: vo.getOutStockContinuousDays().intValue());
                          applyProduct.setStockTurnover(vo.getArrivalCycle() == null ? 0: vo.getArrivalCycle().intValue());
                      }
                      productList.add(applyProduct);
                  }
                  purchaseApplyProductDao.insertAll(productList);
              }
              encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
              applyList.add(purchaseApply);
          }
            purchaseApplyDao.insertAll(applyList);
        }
        return HttpResponse.success();
    }

    @Scheduled(cron = "0 5 0 * * ?")  //  每天00:05 执行一次执行
    public void execute(){
        this.executeWarehousing();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse executeWarehousing(){
        // 查询备货确认有前一天（有效期到期，没有入库完成）的数据
        // 获取前一天的时间
        String date = DateUtils.yestedayDate();
        String beginTime = date + " 00:00:00";
        String finishTime = date + " 23:59:59";
        List<PurchaseApplyDetailResponse> details = purchaseOrderDao.orderByExecuteWarehousing(beginTime, finishTime);
        if(CollectionUtils.isNotEmpty(details)){
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            PurchaseOrderDetails detail;
            for(PurchaseApplyDetailResponse order:details){
                purchaseOrder.setPurchaseOrderId(order.getPurchaseOrderId());
                if(!order.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
                    purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
                }else {
                    purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_7);
                    // 添加入库完成时间
                    detail = new PurchaseOrderDetails();
                    detail.setPurchaseOrderId(order.getPurchaseOrderId());
                    detail.setWarehouseTime(Calendar.getInstance().getTime());
                    detail.setUpdateByName("有效期到期，自动执行");
                    detail.setUpdateById("0");
                    purchaseOrderDetailsDao.update(detail);
                }
                purchaseOrder.setUpdateByName("有效期到期，自动执行");
                purchaseOrder.setUpdateById("0");
                purchaseOrderDao.update(purchaseOrder);
                if(purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)){
                    OperationLog log = new OperationLog();
                    log.setOperationId(order.getPurchaseOrderId());
                    log.setOperationType(PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode());
                    log.setOperationContent(PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName());
                    log.setRemark(order.getApplyTypeForm());
                    log.setCreateById("0");
                    log.setCreateByName("有效期到期，自动执行");
                    operationLogDao.insert(log);
                    if(!order.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
                        log.setOperationType(PurchaseOrderLogEnum.PURCHASE_FINISH.getCode());
                        log.setOperationContent(PurchaseOrderLogEnum.PURCHASE_FINISH.getName());
                        operationLogDao.insert(log);
                    }
                }
            }
        }
        return HttpResponse.success();
    }

    @Scheduled(cron = "0 0 8 1 * ?")   //每月1号的8:00执行
    public void intellectPurchase(){
        // 获取当前月时间
        String months = DateUtils.getMonths();
        this.intellect(months);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse intellect(String months){
        // 查询智能下单的sku
        List<BiAutomaticOrder> orderList = biAutomaticOrderDao.automaticOrderList(months);
        if(CollectionUtils.isNotEmpty(orderList)){
            // 计算审核天数
            DetailRespVo rule = supplierRuleDao.findByCompanyCode("01");
            Integer checkDay;
            if(rule != null){
                checkDay = rule.getPurchaseProcessReviewDay() + rule.getPurchaseProcessPaymentDay() + rule.getPurchaseProcessSupplierConfirmDay();
            }else {
                checkDay = 0;
            }
            List<BiSmartReplenishment> list = Lists.newArrayList();
            BiSmartReplenishment smartReplenishment;
            for(BiAutomaticOrder order:orderList){
                // 判断库存天数与订货周期
                Integer forecastCount = 0;
                // 库存天数
                Integer inventoryDays = order.getInventoryDays() == null ? 0 : order.getInventoryDays();
                // 订货周期
                Integer orderCycle = order.getOrderCycle() == null ? 0 : order.getOrderCycle();
                // 方案3 （最大上限）
                Integer caseThree = order.getCaseThree() == null ? 0 : order.getCaseThree();
                // 方案2
                Integer caseTwo = order.getCaseTwo() == null ? 0 : order.getCaseTwo();
                // 方案1
                Integer caseOne = order.getCaseOne() == null ? 0 : order.getCaseOne();
                if(inventoryDays > orderCycle){
                    if(caseThree < caseOne){
                        forecastCount = caseThree;
                    }else {
                        forecastCount = caseOne;
                    }
                }else {
                    // 计算产能天数
                    List<ProductSkuSupplyUnitCapacity> capacities = productSkuSupplyUnitCapacityDao.selectSupplyCapacityInfo(order.getSupplierCode(), order.getSkuCode());
                    if(CollectionUtils.isEmpty(capacities)){
                        LOGGER.info("未获取该sku的产能" + order.getSkuCode());
                        continue;
                     }
                     Integer capacityDay = 0;
                     for(ProductSkuSupplyUnitCapacity capacity:capacities){
                         Integer outPut = capacity.getOutPut() == null ? 0 : capacity.getOutPut().intValue();
                         if(outPut - caseOne >= 0){
                             capacityDay = capacity.getNeedDays().intValue();
                             break;
                         }
                     }
                    // 到货周期
                    Integer arrivalCycle = order.getArrivalCycle() == null ? 0 : order.getArrivalCycle();
                    Integer day1 = checkDay + arrivalCycle + capacityDay;
                    if(day1 > inventoryDays){
                        if(caseTwo <= caseThree){
                            forecastCount = caseTwo;
                        }else {
                            forecastCount = caseThree;
                        }
                    }else {
                        if(caseOne <= caseThree){
                            forecastCount = caseOne;
                        }else {
                            forecastCount = caseThree;
                        }
                    }
                }
                smartReplenishment = new BiSmartReplenishment();
                smartReplenishment.setSkuCode(order.getSkuCode());
                smartReplenishment.setSkuName(order.getSkuName());
                smartReplenishment.setTransportCenterCode(order.getTransportCenterCode());
                smartReplenishment.setTransportCenterName(order.getTransportCenterName());
                smartReplenishment.setWarehouseCode(order.getWarehouseCode());
                smartReplenishment.setWarehouseName(order.getWarehouseName());
                smartReplenishment.setSupplierCode(order.getSupplierCode());
                smartReplenishment.setSupplierName(order.getSupplierName());
                smartReplenishment.setSuggestedReplenishmentNumber(forecastCount);
                smartReplenishment.setCreateById("0");
                smartReplenishment.setCreateByName("系统");
                list.add(smartReplenishment);
            }
            biSmartReplenishmentDao.insertAll(list);
        }
        return HttpResponse.success();
    }
}
