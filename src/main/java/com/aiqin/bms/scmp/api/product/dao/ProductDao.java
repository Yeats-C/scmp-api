package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.Product;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductDraft;
import com.aiqin.bms.scmp.api.product.domain.request.ProductRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    List<Product> selectProductInfoBySearch(Product product);

    Integer countProductInfoBySearch();

    Integer updateProductByProductId(ProductRequest productRequest);

    List<Product> selectProductInfoByCategoryAndCode(Product product);

    Integer countProductInfoByCategoryAndCode(Product product);

    List<Product> selectPriceByProductIdList(Product product);

    List<ApplyProductDraft> getProductDrafts(List<String> productCodes);

    Product selectByProductCode(@Param("productCode") String productCode);
}