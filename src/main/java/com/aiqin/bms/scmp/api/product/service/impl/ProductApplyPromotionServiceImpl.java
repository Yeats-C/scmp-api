package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.bireport.dao.ProSuggestReplenishmentDao;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.dao.TaxCostLogDao;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuInfoImportNew;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionDetailReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionDetailRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionProductRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductApplyPromotionService;
import com.aiqin.bms.scmp.api.supplier.mapper.PurchaseGroupBuyerMapper;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 11:18
 * @Description:
 */
@Service
@WorkFlowAnnotation(WorkFlow.SCMP_APPLY_GOODS_PROMOTION)
public class ProductApplyPromotionServiceImpl extends BaseServiceImpl implements ProductApplyPromotionService, WorkFlowHelper {

  @Autowired
  private ProductApplyPromotionMapper productApplyPromotionMapper;
    @Autowired
   private ProductSkuDao productSkuDao;

  @Autowired
  private PricePromotionDetailMapper pricePromotionDetailMapper;

    @Autowired
    private PricePromotionProductMapper pricePromotionProductMapper;

    @Autowired
    private PricePromotionMapper pricePromotionMapper;

    @Autowired
    private PurchaseGroupBuyerMapper purchaseGroupBuyerMapper;

    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;

    @Autowired
    private TaxCostLogDao taxCostLogDao;

    @Autowired
    private ProSuggestReplenishmentDao proSuggestReplenishmentDao;

    @Autowired
    private StockDao stockDao;


