package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.UpdateOutBoundReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.UpdateStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.QueryOutboundReqVo;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;

import java.util.List;


public interface OutboundDao {


    /**
     * 分页查询以及搜索
     * @param vo
     * @return
     */
    List<Outbound> getOutboundList(QueryOutboundReqVo vo);

    int deleteByPrimaryKey(Long id);

    Outbound selectByCode(String outboundOderCode);

    int insert(Outbound record);

    int insertSelective(Outbound record);

    Outbound selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Outbound record);

    int updateByPrimaryKey(Outbound record);

    List<Outbound> selectOutboundInfoByBoundSearch(BoundRequest boundRequest);

    List<UpdateStockReqVo> selectUpdateStockInfoBySourceOrderCode(String sourceOderCode, String stockStatusCode);

    int updateOutboundInfo(UpdateOutBoundReqVO reqVO);

    /**
     * 批量插入出库数据
     * @author NullPointException
     * @date 2019/6/26
     * @param list
     * @return int
     */
    int insertBatch(List<Outbound> list);

    Outbound selectById(Long id);

    List<Outbound> listForSap(SapOrderRequest sapOrderRequest);

    void updateByOrderCodes(List<String> list);

    Outbound selectBySourceCode(String sourceOderCode);
}