package com.aiqin.bms.scmp.api.purchase.jobs.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.dao.BiSmartReplenishmentDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyProductDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.jobs.AutomaticPurchaseService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.PurchaseGroup;
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

    @Scheduled(cron = "0 0 1 * * ?") // 每天00:05 执行一次执行
    public void automatic(){
        this.automaticPurchase();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse automaticPurchase(){
        DateTime dateTime = new DateTime(Calendar.getInstance().getTime());
        String data = dateTime.toString("yyyy-MM-dd");
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
                      Integer price  = product.getNewPurchasePrice() == null ? 0 : product.getNewPurchasePrice();
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
                          applyProduct.setPurchaseMax(priceTax == null ? 0 : priceTax.intValue());
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
            for(PurchaseApplyDetailResponse order:details){
                purchaseOrder.setPurchaseOrderId(order.getPurchaseOrderId());
                if(order.getStorageStatus().equals(Global.STORAGE_STATUS_2)){
                    purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
                }else {
                    purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_7);
                }
                purchaseOrderDao.update(purchaseOrder);
            }
        }
        return HttpResponse.success();
    }
}
