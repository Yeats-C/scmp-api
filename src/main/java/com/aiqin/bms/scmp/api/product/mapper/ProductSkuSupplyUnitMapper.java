package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import org.apache.ibatis.annotations.Param;

public interface ProductSkuSupplyUnitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSupplyUnit record);

    int insertSelective(ProductSkuSupplyUnit record);

    ProductSkuSupplyUnit selectByPrimaryKey(Long id);

    ProductSkuSupplyUnit selectBySupplyCode(@Param("skuCode") String skuCode, @Param("supplyCode") String supplyCode);

    int updateByPrimaryKeySelective(ProductSkuSupplyUnit record);

    int updateByPrimaryKey(ProductSkuSupplyUnit record);

    /**
     * 根据sku编号和供应商编号查询数量
     * @param skuCode
     * @param supplyCode
     * @return
     */
    int selectCountBySkuCodeAndSupplyCode(@Param("skuCode") String skuCode, @Param("supplyCode") String supplyCode);
}