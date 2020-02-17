package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.bms.scmp.api.bireport.service.ProSuggestReplenishmentService;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPurchaseInfoDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfig;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuConfigMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyTransportCenter;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.pdf.SupplyPdfResponse;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplySaveRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseNewContrastRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/
@Service
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseApplyServiceImpl.class);

    private static final BigDecimal big = BigDecimal.valueOf(0);

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
    private StockDao stockDao;
    @Resource
    private PurchaseOrderDetailsDao purchaseOrderDetailsDao;
    @Resource
    private WarehouseDao warehouseDao;
    @Resource
    private ProductSkuPurchaseInfoDao productSkuPurchaseInfoDao;
    @Resource
    private PurchaseApplyTransportCenterDao purchaseApplyTransportCenterDao;
    @Resource
    private FileRecordDao fileRecordDao;

    @Override
    public HttpResponse applyList(PurchaseApplyRequest purchaseApplyRequest){
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
        PageResData pageResData = new PageResData();
        if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.success(pageResData);
        }
        purchaseApplyRequest.setGroupList(groupVoList);
        List<PurchaseApplyResponse> purchases = purchaseApplyDao.applyList(purchaseApplyRequest);
        Integer count = purchaseApplyDao.applyCount(purchaseApplyRequest);
        pageResData.setDataList(purchases);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    // 查询新增采购申请单时候的库存商品信息
    @Override
    public HttpResponse applyProductList(PurchaseApplyRequest purchases){
        PageResData pageResData = new PageResData();
        if(StringUtils.isBlank(purchases.getPurchaseGroupCode())){
            return HttpResponse.success(pageResData);
        }
        this.fourProduct(purchases);
        // 新增时的商品信息
        if(StringUtils.isBlank(purchases.getPurchaseApplyId())){
            return this.stockProductInfo(purchases, pageResData);
        }
        // 编辑时的商品信息
        List<PurchaseApplyDetailResponse> applyProducts = purchaseApplyProductDao.applyProductList(purchases);
        if(CollectionUtils.isNotEmptyCollection(applyProducts)){
            PurchaseApplyProduct applyProduct;
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
    private HttpResponse stockProductInfo(PurchaseApplyRequest purchases, PageResData pageResData) {
        // 查询库存，商品， 供应商等信息
        List<PurchaseApplyDetailResponse> detail = productSkuDao.purchaseProductList(purchases);
        this.productDetail(detail);
        Integer count = productSkuDao.purchaseProductCount(purchases);
        pageResData.setDataList(detail);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    private void productDetail(List<PurchaseApplyDetailResponse> detail){
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

            Map<String, BigDecimal> productTax = new HashMap<>();
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
                if(product.getTaxCost() != null && product.getStockCount() != null){
                    product.setStockAmount(product.getTaxCost().multiply(BigDecimal.valueOf(product.getStockCount())).setScale(4, BigDecimal.ROUND_HALF_UP));
                }
                if(StringUtils.isNotBlank(product.getCategoryId())){
                    product.setCategoryName(categoryNames.get(product.getCategoryId()));
                }
                // 获取最高采购价(价格管理中供应商的含税价格)
                if (StringUtils.isNotBlank(product.getSkuCode()) && StringUtils.isNotBlank(product.getSupplierCode())) {
                    key = String.format("%s,%s", product.getSkuCode(), product.getSupplierCode());
                    BigDecimal priceTax = productTax.get(key);
                    product.setPurchaseMax(priceTax == null ? big : priceTax);
                }
                // 报表取数据(预测采购件数， 预测到货时间， 近90天销量 )
                key = String.format("%s,%s,%s", product.getSkuCode(), product.getSupplierCode(), product.getTransportCenterCode());
                PurchaseApplyRespVo vo = purchaseApply.get(key);
                if(vo != null){
                    product.setPurchaseNumber(vo.getAdviceOrders() == null ? 0: vo.getAdviceOrders().intValue());
                    if(StringUtils.isNotBlank(vo.getPredictedArrival())){
                        product.setReceiptTime(DateUtils.getDate(vo.getPredictedArrival()));
                    }
                    Integer averageAmount = vo.getAverageAmount() == null ? 0: vo.getAverageAmount().intValue();
                    product.setSalesVolumeAvg(averageAmount);
                    product.setSalesVolume(averageAmount * 90);
                    product.setShortageNumber(vo.getOutStockAffectMoney() == null ? big : vo.getOutStockAffectMoney());
                    product.setShortageDay(vo.getOutStockContinuousDays() == null ? 0 : vo.getOutStockContinuousDays().intValue());
                    product.setStockTurnover(vo.getArrivalCycle() == null ? 0: vo.getArrivalCycle().intValue());
                }
            }
        }
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
            purchaseApply.setCompanyCode(applyProductRequest.getCompanyCode());
            purchaseApply.setCompanyName(applyProductRequest.getCompanyName());
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
    public HttpResponse searchApplyProduct(String purchaseApplyId){
        if(StringUtils.isBlank(purchaseApplyId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseApplyDetailResponse> products = purchaseApplyProductDao.productListByDetail(purchaseApplyId);
        if(CollectionUtils.isEmptyCollection(products)){
            LOGGER.info("查询采购申请商品的信息失败...{}:" +purchaseApplyId);
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }else {
            // 查询采购申请单的公司
            PurchaseApply apply = purchaseApplyDao.purchaseApplyInfo(purchaseApplyId);
            Stock stock;
            for(PurchaseApplyDetailResponse detail:products){
                // 查询库存数量，库存金额， 在途库存
                stock = new Stock();
                stock.setSkuCode(detail.getSkuCode());
                stock.setTransportCenterCode(detail.getTransportCenterCode());
                stock.setWarehouseCode(detail.getWarehouseCode());
                stock.setCompanyCode(apply.getCompanyCode());
                Stock info = stockDao.stockInfo(stock);
                if(info != null){
                    detail.setStockCount(info.getInventoryNum().intValue());
                    detail.setTotalWayNum(info.getTotalWayNum().intValue());
                    detail.setStockAmount(info.getTaxCost().multiply(BigDecimal.valueOf(info.getInventoryNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
                    detail.setNewPurchasePrice(info.getNewPurchasePrice());
                }
            }
            this.productDetail(products);
        }                                                                                                                                
        return HttpResponse.success(products);
    }

    @Override
    public HttpResponse<List<PurchaseApplyTransportCenter>> transportCenterPurchase(String purchaseApplyCode, String transportCenterCode){
       if(StringUtils.isBlank(purchaseApplyCode)){
           return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
       }
       // 查询各个仓库的分仓信息
       List<PurchaseApplyTransportCenter> list = purchaseApplyTransportCenterDao.selectList(purchaseApplyCode, transportCenterCode);
       return HttpResponse.success(list);
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
    public HttpResponse productGroup(PurchaseApplyProductRequest request){
        if(CollectionUtils.isEmptyCollection(request.getApplyProducts())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 根据商品信息根据采购组、供应商、结算方式进行分组
        List<PurchaseApplyProduct> productList = request.getApplyProducts();
        List<PurchaseApplyProduct> products = productList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(o -> o.getSupplierCode() + ";" + o.getPurchaseGroupCode() + ";" + o.getSettlementMethodCode()))), ArrayList::new));
        LOGGER.info("分组后的商品信息：" + products.toString());
        return HttpResponse.success(products);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse applyPurchaseSave(PurchaseApplySaveRequest request){
        if(request == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 获取当前登录人的信息
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (currentAuthToken == null) {
            LOGGER.info("获取当前登录信息失败");
            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
        }
        // 添加采购申请单通用信息
        if(request.getPurchaseApply() == null){
            LOGGER.info("采购申请单的通用信息为空");
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_INFO_NULL);
        }
        Integer applyCount = purchaseApplyDao.insert(request.getPurchaseApply());
        LOGGER.info("添加采购申请单:{}", applyCount);

        // 添加采购申请单的分仓信息
        if(CollectionUtils.isEmptyCollection(request.getPurchaseTransportList())){
            LOGGER.info("采购申请单的分仓信息为空");
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_TRANSPORT_NULL);
        }
        Integer transportCount = purchaseApplyTransportCenterDao.insertAll(request.getPurchaseTransportList());
        LOGGER.info("添加采购申请单分仓信息:{}", transportCount);

        // 添加采购申请单的商品信息
        if(CollectionUtils.isEmptyCollection(request.getProductList())){
            LOGGER.info("采购申请单的商品信息为空");
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_PRODUCT_NULL);
        }
        Integer procuctCount = purchaseApplyProductDao.insertAll(request.getProductList());
        LOGGER.info("添加采购申请单商品信息:{}", procuctCount);

        // 添加采购申请单的文件信息
        if(CollectionUtils.isNotEmptyCollection(request.getFileList())){
            Integer count = fileRecordDao.insertAll("", request.getFileList());
            LOGGER.info("添加采购申请单文件:{}", count);
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
        Integer productSingleSum= 0, matterSingleSum = 0, giftSingleSum = 0, singleSum = 0;
        BigDecimal productTaxSum = big, matterTaxSum = big, giftTaxSum = big;
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
                BigDecimal productPurchaseAmount = product.getProductPurchaseAmount() == null ? big : product.getProductPurchaseAmount();
                BigDecimal productPurchaseSum = productPurchaseAmount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP);
                product.setProductPurchaseSum(productPurchaseSum);
                if(product.getProductType() != null){
                    if(product.getProductType().equals(Global.PRODUCT_TYPE_0)){
                        productPieceSum += purchaseWhole;
                        productSingleSum += singleCount;
                        productTaxSum = productPurchaseSum.add(productTaxSum);
                    }else if(product.getProductType().equals(Global.PRODUCT_TYPE_1)){
                        giftSingleSum += purchaseWhole;
                        giftSingleSum += singleCount;
                        giftTaxSum = productPurchaseSum.add(giftTaxSum);
                    }else if(product.getProductType().equals(Global.PRODUCT_TYPE_2)){
                        matterPieceSum += purchaseWhole;
                        matterSingleSum += singleCount;
                        matterTaxSum = productPurchaseSum.add(matterTaxSum);
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
                BigDecimal productPurchaseAmount = product.getProductPurchaseAmount() == null ? big : product.getProductPurchaseAmount();
                BigDecimal productPurchaseSum = productPurchaseAmount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP);
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
            List<PurchaseImportResponse> errorList = new ArrayList<>();
            Integer errorCount = 0;
            if (result != null) {
                String validResult = FileReaderUtil.validStoreValue(result, importRejectApplyHeaders);
                if (StringUtils.isNotBlank(validResult)) {
                    return HttpResponse.failure(MessageId.create(Project.SCMP_API, 88888, validResult));
                }
                String[] record;
                SupplyCompany supplier;
                LogisticsCenter logisticsCenter;
                PurchaseImportResponse response;
                PurchaseApplyDetailResponse applyProduct;
                PurchaseApplyReqVo applyReqVo;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 1; i <= result.length - 1; i++) {
                    record = result[i];
                    response = new PurchaseImportResponse();
                    response.setErrorNum(i);
                    if (StringUtils.isBlank(record[0]) || StringUtils.isBlank(record[1]) || StringUtils.isBlank(record[2]) ||
                            StringUtils.isBlank(record[3])) {
                        HandleResponse(response, record, "导入的数据不全；", i);
                        errorCount++;
                        errorList.add(response);
                        continue;
                    }
                    supplier = supplyCompanyDao.selectBySupplierName(record[2]);
                    if (supplier == null) {
                        HandleResponse(response, record, "未查询到供应商信息；", i);
                        errorCount++;
                        errorList.add(response);
                        continue;
                    }
                    logisticsCenter = logisticsCenterDao.selectByCenterName(record[3]);
                    if (logisticsCenter == null) {
                        HandleResponse(response, record, "未查询到仓库信息；", i);
                        errorCount++;
                        errorList.add(response);
                        continue;
                    }
                    applyProduct = productSkuDao.purchaseBySkuStock(purchaseGroupCode, record[0], supplier.getSupplyCode(), logisticsCenter.getLogisticsCenterCode());
                    if (applyProduct != null) {
                        if (StringUtils.isNotBlank(applyProduct.getCategoryId())) {
                            String categoryName = goodsRejectService.selectCategoryName(applyProduct.getCategoryId());
                            applyProduct.setCategoryName(categoryName);
                        }
                        // 报表取缺货影响的销售额， 缺货天数， 预测订货件数, 库存周转期
                        applyReqVo = new PurchaseApplyReqVo();
                        applyReqVo.setSkuCode(record[0]);
                        applyReqVo.setSupplierCode(supplier.getSupplyCode());
                        applyReqVo.setTransportCenterCode(logisticsCenter.getLogisticsCenterCode());
                        PurchaseApplyRespVo vo = replenishmentService.selectPurchaseApplySkuList(applyReqVo);
                        if (vo != null) {
                            applyProduct.setPurchaseNumber(vo.getAdviceOrders() == null ? 0 : vo.getAdviceOrders().intValue());
                            if (StringUtils.isNotBlank(vo.getPredictedArrival())) {
                                Date parse = formatter.parse(vo.getPredictedArrival());
                                applyProduct.setReceiptTime(parse);
                            }
                            applyProduct.setSalesVolume(vo.getAverageAmount() == null ? 0 : vo.getAverageAmount().intValue() * 90);
                            applyProduct.setShortageNumber(vo.getOutStockAffectMoney() == null ? big : vo.getOutStockAffectMoney());
                            applyProduct.setShortageDay(vo.getOutStockContinuousDays() == null ? 0 : vo.getOutStockContinuousDays().intValue());
                            applyProduct.setStockTurnover(vo.getArrivalCycle() == null ? 0 : vo.getArrivalCycle().intValue());
                        }
                        Integer baseProductContent = applyProduct.getBaseProductContent();
                        if (StringUtils.isNotBlank(record[4]) && baseProductContent != 0) {
                            Integer count = Double.valueOf(record[4]).intValue();
                            applyProduct.setPurchaseWhole(count / baseProductContent);
                            applyProduct.setPurchaseSingle(count % baseProductContent);
                        } else {
                            applyProduct.setPurchaseWhole(0);
                            applyProduct.setPurchaseSingle(0);
                        }
                        if (StringUtils.isNotBlank(record[5]) && baseProductContent != 0) {
                            Integer count = Double.valueOf(record[5]).intValue();
                            applyProduct.setReturnWhole(count / baseProductContent);
                            applyProduct.setReturnSingle(count % baseProductContent);
                        } else {
                            applyProduct.setReturnWhole(0);
                            applyProduct.setReturnSingle(0);
                        }
                        BeanUtils.copyProperties(applyProduct, response);
                        if (StringUtils.isBlank((record[6]))) {
                            response.setProductPurchaseAmount(applyProduct.getPurchaseMax() == null ? big : applyProduct.getPurchaseMax());
                        } else {
                            response.setProductPurchaseAmount(new BigDecimal(record[6]));
                        }
                    } else {
                        HandleResponse(response, record, "未查询到对应的商品；", i);
                        errorCount++;
                        errorList.add(response);
                    }
                    list.add(response);
                }
            }
            if(errorCount > 0){
                return HttpResponse.success(errorList);
            }
            return HttpResponse.success(list);
        } catch (Exception e) {
            LOGGER.error("采购申请单导入异常:{}", e.getMessage());
            return HttpResponse.failure(ResultCode.IMPORT_PURCHASE_APPLY_ERROR);
        }
    }

    private void HandleResponse(PurchaseImportResponse response, String[] record, String errorReason, int i) {
        response.setSkuCode(record[0]);
        response.setSkuName(record[1]);
        response.setSupplierName(record[2]);
        response.setTransportCenterName(record[3]);
        response.setPurchaseCount(record[4]);
        response.setReturnCount(record[5]);
        if(StringUtils.isNotBlank(record[6])){
            response.setProductPurchaseAmount(new BigDecimal(record[6]));
        }
        response.setErrorInfo("第" + (i + 1) + "行  " + errorReason);
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
    public HttpResponse<PurchaseFlowPathResponse> applyProductDetail(Integer singleCount, BigDecimal productPurchaseAmount, String skuCode,
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
        flow.setPurchaseAmountSum(productPurchaseAmount.multiply(BigDecimal.valueOf(productCount)).setScale(4, BigDecimal.ROUND_HALF_UP));
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
    public HttpResponse<PurchaseNewContrastResponse> purchaseContrast(PurchaseNewContrastRequest contrastRequest){
        if(contrastRequest == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseApplyDetailResponse> list;
        if(StringUtils.isNotBlank(contrastRequest.getPurchaseOrderId())){
            // 查询采购单对应的商品信息
            list = purchaseOrderProductDao.orderProductList(contrastRequest.getPurchaseOrderId());
        }else {
            list = contrastRequest.getProductList();
        }
        if(CollectionUtils.isEmptyCollection(list)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 根据sku、仓库、库房去重
        List<PurchaseApplyDetailResponse> details =
                list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(o -> o.getSkuCode() + "#" + o.getTransportCenterCode()
                                +  "#" + o.getWarehouseCode() + "#" + o.getSupplierCode() + "#" + o.getProductType()))),
                        ArrayList::new));
        BigDecimal frontTurnover = big, frontPurchaseCost = big;
        PurchaseNewContrastResponse response = new PurchaseNewContrastResponse();
        Integer unsalableFrontCount = 0, stockCount = 0, unsalableAfterCount = 0;
        for(PurchaseApplyDetailResponse detail:details){
            // 查询库存周转天数、大库存预警天数、库存数量
            if(StringUtils.isBlank(detail.getSkuCode()) || StringUtils.isBlank(detail.getTransportCenterCode()) ||
                    StringUtils.isBlank(detail.getWarehouseCode())){
                continue;
            }
            PurchaseStockResponse stockResponse = stockDao.stockCountByOtherInfo(detail.getSkuCode(), detail.getTransportCenterCode(), detail.getWarehouseCode());
            if(stockResponse == null){
                continue;
            }
            // 查询sku对应的爱亲渠道价
            BigDecimal channelPrice;
            if(StringUtils.isNotBlank(detail.getSkuCode()) || StringUtils.isNotBlank(detail.getSupplierCode())){
                channelPrice = productSkuPriceInfoDao.selectPriceTax(detail.getSkuCode(), detail.getSupplierCode());
                if(channelPrice == null){
                    channelPrice = big;
                }
            }else {
                channelPrice = big;
            }
            // 库存数量
            Long availableNum = stockResponse.getAvailableNum() == null ? 0L : stockResponse.getAvailableNum();
            BigDecimal taxCost = stockResponse.getTaxCost() == null ? big : stockResponse.getTaxCost();
            if(!detail.getProductType().equals(Global.PRODUCT_TYPE_2)){
                frontTurnover = channelPrice.multiply(BigDecimal.valueOf(availableNum)).setScale(4, BigDecimal.ROUND_HALF_UP).add(frontTurnover);
                frontPurchaseCost = taxCost.multiply(BigDecimal.valueOf(availableNum)).setScale(4, BigDecimal.ROUND_HALF_UP).add(frontPurchaseCost);
            }
        }
        List<PurchaseApplyDetailResponse> skuList =
                list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(o -> o.getSkuCode() + "#" + o.getTransportCenterCode()
                                +  "#" + o.getWarehouseCode() + "#" + o.getSupplierCode()))),
                        ArrayList::new));
        for(PurchaseApplyDetailResponse detail:skuList){
            if(StringUtils.isBlank(detail.getSkuCode()) || StringUtils.isBlank(detail.getTransportCenterCode()) ||
                    StringUtils.isBlank(detail.getWarehouseCode())){
                continue;
            }
            PurchaseStockResponse stockResponse = stockDao.stockCountByOtherInfo(detail.getSkuCode(), detail.getTransportCenterCode(), detail.getWarehouseCode());
            if(stockResponse == null){
                continue;
            }
            // 库存周转天数
            Integer daysTurnover = stockResponse.getDaysTurnover() == null ? 0 : stockResponse.getDaysTurnover().intValue();
            Integer largeInventoryWarnDay = stockResponse.getLargeInventoryWarnDay() == null ? 0 : stockResponse.getLargeInventoryWarnDay();
            // 计算滞销个数
            if(daysTurnover > largeInventoryWarnDay){
                ++unsalableFrontCount;
            }
            Long availableNum = stockResponse.getAvailableNum() == null ? 0L : stockResponse.getAvailableNum();
            if(availableNum <= 0){
                ++stockCount;
            }

            Map<String, Integer> purchaseMap = this.purchaseMap(list);
            StringBuilder sb = new StringBuilder();
            sb.append(detail.getSkuCode()).append("_");
            sb.append(detail.getTransportCenterCode()).append("_");
            sb.append(detail.getWarehouseCode()).append("_");
            sb.append(detail.getSupplierCode());

            // 采购数量
            Integer purchaseCount = purchaseMap.get(sb.toString());
            Double salesAvgMonthNum = stockResponse.getSalesAvgMonthNum() == null ? 0 : stockResponse.getSalesAvgMonthNum();
            // 计算采购后的滞销数
            if(purchaseCount + availableNum > largeInventoryWarnDay * salesAvgMonthNum){
                ++unsalableAfterCount;
            }
        }
        response.setFrontTurnover(frontTurnover);
        response.setFrontPurchaseCost(frontPurchaseCost);
        response.setFrontGrossProfit(frontTurnover.subtract(frontPurchaseCost));
        this.purchaseAfter(list, response);
        response.setFrontUnsalableSku(unsalableFrontCount);
        response.setAfterUnsalableSku(unsalableAfterCount);
        response.setSkuSum(skuList.size());
        response.setFrontShortageCount(stockCount);
        return HttpResponse.success(response);
    }

    private void purchaseAfter(List<PurchaseApplyDetailResponse> list, PurchaseNewContrastResponse response){
        BigDecimal afterTurnover = big, afterPurchaseCost = big;
        for(PurchaseApplyDetailResponse detail:list){
            // 销售价
            BigDecimal channelPrice =  productSkuPriceInfoDao.selectPriceTax(detail.getSkuCode(), detail.getSupplierCode());
            channelPrice = channelPrice == null ? big : channelPrice;
            // 采购价
            BigDecimal productAmount = detail.getProductAmount() == null ? big : detail.getProductAmount();
            // 采购数量
            Long singCount = detail.getSingleCount() == null ? 0L : detail.getSingleCount().longValue();
            if(detail.getProductType().equals(Global.PRODUCT_TYPE_2)){
                continue;
            }
            afterTurnover = channelPrice.multiply(BigDecimal.valueOf(singCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(afterTurnover);
            afterPurchaseCost = productAmount.multiply(BigDecimal.valueOf(singCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(afterPurchaseCost);
        }
        response.setAfterTurnover(response.getFrontTurnover().add(afterTurnover));
        response.setAfterPurchaseCost(response.getFrontPurchaseCost().add(afterPurchaseCost));
        response.setAfterGrossProfit(response.getAfterTurnover().subtract(response.getAfterPurchaseCost()));
    }

    private Map<String, Integer> purchaseMap(List<PurchaseApplyDetailResponse> list){
        List<Map<String, Object>> data = new ArrayList<>();
        HashMap<String, Object> map;
        for(PurchaseApplyDetailResponse detail:list){
            map = new HashMap<>();
            map.put("skuCode", detail.getSkuCode());
            map.put("transport", detail.getTransportCenterCode());
            map.put("warehouse", detail.getWarehouseCode());
            map.put("supplier", detail.getSupplierCode());
            map.put("sum", detail.getSingleCount());
            data.add(map);
        }
        Map<String, Integer> collect = data.stream().collect(Collectors.groupingBy(m -> {
            StringBuilder sb = new StringBuilder();
            sb.append(MapUtils.getString(m, "skuCode")).append("_");
            sb.append(MapUtils.getString(m, "transport")).append("_");
            sb.append(MapUtils.getString(m, "warehouse")).append("_");
            sb.append(MapUtils.getString(m, "supplier"));
            return sb.toString();
        }, Collectors.summingInt(s -> MapUtils.getInteger(s, "sum"))));
        return collect;
    }

    @Override
    public HttpResponse importPdf(String purchaseOrderCode, HttpServletResponse response){
        if(StringUtils.isBlank(purchaseOrderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String name = "订货单模板.htm";
        PDFUtil pdfUtil = new PDFUtil(name);
        /** 用于组装word、pdf页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<>();
        /** 组装数据 */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 查询采购单的信息
        PurchaseApplyDetailResponse detail = purchaseOrderDetailsDao.purchaseOrderDetail(purchaseOrderCode);
        dataMap.put("code", purchaseOrderCode);
        if(detail != null){
            dataMap.put("singleSum", detail.getSingleCount());
            if(detail.getExpectArrivalTime() != null) {
                dataMap.put("time", sdf.format(detail.getExpectArrivalTime()));
            }
        }
        String fileName = "订货单";
        if(StringUtils.isNotBlank(detail.getSupplierCode())) {
            dataMap.put("supplyName", detail.getSupplierName());
            fileName = "(" + detail.getSupplierCode() + ")" + detail.getSupplierName();
            SupplyPdfResponse supply = supplyCompanyDao.supplyInfoByPdf(detail.getSupplierCode());
            dataMap.put("number", supply == null || supply.getSupplyCode() == null ? "" : supply.getSupplyCode());
            dataMap.put("address", supply == null || supply.getAddress() == null ? "" : supply.getAddress());
            dataMap.put("phone", supply == null || supply.getMobilePhone() == null ? "" : supply.getMobilePhone());
            dataMap.put("fax", supply == null || supply.getFax() == null ? "" : supply.getFax());
            dataMap.put("contacts", supply == null || supply.getContactName() == null ? "" : supply.getContactName());
        }
        // 查询收货部门的信息
        if(StringUtils.isNotBlank(detail.getWarehouseCode())){
            WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(detail.getWarehouseCode());
            dataMap.put("dept", detail.getTransportCenterName());
            dataMap.put("goodsAddress", warehouse.getProvinceName() + warehouse.getCityName() + "/" + warehouse.getDetailedAddress());
            dataMap.put("mobile", warehouse.getPhone());
            dataMap.put("goodsPerson", warehouse.getContact());
        }
        // 查询采购单的商品信息
        List<PurchaseOrderProduct> list = purchaseOrderProductDao.orderProductInfo(detail.getPurchaseOrderId());
        List<Map<String, Object>> productList = new ArrayList<>();
        Map<String, Object> map;
        int i = 0;
        int box = 0;
        BigDecimal amountSum = new BigDecimal("0");
        if(CollectionUtils.isNotEmptyCollection(list)){
            for (PurchaseOrderProduct product:list) {
                map = new HashMap<>();
                box += product.getPurchaseWhole();
                map.put("id", ++i);
                map.put("skuCode", product.getSkuCode());
                // 查询sku 的销售条形码
                ProductSkuPurchaseInfo salesInfo = productSkuPurchaseInfoDao.getInfo(product.getSkuCode());
                String salesCode = salesInfo.getPurchaseCode() == null ? "" : salesInfo.getPurchaseCode();
                map.put("distribution", salesCode);
                map.put("skuName", product.getSkuName());
                try {
                    String str = new String(product.getProductSpec().getBytes("utf-8"));
                    String spec = URLEncoder.encode(str, "utf-8");
                    map.put("spec", spec);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.info("规格转义失败", e);
                }
                String type;
                // 0商品 1赠品 2实物返
                if (product.getProductType().equals(Global.PRODUCT_TYPE_0)) {
                    type = "商品";
                } else if (product.getProductType().equals(Global.PRODUCT_TYPE_1)) {
                    type = "赠品";
                } else {
                    type = "实物返";
                }
                map.put("type", type);
                map.put("goodsCount", product.getPurchaseWhole());
                map.put("goodsMin", product.getSingleCount());
                map.put("price", product.getProductAmount().toString());
                BigDecimal priceSum = product.getProductTotalAmount();
                map.put("priceSum", priceSum.toString());
                amountSum = amountSum.add(priceSum);
                productList.add(map);
            }
        }else {
            map = new HashMap<>();
            map.put("id", "");
            map.put("skuCode", "");
            map.put("distribution", "");
            map.put("skuName", "");
            map.put("spec", "");
            map.put("type", "");
            map.put("goodsCount", "");
            map.put("goodsMin", "");
            map.put("price", "");
            map.put("priceSum", "");
            productList.add(map);
        }
        dataMap.put("productList", productList);
        dataMap.put("amountSum", amountSum.toString());
        dataMap.put("boxSum", box);
        try {
            response.setContentType("*/*");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".pdf");
            OutputStream out = response.getOutputStream();
            out.write(pdfUtil.fillTemplate(dataMap));
            out.flush();
            out.close();
        } catch (Exception e) {
            LOGGER.error("导出订货单PDF数据失败！", e);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse purchaseDelete(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        purchaseApplyDao.delete(purchaseOrderId);
        return HttpResponse.success();
    }
}
