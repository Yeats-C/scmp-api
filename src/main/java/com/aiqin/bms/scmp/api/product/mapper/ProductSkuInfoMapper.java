package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.SkuStatusRespVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductSkuInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuInfo record);

    int insertSelective(ProductSkuInfo record);

    ProductSkuInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuInfo record);

    int updateByPrimaryKey(ProductSkuInfo record);

    int insertBatch(List<ProductSkuInfo> records);

    int updateStatus(List<SkuStatusRespVo> respVos);
    /**
     * 通过编码查sku
     * @author NullPointException
     * @date 2019/7/18
     * @param skuList
     * @param companyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo>
     */
    @MapKey("skuCode")
    Map<String,ProductSkuInfo> selectBySkuCodes(@Param("list") Set<String> skuList, @Param("companyCode") String companyCode);
}