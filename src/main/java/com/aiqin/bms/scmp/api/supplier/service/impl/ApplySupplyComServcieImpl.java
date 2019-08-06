package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.*;
import com.aiqin.bms.scmp.api.supplier.domain.SpecialArea;
import com.aiqin.bms.scmp.api.supplier.domain.excel.*;
import com.aiqin.bms.scmp.api.supplier.domain.excel.im.SupplierImportNew;
import com.aiqin.bms.scmp.api.supplier.domain.excel.im.SupplierImportUpdate;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.QueryApplySupplyListComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.ApplyComDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.ApplySupplyComApplyListRespVO;
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
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private DeliveryInfoService deliveryInfoService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SupplyCompanyAccountDao supplyCompanyAccountDao;

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
            if(Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                applySupplyCompanyReqDTO.setApplyStatus(ApplyStatus.PENDING_SUBMISSION.getNumber());
            } else {
                applySupplyCompanyReqDTO.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
            }
            //新增申请和日志
            if(StringUtils.isNotBlank(applySupplyCompanyReqVO.getApplySupplyCode())){
                applySupplyCompanyReqDTO.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
            } else {
                applySupplyCompanyReqDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                applySupplyCompanyReqDTO.setEnable(StatusTypeCode.EN_ABLE.getStatus());
            }
            String applySupplyCompanyCode = insertApplyAndOther(applySupplyCompanyReqDTO);
            if(!Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                //审批流程
                applySupplyCompanyReqDTO.setFormNo("GYS"+IdSequenceUtils.getInstance().nextId());
                workFlow(applySupplyCompanyReqDTO);
            }
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
    public Long updateApply(ApplySupplyCompanyReqVO applySupplyCompanyReqVO) {
        Long num;
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
            ApplySupplyCompanyReqDTO applySupplyCompany = new ApplySupplyCompanyReqDTO();
            BeanCopyUtils.copy(applySupplyCompanyReqVO,applySupplyCompany);
            //正式供应商编码
            applySupplyCompany.setSupplyCompanyCode(applySupplyCompanyReqVO.getApplySupplyCode());
            SupplyCompany s = supplyCompanyMapper.selectBySupplyComCode(applySupplyCompany.getSupplyCompanyCode(),getUser().getCompanyCode());
            List<String> codes = Lists.newArrayList();
            codes.add(applySupplyCompanyReqVO.getApplySupplyCode());
            List<ApplySupplyCompany> applySupplyCompanies = applySupplyCompanyMapper.selectBySupplyCode(codes, getUser().getCompanyCode(),ApplyStatus.APPROVAL.getNumber());
            List<ApplySupplyCompany> forImportList = applySupplyCompanyMapper.selectBySupplyCode(codes, getUser().getCompanyCode(),ApplyStatus.PENDING_SUBMISSION.getNumber());
            if (CollectionUtils.isNotEmptyCollection(applySupplyCompanies)) {
               //如果有审批中的数据  直接报错
                throw new BizException(MessageId.create(Project.PRODUCT_API, 998, "供应商编号为"+applySupplyCompany.getSupplyCompanyCode()+"已有在审核中的数据,无法提交"));
            }
            //正式供应商编码
            applySupplyCompany.setSupplyCompanyCode(applySupplyCompanyReqVO.getApplySupplyCode());
            //供货单位申请编码
            EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.APPLY_SUPPLY_COM_CODE);
            applySupplyCompany.setApplyCode(String.valueOf(encodingRule.getNumberingValue()+1));
            if(Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                applySupplyCompany.setApplyStatus(ApplyStatus.PENDING_SUBMISSION.getNumber());
                applySupplyCompany.setEnable(s.getEnable());
            } else {
                applySupplyCompany.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
                applySupplyCompany.setFormNo("GYS"+IdSequenceUtils.getInstance().nextId());
            }
            applySupplyCompany.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
            num = ((ApplySupplyComServcie)AopContext.currentProxy()).insert(applySupplyCompany);
            String content = ApplyStatus.PENDING.getContent().replace("CREATEBY", applySupplyCompany.getUpdateBy()).replace("APPLYTYPE", "修改");
            //存日志
            supplierCommonService.getInstance(applySupplyCompany.getApplyCode()+"", HandleTypeCoce.PENDING.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),content,null,HandleTypeCoce.PENDING.getName());
            //修改编码
            encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
            //更新正式表的申请编码
            supplyCompanyMapper.updateApplyCodeBySupplyComCode(applySupplyCompany.getSupplyCompanyCode(),applySupplyCompany.getApplyCode());
            //发货信息
            applyDeliveryInfoDao.deleteBatch(applySupplyCompany.getApplyCode());
            //发货信息
            if(CollectionUtils.isEmptyCollection(applySupplyCompanyReqVO.getDeliveryInfoList())){
                throw new BizException(ResultCode.DELIVERY_RETURN_EMPTY);
            }
            //判断是否存在发货信息
            List<ApplyDeliveryInfoReqVO> collect = applySupplyCompanyReqVO.getDeliveryInfoList().stream().filter(item -> item.getDeliveryType().equals(Global.BYTE_ZERO)).collect(Collectors.toList());
            if(CollectionUtils.isEmptyCollection(collect)){
                throw new BizException(ResultCode.DELIVERY_EMPTY);
            }
            //判断是否在退货信息
            List<ApplyDeliveryInfoReqVO> collect2 = applySupplyCompanyReqVO.getDeliveryInfoList().stream().filter(item -> item.getDeliveryType().equals(Global.BYTE_ONE)).collect(Collectors.toList());
            if(CollectionUtils.isEmptyCollection(collect2)){
                throw new BizException(ResultCode.RETURN_EMPTY);
            }
            try {
                List<ApplyDeliveryDTO> deliveryDTOS = BeanCopyUtils.copyList(applySupplyCompanyReqVO.getDeliveryInfoList(), ApplyDeliveryDTO.class);
                deliveryDTOS.forEach(item->{
                    item.setApplySupplyCompanyCode(applySupplyCompany.getApplyCode());
                    item.setApplySupplyCompanyName(applySupplyCompany.getApplySupplyName());
                });
                applyDeliveryService.insideSaveBatchApply(deliveryDTOS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(MessageId.create(Project.SUPPLIER_API, 63,
                        "系统错误"));
            }
            //增加文件信息
            applySupplierFileDao.deleteApplySupplierFileByApplyCode(applySupplyCompany.getApplyCode());
            List<SupplierFileReqVO> fileReqVOList = applySupplyCompanyReqVO.getFileReqVOList();
            if(CollectionUtils.isNotEmptyCollection(fileReqVOList)){
                fileReqVOList.forEach(item->{
                    item.setApplySupplierCode(applySupplyCompany.getApplyCode());
                    item.setApplySupplierName(applySupplyCompany.getApplySupplyName());
                    item.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                });
                applySupplierFileService.copySaveInfo(fileReqVOList);
            }
            if (CollectionUtils.isNotEmptyCollection(forImportList)) {
                applySupplyCompanyMapper.delectByIds(forImportList.stream().map(ApplySupplyCompany::getId).collect(Collectors.toList()));
            }
            if(!Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                workFlow(applySupplyCompany);
            }
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
            if(Objects.equals(StatusTypeCode.ADD_APPLY.getStatus(),applySupplyCompanyReqDTO.getApplyType())){
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
                String content = ApplyStatus.APPROVAL.getContent().replace("CREATEBY", applySupplyCompanyReqDTO.getUpdateBy()).replace("APPLYTYPE", title);
                //存日志
                supplierCommonService.getInstance(applySupplyCompanyReqDTO.getApplyCode()+"", HandleTypeCoce.APPROVAL.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),content,null,HandleTypeCoce.APPROVAL.getName());
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
//                queryApplySupplyComReqVO.setApplyBy(authToken.getPersonName());
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
            List<ApplyDeliveryInfoRespVO> applyDeliveryInfoRespVO = applyDeliveryInfoDao.getApplyDeliveryInfoDetail(applyCode);
            applySupplyComDetailRespVO.setApplyDeliveryInfoRespVO(applyDeliveryInfoRespVO);
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

        applySupplyCompanyReqDTO.setApplyCode(String.valueOf(encodingRule.getNumberingValue()+1));
        //新增供货单位申请
        Long resultNum =((ApplySupplyComServcie)AopContext.currentProxy()).insert(applySupplyCompanyReqDTO);
        ApplyStatus applyStatus = ApplyStatus.getApplyStatusByNumber(applySupplyCompanyReqDTO.getApplyStatus());
        String content =applyStatus.getContent().replace("CREATEBY", applySupplyCompanyReqDTO.getCreateBy()).replace("APPLYTYPE", "新增");
        HandleTypeCoce handleTypeCoce = HandleTypeCoce.PENDING;
        if(Objects.equals(applyStatus,ApplyStatus.PENDING_SUBMISSION)){
            handleTypeCoce = HandleTypeCoce.PENDING_SUBMISSION;
        }
        //存日志
        supplierCommonService.getInstance(applySupplyCompanyReqDTO.getApplyCode()+"",handleTypeCoce.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),content,null,handleTypeCoce.getName());
        //修改编码
        encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        //发货信息
        if(CollectionUtils.isEmptyCollection(applySupplyCompanyReqDTO.getDeliveryInfoList())){
           throw new BizException(ResultCode.DELIVERY_RETURN_EMPTY);
        }
        //判断是否存在发货信息
        List<ApplyDeliveryInfoReqVO> collect = applySupplyCompanyReqDTO.getDeliveryInfoList().stream().filter(item -> item.getDeliveryType().equals(Global.BYTE_ZERO)).collect(Collectors.toList());
        if(CollectionUtils.isEmptyCollection(collect)){
            throw new BizException(ResultCode.DELIVERY_EMPTY);
        }
        //判断是否在退货信息
        List<ApplyDeliveryInfoReqVO> collect2 = applySupplyCompanyReqDTO.getDeliveryInfoList().stream().filter(item -> item.getDeliveryType().equals(Global.BYTE_ONE)).collect(Collectors.toList());
        if(CollectionUtils.isEmptyCollection(collect2)){
            throw new BizException(ResultCode.RETURN_EMPTY);
        }
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
        HandleTypeCoce applyHandleTypeCoce;
        ApplySupplyCompanyAccount applySupplyCompanyAccount = applySupplyCompanyAcctDao.getApplySupplyComAcct(applySupplyCompany.getApplySupplyCompanyCode());
        if(applySupplyCompany.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            applySupplyCompany.setApplyStatus(vo.getApplyStatus());
            if(vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())){
                applyHandleTypeCoce = HandleTypeCoce.APPROVAL_SUCCESS;
                SupplyCompany oldSupplyCompany = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
                //通过插入正式数据
                SupplyCompany supplyCompany = new SupplyCompany();
                BeanCopyUtils.copy(applySupplyCompany,supplyCompany);
                supplyCompany.setAuditorBy(vo.getApprovalUserName());
                supplyCompany.setAuditorTime(new Date());
                String content;
                HandleTypeCoce handleTypeCoce;
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
                    supplyCompany.setStarScore(oldSupplyCompany.getStarScore());
                    handleTypeCoce = HandleTypeCoce.UPDATE;
                    content = HandleTypeCoce.UPDATE_SUPPLY_COMPANY.getName();
                    supplyCompanyMapper.updateByPrimaryKey(supplyCompany);
                } else {
                    EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.SUPPLY_COM_CODE);
                    supplyCompany.setId(null);
                    supplyCompany.setSupplyName(applySupplyCompany.getApplySupplyName());
                    supplyCompany.setSupplyType(applySupplyCompany.getApplySupplyType());
                    supplyCompany.setSupplyAbbreviation(applySupplyCompany.getApplyAbbreviation());
                    supplyCompany.setSupplyCode(String.valueOf(encodingRule.getNumberingValue()));
                    content = HandleTypeCoce.ADD_SUPPLY_COMPANY.getName();
                    handleTypeCoce = HandleTypeCoce.ADD;
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

                supplierCommonService.getInstance(supplyCompany.getSupplyCode(),handleTypeCoce.getStatus(),ObjectTypeCode.SUPPLY_COMPANY.getStatus(),content,null, handleTypeCoce.getName(),applySupplyCompany.getCreateBy());
            }else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())){
                applyHandleTypeCoce = HandleTypeCoce.APPROVAL_FAILED;
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
                applyHandleTypeCoce = HandleTypeCoce.APPROVAL;
                ApplyStatus applyStatus = ApplyStatus.getApplyStatusByNumber(applySupplyCompany.getApplyStatus());
                String content = applyStatus.getContent().replace("CREATEBY", applySupplyCompany.getCreateBy()).replace("AUDITORBY", vo.getApprovalUserName());
                supplierCommonService.getInstance(applySupplyCompany.getApplySupplyCompanyCode(),applyHandleTypeCoce.getStatus(),ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),content, null,applyHandleTypeCoce.getName(),vo.getApprovalUserName());
                //传入的是审批中，继续该流程
                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
            }else if (vo.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber())){
                applyHandleTypeCoce = HandleTypeCoce.REVOKED;
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
        ApplyStatus applyStatus = ApplyStatus.getApplyStatusByNumber(applySupplyCompany.getApplyStatus());
        String content = applyStatus.getContent().replace("CREATEBY", applySupplyCompany.getCreateBy()).replace("AUDITORBY", vo.getApprovalUserName());
        supplierCommonService.getInstance(applySupplyCompany.getApplySupplyCompanyCode(),applyHandleTypeCoce.getStatus(),ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),content, null,applyHandleTypeCoce.getName(),vo.getApprovalUserName());
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }

    @Override
    public List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            querySupplierReqVO.setCompanyCode(authToken.getCompanyCode());
            //querySupplierReqVO.setApplyBy(authToken.getPersonName());
        }
        return applySupplyCompanyDao.queryApplyList(querySupplierReqVO);
    }

    @Override
    public SupplierImportResp dealImport(MultipartFile file) {
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
            List<SupplierImport> importVos = Lists.newArrayList();
            for (int i = 0; i < supplierImportNews.size(); i++) {
                SupplierImportNew supplierImportNew = supplierImportNews.get(i);
                CheckSupply checkSupply = new CheckSupply(supplierImportNew, areaTree, supplyCompanies, null, supplierList, dictionaryInfoList)
                        .checkSupplyNew()
                        .checkCommonData();
                applyList.add(checkSupply.getReqVO());
                importVos.add(checkSupply.getSupplierImport());
            }
            return new SupplierImportResp(applyList,importVos);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_EXCEPTION);
        }
    }
    @Override
    public SupplierImportResp dealImport2(MultipartFile file) {
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
            List<SupplierImport> importVos = Lists.newArrayList();
            for (int i = 0; i < supplierImportNews.size(); i++) {
                SupplierImportUpdate supplierImportNew = supplierImportNews.get(i);
                CheckSupply checkSupply = new CheckSupply(supplierImportNew, areaTree, supplyCompanies, codeList, supplierList, dictionaryInfoList)
                        .checkSupplyUpdate()
                        .checkCommonData();
                applyList.add(checkSupply.getReqVO());
                importVos.add(checkSupply.getSupplierImport());
            }
            return new SupplierImportResp(applyList,importVos);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_EXCEPTION);
        }
    }


    /**
     * 功能描述: 新增导入保存
     *
     * @param req
     * @return
     * @auther knight.xie
     * @date 2019/7/26 14:40
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importSupplierNewSave(SupplierImportReq req) {
        for (ApplySupplyCompanyReqVO reqVO : req.getApplyList()) {
            reqVO.setPurchasingGroupCode(req.getPurchaseGroupCode());
            reqVO.setPurchasingGroupName(req.getPurchaseGroupName());
            reqVO.setSource(Byte.valueOf("1"));
            ((ApplySupplyComServcie)AopContext.currentProxy()).saveApply(reqVO);
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述: 修改导入保存
     *
     * @param req
     * @return
     * @auther knight.xie
     * @date 2019/7/26 14:41
     */
    @Override
    public Boolean importSupplierNewUpdate(SupplierImportReq req) {
        for (ApplySupplyCompanyReqVO reqVO : req.getApplyList()) {
            reqVO.setPurchasingGroupCode(req.getPurchaseGroupCode());
            reqVO.setPurchasingGroupName(req.getPurchaseGroupName());
            reqVO.setSource(Byte.valueOf("1"));
            ((ApplySupplyComServcie)AopContext.currentProxy()).updateApply(reqVO);
        }
        return Boolean.TRUE;
    }

    @Override
    public BasePage<ApplySupplyComApplyListRespVO> applyList(QueryApplySupplyListComReqVO queryApplySupplyComReqVO) {
            queryApplySupplyComReqVO.setCompanyCode(getUser().getCompanyCode());
            queryApplySupplyComReqVO.setPersonId(getUser().getPersonId());
        PageHelper.startPage(queryApplySupplyComReqVO.getPageNo(), queryApplySupplyComReqVO.getPageSize());
        List<ApplySupplyComApplyListRespVO> applySupplierResps = applySupplyCompanyDao.applyList(queryApplySupplyComReqVO);
        return PageUtil.getPageList(queryApplySupplyComReqVO.getPageNo(),applySupplierResps);
    }

    @Override
    public ApplyComDetailRespVO applyView(Long id,String statusTypeCode) {
        ApplyComDetailRespVO supplyComDetailRespVO = new ApplyComDetailRespVO();
        try {
            //根据ID查询供货单位,结算,收货信息
            SupplyCompanyDetailDTO supplyCompanyDetailDTO = supplyCompanyDao.getApplySupplyComDetail(id);
            if (null != supplyCompanyDetailDTO){
                BeanCopyUtils.copy(supplyCompanyDetailDTO,supplyComDetailRespVO);
                supplyComDetailRespVO.setId(supplyCompanyDetailDTO.getSupplyComId());
                supplyComDetailRespVO.setPurchasingGroupCode(supplyCompanyDetailDTO.getSupplyPurchasingGroupCode());
                supplyComDetailRespVO.setPurchasingGroupName(supplyCompanyDetailDTO.getSupplyPurchasingGroupName());
                supplyComDetailRespVO.setApplyAbbreviation(supplyCompanyDetailDTO.getSupplyAbbreviation());
                supplyComDetailRespVO.setApplySupplyCode(supplyCompanyDetailDTO.getSupplyCode());
                supplyComDetailRespVO.setApplySupplyType(supplyCompanyDetailDTO.getSupplyType());
                supplyComDetailRespVO.setApplySupplyTypeName(supplyCompanyDetailDTO.getSupplyTypeName());
                supplyComDetailRespVO.setApplySupplyName(supplyCompanyDetailDTO.getSupplyName());
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.SUPPLY_COMPANY.getStatus());
                operationLogVo.setObjectId(supplyCompanyDetailDTO.getSupplyCode());
                BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo,62);
                List<LogData> logDataList = new ArrayList<>();
                if (null != pageList.getDataList() && pageList.getDataList().size() > 0){
                    logDataList = pageList.getDataList();
                }
                supplyComDetailRespVO.setLogDataList(logDataList);
                //根据供货单位编码查询发货/退货信息
                List<DeliveryInfoRespVO> deliveryInfoRespVOS = deliveryInfoService.getDeliveryInfoByApplyCompanyCode(supplyCompanyDetailDTO.getSupplyCode());
                supplyComDetailRespVO.setDeliveryInfoRespVOS(deliveryInfoRespVOS);
//                //根据供货单位编码查询标签信息
//                List<DetailTagUseRespVo> useTagRecordReqVos = tagInfoService.getUseTagRecordByUseObjectCode2(supplyCompanyDetailDTO.getSupplyCode(), TagTypeCode.SUPPLIER.getStatus());
//                supplyComDetailRespVO.setUseTagRecordReqVos(useTagRecordReqVos);

                List<SupplierFile> supplierFile = supplierFileDao.getApplySupplierFile(supplyCompanyDetailDTO.getSupplyCode());
                if(CollectionUtils.isNotEmptyCollection(supplierFile)){
                    List<SupplierFileReqVO> list = BeanCopyUtils.copyList(supplierFile, SupplierFileReqVO.class);
                    list.forEach(item->{
                        item.setApplySupplierCode(supplyCompanyDetailDTO.getSupplyCode());
                        item.setApplySupplierName(supplyCompanyDetailDTO.getSupplyName());
                    });
                    supplyComDetailRespVO.setFileReqVOList(list);
                }
//                if (Objects.equals(StatusTypeCode.SHOW_ACCOUNT_SKU,statusTypeCode)) {
//                    QuerySupplierComAcctReqVo vo = new QuerySupplierComAcctReqVo();
//                    vo.setSupplyCompanyCode(supplyComDetailRespVO.getApplySupplyCode());
//                    List<QuerySupplierComAcctRespVo> querySupplierComAcctRespVos = supplyCompanyAccountDao.selectListByQueryVO(vo);
//                    supplyComDetailRespVO.setSupplierComAcctRespVos(querySupplierComAcctRespVos);
//                    List<QueryProductSkuListResp> queryProductSkuListResps = null;
//                    queryProductSkuListResps = skuInfoService.querySkuListBySupplyUnitCode(supplyComDetailRespVO.getApplySupplyCode());
//                    supplyComDetailRespVO.setSkuListRespVos(queryProductSkuListResps);
//                }
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"未查询信息"));
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplyComDetailRespVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editApply(ApplySupplyCompanyReqVO applySupplyCompanyReqVO) {
        try {
            ApplySupplyCompany s = applySupplyCompanyMapper.selectByCode(applySupplyCompanyReqVO.getApplySupplyCode());
            if(null == s){
                throw new BizException(ResultCode.OBJECT_EMPTY);
            }
            String companyCode = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("name",applySupplyCompanyReqVO.getApplySupplyName());
            map.put("code",s.getSupplyCompanyCode());
            map.put("companyCode",companyCode);
            int nameCount = supplyCompanyDao.checkName(map);
            if (nameCount > 0){
                throw new BizException(ResultCode.NAME_REPEAT);
            }
            //通过编码查询

            //复制对象
            ApplySupplyCompany applySupplyCompany = new ApplySupplyCompany();
            BeanCopyUtils.copy(applySupplyCompanyReqVO,applySupplyCompany);
            applySupplyCompany.setSupplyCompanyCode(applySupplyCompanyReqVO.getApplySupplyCode());
            //根据供货单位code获取相应的三张申请表id
            applySupplyCompany.setId(s.getId());
            applySupplyCompany.setFormNo(s.getFormNo());
            applySupplyCompany.setSupplyCompanyCode(s.getSupplyCompanyCode());
            applySupplyCompany.setApplySupplyCompanyCode(s.getApplySupplyCompanyCode());
            if(Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                applySupplyCompany.setApplyStatus(ApplyStatus.PENDING_SUBMISSION.getNumber());
            } else {
                applySupplyCompany.setApplyStatus(StatusTypeCode.PENDING_STATUS.getStatus());
                applySupplyCompany.setFormNo("GYS"+IdSequenceUtils.getInstance().nextId());
            }
            Byte status = null;
            if (StringUtils.isNotBlank(s.getSupplyCompanyCode())) {
                status = StatusTypeCode.UPDATE_APPLY.getStatus();
            }else {
                status = StatusTypeCode.ADD_APPLY.getStatus();
            }
                applySupplyCompany.setApplyType(status);
            //存日志
            Long id=applySupplyCompany.getId();
            ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO = new ApplySupplyCompanyReqDTO();
            if(id!=null) {
                ApplySupplyCompany applySupplyCompany1 = applySupplyCompanyMapper.selectByPrimaryKey(id);
                BeanCopyUtils.copy(applySupplyCompany1, applySupplyCompanyReqDTO);
                applySupplyCompanyReqDTO.setApplyType(status);
                applySupplyCompanyReqDTO.setApplyCode(applySupplyCompany1.getApplySupplyCompanyCode());
            }
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
            //增加文件信息
            applySupplierFileDao.deleteApplySupplierFileByApplyCode(applySupplyCompanyReqDTO.getApplyCode());
            List<SupplierFileReqVO> fileReqVOList = applySupplyCompanyReqVO.getFileReqVOList();
            if(CollectionUtils.isNotEmptyCollection(fileReqVOList)){
                Byte finalStatus = status;
                fileReqVOList.forEach(item->{
                    item.setApplySupplierCode(applySupplyCompanyReqDTO.getApplyCode());
                    item.setApplySupplierName(applySupplyCompanyReqDTO.getApplySupplyName());
                    item.setApplyType(finalStatus);
                });
                applySupplierFileService.copySaveInfo(fileReqVOList);
            }
            int i = applySupplyCompanyMapper.delectById(id);
            if(i==0){
                throw new GroundRuntimeException("修改失败");
            }
            applySupplyCompany.setDelFlag((byte) 0);
            applySupplyCompany.setId(null);
            //如果是供应商修改，需要更新申请编码
            ((ApplySupplyComServcie)AopContext.currentProxy()).insertData(applySupplyCompany);
            if(StringUtils.isNotBlank(s.getSupplyCompanyCode())){
                int temp = supplyCompanyDao.updateApplyCode(s.getSupplyCompanyCode(),applySupplyCompany.getApplySupplyCompanyCode());
                if(temp!=1){
                    throw new BizException(ResultCode.UPDATE_ERROR);
                }
            }
            if(!Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                applySupplyCompanyReqDTO.setDirectSupervisorCode(applySupplyCompanyReqVO.getDirectSupervisorCode());
                applySupplyCompanyReqDTO.setDirectSupervisorName(applySupplyCompanyReqVO.getDirectSupervisorName());
                applySupplyCompanyReqDTO.setFormNo(applySupplyCompany.getFormNo());
                applySupplyCompanyReqDTO.setId(applySupplyCompany.getId());
                workFlow(applySupplyCompanyReqDTO);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
        return Boolean.TRUE;
    }

    @Override
    @Update
    @Save
    @Transactional(rollbackFor = Exception.class)
    public int insertData(ApplySupplyCompany applySupplyCompany) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            applySupplyCompany.setCompanyCode(authToken.getCompanyCode());
            applySupplyCompany.setCompanyName(authToken.getCompanyName());
        }
        return applySupplyCompanyMapper.insert(applySupplyCompany);
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


    public Map<String, AreaInfo> getAreaTree() {
        HttpResponse<List<AreaBasic>> treeList = areaBasicInfoService.getTreeList();
        List<AreaBasic> tempData = treeList.getData();
        String s = JSON.toJSONString(tempData);
        List<AreaBasic> data = JSON.parseArray(s, AreaBasic.class);
        Map<String, AreaInfo> map = new HashMap<>(3500);
        for (AreaBasic datum : data) {
            //省
            List<AreaBasic> children = datum.getChildren();
            AreaInfo info = new AreaInfo(datum.getParent_area_name(),datum.getArea_id(),datum.getArea_name());
            map.put(datum.getArea_name(), info);
            if (CollectionUtils.isEmptyCollection(children)) {
                continue;
            }
            //市
            for (AreaBasic child : children) {
                AreaInfo info1 = new AreaInfo(child.getParent_area_name(),child.getArea_id(),child.getArea_name());
                map.put(datum.getArea_name()+child.getArea_name(), info1);
                List<AreaBasic> children1 = child.getChildren();
                if (CollectionUtils.isEmptyCollection(children1)) {
                    continue;
                }
                //区
                for (AreaBasic areaBasic : children1) {
                    AreaInfo info2 = new AreaInfo(areaBasic.getParent_area_name(),areaBasic.getArea_id(),areaBasic.getArea_name());
                    map.put(datum.getArea_name()+child.getArea_name()+areaBasic.getArea_name(),info2);
                }
            }
        }
        return map;
    }

    private void dataValidation(List<SupplierImportNew> supplierImportNews) {
        if(CollectionUtils.isEmptyCollection(supplierImportNews)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (supplierImportNews.size()<2) {
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
    //导入的校验
    private class CheckSupply{

        private List<String> error;
        SupplierImport supplierImport;
        Map<String, AreaInfo> areaTree;
        Map<String, SupplyCompany> supplyCompanyNames;
        Map<String, SupplyCompany> supplyCompanyCodes;
        Map<String, Supplier> supplierList;
        Map<String, SupplierDictionaryInfo> dictionaryInfoList;
        ApplySupplyCompanyReqVO reqVO;
        ApplyDeliveryInfoReqVO sendVO;
        ApplyDeliveryInfoReqVO returnVO;


        public CheckSupply( Object supplierImport, Map<String, AreaInfo> areaTree, Map<String, SupplyCompany> supplyCompanyNames, Map<String, SupplyCompany> supplyCompanyCodes, Map<String, Supplier> supplierList, Map<String, SupplierDictionaryInfo> dictionaryInfoList) {
            this.error = Lists.newArrayList();
            this.supplierImport = BeanCopyUtils.copy(supplierImport,SupplierImport.class);
            this.areaTree = areaTree;
            this.supplyCompanyNames = supplyCompanyNames;
            this.supplyCompanyCodes = supplyCompanyCodes;
            this.supplierList = supplierList;
            this.dictionaryInfoList = dictionaryInfoList;
            this.reqVO = new ApplySupplyCompanyReqVO();
            this.sendVO = new ApplyDeliveryInfoReqVO();
            this.returnVO = new ApplyDeliveryInfoReqVO();
        }

        public ApplySupplyCompanyReqVO getReqVO(){
            List<ApplyDeliveryInfoReqVO> infos = Lists.newArrayList();
            infos.add(sendVO);
            infos.add(returnVO);
            this.reqVO.setDeliveryInfoList(infos);
            return this.reqVO;
        }
        public SupplierImport getSupplierImport(){
            this.supplierImport.setError(StringUtils.strip(this.error.toString(), "[]"));
            return this.supplierImport;
        }
        private CheckSupply checkSupplyNew(){
            if(StringUtils.isBlank(supplierImport.getApplySupplyName())){
                error.add("供应商名称不能为空");
            }else {
                SupplyCompany supplyCompany1 = supplyCompanyNames.get(supplierImport.getApplySupplyName().trim());
                if(Objects.nonNull(supplyCompany1)){
                    error.add("供应商名称重复");
                }else {
                    reqVO.setApplySupplyName(supplierImport.getApplySupplyName().trim());
                    supplyCompanyNames.put(supplierImport.getApplySupplyName().trim(), new SupplyCompany());
                }
            }
            return this;
        }

        private CheckSupply checkSupplyUpdate(){
            if(StringUtils.isBlank(supplierImport.getApplySupplyCode())){
                error.add("供应商编码不能为空");
            }else {
                SupplyCompany supplyCompany1 = supplyCompanyCodes.get(supplierImport.getApplySupplyCode().trim());
                if(Objects.isNull(supplyCompany1)){
                    error.add("供应商编码不存在");
                }else {
                    if(StringUtils.isBlank(supplierImport.getApplySupplyName())){
                        error.add("供应商名称不能为空");
                    }else {
                        boolean equals = supplyCompany1.getSupplyName().equals(supplierImport.getApplySupplyName().trim());
                        if (!equals) {
                            SupplyCompany supplyCompany = supplyCompanyNames.get(supplierImport.getApplySupplyName().trim());
                            if (Objects.nonNull(supplyCompany)) {
                                error.add("供应商名称重复");
                            }
                        }
                        reqVO.setApplySupplyCode(supplyCompany1.getSupplyCode());
                        reqVO.setApplySupplyName(supplierImport.getApplySupplyName().trim());
                    }
                }
            }
            return this;
        }

        private CheckSupply checkCommonData(){
            //供应商类型
            if(StringUtils.isBlank(supplierImport.getApplySupplyType())){
                error.add("供应商类型不能为空");
            }else {
                SupplierDictionaryInfo info = dictionaryInfoList.get(supplierImport.getApplySupplyType().trim());
                if(Objects.isNull(info)){
                    error.add("未找到对应的供应商类型");
                }else {
                    reqVO.setApplySupplyType(info.getSupplierDictionaryValue());
                    reqVO.setApplySupplyTypeName(info.getSupplierContent());
                }
            }
            //供应商集团
            if(StringUtils.isNotBlank(supplierImport.getSupplierName())){
                Supplier supplier = supplierList.get(supplierImport.getSupplierName().trim());
                if(Objects.isNull(supplier)){
                    error.add("未找到对应名称的供应商集团");
                }else {
                    reqVO.setSupplierCode(supplier.getSupplierCode());
                    reqVO.setSupplierName(supplier.getSupplierName());
                }
            }
            //简称
            if(StringUtils.isBlank(supplierImport.getApplyAbbreviation())){
                error.add("简称不能为空");
            }else {
                if(!Constraint.ckChnLetterAndNumAndChar(supplierImport.getApplyAbbreviation().trim())){
                    error.add("简称不能输入特殊字符");
                }
                reqVO.setApplyAbbreviation(supplierImport.getApplyAbbreviation().trim());
            }
            //电话
            if(StringUtils.isNotBlank(supplierImport.getPhone())){
                reqVO.setPhone(supplierImport.getPhone());
            }
            //传真
            if(StringUtils.isNotBlank(supplierImport.getFax())){
                reqVO.setFax(supplierImport.getFax());
            }
            if (StringUtils.isBlank(supplierImport.getProvinceName())) {
                error.add("省不能为空");
            } else {
                SpecialArea specialArea = SpecialArea.getAll().get(supplierImport.getProvinceName());
                if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                    if (StringUtils.isBlank(supplierImport.getCityName())) {
                        error.add("市不能为空");
                    }else {
                        checkArea(supplierImport.getProvinceName(), supplierImport.getCityName(), supplierImport.getDistrictName(), CheckAreaEnum.普通省市县);
                    }
                }
                if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                    if (StringUtils.isBlank(supplierImport.getCityName())) {
                        error.add("县不能为空");
                    }else {
                        checkArea(supplierImport.getProvinceName(), supplierImport.getCityName(), supplierImport.getDistrictName(), CheckAreaEnum.普通省市县);
                    }
                }
            }
            //详细地址
            if(StringUtils.isBlank(supplierImport.getAddress())){
                error.add("详细地址不能为空");
            }else {
                reqVO.setAddress(supplierImport.getAddress().trim());
            }
            //邮编
            if(StringUtils.isNotBlank(supplierImport.getZipCode())){
                reqVO.setZipCode(supplierImport.getZipCode().trim());
            }
            //邮箱
            if(StringUtils.isBlank(supplierImport.getEmail())){
                error.add("邮箱不能为空");
            }else {
                reqVO.setEmail(supplierImport.getEmail().trim());
            }
            //公司网址
            if(StringUtils.isNotBlank(supplierImport.getCompanyWebsite())){
                reqVO.setCompanyWebsite(supplierImport.getCompanyWebsite().trim());
            }
            //税号
            if(StringUtils.isBlank(supplierImport.getTaxId())){
                error.add("税号不能为空");
            }else {
                reqVO.setTaxId(supplierImport.getTaxId().trim());
            }
            //注册资金
            if(StringUtils.isBlank(supplierImport.getRegisteredCapital())){
                error.add("注册资金不能为空");
            }else {
                try {
                    reqVO.setRegisteredCapital(NumberConvertUtils.stringParseBigDecimal(supplierImport.getRegisteredCapital()));
                } catch (NumberFormatException e) {
                    error.add("注册资金格式不正确");
                }
            }
            //法人代表
            if(StringUtils.isBlank(supplierImport.getCorporateRepresentative())){
                error.add("法人代表不能为空");
            }else {
                reqVO.setCorporateRepresentative(supplierImport.getCorporateRepresentative());
            }
            if(StringUtils.isBlank(supplierImport.getContactName())){
                error.add("联系人姓名不能为空");
            }else {
                reqVO.setContactName(supplierImport.getContactName());
            }
            if(StringUtils.isBlank(supplierImport.getMobilePhone())){
                error.add("手机号不能为空");
            }else {
                if(!Constraint.ckCountNum(11,supplierImport.getMobilePhone())){
                    error.add("手机号码格式不正确");
                } else {
                    reqVO.setMobilePhone(supplierImport.getMobilePhone());
                }
            }
            boolean flag = true;
            if(StringUtils.isBlank(supplierImport.getMinOrderAmount())){
                error.add("最低订货金额不能为空");
                flag = false;
            }
            if(StringUtils.isBlank(supplierImport.getMaxOrderAmount())){
                error.add("最高订货金额不能为空");
                flag = false;
            }
            if (flag) {
                try {
                    String minOrderAmount = supplierImport.getMinOrderAmount();
                    String maxOrderAmount = supplierImport.getMaxOrderAmount();
                    long l = NumberConvertUtils.stringParseLong(minOrderAmount);
                    long l2 = NumberConvertUtils.stringParseLong(maxOrderAmount);
                    if(l<0){
                        error.add("最小起订金额不能小于0");
                    }
                    if(l2<0){
                        error.add("最大起订金额不能小于0");
                    }
                    if (l > l2) {
                        error.add("最小起订金额不能大于最大起订金额");
                    }
                    reqVO.setMinOrderAmount(l);
                    reqVO.setMaxOrderAmount(l2);
                } catch (NumberFormatException e) {
                    error.add("最小起订金额或最大起订金额格式不正确");
                }
            }
            //发货省市区
            if(StringUtils.isBlank(supplierImport.getSendProvinceName())){
                error.add("发货省不能为空");
            }else {
                SpecialArea specialArea = SpecialArea.getAll().get(supplierImport.getSendProvinceName());
                if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                    if(StringUtils.isBlank(supplierImport.getSendCityName())){
                        error.add("发货市不能为空");
                    }else {
                        checkArea(supplierImport.getSendProvinceName(), supplierImport.getSendCityName(), supplierImport.getSendDistrictName(), CheckAreaEnum.发货省市县);
                    }
                }
                if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                    if(StringUtils.isBlank(supplierImport.getSendDistrictName())){
                        error.add("发货县不能为空");
                    }else {
                        checkArea(supplierImport.getSendProvinceName(), supplierImport.getSendCityName(), supplierImport.getSendDistrictName(), CheckAreaEnum.发货省市县);
                    }
                }

            }
            //详细地址
            if(StringUtils.isBlank(supplierImport.getSendingAddress())){
                error.add("发货详细地址不能为空");
            }else {
                sendVO.setSendingAddress(supplierImport.getSendingAddress().trim());
            }
            //发货至
            if(StringUtils.isBlank(supplierImport.getSendTo())){
                error.add("发货至不能为空");
            }else {
                SupplierDictionaryInfo info1 = dictionaryInfoList.get(supplierImport.getSendTo().trim());
                if(Objects.isNull(info1)){
                    error.add("未找到对应的发货至选项");
                }else {
                    sendVO.setSendTo(info1.getSupplierDictionaryValue());
                    sendVO.setSendToDesc(supplierImport.getSendTo().trim());
                }
            }
            //发货天数
            if (StringUtils.isNotBlank(supplierImport.getDeliveryDays())) {
                try {
                    String deliveryDays = supplierImport.getDeliveryDays();
                    if (StringUtils.isNotBlank(deliveryDays)) {
                        sendVO.setDeliveryDays(Long.valueOf(deliveryDays));
                    }
                } catch (NumberFormatException e) {
                    error.add("发货天数格式不正确");
                }
            }
            //收货省市区
            if(StringUtils.isBlank(supplierImport.getReturnProvinceName())){
                error.add("收货省不能为空");
            }else {
                SpecialArea specialArea = SpecialArea.getAll().get(supplierImport.getReturnProvinceName());
                if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
                    if(StringUtils.isBlank(supplierImport.getReturnCityName())){
                        error.add("收货市不能为空");
                    }else {
                        checkArea(supplierImport.getReturnProvinceName(), supplierImport.getReturnCityName(), supplierImport.getReturnDistrictName(), CheckAreaEnum.退货省市县);
                    }
                }
                if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
                    if(StringUtils.isBlank(supplierImport.getReturnDistrictName())){
                        error.add("收货县不能为空");
                    }else {
                        checkArea(supplierImport.getReturnProvinceName(), supplierImport.getReturnCityName(), supplierImport.getReturnDistrictName(), CheckAreaEnum.退货省市县);
                    }
                }
            }
            //详细地址
            if(StringUtils.isBlank(supplierImport.getReturningAddress())){
                error.add("收货详细地址不能为空");
            }else {
                returnVO.setSendingAddress(supplierImport.getReturningAddress().trim());
            }
            //收货至
            if(StringUtils.isBlank(supplierImport.getReturnTo())){
                error.add("收货至不能为空");
            }else {
                SupplierDictionaryInfo info1 = dictionaryInfoList.get(supplierImport.getReturnTo().trim());
                if(Objects.isNull(info1)){
                    error.add("未找到对应的收货至选项");
                }else {
                    returnVO.setSendTo(info1.getSupplierDictionaryValue());
                    returnVO.setSendToDesc(supplierImport.getReturnTo().trim());
                }
            }
            //收货天数
            if (StringUtils.isNotBlank(supplierImport.getReturnDays())) {
                try {
                    String deliveryDays = supplierImport.getReturnDays();
                    if (StringUtils.isNotBlank(deliveryDays)) {
                        returnVO.setDeliveryDays(Long.valueOf(deliveryDays));
                    }
                } catch (NumberFormatException e) {
                    error.add("收货天数格式不正确");
                }
            }
            sendVO.setDeliveryType((byte) 0);
            returnVO.setDeliveryType((byte) 1);
            return this;
        }
        private void checkArea(String province, String city, String district,CheckAreaEnum checkAreaEnum) {
            SpecialArea specialArea = SpecialArea.getAll().get(province);
            if (Objects.nonNull(specialArea)) {
                if (specialArea.getHasCity()) {
                    AreaInfo areaInfo1 = areaTree.get(province);
                    if(Objects.isNull(areaInfo1)){
                        error.add(checkAreaEnum.getProvince());
                    }else {
                        if (checkAreaEnum.getType() == 1) {
                            reqVO.setProvinceId(areaInfo1.getCode());
                            reqVO.setProvinceName(province);
                        } else if (checkAreaEnum.getType() == 2) {
                            sendVO.setSendProvinceId(areaInfo1.getCode());
                            sendVO.setSendProvinceName(province);
                        } else if (checkAreaEnum.getType() == 3) {
                            returnVO.setSendProvinceId(areaInfo1.getCode());
                            returnVO.setSendProvinceName(province);
                        }
                    }
                    AreaInfo areaInfo = areaTree.get(province+city);
                    if(Objects.isNull(areaInfo)||!areaInfo.getParentName().equals(province)){
                        error.add(checkAreaEnum.getCity());
                    }else {
                        if (checkAreaEnum.getType() == 1) {
                            reqVO.setCityId(areaInfo.getCode());
                            reqVO.setCityName(city);
                        } else if (checkAreaEnum.getType() == 2) {
                            sendVO.setSendCityId(areaInfo.getCode());
                            sendVO.setSendCityName(city);
                        } else if (checkAreaEnum.getType() == 3) {
                            returnVO.setSendCityId(areaInfo.getCode());
                            returnVO.setSendCityName(city);
                        }
                    }

                }else{
                    AreaInfo areaInfo = areaTree.get(province);
                    if(Objects.isNull(areaInfo)){
                        error.add(checkAreaEnum.getProvince());
                    }else {
                        if (checkAreaEnum.getType() == 1) {
                            reqVO.setProvinceId(areaInfo.getCode());
                            reqVO.setProvinceName(province);
                        } else if (checkAreaEnum.getType() == 2) {
                            sendVO.setSendProvinceId(areaInfo.getCode());
                            sendVO.setSendProvinceName(province);
                        } else if (checkAreaEnum.getType() == 3) {
                            returnVO.setSendProvinceId(areaInfo.getCode());
                            returnVO.setSendProvinceName(province);
                        }
                    }
                }
            }else {
                AreaInfo areaInfo1 = areaTree.get(province);
                if(Objects.isNull(areaInfo1)){
                    error.add(checkAreaEnum.getProvince());
                }else {
                    if (checkAreaEnum.getType() == 1) {
                        reqVO.setProvinceId(areaInfo1.getCode());
                        reqVO.setProvinceName(province);
                    } else if (checkAreaEnum.getType() == 2) {
                        sendVO.setSendProvinceId(areaInfo1.getCode());
                        sendVO.setSendProvinceName(province);
                    } else if (checkAreaEnum.getType() == 3) {
                        returnVO.setSendProvinceId(areaInfo1.getCode());
                        returnVO.setSendProvinceName(province);
                    }
                }
                AreaInfo areaInfo = areaTree.get(province+city);
                if (Objects.isNull(areaInfo)) {
                    error.add(checkAreaEnum.getCity());
                } else {
                    if (checkAreaEnum.getType() == 1) {
                        reqVO.setCityId(areaInfo.getCode());
                        reqVO.setCityName(city);
                    } else if (checkAreaEnum.getType() == 2) {
                        sendVO.setSendCityId(areaInfo.getCode());
                        sendVO.setSendCityName(city);
                    } else if (checkAreaEnum.getType() == 3) {
                        returnVO.setSendCityId(areaInfo.getCode());
                        returnVO.setSendCityName(city);
                    }
                }
                AreaInfo areaInfo2 = areaTree.get(province+city+district);
                if(Objects.isNull(areaInfo2)){
                    error.add(checkAreaEnum.getDis());
                }else {
                    if (checkAreaEnum.getType() == 1) {
                        reqVO.setDistrictId(areaInfo2.getCode());
                        reqVO.setDistrictName(district);
                    } else if (checkAreaEnum.getType() == 2) {
                        sendVO.setSendDistrictId(areaInfo2.getCode());
                        sendVO.setSendDistrictName(city);
                    } else if (checkAreaEnum.getType() == 3) {
                        returnVO.setSendDistrictId(areaInfo2.getCode());
                        returnVO.setSendDistrictName(city);
                    }
                }
            }
        }
    }
}
