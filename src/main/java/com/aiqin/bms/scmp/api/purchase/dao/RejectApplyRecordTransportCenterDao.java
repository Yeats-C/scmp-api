package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordTransportCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectApplyRecordTransportCenterDao {

    Integer insert(RejectApplyRecordTransportCenter record);

    Integer update(RejectApplyRecordTransportCenter record);

    List<RejectApplyRecordTransportCenter> rejectApplyTransportCenterInfo(RejectApplyRecordTransportCenter record);

    Integer insertAll(@Param("list") List<RejectApplyRecordTransportCenter> list);

    Integer delete(String rejectApplyRecordCode);

}