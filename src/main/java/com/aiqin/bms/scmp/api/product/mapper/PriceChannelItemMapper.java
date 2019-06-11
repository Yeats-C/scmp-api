package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem;

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
}