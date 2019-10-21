package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApprovalFileInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;

import java.util.List;

public interface ApprovalFileInfoMapper {

    int insertBatch(List<ApprovalFileInfo> approvalFileInfos);

    List<ApprovalFileInfoReqVo> selectByApprovalTypeAndApplyCode(Byte approvalType, String applyCode);

    List<ApprovalFileInfoReqVo> selectByApprovalTypeAndFormNo(Byte approvalType, String formNo);
}