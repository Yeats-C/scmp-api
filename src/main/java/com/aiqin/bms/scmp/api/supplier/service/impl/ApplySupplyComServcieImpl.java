package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.*;
import com.aiqin.bms.scmp.api.supplier.domain.SpecialArea;
import com.aiqin.bms.scmp.api.supplier.domain.excel.AreaInfo;
import com.aiqin.bms.scmp.api.supplier.domain.excel.CheckAreaEnum;
import com.aiqin.bms.scmp.api.supplier.domain.excel.im.SupplierImportNew;
import com.aiqin.bms.scmp.api.supplier.domain.excel.im.SupplierImportUpdate;
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
import com.aiqin.bms.scmp.api.util.excel.Constraint;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 16:41
 */
@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.APPLY_COMPANY)
public class ApplySupplyComServcieImpl extends BaseServiceImpl implements ApplySupplyComServcie, WorkFlowHelper {
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
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private AreaBasicInfoService areaBasicInfoService;
    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;

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
        map.put("code",applySupplyCompanyReqVO.getApplySupplyCode());
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
            if(StringUtils.isNotBlank(applySupplyCompanyReqVO.getApplySupplyCode())){
                applySupplyCompanyReqDTO.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
            } else {
                applySupplyCompanyReqDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                applySupplyCompanyReqDTO.setEnable(StatusTypeCode.EN_ABLE.getStatus());
            }
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
//        return saveApply(applySupplyCompanyReqVO).getData();
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
                applySupplyCompanyReqDTO.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
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
            String title = "修改";
            if(Objects.equals(StatusTypeCode.ADD_APPLY.getStatus(),applySupplyCompanyReqDTO.getApplyStatus())){
                title = "新增";
            }
            workFlowVO.setTitle(title+applySupplyCompanyReqDTO.getApplySupplyName()+"申请");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("auditPersonId",applySupplyCompanyReqDTO.getDirectSupervisorCode());
            workFlowVO.setVariables(jsonObject.toString());
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
            List<ApplyDeliveryInfoRespVO> applyDeliveryInfoRespVO =  BeanCopyUtils.copyList(applyDeliveryInfo,ApplyDeliveryInfoRespVO.class);
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
        applySupplyCompanyReqDTO.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
        applySupplyCompanyReqDTO.setApplyCode(String.valueOf(encodingRule.getNumberingValue()+1));
        //新增供货单位申请
        Long resultNum =((ApplySupplyComServcie)AopContext.currentProxy()).insert(applySupplyCompanyReqDTO);
        supplierCommonService.getInstance(applySupplyCompanyReqDTO.getApplyCode(), HandleTypeCoce.APPLY_ADD_SUPPLY_COMPANY.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(), applySupplyCompanyReqDTO,HandleTypeCoce.APPLY_ADD_SUPPLY_COMPANY.getName());
        //修改编码
        encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
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
                item.setApplyType(applySupplyCompanyReqDTO.getApplyType());
            });
            applySupplierFileService.copySaveInfo(fileReqVOList);
        }
        //判断是否需要新增供货单位账户申请
        //判断是否需要新增账户信息
        Boolean addAccount = null != applySupplyCompanyReqDTO.getAddAccount() && applySupplyCompanyReqDTO.getAddAccount().equals(StatusTypeCode.ADD_ACCOUNT.getStatus());
        if(addAccount && Objects.equals(StatusTypeCode.ADD_APPLY.getStatus(),applySupplyCompanyReqDTO.getApplyType())){
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
    public List<ApplySupplyCompanyReqVO> dealImport(MultipartFile file) {
        try {
            List<SupplierImportNew> supplierImportNews = ExcelUtil.readExcel(file, SupplierImportNew.class, 1, 0);
            //校验
            dataValidation(supplierImportNews);
            supplierImportNews.remove(0);
            List<String> companyNameList = Lists.newArrayList();
            List<String> supplierNameList = Lists.newArrayList();
            for (SupplierImportNew o : supplierImportNews) {
                companyNameList.add(o.getApplySupplyName());
                supplierNameList.add(o.getSupplierName());
            }
            Map<String, AreaInfo> areaTree = getAreaTree();
            Map<String, SupplyCompany> supplyCompanies = supplyCompanyDao.selectByCompanyNameList(companyNameList, getUser().getCompanyCode());
            List<String> dicName = Lists.newArrayList();
            dicName.add("供应商类型");
            dicName.add("发送至");
            Map<String, SupplierDictionaryInfo> dictionaryInfoList = supplierDictionaryInfoDao.selectByName(dicName, getUser().getCompanyCode());
            Map<String, Supplier> supplierList = null;
            if(CollectionUtils.isNotEmptyCollection(supplierNameList)){
                supplierList = supplierDao.selectByNameList(supplierNameList,getUser().getCompanyCode());
            }
            List<ApplySupplyCompanyReqVO> applyList = Lists.newArrayList();
            for (int i = 0; i < supplierImportNews.size(); i++) {
                SupplierImportNew supplierImportNew = supplierImportNews.get(i);
                StringBuilder sb = new StringBuilder();
                //判空
                validNull(supplierImportNew, sb);
                if (StringUtils.isNotBlank(sb.toString())) {
                    throw new BizException(MessageId.create(Project.PRODUCT_API,62,"第"+(i+1)+"数据不完整"+sb.toString()+",请将该行填写完整或删除该行！"));
                }
                //验证拼装
                ApplySupplyCompanyReqVO reqVO = validAndFullUp(supplierImportNew,sb,areaTree,supplyCompanies,supplierList,dictionaryInfoList);
                applyList.add(reqVO);
            }
            return applyList;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_EXCEPTION);
        }
    }
    @Override
    public List<ApplySupplyCompanyReqVO> dealImport2(MultipartFile file) {
        try {
            List<SupplierImportUpdate> supplierImportNews = ExcelUtil.readExcel(file, SupplierImportUpdate.class, 1, 0);
            //校验
            dataValidation2(supplierImportNews);
            supplierImportNews.remove(0);
            List<String> companyNameList = Lists.newArrayList();
            List<String> supplierNameList = Lists.newArrayList();
            List<String> companyCodeList = Lists.newArrayList();
            for (SupplierImportUpdate o : supplierImportNews) {
                companyNameList.add(o.getApplySupplyName());
                supplierNameList.add(o.getSupplierName());
                companyCodeList.add(o.getApplySupplyCode());
            }

            Map<String, AreaInfo> areaTree = getAreaTree();
            Map<String, SupplyCompany> supplyCompanies = supplyCompanyDao.selectByCompanyNameList(companyNameList, getUser().getCompanyCode());
            Map<String, SupplyCompany> codeList = supplyCompanyDao.selectByCompanyCodeList(companyCodeList, getUser().getCompanyCode());
            List<String> dicName = Lists.newArrayList();
            dicName.add("供应商类型");
            dicName.add("发送至");
            Map<String, SupplierDictionaryInfo> dictionaryInfoList = supplierDictionaryInfoDao.selectByName(dicName, getUser().getCompanyCode());
            Map<String, Supplier> supplierList = null;
            if(CollectionUtils.isNotEmptyCollection(supplierNameList)){
                supplierList = supplierDao.selectByNameList(supplierNameList,getUser().getCompanyCode());
            }
            List<ApplySupplyCompanyReqVO> applyList = Lists.newArrayList();
            for (int i = 0; i < supplierImportNews.size(); i++) {
                SupplierImportUpdate supplierImportNew = supplierImportNews.get(i);
                StringBuilder sb = new StringBuilder();
                //判空
                validNull2(supplierImportNew, sb);
                if (StringUtils.isNotBlank(sb.toString())) {
                    throw new BizException(MessageId.create(Project.PRODUCT_API,62,"第"+(i+1)+"数据不完整"+sb.toString()+",请将该行填写完整或删除该行！"));
                }
                //验证拼装
                ApplySupplyCompanyReqVO reqVO = validAndFullUp2(supplierImportNew,sb,areaTree,supplyCompanies,supplierList,dictionaryInfoList,codeList);
                applyList.add(reqVO);
            }
            return applyList;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_EXCEPTION);
        }
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
    //导入的校验
    private ApplySupplyCompanyReqVO validAndFullUp(SupplierImportNew supplierImportNew, StringBuilder sb, Map<String, AreaInfo> areaTree, Map<String, SupplyCompany> supplyCompanies, Map<String, Supplier> supplierList, Map<String, SupplierDictionaryInfo> dictionaryInfoList) {
        ApplySupplyCompanyReqVO reqVO = new ApplySupplyCompanyReqVO();
        List<String> name = Lists.newArrayList();
        SupplyCompany supplyCompany1 = supplyCompanies.get(supplierImportNew.getApplySupplyName().trim());
        if(Objects.nonNull(supplyCompany1)||name.contains(supplierImportNew.getApplySupplyName())){
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append(",");
            }
            sb.append("供应商名称重复");
        }
        name.add(supplierImportNew.getApplySupplyName().trim());
        reqVO.setApplySupplyName(supplierImportNew.getApplySupplyName().trim());
        if(StringUtils.isNotBlank(supplierImportNew.getSupplierName())){
            Supplier supplier = supplierList.get(supplierImportNew.getSupplierName().trim());
            if(Objects.isNull(supplier)){
                sb.append(",").append("未找到对应名称的供应商集团");
            }else {
                reqVO.setSupplierCode(supplier.getSupplierCode());
            }
            reqVO.setSupplierName(supplierImportNew.getSupplierName().trim());
        }
        SupplierDictionaryInfo info = dictionaryInfoList.get(supplierImportNew.getApplySupplyType().trim());
        if(Objects.isNull(info)){
            sb.append(",").append("未找到对应的供应商类型");
        }else {
            reqVO.setApplySupplyType(info.getSupplierDictionaryValue());
        }
        reqVO.setApplySupplyTypeName(supplierImportNew.getApplySupplyType().trim());
        if(!Constraint.ckChnLetterAndNumAndChar(supplierImportNew.getApplyAbbreviation().trim())){
            sb.append(",").append("简称不能输入特殊字符");
        }
        reqVO.setApplyAbbreviation(supplierImportNew.getApplyAbbreviation().trim());
        checkArea(supplierImportNew, sb, areaTree, reqVO, CheckAreaEnum.普通省市县);
        reqVO.setAddress(supplierImportNew.getAddress().trim());
        reqVO.setContactName(supplierImportNew.getContactName().trim());
        String mobilePhone = supplierImportNew.getMobilePhone();
        if(!Constraint.ckCountNum(11,mobilePhone)){
            sb.append(",").append("手机号码格式不正确");
        }
        reqVO.setMobilePhone(mobilePhone);
        reqVO.setPhone(supplierImportNew.getPhone());
        reqVO.setFax(supplierImportNew.getFax());
        reqVO.setEmail(supplierImportNew.getEmail().trim());
        reqVO.setTaxId(supplierImportNew.getTaxId().trim());
        reqVO.setCorporateRepresentative(supplierImportNew.getCorporateRepresentative());
