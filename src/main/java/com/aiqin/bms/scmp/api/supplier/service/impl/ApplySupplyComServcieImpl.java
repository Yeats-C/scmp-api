package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.*;
import com.aiqin.bms.scmp.api.supplier.mapper.ApplyDeliveryInformationMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.ApplySupplyCompanyAccountMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.ApplySupplyCompanyMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplyCompanyMapper;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 16:41
 */
@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.APPLY_COMPANY)
public class ApplySupplyComServcieImpl extends SupplierBaseServiceImpl implements ApplySupplyComServcie {
    @Autowired
    private ApplySettlementService applySettlementService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private SupplyCompanyMapper supplyCompanyMapper;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Autowired
    private EncodingRuleService encodingRuleService;
    @Autowired
    private ApplySupplyCompanyDao applySupplyCompanyDao;
    @Autowired
    private SupplyCompanyDao supplyCompanyDao;
    @Autowired
    private ApplySupplyCompanyMapper applySupplyCompanyMapper;
    @Autowired
    private ApplyDeliveryInfoDao applyDeliveryInfoDao;
    @Autowired
    private ApplySupplyCompanyAcctDao applySupplyCompanyAcctDao;
    @Autowired
    private ApplyDeliveryService applyDeliveryService;
    @Autowired
    private ApplySupplyComAcctService applySupplyComAcctService;
    @Autowired
    private ApplyDeliveryInformationMapper applyDeliveryInformationMapper;
    @Autowired
    private ApplySupplierFileService applySupplierFileService;
    @Autowired
    private ApplySupplyCompanyAccountMapper applySupplyCompanyAccountMapper;
    @Autowired
    private ApplySupplierFileDao applySupplierFileDao;
    @Autowired
    private SupplierFileDao supplierFileDao;
    @Autowired
    private DeliveryInfoDao deliveryInfoDao;
    @Autowired
    private ApplyUseTagRecordService applyUseTagRecordService;

