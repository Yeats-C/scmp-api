package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.excel.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryNewProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.PurchaseSaleStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.info.SaveSkuInfoReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewSkuDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBoxPackingRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.SkuStatusRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsWmsRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.product.service.impl.skuimport.CheckSkuNew;
import com.aiqin.bms.scmp.api.product.service.impl.skuimport.CheckSkuNewReally;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectResponse;
import com.aiqin.bms.scmp.api.purchase.manager.DataManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.purchasegroup.PurchaseGroupBuyerDao;
import com.aiqin.bms.scmp.api.supplier.dao.purchasegroup.PurchaseGroupDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordItemReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.mapper.ApplyUseTagRecordMapper;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewProductServiceImpl extends BaseServiceImpl implements NewProductService {
    @Autowired
    private NewProductMapper newProductMapper;
    @Autowired
    private ProductSkuManufacturerDao productSkuManufacturerDao;
    @Autowired
    private ProductSkuCheckoutMapper productSkuCheckoutMapper;
    @Autowired
    private ProductSkuStockInfoMapper productSkuStockInfoMapper;
    @Autowired
    private ProductSkuPurchaseInfoMapper productSkuPurchaseInfoMapper;
    @Autowired
    private ProductSkuDistributionInfoMapper productSkuDistributionInfoMapper;
    @Autowired
    private ProductSkuBoxPackingDao productSkuBoxPackingDao;
    @Autowired
    private ProductSkuConfigService productSkuConfigService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private ProductSkuSalesInfoDao productSkuSalesInfoDao;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private ProductSkuInfoMapper productSkuInfoMapper;
    @Autowired
    private ProductSkuPushWmsMapper productSkuPushWmsMapper;
    @Autowired
    private TagInfoService tagInfoService;
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
    private ProductSkuSalesInfoService productSkuSalesInfoService;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private ProductSkuConfigMapper productSkuConfigMapper;
    @Autowired
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;
    @Autowired
    private ProductSkuChannelMapper productSkuChannelMapper;
    @Autowired
    private PurchaseGroupDao purchaseGroupDao;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private ProductSkuDisInfoDao productSkuDisInfoDao;

    @Resource
    private CodeUtils codeUtils;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertProduct(NewProductSaveReqVO newProductSaveReqVO) {
        int flg = 0;
//        EncodingRule encodingRule = encodingRuleDao.getNumberingType("PRODUCT_CODE");
//        long code = encodingRule.getNumberingValue();
//        encodingRuleDao.updateNumberValue(code, encodingRule.getId());
        String code = codeUtils.getRedisCode("PRODUCT_CODE");
        NewProduct newProduct = new NewProduct();
        newProduct.setProductCode(code);
        BeanCopyUtils.copy(newProductSaveReqVO, newProduct);
        flg = ((NewProductService) AopContext.currentProxy()).save(newProduct);
        productCommonService.getInstance(code, HandleTypeCoce.ADD_PRODUCT.getStatus(), ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus(), newProduct, HandleTypeCoce.ADD_PRODUCT.getName());
        return newProduct.getProductCode();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProduct(NewProductUpdateReqVO newProductUpdateReqVO) {
        String productCode = newProductUpdateReqVO.getProductCode();
        ExceptionId(productCode);

        NewProduct newProduct = newProductMapper.getProductCode(productCode);
        if(null == newProduct){
            throw  new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
        int i = newProductMapper.checkName(newProductUpdateReqVO.getProductName(), newProduct.getCompanyCode(), productCode);
        if(i>0){
            throw  new BizException(ResultCode.SPU_NAME_EXISTS);
        }
        BeanCopyUtils.copy(newProductUpdateReqVO, newProduct);
        //设置审批状态为审批中
        //newProduct.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
        int flg = ((NewProductService) AopContext.currentProxy()).update(newProduct);
        productCommonService.getInstance(newProduct.getProductCode(), HandleTypeCoce.UPDATE_PRODUCT.getStatus(), ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus(), newProduct, HandleTypeCoce.UPDATE_PRODUCT.getName());
        return flg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public int save(NewProduct newProduct) {
        newProduct.setCompanyCode(getUser().getCompanyCode());
        newProduct.setCompanyName(getUser().getCompanyName());
        int i = newProductMapper.checkName(newProduct.getProductName(), newProduct.getCompanyCode(),null);
        if(i>0){
            throw  new BizException(ResultCode.SPU_NAME_EXISTS);
        }
        int k = newProductMapper.insertSelective(newProduct);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.PRODUCT);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Update
    public int update(NewProduct newProduct) {
        int k = newProductMapper.updateByPrimaryKeySelective(newProduct);
        if (k > 0) {
            return k;
        } else {
            log.error(HandlingExceptionCode.PRODUCT_UPDATE);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<NewProduct> list) {
        int flg = 0;
        if (list != null && list.size() > 0) {
            newProductMapper.deleteList(list);
            flg = newProductMapper.insertList(list);
            if (flg > 0) {
                return flg;
            }
            else {
                log.error(HandlingExceptionCode.PRODUCT);
                throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT);
            }
        }
        return flg;
    }

    @Override
    public void ExceptionId(String productCode) {
        if (StringUtils.isBlank(productCode)) {
            log.error(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
            throw new GroundRuntimeException(HandlingExceptionCode.PRODUCT_PRODUCTCODE);
        }
    }

    @Override
    public BasePage<NewProductResponseVO> getList(QueryNewProductReqVO queryNewProductReqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            queryNewProductReqVO.setCompanyCode(authToken.getCompanyCode());
            queryNewProductReqVO.setPersonId(authToken.getPersonId());
        }
        PageHelper.startPage(queryNewProductReqVO.getPageNo(), queryNewProductReqVO.getPageSize());
        List<NewProductResponseVO> list = newProductMapper.getList(queryNewProductReqVO);
        return PageUtil.getPageList(queryNewProductReqVO.getPageNo(), list);
    }

    @Override
    public List<NewSkuDetailsResponseVO> productSku(String productCode, String productName) {
        return newProductMapper.productSku(productCode, productName);
    }

    @Override
    public List<NewProductResponseVO> getPageList(QueryNewProductReqVO queryNewProductReqVO) {
        return newProductMapper.getList(queryNewProductReqVO);
    }
    @Override
    public Map<String,NewProduct> selectBySpuName(Set<String> list, String companyCode) {
        return newProductMapper.selectBySpuName(list,companyCode);
    }

    @Override
    public NewProductResponseVO getDetail(String productCode) {
        return newProductMapper.selectByProdutCode(productCode);
    }

    /**
     * 保存导入商品信息（真实表）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse saveImportSkuInfo(SkuImportReq saveSkuInfoReqVo) {
        HashMap<String, String> spuMap = Maps.newHashMap();
        for (AddSkuInfoReqVO reqVO : saveSkuInfoReqVo.getAddSkuList()) {
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
                            saveReqVO.setPurchasingGroupCode(saveSkuInfoReqVo.getPurchaseGroupCode());
                            saveReqVO.setPurchasingGroupName(saveSkuInfoReqVo.getPurchaseGroupName());
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
//            reqVO.getProductSkuDraft().setProcurementSectionCode(saveSkuInfoReqVo.getPurchaseGroupCode());
//            reqVO.getProductSkuDraft().setProcurementSectionName(saveSkuInfoReqVo.getPurchaseGroupName());
            Date date = new Date();
            // spu商品是否存在  不存在新增
            savespuInfo(reqVO.getSpuInfo(),reqVO.getProductSkuDraft(),date);
            // 保存商品信息
            saveSkuInfo(reqVO.getProductSkuDraft(),reqVO.getProductSkuConfigs(),date);
            // 保存标签信息
            saveSkuInfTag(reqVO.getTagInfoList(),reqVO.getProductSkuDraft());
            // 保存渠道信息
            saveSkuInfChannel(reqVO.getProductSkuChannelDrafts(),reqVO.getProductSkuDraft());
            // 保存进销存及包装信息
            saveSkuInfoPurchaseSaleBox(reqVO.getProductSkuDraft(),reqVO.getPurchaseSaleStockReqVos(),reqVO.getProductSkuBoxPackingDrafts());
            // 保存商品配置信息
            saveSkuInfoConfig(reqVO.getProductSkuConfigs(),reqVO.getProductSkuDraft());
            // 保存商品库存信息
            saveSkuInfoStock(reqVO.getProductSkuDraft());
            // 保存结算信息
            saveSkuInfoCheckout(reqVO.getProductSkuCheckoutDraft());
            // 保存供应商信息
            saveSkuInfoSupply(reqVO.getProductSkuSupplyUnitDrafts(), date);
            // 保存生产厂家
            saveSkuInfoManufacturer(reqVO.getProductSkuManufacturerDrafts(), reqVO.getProductSkuDraft());
            // 保存价格信息
            saveSkuInfoPrice(reqVO.getProductSkuPrices(),reqVO.getProductSkuDraft(), date);
        }
        return HttpResponse.success();
    }

    /** 保存商品渠道信息 */
    private void saveSkuInfChannel(List<ProductSkuChannelDraft> productSkuChannelDrafts, ProductSkuDraft productSkuDraft) {
        if (CollectionUtils.isNotEmpty(productSkuChannelDrafts)) {
            productSkuChannelDrafts.forEach(item->{
                item.setSkuCode(productSkuDraft.getSkuCode());
                item.setSkuName(productSkuDraft.getSkuName());
                item.setCreateBy(productSkuDraft.getCreateBy());
                item.setUpdateBy(productSkuDraft.getUpdateBy());
                item.setCreateTime(productSkuDraft.getCreateTime());
                item.setUpdateTime(productSkuDraft.getUpdateTime());
            });
            List<ProductSkuChannel> list = BeanCopyUtils.copyList(productSkuChannelDrafts, ProductSkuChannel.class);
            productSkuChannelMapper.insertBatch(list);
        }
    }

    /** 保存商品标签信息 */
    private void saveSkuInfTag(List<SaveUseTagRecordItemReqVo> tagInfoList,ProductSkuDraft productSkuDraft) {
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
//            applyUseTagRecordService.saveBatch(applyUseTagRecords);
            List<SaveUseTagRecordReqVo> records = Lists.newArrayList();
            SaveUseTagRecordReqVo saveUseTagRecordReqVo = new SaveUseTagRecordReqVo();
            saveUseTagRecordReqVo.setUseObjectCode(productSkuDraft.getSkuCode());
            saveUseTagRecordReqVo.setUseObjectName(productSkuDraft.getSkuName());
            saveUseTagRecordReqVo.setTagTypeCode(TagTypeCode.SKU.getStatus());
            saveUseTagRecordReqVo.setTagTypeName(TagTypeCode.SKU.getName());
//        saveUseTagRecordReqVo.setSourceCode(applyCode);
            List<SaveUseTagRecordItemReqVo> tagRecordItemReqVos = BeanCopyUtils.copyList(applyUseTagRecords, SaveUseTagRecordItemReqVo.class);
            saveUseTagRecordReqVo.setItemReqVos(tagRecordItemReqVos);
            records.add(saveUseTagRecordReqVo);
            tagInfoService.saveRecordList(records);
        }
//        if(CollectionUtils.isNotEmpty(tagInfoList)){
//            List<ApplyUseTagRecord> applyUseTagRecords = Lists.newArrayList();
//            tagInfoList.forEach(item->{
//                ApplyUseTagRecord applyUseTagRecord = new ApplyUseTagRecord();
//                applyUseTagRecord.setApplyUseObjectCode(productSkuDraft.getSkuCode());
//                applyUseTagRecord.setTagCode(item.getTagCode());
//                applyUseTagRecord.setTagName(item.getTagName());
//                applyUseTagRecord.setUseObjectCode(productSkuDraft.getSkuCode());
//                applyUseTagRecord.setUseObjectName(productSkuDraft.getSkuName());
//                applyUseTagRecord.setTagTypeCode(TagTypeCode.SKU.getStatus());
//                applyUseTagRecord.setTagTypeName(TagTypeCode.SKU.getName());
//                applyUseTagRecord.setCreateBy(productSkuDraft.getCreateBy());
//                applyUseTagRecord.setUpdateBy(productSkuDraft.getUpdateBy());
//                applyUseTagRecord.setCreateTime(productSkuDraft.getCreateTime());
//                applyUseTagRecord.setUpdateTime(productSkuDraft.getUpdateTime());
//                applyUseTagRecords.add(applyUseTagRecord);
//            });
//        }
    }

    /** 保存导入商品信息--sku进销存及包装信息 */
    private void saveSkuInfoPurchaseSaleBox(ProductSkuDraft productSkuDraft,List<PurchaseSaleStockReqVo> purchaseSaleStockReqVos, List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts) {
        Date date = new Date();
        // 进销存信息
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
            productSkuStockInfoDraft.setCreateBy(productSkuDraft.getCreateBy());
            productSkuStockInfoDraft.setUpdateBy(productSkuDraft.getUpdateBy());
            productSkuStockInfoDraft.setCreateTime(productSkuDraft.getCreateTime());
            productSkuStockInfoDraft.setUpdateTime(productSkuDraft.getUpdateTime());
            ProductSkuStockInfo record = new ProductSkuStockInfo();
            BeanUtils.copyProperties(productSkuStockInfoDraft, record);
            productSkuStockInfoMapper.insertSelective(record);
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
            ProductSkuPurchaseInfo productSkuPurchaseInfo = new ProductSkuPurchaseInfo();
            BeanUtils.copyProperties(productSkuPurchaseInfoDraft, productSkuPurchaseInfo);
            productSkuPurchaseInfoMapper.insertSelective(productSkuPurchaseInfo);
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
            List<ProductSkuSalesInfo> productSkuSalesInfos = BeanCopyUtils.copyList(productSkuSalesInfoDrafts, ProductSkuSalesInfo.class);
            productSkuSalesInfoDao.insertList(productSkuSalesInfos);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
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
            ProductSkuDisInfoDraft productSkuDisInfoDraft = BeanCopyUtils.copy(saleList.get(0), ProductSkuDisInfoDraft.class);
            productSkuDisInfoDraft.setProductSkuCode(productSkuDraft.getSkuCode());
            productSkuDisInfoDraft.setProductSkuName(productSkuDraft.getSkuName());
            productSkuDisInfoDraft.setProductCode(productSkuDraft.getProductCode());
            productSkuDisInfoDraft.setProductName(productSkuDraft.getProductName());
            productSkuDisInfoDraft.setCreateBy(productSkuDraft.getCreateBy());
            productSkuDisInfoDraft.setUpdateBy(productSkuDraft.getUpdateBy());
            productSkuDisInfoDraft.setCreateTime(productSkuDraft.getCreateTime());
            productSkuDisInfoDraft.setUpdateTime(productSkuDraft.getUpdateTime());
            ProductSkuDistributionInfo productSkuDistributionInfo = new ProductSkuDistributionInfo();
            BeanUtils.copyProperties(productSkuDisInfoDraft, productSkuDistributionInfo);
            productSkuDistributionInfoMapper.insertSelective(productSkuDistributionInfo);
        }catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
        if (CollectionUtils.isEmpty(productSkuBoxPackingDrafts)){
            throw new BizException(ResultCode.BOX_PACKING_EMPTY);
        }
        if(CollectionUtils.isNotEmpty(productSkuBoxPackingDrafts)){
            productSkuBoxPackingDrafts.forEach(item->{
                item.setProductSkuCode(productSkuDraft.getSkuCode());
                item.setProductSkuName(productSkuDraft.getSkuName());
                item.setCreateBy(productSkuDraft.getCreateBy());
                item.setUpdateBy(productSkuDraft.getUpdateBy());
                item.setCreateTime(productSkuDraft.getCreateTime());
                item.setUpdateTime(productSkuDraft.getUpdateTime());
            });
        }
        // 包装信息  采购销售单位相同存一份 不同都存
        List<ProductSkuBoxPacking> productSkuBoxPackings = BeanCopyUtils.copyList(productSkuBoxPackingDrafts, ProductSkuBoxPacking.class);
        productSkuBoxPackingDao.insertBoxList(productSkuBoxPackings);
    }

    /** 保存导入商品信息--sku生产厂家 */
    private void saveSkuInfoManufacturer(List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts, ProductSkuDraft productSkuDraft) {
        List<ProductSkuManufacturer> productSkuManufacturers = BeanCopyUtils.copyList(productSkuManufacturerDrafts, ProductSkuManufacturer.class);
        if(CollectionUtils.isNotEmpty(productSkuManufacturers)){
            productSkuManufacturers.forEach(item->{
                item.setProductSkuCode(productSkuDraft.getSkuCode());
                item.setProductSkuName(productSkuDraft.getSkuName());
                item.setCreateBy(productSkuDraft.getCreateBy());
                item.setUpdateBy(productSkuDraft.getUpdateBy());
                item.setCreateTime(productSkuDraft.getCreateTime());
                item.setUpdateTime(productSkuDraft.getUpdateTime());
            });
        }
        productSkuManufacturerDao.insertList(productSkuManufacturers);
    }

    /** 保存导入商品信息--sku结算信息 */
    private void saveSkuInfoCheckout(ProductSkuCheckoutDraft skuInfo) {
        Date date = new Date();
        //结算信息
        ProductSkuCheckout productSkuCheckout = new ProductSkuCheckout();
        productSkuCheckout.setOutputTaxRate(skuInfo.getOutputTaxRate());
        productSkuCheckout.setInputTaxRate(skuInfo.getInputTaxRate());
        productSkuCheckout.setIntegralCoefficient(skuInfo.getIntegralCoefficient());
        productSkuCheckout.setSettlementMethodCode(skuInfo.getSettlementMethodCode());
//        if("经销".equals(skuInfo.getSettlementMethodName())){
//            productSkuCheckout.setSettlementMethodCode("1");
//        } else if ("联营".equals(skuInfo.getSettlementMethodName())) {
//            productSkuCheckout.setSettlementMethodCode("2");
//        }else if ("代销".equals(skuInfo.getSettlementMethodName())){
//            productSkuCheckout.setSettlementMethodCode("3");
//        }
        productSkuCheckout.setSettlementMethodName(skuInfo.getSettlementMethodName());
        productSkuCheckout.setLogisticsFeeAwardRatio(skuInfo.getLogisticsFeeAwardRatio());
        productSkuCheckout.setSkuCode(skuInfo.getSkuCode());
        productSkuCheckout.setSkuName(skuInfo.getSkuName());
        productSkuCheckout.setCreateBy(getUser().getPersonName());
        productSkuCheckout.setUpdateBy(getUser().getPersonName());
        productSkuCheckout.setCreateTime(date);
        productSkuCheckout.setUpdateTime(date);
        productSkuCheckoutMapper.insertSelective(productSkuCheckout);
    }

    /** 保存导入商品信息--sku供应商信息 */
    private void saveSkuInfoSupply(List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts, Date date) {
        List<ProductSkuSupplyUnit> productSkuSupplyUnits = BeanCopyUtils.copyList(productSkuSupplyUnitDrafts, ProductSkuSupplyUnit.class);
        if(CollectionUtils.isNotEmpty(productSkuSupplyUnits)){
            productSkuSupplyUnits.forEach(item->{
                item.setCreateBy(getUser().getPersonName());
                item.setUpdateBy(getUser().getPersonName());
                item.setCreateTime(date);
                item.setUpdateTime(date);
                item.setUsageStatus((byte)1);
            });
        }
        productSkuSupplyUnitDao.insertList(productSkuSupplyUnits);
//        //供应商产能
//        if (CollectionUtils.isNotEmpty(productSkuSupplyUnitCapacities)){
//            productSkuSupplyUnitCapacityMapper.insertBatch(productSkuSupplyUnitCapacities);
//        }
    }

    /** 保存导入商品信息--sku价格信息 */
    private void saveSkuInfoPrice(List<SkuPriceDraftReqVO> skuInfos, ProductSkuDraft productSkuDraft,Date date) {
        List<ProductSkuPriceInfo> productSkuConfigs = BeanCopyUtils.copyList(skuInfos, ProductSkuPriceInfo.class);
        if(CollectionUtils.isNotEmpty(productSkuConfigs)){
            productSkuConfigs.forEach(item->{
                item.setSkuCode(productSkuDraft.getSkuCode());
                item.setSkuName(productSkuDraft.getSkuName());
                item.setCompanyCode(getUser().getCompanyCode());
                item.setCompanyName(getUser().getCompanyName());
                item.setCreateBy(getUser().getPersonName());
                item.setUpdateBy(getUser().getPersonName());
                item.setCreateTime(date);
                item.setUpdateTime(date);
                item.setBeContainArea(0);
                item.setPurchaseGroupCode(productSkuDraft.getProcurementSectionCode());
                item.setPurchaseGroupName(productSkuDraft.getProcurementSectionName());
            });
        }
        productSkuPriceInfoMapper.insertBatch(productSkuConfigs);
//        List<ProductSkuPriceInfo> productSkuConfigs = new ArrayList<>();
//        ProductSkuPriceInfo productSkuConfig1 = new ProductSkuPriceInfo();
//        productSkuConfig1.setPriceTax(skuInfo.getSkuPriceTax1());
//        saveSkuInfoPriceConfig(productSkuConfig1,skuInfo);
//        productSkuConfigs.add(productSkuConfig1);  // 爱亲渠道价
//
//        ProductSkuPriceInfo productSkuConfig2 = new ProductSkuPriceInfo();
//        productSkuConfig2.setPriceTax(skuInfo.getSkuPriceTax2());
//        saveSkuInfoPriceConfig(productSkuConfig2,skuInfo);
//        productSkuConfigs.add(productSkuConfig2);  // 萌贝树渠道价
//
//        ProductSkuPriceInfo productSkuConfig3 = new ProductSkuPriceInfo();
//        productSkuConfig3.setPriceTax(skuInfo.getSkuPriceTax3());
//        saveSkuInfoPriceConfig(productSkuConfig3,skuInfo);
//        productSkuConfigs.add(productSkuConfig3);  // 小红马渠道价
//
//        ProductSkuPriceInfo productSkuConfig4 = new ProductSkuPriceInfo();
//        productSkuConfig4.setPriceTax(skuInfo.getSkuPriceTax4());
//        saveSkuInfoPriceConfig(productSkuConfig4,skuInfo);
//        productSkuConfigs.add(productSkuConfig4);  // 爱亲分销价
//
//        ProductSkuPriceInfo productSkuConfig5 = new ProductSkuPriceInfo();
//        productSkuConfig5.setPriceTax(skuInfo.getSkuPriceTax5());
//        saveSkuInfoPriceConfig(productSkuConfig5,skuInfo);
//        productSkuConfigs.add(productSkuConfig5);  //  萌贝树分销价
//
//        ProductSkuPriceInfo productSkuConfig6 = new ProductSkuPriceInfo();
//        productSkuConfig6.setPriceTax(skuInfo.getSkuPriceTax6());
//        saveSkuInfoPriceConfig(productSkuConfig6,skuInfo);
//        productSkuConfigs.add(productSkuConfig6);  //  小红马分销价
//
//        ProductSkuPriceInfo productSkuConfig7 = new ProductSkuPriceInfo();
//        productSkuConfig7.setPriceTax(skuInfo.getSkuPriceTax7());
//        saveSkuInfoPriceConfig(productSkuConfig7,skuInfo);
//        productSkuConfigs.add(productSkuConfig7);  //  售价
//
//        ProductSkuPriceInfo productSkuConfig8 = new ProductSkuPriceInfo();
//        productSkuConfig8.setPriceTax(skuInfo.getSkuPriceTax8());
//        saveSkuInfoPriceConfig(productSkuConfig8,skuInfo);
//        productSkuConfigs.add(productSkuConfig8);  //  会员价
    }

    private ProductSkuPriceInfo saveSkuInfoPriceConfig(ProductSkuPriceInfo productSkuConfig, SaveSkuInfoReqVo skuInfo) {
        //初始化销项税率
        BigDecimal outputTaxRate = BigDecimal.ONE;
        BigDecimal outputTaxRateL = BigDecimal.ONE;
        final BigDecimal finalOutputTaxRate = outputTaxRate;
        final BigDecimal finalOutputTaxRateL = outputTaxRateL;
        productSkuConfig.setSkuCode(skuInfo.getSkuCode());
        productSkuConfig.setSkuName(skuInfo.getSkuName());
        productSkuConfig.setCompanyCode(getUser().getCompanyCode());
        productSkuConfig.setCompanyName(getUser().getCompanyName());
        productSkuConfig.setPurchaseGroupCode(skuInfo.getProcurementSectionCode());
        productSkuConfig.setPurchaseGroupName(skuInfo.getProcurementSectionName());
        productSkuConfig.setTax(finalOutputTaxRateL);
        BigDecimal taxIncludedPrice = productSkuConfig.getPriceTax().divide(BigDecimal.ONE.add(finalOutputTaxRate),4,BigDecimal.ROUND_HALF_UP);
        productSkuConfig.setPriceNoTax(taxIncludedPrice);
        productSkuConfig.setCreateBy(getUser().getPersonName());
        productSkuConfig.setUpdateBy(getUser().getPersonName());
        productSkuConfig.setCode("pp"+ IdUtil.uuid());
        productSkuConfig.setBeContainArea(0);
        Date date = new Date();
        productSkuConfig.setBeContainArea(0);
        if(!productSkuConfig.getEffectiveTimeStart().after(date)){
            productSkuConfig.setBeSynchronous(1);
        }else {
            productSkuConfig.setBeSynchronous(0);
        }
        return productSkuConfig;
    }

    /** 保存导入商品信息--spu信息 */
    private NewProduct savespuInfo(NewProduct skuInfo,ProductSkuDraft productSkuDraft, Date date) {
        // spu编码
        String productName = skuInfo.getProductName();
        // 判断商品名称是否为空
        ExceptionId(productName);
        // 查询该spu商品是否存在
        NewProduct newProduct = newProductMapper.getProductName(productName);
        if(null != newProduct){
            productSkuDraft.setProductCode(newProduct.getProductCode());
        }
        if(null == newProduct){
//            EncodingRule encodingRule = encodingRuleDao.getNumberingType("PRODUCT_CODE");
//            long code = encodingRule.getNumberingValue();
//            encodingRuleDao.updateNumberValue(code, encodingRule.getId());
            String code = codeUtils.getRedisCode("PRODUCT_CODE");
            // 等于null  不存在 新增
            NewProduct insetProduct = new NewProduct();
            insetProduct.setProductCode(code);
            insetProduct.setProductName(skuInfo.getProductName());
            insetProduct.setCompanyCode(getUser().getCompanyCode());
            insetProduct.setCompanyName(getUser().getCompanyName());
            insetProduct.setBarCode(skuInfo.getBarCode());
            insetProduct.setPurchasingGroupCode(productSkuDraft.getProcurementSectionCode());
            insetProduct.setPurchasingGroupName(productSkuDraft.getProcurementSectionName());
            insetProduct.setStyleNumber(skuInfo.getStyleNumber());
            insetProduct.setAbbreviation(skuInfo.getProductName());
            insetProduct.setCreateBy(getUser().getPersonName());
            insetProduct.setCreateTime(date);
            insetProduct.setUpdateBy(getUser().getPersonName());
            insetProduct.setUpdateTime(date);
            newProductMapper.insertSelective(insetProduct);
            productSkuDraft.setProductCode(code);
            return insetProduct;
        }
        return newProduct;
    }
    /** 保存导入商品信息--sku信息/配置信息 */
    private void saveSkuInfo(ProductSkuDraft skuInfo, List<SaveSkuConfigReqVo> productSkuConfigs, Date date) {
        ProductSkuInfo productSkuInfo = new ProductSkuInfo();
        BeanCopyUtils.copy(skuInfo, productSkuInfo);

        //        Map<String, SaveSkuConfigReqVo> warehouse = PriceAndWarehouseMap.warehouse;
        // 保存仓库信息
//        saveSkuInfoStock(skuInfo);
        List<SkuConfigsRepsVo> skuConfigsRepsVos = BeanCopyUtils.copyList(productSkuConfigs,SkuConfigsRepsVo.class);
        SkuStatusRespVo skuStatusRespVo = productSkuConfigService.calculationSkuStatus(skuConfigsRepsVos);
        productSkuInfo.setSkuStatus(skuStatusRespVo.getSkuStatus());
        productSkuInfo.setOnSale(Optional.ofNullable(skuStatusRespVo.getOnSale()).orElse(SkuSaleStatusEnum.NOT_IN_STOCK.getStatus()));
        productSkuInfo.setDelFlag(skuStatusRespVo.getOnSale());
        //拆分品类信息
        String productCategoryCode = productSkuInfo.getProductCategoryCode();
        if(StringUtils.isNotBlank(productCategoryCode)){
            String[] split = productCategoryCode.split(",");
            productCategoryCode = split[split.length-1];
            productSkuInfo.setProductCategoryCode(productCategoryCode);
        }
        productSkuInfo.setInventoryAllocation(Optional.ofNullable(productSkuInfo.getInventoryAllocation()).orElse(Global.BYTE_ZERO));
        productSkuInfo.setPriceModel(Optional.ofNullable(productSkuInfo.getPriceModel()).orElse(Global.BYTE_ZERO));
        productSkuInfo.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
        productSkuInfo.setGoodsGifts(skuInfo.getGoodsGifts());
        productSkuInfo.setCompanyCode(getUser().getCompanyCode());
        productSkuInfo.setCompanyName(getUser().getCompanyName());
        PurchaseGroupDTO purchaseGroupVo = purchaseGroupDao.selectByPurchaseName(skuInfo.getProcurementSectionName());
        productSkuInfo.setProcurementSectionCode(purchaseGroupVo.getPurchaseGroupCode());
        productSkuInfo.setProcurementSectionName(skuInfo.getProcurementSectionName());
        productSkuInfo.setCreateBy(getUser().getPersonName());
        productSkuInfo.setCreateTime(date);
        productSkuInfo.setUpdateBy(getUser().getPersonName());
        productSkuInfo.setUpdateTime(date);
        productSkuInfoMapper.insertSelective(productSkuInfo);
        skuInfo.setCreateBy(productSkuInfo.getCreateBy());
        skuInfo.setCreateTime(productSkuInfo.getCreateTime());
        skuInfo.setCompanyCode(getUser().getCompanyCode());
        skuInfo.setCompanyName(getUser().getCompanyName());
        skuInfo.setProcurementSectionCode(purchaseGroupVo.getPurchaseGroupCode());
        skuInfo.setProcurementSectionName(purchaseGroupVo.getPurchaseGroupName());
    }

    /** 保存导入商品信息--sku仓库信息 */
    private void saveSkuInfoStock(ProductSkuDraft skuInfo) {
        // 初始化每个仓库库存信息
        ArrayList<Stock> stockList = Lists.newArrayList();
        List<SkuConfigsRepsVo> configList = productSkuConfigMapper.getListBySkuCode(skuInfo.getSkuCode());
        if (CollectionUtils.isNotEmpty(configList)) {
            for (SkuConfigsRepsVo skuConfigsRepsVo : configList) {
                List<WarehouseDTO> warehouseDTO = warehouseDao.getWarehouseCodeByTransportCenterCode(skuConfigsRepsVo.getTransportCenterCode());
                if (CollectionUtils.isNotEmpty(warehouseDTO)) {
                    for (WarehouseDTO warehouse : warehouseDTO) {
                        Stock stock = BeanCopyUtils.copy(skuInfo, Stock.class);
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

    /** 保存导入商品信息--sku配置信息 */
    private void saveSkuInfoConfig(List<SaveSkuConfigReqVo> configs, ProductSkuDraft productSkuDraft) {
        for (SaveSkuConfigReqVo config : configs) {
            SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(config.getConfigStatusName());
            if (Objects.isNull(statusEnum)) {
//                error.add("无法找到华北仓的状态类型");
            } else {
                SkuConfigsRepsVo saveSkuConfig = new SkuConfigsRepsVo();
                saveSkuConfig.setConfigStatus(statusEnum.getStatus());
                // 保存sku配置
                ProductSkuConfig productSkuConfig = BeanCopyUtils.copy(config, ProductSkuConfig.class);
//                //设置编码
//                EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.SKU_CONFIG_CODE);
//                // 更新数据库编码
//                encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
                String redisCode = codeUtils.getRedisCode(EncodingRuleType.SKU_CONFIG_CODE);
                productSkuConfig.setConfigCode(redisCode);
                productSkuConfig.setProductCode(productSkuDraft.getProductCode());
                productSkuConfig.setProductName(productSkuDraft.getProductName());
                productSkuConfig.setSkuCode(productSkuDraft.getSkuCode());
                productSkuConfig.setSkuName(productSkuDraft.getSkuName());
//                productSkuConfig.setConfigStatus(statusEnum.getStatus());
                productSkuConfig.setCompanyCode(getUser().getCompanyCode());
                productSkuConfig.setCompanyName(getUser().getCompanyName());
                productSkuConfig.setCreateBy(getUser().getPersonName());
                productSkuConfig.setUpdateBy(getUser().getPersonName());
                productSkuConfigMapper.insertSelective(productSkuConfig);
            }
        }
    }

    @Override
    public SkuInfoImportMain saveImportSkuInfoCheck(MultipartFile file) {
        try {
            List<SkuInfoImportNewReally> skuInfoImports = ExcelUtil.readExcel(file, SkuInfoImportNewReally.class, 1, 0);
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
            List<SkuInfoImportReally> importList = Lists.newArrayList();
            Map<String, String> reaptMap = Maps.newHashMap();
            for (int i = 0; i < skuInfoImports.size(); i++) {
                //检查信息
                CheckSkuNewReally checkSku = new CheckSkuNewReally(productSkuMap, supplyCompanyMap, brandMap , categoryMap, channelMap, skuTagMap, reaptMap, skuInfoImports.get(i),spuMap,dicMap,manufactureMap,dicMap2)
                        .checkRepeat() //检查重复
                        .checkSKuNew() //新增检查sku
                        .checkBaseDate() //检查基础数据和spu
                        .checkInvoice() //检查进销存包装
                        .checkSettlement() //检查结算信息
                        .checkSupplier() //检查供应商
                        .checkPrice() //检查价格
                        .checkConfig() //检查配置
                        .checkManufacturer(); //检查厂家
                //返回实体
                AddSkuInfoReqVO resp = checkSku.getRespVO();
                SkuInfoImportReally skuInfoImport = checkSku.getSkuInfoImportReally();
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
//            return new SkuImportMain(skuInfoList,importList);
            Map<String, SkuInfoImportReally> skuMap = importList.stream().collect(Collectors.toMap(SkuInfoImportReally::getSkuCode, input -> input, (k1, k2) -> k1));
            for (AddSkuInfoReqVO addSkuInfoReqVO : skuInfoList) {
                if (skuMap.containsKey(addSkuInfoReqVO.getProductSkuDraft().getSkuCode())) {
                    addSkuInfoReqVO.setError(skuMap.get(addSkuInfoReqVO.getProductSkuDraft().getSkuCode()).getError());
                }
            }
            return new SkuInfoImportMain(skuInfoList,null);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }
    @Override
    public Map<String, ProductSkuInfo> selectBySkuNames(Set<String> skuNameList, String companyCode) {
        return productSkuInfoMapper.selectBySkuNames(skuNameList,companyCode);
    }

    @Override
    public HttpResponse skuImportWms() {
        // 查询出所有没有推送到wms商品信息
        List<String> skuCodes = productSkuPushWmsMapper.selectAllSkuCode();
        log.info("查询出所有没有推送到wms商品编码：[{}]", JsonUtil.toJson(skuCodes));
        for (String skuCode : skuCodes) {
            log.info("当前准备推送到wms商品编码：[{}]", JsonUtil.toJson(skuCode));
            ProductSkuInfoWms productSkuInfoWms=new ProductSkuInfoWms();
            // sku整箱商品包装信息
            List<ProductSkuBoxPackingRespVo> productSkuBoxPackingRespVos = productSkuBoxPackingDao.getList(skuCode);
            if(CollectionUtils.isNotEmpty(productSkuBoxPackingRespVos)){
                productSkuInfoWms.setPackgeUnit(productSkuBoxPackingRespVos.get(0).getLargeUnit());
                productSkuInfoWms.setBoxGrossWeight(productSkuBoxPackingRespVos.get(0).getNetWeight());
                productSkuInfoWms.setBoxLength(productSkuBoxPackingRespVos.get(0).getBoxHeight());
                productSkuInfoWms.setBoxHeight(productSkuBoxPackingRespVos.get(0).getBoxHeight());
                productSkuInfoWms.setBoxVolume(productSkuBoxPackingRespVos.get(0).getBoxVolume());
                productSkuInfoWms.setBoxGrossWeight(productSkuBoxPackingRespVos.get(0).getBoxGrossWeight());
                productSkuInfoWms.setNetWeight(productSkuBoxPackingRespVos.get(0).getNetWeight());
            }
            // sku商品库存配置信息
            List<PurchaseSaleStockRespVo> purchaseSaleStockRespVos = productSkuStockInfoMapper.getList(skuCode);
            if(CollectionUtils.isNotEmpty(purchaseSaleStockRespVos)){
                productSkuInfoWms.setUnitName(purchaseSaleStockRespVos.get(0).getUnitName());
                productSkuInfoWms.setUnitCode(purchaseSaleStockRespVos.get(0).getUnitCode());
            }
            // sku商品信息
            ProductSkuInfo productSkuInfo = productSkuInfoMapper.selectBySkuCode(skuCode);
            productSkuInfoWms.setFlag(Integer.valueOf(productSkuInfo.getGoodsGifts()));
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

            // sku商品门店销售信息
            PurchaseSaleStockRespVo purchaseSaleStockRespVo = productSkuSalesInfoDao.selectBarCodeBySkuCode(skuCode);
            log.info("sku条形码{}", JsonUtil.toJson(purchaseSaleStockRespVo));
            productSkuInfoWms.setSkuBarCode(purchaseSaleStockRespVo.getBarCode());

            // sku分销设置
            ProductSkuDisInfoDraft productSkuDisInfoDraft = productSkuDisInfoDao.getDraft(skuCode);
            if(productSkuDisInfoDraft != null){
                productSkuInfoWms.setSpec(productSkuDisInfoDraft.getSpec());
            }

            // sku经销商信息
            List<ProductSkuSupplyUnitRespVo> productSkuSupplyUnitRespVos = productSkuSupplyUnitDao.selectBySkuCode(skuCode);
            for (ProductSkuSupplyUnitRespVo productSkuSupplyUnitRespVo : productSkuSupplyUnitRespVos ) {
                if (productSkuSupplyUnitRespVo.getIsDefault().equals("1")){
                    productSkuInfoWms.setSupplyUnitCode(productSkuSupplyUnitRespVo.getSupplyUnitCode());
                    productSkuInfoWms.setSupplyUnitName(productSkuSupplyUnitRespVo.getSupplyUnitName());
                    break;
                }
            }
            // sku商品配置设置
            List<SkuConfigsRepsVo> skuConfigsRepsVoList=  productSkuConfigMapper.getListBySkuCode(skuCode);
            List<SkuConfigsWmsRepsVo> skuConfigsWmsRepsVos= Lists.newArrayList();
            SkuConfigsWmsRepsVo skuConfigsWmsRepsVo;
            for (SkuConfigsRepsVo skuConfigsRepsVo: skuConfigsRepsVoList ) {
                skuConfigsWmsRepsVo=new SkuConfigsWmsRepsVo();
                skuConfigsWmsRepsVo.setConfigStatus(skuConfigsRepsVo.getConfigStatus());
                skuConfigsWmsRepsVo.setTransportCenterCode(skuConfigsRepsVo.getTransportCenterCode());
                skuConfigsWmsRepsVo.setTransportCenterName(skuConfigsRepsVo.getTransportCenterName());
                skuConfigsWmsRepsVos.add(skuConfigsWmsRepsVo);
            }
            productSkuInfoWms.setSkuConfigsWmsRepsVos(skuConfigsWmsRepsVos);
            log.info("传入wms的消息为{}", JsonUtil.toJson(productSkuInfoWms));
            try {
                String url = urlConfig.WMS_API_URL2 + "/infoPushAndInquiry/source/productInfoPush";
                HttpClient httpClient = HttpClient.post(url).json(productSkuInfoWms).timeout(30000);
                HttpResponse result = httpClient.action().result(new TypeReference<HttpResponse>(){});
                if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                    log.info("穿入wms的sku商品信息失败，传入参数是[{}]", JsonUtil.toJson(productSkuInfoWms));
                }
            } catch (Exception e) {
                e.printStackTrace();
            log.info("穿入wms的sku商品信息失败，传入参数是[{}]", JsonUtil.toJson(productSkuInfoWms));
            }

            // 执行完 改变商品状态
            productSkuPushWmsMapper.updateWmsStatusBySkuCode(skuCode);
        }
        return HttpResponse.success();
    }

    private void dataValidation( List<SkuInfoImportNewReally> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuInfoImports.size()<3) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = SkuInfoImportNewReally.HEAD;
        String s = skuInfoImports.get(1).toString();
        System.out.println(head);
        System.out.println(s);
        boolean equals = StringUtils.equals(head,skuInfoImports.get(1).toString());
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

}
