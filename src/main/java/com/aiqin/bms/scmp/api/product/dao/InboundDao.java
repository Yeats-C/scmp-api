package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.QueryInboundReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboundDao {

    /**分页查询模糊搜索*/
    List<Inbound> getInboundList(QueryInboundReqVo vo);

    int deleteByPrimaryKey(Long id);

    int insert(Inbound record);

    int insertSelective(Inbound record);

    Inbound selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Inbound record);

    int updateByPrimaryKey(Inbound record);

    List<Inbound> selectInboundInfoByBoundSearch(BoundRequest boundRequest);

    Inbound selectByCode(String inboundOderCode);

    /*** 批量保存入库信息*/
    int insertBatch(List<Inbound> inboundList);

    List<Inbound> selectTimeAndSatusBySourchAndNum(@Param("sourceOderCode")String sourceOderCode);

    List<Inbound> listForSap(SapOrderRequest sapOrderRequest);

    void updateByOrderCodes(List<String> list);

    ///String cancelById(String code);

    Inbound inboundCodeOrderLast(@Param("sourceOderCode")String sourceOderCode,
                                 @Param("inboundTypeCode")String inboundTypeCode);

    /** 调拨/损溢入库同步sap **/
    void updateInboundSynchrSap(Inbound inbound);

    List<Inbound> inboundBySource(@Param("sourceOderCode")String sourceOderCode,
                                  @Param("inboundTypeCode")String inboundTypeCode);

    Integer inboundIsComplete(@Param("sourceOderCode")String sourceOderCode,
                              @Param("inboundTypeCode")String inboundTypeCode);

}