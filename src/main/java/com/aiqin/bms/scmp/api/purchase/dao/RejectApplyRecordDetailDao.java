package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyResponse;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RejectApplyRecordDetailDao {
    int deleteByPrimaryKey(Long id);

    int insertAll(@Param("list") List<RejectApplyRecordDetail> list);

    int updateByPrimaryKey(RejectApplyRecordDetail record);

    List<RejectApplyResponse> listForRejectRecord(RejectApplyRequest rejectApplyQueryRequest);

    List<RejectApplyRecordDetail> listByCondition(@Param("supplierCode") String supplierCode,@Param("purchaseGroupCode") String purchaseGroupCode,@Param("settlementMethodCode") String settlementMethod,@Param("transportCenterCode") String transportCenterCode, @Param("warehouseCode")String warehouseCode, @Param("rejectApplyRecordCodes")List<String> rejectApplyRecordCodes);

    Integer updateByDetailIds(@Param("list") List<String> detailIds);

    void deleteAll(String rejectApplyRecordCode);

    List<RejectApplyDetailResponse> selectByRejectCode(String rejectApplyCode);

    SupplyCompany selectSupplyCompany(String supplierCode);
}