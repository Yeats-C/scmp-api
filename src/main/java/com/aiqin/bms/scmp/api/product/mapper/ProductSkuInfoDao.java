package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.SkuDataListReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuResponse;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.ErrorVariableResponse;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.SkuDataListResponse;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ProductSkuInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuInfo record);

    int insertSelective(ProductSkuInfo record);

    ProductSkuInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuInfo record);

    int updateByPrimaryKey(ProductSkuInfo record);

    List<ProductSkuResponse> selectSkuInfoListCanUseBySkuCodeList(@Param("skuCodeList") List<String> skuCodeList);

    List<SkuDataListResponse> getDataList(SkuDataListReqVo skuDataListReqVo);

    List<ErrorVariableResponse> getSkuCode(@Param("list") Collection<String> list, @Param("priceTypeCode") String priceTypeCode);

    int updateList();

    ProductSkuResponse selectSkuInfoBySkuCode(@Param(value = "skuCode") String skuCode);


}