package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.ProductCategoryDistribution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDistributionDao {

    Integer insertCategoryInfoWhenOnSale(ProductCategoryDistribution productCategoryDistribution);

    Integer updateCategoryInfoWhenOnSale(ProductCategoryDistribution productCategoryDistribution);

    ProductCategoryDistribution selectCategoryDisInfoByCategoryId(@Param("categoryId") String categoryId, @Param("distributorId") String distributorId);

    List<ProductCategoryDistribution> selectCategoryDisInfoByDisId(String distributionId);

    Integer deleteCategoryInfoByCategoryIdAndDisId(@Param("categoryId") String categoryId, @Param("distributorId") String distributorId);

    Boolean insertList(@Param("list") List<ProductCategoryDistribution> productCategorys);
}