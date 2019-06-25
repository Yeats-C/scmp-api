package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectApplyRecordDetailDao {
    int deleteByPrimaryKey(Long id);

    int insertAll(List<RejectApplyRecordDetail> list);

    int updateByPrimaryKeySelective(RejectApplyRecordDetail record);

    int updateByPrimaryKey(RejectApplyRecordDetail record);

    List<RejectApplyResponse> listForRejectRecord(RejectApplyRequest rejectApplyQueryRequest);

    List<RejectApplyDetailResponse> listByCondition(@Param("supplierCode") String supplierCode,@Param("purchaseGroupCode") String purchaseGroupCode,@Param("settlementMethodCode") String settlementMethod,@Param("transportCenterCode") String transportCenterCode, @Param("warehouseCode")String warehouseCode, @Param("rejectApplyRecordCodes")List<String> rejectApplyRecordCodes);

    Integer updateByDetailIds(@Param("list") List<String> detailIds);

    void deleteAll(String rejectApplyRecordCode);

    List<RejectApplyDetailResponse> selectByRejectCode(String rejectApplyCode);
}