    @Override
    public BasePage<PriceApplyPromotionRespVo> list(PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
       //获取当前用户
        AuthToken authToken = getUser();
        //获取当前用户的采购组列表
         List<String> purchaseGroupCodes= purchaseGroupBuyerMapper.loadCodesByPersonId(authToken.getPersonId());
        purchaseGroupBuyerMapper.loadNamesByPersonId(authToken.getPersonId());
     //   vo.setCompanyCode(authToken.getCompanyCode());
        PageHelper.startPage(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionReqVo.getPageSize());
        priceApplyPromotionReqVo.setPurchaseGroupCodes(purchaseGroupCodes);
        List<PriceApplyPromotionRespVo> priceApplyPromotionRespVoBasePage= productApplyPromotionMapper.list(priceApplyPromotionReqVo);
        return PageUtil.getPageList(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionRespVoBasePage);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean add(PriceApplyPromotionReqVo priceApplyPromotionReqVo) throws Exception {
       if(StringUtils.isEmpty(priceApplyPromotionReqVo.getApplyPromotionName())
               ||StringUtils.isEmpty(priceApplyPromotionReqVo.getProcurementSectionCode())
       ||StringUtils.isEmpty(priceApplyPromotionReqVo.getApplyPromotionName())){
           throw new BizException("有必填项没有传输");
       }
        //获取当前用户
        AuthToken authToken = getUser();
        //   vo.setCompanyCode(authToken.getCompanyCode());
        priceApplyPromotionReqVo.setCreateName(authToken.getPersonName());
        priceApplyPromotionReqVo.setCreateTimestamp(new Date());
        //获取编码
        synchronized (ProductApplyPromotionServiceImpl.class){
            String code = getCode("PAP", EncodingRuleType.APPLY_PROMOTION_NO);
            priceApplyPromotionReqVo.setApplyPromotionNo(code);
        }
        Long promotionId= IdSequenceUtils.getInstance().nextId();
        priceApplyPromotionReqVo.setId(promotionId);

        productApplyPromotionMapper.insert(priceApplyPromotionReqVo);
        //进行下属规则的添加，主键关联
        addDetailAndproduct(priceApplyPromotionReqVo);
        return true;
    }



    @Override
    public Boolean updateOrDelete(PriceApplyPromotionReqVo priceApplyPromotionReqVo,Integer type) {
        if (StringUtils.isEmpty(String.valueOf(type))){
            throw new BizException("参数为空");
        }
        //获取当前用户
        AuthToken authToken = getUser();
        //   vo.setCompanyCode(authToken.getCompanyCode());
        priceApplyPromotionReqVo.setUpdateName(authToken.getPersonName());
        priceApplyPromotionReqVo.setUpdateTimestamp(new Date());
        //进行调整
       if(type==1){
           //把规则下面的产品
           List<PricePromotionDetailRespVo> pricePromotionDetailRespVoList=pricePromotionDetailMapper.loadByPromotionId(priceApplyPromotionReqVo.getId());
           for (PricePromotionDetailRespVo pricePromotionDetailRespVo:
                   pricePromotionDetailRespVoList) {
               pricePromotionProductMapper.deteleByBusinessId(pricePromotionDetailRespVo.getId());
           }
           //把原来促销下面的规则删除了
           pricePromotionDetailMapper.deteleByPromotionId(priceApplyPromotionReqVo.getId());
           productApplyPromotionMapper.updateById(priceApplyPromotionReqVo);
           addDetailAndproduct(priceApplyPromotionReqVo);
       }else {
           productApplyPromotionMapper.deteleById(priceApplyPromotionReqVo.getId());
       }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PriceApplyPromotionRespVo load(Long id) {
        PriceApplyPromotionRespVo priceApplyPromotionRespVo=productApplyPromotionMapper.loadById(id);
        List<PricePromotionDetailRespVo> pricePromotionDetailRespVoList=pricePromotionDetailMapper.loadByPromotionId(id);
        for (PricePromotionDetailRespVo pricePromotionDetailRespVo:
        pricePromotionDetailRespVoList) {
            List<PricePromotionProductRespVo> pricePromotionProductRespVoList=pricePromotionProductMapper.loadByBusinessId(pricePromotionDetailRespVo.getId());
            for (PricePromotionProductRespVo pricePromotionProductRespVo:
            pricePromotionProductRespVoList) {
                //对有测算标识进行计算
                if(pricePromotionDetailRespVo.getIsSign()==0&&pricePromotionProductRespVo.getProductType()==1){
                    calculationProduct(pricePromotionProductRespVo,
                            priceApplyPromotionRespVo.getCategoriesSupplyChannelsCode(),
                            pricePromotionDetailRespVo.getPromotionType(),
                            pricePromotionDetailRespVo.getConditionNum(),
                            pricePromotionDetailRespVo.getGiveNum(),
                            pricePromotionDetailRespVo.getRuleType(),
                            id);
                }
            }
            pricePromotionDetailRespVo.setPricePromotionProductRespVoList(pricePromotionProductRespVoList);
        }
        priceApplyPromotionRespVo.setPricePromotionDetailRespVoList(pricePromotionDetailRespVoList);
        return priceApplyPromotionRespVo;
    }

    //计算商品
   public void calculationProduct(PricePromotionProductRespVo pricePromotionProductRespVo
           ,String categoriesSupplyChannelsCode
           ,Integer promotionType
           ,Float conditionNum
           ,Float giveNum
           ,Integer ruleType
           ,Long promotionId){
       List<BigDecimal> prices =   taxCostLogDao.loadPriceByProductCode(pricePromotionProductRespVo.getProductCode());
       if (CollectionUtils.isNotEmptyCollection(prices)){
           //设置库存价格
           pricePromotionProductRespVo.setStockPrice(prices.get(0));
       }else {
           //设置库存价格
           pricePromotionProductRespVo.setStockPrice(BigDecimal.ZERO);
       }
       //获取供货渠道
//       String categoriesSupplyChannelsCode= priceApplyPromotionRespVo.getCategoriesSupplyChannelsCode();
       List<BigDecimal> prices2= productSkuPriceInfoMapper.getDistributionPriceByProductCode(pricePromotionProductRespVo.getProductCode(),categoriesSupplyChannelsCode);
       if (CollectionUtils.isNotEmptyCollection(prices2)){
           //设置分销价格
           pricePromotionProductRespVo.setDistributionPrice(prices2.get(0));
       }else {
           //设置库存价格
           pricePromotionProductRespVo.setDistributionPrice(BigDecimal.ZERO);
       }
       //获取当前用户
       AuthToken authToken = getUser();
       //设置可用库存
       pricePromotionProductRespVo.setStockNum(stockDao.selectSkuAndCompanyByQueryAvailableSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode())==null?0:stockDao.selectSkuAndCompanyByQueryAvailableSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode()));
       //设置库存金额
       pricePromotionProductRespVo.setStockMoney(BigDecimal.valueOf(pricePromotionProductRespVo.getStockNum()==null?0:pricePromotionProductRespVo.getStockNum()).multiply(pricePromotionProductRespVo.getStockPrice()));
       //设置月平均销量
       Long num= proSuggestReplenishmentDao.biAppSuggestReplenishmentAllForPromotion(pricePromotionProductRespVo.getProductCode())==null?0:proSuggestReplenishmentDao.biAppSuggestReplenishmentAllForPromotion(pricePromotionProductRespVo.getProductCode());
       pricePromotionProductRespVo.setAverageNumLastThreeMouth(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(30)));

