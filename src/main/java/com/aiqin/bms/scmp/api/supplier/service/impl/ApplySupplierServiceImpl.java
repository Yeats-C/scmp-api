package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplier;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Supplier;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplierDetailDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplierReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.*;
import com.aiqin.bms.scmp.api.supplier.mapper.*;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 16:50
 */
@Service
@WorkFlowAnnotation(WorkFlow.APPLY_SUPPLIER)
public class ApplySupplierServiceImpl extends SupplierBaseServiceImpl implements ApplySupplierService, WorkFlowHelper {
    @Autowired
    private EncodingRuleService encodingRuleService;
    @Autowired
    private ApplySupplierDao applySupplierDao;
    @Autowired
    private ApplySupplyComServcie applySupplyComServcie;
    @Autowired
    private ApplySupplyComAcctService applySupplyComAcctService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private ApplySupplierMapper applySupplierMapper;
    @Autowired
    private SupplierEventTriggeringServie supplierEventTriggeringServie;
    @Autowired
    private ApplySupplierFileService applySupplierFileService;
    @Autowired
    private ApplySupplierFileDao applySupplierFileDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private ApplySupplyCompanyDao applySupplyCompanyDao;
    @Autowired
    private ApplySupplyCompanyMapper applySupplyCompanyMapper;
    @Autowired
    private ApplyDeliveryInfoDao applyDeliveryInfoDao;
    @Autowired
    private ApplySettlementInfoDao applySettlementInfoDao;
    @Autowired
    private ApplyDeliveryInformationMapper applyDeliveryInformationMapper;
    @Autowired
    private ApplySettlementInformationMapper applySettlementInformationMapper;
    @Autowired
    private ApplySupplyCompanyAcctDao applySupplyCompanyAcctDao;
    @Autowired
    private ApplySupplyCompanyAccountMapper applySupplyCompanyAccountMapper;
    @Autowired
    private SupplierFileDao supplierFileDao;
    @Autowired
    private SupplyCompanyDao supplyCompanyDao;

