package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
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
}