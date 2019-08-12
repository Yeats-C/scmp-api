package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ProductSkuPromoteSales;
import com.aiqin.bms.scmp.api.supplier.domain.request.promotesales.QueryPromoteSalesReqVo;

import java.util.List;

public interface ProductSkuPromoteSalesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPromoteSales record);

    int insertSelective(ProductSkuPromoteSales record);

    ProductSkuPromoteSales selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPromoteSales record);

    int updateByPrimaryKey(ProductSkuPromoteSales record);

    int deleteAll();

    List<ProductSkuPromoteSales> getList(QueryPromoteSalesReqVo reqVo);

    int insertBatch(List<ProductSkuPromoteSales> records);

    List<ProductSkuPromoteSales> getAll();

    int deleteBySkuCodes(List<String> delSkus);
}