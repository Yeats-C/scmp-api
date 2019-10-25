package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacityDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitCapacityRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductSkuSupplyUnitCapacityDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSupplyUnitCapacityDraft record);

    int insertSelective(ProductSkuSupplyUnitCapacityDraft record);

    ProductSkuSupplyUnitCapacityDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSupplyUnitCapacityDraft record);

    int updateByPrimaryKey(ProductSkuSupplyUnitCapacityDraft record);

    int saveBatch(List<ProductSkuSupplyUnitCapacityDraft> records);

    List<ProductSkuSupplyUnitCapacityRespVo> getDraftList(Map map);

    List<ProductSkuSupplyUnitCapacityDraft> getDrafts(List<String> skuCodes);

    Integer delete(List<String> skuCodes);

    List<ProductSkuSupplyUnitCapacityDraft> getDraftsBySupplyUnitDrafts(List<ProductSkuSupplyUnitDraft> skuSupplyUnitDrafts);

    int deleteByIds(List<Long> ids);

    Integer deleteBySkuCodeAndSupplierCodes(@Param("skuCode") String skuCode, @Param("deleteCodes") List<String> deleteSupplyUnitCodes);

    List<ProductSkuSupplyUnitCapacityRespVo> getCapacityInfoBySupplyUnitCodeAndProductSkuCode(@Param("supplyUnitCode")String supplyUnitCode, @Param("productSkuCode")String productSkuCode);

    Integer deleteBySkuCodeAndSupplierCode(@Param("skuCode") String productSkuCode, @Param("supplyUnitCode") String supplyUnitCode);
}