//        if (!Constraint.ckCountNum(8, supplierImportNew.getRegisteredCapital())) {
//            sb.append(",").append("注册资金");
//        }
        try {
            reqVO.setRegisteredCapital(Long.parseLong(supplierImportNew.getRegisteredCapital()));
        } catch (NumberFormatException e) {
            sb.append(",").append("注册资金格式不正确");
        }
        reqVO.setZipCode(supplierImportNew.getZipCode());
        reqVO.setCompanyWebsite(supplierImportNew.getCompanyWebsite());

        try {
            String minOrderAmount = supplierImportNew.getMinOrderAmount();
            String maxOrderAmount = supplierImportNew.getMaxOrderAmount();
            long l = Long.parseLong(minOrderAmount);
            long l2 = Long.parseLong(maxOrderAmount);
            if(l<0){
                sb.append(",").append("最小起订金额不能小于0");
            }
            if(l2<0){
                sb.append(",").append("最大起订金额不能小于0");
            }
            if (l > l2) {
                sb.append(",").append("最小起订金额不能大于最大起订金额");
            }
            reqVO.setMinOrderAmount(l);
            reqVO.setMaxOrderAmount(l2);
        } catch (NumberFormatException e) {
            sb.append(",").append("最小起订金额或最大起订金额格式不正确");
        }
        List<ApplyDeliveryInfoReqVO> deliver = Lists.newArrayList();
        ApplyDeliveryInfoReqVO sendVO = new ApplyDeliveryInfoReqVO();
        ApplyDeliveryInfoReqVO returnVO = new ApplyDeliveryInfoReqVO();
        try {
            String deliveryDays = supplierImportNew.getDeliveryDays();
            if (StringUtils.isNotBlank(deliveryDays)) {
                sendVO.setDeliveryDays(Long.valueOf(deliveryDays));
            }
        } catch (NumberFormatException e) {
            sb.append(",").append("发货天数格式不正确");
        }
        try {
            String deliveryDays = supplierImportNew.getReturnDays();
            if (StringUtils.isNotBlank(deliveryDays)) {
                returnVO.setDeliveryDays(Long.valueOf(deliveryDays));
            }
        } catch (NumberFormatException e) {
            sb.append(",").append("收货天数格式不正确");
        }
        sendVO.setDeliveryType((byte)0);
        checkArea2(supplierImportNew, sb, areaTree, sendVO, CheckAreaEnum.发货省市县);
        sendVO.setSendingAddress(supplierImportNew.getSendingAddress().trim());
        returnVO.setDeliveryType((byte)1);
        checkArea2(supplierImportNew, sb, areaTree, returnVO, CheckAreaEnum.退货省市县);
        returnVO.setSendingAddress(supplierImportNew.getReturningAddress().trim());
        SupplierDictionaryInfo info1 = dictionaryInfoList.get(supplierImportNew.getSendTo().trim());
        if(Objects.isNull(info1)){
            sb.append(",").append("未找到对应的发货至选项");
        }else {
            sendVO.setSendTo(info.getSupplierDictionaryValue());
        }
        SupplierDictionaryInfo info2 = dictionaryInfoList.get(supplierImportNew.getReturnTo().trim());
        if(Objects.isNull(info2)){
            sb.append(",").append("未找到对应的收货至选项");
        }else {
            returnVO.setSendTo(info.getSupplierDictionaryValue());
        }
        reqVO.setApplySupplyTypeName(supplierImportNew.getApplySupplyType().trim());
        returnVO.setSendToDesc(supplierImportNew.getReturnTo().trim());
        sendVO.setSendToDesc(supplierImportNew.getSendTo().trim());
        deliver.add(sendVO);
        deliver.add(returnVO);
        reqVO.setDeliveryInfoList(deliver);
        reqVO.setError(sb.toString());
        return reqVO;
    }

    private ApplySupplyCompanyReqVO validAndFullUp2(SupplierImportUpdate supplierImportNew, StringBuilder sb, Map<String, AreaInfo> areaTree, Map<String, SupplyCompany> supplyCompanies, Map<String, Supplier> supplierList, Map<String, SupplierDictionaryInfo> dictionaryInfoList,Map<String, SupplyCompany> codeForlist) {
        ApplySupplyCompanyReqVO reqVO = new ApplySupplyCompanyReqVO();
        List<String> name = Lists.newArrayList();
        if (Objects.isNull(codeForlist.get(supplierImportNew.getApplySupplyCode()))) {
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append(",");
            }
            sb.append("该编码对应的供应商不存在");
        } else {
            SupplyCompany supplyCompany1 = supplyCompanies.get(supplierImportNew.getApplySupplyName().trim());
            if((Objects.nonNull(supplyCompany1)&&!codeForlist.get(supplierImportNew.getApplySupplyCode()).getSupplyName().equals(supplierImportNew.getApplySupplyName()))||name.contains(supplierImportNew.getApplySupplyName())){
                if (StringUtils.isNotBlank(sb.toString())) {
                    sb.append(",");
                }
                sb.append("供应商名称重复");
            }
            name.add(supplierImportNew.getApplySupplyName().trim());
            reqVO.setApplySupplyName(supplierImportNew.getApplySupplyName().trim());
        }
        reqVO.setApplySupplyCode(supplierImportNew.getApplySupplyCode());
        if(StringUtils.isNotBlank(supplierImportNew.getSupplierName())){
            Supplier supplier = supplierList.get(supplierImportNew.getSupplierName().trim());
            if(Objects.isNull(supplier)){
                sb.append(",").append("未找到对应名称的供应商集团");
            }else {
                reqVO.setSupplierCode(supplier.getSupplierCode());
            }
            reqVO.setSupplierName(supplierImportNew.getSupplierName().trim());
        }
        SupplierDictionaryInfo info = dictionaryInfoList.get(supplierImportNew.getApplySupplyType().trim());
        if(Objects.isNull(info)){
            sb.append(",").append("未找到对应的供应商类型");
        }else {
            reqVO.setApplySupplyType(info.getSupplierDictionaryValue());
        }
        reqVO.setApplySupplyTypeName(supplierImportNew.getApplySupplyType().trim());
        if(!Constraint.ckChnLetterAndNumAndChar(supplierImportNew.getApplyAbbreviation().trim())){
            sb.append(",").append("简称不能输入特殊字符");
        }
        reqVO.setApplyAbbreviation(supplierImportNew.getApplyAbbreviation().trim());
        checkAreaForUpdate(supplierImportNew, sb, areaTree, reqVO, CheckAreaEnum.普通省市县);
        reqVO.setAddress(supplierImportNew.getAddress().trim());
        reqVO.setContactName(supplierImportNew.getContactName().trim());
        String mobilePhone = supplierImportNew.getMobilePhone();
        if(!Constraint.ckCountNum(11,mobilePhone)){
            sb.append(",").append("手机号码格式不正确");
        }
        reqVO.setMobilePhone(mobilePhone);
        reqVO.setPhone(supplierImportNew.getPhone());
        reqVO.setFax(supplierImportNew.getFax());
        reqVO.setEmail(supplierImportNew.getEmail().trim());
        reqVO.setTaxId(supplierImportNew.getTaxId().trim());
        reqVO.setCorporateRepresentative(supplierImportNew.getCorporateRepresentative());
