package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleAreaChannel;

import java.util.List;

public interface ApplyProductSkuSaleAreaChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuSaleAreaChannel record);

    int insertSelective(ApplyProductSkuSaleAreaChannel record);

    ApplyProductSkuSaleAreaChannel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuSaleAreaChannel record);

    int updateByPrimaryKey(ApplyProductSkuSaleAreaChannel record);
    /**
     * 批量保存
     * @author NullPointException
     * @date 2019/6/4
     * @param channelInfoList
     * @return int
     */
    int insertBatch(List<ApplyProductSkuSaleAreaChannel> channelInfoList);
}