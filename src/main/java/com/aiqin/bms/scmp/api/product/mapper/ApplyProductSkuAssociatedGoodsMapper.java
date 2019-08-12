package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuAssociatedGoods;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuAssociatedGoodsRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuAssociatedGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuAssociatedGoods record);

    int insertSelective(ApplyProductSkuAssociatedGoods record);

    ApplyProductSkuAssociatedGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuAssociatedGoods record);

    int updateByPrimaryKey(ApplyProductSkuAssociatedGoods record);

    int insertBatch(List<ApplyProductSkuAssociatedGoods> list);

    List<ProductSkuAssociatedGoodsRespVo> getApplys(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);
    List<ApplyProductSkuAssociatedGoods> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);
}