       //促销分销价
       BigDecimal promotionDistributionPrice=pricePromotionProductRespVo.getPromotionDistributionPrice();
       //库存成本
       BigDecimal stockPrice = pricePromotionProductRespVo.getStockPrice();
       //补贴后成本
       BigDecimal subsidyCost = pricePromotionProductRespVo.getSubsidyCost();
       if(promotionDistributionPrice!=null&&stockPrice!=null&&subsidyCost!=null) {
           //设置促销分销毛利，补贴分销毛利率，买赠
           if (promotionType.equals(1)) {

               if (promotionDistributionPrice.compareTo(BigDecimal.ZERO) >= 0) {

//             买赠：取标有测算标识的一行，假设某个sku1，买N个赠M个。
//             促销分销毛利率：（促销分销价*N-库存成本*（N+M））/促销分销价*N*100%
                   BigDecimal num1 = promotionDistributionPrice.multiply(BigDecimal.valueOf(conditionNum));
                   BigDecimal num2 = stockPrice.multiply(BigDecimal.valueOf(conditionNum + giveNum));
                   BigDecimal num3 = (num1.subtract(num2)).compareTo(BigDecimal.ZERO) > 0 ? num1.subtract(num2) : BigDecimal.ZERO;
                   BigDecimal num4 = num3.divide(promotionDistributionPrice.multiply(BigDecimal.valueOf(conditionNum)).multiply(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
                   pricePromotionProductRespVo.setDistributionGrossProfit(num4);

//                    补贴后分销毛利率：（促销分销价*N-补贴后成本*（N+M））/促销分销价*N*100%
                   BigDecimal num5 = promotionDistributionPrice.multiply(BigDecimal.valueOf(conditionNum));
                   BigDecimal num6 = subsidyCost.multiply(BigDecimal.valueOf(conditionNum + giveNum));
                   BigDecimal num7 = (num5.subtract(num6)).compareTo(BigDecimal.ZERO) > 0 ? num5.subtract(num6) : BigDecimal.ZERO;
                   BigDecimal num8 = num7.divide(promotionDistributionPrice.multiply(BigDecimal.valueOf(conditionNum)).multiply(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
                   pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(num8);
               } else {
                   pricePromotionProductRespVo.setDistributionGrossProfit(BigDecimal.ZERO);
                   pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(BigDecimal.ZERO);
               }
               //设置促销分销毛利，补贴分销毛利率，满赠
           } else if (promotionType.equals(2)) {
             if (promotionDistributionPrice.compareTo(BigDecimal.ZERO)>=0) {

//                 假设sku1买满X元赠sku2*N个，sku3*M个。X元/sku1的分销价，向上取整得到Y个
//                 假设sku1买满Y个赠sku2*N个，sku3*M个。sku2的库存成本*N个+sku3的库存成本*M个=Z元

                 if(ruleType.equals(0)){
                   BigDecimal  conditionNum1=BigDecimal.valueOf(conditionNum).divide(promotionDistributionPrice).setScale(0,BigDecimal.ROUND_UP);
                 }else {
                     BigDecimal conditionNum1=BigDecimal.valueOf(conditionNum);
                 }
                BigDecimal z=BigDecimal.ZERO;
                Float productConditionNum=pricePromotionProductMapper.getConditionNum(promotionId,pricePromotionProductRespVo.getBusinessId(),pricePromotionProductRespVo.getId());
             //在没有赠送多种=的情况下
               if (productConditionNum==null){
                   for (String codes:
                   pricePromotionProductMapper.getGiveCodes(pricePromotionProductRespVo.getBusinessId())) {
                       List<BigDecimal> givePrices =   taxCostLogDao.loadPriceByProductCode(codes);
                       PricePromotionDetailRespVo detailRespVo=   pricePromotionDetailMapper.loadById(pricePromotionProductRespVo.getBusinessId());
                       BigDecimal stockPrice1;
                       if (CollectionUtils.isNotEmptyCollection(givePrices)){
                           //设置库存价格
                           stockPrice1= givePrices.get(0);
                       }else {
                           //设置库存价格
                            stockPrice1= BigDecimal.ZERO;
                       }

                       z=z.add(BigDecimal.valueOf(detailRespVo.getGiveNum()).multiply(stockPrice1));
                   }

               }else {
                   List<Long> ids=pricePromotionDetailMapper.getBusinessIdForEnough(promotionId,productConditionNum);
                   for (Long businessId:
                   ids ) {
                       for (String codes:
                               pricePromotionProductMapper.getGiveCodes(businessId)) {
                           List<BigDecimal> givePrices =   taxCostLogDao.loadPriceByProductCode(codes);
                           PricePromotionDetailRespVo detailRespVo=   pricePromotionDetailMapper.loadById(pricePromotionProductRespVo.getBusinessId());
                           BigDecimal stockPrice1;
                           if (CollectionUtils.isNotEmptyCollection(givePrices)){
                               //设置库存价格
                               stockPrice1= givePrices.get(0);
                           }else {
                               //设置库存价格
                               stockPrice1= BigDecimal.ZERO;
                           }

                           z=z.add(BigDecimal.valueOf(detailRespVo.getGiveNum()).multiply(stockPrice1));
                       }
                   }

               }
//                 促销分销毛利率：((促销分销价*Y-Z)-库存成本*Y)/(促销分销价*Y-Z)*100%
                 BigDecimal num1 = promotionDistributionPrice.multiply(BigDecimal.valueOf(conditionNum)).subtract(z);
                 BigDecimal num2 = stockPrice.multiply(BigDecimal.valueOf(conditionNum));
                 BigDecimal num3 = (num1.subtract(num2)).compareTo(BigDecimal.ZERO) > 0 ? num1.subtract(num2) : BigDecimal.ZERO;
                 BigDecimal num4 = num3.divide(num1).multiply(BigDecimal.ONE).setScale(2, BigDecimal.ROUND_HALF_UP);
                 pricePromotionProductRespVo.setDistributionGrossProfit(num4);

//           补贴后分销毛利率：((促销分销价*Y-Z)-补贴后成本*Y)/(促销分销价*Y-Z)*100%
                 BigDecimal num5 = promotionDistributionPrice.multiply(BigDecimal.valueOf(conditionNum)).subtract(z);
                 BigDecimal num6 = subsidyCost.multiply(BigDecimal.valueOf(conditionNum));
                 BigDecimal num7 = (num5.subtract(num6)).compareTo(BigDecimal.ZERO) > 0 ? num5.subtract(num6) : BigDecimal.ZERO;
                 BigDecimal num8 = num7.divide(num5).multiply(BigDecimal.ONE).setScale(2, BigDecimal.ROUND_HALF_UP);
                 pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(num8);

             }else {
                 pricePromotionProductRespVo.setDistributionGrossProfit(BigDecimal.ZERO);
                 pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(BigDecimal.ZERO);
             }

           } else if (promotionType.equals(3)) {

               if (promotionDistributionPrice.compareTo(BigDecimal.ZERO) >= 0) {

                   BigDecimal conditionNum1;
                   if (ruleType.equals(0)) {
                       conditionNum1 = BigDecimal.valueOf(conditionNum).divide(promotionDistributionPrice).setScale(0, BigDecimal.ROUND_UP);
                   } else {
                       conditionNum1 = BigDecimal.valueOf(conditionNum);
                   }
                   //             满减：取标有测算标识的一行，
//             假设sku1买满X元减N元。X元/sku1的分销价，向上取整得到Y个
//             假设sku1买满Y个减N元。
//             促销分销毛利率：((促销分销价*Y-N)-库存成本*Y)/(促销分销价*Y-N)*100%
                   BigDecimal num1 = promotionDistributionPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum));
                   BigDecimal num2 = stockPrice.multiply(conditionNum1);
                   BigDecimal num3 = (stockPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum))).compareTo(BigDecimal.ZERO) > 0 ? stockPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum)) : BigDecimal.ONE;
                   BigDecimal num4 = (num1.subtract(num2)).divide(num3).setScale(2, BigDecimal.ROUND_HALF_UP);
                   pricePromotionProductRespVo.setDistributionGrossProfit(num4);

//                     补贴后分销毛利率：((促销分销价*Y-N)-补贴后成本*Y)/(促销分销价*Y-N)*100%
                   BigDecimal num5 = promotionDistributionPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum));
                   BigDecimal num6 = subsidyCost.multiply(conditionNum1);
                   BigDecimal num7 = (stockPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum))).compareTo(BigDecimal.ZERO) > 0 ? stockPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum)) : BigDecimal.ONE;
                   BigDecimal num8 = (num5.subtract(num6)).divide(num7).setScale(2, BigDecimal.ROUND_HALF_UP);
                   pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(num8);
               } else {
                   pricePromotionProductRespVo.setDistributionGrossProfit(BigDecimal.ZERO);
                   pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(BigDecimal.ZERO);
               }


           } else if (promotionType.equals(4)) {

               if (promotionDistributionPrice.compareTo(BigDecimal.ZERO) >= 0) {

                   BigDecimal conditionNum1;
                   if (ruleType.equals(0)) {
                       conditionNum1 = BigDecimal.valueOf(conditionNum).divide(promotionDistributionPrice).setScale(0, BigDecimal.ROUND_UP);
                   } else {
                       conditionNum1 = BigDecimal.valueOf(conditionNum);
                   }
//                 满折：取标有测算标识的一行，
//                 假设sku1买满X元折扣N。X元/sku1的分销价，向上取整得到Y个
//                 假设sku1买满Y个折扣N。
//                 促销分销毛利率：((促销分销价*Y*N)-库存成本*Y)/(促销分销价*Y*N)*100%
                   BigDecimal num1 = promotionDistributionPrice.multiply(conditionNum1).multiply(BigDecimal.valueOf(giveNum));
                   BigDecimal num2 = stockPrice.multiply(conditionNum1);
                   BigDecimal num3 = (promotionDistributionPrice.multiply(conditionNum1).multiply(BigDecimal.valueOf(giveNum))).compareTo(BigDecimal.ZERO) > 0 ? stockPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum)) : BigDecimal.ONE;
                   BigDecimal num4 = (num1.subtract(num2)).divide(num3).setScale(2, BigDecimal.ROUND_HALF_UP);
                   pricePromotionProductRespVo.setDistributionGrossProfit(num4);

