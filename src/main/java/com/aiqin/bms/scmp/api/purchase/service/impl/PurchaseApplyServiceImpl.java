package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockBatchSkuRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyProductDao;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyProductInfoResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectImportResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
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
import java.util.ArrayList;
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
    private PurchaseGroupService purchaseGroupService;
    @Resource
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private StockDao stockDao;

    @Override
    public HttpResponse<List<PurchaseApplyResponse>> applyList(PurchaseApplyRequest purchaseApplyRequest){
        PageResData pageResData = new PageResData();
        List<PurchaseApplyResponse> purchases = purchaseApplyDao.applyList(purchaseApplyRequest);
        if(CollectionUtils.isNotEmptyCollection(purchases)){
            for (PurchaseApplyResponse apply:purchases){
                // 计算sku数量 / 单品数量/ 采购含税金额 / 实物返金额
                PurchaseApplyProductInfoResponse info = this.applyProductCount(apply.getPurchaseApplyId());
                // 计算sku数量
                Integer skuCount = purchaseApplyProductDao.skuCount(apply.getPurchaseApplyId(), null);
                apply.setSkuCount(skuCount);
                apply.setSingleCount(info.getSingleSum());
                apply.setProductTotalAmount(info.getTaxSum());
                apply.setReturnAmount(info.getMatterTaxSum());
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
            for (PurchaseApplyDetailResponse product : detail) {
                // 获取最高采购价(价格管理中供应商的含税价格)
                if (StringUtils.isNotBlank(product.getSkuCode()) && StringUtils.isNotBlank(product.getSupplierCode())) {
                    Long priceTax = productSkuPriceInfoMapper.selectPriceTax(product.getSkuCode(), product.getSupplierCode());
                    product.setPurchaseMax(priceTax == null ? 0 : priceTax.intValue());
                }
                // 报表取数据(预测采购件数， 预测到货时间， 近90天销量 )
                // TODO
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
            // TODO
            //purchases.setAReplenish();
        }
        // 查询畅销商品建议补货
        if(purchases.getProductReplenishType() != null && purchases.getProductReplenishType() == 0){

        }
        // 查询14大A品缺货
        if(purchases.getAShortageType() != null && purchases.getAShortageType() == 0){

        }
        // 查询畅销商品缺货
        if(purchases.getProductShortageType() != null && purchases.getProductShortageType() == 0){

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
        String purchaseApplyId = null;
        if(StringUtils.isNotBlank(applyProductRequest.getPurchaseApplyId())){
            purchaseApplyId = applyProductRequest.getPurchaseApplyId();
            purchaseApplyProductDao.delete(purchaseApplyId);
        }else {
            // 生成采购申请单id
            purchaseApplyId = IdUtil.purchaseId();
        }
        // 保存采购申请选中商品
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_APPLY_CODE);
        for(PurchaseApplyProduct product:applyProducts){
            product.setApplyProductId(IdUtil.purchaseId());
            product.setPurchaseApplyId(purchaseApplyId);
            product.setPurchaseApplyCode("CS" + String.valueOf(encodingRule.getNumberingValue()));
        }
        Integer productCount = purchaseApplyProductDao.insertAll(applyProducts);
        if(productCount > 0){
            // 生成采购申请单
            PurchaseApply purchaseApply = new PurchaseApply();
            purchaseApply.setPurchaseApplyId(purchaseApplyId);
            // 获取采购申请单编码
            purchaseApply.setPurchaseApplyCode("CS" + String.valueOf(encodingRule.getNumberingValue()));
            purchaseApply.setApplyType(Global.PURCHASE_APPLY_TYPE_0);
            purchaseApply.setApplyStatus(Global.PURCHASE_APPLY_STATUS_0);
            purchaseApply.setPurchaseGroupCode(applyProducts.get(0).getPurchaseGroupCode());
            purchaseApply.setPurchaseGroupName(applyProducts.get(0).getPurchaseGroupName());
            purchaseApply.setCreateById(applyProducts.get(0).getCreateById());
            purchaseApply.setCreateByName(applyProducts.get(0).getCreateByName());
            Integer count = purchaseApplyDao.insert(purchaseApply);
            if(count > 0){
                encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
            }
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse searchApplyProduct(String applyProductId){
        if(StringUtils.isBlank(applyProductId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseApplyProduct product = new PurchaseApplyProduct();
        product.setApplyProductId(applyProductId);
        PurchaseApplyProduct purchaseApplyProduct = purchaseApplyProductDao.applyProduct(product);
        if(purchaseApplyProduct == null){
            LOGGER.info("查询采购申请商品的信息失败...{}:" +applyProductId);
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
        return HttpResponse.success(purchaseApplyProduct);
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
        PurchaseApplyProductInfoResponse info = this.applyProductCount(purchaseApplyId);
        return HttpResponse.success(info);
    }

    private PurchaseApplyProductInfoResponse applyProductCount(String purchaseApplyId){
        // 计算商品（实物返，赠品）采购件数 、 单品总数 、含税总金额
        List<PurchaseApplyDetailResponse> products = purchaseApplyProductDao.productList(purchaseApplyId);
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
        List<PurchaseApplyDetailResponse> products = purchaseApplyProductDao.productList(purchaseApplyId);
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
            List<PurchaseApplyProduct> list = new ArrayList<>();
            Integer errorCount = 0;
            if (result != null) {
                String validResult = FileReaderUtil.validStoreValue(result, importRejectApplyHeaders);
                if (StringUtils.isNotBlank(validResult)) {
                    return HttpResponse.failure(MessageId.create(Project.SCMP_API, 88888, validResult));
                }
                String[] record;
                PurchaseApplyProduct applyProduct;
                for (int i = 1; i <= result.length - 1; i++) {
                    record = result[i];
                    applyProduct = stockDao.purchaseBySkuStock(purchaseGroupCode,record[0], record[2], record[3]);
                    if(applyProduct != null){
//                        response.setProductCount(record[7]);
//                        response.setProductAmount(record[8]);
//                        response.setProductTotalAmount(String.valueOf(Integer.valueOf(record[8])*Integer.valueOf(record[7])));
//                        if(queryStockBatchSkuRespVo.getAvailableNum()<Integer.valueOf(record[7])){
//                            response.setErrorReason("可用库存数量小于销售数量");
//                            errorCount++;
                        //}
                    }else{
//                        applyProduct.setSkuCode(record[0]);
//                        applyProduct.setSkuName(record[1]);
//                        response.setSupplierCode(record[2]);
//                        response.setTransportCenterCode(record[3]);
//                        response.setWarehouseCode(record[4]);
//                        response.setBatchCode(record[5]);
//                        response.setGoodsGifts(Integer.valueOf(record[6]));
//                        response.setProductCount(record[7]);
//                        response.setProductAmount(record[8]);
//                        response.setErrorReason("未查询到对应的商品");
//                        errorCount++;
                    }
                    list.add(applyProduct);
                }
            }
            return HttpResponse.success(new PageResData(errorCount,list));
        } catch (Exception e) {
            LOGGER.error("采购申请单导入异常:{}", e.getMessage());
            return HttpResponse.failure(ResultCode.IMPORT_PURCHASE_APPLY_ERROR);
        }
    }
}
