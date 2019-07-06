package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuChannel;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuChannelRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuChannel record);

    int insertSelective(ApplyProductSkuChannel record);

    ApplyProductSkuChannel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuChannel record);

    int updateByPrimaryKey(ApplyProductSkuChannel record);

    int insertBartch(List<ApplyProductSkuChannel> list);

    List<ProductSkuChannelRespVo> selectBySkuAndApplyCode(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);
}