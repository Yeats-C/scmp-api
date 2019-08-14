package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.excel.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaForSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.*;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceProjectRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.supplierInfoVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordItemReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.util.excel.utils.StyleExcelHandler;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.aiqin.bms.scmp.api.util.GetChangeValueUtil.skuHeadMap;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 15:44
 */
@Service
@Slf4j
public class SkuInfoServiceImpl extends BaseServiceImpl implements SkuInfoService {
    @Autowired
    private ProductSkuDraftMapper productSkuDraftMapper;
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
    private ApplyProductSkuMapper applyProductSkuMapper;
    @Autowired
    private ProductCommonService productCommonService;
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
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private SupplyComService supplyCompanyService;
    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;
    @Autowired
    private ProductBrandService brandService;
    @Autowired
    private NewProductService newProductService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private PriceChannelService priceChannelService;
    @Autowired
    private DraftService draftService;
    @Autowired
    private PurchaseGroupService purchaseGroupService;
    @Autowired
    private ProductSkuSupplyUnitDraftMapper productSkuSupplyUnitDraftMapper;
    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO) {
        if (null != addSkuInfoReqVO && null != addSkuInfoReqVO.getProductSkuDraft()){
            //SKU基本信息
            ProductSkuDraft productSkuDraft = addSkuInfoReqVO.getProductSkuDraft();
            //判断SKU名称是否
            //临时表
            int count = productSkuDraftMapper.checkName(productSkuDraft.getSkuCode(), productSkuDraft.getSkuName());
            if(count > 0 ){
                throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU信息在申请表中已存在"));
            }
            //申请表
            count = applyProductSkuMapper.checkName(productSkuDraft.getSkuCode(), productSkuDraft.getSkuName());
            if(count > 0 ){
                throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU信息已经在审批中"));
            }
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
            //判断是否需要保存价格和配置信息(修改SKU不需要)
            Boolean flag = false;
            if (StringUtils.isNotBlank(productSkuDraft.getSkuCode())){
               if(Objects.equals(productSkuDraft.getApplyType(),StatusTypeCode.ADD_APPLY.getStatus())){
                   flag = false;
               } else {
                   flag = true;
               }
                if (flag){
                    productSkuDraft.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                    productSkuDraft.setApplyTypeName(StatusTypeCode.UPDATE_APPLY.getName());
                    //计算修改的内容
                    //获取旧的数据
                    ProductSkuDraft oldSku = productSkuDraftMapper.getOfficialBySkuCode(productSkuDraft.getSkuCode());
                    String compareResult = new GetChangeValueUtil<ProductSkuDraft>().compareResult(oldSku, productSkuDraft, skuHeadMap);
                    productSkuDraft.setChangeContent(compareResult);
                } else {
                    productSkuDraft.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                    productSkuDraft.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
                    productSkuDraft.setChangeContent("新增SKU");
                }
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
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());
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
                    applyUseTagRecord.setCreateBy(productSkuDraft.getCreateBy());
                    applyUseTagRecord.setUpdateBy(productSkuDraft.getUpdateBy());
                    applyUseTagRecord.setCreateTime(productSkuDraft.getCreateTime());
                    applyUseTagRecord.setUpdateTime(productSkuDraft.getUpdateTime());
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
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());
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
                    productSkuStockInfoDraft.setCreateBy(productSkuDraft.getCreateBy());
                    productSkuStockInfoDraft.setUpdateBy(productSkuDraft.getUpdateBy());
                    productSkuStockInfoDraft.setCreateTime(productSkuDraft.getCreateTime());
                    productSkuStockInfoDraft.setUpdateTime(productSkuDraft.getUpdateTime());
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
                    productSkuPurchaseInfoDraft.setCreateBy(productSkuDraft.getCreateBy());
                    productSkuPurchaseInfoDraft.setUpdateBy(productSkuDraft.getUpdateBy());
                    productSkuPurchaseInfoDraft.setCreateTime(productSkuDraft.getCreateTime());
                    productSkuPurchaseInfoDraft.setUpdateTime(productSkuDraft.getUpdateTime());
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
                //验证输入的条形码是否重复
                List<String> salesCodes = storeSaleList.stream().map(item -> item.getSalesCode()).distinct().collect(Collectors.toList());
                if (storeSaleList.size() != salesCodes.size()) {
                    throw new BizException(MessageId.create(Project.SCMP_API, 69, "门店销售的条形码有重复,请重新输入"));
                }
                //验证输入的条形码是否在数据库中存在
                List<String> salesCodeTmps = productSkuSalesInfoService.checkSalesCodes(salesCodes,productSkuDraft.getSkuCode());
                if(CollectionUtils.isNotEmpty(salesCodeTmps)){
                    throw new BizException(MessageId.create(Project.SCMP_API, 69,  "条形码["+StringUtils.join(salesCodeTmps, ",")+"],在数据库中存在相同条形码"));
                }
                try {
                    List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts = BeanCopyUtils.copyList(storeSaleList,ProductSkuSalesInfoDraft.class);
                    productSkuSalesInfoDrafts.forEach(item->{
                        item.setProductSkuCode(productSkuDraft.getSkuCode());
                        item.setProductSkuName(productSkuDraft.getSkuName());
                        item.setProductName(productSkuDraft.getProductName());
                        item.setProductCode(productSkuDraft.getProductCode());
                        item.setCreateBy(productSkuDraft.getCreateBy());
                        item.setUpdateBy(productSkuDraft.getUpdateBy());
                        item.setCreateTime(productSkuDraft.getCreateTime());
                        item.setUpdateTime(productSkuDraft.getUpdateTime());
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
                if (addSkuInfoReqVO.getBoxFlag() && CollectionUtils.isEmpty(addSkuInfoReqVO.getProductSkuBoxPackingDrafts())){
                    throw new BizException(ResultCode.BOX_PACKING_EMPTY);
                }
                List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts = addSkuInfoReqVO.getProductSkuBoxPackingDrafts();
                if(CollectionUtils.isNotEmpty(productSkuBoxPackingDrafts)){
                    productSkuBoxPackingDrafts.forEach(item->{
                        item.setProductSkuCode(productSkuDraft.getSkuCode());
                        item.setProductSkuName(productSkuDraft.getSkuName());
                        item.setCreateBy(productSkuDraft.getCreateBy());
                        item.setUpdateBy(productSkuDraft.getUpdateBy());
                        item.setCreateTime(productSkuDraft.getCreateTime());
                        item.setUpdateTime(productSkuDraft.getUpdateTime());
                    });
                    productSkuBoxPackingService.insertDraftList(productSkuBoxPackingDrafts);
                }
                //结算信息
                BigDecimal inputTaxRate = BigDecimal.ONE;
                Long inputTaxRateL = 100L;
                if (null != addSkuInfoReqVO.getProductSkuCheckoutDraft()) {
                    ProductSkuCheckoutDraft productSkuCheckoutDraft = addSkuInfoReqVO.getProductSkuCheckoutDraft();
                    productSkuCheckoutDraft.setSkuCode(productSkuDraft.getSkuCode());
                    productSkuCheckoutDraft.setSkuName(productSkuDraft.getSkuName());
                    productSkuCheckoutDraft.setCreateBy(productSkuDraft.getCreateBy());
                    productSkuCheckoutDraft.setUpdateBy(productSkuDraft.getUpdateBy());
                    productSkuCheckoutDraft.setCreateTime(productSkuDraft.getCreateTime());
                    productSkuCheckoutDraft.setUpdateTime(productSkuDraft.getUpdateTime());
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
                            item.setCreateBy(productSkuDraft.getCreateBy());
                            item.setUpdateBy(productSkuDraft.getUpdateBy());
                            item.setCreateTime(productSkuDraft.getCreateTime());
                            item.setUpdateTime(productSkuDraft.getUpdateTime());
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
                                    item2.setCreateBy(productSkuDraft.getCreateBy());
                                    item2.setUpdateBy(productSkuDraft.getUpdateBy());
                                    item2.setCreateTime(productSkuDraft.getCreateTime());
                                    item2.setUpdateTime(productSkuDraft.getUpdateTime());
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
                            skuPriceDraftReqVO.setCreateBy(productSkuDraft.getCreateBy());
                            skuPriceDraftReqVO.setUpdateBy(productSkuDraft.getUpdateBy());
                            skuPriceDraftReqVO.setCreateTime(productSkuDraft.getCreateTime());
                            skuPriceDraftReqVO.setUpdateTime(productSkuDraft.getUpdateTime());
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
                        item.setCreateBy(productSkuDraft.getCreateBy());
                        item.setUpdateBy(productSkuDraft.getUpdateBy());
                        item.setCreateTime(productSkuDraft.getCreateTime());
                        item.setUpdateTime(productSkuDraft.getUpdateTime());
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
                        item.setCreateBy(productSkuDraft.getCreateBy());
                        item.setUpdateBy(productSkuDraft.getUpdateBy());
                        item.setCreateTime(productSkuDraft.getCreateTime());
                        item.setUpdateTime(productSkuDraft.getUpdateTime());
                    });
                    productSkuManufacturerService.insertDraftList(productSkuManufacturerDrafts);
                }
                //sku质检信息
                if(CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuInspReportDrafts())) {
                    List<ProductSkuInspReportDraft> productSkuInspReportDrafts = addSkuInfoReqVO.getProductSkuInspReportDrafts();
                    productSkuInspReportDrafts.forEach(item->{
                        item.setSkuCode(productSkuDraft.getSkuCode());
                        item.setSkuName(productSkuDraft.getSkuName());
                        item.setCreateBy(productSkuDraft.getCreateBy());
                        item.setUpdateBy(productSkuDraft.getUpdateBy());
                        item.setCreateTime(productSkuDraft.getCreateTime());
                        item.setUpdateTime(productSkuDraft.getUpdateTime());
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
                productSkuDisInfoDraft.setCreateBy(productSkuDraft.getCreateBy());
                productSkuDisInfoDraft.setUpdateBy(productSkuDraft.getUpdateBy());
                productSkuDisInfoDraft.setCreateTime(productSkuDraft.getCreateTime());
                productSkuDisInfoDraft.setUpdateTime(productSkuDraft.getUpdateTime());
                productSkuDisInfoService.insertDraft(productSkuDisInfoDraft);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
            }
            if (!flag) {
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
                        item.setCreateBy(productSkuDraft.getCreateBy());
                        item.setUpdateBy(productSkuDraft.getUpdateBy());
                        item.setCreateTime(productSkuDraft.getCreateTime());
                        item.setUpdateTime(productSkuDraft.getUpdateTime());
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
            }
            //sku图片及介绍
            if (CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuPicturesDrafts())){
                List<ProductSkuPicturesDraft> productSkuPicturesDrafts = addSkuInfoReqVO.getProductSkuPicturesDrafts();
                productSkuPicturesDrafts.forEach(item->{
                    item.setProductSkuCode(productSkuDraft.getSkuCode());
                    item.setProductSkuName(productSkuDraft.getSkuName());
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());
                });
                productSkuPicturesService.insertDraftList(productSkuPicturesDrafts);
            }
            //sku商品说明
            if (null != addSkuInfoReqVO.getProductSkuPicDescDrafts() && addSkuInfoReqVO.getProductSkuPicDescDrafts().size() > 0){
                List<ProductSkuPicDescDraft> productSkuPicDescDrafts = addSkuInfoReqVO.getProductSkuPicDescDrafts();
                productSkuPicDescDrafts.forEach(item-> {
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());
                });
                productSkuPicDescService.insertDraftList(productSkuPicDescDrafts);
            }
            //sku文件管理
            if (null != addSkuInfoReqVO.getProductSkuFileDrafts() && addSkuInfoReqVO.getProductSkuFileDrafts().size() > 0){
                List<ProductSkuFileDraft> productSkuFileDrafts = addSkuInfoReqVO.getProductSkuFileDrafts();
                productSkuFileDrafts.forEach(item->{
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());
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
        ProductSkuRespVo skuRespVo = productSkuDao.getSkuDraft(addSkuInfoReqVO.getProductSkuDraft().getSkuCode());
        if (null == skuRespVo) {
            throw new BizException(ResultCode.SKU_CODE_EMPTY);
        }
        addSkuInfoReqVO.getProductSkuDraft().setApplyType(skuRespVo.getApplyType());
        addSkuInfoReqVO.getProductSkuDraft().setApplyTypeName(skuRespVo.getApplyTypeName());
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
        String formNo =  "SP"+ IdSequenceUtils.getInstance().nextId();
        EncodingRule encodingRule = encodingRuleDao.getNumberingType("APPLY_PRODUCT_CODE");
        long code = encodingRule.getNumberingValue();
        List<ApplyProductSku> applyProductSkus = BeanCopyUtils.copyList(productSkuDrafts, ApplyProductSku.class);
        Date currentDate = new Date();
        String applyBy = getUser().getPersonName();
        applyProductSkus.forEach(item->{
            item.setApplyCode(String.valueOf(code));
            item.setSelectionEffectiveTime(saveSkuApplyInfoReqVO.getSelectionEffectiveTime());
            item.setSelectionEffectiveStartTime(saveSkuApplyInfoReqVO.getSelectionEffectiveStartTime());
            item.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
            item.setUpdateBy(applyBy);
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
        //查询所有父节点
        List<ProductCategory> parentCategoryList = productCategoryService.getParentCategoryList(skuRespVo.getProductCategoryCode());
        List<String> categoryIds = parentCategoryList.stream().map(ProductCategory :: getCategoryId).sorted().collect(Collectors.toList());
        categoryIds.add(skuRespVo.getProductCategoryCode());
        skuRespVo.setProductCategoryCodes(categoryIds);
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
        detailResp.setPurchaseSaleStocks(purchaseSaleStocks.stream().sorted(Comparator.comparing(PurchaseSaleStockRespVo :: getType)).collect(Collectors.toList()));
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
        //查询所有父节点
        List<ProductCategory> parentCategoryList = productCategoryService.getParentCategoryList(skuRespVo.getProductCategoryCode());
        List<String> categoryIds = parentCategoryList.stream().map(ProductCategory :: getCategoryId).sorted().collect(Collectors.toList());
        categoryIds.add(skuRespVo.getProductCategoryCode());
        skuRespVo.setProductCategoryCodes(categoryIds);
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
        //进销存排序
        detailResp.setPurchaseSaleStocks(purchaseSaleStocks.stream().sorted(Comparator.comparing(PurchaseSaleStockRespVo :: getType)).collect(Collectors.toList()));
        //sku图片及介绍
        detailResp.setProductSkuPictures(productSkuPicturesService.getList(skuCode));
        //sku商品说明
        detailResp.setProductSkuPicDescs(productSkuPicDescService.getList(skuCode));
        //sku文件管理
        detailResp.setProductSkuFiles(productSkuFileService.getList(skuCode));
        //价格信息
        List<ProductSkuPriceRespVo> productSkuPriceInfosTemp =
                productSkuPriceInfoService.getSkuPriceBySkuCodeForOfficial(skuCode);
        List<ProductSkuPriceRespVo>  productSkuPriceInfos =
        productSkuPriceInfosTemp.stream().filter(item ->
                        !Objects.equals(item.getPriceTypeCode(), PriceTypeEnum.PURCHASE.getTypeCode())).
                        collect(Collectors.toList());
        detailResp.setProductSkuPrices(productSkuPriceInfos);
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
                querySkuListReqVO.setPersonId(authToken.getPersonId());
            }
            if(CollectionUtils.isNotEmpty(querySkuListReqVO.getProductCategoryCodes())){
                try {
                    querySkuListReqVO.setProductCategoryLv1Code(querySkuListReqVO.getProductCategoryCodes().get(0));
                    querySkuListReqVO.setProductCategoryLv2Code(querySkuListReqVO.getProductCategoryCodes().get(1));
                    querySkuListReqVO.setProductCategoryLv3Code(querySkuListReqVO.getProductCategoryCodes().get(2));
                    querySkuListReqVO.setProductCategoryLv4Code(querySkuListReqVO.getProductCategoryCodes().get(3));
                } catch (Exception e) {
                    log.info("不做处理,让程序继续执行下去");
                }
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
        //查询所有父节点
        List<ProductCategory> parentCategoryList = productCategoryService.getParentCategoryList(skuRespVo.getProductCategoryCode());
        List<String> categoryIds = parentCategoryList.stream().map(ProductCategory :: getCategoryId).sorted().collect(Collectors.toList());
        categoryIds.add(skuRespVo.getProductCategoryCode());
        skuRespVo.setProductCategoryCodes(categoryIds);
        detailResp.setProductSkuInfo(skuRespVo);
        //标签信息
        List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCodeAndUseObjectCode(applyCode,TagTypeCode.SKU.getStatus(),skuCode);
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
        detailResp.setPurchaseSaleStocks(purchaseSaleStocks.stream().sorted(Comparator.comparing(PurchaseSaleStockRespVo :: getType)).collect(Collectors.toList()));
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
        if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }
        List<ApplyProductSku> applyProductSkus = productSkuDao.getApplySkuByFormNo(vo.getFormNo());
        if(CollectionUtils.isEmpty(applyProductSkus)){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        String applyCode = applyProductSkus.get(0).getApplyCode();
        Byte applyStatus = applyProductSkus.get(0).getApplyStatus();
        //判断查出来的是否是在审批中的数据
        if(!ApplyStatus.APPROVAL.getNumber().equals(applyStatus)){
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常，不是在审批中的数据！"));
        }
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
            List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCodeAndUseObjectCode(applyCode,TagTypeCode.SKU.getStatus(),skuCode);
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
            if(null == oldSku){
                //配置
                productSkuConfigService.saveList(vo,skuCode,applyCode);
            }
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
            Date current = new Date();
            productSkuPriceInfos.forEach(item->{
                if(null == item.getEffectiveTimeStart()){
                    item.setEffectiveTimeStart(current);
                }
            });
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
        List<ProductSkuDraftRespVo> productSkuDraftByCompanyCode = productSkuDraftMapper.getProductSkuDraftByCompanyCode(companyCode, personId);
        return productSkuDraftByCompanyCode;
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
     * 批量删除草稿信息
     *
     * @param skuCodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteProductSkuDraftForPlatform(List<String> skuCodes) {
        int deleteNum = productSkuDao.deleteSkuDraftByCodes(skuCodes);
        if (deleteNum == 0 ) {
            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
        }
        productSkuPurchaseInfoService.deleteDrafts(skuCodes);
        productSkuStockInfoService.deleteDrafts(skuCodes);
        productSkuDisInfoService.deleteDrafts(skuCodes);
        productSkuSalesInfoService.deleteDrafts(skuCodes);
        productSkuBoxPackingService.deleteDrafts(skuCodes);
        productSkuChannelService.deleteDrafts(skuCodes);
        productSkuCheckoutService.deleteDrafts(skuCodes);
        applyUseTagRecordService.deletes(skuCodes);
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
        if(CollectionUtils.isNotEmpty(vo.getProductCategoryCodes())){
            try {
                vo.setProductCategoryLv1Code(vo.getProductCategoryCodes().get(0));
                vo.setProductCategoryLv2Code(vo.getProductCategoryCodes().get(1));
                vo.setProductCategoryLv3Code(vo.getProductCategoryCodes().get(2));
                vo.setProductCategoryLv4Code(vo.getProductCategoryCodes().get(3));
            } catch (Exception e) {
                log.info("不做处理,让程序继续执行下去");
            }
        }
        List<Long> longs = getSkuListByQueryNoPageCount(vo);
        if(CollectionUtils.isEmpty(longs)){
            return PageUtil.getPageList(vo.getPageNo(), Lists.newArrayList());
        }
        List<Long> longs1 = PageUtil.myPage(longs, vo);
        List<QuerySkuInfoRespVO> respVos = Lists.newArrayList();
        if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(vo.getChangePriceType())){
            respVos = productSkuDao.selectSkuListForPurchasePrice(longs1);
            //采购变价需要查询渠道价
            respVos = dealPurchasePriceSkuData(respVos);
        } else if(CommonConstant.SALE_PRICE.contains(vo.getChangePriceType())){
            if(CommonConstant.FOREVER_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
            }else if(CommonConstant.TEMP_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
            }
            respVos = productSkuDao.selectSkuListForSalePrice(longs1,vo.getChangePriceType());
        }else {
            throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        return PageUtil.getPageList(vo.getPageNo(),vo.getPageSize(),longs.size(),respVos);
    }

    /**
     *
     * @param respVos
     * @return
     */
    private List<QuerySkuInfoRespVO> dealPurchasePriceSkuData(List<QuerySkuInfoRespVO> respVos) {
        Map<String,ProductSkuPriceInfo> priceMap = productSkuPriceInfoMapper.selectChannelPriceBySkuCode(respVos);
        for (QuerySkuInfoRespVO respVo : respVos) {
            ProductSkuPriceInfo productSkuPriceInfo = priceMap.get(respVo.getSkuCode());
            Long price = 0L;
            if(Objects.nonNull(productSkuPriceInfo)){
                price = productSkuPriceInfo.getPriceTax();
            }
            List<supplierInfoVO> supplierInfoVOS = respVo.getSupplierInfoVOS();
            for (supplierInfoVO supplierInfoVO : supplierInfoVOS) {
                supplierInfoVO.setTaxCost(price);
            }
            respVo.setSupplierInfoVOS(supplierInfoVOS);
        }

        return respVos;
    }

    @Override
    public List<Long> getSkuListByQueryNoPageCount(QuerySkuInfoReqVO vo){
        if(StringUtils.isBlank(vo.getChangePriceType())){
            throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        List<Long> list = Lists.newArrayList();
        if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(vo.getChangePriceType())){
            list = productSkuDao.selectSkuListForPurchasePriceCount(vo);
        } else if(CommonConstant.SALE_PRICE.contains(vo.getChangePriceType())){
            if(CommonConstant.FOREVER_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
            }else if(CommonConstant.TEMP_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
            }
            list = productSkuDao.selectSkuListForSalePriceCount(vo);
        }else {
            throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        return list;
    }

    @Override
    public List<QuerySkuInfoRespVO> getSkuListByQueryNoPage(QuerySkuInfoReqVO vo){
        if(StringUtils.isBlank(vo.getChangePriceType())){
            throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        List<Long> ids = getSkuListByQueryNoPageCount(vo);
        List<QuerySkuInfoRespVO> list = Lists.newArrayList();
        if(CollectionUtils.isEmpty(ids)){
            return list;
        }
        if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(vo.getChangePriceType())){
            list = productSkuDao.selectSkuListForPurchasePrice(ids);
        } else if(CommonConstant.SALE_PRICE.contains(vo.getChangePriceType())){
            if(CommonConstant.FOREVER_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
            }else if(CommonConstant.TEMP_PRICE.contains(vo.getChangePriceType())){
                vo.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
            }
            list = productSkuDao.selectSkuListForSalePrice(ids,vo.getChangePriceType());
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

    @Override
    public SkuImportMain importSkuNew(MultipartFile file) {
        try {
            List<SkuInfoImportNew> skuInfoImports = ExcelUtil.readExcel(file, SkuInfoImportNew.class, 1, 0);
            dataValidation(skuInfoImports);
            skuInfoImports = skuInfoImports.subList(2, skuInfoImports.size());
            //取数据
            //sku名称
            Set<String> skuNameList = Sets.newHashSet();
            //供应商名称
            Set<String> supplierList = Sets.newHashSet();
            //spu名称
            Set<String> spuName = Sets.newHashSet();
            //品牌
            Set<String> brandNameList = Sets.newHashSet();
            //品类
            Set<String> categoryNameList = Sets.newHashSet();
            //字典数据
            List<String> dicNameList = Lists.newArrayList();
            //渠道信息
            Set<String> channelList = Sets.newHashSet();
            //标签信息
            Set<String> skuTagList = Sets.newHashSet();
            //厂家制造商
            Set<String> manufactureList = Sets.newHashSet();
            skuInfoImports.forEach(o -> {
                skuNameList.add(o.getSkuName());
                brandNameList.add(o.getProductBrandName());
                supplierList.add(o.getSupplyUnitName());
                if (StringUtils.isNotBlank(o.getProductCategoryName())) {
                    categoryNameList.addAll(Arrays.asList(o.getProductCategoryName().split("-")));
                }
                if (StringUtils.isNotBlank(o.getPriceChannelName())) {
                    channelList.addAll(Arrays.asList(o.getPriceChannelName().split(",")));
                }
                if (StringUtils.isNotBlank(o.getTagName())) {
                    skuTagList.addAll(Arrays.asList(o.getTagName().split(",")));
                }
                if (StringUtils.isNotBlank(o.getManufacturerName())) {
                    manufactureList.add(o.getManufacturerName());
                }
                if (StringUtils.isNotBlank(o.getProductName())) {
                    spuName.add(o.getProductName());
                }

            });
            Map<String, ProductSkuInfo> productSkuMap = Maps.newHashMap();
            Map<String, SupplyCompany> supplyCompanyMap = Maps.newHashMap();
            Map<String, ProductBrandType> brandMap = Maps.newHashMap();
            List<ProductCategory> categoryList = Lists.newArrayList();
            Map<String,ProductCategory> categoryMap = Maps.newHashMap();
            Map<String, PriceChannel> channelMap = Maps.newHashMap();
            Map<String, TagInfo> skuTagMap = Maps.newHashMap();
            Map<String, NewProduct> spuMap = Maps.newHashMap();
            Map<String, Manufacturer> manufactureMap = Maps.newHashMap();
            //sku信息
            if (CollectionUtils.isNotEmpty(skuNameList)) {
                productSkuMap = selectBySkuNames(skuNameList, getUser().getCompanyCode());
            }
            //供应商信息
            if (CollectionUtils.isNotEmpty(supplierList)) {
                supplyCompanyMap = supplyCompanyService.selectBySupplyComNames(supplierList, getUser().getCompanyCode());
            }
            //品牌
            if (CollectionUtils.isNotEmpty(brandNameList)) {
                brandMap = brandService.selectByBrandNames(brandNameList, getUser().getCompanyCode());
            }
            //品牌
            if (CollectionUtils.isNotEmpty(spuName)) {
                spuMap = newProductService.selectBySpuName(spuName, getUser().getCompanyCode());
            }
            //品类
            if (CollectionUtils.isNotEmpty(categoryNameList)) {
                categoryList = productCategoryService.selectByCategoryNames(categoryNameList, getUser().getCompanyCode());
                categoryMap = categoryList.stream().collect(Collectors.toMap(o->o.getCategoryName()+","+o.getCategoryLevel(), Function.identity()));
            }
            //制造商
            if (CollectionUtils.isNotEmpty(manufactureList)) {
                manufactureMap = manufacturerService.selectByManufactureNames(manufactureList, getUser().getCompanyCode());
            }
            //渠道
            if (CollectionUtils.isNotEmpty(channelList)) {
                channelMap = priceChannelService.selectByChannelNames(channelList, getUser().getCompanyCode());
            }
            //商品标签
            if (CollectionUtils.isNotEmpty(skuTagList)) {
                skuTagMap = tagInfoService.selectByTagNames(skuTagList, getUser().getCompanyCode());
            }
            dicNameList.add("供货渠道类别");
            dicNameList.add("商品属性");
            dicNameList.add("商品类别");
            dicNameList.add("结算方式");
            dicNameList.add("单位");
            dicNameList.add("仓位类型");
            Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(dicNameList, getUser().getCompanyCode());
            List<AddSkuInfoReqVO> skuInfoList = Lists.newArrayList();
            List<SkuInfoImport> importList = Lists.newArrayList();
            Map<String, String> reaptMap = Maps.newHashMap();
            for (int i = 0; i < skuInfoImports.size(); i++) {
                //检查信息
                CheckSku checkSku = new CheckSku(productSkuMap, supplyCompanyMap, brandMap , categoryMap, channelMap, skuTagMap, reaptMap, skuInfoImports.get(i),spuMap,dicMap,manufactureMap)
                        .checkRepeat() //检查重复
                        .checkSKuNew() //新增检查sku
                        .checkBaseDate() //检查基础数据
                        .checkInvoice() //检查进销存包装
                        .checkSettlement() //检查结算信息
                        .checkSupplier() //检查供应商
                        .checkPrice() //检查价格
                        .checkConfig() //检查配置
                        .checkManufacturer() //检查厂家
                        .checkPic();//检查图片
                //返回实体
                AddSkuInfoReqVO resp = checkSku.getRespVO();
                SkuInfoImport skuInfoImport = checkSku.getSkuInfoImport();
                String error = skuInfoImport.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s = "第" + (i + 3) + "行 " + error;
                    skuInfoImport.setError(s);
                }
                skuInfoList.add(resp);
                importList.add(skuInfoImport);
                reaptMap = checkSku.getReapMap();
                spuMap = checkSku.getSpuMap();
            }
            return new SkuImportMain(skuInfoList,importList);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }
    @Override
    public SkuImportMain importSkuForSupplyPlatform(MultipartFile file) {
        try {
            List<ExportSkuInfo> skuInfoImports = ExcelUtil.readExcel(file, ExportSkuInfo.class, 1, 0);
            dataValidation3(skuInfoImports);
            skuInfoImports = skuInfoImports.subList(2, skuInfoImports.size());
            //取数据
            //sku名称
            Set<String> skuNameList = Sets.newHashSet();
            //sku名称
            Set<String> purchaseGroupList = Sets.newHashSet();
            //供应商名称
            Set<String> supplierList = Sets.newHashSet();
            //spu名称
            Set<String> spuName = Sets.newHashSet();
            //品牌
            Set<String> brandNameList = Sets.newHashSet();
            //品类
            Set<String> categoryNameList = Sets.newHashSet();
            //字典数据
            List<String> dicNameList = Lists.newArrayList();
            //渠道信息
            Set<String> channelList = Sets.newHashSet();
            //标签信息
            Set<String> skuTagList = Sets.newHashSet();
            //厂家制造商
            Set<String> manufactureList = Sets.newHashSet();
            skuInfoImports.forEach(o -> {
                skuNameList.add(o.getSkuCode());
                brandNameList.add(o.getProductBrandName());
                supplierList.add(o.getSupplyUnitName());
                if (StringUtils.isNotBlank(o.getProductCategoryName())) {
                    categoryNameList.addAll(Arrays.asList(o.getProductCategoryName().split("-")));
                }
                if (StringUtils.isNotBlank(o.getPriceChannelName())) {
                    channelList.addAll(Arrays.asList(o.getPriceChannelName().split(",")));
                }
                if (StringUtils.isNotBlank(o.getTagName())) {
                    skuTagList.addAll(Arrays.asList(o.getTagName().split(",")));
                }
//                if (StringUtils.isNotBlank(o.getManufacturerName())) {
//                    manufactureList.add(o.getManufacturerName());
//                }
                if (StringUtils.isNotBlank(o.getProductName())) {
                    spuName.add(o.getProductName());
                }
                if (StringUtils.isNotBlank(o.getProductName())) {
                    purchaseGroupList.add(o.getProcurementSectionName());
                }

            });
            Map<String, ProductSkuInfo> productSkuMap = Maps.newHashMap();
            Map<String, ProductSkuDraft> productSkuDraftMap = Maps.newHashMap();
            Map<String, SupplyCompany> supplyCompanyMap = Maps.newHashMap();
            Map<String, ProductBrandType> brandMap = Maps.newHashMap();
            List<ProductCategory> categoryList = Lists.newArrayList();
            Map<String,ProductCategory> categoryMap = Maps.newHashMap();
            Map<String, PriceChannel> channelMap = Maps.newHashMap();
            Map<String, TagInfo> skuTagMap = Maps.newHashMap();
            Map<String, NewProduct> spuMap = Maps.newHashMap();
            Map<String, Manufacturer> manufactureMap = Maps.newHashMap();
            Map<String, PurchaseGroupDTO> purchaseGroupMap = Maps.newHashMap();
            //sku信息
            if (CollectionUtils.isNotEmpty(skuNameList)) {
                productSkuMap = selectBySkuCodes(skuNameList, getUser().getCompanyCode());
                productSkuDraftMap = draftService.selectBySkuCode(skuNameList, getUser().getCompanyCode());
            }
            //供应商信息
            if (CollectionUtils.isNotEmpty(supplierList)) {
                supplyCompanyMap = supplyCompanyService.selectBySupplyComNames(supplierList, getUser().getCompanyCode());
            }
            //品牌
            if (CollectionUtils.isNotEmpty(brandNameList)) {
                brandMap = brandService.selectByBrandNames(brandNameList, getUser().getCompanyCode());
            }
            //品牌
            if (CollectionUtils.isNotEmpty(spuName)) {
                spuMap = newProductService.selectBySpuName(spuName, getUser().getCompanyCode());
            }
            //品类
            if (CollectionUtils.isNotEmpty(categoryNameList)) {
                categoryList = productCategoryService.selectByCategoryNames(categoryNameList, getUser().getCompanyCode());
                categoryMap = categoryList.stream().collect(Collectors.toMap(o->o.getCategoryName()+","+o.getCategoryLevel(), Function.identity()));
            }
            //制造商
            if (CollectionUtils.isNotEmpty(manufactureList)) {
                manufactureMap = manufacturerService.selectByManufactureNames(manufactureList, getUser().getCompanyCode());
            }
            //渠道
            if (CollectionUtils.isNotEmpty(channelList)) {
                channelMap = priceChannelService.selectByChannelNames(channelList, getUser().getCompanyCode());
            }
            //商品标签
            if (CollectionUtils.isNotEmpty(skuTagList)) {
                skuTagMap = tagInfoService.selectByTagNames(skuTagList, getUser().getCompanyCode());
            }
            //采购组
            if (CollectionUtils.isNotEmpty(purchaseGroupList)) {
                purchaseGroupMap = purchaseGroupService.selectByNames(purchaseGroupList, getUser().getCompanyCode());
            }
            dicNameList.add("供货渠道类别");
            dicNameList.add("商品属性");
            dicNameList.add("商品类别");
            dicNameList.add("结算方式");
            dicNameList.add("单位");
            dicNameList.add("仓位类型");
            Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(dicNameList, getUser().getCompanyCode());
            List<AddSkuInfoReqVO> skuInfoList = Lists.newArrayList();
            List<SkuInfoImport> importList = Lists.newArrayList();
            Map<String, String> reaptMap = Maps.newHashMap();
            for (int i = 0; i < skuInfoImports.size(); i++) {
                //检查信息
                CheckSku checkSku = new CheckSku(productSkuMap, supplyCompanyMap, brandMap , categoryMap, channelMap, skuTagMap, reaptMap, skuInfoImports.get(i),spuMap,dicMap,productSkuDraftMap,purchaseGroupMap)
                        .checkRepeat() //检查重复
                        .checkSKuUpdate1() //修改检查sku
                        .checkBaseDate() //检查基础数据
                        .checkPurchaseGroup()//检查采购组
                        .checkInvoice() //检查进销存包装
                        .checkSettlement() //检查结算信息
                        .checkSupplier2(); //检查供应商
//                        .checkPrice() //检查价格
//                        .checkConfig() //检查配置
//                        .checkManufacturer() //检查厂家
//                        .checkPic();//检查图片
                //返回实体
                AddSkuInfoReqVO resp = checkSku.getRespVO();
                skuInfoList.add(resp);
                SkuInfoImport skuInfoImport = checkSku.getSkuInfoImport();
                String error = skuInfoImport.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s = "第" + (i + 3) + "行 " + error;
                    skuInfoImport.setError(s);
                }
                importList.add(skuInfoImport);
                reaptMap = checkSku.getReapMap();
                spuMap = checkSku.getSpuMap();
            }
            return new SkuImportMain(skuInfoList,importList);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }

    @Override
    public Map<String, ProductSkuInfo> selectBySkuNames(Set<String> skuNameList, String companyCode) {
        return productSkuInfoMapper.selectBySkuNames(skuNameList,companyCode);
    }

    @Override
    public SkuImportMain importSkuUpdate(MultipartFile file) {
        try {
            List<SkuInfoImportUpdate> skuInfoImports = ExcelUtil.readExcel(file, SkuInfoImportUpdate.class, 1, 0);
            dataValidation2(skuInfoImports);
            skuInfoImports = skuInfoImports.subList(2, skuInfoImports.size());
            //取数据
            //sku编码
            Set<String> skuNameList = Sets.newHashSet();
            //供应商名称
            Set<String> supplierList = Sets.newHashSet();
            //spu名称
            Set<String> spuName = Sets.newHashSet();
            //品牌
            Set<String> brandNameList = Sets.newHashSet();
            //品类
            Set<String> categoryNameList = Sets.newHashSet();
            //字典数据
            List<String> dicNameList = Lists.newArrayList();
            //渠道信息
            Set<String> channelList = Sets.newHashSet();
            //标签信息
            Set<String> skuTagList = Sets.newHashSet();
            //厂家制造商
            Set<String> manufactureList = Sets.newHashSet();
            skuInfoImports.forEach(o -> {
                skuNameList.add(o.getSkuCode());
                brandNameList.add(o.getProductBrandName());
                if (StringUtils.isNotBlank(o.getProductCategoryName())) {
                    categoryNameList.addAll(Arrays.asList(o.getProductCategoryName().split("-")));
                }
                if (StringUtils.isNotBlank(o.getPriceChannelName())) {
                    channelList.addAll(Arrays.asList(o.getPriceChannelName().split(",")));
                }
                if (StringUtils.isNotBlank(o.getTagName())) {
                    skuTagList.addAll(Arrays.asList(o.getTagName().split(",")));
                }
                if (StringUtils.isNotBlank(o.getManufacturerName())) {
                    manufactureList.add(o.getManufacturerName());
                }
                if (StringUtils.isNotBlank(o.getProductName())) {
                    spuName.add(o.getProductName());
                }

            });
            Map<String, ProductSkuInfo> productSkuMap = Maps.newHashMap();
            Map<String, SupplyCompany> supplyCompanyMap = Maps.newHashMap();
            Map<String, ProductBrandType> brandMap = Maps.newHashMap();
            List<ProductCategory> categoryList = Lists.newArrayList();
            Map<String,ProductCategory> categoryMap = Maps.newHashMap();
            Map<String, PriceChannel> channelMap = Maps.newHashMap();
            Map<String, TagInfo> skuTagMap = Maps.newHashMap();
            Map<String, NewProduct> spuMap = Maps.newHashMap();
            Map<String, Manufacturer> manufactureMap = Maps.newHashMap();
            //sku信息
            if (CollectionUtils.isNotEmpty(skuNameList)) {
                productSkuMap = selectBySkuCodes(skuNameList, getUser().getCompanyCode());
            }
            //供应商信息
            if (CollectionUtils.isNotEmpty(supplierList)) {
                supplyCompanyMap = supplyCompanyService.selectBySupplyComNames(supplierList, getUser().getCompanyCode());
            }
            //品牌
            if (CollectionUtils.isNotEmpty(brandNameList)) {
                brandMap = brandService.selectByBrandNames(brandNameList, getUser().getCompanyCode());
            }
            //品牌
            if (CollectionUtils.isNotEmpty(spuName)) {
                spuMap = newProductService.selectBySpuName(spuName, getUser().getCompanyCode());
            }
            //品类
            if (CollectionUtils.isNotEmpty(categoryNameList)) {
                categoryList = productCategoryService.selectByCategoryNames(categoryNameList, getUser().getCompanyCode());
                categoryMap = categoryList.stream().collect(Collectors.toMap(o->o.getCategoryName()+","+o.getCategoryLevel(), Function.identity()));
            }
            //制造商
            if (CollectionUtils.isNotEmpty(manufactureList)) {
                manufactureMap = manufacturerService.selectByManufactureNames(manufactureList, getUser().getCompanyCode());
            }
            //渠道
            if (CollectionUtils.isNotEmpty(channelList)) {
                channelMap = priceChannelService.selectByChannelNames(channelList, getUser().getCompanyCode());
            }
            //商品标签
            if (CollectionUtils.isNotEmpty(skuTagList)) {
                skuTagMap = tagInfoService.selectByTagNames(skuTagList, getUser().getCompanyCode());
            }
            dicNameList.add("供货渠道类别");
            dicNameList.add("商品属性");
            dicNameList.add("商品类别");
            dicNameList.add("结算方式");
            dicNameList.add("单位");
            dicNameList.add("仓位类型");
            Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(dicNameList, getUser().getCompanyCode());
            List<AddSkuInfoReqVO> skuInfoList = Lists.newArrayList();
            List<SkuInfoImport> importList = Lists.newArrayList();
            Map<String, String> reaptMap = Maps.newHashMap();
            for (int i = 0; i < skuInfoImports.size(); i++) {
                //检查信息
                CheckSku checkSku = new CheckSku(productSkuMap, supplyCompanyMap, brandMap , categoryMap, channelMap, skuTagMap, reaptMap, skuInfoImports.get(i),spuMap,dicMap,manufactureMap)
                        .checkRepeat() //检查重复
                        .checkSKuUpdate() //修改检查sku
                        .checkBaseDate() //检查基础数据
                        .checkInvoice() //检查进销存包装
                        .checkSettlement() //检查结算信息
//                        .checkSupplier() //检查供应商
//                        .checkPrice() //检查价格
//                        .checkConfig() //检查配置
                        .checkManufacturer() //检查厂家
                        .checkPic();//检查图片
                //返回实体
                AddSkuInfoReqVO resp = checkSku.getRespVO();
                skuInfoList.add(resp);
                SkuInfoImport skuInfoImport = checkSku.getSkuInfoImport();
                String error = skuInfoImport.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s = "第" + (i + 3) + "行 " + error;
                    skuInfoImport.setError(s);
                }
                importList.add(skuInfoImport);
                reaptMap = checkSku.getReapMap();
                spuMap = checkSku.getSpuMap();
            }
            return new SkuImportMain(skuInfoList,importList);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }

    }

    @Override
    @Transactional(rollbackFor = ExcelException.class)
    public Boolean importSkuNewSave(SkuImportReq reqVOs) {
        HashMap<String, String> spuMap = Maps.newHashMap();
        for (AddSkuInfoReqVO reqVO : reqVOs.getAddSkuList()) {
            if (StringUtils.isBlank(reqVO.getProductSkuDraft().getProductCode())) {
                //判断是否新增了
                String s1 = spuMap.get(reqVO.getProductSkuDraft().getProductName());
                if (StringUtils.isNotBlank(s1)) {
                    reqVO.getProductSkuDraft().setProductCode(s1);
                } else {
                    //没有spu信息需要先增加品牌信息
                    synchronized (SkuInfoService.class) {
                        NewProductSaveReqVO saveReqVO = new NewProductSaveReqVO();
                        saveReqVO.setProductName(reqVO.getProductSkuDraft().getProductName());
                        String s = newProductService.insertProduct(saveReqVO);
                        reqVO.getProductSkuDraft().setProductCode(s);
                        spuMap.put(reqVO.getProductSkuDraft().getProductName(), s);
                    }
                }
            }
            reqVO.setBoxFlag(false);
            reqVO.getProductSkuDraft().setProcurementSectionCode(reqVOs.getPurchaseGroupCode());
            reqVO.getProductSkuDraft().setProcurementSectionName(reqVOs.getPurchaseGroupName());
            saveDraftSkuInfo(reqVO);
        }
        return Boolean.TRUE;
    }


    @Override
    @Transactional(rollbackFor = ExcelException.class)
    public Boolean importSkuNewUpdate(SkuImportReq reqVOs) {
        HashMap<String, String> spuMap = Maps.newHashMap();
        for (AddSkuInfoReqVO reqVO : reqVOs.getAddSkuList()) {
            if (StringUtils.isBlank(reqVO.getProductSkuDraft().getProductCode())) {
                //判断是否新增了
                String s1 = spuMap.get(reqVO.getProductSkuDraft().getProductName());
                if (StringUtils.isNotBlank(s1)) {
                    reqVO.getProductSkuDraft().setProductCode(s1);
                } else {
                    //没有spu信息需要先增加品牌信息
                    synchronized (SkuInfoService.class) {
                        NewProductSaveReqVO saveReqVO = new NewProductSaveReqVO();
                        saveReqVO.setProductName(reqVO.getProductSkuDraft().getProductName());
                        String s = newProductService.insertProduct(saveReqVO);
                        reqVO.getProductSkuDraft().setProductCode(s);
                        spuMap.put(reqVO.getProductSkuDraft().getProductName(), s);
                    }
                }
            }
            reqVO.setBoxFlag(false);
            reqVO.getProductSkuDraft().setProcurementSectionCode(reqVOs.getPurchaseGroupCode());
            reqVO.getProductSkuDraft().setProcurementSectionName(reqVOs.getPurchaseGroupName());
            updateDraftSkuInfo(reqVO);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean exportSku(HttpServletResponse resp) {
        try {
            List<ExportSkuInfo> list = productSkuDraftMapper.exportSku();
            StyleExcelHandler handler = new StyleExcelHandler();
            ExcelWriter writer = new ExcelWriter(null, getOutputStream("商品申请确认模板",resp,ExcelTypeEnum.XLSX), ExcelTypeEnum.XLSX, true, handler);
            Sheet sheet1 = new Sheet(1, 1, ExportSkuInfo.class, "商品申请确认模板", null);
            sheet1.setAutoWidth(true);
            writer.write(list ,sheet1);
            writer.finish();
            return Boolean.TRUE;
        } catch (ExcelException e) {
            log.error(e.getMessage(),e);
            throw new BizException(ResultCode.EXPORT_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importSkuUpdateForSupplyPlatform(SkuImportReq reqVOs) {
        HashMap<String, String> spuMap = Maps.newHashMap();
        for (AddSkuInfoReqVO reqVO : reqVOs.getAddSkuList()) {
            if (StringUtils.isBlank(reqVO.getProductSkuDraft().getProductCode())) {
                //判断是否新增了
                String s1 = spuMap.get(reqVO.getProductSkuDraft().getProductName());
                if (StringUtils.isNotBlank(s1)) {
                    reqVO.getProductSkuDraft().setProductCode(s1);
                } else {
                    //没有spu信息需要先增加品牌信息
                    synchronized (SkuInfoService.class) {
                        NewProductSaveReqVO saveReqVO = new NewProductSaveReqVO();
                        saveReqVO.setProductName(reqVO.getProductSkuDraft().getProductName());
                        String s = newProductService.insertProduct(saveReqVO);
                        reqVO.getProductSkuDraft().setProductCode(s);
                        spuMap.put(reqVO.getProductSkuDraft().getProductName(), s);
                    }
                }
            }
            reqVO.setBoxFlag(false);
            saveDraftSkuInfoForPlatform(reqVO);
        }
        return Boolean.TRUE;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDraftSkuInfoForPlatform(AddSkuInfoReqVO reqVO) {
        if(Objects.isNull(reqVO.getProductSkuDraft().getSkuCode())){
            throw new BizException(ResultCode.SKU_CODE_EMPTY);
        }
        ProductSkuRespVo skuRespVo = productSkuDao.getSkuDraft(reqVO.getProductSkuDraft().getSkuCode());
        if (null == skuRespVo) {
            throw new BizException(ResultCode.SKU_CODE_EMPTY);
        }
        reqVO.getProductSkuDraft().setApplyType(skuRespVo.getApplyType());
        reqVO.getProductSkuDraft().setApplyTypeName(skuRespVo.getApplyTypeName());
        List<String> skuCodes = Lists.newArrayList();
        Set<String> sets = Sets.newHashSet();
        skuCodes.add(reqVO.getProductSkuDraft().getSkuCode());
        sets.add(reqVO.getProductSkuDraft().getSkuCode());
        //查询该调sku对应的供应商信息
        Map<String, ProductSkuDraft> stringProductSkuDraftMap = draftService.selectBySkuCode(sets, getUser().getCompanyCode());
        ProductSkuDraft draft = stringProductSkuDraftMap.get(reqVO.getProductSkuDraft().getSkuCode());
        if (Objects.isNull(draft) || CollectionUtils.isEmpty(draft.getSupplyList())||draft.getSupplyList().size()!=1) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
        ProductSkuSupplyUnitDraft unitDraft = draft.getSupplyList().get(0);
        List<Long> list = Lists.newArrayList();
        list.add(unitDraft.getId());
        productSkuSupplyUnitDraftMapper.deleteDraftByIds(list);
        ((SkuInfoService)AopContext.currentProxy()).deleteProductSkuDraftForPlatform(skuCodes);
        reqVO.getProductSkuSupplyUnitDrafts().get(0).setIsDefault(unitDraft.getIsDefault());
        return  ((SkuInfoService)AopContext.currentProxy()).saveDraftSkuInfo(reqVO);
    }

    @Override
    public List<ProductSkuDraftRespVo> getProductSkuDraftList(QuerySkuDraftListReqVO reqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVO.setCompanyCode(authToken.getCompanyCode());
            reqVO.setPersonId(authToken.getPersonId());
        }
        if(CollectionUtils.isNotEmpty(reqVO.getProductCategoryCodes())){
            try {
                reqVO.setProductCategoryLv1Code(reqVO.getProductCategoryCodes().get(0));
                reqVO.setProductCategoryLv2Code(reqVO.getProductCategoryCodes().get(1));
                reqVO.setProductCategoryLv3Code(reqVO.getProductCategoryCodes().get(2));
                reqVO.setProductCategoryLv4Code(reqVO.getProductCategoryCodes().get(3));
            } catch (Exception e) {
                log.info("不做处理,让程序继续执行下去");
            }
        }
        return productSkuDraftMapper.getProductSkuDraft(reqVO);
    }

    @Override
    public DetailRequestRespVo getInfoByForm(String formNo) {
        DetailRequestRespVo respVo = new DetailRequestRespVo();
        List<ApplyProductSku> applyProductSkus = productSkuDao.getApplySkuByFormNo(formNo);
        if(CollectionUtils.isEmpty(applyProductSkus)){
            throw new BizException(ResultCode.OBJECT_EMPTY_BY_FORMNO);
        }
        String applyCode = applyProductSkus.get(0).getApplyCode();
        respVo.setApplyCode(applyCode);
        respVo.setItemCode("1");
        return respVo;
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response, ExcelTypeEnum excelTypeEnum) throws ExcelException{
        //创建本地文件
        String filePath = fileName + excelTypeEnum.getValue();
        try {
            fileName = new String(filePath.getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new ExcelException("创建文件失败！");
        }
    }

    private void dataValidation( List<SkuInfoImportNew> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuInfoImports.size()<3) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = SkuInfoImportNew.HEAD;
        boolean equals = skuInfoImports.get(1).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

    private void dataValidation2( List<SkuInfoImportUpdate> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
//        if (skuInfoImports.size()<3) {
//            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
//        }
        String  head = SkuInfoImportUpdate.HEAD;
        boolean equals = skuInfoImports.get(1).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

    private void dataValidation3( List<ExportSkuInfo> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuInfoImports.size()<3) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = ExportSkuInfo.HEAD;
        boolean equals = skuInfoImports.get(1).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

    private ProductApplyInfoRespVO<ProductSkuApplyVo> dealApplyViewData(List<ProductSkuApplyVo> list) {
        ProductApplyInfoRespVO<ProductSkuApplyVo> resp = new ProductApplyInfoRespVO<>();
        //数据相同默认取第一个
        ProductSkuApplyVo applyVO = list.get(0);
        resp.setApplyBy(applyVO.getUpdateBy());
        resp.setApplyTime(applyVO.getUpdateTime());
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

    /**
     * 校验导入sku的内部类
     */
    @Data
    private class CheckSku {
        AddSkuInfoReqVO resp;
        Map<String, ProductSkuInfo> productSkuMap;
        Map<String, SupplyCompany> supplyCompanyMap;
        Map<String, ProductBrandType> brandMap;
        Map<String, ProductCategory> categoryMap;
        Map<String, PriceChannel> channelMap;
        Map<String, TagInfo> skuTagMap;
        Map<String, NewProduct> spuMap;
        Map<String, String> repeatMap;
        SkuInfoImport importVo;
        List<String> error;
        Map<String, SupplierDictionaryInfo> dicMap;
        Map<String, Manufacturer> manufactureMap;
        Map<String, ProductSkuDraft> productSkuDraftMap;
        Map<String, PurchaseGroupDTO> purchaseGroupMap;

        private CheckSku() {
        }

        private CheckSku(Map<String, ProductSkuInfo> productSkuMap, Map<String, SupplyCompany> supplyCompanyMap, Map<String, ProductBrandType> brandMap, Map<String, ProductCategory> categoryMap, Map<String, PriceChannel> channelMap, Map<String, TagInfo> skuTagMap, Map<String, String> repeatMap, Object importVo, Map<String, NewProduct> spuMap, Map<String, SupplierDictionaryInfo> dicMap, Map<String, Manufacturer> manufactureMap) {
            this.error = Lists.newArrayList();
            this.resp = new AddSkuInfoReqVO();
            this.productSkuMap = productSkuMap;
            this.supplyCompanyMap = supplyCompanyMap;
            this.brandMap = brandMap;
            this.categoryMap = categoryMap;
            this.channelMap = channelMap;
            this.skuTagMap = skuTagMap;
            this.repeatMap = repeatMap;
            this.importVo = BeanCopyUtils.copy(importVo,SkuInfoImport.class);
            this.spuMap = spuMap;
            this.dicMap = dicMap;
            this.manufactureMap = manufactureMap;
        }

        public CheckSku(Map<String, ProductSkuInfo> productSkuMap, Map<String, SupplyCompany> supplyCompanyMap, Map<String, ProductBrandType> brandMap, Map<String, ProductCategory> categoryMap, Map<String, PriceChannel> channelMap, Map<String, TagInfo> skuTagMap, Map<String, String> reaptMap, ExportSkuInfo importVo, Map<String, NewProduct> spuMap, Map<String, SupplierDictionaryInfo> dicMap, Map<String, ProductSkuDraft> productSkuDraftMap,Map<String,PurchaseGroupDTO> purchaseGroupMap) {
            this.error = Lists.newArrayList();
            this.resp = new AddSkuInfoReqVO();
            this.productSkuMap = productSkuMap;
            this.supplyCompanyMap = supplyCompanyMap;
            this.brandMap = brandMap;
            this.categoryMap = categoryMap;
            this.channelMap = channelMap;
            this.skuTagMap = skuTagMap;
            this.repeatMap = reaptMap;
            this.importVo = BeanCopyUtils.copy(importVo,SkuInfoImport.class);
            this.spuMap = spuMap;
            this.dicMap = dicMap;
//            this.manufactureMap = manufactureMap;
            this.productSkuDraftMap = productSkuDraftMap;
            this.purchaseGroupMap = purchaseGroupMap;
        }

        //检查重复
        private CheckSku checkRepeat() {
            return this;
        }
        //新增检查sku
        private CheckSku checkSKuNew() {
            ProductSkuDraft draft = BeanCopyUtils.copy(importVo, ProductSkuDraft.class);
            //sku名称
            if (Objects.isNull(importVo.getSkuName())) {
                error.add("SKU名称不能为空");
            } else {
                ProductSkuInfo sku = productSkuMap.get(importVo.getSkuName());
                if (Objects.nonNull(sku)) {
                    error.add("sku名称已存在");
                }
            }
            this.resp.setProductSkuDraft(draft);
            return this;
        }
        //修改检查sku
        private CheckSku checkSKuUpdate() {
            ProductSkuDraft draft = BeanCopyUtils.copy(importVo, ProductSkuDraft.class);
            //sku编码
            if (Objects.isNull(importVo.getSkuCode())) {
                error.add("sku编码不能为空");
            }else {
                ProductSkuInfo sku = productSkuMap.get(importVo.getSkuCode());
                //sku名称
                if (Objects.isNull(sku)) {
                    error.add("无对应的sku编码");
                }else {
                    if (Objects.isNull(importVo.getSkuName())) {
                        error.add("SKU名称不能为空");
                    }
//                    else {
//                        if (!sku.getSkuName().equals(importVo.getSkuName())) {
//                            error.add("sku编码和sku名称不对应");
//                        }
//                    }
                }
            }
            this.resp.setProductSkuDraft(draft);
            return this;
        }

        //供应商平台数据导入修改检查sku
        private CheckSku checkSKuUpdate1() {
            ProductSkuDraft draft = BeanCopyUtils.copy(importVo, ProductSkuDraft.class);
            //sku编码
            if (Objects.isNull(importVo.getSkuCode())) {
                error.add("sku编码不能为空");
            }else {
                ProductSkuDraft sku = productSkuDraftMap.get(importVo.getSkuCode());
                //sku名称
                if (Objects.isNull(sku)) {
                    error.add("无对应的sku编码");
                }else {
                    if (Objects.isNull(importVo.getSkuName())) {
                        error.add("SKU名称不能为空");
                    }
//                    else {
//                        if (!sku.getSkuName().equals(importVo.getSkuName())) {
//                            error.add("sku编码和sku名称不对应");
//                        }
//                    }
                }
            }
            this.resp.setProductSkuDraft(draft);
            return this;
        }
        //检查基础数据
        private CheckSku checkBaseDate() {
            ProductSkuDraft productSkuDraft = this.resp.getProductSkuDraft();
            //类型
            if (Objects.isNull(importVo.getGoodsGiftsDesc())) {
                error.add("类型不能为空");
            } else {
                StatusTypeCode code = StatusTypeCode.getAll().get(importVo.getGoodsGiftsDesc());
                if (Objects.isNull(code)) {
                    error.add("类型值错误，请填写商品或赠品");
                } else {
                    productSkuDraft.setGoodsGifts(code.getStatus());
                }
            }
            //sku简称
            if (Objects.isNull(importVo.getSkuAbbreviation())) {
//                error.add("SKU简称不能为空");
            }
            //品牌
            if (Objects.isNull(importVo.getProductBrandName())) {
                error.add("品牌不能为空");
            } else {
                ProductBrandType productBrandType = brandMap.get(importVo.getProductBrandName());
                if (Objects.isNull(productBrandType)) {
                    error.add("无对应品牌信息");
                } else {
                    productSkuDraft.setProductBrandCode(productBrandType.getBrandId());
                }
            }
            //品类
            if (Objects.isNull(importVo.getProductCategoryName())) {
                error.add("品类不能为空");
            } else {
                String[] split = importVo.getProductCategoryName().split("-");
                if (split.length != 4) {
                    error.add("品类应为四级用\"-\"分割");
                } else {
                    boolean flag = true;
                    ProductCategory current = null;
                    for (int i = split.length - 1; i >= 0; i--) {
                        ProductCategory productCategory = categoryMap.get(split[i] + "," + (i + 1));
                        if (Objects.isNull(productCategory)) {
                            error.add("无对应名称为" + split[i] + "的品牌信息");
                            flag = false;
                            break;
                        } else {
                            if (split.length - 1 == i) {
                                current = productCategory;
                            } else {
                                if (!productCategory.getCategoryId().equals(current.getParentId())) {
                                    error.add("品牌名为" + current.getCategoryName() + "的上级名称不为" + split[i]);
                                    flag = false;
                                    break;
                                } else {
                                    current = productCategory;
                                }
                            }
                        }
                    }
                    if (flag) {
                        productSkuDraft.setProductCategoryCode(categoryMap.get(split[split.length - 1] + "," + 4).getCategoryId());
                        productSkuDraft.setProductCategoryName(categoryMap.get(split[split.length - 1] + "," + 4).getCategoryName());
                    }
                }
            }
            //spu
            if (Objects.isNull(importVo.getProductName())) {
                error.add("所属SPU不能为空");
            } else {
                NewProduct newProduct = spuMap.get(importVo.getProductName().trim());
                if (Objects.isNull(newProduct)) {
                    spuMap.put(importVo.getProductName().trim(), new NewProduct());
                } else {
                    productSkuDraft.setProductCode(newProduct.getProductCode());
                }
            }
            //商品属性
            if (Objects.isNull(importVo.getProductPropertyName())) {
                error.add("商品属性不能为空");
            } else {
                SupplierDictionaryInfo dic = dicMap.get(importVo.getProductPropertyName());
                if (Objects.isNull(dic)) {
                    error.add("无对应名称的商品属性");
                } else {
                    productSkuDraft.setProductPropertyCode(dic.getSupplierDictionaryValue());
                }
            }
            //所属部门
            if (Objects.isNull(importVo.getProductSortName())) {
                error.add("所属部门不能为空");
            } else {
                SupplierDictionaryInfo dic = dicMap.get(importVo.getProductSortName());
                if (Objects.isNull(dic)) {
                    error.add("无对应名称的所属部门");
                } else {
                    productSkuDraft.setProductSortCode(dic.getSupplierDictionaryValue());
                }
            }
            boolean flag1 = false;
            boolean flag2 = false;
            //颜色
            if (Objects.nonNull(importVo.getColorName())) {
//                error.add("颜色不能为空");
                flag1 = true;
            }
            //型号
            if (Objects.nonNull(importVo.getModelNumber())) {
//                error.add("型号不能为空");
                flag2 = true;
            }
            if (flag1&&flag2) {
                error.add("颜色和型号只能填写一个");
            } else if(!(flag1 || flag2)){
                error.add("颜色和型号必须填写一个");
            }
            //是否管理保质期
            if (Objects.isNull(importVo.getQualityAssuranceManagementDesc())) {
                error.add("是否管理保质期不能为空");
            } else {
                QualityAssuranceManagements e = QualityAssuranceManagements.getAll().get(importVo.getQualityAssuranceManagementDesc());
                if (Objects.isNull(e)) {
                    error.add("是否管理保质期请选择管理或者不管理");
                } else {
                    productSkuDraft.setQualityAssuranceManagement(e.getType());
                }
                if (e.getType().equals((byte) 0)) {
                    //管理
                    //保质期单位
                    if (Objects.isNull(importVo.getQualityNumber())) {
                        error.add("保质期单位不能为空");
                    } else {
                        QualityTypes type = QualityTypes.getAll().get(importVo.getQualityNumber());
                        if (Objects.isNull(type)) {
                            error.add("保质期单位只能是年月天");
                        } else {
                            productSkuDraft.setQualityNumber(type.getType().toString());
                        }
                    }
                    //保质天数
                    if (Objects.isNull(importVo.getQualityDate())) {
                        error.add("保质天数不能为空");
                    }else {
                        productSkuDraft.setQualityDate(Integer.parseInt(importVo.getQualityDate())+"");
                    }
                }
            }

            //供货渠道类别
            if (Objects.isNull(importVo.getCategoriesSupplyChannelsName())) {
                error.add("供货渠道类别不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getCategoriesSupplyChannelsName());
                if (Objects.isNull(info)) {
                    error.add("无对应的名称的供货渠道类别");
                } else {
                    productSkuDraft.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                }
            }
            //助记码
            if (false) {
            }
            //厂家指导价
            if (Objects.isNull(importVo.getManufacturerGuidePrice())) {
                error.add("厂家指导价不能为空");
            } else {
                try {
                    productSkuDraft.setManufacturerGuidePrice(NumberConvertUtils.stringParseLong(importVo.getManufacturerGuidePrice()));
                } catch (NumberFormatException e) {
                    error.add("厂家指导价格式不正确");
                }
            }
            //适用其实月龄
            if (false) {
            }
            //是否季节商品
            if (Objects.isNull(importVo.getSeasonalGoodsDesc())) {
                error.add("是否季节商品不能为空");
            } else {
                Generals generals = Generals.getAll().get(importVo.getSeasonalGoodsDesc());
                if (Objects.isNull(generals)) {
                    error.add("是否季节商品请填写是或者否");
                } else {
                    productSkuDraft.setSeasonalGoods(generals.getType());
                }
            }
            //仓位类型
            if (Objects.isNull(importVo.getWarehouseTypeName())) {
                error.add("仓位类型不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getWarehouseTypeName());
                if (Objects.isNull(info)) {
                    error.add("仓位类型请填写大货仓位或者小货仓位");
                } else {
                    productSkuDraft.setWarehouseTypeCode(info.getSupplierDictionaryValue());
                }
            }
            //结构性商品
            if (Objects.isNull(importVo.getStructuralGoodsDesc())) {
                error.add("是否结构性商品不能为空");
            } else {
                Generals generals = Generals.getAll().get(importVo.getStructuralGoodsDesc());
                if (Objects.isNull(generals)) {
                    error.add("结构性商品请填写是或者否");
                } else {
                    productSkuDraft.setStructuralGoods(generals.getType());
                }
            }
            //使用时长
            if (Objects.nonNull(importVo.getUseTime())) {
                try {
                    productSkuDraft.setUseTime(Integer.parseInt(importVo.getUseTime()));
                } catch (Exception e) {
                    error.add("使用时长格式不正确");
                }
            }
            //库存模式
            if (Objects.isNull(importVo.getInventoryModelDesc())) {
                error.add("库存模式不能为空");
            } else {
                InventoryModels generals = InventoryModels.getAll().get(importVo.getInventoryModelDesc());
                if (Objects.isNull(generals)) {
                    error.add("库存模式请填写有库存销售或者无库存销售");
                } else {
                    productSkuDraft.setInventoryModel(generals.getType());
                }
            }
            //唯一码管理
            if (Objects.isNull(importVo.getUniqueCodeDesc())) {
                error.add("唯一码管理不能为空");
            } else {
                Generals generals = Generals.getAll().get(importVo.getUniqueCodeDesc());
                if (Objects.isNull(generals)) {
                    error.add("唯一码管理请填写是或者否");
                } else {
                    productSkuDraft.setUniqueCode(generals.getType());
                }
            }
            //覆盖渠道
            if (Objects.isNull(importVo.getPriceChannelName())) {
                error.add("覆盖渠道不能为空");
            } else {
                List<ProductSkuChannelDraft> draft = Lists.newArrayList();
                String[] split = importVo.getPriceChannelName().split(",");
                boolean flag = true;
                if (split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        PriceChannel priceChannel = channelMap.get(split[i]);
                        if (Objects.isNull(priceChannel)) {
                            error.add("无法找到名称为" + split[i] + "的渠道");
                            flag = false;
                        }
                    }
                    if (flag) {
                        //这里需要渠道完全正确才封装对象
                        for (int i = 0; i < split.length; i++) {
                            PriceChannel priceChannel = channelMap.get(split[i]);
                            ProductSkuChannelDraft channelDraft = new ProductSkuChannelDraft();
                            channelDraft.setSkuName(importVo.getSkuName());
                            channelDraft.setPriceChannelCode(priceChannel.getPriceChannelCode());
                            channelDraft.setPriceChannelName(priceChannel.getPriceChannelName());
                            draft.add(channelDraft);
                            resp.setProductSkuChannelDrafts(draft);
                        }
                    }
                } else {
                    //只有一条
                    PriceChannel priceChannel = channelMap.get(importVo.getPriceChannelName());
                    if (Objects.isNull(priceChannel)) {
                        error.add("无法找到名称为" + importVo.getPriceChannelName() + "的渠道");
                        flag = false;
                    } else {
                        ProductSkuChannelDraft channelDraft = new ProductSkuChannelDraft();
                        channelDraft.setSkuName(importVo.getSkuName());
                        channelDraft.setPriceChannelCode(priceChannel.getPriceChannelCode());
                        channelDraft.setPriceChannelName(priceChannel.getPriceChannelName());
                        draft.add(channelDraft);
                        resp.setProductSkuChannelDrafts(draft);
                    }
                }
            }
            //商品标签
            if (Objects.nonNull(importVo.getTagName())) {
                List<SaveUseTagRecordItemReqVo> tagInfoList = Lists.newArrayList();
                boolean flag = true;
                String[] split = importVo.getTagName().split(",");
                if (split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        TagInfo tagInfo = skuTagMap.get(split[i]);
                        if (Objects.isNull(tagInfo)) {
                            error.add("名称为" + split[i] + "的标签不存在");
                            flag = false;
                        }
                    }
                    if (flag) {
                        //全部验证成功才能提交
                        for (int i = 0; i < split.length; i++) {
                            TagInfo tagInfo = skuTagMap.get(split[i]);
                            SaveUseTagRecordItemReqVo reqVo = new SaveUseTagRecordItemReqVo();
                            reqVo.setTagCode(tagInfo.getTagCode());
                            reqVo.setTagName(tagInfo.getTagName());
                            tagInfoList.add(reqVo);
                        }
                        resp.setTagInfoList(tagInfoList);
                    }
                } else {
                    TagInfo tagInfo = skuTagMap.get(importVo.getTagName());
                    if (Objects.isNull(tagInfo)) {
                        error.add("名称为" + importVo.getTagName() + "的标签不存在");
                    } else {
                        SaveUseTagRecordItemReqVo reqVo = new SaveUseTagRecordItemReqVo();
                        reqVo.setTagCode(tagInfo.getTagCode());
                        reqVo.setTagName(tagInfo.getTagName());
                        tagInfoList.add(reqVo);
                        resp.setTagInfoList(tagInfoList);
                    }
                }
            }
            //商品备注
            if (false) {
            }
            //管理方式默认写死
            productSkuDraft.setManagementStyleCode("1");
            productSkuDraft.setManagementStyleName("只管理数量");
            this.resp.setProductSkuDraft(productSkuDraft);
            return this;
        }

        //检查进销存包装
        private CheckSku checkInvoice() {
            List<PurchaseSaleStockReqVo> purchaseSaleStockReqVos = Lists.newArrayList();
            List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts = Lists.newArrayList();
            //库存
            PurchaseSaleStockReqVo stock = new PurchaseSaleStockReqVo();
            stock.setBaseProductContent(1);
            stock.setZeroRemovalCoefficient(1L);
            stock.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            stock.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            stock.setProductCode(this.resp.getProductSkuDraft().getProductCode());
            stock.setProductName(this.resp.getProductSkuDraft().getProductName());
            stock.setType((byte) 0);
            //库存规格
            if (Objects.isNull(importVo.getStockSpec())) {
                error.add("库存规格不能为空");
            } else {
                stock.setSpec(importVo.getStockSpec());
            }
            //库存单位
            if (Objects.isNull(importVo.getStockUnitName())) {
                error.add("库存单位不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getStockUnitName());
                if (Objects.isNull(info)) {
                    error.add("无对应名称为" + importVo.getStockUnitName() + "的单位");
                } else {
                    stock.setUnitCode(info.getSupplierDictionaryValue());
                    stock.setUnitName(info.getSupplierContent());
                }
            }
            //库存包装信息
            if (Objects.nonNull(importVo.getStockBoxLength())) {
                ProductSkuBoxPackingDraft stockBox = new ProductSkuBoxPackingDraft();
                stockBox.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
                stockBox.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
                stockBox.setLargeUnit(stock.getUnitName());
                stockBox.setUnitCode(stock.getUnitCode());
                boolean flag = true;
                try {
                    stockBox.setBoxLength(NumberConvertUtils.stringParseLong(importVo.getStockBoxLength().trim()));
                } catch (Exception e) {
                    error.add("库存长格式不正确");
                    flag = false;
                }
                try {
                    stockBox.setBoxWidth(NumberConvertUtils.stringParseLong(importVo.getStockBoxWidth().trim()));
                } catch (Exception e) {
                    error.add("库存宽格式不正确");
                    flag = false;
                }
                try {
                    stockBox.setBoxHeight(NumberConvertUtils.stringParseLong(importVo.getStockBoxHeight().trim()));
                } catch (Exception e) {
                    error.add("库存高格式不正确");
                    flag = false;
                }
                if (flag) {
                    stockBox.setBoxVolume(stockBox.getBoxLength() * stockBox.getBoxWidth() * stockBox.getBoxHeight()/10000);
                }
                try {
                    stockBox.setBoxGrossWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getStockBoxGrossWeight().trim()));
                } catch (Exception e) {
                    error.add("库存毛重格式不正确");
                }
                try {
                    stockBox.setNetWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getStockNetWeight()));
                } catch (Exception e) {
                    error.add("库存净重格式不正确");
                }
                productSkuBoxPackingDrafts.add(stockBox);
            }
            //库存条形码
            if (Objects.isNull(importVo.getStockBarCode())) {
                error.add("库存条形码不能为空");
            } else {
                stock.setBarCode(importVo.getStockBarCode().trim());
            }
            purchaseSaleStockReqVos.add(stock);
            //采购
            PurchaseSaleStockReqVo purchase = new PurchaseSaleStockReqVo();
            purchase.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            purchase.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            purchase.setProductCode(this.resp.getProductSkuDraft().getProductCode());
            purchase.setProductName(this.resp.getProductSkuDraft().getProductName());
            purchase.setType((byte) 1);
            //采购规格
            if (Objects.isNull(importVo.getPurchaseSpec())) {
                error.add("采购规格不能为空");
            } else {
                purchase.setSpec(importVo.getPurchaseSpec());
            }
            //采购单位
            if (Objects.isNull(importVo.getPurchaseUnitName())) {
                error.add("采购单位不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getPurchaseUnitName());
                if (Objects.isNull(info)) {
                    error.add("无对应名称为" + importVo.getPurchaseUnitName() + "的单位");
                } else {
                    purchase.setUnitCode(info.getSupplierDictionaryValue());
                    purchase.setUnitName(info.getSupplierContent());
                }
            }
            //采购包装信息
            if (!importVo.getStockUnitName().equals(importVo.getPurchaseUnitName())) {
                if (Objects.nonNull(importVo.getPurchaseBoxLength())) {
                    ProductSkuBoxPackingDraft purchaseBox = new ProductSkuBoxPackingDraft();
                    purchaseBox.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
                    purchaseBox.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
                    purchaseBox.setLargeUnit(purchase.getUnitName());
                    purchaseBox.setUnitCode(purchase.getUnitCode());
                    boolean flag = true;
                    try {
                        purchaseBox.setBoxLength(NumberConvertUtils.stringParseLong(importVo.getPurchaseBoxLength().trim()));
                    } catch (Exception e) {
                        error.add("采购长格式不正确");
                        flag = false;
                    }
                    try {
                        purchaseBox.setBoxWidth(NumberConvertUtils.stringParseLong(importVo.getPurchaseBoxWidth().trim()));
                    } catch (Exception e) {
                        error.add("采购宽格式不正确");
                        flag = false;
                    }
                    try {
                        purchaseBox.setBoxHeight(NumberConvertUtils.stringParseLong(importVo.getPurchaseBoxHeight().trim()));
                    } catch (Exception e) {
                        error.add("采购高格式不正确");
                        flag = false;
                    }
                    if (flag) {
                        purchaseBox.setBoxVolume(purchaseBox.getBoxLength() * purchaseBox.getBoxWidth() * purchaseBox.getBoxHeight()/10000);
                    }
                    try {
                        purchaseBox.setBoxGrossWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getPurchaseBoxGrossWeight().trim()));
                    } catch (Exception e) {
                        error.add("采购毛重格式不正确");
                    }
                    try {
                        purchaseBox.setNetWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getPurchaseNetWeight()));
                    } catch (Exception e) {
                        error.add("采购净重格式不正确");
                    }
                    productSkuBoxPackingDrafts.add(purchaseBox);
                }
            }
            //采购基商品含量
            if (Objects.isNull(importVo.getPurchaseBaseProductContent())) {
                error.add("采购基商品含量不能为空");
            } else {
                try {
                    purchase.setBaseProductContent(Integer.parseInt(importVo.getPurchaseBaseProductContent()));
                } catch (Exception e) {
                    error.add("采购基商品含量不正确");
                }
            }
            //采购拆零系数
            if (Objects.isNull(importVo.getPurchaseZeroRemovalCoefficient())) {
                error.add("采购拆零系数不能为空");
            } else {
                try {
                    purchase.setZeroRemovalCoefficient(Long.parseLong(importVo.getPurchaseZeroRemovalCoefficient()));
                } catch (Exception e) {
                    error.add("采购拆零系数不正确");
                }
            }
            //采购条形码
            if (Objects.isNull(importVo.getPurchaseBarCode())) {
                error.add("采购条形码不能为空");
            } else {
                purchase.setBarCode(importVo.getPurchaseBarCode().trim());
            }
            purchaseSaleStockReqVos.add(purchase);

            //分销
            PurchaseSaleStockReqVo distribution = new PurchaseSaleStockReqVo();
            distribution.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            distribution.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            distribution.setProductCode(this.resp.getProductSkuDraft().getProductCode());
            distribution.setProductName(this.resp.getProductSkuDraft().getProductName());
            distribution.setType((byte) 2);
            //分销规格
            if (Objects.isNull(importVo.getDistributionSpec())) {
                error.add("分销规格不能为空");
            } else {
                distribution.setSpec(importVo.getDistributionSpec());
            }
            //分销单位
            if (Objects.isNull(importVo.getDistributionUnitName())) {
                error.add("分销单位不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getDistributionUnitName());
                if (Objects.isNull(info)) {
                    error.add("无对应名称为" + importVo.getDistributionUnitName() + "的单位");
                } else {
                    distribution.setUnitCode(info.getSupplierDictionaryValue());
                    distribution.setUnitName(info.getSupplierContent());
                }
            }
            //分销基商品含量
            if (Objects.isNull(importVo.getDistributionBaseProductContent())) {
                error.add("分销基商品含量不能为空");
            } else {
                try {
                    distribution.setBaseProductContent(Integer.parseInt(importVo.getDistributionBaseProductContent()));
                } catch (Exception e) {
                    error.add("分销基商品含量不正确");
                }
            }
            //分销拆零系数
            if (Objects.isNull(importVo.getDistributionZeroRemovalCoefficient())) {
                error.add("分销拆零系数不能为空");
            } else {
                try {
                    distribution.setZeroRemovalCoefficient(Long.parseLong(importVo.getDistributionZeroRemovalCoefficient()));
                } catch (Exception e) {
                    error.add("分销拆零系数不正确");
                }
            }
            //分销条形码
            if (Objects.isNull(importVo.getDistributionBarCode())) {
                error.add("分销条形码不能为空");
            } else {
                distribution.setBarCode(importVo.getDistributionBarCode().trim());
            }
            //最大订购数量
            if (Objects.nonNull(importVo.getMaxOrderNum())) {
                try {
                    distribution.setMaxOrderNum(Integer.parseInt(importVo.getMaxOrderNum()));
                } catch (NumberFormatException e) {
                    error.add("最大订购数量格式不正确");
                }
            }
            purchaseSaleStockReqVos.add(distribution);

            //销售
            PurchaseSaleStockReqVo sale = new PurchaseSaleStockReqVo();
            sale.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            sale.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            sale.setProductCode(this.resp.getProductSkuDraft().getProductCode());
            sale.setProductName(this.resp.getProductSkuDraft().getProductName());
            sale.setIsDefault((byte)1);
            sale.setType((byte) 3);
            //销售规格
            if (Objects.isNull(importVo.getSaleSpec())) {
                error.add("销售规格不能为空");
            } else {
                sale.setSpec(importVo.getSaleSpec());
            }
            //销售单位
            if (Objects.isNull(importVo.getSaleUnitName())) {
                error.add("销售单位不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getSaleUnitName());
                if (Objects.isNull(info)) {
                    error.add("无对应名称为" + importVo.getSaleUnitName() + "的单位");
                } else {
                    sale.setUnitCode(info.getSupplierDictionaryValue());
                    sale.setUnitName(info.getSupplierContent());
                    if (!Optional.ofNullable(stock.getUnitCode()).orElse("库存").equals(info.getSupplierDictionaryValue())) {
                        error.add("销售的单位必须和库存的单位一致");
                    }
                }
            }
            //销售基商品含量
//            if (Objects.isNull(importVo.getSaleBaseProductContent())) {
//                error.add("销售基商品含量不能为空");
//            } else {
//                try {
//                    sale.setBaseProductContent(Integer.parseInt(importVo.getSaleBaseProductContent()));
//                } catch (Exception e) {
//                    error.add("销售基商品含量不正确");
//                }
//            }
            //销售拆零系数
//            if (Objects.isNull(importVo.getSaleZeroRemovalCoefficient())) {
//                error.add("销售拆零系数不能为空");
//            } else {
//                try {
//                    sale.setZeroRemovalCoefficient(Long.parseLong(importVo.getSaleZeroRemovalCoefficient()));
//                } catch (Exception e) {
//                    error.add("销售拆零系数不正确");
//                }
//            }
            //销售条形码
            if (Objects.isNull(importVo.getSaleBarCode())) {
                error.add("销售条形码不能为空");
            } else {
                sale.setBarCode(importVo.getSaleBarCode().trim());
            }
            //销售描述
            if (Objects.isNull(importVo.getDescription())) {
                error.add("销售描述不能为空");
            } else {
                sale.setDescription(importVo.getDescription().trim());
            }
            purchaseSaleStockReqVos.add(sale);
            this.resp.setPurchaseSaleStockReqVos(purchaseSaleStockReqVos);
            this.resp.setProductSkuBoxPackingDrafts(productSkuBoxPackingDrafts);
            return this;
        }

        //检查结算信息
        private CheckSku checkSettlement() {
            ProductSkuCheckoutDraft draft = new ProductSkuCheckoutDraft();
            draft.setSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            draft.setSkuName(this.resp.getProductSkuDraft().getSkuName());
            //结算方式
            if (Objects.isNull(importVo.getSettlementMethodName())) {
                error.add("结算方式不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getSettlementMethodName().trim());
                if (Objects.isNull(info)) {
                    error.add("未找到该名称对应的结算方式");
                } else {
                    draft.setSettlementMethodName(info.getSupplierContent());
                    draft.setSettlementMethodCode(info.getSupplierDictionaryValue());
                }
            }
            //进项税率
            if (Objects.isNull(importVo.getInputTaxRate())) {
                error.add("进项税率不能为空");
            } else {
                try {
                    draft.setInputTaxRate(NumberConvertUtils.stringParseLong(importVo.getInputTaxRate()));
                } catch (Exception e) {
                    error.add("进项税率格式不正确");
                }
            }
            //销项税率
            if (Objects.isNull(importVo.getOutputTaxRate())) {
                error.add("销项税率不能为空");
            } else {
                try {
                    draft.setOutputTaxRate(NumberConvertUtils.stringParseLong(importVo.getOutputTaxRate()));
                } catch (Exception e) {
                    error.add("销项税率格式不正确");
                }
            }
            //积分系数
            if (Objects.isNull(importVo.getIntegralCoefficient())) {
                error.add("积分系数不能为空");
            } else {
                try {
                    draft.setIntegralCoefficient(Long.parseLong(importVo.getIntegralCoefficient()));
                } catch (Exception e) {
                    error.add("积分系数格式不正确");
                }
            }
            //物流费奖励比例
            if (Objects.isNull(importVo.getLogisticsFeeAwardRatio())) {
                error.add("物流费奖励比例不能为空");
            } else {
                try {
                    draft.setLogisticsFeeAwardRatio(NumberConvertUtils.stringParseBigDecimal(importVo.getLogisticsFeeAwardRatio()).multiply(BigDecimal.valueOf(100)));
                } catch (Exception e) {
                    error.add("物流费奖励比例格式不正确");
                }
            }
            this.resp.setProductSkuCheckoutDraft(draft);
            return this;
        }

        //检查供应商
        private CheckSku checkSupplier() {
            List<ProductSkuSupplyUnitDraft> supply = Lists.newArrayList();
            ProductSkuSupplyUnitDraft supplyUnitDraft = new ProductSkuSupplyUnitDraft();
            supplyUnitDraft.setIsDefault((byte) 1);
            supplyUnitDraft.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            supplyUnitDraft.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            //供应商
            if (Objects.isNull(importVo.getSupplyUnitName())) {
                error.add("供应商不能为空");
            } else {
                SupplyCompany supplyCompany = supplyCompanyMap.get(importVo.getSupplyUnitName().trim());
                if (Objects.isNull(supplyCompany)) {
                    error.add("无对应名称的供应商");
                } else {
                    String s = repeatMap.get(supplyCompany.getSupplyName() + importVo.getSkuName().trim());
                    if (StringUtils.isNotBlank(s)) {
                        error.add("sku名称为:" + importVo.getSkuName() + "下的供应商名称为" + supplyCompany.getSupplyName() + "已重复");
                    } else {
                        supplyUnitDraft.setSupplyUnitCode(supplyCompany.getSupplyCode());
                        supplyUnitDraft.setSupplyUnitName(supplyCompany.getSupplyName());
                        repeatMap.put(supplyCompany.getSupplyName() + importVo.getSkuName().trim(), importVo.getSkuName());
                    }
                }
            }
            //含税采购价
            if (Objects.isNull(importVo.getTaxIncludedPrice())) {
                error.add("含税采购价不能为空");
            } else {
                try {
                    supplyUnitDraft.setTaxIncludedPrice(NumberConvertUtils.stringParseLong(importVo.getTaxIncludedPrice()));
                } catch (Exception e) {
                    error.add("含税采购价格式不正确");
                }
            }
            //联营扣点
            if (Objects.nonNull(importVo.getJointFranchiseRate())) {
                try {
                    supplyUnitDraft.setJointFranchiseRate(NumberConvertUtils.stringParseLong(importVo.getJointFranchiseRate().trim()));
                } catch (Exception e) {
                    error.add("联营扣点格式不正确");
                }
            }
            //返点
            if (Objects.nonNull(importVo.getPoint())) {
                try {
                    supplyUnitDraft.setPoint(NumberConvertUtils.stringParseLong(importVo.getPoint().trim()));
                } catch (Exception e) {
                    error.add("返点格式不正确");
                }
            }
            //厂商SKU编码
            if (Objects.nonNull(importVo.getFactorySkuCode())) {
                supplyUnitDraft.setFactorySkuCode(importVo.getFactorySkuCode().trim());
            }
            //供应商供货渠道类别
            if (Objects.isNull(importVo.getSupplyCategoriesSupplyChannelsName())) {
                error.add("供应商供货渠道类别不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getSupplyCategoriesSupplyChannelsName().trim());
                if (Objects.isNull(info)) {
                    error.add("未找到对应名称的供应商供货渠道类别");
                } else {
                    supplyUnitDraft.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                    supplyUnitDraft.setCategoriesSupplyChannelsName(info.getSupplierContent());
                }
            }
            supply.add(supplyUnitDraft);
            this.resp.setProductSkuSupplyUnitDrafts(supply);
            return this;
        }
        //检验导入sku
        private CheckSku checkSupplier2() {
            List<ProductSkuSupplyUnitDraft> supply = Lists.newArrayList();
            ProductSkuSupplyUnitDraft supplyUnitDraft = new ProductSkuSupplyUnitDraft();
            supplyUnitDraft.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            supplyUnitDraft.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            //供应商编码
            if (Objects.isNull(importVo.getSupplyUnitCode())) {
                error.add("供应商编码不能为空");
            } else {
                ProductSkuDraft productSkuDraft = productSkuDraftMap.get(importVo.getSkuCode());
                if (Objects.isNull(productSkuDraft)) {
                    error.add("无对应编码的sku");
                } else {
                    List<ProductSkuSupplyUnitDraft> supplyList = productSkuDraft.getSupplyList();
                    if (CollectionUtils.isEmpty(supplyList) || supplyList.size() < 1) {
                        error.add("无法找修改前的供应商信息");
                    } else {
                        if (Objects.isNull(importVo.getSupplyUnitName())) {
                            error.add("供应商名称不能为空");
                        } else {
                            SupplyCompany supplyCompany = supplyCompanyMap.get(importVo.getSupplyUnitName().trim());

                            String s = repeatMap.get(supplyCompany.getSupplyName() + importVo.getSkuName().trim());
                            if (StringUtils.isNotBlank(s)) {
                                error.add("sku名称为:" + importVo.getSkuName() + "下的供应商名称为" + supplyCompany.getSupplyName() + "已重复");
                            } else {
                                supplyUnitDraft.setSupplyUnitCode(supplyCompany.getSupplyCode());
                                supplyUnitDraft.setSupplyUnitName(supplyCompany.getSupplyName());
                                repeatMap.put(supplyCompany.getSupplyName() + importVo.getSkuName().trim(), importVo.getSkuName());
                            }
                        }
                    }
                }
            }
            //含税采购价
            if (Objects.isNull(importVo.getTaxIncludedPrice())) {
                error.add("含税采购价不能为空");
            } else {
                try {
                    supplyUnitDraft.setTaxIncludedPrice(NumberConvertUtils.stringParseLong(importVo.getTaxIncludedPrice()));
                } catch (Exception e) {
                    error.add("含税采购价格式不正确");
                }
            }
            //联营扣点
            if (Objects.nonNull(importVo.getJointFranchiseRate())) {
                try {
                    supplyUnitDraft.setJointFranchiseRate(NumberConvertUtils.stringParseLong(importVo.getJointFranchiseRate().trim()));
                } catch (Exception e) {
                    error.add("联营扣点格式不正确");
                }
            }
            //返点
            if (Objects.nonNull(importVo.getPoint())) {
                try {
                    supplyUnitDraft.setPoint(NumberConvertUtils.stringParseLong(importVo.getPoint().trim()));
                } catch (Exception e) {
                    error.add("返点格式不正确");
                }
            }
            //厂商SKU编码
            if (Objects.nonNull(importVo.getFactorySkuCode())) {
                supplyUnitDraft.setFactorySkuCode(importVo.getFactorySkuCode().trim());
            }
            //供应商供货渠道类别
            if (Objects.isNull(importVo.getSupplyCategoriesSupplyChannelsName())) {
                error.add("供应商供货渠道类别不能为空");
            } else {
                SupplierDictionaryInfo info = dicMap.get(importVo.getSupplyCategoriesSupplyChannelsName().trim());
                if (Objects.isNull(info)) {
                    error.add("未找到对应名称的供应商供货渠道类别");
                } else {
                    supplyUnitDraft.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                    supplyUnitDraft.setCategoriesSupplyChannelsName(info.getSupplierContent());
                }
            }
            supply.add(supplyUnitDraft);
            this.resp.setProductSkuSupplyUnitDrafts(supply);
            return this;
        }

        //检查价格
        private CheckSku checkPrice() {
            List<SkuPriceDraftReqVO> priceList = Lists.newArrayList();
            Map<String, SkuPriceDraftReqVO> price = PriceAndWarehouseMap.price;
            //爱亲渠道价
            if (Objects.isNull(importVo.getReadyCol67())) {
                error.add("爱亲渠道价不能为空");
            } else {
                SkuPriceDraftReqVO aiqinChannel = price.get("爱亲渠道价");
                try {
                    aiqinChannel.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol67()));
                } catch (Exception e) {
                    error.add("爱亲渠道价格式不正确");
                }
                priceList.add(aiqinChannel);
            }
            //萌贝树渠道价
            if (Objects.isNull(importVo.getReadyCol68())) {
                error.add("萌贝树渠道价不能为空");
            } else {
                SkuPriceDraftReqVO mengbeishuChannel = price.get("萌贝树渠道价");
                try {
                    mengbeishuChannel.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol68()));
                } catch (Exception e) {
                    error.add("萌贝树渠道价格式不正确");
                }
                priceList.add(mengbeishuChannel);
            }
            //小红马渠道价
            if (Objects.isNull(importVo.getReadyCol69())) {
                error.add("小红马渠道价不能为空");
            } else {
                SkuPriceDraftReqVO xiaohongmaChannel = price.get("小红马渠道价");
                try {
                    xiaohongmaChannel.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol69()));
                } catch (Exception e) {
                    error.add("小红马渠道价格式不正确");
                }
                priceList.add(xiaohongmaChannel);
            }
            //爱亲分销价
            if (Objects.isNull(importVo.getReadyCol70())) {
                error.add("爱亲分销价不能为空");
            } else {
                SkuPriceDraftReqVO aiqinDistribution = price.get("爱亲分销价");
                try {
                    aiqinDistribution.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol70()));
                } catch (Exception e) {
                    error.add("爱亲分销价格式不正确");
                }
                priceList.add(aiqinDistribution);
            }
            //萌贝树分销价
            if (Objects.isNull(importVo.getReadyCol71())) {
                error.add("萌贝树分销价不能为空");
            } else {
                SkuPriceDraftReqVO mengbeishuDistribution = price.get("萌贝树分销价");
                try {
                    mengbeishuDistribution.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol71()));
                } catch (Exception e) {
                    error.add("萌贝树分销价格式不正确");
                }
                priceList.add(mengbeishuDistribution);
            }
            //小红马分销价
            if (Objects.isNull(importVo.getReadyCol72())) {
                error.add("小红马分销价不能为空");
            } else {
                SkuPriceDraftReqVO xiaohongmaDistribution = price.get("小红马分销价");
                try {
                    xiaohongmaDistribution.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol72()));
                } catch (Exception e) {
                    error.add("小红马分销价格式不正确");
                }
                priceList.add(xiaohongmaDistribution);
            }
            //爱亲售价
            if (Objects.isNull(importVo.getReadyCol73())) {
                error.add("爱亲售价不能为空");
            } else {
                SkuPriceDraftReqVO aiqinSale = price.get("爱亲售价");
                try {
                    aiqinSale.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol73()));
                } catch (Exception e) {
                    error.add("爱亲售价格式不正确");
                }
                priceList.add(aiqinSale);
            }
            //萌贝树售价
            if (Objects.isNull(importVo.getReadyCol74())) {
                error.add("萌贝树售价不能为空");
            } else {
                SkuPriceDraftReqVO mengbeishuSale = price.get("萌贝树售价");
                try {
                    mengbeishuSale.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol74()));
                } catch (Exception e) {
                    error.add("萌贝树售价格式不正确");
                }
                priceList.add(mengbeishuSale);
            }
            //小红马售价
            if (Objects.isNull(importVo.getReadyCol75())) {
                error.add("小红马售价不能为空");
            } else {
                SkuPriceDraftReqVO xiaohongmaSale = price.get("小红马售价");
                try {
                    xiaohongmaSale.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol75()));
                } catch (Exception e) {
                    error.add("小红马售价格式不正确");
                }
                priceList.add(xiaohongmaSale);
            }
            this.resp.setProductSkuPrices(priceList);
            return this;
        }

        //检查配置
        private CheckSku checkConfig() {
            List<SaveSkuConfigReqVo> configReqVos = Lists.newArrayList();
            Map<String, SaveSkuConfigReqVo> warehouse = PriceAndWarehouseMap.warehouse;
            //华北仓
            if (Objects.isNull(importVo.getReadyCol76())) {
                error.add("华北仓状态不能为空");
            } else {
                SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol76());
                if (Objects.isNull(statusEnum)) {
                    error.add("无法找到华北仓的状态类型");
                } else {
                    SaveSkuConfigReqVo huabei = warehouse.get("华北仓");
                    huabei.setConfigStatus(statusEnum.getStatus());
                    huabei.setConfigStatusName(statusEnum.getName());
                    configReqVos.add(huabei);
                }
            }
            //华东仓
            if (Objects.isNull(importVo.getReadyCol77())) {
                error.add("华东仓状态不能为空");
            } else {
                SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol77());
                if (Objects.isNull(statusEnum)) {
                    error.add("无法找到华东仓状态的状态");
                } else {
                    SaveSkuConfigReqVo huadong = warehouse.get("华东仓");
                    huadong.setConfigStatus(statusEnum.getStatus());
                    huadong.setConfigStatusName(statusEnum.getName());
                    configReqVos.add(huadong);
                }
            }
            //华南仓
            if (Objects.isNull(importVo.getReadyCol78())) {
                error.add("华南仓状态不能为空");
            } else {
                SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol78());
                if (Objects.isNull(statusEnum)) {
                    error.add("无法找到华南仓状态的状态");
                } else {
                    SaveSkuConfigReqVo huanan = warehouse.get("华南仓");
                    huanan.setConfigStatus(statusEnum.getStatus());
                    huanan.setConfigStatusName(statusEnum.getName());
                    configReqVos.add(huanan);
                }
            }
            //西南仓
            if (Objects.isNull(importVo.getReadyCol79())) {
                error.add("西南仓状态不能为空");
            } else {
                SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol79());
                if (Objects.isNull(statusEnum)) {
                    error.add("无法找到西南仓状态的状态");
                } else {
                    SaveSkuConfigReqVo xinan = warehouse.get("西南仓");
                    xinan.setConfigStatus(statusEnum.getStatus());
                    xinan.setConfigStatusName(statusEnum.getName());
                    configReqVos.add(xinan);
                }
            }
            //华中仓
            if (Objects.isNull(importVo.getReadyCol79())) {
                error.add("华中仓状态不能为空");
            } else {
                SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol79());
                if (Objects.isNull(statusEnum)) {
                    error.add("无法找到华中仓状态的状态");
                } else {
                    SaveSkuConfigReqVo huazhong = warehouse.get("华中仓");
                    huazhong.setConfigStatus(statusEnum.getStatus());
                    huazhong.setConfigStatusName(statusEnum.getName());
                    configReqVos.add(huazhong);
                }
            }
            this.resp.setProductSkuConfigs(configReqVos);
            return this;
        }

        //检查厂家
        private CheckSku checkManufacturer() {
            //生产厂家
            if (Objects.nonNull(importVo.getManufacturerName())) {
                Manufacturer manufacturer = manufactureMap.get(importVo.getManufacturerName().trim());
                if (Objects.isNull(manufacturer)) {
                    error.add("未找到对应名称的生产厂家");
                } else {
                    List<ProductSkuManufacturerDraft> manufacturerDrafts = Lists.newArrayList();
                    ProductSkuManufacturerDraft draft = new ProductSkuManufacturerDraft();
                    draft.setManufacturerName(manufacturer.getName());
                    draft.setManufacturerCode(manufacturer.getManufacturerCode());
                    draft.setIsDefault((byte)1);
                    //厂方商品编号
                    if (Objects.isNull(importVo.getFactoryProductNumber())) {
                        error.add("厂方商品编号不能为空");
                    } else {
                        draft.setFactoryProductNumber(importVo.getFactoryProductNumber());
                    }
                    //保修地址
                    if (Objects.isNull(importVo.getAddress())) {
                        error.add("保修地址不能为空");
                    } else {
                        draft.setAddress(importVo.getAddress());
                    }
                    manufacturerDrafts.add(draft);
                    this.resp.setProductSkuManufacturerDrafts(manufacturerDrafts);
                }
            }
            return this;
        }

        //检查图片
        private CheckSku checkPic() {
            if (Objects.isNull(importVo.getPicFolderCode())) {
//                error.add("图片文件夹编号不能为空");
            }else {
                this.resp.getProductSkuDraft().setPicFolderCode(importVo.getPicFolderCode().trim());
            }
            return this;
        }

        //返回实体
        private AddSkuInfoReqVO getRespVO() {
            return this.resp;
        }

        //返回检查sku+供应商重复的校验map
        private Map<String, String> getReapMap() {
            return this.repeatMap;
        }

        //返回spu重复校验的map
        private Map<String, NewProduct> getSpuMap() {
            return this.spuMap;
        }

        //返回导入的vo
        private SkuInfoImport getSkuInfoImport() {
            this.importVo.setError(StringUtils.strip(this.error.toString(), "[]"));
            return this.importVo;
        }

        private CheckSku checkPurchaseGroup() {
            if (Objects.isNull(importVo.getProcurementSectionName())) {
                error.add("采购组不能为空");
            }else {
                PurchaseGroupDTO purchaseGroup = purchaseGroupMap.get(importVo.getProcurementSectionName());
                if (Objects.isNull(purchaseGroup)) {
                    error.add("无对应名称的采购组");
                }else {
                    this.resp.getProductSkuDraft().setProcurementSectionCode(purchaseGroup.getPurchaseGroupCode());
                    this.resp.getProductSkuDraft().setProcurementSectionName(purchaseGroup.getPurchaseGroupName());
                }
            }
            return this;
        }
    }
}

