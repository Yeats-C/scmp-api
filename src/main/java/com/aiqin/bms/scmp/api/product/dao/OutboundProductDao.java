package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.UpdateOutboundProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.ReturnOutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.OutboundProductWmsResVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundProductDao {

    int deleteByPrimaryKey(Long id);

    int insert(OutboundProduct record);

    int insertSelective(OutboundProduct record);

    OutboundProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutboundProduct record);

    int updateByPrimaryKey(OutboundProduct record);

    /**
     * 批量插入
     * @param records
     * @return
     */
    int insertBatch(List<OutboundProduct> records);

    List<OutboundProduct> selectByOutboundOderCode(String outboundOderCode);

    List<OutboundProduct> selectOutboundProductListByOutBoundOderCodeList(@Param("outboundOderCodeList") List<String> outboundOderCodeList);

    List<OutboundProduct> selectOutboundProductInfoBySourceOrderCode(String sourceOrderCode);


    int updateBatch(List<UpdateOutboundProductReqVO> records);

    /**
     * 通过出库单编码查询sku
     * @param outboundOderCode
     * @return
     */
    List<OutboundProductWmsResVO> selectMmsReqByOutboundOderCode(String outboundOderCode);



    /**
     *  出库单sku 详情以及进项，销项水税率
     * @param outboundOderCode
     * @param skuCode
     * @param linenum
     */
    ReturnOutboundProduct selectByLinenum(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode, @Param("linenum") Long linenum);

    List<ReturnOutboundProduct> selectTax(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode);

    /**
     * 回显出库库存成本
     */
    Integer updateStockCost(@Param("productStockCost")Long productStockCost, @Param("outboundOderCode")String outboundOderCode, @Param("skuCode")String skuCode);
}