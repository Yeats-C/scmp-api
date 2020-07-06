package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuManufacturerDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryNewProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.info.SaveSkuInfoReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewSkuDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuManufacturerRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.SkuStatusRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.NewProductService;
import com.aiqin.bms.scmp.api.product.service.ProductCommonService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuConfigService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class NewProductServiceImpl extends BaseServiceImpl implements NewProductService {
    @Autowired
    private NewProductMapper newProductMapper;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private ProductSkuConfigService productSkuConfigService;
    @Autowired
    private ProductSkuConfigMapper productSkuConfigMapper;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private ProductSkuInfoMapper productSkuInfoMapper;
    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;
    @Autowired
    private ProductSkuManufacturerDao productSkuManufacturerDao;
    @Autowired
    private ProductSkuCheckoutMapper productSkuCheckoutMapper;
    @Autowired
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Autowired
    private ProductSkuSupplyUnitCapacityMapper productSkuSupplyUnitCapacityMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertProduct(NewProductSaveReqVO newProductSaveReqVO) {
        int flg = 0;
        EncodingRule encodingRule = encodingRuleDao.getNumberingType("PRODUCT_CODE");
        long code = encodingRule.getNumberingValue();
        encodingRuleDao.updateNumberValue(code, encodingRule.getId());
        NewProduct newProduct = new NewProduct();
        newProduct.setProductCode(Long.toString(code));
        BeanCopyUtils.copy(newProductSaveReqVO, newProduct);
        flg = ((NewProductService) AopContext.currentProxy()).save(newProduct);
        productCommonService.getInstance(Long.toString(code), HandleTypeCoce.ADD_PRODUCT.getStatus(), ObjectTypeCode.PRODUCT_MANAGEMENT.getStatus(), newProduct, HandleTypeCoce.ADD_PRODUCT.getName());
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
    public HttpResponse saveImportSkuInfo(List<SaveSkuInfoReqVo> saveSkuInfoReqVo) {
        for (SaveSkuInfoReqVo skuInfo : saveSkuInfoReqVo) {
            // spu商品是否存在  不存在新增
            savespuInfo(skuInfo);
            // 保存商品信息
            saveSkuInfo(skuInfo);
            // 保存结算信息
            saveSkuInfoCheckout(skuInfo);
            // 保存价格信息
            saveSkuInfoPrice(skuInfo);
            // 保存供应商信息
            saveSkuInfoSupply(skuInfo);
            // 保存进销存及包装信息
            saveSkuInfoPurchaseSaleBox(skuInfo);
            // 保存生产厂家
            saveSkuInfoManufacturer(skuInfo);
        }
        return HttpResponse.success();
    }

    /** 保存导入商品信息--sku进销存及包装信息 */
    private void saveSkuInfoPurchaseSaleBox(SaveSkuInfoReqVo skuInfo) {

    }

    /** 保存导入商品信息--sku生产厂家 */
    private void saveSkuInfoManufacturer(SaveSkuInfoReqVo skuInfo) {
        List<ProductSkuManufacturer> productSkuManufacturers = new ArrayList<>();
         //商产厂家
        Date date = new Date();
        ProductSkuManufacturer productSkuManufacturer = new ProductSkuManufacturer();
//        productSkuManufacturer.setManufacturerCode();
        productSkuManufacturer.setManufacturerName(skuInfo.getManufacturerName());
        productSkuManufacturer.setFactoryProductNumber(skuInfo.getFactoryProductNumber());
        productSkuManufacturer.setAddress(skuInfo.getAddress());
        productSkuManufacturer.setProductSkuCode(skuInfo.getSkuCode());
        productSkuManufacturer.setProductSkuName(skuInfo.getSkuName());
        productSkuManufacturer.setUsageStatus(StatusTypeCode.USE.getStatus());
        productSkuManufacturer.setCreateBy(getUser().getPersonName());
        productSkuManufacturer.setUpdateBy(getUser().getPersonName());
        productSkuManufacturer.setCreateTime(date);
        productSkuManufacturer.setUpdateTime(date);
        productSkuManufacturers.add(productSkuManufacturer);
        productSkuManufacturerDao.insertList(productSkuManufacturers);
    }

    /** 保存导入商品信息--sku结算信息 */
    private void saveSkuInfoCheckout(SaveSkuInfoReqVo skuInfo) {
        Date date = new Date();
        //结算信息
        ProductSkuCheckout productSkuCheckout = new ProductSkuCheckout();
        productSkuCheckout.setOutputTaxRate(skuInfo.getOutputTaxRate());
        productSkuCheckout.setInputTaxRate(skuInfo.getInputTaxRate());
        productSkuCheckout.setIntegralCoefficient(skuInfo.getIntegralCoefficient());
        if("经销".equals(skuInfo.getSettlementMethodName())){
            productSkuCheckout.setSettlementMethodCode("1");
        } else if ("联营".equals(skuInfo.getSettlementMethodName())) {
            productSkuCheckout.setSettlementMethodCode("2");
        }else if ("代销".equals(skuInfo.getSettlementMethodName())){
            productSkuCheckout.setSettlementMethodCode("3");
        }
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
    private void saveSkuInfoSupply(SaveSkuInfoReqVo skuInfo) {
        Date date = new Date();
        //获取采购价格项目
        //                    QueryPriceProjectRespVo purchasePriceProject = priceProjectService.getPurchasePriceProject();
        //                    if(null == purchasePriceProject){
        //                        throw new BizException(ResultCode.SKU_PURCHASE_PRICE_IS_EMPTY);
        //                    }
        final BigDecimal finalInputTaxRate = skuInfo.getInputTaxRate();
        final BigDecimal finalInputTaxRateL = skuInfo.getInputTaxRate();
        List<ProductSkuSupplyUnit> productSkuSupplyUnits = new ArrayList<>();
        ProductSkuSupplyUnit productSkuSupplyUnit = new ProductSkuSupplyUnit();
        List<ProductSkuSupplyUnitCapacity> productSkuSupplyUnitCapacities = new ArrayList<>();
        ProductSkuSupplyUnitCapacity productSkuSupplyUnitCapacity = new ProductSkuSupplyUnitCapacity();

//        productSkuSupplyUnit.setSupplyUnitCode();
        productSkuSupplyUnit.setSupplyUnitName(skuInfo.getSupplyUnitName());
        productSkuSupplyUnit.setJointFranchiseRate(skuInfo.getJointFranchiseRate());
        productSkuSupplyUnit.setPoint(skuInfo.getPoint());
        productSkuSupplyUnit.setFactorySkuCode(skuInfo.getFactorySkuCode());
        productSkuSupplyUnit.setTaxIncludedPrice(skuInfo.getTaxIncludedPrice());
        if("配送".equals(skuInfo.getCategoriesSupplyChannelsName())){
            productSkuSupplyUnit.setCategoriesSupplyChannelsCode(String.valueOf(Global.ORDER_TYPE_1));
        }else if ("直送".equals(skuInfo.getCategoriesSupplyChannelsName())){
            productSkuSupplyUnit.setCategoriesSupplyChannelsCode(String.valueOf(Global.ORDER_TYPE_2));
        }else if ("货架直送".equals(skuInfo.getCategoriesSupplyChannelsName())){
            productSkuSupplyUnit.setCategoriesSupplyChannelsCode(String.valueOf(Global.ORDER_TYPE_3));
        }else if ("采购直送".equals(skuInfo.getCategoriesSupplyChannelsName())){
            productSkuSupplyUnit.setCategoriesSupplyChannelsCode(String.valueOf(Global.ORDER_TYPE_4));
        }
        productSkuSupplyUnit.setCategoriesSupplyChannelsName(skuInfo.getCategoriesSupplyChannelsName());
        productSkuSupplyUnit.setProductSkuCode(skuInfo.getSkuCode());
        productSkuSupplyUnit.setProductSkuName(skuInfo.getSkuName());
        productSkuSupplyUnit.setCreateBy(getUser().getPersonName());
        productSkuSupplyUnit.setUpdateBy(getUser().getPersonName());
        productSkuSupplyUnit.setCreateTime(date);
        productSkuSupplyUnit.setUpdateTime(date);
        //先把含税金额除以100兑换成元,含税金额/(1+税率) = 未税金额,最终结果*100转换成分,舍弃分以后的数字
        BigDecimal taxNoPrice = productSkuSupplyUnit.getTaxIncludedPrice().divide(BigDecimal.ONE.add(finalInputTaxRate),4,BigDecimal.ROUND_HALF_UP);
        productSkuSupplyUnit.setNoTaxPurchasePrice(taxNoPrice);
        productSkuSupplyUnit.setTaxRate(finalInputTaxRateL.longValue());
        productSkuSupplyUnit.setUsageStatus(StatusTypeCode.USE.getStatus());
        // 供应商产能
        productSkuSupplyUnitCapacity.setProductSkuCode(skuInfo.getSkuCode());
        productSkuSupplyUnitCapacity.setProductSkuName(skuInfo.getSkuName());
        productSkuSupplyUnitCapacity.setSupplyUnitCode(productSkuSupplyUnit.getSupplyUnitCode());
        productSkuSupplyUnitCapacity.setSupplyUnitName(skuInfo.getSupplyUnitName());
        productSkuSupplyUnitCapacity.setCreateBy(getUser().getPersonName());
        productSkuSupplyUnitCapacity.setUpdateBy(getUser().getPersonName());
        productSkuSupplyUnitCapacity.setCreateTime(date);
        productSkuSupplyUnitCapacity.setUpdateTime(date);
        productSkuSupplyUnitCapacities.add(productSkuSupplyUnitCapacity);

        productSkuSupplyUnits.add(productSkuSupplyUnit);
        productSkuSupplyUnitDao.insertList(productSkuSupplyUnits);
        //供应商产能
        if (CollectionUtils.isNotEmpty(productSkuSupplyUnitCapacities)){
            productSkuSupplyUnitCapacityMapper.insertBatch(productSkuSupplyUnitCapacities);
        }
    }

    /** 保存导入商品信息--sku价格信息 */
    private void saveSkuInfoPrice(SaveSkuInfoReqVo skuInfo) {
        List<ProductSkuPriceInfo> productSkuConfigs = new ArrayList<>();
        ProductSkuPriceInfo productSkuConfig1 = new ProductSkuPriceInfo();
        productSkuConfig1.setPriceTax(skuInfo.getSkuPriceTax1());
        saveSkuInfoPriceConfig(productSkuConfig1,skuInfo);
        productSkuConfigs.add(productSkuConfig1);  // 爱亲渠道价

        ProductSkuPriceInfo productSkuConfig2 = new ProductSkuPriceInfo();
        productSkuConfig2.setPriceTax(skuInfo.getSkuPriceTax2());
        saveSkuInfoPriceConfig(productSkuConfig2,skuInfo);
        productSkuConfigs.add(productSkuConfig2);  // 萌贝树渠道价

        ProductSkuPriceInfo productSkuConfig3 = new ProductSkuPriceInfo();
        productSkuConfig3.setPriceTax(skuInfo.getSkuPriceTax3());
        saveSkuInfoPriceConfig(productSkuConfig3,skuInfo);
        productSkuConfigs.add(productSkuConfig3);  // 小红马渠道价

        ProductSkuPriceInfo productSkuConfig4 = new ProductSkuPriceInfo();
        productSkuConfig4.setPriceTax(skuInfo.getSkuPriceTax4());
        saveSkuInfoPriceConfig(productSkuConfig4,skuInfo);
        productSkuConfigs.add(productSkuConfig4);  // 爱亲分销价

        ProductSkuPriceInfo productSkuConfig5 = new ProductSkuPriceInfo();
        productSkuConfig5.setPriceTax(skuInfo.getSkuPriceTax5());
        saveSkuInfoPriceConfig(productSkuConfig5,skuInfo);
        productSkuConfigs.add(productSkuConfig5);  //  萌贝树分销价

        ProductSkuPriceInfo productSkuConfig6 = new ProductSkuPriceInfo();
        productSkuConfig6.setPriceTax(skuInfo.getSkuPriceTax6());
        saveSkuInfoPriceConfig(productSkuConfig6,skuInfo);
        productSkuConfigs.add(productSkuConfig6);  //  小红马分销价

        ProductSkuPriceInfo productSkuConfig7 = new ProductSkuPriceInfo();
        productSkuConfig7.setPriceTax(skuInfo.getSkuPriceTax7());
        saveSkuInfoPriceConfig(productSkuConfig7,skuInfo);
        productSkuConfigs.add(productSkuConfig7);  //  售价

        ProductSkuPriceInfo productSkuConfig8 = new ProductSkuPriceInfo();
        productSkuConfig8.setPriceTax(skuInfo.getSkuPriceTax8());
        saveSkuInfoPriceConfig(productSkuConfig8,skuInfo);
        productSkuConfigs.add(productSkuConfig8);  //  会员价
        productSkuPriceInfoMapper.insertBatch(productSkuConfigs);
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
        productSkuConfig.setCreateBy("系统导入");
        productSkuConfig.setUpdateBy("系统导入");
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
    private void savespuInfo(SaveSkuInfoReqVo skuInfo) {
        // spu编码
        String productCode = skuInfo.getProductCode();
        // 判断商品编码是否为空
        ExceptionId(productCode);
        // 查询该spu商品是否存在
        NewProduct newProduct = newProductMapper.getProductCode(productCode);
        if(null == newProduct){
            // 等于null  不存在 新增
            NewProduct insetProduct = new NewProduct();
            insetProduct.setProductCode(productCode);
            insetProduct.setCompanyCode(getUser().getCompanyCode());
            insetProduct.setCompanyName(getUser().getCompanyName());
            insetProduct.setBarCode(skuInfo.getSaleBarCode());
            insetProduct.setProductName(skuInfo.getProductName());
            insetProduct.setPurchasingGroupCode(skuInfo.getProcurementSectionCode());
            insetProduct.setPurchasingGroupName(skuInfo.getProcurementSectionName());
            insetProduct.setStyleNumber(skuInfo.getStyleNumber());
            insetProduct.setAbbreviation(skuInfo.getProductName());
            newProductMapper.insertSelective(insetProduct);
        }
    }
    /** 保存导入商品信息--sku信息/配置信息 */
    private void saveSkuInfo(SaveSkuInfoReqVo skuInfo) {
        ProductSkuInfo productSkuInfo = new ProductSkuInfo();
        BeanCopyUtils.copy(skuInfo, productSkuInfo);

        List<SkuConfigsRepsVo> skuConfigsRepsVos = new ArrayList<>();
        Map<String, SaveSkuConfigReqVo> warehouse = PriceAndWarehouseMap.warehouse;
        //保存sku配置信息
        //华北仓
        if (Objects.isNull(skuInfo.getConfigStatusName1())) {
//            error.add("华北仓状态不能为空");
        } else {
            SaveSkuConfigReqVo huabei1 = warehouse.get("华北仓");
            SkuConfigsRepsVo skuInfoConfig = saveSkuInfoConfig(skuInfo.getConfigStatusName1(), huabei1, skuInfo);
            skuConfigsRepsVos.add(skuInfoConfig);
        }
        //华东仓
        if (Objects.isNull(skuInfo.getConfigStatusName2())) {
//            error.add("华东仓状态不能为空");
        } else {
            SaveSkuConfigReqVo huadong1 = warehouse.get("华东仓");
            SkuConfigsRepsVo skuInfoConfig = saveSkuInfoConfig(skuInfo.getConfigStatusName2(),huadong1, skuInfo);
            skuConfigsRepsVos.add(skuInfoConfig);
        }
        //华南仓
        if (Objects.isNull(skuInfo.getConfigStatusName3())) {
//            error.add("华南仓状态不能为空");
        } else {
            SaveSkuConfigReqVo huanan1 = warehouse.get("华南仓");
            SkuConfigsRepsVo skuInfoConfig = saveSkuInfoConfig(skuInfo.getConfigStatusName3(),huanan1, skuInfo);
            skuConfigsRepsVos.add(skuInfoConfig);
        }
        //西南仓
        if (Objects.isNull(skuInfo.getConfigStatusName4())) {
//            error.add("西南仓状态不能为空");
        } else {
            SaveSkuConfigReqVo xinan1 = warehouse.get("西南仓");
            SkuConfigsRepsVo skuInfoConfig = saveSkuInfoConfig(skuInfo.getConfigStatusName4(),xinan1, skuInfo);
            skuConfigsRepsVos.add(skuInfoConfig);
        }
        //华中仓
        if (Objects.isNull(skuInfo.getConfigStatusName5())) {
//            error.add("华中仓状态不能为空");
        } else {
            SaveSkuConfigReqVo huazhong1 = warehouse.get("华中仓");
            SkuConfigsRepsVo skuInfoConfig = saveSkuInfoConfig(skuInfo.getConfigStatusName5(),huazhong1, skuInfo);
            skuConfigsRepsVos.add(skuInfoConfig);
        }
        SkuStatusRespVo skuStatusRespVo = productSkuConfigService.calculationSkuStatus(skuConfigsRepsVos);
        productSkuInfo.setSkuStatus(skuStatusRespVo.getSkuStatus());
        productSkuInfo.setOnSale(Optional.ofNullable(skuStatusRespVo.getOnSale()).orElse(SkuSaleStatusEnum.NOT_IN_STOCK.getStatus()));
        productSkuInfo.setInventoryAllocation(Optional.ofNullable(productSkuInfo.getInventoryAllocation()).orElse(Global.BYTE_ZERO));
        productSkuInfo.setPriceModel(Optional.ofNullable(productSkuInfo.getPriceModel()).orElse(Global.BYTE_ZERO));
        productSkuInfo.setDelFlag(skuStatusRespVo.getOnSale());
        //拆分品类信息
        String productCategoryCode = productSkuInfo.getProductCategoryCode();
        if(StringUtils.isNotBlank(productCategoryCode)){
            String[] split = productCategoryCode.split(",");
            productCategoryCode = split[split.length-1];
            productSkuInfo.setProductCategoryCode(productCategoryCode);
        }
        productSkuInfo.setApplyTypeName(StatusTypeCode.ADD_APPLY.getName());
        productSkuInfo.setDelFlag((byte)0);
        if(Objects.equals(Global.PRODUCT_TYPE_0,skuInfo.getGoodsGiftsName())){
            productSkuInfo.setGoodsGifts((byte)0);
        }else {
            productSkuInfo.setGoodsGifts((byte)1);
        }
        // 保存仓库信息
        saveSkuInfoStock(skuInfo);
        productSkuInfoMapper.insertSelective(productSkuInfo);
    }

    /** 保存导入商品信息--sku仓库信息 */
    private void saveSkuInfoStock(SaveSkuInfoReqVo skuInfo) {
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
    private SkuConfigsRepsVo saveSkuInfoConfig(String configStatusName, SaveSkuConfigReqVo config, SaveSkuInfoReqVo skuInfo) {
        SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(configStatusName);
        if (Objects.isNull(statusEnum)) {
//                error.add("无法找到华北仓的状态类型");
        } else {
            SkuConfigsRepsVo saveSkuConfig = new SkuConfigsRepsVo();
            saveSkuConfig.setConfigStatus(statusEnum.getStatus());
            // 保存sku配置
            ProductSkuConfig productSkuConfig = BeanCopyUtils.copy(config, ProductSkuConfig.class);
            //设置编码
            EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.SKU_CONFIG_CODE);
            // 更新数据库编码
            encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
            productSkuConfig.setConfigCode(String.valueOf(encodingRule.getNumberingValue()));
            productSkuConfig.setProductCode(skuInfo.getProductCode());
            productSkuConfig.setProductName(skuInfo.getProductName());
            productSkuConfig.setSkuCode(skuInfo.getSkuCode());
            productSkuConfig.setSkuName(skuInfo.getSkuName());
            productSkuConfig.setConfigStatus(statusEnum.getStatus());
            productSkuConfig.setCompanyCode(getUser().getCompanyCode());
            productSkuConfig.setCompanyName(getUser().getCompanyName());
            productSkuConfig.setCreateBy("系统导入");
            productSkuConfig.setUpdateBy("系统导入");
            productSkuConfigMapper.insertSelective(productSkuConfig);
            return saveSkuConfig;
        }
        return null;
    }

}