//        if (!Constraint.ckCountNum(8, supplierImportNew.getRegisteredCapital())) {
//            sb.append(",").append("注册资金");
//        }
        try {
            reqVO.setRegisteredCapital(Long.parseLong(supplierImportNew.getRegisteredCapital()));
        } catch (NumberFormatException e) {
            sb.append(",").append("注册资金格式不正确");
        }
        reqVO.setZipCode(supplierImportNew.getZipCode());
        reqVO.setCompanyWebsite(supplierImportNew.getCompanyWebsite());

        try {
            String minOrderAmount = supplierImportNew.getMinOrderAmount();
            String maxOrderAmount = supplierImportNew.getMaxOrderAmount();
            long l = Long.parseLong(minOrderAmount);
            long l2 = Long.parseLong(maxOrderAmount);
            if(l<0){
                sb.append(",").append("最小起订金额不能小于0");
            }
            if(l2<0){
                sb.append(",").append("最大起订金额不能小于0");
            }
            if (l > l2) {
                sb.append(",").append("最小起订金额不能大于最大起订金额");
            }
            reqVO.setMinOrderAmount(l);
            reqVO.setMaxOrderAmount(l2);
        } catch (NumberFormatException e) {
            sb.append(",").append("最小起订金额或最大起订金额格式不正确");
        }
        List<ApplyDeliveryInfoReqVO> deliver = Lists.newArrayList();
        ApplyDeliveryInfoReqVO sendVO = new ApplyDeliveryInfoReqVO();
        ApplyDeliveryInfoReqVO returnVO = new ApplyDeliveryInfoReqVO();
        try {
            String deliveryDays = supplierImportNew.getDeliveryDays();
            if (StringUtils.isNotBlank(deliveryDays)) {
                sendVO.setDeliveryDays(Long.valueOf(deliveryDays));
            }
        } catch (NumberFormatException e) {
            sb.append(",").append("发货天数格式不正确");
        }
        try {
            String deliveryDays = supplierImportNew.getReturnDays();
            if (StringUtils.isNotBlank(deliveryDays)) {
                returnVO.setDeliveryDays(Long.valueOf(deliveryDays));
            }
        } catch (NumberFormatException e) {
            sb.append(",").append("收货天数格式不正确");
        }
        sendVO.setDeliveryType((byte)0);
        checkArea2ForUpdate(supplierImportNew, sb, areaTree, sendVO, CheckAreaEnum.发货省市县);
        sendVO.setSendingAddress(supplierImportNew.getSendingAddress().trim());
        returnVO.setDeliveryType((byte)1);
        checkArea2ForUpdate(supplierImportNew, sb, areaTree, returnVO, CheckAreaEnum.退货省市县);
        returnVO.setSendingAddress(supplierImportNew.getReturningAddress().trim());
        SupplierDictionaryInfo info1 = dictionaryInfoList.get(supplierImportNew.getSendTo().trim());
        if(Objects.isNull(info1)){
            sb.append(",").append("未找到对应的发货至选项");
        }else {
            sendVO.setSendTo(info.getSupplierDictionaryValue());
        }
        SupplierDictionaryInfo info2 = dictionaryInfoList.get(supplierImportNew.getReturnTo().trim());
        if(Objects.isNull(info2)){
            sb.append(",").append("未找到对应的收货至选项");
        }else {
            returnVO.setSendTo(info.getSupplierDictionaryValue());
        }
        reqVO.setApplySupplyTypeName(supplierImportNew.getApplySupplyType().trim());
        returnVO.setSendToDesc(supplierImportNew.getReturnTo().trim());
        sendVO.setSendToDesc(supplierImportNew.getSendTo().trim());
        deliver.add(sendVO);
        deliver.add(returnVO);
        reqVO.setDeliveryInfoList(deliver);
        reqVO.setError(sb.toString());
        return reqVO;
    }

    private void checkArea(SupplierImportNew supplierImportNew, StringBuilder sb, Map<String, AreaInfo> areaTree, ApplySupplyCompanyReqVO reqVO,CheckAreaEnum checkAreaEnum) {
        SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getProvinceName());
        if (Objects.nonNull(specialArea)) {
            if (specialArea.getHasCity()) {
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getCity());
                    return;
                }else {
                    reqVO.setCityId(areaInfo.getCode());
                }
                reqVO.setCityName(supplierImportNew.getCityName());
                AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
                if(Objects.isNull(areaInfo1)|| supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setProvinceId(areaInfo1.getCode());
                }
                reqVO.setProvinceName(supplierImportNew.getProvinceName());
            }else{
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getProvinceName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setProvinceId(areaInfo.getCode());
                }
                reqVO.setProvinceName(supplierImportNew.getProvinceName());
            }
        }else {
            AreaInfo areaInfo2 = areaTree.get(supplierImportNew.getDistrictName());
            if(Objects.isNull(areaInfo2)){
                sb.append(",").append(checkAreaEnum.getDis());
                return;
            }else {
                reqVO.setDistrictId(areaInfo2.getCode());
            }
            reqVO.setDistrictName(supplierImportNew.getDistrictName());
            AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
            if(Objects.isNull(areaInfo)||!supplierImportNew.getCityName().equals(areaInfo2.getParentName())){
                sb.append(",").append(checkAreaEnum.getCity());
                return;
            }else {
                reqVO.setCityId(areaInfo.getCode());
            }
            reqVO.setCityName(supplierImportNew.getCityName());
            AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
            if(Objects.isNull(areaInfo1)|| !supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                sb.append(",").append(checkAreaEnum.getProvince());
                return;
            }else {
                reqVO.setProvinceId(areaInfo1.getCode());
            }
            reqVO.setProvinceName(supplierImportNew.getProvinceName());
        }
    }

    private void checkAreaForUpdate(SupplierImportUpdate supplierImportNew, StringBuilder sb, Map<String, AreaInfo> areaTree, ApplySupplyCompanyReqVO reqVO,CheckAreaEnum checkAreaEnum) {
        SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getProvinceName());
        if (Objects.nonNull(specialArea)) {
            if (specialArea.getHasCity()) {
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getCity());
                    return;
                }else {
                    reqVO.setCityId(areaInfo.getCode());
                }
                reqVO.setCityName(supplierImportNew.getCityName());
                AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
                if(Objects.isNull(areaInfo1)|| supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setProvinceId(areaInfo1.getCode());
                }
                reqVO.setProvinceName(supplierImportNew.getProvinceName());
            }else{
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getProvinceName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setProvinceId(areaInfo.getCode());
                }
                reqVO.setProvinceName(supplierImportNew.getProvinceName());
            }
        }else {
            AreaInfo areaInfo2 = areaTree.get(supplierImportNew.getDistrictName());
            if(Objects.isNull(areaInfo2)){
                sb.append(",").append(checkAreaEnum.getDis());
                return;
            }else {
                reqVO.setDistrictId(areaInfo2.getCode());
            }
            reqVO.setDistrictName(supplierImportNew.getDistrictName());
            AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
            if(Objects.isNull(areaInfo)||!supplierImportNew.getCityName().equals(areaInfo2.getParentName())){
                sb.append(",").append(checkAreaEnum.getCity());
                return;
            }else {
                reqVO.setCityId(areaInfo.getCode());
            }
            reqVO.setCityName(supplierImportNew.getCityName());
            AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
            if(Objects.isNull(areaInfo1)|| !supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                sb.append(",").append(checkAreaEnum.getProvince());
                return;
            }else {
                reqVO.setProvinceId(areaInfo1.getCode());
            }
            reqVO.setProvinceName(supplierImportNew.getProvinceName());
        }
    }

    private void checkArea2(SupplierImportNew supplierImportNew, StringBuilder sb, Map<String, AreaInfo> areaTree, ApplyDeliveryInfoReqVO reqVO,CheckAreaEnum checkAreaEnum) {
        SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getProvinceName());
        if (Objects.nonNull(specialArea)) {
            if (specialArea.getHasCity()) {
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getCity());
                    return;
                }else {
                    reqVO.setSendCityId(areaInfo.getCode());
                }
                reqVO.setSendCityName(supplierImportNew.getCityName());
                AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
                if(Objects.isNull(areaInfo1)|| supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setSendProvinceId(areaInfo1.getCode());
                }
                reqVO.setSendProvinceName(supplierImportNew.getProvinceName());
            }else{
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getProvinceName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setSendProvinceId(areaInfo.getCode());
                }
                reqVO.setSendProvinceName(supplierImportNew.getProvinceName());
            }
        }else {
            AreaInfo areaInfo2 = areaTree.get(supplierImportNew.getDistrictName());
            if(Objects.isNull(areaInfo2)){
                sb.append(",").append(checkAreaEnum.getDis());
                return;
            }else {
                reqVO.setSendDistrictId(areaInfo2.getCode());
            }
            reqVO.setSendDistrictName(supplierImportNew.getDistrictName());
            AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
            if(Objects.isNull(areaInfo)||!supplierImportNew.getCityName().equals(areaInfo2.getParentName())){
                sb.append(",").append(checkAreaEnum.getCity());
                return;
            }else {
                reqVO.setSendCityId(areaInfo.getCode());
            }
            reqVO.setSendCityName(supplierImportNew.getCityName());
            AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
            if(Objects.isNull(areaInfo1)|| !supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                sb.append(",").append(checkAreaEnum.getProvince());
                return;
            }else {
                reqVO.setSendProvinceId(areaInfo1.getCode());
            }
            reqVO.setSendProvinceName(supplierImportNew.getProvinceName());
        }
    }

    private void checkArea2ForUpdate(SupplierImportUpdate supplierImportNew, StringBuilder sb, Map<String, AreaInfo> areaTree, ApplyDeliveryInfoReqVO reqVO,CheckAreaEnum checkAreaEnum) {
        SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getProvinceName());
        if (Objects.nonNull(specialArea)) {
            if (specialArea.getHasCity()) {
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getCity());
                    return;
                }else {
                    reqVO.setSendCityId(areaInfo.getCode());
                }
                reqVO.setSendCityName(supplierImportNew.getCityName());
                AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
                if(Objects.isNull(areaInfo1)|| supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setSendProvinceId(areaInfo1.getCode());
                }
                reqVO.setSendProvinceName(supplierImportNew.getProvinceName());
            }else{
                AreaInfo areaInfo = areaTree.get(supplierImportNew.getProvinceName());
                if(Objects.isNull(areaInfo)){
                    sb.append(",").append(checkAreaEnum.getProvince());
                    return;
                }else {
                    reqVO.setSendProvinceId(areaInfo.getCode());
                }
                reqVO.setSendProvinceName(supplierImportNew.getProvinceName());
            }
        }else {
            AreaInfo areaInfo2 = areaTree.get(supplierImportNew.getDistrictName());
            if(Objects.isNull(areaInfo2)){
                sb.append(",").append(checkAreaEnum.getDis());
                return;
            }else {
                reqVO.setSendDistrictId(areaInfo2.getCode());
            }
            reqVO.setSendDistrictName(supplierImportNew.getDistrictName());
            AreaInfo areaInfo = areaTree.get(supplierImportNew.getCityName());
            if(Objects.isNull(areaInfo)||!supplierImportNew.getCityName().equals(areaInfo2.getParentName())){
                sb.append(",").append(checkAreaEnum.getCity());
                return;
            }else {
                reqVO.setSendCityId(areaInfo.getCode());
            }
            reqVO.setSendCityName(supplierImportNew.getCityName());
            AreaInfo areaInfo1 = areaTree.get(areaInfo.getParentName());
            if(Objects.isNull(areaInfo1)|| !supplierImportNew.getProvinceName().equals(areaInfo.getParentName())){
                sb.append(",").append(checkAreaEnum.getProvince());
                return;
            }else {
                reqVO.setSendProvinceId(areaInfo1.getCode());
            }
            reqVO.setSendProvinceName(supplierImportNew.getProvinceName());
        }
    }

    private StringBuilder validNull(SupplierImportNew supplierImportNew, StringBuilder sb) {
        if(StringUtils.isBlank(supplierImportNew.getApplySupplyName())){
            sb.append("供应商名称不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getApplySupplyType())){
            sb.append(",");
            sb.append("供应商类型不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getApplyAbbreviation())){
            sb.append(",");
            sb.append("简称不能为空");
        }
        if (StringUtils.isBlank(supplierImportNew.getProvinceName())) {
            sb.append(",");
            sb.append("省不能为空");
        } else {
            SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getProvinceName());
            if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                if (StringUtils.isBlank(supplierImportNew.getCityName())) {
                    sb.append(",");
                    sb.append("市不能为空");
                }
            }
            if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                if (StringUtils.isBlank(supplierImportNew.getCityName())) {
                    sb.append(",");
                    sb.append("县不能为空");
                }
            }

        }
        if(StringUtils.isBlank(supplierImportNew.getAddress())){
            sb.append(",");
            sb.append("详细地址不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getEmail())){
            sb.append(",");
            sb.append("邮箱不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getTaxId())){
            sb.append(",");
            sb.append("税号不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getRegisteredCapital())){
            sb.append(",");
            sb.append("注册资金不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getCorporateRepresentative())){
            sb.append(",");
            sb.append("法人代表不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getContactName())){
            sb.append(",");
            sb.append("联系人姓名不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getMobilePhone())){
            sb.append(",");
            sb.append("手机号不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getMinOrderAmount())){
            sb.append(",");
            sb.append("最低订货金额不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getMaxOrderAmount())){
            sb.append(",");
            sb.append("最高订货金额不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getSendProvinceName())){
            sb.append(",");
            sb.append("发货省不能为空");
        }else {
            SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getSendProvinceName());
            if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                if(StringUtils.isBlank(supplierImportNew.getSendCityName())){
                    sb.append(",");
                    sb.append("发货市不能为空");
                }
            }
            if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                if(StringUtils.isBlank(supplierImportNew.getSendDistrictName())){
                    sb.append(",");
                    sb.append("发货县不能为空");
                }
            }

        }
        if(StringUtils.isBlank(supplierImportNew.getSendingAddress())){
            sb.append(",");
            sb.append("发货详细地址不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getSendTo())){
            sb.append(",");
            sb.append("发货至不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getReturnProvinceName())){
            sb.append(",");
            sb.append("收货省不能为空");
        }else {
            SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getReturnProvinceName());
            if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                if(StringUtils.isBlank(supplierImportNew.getReturnCityName())){
                    sb.append(",");
                    sb.append("收货市不能为空");
                }
            }
            if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                if(StringUtils.isBlank(supplierImportNew.getReturnDistrictName())){
                    sb.append(",");
                    sb.append("收货县不能为空");
                }
            }
        }
        if(StringUtils.isBlank(supplierImportNew.getReturningAddress())){
            sb.append(",");
            sb.append("收货详细地址不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getReturnTo())){
            sb.append(",");
            sb.append("收货至不能为空");
        }
        return sb;
    }

    private StringBuilder validNull2(SupplierImportUpdate supplierImportNew, StringBuilder sb) {
        if(StringUtils.isBlank(supplierImportNew.getApplySupplyCode())){
            sb.append("供应商编码不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getApplySupplyName())){
            sb.append("供应商名称不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getApplySupplyType())){
            sb.append(",");
            sb.append("供应商类型不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getApplyAbbreviation())){
            sb.append(",");
            sb.append("简称不能为空");
        }
        if (StringUtils.isBlank(supplierImportNew.getProvinceName())) {
            sb.append(",");
            sb.append("省不能为空");
        } else {
            SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getProvinceName());
            if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                if (StringUtils.isBlank(supplierImportNew.getCityName())) {
                    sb.append(",");
                    sb.append("市不能为空");
                }
            }
            if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                if (StringUtils.isBlank(supplierImportNew.getCityName())) {
                    sb.append(",");
                    sb.append("县不能为空");
                }
            }

        }
        if(StringUtils.isBlank(supplierImportNew.getAddress())){
            sb.append(",");
            sb.append("详细地址不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getEmail())){
            sb.append(",");
            sb.append("邮箱不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getTaxId())){
            sb.append(",");
            sb.append("税号不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getRegisteredCapital())){
            sb.append(",");
            sb.append("注册资金不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getCorporateRepresentative())){
            sb.append(",");
            sb.append("法人代表不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getContactName())){
            sb.append(",");
            sb.append("联系人姓名不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getMobilePhone())){
            sb.append(",");
            sb.append("手机号不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getMinOrderAmount())){
            sb.append(",");
            sb.append("最低订货金额不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getMaxOrderAmount())){
            sb.append(",");
            sb.append("最高订货金额不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getSendProvinceName())){
            sb.append(",");
            sb.append("发货省不能为空");
        }else {
            SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getSendProvinceName());
            if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                if(StringUtils.isBlank(supplierImportNew.getSendCityName())){
                    sb.append(",");
                    sb.append("发货市不能为空");
                }
            }
            if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                if(StringUtils.isBlank(supplierImportNew.getSendDistrictName())){
                    sb.append(",");
                    sb.append("发货县不能为空");
                }
            }

        }
        if(StringUtils.isBlank(supplierImportNew.getSendingAddress())){
            sb.append(",");
            sb.append("发货详细地址不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getSendTo())){
            sb.append(",");
            sb.append("发货至不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getReturnProvinceName())){
            sb.append(",");
            sb.append("收货省不能为空");
        }else {
            SpecialArea specialArea = SpecialArea.getAll().get(supplierImportNew.getReturnProvinceName());
            if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                if(StringUtils.isBlank(supplierImportNew.getReturnCityName())){
                    sb.append(",");
                    sb.append("收货市不能为空");
                }
            }
            if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                if(StringUtils.isBlank(supplierImportNew.getReturnDistrictName())){
                    sb.append(",");
                    sb.append("收货县不能为空");
                }
            }
        }
        if(StringUtils.isBlank(supplierImportNew.getReturningAddress())){
            sb.append(",");
            sb.append("收货详细地址不能为空");
        }
        if(StringUtils.isBlank(supplierImportNew.getReturnTo())){
            sb.append(",");
            sb.append("收货至不能为空");
        }
        return sb;
    }

    public Map<String, AreaInfo> getAreaTree() {
        HttpResponse<List<AreaBasic>> treeList = areaBasicInfoService.getTreeList();
        List<AreaBasic> tempData = treeList.getData();
        String s = JSON.toJSONString(tempData);
        List<AreaBasic> data = JSON.parseArray(s, AreaBasic.class);
        Map<String, AreaInfo> map = new HashMap<>(3500);
        for (AreaBasic datum : data) {
            //省
            List<AreaBasic> children = datum.getChildren();
            if (CollectionUtils.isEmptyCollection(children)) {
                continue;
            }
            //市
            for (AreaBasic child : children) {
                List<AreaBasic> children1 = child.getChildren();
                if (CollectionUtils.isEmptyCollection(children1)) {
                    continue;
                }
                Map<String, AreaBasic> map1 = Maps.newHashMap();
                //区
                for (AreaBasic areaBasic : children1) {
                    AreaInfo info = new AreaInfo(areaBasic.getParent_area_name(),areaBasic.getArea_id(),areaBasic.getArea_name());
                    map.put(areaBasic.getArea_name(),info);
                }
                AreaInfo info = new AreaInfo(child.getParent_area_name(),child.getArea_id(),child.getArea_name());
                map.put(child.getArea_name(), info);
            }
            AreaInfo info = new AreaInfo(datum.getParent_area_name(),datum.getArea_id(),datum.getArea_name());
            map.put(datum.getArea_name(), info);
        }
        return map;
    }

    private void dataValidation(List<SupplierImportNew> supplierImportNews) {
        if(CollectionUtils.isEmptyCollection(supplierImportNews)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (Objects.isNull(supplierImportNews.size()<2)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
         String  head = SupplierImportNew.HEDE;
        boolean equals = supplierImportNews.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

    private void dataValidation2(List<SupplierImportUpdate> supplierImportNews) {
        if(CollectionUtils.isEmptyCollection(supplierImportNews)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (supplierImportNews.size()<2) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String head = SupplierImportUpdate.HEDE;
        boolean equals = supplierImportNews.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }
}
