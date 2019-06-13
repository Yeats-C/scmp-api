package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.ApplyStatus;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.ApplyDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.DraftSaveListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaForSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductMapper;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInfoMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 15:44
 */
@Service
public class SkuInfoServiceImplProduct extends ProductBaseServiceImpl implements SkuInfoService {
    @Autowired
    ProductSkuDraftMapper productSkuDraftMapper;
    @Autowired
    ProductSkuPriceService productSkuPriceService;
    @Autowired
    ProductSkuConfigService productSkuConfigService;
    @Autowired
    ProductSkuFileService productSkuFileService;
    @Autowired
    ProductSkuSupplyUnitService productSkuSupplyUnitService;
    @Autowired
    EncodingRuleDao encodingRuleDao;
    @Autowired
    ProductSkuCheckoutService productSkuCheckoutService;
    @Autowired
    ProductSkuPurchaseInfoService productSkuPurchaseInfoService;
    @Autowired
    ProductSkuDisInfoService productSkuDisInfoService;
    @Autowired
    ProductSkuBoxPackingService productSkuBoxPackingService;
    @Autowired
    ProductSkuPicturesService productSkuPicturesService;
    @Autowired
    ProductSkuPicDescService productSkuPicDescService;
    @Autowired
    ProductSkuSalesInfoService productSkuSalesInfoService;
    @Autowired
    ProductSkuManufacturerService productSkuManufacturerService;
    @Autowired
    ProductSkuInspReportService productSkuInspReportService;
    @Autowired
    ProductSkuDao productSkuDao;
    @Autowired
    ProductSkuCheckoutDao productSkuCheckoutDao;
    @Autowired
    ProductSkuPicturesDao productSkuPicturesDao;
    @Autowired
    ProductSkuPriceDao productSkuPriceDao;
    @Autowired
    ProductSkuPicDescDao productSkuPicDescDao;
    @Autowired
    ProductSkuPurchaseInfoDao productSkuPurchaseInfoDao;
    @Autowired
    ProductSkuDisInfoDao productSkuDisInfoDao;
    @Autowired
    ProductSkuBoxPackingDao productSkuBoxPackingDao;
    @Autowired
    ProductSkuSalesInfoDao productSkuSalesInfoDao;
    @Autowired
    ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Autowired
    ProductSkuManufacturerDao productSkuManufacturerDao;
    @Autowired
    ProductSkuFileDao productSkuFileDao;
    @Autowired
    ApplyProductSkuMapper applyProductSkuMapper;
    @Autowired
    ProductSkuInspReportDao productSkuInspReportDao;
    @Autowired
    ProductCommonService productCommonService;
    @Autowired
    ProductDao productDao;
    @Autowired
    ApplyProductService applyProductService;
    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Autowired
    ApplyProductDraftService applyProductDraftService;
    @Autowired
    ProductSkuInfoMapper productSkuInfoMapper;
    @Autowired
    private ApplyProductMapper applyProductMapper;
    @Autowired
    private ApplyProductSkuServiceProduct applyProductSkuService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductSkuChannelService productSkuChannelService;
    @Autowired
    private ProductSkuSupplyUnitCapacityService productSkuSupplyUnitCapacityService;
    @Autowired
    private ProductSkuAssociatedGoodsService productSkuAssociatedGoodsService;
    @Autowired
    private ProductSkuStockInfoService productSkuStockInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO) {
        if (null != addSkuInfoReqVO && null != addSkuInfoReqVO.getProductSkuDraft()){
            //SKU基本信息
            ProductSkuDraft productSkuDraft = addSkuInfoReqVO.getProductSkuDraft();
            //拆分品类信息
            String productCategoryCode = productSkuDraft.getProductCategoryCode();
            if(StringUtils.isNotBlank(productCategoryCode)){
                String[] split = productCategoryCode.split(",");
                productCategoryCode = split[split.length-1];
                productSkuDraft.setProductCategoryCode(productCategoryCode);
            }
            if (null != productSkuDraft.getSkuCode()){
                productSkuDraft.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                productSkuDraft.setApplyTypeName(StatusTypeCode.UPDATE_APPLY.getName());
                ((SkuInfoService) AopContext.currentProxy()).insertDraft(productSkuDraft);
            } else {
                EncodingRule encodingRule=encodingRuleDao.getNumberingType("PRODUCT_SKU_CODE");
                Long thisCode = encodingRule.getNumberingValue();
                productSkuDraft.setSkuCode(String.valueOf(thisCode+1));
                productSkuDraft.setSkuStatus(StatusTypeCode.EN_ABLE.getStatus());
                productSkuDraft.setOnSale(Global.ON_SALE_TOP.byteValue());
                productSkuDraft.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                productSkuDraft.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
                ((SkuInfoService) AopContext.currentProxy()).insertDraft(productSkuDraft);
                encodingRuleDao.updateNumberValue(thisCode,encodingRule.getId());
            }
            productCommonService.getInstance(productSkuDraft.getSkuCode(), HandleTypeCoce.ADD_SKU.getStatus(), ObjectTypeCode.SKU_MANAGEMENT.getStatus(),addSkuInfoReqVO.getProductSkuDraft(),HandleTypeCoce.ADD_SKU.getName());
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
            //获取销售信息
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
            if (null != addSkuInfoReqVO.getProductSkuCheckoutDraft()){
                ProductSkuCheckoutDraft productSkuCheckoutDraft =addSkuInfoReqVO.getProductSkuCheckoutDraft();
                productSkuCheckoutDraft.setSkuCode(productSkuDraft.getSkuCode());
                productSkuCheckoutDraft.setSkuName(productSkuDraft.getSkuName());
                productSkuCheckoutService.insertDraft(productSkuCheckoutDraft);
                inputTaxRateL = productSkuCheckoutDraft.getInputTaxRate();
                inputTaxRate = new BigDecimal(productSkuCheckoutDraft.getInputTaxRate()).divide(new BigDecimal(10000),4,BigDecimal.ROUND_DOWN);
            }

            //供应商信息
            if (CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuSupplyUnitDrafts())){
                List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts = addSkuInfoReqVO.getProductSkuSupplyUnitDrafts();
                final BigDecimal finalInputTaxRate = inputTaxRate;
                final Long finalInputTaxRateL = inputTaxRateL;
                List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts = Lists.newArrayList();
                productSkuSupplyUnitDrafts.forEach(item->{
                    item.setProductSkuCode(productSkuDraft.getSkuCode());
                    item.setProductSkuName(productSkuDraft.getSkuName());
                    //先把未税金额除以100兑换成元,未税金额/(1+税率) = 含税金额,最终结果*100转换成分,舍弃分以后的数字
                    Long taxIncludedPrice = new BigDecimal(item.getNoTaxPurchasePrice()).divide(new BigDecimal(100)).divide(BigDecimal.ONE.add(finalInputTaxRate),2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).longValue();
                    item.setTaxIncludedPrice(taxIncludedPrice);
                    item.setJointFranchiseRate(finalInputTaxRateL);
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
                });
                productSkuSupplyUnitService.insertDraftList(productSkuSupplyUnitDrafts);
                //供应商产能
                if (CollectionUtils.isNotEmpty(productSkuSupplyUnitCapacityDrafts)){
                    productSkuSupplyUnitCapacityService.insertDraftList(productSkuSupplyUnitCapacityDrafts);
                }
            }

            //价格(未确定)
            if (null != addSkuInfoReqVO.getProductSkuPriceDrafts() && addSkuInfoReqVO.getProductSkuPriceDrafts().size() > 0){
                List<ProductSkuPriceDraft> productSkuPriceDrafts = addSkuInfoReqVO.getProductSkuPriceDrafts();
                productSkuPriceDrafts.forEach(item->{
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                });
                productSkuPriceService.insertDraftList(productSkuPriceDrafts);
            }
            //商品配置(未确定)
            if (null != addSkuInfoReqVO.getProductSkuConfigDrafts() && addSkuInfoReqVO.getProductSkuConfigDrafts().size() > 0){
                List<ProductSkuConfigDraft> productSkuConfigDrafts = addSkuInfoReqVO.getProductSkuConfigDrafts();
                productSkuConfigDrafts.forEach(item->{
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                    item.setConfigStatus((byte)0);
                });
               // productSkuConfigService.insertDraftList(productSkuConfigDrafts);
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
            //sku质检信息
            if(CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuInspReportDrafts())) {
                List<ProductSkuInspReportDraft> productSkuInspReportDrafts = addSkuInfoReqVO.getProductSkuInspReportDrafts();
                productSkuInspReportDrafts.forEach(item->{
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                });
                productSkuInspReportService.insertDraftList(productSkuInspReportDrafts);
            }
            return 1;
        } else {
            return 0;
        }
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
        if(CollectionUtils.isNotEmpty(saveSkuApplyInfoReqVO.getProductCodes())){
            //过滤掉重复数据
            List<String> productCodes = saveSkuApplyInfoReqVO.getProductCodes().stream().distinct().collect(Collectors.toList());
            saveSkuApplyInfoReqVO.setProductCodes(productCodes);
            List<ApplyProduct> products = applyProductService.getProductApplyList(saveSkuApplyInfoReqVO.getProductCodes(),ApplyStatus.APPROVAL.getNumber());
            if(CollectionUtils.isNotEmpty(products)){
                products.forEach(o-> sb.append("商品编码为").append(o.getProductCode()).append("的商品已有在审核流程中的数据 "));
            }
        }
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
            throw new BizException(sb.append("提交失败").toString());
        }

        List<ProductSkuDraft> productSkuDrafts = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(saveSkuApplyInfoReqVO.getSkuCodes())){
            productSkuDrafts = productSkuDao.getSkuDraftByCodes(saveSkuApplyInfoReqVO.getSkuCodes());
        }
        String formNo = null;
        EncodingRule encodingRule = encodingRuleDao.getNumberingType("APPLY_PRODUCT_CODE");
        long code = encodingRule.getNumberingValue();
        List<ApplyProductDraft> productDrafts = new ArrayList<>();
        if (null != saveSkuApplyInfoReqVO.getProductCodes() && saveSkuApplyInfoReqVO.getProductCodes().size() > 0){
            productDrafts = productDao.getProductDrafts(saveSkuApplyInfoReqVO.getProductCodes());
            List<DraftSaveListReqVO> draftSaveListReqVOS = new ArrayList<>();
            ApplyDraftReqVO applyDraftReqVO = new ApplyDraftReqVO();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date startTime = null;
                Date endTime = null;
                if(null != saveSkuApplyInfoReqVO.getSelectionEffectiveStartTime() && !saveSkuApplyInfoReqVO.getSelectionEffectiveStartTime().equals("")){
                     startTime = format.parse(saveSkuApplyInfoReqVO.getSelectionEffectiveStartTime() +" 00:00:00");
                }
                applyDraftReqVO.setSelectionEffectiveEndTime(startTime);
                applyDraftReqVO.setSelectionEffectiveStartTime(endTime);
            } catch (ParseException exception){
                applyDraftReqVO.setSelectionEffectiveEndTime(null);
                applyDraftReqVO.setSelectionEffectiveStartTime(null);
            }
            applyDraftReqVO.setSelectionEffectiveTime(saveSkuApplyInfoReqVO.getSelectionEffectiveTime());
            productDrafts.forEach(item->{
                DraftSaveListReqVO draftSaveListReqVO = new DraftSaveListReqVO();
                BeanCopyUtils.copy(item,draftSaveListReqVO);
                draftSaveListReqVOS.add(draftSaveListReqVO);
            });
            if (productDrafts !=null && productDrafts.size() > 0){
                applyDraftReqVO.setDraftSaveListReqVO(draftSaveListReqVOS);
                formNo = applyProductService.saveList(applyDraftReqVO, code);
            }
        }
        List<ApplyProductSku> applyProductSkus = new ArrayList<>();
        for (int i = 0;i < productSkuDrafts.size();i++){
            ApplyProductSku applyProductSku = new ApplyProductSku();
            BeanCopyUtils.copy(productSkuDrafts.get(i),applyProductSku);
            applyProductSku.setApplyCode(String.valueOf(code));
//                applyProductSku.setSelectionEffectiveEndTime(saveSkuApplyInfoReqVO.getSelectionEffectiveEndTime());
//                applyProductSku.setSelectionEffectiveStartTime(saveSkuApplyInfoReqVO.getSelectionEffectiveStartTime());
            applyProductSku.setSelectionEffectiveTime(saveSkuApplyInfoReqVO.getSelectionEffectiveTime());
            applyProductSku.setApplyStatus((byte)1);
            applyProductSkus.add(applyProductSku);
        }
        if (applyProductSkus !=null && applyProductSkus.size() > 0){
            if (StringUtils.isBlank(formNo)){
                formNo = "SP"+ new IdSequenceUtils().nextId();
            }
            String finalFormNo = formNo;
            applyProductSkus.forEach(item->{
                item.setFormNo(finalFormNo);
            });
            //批量新增sku申请信息
            productSkuDao.insertApplySkuList(applyProductSkus);
            //删除sku草稿信息
            if (null != saveSkuApplyInfoReqVO.getSkuCodes() && saveSkuApplyInfoReqVO.getSkuCodes().size() > 0){
                productSkuDao.deleteSkuDraftByCodes(saveSkuApplyInfoReqVO.getSkuCodes());
            }
            //批量删除商品草稿信息
            if (null != saveSkuApplyInfoReqVO.getProductCodes() && saveSkuApplyInfoReqVO.getProductCodes().size() > 0){
                applyProductDraftService.deleteCode(saveSkuApplyInfoReqVO.getProductCodes());
            }
            //批量操作sku相关申请信息
            productSkuCheckoutService.saveApply(applyProductSkus);
            productSkuPicturesService.saveApplyList(applyProductSkus);
            productSkuPriceService.saveApplyList(applyProductSkus);
            productSkuPicDescService.saveApplyList(applyProductSkus);
            productSkuPurchaseInfoService.saveApplyList(applyProductSkus);
            productSkuDisInfoService.saveApplyList(applyProductSkus);
            productSkuSalesInfoService.saveApplyList(applyProductSkus);
            productSkuBoxPackingService.saveApplyList(applyProductSkus);
            productSkuSupplyUnitService.saveApplyList(applyProductSkus);
            productSkuManufacturerService.saveApplyList(applyProductSkus);
            productSkuFileService.saveApplyList(applyProductSkus);
            //productSkuConfigService.saveApplyList(applyProductSkus);
            productSkuInspReportService.saveApplyList(applyProductSkus);
        }
        //修改申请编码
        encodingRuleDao.updateNumberValue(Long.valueOf(code),encodingRule.getId());
        if (CollectionUtils.isNotEmpty(applyProductSkus) || CollectionUtils.isNotEmpty(productDrafts)){
            //调用审批接口
            workFlow(String.valueOf(code),formNo,applyProductSkus);
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
        //SKU渠道信息
        List<ProductSkuChannelRespVo> skuChannelRespVos = productSkuChannelService.getList(skuCode);
        detailResp.setProductSkuChannels(skuChannelRespVos);
        //SKU进销存信息
        List<PurchaseSaleStockRespVo> purchaseSaleStocks = Lists.newArrayList();
        //库存配置信息
        purchaseSaleStocks.addAll(productSkuStockInfoService.getDraftList(skuCode));
        //采购配置信息
        purchaseSaleStocks.addAll(productSkuPurchaseInfoService.getDraftList(skuCode));
        //门店销售
        purchaseSaleStocks.addAll(productSkuDisInfoService.getDraftList(skuCode));
        //销售
        purchaseSaleStocks.addAll(productSkuSalesInfoService.getDraftList(skuCode));
        detailResp.setPurchaseSaleStocks(purchaseSaleStocks);
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
        //sku图片及介绍
        detailResp.setProductSkuPictures(productSkuPicturesService.getDraftList(skuCode));
        //sku商品说明
        detailResp.setProductSkuPicDescs(productSkuPicDescService.getDraftList(skuCode));
        //sku文件管理
        detailResp.setProductSkuFiles(productSkuFileService.getDraftList(skuCode));
        //sku质检信息
        detailResp.setProductSkuInspReports(productSkuInspReportService.getDraftList(skuCode));
        return detailResp;
    }

    @Override
    public ProductSkuDetailResp getSkuDetail(String skuCode) {
        try {
            ProductSkuDetailResp productSkuDetailResp = new ProductSkuDetailResp();
//            ProductSkuInfo productSkuInfo = productSkuDao.getSkuInfo(skuCode);
//            if (null != productSkuInfo){
//                String categoryId = productSkuInfo.getProductCategoryCode();
//                List<ProductCategory> parentCategoryList = productCategoryService.getParentCategoryList(categoryId);
//                String productCategoryCode = "";
//                int length = parentCategoryList.size();
//                for (int i = length; i > 0; i--) {
//                    productCategoryCode+=parentCategoryList.get(i-1).getCategoryId()+",";
//                }
//                productCategoryCode+=categoryId;
//                productSkuInfo.setProductCategoryCode(productCategoryCode);
//                productSkuDetailResp.setProductSkuInfo(productSkuInfo);
//                ProductSkuCheckout productSkuCheckout = productSkuCheckoutDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuCheckout(productSkuCheckout);
//                List<ProductSkuPictures> productSkuPictures = productSkuPicturesDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuPictures(productSkuPictures);
//                List<ProductSkuPrice> productSkuPrices = productSkuPriceDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuPrices(productSkuPrices);
//                List<ProductSkuPicDesc> productSkuPicDescs = productSkuPicDescDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuPicDescs(productSkuPicDescs);
//                ProductSkuPurchaseInfo productSkuPurchaseInfo = productSkuPurchaseInfoDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuPurchaseInfo(productSkuPurchaseInfo);
//                ProductSkuDistributionInfo productSkuDistributionInfo = productSkuDisInfoDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuDistributionInfo(productSkuDistributionInfo);
//                ProductSkuBoxPacking productSkuBoxPacking = productSkuBoxPackingDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuBoxPacking(productSkuBoxPacking);
//                List<ProductSkuSalesInfo> productSkuSalesInfos = productSkuSalesInfoDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuSalesInfos(productSkuSalesInfos);
//                List<ProductSkuSupplyUnit> productSkuSupplyUnits = productSkuSupplyUnitDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuSupplyUnits(productSkuSupplyUnits);
//                List<ProductSkuManufacturer> productSkuManufacturers = productSkuManufacturerDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuManufacturers(productSkuManufacturers);
//                List<ProductSkuFile> productSkuFiles = productSkuFileDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuFiles(productSkuFiles);
//                List<ProductSkuConfig> productSkuConfigs = productSkuConfigDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuConfigs(productSkuConfigs);
//                List<ProductSkuInspReport> productSkuInspReports = productSkuInspReportDao.getInfo(skuCode);
//                productSkuDetailResp.setProductSkuInspReports(productSkuInspReports);
//                return productSkuDetailResp;
//            } else {
//              return productSkuDetailResp;
//            }
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
        return null;
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
    public ApplySkuDetailResp getSkuApplyDetail(String applyCode) {
        try {
            ApplySkuDetailResp applySkuDetailResp = new ApplySkuDetailResp();
            List<ApplyProductDetailsResponseVO> applyDetailProductListResps = applyProductService.getApplyProduct(applyCode);
            List<ApplyDetailSkuListResp> applyDetailSkuListResps = productSkuDao.getApplySkuList(applyCode);
            applySkuDetailResp.setApplyDetailProductListResps(applyDetailProductListResps);
            applySkuDetailResp.setApplyDetailSkuListResps(applyDetailSkuListResps);
            if (null != applyDetailProductListResps && applyDetailProductListResps.size() > 0){
                applySkuDetailResp.setApplyCode(applyCode);
                applySkuDetailResp.setFormNo(applyDetailProductListResps.get(0).getFormNo());
                applySkuDetailResp.setCreateBy(applyDetailProductListResps.get(0).getCreateBy());
                applySkuDetailResp.setCreateTime(applyDetailProductListResps.get(0).getCreateTime());
                applySkuDetailResp.setSelectionEffectiveTime(applyDetailProductListResps.get(0).getSelectionEffectiveTime());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTime = null;
                String endTime = null;
                if(null != applyDetailProductListResps.get(0).getSelectionEffectiveStartTime()){
                    endTime = format.format(applyDetailProductListResps.get(0).getSelectionEffectiveStartTime());
                }
                if(null != applyDetailProductListResps.get(0).getSelectionEffectiveEndTime()){
                    startTime = format.format(applyDetailProductListResps.get(0).getSelectionEffectiveStartTime());
                }
                applySkuDetailResp.setSelectionEffectiveEndTime(endTime);
                applySkuDetailResp.setSelectionEffectiveStartTime(startTime);
            }
            if(StringUtils.isBlank(applySkuDetailResp.getApplyCode())){
                if(CollectionUtils.isNotEmpty(applyDetailSkuListResps)){
                    applySkuDetailResp.setApplyCode(applyCode);
                    applySkuDetailResp.setCreateBy(applyDetailSkuListResps.get(0).getCreateBy());
                    applySkuDetailResp.setFormNo(applyDetailSkuListResps.get(0).getFormNo());
                    applySkuDetailResp.setCreateTime(applyDetailSkuListResps.get(0).getCreateTime());
                    applySkuDetailResp.setSelectionEffectiveTime(applyDetailSkuListResps.get(0).getSelectionEffectiveTime());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String startTime = null;
                    String endTime = null;
                    if(null != applyDetailSkuListResps.get(0).getSelectionEffectiveStartTime()){
                        //endTime = format.format(applyDetailSkuListResps.get(0).getSelectionEffectiveStartTime());
                        endTime = applyDetailSkuListResps.get(0).getSelectionEffectiveStartTime();
                    }
                    if(null != applyDetailSkuListResps.get(0).getSelectionEffectiveEndTime()){
                        //startTime = format.format(applyDetailSkuListResps.get(0).getSelectionEffectiveStartTime());
                        startTime = applyDetailSkuListResps.get(0).getSelectionEffectiveStartTime();
                    }
                    applySkuDetailResp.setSelectionEffectiveEndTime(endTime);
                    applySkuDetailResp.setSelectionEffectiveStartTime(startTime);
                }

            }
            return applySkuDetailResp;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public ApplyProductSkuDetailResp getProductSkuApplyDetail(String skuCode,String applyCode) {
        try {
            ApplyProductSkuDetailResp applyProductSkuDetailResp = new ApplyProductSkuDetailResp();
            ApplyProductSku applyProductSku = productSkuDao.getApply(skuCode,applyCode);
            if (applyProductSku != null){
                applyProductSkuDetailResp.setApplyProductSku(applyProductSku);
                ApplyProductSkuCheckout applyProductSkuCheckout = productSkuCheckoutDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuCheckout(applyProductSkuCheckout);
                List<ApplyProductSkuPictures> applyProductSkuPictures = productSkuPicturesDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuPictures(applyProductSkuPictures);
                List<ApplyProductSkuFile> applyProductSkuFiles = productSkuFileDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuFiles(applyProductSkuFiles);
                List<ApplyProductSkuSalesInfo> applyProductSkuSalesInfos = productSkuSalesInfoDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuSalesInfos(applyProductSkuSalesInfos);
                List<ApplyProductSkuPrice> applyProductSkuPrices = productSkuPriceDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuPrices(applyProductSkuPrices);
                List<ApplyProductSkuPicDesc> applyProductSkuPicDescs = productSkuPicDescDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuPicDescs(applyProductSkuPicDescs);
                ApplyProductSkuPurchaseInfo applyProductSkuPurchaseInfo = productSkuPurchaseInfoDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuPurchaseInfo(applyProductSkuPurchaseInfo);
                ApplyProductSkuDisInfo applyProductSkuDisInfo = productSkuDisInfoDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuDisInfo(applyProductSkuDisInfo);
                ApplyProductSkuBoxPacking applyProductSkuBoxPacking = productSkuBoxPackingDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setProductSkuBoxPacking(applyProductSkuBoxPacking);
                List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = productSkuSupplyUnitDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuSupplyUnits(applyProductSkuSupplyUnits);
                List<ApplyProductSkuManufacturer> applyProductSkuManufacturers = productSkuManufacturerDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuManufacturers(applyProductSkuManufacturers);
                //List<ApplyProductSkuConfig> applyProductSkuConfigs = productSkuConfigDao.getApply(skuCode);
                //applyProductSkuDetailResp.setApplyProductSkuConfigs(applyProductSkuConfigs);
                List<ApplyProductSkuInspReport> applyProductSkuInspReports = productSkuInspReportDao.getApply(skuCode,applyCode);
                applyProductSkuDetailResp.setApplyProductSkuInspReports(applyProductSkuInspReports);
            } else {
                throw new BizException("未查询出对应的sku信息");
            }
            return applyProductSkuDetailResp;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int cancelSkuApply(String applyCode) {
        try {
            List<ApplyProductSku> applyProductSkus = productSkuDao.getSkuApplyList(applyCode);
            for (int i = 0 ;i < applyProductSkus.size(); i++){
                applyProductSkus.get(i).setApplyStatus((byte)4);
                ((SkuInfoService) AopContext.currentProxy()).cancelApply(applyProductSkus.get(i));
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
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
    public void workFlow(String applyCode, String form, List<ApplyProductSku> applyProductSkus) {
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormUrl(workFlowBaseUrl.applySku+"?applyCode="+applyCode+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setFormNo(form);
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ WorkFlow.APPLY_GOODS.getNum());
            AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
            String personName = null != currentAuthToken.getPersonName() ? currentAuthToken.getPersonName() : "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTime= LocalDateTime.now().format(formatter);
            String title = personName+"在"+currentTime+","+WorkFlow.APPLY_GOODS.getTitle();
            workFlowVO.setTitle(title);
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_GOODS);
            if(workFlowRespVO.getSuccess()){
                List<ApplyProduct> applyProducts = applyProductMapper.getApplyCode(applyCode);
                if(CollectionUtils.isNotEmpty(applyProducts)){
                    applyProducts.forEach(applyProduct ->
                            {
                                applyProduct.setApplyStatus(HandlingExceptionCode.ONE);
                                applyProduct.setFormNo(workFlowVO.getFormNo());
                            }
                    );
                    int i = applyProductMapper.updateList(applyProducts);
                }
                if(CollectionUtils.isNotEmpty(applyProductSkus)){
                    //存日志
                     productCommonService.getInstance(applyProductSkus.get(0).getApplyCode()+1+"", HandleTypeCoce.ADD_PRODUCT_SKU.getStatus(), ObjectTypeCode.APPLY_SKU.getStatus(),applyProductSkus,HandleTypeCoce.ADD_PRODUCT_SKU.getName());
                }
            }else{
                //存调用失败的日志
                String msg = workFlowRespVO.getMsg();
                throw new BizException(msg);
            }
        } catch (BizException e) {
            //存失败日志
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public String skuWorkFlowCallback(WorkFlowCallbackVO vo1) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(vo1);
        List<ApplyProductSku> applyProductSkus = productSkuDao.getApplySkuByFormNo(vo.getFormNo());
        if(Objects.isNull(applyProductSkus) || applyProductSkus.size() < 1){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        if(null != applyProductSkus && applyProductSkus.size() > 0){
            //productSkuConfigService.saveList(applyProductSkus.get(0).getApplyCode());
            for (int i =0 ; i < applyProductSkus.size();i++){
                String skuCode = applyProductSkus.get(i).getSkuCode();
                String applyCode = applyProductSkus.get(i).getApplyCode();
                productSkuCheckoutService.saveInfo(skuCode,applyCode);
                productSkuBoxPackingService.saveInfo(skuCode,applyCode);
                productSkuDisInfoService.saveInfo(skuCode,applyCode);
                productSkuPicDescService.saveList(skuCode,applyCode);
                productSkuPicturesService.saveList(skuCode,applyCode);
                productSkuPurchaseInfoService.saveList(skuCode,applyCode);
                productSkuSupplyUnitService.saveList(skuCode,applyCode);
                productSkuPriceService.saveList(skuCode,applyCode);
                productSkuManufacturerService.saveList(skuCode,applyCode);
                productSkuInspReportService.saveList(skuCode,applyCode);
                productSkuFileService.saveList(skuCode,applyCode);
                productSkuSalesInfoService.saveList(skuCode,applyCode);
                applyProductSkus.get(i).setAuditorBy(vo.getApprovalUserName());
                applyProductSkus.get(i).setAuditorTime(new Date());
                if(applyProductSkus.get(i).getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
                    applyProductSkus.get(i).setApplyStatus(vo.getApplyStatus());
                    if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())){
                        //通过插入/更新正式数据
                        ProductSkuInfo productSkuInfo = new ProductSkuInfo();
                        BeanCopyUtils.copy(applyProductSkus.get(i),productSkuInfo);
                        ProductSkuInfo oldSku = productSkuDao.getSkuInfo(productSkuInfo.getSkuCode());
                        Byte handleTypeCoceStatus;
                        String handleTypeCoceName;
                        if (null != oldSku){
                            productSkuInfo.setId(oldSku.getId());
                            handleTypeCoceStatus = HandleTypeCoce.UPDATE_SKU.getStatus();
                            handleTypeCoceName = HandleTypeCoce.UPDATE_SKU.getName();
                            productSkuInfoMapper.updateByPrimaryKey(productSkuInfo);
                        } else {
                            productSkuInfo.setId(null);
                            handleTypeCoceStatus = HandleTypeCoce.ADD_SKU.getStatus();
                            handleTypeCoceName = HandleTypeCoce.ADD_SKU.getName();
                            productSkuInfoMapper.insert(productSkuInfo);
                        }
                        productCommonService.getInstance(productSkuInfo.getSkuCode(),handleTypeCoceStatus,ObjectTypeCode.APPLY_SKU.getStatus(),productSkuInfo,handleTypeCoceName);
                    } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
                        //驳回, 设置状态
                        applyProductSkus.get(i).setApplyStatus(vo.getApplyStatus());
                    } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
                        //传入的是审批中，继续该流程
                    } else {
                        return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
                    }
                }else{
                    return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
                }
                applyProductSkuMapper.updateByPrimaryKey(applyProductSkus.get(i));
                //判断审核状态，存日志信息
                HandleTypeCoce s = applyProductSkus.get(i).getApplyStatus().intValue()==ApplyStatus.APPROVAL_SUCCESS.getNumber()?HandleTypeCoce.APPLY_APPROVAL_SUCCESS_SKU:HandleTypeCoce.APPLY_APPROVAL_FAIL_SKU;
                productCommonService.getInstance(applyProductSkus.get(i).getSkuCode(),s.getStatus(),ObjectTypeCode.APPLY_SKU.getStatus(),applyProductSkus.get(i),s.getName());
            }
        } else{
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }

    /**
     * 获取商品临时数据
     *
     * @param companyCode
     * @return
     */
    @Override
    public List<ProductSkuDraftRespVo> getProductSkuDraftsByCompanyCode(String companyCode) {
        return productSkuDraftMapper.getProductSkuDraftByCompanyCode(companyCode);
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
        PageHelper.startPage(reqVO.getPageNo(),reqVO.getPageSize());
       List<QueryProductSaleAreaForSkuRespVO> list =  productSkuDao.selectSkuListForSaleArea(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(),list);
    }
}


