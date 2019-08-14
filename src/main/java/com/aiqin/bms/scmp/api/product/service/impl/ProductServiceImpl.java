package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryStoreSkusReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryResponse;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.StoreSkuItemRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductService;
import com.aiqin.bms.scmp.api.util.UploadFileUtil;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 爱亲 on 2018/11/13.
 */

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private ProductDistributorDao productDistributorDao;
    @Resource
    private ProductCategoryDao productCategoryDao;
    @Resource
    private ProductDao productDao;
    @Resource
    private ProductCycleDao productCycleDao;
    @Resource
    private ProductBrandTypeDao productBrandTypeDao;
    @Resource
    private ProductBrandDistributionDao productBrandDistributionDao;
    @Resource
    private ProductCategoryDistributionDao productCategoryDistributionDao;
    @Resource
    private ProductPriceChangeDao productPriceChangeDao;
    @Resource
    private UploadFileUtil uploadFileUtil;

    @Value("${order.info.url}")
    private String orderInfoUrl;



    @Override
    public HttpResponse selectWeiShopSkuInfo(String distributorId) {
        try {
            LOGGER.info("查询微商城sku数量");
            return HttpResponse.success(productDistributorDao.countWeiShopSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询微商城sku失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_WEI_SHOP_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse selectShopSku(String distributorId) {
        try {
            LOGGER.info("查询门店sku数量");
            return HttpResponse.success(productDistributorDao.countShopSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询门店sku失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_SHOP_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse selectShopTopSku(String distributorId) {
        try {
            LOGGER.info("查询微商城已上架sku信息");
            return HttpResponse.success(productDistributorDao.countShopTopSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询微商城已上架sku失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_SHOP_TOP_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse selectWeiShopTopSkuInfo(String distributorId) {
        try {
            LOGGER.info("查询门店已上架sku信息");
            return HttpResponse.success(productDistributorDao.countWeiShopTopSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询门店已上架sku失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_WEI_SHOP_TOP_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse selectWarningStockSkuInfo(String distributorId) {
        try {
            LOGGER.info("查询库存预警sku信息");
            List<ProductDistributor> list = productDistributorDao.selectWarningStockSku(distributorId);
            int total = productDistributorDao.countWarningStockSku(distributorId);
            return HttpResponse.success(new PageResData(total, list));
        } catch (Exception e) {
            LOGGER.error("查询库存预警sku失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_WARNING_STOCK_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductCategoryListInfo() {
        try {
            LOGGER.info("查询商品分类信息");
            List<ProductCategoryResponse> firstList = productCategoryDao.selectFirstCategoryList();
            List<ProductCategoryResponse> secondList = productCategoryDao.selecSecondCategoryList();
            List<ProductCategoryResponse> thirdList = productCategoryDao.selecThirdCategoryList();
            List<ProductCategoryResponse> fouthList = productCategoryDao.selecFouthCategoryList();
            Map<String, List<ProductCategoryResponse>> map1 = secondList.stream().collect(Collectors.groupingBy(ProductCategoryResponse::getParentId));
            Map<String, List<ProductCategoryResponse>> map2 = thirdList.stream().collect(Collectors.groupingBy(ProductCategoryResponse::getParentId));
            Map<String, List<ProductCategoryResponse>> map3 = fouthList.stream().collect(Collectors.groupingBy(ProductCategoryResponse::getParentId));
            if (CollectionUtils.isNotEmpty(firstList)) {
                for (ProductCategoryResponse productCategory1 : firstList) {
                    productCategory1.setChildrenList(map1.get(productCategory1.getCategoryId()));
                    if (CollectionUtils.isNotEmpty(secondList)) {
                        for (ProductCategoryResponse productCategory2 : secondList) {
                            productCategory2.setChildrenList(map2.get(productCategory2.getCategoryId()));
                            if (CollectionUtils.isNotEmpty(thirdList)) {
                                for (ProductCategoryResponse productCategory3 : thirdList) {
                                    productCategory3.setChildrenList(map3.get(productCategory3.getCategoryId()));
                                }
                            }
                        }
                    }
                }
            }
            return HttpResponse.success(firstList);
        } catch (Exception e) {
            LOGGER.error("查询商品分类信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_CATEGORY_INFO_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductListInfo(Product product) {
        try {
            LOGGER.info("查询商品信息");
            List<Product> list = productDao.selectProductInfoBySearch(product);
            int total = productDao.countProductInfoBySearch();
            return HttpResponse.success(new PageResData(total, list));
        } catch (Exception e) {
            LOGGER.error("查询库存预警sku失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_WARNING_STOCK_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductInfoBySkuCode(String skuCode, String distributorId) {
        try {
            LOGGER.info("根据商品id查询商品信息");
            return HttpResponse.success(productDistributorDao.selectProductInfoBySkuCode(skuCode, distributorId));
        } catch (Exception e) {
            LOGGER.error("根据商品id查询商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_BY_PRODUCTID_ERROR);
        }
    }

    @Override
    public HttpResponse updateProductInfoByProductId(ProductRequest productRequest) {
        try {
            LOGGER.info("根据商品id修改商品信息");
            Integer i = productDistributorDao.updateProductInfoByProductId(productRequest);
            Integer j = productDao.updateProductByProductId(productRequest);
            if (i <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS || j <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS) {
                LOGGER.error("修改商品信息失败");
                return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_INFO_ERROR);
            }
            LOGGER.info("修改成功，当前商品id为:" + productRequest.getProductId() + "，商品名称为:" + productRequest.getProductName());
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("根据商品id修改商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_INFO_ERROR);
        }
    }

    @Override
    public HttpResponse uploadImage(String base64) {
        LOGGER.info("上传图片");
        if (!base64.startsWith("data:image") && !base64.startsWith("data:img")) {
            LOGGER.error("base64内容不是图片类型", base64.substring(0, 30));
            return HttpResponse.failure(ResultCode.NOT_HAVE_PARAM);
        }
        try {
            String url = uploadFileUtil.uploadImage(base64);
            LOGGER.info("上传图片成功,url={}", url);
            return HttpResponse.success(url);
        } catch (Exception e) {
            LOGGER.error("上传图片失败", e);
            return HttpResponse.failure(ResultCode.UPLODE_IMAGE_ERROR);
        }
    }

    @Override
    public HttpResponse updateChannelBySkuCode(ProductDistributor productDistributor) {
        try {
            LOGGER.info("将商品从微商城删除");
            Integer i = productDistributorDao.updateChannelBySkuCode(productDistributor);
            if (i <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS) {
                LOGGER.error("将商品从微商城删除失败");
                return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_INFO_ERROR);
            }
            ProductDistributor productDistributorInfo = productDistributorDao.selectProductInfoBySkuCode(productDistributor.getSkuCode(), productDistributor.getDistributorId());
            if (productDistributorInfo != null) {
                if (productDistributorInfo.getBrandId() != null) {
                    ProductBrandDistribution productBrandDistributionInfo =
                            productBrandDistributionDao.selectByBrandIdAndDistributorId(productDistributorInfo.getBrandId(), productDistributorInfo.getDistributorId());
                    if (productBrandDistributionInfo != null) {
                        i = productBrandDistributionDao.deleteBrandInfoByBrandIdAndDisId(productDistributorInfo.getDistributorId(), productDistributorInfo.getBrandId());
                        if (i <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS) {
                            LOGGER.error("将商品从微商城删除失败");
                            return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_INFO_ERROR);
                        }
                    }
                }
                if (productDistributorInfo.getCategoryId() != null) {
                    ProductCategoryDistribution productCategoryDistributionInfo = productCategoryDistributionDao.selectCategoryDisInfoByCategoryId(productDistributorInfo.getCategoryId(), productDistributorInfo.getDistributorId());
                    if (productCategoryDistributionInfo != null) {
                        i = productCategoryDistributionDao.deleteCategoryInfoByCategoryIdAndDisId(productDistributorInfo.getDistributorId(), productDistributorInfo.getBrandId());
                        if (i <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS) {
                            LOGGER.error("将商品从微商城删除失败");
                            return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_INFO_ERROR);
                        }
                    }
                }
            }
            LOGGER.info("将商品从微商城删除成功，当前商品skuCode为:" + productDistributor.getSkuCode());
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("根据商品id将商品从微商城下架失败，系统异常", e);
            return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_ON_SALE_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductInfoByProductCodeList(List<String> productCodeList) {
        try {
            LOGGER.info("根据商品codeList查询商品信息");
            return HttpResponse.success(productDistributorDao.selectProductInfoByProductCodeList(productCodeList));
        } catch (Exception e) {
            LOGGER.error("根据商品codeList查询商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_BY_PRODUCTCODELIST_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductInfoBySearch(Product product) {
        try {
            LOGGER.info("根据搜索条件查询商品信息");
            return HttpResponse.success(productDistributorDao.selectProductInfoBySearch(product));
        } catch (Exception e) {
            LOGGER.error("根据搜索条件查询商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_BY_SEARCHTEXT_ERROR);
        }
    }

    @Override
    public HttpResponse updateProductInfoBySearch(List<ProductInfoRequest> productInfoRequestList) {
        try {
            LOGGER.info("根据搜索条件修改商品信息");
            int i = 0;
            int j = 0;
            int m = 0;
            int c = 0;
            int u = 0;
            i = productDistributorDao.updateProductDisBySearch(productInfoRequestList);
            for (ProductInfoRequest productInfoRequest : productInfoRequestList) {
                if (productInfoRequest.getBrandId() != null) {
                    ProductBrandType productBrandType = productBrandTypeDao.selectBrandInfoBy(productInfoRequest.getBrandId());
                    ProductBrandDistribution productBrandDistribution = new ProductBrandDistribution();
                    productBrandDistribution.setBrandSort(Global.BRAND_SORT_DEFAULT);
                    productBrandDistribution.setDistributorId(productInfoRequest.getDistributorId());
                    if (productBrandType != null) {
                        productBrandDistribution.setBrandId(productBrandType.getBrandId());
                        productBrandDistribution.setBrandName(productBrandType.getBrandName());
                        productBrandDistribution.setBrandInitials(productBrandType.getBrandInitials());
                    }
                    List<ProductDistributor> productDistributorList = productDistributorDao.selectProductDisByDisId(productInfoRequest.getDistributorId());
                    if (CollectionUtils.isNotEmpty(productDistributorList)) {
                        productBrandDistribution.setDistributorName(productDistributorList.get(1).getDistributorName());
                    }
                    ProductBrandDistribution productBrandDistributionInfo =
                            productBrandDistributionDao.selectByBrandIdAndDistributorId(productBrandDistribution.getBrandId(), productBrandDistribution.getDistributorId());
                    if (productInfoRequest.getOnSale().equals(Global.ON_SALE_TOP)) {
                        productBrandDistribution.setIsShow(Global.BRAND_IS_SHOW_YES);
                        if (productBrandDistributionInfo == null) {
                            j = productBrandDistributionDao.insertBrandInfoWhenOnSale(productBrandDistribution);
                        } else {
                            m = productBrandDistributionDao.updateBrandInfoWhenOnSale(productBrandDistribution);
                        }
                    } else if (productInfoRequest.getOnSale().equals(Global.ON_SALE_NOT)) {
                        if (productBrandDistributionInfo == null) {
                            productBrandDistribution.setIsShow(Global.BRAND_IS_SHOW_NOT);
                        } else {
                            productBrandDistribution.setIsShow(Global.BRAND_IS_SHOW_YES);
                        }
                        m = productBrandDistributionDao.updateBrandInfoWhenOnSale(productBrandDistribution);
                    }
                }
                if (productInfoRequest.getCategoryId() != null) {
                    ProductCategoryResponse productCategory = productCategoryDao.selectCategoryLevelByCategoryId(productInfoRequest.getCategoryId());
                    ProductCategoryDistribution productCategoryDistribution = new ProductCategoryDistribution();
                    productCategoryDistribution.setCategoryId(productInfoRequest.getCategoryId());
                    productCategoryDistribution.setDistributorId(productInfoRequest.getDistributorId());
                    if (productCategory != null) {
                        productCategoryDistribution.setCategoryName(productCategory.getCategoryName());
                    }
                    List<ProductDistributor> productDistributorList = productDistributorDao.selectProductDisByDisId(productInfoRequest.getDistributorId());
                    if (CollectionUtils.isNotEmpty(productDistributorList)) {
                        productCategoryDistribution.setDistributorName(productDistributorList.get(1).getDistributorName());
                    }
                    ProductCategoryDistribution productCategoryDistributionInfo = productCategoryDistributionDao.selectCategoryDisInfoByCategoryId(productCategoryDistribution.getCategoryId(), productCategoryDistribution.getDistributorId());
                    if (productInfoRequest.getOnSale().equals(Global.ON_SALE_TOP)) {
                        productCategoryDistribution.setIsShow(Global.CATEGORY_IS_SHOW_YES);
                        if (productCategoryDistributionInfo == null) {
                            c = productCategoryDistributionDao.insertCategoryInfoWhenOnSale(productCategoryDistribution);
                        } else {
                            u = productCategoryDistributionDao.updateCategoryInfoWhenOnSale(productCategoryDistribution);
                        }
                    } else if (productInfoRequest.getOnSale().equals(Global.ON_SALE_NOT)) {
                        if (productCategoryDistributionInfo == null) {
                            productCategoryDistribution.setIsShow(Global.CATEGORY_IS_SHOW_NOT);
                        } else {
                            productCategoryDistribution.setIsShow(Global.CATEGORY_IS_SHOW_YES);
                        }
                        u = productCategoryDistributionDao.updateCategoryInfoWhenOnSale(productCategoryDistribution);
                    }
                }
            }
            if (i <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS && j <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS
                    && m <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS && c <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS
                    && u <= Global.CHECK_INSERT_UPDATE_DELETE_SUCCESS) {
                LOGGER.error("修改商品信息失败");
                return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_INFO_ERROR);
            }
            LOGGER.info("修改成功");
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("根据搜索条件修改商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_BY_SEARCHTEXT_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductInfoByCategoryAndCode(Product product) {
        try {
            LOGGER.info("根据搜索条件查询商品信息");
            List<Product> list = productDao.selectProductInfoByCategoryAndCode(product);
            int total = productDao.countProductInfoByCategoryAndCode(product);
            return HttpResponse.success(new PageResData(total, list));
        } catch (Exception e) {
            LOGGER.error("根据搜索条件查询商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_BY_SEARCHTEXT_ERROR);
        }
    }

    @Override
    public HttpResponse selectCategoryThirdByCategoryId(String categoryId) {
        try {
            LOGGER.info("根据搜索条件查询商品最末级分类信息");
            List<ProductCategoryResponse> firstList = new ArrayList<>();
            List<ProductCategoryResponse> secondList = new ArrayList<>();
            List<ProductCategoryResponse> thirdList = new ArrayList<>();
            List<ProductCategoryResponse> fouthList = new ArrayList<>();
            if (StringUtils.isNotBlank(categoryId)) {
                ProductCategoryResponse productCategoryResponse = productCategoryDao.selectCategoryLevelByCategoryId(categoryId);
                if (productCategoryResponse != null && productCategoryResponse.getCategoryLevel() != null) {
                    Integer categoryLevel = productCategoryResponse.getCategoryLevel();
                    if (categoryLevel.equals(Global.PRODUCT_CATEGORY_FIRST_LEVEL)) {
                        secondList = productCategoryDao.selectSencondCategoryListByCategoryId(categoryId);
                        thirdList = productCategoryDao.selecThirdCategoryList();
                        fouthList = productCategoryDao.selecFouthCategoryList();
                        Map<String, List<ProductCategoryResponse>> map2 = thirdList.stream().collect(Collectors.groupingBy(ProductCategoryResponse::getParentId));
                        Map<String, List<ProductCategoryResponse>> map3 = fouthList.stream().collect(Collectors.groupingBy(ProductCategoryResponse::getParentId));
                        if (CollectionUtils.isNotEmpty(secondList)) {
                            for (ProductCategoryResponse productCategory2 : secondList) {
                                productCategory2.setChildrenList(map2.get(productCategory2.getCategoryId()));
                                for (ProductCategoryResponse productCategory3 : thirdList) {
                                    productCategory3.setChildrenList(map3.get(productCategory3.getCategoryId()));
                                }
                            }
                            productCategoryResponse.setChildrenList(secondList);
                            firstList.add(productCategoryResponse);
                        }
                    } else if (categoryLevel.equals(Global.PRODUCT_CATEGORY_SECOND_LEVEL)) {
                        thirdList = productCategoryDao.selectThirdCategoryListByCategoryId(categoryId);
                        fouthList = productCategoryDao.selecFouthCategoryList();
                        Map<String, List<ProductCategoryResponse>> map3 = fouthList.stream().collect(Collectors.groupingBy(ProductCategoryResponse::getParentId));
                        if (CollectionUtils.isNotEmpty(thirdList)) {
                            for (ProductCategoryResponse productCategory3 : thirdList) {
                                productCategory3.setChildrenList(map3.get(productCategory3.getCategoryId()));
                            }
                            productCategoryResponse.setChildrenList(thirdList);
                            firstList.add(productCategoryResponse);
                        }
                    } else if (categoryLevel.equals(Global.PRODUCT_CATEGORY_THIRD_LEVEL)) {
                        fouthList = productCategoryDao.selectFouthCategoryListByCategoryId(categoryId);
                        productCategoryResponse.setChildrenList(fouthList);
                        firstList.add(productCategoryResponse);
                    }
                }
            } else {
                firstList = productCategoryDao.selectFirstCategoryList();
            }
            return HttpResponse.success(firstList);
        } catch (Exception e) {
            LOGGER.error("根据搜索条件查询商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_BY_SEARCHTEXT_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductInfoByIdOrCode(ProductDistributor productDistributor) {
        try {
            LOGGER.info("根据code或者名称查询商品信息");
            List<ProductDistributor> productDistributorList = productDistributorDao.selectProductInfoByIdOrCode(productDistributor);
            int total = productDistributorDao.countProductInfoByIdOrCode(productDistributor);
            return HttpResponse.success(new PageResData<>(total, productDistributorList));
        } catch (Exception e) {
            LOGGER.error("根据code或者名称查询商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_BY_SEARCHTEXT_ERROR);
        }
    }

    @Override
    public HttpResponse countAllCategory(String distributorId) {
        try {
            LOGGER.info("查询品类数量");
            List<String> categoryIdList = new ArrayList<>();
            List<ProductDistributor> productDistributorList = productDistributorDao.selectProductDisByDisId(distributorId);
            if (CollectionUtils.isNotEmpty(productDistributorList)) {
                for (ProductDistributor productDistributor : productDistributorList) {
                    if (productDistributor.getCategoryId() != null) {
                        String categoryId = productDistributor.getCategoryId();
                        categoryIdList.add(categoryId);
                    }
                }
            }
            return HttpResponse.success(productCategoryDao.countAllCategory(categoryIdList));
        } catch (Exception e) {
            LOGGER.error("查询品类数量失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_CATEGORY_COUNT_ERROR);
        }
    }

    @Override
    public HttpResponse selectPriceByProductIdList(Product product) {
        try {
            LOGGER.info("查询商品价格跟零售价");
            return HttpResponse.success(productDao.selectPriceByProductIdList(product));
        } catch (Exception e) {
            LOGGER.error("查询商品价格跟零售价失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_PRICE_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductCycleByProductIdList(List<String> productIdList) {
        try {
            LOGGER.info("查询商品消耗周期");
            return HttpResponse.success(productCycleDao.selectProductCycleByProductIdList(productIdList));
        } catch (Exception e) {
            LOGGER.error("查询商品消耗周期失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_CYCLE_ERROR);
        }
    }

    @Override
    public HttpResponse selectProductInfoByProductIdList(ProductSkuRequest productSkuRequest) {
        try {
            LOGGER.info("根据商品id集合查询商品信息");
            return HttpResponse.success(productDistributorDao.selectProductInfoByProductIdList(productSkuRequest));
        } catch (Exception e) {
            LOGGER.error("根据商品id集合查询商品信息失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_INFO_BY_PIDLIST_ERROR);
        }
    }

    @Override
    public HttpResponse countAllTopSku(String distributorId) {
        try {
            LOGGER.info("查询所有上架sku数量");
            return HttpResponse.success(productDistributorDao.countAllTopSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询所有上架sku数量失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_ALL_TOP_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse countAllDownSku(String distributorId) {
        try {
            LOGGER.info("查询所有下架sku数量");
            return HttpResponse.success(productDistributorDao.countAllDownSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询所有下架sku数量失败，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_ALL_DOWN_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse countSoldOutShopSku(String distributorId) {
        try {
            LOGGER.info("查询门店售罄sku数量");
            return HttpResponse.success(productDistributorDao.countSoldOutShopSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询门店售罄sku数量失败，系统异常", e);
            return HttpResponse.failure(ResultCode.COUNT_SOLD_OUT_SHOP_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse countSoldOutWeiShopSku(String distributorId) {
        try {
            LOGGER.info("查询微商城售罄sku数量");
            return HttpResponse.success(productDistributorDao.countSoldOutWeiShopSku(distributorId));
        } catch (Exception e) {
            LOGGER.error("查询微商城售罄sku数量失败，系统异常", e);
            return HttpResponse.failure(ResultCode.COUNT_SOLD_OUT_WEI_SHOP_SKU_ERROR);
        }
    }

    @Override
    public HttpResponse updateProductPrice(ProductPriceRequest request) {
        ProductDistributor info = productDistributorDao.selectProductInfoBySkuCode(request.getSkuCode(), request.getDistributorId());
        ProductPriceChange change = new ProductPriceChange();
        change.setBeforeChangePrice(info.getPrice());
        change.setBeforeChangeMemberPrice(info.getMemberPrice());
        Integer result = productDistributorDao.updateProductPrice(request);
        if (result != null) {
            BeanUtils.copyProperties(info, change);
            change.setAfterChangePrice(request.getPrice());
            change.setAfterChangeMemberPrice(request.getMemberPrice());
            change.setPurchasePrice(request.getPurchasePrice());
            change.setColor(info.getColorName());
            change.setModel(info.getModelNumber());
            change.setSpec(info.getSpec());
            change.setCreateTime(request.getCreateTime());
            change.setCreateBy(request.getCreateBy());
            change.setCreateByName(request.getCreateByName());
            change.setUpdateByName(request.getUpdateByName());
            Integer changeResult = productPriceChangeDao.insert(change);
            if (changeResult <= 0) {
                log.error("插入修改记录失败");
            }
            return HttpResponse.success(changeResult);
        }
        return HttpResponse.failure(ResultCode.UPDATE_PRODUCT_INFO_ERROR);
    }

    @Override
    public HttpResponse<PageResData<ProductPriceChange>> listProductPriceChange(ProductPriceChangeQuery query) {
        try {
            List<ProductPriceChange> list = productPriceChangeDao.listProductPriceChange(query);
            Integer count = productPriceChangeDao.countPriceChange(query);
            return HttpResponse.success(new PageResData(count, list));
        } catch (Exception e) {
            LOGGER.error("查询变价记录，系统异常", e);
            return HttpResponse.failure(ResultCode.SELECT_PRODUCT_BY_SEARCHTEXT_ERROR);
        }
    }

    @Override
    public PageResData<ProductDistributor> unsoldProductPage(UnsoldDistributorProductRequest request) {
        List<String> skuCodes = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(skuCodes)) {
            request.setSoldSkuCodes(skuCodes);
        }
        ProductSearchRequest productSearchRequest = new ProductSearchRequest();
        productSearchRequest.setDistributorId(request.getDistributorId());
        productSearchRequest.setSkuCodesNotIn(skuCodes);
        productSearchRequest.setPageNo(request.getPageNo());
        productSearchRequest.setPageSize(request.getPageSize());
        List<ProductDistributor> list = productDistributorDao.selectProductPage(productSearchRequest);
        int total = productDistributorDao.countProductPage(productSearchRequest);
        return new PageResData(total, list);
    }
}