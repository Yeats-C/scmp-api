package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuChangePriceImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuChangePriceImage record);

    int insertSelective(ProductSkuChangePriceImage record);

    ProductSkuChangePriceImage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuChangePriceImage record);

    int updateByPrimaryKey(ProductSkuChangePriceImage record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/5/22
     * @param images
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuChangePriceImage> images);
    /**
     * 编码删除图片
     * @author NullPointException
     * @date 2019/5/23
     * @param code
     * @return int
     */
    int deleteByCode(String code);
}