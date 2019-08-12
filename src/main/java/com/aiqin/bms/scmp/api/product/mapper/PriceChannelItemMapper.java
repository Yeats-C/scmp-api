package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PriceChannelItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PriceChannelItem record);

    int insertSelective(PriceChannelItem record);

    PriceChannelItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PriceChannelItem record);

    int updateByPrimaryKey(PriceChannelItem record);

    List<PriceChannelItem> selectByPriceChannelCode(String channelCode);

    int deleteByPriceChannelCode(String channelCode);

    int insertBach(List<PriceChannelItem> items);
    /**
     * 渠道编码集合和类型查询价格项目
     * @author NullPointException
     * @date 2019/6/29
     * @param codes
     * @param type
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem>
     */
    List<PriceChannelItem> selectByChannelCodes(@Param("codes") List<String> codes, @Param("type") Integer type);
}