    @Autowired
    private TagInfoService tagInfoService;

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer> saveApply(ApplySupplyCompanyReqVO applySupplyCompanyReqVO) {
        int resultNum;
        String companyCode = "";
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("name",applySupplyCompanyReqVO.getApplySupplyName());
        map.put("code",null);
        map.put("companyCode",companyCode);
        int nameCount = applySupplyCompanyDao.checkName(map);
        if (nameCount > 0){
          throw new GroundRuntimeException("名字重复无法添加");
        }
        try {
            //复制对象
            ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO = new ApplySupplyCompanyReqDTO();
            BeanCopyUtils.copy(applySupplyCompanyReqVO,applySupplyCompanyReqDTO);
            //新增申请和日志
            String applySupplyCompanyCode = insertApplyAndOther(applySupplyCompanyReqDTO);
            //审批流程
            applySupplyCompanyReqDTO.setFormNo("GYS"+new IdSequenceUtils().nextId());
            workFlow(applySupplyCompanyReqDTO);
            if(applySupplyCompanyCode != null){
                resultNum=1;
            } else {
                resultNum=0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GroundRuntimeException("添加失败");
        }
        return HttpResponse.success(resultNum);
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int updateApply(ApplySupplyCompanyReqVO applySupplyCompanyReqVO) {
        int num;
        try {
            String companyCode = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("name",applySupplyCompanyReqVO.getApplySupplyName());
            map.put("code",applySupplyCompanyReqVO.getApplySupplyCode());
            map.put("companyCode",companyCode);
            int nameCount = supplyCompanyDao.checkName(map);
            if (nameCount > 0){
                throw new BizException(ResultCode.NAME_REPEAT);
            }
            //复制对象
            ApplySupplyCompany applySupplyCompany = new ApplySupplyCompany();
            BeanCopyUtils.copy(applySupplyCompanyReqVO,applySupplyCompany);
            applySupplyCompany.setSupplyCompanyCode(applySupplyCompanyReqVO.getApplySupplyCode());
            //根据供货单位code获取相应的三张申请表id
            SupplyThreeIdDTO supplyThreeIdDTO = applySupplyCompanyDao.getThreeApplyIdBySupplyCode(applySupplyCompanyReqVO.getApplySupplyCode());
            if (null != supplyThreeIdDTO){
                applySupplyCompany.setId(supplyThreeIdDTO.getApplySupplyId());
            } else {
                throw new BizException(ResultCode.UPDATE_ERROR);
            }
            applySupplyCompany.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
            applySupplyCompany.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
            num = ((ApplySupplyComServcie)AopContext.currentProxy()).update(applySupplyCompany);
            Long id=applySupplyCompany.getId();
            String code=null;
            ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO = new ApplySupplyCompanyReqDTO();
            if(id!=null){
                ApplySupplyCompany applySupplyCompany1= applySupplyCompanyMapper.selectByPrimaryKey(id);
                code=applySupplyCompany1.getApplySupplyCompanyCode();
                BeanCopyUtils.copy(applySupplyCompany1,applySupplyCompanyReqDTO);
                applySupplyCompanyReqDTO.setApplyCode(applySupplyCompany1.getApplySupplyCompanyCode());
            }
            supplierCommonService.getInstance(code, HandleTypeCoce.APPLY_UPDATE_SUPPLY_COMPANY.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(), applySupplyCompany,HandleTypeCoce.APPLY_UPDATE_SUPPLY_COMPANY.getName());
            //结算信息 去掉结算信息
//            if (null != applySupplyCompanyReqVO.getApplySettlementInfoReqVO()){
//                ApplySettlementVO applySettlementVO = new ApplySettlementVO();
//                BeanCopyUtils.copy(applySupplyCompanyReqVO.getApplySettlementInfoReqVO(),applySettlementVO);
//                applySettlementVO.setId(supplyThreeIdDTO.getApplySettlementId());
//                num = applySettlementService.updateApply(applySettlementVO);
//            }
            //发货信息
            applyDeliveryInfoDao.deleteBatch(applySupplyCompanyReqDTO.getApplyCode());
            if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyReqVO.getDeliveryInfoList())){
                try {
                    List<ApplyDeliveryDTO> deliveryDTOS = BeanCopyUtils.copyList(applySupplyCompanyReqVO.getDeliveryInfoList(), ApplyDeliveryDTO.class);
                    deliveryDTOS.forEach(item->{
                        item.setApplySupplyCompanyCode(applySupplyCompanyReqDTO.getApplyCode());
                        item.setApplySupplyCompanyName(applySupplyCompanyReqDTO.getApplySupplyName());
                    });
                    applyDeliveryService.insideSaveBatchApply(deliveryDTOS);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BizException(MessageId.create(Project.SUPPLIER_API, 63,
                            "系统错误"));
                }
            }
            applySupplyCompanyReqDTO.setFormNo("GYS"+new IdSequenceUtils().nextId());
            //增加文件信息
            applySupplierFileDao.deleteApplySupplierFileByApplyCode(applySupplyCompanyReqDTO.getApplyCode());
            List<SupplierFileReqVO> fileReqVOList = applySupplyCompanyReqVO.getFileReqVOList();
            if(CollectionUtils.isNotEmptyCollection(fileReqVOList)){
                fileReqVOList.forEach(item->{
                    item.setApplySupplierCode(applySupplyCompanyReqDTO.getApplyCode());
                    item.setApplySupplierName(applySupplyCompanyReqDTO.getApplySupplyName());
                    item.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                });
                applySupplierFileService.copySaveInfo(fileReqVOList);
            }
            //标签信息
//            applyUseTagRecordService.delete(applySupplyCompanyReqDTO.getApplyCode());
//            SaveUseTagRecordReqVo tagInfo = applySupplyCompanyReqVO.getTagInfo();
//            if (null != tagInfo) {
//                if (CollectionUtils.isNotEmptyCollection(tagInfo.getItemReqVos())) {
//                    List<ApplyUseTagRecord> applyUseTagRecords = Lists.newArrayList();
//                    tagInfo.getItemReqVos().forEach(item->{
//                        ApplyUseTagRecord applyUseTagRecord = new ApplyUseTagRecord();
//                        applyUseTagRecord.setApplyUseObjectCode(applySupplyCompanyReqDTO.getApplyCode());
//                        applyUseTagRecord.setUseObjectName(applySupplyCompanyReqDTO.getApplySupplyName());
//                        applyUseTagRecord.setTagCode(item.getTagCode());
//                        applyUseTagRecord.setTagName(item.getTagName());
//                        applyUseTagRecord.setTagTypeCode(tagInfo.getTagTypeCode());
//                        applyUseTagRecord.setTagTypeName(tagInfo.getTagTypeName());
//                        applyUseTagRecords.add(applyUseTagRecord);
//                    });
//                    applyUseTagRecordService.saveBatch(applyUseTagRecords);
//                }
//            }
            workFlow(applySupplyCompanyReqDTO);
        } catch (Exception e){
            e.printStackTrace();
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int update(ApplySupplyCompany applySupplyCompany) {
        int resultNum= applySupplyCompanyMapper.updateByPrimaryKeySelective(applySupplyCompany);
        if(resultNum > 0){
            return resultNum;
        } else {
            throw new BizException(ResultCode.UPDATE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public Long insert(ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO){
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            applySupplyCompanyReqDTO.setCompanyCode(authToken.getCompanyCode());
            applySupplyCompanyReqDTO.setCompanyName(authToken.getCompanyName());
        }
        Long resultNum= applySupplyCompanyDao.insertApply(applySupplyCompanyReqDTO);
        if(resultNum > 0){
            return applySupplyCompanyReqDTO.getId();
        } else {
            throw new BizException(ResultCode.ADD_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workFlow(ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO) {
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormUrl(workFlowBaseUrl.applySupplierUrl+"?applyType="+applySupplyCompanyReqDTO.getApplyType()+"&applyCode="+applySupplyCompanyReqDTO.getApplyCode()+"&id="+applySupplyCompanyReqDTO.getId()+"&itemCode=1"+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setFormNo(applySupplyCompanyReqDTO.getFormNo());
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+ WorkFlow.APPLY_COMPANY.getNum());
            workFlowVO.setTitle(applySupplyCompanyReqDTO.getApplySupplyName()+"申请");
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_COMPANY);
            if(workFlowRespVO.getSuccess()){
                ApplySupplyCompany applySupplyCompany = new ApplySupplyCompany();
                applySupplyCompany.setId(applySupplyCompanyReqDTO.getId());
                //状态变为审核中
                applySupplyCompany.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
                applySupplyCompany.setFormNo(applySupplyCompanyReqDTO.getFormNo());
                int i = applySupplyCompanyMapper.updateByPrimaryKeySelective(applySupplyCompany);

                SupplyCompany oldComByApplyCode = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompanyReqDTO.getApplyCode());
                if(null != oldComByApplyCode){
                    SupplyCompany supplyCompany = new SupplyCompany();
                    supplyCompany.setId(oldComByApplyCode.getId());
                    supplyCompany.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
                    supplyCompanyMapper.updateByPrimaryKeySelective(supplyCompany);
                }
                List<ApplyDeliveryInformation> oldApplyDeliveryInfo = applyDeliveryInfoDao.getApplyDeliveryInfo(applySupplyCompanyReqDTO.getApplyCode());
                if (CollectionUtils.isNotEmptyCollection(oldApplyDeliveryInfo)){
                    oldApplyDeliveryInfo.forEach(item->{
                        item.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
                    });
                    applyDeliveryInfoDao.updateBatch(oldApplyDeliveryInfo);
                }
                //去掉结算信息 2019-04-22
//                ApplySettlementInformation oldApplySetInfo = applySettlementInfoDao.getApplySettInfo(applySupplyCompanyReqDTO.getApplyCode());
//                if (null != oldApplySetInfo){
//                    ApplySettlementInformation newApplySetInfo = new ApplySettlementInformation();
//                    newApplySetInfo.setId(oldApplySetInfo.getId());
//                    newApplySetInfo.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
//                    i = applySettlementInformationMapper.updateByPrimaryKeySelective(newApplySetInfo);
//                }
                ApplySupplyCompanyAccount oldApplySupplyComAcct = applySupplyCompanyAcctDao.getApplySupplyComAcct(applySupplyCompanyReqDTO.getApplyCode());
                if(null != oldApplySupplyComAcct) {
                    ApplySupplyCompanyAccount newApplySupplyComAcct = new ApplySupplyCompanyAccount();
                    newApplySupplyComAcct.setId(oldApplySupplyComAcct.getId());
                    newApplySupplyComAcct.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
                    applySupplyCompanyAccountMapper.updateByPrimaryKeySelective(newApplySupplyComAcct);
                }

                if(i<=0){
                    throw new GroundRuntimeException("审核状态修改失败");
                }
                //存日志
                supplierCommonService.getInstance(applySupplyCompanyReqDTO.getApplyCode()+"", HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUPPLY_COMPANY.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),applySupplyCompanyReqDTO,HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUPPLY_COMPANY.getName());
            }else {
                //存调用失败的日志
                String msg = workFlowRespVO.getMsg();
                throw new GroundRuntimeException(msg);
            }
        } catch (Exception e) {
            //存失败日志
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    public BasePage<ApplySupplyComListRespVO> getApplyList(QueryApplySupplyComReqVO queryApplySupplyComReqVO) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                queryApplySupplyComReqVO.setCompanyCode(authToken.getCompanyCode());
                queryApplySupplyComReqVO.setApplyBy(authToken.getPersonName());
            }
            PageHelper.startPage(queryApplySupplyComReqVO.getPageNo(), queryApplySupplyComReqVO.getPageSize());
            List<ApplySupplyComListRespVO> applySupplierResps = applySupplyCompanyDao.getApplyList(queryApplySupplyComReqVO);
            return PageUtil.getPageList(queryApplySupplyComReqVO.getPageNo(),applySupplierResps);
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String insideSaveApply(ApplySupplyComDTO applySupplyComDTO) {
        String applySupplyCompanyCode;
        try {
            //复制对象
            ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO = new ApplySupplyCompanyReqDTO();
            applySupplyComDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
            BeanCopyUtils.copy(applySupplyComDTO,applySupplyCompanyReqDTO);
            //新增申请和日志
            applySupplyCompanyCode  = insertApplyAndOther(applySupplyCompanyReqDTO);
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
        return applySupplyCompanyCode;
    }

    @Override
    public ApplySupplyComDetailRespVO getApplySupplyComDetail(String applyCode) {
        try {
            ApplySupplyComDetailRespVO applySupplyComDetailRespVO = new ApplySupplyComDetailRespVO();
            ApplyInfoRespVO applyInfoRespVO = new ApplyInfoRespVO();
            ApplySupplyComInfoRespVO applySupplyComInfoRespVO = new ApplySupplyComInfoRespVO();
            ApplySupplyComAcctInfoRespVO applySupplyComAcctInfoRespVO = new ApplySupplyComAcctInfoRespVO();

            ApplySupplyComDetailDTO applySupplyComDetailDTO = applySupplyCompanyDao.getApplySupplyComDetail(applyCode);
            BeanCopyUtils.copy(applySupplyComDetailDTO,applyInfoRespVO);
            BeanCopyUtils.copy(applySupplyComDetailDTO,applySupplyComInfoRespVO);
            if (null != applyInfoRespVO){
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus());
                operationLogVo.setObjectId(applyCode);
                BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo,62);
                List<LogData> logDataList = new ArrayList<>();
                if (null != pageList.getDataList() && pageList.getDataList().size() > 0){
                    logDataList = pageList.getDataList();
                }
                applyInfoRespVO.setModelType("供应商");
                applyInfoRespVO.setModelTypeCode("1");
                applyInfoRespVO.setStatus(applySupplyComDetailDTO.getApplyStatus().toString());
                applySupplyComDetailRespVO.setLogDataList(logDataList);
                applySupplyComDetailRespVO.setApplyInfoRespVO(applyInfoRespVO);
            }
            if (null != applySupplyComInfoRespVO) {
                applySupplyComDetailRespVO.setApplySupplyComInfoRespVO(applySupplyComInfoRespVO);
            }
            if(StatusTypeCode.ADD_ACCOUNT.getStatus().equals(applySupplyComDetailDTO.getAddAccount())){
                //查询账户信息
                ApplySupplyCompanyAccount applySupplyComAcct = applySupplyCompanyAcctDao.getApplySupplyComAcct(applyCode);
                if(null != applySupplyComAcct){
                    BeanCopyUtils.copy(applySupplyComAcct,applySupplyComAcctInfoRespVO);
                    if(null != applySupplyComAcctInfoRespVO){
                        applySupplyComAcctInfoRespVO.setAcctMaxPaymentAmount(applySupplyComAcct.getMaxPaymentAmount());
                        applySupplyComDetailRespVO.setApplySupplyComAcctInfoRespVO(applySupplyComAcctInfoRespVO);
                    }
                }
                applySupplyComDetailRespVO.setShowAccount(StatusTypeCode.SHOW_ACCOUNT.getStatus());
            } else {
                applySupplyComDetailRespVO.setShowAccount(StatusTypeCode.NOT_SHOW_ACCOUNT.getStatus());
            }
            //查询文件信息
            List<ApplySupplierFile> applySupplierFiles  = applySupplierFileDao.getApplySupplierFile(applyCode);
            applySupplyComDetailRespVO.setApplySupplierFileList(applySupplierFiles);
            //根据供应商申请编码获取发货/退后申请信息
            List<ApplyDeliveryInformation> applyDeliveryInfo = applyDeliveryInfoDao.getApplyDeliveryInfo(applyCode);
            List<ApplyDeliveryInfoRespVO> applyDeliveryInfoRespVO = Lists.newArrayList();
            applyDeliveryInfoRespVO = BeanCopyUtils.copyList(applyDeliveryInfo,ApplyDeliveryInfoRespVO.class);
            applySupplyComDetailRespVO.setApplyDeliveryInfoRespVO(applyDeliveryInfoRespVO);
            //获取标签信息
//            List<ApplyUseTagRecord> applyUseTagRecordList = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCode(applyCode);
//            applySupplyComDetailRespVO.setApplyUseTagRecords(applyUseTagRecordList);
            return applySupplyComDetailRespVO;
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int cancelApply(CancelApplySupplyComReqVO cancelApplySupplyComReqVO) {
        try {


            ApplySupplyCompany applySupplyCompany2 = applySupplyCompanyMapper.selectByPrimaryKey(cancelApplySupplyComReqVO.getId());
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormNo(applySupplyCompany2.getFormNo());
            // 调用审批流的撤销接口
            WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
            if(workFlowRespVO.getSuccess().equals(true)){
                return 0;
            }else {
                throw  new GroundRuntimeException("撤销失败");
            }

//            SupplyThreeIdDTO supplyThreeIdDTO = applySupplyCompanyDao.getApplyIdsById(cancelApplySupplyComReqVO.getId());
//            if(null == supplyThreeIdDTO){
//                throw new GroundRuntimeException("没有找到申请信息");
//            }
//            if (null == cancelApplySupplyComReqVO.getDeliveryInfoId() || null == cancelApplySupplyComReqVO.getSettlementInfoId()){
//                if (null != supplyThreeIdDTO){
//                    if (null != supplyThreeIdDTO.getApplySettlementId()){
//                        cancelApplySupplyComReqVO.setSettlementInfoId(supplyThreeIdDTO.getApplySettlementId());
//                    }
//                    if (null != supplyThreeIdDTO.getApplyDeliveryId()){
//                        cancelApplySupplyComReqVO.setDeliveryInfoId(supplyThreeIdDTO.getApplyDeliveryId());
//                    }
//                }
//            }
//            ApplySupplyCompany applySupplyCompany = new ApplySupplyCompany();
//            applySupplyCompany.setId(cancelApplySupplyComReqVO.getId());
//            applySupplyCompany.setApplyStatus(StatusTypeCode.CANCEL_APPLY.getStatus());
//            //根据id修改供货单位申请状态为撤销
//            int num = ((ApplySupplyComServcie)AopContext.currentProxy()).update(applySupplyCompany);
//            if (null != cancelApplySupplyComReqVO.getId()){
//                ApplySupplyCompany applySupplyCompany1 = applySupplyCompany2;
//                supplierCommonService.getInstance(applySupplyCompany1.getApplySupplyCompanyCode(), HandleTypeCoce.APPLY_UPDATE_REVOKE_SUPPLY_COMPANY.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(), applySupplyCompany1,HandleTypeCoce.APPLY_UPDATE_REVOKE_SUPPLY_COMPANY.getName());
//            }
//            //根据id修改供货单位结算信息申请状态为撤销
//            applySettlementService.cancelApply(cancelApplySupplyComReqVO.getSettlementInfoId());
//            //根据id修改供货单位收货信息申请状态为撤销
//            applyDeliveryService.cancelApply(cancelApplySupplyComReqVO.getDeliveryInfoId());
//            if (num > 0){
//                return  num;
//            } else {
//                throw new BizException(ResultCode.CANCEL_ERROR);
//            }
        } catch (GroundRuntimeException e){
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Transactional(rollbackFor = GroundRuntimeException.class)
    public String insertApplyAndOther(ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO){
        //此方法返回供货单位申请code，方便后面的结算及收货信息新增
        String applySupplyCompanyCode = null;
        //供货单位申请编码
        EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.APPLY_SUPPLY_COM_CODE);
        applySupplyCompanyReqDTO.setApplyCode(String.valueOf(encodingRule.getNumberingValue()+1));
        applySupplyCompanyReqDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
        applySupplyCompanyReqDTO.setEnable(StatusTypeCode.EN_ABLE.getStatus());
        //新增供货单位申请
        Long resultNum =((ApplySupplyComServcie)AopContext.currentProxy()).insert(applySupplyCompanyReqDTO);
        supplierCommonService.getInstance(applySupplyCompanyReqDTO.getApplyCode(), HandleTypeCoce.APPLY_ADD_SUPPLY_COMPANY.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(), applySupplyCompanyReqDTO,HandleTypeCoce.APPLY_ADD_SUPPLY_COMPANY.getName());
        //修改编码
        encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        //结算信息 去掉结算结算 2019-04-22
//        ApplySettlementDTO applySettlementDTO = new ApplySettlementDTO();
//        if (null != applySupplyCompanyReqDTO.getApplySettlementInfoReqVO()){
//            BeanCopyUtils.copy(applySupplyCompanyReqDTO.getApplySettlementInfoReqVO(), applySettlementDTO);
//            applySettlementDTO.setApplySupplyCompanyCode(applySupplyCompanyReqDTO.getApplyCode());
//            applySettlementDTO.setApplySupplyCompanyName(applySupplyCompanyReqDTO.getApplySupplyName());
//            resultNum = applySettlementService.insideSaveApply(applySettlementDTO);
//        }
        //发货信息
        if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyReqDTO.getDeliveryInfoList())){
            try {
                List<ApplyDeliveryDTO> deliveryDTOS = BeanCopyUtils.copyList(applySupplyCompanyReqDTO.getDeliveryInfoList(), ApplyDeliveryDTO.class);
                deliveryDTOS.forEach(item->{
                    item.setApplySupplyCompanyCode(applySupplyCompanyReqDTO.getApplyCode());
                    item.setApplySupplyCompanyName(applySupplyCompanyReqDTO.getApplySupplyName());
                });
                resultNum = applyDeliveryService.insideSaveBatchApply(deliveryDTOS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(MessageId.create(Project.SUPPLIER_API, 63,
                        "系统错误"));
            }
        }
        //增加文件信息
        List<SupplierFileReqVO> fileReqVOList = applySupplyCompanyReqDTO.getFileReqVOList();
        if(CollectionUtils.isNotEmptyCollection(fileReqVOList)){
            fileReqVOList.forEach(item->{
                item.setApplySupplierCode(applySupplyCompanyReqDTO.getApplyCode());
                item.setApplySupplierName(applySupplyCompanyReqDTO.getApplySupplyName());
                item.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
            });
            applySupplierFileService.copySaveInfo(fileReqVOList);
        }
        //标签信息
//        SaveUseTagRecordReqVo tagInfos = applySupplyCompanyReqDTO.getTagInfo();
//        if (null != tagInfos) {
//            if (CollectionUtils.isNotEmptyCollection(tagInfos.getItemReqVos())) {
//                List<ApplyUseTagRecord> applyUseTagRecords = Lists.newArrayList();
//                tagInfos.getItemReqVos().forEach(item->{
//                    ApplyUseTagRecord applyUseTagRecord = new ApplyUseTagRecord();
//                    applyUseTagRecord.setTagTypeCode(tagInfos.getTagTypeCode());
//                    applyUseTagRecord.setTagTypeName(tagInfos.getTagTypeName());
//                    applyUseTagRecord.setApplyUseObjectCode(applySupplyCompanyReqDTO.getApplyCode());
//                    applyUseTagRecord.setUseObjectName(applySupplyCompanyReqDTO.getApplySupplyName());
//                    applyUseTagRecord.setTagCode(item.getTagCode());
//                    applyUseTagRecord.setTagName(item.getTagName());
//                    applyUseTagRecords.add(applyUseTagRecord);
//                });
//                applyUseTagRecordService.saveBatch(applyUseTagRecords);
//            }
//        }
        //判断是否需要新增供货单位账户申请
        //判断是否需要新增账户信息
        Boolean addAccount = null != applySupplyCompanyReqDTO.getAddAccount() && applySupplyCompanyReqDTO.getAddAccount().equals(StatusTypeCode.ADD_ACCOUNT.getStatus());
        if(addAccount){
            ApplySupplyCompanyAcctReqVO applySupplyCompanyAcctReqVO = applySupplyCompanyReqDTO.getApplySupplyCompanyAccountReq();
            ApplySupplyComAcctDTO applySupplyComAcctDTO = new ApplySupplyComAcctDTO();
            BeanCopyUtils.copy(applySupplyCompanyAcctReqVO, applySupplyComAcctDTO);
            applySupplyComAcctDTO.setApplySupplyCompanyCode(applySupplyCompanyReqDTO.getApplyCode());
            applySupplyComAcctDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
            applySupplyComAcctDTO.setApplyShow((byte)1);
            applySupplyComAcctService.insideSaveApply(applySupplyComAcctDTO);
        }
        if(resultNum > 0){
            applySupplyCompanyCode = applySupplyCompanyReqDTO.getApplyCode();
        }
        return  applySupplyCompanyCode;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insideWorkFlowCallback(ApplySupplyCompany applySupplyCompany, WorkFlowCallbackVO vo) {
       // ApplySettlementInformation applySettlementInformation = applySettlementInfoDao.getApplySettInfo(applySupplyCompany.getApplySupplyCompanyCode());
       // List<ApplyDeliveryInformation> applyDeliveryInformations = applyDeliveryInfoDao.getApplyDeliveryInfo(applySupplyCompany.getApplySupplyCompanyCode());
        ApplySupplyCompanyAccount applySupplyCompanyAccount = applySupplyCompanyAcctDao.getApplySupplyComAcct(applySupplyCompany.getApplySupplyCompanyCode());
        if(applySupplyCompany.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            applySupplyCompany.setApplyStatus(vo.getApplyStatus());
            if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())){
                SupplyCompany oldSupplyCompany = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
                //通过插入正式数据
                SupplyCompany supplyCompany = new SupplyCompany();
                BeanCopyUtils.copy(applySupplyCompany,supplyCompany);
                supplyCompany.setAuditorBy(vo.getApprovalUserName());
                supplyCompany.setAuditorTime(new Date());
                Byte handleTypeCoceStatus;
                String handleTypeCoceName;
                if (null != oldSupplyCompany){
                    //删除原有的发货/退货信息
                    deliveryInfoDao.deleteDeliveryInfoBySupplyCompanyCode(oldSupplyCompany.getSupplyCode());
                    //删除原有的附件信息
                    supplierFileDao.deleteFile(oldSupplyCompany.getSupplyCode());
                    supplyCompany.setId(oldSupplyCompany.getId());
                    supplyCompany.setSupplyName(applySupplyCompany.getApplySupplyName());
                    supplyCompany.setApplySupplyCompanyCode(applySupplyCompany.getApplySupplyCompanyCode());
                    supplyCompany.setSupplyAbbreviation(applySupplyCompany.getApplyAbbreviation());
                    supplyCompany.setSupplyCode(oldSupplyCompany.getSupplyCode());
                    supplyCompany.setSupplyType(applySupplyCompany.getApplySupplyType());
                    handleTypeCoceStatus = HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY.getStatus();
                    handleTypeCoceName = HandleTypeCoce.UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY.getName();
                    supplyCompanyMapper.updateByPrimaryKey(supplyCompany);
                } else {
                    EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.SUPPLY_COM_CODE);
                    supplyCompany.setId(null);
                    supplyCompany.setSupplyName(applySupplyCompany.getApplySupplyName());
                    supplyCompany.setSupplyType(applySupplyCompany.getApplySupplyType());
                    supplyCompany.setSupplyAbbreviation(applySupplyCompany.getApplyAbbreviation());
                    supplyCompany.setSupplyCode(String.valueOf(encodingRule.getNumberingValue()));
                    handleTypeCoceStatus = HandleTypeCoce.ADD_APPROVAL_SUCCESS_SUPPLY_COMPANY.getStatus();
                    handleTypeCoceName = HandleTypeCoce.ADD_APPROVAL_SUCCESS_SUPPLY_COMPANY.getName();
                    supplyCompanyMapper.insert(supplyCompany);
                    encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
                }
                applySupplyCompany.setSupplyCompanyCode(supplyCompany.getSupplyCode());
//                if (applySettlementInformation!=null){
//                    applySettlementInformation.setSupplyCompanyCode(supplyCompany.getSupplyCode());
//                    applySettlementInformation.setSupplyCompanyName(supplyCompany.getSupplyName());
//                }
                if (applySupplyCompanyAccount != null){
                    applySupplyCompanyAccount.setSupplyCompanyCode(supplyCompany.getSupplyCode());
                    applySupplyCompanyAccount.setSupplyCompanyName(supplyCompany.getSupplyName());
                }
                //发货/退货信息
                //插入正式数据

                List<ApplyDeliveryInformation> applyDeliveryInfos = applyDeliveryInfoDao.getApplyDeliveryInfo(applySupplyCompany.getApplySupplyCompanyCode());
                if (CollectionUtils.isNotEmptyCollection(applyDeliveryInfos)) {
                    //更新申请表信息
                    applyDeliveryInfos.forEach(item->{
                        item.setAuditorBy(vo.getApprovalUserName());
                        item.setAuditorTime(new Date());
                        item.setApplyStatus(vo.getApplyStatus());
                        item.setApplyDeliveryInformationCode(item.getApplyCode());
                    });
                    applyDeliveryInfoDao.updateBatch(applyDeliveryInfos);
                    List<DeliveryInformation> deliveryInformations = Lists.newArrayList();
                    try {
                        deliveryInformations = BeanCopyUtils.copyList(applyDeliveryInfos, DeliveryInformation.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(log.isDebugEnabled()) {
                            log.info("供应商发货/退货信息转化失败");
                        }
                    }
                    if (CollectionUtils.isNotEmptyCollection(deliveryInformations)) {
                        deliveryInformations.forEach(item->{
                            item.setSupplyCompanyCode(supplyCompany.getSupplyCode());
                            item.setSupplyCompanyName(supplyCompany.getSupplyName());
                        });
                    }
                    deliveryInfoDao.insertBatch(deliveryInformations);
                }

                //附件信息
                List<ApplySupplierFile> applySupplierFiles  = applySupplierFileDao.getApplySupplierFile(applySupplyCompany.getApplySupplyCompanyCode());
                if(CollectionUtils.isNotEmptyCollection(applySupplierFiles)){
                    List<SupplierFile> supplierFiles = null;
                    try {
                        supplierFiles = BeanCopyUtils.copyList(applySupplierFiles, SupplierFile.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(log.isDebugEnabled()) {
                            log.info("供应商文件信息转化失败");
                        }
                    }
                    if(CollectionUtils.isNotEmptyCollection(supplierFiles)){
                        supplierFiles.forEach(item->{
                            item.setSupplierCode(supplyCompany.getSupplyCode());
                            item.setSupplierName(supplyCompany.getSupplyName());
                        });
                        supplierFileDao.insertFileList(supplierFiles);
                    }
                }
                //标签信息
//                List<ApplyUseTagRecord> applyUseTagRecords = applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCode(applySupplyCompany.getApplySupplyCompanyCode());
//                List<SaveUseTagRecordReqVo> records = Lists.newArrayList();
//                SaveUseTagRecordReqVo recordReqVo = new SaveUseTagRecordReqVo();
//                recordReqVo.setTagTypeCode(TagTypeCode.SUPPLIER.getStatus());
//                recordReqVo.setTagTypeName(TagTypeCode.SUPPLIER.getName());
//                recordReqVo.setUseObjectCode(supplyCompany.getSupplyCode());
//                recordReqVo.setUseObjectName(supplyCompany.getSupplyName());
//                List<SaveUseTagRecordItemReqVo> itemReqVos = Lists.newArrayList();
//                if (CollectionUtils.isNotEmptyCollection(applyUseTagRecords)) {
//                    applyUseTagRecords.forEach(item->{
//                        item.setUseObjectCode(supplyCompany.getSupplyCode());
//                        SaveUseTagRecordItemReqVo itemReqVo = new SaveUseTagRecordItemReqVo();
//                        itemReqVo.setTagCode(item.getTagCode());
//                        itemReqVo.setTagName(item.getTagName());
//                        itemReqVos.add(itemReqVo);
//                    });
//                    applyUseTagRecordService.updateBatch(applyUseTagRecords);
//
//                }
//                recordReqVo.setItemReqVos(itemReqVos);
//                records.add(recordReqVo);
//                tagInfoService.saveRecordList(records);

                supplierCommonService.getInstance(supplyCompany.getSupplyCode(),handleTypeCoceStatus,ObjectTypeCode.SUPPLY_COMPANY.getStatus(),supplyCompany,handleTypeCoceName);
            }else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
                //驳回, 设置状态
                applySupplyCompany.setApplyStatus(vo.getApplyStatus());
                SupplyCompany oldSupplyCompany = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
                if(null != oldSupplyCompany){
                    SupplyCompany supplyCompany = new SupplyCompany();
                    supplyCompany.setId(oldSupplyCompany.getId());
                    supplyCompany.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                    supplyCompanyMapper.updateByPrimaryKeySelective(supplyCompany);
                }
            }else if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
                //传入的是审批中，继续该流程
                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
            }else if (vo.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber())){
                applySupplyCompany.setApplyStatus(vo.getApplyStatus());
                SupplyCompany oldSupplyCompany = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
                if(null != oldSupplyCompany){
                    SupplyCompany supplyCompany = new SupplyCompany();
                    supplyCompany.setId(oldSupplyCompany.getId());
                    supplyCompany.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                    supplyCompanyMapper.updateByPrimaryKeySelective(supplyCompany);
                }
            }else {
                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
            }
        }else {
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
//        String result = applySettlementService.insideWorkFlowCallback(applySettlementInformation,vo);
//        if (result.equals(HandlingExceptionCode.FLOW_CALL_BACK_FALSE)){
//            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
//        }
//        String result = applyDeliveryService.insideWorkFlowCallback(applyDeliveryInformations,vo);
//        if (result.equals(HandlingExceptionCode.FLOW_CALL_BACK_FALSE)){
//            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
//        }
        if (null != applySupplyCompanyAccount){
            applySupplyCompanyAccount.setCompanyName(applySupplyCompany.getCompanyName());
            applySupplyCompanyAccount.setCompanyCode(applySupplyCompany.getCompanyCode());
            String result = applySupplyComAcctService.insideWorkFlowCallback(applySupplyCompanyAccount,vo);
            if (result.equals(HandlingExceptionCode.FLOW_CALL_BACK_FALSE)){
                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
            }
        }
        applySupplyCompany.setAuditorBy(vo.getApprovalUserName());
        applySupplyCompany.setAuditorTime(new Date());
        applySupplyCompanyMapper.updateByPrimaryKey(applySupplyCompany);
        //判断审核状态，存日志信息
        HandleTypeCoce s = applySupplyCompany.getApplyStatus().intValue()==ApplyStatus.APPROVAL_SUCCESS.getNumber()?HandleTypeCoce.APPLY_UPDATE_APPROVAL_SUCCESS_SUPPLY_COMPANY:HandleTypeCoce.APPLY_UPDATE_APPROVAL_FAIL_SUPPLY_COMPANY;
        supplierCommonService.getInstance(applySupplyCompany.getApplySupplyCompanyCode(),s.getStatus(),ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),applySupplyCompany,s.getName());
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }

    @Override
    public List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            querySupplierReqVO.setCompanyCode(authToken.getCompanyCode());
            querySupplierReqVO.setApplyBy(authToken.getPersonName());
        }
        return applySupplyCompanyDao.queryApplyList(querySupplierReqVO);
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public String workFlowCallback(WorkFlowCallbackVO workFlowCallbackVO){
        //通过编码查询实体
        WorkFlowCallbackVO vo = updateSupStatus(workFlowCallbackVO);
        ApplySupplyCompany applySupplyCompany = applySupplyCompanyDao.getApplySupplyComByFormNo(vo.getFormNo());
        if(Objects.isNull(applySupplyCompany)){
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        return insideWorkFlowCallback(applySupplyCompany,vo);
    }

}
