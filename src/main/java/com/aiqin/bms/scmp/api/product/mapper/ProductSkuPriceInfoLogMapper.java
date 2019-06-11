package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuPriceInfoLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPriceInfoLog record);

    int insertSelective(ProductSkuPriceInfoLog record);

    ProductSkuPriceInfoLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPriceInfoLog record);

    int updateByPrimaryKey(ProductSkuPriceInfoLog record);


    /**
     * 批量插入日志信息
     * @author NullPointException
     * @date 2019/5/27
     * @param logList
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuPriceInfoLog> logList);
}