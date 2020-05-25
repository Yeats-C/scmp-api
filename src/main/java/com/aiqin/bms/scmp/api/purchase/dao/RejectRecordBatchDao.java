package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.purchase.ScmpPurchaseBatch;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectRecordBatchDao {

    Integer insertAll(@Param("list") List<RejectRecordBatch> batchList);

    Integer update(RejectRecordBatch record);

    List<RejectRecordBatch> list(String rejectRecordCode);

    Integer listCount(String rejectRecordCode);

    List<ScmpPurchaseBatch> rejectBatchListBySap(@Param("skuCode") String skuCode,
                                            @Param("rejectRecordCode") String rejectRecordCode,
                                            @Param("lineCode") Integer lineCode);

    List<RejectRecordBatch> rejectBatchInfoList(String rejectRecordCode);

    Integer updateAll(@Param("list") List<RejectRecordBatch> record);

}