    @Override
    @Transactional(rollbackFor = BizException.class)
    public Long saveApply(ApplySupplierReqVO supplierReq) {
        Long resultNum;
        try {
            String companyCode = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("name",supplierReq.getSupplierName());
            map.put("code",null);
            map.put("companyCode",companyCode);
            int nameCount = applySupplierDao.checkName(map);
            if (nameCount > 0){
                throw new BizException(ResultCode.NAME_REPEAT);
            }
            //复制对象
            ApplySupplierReqDTO applySupplierReqDTO = new ApplySupplierReqDTO();
            BeanCopyUtils.copy(supplierReq,applySupplierReqDTO);
            //供应商申请编码
            EncodingRule encodingRule =encodingRuleService.getNumberingType(EncodingRuleType.APPLY_SUPPLIER_CODE);
            applySupplierReqDTO.setApplySupplierCode(String.valueOf(encodingRule.getNumberingValue()+1));
            applySupplierReqDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
            applySupplierReqDTO.setFormNo(getFormNO());
            applySupplierReqDTO.setEnable(StatusTypeCode.EN_ABLE.getStatus());
            resultNum = ((ApplySupplierService) AopContext.currentProxy()).insert(applySupplierReqDTO);
            supplierCommonService.getInstance(applySupplierReqDTO.getApplySupplierCode(), HandleTypeCoce.APPLY_ADD_SUPPLIER.getStatus(), ObjectTypeCode.APPLY_SUPPLIER.getStatus(), applySupplierReqDTO,HandleTypeCoce.APPLY_ADD_SUPPLIER.getName());
            encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
            //调用审批接口
            workFlow(applySupplierReqDTO);
        } catch (Exception ex){
            throw new BizException(MessageId.create(Project.SUPPLIER_API, 41, ex.getMessage()));
        }
        return resultNum;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int updateApply(SupplierUpdateReqVO supplierUpdateReqVO) {
        int num;
        try {
            //如果修改状态为禁用,则需要验证是否有供应商
            if(StatusTypeCode.DIS_ABLE.getStatus().equals(supplierUpdateReqVO.getEnable())){
                QuerySupplyComReqVO querySupplyComReqVO = new QuerySupplyComReqVO();
                querySupplyComReqVO.setSupplierCode(supplierUpdateReqVO.getSupplierCode());
                querySupplyComReqVO.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
                List<SupplyComListRespVO> supplyCompanyList = supplyCompanyDao.getSupplyCompanyList(querySupplyComReqVO);
                if(CollectionUtils.isNotEmptyCollection(supplyCompanyList)){
                    throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"此供应商集团下包含了供应商不能禁用"));
                }
            }
            String companyCode = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("name",supplierUpdateReqVO.getSupplierName());
            map.put("code",supplierUpdateReqVO.getSupplierCode());
            map.put("companyCode",companyCode);
            int nameCount = supplierDao.checkName(map);
            if (nameCount > 0){
                throw new BizException(ResultCode.NAME_REPEAT);
            }
            //根据供应商编码获取供应商申请ID
            ApplySupplier applySupplier = new ApplySupplier();
            ApplySupplier applySupplierId = applySupplierDao.getApplySupplierBySupplierCode(supplierUpdateReqVO.getSupplierCode());
            if (null != applySupplierId){
                BeanCopyUtils.copy(supplierUpdateReqVO,applySupplier);
                applySupplier.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                applySupplier.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
                applySupplier.setId(applySupplierId.getId());
                applySupplier.setFormNo(getFormNO());
            } else {
                throw new BizException(ResultCode.UPDATE_ERROR);
            }
            //执行修改供应商申请
            num = ((ApplySupplierService) AopContext.currentProxy()).update(applySupplier);
            //修改供应商集团申请状态
            supplierDao.updatetSupplierApplyStatusByCode(supplierUpdateReqVO.getSupplierCode());
            ApplySupplierReqDTO applySupplierReqDTO = new ApplySupplierReqDTO();
            BeanCopyUtils.copy(applySupplierId,applySupplierReqDTO);
            applySupplierReqDTO.setApplySupplierCode(applySupplierId.getApplySupplierCode());
            applySupplierReqDTO.setFormNo(applySupplier.getFormNo());
            workFlow(applySupplierReqDTO);
        } catch (Exception e){
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
        return num;
    }



    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public Long insert(ApplySupplierReqDTO applySupplierReqDTO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            applySupplierReqDTO.setCompanyCode(authToken.getCompanyCode());
            applySupplierReqDTO.setCompanyName(authToken.getCompanyName());
        }
        Long resultNum = applySupplierDao.insertApply(applySupplierReqDTO);
        if(resultNum > 0){
            return resultNum;
        } else {
            throw new BizException(ResultCode.ADD_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workFlow(ApplySupplierReqDTO applySupplierReqDTO) {
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormUrl(workFlowBaseUrl.applySupplierGroupUrl+"?applyType="+applySupplierReqDTO.getApplyType()+"&applyCode="+applySupplierReqDTO.getApplySupplierCode()+"&id="+applySupplierReqDTO.getId()+"&itemCode=2"+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setFormNo(applySupplierReqDTO.getFormNo());
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ WorkFlow.APPLY_SUPPLIER.getNum());
            String applyTypeTitle = "新增";
            if(StatusTypeCode.ADD_APPLY.getStatus().equals(applySupplierReqDTO.getApplyType())) {
                applyTypeTitle =  "新增";
            } else {
                applyTypeTitle =  "修改";
            }
            workFlowVO.setTitle(applyTypeTitle+applySupplierReqDTO.getSupplierName()+"申请");
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_SUPPLIER);
            if(workFlowRespVO.getSuccess()){
                ApplySupplier applySupplier = new ApplySupplier();
                applySupplier.setId(applySupplierReqDTO.getId());
                //状态变为审核中
                applySupplier.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
                int i = applySupplierMapper.updateByPrimaryKeySelective(applySupplier);
                if(i<=0){
                    throw new BizException("审核状态修改失败");
                }
                //存日志
                supplierCommonService.getInstance(applySupplierReqDTO.getApplySupplierCode()+"", HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUPPLIER.getStatus(), ObjectTypeCode.APPLY_SUPPLIER.getStatus(),applySupplierReqDTO,HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUPPLIER.getName());
            }else {
                //存调用失败的日志
                String msg = workFlowRespVO.getMsg();
                throw new BizException(msg);
            }
        } catch (Exception e) {
            //存失败日志
            throw new BizException(e.getMessage());
        }
    }


    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int update(ApplySupplier applySupplier) {
        Long id=applySupplier.getId();
        String name=applySupplier.getSupplierName();
        int num=0;
        if(id!=null && StringUtils.isNotBlank(name)){
           ApplySupplier applySupplier1= applySupplierMapper.selectByPrimaryKey(id);
            if(!name.equals(applySupplier1.getSupplierName())){
                supplierEventTriggeringServie.pushSuplier(DataBaseType.APPLY_SUPPLIER,applySupplier.getApplySupplierCode(),name);
            }
            num = applySupplierMapper.updateByPrimaryKeySelective(applySupplier);
        } else {
            num = applySupplierMapper.updateByPrimaryKeySelective(applySupplier);
        }
        if(num > 0){
            return num;
        } else {
            throw new BizException(ResultCode.UPDATE_ERROR);
        }
    }


