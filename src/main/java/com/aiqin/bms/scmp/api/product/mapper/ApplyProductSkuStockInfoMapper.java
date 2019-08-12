package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuStockInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuStockInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuStockInfo record);

    int insertSelective(ApplyProductSkuStockInfo record);

    ApplyProductSkuStockInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuStockInfo record);

    int updateByPrimaryKey(ApplyProductSkuStockInfo record);

    int insertBatch(List<ApplyProductSkuStockInfo> list);

    List<PurchaseSaleStockRespVo> getList(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);


    List<ApplyProductSkuStockInfo> getApplyProductSkuStockInfo(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);
}