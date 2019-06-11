package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.Product;
import com.aiqin.bms.scmp.api.product.domain.ProductDistributor;
import com.aiqin.bms.scmp.api.product.domain.ProductPriceChange;
import com.aiqin.bms.scmp.api.product.domain.request.*;

import java.util.List;


/**
 * Created by 爱亲 on 2018/11/13.
 */
public interface ProductService {

    HttpResponse selectWeiShopSkuInfo(String distributorId);

    HttpResponse selectShopSku(String distributorId);

    HttpResponse selectWeiShopTopSkuInfo(String distributorId);

    HttpResponse selectShopTopSku(String distributorId);

    HttpResponse selectWarningStockSkuInfo(String distributorId);

    HttpResponse selectProductCategoryListInfo();

    HttpResponse selectProductListInfo(Product product);

    HttpResponse selectProductInfoBySkuCode(String skuCode, String distributorId);

    HttpResponse updateProductInfoByProductId(ProductRequest productRequest);

    HttpResponse uploadImage(String base64);

    HttpResponse updateChannelBySkuCode(ProductDistributor productDistributor);

    HttpResponse selectProductInfoByProductCodeList(List<String> productCodeList);

    HttpResponse selectProductInfoBySearch(Product product);

    HttpResponse updateProductInfoBySearch(List<ProductInfoRequest> productInfoRequestList);

    HttpResponse selectProductInfoByCategoryAndCode(Product product);

    HttpResponse selectCategoryThirdByCategoryId(String categoryId);

    HttpResponse selectProductInfoByIdOrCode(ProductDistributor productDistributor);

    /**
     * 商品搜索
     *
     * @param productSearchRequest
     * @return
     */
    HttpResponse selectProductPage(ProductSearchRequest productSearchRequest);

    HttpResponse countAllCategory(String distributorId);

    HttpResponse selectPriceByProductIdList(Product product);

    HttpResponse selectProductCycleByProductIdList(List<String> productIdList);

    HttpResponse selectProductInfoByProductIdList(ProductSkuRequest productSkuRequest);

    HttpResponse countAllTopSku(String distributorId);

    HttpResponse countAllDownSku(String distributorId);

    HttpResponse countSoldOutShopSku(String distributorId);

    HttpResponse countSoldOutWeiShopSku(String distributorId);

    /**
     * 修改商品价格
     *
     * @param request
     * @return
     */
    HttpResponse updateProductPrice(ProductPriceRequest request);

    /**
     * 获取变价记录
     *
     * @param query
     * @return
     */
    HttpResponse<PageResData<ProductPriceChange>> listProductPriceChange(ProductPriceChangeQuery query);

    /**
     * 获取滞销商品分页数据
     *
     * @param request
     * @return
     */
    PageResData<ProductDistributor> unsoldProductPage(UnsoldDistributorProductRequest request);

    HttpResponse selectLikeProductList(String skuCode, String distributorId);
}
