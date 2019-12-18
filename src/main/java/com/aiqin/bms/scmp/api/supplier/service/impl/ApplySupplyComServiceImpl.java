package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
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
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.*;
import com.aiqin.bms.scmp.api.supplier.mapper.*;
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
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
public class ApplySupplyComServiceImpl extends BaseServiceImpl implements ApplySupplyComService, WorkFlowHelper {
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
    private SupplierDao supplierDao;
    @Autowired
    private AreaBasicInfoService areaBasicInfoService;
    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;
    @Autowired
    private DeliveryInfoService deliveryInfoService;
    @Autowired
    private ApplySupplyCompanyPurchaseGroupMapper applySupplyCompanyPurchaseGroupMapper;
    @Autowired
    private SupplyCompanyPurchaseGroupMapper supplyCompanyPurchaseGroupMapper;
    @Autowired
    private ApplyDeliveryInformationMapper applyDeliveryInformationMapper;
    @Autowired
    private DeliveryInformationMapper deliveryInformationMapper;
    @Autowired
    private SupplierFileMapper supplierFileMapper;
    @Autowired
    private ApprovalFileInfoService approvalFileInfoService;


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
          throw new GroundRuntimeException("名字在申请表中存在,无法添加");
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("name",applySupplyCompanyReqVO.getApplySupplyName());
        map1.put("code",applySupplyCompanyReqVO.getApplySupplyCode());
        map1.put("companyCode",companyCode);
        int nameCount1 = supplyCompanyDao.checkName2(map1);
        if (nameCount1 > 0){
            throw new GroundRuntimeException("名字在正式表中存在,无法添加");
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
                approvalFileInfoService.batchSave(applySupplyCompanyReqVO.getApprovalFileInfos(),applySupplyCompanyReqDTO.getApplyCode(),applySupplyCompanyReqDTO.getFormNo(),ApprovalFileTypeEnum.SUPPLIER.getType());
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
            //查申请表
            Map<String,Object> map1 = new HashMap<>();
            map1.put("name",applySupplyCompanyReqVO.getApplySupplyName());
            map1.put("code",applySupplyCompanyReqVO.getApplySupplyCode());
            map1.put("companyCode",companyCode);
            int nameCount1 = applySupplyCompanyDao.checkName(map1);
            if (nameCount1 > 0){
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
//            List<ApplySupplyCompany> forImportList = applySupplyCompanyMapper.selectBySupplyCode(codes, getUser().getCompanyCode(),ApplyStatus.PENDING_SUBMISSION.getNumber());
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
            if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyReqVO.getPurchaseGroupVos())){
                applySupplyCompany.setPurchasingGroupCode(StringUtils.join( applySupplyCompanyReqVO.getPurchaseGroupVos().
                        stream().map(ApplySupplyCompanyPurchaseGroupReqVo :: getPurchasingGroupCode).collect(Collectors.toList()),","));
                applySupplyCompany.setPurchasingGroupName(StringUtils.join( applySupplyCompanyReqVO.getPurchaseGroupVos().
                        stream().map(ApplySupplyCompanyPurchaseGroupReqVo :: getPurchasingGroupName).collect(Collectors.toList()),","));
            }
            num = ((ApplySupplyComService)AopContext.currentProxy()).insert(applySupplyCompany);
            String content = ApplyStatus.PENDING.getContent().replace(Global.CREATE_BY, applySupplyCompany.getUpdateBy()).replace(Global.APPLY_TYPE, "修改");
            //存日志
            supplierCommonService.getInstance(applySupplyCompany.getApplyCode()+"", HandleTypeCoce.PENDING.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(),content,null,HandleTypeCoce.PENDING.getName());
            //修改编码
            encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
            //更新正式表的申请编码
            supplyCompanyMapper.updateApplyCodeBySupplyComCode(applySupplyCompany.getSupplyCompanyCode(),applySupplyCompany.getApplyCode());
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
            List<ApplyDeliveryDTO> deliveryDTOS = BeanCopyUtils.copyList(applySupplyCompanyReqVO.getDeliveryInfoList(), ApplyDeliveryDTO.class);
            deliveryDTOS.forEach(item->{
                item.setApplySupplyCompanyName(applySupplyCompany.getApplySupplyName());
                item.setApplySupplyCompanyCode(applySupplyCompany.getApplyCode());
            });
            applyDeliveryService.insideSaveBatchApply(deliveryDTOS);
            //增加文件信息
            List<SupplierFileReqVO> fileReqVOList = applySupplyCompanyReqVO.getFileReqVOList();
            if(CollectionUtils.isNotEmptyCollection(fileReqVOList)){
                fileReqVOList.forEach(item->{
                    item.setApplySupplierCode(applySupplyCompany.getApplyCode());
                    item.setApplySupplierName(applySupplyCompany.getApplySupplyName());
                    item.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
                });
                applySupplierFileService.copySaveInfo(fileReqVOList);
            }
            //采购组
            List<ApplySupplyCompanyPurchaseGroupReqVo> applyPurchaseGroupVos = applySupplyCompanyReqVO.getPurchaseGroupVos();
            if(CollectionUtils.isNotEmptyCollection(applyPurchaseGroupVos)){
                List<ApplySupplyCompanyPurchaseGroup> applySupplyCompanyPurchaseGroups = BeanCopyUtils.copyList(applyPurchaseGroupVos, ApplySupplyCompanyPurchaseGroup.class);
                applySupplyCompanyPurchaseGroups.forEach(item->{
                    item.setApplySupplyCompanyName(applySupplyCompany.getApplySupplyName());
                    item.setApplySupplyCompanyCode(applySupplyCompany.getApplyCode());
                });
                ((ApplySupplyComService) AopContext.currentProxy()).saveApplyPurchaseGroupList(applySupplyCompanyPurchaseGroups);
            }
//            if (CollectionUtils.isNotEmptyCollection(forImportList)) {
//                applyDeliveryInfoDao.deleteBatch(forImportList.get(0).getApplySupplyCompanyCode());
//                applySupplierFileDao.deleteApplySupplierFileByApplyCode(forImportList.get(0).getApplySupplyCompanyCode());
//                applySupplyCompanyPurchaseGroupMapper.deleteByApplyCode(forImportList.get(0).getApplySupplyCompanyCode());
//                applySupplyCompanyMapper.delectByIds(forImportList.stream().map(ApplySupplyCompany::getId).collect(Collectors.toList()));
//            }
            if(!Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                approvalFileInfoService.batchSave(applySupplyCompanyReqVO.getApprovalFileInfos(),applySupplyCompany.getApplyCode(),applySupplyCompany.getFormNo(),ApprovalFileTypeEnum.SUPPLIER.getType());
                workFlow(applySupplyCompany);
            }
        } catch (Exception e){
            log.error(Global.ERROR, e);
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
        return num;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveImport(ApplySupplyCompanyReqVO applySupplyCompanyReqVO){
        //验证名称
        validateName(applySupplyCompanyReqVO);
        //检验重复
        ApplySupplyCompany copy = BeanCopyUtils.copy(applySupplyCompanyReqVO, ApplySupplyCompany.class);
        //查询最近审批通过的一条数据
//        ApplySupplyCompany newest = applySupplyCompanyDao.selectBySupplyCodeForNewest(getUser().getCompanyCode(),applySupplyCompanyReqVO.getApplySupplyCode(),2);
        ApplySupplyCompany newest = supplyCompanyDao.selectBySupplierCode2(applySupplyCompanyReqVO.getApplySupplyCode());
        String oldCode = newest.getSupplyCompanyCode();
        List<ApplySupplyCompany> repeatList = applySupplyCompanyDao.selectByCode2(oldCode);
        if (CollectionUtils.isNotEmptyCollection(repeatList)) {
            throw new BizException(ResultCode.UN_SUBMIT_APPROVAL);
        }
        //供货单位申请编码
        if (Objects.isNull(newest)) {
            log.error("数据异常，无法找到已审核通过的数据");
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
        String code = null;
        synchronized (ApplySupplyComServiceImpl.class){
            //供货单位申请编码
            EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.APPLY_SUPPLY_COM_CODE);
            code= String.valueOf(encodingRule.getNumberingValue()+1);
            //修改编码
            encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        }
//        String code = getCode("", EncodingRuleType.APPLY_SUPPLY_COM_CODE);

        //非空复制主表信息
        ApplySupplyCompany saveVO = BeanCopyUtils.copyValueWithoutNull(copy, newest);
        //补充数据
        saveVO.setApplySupplyCompanyCode(code);
        saveVO.setUpdateBy(getUser().getPersonName());
        saveVO.setUpdateTime(new Date());
        saveVO.setApplyStatus((byte) 5);
        saveVO.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
        //补充发货信息
//        List<ApplyDeliveryInformation> deliverys = applyDeliveryInformationMapper.selectByApplyCode(oldCode);
        List<ApplyDeliveryInformation> deliverys = deliveryInformationMapper.selectByCode(oldCode);
        //对比
        List<ApplyDeliveryInformation> list = compareValue(applySupplyCompanyReqVO.getDeliveryInfoList(),deliverys);
        String finalCode = code;
        list.forEach(o->{o.setApplySupplyCompanyCode(finalCode);o.setApplyCode(finalCode);o.setApplySupplyCompanyName(saveVO.getApplySupplyName());o.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());o.setApplyStatus((byte) 0);});
        //补充采购组
        List<ApplySupplyCompanyPurchaseGroupReqVo> purchaseGroupVos = applySupplyCompanyReqVO.getPurchaseGroupVos();
        List<ApplySupplyCompanyPurchaseGroup> saveGroup = BeanCopyUtils.copyList(purchaseGroupVos, ApplySupplyCompanyPurchaseGroup.class);
        saveGroup.forEach(o->{o.setApplySupplyCompanyCode(finalCode);o.setApplySupplyCompanyName(saveVO.getApplySupplyName());});
        //补充文件信息
//        List<SupplierFileReqVO> fileReqVOList = applySupplierFileService.selectByApplyCode(oldCode);
        List<SupplierFileReqVO> fileReqVOList = supplierFileMapper.selectByCode(oldCode);
        if(CollectionUtils.isNotEmptyCollection(fileReqVOList)){
            fileReqVOList.forEach(item->{
                item.setApplySupplierCode(saveVO.getApplySupplyCompanyCode());
                item.setApplySupplierName(saveVO.getApplySupplyName());
                item.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
            });
            applySupplierFileService.copySaveInfo(fileReqVOList);
        }
        //保存
        //主表
        applySupplyCompanyMapper.insert(saveVO);
        //发货信息
        if (CollectionUtils.isNotEmptyCollection(list)) {
            applyDeliveryInformationMapper.insertBatch(list);
        }
        if(CollectionUtils.isNotEmptyCollection(saveGroup)){
            ((ApplySupplyComService) AopContext.currentProxy()).saveApplyPurchaseGroupList(saveGroup);
        }
        //存日志
        supplierCommonService.getInstance(code+"", HandleTypeCoce.PENDING.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(), null,null,HandleTypeCoce.PENDING.getName());
    }

    /**
     * 这里做全替换。如果退货和发货有一个没有。那么全体换
     * @param deliveryInfoList
     * @param deliverys
     * @return
     */
    private List<ApplyDeliveryInformation> compareValue(List<ApplyDeliveryInfoReqVO> deliveryInfoList, List<ApplyDeliveryInformation> deliverys) {
        List<ApplyDeliveryInformation> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmptyCollection(deliveryInfoList)) {
            for (ApplyDeliveryInfoReqVO o : deliveryInfoList) {
                deliverys = deliverys.stream().filter(o1 -> !o.getDeliveryType().equals(o1.getDeliveryType())).collect(Collectors.toList());
                list.add(BeanCopyUtils.copy(o, ApplyDeliveryInformation.class));
            }
        }
        list.addAll(deliverys);
        return list;

    }

    public void validateName(ApplySupplyCompanyReqVO applySupplyCompanyReqVO) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",applySupplyCompanyReqVO.getApplySupplyName());
        map.put("code",applySupplyCompanyReqVO.getApplySupplyCode());
        map.put("companyCode",getUser().getCompanyCode());
        int nameCount = supplyCompanyDao.checkName(map);
        if (nameCount > 0){
            throw new BizException(ResultCode.NAME_REPEAT);
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("name",applySupplyCompanyReqVO.getApplySupplyName());
        map1.put("code",applySupplyCompanyReqVO.getApplySupplyCode());
        map1.put("companyCode",getUser().getCompanyCode());
        int nameCount1 = applySupplyCompanyDao.checkName(map1);
        if (nameCount1 > 0){
            throw new BizException(ResultCode.NAME_REPEAT);
        }
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
        WorkFlowVO workFlowVO = new WorkFlowVO();
//        workFlowVO.setPositionCode(applySupplyCompanyReqDTO.getPositionCode());
        workFlowVO.setFormUrl(workFlowBaseUrl.applySupplierUrl + "?applyType=" + applySupplyCompanyReqDTO.getApplyType() + "&applyCode=" + applySupplyCompanyReqDTO.getApplyCode() + "&id=" + applySupplyCompanyReqDTO.getId() + "&itemCode=1" + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(applySupplyCompanyReqDTO.getFormNo());
        WorkFlow workFlow;
        if (Objects.equals(applySupplyCompanyReqDTO.getApplyType(), StatusTypeCode.ADD_APPLY.getStatus())) {
            workFlow = WorkFlow.APPLY_COMPANY;
        } else {
            workFlow = WorkFlow.APPLY_COMPANY_REVISE;
        }
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + workFlow.getNum());
        workFlowVO.setTitle(applySupplyCompanyReqDTO.getApprovalName());
        workFlowVO.setRemark(applySupplyCompanyReqDTO.getApprovalRemark());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId", applySupplyCompanyReqDTO.getDirectSupervisorCode());
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, workFlow);
        if (workFlowRespVO.getSuccess()) {
            ApplySupplyCompany applySupplyCompany = new ApplySupplyCompany();
            applySupplyCompany.setId(applySupplyCompanyReqDTO.getId());
            //状态变为审核中
            applySupplyCompany.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
            applySupplyCompany.setFormNo(applySupplyCompanyReqDTO.getFormNo());
            int i = applySupplyCompanyMapper.updateByPrimaryKeySelective(applySupplyCompany);

            SupplyCompany oldComByApplyCode = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompanyReqDTO.getApplyCode());
            if (null != oldComByApplyCode) {
                SupplyCompany supplyCompany = new SupplyCompany();
                supplyCompany.setId(oldComByApplyCode.getId());
                supplyCompany.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
                supplyCompanyMapper.updateByPrimaryKeySelective(supplyCompany);
            }
            List<ApplyDeliveryInformation> oldApplyDeliveryInfo = applyDeliveryInfoDao.getApplyDeliveryInfo(applySupplyCompanyReqDTO.getApplyCode());
            if (CollectionUtils.isNotEmptyCollection(oldApplyDeliveryInfo)) {
                oldApplyDeliveryInfo.forEach(item -> {
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
            if (null != oldApplySupplyComAcct) {
                ApplySupplyCompanyAccount newApplySupplyComAcct = new ApplySupplyCompanyAccount();
                newApplySupplyComAcct.setId(oldApplySupplyComAcct.getId());
                newApplySupplyComAcct.setApplyStatus(StatusTypeCode.REVIEW_STATUS.getStatus());
                applySupplyCompanyAccountMapper.updateByPrimaryKeySelective(newApplySupplyComAcct);
            }
            if (i <= 0) {
                throw new GroundRuntimeException("审核状态修改失败");
            }
            String content = ApplyStatus.APPROVAL.getContent().replace(Global.CREATE_BY, applySupplyCompanyReqDTO.getUpdateBy()).replace(Global.APPLY_TYPE, applySupplyCompanyReqDTO.getApprovalName());
            //存日志
            supplierCommonService.getInstance(applySupplyCompanyReqDTO.getApplyCode() + "", HandleTypeCoce.APPROVAL.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(), content, null, HandleTypeCoce.APPROVAL.getName());
        } else {
            //存调用失败的日志
            String msg = workFlowRespVO.getMsg();
            throw new GroundRuntimeException(msg);
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
        ApplySupplyComDetailRespVO applySupplyComDetailRespVO = new ApplySupplyComDetailRespVO();
        ApplyInfoRespVO applyInfoRespVO = new ApplyInfoRespVO();
        ApplySupplyComInfoRespVO applySupplyComInfoRespVO = new ApplySupplyComInfoRespVO();
        ApplySupplyComAcctInfoRespVO applySupplyComAcctInfoRespVO = new ApplySupplyComAcctInfoRespVO();

        ApplySupplyComDetailDTO applySupplyComDetailDTO = applySupplyCompanyDao.getApplySupplyComDetail(applyCode);
        BeanCopyUtils.copy(applySupplyComDetailDTO, applyInfoRespVO);
        BeanCopyUtils.copy(applySupplyComDetailDTO, applySupplyComInfoRespVO);
        if (null != applyInfoRespVO) {
            //获取操作日志
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setPageNo(1);
            operationLogVo.setPageSize(100);
            operationLogVo.setObjectType(ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus());
            operationLogVo.setObjectId(applyCode);
            BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo, 62);
            List<LogData> logDataList = new ArrayList<>();
            if (null != pageList.getDataList() && pageList.getDataList().size() > 0) {
                logDataList = pageList.getDataList();
            }
            applyInfoRespVO.setModelType("供应商");
            applyInfoRespVO.setModelTypeCode("1");
            applyInfoRespVO.setStatus(applySupplyComDetailDTO.getApplyStatus().toString());
            applyInfoRespVO.setApprovalFileInfos(approvalFileInfoService.selectByApprovalTypeAndApplyCode(ApprovalFileTypeEnum.SUPPLIER.getType(), applyInfoRespVO.getApplyCode()));
            applySupplyComDetailRespVO.setLogDataList(logDataList);
            applySupplyComDetailRespVO.setApplyInfoRespVO(applyInfoRespVO);
        }
        if (null != applySupplyComInfoRespVO) {
            applySupplyComDetailRespVO.setApplySupplyComInfoRespVO(applySupplyComInfoRespVO);
        }
        if (StatusTypeCode.ADD_ACCOUNT.getStatus().equals(applySupplyComDetailDTO.getAddAccount())) {
            //查询账户信息
            ApplySupplyCompanyAccount applySupplyComAcct = applySupplyCompanyAcctDao.getApplySupplyComAcct(applyCode);
            if (null != applySupplyComAcct) {
                BeanCopyUtils.copy(applySupplyComAcct, applySupplyComAcctInfoRespVO);
                if (null != applySupplyComAcctInfoRespVO) {
                    applySupplyComAcctInfoRespVO.setAcctMaxPaymentAmount(applySupplyComAcct.getMaxPaymentAmount());
                    applySupplyComDetailRespVO.setApplySupplyComAcctInfoRespVO(applySupplyComAcctInfoRespVO);
                }
            }
            applySupplyComDetailRespVO.setShowAccount(StatusTypeCode.SHOW_ACCOUNT.getStatus());
        } else {
            applySupplyComDetailRespVO.setShowAccount(StatusTypeCode.NOT_SHOW_ACCOUNT.getStatus());
        }
        //查询文件信息
        List<ApplySupplierFile> applySupplierFiles = applySupplierFileDao.getApplySupplierFile(applyCode);
        applySupplyComDetailRespVO.setApplySupplierFileList(applySupplierFiles);
        //根据供应商申请编码获取发货/退后申请信息
        List<ApplyDeliveryInfoRespVO> applyDeliveryInfoRespVO = applyDeliveryInfoDao.getApplyDeliveryInfoDetail(applyCode);
        applySupplyComDetailRespVO.setApplyDeliveryInfoRespVO(applyDeliveryInfoRespVO);
        //采购组
        List<ApplySupplyCompanyPurchaseGroup> applySupplyCompanyPurchaseGroups = applySupplyCompanyPurchaseGroupMapper.selectByApplySupplyCompanyCode(applyCode);
        if (CollectionUtils.isNotEmptyCollection(applySupplyCompanyPurchaseGroups)) {
            List<SupplyCompanyPurchaseGroupResVo> purchaseGroupResVos = BeanCopyUtils.copyList(applySupplyCompanyPurchaseGroups, SupplyCompanyPurchaseGroupResVo.class);
            applySupplyComDetailRespVO.setPurchaseGroupVos(purchaseGroupResVos);
        }
        return applySupplyComDetailRespVO;

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
        Long resultNum = 0L;
        String applySupplyCompanyCode = null;
        //供货单位申请编码
        EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.APPLY_SUPPLY_COM_CODE);
        applySupplyCompanyReqDTO.setApplyCode(String.valueOf(encodingRule.getNumberingValue()+1));
        if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyReqDTO.getPurchaseGroupVos())){
            applySupplyCompanyReqDTO.setPurchasingGroupCode(StringUtils.join( applySupplyCompanyReqDTO.getPurchaseGroupVos().
                    stream().map(ApplySupplyCompanyPurchaseGroupReqVo :: getPurchasingGroupCode).collect(Collectors.toList()),","));
            applySupplyCompanyReqDTO.setPurchasingGroupName(StringUtils.join( applySupplyCompanyReqDTO.getPurchaseGroupVos().
                    stream().map(ApplySupplyCompanyPurchaseGroupReqVo :: getPurchasingGroupName).collect(Collectors.toList()),","));
        }
        //新增供货单位申请
        resultNum = ((ApplySupplyComService)AopContext.currentProxy()).insert(applySupplyCompanyReqDTO);
        ApplyStatus applyStatus = ApplyStatus.getApplyStatusByNumber(applySupplyCompanyReqDTO.getApplyStatus());
        String content =applyStatus.getContent().replace(Global.CREATE_BY, applySupplyCompanyReqDTO.getCreateBy()).replace(Global.APPLY_TYPE, "新增");
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
            log.error(Global.ERROR, e);
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
        //增加采购组
        if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyReqDTO.getPurchaseGroupVos())){
            List<ApplySupplyCompanyPurchaseGroup> applySupplyCompanyPurchaseGroupReqVos = BeanCopyUtils.copyList( applySupplyCompanyReqDTO.getPurchaseGroupVos(),ApplySupplyCompanyPurchaseGroup.class);
            if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyPurchaseGroupReqVos)){
                applySupplyCompanyPurchaseGroupReqVos.stream().forEach(group ->{
                    group.setApplySupplyCompanyCode(applySupplyCompanyReqDTO.getApplyCode());
                    group.setApplySupplyCompanyName(applySupplyCompanyReqDTO.getApplySupplyName());
                });
                ((ApplySupplyComService) AopContext.currentProxy()).saveApplyPurchaseGroupList(applySupplyCompanyPurchaseGroupReqVos);
            }
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
        HandleTypeCoce applyHandleTypeCoce;
        ApplySupplyCompanyAccount applySupplyCompanyAccount = applySupplyCompanyAcctDao.getApplySupplyComAcct(applySupplyCompany.getApplySupplyCompanyCode());
        applySupplyCompany.setApplyStatus(vo.getApplyStatus());
        if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            applyHandleTypeCoce = HandleTypeCoce.APPROVAL_SUCCESS;
            SupplyCompany oldSupplyCompany = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
            //通过插入正式数据
            SupplyCompany supplyCompany = new SupplyCompany();
            BeanCopyUtils.copy(applySupplyCompany, supplyCompany);
            supplyCompany.setAuditorBy(vo.getApprovalUserName());
            supplyCompany.setAuditorTime(new Date());
            String content;
            HandleTypeCoce handleTypeCoce;
            if (null != oldSupplyCompany) {
                //删除原有的发货/退货信息
                deliveryInfoDao.deleteDeliveryInfoBySupplyCompanyCode(oldSupplyCompany.getSupplyCode());
                //删除原有的附件信息
                supplierFileDao.deleteFile(oldSupplyCompany.getSupplyCode());
                //删除原有的采购组信息
                supplyCompanyPurchaseGroupMapper.deleteBySupplyCompanyCode(oldSupplyCompany.getSupplyCode());
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
                encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
            }
            applySupplyCompany.setSupplyCompanyCode(supplyCompany.getSupplyCode());
            if (applySupplyCompanyAccount != null) {
                applySupplyCompanyAccount.setSupplyCompanyCode(supplyCompany.getSupplyCode());
                applySupplyCompanyAccount.setSupplyCompanyName(supplyCompany.getSupplyName());
            }
            //发货/退货信息
            //插入正式数据
            List<ApplyDeliveryInformation> applyDeliveryInfos = applyDeliveryInfoDao.getApplyDeliveryInfo(applySupplyCompany.getApplySupplyCompanyCode());
            if (CollectionUtils.isNotEmptyCollection(applyDeliveryInfos)) {
                //更新申请表信息
                applyDeliveryInfos.forEach(item -> {
                    item.setAuditorBy(vo.getApprovalUserName());
                    item.setAuditorTime(new Date());
                    item.setApplyStatus(vo.getApplyStatus());
                    item.setApplyDeliveryInformationCode(item.getApplyCode());
                });
                applyDeliveryInfoDao.updateBatch(applyDeliveryInfos);
                List<DeliveryInformation> deliveryInformations = BeanCopyUtils.copyList(applyDeliveryInfos, DeliveryInformation.class);
                if (CollectionUtils.isNotEmptyCollection(deliveryInformations)) {
                    deliveryInformations.forEach(item -> {
                        item.setSupplyCompanyCode(supplyCompany.getSupplyCode());
                        item.setSupplyCompanyName(supplyCompany.getSupplyName());
                    });
                }
                deliveryInfoDao.insertBatch(deliveryInformations);
            }
            //附件信息
            List<ApplySupplierFile> applySupplierFiles = applySupplierFileDao.getApplySupplierFile(applySupplyCompany.getApplySupplyCompanyCode());
            if (CollectionUtils.isNotEmptyCollection(applySupplierFiles)) {
                List<SupplierFile> supplierFiles = BeanCopyUtils.copyList(applySupplierFiles, SupplierFile.class);
                if (CollectionUtils.isNotEmptyCollection(supplierFiles)) {
                    supplierFiles.forEach(item -> {
                        item.setSupplierCode(supplyCompany.getSupplyCode());
                        item.setSupplierName(supplyCompany.getSupplyName());
                    });
                    supplierFileDao.insertFileList(supplierFiles);
                }
            }
            //采购组信息
            List<ApplySupplyCompanyPurchaseGroup> applySupplyCompanyPurchaseGroups = applySupplyCompanyPurchaseGroupMapper.selectByApplySupplyCompanyCode(applySupplyCompany.getApplySupplyCompanyCode());
            if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyPurchaseGroups)){
                List<SupplyCompanyPurchaseGroup> supplyCompanyPurchaseGroups = BeanCopyUtils.copyList(applySupplyCompanyPurchaseGroups, SupplyCompanyPurchaseGroup.class);
                if(CollectionUtils.isNotEmptyCollection(supplyCompanyPurchaseGroups)){
                    supplyCompanyPurchaseGroups.forEach(item->{
                        item.setSupplyCompanyCode(supplyCompany.getSupplyCode());
                        item.setSupplyCompanyName(supplyCompany.getSupplyName());
                    });
                }
                supplyCompanyPurchaseGroupMapper.insertBatch(supplyCompanyPurchaseGroups);
            }
            supplierCommonService.getInstance(supplyCompany.getSupplyCode(), handleTypeCoce.getStatus(), ObjectTypeCode.SUPPLY_COMPANY.getStatus(), content, null, handleTypeCoce.getName(), applySupplyCompany.getCreateBy());
        } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL_FAILED.getNumber())) {
            applyHandleTypeCoce = HandleTypeCoce.APPROVAL_FAILED;
            SupplyCompany oldSupplyCompany = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
            if (null != oldSupplyCompany) {
                SupplyCompany supplyCompany = new SupplyCompany();
                supplyCompany.setId(oldSupplyCompany.getId());
                supplyCompany.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                supplyCompanyMapper.updateByPrimaryKeySelective(supplyCompany);
            }
        } else if (vo.getApplyStatus().equals(ApplyStatus.APPROVAL.getNumber())) {
            applyHandleTypeCoce = HandleTypeCoce.APPROVAL;
        } else if (vo.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber())) {
            applyHandleTypeCoce = HandleTypeCoce.REVOKED;
            SupplyCompany oldSupplyCompany = supplyCompanyDao.getSupplyComByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
            if (null != oldSupplyCompany) {
                SupplyCompany supplyCompany = new SupplyCompany();
                supplyCompany.setId(oldSupplyCompany.getId());
                supplyCompany.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                supplyCompanyMapper.updateByPrimaryKeySelective(supplyCompany);
            }
        } else {
            return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
        }
        if (null != applySupplyCompanyAccount) {
            applySupplyCompanyAccount.setCompanyName(applySupplyCompany.getCompanyName());
            applySupplyCompanyAccount.setCompanyCode(applySupplyCompany.getCompanyCode());
            String result = applySupplyComAcctService.insideWorkFlowCallback(applySupplyCompanyAccount, vo);
            if (result.equals(HandlingExceptionCode.FLOW_CALL_BACK_FALSE)) {
                return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
            }
        }
        applySupplyCompany.setAuditorBy(vo.getApprovalUserName());
        applySupplyCompany.setAuditorTime(new Date());
        applySupplyCompanyMapper.updateByPrimaryKey(applySupplyCompany);
        //判断审核状态，存日志信息
        ApplyStatus applyStatus = ApplyStatus.getApplyStatusByNumber(applySupplyCompany.getApplyStatus());
        if(Objects.equals(ApplyStatus.APPROVAL,applyStatus)){
            applyStatus = ApplyStatus.APPROVAL_SUCCESS;
        }
        String content = applyStatus.getContent().replace(Global.CREATE_BY, applySupplyCompany.getCreateBy()).replace(Global.AUDITOR_BY, vo.getApprovalUserName());
        supplierCommonService.getInstance(applySupplyCompany.getApplySupplyCompanyCode(), applyHandleTypeCoce.getStatus(), ObjectTypeCode.APPLY_SUPPLY_COMPANY.getStatus(), content, null, applyHandleTypeCoce.getName(), vo.getApprovalUserName());
        return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
    }

    @Override
    public List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            querySupplierReqVO.setCompanyCode(authToken.getCompanyCode());
            //querySupplierReqVO.setApplyBy(authToken.getPersonName());
            querySupplierReqVO.setPersonId(authToken.getPersonId());
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
            Map<String, SupplyCompany> stringSupplyCompanyMap = supplyCompanyDao.selectOfficialByCompanyNameList(companyNameList, getUser().getCompanyCode());
            supplyCompanies.putAll(stringSupplyCompanyMap);
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
                        .checkCommonData()
                        .checkAccount();
                applyList.add(checkSupply.getReqVO());
                SupplierImport supplierImport = checkSupply.getSupplierImport();
                String error = supplierImport.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s = "第" + (i + 2) + "行 " + error;
                    supplierImport.setError(s);
                }
                importVos.add(supplierImport);
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
            Map<String, SupplyCompany> supplyCompanies2 = supplyCompanyDao.selectOfficialByCompanyNameList(companyNameList, getUser().getCompanyCode());
            supplyCompanies.putAll(supplyCompanies2);
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
                CheckSupplyForUpdate checkSupply = new CheckSupplyForUpdate(supplierImportNew, areaTree, supplyCompanies, codeList, supplierList, dictionaryInfoList)
                        .checkSupplyUpdate()
                        .checkCommonData();
                applyList.add(checkSupply.getReqVO());
                SupplierImport supplierImport = checkSupply.getSupplierImport();
                String error = supplierImport.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s = "第" + (i + 2) + "行 " + error;
                    supplierImport.setError(s);
                }
                importVos.add(supplierImport);
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
        List<ApplySupplyCompanyPurchaseGroupReqVo> purchaseGroupVos = Lists.newArrayList();
        ApplySupplyCompanyPurchaseGroupReqVo  purchaseGroupReqVo = new ApplySupplyCompanyPurchaseGroupReqVo();
        purchaseGroupReqVo.setPurchasingGroupCode(req.getPurchaseGroupCode());
        purchaseGroupReqVo.setPurchasingGroupName(req.getPurchaseGroupName());
        purchaseGroupVos.add(purchaseGroupReqVo);
        for (ApplySupplyCompanyReqVO reqVO : req.getApplyList()) {
            reqVO.setPurchaseGroupVos(purchaseGroupVos);
            reqVO.setSource(Byte.valueOf("1"));
            ((ApplySupplyComService)AopContext.currentProxy()).saveApply(reqVO);
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
        List<ApplySupplyCompanyPurchaseGroupReqVo> purchaseGroupVos = Lists.newArrayList();
        ApplySupplyCompanyPurchaseGroupReqVo  purchaseGroupReqVo = new ApplySupplyCompanyPurchaseGroupReqVo();
        purchaseGroupReqVo.setPurchasingGroupCode(req.getPurchaseGroupCode());
        purchaseGroupReqVo.setPurchasingGroupName(req.getPurchaseGroupName());
        purchaseGroupVos.add(purchaseGroupReqVo);
        for (ApplySupplyCompanyReqVO reqVO : req.getApplyList()) {
            reqVO.setPurchaseGroupVos(purchaseGroupVos);
            reqVO.setSource(Byte.valueOf("1"));
            reqVO.setPurchasingGroupCode(req.getPurchaseGroupCode());
            reqVO.setPurchasingGroupName(req.getPurchaseGroupName());
            ((ApplySupplyComService)AopContext.currentProxy()).saveImport(reqVO);
        }
        return Boolean.TRUE;
    }

    @Override
    public BasePage<ApplySupplyComApplyListRespVO> applyList(QueryApplySupplyListComReqVO queryApplySupplyComReqVO) {
        queryApplySupplyComReqVO.setCompanyCode(getUser().getCompanyCode());
        queryApplySupplyComReqVO.setPersonId(getUser().getPersonId());
        PageHelper.startPage(queryApplySupplyComReqVO.getPageNo(), queryApplySupplyComReqVO.getPageSize());
        List<ApplySupplyComApplyListRespVO> applySupplierResps = applySupplyCompanyDao.applyList(queryApplySupplyComReqVO);
        return PageUtil.getPageList(queryApplySupplyComReqVO.getPageNo(), applySupplierResps);
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
                List<ApplySupplyCompanyPurchaseGroup> supplyCompanyPurchaseGroups = applySupplyCompanyPurchaseGroupMapper.selectByApplySupplyCompanyCode(supplyCompanyDetailDTO.getSupplyCode());
                if(CollectionUtils.isNotEmptyCollection(supplyCompanyPurchaseGroups)){
                    List<SupplyCompanyPurchaseGroupResVo> purchaseGroupVos = BeanCopyUtils.copyList(supplyCompanyPurchaseGroups, SupplyCompanyPurchaseGroupResVo.class);
                    supplyComDetailRespVO.setPurchaseGroupVos(purchaseGroupVos);
                }
                QuerySupplierComAcctReqVo vo = new QuerySupplierComAcctReqVo();
                vo.setSupplyCompanyCode(supplyComDetailRespVO.getApplySupplyCode());
                List<QuerySupplierComAcctRespVo> querySupplierComAcctRespVos = applySupplyCompanyAccountMapper.selectListByQueryVO(vo);
                supplyComDetailRespVO.setSupplierComAcctRespVos(querySupplierComAcctRespVos);
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"未查询信息"));
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
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
            if (Objects.equals(s.getApplyType().intValue(),1)) {
                //新增
                String applySupplyName = s.getApplySupplyName();
                if (!applySupplyName.equals(applySupplyCompanyReqVO.getApplySupplyName())) {
                    Map<String,Object> map1 = new HashMap<>();
                    map1.put("name",applySupplyCompanyReqVO.getApplySupplyName());
                    map1.put("code",s.getSupplyCompanyCode());
                    map1.put("companyCode",companyCode);
                    int nameCount1 = applySupplyCompanyDao.checkName(map1);
                    if (nameCount1 > 0){
                        throw new BizException(ResultCode.NAME_REPEAT);
                    }
                }
            }else {
                Map<String,Object> map1 = new HashMap<>();
                map1.put("name",applySupplyCompanyReqVO.getApplySupplyName());
                map1.put("code",s.getSupplyCompanyCode());
                map1.put("companyCode",companyCode);
                int nameCount1 = applySupplyCompanyDao.checkName(map1);
                if (nameCount1 > 0){
                    throw new BizException(ResultCode.NAME_REPEAT);
                }
                List<ApplySupplyCompany> repeatList = applySupplyCompanyDao.selectByCode2(s.getSupplyCompanyCode());
                if (CollectionUtils.isNotEmptyCollection(repeatList)) {
                    throw new BizException(ResultCode.UN_SUBMIT_APPROVAL);
                }
            }
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
                    log.error(Global.ERROR, e);
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
            ((ApplySupplyComService)AopContext.currentProxy()).insertData(applySupplyCompany);
            if(StringUtils.isNotBlank(s.getSupplyCompanyCode())){
                int temp = supplyCompanyDao.updateApplyCode(s.getSupplyCompanyCode(),applySupplyCompany.getApplySupplyCompanyCode());
                if(temp!=1){
                    throw new BizException(ResultCode.UPDATE_ERROR);
                }
            }
            //删除旧的
            applySupplyCompanyAccountMapper.deleteByPrimaryKey(applySupplyCompanyReqVO.getApplySupplyCompanyAccountReq().getId());
            //判断是否需要新增供货单位账户申请
            //判断是否需要新增账户信息
            Boolean addAccount = null != applySupplyCompanyReqVO.getAddAccount() && applySupplyCompanyReqVO.getAddAccount().equals(StatusTypeCode.ADD_ACCOUNT.getStatus());
            if(addAccount && Objects.equals(StatusTypeCode.ADD_APPLY.getStatus(),s.getApplyType())){
                ApplySupplyCompanyAcctReqVO applySupplyCompanyAcctReqVO = applySupplyCompanyReqVO.getApplySupplyCompanyAccountReq();
                ApplySupplyComAcctDTO applySupplyComAcctDTO = new ApplySupplyComAcctDTO();
                BeanCopyUtils.copy(applySupplyCompanyAcctReqVO, applySupplyComAcctDTO);
                applySupplyComAcctDTO.setApplySupplyCompanyCode(applySupplyCompanyReqDTO.getApplyCode());
                applySupplyComAcctDTO.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
                applySupplyComAcctDTO.setApplyShow((byte)1);
                applySupplyComAcctService.insideSaveApply(applySupplyComAcctDTO);
            }
            if(!Objects.equals(Byte.valueOf("1"),applySupplyCompanyReqVO.getSource())){
                approvalFileInfoService.batchSave(applySupplyCompanyReqVO.getApprovalFileInfos(),applySupplyCompanyReqDTO.getApplyCode(),applySupplyCompany.getFormNo(),ApprovalFileTypeEnum.SUPPLIER.getType());
                applySupplyCompanyReqDTO.setDirectSupervisorCode(applySupplyCompanyReqVO.getDirectSupervisorCode());
                applySupplyCompanyReqDTO.setDirectSupervisorName(applySupplyCompanyReqVO.getDirectSupervisorName());
                applySupplyCompanyReqDTO.setFormNo(applySupplyCompany.getFormNo());
                applySupplyCompanyReqDTO.setId(applySupplyCompany.getId());
                applySupplyCompanyReqDTO.setApprovalName(applySupplyCompany.getApprovalName());
                applySupplyCompanyReqDTO.setApprovalRemark(applySupplyCompany.getApprovalRemark());
                workFlow(applySupplyCompanyReqDTO);
            }
            //审批附件
        } catch (Exception e){
            log.error(Global.ERROR, e);
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

    /**
     * 功能描述: 返回详情接口请求参数
     *
     * @param formNo
     * @return
     * @auther knight.xie
     * @date 2019/8/9 14:41
     */
    @Override
    public DetailRequestRespVo getInfoByForm(String formNo) {
        ApplySupplyCompany applySupplyCompany = applySupplyCompanyDao.getApplySupplyComByFormNo(formNo);
        if(null == applySupplyCompany){
            throw new BizException(ResultCode.OBJECT_EMPTY_BY_FORMNO);
        }
        DetailRequestRespVo detailRequestRespVo = new DetailRequestRespVo();
        detailRequestRespVo.setApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
        detailRequestRespVo.setId(applySupplyCompany.getId());
        detailRequestRespVo.setItemCode("1");
        return detailRequestRespVo;
    }

    /**
     * 功能描述: 保存供应商采购组申请信息
     *
     * @param purchaseGroups
     * @return
     * @auther knight.xie
     * @date 2019/8/23 10:52
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyPurchaseGroupList(List<ApplySupplyCompanyPurchaseGroup> purchaseGroups) {
        if(CollectionUtils.isEmptyCollection(purchaseGroups)){
            return 0;
        }
        return applySupplyCompanyPurchaseGroupMapper.insertBatch(purchaseGroups);
    }

    @Override
    public Boolean deleteApply(Long id) {
        ApplySupplyCompany applySupplyCompany = applySupplyCompanyMapper.selectByPrimaryKey(id);
        if (Objects.isNull(applySupplyCompany)) {
            return Boolean.TRUE;
        }
        //删除主表
        int i = applySupplyCompanyMapper.delectById(id);
        //删除采购组附表
        applySupplyCompanyPurchaseGroupMapper.deleteByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
        //删除发货配置
        applyDeliveryInformationMapper.deleteByApplyCode(applySupplyCompany.getApplySupplyCompanyCode());
        return i==1;
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
        ApplySupplyCompanyAcctReqVO acctReqVO = null;


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
//            this.acctReqVO = new ApplySupplyCompanyAcctReqVO();
            this.returnVO = new ApplyDeliveryInfoReqVO();
        }

        public ApplySupplyCompanyReqVO getReqVO(){
            List<ApplyDeliveryInfoReqVO> infos = Lists.newArrayList();
            infos.add(sendVO);
            infos.add(returnVO);
            this.reqVO.setApplySupplyCompanyAccountReq(acctReqVO);
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
//                error.add("简称不能为空");
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
//                SpecialArea specialArea = SpecialArea.getAll().get(supplierImport.getProvinceName());
//                if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
//                    if (StringUtils.isBlank(supplierImport.getCityName())) {
////                        error.add("市不能为空");
//                    }else {
//                        checkArea(supplierImport.getProvinceName(), supplierImport.getCityName(), supplierImport.getDistrictName(), CheckAreaEnum.普通省市县);
//                    }
//                }
//                if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
//                    if (StringUtils.isBlank(supplierImport.getCityName())) {
////                        error.add("县不能为空");
//                    }else {
//                        checkArea(supplierImport.getProvinceName(), supplierImport.getCityName(), supplierImport.getDistrictName(), CheckAreaEnum.普通省市县);
//                    }
//                }
                checkArea(supplierImport.getProvinceName(), supplierImport.getCityName(), supplierImport.getDistrictName(), CheckAreaEnum.普通省市县);
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
//                error.add("最低订货金额不能为空");
                flag = false;
            }
            if(StringUtils.isBlank(supplierImport.getMaxOrderAmount())){
//                error.add("最高订货金额不能为空");
                flag = false;
            }
            if (flag) {
                try {
                    String minOrderAmount = supplierImport.getMinOrderAmount();
                    String maxOrderAmount = supplierImport.getMaxOrderAmount();
                    BigDecimal l = NumberConvertUtils.stringParseBigDecimal(minOrderAmount);
                    BigDecimal l2 = NumberConvertUtils.stringParseBigDecimal(maxOrderAmount);
                    if(l.compareTo(BigDecimal.ZERO)==-1){
                        error.add("最小起订金额不能小于0");
                    }
                    if(l2.compareTo(BigDecimal.ZERO)==-1){
                        error.add("最大起订金额不能小于0");
                    }
                    if (l.compareTo(l2)==1) {
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
//                SpecialArea specialArea = SpecialArea.getAll().get(supplierImport.getSendProvinceName());
//                if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
//                    if(StringUtils.isBlank(supplierImport.getSendCityName())){
//                        error.add("发货市不能为空");
//                    }else {
//                        checkArea(supplierImport.getSendProvinceName(), supplierImport.getSendCityName(), supplierImport.getSendDistrictName(), CheckAreaEnum.发货省市县);
//                    }
//                }
//                if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
//                    if(StringUtils.isBlank(supplierImport.getSendDistrictName())){
//                        error.add("发货县不能为空");
//                    }else {
//                        checkArea(supplierImport.getSendProvinceName(), supplierImport.getSendCityName(), supplierImport.getSendDistrictName(), CheckAreaEnum.发货省市县);
//                    }
//                }
                checkArea(supplierImport.getSendProvinceName(), supplierImport.getSendCityName(), supplierImport.getSendDistrictName(), CheckAreaEnum.发货省市县);
            }
            //详细地址
            if(StringUtils.isBlank(supplierImport.getSendingAddress())){
//                error.add("发货详细地址不能为空");
            }else {
                sendVO.setSendingAddress(supplierImport.getSendingAddress().trim());
            }
//            //发货至
//            if(StringUtils.isBlank(supplierImport.getSendTo())){
//                error.add("发货至不能为空");
//            }else {
//                SupplierDictionaryInfo info1 = dictionaryInfoList.get(supplierImport.getSendTo().trim());
//                if(Objects.isNull(info1)){
//                    error.add("未找到对应的发货至选项");
//                }else {
//                    sendVO.setSendTo(info1.getSupplierDictionaryValue());
//                    sendVO.setSendToDesc(supplierImport.getSendTo().trim());
//                }
//            }
//            //发货天数
//            if (StringUtils.isNotBlank(supplierImport.getDeliveryDays())) {
//                try {
//                    String deliveryDays = supplierImport.getDeliveryDays();
//                    if (StringUtils.isNotBlank(deliveryDays)) {
//                        sendVO.setDeliveryDays(Long.valueOf(deliveryDays));
//                    }
//                } catch (NumberFormatException e) {
//                    error.add("发货天数格式不正确");
//                }
//            }
            //收货省市区
            if(StringUtils.isBlank(supplierImport.getReturnProvinceName())){
                error.add("收货省不能为空");
            }else {
//                SpecialArea specialArea = SpecialArea.getAll().get(supplierImport.getReturnProvinceName());
//                if (Objects.isNull(specialArea) || specialArea.getHasCity()) {
//                    if(StringUtils.isBlank(supplierImport.getReturnCityName())){
//                        error.add("收货市不能为空");
//                    }else {
//                        checkArea(supplierImport.getReturnProvinceName(), supplierImport.getReturnCityName(), supplierImport.getReturnDistrictName(), CheckAreaEnum.退货省市县);
//                    }
//                }
//                if (Objects.isNull(specialArea) || specialArea.getHasDistrict()) {
//                    if(StringUtils.isBlank(supplierImport.getReturnDistrictName())){
//                        error.add("收货县不能为空");
//                    }else {
//                        checkArea(supplierImport.getReturnProvinceName(), supplierImport.getReturnCityName(), supplierImport.getReturnDistrictName(), CheckAreaEnum.退货省市县);
//                    }
//                }
                checkArea(supplierImport.getReturnProvinceName(), supplierImport.getReturnCityName(), supplierImport.getReturnDistrictName(), CheckAreaEnum.退货省市县);
            }
            //详细地址
            if(StringUtils.isBlank(supplierImport.getReturningAddress())){
//                error.add("收货详细地址不能为空");
            }else {
                returnVO.setSendingAddress(supplierImport.getReturningAddress().trim());
            }
            //收货至
//            if(StringUtils.isBlank(supplierImport.getReturnTo())){
//                error.add("收货至不能为空");
//            }else {
//                SupplierDictionaryInfo info1 = dictionaryInfoList.get(supplierImport.getReturnTo().trim());
//                if(Objects.isNull(info1)){
//                    error.add("未找到对应的收货至选项");
//                }else {
//                    returnVO.setSendTo(info1.getSupplierDictionaryValue());
//                    returnVO.setSendToDesc(supplierImport.getReturnTo().trim());
//                }
//            }
//            //收货天数
//            if (StringUtils.isNotBlank(supplierImport.getReturnDays())) {
//                try {
//                    String deliveryDays = supplierImport.getReturnDays();
//                    if (StringUtils.isNotBlank(deliveryDays)) {
//                        returnVO.setDeliveryDays(Long.valueOf(deliveryDays));
//                    }
//                } catch (NumberFormatException e) {
//                    error.add("收货天数格式不正确");
//                }
//            }
            sendVO.setDeliveryType((byte) 0);
            returnVO.setDeliveryType((byte) 1);

            //"品牌"
            if (StringUtils.isNotBlank(supplierImport.getBrand())) {
                reqVO.setBrand(supplierImport.getBrand().trim());
            }
            //"结款方式")
            if (StringUtils.isNotBlank(supplierImport.getPaymentMethod())) {
                reqVO.setPaymentMethod(supplierImport.getPaymentMethod().trim());
            }

            //"供货区域")
            if (StringUtils.isNotBlank(supplierImport.getDeliveryArea())) {
                reqVO.setDeliveryArea(supplierImport.getDeliveryArea().trim());
            }
            //"备注")
            if (StringUtils.isNotBlank(supplierImport.getRemark())) {
                reqVO.setRemark(supplierImport.getRemark().trim());
            }
            //"审批名称")
            if (StringUtils.isBlank(supplierImport.getApprovalName())) {
                error.add("审批名称不能为空");
            }else{
                reqVO.setApprovalName(supplierImport.getApprovalName().trim());
            }
            return this;
        }
        private CheckSupply checkAccount(){
            //
            this.reqVO.setAddAccount((byte) 1);
            boolean b = StringUtils.isNotBlank(supplierImport.getBankAccount()) || StringUtils.isNotBlank(supplierImport.getAccount())
                    || StringUtils.isNotBlank(supplierImport.getAccountName()) || StringUtils.isNotBlank(supplierImport.getMaxPaymentAmount());
            //有一个填写了都那么都必填
            if(b){
                //开户银行
                this.acctReqVO = new ApplySupplyCompanyAcctReqVO();
                boolean flag = true;
                if(StringUtils.isBlank(supplierImport.getBankAccount())){
                    error.add("开户银行不能为空");
                    flag = false;
                }else {
                    this.acctReqVO.setBankAccount(supplierImport.getBankAccount().trim());
                }
                //银行账号
                if(StringUtils.isBlank(supplierImport.getAccount())){
                    error.add("银行账号");
                    flag = false;
                }else {
                    this.acctReqVO.setAccount(supplierImport.getAccount().trim());
                }
                //户名
                if(StringUtils.isBlank(supplierImport.getAccountName())){
                    error.add("户名");
                    flag = false;
                }else {
                    this.acctReqVO.setAccountName(supplierImport.getAccountName().trim());
                }
                //最高付款金额
                if(StringUtils.isBlank(supplierImport.getMaxPaymentAmount())){
                    error.add("最高付款金额");
                    flag = false;
                }else {
                    try {
                        this.acctReqVO.setMaxPaymentAmount(new BigDecimal(supplierImport.getMaxPaymentAmount().trim()));
                    } catch (Exception e) {
                        error.add("最高付款金额格式不正确");
                        flag = false;
                    }
                }
                if(flag){
                    this.reqVO.setAddAccount((byte) 0);
                }
            }
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
                    if(StringUtils.isNotBlank(city)){
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
                if(StringUtils.isNotBlank(city)){
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
                    if(StringUtils.isNotBlank(district)) {
                        AreaInfo areaInfo2 = areaTree.get(province + city + district);
                        if (Objects.isNull(areaInfo2)) {
                            error.add(checkAreaEnum.getDis());
                        } else {
                            if (checkAreaEnum.getType() == 1) {
                                reqVO.setDistrictId(areaInfo2.getCode());
                                reqVO.setDistrictName(district);
                            } else if (checkAreaEnum.getType() == 2) {
                                sendVO.setSendDistrictId(areaInfo2.getCode());
                                sendVO.setSendDistrictName(district);
                            } else if (checkAreaEnum.getType() == 3) {
                                returnVO.setSendDistrictId(areaInfo2.getCode());
                                returnVO.setSendDistrictName(district);
                            }
                        }
                    }
                }
            }
        }
    }
    private class CheckSupplyForUpdate{

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
        ApplySupplyCompanyAcctReqVO acctReqVO = null;


        public CheckSupplyForUpdate( Object supplierImport, Map<String, AreaInfo> areaTree, Map<String, SupplyCompany> supplyCompanyNames, Map<String, SupplyCompany> supplyCompanyCodes, Map<String, Supplier> supplierList, Map<String, SupplierDictionaryInfo> dictionaryInfoList) {
            this.error = Lists.newArrayList();
            this.supplierImport = BeanCopyUtils.copy(supplierImport,SupplierImport.class);
            this.areaTree = areaTree;
            this.supplyCompanyNames = supplyCompanyNames;
            this.supplyCompanyCodes = supplyCompanyCodes;
            this.supplierList = supplierList;
            this.dictionaryInfoList = dictionaryInfoList;
            this.reqVO = new ApplySupplyCompanyReqVO();
//            this.sendVO = new ApplyDeliveryInfoReqVO();
//            this.acctReqVO = new ApplySupplyCompanyAcctReqVO();
//            this.returnVO = new ApplyDeliveryInfoReqVO();
        }

        public ApplySupplyCompanyReqVO getReqVO(){
            if(CollectionUtils.isEmptyCollection(error)){
                List<ApplyDeliveryInfoReqVO> infos = Lists.newArrayList();
                if(Objects.nonNull(sendVO))
                infos.add(sendVO);
                if(Objects.nonNull(returnVO))
                infos.add(returnVO);
                this.reqVO.setDeliveryInfoList(infos);
                if(Objects.nonNull(acctReqVO))
                this.reqVO.setApplySupplyCompanyAccountReq(acctReqVO);
            }
            return this.reqVO;
        }
        public SupplierImport getSupplierImport(){
            this.supplierImport.setError(StringUtils.strip(this.error.toString(), "[]"));
            return this.supplierImport;
        }

        private CheckSupplyForUpdate checkSupplyUpdate(){
            if(StringUtils.isBlank(supplierImport.getApplySupplyCode())){
                error.add("供应商编码不能为空");
            }else {
                SupplyCompany supplyCompany1 = supplyCompanyCodes.get(supplierImport.getApplySupplyCode().trim());
                if(Objects.isNull(supplyCompany1)){
                    error.add("供应商编码不存在");
                }else {
                    if(StringUtils.isBlank(supplierImport.getApplySupplyName())){
//                        error.add("供应商名称不能为空");
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

        private CheckSupplyForUpdate checkCommonData(){
            //供应商类型
            if(StringUtils.isBlank(supplierImport.getApplySupplyType())){
//                error.add("供应商类型不能为空");
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
//                error.add("简称不能为空");
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
//                error.add("省不能为空");
            } else {
                checkArea(supplierImport.getProvinceName(), supplierImport.getCityName(), supplierImport.getDistrictName(), CheckAreaEnum.普通省市县);
            }
            //详细地址
            if(StringUtils.isBlank(supplierImport.getAddress())){
//                error.add("详细地址不能为空");
            }else {
                reqVO.setAddress(supplierImport.getAddress().trim());
            }
            //邮编
            if(StringUtils.isNotBlank(supplierImport.getZipCode())){
                reqVO.setZipCode(supplierImport.getZipCode().trim());
            }
            //邮箱
            if(StringUtils.isBlank(supplierImport.getEmail())){
//                error.add("邮箱不能为空");
            }else {
                reqVO.setEmail(supplierImport.getEmail().trim());
            }
            //公司网址
            if(StringUtils.isNotBlank(supplierImport.getCompanyWebsite())){
                reqVO.setCompanyWebsite(supplierImport.getCompanyWebsite().trim());
            }
            //税号
            if(StringUtils.isBlank(supplierImport.getTaxId())){
//                error.add("税号不能为空");
            }else {
                reqVO.setTaxId(supplierImport.getTaxId().trim());
            }
            //注册资金
            if(StringUtils.isBlank(supplierImport.getRegisteredCapital())){
//                error.add("注册资金不能为空");
            }else {
                try {
                    reqVO.setRegisteredCapital(NumberConvertUtils.stringParseBigDecimal(supplierImport.getRegisteredCapital()));
                } catch (NumberFormatException e) {
                    error.add("注册资金格式不正确");
                }
            }
            //法人代表
            if(StringUtils.isBlank(supplierImport.getCorporateRepresentative())){
//                error.add("法人代表不能为空");
            }else {
                reqVO.setCorporateRepresentative(supplierImport.getCorporateRepresentative());
            }
            if(StringUtils.isBlank(supplierImport.getContactName())){
//                error.add("联系人姓名不能为空");
            }else {
                reqVO.setContactName(supplierImport.getContactName());
            }
            if(StringUtils.isBlank(supplierImport.getMobilePhone())){
//                error.add("手机号不能为空");
            }else {
                if(!Constraint.ckCountNum(11,supplierImport.getMobilePhone())){
                    error.add("手机号码格式不正确");
                } else {
                    reqVO.setMobilePhone(supplierImport.getMobilePhone());
                }
            }
            boolean flag = true;
            if(StringUtils.isBlank(supplierImport.getMinOrderAmount())){
//                error.add("最低订货金额不能为空");
                flag = false;
            }
            if(StringUtils.isBlank(supplierImport.getMaxOrderAmount())){
//                error.add("最高订货金额不能为空");
                flag = false;
            }
            if (flag) {
                try {
                    String minOrderAmount = supplierImport.getMinOrderAmount();
                    String maxOrderAmount = supplierImport.getMaxOrderAmount();
                    BigDecimal l = NumberConvertUtils.stringParseBigDecimal(minOrderAmount);
                    BigDecimal l2 = NumberConvertUtils.stringParseBigDecimal(maxOrderAmount);
                    if(l.compareTo(BigDecimal.ZERO)==-1){
                        error.add("最小起订金额不能小于0");
                    }
                    if(l2.compareTo(BigDecimal.ZERO)==-1){
                        error.add("最大起订金额不能小于0");
                    }
                    if (l.compareTo(l2)==1) {
                        error.add("最小起订金额不能大于最大起订金额");
                    }
                    reqVO.setMinOrderAmount(l);
                    reqVO.setMaxOrderAmount(l2);
                } catch (NumberFormatException e) {
                    error.add("最小起订金额或最大起订金额格式不正确");
                }
            }
            if(StringUtils.isNotBlank(supplierImport.getSendProvinceName())){
                sendVO = new ApplyDeliveryInfoReqVO();
                //发货省市区
                checkArea(supplierImport.getSendProvinceName(), supplierImport.getSendCityName(), supplierImport.getSendDistrictName(), CheckAreaEnum.发货省市县);
                //详细地址
                if(StringUtils.isBlank(supplierImport.getSendingAddress())){
//                     error.add("发货详细地址不能为空");
                }else {
                    sendVO.setSendingAddress(supplierImport.getSendingAddress().trim());
                }
                sendVO.setDeliveryType((byte) 0);
            }
            if(StringUtils.isNotBlank(supplierImport.getReturnProvinceName())){
                returnVO = new ApplyDeliveryInfoReqVO();
                //收货省市区
                checkArea(supplierImport.getReturnProvinceName(), supplierImport.getReturnCityName(), supplierImport.getReturnDistrictName(), CheckAreaEnum.退货省市县);
                //详细地址
                if(StringUtils.isBlank(supplierImport.getReturningAddress())){
//                    error.add("收货详细地址不能为空");
                }else {
                    returnVO.setSendingAddress(supplierImport.getReturningAddress().trim());
                }
                returnVO.setDeliveryType((byte) 1);
            }
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
                if(city!=null){
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
                    if(district!=null) {
                        AreaInfo areaInfo2 = areaTree.get(province + city + district);
                        if (Objects.isNull(areaInfo2)) {
                            error.add(checkAreaEnum.getDis());
                        } else {
                            if (checkAreaEnum.getType() == 1) {
                                reqVO.setDistrictId(areaInfo2.getCode());
                                reqVO.setDistrictName(district);
                            } else if (checkAreaEnum.getType() == 2) {
                                sendVO.setSendDistrictId(areaInfo2.getCode());
                                sendVO.setSendDistrictName(district);
                            } else if (checkAreaEnum.getType() == 3) {
                                returnVO.setSendDistrictId(areaInfo2.getCode());
                                returnVO.setSendDistrictName(district);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer reUpdateApply(String applyCode) {
        Integer num = 0;
        if(StringUtils.isBlank(applyCode)){
            throw new BizException(ResultCode.REQUIRED_PARAMETER);
        }
        //供应商信息
        ApplySupplyCompany applySupplyCompany = applySupplyCompanyDao.getApplySupplyCompany(applyCode);
        if(null == applySupplyCompany){
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
        //判断状态
        if(!Objects.equals(applySupplyCompany.getApplyStatus(),ApplyStatus.REVOKED.getNumber()) &&
                !Objects.equals(applySupplyCompany.getApplyStatus(),ApplyStatus.APPROVAL_FAILED.getNumber())){
            throw new BizException(ResultCode.REUPDATE_ERROR);
        }
        //账户信息
        ApplySupplyCompanyAcctReqVO applySupplyCompanyAccountReq = null;
        if(StatusTypeCode.ADD_ACCOUNT.getStatus().equals(applySupplyCompany.getAddAccount())){
            ApplySupplyCompanyAccount applySupplyComAcct = applySupplyCompanyAcctDao.getApplySupplyComAcct(applyCode);
            applySupplyCompanyAccountReq = BeanCopyUtils.copy(applySupplyComAcct,ApplySupplyCompanyAcctReqVO.class);
        }
        //文件信息
        List<ApplySupplierFile> applySupplierFiles  = applySupplierFileDao.getApplySupplierFile(applyCode);
        //发货/退后申请信息
        List<ApplyDeliveryInformation> applyDeliveryInfo = applyDeliveryInfoDao.getApplyDeliveryInfo(applyCode);
        //采购组
        List<ApplySupplyCompanyPurchaseGroup> applySupplyCompanyPurchaseGroups = applySupplyCompanyPurchaseGroupMapper.selectByApplySupplyCompanyCode(applyCode);
        //数据转化
        ApplySupplyCompanyReqVO applySupplyCompanyReqVO = BeanCopyUtils.copy(applySupplyCompany,ApplySupplyCompanyReqVO.class);
        if(null != applySupplyCompanyAccountReq){
            applySupplyCompanyReqVO.setApplySupplyCompanyAccountReq(applySupplyCompanyAccountReq);
        }
        if(CollectionUtils.isNotEmptyCollection(applySupplierFiles)){
            List<SupplierFileReqVO> fileReqVOList = BeanCopyUtils.copyList(applySupplierFiles,SupplierFileReqVO.class);
            applySupplyCompanyReqVO.setFileReqVOList(fileReqVOList);
        }
        if(CollectionUtils.isNotEmptyCollection(applySupplyCompanyPurchaseGroups)){
            List<ApplySupplyCompanyPurchaseGroupReqVo> purchaseGroupVos = BeanCopyUtils.copyList(applySupplyCompanyPurchaseGroups,ApplySupplyCompanyPurchaseGroupReqVo.class);
            applySupplyCompanyReqVO.setPurchaseGroupVos(purchaseGroupVos);
        }
        if(CollectionUtils.isNotEmptyCollection(applyDeliveryInfo)){
            List<ApplyDeliveryInfoReqVO> deliveryInfoList = BeanCopyUtils.copyList(applyDeliveryInfo,ApplyDeliveryInfoReqVO.class);
            applySupplyCompanyReqVO.setDeliveryInfoList(deliveryInfoList);
        }
        applySupplyCompanyReqVO.setSource(Byte.valueOf("1"));
        //如果是新增
        if(Objects.equals(StatusTypeCode.ADD_APPLY.getStatus(),applySupplyCompany.getApplyType())){
            applySupplyCompanyReqVO.setApplySupplyCode(null);
            num = ((ApplySupplyComService) AopContext.currentProxy()).saveApply(applySupplyCompanyReqVO).getData();
        }else{
            num = ((ApplySupplyComService) AopContext.currentProxy()).updateApply(applySupplyCompanyReqVO).intValue();
        }
        return num;
    }
}