//                     补贴后分销毛利率：((促销分销价*Y*N)-补贴后成本*Y)/(促销分销价*Y*N)*100%
                   BigDecimal num5 = promotionDistributionPrice.multiply(conditionNum1).multiply(BigDecimal.valueOf(giveNum));
                   BigDecimal num6 = subsidyCost.multiply(conditionNum1);
                   BigDecimal num7 = (promotionDistributionPrice.multiply(conditionNum1).multiply(BigDecimal.valueOf(giveNum))).compareTo(BigDecimal.ZERO) > 0 ? stockPrice.multiply(conditionNum1).subtract(BigDecimal.valueOf(giveNum)) : BigDecimal.ONE;
                   BigDecimal num8 = (num5.subtract(num6)).divide(num7).setScale(2, BigDecimal.ROUND_HALF_UP);
                   pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(num8);
               } else {
                   pricePromotionProductRespVo.setDistributionGrossProfit(BigDecimal.ZERO);
                   pricePromotionProductRespVo.setSubsidyDistributionGrossProfit(BigDecimal.ZERO);
               }

           }

       }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean generatePromotion(PricePromotionReqVo pricePromotionReqVo) {
        AuthToken authToken = getUser();
        //获取促销编码
        synchronized (ProductApplyPromotionServiceImpl.class){
            String code = getCode("PP", EncodingRuleType.PROMOTION_NO);
            pricePromotionReqVo.setPromotionNo(code);
        }
        pricePromotionReqVo.setId(IdSequenceUtils.getInstance().nextId());
        synchronized (ProductApplyPromotionServiceImpl.class){
            //申请单编码
            String  formNo = "PPA" + IdSequenceUtils.getInstance().nextId();
            pricePromotionReqVo.setApprovalNo(formNo);
        }
        pricePromotionReqVo.setStatus(ApplyStatus.PENDING.getNumber());
        pricePromotionReqVo.setCreateName(authToken.getPersonName());
        pricePromotionReqVo.setCreateTimestamp(new Date());
        pricePromotionMapper.insert(pricePromotionReqVo);
        for (PriceApplyPromotionReqVo priceApplyPromotionReqVo:
                pricePromotionReqVo.getPriceApplyPromotionReqVoList()  ) {
            priceApplyPromotionReqVo.setPromotionNo(pricePromotionReqVo.getPromotionNo());
            priceApplyPromotionReqVo.setOperateName(authToken.getPersonName());
            priceApplyPromotionReqVo.setOperateTimestamp(new Date());
            priceApplyPromotionReqVo.setPromotionName(pricePromotionReqVo.getPromotionName());
            priceApplyPromotionReqVo.setStatus(1);
            productApplyPromotionMapper.updateById(priceApplyPromotionReqVo);
        }
        workFlow(pricePromotionReqVo.getApprovalNo(),pricePromotionReqVo.getApplyPromotionNo(),authToken.getPersonName()
                ,pricePromotionReqVo.getDirectSupervisorCode(),pricePromotionReqVo.getPromotionName(),pricePromotionReqVo.getRemark());
       return true;
    }



    //把数据传输给审批流
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void workFlow(String formNo, String applyCode, String userName,String directSupervisorCode,String approvalName,String approvalRemark) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        //详情
//       workFlowVO.setFormUrl(workFlowBaseUrl.applySkuPromotion + "?approvalType=2&code=" + applyCode + "&" + workFlowBaseUrl.authority);
        ///主机地址
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        //流程编号
        workFlowVO.setFormNo(formNo);
        //进行编码修改
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.SCMP_APPLY_GOODS_PROMOTION.getNum());
        //进行审批名称的传输
        workFlowVO.setTitle(approvalName);
        //添加备注
        workFlowVO.setRemark(approvalRemark);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        //传输进行
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.SCMP_APPLY_GOODS_PROMOTION);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            //TODO 这里暂时没有任何操作
        } else {
            throw new BizException(MessageId.create(Project.SCMP_API,999,workFlowRespVO.getMsg()));
        }
    }

    @Override
    public List<PricePromotionProductRespVo> importSkuNew(MultipartFile file) {

        List<PricePromotionProductRespVo> pricePromotionProductRespVoList2= Lists.newArrayList();
        try {
            List<PricePromotionProductRespVo> pricePromotionProductRespVoList = ExcelUtil.readExcel(file, PricePromotionProductRespVo.class, 1, 1);

            pricePromotionProductRespVoList2=pricePromotionProductRespVoList.stream().distinct().collect(Collectors.toList());
            if (
              pricePromotionProductRespVoList.size()!=pricePromotionProductRespVoList2.size()
            ){
                throw  new BizException("导入有重复数据");
            }
            for (PricePromotionProductRespVo priceApplyPromotionRespVo:
                 pricePromotionProductRespVoList2) {
                if (StringUtils.isEmpty(priceApplyPromotionRespVo.getProductCode())||StringUtils.isEmpty(priceApplyPromotionRespVo.getProductName())){
                    throw  new BizException("有必填项没有输入");
                }
                ProductSkuInfo productSkuInfo= productSkuDao.getSkuInfo(priceApplyPromotionRespVo.getProductCode());
               if (productSkuInfo==null){
                   throw  new BizException("错误sku:"+priceApplyPromotionRespVo.getProductCode());
               }
                if (!productSkuInfo.getSkuName().equals(priceApplyPromotionRespVo.getProductName())){
                    throw  new BizException("错误sku:"+priceApplyPromotionRespVo.getProductCode()+"sku名称不对应");
                }
                //品类名称
                priceApplyPromotionRespVo.setCategory(productSkuInfo.getProductCategoryName());
                //商品属性
                priceApplyPromotionRespVo.setAttribute(productSkuInfo.getProductPropertyName());
                //
                List<BigDecimal> prices =   taxCostLogDao.loadPriceByProductCode(priceApplyPromotionRespVo.getProductCode());
                if (CollectionUtils.isNotEmptyCollection(prices)){
                    //设置库存价格
                    priceApplyPromotionRespVo.setStockPrice(prices.get(0));
                }else {
                    //设置库存价格
                    priceApplyPromotionRespVo.setStockPrice(BigDecimal.ZERO);
                }
                //获取供货渠道
////       String categoriesSupplyChannelsCode= priceApplyPromotionRespVo.getCategoriesSupplyChannelsCode();
//                List<BigDecimal> prices2= productSkuPriceInfoMapper.getDistributionPriceByProductCode(priceApplyPromotionRespVo.getProductCode(),categoriesSupplyChannelsCode);
//                if (CollectionUtils.isNotEmptyCollection(prices2)){
//                    //设置分销价格
//                    priceApplyPromotionRespVo.setDistributionPrice(prices2.get(0));
//                }else {
//                    //设置库存价格
//                    priceApplyPromotionRespVo.setDistributionPrice(BigDecimal.ZERO);
//                }
                //设置库存
                priceApplyPromotionRespVo.setStockNum(stockDao.selectSkuCodeByQueryAvailableSum(priceApplyPromotionRespVo.getProductCode())==null?0:stockDao.selectSkuCodeByQueryAvailableSum(priceApplyPromotionRespVo.getProductCode()));
                //设置库存金额
                priceApplyPromotionRespVo.setStockMoney(BigDecimal.valueOf(priceApplyPromotionRespVo.getStockNum()==null?0:priceApplyPromotionRespVo.getStockNum()).multiply(priceApplyPromotionRespVo.getStockPrice()));
                //设置月平均销量
                Long num= proSuggestReplenishmentDao.biAppSuggestReplenishmentAllForPromotion(priceApplyPromotionRespVo.getProductCode())==null?0:proSuggestReplenishmentDao.biAppSuggestReplenishmentAllForPromotion(priceApplyPromotionRespVo.getProductCode());
                priceApplyPromotionRespVo.setAverageNumLastThreeMouth(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(30)));

            }


        } catch (ExcelException e) {
            e.printStackTrace();
        }
        return pricePromotionProductRespVoList2;
    }

    @Override
    public BasePage<PricePromotionProductRespVo> skuList(PricePromotionProductReqVo priceApplyPromotionReqVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            priceApplyPromotionReqVo.setPersonId(authToken.getPersonId());
        }
        PageHelper.startPage(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionReqVo.getPageSize());
        List<PricePromotionProductRespVo>  pricePromotionProductRespVoBasePage =  pricePromotionProductMapper.skuList(priceApplyPromotionReqVo);
        for (PricePromotionProductRespVo pricePromotionProductRespVo:
        pricePromotionProductRespVoBasePage ) {

            List<BigDecimal> prices =   taxCostLogDao.loadPriceByProductCode(pricePromotionProductRespVo.getProductCode());
            if (CollectionUtils.isNotEmptyCollection(prices)){
                //设置库存价格
                pricePromotionProductRespVo.setStockPrice(prices.get(0));
            }else {
                //设置库存价格
                pricePromotionProductRespVo.setStockPrice(BigDecimal.ZERO);
            }

            //设置可用库存
            pricePromotionProductRespVo.setStockNum(stockDao.selectSkuAndCompanyByQueryAvailableSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode())==null?0:stockDao.selectSkuAndCompanyByQueryAvailableSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode()));
            //设置库存金额
            pricePromotionProductRespVo.setStockMoney(BigDecimal.valueOf(pricePromotionProductRespVo.getStockNum()==null?0:pricePromotionProductRespVo.getStockNum()).multiply(pricePromotionProductRespVo.getStockPrice()));
            //设置月平均销量
            Long num= proSuggestReplenishmentDao.biAppSuggestReplenishmentAllForPromotion(pricePromotionProductRespVo.getProductCode())==null?0:proSuggestReplenishmentDao.biAppSuggestReplenishmentAllForPromotion(pricePromotionProductRespVo.getProductCode());
            pricePromotionProductRespVo.setAverageNumLastThreeMouth(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(30)));
            //设置销售库存
            pricePromotionProductRespVo.setSaleStockNum(stockDao.selectSkuCodeBySaleSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode())==null?0:stockDao.selectSkuCodeBySaleSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode()));
            //设置特卖库存
            pricePromotionProductRespVo.setSpecialStockNum(stockDao.selectSkuCodeBySpecialSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode())==null?0:stockDao.selectSkuCodeBySpecialSum(pricePromotionProductRespVo.getProductCode(),authToken.getCompanyCode()));

        }
        return PageUtil.getPageList(priceApplyPromotionReqVo.getPageNo(),pricePromotionProductRespVoBasePage);

    }

