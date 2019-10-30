package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.HandlingExceptionCode;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplySupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompany;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


public interface ApplySupplyComReviseService extends BaseService{
}
