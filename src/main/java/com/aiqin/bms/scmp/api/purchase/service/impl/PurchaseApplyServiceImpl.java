package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.bms.scmp.api.bireport.service.ProSuggestReplenishmentService;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfig;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuConfigMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyProductDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderProductDao;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplierDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Supplier;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.FileReaderUtil;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private StockDao stockDao;
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

    @Override
    public HttpResponse applyList(PurchaseApplyRequest purchaseApplyRequest){
        PageResData pageResData = new PageResData();
        List<PurchaseApplyResponse> purchases = purchaseApplyDao.applyList(purchaseApplyRequest);
        if(CollectionUtils.isNotEmptyCollection(purchases)){
            for (PurchaseApplyResponse apply:purchases){
                // 计算sku数量 / 单品数量/ 采购含税金额 / 实物返金额
                PurchaseApplyProductInfoResponse info = this.applyProductReckon(apply.getPurchaseApplyId());
                // 计算sku数量
                Integer skuCount = purchaseApplyProductDao.skuCount(apply.getPurchaseApplyId(), null);
                apply.setSkuCount(skuCount);
                apply.setSingleCount(info.getSingleSum());
                apply.setProductTotalAmount(info.getTaxSum());
                apply.setReturnAmount(info.getMatterTaxSum());
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
        List<PurchaseApplyDetailResponse> detail = stockDao.purchaseProductList(purchases);
        if (CollectionUtils.isNotEmptyCollection(detail)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            PurchaseApplyReqVo applyReqVo;
            for (PurchaseApplyDetailResponse product : detail) {
                // 获取最高采购价(价格管理中供应商的含税价格)
                if (StringUtils.isNotBlank(product.getSkuCode()) && StringUtils.isNotBlank(product.getSupplierCode())) {
                    Long priceTax = productSkuPriceInfoMapper.selectPriceTax(product.getSkuCode(), product.getSupplierCode());
                    product.setPurchaseMax(priceTax == null ? 0 : priceTax.intValue());
                }
                // 查询采购的到货后周转期
                ProductSkuConfig cycleInfo = productSkuConfigDao.getCycleInfo(product.getSkuCode(), product.getTransportCenterCode());
                if(cycleInfo != null){
                    product.setReceiptTurnover(cycleInfo.getTurnoverPeriodAfterArrival());
                }
                // 报表取数据(预测采购件数， 预测到货时间， 近90天销量 )
                applyReqVo = new PurchaseApplyReqVo();
                applyReqVo.setSkuCode(product.getSkuCode());
                applyReqVo.setSupplierCode(product.getSupplierCode());
                applyReqVo.setTransportCenterCode(product.getTransportCenterCode());
                PurchaseApplyRespVo vo = replenishmentService.selectPurchaseApplySkuList(applyReqVo);
                if(vo != null){
                    product.setPurchaseNumber(vo.getAdviceOrders() == null ? 0: vo.getAdviceOrders().intValue());
                    try {
                        if(StringUtils.isNotBlank(vo.getPredictedArrival())){
                            Date parse = formatter.parse(vo.getPredictedArrival());
                            product.setReceiptTime(parse);
                        }
                    }catch (Exception e){
                        LOGGER.error("转换时间失败");
                    }
                    product.setSalesVolume(vo.getAverageAmount() == null ? 0: vo.getAverageAmount().intValue() * 90);
                    product.setShortageNumber(vo.getOutStockAffectMoney() == null ? 0: vo.getOutStockAffectMoney().intValue());
                    product.setShortageDay(vo.getOutStockContinuousDays() == null ? 0: vo.getOutStockContinuousDays().intValue());
                    product.setStockTurnover(vo.getArrivalCycle() == null ? 0: vo.getArrivalCycle().intValue());
                }
            }
        }
        Integer count = stockDao.purchaseProductCount(purchases);
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
            purchaseApply.setUpdateByName(applyProductRequest.getCreateByName());
            purchaseApply.setUpdateById(applyProductRequest.getCreateById());
            purchaseApplyDao.update(purchaseApply);
            purchaseApplyCode = purchaseApply.getPurchaseApplyCode();
            purchaseApplyProductDao.delete(purchaseApplyId);
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
        Integer productTaxSum = 0, matterTaxSum = 0;
        if(CollectionUtils.isNotEmptyCollection(products)) {
            for (PurchaseApplyDetailResponse product : products) {
                // 商品采购件数量
                Integer purchaseWhole = product.getPurchaseWhole() == null ? 0 : product.getPurchaseWhole();
                Integer purchaseSingle = product.getPurchaseSingle() == null ? 0 : product.getPurchaseSingle();
                // 包装数量
                Integer packNumber = product.getBaseProductContent() == null ? 0 : product.getBaseProductContent();
                // 计算商品采购件数量
                Integer singleCount = purchaseWhole * packNumber + purchaseSingle;
                // 计算采购含税总价
                Integer productPurchaseAmount = product.getProductPurchaseAmount() == null ? 0 : product.getProductPurchaseAmount();
                Integer productPurchaseSum = productPurchaseAmount * singleCount;
                product.setProductPurchaseSum(productPurchaseSum);
                if(product.getProductType() != null){
                    if(product.getProductType() == 0){
                        productPieceSum += purchaseWhole;
                        productSingleSum += singleCount;
                        productTaxSum += productPurchaseSum;
                    }else if(product.getProductType() == 1){
                        matterPieceSum += purchaseWhole;
                        matterSingleSum += singleCount;
                        matterTaxSum += productPurchaseSum;
                    }else if(product.getProductType() == 2){
                        giftSingleSum += purchaseWhole;
                        giftSingleSum += singleCount;
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
        info.setGiftTaxSum(0);
        info.setPieceSum(productPieceSum + matterPieceSum + giftPieceSum);
        info.setSingleSum(productSingleSum + matterSingleSum + giftSingleSum);
        info.setTaxSum(productTaxSum);
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
                PurchaseApplyProduct applyProduct;
                for (int i = 1; i <= result.length - 1; i++) {
                    record = result[i];
                    response = new PurchaseImportResponse();
                    if (StringUtils.isBlank(record[0]) || StringUtils.isBlank(record[1]) || StringUtils.isBlank(record[2]) ||
                            StringUtils.isBlank(record[3]) || StringUtils.isBlank(record[4]) || StringUtils.isBlank(record[5]) || StringUtils.isBlank(record[6])) {
                        HandleResponse(response, record,"导入的数据不全");
                        errorCount++;
                        continue;
                    }
                    supplier = supplyCompanyDao.selectBySupplierName(record[2]);
                    if(supplier==null){
                        HandleResponse(response, record,"未查询到供应商信息");
                        errorCount++;
                        continue;
                    }
                    logisticsCenter = logisticsCenterDao.selectByCenterName(record[3]);
                    if(logisticsCenter==null){
                        HandleResponse(response, record,"未查询到仓库信息");
                        errorCount++;
                        continue;
                    }
                    applyProduct = stockDao.purchaseBySkuStock(purchaseGroupCode, record[0], supplier.getSupplierCode(), "1028");
                    if(applyProduct != null){
                         // 报表取缺货影响的销售额， 缺货天数， 预测订货件数, 库存周转期
                        // TODO

                        // 获取到货后周转期
                        ProductSkuConfig cycleInfo = productSkuConfigDao.getCycleInfo(applyProduct.getSkuCode(), applyProduct.getTransportCenterCode());
                        if(cycleInfo != null){
                            applyProduct.setReceiptTurnover(cycleInfo.getTurnoverPeriodAfterArrival());
                        }
                        // 获取最高采购价(价格管理中供应商的含税价格)
                        if (StringUtils.isNotBlank(applyProduct.getSkuCode()) && StringUtils.isNotBlank(applyProduct.getSupplierCode())) {
                            Long priceTax = productSkuPriceInfoMapper.selectPriceTax(applyProduct.getSkuCode(), applyProduct.getSupplierCode());
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
                             response.setReturnWhole(whole);
                             response.setReturnSingle(single);
                         }
                        BeanUtils.copyProperties(applyProduct, response);
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
        response.setProductPurchaseAmount(Integer.valueOf(record[6]));
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
                                                                     String supplierCode, String transportCenterCode){
        if(StringUtils.isBlank(skuCode) || StringUtils.isBlank(supplierCode) || StringUtils.isBlank(transportCenterCode)){
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
        flow.setPurchaseAmountSum(singleCount * productPurchaseAmount);
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
    public HttpResponse contrast(List<PurchaseApplyProduct> list){
        if(CollectionUtils.isEmptyCollection(list)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询采购后的信息
        for(PurchaseApplyProduct product:list){
            // 计算采购的总营业额
        }
        return HttpResponse.success();
    }
}
