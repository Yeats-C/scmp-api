package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.ProductBrandDistribution;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryBrandDistributionRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductBrandDistributionDao {

    Integer insertBrandInfoWhenOnSale(ProductBrandDistribution productBrandDistribution);

    Integer updateBrandInfoWhenOnSale(ProductBrandDistribution productBrandDistribution);

    ProductBrandDistribution selectByBrandIdAndDistributorId(@Param("brandId") String brandId, @Param("distributorId") String distributorId);

    List<ProductBrandDistribution> selectBrandInfoByBrandName(ProductBrandDistribution productBrandDistribution);

    Integer countBrandInfoByBrandName(ProductBrandDistribution productBrandDistribution);

    Integer updateBrandDisByBrandIdAndDisId(ProductBrandDistribution productBrandDistribution);

    Integer deleteBrandInfoByBrandIdAndDisId(@Param("distributorId") String distributorId, @Param("brandId") String brandId);

    List<ProductBrandDistribution> selectBrandDisByInitAsc(String distributorId);

    List<ProductBrandDistribution> queryByParamOrderByBrandInitialsAsc(@Param("req") QueryBrandDistributionRequest request);

    Integer updateStatusByParam(ProductBrandDistribution productBrandDistribution);

    Boolean insertList(@Param("list") List<ProductBrandDistribution> productBrands);
}