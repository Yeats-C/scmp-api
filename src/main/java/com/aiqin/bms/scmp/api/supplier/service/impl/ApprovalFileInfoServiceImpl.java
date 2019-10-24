package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApprovalFileInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import com.aiqin.bms.scmp.api.supplier.mapper.ApprovalFileInfoMapper;
import com.aiqin.bms.scmp.api.supplier.service.ApprovalFileInfoService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service
public class ApprovalFileInfoServiceImpl extends BaseServiceImpl implements ApprovalFileInfoService {

    @Autowired
    private ApprovalFileInfoMapper approvalFileInfoMapper;

    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<ApprovalFileInfoReqVo> approvalFileInfos, String applyCode, String formNo, Byte approvalType) {
        if (CollectionUtils.isNotEmptyCollection(approvalFileInfos)) {
            List<ApprovalFileInfo> approvalFileInfos1 = BeanCopyUtils.copyList(approvalFileInfos, ApprovalFileInfo.class);
            AuthToken user = getUser();
            String companyCode = user.getCompanyCode();
            String companyName = user.getCompanyName();
            approvalFileInfos1.forEach(item -> {
                item.setApplyCode(applyCode);
                item.setFormNo(formNo);
                item.setApprovalType(approvalType);
                item.setCompanyCode(companyCode);
                item.setCompanyName(companyName);
            });
            approvalFileInfoMapper.insertBatch(approvalFileInfos1);
        }
    }

    @Override
    public List<ApprovalFileInfoReqVo> selectByApprovalTypeAndApplyCode(Byte approvalType, String applyCode) {
        return approvalFileInfoMapper.selectByApprovalTypeAndApplyCode(approvalType, applyCode);
    }

    @Override
    public List<ApprovalFileInfoReqVo> selectByApprovalTypeAndFormNo(Byte approvalType, String formNo) {
        return approvalFileInfoMapper.selectByApprovalTypeAndFormNo(approvalType, formNo);
    }
}
