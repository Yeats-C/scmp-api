package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannel;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceChannelRespVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PriceChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PriceChannel record);

    int insertSelective(PriceChannel record);

    PriceChannel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PriceChannel record);

    int updateByPrimaryKey(PriceChannel record);

    List<QueryPriceChannelRespVo> getList(QueryPriceChannelReqVo reqVo);

    Integer checkName(@Param("name") String name, @Param("id") Long id, @Param("companyCode") String companyCode);

    /**
     * 通过名称查询
     * @param channelList
     * @param companyCode
     * @return
     */
    @MapKey("priceChannelName")
    Map<String, PriceChannel> selectByChannelNames(@Param("list") Set<String> channelList, @Param("companyCode") String companyCode);
}