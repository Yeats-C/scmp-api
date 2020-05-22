package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.UpdateOutboundProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.ReturnOutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.OutboundProductWmsResVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OutboundProductDao {

    OutboundProduct selectByPrimaryKey(Long id);

    Integer update(OutboundProduct record);

    /**
     * 批量插入
     */
    int insertAll(@Param("list") List<OutboundProduct> records);

    List<OutboundProduct> selectByOutboundOderCode(String outboundOderCode);

    List<OutboundProduct> selectOutboundProductListByOutBoundOderCodeList(@Param("outboundOderCodeList") List<String> outboundOderCodeList);

    int updateBatch(List<UpdateOutboundProductReqVO> records);

    /**
     * 通过出库单编码查询sku
     */
    List<OutboundProductWmsResVO> selectMmsReqByOutboundOderCode(String outboundOderCode);

    /**
     *  出库单sku 详情以及进项，销项水税率
     */
    OutboundProduct selectByLineCode(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode, @Param("linenum") Long linenum);

    List<ReturnOutboundProduct> selectTax(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode);

    /**
     * 回显出库库存成本
     */
    Integer updateStockCost(@Param("productStockCost") BigDecimal productStockCost, @Param("outboundOderCode")String outboundOderCode, @Param("skuCode")String skuCode);

    List<ReturnOutboundProduct> selectBySkuCode(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode);

    List<OutboundProduct> listDetailForSap(SapOrderRequest sapOrderRequest);

    OutboundProduct selectByProductAmount(@Param("outboundOderCode") String outboundOderCode, @Param("lineCode") Long lineCode);

    Integer updateAll(List<UpdateOutboundProductReqVO> list);
}