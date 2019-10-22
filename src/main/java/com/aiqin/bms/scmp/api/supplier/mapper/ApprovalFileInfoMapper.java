package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApprovalFileInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApprovalFileInfoMapper {

    int insertBatch(List<ApprovalFileInfo> approvalFileInfos);

    List<ApprovalFileInfoReqVo> selectByApprovalTypeAndApplyCode(@Param("approvalType") Byte approvalType, @Param("applyCode") String applyCode);

    List<ApprovalFileInfoReqVo> selectByApprovalTypeAndFormNo(@Param("approvalType") Byte approvalType, @Param("formNo") String formNo);
}