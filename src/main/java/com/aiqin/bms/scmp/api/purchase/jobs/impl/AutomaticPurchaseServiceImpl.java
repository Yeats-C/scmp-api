package com.aiqin.bms.scmp.api.purchase.jobs.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.dao.BiSmartReplenishmentDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyProductDao;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public HttpResponse automaticPurchase(){
        // 获取前一天的时间
        String date = DateUtils.yestedayDate();
        String beginTime = date + " 00:00:00";
        String finishTime = date + " 23:59:59";
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
              // 生成申请采购单号
              EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_APPLY_CODE);
              String purchaseApplyCode = "CS" + String.valueOf(encodingRule.getNumberingValue());
              LOGGER.info(purchaseApplyCode + "------------采购单号-----------"+ group.getPurchaseGroupCode());
              purchaseApply.setPurchaseApplyCode(purchaseApplyCode);
              // 查询sku 的相关数据
              List<PurchaseApplyProduct> applyProducts = biSmartReplenishmentDao.skuInfo(beginTime, finishTime, group.getPurchaseGroupCode());
              PurchaseApplyProduct applyProduct;
              List<PurchaseApplyProduct> productList = Lists.newArrayList();
              if(CollectionUtils.isNotEmpty(applyProducts)){
                  for(PurchaseApplyProduct product:applyProducts){
                      applyProduct = new PurchaseApplyProduct();
                      BeanUtils.copyProperties(product, applyProduct);
                      applyProduct.setCreateByName("系统");
                      applyProduct.setCreateById("0");
                      applyProduct.setInfoStatus(Global.PURCHASE_APPLY_STATUS_0);
                      applyProduct.setApplyProductStatus(Global.USER_ON);
                      applyProduct.setProductType(Global.PRODUCT_TYPE_0);
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
}
