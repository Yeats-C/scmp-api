package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.ApplyStatus;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplySettlementInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SettlementInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySettlementInformation;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SettlementInformation;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySettlementDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySettlementInfoReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySettlementInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySettlementVO;
import com.aiqin.bms.scmp.api.supplier.mapper.ApplySettlementInformationMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.SettlementInformationMapper;
import com.aiqin.bms.scmp.api.supplier.service.ApplySettlementService;
import com.aiqin.bms.scmp.api.supplier.service.EncodingRuleService;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/4 0004 10:06
 */
@Service
public class ApplySettlementServiceImpl extends SupplierBaseServiceImpl implements ApplySettlementService {
    @Autowired
    private ApplySettlementInfoDao applySettlementInfoDao;
    @Autowired
    private ApplySettlementInformationMapper applySettlementInformationMapper;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private EncodingRuleService encodingRuleService;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private SettlementInformationMapper settlementInformationMapper;
    @Autowired
    private SettlementInfoDao settlementInfoDao;

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long saveApply(ApplySettlementInfoReqVO applySettlementInfoReqVO) {
        ApplySettlementInfoReqDTO applySettlementInfoReqDTO = new ApplySettlementInfoReqDTO();
        BeanCopyUtils.copy(applySettlementInfoReqVO,applySettlementInfoReqDTO);
        //结算申请编码
        Long thisCode=encodingRuleService.getNumberValue((long)12);
        applySettlementInfoReqDTO.setApplyCode(String.valueOf(thisCode+1));
        applySettlementInfoReqDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
        applySettlementInfoReqDTO.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
        Long resultNum = insertApplyAndLog(applySettlementInfoReqDTO);
        encodingRuleService.updateNumberValue(thisCode,(long)12);
        return resultNum;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int updateApply(ApplySettlementVO applySettlementVO) {
        int num;
        ApplySettlementInformation applySettlementInformation = new ApplySettlementInformation();
        BeanCopyUtils.copy(applySettlementVO,applySettlementInformation);
        applySettlementInformation.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
        applySettlementInformation.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
        num = ((ApplySettlementService) AopContext.currentProxy()).update(applySettlementInformation);
        if (null != applySettlementVO.getId()) {
            ApplySettlementInformation applySettlementInformation1 = applySettlementInformationMapper.selectByPrimaryKey(applySettlementVO.getId());
            supplierCommonService.getInstance(applySettlementInformation1.getApplyCode(), HandleTypeCoce.APPLY_UPDATE_SETTLEMENT_INFORMATION.getStatus(), ObjectTypeCode.APPLY_SETTLEMENT_INFORMATION.getStatus(), applySettlementInformation1,HandleTypeCoce.APPLY_UPDATE_SETTLEMENT_INFORMATION.getName());
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int cancelApply(Long id) {
        try {
            ApplySettlementInformation applySettlementInformation = new ApplySettlementInformation();
            applySettlementInformation.setId(id);
            applySettlementInformation.setApplyStatus(StatusTypeCode.CANCEL_APPLY.getStatus());
            int num = ((ApplySettlementService)AopContext.currentProxy()).update(applySettlementInformation);
            if (null != id) {
                ApplySettlementInformation applySettlementInformation1 = applySettlementInformationMapper.selectByPrimaryKey(id);
                supplierCommonService.getInstance(applySettlementInformation1.getApplyCode(), HandleTypeCoce.APPLY_UPDATE_REVOKE_SETTLEMENT_INFORMATION.getStatus(), ObjectTypeCode.APPLY_SETTLEMENT_INFORMATION.getStatus(), applySettlementInformation1,HandleTypeCoce.APPLY_UPDATE_REVOKE_SETTLEMENT_INFORMATION.getName());
            }
            if(num > 0){
                return  num;
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"撤销失败"));
            }
        } catch (GroundRuntimeException e){
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int update(ApplySettlementInformation applySettlementInformation) {
        int resultNum= applySettlementInformationMapper.updateByPrimaryKeySelective(applySettlementInformation);
        if(resultNum > 0){
            return resultNum;
        } else {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"修改失败"));
        }
    }

    @Override
    @Save
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long insert(ApplySettlementInfoReqDTO applySettlementInfoReqDTO){
        Long resultNum= applySettlementInfoDao.insertApply(applySettlementInfoReqDTO);
        if(resultNum > 0){
            return applySettlementInfoReqDTO.getId();
        } else {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"新增失败"));
        }
    }

    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long insertApplyAndLog(ApplySettlementInfoReqDTO applySettlementInfoReqDTO){
        Long resultNum = ((ApplySettlementService) AopContext.currentProxy()).insert(applySettlementInfoReqDTO);
        resultNum= supplierCommonService.getInstance(applySettlementInfoReqDTO.getApplyCode(), HandleTypeCoce.APPLY_ADD_SETTLEMENT_INFORMATION.getStatus(), ObjectTypeCode.APPLY_SETTLEMENT_INFORMATION.getStatus(), applySettlementInfoReqDTO,HandleTypeCoce.APPLY_ADD_SETTLEMENT_INFORMATION.getName());
        return resultNum;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long insideSaveApply(ApplySettlementDTO applySettlementDTO) {
        EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.APPLY_SETTLEMENT_CODE);
        applySettlementDTO.setApplyCode(String.valueOf(encodingRule.getNumberingValue()+1));
        ApplySettlementInfoReqDTO applySettlementInfoReqDTO = new ApplySettlementInfoReqDTO();
        applySettlementDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
        BeanCopyUtils.copy(applySettlementDTO,applySettlementInfoReqDTO);
        Long resultNum = insertApplyAndLog(applySettlementInfoReqDTO);
        encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        return resultNum;
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public String insideWorkFlowCallback(ApplySettlementInformation applySettlementInformation, WorkFlowCallbackVO vo) {
        if(Objects.isNull(applySettlementInformation)){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        if(applySettlementInformation.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            applySettlementInformation.setApplyStatus(vo.getApplyStatus());
            if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())){
                SettlementInformation oldSetInfo = settlementInfoDao.getSetInfoByApplyCode(applySettlementInformation.getApplyCode());
                //通过插入正式数据
                SettlementInformation settlementInformation = new SettlementInformation();
                BeanCopyUtils.copy(applySettlementInformation,settlementInformation);
                settlementInformation.setAuditorBy(vo.getApprovalUserName());
                settlementInformation.setAuditorTime(new Date());
                settlementInformation.setApplySettlementInformationCode(applySettlementInformation.getApplyCode());
                if (oldSetInfo != null){
                    settlementInformation.setId(oldSetInfo.getId());
                    settlementInformationMapper.updateByPrimaryKey(settlementInformation);
                } else {
                    EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.SETTLEMENT_CODE);
                    settlementInformation.setId(null);
                    settlementInformation.setSettlementCode(String.valueOf(encodingRule.getNumberingValue()+1));
                    settlementInformation.setApplySettlementInformationCode(applySettlementInformation.getApplyCode());
                    settlementInformationMapper.insert(settlementInformation);
                    encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
                }
                supplierCommonService.getInstance(settlementInformation.getSettlementCode(),HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_SETTLEMENT_INFORMATION.getStatus(),ObjectTypeCode.SETTLEMENT_INFORMATION.getStatus(),settlementInformation,HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_SETTLEMENT_INFORMATION.getName());
            }else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
                //驳回, 设置状态
                applySettlementInformation.setApplyStatus(vo.getApplyStatus());
            }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
                //传入的是审批中，继续该流程
                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
            }else if (vo.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber())){
                applySettlementInformation.setApplyStatus(vo.getApplyStatus());
            }else {
                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
            }
        }else {
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        applySettlementInformation.setAuditorBy(vo.getApprovalUserName());
        applySettlementInformation.setAuditorTime(new Date());
        applySettlementInformationMapper.updateByPrimaryKey(applySettlementInformation);
        //判断审核状态，存日志信息
        HandleTypeCoce s = applySettlementInformation.getApplyStatus().intValue()==ApplyStatus.APPROVAL_SUCCESS.getNumber()?HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUCCESS_SETTLEMENT_INFORMATION:HandleTypeCoce.APPLY_UPDATE_APPROVAL_FAIL_SETTLEMENT_INFORMATION;
        supplierCommonService.getInstance(applySettlementInformation.getApplyCode(),s.getStatus(),ObjectTypeCode.APPLY_SETTLEMENT_INFORMATION.getStatus(),applySettlementInformation,s.getName());
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }

}
