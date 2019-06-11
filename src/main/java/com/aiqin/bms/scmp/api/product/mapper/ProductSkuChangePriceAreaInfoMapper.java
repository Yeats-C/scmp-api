package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceAreaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuChangePriceAreaInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuChangePriceAreaInfo record);

    int insertSelective(ProductSkuChangePriceAreaInfo record);

    ProductSkuChangePriceAreaInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuChangePriceAreaInfo record);

    int updateByPrimaryKey(ProductSkuChangePriceAreaInfo record);
    /**
     * 批量插入
     * @author NullPointException
     * @date 2019/5/22
     * @param area
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuChangePriceAreaInfo> area);
    /**
     * 通过编码删除
     * @author NullPointException
     * @date 2019/5/23
     * @param code
     * @return int
     */
    int deleteByCode(String code);
}