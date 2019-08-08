package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuSaleAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuSaleArea record);

    int insertSelective(ApplyProductSkuSaleArea record);

    ApplyProductSkuSaleArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuSaleArea record);

    int updateByPrimaryKey(ApplyProductSkuSaleArea record);
    /**
     * 通过sku编码查询销售区域sku信息
     * @author NullPointException
     * @date 2019/6/4
     * @param skuCodes
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ApplyProductSkuSaleArea>
     */
    List<ApplyProductSkuSaleArea> selectBySkuCodes(@Param("items") List<String> skuCodes,@Param("companyCode") String companyCode);
    /**
     * 批量保存
     * @author NullPointException
     * @date 2019/6/4
     * @param skuInfoList
     * @return int
     */
    int insertBatch(@Param("items") List<ApplyProductSkuSaleArea> skuInfoList);
}