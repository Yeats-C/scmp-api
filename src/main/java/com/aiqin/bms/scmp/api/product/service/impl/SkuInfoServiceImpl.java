package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaForSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.*;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceProjectRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInfoMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyUseTagRecord;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordItemReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.service.ApplyUseTagRecordService;
import com.aiqin.bms.scmp.api.supplier.service.TagInfoService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.aiqin.bms.scmp.api.util.GetChangeValueUtil.skuHeadMap;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 15:44
 */
@Service
public class SkuInfoServiceImpl extends BaseServiceImpl implements SkuInfoService {
    @Autowired
    private ProductSkuDraftMapper productSkuDraftMapper;
    @Autowired
    private ProductSkuPriceService productSkuPriceService;
    @Autowired
    private ProductSkuConfigService productSkuConfigService;
    @Autowired
    private ProductSkuFileService productSkuFileService;
    @Autowired
    private ProductSkuSupplyUnitService productSkuSupplyUnitService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private ProductSkuCheckoutService productSkuCheckoutService;
    @Autowired
    private ProductSkuPurchaseInfoService productSkuPurchaseInfoService;
    @Autowired
    private ProductSkuDisInfoService productSkuDisInfoService;
    @Autowired
    private ProductSkuBoxPackingService productSkuBoxPackingService;
    @Autowired
    private ProductSkuPicturesService productSkuPicturesService;
    @Autowired
    private ProductSkuPicDescService productSkuPicDescService;
    @Autowired
    private ProductSkuSalesInfoService productSkuSalesInfoService;
    @Autowired
    private ProductSkuManufacturerService productSkuManufacturerService;
    @Autowired
    private ProductSkuInspReportService productSkuInspReportService;
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private ProductSkuCheckoutDao productSkuCheckoutDao;
    @Autowired
    private ProductSkuPicturesDao productSkuPicturesDao;
    @Autowired
    private ProductSkuPriceDao productSkuPriceDao;
    @Autowired
    private ProductSkuPicDescDao productSkuPicDescDao;
    @Autowired
    private ProductSkuPurchaseInfoDao productSkuPurchaseInfoDao;
    @Autowired
    private ProductSkuDisInfoDao productSkuDisInfoDao;
    @Autowired
    private ProductSkuBoxPackingDao productSkuBoxPackingDao;
    @Autowired
    private ProductSkuSalesInfoDao productSkuSalesInfoDao;
    @Autowired
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Autowired
    private ProductSkuManufacturerDao productSkuManufacturerDao;
    @Autowired
    private ProductSkuFileDao productSkuFileDao;
    @Autowired
    private ApplyProductSkuMapper applyProductSkuMapper;
    @Autowired
    private ProductSkuInspReportDao productSkuInspReportDao;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ApplyProductService applyProductService;
    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Autowired
    private ProductSkuInfoMapper productSkuInfoMapper;
    @Autowired
    private ApplyProductSkuServiceProduct applyProductSkuService;
    @Autowired
    private ProductSkuChannelService productSkuChannelService;
    @Autowired
    private ProductSkuSupplyUnitCapacityService productSkuSupplyUnitCapacityService;
    @Autowired
    private ProductSkuAssociatedGoodsService productSkuAssociatedGoodsService;
    @Autowired
    private ProductSkuStockInfoService productSkuStockInfoService;
    @Autowired
    private ProductSkuPriceInfoService productSkuPriceInfoService;
    @Autowired
    private PriceProjectService priceProjectService;
    @Autowired
    private ApplyUseTagRecordService applyUseTagRecordService;
    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private ProductSkuSubService productSkuSubService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO) {
        if (null != addSkuInfoReqVO && null != addSkuInfoReqVO.getProductSkuDraft()){
            //SKU基本信息
            ProductSkuDraft productSkuDraft = addSkuInfoReqVO.getProductSkuDraft();
            //计算状态
            if (CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuConfigs())) {
                List<SkuConfigsRepsVo> skuConfigsRepsVos = BeanCopyUtils.copyList(addSkuInfoReqVO.getProductSkuConfigs(),SkuConfigsRepsVo.class);
                SkuStatusRespVo skuStatusRespVo = productSkuConfigService.calculationSkuStatus(skuConfigsRepsVos);
                productSkuDraft.setSkuStatus(skuStatusRespVo.getSkuStatus());
                productSkuDraft.setOnSale(Optional.ofNullable(skuStatusRespVo.getOnSale()).orElse(SkuSaleStatusEnum.NOT_IN_STOCK.getStatus()));
            }
            productSkuDraft.setInventoryAllocation(Optional.ofNullable(productSkuDraft.getInventoryAllocation()).orElse(Global.BYTE_ZERO));
            productSkuDraft.setPriceModel(Optional.ofNullable(productSkuDraft.getPriceModel()).orElse(Global.BYTE_ZERO));
            //拆分品类信息
            String productCategoryCode = productSkuDraft.getProductCategoryCode();
            if(StringUtils.isNotBlank(productCategoryCode)){
                String[] split = productCategoryCode.split(",");
                productCategoryCode = split[split.length-1];
                productSkuDraft.setProductCategoryCode(productCategoryCode);
            }
            if (StringUtils.isNotBlank(productSkuDraft.getSkuCode())){
                productSkuDraft.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                productSkuDraft.setApplyTypeName(StatusTypeCode.UPDATE_APPLY.getName());
                //判断临时表中是否存在
                ProductSkuRespVo skuRespVo = productSkuDao.getSkuDraft(productSkuDraft.getSkuCode());
                if(null != skuRespVo){
                    throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU信息在申请表中已存在"));
                }
                //判断申请表中是否存在申请中的数据
                ApplyProductSku applyProductSku = applyProductSkuMapper.selectNoExistsApprovalBySkuCode(productSkuDraft.getSkuCode());
                if(null != applyProductSku){
                    throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU信息已经在审批中"));
                }
                //计算修改的内容
                //获取旧的数据
                ProductSkuDraft oldSku = productSkuDraftMapper.getOfficialBySkuCode(productSkuDraft.getSkuCode());
                String compareResult = new GetChangeValueUtil<ProductSkuDraft>().compareResult(oldSku, productSkuDraft, skuHeadMap);
                productSkuDraft.setChangeContent(compareResult);
                ((SkuInfoService) AopContext.currentProxy()).insertDraft(productSkuDraft);
                productCommonService.getInstance(productSkuDraft.getSkuCode(), HandleTypeCoce.UPDATE.getStatus(), ObjectTypeCode.SKU_MANAGEMENT.getStatus(),HandleTypeCoce.UPDATE_SKU.getName(),HandleTypeCoce.UPDATE.getName());
            } else {
                EncodingRule encodingRule=encodingRuleDao.getNumberingType("PRODUCT_SKU_CODE");
                Long thisCode = encodingRule.getNumberingValue();
                productSkuDraft.setSkuCode(String.valueOf(thisCode+1));
                productSkuDraft.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                productSkuDraft.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
                productSkuDraft.setChangeContent("新增SKU");
                ((SkuInfoService) AopContext.currentProxy()).insertDraft(productSkuDraft);
                encodingRuleDao.updateNumberValue(thisCode,encodingRule.getId());
                productCommonService.getInstance(productSkuDraft.getSkuCode(), HandleTypeCoce.ADD.getStatus(), ObjectTypeCode.SKU_MANAGEMENT.getStatus(),HandleTypeCoce.ADD_SKU.getName(),HandleTypeCoce.ADD.getName());
            }
            SkuTypeEnum skuTypeEnum = SkuTypeEnum.getSkuTypeEnumByType(productSkuDraft.getGoodsGifts());
            //组合商品-子商品列表
            List<ProductSkuSubDraft> productSkuSubs = addSkuInfoReqVO.getProductSkuSubs();
            if(Objects.equals(SkuTypeEnum.COMBINATION,skuTypeEnum) && CollectionUtils.isNotEmpty(productSkuSubs)) {
                //判断是否包含主商品
                List<ProductSkuSubDraft> mainProduct = productSkuSubs.stream().filter(item -> Objects.equals(item.getMainProduct(), Global.MAIN_PRODUCT)).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(mainProduct)){
                    throw new BizException(ResultCode.MAIN_PRODUCT_EMPTY);
                }
                productSkuSubs.forEach(item -> {
                    item.setMainSkuCode(productSkuDraft.getSkuCode());
                    item.setMainSkuName(productSkuDraft.getSkuName());
                });
                productSkuSubService.insertDraftList(productSkuSubs);
            }
            //SKU标签信息
            List<SaveUseTagRecordItemReqVo> tagInfoList = addSkuInfoReqVO.getTagInfoList();
            if(CollectionUtils.isNotEmpty(tagInfoList)){
                List<ApplyUseTagRecord> applyUseTagRecords = Lists.newArrayList();
                tagInfoList.forEach(item->{
                    ApplyUseTagRecord applyUseTagRecord = new ApplyUseTagRecord();
                    applyUseTagRecord.setApplyUseObjectCode(productSkuDraft.getSkuCode());
                    applyUseTagRecord.setTagCode(item.getTagCode());
                    applyUseTagRecord.setTagName(item.getTagName());
                    applyUseTagRecord.setUseObjectCode(productSkuDraft.getSkuCode());
                    applyUseTagRecord.setUseObjectName(productSkuDraft.getSkuName());
                    applyUseTagRecord.setTagTypeCode(TagTypeCode.SKU.getStatus());
                    applyUseTagRecord.setTagTypeName(TagTypeCode.SKU.getName());
                    applyUseTagRecords.add(applyUseTagRecord);
                });
                applyUseTagRecordService.saveBatch(applyUseTagRecords);
            }
            //SKU渠道信息
            List<ProductSkuChannelDraft> productSkuChannelDrafts = addSkuInfoReqVO.getProductSkuChannelDrafts();
            if (CollectionUtils.isNotEmpty(productSkuChannelDrafts)) {
                productSkuChannelDrafts.forEach(item->{
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                });
                productSkuChannelService.insertDraftList(productSkuChannelDrafts);
            }
            //SKU进销存信息
            List<PurchaseSaleStockReqVo> purchaseSaleStockReqVos = addSkuInfoReqVO.getPurchaseSaleStockReqVos();
            if (CollectionUtils.isEmpty(purchaseSaleStockReqVos)) {
                throw new BizException(ResultCode.PURCHASE_SALE_STOCK_EMPTY);
            }
            //初始化销项税率
            BigDecimal outputTaxRate = BigDecimal.ONE;
            Long outputTaxRateL = 100L;
            List<SkuPriceDraftReqVO> productSkuPrices = Lists.newArrayList();
            //非组合商品才有库存/采购/门店销售
            if(!Objects.equals(SkuTypeEnum.COMBINATION,skuTypeEnum)){
                //获取库存信息
                List<PurchaseSaleStockReqVo>  stockList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.STOCK.getStatus(),item.getType())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(stockList)){
                    throw new BizException(ResultCode.STOCK_EMPTY);
                }
                if (stockList.size() != 1) {
                    throw new BizException(ResultCode.STOCK_ONE);
                }
                try {
                    ProductSkuStockInfoDraft productSkuStockInfoDraft =  BeanCopyUtils.copy(stockList.get(0),ProductSkuStockInfoDraft.class);
                    productSkuStockInfoDraft.setProductSkuCode(productSkuDraft.getSkuCode());
                    productSkuStockInfoDraft.setProductSkuName(productSkuDraft.getSkuName());
                    productSkuStockInfoDraft.setProductCode(productSkuDraft.getProductCode());
                    productSkuStockInfoDraft.setProductName(productSkuDraft.getProductName());
                    productSkuStockInfoDraft.setBaseProductContent(1);
                    productSkuStockInfoDraft.setZeroRemovalCoefficient(1L);
                    productSkuStockInfoService.insertDraft(productSkuStockInfoDraft);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
                }
                //获取采购信息
                List<PurchaseSaleStockReqVo>  purchaseList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.PURCHASE.getStatus(),item.getType())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(purchaseList)){
                    throw new BizException(ResultCode.PURCHASE_EMPTY);
                }
                if (purchaseList.size() != 1) {
                    throw new BizException(ResultCode.PURCHASE_ONE);
                }
                try {
                    ProductSkuPurchaseInfoDraft productSkuPurchaseInfoDraft =  BeanCopyUtils.copy(purchaseList.get(0),ProductSkuPurchaseInfoDraft.class);
                    productSkuPurchaseInfoDraft.setProductSkuCode(productSkuDraft.getSkuCode());
                    productSkuPurchaseInfoDraft.setProductSkuName(productSkuDraft.getSkuName());
                    productSkuPurchaseInfoDraft.setProductCode(productSkuDraft.getProductCode());
                    productSkuPurchaseInfoDraft.setProductName(productSkuDraft.getProductName());
                    productSkuPurchaseInfoService.insertDraft(productSkuPurchaseInfoDraft);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
                }
                //获取门店销售信息
                List<PurchaseSaleStockReqVo> storeSaleList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.STORE_SALE.getStatus(),item.getType())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(storeSaleList)){
                    throw new BizException(ResultCode.STORE_SALE_EMPTY);
                }
                try {
                    List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts = BeanCopyUtils.copyList(storeSaleList,ProductSkuSalesInfoDraft.class);
                    productSkuSalesInfoDrafts.forEach(item->{
                        item.setProductSkuCode(productSkuDraft.getSkuCode());
                        item.setProductSkuName(productSkuDraft.getSkuName());
                        item.setProductName(productSkuDraft.getProductName());
                        item.setProductCode(productSkuDraft.getProductCode());
                        item.setBaseProductContent(1);
                        item.setZeroRemovalCoefficient(1L);
                        item.setUsageStatus(StatusTypeCode.USE.getStatus());
                    });
                    productSkuSalesInfoService.insertDraftList(productSkuSalesInfoDrafts);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
                }
                //获取包装信息
                if (CollectionUtils.isEmpty(addSkuInfoReqVO.getProductSkuBoxPackingDrafts())){
                    throw new BizException(ResultCode.BOX_PACKING_EMPTY);
                }
                List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts = addSkuInfoReqVO.getProductSkuBoxPackingDrafts();
                productSkuBoxPackingDrafts.forEach(item->{
                    item.setProductSkuCode(productSkuDraft.getSkuCode());
                    item.setProductSkuName(productSkuDraft.getSkuName());
                });
                productSkuBoxPackingService.insertDraftList(productSkuBoxPackingDrafts);
                //结算信息
                BigDecimal inputTaxRate = BigDecimal.ONE;
                Long inputTaxRateL = 100L;
                if (null != addSkuInfoReqVO.getProductSkuCheckoutDraft()) {
                    ProductSkuCheckoutDraft productSkuCheckoutDraft = addSkuInfoReqVO.getProductSkuCheckoutDraft();
                    productSkuCheckoutDraft.setSkuCode(productSkuDraft.getSkuCode());
                    productSkuCheckoutDraft.setSkuName(productSkuDraft.getSkuName());
                    productSkuCheckoutService.insertDraft(productSkuCheckoutDraft);
                    inputTaxRateL = productSkuCheckoutDraft.getInputTaxRate();
                    outputTaxRateL = productSkuCheckoutDraft.getOutputTaxRate();
                    inputTaxRate = new BigDecimal(productSkuCheckoutDraft.getInputTaxRate()).divide(new BigDecimal(10000), 4, BigDecimal.ROUND_DOWN);
                    outputTaxRate = new BigDecimal(productSkuCheckoutDraft.getOutputTaxRate()).divide(new BigDecimal(10000), 4, BigDecimal.ROUND_DOWN);
                }
                //供应商信息
                if (CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuSupplyUnitDrafts())){
                    List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts = addSkuInfoReqVO.getProductSkuSupplyUnitDrafts();
                    //获取采购价格项目
                    QueryPriceProjectRespVo purchasePriceProject = priceProjectService.getPurchasePriceProject();
                    if(null == purchasePriceProject){
                        throw new BizException(ResultCode.SKU_PURCHASE_PRICE_IS_EMPTY);
                    }
                    final BigDecimal finalInputTaxRate = inputTaxRate;
                    final Long finalInputTaxRateL = inputTaxRateL;

                    List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts = Lists.newArrayList();
                    productSkuSupplyUnitDrafts.forEach(item->{
                        item.setProductSkuCode(productSkuDraft.getSkuCode());
                        item.setProductSkuName(productSkuDraft.getSkuName());
                        //先把含税金额除以100兑换成元,含税金额/(1+税率) = 未税金额,最终结果*100转换成分,舍弃分以后的数字
                        Long taxNoPrice = new BigDecimal(item.getTaxIncludedPrice()).divide(new BigDecimal(100)).divide(BigDecimal.ONE.add(finalInputTaxRate),2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).longValue();
                        item.setNoTaxPurchasePrice(taxNoPrice);
                        item.setTaxRate(finalInputTaxRateL);
                        item.setUsageStatus(StatusTypeCode.USE.getStatus());
                        if(CollectionUtils.isNotEmpty(item.getProductSkuSupplyUnitCapacityDrafts())){
                            item.getProductSkuSupplyUnitCapacityDrafts().forEach(item2->{
                                item2.setProductSkuCode(productSkuDraft.getSkuCode());
                                item2.setProductSkuName(productSkuDraft.getSkuName());
                                item2.setSupplyUnitCode(item.getSupplyUnitCode());
                                item2.setSupplyUnitName(item.getSupplyUnitName());
                            });
                            productSkuSupplyUnitCapacityDrafts.addAll(item.getProductSkuSupplyUnitCapacityDrafts());
                        }
                        SkuPriceDraftReqVO skuPriceDraftReqVO = new SkuPriceDraftReqVO();
                        //SKU编码
                        skuPriceDraftReqVO.setSkuCode(productSkuDraft.getSkuCode());
                        skuPriceDraftReqVO.setSkuName(productSkuDraft.getSkuName());
                        //公司
                        skuPriceDraftReqVO.setCompanyCode(getUser().getCompanyCode());
                        skuPriceDraftReqVO.setCompanyName(getUser().getCompanyName());
                        //采购组
                        skuPriceDraftReqVO.setPurchaseGroupCode(productSkuDraft.getProcurementSectionCode());
                        skuPriceDraftReqVO.setPurchaseGroupName(productSkuDraft.getProcurementSectionName());
                        //价格项目信息
                        skuPriceDraftReqVO.setPriceItemCode(purchasePriceProject.getPriceProjectCode());
                        skuPriceDraftReqVO.setPriceItemName(purchasePriceProject.getPriceProjectName());
                        skuPriceDraftReqVO.setPriceTypeCode(purchasePriceProject.getPriceTypeCode());
                        skuPriceDraftReqVO.setPriceTypeName(purchasePriceProject.getPriceTypeName());
                        skuPriceDraftReqVO.setPriceAttributeCode(purchasePriceProject.getPriceCategoryCode());
                        skuPriceDraftReqVO.setPriceAttributeName(purchasePriceProject.getPriceCategoryName());
                        //税率
                        skuPriceDraftReqVO.setTax(finalInputTaxRateL);
                        //未税价
                        skuPriceDraftReqVO.setPriceNoTax(taxNoPrice);
                        //含税价
                        skuPriceDraftReqVO.setPriceTax(item.getTaxIncludedPrice());
                        //生效时间
                        skuPriceDraftReqVO.setEffectiveTimeStart(new Date());
                        //供应商
                        skuPriceDraftReqVO.setSupplierCode(item.getSupplyUnitCode());
                        skuPriceDraftReqVO.setSupplierName(item.getSupplyUnitName());
                        //是否默认
                        skuPriceDraftReqVO.setBeDefault(item.getIsDefault().intValue());
                        //创建/修改时间/人
                        skuPriceDraftReqVO.setCreateBy(getUser().getPersonName());
                        skuPriceDraftReqVO.setCreateTime(new Date());
                        skuPriceDraftReqVO.setUpdateBy(getUser().getPersonName());
                        skuPriceDraftReqVO.setUpdateTime(new Date());
                        productSkuPrices.add(skuPriceDraftReqVO);
                    });
                    productSkuSupplyUnitService.insertDraftList(productSkuSupplyUnitDrafts);
                    //供应商产能
                    if (CollectionUtils.isNotEmpty(productSkuSupplyUnitCapacityDrafts)){
                        productSkuSupplyUnitCapacityService.insertDraftList(productSkuSupplyUnitCapacityDrafts);
                    }
                }
                //关联商品
                if (CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuAssociatedGoodsDrafts())) {
                    List<ProductSkuAssociatedGoodsDraft> productSkuAssociatedGoodsDrafts = addSkuInfoReqVO.getProductSkuAssociatedGoodsDrafts();
                    productSkuAssociatedGoodsDrafts.forEach(item->{
                        item.setMainSkuCode(productSkuDraft.getSkuCode());
                        item.setMainSkuName(productSkuDraft.getSkuName());
                    });
                    productSkuAssociatedGoodsService.insertDraftList(productSkuAssociatedGoodsDrafts);
                }
                //商产厂家
                if (null != addSkuInfoReqVO.getProductSkuManufacturerDrafts() && addSkuInfoReqVO.getProductSkuManufacturerDrafts().size() > 0){
                    List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts = addSkuInfoReqVO.getProductSkuManufacturerDrafts();
                    productSkuManufacturerDrafts.forEach(item->{
                        item.setProductSkuCode(productSkuDraft.getSkuCode());
                        item.setProductSkuName(productSkuDraft.getSkuName());
                        item.setUsageStatus(StatusTypeCode.USE.getStatus());
                    });
                    productSkuManufacturerService.insertDraftList(productSkuManufacturerDrafts);
                }
                //sku质检信息
                if(CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuInspReportDrafts())) {
                    List<ProductSkuInspReportDraft> productSkuInspReportDrafts = addSkuInfoReqVO.getProductSkuInspReportDrafts();
                    productSkuInspReportDrafts.forEach(item->{
                        item.setSkuCode(productSkuDraft.getSkuCode());
                        item.setSkuName(productSkuDraft.getSkuName());
                    });
                    productSkuInspReportService.insertDraftList(productSkuInspReportDrafts);
                }
            } else {
                // 组合商品进项/销项税率.从主商品获取
                //主商品skuCode获取
                String skuCode = productSkuSubs.stream().filter(item -> Objects.equals(item.getMainProduct(), Global.MAIN_PRODUCT)).map(ProductSkuSubDraft::getSubSkuCode).findFirst().get();
                //根据skuCode获取正式结算信息
                ProductSkuCheckoutRespVo productSkuCheckoutRespVo = productSkuCheckoutService.getBySkuCode(skuCode);
                outputTaxRateL = productSkuCheckoutRespVo.getOutputTaxRate();
                outputTaxRate = new BigDecimal(productSkuCheckoutRespVo.getOutputTaxRate()).divide(new BigDecimal(10000), 4, BigDecimal.ROUND_DOWN);
            }
            //获取分销信息
            List<PurchaseSaleStockReqVo> saleList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.SALE.getStatus(),item.getType())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(saleList)){
                throw new BizException(ResultCode.SALE_EMPTY);
            }
            if (saleList.size() != 1) {
                throw new BizException(ResultCode.SALE_ONE);
            }
            try {
                ProductSkuDisInfoDraft productSkuDisInfoDraft = BeanCopyUtils.copy(saleList.get(0),ProductSkuDisInfoDraft.class);
                productSkuDisInfoDraft.setProductSkuCode(productSkuDraft.getSkuCode());
                productSkuDisInfoDraft.setProductSkuName(productSkuDraft.getSkuName());
                productSkuDisInfoDraft.setProductCode(productSkuDraft.getProductCode());
                productSkuDisInfoDraft.setProductName(productSkuDraft.getProductName());
                productSkuDisInfoService.insertDraft(productSkuDisInfoDraft);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
            }
            //价格
            if (null != addSkuInfoReqVO.getProductSkuPrices() && addSkuInfoReqVO.getProductSkuPrices().size() > 0){
                List<SkuPriceDraftReqVO> temps = addSkuInfoReqVO.getProductSkuPrices();
                final BigDecimal finalOutputTaxRate = outputTaxRate;
                final Long finalOutputTaxRateL = outputTaxRateL;
                temps.forEach(item->{
                    //SKU编码
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                    //公司
                    item.setCompanyCode(getUser().getCompanyCode());
                    item.setCompanyName(getUser().getCompanyName());
                    //采购组
                    item.setPurchaseGroupCode(productSkuDraft.getProcurementSectionCode());
                    item.setPurchaseGroupName(productSkuDraft.getProcurementSectionName());
                    //税率
                    item.setTax(finalOutputTaxRateL);
                    //含税价
                    Long taxIncludedPrice = new BigDecimal(item.getPriceTax()).divide(new BigDecimal(100)).divide(BigDecimal.ONE.add(finalOutputTaxRate),2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).longValue();
                    item.setPriceNoTax(taxIncludedPrice);
                    //创建/修改时间/人
                    item.setCreateBy(getUser().getPersonName());
                    item.setCreateTime(new Date());
                    item.setUpdateBy(getUser().getPersonName());
                    item.setUpdateTime(new Date());
                });
                productSkuPrices.addAll(temps);
            }
            if(CollectionUtils.isNotEmpty(productSkuPrices)){
                productSkuPriceInfoService.saveSkuPriceDraft(productSkuPrices);
            }
            //商品配置
            if (null != addSkuInfoReqVO.getProductSkuConfigs() && addSkuInfoReqVO.getProductSkuConfigs().size() > 0){
                List<SaveSkuConfigReqVo> productSkuConfigs = addSkuInfoReqVO.getProductSkuConfigs();
                productSkuConfigs.forEach(item->{
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                    item.setProductName(productSkuDraft.getProductName());
                    item.setProductCode(productSkuDraft.getProductCode());
                });
                productSkuConfigService.insertDraftList(productSkuConfigs);
            }
            //sku图片及介绍
            if (CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuPicturesDrafts())){
                List<ProductSkuPicturesDraft> productSkuPicturesDrafts = addSkuInfoReqVO.getProductSkuPicturesDrafts();
                productSkuPicturesDrafts.forEach(item->{
                    item.setProductSkuCode(productSkuDraft.getSkuCode());
                    item.setProductSkuName(productSkuDraft.getSkuName());
                });
                productSkuPicturesService.insertDraftList(productSkuPicturesDrafts);
            }
            //sku商品说明
            if (null != addSkuInfoReqVO.getProductSkuPicDescDrafts() && addSkuInfoReqVO.getProductSkuPicDescDrafts().size() > 0){
                List<ProductSkuPicDescDraft> productSkuPicDescDrafts = addSkuInfoReqVO.getProductSkuPicDescDrafts();
                productSkuPicDescDrafts.forEach(item-> {
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                });
                productSkuPicDescService.insertDraftList(productSkuPicDescDrafts);
            }
            //sku文件管理
            if (null != addSkuInfoReqVO.getProductSkuFileDrafts() && addSkuInfoReqVO.getProductSkuFileDrafts().size() > 0){
                List<ProductSkuFileDraft> productSkuFileDrafts = addSkuInfoReqVO.getProductSkuFileDrafts();
                productSkuFileDrafts.forEach(item->{
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                });
                productSkuFileService.insertDraftList(productSkuFileDrafts);
            }
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 更新sku所有信息
     *
     * @param addSkuInfoReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO) {
        if(Objects.isNull(addSkuInfoReqVO.getProductSkuDraft().getSkuCode())){
            throw new BizException(ResultCode.SKU_CODE_EMPTY);
        }
        List<String> skuCodes = Lists.newArrayList();
        skuCodes.add(addSkuInfoReqVO.getProductSkuDraft().getSkuCode());
        ((SkuInfoService)AopContext.currentProxy()).deleteProductSkuDraft(skuCodes);
        return  ((SkuInfoService)AopContext.currentProxy()).saveDraftSkuInfo(addSkuInfoReqVO);
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public int insertDraft(ProductSkuDraft productSkuDraft) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != currentAuthToken){
            productSkuDraft.setCompanyCode(currentAuthToken.getCompanyCode());
            productSkuDraft.setCompanyName(currentAuthToken.getCompanyName());
        }
        return  productSkuDraftMapper.insert(productSkuDraft);
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public int insertApply(ApplyProductSku applyProductSku) {
        return  applyProductSkuMapper.insert(applyProductSku);
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int saveSkuApplyInfo(SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO) {
        //验证重复 如果有审核中的数据，不能提交流程
        StringBuffer sb = new StringBuffer();
        if(CollectionUtils.isNotEmpty(saveSkuApplyInfoReqVO.getSkuCodes())){
            //过滤掉重复数据
            List<String> skuCodes = saveSkuApplyInfoReqVO.getSkuCodes().stream().distinct().collect(Collectors.toList());
            saveSkuApplyInfoReqVO.setSkuCodes(skuCodes);
            List<ApplyProductSku> productSkus = applyProductSkuService.getProductApplyList(saveSkuApplyInfoReqVO.getSkuCodes(),ApplyStatus.APPROVAL.getNumber());
            if(CollectionUtils.isNotEmpty(productSkus)){
                productSkus.forEach(o-> sb.append("sku编码为").append(o.getSkuCode()).append("的sku已有在审核流程中的数据 "));
            }
        }
        if(StringUtils.isNotBlank(sb.toString())){
            throw new BizException(MessageId.create(Project.SCMP_API, 5001, sb.append("提交失败").toString()));
        }
        List<ProductSkuDraft> productSkuDrafts = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(saveSkuApplyInfoReqVO.getSkuCodes())){
            productSkuDrafts = productSkuDao.getSkuDraftByCodes(saveSkuApplyInfoReqVO.getSkuCodes());
        }
        String formNo =  "SP"+ new IdSequenceUtils().nextId();
        EncodingRule encodingRule = encodingRuleDao.getNumberingType("APPLY_PRODUCT_CODE");
        long code = encodingRule.getNumberingValue();
        List<ApplyProductSku> applyProductSkus = BeanCopyUtils.copyList(productSkuDrafts, ApplyProductSku.class);
        Date currentDate = new Date();
        applyProductSkus.forEach(item->{
            item.setApplyCode(String.valueOf(code));
            item.setSelectionEffectiveTime(saveSkuApplyInfoReqVO.getSelectionEffectiveTime());
            item.setSelectionEffectiveStartTime(saveSkuApplyInfoReqVO.getSelectionEffectiveStartTime());
            item.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
            item.setCreateTime(currentDate);
            item.setUpdateTime(currentDate);
            item.setFormNo(formNo);
        });
        if (CollectionUtils.isNotEmpty(applyProductSkus)){
            //批量新增sku申请信息
            productSkuDao.insertApplySkuList(applyProductSkus);
            //删除sku草稿信息
            if (null != saveSkuApplyInfoReqVO.getSkuCodes() && saveSkuApplyInfoReqVO.getSkuCodes().size() > 0){
                productSkuDao.deleteSkuDraftByCodes(saveSkuApplyInfoReqVO.getSkuCodes());
            }
            //渠道
            productSkuChannelService.saveApplyList(applyProductSkus);
            //标签
            List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCodes(saveSkuApplyInfoReqVO.getSkuCodes(),TagTypeCode.SKU.getStatus());
            if(CollectionUtils.isNotEmpty(applyUseTagRecords)){
                applyUseTagRecords.forEach(item->{
                    item.setApplyUseObjectCode(String.valueOf(code));
                });
                applyUseTagRecordService.updateBatch(applyUseTagRecords);
            }
            //包装
            productSkuBoxPackingService.saveApplyList(applyProductSkus);
            //进销存信息
            productSkuStockInfoService.saveApplyList(applyProductSkus);
            productSkuPurchaseInfoService.saveApplyList(applyProductSkus);
            productSkuDisInfoService.saveApplyList(applyProductSkus);
            productSkuSalesInfoService.saveApplyList(applyProductSkus);
            //结算
            productSkuCheckoutService.saveApply(applyProductSkus);
            //供应商
            productSkuSupplyUnitService.saveApplyList(applyProductSkus);
            //供应商产能
            productSkuSupplyUnitCapacityService.saveApplyList(applyProductSkus);
            //价格
            List<ProductSkuPriceInfoDraft> productSkuPriceInfoDrafts =
                    productSkuPriceInfoService.getSkuPriceListDraftBySkuCodes(saveSkuApplyInfoReqVO.getSkuCodes());
            if(CollectionUtils.isNotEmpty(productSkuPriceInfoDrafts)){
                List<ApplyProductSkuPriceInfo> applyList = BeanCopyUtils.copyList(productSkuPriceInfoDrafts,ApplyProductSkuPriceInfo.class);
                applyList.forEach(item->{
                    item.setApplyCode(String.valueOf(code));
                });
                productSkuPriceInfoService.saveSkuPriceApply(applyList);
                productSkuPriceInfoService.deleteSkuPriceDraft(saveSkuApplyInfoReqVO.getSkuCodes());
            }
            //配置
            productSkuConfigService.outInsertApplyList(applyProductSkus);
            //关联商品
            productSkuAssociatedGoodsService.saveApplyList(applyProductSkus);
            //生产厂家
            productSkuManufacturerService.saveApplyList(applyProductSkus);
            //图片及介绍
            productSkuPicturesService.saveApplyList(applyProductSkus);
            //商品说明
            productSkuPicDescService.saveApplyList(applyProductSkus);
            //文件
            productSkuFileService.saveApplyList(applyProductSkus);
            //质检报告
            productSkuInspReportService.saveApplyList(applyProductSkus);
            //组合商品子商品列表
            productSkuSubService.saveApplyList(applyProductSkus);
        }
        //修改申请编码
        encodingRuleDao.updateNumberValue(Long.valueOf(code),encodingRule.getId());
        if (CollectionUtils.isNotEmpty(applyProductSkus)){
            //调用审批接口
            workFlow(String.valueOf(code),formNo,applyProductSkus,saveSkuApplyInfoReqVO.getDirectSupervisorCode());
        }
        return 1;
    }

    @Override
    public ProductSkuDetailResp getSkuDraftInfo(String skuCode) {
        ProductSkuDetailResp detailResp = new ProductSkuDetailResp();
        //SKU基本信息
        ProductSkuRespVo skuRespVo = productSkuDao.getSkuDraft(skuCode);
        if (null == skuRespVo) {
            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
        }
        detailResp.setProductSkuInfo(skuRespVo);
        //标签信息
        List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCode(skuRespVo.getSkuCode(),TagTypeCode.SKU.getStatus());
        detailResp.setTagInfoList(applyUseTagRecords);
        //SKU渠道信息
        List<ProductSkuChannelRespVo> skuChannelRespVos = productSkuChannelService.getDraftList(skuCode);
        detailResp.setProductSkuChannels(skuChannelRespVos);
        //SKU进销存信息
        List<PurchaseSaleStockRespVo> purchaseSaleStocks = Lists.newArrayList();
        SkuTypeEnum skuTypeEnum = SkuTypeEnum.getSkuTypeEnumByType(skuRespVo.getGoodsGifts());
        if(!Objects.equals(skuTypeEnum,SkuTypeEnum.COMBINATION)){
            //库存配置信息
            purchaseSaleStocks.addAll(productSkuStockInfoService.getDraftList(skuCode));
            //采购配置信息
            purchaseSaleStocks.addAll(productSkuPurchaseInfoService.getDraftList(skuCode));
            //门店销售
            purchaseSaleStocks.addAll(productSkuSalesInfoService.getDraftList(skuCode));
            //sku整箱商品包装信息
            detailResp.setProductSkuBoxPackings(productSkuBoxPackingService.getDraftList(skuCode));
            //SKU结算信息
            detailResp.setProductSkuCheckout(productSkuCheckoutService.getDraft(skuCode));
            //供应商信息
            detailResp.setProductSkuSupplyUnits(productSkuSupplyUnitService.getDraftList(skuCode));
            //关联商品信息
            detailResp.setProductAssociatedGoods(productSkuAssociatedGoodsService.getDraftList(skuCode));
            //sku生产厂家信息
            detailResp.setProductSkuManufacturers(productSkuManufacturerService.getDraftList(skuCode));
            //sku质检信息
            detailResp.setProductSkuInspReports(productSkuInspReportService.getDraftList(skuCode));
        }else{
            detailResp.setProductSkuSubRespVos(productSkuSubService.draftDetail(skuCode));
        }
        //分销
        purchaseSaleStocks.addAll(productSkuDisInfoService.getDraftList(skuCode));
        detailResp.setPurchaseSaleStocks(purchaseSaleStocks);
        //sku图片及介绍
        detailResp.setProductSkuPictures(productSkuPicturesService.getDraftList(skuCode));
        //sku商品说明
        detailResp.setProductSkuPicDescs(productSkuPicDescService.getDraftList(skuCode));
        //sku文件管理
        detailResp.setProductSkuFiles(productSkuFileService.getDraftList(skuCode));
        //价格信息
        List<ProductSkuPriceRespVo> draftTemps =
                productSkuPriceInfoService.getSkuPriceBySkuCodeForDraft(skuCode);
        List<ProductSkuPriceRespVo> priceDraftRespVos =
                draftTemps.stream().filter(item ->
                        !Objects.equals(item.getPriceTypeCode(), PriceTypeEnum.PURCHASE.getTypeCode())).
                        collect(Collectors.toList());
        detailResp.setProductSkuPrices(priceDraftRespVos);
        //配置信息
        detailResp.setProductSkuConfigs(productSkuConfigService.draftDetail(skuCode));
        return detailResp;
    }

    @Override
    public ProductSkuDetailResp getSkuDetail(String skuCode) {
        ProductSkuDetailResp detailResp = new ProductSkuDetailResp();
        //SKU基本信息
        ProductSkuRespVo skuRespVo = productSkuDao.getSkuInfoResp(skuCode);
        if (null == skuRespVo) {
            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
        }
        detailResp.setProductSkuInfo(skuRespVo);
        //标签信息
        List<DetailTagUseRespVo> useTagRecordByUseObjectCode = tagInfoService.getUseTagRecordByUseObjectCode(skuRespVo.getSkuCode(), TagTypeCode.SKU.getStatus());
        List<ApplyUseTagRecord> applyUseTagRecords = BeanCopyUtils.copyList(useTagRecordByUseObjectCode, ApplyUseTagRecord.class);
        detailResp.setTagInfoList(applyUseTagRecords);
        //SKU渠道信息
        List<ProductSkuChannelRespVo> skuChannelRespVos = productSkuChannelService.getList(skuCode);
        detailResp.setProductSkuChannels(skuChannelRespVos);
        //SKU进销存信息
        List<PurchaseSaleStockRespVo> purchaseSaleStocks = Lists.newArrayList();
        SkuTypeEnum skuTypeEnum = SkuTypeEnum.getSkuTypeEnumByType(skuRespVo.getGoodsGifts());
        if(!Objects.equals(skuTypeEnum,SkuTypeEnum.COMBINATION)){
            //库存配置信息
            purchaseSaleStocks.addAll(productSkuStockInfoService.getList(skuCode));
            //采购配置信息
            purchaseSaleStocks.addAll(productSkuPurchaseInfoService.getList(skuCode));
            //门店销售
            purchaseSaleStocks.addAll(productSkuSalesInfoService.getList(skuCode));
            //sku整箱商品包装信息
            detailResp.setProductSkuBoxPackings(productSkuBoxPackingService.getList(skuCode));
            //SKU结算信息
            detailResp.setProductSkuCheckout(productSkuCheckoutService.getBySkuCode(skuCode));
            //供应商信息
            detailResp.setProductSkuSupplyUnits(productSkuSupplyUnitService.selectBySkuCode(skuCode));
            //关联商品信息
            detailResp.setProductAssociatedGoods(productSkuAssociatedGoodsService.getList(skuCode));
            //sku生产厂家信息
            detailResp.setProductSkuManufacturers(productSkuManufacturerService.getList(skuCode));
            //sku质检信息
            detailResp.setProductSkuInspReports(productSkuInspReportService.getListBySkuCode(skuCode));
        }else{
            detailResp.setProductSkuSubRespVos(productSkuSubService.getList(skuCode));
        }

        //分销
        purchaseSaleStocks.addAll(productSkuDisInfoService.getList(skuCode));
        detailResp.setPurchaseSaleStocks(purchaseSaleStocks);
        //sku图片及介绍
        detailResp.setProductSkuPictures(productSkuPicturesService.getList(skuCode));
        //sku商品说明
        detailResp.setProductSkuPicDescs(productSkuPicDescService.getList(skuCode));
        //sku文件管理
        detailResp.setProductSkuFiles(productSkuFileService.getList(skuCode));
        //价格信息
        List<ProductSkuPriceRespVo> applyProductSkuPriceInfos =
                productSkuPriceInfoService.getSkuPriceBySkuCodeForOfficial(skuCode);
        detailResp.setProductSkuPrices(applyProductSkuPriceInfos);
        //配置信息
        detailResp.setProductSkuConfigs(productSkuConfigService.getList(skuCode));
        return detailResp;
    }

    @Override
    public BasePage<QueryProductSkuListResp> querySkuList(QuerySkuListReqVO querySkuListReqVO) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                querySkuListReqVO.setCompanyCode(authToken.getCompanyCode());
            }
            PageHelper.startPage(querySkuListReqVO.getPageNo(),querySkuListReqVO.getPageSize());
            List<QueryProductSkuListResp> queryProductSkuListResps = productSkuDao.querySkuList(querySkuListReqVO);
            return PageUtil.getPageList(querySkuListReqVO.getPageNo(),queryProductSkuListResps);
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 通过供应商编号查询
     *
     * @param supplyUnitCode
     * @return
     */
    @Override
    public List<QueryProductSkuListResp> querySkuListBySupplyUnitCode(String supplyUnitCode) {
        return productSkuDao.querySkuListBySupplyUnitCode(supplyUnitCode);
    }

    @Override
    public List<ProductDraftListResp> getProductDraftList() {
        try {
            List<ProductDraftListResp> productDraftListResps = productSkuDao.getProductDraftList();
            return productDraftListResps;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<ProductSkuDraft> getProductSkuDraftList() {
        try {
            List<ProductSkuDraft> productSkuDrafts = productSkuDao.getProductSkuDraftList();
            return productSkuDrafts;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public ProductApplyInfoRespVO<ProductSkuApplyVo> getSkuApplyDetail(String applyCode) {
        List<ProductSkuApplyVo> list = applyProductSkuMapper.selectByApplyCode(applyCode);
        if(CollectionUtils.isEmpty(list)){
            throw new BizException(MessageId.create(Project.PRODUCT_API,98,"数据异常，无法查询到该数据"));
        }
        return dealApplyViewData(list);
    }



    @Override
    public ProductSkuDetailResp getProductSkuApplyDetail(String skuCode,String applyCode) {
        ProductSkuDetailResp detailResp = new ProductSkuDetailResp();
        //SKU基本信息
        ProductSkuRespVo skuRespVo = productSkuDao.getApply(skuCode,applyCode);
        if (null == skuRespVo) {
            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
        }
        detailResp.setProductSkuInfo(skuRespVo);
        //标签信息
        List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCode(applyCode,TagTypeCode.SKU.getStatus());
        detailResp.setTagInfoList(applyUseTagRecords);
        //SKU渠道信息
        List<ProductSkuChannelRespVo> skuChannelRespVos = productSkuChannelService.getApplyList(skuCode,applyCode);
        detailResp.setProductSkuChannels(skuChannelRespVos);
        SkuTypeEnum skuTypeEnum = SkuTypeEnum.getSkuTypeEnumByType(skuRespVo.getGoodsGifts());
        //SKU进销存信息
        List<PurchaseSaleStockRespVo> purchaseSaleStocks = Lists.newArrayList();
        if(!Objects.equals(skuTypeEnum,SkuTypeEnum.COMBINATION)){
            //库存配置信息
            purchaseSaleStocks.addAll(productSkuStockInfoService.getApplyList(skuCode,applyCode));
            //采购配置信息
            purchaseSaleStocks.addAll(productSkuPurchaseInfoService.getApplyList(skuCode,applyCode));
            //销售
            purchaseSaleStocks.addAll(productSkuSalesInfoService.getApplyList(skuCode,applyCode));
            //sku整箱商品包装信息
            detailResp.setProductSkuBoxPackings(productSkuBoxPackingService.getApply(skuCode,applyCode));
            //SKU结算信息
            detailResp.setProductSkuCheckout(productSkuCheckoutService.getApply(skuCode,applyCode));
            //供应商信息
            detailResp.setProductSkuSupplyUnits(productSkuSupplyUnitService.getApply(skuCode,applyCode));
            //关联商品信息
            detailResp.setProductAssociatedGoods(productSkuAssociatedGoodsService.getApply(skuCode,applyCode));
            //sku生产厂家信息
            detailResp.setProductSkuManufacturers(productSkuManufacturerService.getApply(skuCode,applyCode));
            //sku质检信息
            detailResp.setProductSkuInspReports(productSkuInspReportService.getApply(skuCode,applyCode));
        } else {
            //组合商品子SKU列表信息
            detailResp.setProductSkuSubRespVos(productSkuSubService.getApply(skuCode,applyCode));
        }
        //分销
        purchaseSaleStocks.addAll(productSkuDisInfoService.getApplyList(skuCode,applyCode));
        detailResp.setPurchaseSaleStocks(purchaseSaleStocks);
        //sku图片及介绍
        detailResp.setProductSkuPictures(productSkuPicturesService.getApply(skuCode,applyCode));
        //sku商品说明
        detailResp.setProductSkuPicDescs(productSkuPicDescService.getApply(skuCode,applyCode));
        //sku文件管理
        detailResp.setProductSkuFiles(productSkuFileService.getApply(skuCode,applyCode));
        //价格信息
        List<ProductSkuPriceRespVo> applyProductSkuPriceInfos =
                productSkuPriceInfoService.getSkuPriceBySkuCodeForApply(skuCode,applyCode);
        List<ProductSkuPriceRespVo> priceDraftRespVos =
                applyProductSkuPriceInfos.stream().filter(item ->
                        !Objects.equals(item.getPriceTypeCode(), PriceTypeEnum.PURCHASE.getTypeCode())).
                        collect(Collectors.toList());
        detailResp.setProductSkuPrices(priceDraftRespVos);
        //配置信息
        detailResp.setProductSkuConfigs(productSkuConfigService.getApply(skuCode,applyCode));
        return detailResp;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int cancelSkuApply(String applyCode) {
        String formNo = applyProductSkuMapper.findFormNoByCode(applyCode);
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormNo(formNo);
        // 调用审批流的撤销接口
        WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
        if(workFlowRespVO.getSuccess().equals(true)){
            return 0;
        }else {
            throw  new GroundRuntimeException("撤销失败");
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @Update
    public int cancelApply(ApplyProductSku applyProductSku) {
        try {
            return applyProductSkuMapper.updateByPrimaryKeySelective(applyProductSku);
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public void workFlow(String applyCode, String form, List<ApplyProductSku> applyProductSkus, String directSupervisorCode) {

        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormUrl(workFlowBaseUrl.applySku+"?approvalType=1&code="+applyCode+"&"+workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(form);
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ WorkFlow.APPLY_GOODS.getNum());
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        String personName = null != currentAuthToken.getPersonName() ? currentAuthToken.getPersonName() : "";
        String currentTime= DateUtils.getCurrentDateTime(DateUtils.FORMAT);
        String title = personName+"在"+currentTime+","+WorkFlow.APPLY_GOODS.getTitle();
        workFlowVO.setTitle(title);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_GOODS);
        if(workFlowRespVO.getSuccess()){
            if(CollectionUtils.isNotEmpty(applyProductSkus)){
                //存日志
                 productCommonService.getInstance(applyCode +"", HandleTypeCoce.ADD_PRODUCT_SKU.getStatus(), ObjectTypeCode.APPLY_SKU.getStatus(),applyProductSkus,HandleTypeCoce.ADD_PRODUCT_SKU.getName());
            }
        }else{
            //存调用失败的日志
            throw new BizException(MessageId.create(Project.PRODUCT_API,57,workFlowRespVO.getMsg()));
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public String skuWorkFlowCallback(WorkFlowCallbackVO vo1) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(vo1);
        List<ApplyProductSku> applyProductSkus = productSkuDao.getApplySkuByFormNo(vo.getFormNo());
        if(CollectionUtils.isEmpty(applyProductSkus)){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        String applyCode = applyProductSkus.get(0).getApplyCode();
        Date auditorTime = new Date();
        for (int i =0 ; i < applyProductSkus.size();i++){
            Byte handleTypeCoceStatus;
            String handleTypeCoceName;
            String skuCode = applyProductSkus.get(i).getSkuCode();
            String skuName =  applyProductSkus.get(i).getSkuName();
            //sku基本信息
            ProductSkuInfo oldSku = productSkuDao.getSkuInfo(skuCode);
            ProductSkuInfo productSkuInfo = new ProductSkuInfo();
            BeanCopyUtils.copyIgnoreNullValue(applyProductSkus.get(i),productSkuInfo);
            productSkuInfo.setAuditorBy(vo.getApprovalUserName());
            productSkuInfo.setAuditorTime(auditorTime);
            productSkuInfo.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
            if (null != oldSku) {
                productSkuInfo.setId(oldSku.getId());
                handleTypeCoceStatus = HandleTypeCoce.UPDATE_SKU.getStatus();
                handleTypeCoceName = HandleTypeCoce.UPDATE_SKU.getName();
                productSkuInfoMapper.updateByPrimaryKeySelective(productSkuInfo);
            } else {
                productSkuInfo.setId(null);
                handleTypeCoceStatus = HandleTypeCoce.ADD_SKU.getStatus();
                handleTypeCoceName = HandleTypeCoce.ADD_SKU.getName();
                productSkuInfoMapper.insertSelective(productSkuInfo);
            }
            productCommonService.getInstance(productSkuInfo.getSkuCode(),handleTypeCoceStatus,ObjectTypeCode.APPLY_SKU.getStatus(),productSkuInfo,handleTypeCoceName);
            //渠道
            productSkuChannelService.save(skuCode,applyCode);
            //标签
            List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCode(applyCode,TagTypeCode.SKU.getStatus());
            if(CollectionUtils.isNotEmpty(applyUseTagRecords)) {
                List<SaveUseTagRecordReqVo> records = Lists.newArrayList();
                SaveUseTagRecordReqVo saveUseTagRecordReqVo = new SaveUseTagRecordReqVo();
                saveUseTagRecordReqVo.setUseObjectCode(skuCode);
                saveUseTagRecordReqVo.setUseObjectName(skuName);
                saveUseTagRecordReqVo.setTagTypeCode(TagTypeCode.SKU.getStatus());
                saveUseTagRecordReqVo.setTagTypeName(TagTypeCode.SKU.getName());
                saveUseTagRecordReqVo.setSourceCode(applyCode);
                List<SaveUseTagRecordItemReqVo> tagRecordItemReqVos = BeanCopyUtils.copyList(applyUseTagRecords, SaveUseTagRecordItemReqVo.class);
                saveUseTagRecordReqVo.setItemReqVos(tagRecordItemReqVos);
                records.add(saveUseTagRecordReqVo);
                tagInfoService.saveRecordList(records);
            }
            //包装
            productSkuBoxPackingService.saveInfo(skuCode,applyCode);
            //进销存信息
            productSkuStockInfoService.saveInfo(skuCode,applyCode);
            productSkuPurchaseInfoService.saveInfo(skuCode,applyCode);
            productSkuDisInfoService.saveInfo(skuCode,applyCode);
            productSkuSalesInfoService.saveList(skuCode,applyCode);
            //结算
            productSkuCheckoutService.saveInfo(skuCode,applyCode);
            //供应商
            productSkuSupplyUnitService.saveList(skuCode,applyCode);
            //供应商产能
            productSkuSupplyUnitCapacityService.saveList(skuCode,applyCode);
            //配置
            productSkuConfigService.saveList(vo,skuCode,applyCode);
            //关联商品
            productSkuAssociatedGoodsService.saveList(skuCode,applyCode);
            //生产厂家
            productSkuManufacturerService.saveList(skuCode,applyCode);
            //图片及介绍
            productSkuPicturesService.saveList(skuCode,applyCode);
            //商品说明
            productSkuPicDescService.saveList(skuCode,applyCode);
            //文件
            productSkuFileService.saveList(skuCode,applyCode);
            //质检报告
            productSkuInspReportService.saveList(skuCode,applyCode);
            //组合商品子商品列表
            productSkuSubService.saveList(skuCode,applyCode);
        }
        //价格
        List<String> skuCodes = applyProductSkus.stream().map(item -> item.getSkuCode()).distinct().collect(Collectors.toList());
        List<ApplyProductSkuPriceInfo> skuPriceListApplyBySkuCode = productSkuPriceInfoService.getSkuPriceListApplyBySkuCodes(skuCodes, applyCode);
        if(CollectionUtils.isNotEmpty(skuPriceListApplyBySkuCode)){
            List<ProductSkuPriceInfo> productSkuPriceInfos = BeanCopyUtils.copyList(skuPriceListApplyBySkuCode,ProductSkuPriceInfo.class);
            productSkuPriceInfoService.saveSkuPriceOfficial(productSkuPriceInfos);
        }
        //更新审批状态
        applyProductSkuMapper.updateStatusByFormNo(ApplyStatus.APPROVAL_SUCCESS.getNumber(),vo.getFormNo(),vo.getApprovalUserName(),auditorTime);
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }

    /**
     * 获取商品临时数据
     *
     * @param companyCode
     * @return
     */
    @Override
    public List<ProductSkuDraftRespVo> getProductSkuDraftsByCompanyCode(String companyCode,String personId) {
        return productSkuDraftMapper.getProductSkuDraftByCompanyCode(companyCode,personId);
    }

    /**
     * 根据条件查询正式SKU信息
     *
     * @param reqVO
     * @return
     */
    @Override
    public List<ProductSkuRespVo> getProductSkuInfos(QuerySkuReqVO reqVO) {
        String companyCode = null;
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (null != authToken) {
            companyCode = authToken.getCompanyCode();
        }
        reqVO.setCompanyCode(companyCode);
        return  productSkuDao.getProductSkuInfos(reqVO);
    }

    /**
     * 批量删除草稿信息
     *
     * @param skuCodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteProductSkuDraft(List<String> skuCodes) {
        int deleteNum = productSkuDao.deleteSkuDraftByCodes(skuCodes);
        if (deleteNum == 0 ) {
            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
        }
        productSkuChannelService.deleteDrafts(skuCodes);
        productSkuPurchaseInfoService.deleteDrafts(skuCodes);
        productSkuStockInfoService.deleteDrafts(skuCodes);
        productSkuDisInfoService.deleteDrafts(skuCodes);
        productSkuSalesInfoService.deleteDrafts(skuCodes);
        productSkuBoxPackingService.deleteDrafts(skuCodes);
        productSkuCheckoutService.deleteDrafts(skuCodes);
        productSkuSupplyUnitService.deleteDrafts(skuCodes);
        productSkuSupplyUnitCapacityService.deleteDrafts(skuCodes);
        productSkuAssociatedGoodsService.deleteDrafts(skuCodes);
        productSkuManufacturerService.deleteDrafts(skuCodes);
        productSkuPicturesService.deleteDrafts(skuCodes);
        productSkuPicDescService.deleteDrafts(skuCodes);
        productSkuFileService.deleteDrafts(skuCodes);
        productSkuInspReportService.deleteDrafts(skuCodes);
        applyUseTagRecordService.deletes(skuCodes);
        productSkuConfigService.deleteDraftBySkuCodes(skuCodes);
        productSkuPriceInfoService.deleteSkuPriceDraft(skuCodes);
        return deleteNum;
    }

    /**
     * 根据商品编码获取SKU临时数据
     *
     * @param productCode
     * @return
     */
    @Override
    public List<ProductSkuDraft> getProductSkuDraftsByProductCode(String productCode) {
         return productSkuDraftMapper.getProductSkuDraftByProductCode(productCode);
    }
    @Override
    public BasePage<QuerySkuInfoRespVO> getSkuListByQueryVO(QuerySkuInfoReqVO vo){
        PageHelper.startPage(vo.getPageNo(),vo.getPageSize());
        List<QuerySkuInfoRespVO> list = getSkuListByQueryNoPage(vo);
        return PageUtil.getPageList(vo.getPageNo(),list);
    }
    @Override
    public List<QuerySkuInfoRespVO> getSkuListByQueryNoPage(QuerySkuInfoReqVO vo){
        if(StringUtils.isBlank(vo.getChangePriceType())){
            throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        List<QuerySkuInfoRespVO> list = Lists.newArrayList();
        if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(vo.getChangePriceType())){
            list = productSkuDao.selectSkuListForPurchasePrice(vo);
        } else if(CommonConstant.SALE_PRICE.contains(vo.getChangePriceType())){
            if(CommonConstant.FOREVER_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
            }else if(CommonConstant.TEMP_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
            }
            list = productSkuDao.selectSkuListForSalePrice(vo);
        }else {
            throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        return list;
    }

    @Override
    public List<QueryProductSaleAreaRespVO> selectDirectSupplierBySkuCodes(List<String> collect) {
        return productSkuDao.selectDirectSupplierBySkuCodes(collect);
    }

    @Override
    public BasePage<QueryProductSaleAreaForSkuRespVO> selectSkuListForSaleArea(QueryProductSaleAreaForSkuReqVO reqVO) {
        List<Long> longs = productSkuDao.selectSkuListForSaleAreaCount(reqVO);
        if(CollectionUtils.isEmpty(longs)){
            return PageUtil.getPageList(reqVO.getPageNo(), Lists.newArrayList());
        }
       List<QueryProductSaleAreaForSkuRespVO> list =  productSkuDao.selectSkuListForSaleArea(PageUtil.myPage(longs, reqVO));
        return PageUtil.getPageList(reqVO.getPageNo(),reqVO.getPageSize(),longs.size(),list);
    }

    /**
     * 查询申请审批列表信息
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo) {
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        return applyProductSkuMapper.queryApplyList(reqVo);
    }

    /**
     * 功能描述: 更新SKU状态
     *
     * @param respVos
     * @return
     * @auther knight.xie
     * @date 2019/7/18 0:39
     */
    @Override
    public int updateStatus(List<SkuStatusRespVo> respVos) {
        return productSkuInfoMapper.updateStatus(respVos);
    }

    @Override
    public Map<String,ProductSkuInfo> selectBySkuCodes(Set<String> skuList, String companyCode) {
        return productSkuInfoMapper.selectBySkuCodes(skuList, companyCode);
    }

    private ProductApplyInfoRespVO<ProductSkuApplyVo> dealApplyViewData(List<ProductSkuApplyVo> list) {
        ProductApplyInfoRespVO<ProductSkuApplyVo> resp = new ProductApplyInfoRespVO<>();
        //数据相同默认取第一个
        ProductSkuApplyVo applyVO = list.get(0);
        resp.setApplyBy(applyVO.getCreateBy());
        resp.setApplyTime(applyVO.getCreateTime());
        resp.setApplyStatus(applyVO.getApplyStatus());
        resp.setAuditorBy(applyVO.getAuditorBy());
        resp.setAuditorTime(applyVO.getAuditorTime());
        resp.setSelectionEffectiveStartTime(applyVO.getSelectionEffectiveStartTime());
        resp.setSelectionEffectiveTime(applyVO.getSelectionEffectiveTime());
        resp.setCode(applyVO.getApplyCode());
        resp.setFormNo(applyVO.getFormNo());
        //统计sku数量
        List<String> skuCodes = list.stream().map(ProductSkuApplyVo::getCode).distinct().collect(Collectors.toList());
        resp.setSkuNum(skuCodes.size());
        //统计SPU数量吗
        List<String> spuCodes = list.stream().map(ProductSkuApplyVo::getProductCode).distinct().collect(Collectors.toList());
        resp.setSpuNum(spuCodes.size());
        resp.setData(list);
        return resp;
    }
}


