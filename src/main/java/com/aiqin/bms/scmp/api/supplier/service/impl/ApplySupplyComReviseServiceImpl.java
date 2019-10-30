package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.HandlingExceptionCode;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplySupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompany;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplyComReviseService;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplyComService;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@WorkFlowAnnotation(WorkFlow.APPLY_COMPANY_REVISE)
public class ApplySupplyComReviseServiceImpl extends BaseServiceImpl implements ApplySupplyComReviseService,WorkFlowHelper {

    @Autowired
    private ApplySupplyComService applySupplyComService;

    @Autowired
    private ApplySupplyCompanyDao applySupplyCompanyDao;

    @Override
    public String workFlowCallback(WorkFlowCallbackVO vo) {
        WorkFlowCallbackVO vo1 = updateSupStatus(vo);
        ApplySupplyCompany applySupplyCompany = applySupplyCompanyDao.getApplySupplyComByFormNo(vo1.getFormNo());
        if(Objects.isNull(applySupplyCompany)){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        return applySupplyComService.insideWorkFlowCallback(applySupplyCompany,vo1);
    }
}
