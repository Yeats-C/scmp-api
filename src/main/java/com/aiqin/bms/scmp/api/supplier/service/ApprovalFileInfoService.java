package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApprovalFileInfoService extends BaseService {
    /**
     * 批量保存
     * @param approvalFileInfos
     * @param applyCode
     * @param formNo
     * @param approvalType
     */
    void batchSave(List<ApprovalFileInfoReqVo> approvalFileInfos, String applyCode, String formNo, Byte approvalType);

    /**
     * 根据审批类型和申请编码查询
     * @param approvalType
     * @param applyCode
     * @return
     */
    List<ApprovalFileInfoReqVo> selectByApprovalTypeAndApplyCode( Byte approvalType,  String applyCode);

    /**
     * 根据审批类型和表单编码
     * @param approvalType
     * @param formNo
     * @return
     */
    List<ApprovalFileInfoReqVo> selectByApprovalTypeAndFormNo(Byte approvalType, String formNo);
}
