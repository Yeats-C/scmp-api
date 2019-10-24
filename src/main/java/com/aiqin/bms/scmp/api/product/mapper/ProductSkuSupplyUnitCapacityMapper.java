package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacity;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacityDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitCapacityRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuSupplyUnitCapacityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSupplyUnitCapacity record);

    int insertSelective(ProductSkuSupplyUnitCapacity record);

    ProductSkuSupplyUnitCapacity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSupplyUnitCapacity record);

    int updateByPrimaryKey(ProductSkuSupplyUnitCapacity record);

    int deleteBySkuCode(String skuCode);
    int deleteBySkuCode2(List<String> list);

    int insertBatch(List<ProductSkuSupplyUnitCapacity> capacities);

    int deleteDraftsByVos(List<ProductSkuSupplyUnitCapacityDraft> capacityDrafts);

    List<ProductSkuSupplyUnitCapacity> selectSupplyCapacityInfo(@Param("supplierCode") String supplierCode, @Param("skuCode")String skuCode);

    int deleteByUintCode(List<String> supplierCode);

    List<ProductSkuSupplyUnitCapacityRespVo> getCapacityInfoBySupplyUnitCodeAndProductSkuCode(@Param("supplierCode")String supplyUnitCode, @Param("productSkuCode")String productSkuCode);

}