package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.Product;
import com.aiqin.bms.scmp.api.product.domain.ProductDistributor;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDistributorDao {

    Integer countWeiShopSku(String distributorId);

    Integer countShopSku(String distributorId);

    Integer countWeiShopTopSku(String distributorId);

    Integer countShopTopSku(String distributorId);

    Integer countWarningStockSku(String distributorId);

    List<ProductDistributor> selectWarningStockSku(String distributorId);

    ProductDistributor selectProductInfoBySkuCode(@Param(value = "skuCode") String skuCode, @Param(value = "distributorId") String distributorId);

    Integer updateProductInfoByProductId(ProductRequest productRequest);

    List<ProductDistributor> selectProductInfoByProductCodeList(@Param("productCodeList") List<String> productCodeList);

    List<ProductDistributor> selectProductInfoBySearch(Product product);

    Integer updateProductDisBySearch(@Param(value = "list") List<ProductInfoRequest> productInfoRequestList);

    List<ProductDistributor> selectProductPage(ProductSearchRequest productSearchRequest);

    Integer countProductPage(ProductSearchRequest productSearchRequest);

    List<ProductDistributor> selectProductDisByDisId(String distributorId);

    Integer updateChannelBySkuCode(ProductDistributor productDistributord);

    List<ProductDistributor> selectProductInfoByProductIdList(ProductSkuRequest productSkuRequest);

    Integer countProductInfoByIdOrCode(ProductDistributor productDistributor);

    Integer countAllTopSku(String distributorId);

    Integer countAllDownSku(String distributorId);

    Integer countSoldOutShopSku(String distributorId);

    Integer countSoldOutWeiShopSku(String distributorId);

    List<ProductDistributor> selectProductInfoByIdOrCode(ProductDistributor productDistributor);

    /**
     * 修改商品价格
     *
     * @param request
     * @return
     */
    Integer updateProductPrice(@Param("request") ProductPriceRequest request);

    List<ProductDistributor> getSelectByDistributorIdAndSkuCodeIn(@Param("distributorId") String distributorId, @Param("list") List<String> skuCodes);

    void batchUpdateStock(@Param("list") List<ProductDistributor> list);

    void batchInsertStock(@Param("list") List<ProductDistributor> list);

    List<ProductDistributor> selectProductByCategoryId(@Param(value = "categoryId") String categoryId, @Param(value = "distributorId") String distributorId);
}