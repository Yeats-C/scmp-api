package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.ReturnOutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.OutboundBatchWmsResVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundBatchDao {
    int deleteByPrimaryKey(Long id);

    int insert(OutboundBatch record);

    int insertSelective(OutboundBatch record);

    OutboundBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutboundBatch record);

    int updateByPrimaryKey(OutboundBatch record);

    List<OutboundBatch> selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch);

    Integer countOutboundBatchInfoByOutboundOderCode(String outboundOderCode);

    Integer insertInfo(@Param("list")List<OutboundBatch> outboundBatches);

    List<OutboundBatchWmsResVO> selectOutboundBatchWmsResponse(String outboundOderCode);

    List<OutboundBatch> selectByOutboundBatchOderCode(String outboundOderCode);

    ReturnOutboundBatch selectBatchInfoByLinenum(@Param("outboundOderCode") String outboundOderCode, @Param("skuCode") String skuCode, @Param("linenum") Long linenum);

    Integer updateBatchInfoByOutboundOderCodeAndLineNum(OutboundBatch outboundBatch);
}