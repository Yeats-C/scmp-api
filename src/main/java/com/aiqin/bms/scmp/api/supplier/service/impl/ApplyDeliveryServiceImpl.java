package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplyDeliveryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.DeliveryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyDeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplyDeliveryDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplyDeliveryInfoReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplyDeliveryInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplyDeliveryVO;
import com.aiqin.bms.scmp.api.supplier.mapper.ApplyDeliveryInformationMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.DeliveryInformationMapper;
import com.aiqin.bms.scmp.api.supplier.service.ApplyDeliveryService;
import com.aiqin.bms.scmp.api.supplier.service.EncodingRuleService;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/4 0004 10:05
 */
@Service
public class ApplyDeliveryServiceImpl implements ApplyDeliveryService {
    @Autowired
    private ApplyDeliveryInfoDao applyDeliveryInfoDao;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private ApplyDeliveryInformationMapper applyDeliveryInformationMapper;
    @Autowired
    private EncodingRuleService encodingRuleService;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private DeliveryInformationMapper deliveryInformationMapper;
    @Autowired
    private DeliveryInfoDao deliveryInfoDao;

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long saveApply(ApplyDeliveryInfoReqVO applyDeliveryInfoReq) {
        ApplyDeliveryInfoReqDTO applyDeliveryInfoReqDTO = new ApplyDeliveryInfoReqDTO();
        BeanUtils.copyProperties(applyDeliveryInfoReq,applyDeliveryInfoReqDTO);
        Long resultNum = insertApplyAndLog(applyDeliveryInfoReqDTO);
        return resultNum;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int updateApply(ApplyDeliveryVO applyDeliveryVO) {
        int num;
        ApplyDeliveryInformation applyDeliveryInformation = new ApplyDeliveryInformation();
        BeanCopyUtils.copy(applyDeliveryVO,applyDeliveryInformation);
        applyDeliveryInformation.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
        applyDeliveryInformation.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
        num = ((ApplyDeliveryService)AopContext.currentProxy()).update(applyDeliveryInformation);
        if (null != applyDeliveryVO.getId()){
            ApplyDeliveryInformation applyDeliveryInformation1 = applyDeliveryInformationMapper.selectByPrimaryKey(applyDeliveryVO.getId());
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int cancelApply(Long id) {
        try {
            ApplyDeliveryInformation applyDeliveryInformation =new ApplyDeliveryInformation();
            applyDeliveryInformation.setId(id);
            applyDeliveryInformation.setApplyStatus(StatusTypeCode.CANCEL_APPLY.getStatus());
            int num = ((ApplyDeliveryService)AopContext.currentProxy()).update(applyDeliveryInformation);
            if (null != id){
                ApplyDeliveryInformation applyDeliveryInformation1 = applyDeliveryInformationMapper.selectByPrimaryKey(id);
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
    public int update(ApplyDeliveryInformation applyDeliveryInformation) {
        int resultNum= applyDeliveryInformationMapper.updateByPrimaryKeySelective(applyDeliveryInformation);
        if(resultNum>0){
            return resultNum;
        } else {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"修改失败"));
        }
    }

    /**
     * 新增申请和日志公共方法
     * @param applyDeliveryInfoReqDTO
     * @return
     */
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public  Long insertApplyAndLog(ApplyDeliveryInfoReqDTO applyDeliveryInfoReqDTO){
        //收货信息申请编码
       String  code = encodingRuleService.getNumberingType(EncodingRuleType.APPLY_DELIVERY_CODE);
        //long code = Long.parseLong(redisCode) + 1;
        applyDeliveryInfoReqDTO.setApplyCode(code);
        applyDeliveryInfoReqDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
        applyDeliveryInfoReqDTO.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
        Long resultNum = ((ApplyDeliveryService) AopContext.currentProxy()).insert(applyDeliveryInfoReqDTO);
        //encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        return resultNum;
    }
    /**
     * 调用Dao 添加申请收货信息
     * @param applyDeliveryInfoReqDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public Long insert(ApplyDeliveryInfoReqDTO applyDeliveryInfoReqDTO){
        Long resultNum= applyDeliveryInfoDao.insertApply(applyDeliveryInfoReqDTO);
        if(resultNum>0){
            return applyDeliveryInfoReqDTO.getId();
        } else {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"新增失败"));
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long insideSaveApply(ApplyDeliveryDTO applyDeliveryDTO) {
        ApplyDeliveryInfoReqDTO applyDeliveryInfoReqDTO = new ApplyDeliveryInfoReqDTO();
        applyDeliveryDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
        BeanUtils.copyProperties(applyDeliveryDTO,applyDeliveryInfoReqDTO);
        Long resultNum = insertApplyAndLog(applyDeliveryInfoReqDTO);
        return resultNum;
    }

    /**
     * 非前端请求新增
     *
     * @param deliveryDTOList
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public Long insideSaveBatchApply(List<ApplyDeliveryDTO> deliveryDTOList) {
        Long num = 0L;
        for (ApplyDeliveryDTO applyDeliveryDTO : deliveryDTOList) {
            num += ((ApplyDeliveryService)AopContext.currentProxy()).insideSaveApply(applyDeliveryDTO);
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = BizException.class )
    public String insideWorkFlowCallback(List<ApplyDeliveryInformation> applyDeliveryInformations, WorkFlowCallbackVO vo) {
        if(CollectionUtils.isEmptyCollection(applyDeliveryInformations)){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }

//        if(applyDeliveryInformation.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
//            applyDeliveryInformation.setApplyStatus(vo.getApplyStatus());
//            if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())){
//                DeliveryInformation oldDeliveryInfo = deliveryInfoDao.getDeliveryInfoByApplyCode(applyDeliveryInformation.getApplyCode());
//                //通过插入正式数据
//                DeliveryInformation deliveryInformation = new DeliveryInformation();
//                BeanCopyUtils.copy(applyDeliveryInformation,deliveryInformation);
//                deliveryInformation.setAuditorBy(vo.getApprovalUserName());
//                deliveryInformation.setAuditorTime(new Date());
//                deliveryInformation.setApplyDeliveryInformationCode(applyDeliveryInformation.getApplyCode());
//                if (null != oldDeliveryInfo){
//                    deliveryInformation.setId(oldDeliveryInfo.getId());
//                    deliveryInformationMapper.updateByPrimaryKey(deliveryInformation);
//                } else {
//                    deliveryInformation.setId(null);
//                    deliveryInformationMapper.insert(deliveryInformation);
//                }
//                supplierCommonService.getInstance(String.valueOf(deliveryInformation.getId()),HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_DELIVERY_INFORMATION.getStatus(),ObjectTypeCode.DELIVERY_INFORMATION.getStatus(),deliveryInformation,HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_DELIVERY_INFORMATION.getName());
//            }else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
//                //驳回, 设置状态
//                applyDeliveryInformation.setApplyStatus(vo.getApplyStatus());
//            }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
//                //传入的是审批中，继续该流程
//                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
//            }else if (vo.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber())){
//                applyDeliveryInformation.setApplyStatus(vo.getApplyStatus());
//            }else {
//                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
//            }
//        }else {
//            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
//        }
//        applyDeliveryInformation.setAuditorBy(vo.getApprovalUserName());
//        applyDeliveryInformation.setAuditorTime(new Date());
//        applyDeliveryInformationMapper.updateByPrimaryKey(applyDeliveryInformation);
//        //判断审核状态，存日志信息
//        HandleTypeCoce s = applyDeliveryInformation.getApplyStatus().intValue()==ApplyStatus.APPROVAL_SUCCESS.getNumber()?HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUCCESS_DELIVERY_INFORMATION:HandleTypeCoce.APPLY_UPDATE_APPROVAL_FAIL_DELIVERY_INFORMATION;
//        supplierCommonService.getInstance(applyDeliveryInformation.getApplyCode(),s.getStatus(),ObjectTypeCode.APPLY_DELIVERY_INFORMATION.getStatus(),applyDeliveryInformation,s.getName());
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }
}
