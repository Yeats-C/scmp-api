package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.bms.scmp.api.bireport.service.ProSuggestReplenishmentService;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPurchaseInfoDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfig;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuConfigMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.FileReaderUtil;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/
@Service
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseApplyServiceImpl.class);
    private static final String[] importRejectApplyHeaders = new String[]{
            "SKU编号", "SKU名称", "供应商", "仓库", "采购数量", "实物返数量", "含税单价",
    };

    @Resource
    private PurchaseApplyDao purchaseApplyDao;
    @Resource
    private PurchaseApplyProductDao purchaseApplyProductDao;
    @Resource
    private ProductSkuPriceInfoMapper productSkuPriceInfoDao;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private LogisticsCenterDao logisticsCenterDao;
    @Resource
    private SupplyCompanyDao supplyCompanyDao;
    @Resource
    private ProductSkuConfigMapper productSkuConfigDao;
    @Resource
    private ProSuggestReplenishmentService replenishmentService;
    @Resource
    private PurchaseOrderProductDao purchaseOrderProductDao;
    @Resource
    private GoodsRejectService goodsRejectService;
    @Resource
    private PurchaseGroupService purchaseGroupService;
    @Resource
    private ProductSkuDao productSkuDao;
    @Resource
    private BiAClassificationDao biAClassificationDao;
    @Resource
    private BiClassificationDao biClassificationDao;
    @Resource
    private BiGrossProfitMarginDao biGrossProfitMarginDao;
    @Resource
    private BiStockoutDetailDao biStockoutDetailDao;
    @Resource
    private BiStockoutRateDao biStockoutRateDao;

    @Override
    public HttpResponse applyList(PurchaseApplyRequest purchaseApplyRequest){
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.success();
        }
        purchaseApplyRequest.setGroupList(groupVoList);
        List<PurchaseApplyResponse> purchases = purchaseApplyDao.applyList(purchaseApplyRequest);
        PageResData pageResData = new PageResData();
        if(CollectionUtils.isNotEmptyCollection(purchases)){
            for (PurchaseApplyResponse apply:purchases){
                // 计算sku数量 / 单品数量/ 采购含税金额 / 实物返金额
                PurchaseApplyProductInfoResponse info = this.applyProductReckon(apply.getPurchaseApplyId());
                // 计算sku数量
                Integer skuCount = purchaseApplyProductDao.skuCount(apply.getPurchaseApplyId(), null);
                apply.setSkuCount(skuCount);
                apply.setSingleCount(info.getSingleSum());
                apply.setProductTotalAmount(info.getProductTaxSum());
                apply.setReturnAmount(info.getMatterTaxSum());
                apply.setGiftTaxSum(info.getGiftTaxSum());
                if(apply.getApplyStatus() == 0){
                    Integer count = purchaseApplyProductDao.skuCount(apply.getPurchaseApplyId(), Global.PURCHASE_APPLY_STATUS_1);
                    apply.setSubmitStatus(count > 0 ? 0 : 1);
                }else {
                    apply.setSubmitStatus(0);
                }
            }
        }
        Integer count = purchaseApplyDao.applyCount(purchaseApplyRequest);
        pageResData.setDataList(purchases);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse applyProductList(PurchaseApplyRequest purchases){
        if(StringUtils.isBlank(purchases.getPurchaseGroupCode())){
            return HttpResponse.failure(ResultCode.NOT_PURCHASE_GROUP_DATA);
        }
        this.fourProduct(purchases);
        // 新增时的商品信息
        PageResData pageResData = new PageResData();
        if(StringUtils.isBlank(purchases.getPurchaseApplyId())){
            return this.stockProductInfo(purchases, pageResData);
        }
        // 编辑时的商品信息
        List<PurchaseApplyDetailResponse> applyProducts = purchaseApplyProductDao.applyProductList(purchases);
        if(CollectionUtils.isNotEmptyCollection(applyProducts)){
            PurchaseApplyProduct applyProduct = null;
            for(PurchaseApplyDetailResponse product:applyProducts){
                if(StringUtils.isNotBlank(product.getCategoryId())){
                    String categoryName = goodsRejectService.selectCategoryName(product.getCategoryId());
                    product.setCategoryName(categoryName);
                }
                product.setReturnWhole(0);
                product.setReturnSingle(0);
                applyProduct = new PurchaseApplyProduct();
                applyProduct.setSkuCode(product.getSkuCode());
                if(product.getProductType().equals(Global.PRODUCT_TYPE_0)) {
                    applyProduct.setProductType(Global.PRODUCT_TYPE_2);
                    // 查询相同sku的实物返商品
                    PurchaseApplyProduct pro = purchaseApplyProductDao.applyProduct(applyProduct);
                    if(pro != null){
                        product.setReturnWhole(pro.getPurchaseWhole() == null ? 0 : pro.getPurchaseWhole());
                        product.setReturnSingle(pro.getPurchaseSingle() == null ? 0 : pro.getPurchaseSingle());
                    }
                }else if(product.getProductType().equals(Global.PRODUCT_TYPE_2)) {
                    applyProduct.setProductType(Global.PRODUCT_TYPE_0);
                    // 查询相同sku的实物返商品
                    PurchaseApplyProduct pro = purchaseApplyProductDao.applyProduct(applyProduct);
                    if(pro != null){
                        product.setPurchaseWhole(pro.getPurchaseWhole() == null ? 0 : pro.getPurchaseWhole());
                        product.setPurchaseWhole(pro.getPurchaseSingle() == null ? 0 : pro.getPurchaseSingle());
                    }
                }
            }
        }
        Integer count = purchaseApplyProductDao.applyProductCount(purchases);
        pageResData.setDataList(applyProducts);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    // 查询新增采购申请单时候的库存商品信息
    private HttpResponse stockProductInfo(PurchaseApplyRequest purchases, PageResData pageResData) {
        // 查询库存，商品， 供应商等信息
        List<PurchaseApplyDetailResponse> detail = productSkuDao.purchaseProductList(purchases);
        if (CollectionUtils.isNotEmptyCollection(detail)) {
            PurchaseApplyReqVo applyReqVo;
            Map<String, String> categoryNames = new HashMap<>();
            String categoryId;
            for (PurchaseApplyDetailResponse product : detail) {
                categoryId = product.getCategoryId();
                if(StringUtils.isNotBlank(categoryId)){
                    if (StringUtils.isBlank(categoryNames.get(categoryId))) {
                        categoryNames.put(categoryId, goodsRejectService.selectCategoryName(product.getCategoryId()));
                    }
                }
            }

            Map<String, Long> productTax = new HashMap<>();
            String key;
            for (PurchaseApplyDetailResponse product : detail) {
                key = String.format("%s,%s", product.getSkuCode(), product.getSupplierCode());
                if (productTax.get(key) == null) {
                    productTax.put(key, productSkuPriceInfoDao.selectPriceTax(product.getSkuCode(), product.getSupplierCode()));
                }
            }

            Map<String, PurchaseApplyRespVo> purchaseApply = new HashMap<>();
            for (PurchaseApplyDetailResponse product : detail) {
                key = String.format("%s,%s,%s", product.getSkuCode(), product.getSupplierCode(), product.getTransportCenterCode());
                if (purchaseApply.get(key) == null) {
                    applyReqVo = new PurchaseApplyReqVo();
                    applyReqVo.setSkuCode(product.getSkuCode());
                    applyReqVo.setSupplierCode(product.getSupplierCode());
                    applyReqVo.setTransportCenterCode(product.getTransportCenterCode());
                    purchaseApply.put(key, replenishmentService.selectPurchaseApplySkuList(applyReqVo));
                }
            }

            for (PurchaseApplyDetailResponse product : detail) {
                if(StringUtils.isNotBlank(product.getCategoryId())){
                    product.setCategoryName(categoryNames.get(product.getCategoryId()));
                }
                // 获取最高采购价(价格管理中供应商的含税价格)
                if (StringUtils.isNotBlank(product.getSkuCode()) && StringUtils.isNotBlank(product.getSupplierCode())) {
                    key = String.format("%s,%s", product.getSkuCode(), product.getSupplierCode());
                    Long priceTax = productTax.get(key);
                    product.setPurchaseMax(priceTax == null ? 0 : priceTax.intValue());
                }
                // 报表取数据(预测采购件数， 预测到货时间， 近90天销量 )
                key = String.format("%s,%s,%s", product.getSkuCode(), product.getSupplierCode(), product.getTransportCenterCode());
                PurchaseApplyRespVo vo = purchaseApply.get(key);
                if(vo != null){
                    product.setPurchaseNumber(vo.getAdviceOrders() == null ? 0: vo.getAdviceOrders().intValue());
                    if(StringUtils.isNotBlank(vo.getPredictedArrival())){
                        product.setReceiptTime(DateUtils.getDate(vo.getPredictedArrival()));
                    }
                    product.setSalesVolume(vo.getAverageAmount() == null ? 0: vo.getAverageAmount().intValue() * 90);
                    product.setShortageNumber(vo.getOutStockAffectMoney() == null ? 0: vo.getOutStockAffectMoney().intValue());
                    product.setShortageDay(vo.getOutStockContinuousDays() == null ? 0: vo.getOutStockContinuousDays().intValue());
                    product.setStockTurnover(vo.getArrivalCycle() == null ? 0: vo.getArrivalCycle().intValue());
                }
            }
        }
        Integer count = productSkuDao.purchaseProductCount(purchases);
        pageResData.setDataList(detail);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    private PurchaseApplyRequest fourProduct(PurchaseApplyRequest purchases){
        // 查询14大A品建议补货
        if(purchases.getAReplenishType() != null && purchases.getAReplenishType() == 0){
            List<String> strings = replenishmentService.selectSuggestReplenishmentByPro();
            purchases.setAReplenish(strings);
        }
        // 查询畅销商品建议补货
        if(purchases.getProductReplenishType() != null && purchases.getProductReplenishType() == 0){
            List<String> strings = replenishmentService.selectSuggestReplenishmentBySell();
            purchases.setProductReplenish(strings);
        }
        // 查询14大A品缺货
        if(purchases.getAShortageType() != null && purchases.getAShortageType() == 0){
            List<String> strings = replenishmentService.selectOutStockByPro();
            purchases.setAShortage(strings);
        }
        // 查询畅销商品缺货
        if(purchases.getProductShortageType() != null && purchases.getProductShortageType() == 0){
            List<String> strings = replenishmentService.selectOutStockBySell();
            purchases.setProductShortage(strings);
        }
        return purchases;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseApplyForm(PurchaseApplyProductRequest applyProductRequest){
        List<PurchaseApplyProduct> applyProducts = applyProductRequest.getApplyProducts();
        if(CollectionUtils.isEmptyCollection(applyProducts)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 判断此采购申请单的商品之前是否已生成
        String purchaseApplyId;
        String purchaseApplyCode;
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_APPLY_CODE);
        if(StringUtils.isNotBlank(applyProductRequest.getPurchaseApplyId())){
            purchaseApplyId = applyProductRequest.getPurchaseApplyId();
            PurchaseApply purchaseApply = purchaseApplyDao.purchaseApplyInfo(purchaseApplyId);
            purchaseApply.setApplyStatus(Global.PURCHASE_APPLY_STATUS_0);
            purchaseApply.setUpdateByName(applyProductRequest.getUpdateByName());
            purchaseApply.setUpdateById(applyProductRequest.getUpdateById());
            purchaseApplyDao.update(purchaseApply);
            purchaseApplyCode = purchaseApply.getPurchaseApplyCode();
            purchaseApplyProductDao.delete(purchaseApplyId);
            applyProductRequest.setCreateById(applyProductRequest.getUpdateById());
            applyProductRequest.setCreateByName(applyProductRequest.getUpdateByName());
        }else {
            // 生成采购申请单id
            purchaseApplyId = IdUtil.purchaseId();
            // 生成采购申请单
            PurchaseApply purchaseApply = new PurchaseApply();
            purchaseApply.setPurchaseApplyId(purchaseApplyId);
            // 获取采购申请单编码
            purchaseApplyCode = "CS" + String.valueOf(encodingRule.getNumberingValue());
            purchaseApply.setPurchaseApplyCode(purchaseApplyCode);
            purchaseApply.setApplyType(Global.PURCHASE_APPLY_TYPE_0);
            purchaseApply.setApplyStatus(Global.PURCHASE_APPLY_STATUS_0);
            purchaseApply.setPurchaseGroupCode(applyProducts.get(0).getPurchaseGroupCode());
            purchaseApply.setPurchaseGroupName(applyProducts.get(0).getPurchaseGroupName());
            purchaseApply.setCreateById(applyProductRequest.getCreateById());
            purchaseApply.setCreateByName(applyProductRequest.getCreateByName());
            purchaseApplyDao.insert(purchaseApply);
            encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
        }
        // 保存采购申请选中商品
        for(PurchaseApplyProduct product:applyProducts){
            product.setApplyProductId(IdUtil.purchaseId());
            product.setPurchaseApplyId(purchaseApplyId);
            product.setPurchaseApplyCode(purchaseApplyCode);
            product.setApplyProductStatus(Global.USER_ON);
        }
         purchaseApplyProductDao.insertAll(applyProducts);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse searchApplyProduct(String applyProductId){
        if(StringUtils.isBlank(applyProductId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseApplyDetailResponse> products = purchaseApplyProductDao.productListByDetail(applyProductId);
        if(CollectionUtils.isEmptyCollection(products)){
            LOGGER.info("查询采购申请商品的信息失败...{}:" +applyProductId);
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
        return HttpResponse.success(products);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse deleteApplyProduct(String applyProductId){
        if(StringUtils.isBlank(applyProductId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseApplyProduct purchaseApplyProduct = new PurchaseApplyProduct();
        purchaseApplyProduct.setApplyProductId(applyProductId);
        purchaseApplyProduct.setApplyProductStatus(Global.USER_OFF);
        Integer count = purchaseApplyProductDao.update(purchaseApplyProduct);
        if(count == 0){
            LOGGER.info("删除采购申请单的商品信息失败...{}" + applyProductId);
            return HttpResponse.failure(ResultCode.DELETE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse applyProductBasic(String purchaseApplyId){
        if(StringUtils.isBlank(purchaseApplyId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseApplyProductInfoResponse info = this.applyProductReckon(purchaseApplyId);
        return HttpResponse.success(info);
    }

    private PurchaseApplyProductInfoResponse applyProductReckon(String purchaseApplyId){
        // 计算商品（实物返，赠品）采购件数 、 单品总数 、含税总金额
        List<PurchaseApplyDetailResponse> products = purchaseApplyProductDao.productListByDetail(purchaseApplyId);
        PurchaseApplyProductInfoResponse info = new PurchaseApplyProductInfoResponse();
        Integer productPieceSum = 0, matterPieceSum = 0, giftPieceSum = 0;
        Integer productSingleSum= 0, matterSingleSum = 0, giftSingleSum = 0;
        Integer productTaxSum = 0, matterTaxSum = 0, giftTaxSum = 0, singleSum = 0;
        if(CollectionUtils.isNotEmptyCollection(products)) {
            for (PurchaseApplyDetailResponse product : products) {
                // 商品采购件数量
                Integer purchaseWhole = product.getPurchaseWhole() == null ? 0 : product.getPurchaseWhole();
                Integer purchaseSingle = product.getPurchaseSingle() == null ? 0 : product.getPurchaseSingle();
                // 包装数量
                Integer packNumber = product.getBaseProductContent() == null ? 0 : product.getBaseProductContent();
                // 计算商品采购件数量
                Integer singleCount = purchaseWhole * packNumber + purchaseSingle;
                singleSum += singleCount;
                // 计算采购含税总价
                Integer productPurchaseAmount = product.getProductPurchaseAmount() == null ? 0 : product.getProductPurchaseAmount();
                Integer productPurchaseSum = productPurchaseAmount * singleCount;
                product.setProductPurchaseSum(productPurchaseSum);
                if(product.getProductType() != null){
                    if(product.getProductType().equals(Global.PRODUCT_TYPE_0)){
                        productPieceSum += purchaseWhole;
                        productSingleSum += singleCount;
                        productTaxSum += productPurchaseSum;
                    }else if(product.getProductType().equals(Global.PRODUCT_TYPE_1)){
                        giftSingleSum += purchaseWhole;
                        giftSingleSum += singleCount;
                        giftTaxSum += productPurchaseSum;
                    }else if(product.getProductType().equals(Global.PRODUCT_TYPE_2)){
                        matterPieceSum += purchaseWhole;
                        matterSingleSum += singleCount;
                        matterTaxSum += productPurchaseSum;
                    }
                }
            }
        }
        info.setProductPieceSum(productPieceSum);
        info.setProductSingleSum(productSingleSum);
        info.setProductTaxSum(productTaxSum);
        info.setMatterPieceSum(matterPieceSum);
        info.setMatterSingleSum(matterSingleSum);
        info.setMatterTaxSum(matterTaxSum);
        info.setGiftPieceSum(giftPieceSum);
        info.setGiftSingleSum(giftSingleSum);
        info.setGiftTaxSum(giftTaxSum);
        info.setPieceSum(productPieceSum + matterPieceSum + giftPieceSum);
        info.setSingleSum(singleSum);
        //info.setTaxSum(productTaxSum);
        return info;
    }

    @Override
    public HttpResponse applySelectionProduct(String purchaseApplyId){
        if(StringUtils.isBlank(purchaseApplyId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseApplyDetailResponse> products = purchaseApplyProductDao.productListByDetail(purchaseApplyId);
        if(CollectionUtils.isNotEmptyCollection(products)){
            for(PurchaseApplyDetailResponse product:products){
                // 计算单品总数
                Integer purchaseWhole = product.getPurchaseWhole() == null ? 0 : product.getPurchaseWhole();
                Integer purchaseSingle = product.getPurchaseSingle() == null ? 0 : product.getPurchaseSingle();
                // 查询采购 对应的sku基商品含量
                Integer packNumber = product.getBaseProductContent() == null ? 0 : product.getBaseProductContent();
                Integer singleCount = purchaseWhole * packNumber + purchaseSingle;
                product.setSingleCount(singleCount);
                // 计算采购含税总价
                Integer productPurchaseAmount = product.getProductPurchaseAmount() == null ? 0 : product.getProductPurchaseAmount();
                Integer productPurchaseSum = productPurchaseAmount * singleCount;
                product.setProductPurchaseSum(productPurchaseSum);
            }
        }
        return HttpResponse.success(products);
    }

    @Override
    public HttpResponse purchaseApplyImport(MultipartFile file, String purchaseGroupCode){
        if (file == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        try {
            String[][] result = FileReaderUtil.readExcel(file, importRejectApplyHeaders.length);
            if(result.length<2){
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            List<PurchaseImportResponse> list = new ArrayList<>();
            Integer errorCount = 0;
            if (result != null) {
                String validResult = FileReaderUtil.validStoreValue(result, importRejectApplyHeaders);
                if (StringUtils.isNotBlank(validResult)) {
                    return HttpResponse.failure(MessageId.create(Project.SCMP_API, 88888, validResult));
                }
                String[] record;
                SupplyCompany supplier;
                LogisticsCenter logisticsCenter;
                PurchaseImportResponse response ;
                PurchaseApplyDetailResponse applyProduct;
                PurchaseApplyReqVo applyReqVo;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 1; i <= result.length - 1; i++) {
                    record = result[i];
                    response = new PurchaseImportResponse();
                    if (StringUtils.isBlank(record[0]) || StringUtils.isBlank(record[1]) || StringUtils.isBlank(record[2]) ||
                            StringUtils.isBlank(record[3]) || StringUtils.isBlank(record[4]) || StringUtils.isBlank(record[5]) || StringUtils.isBlank(record[6])) {
                        HandleResponse(response, record,"导入的数据不全");
                        errorCount++;
                        list.add(response);
                        continue;
                    }
                    supplier = supplyCompanyDao.selectBySupplierName(record[2]);
                    if(supplier==null){
                        HandleResponse(response, record,"未查询到供应商信息");
                        errorCount++;
                        list.add(response);
                        continue;
                    }
                    logisticsCenter = logisticsCenterDao.selectByCenterName(record[3]);
                    if(logisticsCenter==null){
                        HandleResponse(response, record,"未查询到仓库信息");
                        errorCount++;
                        list.add(response);
                        continue;
                    }
                    applyProduct = productSkuDao.purchaseBySkuStock(purchaseGroupCode, record[0], supplier.getSupplyCode(), logisticsCenter.getLogisticsCenterCode());
                    if(applyProduct != null){
                        if(StringUtils.isNotBlank(applyProduct.getCategoryId())){
                            String categoryName = goodsRejectService.selectCategoryName(applyProduct.getCategoryId());
                            applyProduct.setCategoryName(categoryName);
                        }
                         // 报表取缺货影响的销售额， 缺货天数， 预测订货件数, 库存周转期
                        applyReqVo = new PurchaseApplyReqVo();
                        applyReqVo.setSkuCode(record[0]);
                        applyReqVo.setSupplierCode(supplier.getSupplyCode());
                        applyReqVo.setTransportCenterCode(logisticsCenter.getLogisticsCenterCode());
                        PurchaseApplyRespVo vo = replenishmentService.selectPurchaseApplySkuList(applyReqVo);
                        if(vo != null){
                            applyProduct.setPurchaseNumber(vo.getAdviceOrders() == null ? 0: vo.getAdviceOrders().intValue());
                            if(StringUtils.isNotBlank(vo.getPredictedArrival())){
                               Date parse = formatter.parse(vo.getPredictedArrival());
                               applyProduct.setReceiptTime(parse);
                            }
                            applyProduct.setSalesVolume(vo.getAverageAmount() == null ? 0 : vo.getAverageAmount().intValue() * 90);
                            applyProduct.setShortageNumber(vo.getOutStockAffectMoney() == null ? 0 : vo.getOutStockAffectMoney().intValue());
                            applyProduct.setShortageDay(vo.getOutStockContinuousDays() == null ? 0 : vo.getOutStockContinuousDays().intValue());
                            applyProduct.setStockTurnover(vo.getArrivalCycle() == null ? 0 : vo.getArrivalCycle().intValue());
                        }
                        // 获取最高采购价(价格管理中供应商的含税价格)
                        if (StringUtils.isNotBlank(applyProduct.getSkuCode()) && StringUtils.isNotBlank(applyProduct.getSupplierCode())) {
                            Long priceTax = productSkuPriceInfoDao.selectPriceTax(applyProduct.getSkuCode(), applyProduct.getSupplierCode());
                            applyProduct.setPurchaseMax(priceTax == null ? 0 : priceTax.intValue());
                        }
                         if(record[4] != null){
                             String index = record[4].replace("零", "/").trim();
                             int index1 = index.indexOf("/");
                             int length = index.length();
                             Integer whole = Integer.valueOf(index.substring(0, index1));
                             Integer single = Integer.valueOf(index.substring(index1 + 1, length));
                             applyProduct.setPurchaseWhole(whole);
                             applyProduct.setPurchaseSingle(single);
                         }
                         if(record[5] != null){
                             String index = record[5].replace("零", "/").trim();
                             int index1 = index.indexOf("/");
                             int length = index.length();
                             Integer whole = Integer.valueOf(index.substring(0, index1));
                             Integer single = Integer.valueOf(index.substring(index1 + 1, length));
                             applyProduct.setReturnWhole(whole);
                             applyProduct.setReturnSingle(single);
                         }
                        BeanUtils.copyProperties(applyProduct, response);
                        if(StringUtils.isBlank((record[6]))){
                            response.setProductPurchaseAmount(0);
                        }else {
                            Integer value = Integer.valueOf(record[6]);
                            response.setProductPurchaseAmount(value * 100);
                        }
                    }else{
                        HandleResponse(response, record,"未查询到对应的商品");
                        errorCount++;
                    }
                    list.add(response);
                }
            }
            return HttpResponse.success(new PageResData(errorCount,list));
        } catch (Exception e) {
            LOGGER.error("采购申请单导入异常:{}", e.getMessage());
            return HttpResponse.failure(ResultCode.IMPORT_PURCHASE_APPLY_ERROR);
        }
    }

    private void HandleResponse(PurchaseImportResponse response, String[] record, String errorReason) {
        response.setSkuCode(record[0]);
        response.setSkuName(record[1]);
        response.setSupplierName(record[2]);
        response.setTransportCenterName(record[3]);
        response.setPurchaseCount(record[4]);
        response.setReturnCount(record[5]);
        if(StringUtils.isNotBlank(record[6] )){
            response.setProductPurchaseAmount(Integer.valueOf(record[6]));
        }
        response.setErrorInfo(errorReason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseApplyStatus(PurchaseApply purchaseApply){
        if(purchaseApply == null || StringUtils.isBlank(purchaseApply.getPurchaseApplyId())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer count = purchaseApplyDao.update(purchaseApply);
        if(count == 0){
            LOGGER.error("修改采购申请单是状态信息失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<PurchaseFlowPathResponse> applyProductDetail(Integer singleCount, Integer productPurchaseAmount, String skuCode,
                                                                     String supplierCode, String transportCenterCode, Integer productCount){
        if(StringUtils.isBlank(skuCode) || StringUtils.isBlank(supplierCode) || StringUtils.isBlank(transportCenterCode) ||
            productPurchaseAmount == null || singleCount == null || productCount == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseFlowPathResponse flow = new PurchaseFlowPathResponse();
        PurchaseApplyReqVo applyReqVo = new PurchaseApplyReqVo();
        applyReqVo.setSkuCode(skuCode);
        applyReqVo.setSupplierCode(supplierCode);
        applyReqVo.setTransportCenterCode(transportCenterCode);
        PurchaseApplyRespVo vo = replenishmentService.selectPurchaseApplySkuList(applyReqVo);
        flow.setPurchaseCount(singleCount);
        flow.setPurchaseAmount(productPurchaseAmount);
        flow.setPurchaseAmountSum(productCount * productPurchaseAmount);
        // 查询sku配置信息
        ProductSkuConfig cycleInfo = productSkuConfigDao.getCycleInfo(skuCode, transportCenterCode);
        if(cycleInfo != null){
            flow.setLargeInventoryWarnDay(cycleInfo.getLargeInventoryWarnDay());
            flow.setBigEffectPeriodWarnDay(cycleInfo.getBigEffectPeriodWarnDay());
            flow.setTurnoverPeriodAfterArrival(cycleInfo.getTurnoverPeriodAfterArrival());
            flow.setBasicInventoryDay(cycleInfo.getBasicInventoryDay());
            flow.setOrderCycle(cycleInfo.getOrderCycle());
            flow.setArrivalCycle(cycleInfo.getArrivalCycle());
        }
        if(vo != null){
            flow.setDayOne(vo.getNumOrderApproved() == null ? 0 : vo.getNumOrderApproved().intValue());
            flow.setDayTwo(vo.getNumApprovedPayment() == null ? 0 : vo.getNumApprovedPayment().intValue());
            flow.setDayThree(vo.getNumPaymentConfirm() == null ? 0 : vo.getNumPaymentConfirm().intValue());
            flow.setDayFour(vo.getNeedDays() == null ? 0 : vo.getNeedDays().intValue());
            flow.setAverageCount(vo.getAverageAmount() == null ? 0 : vo.getAverageAmount().intValue());
            Long availableNum = vo.getAvailableNum() == null ? 0 : vo.getAvailableNum();
            Long averageAmount = vo.getAverageAmount() == null ? 0 : vo.getAverageAmount();
            if(availableNum == 0 || averageAmount == 0){
                flow.setConsumeDay(0);
            }else {
                flow.setConsumeDay(availableNum.intValue() / averageAmount.intValue());
            }
            if(singleCount == 0 || averageAmount == 0){
                flow.setPurchaseTurnover(0);
            }else {
                flow.setPurchaseTurnover(singleCount / averageAmount.intValue());
            }
            flow.setPurchaseCycle(flow.getDayOne() + flow.getDayTwo() + flow.getDayThree() + flow.getDayFour() + flow.getOrderCycle());
            // 在途库存的查询(查询采购单商品审核完成、入库开始、入库中、备货发货的时间与采购数量)
            List<PurchaseApplyDetailResponse> statusByCount = purchaseOrderProductDao.orderStatusByCount(skuCode, transportCenterCode);
            if(CollectionUtils.isNotEmptyCollection(statusByCount)){
                Date d1 = new Date();
                List<PurchaseAfloatResponse> list = new ArrayList<>();
                PurchaseAfloatResponse afloat;
                for(PurchaseApplyDetailResponse order:statusByCount){
                    Integer single = order.getSingleCount() == null ? 0 : order.getSingleCount();
                    Integer actualSingle = order.getActualSingleCount() == null ? 0 : order.getActualSingleCount();
                    if(order.getExpectArrivalTime() != null){
                        int days = (int) ((order.getExpectArrivalTime().getTime() - d1.getTime()) / (24*3600*1000));
                        afloat = new PurchaseAfloatResponse();
                        afloat.setAfloatCount(single - actualSingle);
                        afloat.setAfloatDay(days);
                        list.add(afloat);
                    }
                }
                flow.setAfloatList(list);
            }
        }
        return HttpResponse.success(flow);
    }

    @Override
    public HttpResponse<PurchaseContrastResponse> contrast(List<PurchaseApplyDetailResponse> list){
        if(CollectionUtils.isEmptyCollection(list)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        DateTime dateTime = new DateTime(Calendar.getInstance().getTime());
        String data = dateTime.toString("yyyy-MM-dd");
        String beginTime = data + " 00:00:00";
        String finishTime = data + " 23:59:59";
        BigDecimal bigMum = new BigDecimal("0");
        PurchaseContrastResponse contrast = new PurchaseContrastResponse();
        contrast.setBeginTime(beginTime);
        contrast.setFinishTime(finishTime);
        contrast.setBigMum(bigMum);
        // 获取采购前总营业额/总利润
        BiGrossProfitMargin biGrossProfitMargin = biGrossProfitMarginDao.grossProfitMargin(beginTime, finishTime);
        if(biGrossProfitMargin != null){
            contrast.setFrontSumTurnover(biGrossProfitMargin.getTotalTurnover());
            contrast.setFrontSumProfit(biGrossProfitMargin.getGrossProfitMargin());
        }else {
            contrast.setFrontSumTurnover(bigMum);
            contrast.setFrontSumProfit(bigMum);
        }
        // 查询采购后的总营业额/总利润
        List<String> listSku = Lists.newArrayList();
        if(CollectionUtils.isNotEmptyCollection(list)){
            for (PurchaseApplyDetailResponse product:list){
                listSku.add(product.getSkuCode());
            }
            Integer afterSumTurnover = 0, afterSumProfit = 0;
            for(PurchaseApplyDetailResponse product:list){
                // 计算采购的总营业额
                Integer totalAmount = product.getProductPurchaseSum()== null ? 0 : product.getProductPurchaseSum();
                Integer singleCount = product.getSingleCount() == null ? 0 : product.getSingleCount();
                Integer priceMax = product.getPurchaseMax() == null ? 0 : product.getPurchaseMax();
                afterSumTurnover += totalAmount;
                afterSumProfit += singleCount * priceMax - totalAmount;
            }
            contrast.setAfterSumTurnover(new BigDecimal(afterSumTurnover));
            contrast.setAfterSumProfit(new BigDecimal(afterSumProfit));
        }else {
            contrast.setAfterSumTurnover(bigMum);
            contrast.setAfterSumProfit(bigMum);
        }
        // 缺货占比
        this.stockOut(contrast, listSku);
        // A品占比
        this.aCategory(contrast, list);
        // 分类占比
        this.category(contrast, list);
        return HttpResponse.success(contrast);
    }

    // 计算采购前后的缺货占比
    private void stockOut(PurchaseContrastResponse contrast, List<String> listSku){
        // 获取采购前缺货占比
        BiStockoutRate biStockoutRate = biStockoutRateDao.stockOutRateInfo(contrast.getBeginTime(), contrast.getFinishTime());
        if(biStockoutRate == null){
            contrast.setFrontShortageRatio(contrast.getBigMum());
        }else {
            contrast.setFrontShortageRatio(biStockoutRate.getOutStockRate());
        }
        // 计算采购后的缺货占比
        // 获取所有的缺货sku
        List<String> detailSku = biStockoutDetailDao.stockOutDetail(contrast.getBeginTime(), contrast.getFinishTime());
        long count1 = detailSku.size();
        if(CollectionUtils.isNotEmptyCollection(detailSku)){
            detailSku.removeAll(listSku);
            long count2 = count1 - detailSku.size();
            Long totalStockNum  = biStockoutRate.getTotalStockNum() == null ? 0 : biStockoutRate.getTotalStockNum();
            Long outStockNum  = biStockoutRate.getOutStockNum() == null ? 0 : biStockoutRate.getOutStockNum();
            BigDecimal bigNum1 = new BigDecimal(totalStockNum + listSku.size());
            BigDecimal bigNum2 = new BigDecimal(outStockNum - count2);
            contrast.setAfterShortageRatio(bigNum2.divide(bigNum1, 4 , ROUND_HALF_DOWN));
        }else {
            contrast.setAfterShortageRatio(contrast.getFrontShortageRatio());
        }
    }

    // 计算采购前后的A品占比
    private void aCategory(PurchaseContrastResponse contrast, List<PurchaseApplyDetailResponse> list){
        // 查询采购前的A 品占比
        BiAClassification aCategoryInfo = biAClassificationDao.aCategoryInfo(contrast.getBeginTime(), contrast.getFinishTime());
        contrast.setFrontACategory(aCategoryInfo);
        // 计算采购后的A 品占比
        if(CollectionUtils.isEmptyCollection(list)){
            contrast.setAfterACategory(aCategoryInfo);
        }else {
            BiAClassification aClassification = contrast.getFrontACategory();
            for(PurchaseApplyDetailResponse detail:list){
                if(detail == null || StringUtils.isBlank(detail.getProductPropertyCode()) || !detail.getProductPropertyCode().equals(1)){
                    continue;
                }
                aClassification = this.productCategory(detail, aClassification);
            }
            contrast.setAfterACategory(aClassification);
        }
    }

    private BiAClassification productCategory(PurchaseApplyDetailResponse detail, BiAClassification aClassification){
        // 获取商品类别
        if(StringUtils.isNotBlank(detail.getCategoryId())){
            Integer count = Integer.valueOf(detail.getCategoryId().substring(0, 2));
            switch (count){
                case 1:
                    aClassification.setMilkNum(aClassification.getMilkNum()== null ? 0 :
                            aClassification.getMilkNum() + 1);
                    break;
                case 2:
                    aClassification.setSideDishNum(aClassification.getSideDishNum() == null ?0 :
                            aClassification.getSideDishNum() + 1);
                    break;
                case 3:
                    aClassification.setHealthCareProductsNum(aClassification.getHealthCareProductsNum() == null ? 0 :
                            aClassification.getHealthCareProductsNum() + 1);
                    break;
                case 4:
                    aClassification.setDiaperNum(aClassification.getDiaperNum() == null ? 0 :
                            aClassification.getDiaperNum() + 1);
                    break;
                case 5:
                    aClassification.setProductsNum(aClassification.getProductsNum() == null ? 0 :
                            aClassification.getProductsNum() + 1);
                    break;
                case 6:
                    aClassification.setOccupyHomeNum(aClassification.getOccupyHomeNum() == null ? 0 :
                            aClassification.getOccupyHomeNum() + 1);
                    break;
                case 7:
                    aClassification.setFeedProductsNum(aClassification.getFeedProductsNum() == null ? 0 :
                            aClassification.getOccupyHomeNum() + 1);
                    break;
                case 8:
                    aClassification.setLatheChairNum(aClassification.getLatheChairNum() == null ? 0 :
                            aClassification.getOccupyHomeNum() + 1);
                    break;
                case 9:
                    aClassification.setToyNum(aClassification.getToyNum() == null ? 0 :
                            aClassification.getToyNum() + 1);
                    break;
                case 10:
                    aClassification.setBooksVideoSouvenirsNum(aClassification.getBooksVideoSouvenirsNum() == null ? 0 :
                            aClassification.getBooksVideoSouvenirsNum() + 1);
                    break;
                case 11:
                    aClassification.setCottonGoodsNum(aClassification.getCottonGoodsNum() == null ? 0 :
                            aClassification.getCottonGoodsNum() + 1);
                    break;
                case 12:
                    aClassification.setGiftsNum(aClassification.getGiftsNum() == null ? 0:
                            aClassification.getGiftsNum() + 1);
                    break;
                case 13:
                    aClassification.setMaterialNum(aClassification.getMaterialNum() == null ? 0 :
                            aClassification.getMaterialNum() + 1);
                    break;
                case 14:
                    aClassification.setDeMingJuNum(aClassification.getDeMingJuNum() == null ? 0 :
                            aClassification.getDeMingJuNum());
                    break;
                default:
                    aClassification.setOtherNum(aClassification.getOtherNum() == null ? 0 :
                            aClassification.getOtherNum());
                    break;
            }
        }
        return aClassification;
    }

    // 计算采购前后的分类占比
    private void category(PurchaseContrastResponse contrast, List<PurchaseApplyDetailResponse> list){
        // 查询采购前的分类占比
        BiClassification categoryInfo = biClassificationDao.categoryInfo(contrast.getBeginTime(), contrast.getFinishTime());
        contrast.setFrontCategory(categoryInfo);
        // 计算采购后的分类占比
        if(CollectionUtils.isEmptyCollection(list)){
            contrast.setAfterCategory(categoryInfo);
        }else {
            BiAClassification aClassification = contrast.getFrontACategory();
            for(PurchaseApplyDetailResponse detail:list){
                if(detail == null){
                    continue;
                }
                aClassification = this.productCategory(detail, aClassification);
            }
            BiClassification aClass = new BiClassification();
            BeanUtils.copyProperties(aClassification, aClass);
            contrast.setAfterCategory(aClass);
        }
    }
}