    @Override
    public BasePage<ApplySupplierListRespVO> getApplyList(QueryApplySupplierReqVO queryApplySupplierReqVO) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                queryApplySupplierReqVO.setCompanyCode(authToken.getCompanyCode());
                queryApplySupplierReqVO.setApplyBy(authToken.getPersonName());
            }
            PageHelper.startPage(queryApplySupplierReqVO.getPageNo(), queryApplySupplierReqVO.getPageSize());
            List<ApplySupplierListRespVO> applySupplierResps = applySupplierDao.getApplyList(queryApplySupplierReqVO);
            return PageUtil.getPageList(queryApplySupplierReqVO.getPageNo(),applySupplierResps);
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }


    @Override
    public ApplySupplierDetailRespVO getApplySupplierDetail(String applyCode) {
        try {
            ApplySupplierDetailRespVO applySupplierDetailRespVO = new ApplySupplierDetailRespVO();
            ApplyInfoRespVO applyInfoRespVO = new ApplyInfoRespVO();
            ApplySupplierInfoRespVO applySupplierInfoRespVO = new ApplySupplierInfoRespVO();
            ApplySupplierDetailDTO applySupplierDetailDTO = applySupplierDao.getApplySupplierDetail(applyCode);
            if (null != applySupplierDetailDTO){
                BeanCopyUtils.copy(applySupplierDetailDTO,applyInfoRespVO);
                BeanCopyUtils.copy(applySupplierDetailDTO,applySupplierInfoRespVO);
            } else {
                return applySupplierDetailRespVO;
            }
            if (null != applyInfoRespVO){
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.APPLY_SUPPLIER.getStatus());
                operationLogVo.setObjectId(applyCode);
                BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo,62);
                List<LogData> logDataList = new ArrayList<>();
                if (null != pageList.getDataList() && pageList.getDataList().size() > 0){
                    logDataList = pageList.getDataList();
                }
                applyInfoRespVO.setModelType("供应商集团");
                applyInfoRespVO.setModelTypeCode("2");
                applyInfoRespVO.setStatus(applySupplierDetailDTO.getApplyStatus().toString());
                applySupplierDetailRespVO.setLogDataList(logDataList);
                applySupplierDetailRespVO.setApplyInfoRespVO(applyInfoRespVO);
            }
            if(null != applySupplierInfoRespVO){
                applySupplierInfoRespVO.setId(applySupplierDetailDTO.getApplySupplierId());
                applySupplierDetailRespVO.setApplySupplierInfoRespVO(applySupplierInfoRespVO);
            }

            return applySupplierDetailRespVO;
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelApply(CancelApplySupplierReqVO cancelApplySupplierReqVO){
        try {
            ApplySupplier applySupplier = applySupplierMapper.selectByPrimaryKey(cancelApplySupplierReqVO.getId());

            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormNo(applySupplier.getFormNo());
            // 调用审批流的撤销接口
            WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
            if(workFlowRespVO.getSuccess().equals(true)){
                return 0;
            }else {
                throw  new GroundRuntimeException("撤销失败");
            }
//            ApplySupplier applySupplier = new ApplySupplier();
//            applySupplier.setApplyStatus(StatusTypeCode.CANCEL_APPLY.getStatus());
//            applySupplier.setId(cancelApplySupplierReqVO.getId());
//            int num = ((ApplySupplierService)AopContext.currentProxy()).update(applySupplier);
//            if (num > 0){
//                return num;
//            } else {
//                throw new BizException(ResultCode.CANCEL_ERROR);
//            }
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo1) {
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(vo1);
        ApplySupplier applySupplier = applySupplierDao.selectByFormNO(vo.getFormNo());
        if(Objects.isNull(applySupplier)){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }

        if(applySupplier.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())){
                applySupplier.setApplyStatus(vo.getApplyStatus());
                //通过插入/更新正式数据
                Supplier supplier = new Supplier();
                BeanCopyUtils.copy(applySupplier,supplier);
                Supplier oldSupplier = supplierDao.getSupplierByApplyCode(applySupplier.getApplySupplierCode());
                supplier.setAuditorBy(vo.getApprovalUserName());
                supplier.setAuditorTime(new Date());
                Byte handleTypeCoceStatus;
                String handleTypeCoceName;
                if (null != oldSupplier){
                    supplier.setId(oldSupplier.getId());
                    supplier.setSupplierCode(oldSupplier.getSupplierCode());
                    handleTypeCoceStatus = HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_SUPPLIER.getStatus();
                    handleTypeCoceName = HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_SUPPLIER.getName();
                    supplierMapper.updateByPrimaryKey(supplier);
                } else {
                    //供应商编码
                    EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.SUPPLIER_CODE);
                    supplier.setId(null);
                    supplier.setSupplierCode(String.valueOf(encodingRule.getNumberingValue()+1));
                    supplier.setUpdateBy(applySupplier.getUpdateBy());
                    supplier.setUpdateTime(applySupplier.getUpdateTime());
                    handleTypeCoceStatus = HandleTypeCoce.ADD_APPROVAL_SUCCESS_SUPPLIER.getStatus();
                    handleTypeCoceName = HandleTypeCoce.ADD_APPROVAL_SUCCESS_SUPPLIER.getName();
                    supplierMapper.insert(supplier);
                    //更新编码
                    encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
                }
                //赋值供应商编码
                applySupplier.setSupplierCode(supplier.getSupplierCode());
                supplierCommonService.getInstance(supplier.getSupplierCode(),handleTypeCoceStatus,ObjectTypeCode.SUPPLIER.getStatus(),supplier,handleTypeCoceName);
            }else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
                //驳回, 设置状态
                applySupplier.setApplyStatus(vo.getApplyStatus());
                Supplier oldSupplier = supplierDao.getSupplierByApplyCode(applySupplier.getApplySupplierCode());
                if (null != oldSupplier){
                    Supplier supplier = new Supplier();
                    supplier.setId(oldSupplier.getId());
                    supplier.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                    supplierMapper.updateByPrimaryKeySelective(supplier);
                }
            }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
                //传入的是审批中，继续该流程
                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
            }else if (vo.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber())){
                applySupplier.setApplyStatus(vo.getApplyStatus());
                Supplier oldSupplier = supplierDao.getSupplierByApplyCode(applySupplier.getApplySupplierCode());
                if (null != oldSupplier){
                    Supplier supplier = new Supplier();
                    supplier.setId(oldSupplier.getId());
                    supplier.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                    supplierMapper.updateByPrimaryKeySelective(supplier);
                }
            }else {
                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
            }
        }else{
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        applySupplier.setAuditorBy(vo.getApprovalUserName());
        applySupplier.setAuditorTime(new Date());
        applySupplierMapper.updateByPrimaryKey(applySupplier);
        //判断审核状态，存日志信息
        HandleTypeCoce s = applySupplier.getApplyStatus().intValue()==ApplyStatus.APPROVAL_SUCCESS.getNumber()?HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUCCESS_SUPPLIER:HandleTypeCoce.APPLY_UPDATE_APPROVAL_FAIL_SUPPLIER;
        supplierCommonService.getInstance(applySupplier.getApplySupplierCode(),s.getStatus(),ObjectTypeCode.APPLY_SUPPLIER.getStatus(),applySupplier,s.getName());
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }

    /**
     * 获取formNo
     * @return
     */
    private String getFormNO(){
        return "GYSJT"+new IdSequenceUtils().nextId();
    }
}
