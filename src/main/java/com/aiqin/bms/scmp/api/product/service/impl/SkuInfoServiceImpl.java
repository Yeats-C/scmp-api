package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapProductSku;
import com.aiqin.bms.scmp.api.abutment.domain.request.SapProductSkuBase;
import com.aiqin.bms.scmp.api.abutment.domain.request.SapSkuSale;
import com.aiqin.bms.scmp.api.abutment.domain.request.SapSkuStorageFinancial;
import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.excel.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaForSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.*;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceChannelRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceProjectRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.PriceChannelForChangePrice;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.supplierInfoVO;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsWmsRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.product.service.impl.skuimport.CheckSkuNew;
import com.aiqin.bms.scmp.api.product.service.impl.skuimport.CheckSkuUpdate;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectResponse;
import com.aiqin.bms.scmp.api.purchase.manager.DataManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.FilePathEnum;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordItemReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
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
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private ApplyProductSkuConfigSpareWarehouseMapper applySpareWarehouseMapper;
    @Value("${sap.product}")
    private String PRODUCT_URL;
    @Resource
    private PriceChannelMapper priceChannelMapper;
    @Autowired
    ProductSkuBoxPackingDao productSkuBoxPackingDao;
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
    private ProductSkuSalesInfoDao productSkuSalesInfoDao;
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
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private DataManageService dataManageService;
    @Autowired
    private ApprovalFileInfoService approvalFileInfoService;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private ProductSkuConfigMapper productSkuConfigMapper;
    @Autowired
    private ProductSkuFileDao productSkuFileDao;
    @Autowired
    ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    ProductSkuDisInfoDao productSkuDisInfoDao;
    @Autowired
    WarehouseDao warehouseDao;
    @Autowired
    ProductSkuInspReportDao productSkuInspReportDao;

    @Autowired
    private CodeUtils codeUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO) {
        log.info("新增或修改sku商品信息，传入参数是[{}]", JsonUtil.toJson(addSkuInfoReqVO));
        if (null != addSkuInfoReqVO && null != addSkuInfoReqVO.getProductSkuDraft()){
            //SKU基本信息
            ProductSkuDraft productSkuDraft = addSkuInfoReqVO.getProductSkuDraft();
            // 修改内容
            StringBuilder changeStr = new StringBuilder();
            //判断SKU名称是否
            //临时表
            // int count = productSkuDraftMapper.checkName(productSkuDraft.getSkuCode(), productSkuDraft.getSkuName());
            // if(count > 0 ){
            //     throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU信息在申请表中已存在"));
            // }
            // //申请表
            // count = applyProductSkuMapper.checkName(productSkuDraft.getSkuCode(), productSkuDraft.getSkuName());
            // if(count > 0 ){
            //     throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU信息已经在审批中"));
            // }
            // count = productSkuInfoMapper.checkName(productSkuDraft.getSkuCode(), productSkuDraft.getSkuName());
            // if(count > 0 ){
            //     throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU信息已经存在"));
            // }
            //验证文件夹编码是否存在
            if(StringUtils.isNotBlank(productSkuDraft.getPicFolderCode())){
                int count = productSkuDraftMapper.checkPicFolderCode(productSkuDraft.getSkuCode(), productSkuDraft.getPicFolderCode());
                if(count > 0 ){
                    throw new BizException(MessageId.create(Project.SCMP_API, 13, "图片文件夹编码在申请表中已存在"));
                }
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
                    // productSkuDraft.setChangeContent(compareResult);
                    if (StringUtils.isNotBlank(compareResult)) {
                        changeStr.append("基本信息:").append(compareResult).append(";");
                    }
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
                // 由于要添加修改的内容，基本信息的修改放到最后
                // ((SkuInfoService) AopContext.currentProxy()).insertDraft(productSkuDraft);
                productCommonService.getInstance(productSkuDraft.getSkuCode(), HandleTypeCoce.UPDATE.getStatus(), ObjectTypeCode.SKU_MANAGEMENT.getStatus(),HandleTypeCoce.UPDATE_SKU.getName(),HandleTypeCoce.UPDATE.getName());
            } else {
                //EncodingRule encodingRule=encodingRuleDao.getNumberingType("PRODUCT_SKU_CODE");
                String redisCode = codeUtils.getRedisCode("PRODUCT_SKU_CODE");
                //Long thisCode = encodingRule.getNumberingValue();
                Long thisCode=Long.parseLong(redisCode);
                productSkuDraft.setSkuCode(String.valueOf(thisCode+1));
                productSkuDraft.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                productSkuDraft.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
                productSkuDraft.setChangeContent("新增SKU");
                log.info(JSON.toJSONString(productSkuDraft));
                // ((SkuInfoService) AopContext.currentProxy()).insertDraft(productSkuDraft);
                //encodingRuleDao.updateNumberValue(thisCode,encodingRule.getId());
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
            BigDecimal outputTaxRateL = BigDecimal.ONE;
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
                    log.error(Global.ERROR, e);
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
                    log.error(Global.ERROR, e);
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
                    log.error(Global.ERROR, e);
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
                BigDecimal inputTaxRateL = BigDecimal.ONE;
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
                    inputTaxRate = productSkuCheckoutDraft.getInputTaxRate().divide(new BigDecimal(100), 4, BigDecimal.ROUND_DOWN);
                    outputTaxRate = productSkuCheckoutDraft.getOutputTaxRate().divide(new BigDecimal(100), 4, BigDecimal.ROUND_DOWN);
                    if(flag) {
                        // 计算结算修改的内容
                        // 获取旧数据
                        ProductSkuCheckoutRespVo checkoutRespVo = productSkuCheckoutService.getBySkuCode(productSkuDraft.getSkuCode());
                        ProductSkuCheckoutDraft oldVo = new ProductSkuCheckoutDraft();
                        BeanCopyUtils.copy(checkoutRespVo, oldVo);
                        String checkoutStr = new BeanChangeUtil<ProductSkuCheckoutDraft>().contrastObj(oldVo, productSkuCheckoutDraft);
                        if (StringUtils.isNotBlank(checkoutStr)) {
                            changeStr.append("结算信息:").append(checkoutStr);
                        }
                    }
                }
                //供应商信息
                if (CollectionUtils.isNotEmpty(addSkuInfoReqVO.getProductSkuSupplyUnitDrafts())){
                    List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts = addSkuInfoReqVO.getProductSkuSupplyUnitDrafts();
                    //获取采购价格项目
//                    QueryPriceProjectRespVo purchasePriceProject = priceProjectService.getPurchasePriceProject();
//                    if(null == purchasePriceProject){
//                        throw new BizException(ResultCode.SKU_PURCHASE_PRICE_IS_EMPTY);
//                    }
                    final BigDecimal finalInputTaxRate = inputTaxRate;
                    final BigDecimal finalInputTaxRateL = inputTaxRateL;

                    List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts = Lists.newArrayList();
                    productSkuSupplyUnitDrafts.forEach(item->{
                        item.setProductSkuCode(productSkuDraft.getSkuCode());
                        item.setProductSkuName(productSkuDraft.getSkuName());
                        item.setCreateBy(productSkuDraft.getCreateBy());
                        item.setUpdateBy(productSkuDraft.getUpdateBy());
                        item.setCreateTime(productSkuDraft.getCreateTime());
                        item.setUpdateTime(productSkuDraft.getUpdateTime());
                        //先把含税金额除以100兑换成元,含税金额/(1+税率) = 未税金额,最终结果*100转换成分,舍弃分以后的数字
                        BigDecimal taxNoPrice = item.getTaxIncludedPrice().divide(BigDecimal.ONE.add(finalInputTaxRate),4,BigDecimal.ROUND_HALF_UP);
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
//                        skuPriceDraftReqVO.setPriceItemCode(purchasePriceProject.getPriceProjectCode());
//                        skuPriceDraftReqVO.setPriceItemName(purchasePriceProject.getPriceProjectName());
//                        skuPriceDraftReqVO.setPriceTypeCode(purchasePriceProject.getPriceTypeCode());
//                        skuPriceDraftReqVO.setPriceTypeName(purchasePriceProject.getPriceTypeName());
//                        skuPriceDraftReqVO.setPriceAttributeCode(purchasePriceProject.getPriceCategoryCode());
//                        skuPriceDraftReqVO.setPriceAttributeName(purchasePriceProject.getPriceCategoryName());
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
//                        productSkuPrices.add(skuPriceDraftReqVO);
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
                    if (flag) {
                        // 计算生产厂家修改内容
                        // 获取旧数据
                        List<ProductSkuManufacturerRespVo> manufacturerRespVos = productSkuManufacturerService.getList(productSkuDraft.getSkuCode());
                        BeanChangeUtil<ProductSkuManufacturerDraft> beanChangeUtil = new BeanChangeUtil<>();
                        StringBuilder str = new StringBuilder();
                        if(CollectionUtils.isEmpty(manufacturerRespVos)) {
                            // 如果旧数据为空，全为新增
                            for (int i = 0; i < productSkuManufacturerDrafts.size(); i++) {
                                String addStr = beanChangeUtil.addOrDeleteObj(productSkuManufacturerDrafts.get(i), (i + 1) + "", true, "生产厂家");
                                str.append(addStr);
                            }

                        } else {
                            List<ProductSkuManufacturerDraft> oldVos = BeanCopyUtils.copyList(manufacturerRespVos, ProductSkuManufacturerDraft.class);
                            String manufacturerStr = beanChangeUtil.getChangeInfo(oldVos, productSkuManufacturerDrafts, "生产厂家");
                            str.append(manufacturerStr);
                        }
                        if (StringUtils.isNotBlank(str.toString())) {
                            changeStr.append("生产厂家信息:").append(str.toString());
                        }

                    }
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
                outputTaxRate = productSkuCheckoutRespVo.getOutputTaxRate().divide(new BigDecimal(10000), 4, BigDecimal.ROUND_DOWN);
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
                if (flag) {
                    // 计算进销存分销修改内容
                    // 查询旧数据 查询正式表中的数据
                    List<PurchaseSaleStockRespVo> respVos = productSkuDisInfoService.getList(productSkuDraft.getSkuCode());
                    if(CollectionUtils.isEmpty(respVos)){
                        throw new BizException(ResultCode.SALE_EMPTY);
                    }
                    if (respVos.size() != 1) {
                        throw new BizException(ResultCode.SALE_ONE);
                    }
                    ProductSkuDisInfoDraft oldVo = new ProductSkuDisInfoDraft();
                    oldVo.setSpec(respVos.get(0).getSpec());
                    oldVo.setUnitName(respVos.get(0).getUnitName());
                    oldVo.setDeliveryCode(respVos.get(0).getBarCode());
                    oldVo.setBaseProductContent(respVos.get(0).getBaseProductContent());
                    oldVo.setZeroRemovalCoefficient(respVos.get(0).getZeroRemovalCoefficient());
                    oldVo.setMaxOrderNum(respVos.get(0).getMaxOrderNum());
                    String compareResult = new BeanChangeUtil<ProductSkuDisInfoDraft>().contrastObj(oldVo, productSkuDisInfoDraft);
                    if(StringUtils.isNotBlank(compareResult)) {
                        changeStr.append("分销:").append(compareResult);
                    }
                }
                productSkuDisInfoService.insertDraft(productSkuDisInfoDraft);
            } catch (Exception e) {
                log.error(Global.ERROR, e);
                throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
            }
            // if (!flag) {
            //价格
            if (null != addSkuInfoReqVO.getProductSkuPrices() && addSkuInfoReqVO.getProductSkuPrices().size() > 0){
                List<SkuPriceDraftReqVO> temps = addSkuInfoReqVO.getProductSkuPrices();
                final BigDecimal finalOutputTaxRate = outputTaxRate;
                final BigDecimal finalOutputTaxRateL = outputTaxRateL;
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
                    BigDecimal taxIncludedPrice = item.getPriceTax().divide(BigDecimal.ONE.add(finalOutputTaxRate),4,BigDecimal.ROUND_HALF_UP);
                    item.setPriceNoTax(taxIncludedPrice);
                    //创建/修改时间/人
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());
                });
                productSkuPrices.addAll(temps);
                // }
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
                for (ProductSkuPicturesDraft item : productSkuPicturesDrafts) {
                    item.setProductSkuCode(productSkuDraft.getSkuCode());
                    item.setProductSkuName(productSkuDraft.getSkuName());
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());

                }
                if (flag) {
                    // 计算商品图片是否变更
                    List<ProductSkuPicturesRespVo> picturesRespVos = productSkuPicturesService.getList(productSkuDraft.getSkuCode());
                    if (CollectionUtils.isEmpty(picturesRespVos)) {
                        changeStr.append("图片:商品图片变更;");
                    } else {
                        List<ProductSkuPicturesDraft> oldVos = BeanCopyUtils.copyList(picturesRespVos, ProductSkuPicturesDraft.class);
                        String compareResult = new BeanChangeUtil<ProductSkuPicturesDraft>().getChangeInfo(oldVos, productSkuPicturesDrafts, "商品图片");
                        if(StringUtils.isNotBlank(compareResult)) {
                            changeStr.append("图片:商品图片变更;");
                        }
                    }
                }
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
                if (flag) {
                    // 计算商品说明是否变更
                    List<ProductSkuPicDescRespVo> picDescRespVos = productSkuPicDescService.getList(productSkuDraft.getSkuCode());
                    if(CollectionUtils.isEmpty(picDescRespVos)) {
                        changeStr.append("商品说明:商品说明变更;");
                    } else {
                        List<ProductSkuPicDescDraft> oldVos = BeanCopyUtils.copyList(picDescRespVos, ProductSkuPicDescDraft.class);
                        String compareResult = new BeanChangeUtil<ProductSkuPicDescDraft>().getChangeInfo(oldVos, productSkuPicDescDrafts, "商品说明");
                        if (StringUtils.isNotBlank(compareResult)) {
                            changeStr.append("商品说明:商品说明变更;");
                        }
                    }
                }
                productSkuPicDescService.insertDraftList(productSkuPicDescDrafts);
            }
            //sku文件管理
            String destinationFileKey =  FilePathEnum.PRODUCT_FILE.getFilePath()+productSkuDraft.getSkuCode()+"/";
            if (null != addSkuInfoReqVO.getProductSkuFileDrafts() && addSkuInfoReqVO.getProductSkuFileDrafts().size() > 0){
                List<ProductSkuFileDraft> productSkuFileDrafts = addSkuInfoReqVO.getProductSkuFileDrafts();
                for (ProductSkuFileDraft item : productSkuFileDrafts) {
                    item.setSkuCode(productSkuDraft.getSkuCode());
                    item.setSkuName(productSkuDraft.getSkuName());
                    item.setCreateBy(productSkuDraft.getCreateBy());
                    item.setUpdateBy(productSkuDraft.getUpdateBy());
                    item.setCreateTime(productSkuDraft.getCreateTime());
                    item.setUpdateTime(productSkuDraft.getUpdateTime());
                    //重置图片URL
                    if (StringUtils.isNotBlank(item.getFilePath())) {
                        Map<String, String> map = fileInfoService.getKeyAndType(item.getFilePath());
                        if(null != map) {
                            String destinationKey = destinationFileKey + UUID.randomUUID() + map.get("contentType");
                            if (!map.get("key").endsWith(destinationKey)) {
                                String newUrl = fileInfoService.copyObject(map.get("key"), destinationFileKey + UUID.randomUUID() + map.get("contentType"), true);
                                if (StringUtils.isNotBlank(newUrl)) {
                                    item.setFilePath(newUrl);
                                }
                            }
                        }
                    }
                }
                if (flag) {
                    // 计算文件修改内容
                    // 获取旧数据
                    List<ProductSkuFileRespVO> fileRespVOS = productSkuFileService.getList(productSkuDraft.getSkuCode());
                    BeanChangeUtil<ProductSkuFileDraft> beanChangeUtil = new BeanChangeUtil<>();
                    StringBuilder str = new StringBuilder();
                    if (CollectionUtils.isEmpty(fileRespVOS)) {
                        // 旧数据为空，新数据全为新增
                        for (int i = 0; i < productSkuFileDrafts.size(); i++) {
                            String fileStr = beanChangeUtil.addOrDeleteObj(productSkuFileDrafts.get(i), (i + 1) + "", true, "文件");
                            str.append(fileStr);
                        }
                    } else {
                        List<ProductSkuFileDraft> oldVos = BeanCopyUtils.copyList(fileRespVOS, ProductSkuFileDraft.class);
                        String fileStr = beanChangeUtil.getChangeInfo(oldVos, productSkuFileDrafts, "文件");
                        str.append(fileStr);
                    }
                    if(StringUtils.isNotBlank(str.toString())) {
                        changeStr.append("商品文件:").append(str.toString());
                    }
                }
                productSkuFileService.insertDraftList(productSkuFileDrafts);
            }
            // 由于需要获得修改的内容，所以把修改基本信息放到最后
            productSkuDraft.setChangeContent(changeStr.toString());
            // log.info("=============================================================修改内容:" + changeStr.toString());
            ((SkuInfoService) AopContext.currentProxy()).insertDraft(productSkuDraft);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int importSaveDraftSkuInfo(AddSkuInfoReqVO addSkuInfoReqVO) {
        ProductSkuDraft productSkuDraft = addSkuInfoReqVO.getProductSkuDraft();
        productSkuDraft.setCreateBy(getUser().getPersonName());
        productSkuDraft.setCreateTime(new Date());
        String skuCode = productSkuDraft.getSkuCode();
        //保存主表
        saveProductSkuDraft(productSkuDraft, skuCode);
        //渠道
        saveProductSkuChannelDraft(addSkuInfoReqVO, productSkuDraft);
        //SKU标签信息
        saveUseTagRecord(addSkuInfoReqVO, productSkuDraft);
        //进销存
        saveInvoicing(addSkuInfoReqVO, productSkuDraft);
        //包装信息
        savePackage(addSkuInfoReqVO, productSkuDraft);
        //结算信息
        saveCheckout(addSkuInfoReqVO, productSkuDraft);
        //商产厂家
        saveManufacturer(addSkuInfoReqVO, productSkuDraft);
        return 0;
    }

    public void saveManufacturer(AddSkuInfoReqVO addSkuInfoReqVO, ProductSkuDraft productSkuDraft) {
        List<ProductSkuManufacturerRespVo> list = productSkuManufacturerService.getList(productSkuDraft.getSkuCode());
        List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts = addSkuInfoReqVO.getProductSkuManufacturerDrafts();
        List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts1 = BeanCopyUtils.copyList(list, ProductSkuManufacturerDraft.class);
        if(CollectionUtils.isNotEmpty(productSkuManufacturerDrafts)){
            ProductSkuManufacturerDraft productSkuManufacturerDraft = productSkuManufacturerDrafts.get(0);
            Optional<ProductSkuManufacturerDraft> first = productSkuManufacturerDrafts1.stream().filter(o -> o.getManufacturerCode().equals(productSkuManufacturerDraft.getManufacturerCode())).findFirst();
            if(first.isPresent()){
                ProductSkuManufacturerDraft productSkuManufacturerDraft1 = first.get();
                BeanCopyUtils.copyValueWithoutNull(productSkuManufacturerDraft,productSkuManufacturerDraft1);
                productSkuManufacturerDraft1.setIsDefault((byte) 0);
            }else {
                productSkuManufacturerDrafts1.addAll(productSkuManufacturerDrafts);
            }
        }

        if (CollectionUtils.isNotEmpty(productSkuManufacturerDrafts1)) {
            productSkuManufacturerDrafts1.forEach(item -> {
                item.setId(null);
                item.setProductSkuCode(productSkuDraft.getSkuCode());
                item.setProductSkuName(productSkuDraft.getSkuName());
                item.setUsageStatus(StatusTypeCode.USE.getStatus());
                item.setCreateBy(productSkuDraft.getCreateBy());
                item.setUpdateBy(productSkuDraft.getUpdateBy());
                item.setCreateTime(productSkuDraft.getCreateTime());
                item.setUpdateTime(productSkuDraft.getUpdateTime());
            });
            productSkuManufacturerService.insertDraftList(productSkuManufacturerDrafts1);
        }
    }

    public void saveCheckout(AddSkuInfoReqVO addSkuInfoReqVO, ProductSkuDraft productSkuDraft) {
        //结算信息
        ProductSkuCheckoutDraft productSkuCheckoutDraft = addSkuInfoReqVO.getProductSkuCheckoutDraft();
        ProductSkuCheckoutRespVo bySkuCode = productSkuCheckoutService.getBySkuCode(productSkuDraft.getSkuCode());
        ProductSkuCheckoutDraft copy = BeanCopyUtils.copy(bySkuCode, ProductSkuCheckoutDraft.class);
        if (null != productSkuCheckoutDraft) {
            BeanCopyUtils.copyValueWithoutNull(productSkuCheckoutDraft,copy);
            copy.setId(null);
            copy.setSkuCode(productSkuDraft.getSkuCode());
            copy.setSkuName(productSkuDraft.getSkuName());
            copy.setCreateBy(productSkuDraft.getCreateBy());
            copy.setUpdateBy(productSkuDraft.getUpdateBy());
            copy.setCreateTime(productSkuDraft.getCreateTime());
            copy.setUpdateTime(productSkuDraft.getUpdateTime());
        }
        productSkuCheckoutService.insertDraft(copy);
    }

    public void savePackage(AddSkuInfoReqVO addSkuInfoReqVO, ProductSkuDraft productSkuDraft) {
        //查包装信息
        List<ProductSkuBoxPackingRespVo> list = productSkuBoxPackingService.getList(productSkuDraft.getSkuCode());
        List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts1 = BeanCopyUtils.copyList(list, ProductSkuBoxPackingDraft.class);
        List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts = addSkuInfoReqVO.getProductSkuBoxPackingDrafts();
        Map<String, ProductSkuBoxPackingDraft> collect = productSkuBoxPackingDrafts.stream().collect(Collectors.toMap(ProductSkuBoxPackingDraft::getUnitCode, Function.identity()));
        for (ProductSkuBoxPackingDraft respVo : productSkuBoxPackingDrafts1) {
            //如果原来的有，则做更新
            ProductSkuBoxPackingDraft productSkuBoxPackingRespVo = collect.get(respVo.getUnitCode());
            if (Objects.nonNull(productSkuBoxPackingRespVo)) {
                BeanCopyUtils.copyValueWithoutNull(productSkuBoxPackingRespVo,respVo);
                productSkuBoxPackingDrafts.remove(productSkuBoxPackingRespVo);
            }
        }
        productSkuBoxPackingDrafts.addAll(productSkuBoxPackingDrafts1);
        //获取包装信息
        if (CollectionUtils.isNotEmpty(productSkuBoxPackingDrafts)) {
            productSkuBoxPackingDrafts.forEach(item -> {
                item.setId(null);
                item.setProductSkuCode(productSkuDraft.getSkuCode());
                item.setProductSkuName(productSkuDraft.getSkuName());
                item.setCreateBy(productSkuDraft.getCreateBy());
                item.setUpdateBy(productSkuDraft.getUpdateBy());
                item.setCreateTime(productSkuDraft.getCreateTime());
                item.setUpdateTime(productSkuDraft.getUpdateTime());
            });
            productSkuBoxPackingService.insertDraftList(productSkuBoxPackingDrafts);
        }
    }

    public void saveInvoicing(AddSkuInfoReqVO addSkuInfoReqVO, ProductSkuDraft productSkuDraft) {
        //初始化销项税率
        List<PurchaseSaleStockReqVo> purchaseSaleStockReqVos = addSkuInfoReqVO.getPurchaseSaleStockReqVos();
        //获取库存信息
        List<PurchaseSaleStockReqVo>  stockList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.STOCK.getStatus(),item.getType())).collect(Collectors.toList());
        //查库存信息
        List<PurchaseSaleStockRespVo> list = productSkuStockInfoService.getList(productSkuDraft.getSkuCode());
        if (CollectionUtils.isEmpty(list) || list.size() != 1) {
            throw new BizException(ResultCode.PURCHASE_SALE_STOCK_EMPTY);
        }
        PurchaseSaleStockRespVo purchaseSaleStockRespVo = list.get(0);
        PurchaseSaleStockReqVo copy = BeanCopyUtils.copy(purchaseSaleStockRespVo, PurchaseSaleStockReqVo.class);
        PurchaseSaleStockReqVo stockReqVo = stockList.get(0);
        BeanCopyUtils.copyValueWithoutNull(stockReqVo, copy);
        try {
            ProductSkuStockInfoDraft productSkuStockInfoDraft =  BeanCopyUtils.copy(copy,ProductSkuStockInfoDraft.class);
            productSkuStockInfoDraft.setId(null);
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
            log.error("error", e);
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
        //获取采购信息
        List<PurchaseSaleStockReqVo>  purchaseList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.PURCHASE.getStatus(),item.getType())).collect(Collectors.toList());
        List<PurchaseSaleStockRespVo> list1 = productSkuPurchaseInfoService.getList(productSkuDraft.getSkuCode());
        if (CollectionUtils.isEmpty(list1) || list1.size() != 1) {
            throw new BizException(ResultCode.PURCHASE_SALE_STOCK_EMPTY);
        }
        PurchaseSaleStockRespVo purchaseSaleStockRespVo1 = list1.get(0);
        PurchaseSaleStockReqVo copy1 = BeanCopyUtils.copy(purchaseSaleStockRespVo1, PurchaseSaleStockReqVo.class);
        PurchaseSaleStockReqVo stockReqVo1 = purchaseList.get(0);
        BeanCopyUtils.copyValueWithoutNull(stockReqVo1, copy1);
        try {
            ProductSkuPurchaseInfoDraft productSkuPurchaseInfoDraft =  BeanCopyUtils.copy(copy1,ProductSkuPurchaseInfoDraft.class);
            productSkuPurchaseInfoDraft.setId(null);
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
            log.error("error", e);
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }

        //获取分销信息
        List<PurchaseSaleStockReqVo> saleList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.SALE.getStatus(),item.getType())).collect(Collectors.toList());
        List<PurchaseSaleStockRespVo> list2 = productSkuDisInfoService.getList(productSkuDraft.getSkuCode());
        if (CollectionUtils.isEmpty(list2) || list2.size() != 1) {
            throw new BizException(ResultCode.PURCHASE_SALE_STOCK_EMPTY);
        }
        PurchaseSaleStockRespVo purchaseSaleStockRespVo2 = list2.get(0);
        PurchaseSaleStockReqVo copy2 = BeanCopyUtils.copy(purchaseSaleStockRespVo2, PurchaseSaleStockReqVo.class);
        PurchaseSaleStockReqVo stockReqVo2 = saleList.get(0);
        BeanCopyUtils.copyValueWithoutNull(stockReqVo2, copy2);
        try {
            ProductSkuDisInfoDraft productSkuDisInfoDraft = BeanCopyUtils.copy(copy2, ProductSkuDisInfoDraft.class);
            productSkuDisInfoDraft.setId(null);
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
            log.error("error", e);
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }

        //获取门店销售信息
        List<PurchaseSaleStockReqVo> storeSaleList = purchaseSaleStockReqVos.stream().filter(item-> Objects.equals(StatusTypeCode.STORE_SALE.getStatus(),item.getType())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(storeSaleList)){
            //验证输入的条形码是否重复
            List<String> salesCodes = storeSaleList.stream().map(item -> item.getSalesCode()).distinct().collect(Collectors.toList());
            if (storeSaleList.size() != salesCodes.size()) {
                throw new BizException(MessageId.create(Project.SCMP_API, 69, "门店销售的条形码有重复,请重新输入"));
            }
            //验证输入的条形码是否在数据库中存在
            List<String> salesCodeTmps = productSkuSalesInfoService.checkSalesCodes(salesCodes,productSkuDraft.getSkuCode());
            if(CollectionUtils.isNotEmpty(salesCodeTmps)){
                throw new BizException(MessageId.create(Project.SCMP_API, 69,  "条形码["+ StringUtils.join(salesCodeTmps, ",")+"],在数据库中存在相同条形码"));
            }
        }
        //查
        List<PurchaseSaleStockRespVo> list3 = productSkuSalesInfoService.getList(productSkuDraft.getSkuCode());
        List<PurchaseSaleStockReqVo> purchaseSaleStockReqVos1 = BeanCopyUtils.copyList(list3, PurchaseSaleStockReqVo.class);
        PurchaseSaleStockReqVo purchaseSaleStockRespVo3 = purchaseSaleStockReqVos1.stream().filter(o->o.getIsDefault() == 1).findFirst().orElse(null);
        if (Objects.isNull(purchaseSaleStockRespVo3)) {
            throw new BizException(ResultCode.PURCHASE_SALE_STOCK_EMPTY);
        }
        BeanCopyUtils.copyValueWithoutNull(storeSaleList.get(0),purchaseSaleStockRespVo3);
        try {
            List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts = BeanCopyUtils.copyList(purchaseSaleStockReqVos1,ProductSkuSalesInfoDraft.class);
            productSkuSalesInfoDrafts.forEach(item->{
                item.setId(null);
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
            log.error("error", e);
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
    }

    public void saveUseTagRecord(AddSkuInfoReqVO addSkuInfoReqVO, ProductSkuDraft productSkuDraft) {
        List<SaveUseTagRecordItemReqVo> tagInfoList = addSkuInfoReqVO.getTagInfoList();
        //查询标签
        if (CollectionUtils.isNotEmpty(tagInfoList)) {
            List<ApplyUseTagRecord> applyUseTagRecords = Lists.newArrayList();
            tagInfoList.forEach(item -> {
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
    }

    @Override
    public void saveProductSkuChannelDraft(AddSkuInfoReqVO addSkuInfoReqVO, ProductSkuDraft productSkuDraft) {
        List<ProductSkuChannelDraft> productSkuChannelDrafts = addSkuInfoReqVO.getProductSkuChannelDrafts();
        //查数据
        List<ProductSkuChannelRespVo> draftList = productSkuChannelService.getList(productSkuDraft.getSkuCode());
        List<ProductSkuChannelDraft> oldChannelList = BeanCopyUtils.copyList(draftList, ProductSkuChannelDraft.class);
        Map<String, ProductSkuChannelDraft> collect = oldChannelList.stream().collect(Collectors.toMap(ProductSkuChannelDraft::getPriceChannelCode, Function.identity()));
        if(CollectionUtils.isNotEmpty(productSkuChannelDrafts)){
            productSkuChannelDrafts.forEach(o->{
                if(Objects.isNull(collect.get(o.getPriceChannelCode()))){
                    oldChannelList.add(o);
                }
            });
        }
        for (ProductSkuChannelDraft item : oldChannelList) {
            item.setSkuCode(productSkuDraft.getSkuCode());
            item.setSkuName(productSkuDraft.getSkuName());
            item.setCreateBy(productSkuDraft.getCreateBy());
            item.setUpdateBy(productSkuDraft.getUpdateBy());
            item.setCreateTime(productSkuDraft.getCreateTime());
            item.setUpdateTime(productSkuDraft.getUpdateTime());
        }
        if(!oldChannelList.isEmpty()){
            productSkuChannelService.insertDraftList(oldChannelList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProductSkuDraft(ProductSkuDraft productSkuDraft, String skuCode) {
        //查正式表数据
        ProductSkuDraft copy = productSkuDraftMapper.getOfficialBySkuCode(skuCode);
        BeanCopyUtils.copyValueWithoutNull(productSkuDraft, copy);
        copy.setId(null);

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
        ((SkuInfoService) AopContext.currentProxy()).insertDraft(copy);
        productCommonService.getInstance(productSkuDraft.getSkuCode(), HandleTypeCoce.UPDATE.getStatus(), ObjectTypeCode.SKU_MANAGEMENT.getStatus(),HandleTypeCoce.UPDATE_SKU.getName(),HandleTypeCoce.UPDATE.getName());
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
        // 修改spu
        NewProduct spuInfo = addSkuInfoReqVO.getSpuInfo();
        NewProductUpdateReqVO newProductUpdateReqVO = new NewProductUpdateReqVO();
        BeanCopyUtils.copy(spuInfo, newProductUpdateReqVO);
        newProductService.updateProduct(newProductUpdateReqVO);
        addSkuInfoReqVO.getProductSkuDraft().setPicFolderCode(skuRespVo.getPicFolderCode());
        addSkuInfoReqVO.getProductSkuDraft().setApplyType(skuRespVo.getApplyType());
        addSkuInfoReqVO.getProductSkuDraft().setApplyTypeName(skuRespVo.getApplyTypeName());
        List<String> skuCodes = Lists.newArrayList();
        skuCodes.add(addSkuInfoReqVO.getProductSkuDraft().getSkuCode());
        ((SkuInfoService)AopContext.currentProxy()).deleteProductSkuDraft(skuCodes);
        return  ((SkuInfoService)AopContext.currentProxy()).saveDraftSkuInfo(addSkuInfoReqVO);
    }

    /**
     * 更新sku所有信息
     *
     * @param addSkuInfoReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDraftSkuInfoForImport(AddSkuInfoReqVO addSkuInfoReqVO) {
        if(Objects.isNull(addSkuInfoReqVO.getProductSkuDraft().getSkuCode())){
            throw new BizException(ResultCode.SKU_CODE_EMPTY);
        }
        addSkuInfoReqVO.getProductSkuDraft().setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
        addSkuInfoReqVO.getProductSkuDraft().setApplyTypeName(StatusTypeCode.UPDATE_APPLY.getName());
        List<String> skuCodes = Lists.newArrayList();
        skuCodes.add(addSkuInfoReqVO.getProductSkuDraft().getSkuCode());
        ((SkuInfoService)AopContext.currentProxy()).deleteProductSkuDraft(skuCodes);
        return  ((SkuInfoService)AopContext.currentProxy()).importSaveDraftSkuInfo(addSkuInfoReqVO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int batchInsertDraft(List<ProductSkuDraft> productSkuDrafts) {
        return productSkuDraftMapper.batchInsert(productSkuDrafts);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public int insertApply(ApplyProductSku applyProductSku) {
        return  applyProductSkuMapper.insert(applyProductSku);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveSkuApplyInfo(SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO, String approvalName, String approvalRemark) {
        //验证重复 如果有审核中的数据，不能提交流程
        StringBuilder sb = new StringBuilder();
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
        //验证是否是同一种申请类型
        validateSameApply(productSkuDrafts);
        String formNo =  "SP"+ IdSequenceUtils.getInstance().nextId();
        //EncodingRule encodingRule = encodingRuleDao.getNumberingType("APPLY_PRODUCT_CODE");
        String code = codeUtils.getRedisCode("APPLY_PRODUCT_CODE");
        //long code = encodingRule.getNumberingValue();
        List<ApplyProductSku> applyProductSkus = BeanCopyUtils.copyList(productSkuDrafts, ApplyProductSku.class);
        Date currentDate = new Date();
        String applyBy = getUser().getPersonName();
        Set<String> isRepeatSet = Sets.newHashSet();
        applyProductSkus.forEach(item->{
            item.setApplyCode(code);
            item.setSelectionEffectiveTime(saveSkuApplyInfoReqVO.getSelectionEffectiveTime());
            item.setSelectionEffectiveStartTime(saveSkuApplyInfoReqVO.getSelectionEffectiveStartTime());
            item.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
            item.setUpdateBy(applyBy);
            item.setUpdateTime(currentDate);
            item.setFormNo(formNo);
            item.setApprovalName(approvalName);
            item.setApprovalRemark(approvalRemark);
            isRepeatSet.add(item.getProcurementSectionCode());
        });
        if(isRepeatSet.size()>1){
            throw  new BizException(ResultCode.PURCHASE_GROUP_REPEAT);
        }
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
                    item.setApplyUseObjectCode(code);
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
        //保存审批附件信息
        approvalFileInfoService.batchSave(saveSkuApplyInfoReqVO.getApprovalFileInfos(),String.valueOf(code),formNo,ApprovalFileTypeEnum.SKU.getType());
        //修改申请编码
        //encodingRuleDao.updateNumberValue(Long.valueOf(code),encodingRule.getId());
        if (CollectionUtils.isNotEmpty(applyProductSkus)){
            //调用审批接口
            workFlow(String.valueOf(code),formNo,applyProductSkus,saveSkuApplyInfoReqVO.getDirectSupervisorCode(),approvalName,saveSkuApplyInfoReqVO.getPositionCode());
        }
        return formNo;
    }

    private void validateSameApply(List<ProductSkuDraft> productSkuDrafts) {
        Set<Byte> beSameApply = Sets.newHashSet();
        for (ProductSkuDraft draft : productSkuDrafts) {
            beSameApply.add(draft.getApplyType());
        }
        if (beSameApply.size()>1) {
            throw new BizException(ResultCode.NOT_SAME_APPLY);
        }
    }

    @Override
    public ProductSkuDetailResp getSkuDraftInfo(String skuCode) {
        ProductSkuDetailResp detailResp = new ProductSkuDetailResp();
        //SKU基本信息
        ProductSkuRespVo skuRespVo = productSkuDao.getSkuDraft(skuCode);
        if (null == skuRespVo) {
            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
        }
        // spu信息
        NewProductResponseVO productResponseVO = newProductService.getDetail(skuRespVo.getProductCode());
        if (Objects.nonNull(productResponseVO)) {
            detailResp.setSpuInfo(productResponseVO);
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
            List<ProductSkuSupplyUnitRespVo> draftList = productSkuSupplyUnitService.getDraftList(skuCode);
            if (CollectionUtils.isEmpty(draftList)) {
                draftList = productSkuSupplyUnitService.getList(skuCode);
            }
            detailResp.setProductSkuSupplyUnits(draftList);
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
        List<ProductSkuPicturesRespVo> draftList1 = productSkuPicturesService.getDraftList(skuCode);
        if (CollectionUtils.isEmpty(draftList1)) {
            draftList1 = productSkuPicturesService.getList(skuCode);
        }
        detailResp.setProductSkuPictures(draftList1);
        //sku商品说明
        List<ProductSkuPicDescRespVo> draftList2 = productSkuPicDescService.getDraftList(skuCode);
        if (CollectionUtils.isEmpty(draftList2)) {
            draftList2 = productSkuPicDescService.getList(skuCode);
        }
        detailResp.setProductSkuPicDescs(draftList2);
        //sku文件管理
        List<ProductSkuFileRespVO> draftList3 = productSkuFileService.getDraftList(skuCode);
        if (CollectionUtils.isEmpty(draftList3)) {
            draftList3 = productSkuFileService.getList(skuCode);
        }
        detailResp.setProductSkuFiles(draftList3);
        //价格信息
        List<ProductSkuPriceRespVo> draftTemps =
                productSkuPriceInfoService.getSkuPriceBySkuCodeForDraft(skuCode);
        if (CollectionUtils.isEmpty(draftTemps)) {
            draftTemps = productSkuPriceInfoService.getSkuPriceBySkuCodeForOfficial(skuCode);
        }
        List<ProductSkuPriceRespVo> priceDraftRespVos =
                draftTemps.stream().filter(item ->
                        !Objects.equals(item.getPriceTypeCode(), PriceTypeEnum.PURCHASE.getTypeCode())).
                        collect(Collectors.toList());
        detailResp.setProductSkuPrices(priceDraftRespVos);
        //配置信息
        List<SkuConfigsRepsVo> draftList = productSkuConfigService.draftDetail(skuCode);
        if (CollectionUtils.isEmpty(draftList)) {
            draftList = productSkuConfigService.getList(skuCode);
        }
        detailResp.setProductSkuConfigs(draftList);
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
        // spu信息
        NewProductResponseVO productResponseVO = newProductService.getDetail(skuRespVo.getProductCode());
        if (Objects.nonNull(productResponseVO)) {
            detailResp.setSpuInfo(productResponseVO);
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
            //前端调用需要封装
            if(StringUtils.isBlank(querySkuListReqVO.getCompanyCode())){
                AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
                if(null != authToken){
                    querySkuListReqVO.setCompanyCode(authToken.getCompanyCode());
                    querySkuListReqVO.setPersonId(authToken.getPersonId());
                }
            }
            String categoryId;
            PageHelper.startPage(querySkuListReqVO.getPageNo(),querySkuListReqVO.getPageSize());
            List<QueryProductSkuListResp> queryProductSkuListResps = productSkuDao.querySkuList(querySkuListReqVO);
            if (CollectionUtils.isNotEmpty(queryProductSkuListResps)) {
                for(QueryProductSkuListResp sku:queryProductSkuListResps){
                    if(querySkuListReqVO.getInspectionStatus() == null){
                        List<ProductSkuInspReport> info = productSkuInspReportDao.getInfo(sku.getSkuCode());
                        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(info)){
                            sku.setInspectionStatus("无");
                        }else {
                            sku.setInspectionStatus("有");
                        }
                    }else if (querySkuListReqVO.getInspectionStatus() == 0){
                        sku.setInspectionStatus("有");
                    }else if (querySkuListReqVO.getInspectionStatus() == 1) {
                        sku.setInspectionStatus("无");
                    }
                    //SKU渠道信息
                    List<ProductSkuChannelRespVo> skuChannelRespVos = productSkuChannelService.getList(sku.getSkuCode());
                    sku.setProductSkuChannels(skuChannelRespVos);
                  }
            }
            return PageUtil.getPageList(querySkuListReqVO.getPageNo(),queryProductSkuListResps);
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<String> querySkuCodeList(QuerySkuListReqVO querySkuListReqVO) {
        if(StringUtils.isBlank(querySkuListReqVO.getCompanyCode())){
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                querySkuListReqVO.setCompanyCode(authToken.getCompanyCode());
                querySkuListReqVO.setPersonId(authToken.getPersonId());
            }
        }
        return productSkuDao.querySkuCodeList(querySkuListReqVO);
    }

    /**
     *  查询品类名称
     */
    private void category(QuerySkuListReqVO querySkuListReqVO) {

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
    public ProductApplyInfoRespVO<ProductSkuApplyVo2> getSkuApplyDetail(String applyCode) {
        List<ProductSkuApplyVo2> list = applyProductSkuMapper.selectByApplyCode2(applyCode);
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
        //spu信息
        NewProductResponseVO spuResponseVO = newProductService.getDetail(skuRespVo.getProductCode());
        if (spuResponseVO != null) {
            detailResp.setSpuInfo(spuResponseVO);
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    @Update
    public int cancelApply(ApplyProductSku applyProductSku) {
        try {
            return applyProductSkuMapper.updateByPrimaryKeySelective(applyProductSku);
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workFlow(String applyCode, String form, List<ApplyProductSku> applyProductSkus, String directSupervisorCode, String approvalName,String positionCode) {

        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setPositionCode(positionCode);
        workFlowVO.setFormUrl(workFlowBaseUrl.applySku+"?approvalType=1&code="+applyCode+"&"+workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(form);
        Byte applyType = applyProductSkus.get(0).getApplyType();
        WorkFlow workFlow;
        if (Objects.equals(applyType, StatusTypeCode.ADD_APPLY.getStatus())) {
            workFlow = WorkFlow.APPLY_GOODS;
        } else {
            workFlow = WorkFlow.APPLY_GOODS_REVISE;
        }
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ workFlow.getNum());
        workFlowVO.setTitle(approvalName);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, workFlow);
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
    @Transactional(rollbackFor = Exception.class)
    public String skuWorkFlowCallback(WorkFlowCallbackVO vo1) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(vo1);
        if (Objects.equals(vo.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }
        List<ApplyProductSku> applyProductSkus = productSkuDao.getApplySkuByFormNo(vo.getFormNo());
        if (CollectionUtils.isEmpty(applyProductSkus)) {
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        String applyCode = applyProductSkus.get(0).getApplyCode();
        Byte applyStatus = applyProductSkus.get(0).getApplyStatus();
        //判断查出来的是否是在审批中的数据
//        if (!ApplyStatus.APPROVAL.getNumber().equals(applyStatus)) {
//            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常，不是在审批中的数据！"));
//        }
        Date auditorTime = new Date();
        for (int i = 0; i < applyProductSkus.size(); i++) {
            Byte handleTypeCoceStatus;
            String handleTypeCoceName;
            String skuCode = applyProductSkus.get(i).getSkuCode();
            String skuName = applyProductSkus.get(i).getSkuName();
            //sku基本信息
            ProductSkuInfo oldSku = productSkuDao.getSkuInfo(skuCode);
            ProductSkuInfo productSkuInfo = new ProductSkuInfo();
            BeanCopyUtils.copyIgnoreNullValue(applyProductSkus.get(i), productSkuInfo);
            productSkuInfo.setAuditorBy(vo.getApprovalUserName());
            productSkuInfo.setAuditorTime(auditorTime);
            //是否预约生效时间
            boolean flag = applyProductSkus.get(i).getSelectionEffectiveTime() == 0;
            //判断不生效生效
            boolean beEffective = flag && applyProductSkus.get(i).getSelectionEffectiveStartTime().after(auditorTime);
            //进行判断1新增 还是0修改
            Integer flagNum=0;

            productSkuInfo.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
            if (!beEffective) {
                if (null != oldSku) {
                    productSkuInfo.setId(oldSku.getId());
                    flagNum=0;
                    handleTypeCoceStatus = HandleTypeCoce.UPDATE_SKU.getStatus();
                    handleTypeCoceName = HandleTypeCoce.UPDATE_SKU.getName();
                    productSkuInfoMapper.updateByPrimaryKeySelective(productSkuInfo);
                } else {
                    productSkuInfo.setId(null);
                    flagNum=1;
                    handleTypeCoceStatus = HandleTypeCoce.ADD_SKU.getStatus();
                    handleTypeCoceName = HandleTypeCoce.ADD_SKU.getName();
                    productSkuInfoMapper.insertSelective(productSkuInfo);
                }
                productCommonService.instanceThreeParty(productSkuInfo.getSkuCode(), handleTypeCoceStatus, ObjectTypeCode.APPLY_SKU.getStatus(), productSkuInfo, handleTypeCoceName, new Date(), vo1.getApprovalUserName(), null);
                //渠道
                productSkuChannelService.save(skuCode, applyCode);
                //标签
                List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCodeAndUseObjectCode(applyCode, TagTypeCode.SKU.getStatus(), skuCode);
                if (CollectionUtils.isNotEmpty(applyUseTagRecords)) {
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
                productSkuBoxPackingService.saveInfo(skuCode, applyCode);
                //进销存信息
                productSkuStockInfoService.saveInfo(skuCode, applyCode);
                productSkuPurchaseInfoService.saveInfo(skuCode, applyCode);
                productSkuDisInfoService.saveInfo(skuCode, applyCode);
                productSkuSalesInfoService.saveList(skuCode, applyCode);
                //结算
                productSkuCheckoutService.saveInfo(skuCode, applyCode);
                //供应商
                productSkuSupplyUnitService.saveList(skuCode, applyCode);
                //供应商产能
                productSkuSupplyUnitCapacityService.saveList(skuCode, applyCode);
                if (null == oldSku) {
                    //配置
                    productSkuConfigService.saveList(vo, skuCode, applyCode);
                    // 初始化每个仓库库存信息
                    ArrayList<Stock> stockList = Lists.newArrayList();
                    List<SkuConfigsRepsVo> configList = productSkuConfigMapper.getListBySkuCode(productSkuInfo.getSkuCode());
                    if (CollectionUtils.isNotEmpty(configList)) {
                        for (SkuConfigsRepsVo skuConfigsRepsVo : configList) {
                            List<WarehouseDTO> warehouseDTO = warehouseDao.getWarehouseCodeByTransportCenterCode(skuConfigsRepsVo.getTransportCenterCode());
                            if (CollectionUtils.isNotEmpty(warehouseDTO)) {
                                for (WarehouseDTO warehouse : warehouseDTO) {
                                    Stock stock = BeanCopyUtils.copy(productSkuInfo, Stock.class);
                                    stock.setTransportCenterCode(skuConfigsRepsVo.getTransportCenterCode());
                                    stock.setTransportCenterName(skuConfigsRepsVo.getTransportCenterName());
                                    stock.setStockCode("ST" + IdSequenceUtils.getInstance().nextId());
                                    stock.setWarehouseCode(warehouse.getWarehouseCode());
                                    stock.setWarehouseName(warehouse.getWarehouseName());
                                    stock.setWarehouseType(Integer.valueOf(warehouse.getWarehouseTypeCode()));
                                    stock.setLockCount(0L);
                                    stock.setInventoryCount(0L);
                                    stock.setAvailableCount(0L);
                                    stock.setPurchaseWayCount(0L);
                                    stock.setAllocationWayCount(0L);
                                    stock.setTotalWayCount(0L);
//                            stock.setPurchaseGroupCode(productSkuInfo.getProcurementSectionCode());
//                            stock.setPurchaseGroupName(productSkuInfo.getProcurementSectionName());
                                    stock.setNewPurchasePrice(new BigDecimal(0));
                                    stock.setTaxRate(new BigDecimal(0));
                                    stock.setTaxCost(new BigDecimal(0));
//                            stock.setTaxPrice(new BigDecimal(0));
                                    stockList.add(stock);
                                }
                            }
                        }
                    }
                    stockDao.insertBatch(stockList);
                }
                //关联商品
                productSkuAssociatedGoodsService.saveList(skuCode, applyCode);
                //生产厂家
                productSkuManufacturerService.saveList(skuCode, applyCode);
                //图片及介绍
                productSkuPicturesService.saveList(skuCode, applyCode);
                //商品说明
                productSkuPicDescService.saveList(skuCode, applyCode);
                //文件
                productSkuFileService.saveList(skuCode, applyCode);
                //质检报告
                productSkuInspReportService.saveList(skuCode, applyCode);
                //组合商品子商品列表
                productSkuSubService.saveList(skuCode, applyCode);
                //价格
//            List<String> skuCodes = applyProductSkus.stream().map(item -> item.getSkuCode()).distinct().collect(Collectors.toList());
                List<String> skuCodes = Lists.newArrayList();
                skuCodes.add(skuCode);
                List<ApplyProductSkuPriceInfo> skuPriceListApplyBySkuCode = productSkuPriceInfoService.getSkuPriceListApplyBySkuCodes(skuCodes, applyCode);
                if (CollectionUtils.isNotEmpty(skuPriceListApplyBySkuCode)) {
                    List<ProductSkuPriceInfo> productSkuPriceInfos = BeanCopyUtils.copyList(skuPriceListApplyBySkuCode, ProductSkuPriceInfo.class);
                    Date current = new Date();
                    productSkuPriceInfos.forEach(item -> {
                        if (null == item.getEffectiveTimeStart()) {
                            item.setEffectiveTimeStart(current);
                        }
                    });
                    productSkuPriceInfoService.saveSkuPriceOfficial(productSkuPriceInfos);
                }
                //更新为该条数据生效。在这里只能加一个同步的时间 SelectionEffectiveEndTime
                ApplyProductSku applyProductSku = new ApplyProductSku();
                applyProductSku.setId(applyProductSkus.get(i).getId());
                applyProductSku.setSelectionEffectiveEndTime(auditorTime);
                applyProductSkuMapper.updateByPrimaryKeySelective(applyProductSku);
                //审批通过之后传输给wms
                try {
                    sendWms(applyCode, skuCode, productSkuInfo,flagNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //进行sap传输
                Map<String, List<SapSkuStorageFinancial>> SapSkuStorageFinancialMap = Maps.newHashMap();
                List<QueryPriceChannelRespVo> priceChannels = priceChannelMapper.getList(new QueryPriceChannelReqVo());
                ProductSkuCheckout productSkuCheckout;
                Map<String, ProductSkuCheckout> skuCheckoutMap = null;
                List<SapProductSku> productSkuList = Lists.newArrayList();
                List<SapSkuSale> baseInfo2;
                baseInfo2 = Lists.newArrayList();
                SapProductSku  sapProductSku = new SapProductSku();
                sapProductSku.setSapSkuCode(productSkuInfo.getProductCode());
                sapProductSku.setIndustryDepartmentCode("A");
                //取一级品类
                sapProductSku.setCategoryCode(productSkuInfo.getProductCategoryCode().substring(0, 2));
                sapProductSku.setSkuName(productSkuInfo.getProductName());
                //基本视图
                SapProductSkuBase  baseInfo = new SapProductSkuBase();
                baseInfo.setUnit("EA");
                baseInfo.setProductGroupCode(productSkuInfo.getProductCategoryCode().substring(0, 2));
                baseInfo.setGroupCode(productSkuInfo.getProductCategoryCode());
                baseInfo.setWeight("KG");
                baseInfo.setStandard(productSkuInfo.getSpec());
                sapProductSku.setBaseInfo(baseInfo);
                sapProductSku.setBaseInfo1(SapSkuStorageFinancialMap.get(productSkuInfo.getSkuCode()));
//               productSkuCheckout = skuCheckoutMap.get(productSkuInfo.getProductCode());
                sapProductSku.setBaseInfo2(sapSkuSaleListHandler(null, baseInfo2, priceChannels));
                productSkuList.add(sapProductSku);
                sapStorageAbutment(productSkuList);

            }
        }
        //更新审批状态
        applyProductSkuMapper.updateStatusByFormNo(ApplyStatus.APPROVAL_SUCCESS.getNumber(), vo.getFormNo(), vo.getApprovalUserName(), auditorTime);
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }


    /**
     * sap商品对接
     *
     * @param productSkuList
     */
    private void sapStorageAbutment(List<SapProductSku> productSkuList) {
        log.info("调用sap商品对接参数:{} ", JsonUtil.toJson(productSkuList));
        HttpClient client = HttpClient.post(PRODUCT_URL).json(productSkuList).timeout(10000);
//        HttpResponse httpResponse = client.action().result(HttpResponse.class);
//        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
//            log.info("调用sap商品对接成功:{}", httpResponse.getMessage());
//        } else {
//            log.error("调用sap商品对接异常:{}", httpResponse.getMessage());
//            throw new GroundRuntimeException(String.format("调用sap商品对接异常:%s", httpResponse.getMessage()));
//        }
    }

    private List<SapSkuSale> sapSkuSaleListHandler(ProductSkuCheckout productSkuCheckout, List<SapSkuSale> baseInfo2, List<QueryPriceChannelRespVo> priceChannels) {
        SapSkuSale sapSkuSale;
        for (QueryPriceChannelRespVo priceChannel : priceChannels) {
            sapSkuSale = new SapSkuSale();
            sapSkuSale.setSaleOrg(priceChannel.getPriceChannelCode());
            sapSkuSale.setSubjectGroupCode("14");
            sapSkuSale.setMainCategoryCode("NORM");
            sapSkuSale.setTaxCategoryCode(Objects.isNull(productSkuCheckout)?"0":productSkuCheckout.getInputTaxRate().toString());
            sapSkuSale.setDistributionChannelCode("00");
            baseInfo2.add(sapSkuSale);
        }
        return baseInfo2;
    }
    private void sendWms(String applyCode, String skuCode, ProductSkuInfo productSkuInfo, Integer flagNum) throws Exception {
        ProductSkuInfoWms productSkuInfoWms=new ProductSkuInfoWms();

        //sku整箱商品包装信息
         List<ProductSkuBoxPackingRespVo> productSkuBoxPackingRespVos= productSkuBoxPackingService.getApply(skuCode,applyCode);
        if(CollectionUtils.isNotEmpty(productSkuBoxPackingRespVos)){
            productSkuInfoWms.setPackgeUnit(productSkuBoxPackingRespVos.get(0).getLargeUnit());
        }
        List<PurchaseSaleStockRespVo> applyList = productSkuStockInfoService.getApplyList(skuCode, applyCode);
        if(CollectionUtils.isNotEmpty(applyList)){
             productSkuInfoWms.setUnitName(applyList.get(0).getUnitName());
             productSkuInfoWms.setUnitCode(applyList.get(0).getUnitCode());
         }
        productSkuInfoWms.setFlag(flagNum);
        productSkuInfoWms.setSkuCode(productSkuInfo.getSkuCode());
        productSkuInfoWms.setSkuName(productSkuInfo.getSkuName());
        productSkuInfoWms.setProductBrandCode(productSkuInfo.getProductBrandCode());
        productSkuInfoWms.setProductBrandName(productSkuInfo.getProductBrandName());
        productSkuInfoWms.setProductCategoryCode(productSkuInfo.getProductCategoryCode());
        productSkuInfoWms.setProductCategoryName(productSkuInfo.getProductCategoryName());
        productSkuInfoWms.setSkuAbbreviation(productSkuInfo.getSkuAbbreviation());
        productSkuInfoWms.setModelNumber(productSkuInfo.getModelNumber());
        productSkuInfoWms.setQualityAssuranceManagement(String.valueOf(productSkuInfo.getQualityAssuranceManagement()));
        productSkuInfoWms.setQualityNumber(productSkuInfo.getQualityNumber());
        productSkuInfoWms.setQualityDate(productSkuInfo.getQualityDate());
        productSkuInfoWms.setUpdateTime(productSkuInfo.getUpdateTime());
        productSkuInfoWms.setGoodsGifts(productSkuInfo.getGoodsGifts());
        productSkuInfoWms.setManufacturerGuidePrice(productSkuInfo.getManufacturerGuidePrice());
        productSkuInfoWms.setItemNumber(productSkuInfo.getItemNumber());
        productSkuInfoWms.setItemNumber(productSkuInfo.getItemNumber());
        productSkuInfoWms.setColorCode(productSkuInfo.getColorCode());
        productSkuInfoWms.setColorName(productSkuInfo.getColorName());
        productSkuInfoWms.setSkuStatus(productSkuInfo.getSkuStatus());
        productSkuInfoWms.setSeasonBand(productSkuInfo.getSeasonBand());
        productSkuInfoWms.setUniqueCode(productSkuInfo.getUniqueCode());
        //条形码,门店销售
//        List<PurchaseSaleStockRespVo> purchaseSaleStockRespVos=  productSkuSalesInfoService.getList(skuCode);
//        log.info("sku条形码{}", JSON.toJSONString(purchaseSaleStockRespVos));
//        for (PurchaseSaleStockRespVo purchaseSaleStockRespVo:
//                purchaseSaleStockRespVos) {
//            if (purchaseSaleStockRespVo.getIsDefault().equals("1")){
//                productSkuInfoWms.setBarCode(purchaseSaleStockRespVo.getBarCode());
//                productSkuInfoWms.setCoutanisNumber(String.valueOf(purchaseSaleStockRespVo.getBaseProductContent()));
//                break;
//            }
//
//        }
        PurchaseSaleStockRespVo purchaseSaleStockRespVo = productSkuSalesInfoDao.selectBarCodeBySkuCode(skuCode);
        log.info("sku条形码{}", JSON.toJSONString(purchaseSaleStockRespVo));
        productSkuInfoWms.setSkuBarCode(purchaseSaleStockRespVo.getBarCode());

        //分销设置
        ApplyProductSkuDisInfo applyProductSkuDisInfo = productSkuDisInfoDao.getApply(skuCode,applyCode);
        productSkuInfoWms.setSpec(applyProductSkuDisInfo.getSpec());
        //经销商信息
        List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = productSkuSupplyUnitDao.getApply(skuCode,applyCode);
        for (ApplyProductSkuSupplyUnit applyProductSkuSupplyUnit:
                applyProductSkuSupplyUnits ) {
            if (applyProductSkuSupplyUnit.getIsDefault().equals("1")){
                productSkuInfoWms.setSupplyUnitCode(applyProductSkuSupplyUnit.getSupplyUnitCode());
                productSkuInfoWms.setSupplyUnitName(applyProductSkuSupplyUnit.getSupplyUnitName());
                break;
            }
        }

        //包装信息
        List<ApplyProductSkuBoxPacking> applyProductSkuBoxPackings = productSkuBoxPackingDao.getApply(skuCode,applyCode);
        for (ApplyProductSkuBoxPacking applyProductSkuBoxPacking:
                applyProductSkuBoxPackings) {
            if(applyProductSkuBoxPacking.getUnitCode().equals(productSkuInfoWms.getUnitCode())){
                productSkuInfoWms.setBoxGrossWeight(productSkuInfoWms.getNetWeight());
                productSkuInfoWms.setBoxLength(productSkuInfoWms.getBoxHeight());
                productSkuInfoWms.setBoxHeight(productSkuInfoWms.getBoxHeight());
                productSkuInfoWms.setBoxVolume(productSkuInfoWms.getBoxVolume());
                productSkuInfoWms.setBoxGrossWeight(productSkuInfoWms.getBoxGrossWeight());
                productSkuInfoWms.setNetWeight(productSkuInfoWms.getNetWeight());
                break;
            }

        }
        //商品配置设置
        List<SkuConfigsRepsVo> skuConfigsRepsVoList=  productSkuConfigService.getList(skuCode);
        List<SkuConfigsWmsRepsVo> skuConfigsWmsRepsVos= Lists.newArrayList();
        for (SkuConfigsRepsVo skuConfigsRepsVo:
                skuConfigsRepsVoList ) {
            SkuConfigsWmsRepsVo skuConfigsWmsRepsVo=new SkuConfigsWmsRepsVo();
            skuConfigsWmsRepsVo.setConfigStatus(skuConfigsRepsVo.getConfigStatus());
            skuConfigsWmsRepsVo.setTransportCenterCode(skuConfigsRepsVo.getTransportCenterCode());
            skuConfigsWmsRepsVo.setTransportCenterName(skuConfigsRepsVo.getTransportCenterName());
            skuConfigsWmsRepsVos.add(skuConfigsWmsRepsVo);
        }
        // DL - 2020-09-17 16:31 让注释去掉调用wms
//        productSkuInfoWms.setSkuConfigsWmsRepsVos(skuConfigsWmsRepsVos);
//        log.info("传入wms的消息为{}", JSON.toJSONString(productSkuInfoWms));
//        try {
//            StringBuilder url = new StringBuilder();
//            url.append(urlConfig.WMS_API_URL2).append("/infoPushAndInquiry/source/productInfoPush" );
////            HttpClient httpClient = HttpClient.get(url.toString());
//            HttpClient httpClient = HttpClient.post(String.valueOf(url)).json(productSkuInfoWms).timeout(30000);
//            HttpResponse<RejectResponse> result = httpClient.action().result(new TypeReference<HttpResponse<RejectResponse>>(){
//            });
//            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
//                log.info("穿入wms的sku商品信息失败，传入参数是[{}]", JSON.toJSONString(productSkuInfoWms));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("穿入wms的sku商品信息失败，传入参数是[{}]"+JSON.toJSONString(productSkuInfoWms));
////            log.info("穿入wms的sku商品信息失败，传入参数是[{}]", JSON.toJSONString(productSkuInfoWms));
//        }
    }

    private void sendWms2(ProductSkuInfoWms productSkuInfoWms) {
        try {
            StringBuilder url = new StringBuilder();
//            url.append(urlConfig.WMS_API_URL).append("/infoPushAndInquiry/source/productInfoPush" );
//            HttpClient httpClient = HttpClient.get(url.toString());
            HttpClient httpClient = HttpClient.post(String.valueOf(url)).json(productSkuInfoWms).timeout(30000);
            HttpResponse<RejectResponse> result = httpClient.action().result(new TypeReference<HttpResponse<RejectResponse>>(){
            });
            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                log.info("穿入wms的sku商品信息失败，传入参数是[{}]", JSON.toJSONString(productSkuInfoWms));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("穿入wms的sku商品信息失败，传入参数是[{}]", JSON.toJSONString(productSkuInfoWms));
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toBeEffective(List<ApplyProductSku> applyProductSkus){
        Date auditorTime = new Date();
        String  applyCode = applyProductSkus.get(0).getApplyCode();
        WorkFlowCallbackVO vo = new WorkFlowCallbackVO();
        vo.setApprovalUserName(applyProductSkus.get(0).getAuditorBy());
        for (int i = 0; i < applyProductSkus.size(); i++) {
            Byte handleTypeCoceStatus;
            String handleTypeCoceName;
            String skuCode = applyProductSkus.get(i).getSkuCode();
            String skuName = applyProductSkus.get(i).getSkuName();
            //sku基本信息
            ProductSkuInfo oldSku = productSkuDao.getSkuInfo(skuCode);
            ProductSkuInfo productSkuInfo = new ProductSkuInfo();
            BeanCopyUtils.copyIgnoreNullValue(applyProductSkus.get(i), productSkuInfo);
            productSkuInfo.setAuditorBy(vo.getApprovalUserName());
            productSkuInfo.setAuditorTime(auditorTime);
            //是否预约生效时间
            boolean flag = applyProductSkus.get(i).getSelectionEffectiveTime() == 0 ? true : false;
            //判断不生效生效
            boolean beEffective = flag && applyProductSkus.get(i).getSelectionEffectiveStartTime().after(auditorTime);
            productSkuInfo.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
            if (!beEffective) {
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
                productCommonService.instanceThreeParty(productSkuInfo.getSkuCode(), handleTypeCoceStatus, ObjectTypeCode.APPLY_SKU.getStatus(), productSkuInfo, handleTypeCoceName,new Date(),"系统自动",null);
                //渠道
                productSkuChannelService.save(skuCode, applyCode);
                //标签
                List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCodeAndUseObjectCode(applyCode, TagTypeCode.SKU.getStatus(), skuCode);
                if (CollectionUtils.isNotEmpty(applyUseTagRecords)) {
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
                productSkuBoxPackingService.saveInfo(skuCode, applyCode);
                //进销存信息
                productSkuStockInfoService.saveInfo(skuCode, applyCode);
                productSkuPurchaseInfoService.saveInfo(skuCode, applyCode);
                productSkuDisInfoService.saveInfo(skuCode, applyCode);
                productSkuSalesInfoService.saveList(skuCode, applyCode);
                //结算
                productSkuCheckoutService.saveInfo(skuCode, applyCode);
                //供应商
                productSkuSupplyUnitService.saveList(skuCode, applyCode);
                //供应商产能
                productSkuSupplyUnitCapacityService.saveList(skuCode, applyCode);
                if (null == oldSku) {
                    //配置
                    productSkuConfigService.saveList(vo, skuCode, applyCode);
                }
                //关联商品
                productSkuAssociatedGoodsService.saveList(skuCode, applyCode);
                //生产厂家
                productSkuManufacturerService.saveList(skuCode, applyCode);
                //图片及介绍
                productSkuPicturesService.saveList(skuCode, applyCode);
                //商品说明
                productSkuPicDescService.saveList(skuCode, applyCode);
                //文件
                productSkuFileService.saveList(skuCode, applyCode);
                //质检报告
                productSkuInspReportService.saveList(skuCode, applyCode);
                //组合商品子商品列表
                productSkuSubService.saveList(skuCode, applyCode);
            }
            //价格
            List<String> skuCodes = applyProductSkus.stream().map(item -> item.getSkuCode()).distinct().collect(Collectors.toList());
            List<ApplyProductSkuPriceInfo> skuPriceListApplyBySkuCode = productSkuPriceInfoService.getSkuPriceListApplyBySkuCodes(skuCodes, applyCode);
            if (CollectionUtils.isNotEmpty(skuPriceListApplyBySkuCode)) {
                List<ProductSkuPriceInfo> productSkuPriceInfos = BeanCopyUtils.copyList(skuPriceListApplyBySkuCode, ProductSkuPriceInfo.class);
                Date current = new Date();
                productSkuPriceInfos.forEach(item -> {
                    if (null == item.getEffectiveTimeStart()) {
                        item.setEffectiveTimeStart(current);
                    }
                });
                productSkuPriceInfoService.saveSkuPriceOfficial(productSkuPriceInfos);
            }
            //更新为该条数据生效。在这里只能加一个同步的时间 SelectionEffectiveEndTime
            ApplyProductSku applyProductSku = new ApplyProductSku();
            applyProductSku.setId(applyProductSkus.get(i).getId());
            applyProductSku.setSelectionEffectiveEndTime(auditorTime);
            applyProductSkuMapper.updateByPrimaryKeySelective(applyProductSku);
        }
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
//        if (deleteNum == 0 ) {
//            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
//        }
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
//        List<QuerySkuInfoRespVO> respVos = Lists.newArrayList();
//        if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(vo.getChangePriceType())){
//            respVos = productSkuDao.selectSkuListForPurchasePrice(longs1);
            //采购变价需要查询渠道价
//            respVos = dealPurchasePriceSkuData(respVos);
//        } else if(CommonConstant.SALE_PRICE.contains(vo.getChangePriceType())){
//            if(CommonConstant.FOREVER_PRICE.contains(vo.getChangePriceType())){
//                vo.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
                List<String> list = new ArrayList<>();
                list.add(CommonConstant.SALE_CHANGE_PRICE);
                list.add(CommonConstant.SALE_WHOLE_PRICE);
                vo.setChangePriceTypes(list);
//            }else if(CommonConstant.TEMP_PRICE.contains(vo.getChangePriceType())){
//                vo.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
//            }
        List<QuerySkuInfoRespVO> respVos = productSkuDao.selectSkuListForSalePrice(longs1,vo.getChangePriceType(), vo.getChangePriceTypes());
        for (QuerySkuInfoRespVO respVo : respVos) {
            for (PriceChannelForChangePrice priceChannelForChangePrice : respVo.getPriceChannelList()) {
                ProductSkuSupplyUnit productSkuSupplyUnit = productSkuSupplyUnitDao.selectOneBySkuCode(respVo.getSkuCode());
                priceChannelForChangePrice.setPurchasePriceNewest(productSkuSupplyUnit.getTaxIncludedPrice());
                priceChannelForChangePrice.setTaxCost(productSkuSupplyUnit.getTaxIncludedPrice());
            }
        }
//        }else {
//            throw new BizException(ResultCode.NOT_HAVE_PARAM);
//        }
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
            BigDecimal price = BigDecimal.ZERO;
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
        // 变价类型去掉了 不需要判断
//        if(StringUtils.isBlank(vo.getChangePriceType())){
//            throw new BizException(ResultCode.NOT_HAVE_PARAM);
//        }
        List<Long> list = Lists.newArrayList();
//        if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(vo.getChangePriceType())){
//            list = productSkuDao.selectSkuListForPurchasePriceCount(vo);
//        } else if(CommonConstant.SALE_PRICE.contains(vo.getChangePriceType())){
//            if(CommonConstant.FOREVER_PRICE.contains(vo.getChangePriceType())){
//                vo.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
                List<String> lists = new ArrayList<>();
                lists.add(CommonConstant.SALE_CHANGE_PRICE);
                lists.add(CommonConstant.SALE_WHOLE_PRICE);
                vo.setChangePriceTypes(lists);
//            }else if(CommonConstant.TEMP_PRICE.contains(vo.getChangePriceType())){
//                vo.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
//            }
            list = productSkuDao.selectSkuListForSalePriceCount(vo);
//        }else {
//            throw new BizException(ResultCode.NOT_HAVE_PARAM);
//        }
        return list;
    }

    @Override
    public List<QuerySkuInfoRespVO> getSkuListByQueryNoPage(QuerySkuInfoReqVO vo){
//        if(StringUtils.isBlank(vo.getChangePriceType())){
//            throw new BizException(ResultCode.NOT_HAVE_PARAM);
//        }
        List<Long> ids = getSkuListByQueryNoPageCount(vo);
        List<QuerySkuInfoRespVO> list = Lists.newArrayList();
        if(CollectionUtils.isEmpty(ids)){
            return list;
        }
//        if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(vo.getChangePriceType())){
//            list = productSkuDao.selectSkuListForPurchasePrice(ids);
//        } else if(CommonConstant.SALE_PRICE.contains(vo.getChangePriceType())){
//            if(CommonConstant.FOREVER_PRICE.contains(vo.getChangePriceType())){
//                vo.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
                List<String> lists = new ArrayList<>();
                lists.add(CommonConstant.SALE_CHANGE_PRICE);
                lists.add(CommonConstant.SALE_WHOLE_PRICE);
                vo.setChangePriceTypes(lists);
//            }else if(CommonConstant.TEMP_PRICE.contains(vo.getChangePriceType())){
//                vo.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
//            }
            list = productSkuDao.selectSkuListForSalePrice(ids,vo.getChangePriceType(),vo.getChangePriceTypes());
//        }else {
//            throw new BizException(ResultCode.NOT_HAVE_PARAM);
//        }
        return list;
    }

    @Override
    public List<QueryProductSaleAreaRespVO> selectDirectSupplierBySkuCodes(List<String> collect) {
        return productSkuDao.selectDirectSupplierBySkuCodes(collect);
    }

    @Override
    public BasePage<QueryProductSaleAreaForSkuRespVO> selectSkuListForSaleArea(QueryProductSaleAreaForSkuReqVO reqVO) {
//        List<Long> longs = productSkuDao.selectSkuListForSaleAreaCount(reqVO);
//        if(CollectionUtils.isEmpty(longs)){
//            return PageUtil.getPageList(reqVO.getPageNo(), Lists.newArrayList());
//        }
        PageHelper.startPage(reqVO.getPageNo(),reqVO.getPageSize());
        List<QueryProductSaleAreaForSkuRespVO> list =  productSkuDao.selectSkuListForSaleArea(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(),reqVO.getPageSize(),list.size(),list);
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
    @Transactional(rollbackFor = Exception.class)
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
                //2019年9月12日10:25:06 品类名称改编码
                if (StringUtils.isNotBlank(o.getProductCategoryName())) {
                    categoryNameList.add(o.getProductCategoryName().trim());
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
                categoryList = productCategoryService.selectByCategoryCodes(categoryNameList, getUser().getCompanyCode());
                categoryMap = categoryList.stream().collect(Collectors.toMap(ProductCategory::getCategoryId, Function.identity()));
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
            List<String> dicNameList2 = Lists.newArrayList();
            dicNameList2.add("全量供货渠道类别");
            Map<String, SupplierDictionaryInfo> dicMap2 = supplierDictionaryInfoDao.selectByName(dicNameList2, getUser().getCompanyCode());
            List<AddSkuInfoReqVO> skuInfoList = Lists.newArrayList();
            List<SkuInfoImport> importList = Lists.newArrayList();
            Map<String, String> reaptMap = Maps.newHashMap();
            for (int i = 0; i < skuInfoImports.size(); i++) {
                //检查信息
                CheckSkuNew checkSku = new CheckSkuNew(productSkuMap, supplyCompanyMap, brandMap , categoryMap, channelMap, skuTagMap, reaptMap, skuInfoImports.get(i),spuMap,dicMap,manufactureMap,dicMap2)
                        .checkRepeat() //检查重复
                        .checkSKuNew() //新增检查sku
                        .checkBaseDate() //检查基础数据和spu
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
            List<String> dicNameList2 = Lists.newArrayList();
            dicNameList2.add("全量供货渠道类别");
            Map<String, SupplierDictionaryInfo> dicMap2 = supplierDictionaryInfoDao.selectByName(dicNameList2, getUser().getCompanyCode());
            List<AddSkuInfoReqVO> skuInfoList = Lists.newArrayList();
            List<SkuInfoImport> importList = Lists.newArrayList();
            Map<String, String> reaptMap = Maps.newHashMap();
            for (int i = 0; i < skuInfoImports.size(); i++) {
                //检查信息
                CheckSku checkSku = new CheckSku(productSkuMap, supplyCompanyMap, brandMap , categoryMap, channelMap, skuTagMap, reaptMap, skuInfoImports.get(i),spuMap,dicMap,productSkuDraftMap,purchaseGroupMap,dicMap2)
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
                    categoryNameList.add(o.getProductCategoryName().trim());
                }
                // if (StringUtils.isNotBlank(o.getPriceChannelName())) {
                //     channelList.addAll(Arrays.asList(o.getPriceChannelName().split(",")));
                // }
                // if (StringUtils.isNotBlank(o.getTagName())) {
                //     skuTagList.addAll(Arrays.asList(o.getTagName().split(",")));
                // }
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
                categoryList = productCategoryService.selectByCategoryCodes(categoryNameList, getUser().getCompanyCode());
                categoryMap = categoryList.stream().collect(Collectors.toMap(ProductCategory::getCategoryId, Function.identity()));
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
            List<String> dicNameList2 = Lists.newArrayList();
            dicNameList2.add("全量供货渠道类别");
            Map<String, SupplierDictionaryInfo> dicMap2 = supplierDictionaryInfoDao.selectByName(dicNameList2, getUser().getCompanyCode());
            List<AddSkuInfoReqVO> skuInfoList = Lists.newArrayList();
            List<SkuInfoImport> importList = Lists.newArrayList();
            Map<String, String> reaptMap = Maps.newHashMap();
            for (int i = 0; i < skuInfoImports.size(); i++) {
                //检查信息
                CheckSkuUpdate checkSku = new CheckSkuUpdate(productSkuMap, supplyCompanyMap, brandMap , categoryMap, channelMap, skuTagMap, reaptMap, skuInfoImports.get(i),spuMap,dicMap,manufactureMap,dicMap2)
                        .checkRepeat() //检查重复
                        .checkSKuUpdate() //修改检查sku
                        .checkBaseDate() //检查基础数据
                        .checkInvoice() //检查进销存包装
                        .checkSettlement() //检查结算信息
                        // .checkSupplier() //检查供应商
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
                        String productCode = spuMap.get(reqVO.getProductSkuDraft().getProductName());
                        if(StringUtils.isNotBlank(productCode)){
                            reqVO.getProductSkuDraft().setProductCode(spuMap.get(reqVO.getProductSkuDraft()));
                        } else {
                            NewProductSaveReqVO saveReqVO = new NewProductSaveReqVO();
                            saveReqVO.setProductName(reqVO.getProductSkuDraft().getProductName());
                            saveReqVO.setPurchasingGroupCode(reqVOs.getPurchaseGroupCode());
                            saveReqVO.setPurchasingGroupName(reqVOs.getPurchaseGroupName());
                            saveReqVO.setAbbreviation(reqVO.getSpuInfo().getAbbreviation());
                            saveReqVO.setStyleNumber(reqVO.getSpuInfo().getStyleNumber());
                            productCode = newProductService.insertProduct(saveReqVO);
                            spuMap.put(reqVO.getProductSkuDraft().getProductName(), productCode);
                        }
                        reqVO.getProductSkuDraft().setProductCode(productCode);
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
            if (StringUtils.isBlank(reqVO.getProductSkuDraft().getProductCode())&&StringUtils.isNotBlank(reqVO.getProductSkuDraft().getProductName())) {
                //判断是否新增了
                String s1 = spuMap.get(reqVO.getProductSkuDraft().getProductName());
                if (StringUtils.isNotBlank(s1)) {
                    reqVO.getProductSkuDraft().setProductCode(s1);
                } else {
                    //没有spu信息需要先增加品牌信息
                    synchronized (SkuInfoService.class) {
                        NewProductSaveReqVO saveReqVO = new NewProductSaveReqVO();
                        saveReqVO.setProductName(reqVO.getProductSkuDraft().getProductName());
                        saveReqVO.setPurchasingGroupCode(reqVOs.getPurchaseGroupCode());
                        saveReqVO.setPurchasingGroupName(reqVOs.getPurchaseGroupName());
                        saveReqVO.setAbbreviation(reqVO.getSpuInfo().getAbbreviation());
                        saveReqVO.setStyleNumber(reqVO.getSpuInfo().getStyleNumber());
                        String s = newProductService.insertProduct(saveReqVO);
                        reqVO.getProductSkuDraft().setProductCode(s);
                        spuMap.put(reqVO.getProductSkuDraft().getProductName(), s);
                    }
                }
            }
            reqVO.setBoxFlag(false);
            reqVO.getProductSkuDraft().setProcurementSectionCode(reqVOs.getPurchaseGroupCode());
            reqVO.getProductSkuDraft().setProcurementSectionName(reqVOs.getPurchaseGroupName());
            updateDraftSkuInfoForImport(reqVO);
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
    public Boolean exportFormalSkuBySkuCode(HttpServletResponse resp, List<String> skuCodeList) {
        List<SkuInfoExport> list = productSkuInfoMapper.exportSku(StringUtils.join(skuCodeList,","));
        try {
            ExcelUtil.writeExcel(resp, list, "商品导出模板", "商品导出模板", ExcelTypeEnum.XLSX,SkuInfoExport.class);
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
    public BasePage<ProductSkuDraftRespVo> getProductSkuDraftList(QuerySkuDraftListReqVO reqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
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
        return PageUtil.getPageList(reqVO.getPageNo(),productSkuDraftMapper.getProductSkuDraft(reqVO));
    }

    @Override
    public List<ProductSkuDraftRespVo> getProductSkuDraftListNoPage(QuerySkuDraftListReqVO reqVO) {
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
    public List<ApplyProductSku> selectUnSynData() {
        return applyProductSkuMapper.selectUnSynData();
    }

    @Override
    public Boolean exportAddSku(HttpServletResponse resp, @Valid @NotNull(message = "申请编码不能为空") String applyCode) {
        try {
            List<SkuAddExport> list = productSkuInfoMapper.exportAddSku(applyCode);
            ExcelUtil.writeExcel(resp,list,"商品DL新增导出","商品DL格式导出模板",ExcelTypeEnum.XLS,SkuAddExport.class);
            return Boolean.TRUE;
        } catch (ExcelException e) {
            log.error(e.getMessage(),e);
            throw new BizException(ResultCode.EXPORT_FAILED);
        }
    }

    @Override
    public Boolean exportEditSku(HttpServletResponse resp, @Valid @NotNull(message = "申请编码不能为空") String applyCode) {
        try {
            List<SkuEditExport> list = productSkuInfoMapper.exportEditSku(applyCode);
            ExcelUtil.writeExcel(resp,list,"商品DL修改导出","商品DL格式导出模板",ExcelTypeEnum.XLS,SkuEditExport.class);
            return Boolean.TRUE;
        } catch (ExcelException e) {
            log.error(e.getMessage(),e);
            throw new BizException(ResultCode.EXPORT_FAILED);
        }
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
        boolean equals = StringUtils.equals(head,skuInfoImports.get(1).toString());
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

    private void dataValidation2( List<SkuInfoImportUpdate> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuInfoImports.size()<3) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
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

    private ProductApplyInfoRespVO<ProductSkuApplyVo2> dealApplyViewData(List<ProductSkuApplyVo2> list) {
        ProductApplyInfoRespVO<ProductSkuApplyVo2> resp = new ProductApplyInfoRespVO<>();
        //数据相同默认取第一个
        ProductSkuApplyVo2 applyVO = list.get(0);
        resp.setApplyBy(applyVO.getUpdateBy());
        resp.setApplyTime(applyVO.getUpdateTime());
        resp.setApplyStatus(applyVO.getApplyStatus());
        resp.setAuditorBy(applyVO.getAuditorBy());
        resp.setAuditorTime(applyVO.getAuditorTime());
        resp.setSelectionEffectiveStartTime(applyVO.getSelectionEffectiveStartTime());
        resp.setSelectionEffectiveTime(applyVO.getSelectionEffectiveTime());
        resp.setCode(applyVO.getApplyCode());
        resp.setFormNo(applyVO.getFormNo());
        resp.setPurchaseGroupName(applyVO.getPurchaseGroupName());
        resp.setApprovalRemark(applyVO.getApprovalRemark());
        resp.setApprovalName(applyVO.getApprovalName());
        //统计sku数量
        List<String> skuCodes = list.stream().map(ProductSkuApplyVo2::getSkuCode).distinct().collect(Collectors.toList());
        resp.setSkuNum(skuCodes.size());
        //统计SPU数量吗
        List<String> spuCodes = list.stream().map(ProductSkuApplyVo2::getProductName).distinct().collect(Collectors.toList());
        resp.setSpuNum(spuCodes.size());
        resp.setData(list);
        resp.setApprovalFileInfos(approvalFileInfoService.selectByApprovalTypeAndApplyCode(ApprovalFileTypeEnum.SKU.getType(), applyVO.getApplyCode()));
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
        Map<String, SupplierDictionaryInfo> dicMap2;
        Map<String, Manufacturer> manufactureMap;
        Map<String, ProductSkuDraft> productSkuDraftMap;
        Map<String, PurchaseGroupDTO> purchaseGroupMap;

        private CheckSku() {
        }

        private CheckSku(Map<String, ProductSkuInfo> productSkuMap, Map<String, SupplyCompany> supplyCompanyMap, Map<String, ProductBrandType> brandMap, Map<String, ProductCategory> categoryMap, Map<String, PriceChannel> channelMap, Map<String, TagInfo> skuTagMap, Map<String, String> repeatMap, Object importVo, Map<String, NewProduct> spuMap, Map<String, SupplierDictionaryInfo> dicMap, Map<String, Manufacturer> manufactureMap, Map<String, SupplierDictionaryInfo> dicMap2) {
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
            this.dicMap2 = dicMap2;
            this.manufactureMap = manufactureMap;
        }

        public CheckSku(Map<String, ProductSkuInfo> productSkuMap, Map<String, SupplyCompany> supplyCompanyMap, Map<String, ProductBrandType> brandMap, Map<String, ProductCategory> categoryMap, Map<String, PriceChannel> channelMap, Map<String, TagInfo> skuTagMap, Map<String, String> reaptMap, ExportSkuInfo importVo, Map<String, NewProduct> spuMap, Map<String, SupplierDictionaryInfo> dicMap, Map<String, ProductSkuDraft> productSkuDraftMap,Map<String,PurchaseGroupDTO> purchaseGroupMap,Map<String, SupplierDictionaryInfo> dicMap2) {
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
            this.dicMap2 = dicMap2;
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
                SupplierDictionaryInfo info = dicMap2.get(importVo.getCategoriesSupplyChannelsName());
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
                    productSkuDraft.setManufacturerGuidePrice(new BigDecimal(importVo.getManufacturerGuidePrice()));
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
                    BigDecimal bigDecimalLength = new BigDecimal(importVo.getStockBoxLength().trim());
                    Long longLength = bigDecimalLength.longValue();
                    if (new BigDecimal(longLength).compareTo(bigDecimalLength)==0){}else {
                        //小数
                        throw  new BizException("库存长格式不正确");
                    }
                    stockBox.setBoxLength(longLength);
                } catch (Exception e) {
                    error.add("库存长格式不正确");
                    flag = false;
                }
                try {
                    BigDecimal bigDecimalWidth = new BigDecimal(importVo.getStockBoxWidth().trim());
                    Long longWidth = bigDecimalWidth.longValue();
                    if (new BigDecimal(longWidth).compareTo(bigDecimalWidth)==0){}else {
                        //小数
                        throw  new BizException("库存宽格式不正确");
                    }
                    stockBox.setBoxWidth(longWidth);
                } catch (Exception e) {
                    error.add("库存宽格式不正确");
                    flag = false;
                }
                try {
                    BigDecimal bigDecimalHeight = new BigDecimal(importVo.getStockBoxHeight().trim());
                    Long longHeight = bigDecimalHeight.longValue();
                    if (new BigDecimal(longHeight).compareTo(bigDecimalHeight)==0){}else {
                        //小数
                        throw  new BizException("库存宽格式不正确");
                    }
                    stockBox.setBoxHeight(longHeight);
                } catch (Exception e) {
                    error.add("库存高格式不正确");
                    flag = false;
                }
                if (flag) {
                    stockBox.setBoxVolume(stockBox.getBoxLength() * stockBox.getBoxWidth() * stockBox.getBoxHeight()/10000);
                }
                try {
                    stockBox.setBoxGrossWeight(new BigDecimal(importVo.getStockBoxGrossWeight().trim()));
                } catch (Exception e) {
                    error.add("库存毛重格式不正确");
                }
                try {
                    stockBox.setNetWeight(new BigDecimal(importVo.getStockNetWeight()));
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
                        BigDecimal bigDecimalLength = new BigDecimal(importVo.getStockBoxLength().trim());
                        Long longLength = bigDecimalLength.longValue();
                        if (new BigDecimal(longLength).compareTo(bigDecimalLength)==0){}else {
                            //小数
                            throw  new BizException("库存长格式不正确");
                        }
                        purchaseBox.setBoxLength(longLength);
                    } catch (Exception e) {
                        error.add("采购长格式不正确");
                        flag = false;
                    }
                    try {
                        BigDecimal bigDecimalWidth = new BigDecimal(importVo.getStockBoxWidth().trim());
                        Long longWidth = bigDecimalWidth.longValue();
                        if (new BigDecimal(longWidth).compareTo(bigDecimalWidth)==0){}else {
                            //小数
                            throw  new BizException("库存宽格式不正确");
                        }
                        purchaseBox.setBoxWidth(longWidth);
                    } catch (Exception e) {
                        error.add("采购宽格式不正确");
                        flag = false;
                    }
                    try {
                        BigDecimal bigDecimalHeight = new BigDecimal(importVo.getStockBoxHeight().trim());
                        Long longHeight = bigDecimalHeight.longValue();
                        if (new BigDecimal(longHeight).compareTo(bigDecimalHeight)==0){}else {
                            //小数
                            throw  new BizException("库存宽格式不正确");
                        }
                        purchaseBox.setBoxHeight(longHeight);
                    } catch (Exception e) {
                        error.add("采购高格式不正确");
                        flag = false;
                    }
                    if (flag) {
                        purchaseBox.setBoxVolume(purchaseBox.getBoxLength() * purchaseBox.getBoxWidth() * purchaseBox.getBoxHeight()/10000);
                    }
                    try {
                        purchaseBox.setBoxGrossWeight(new BigDecimal(importVo.getPurchaseBoxGrossWeight().trim()));
                    } catch (Exception e) {
                        error.add("采购毛重格式不正确");
                    }
                    try {
                        purchaseBox.setNetWeight(new BigDecimal(importVo.getPurchaseNetWeight()));
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
//                error.add("销售描述不能为空");
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
                    draft.setInputTaxRate(new BigDecimal(importVo.getInputTaxRate()));
                } catch (Exception e) {
                    error.add("进项税率格式不正确");
                }
            }
            //销项税率
            if (Objects.isNull(importVo.getOutputTaxRate())) {
                error.add("销项税率不能为空");
            } else {
                try {
                    draft.setOutputTaxRate(new BigDecimal(importVo.getOutputTaxRate()));
                } catch (Exception e) {
                    error.add("销项税率格式不正确");
                }
            }
            //积分系数
            if (Objects.isNull(importVo.getIntegralCoefficient())) {
                error.add("积分系数不能为空");
            } else {
                try {
                    draft.setIntegralCoefficient(new BigDecimal(importVo.getIntegralCoefficient()));
                } catch (Exception e) {
                    error.add("积分系数格式不正确");
                }
            }
            //物流费奖励比例
            if (Objects.isNull(importVo.getLogisticsFeeAwardRatio())) {
                error.add("物流费奖励比例不能为空");
            } else {
                try {
                    draft.setLogisticsFeeAwardRatio(new BigDecimal(importVo.getLogisticsFeeAwardRatio()).multiply(BigDecimal.valueOf(100)));
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
                    supplyUnitDraft.setTaxIncludedPrice(new BigDecimal(importVo.getTaxIncludedPrice()));
                } catch (Exception e) {
                    error.add("含税采购价格式不正确");
                }
            }
            //联营扣点
            if (Objects.nonNull(importVo.getJointFranchiseRate())) {
                try {
                    supplyUnitDraft.setJointFranchiseRate(new BigDecimal(importVo.getJointFranchiseRate().trim()));
                } catch (Exception e) {
                    error.add("联营扣点格式不正确");
                }
            }
            //返点
            if (Objects.nonNull(importVo.getPoint())) {
                try {
                    supplyUnitDraft.setPoint(new BigDecimal(importVo.getPoint().trim()));
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
                    supplyUnitDraft.setTaxIncludedPrice(new BigDecimal(importVo.getTaxIncludedPrice()));
                } catch (Exception e) {
                    error.add("含税采购价格式不正确");
                }
            }
            //联营扣点
            if (Objects.nonNull(importVo.getJointFranchiseRate())) {
                try {
                    supplyUnitDraft.setJointFranchiseRate(new BigDecimal(importVo.getJointFranchiseRate().trim()));
                } catch (Exception e) {
                    error.add("联营扣点格式不正确");
                }
            }
            //返点
            if (Objects.nonNull(importVo.getPoint())) {
                try {
                    supplyUnitDraft.setPoint(new BigDecimal(importVo.getPoint().trim()));
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
                SkuPriceDraftReqVO aiqinChannel1 = price.get("爱亲渠道价");
                SkuPriceDraftReqVO aiqinChannel = BeanCopyUtils.copy(aiqinChannel1, SkuPriceDraftReqVO.class);
                try {
                    aiqinChannel.setPriceTax(new BigDecimal(importVo.getReadyCol67()));
                } catch (Exception e) {
                    error.add("爱亲渠道价格式不正确");
                }
                priceList.add(aiqinChannel);
            }
            //萌贝树渠道价
            if (Objects.isNull(importVo.getReadyCol68())) {
                error.add("萌贝树渠道价不能为空");
            } else {
                SkuPriceDraftReqVO mengbeishuChannel1 = price.get("萌贝树渠道价");
                SkuPriceDraftReqVO mengbeishuChannel = BeanCopyUtils.copy(mengbeishuChannel1, SkuPriceDraftReqVO.class);
                try {
                    mengbeishuChannel.setPriceTax(new BigDecimal(importVo.getReadyCol68()));
                } catch (Exception e) {
                    error.add("萌贝树渠道价格式不正确");
                }
                priceList.add(mengbeishuChannel);
            }
            //小红马渠道价
            if (Objects.isNull(importVo.getReadyCol69())) {
                error.add("小红马渠道价不能为空");
            } else {
                SkuPriceDraftReqVO xiaohongmaChannel1 = price.get("小红马渠道价");
                SkuPriceDraftReqVO xiaohongmaChannel = BeanCopyUtils.copy(xiaohongmaChannel1, SkuPriceDraftReqVO.class);
                try {
                    xiaohongmaChannel.setPriceTax(new BigDecimal(importVo.getReadyCol69()));
                } catch (Exception e) {
                    error.add("小红马渠道价格式不正确");
                }
                priceList.add(xiaohongmaChannel);
            }
            //爱亲分销价
            if (Objects.isNull(importVo.getReadyCol70())) {
                error.add("爱亲分销价不能为空");
            } else {
                SkuPriceDraftReqVO aiqinDistribution1 = price.get("爱亲分销价");
                SkuPriceDraftReqVO aiqinDistribution = BeanCopyUtils.copy(aiqinDistribution1, SkuPriceDraftReqVO.class);
                try {
                    aiqinDistribution.setPriceTax(new BigDecimal(importVo.getReadyCol70()));
                } catch (Exception e) {
                    error.add("爱亲分销价格式不正确");
                }
                priceList.add(aiqinDistribution);
            }
            //萌贝树分销价
            if (Objects.isNull(importVo.getReadyCol71())) {
                error.add("萌贝树分销价不能为空");
            } else {
                SkuPriceDraftReqVO mengbeishuDistribution1 = price.get("萌贝树分销价");
                SkuPriceDraftReqVO mengbeishuDistribution = BeanCopyUtils.copy(mengbeishuDistribution1, SkuPriceDraftReqVO.class);
                try {
                    mengbeishuDistribution.setPriceTax(new BigDecimal(importVo.getReadyCol71()));
                } catch (Exception e) {
                    error.add("萌贝树分销价格式不正确");
                }
                priceList.add(mengbeishuDistribution);
            }
            //小红马分销价
            if (Objects.isNull(importVo.getReadyCol72())) {
                error.add("小红马分销价不能为空");
            } else {
                SkuPriceDraftReqVO xiaohongmaDistribution1 = price.get("小红马分销价");
                SkuPriceDraftReqVO xiaohongmaDistribution = BeanCopyUtils.copy(xiaohongmaDistribution1, SkuPriceDraftReqVO.class);
                try {
                    xiaohongmaDistribution.setPriceTax(new BigDecimal(importVo.getReadyCol72()));
                } catch (Exception e) {
                    error.add("小红马分销价格式不正确");
                }
                priceList.add(xiaohongmaDistribution);
            }
            //爱亲售价
            if (Objects.isNull(importVo.getReadyCol73())) {
                error.add("爱亲售价不能为空");
            } else {
                SkuPriceDraftReqVO aiqinSale1 = price.get("爱亲售价");
                SkuPriceDraftReqVO aiqinSale = BeanCopyUtils.copy(aiqinSale1, SkuPriceDraftReqVO.class);
                try {
                    aiqinSale.setPriceTax(new BigDecimal(importVo.getReadyCol73()));
                } catch (Exception e) {
                    error.add("爱亲售价格式不正确");
                }
                priceList.add(aiqinSale);
            }
            //萌贝树售价
            if (Objects.isNull(importVo.getReadyCol74())) {
                error.add("萌贝树售价不能为空");
            } else {
                SkuPriceDraftReqVO mengbeishuSale1 = price.get("萌贝树售价");
                SkuPriceDraftReqVO mengbeishuSale = BeanCopyUtils.copy(mengbeishuSale1, SkuPriceDraftReqVO.class);
                try {
                    mengbeishuSale.setPriceTax(new BigDecimal(importVo.getReadyCol74()));
                } catch (Exception e) {
                    error.add("萌贝树售价格式不正确");
                }
                priceList.add(mengbeishuSale);
            }
            //小红马售价
            if (Objects.isNull(importVo.getReadyCol75())) {
                error.add("小红马售价不能为空");
            } else {
                SkuPriceDraftReqVO xiaohongmaSale1 = price.get("小红马售价");
                SkuPriceDraftReqVO xiaohongmaSale = BeanCopyUtils.copy(xiaohongmaSale1, SkuPriceDraftReqVO.class);
                try {
                    xiaohongmaSale.setPriceTax(new BigDecimal(importVo.getReadyCol75()));
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
                    SaveSkuConfigReqVo huabei1 = warehouse.get("华北仓");
                    SaveSkuConfigReqVo huabei = BeanCopyUtils.copy(huabei1, SaveSkuConfigReqVo.class);
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
                    SaveSkuConfigReqVo huadong1 = warehouse.get("华东仓");
                    SaveSkuConfigReqVo huadong = BeanCopyUtils.copy(huadong1, SaveSkuConfigReqVo.class);
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
                    SaveSkuConfigReqVo huanan1 = warehouse.get("华南仓");
                    SaveSkuConfigReqVo huanan = BeanCopyUtils.copy(huanan1, SaveSkuConfigReqVo.class);
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
                    SaveSkuConfigReqVo xinan1 = warehouse.get("西南仓");
                    SaveSkuConfigReqVo xinan = BeanCopyUtils.copy(xinan1, SaveSkuConfigReqVo.class);
                    xinan.setConfigStatus(statusEnum.getStatus());
                    xinan.setConfigStatusName(statusEnum.getName());
                    configReqVos.add(xinan);
                }
            }
            //华中仓
            if (Objects.isNull(importVo.getReadyCol80())) {
                error.add("华中仓状态不能为空");
            } else {
                SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol80());
                if (Objects.isNull(statusEnum)) {
                    error.add("无法找到华中仓状态的状态");
                } else {
                    SaveSkuConfigReqVo huazhong1 = warehouse.get("华中仓");
                    SaveSkuConfigReqVo huazhong = BeanCopyUtils.copy(huazhong1, SaveSkuConfigReqVo.class);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer reUpdateApply(String applyCode) {
        Integer num = 0;
        if(StringUtils.isBlank(applyCode)){
            throw new BizException(ResultCode.REQUIRED_PARAMETER);
        }
        List<ApplyProductSku> applyProductSkus = applyProductSkuMapper.selectApplyByApplyCode(applyCode);
        if(CollectionUtils.isEmpty(applyProductSkus)){
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
        Byte applyStatus = applyProductSkus.get(0).getApplyStatus();
        //判断状态
        if(!Objects.equals(applyStatus,ApplyStatus.REVOKED.getNumber()) && !Objects.equals(applyStatus,ApplyStatus.APPROVAL_FAILED.getNumber())){
            throw new BizException(ResultCode.REUPDATE_ERROR);
        }
        AuthToken user = getUser();
        //数据验证
        List<String> repeatSkuName = Lists.newArrayList();
        List<String> draftAll = productSkuDao.getDraftAll(applyCode);
        if(CollectionUtils.isNotEmpty(draftAll)){
            repeatSkuName.addAll(draftAll);
        }
        List<String> applyAll = productSkuDao.getApplyAll(applyCode);
        if(CollectionUtils.isNotEmpty(applyAll)){
            repeatSkuName.addAll(applyAll);
        }
        List<String> all = productSkuInfoMapper.getAll(applyCode);
        if(CollectionUtils.isNotEmpty(all)){
            repeatSkuName.addAll(all);
        }
        if(CollectionUtils.isNotEmpty(repeatSkuName)){
            throw new BizException(MessageId.create(Project.SCMP_API, 13, "SKU名称["+StringUtils.join(repeatSkuName, ",")+"],在数据库中存在相同名称"));
        }
        //验证名称销售码是否重复
        List<String> salesCodeTmps = productSkuSalesInfoService.checkSalesCodes(applyCode);
        if(CollectionUtils.isNotEmpty(salesCodeTmps)){
            throw new BizException(MessageId.create(Project.SCMP_API, 69,  "条形码["+StringUtils.join(salesCodeTmps, ",")+"],在数据库中存在相同条形码"));
        }
        List<String> applySkuCodes = applyProductSkus.stream().map(ApplyProductSku::getSkuCode).distinct().collect(Collectors.toList());
        Map<String, ProductSkuDraft> officialBySkuCodes = productSkuDraftMapper.getOfficialBySkuCodes(applySkuCodes, user.getCompanyCode());
        List<ProductSkuDraft> productSkuDrafts = BeanCopyUtils.copyList(applyProductSkus, ProductSkuDraft.class);
        productSkuDrafts.forEach(item->{
            ProductSkuDraft oldSku = officialBySkuCodes.get(item.getSkuCode());
            if(null != oldSku){
                item.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                item.setApplyTypeName(StatusTypeCode.UPDATE_APPLY.getName());
                String compareResult = new GetChangeValueUtil<ProductSkuDraft>().compareResult(oldSku, item, skuHeadMap);
                item.setChangeContent(compareResult);
            }else{
                item.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                item.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
                item.setChangeContent("新增SKU");
            }
        });
        Date currentDate = new Date();
        num =  ((SkuInfoService) AopContext.currentProxy()).batchInsertDraft(productSkuDrafts);
        List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCode(applyCode, TagTypeCode.SKU.getStatus());
        if(CollectionUtils.isNotEmpty(applyUseTagRecords)){
            applyUseTagRecords.forEach(item->{
                item.setApplyUseObjectCode(item.getUseObjectCode());
                item.setCreateBy(user.getPersonName());
                item.setUpdateBy(user.getPersonName());
                item.setCreateTime(currentDate);
                item.setUpdateTime(currentDate);
            });
            applyUseTagRecordService.saveBatch(applyUseTagRecords);
        }
        productSkuChannelService.insertDraftList(applyCode);
        productSkuStockInfoService.insertDraftList(applyCode);
        productSkuPurchaseInfoService.insertDraftList(applyCode);
        productSkuSalesInfoService.insertDraftList(applyCode);
        productSkuBoxPackingService.insertDraftList(applyCode);
        productSkuCheckoutService.insertDraftList(applyCode);
        productSkuSupplyUnitService.insertDraftList(applyCode);
        productSkuSupplyUnitCapacityService.insertDraftList(applyCode);
        productSkuAssociatedGoodsService.insertDraftList(applyCode);
        productSkuManufacturerService.insertDraftList(applyCode);
        productSkuInspReportService.insertDraftList(applyCode);
        productSkuDisInfoService.insertDraftList(applyCode);
        productSkuPriceInfoService.saveSkuPriceDraft(applyCode);
        productSkuConfigService.insertDraftList(applyCode);
        productSkuPicturesService.insertDraftList(applyCode);
        productSkuPicDescService.insertDraftList(applyCode);
        productSkuFileService.insertDraftList(applyCode);
        return num;
    }

    @Override
    public Integer saveDraftSkuInfo2(AddSkuInfoReqVO addSkuInfoReqVO) {
        ProductSkuInfo productSkuInfo=productSkuDao.getSkuInfo("203407");
        ProductSkuInfoWms productSkuInfoWms=new ProductSkuInfoWms();
        BeanCopyUtils.copy(productSkuInfo,productSkuInfoWms);
//        productSkuInfoWms.setSkuCode("testcode001");
//        productSkuInfoWms.setSkuName("testname001");
//        productSkuInfoWms.setProductBrandCode("测试品牌code");
//        productSkuInfoWms.setProductBrandName("测试品牌name");
//        productSkuInfoWms.setProductCategoryCode("测试品类code");
//        productSkuInfoWms.setProductCategoryName("测试品类name");
//        productSkuInfoWms.setSkuAbbreviation("测试品类简称");
//        productSkuInfoWms.setModelNumber("128*12");
//        productSkuInfoWms.setQualityAssuranceManagement((byte) 1);
//        productSkuInfoWms.setQualityDate("2020-03-03");
//        productSkuInfoWms.setUpdateTime(new Date());
//        productSkuInfoWms.setGoodsGifts((byte) 1);
//        productSkuInfoWms.setManufacturerGuidePrice(new BigDecimal(8.88));
        log.info(JSON.toJSONString(productSkuInfoWms));
        sendWms2(productSkuInfoWms);

        return 1;
    }

    @Override
    public Integer saveDraftSkuInfo3( ) {
//        ProductSkuInfo productSkuInfo=new ProductSkuInfo();
//        productSkuInfo.setSkuCode("testcode001");
//        productSkuInfo.setSkuName("testname001");
//        productSkuInfo.setProductCode("testcode001");
//        productSkuInfo.setProductName("testcode001");
//        productSkuInfo.setProductBrandCode("测试品牌code");
//        productSkuInfo.setProductBrandName("测试品牌name");
//        productSkuInfo.setProductCategoryCode("测试品类code");
//        productSkuInfo.setProductCategoryName("测试品类name");
//        productSkuInfo.setSkuAbbreviation("测试品类简称");
//        productSkuInfo.setModelNumber("128*12");
//        productSkuInfo.setQualityAssuranceManagement((byte) 1);
//        productSkuInfo.setQualityDate("2020-03-03");
//        productSkuInfo.setUpdateTime(new Date());
//        productSkuInfo.setGoodsGifts((byte) 1);
//        productSkuInfo.setManufacturerGuidePrice(new BigDecimal(8.88));
        ProductSkuInfo productSkuInfo=productSkuDao.getSkuInfo("203407");
        log.info(JSON.toJSONString(productSkuInfo));
        //进行sap传输
        Map<String, List<SapSkuStorageFinancial>> SapSkuStorageFinancialMap = Maps.newHashMap();
        List<QueryPriceChannelRespVo> priceChannels = priceChannelMapper.getList(new QueryPriceChannelReqVo());
        ProductSkuCheckout productSkuCheckout =new ProductSkuCheckout();
        Map<String, ProductSkuCheckout> skuCheckoutMap = null;
        List<SapProductSku> productSkuList = Lists.newArrayList();
        List<SapSkuSale> baseInfo2;
        baseInfo2 = Lists.newArrayList();
        SapProductSku  sapProductSku = new SapProductSku();
        sapProductSku.setSapSkuCode(productSkuInfo.getProductCode());
        sapProductSku.setIndustryDepartmentCode("A");
        //取一级品类
        sapProductSku.setCategoryCode(productSkuInfo.getProductCategoryCode().substring(0, 2));
        sapProductSku.setSkuName(productSkuInfo.getProductName());
        //基本视图
        SapProductSkuBase  baseInfo = new SapProductSkuBase();
        baseInfo.setUnit("EA");
        baseInfo.setProductGroupCode(productSkuInfo.getProductCategoryCode().substring(0, 2));
        baseInfo.setGroupCode(productSkuInfo.getProductCategoryCode());
        baseInfo.setWeight("KG");
        baseInfo.setStandard(productSkuInfo.getSpec());
        sapProductSku.setBaseInfo(baseInfo);
        sapProductSku.setBaseInfo1(SapSkuStorageFinancialMap.get(productSkuInfo.getSkuCode()));
//        productSkuCheckout = skuCheckoutMap.get(productSkuInfo.getProductCode());
        sapProductSku.setBaseInfo2(sapSkuSaleListHandler(null, baseInfo2, priceChannels));
        productSkuList.add(sapProductSku);
        sapStorageAbutment(productSkuList);
        return 1;
    }

    @Override
    public HttpResponse updateSkuTaxCode(QuerySkuListReqVO querySkuListReqVO) {
        if (StringUtils.isBlank(querySkuListReqVO.getSkuCode())) {
            throw new BizException("sku编号不能为空");
        }
        if (StringUtils.isBlank(querySkuListReqVO.getTaxCode())) {
            throw new BizException("税收分类编码不能为空");
        }
        Integer count = productSkuInfoMapper.updateTaxCodeBySkuCode(querySkuListReqVO.getSkuCode(), querySkuListReqVO.getTaxCode());
        if(count > 0){
            return HttpResponse.success();
        }else {
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
    }
}


