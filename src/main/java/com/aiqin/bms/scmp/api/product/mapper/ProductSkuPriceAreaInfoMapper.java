package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceAreaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuPriceAreaInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPriceAreaInfo record);

    int insertSelective(ProductSkuPriceAreaInfo record);

    ProductSkuPriceAreaInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPriceAreaInfo record);

    int updateByPrimaryKey(ProductSkuPriceAreaInfo record);
    /**
     * 批量插入
     * @author NullPointException
     * @date 2019/5/25
     * @param areaInfos
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuPriceAreaInfo> areaInfos);
}