//    @Override
//    public LoadGeneratePromotionRespVo loadGeneratePromotion(List<PriceApplyPromotionReqVo> priceApplyPromotionReqVoList) {
//        LoadGeneratePromotionRespVo loadGeneratePromotionRespVo=new LoadGeneratePromotionRespVo();
//        List<PriceApplyPromotionRespVo> priceApplyPromotionRespVos=Lists.newArrayList();
//        for (PriceApplyPromotionReqVo priceApplyPromotionReqVo:
//        priceApplyPromotionReqVoList) {
//            PriceApplyPromotionRespVo priceApplyPromotionRespVo=new PriceApplyPromotionRespVo();
//            BeanUtils.copyProperties(priceApplyPromotionReqVo,priceApplyPromotionRespVo);
//            priceApplyPromotionRespVos.add(priceApplyPromotionRespVo);
//        }
//        loadGeneratePromotionRespVo.setPriceApplyPromotionRespVos(priceApplyPromotionRespVos);
//        PriceApplyPromotionRespVo priceApplyPromotionRespVo = load(priceApplyPromotionReqVoList.get(0).getId());
//        //买赠
//        List<PricePromotionDetailRespVo> buyPromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==1).collect(Collectors.toList());
//        loadGeneratePromotionRespVo.setBuyPromotionDetailList(buyPromotionDetailList);
//         //满赠
//         List<PricePromotionDetailRespVo> enoughPromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==2).collect(Collectors.toList());;
//        loadGeneratePromotionRespVo.setEnoughPromotionDetailList(enoughPromotionDetailList);
//         //满减
//         List<PricePromotionDetailRespVo> reducePromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==3).collect(Collectors.toList());;
//        loadGeneratePromotionRespVo.setReducePromotionDetailList(reducePromotionDetailList);
//        //满折
//         List<PricePromotionDetailRespVo> discountPromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==4).collect(Collectors.toList());;
//        loadGeneratePromotionRespVo.setDiscountPromotionDetailList(discountPromotionDetailList);
//
//        return loadGeneratePromotionRespVo;
//    }


    @Override
    public List<String> loadNamesByPersonId() {
        AuthToken authToken = getUser();
        return purchaseGroupBuyerMapper.loadNamesByPersonId(authToken.getPersonId());
    }

    private void addDetailAndproduct(PriceApplyPromotionReqVo priceApplyPromotionReqVo){
        //进行下属规则的添加，主键关联
        List<PricePromotionDetailReqVo> pricePromotionDetailReqVoList= priceApplyPromotionReqVo.getPricePromotionDetailReqVoList();
        pricePromotionDetailReqVoList.stream().forEach(x->x.setPromotionId(priceApplyPromotionReqVo.getId()));
        pricePromotionDetailReqVoList.stream().forEach(x->x.setId(IdSequenceUtils.getInstance().nextId()));
        for (PricePromotionDetailReqVo pricePromotionDetailReqVo:
        pricePromotionDetailReqVoList ) {
            pricePromotionDetailMapper.insert(pricePromotionDetailReqVo);
            if(pricePromotionDetailReqVo.getPromotionType().equals(1)
                    ||pricePromotionDetailReqVo.getPromotionType().equals(3)
                    ||pricePromotionDetailReqVo.getPromotionType().equals(4)) {
                //进行下属的产品的属性书写
                List<PricePromotionProductReqVo> pricePromotionProductReqVoList = pricePromotionDetailReqVo.getPricePromotionProductReqVoList();
                pricePromotionProductReqVoList.stream().forEach(x -> x.setBusinessId(pricePromotionDetailReqVo.getId()));
                pricePromotionProductReqVoList.stream().forEach(x -> x.setId(IdSequenceUtils.getInstance().nextId()));
                pricePromotionProductMapper.insertSelective(pricePromotionProductReqVoList);
                List<PricePromotionProductReqVo> pricePromotionProductReqVoList2 = pricePromotionProductReqVoList;
                pricePromotionProductReqVoList2.stream().forEach(x->x.setId(IdSequenceUtils.getInstance().nextId()));
                pricePromotionProductReqVoList2.stream().forEach(x->x.setProductType(2));
                pricePromotionProductMapper.insertSelective(pricePromotionProductReqVoList2);
            }else {
                //进行下属的产品的属性书写
                List<PricePromotionProductReqVo> pricePromotionProductReqVoList = pricePromotionDetailReqVo.getPricePromotionProductReqVoList();
                pricePromotionProductReqVoList.stream().forEach(x -> x.setBusinessId(pricePromotionDetailReqVo.getId()));
                pricePromotionProductReqVoList.stream().forEach(x -> x.setId(IdSequenceUtils.getInstance().nextId()));
                pricePromotionProductMapper.insertSelective(pricePromotionProductReqVoList);
            }

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo)  {
        WorkFlowCallbackVO newVO = updateSupStatus(vo);
        newVO.setAuditorTime(new Date());
        //审批中，直接返回成功
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }
        //首先通过formNO查找数据 配置数据
        List<PricePromotionRespVo> list = pricePromotionMapper.selectByFormNo(newVO.getFormNo());
        if (org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
            throw new BizException(ResultCode.DATA_ERROR);
        }

        if (!list.get(0).getStatus().equals(ApplyStatus.APPROVAL.getNumber())) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常，不是在审批中的数据！"));
        }
        String applyCode = list.get(0).getApprovalNo();
        //审批驳回
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
            updateApplyInfoByVO(newVO.getFormNo(), newVO.getApplyStatus());
        }
        //撤销
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
            updateApplyInfoByVO(newVO.getFormNo(), newVO.getApplyStatus());
        }
        //审批通过
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            updateApplyInfoByVO(newVO.getFormNo(), newVO.getApplyStatus());
        }
        return WorkFlowReturn.SUCCESS;
    }

    private void updateApplyInfoByVO(String formNo, Byte applyStatus) {
        pricePromotionMapper.updateApplyInfoByVO(formNo,applyStatus);

    }
}
