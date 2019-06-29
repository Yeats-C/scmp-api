package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannel;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.PriceChannelRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceChannelRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceChannelService
 * @date 2019/4/19 15:30
 * @description TODO
 */
public interface PriceChannelService {

    /**
     * 查询List
     * @param reqVo
     * @return
     */
    BasePage<QueryPriceChannelRespVo> getList(QueryPriceChannelReqVo reqVo);

    /**
     * 查询所有启用
     * @return
     */
    List<QueryPriceChannelRespVo> getAll();


    /**
     * 根据Id查询
     * @param id
     * @return
     */
    PriceChannelRespVo view(Long id);

    /**
     * 新增
     * @param addVo
     * @return
     */
    int add(AddPriceChannelReqVo addVo);

    /**
     * 插入
     * @param priceChannel
     * @return
     */
    Integer insertSelective(PriceChannel priceChannel);

    /**
     * 修改
     * @param updateVo
     * @return
     */
    int update(UpdatePriceChannelReqVo updateVo);

    /**
     * 修改
     * @param priceChannel
     * @return
     */
    Integer updateByPrimaryKeySelective(PriceChannel priceChannel);

    Integer insertBatchItem(List<PriceChannelItem> items);
    /**
     * 通过渠道编码集合查询关联的价格项目
     * @author NullPointException
     * @date 2019/6/29
     * @param codes
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem>
     */
    List<PriceChannelItem> selectByChannelCodes(List<String> codes);
}
