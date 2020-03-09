package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaChannel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductSkuSaleAreaChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleAreaChannel record);

    int insertSelective(ProductSkuSaleAreaChannel record);

    ProductSkuSaleAreaChannel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleAreaChannel record);

    int updateByPrimaryKey(ProductSkuSaleAreaChannel record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/5
     * @param channelList
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuSaleAreaChannel> channelList);
    /**
     * 批量删除
     * @author NullPointException
     * @date 2019/6/5
     * @param codes
     * @return int
     */
    int deleteByCodes(@Param("items") Set<String> codes);


    /**
     * 批量删除
     * @author NullPointException
     * @date 2019/6/5
     * @return int
     */
    int deleteByCode(@Param("code") String code);
}