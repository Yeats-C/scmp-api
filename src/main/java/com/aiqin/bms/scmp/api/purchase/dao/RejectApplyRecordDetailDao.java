package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectApplyRecordDetailDao {

    List<RejectApplyRecordDetail> rejectApplyRecordDetailList(RejectApplyQueryRequest request);

    Integer rejectApplyRecordDetailCount(RejectApplyQueryRequest request);

    List<RejectApplyRecordDetail> rejectApplyRecordBatchList(RejectApplyQueryRequest request);

    Integer rejectApplyRecordBatchCount(RejectApplyQueryRequest request);

    List<RejectApplyDetailHandleResponse> rejectApplyRecordDetailByEdit(String rejectApplyRecordCode);

    Integer insertAll(@Param("list") List<RejectApplyRecordDetail> list);

    Integer update(RejectApplyRecordDetail record);

    Integer delete(String rejectApplyRecordCode);

    List<RejectRecordDetail> rejectApplyByWarehouseProduct(@Param("rejectApplyRecordCode") String rejectApplyRecordCode,
                                                           @Param("warehouseCode") String warehouseCode);

    List<RejectRecordBatch> rejectApplyByWarehouseBatch(@Param("rejectApplyRecordCode") String rejectApplyRecordCode,
                                                        @Param("warehouseCode") String warehouseCode,
                                                        @Param("skuCode") String skuCode,
                                                        @Param("supplierCode") String supplierCode,
                                                        @Param("settlementMethodCode") String settlementMethodCode,
                                                        @Param("productType") Integer productType);

}