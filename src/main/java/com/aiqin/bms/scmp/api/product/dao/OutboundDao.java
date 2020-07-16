package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.QueryOutboundReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundDao {

    /**
     * 分页查询以及搜索
     */
    List<Outbound> getOutboundList(QueryOutboundReqVo vo);

    Outbound selectByCode(String outboundOderCode);

    Integer insert(Outbound record);

    int insertSelective(Outbound record);

    Outbound selectByPrimaryKey(Long id);

    Integer update(Outbound record);

    List<Outbound> selectOutboundInfoByBoundSearch(BoundRequest boundRequest);

    /**
     * 批量插入出库数据
     */
    int insertBatch(List<Outbound> list);

    List<Outbound> listForSap(SapOrderRequest sapOrderRequest);

    void updateByOrderCodes(List<String> list);

    Outbound selectBySourceCode(@Param("sourceOderCode") String sourceOderCode, @Param("outboundTypeCode") String outboundTypeCode);

    Outbound selectOutbouondBySourceCode(@Param("sourceOderCode") String sourceOderCode);

    /** 调拨/损溢出库同步sap **/
    void updateOutboundSynchrSap(Outbound outbound);
}