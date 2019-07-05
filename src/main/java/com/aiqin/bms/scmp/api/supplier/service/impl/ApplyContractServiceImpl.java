package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.applycontract.ApplyContractDao;
import com.aiqin.bms.scmp.api.supplier.dao.applycontract.ApplyContractPurchaseVolumeDao;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractDao;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractPurchaseVolumeDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractPurchaseVolumeDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.QueryApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.UpdateApplyContractPurchaseVolumeReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.UpdateApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractPurchaseVolumeDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractPurchaseVolumeReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.PlanTypeReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractFileResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractPurchaseGroupResVo;
import com.aiqin.bms.scmp.api.supplier.mapper.*;
import com.aiqin.bms.scmp.api.supplier.service.ApplyContractService;
import com.aiqin.bms.scmp.api.supplier.service.ContractService;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

;

/**
 * @description: 合同申请实现层
 * @author:曾兴旺
 * @date: 2018/11/30
 */
@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.APPLY_CONTRACT)
public class ApplyContractServiceImpl extends SupplierBaseServiceImpl implements ApplyContractService, WorkFlowHelper {

    @Autowired
    private ApplyContractDao applyContractDao;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private SupplierCommonService supplierCommonService;

    @Autowired
    private ApplyContractPurchaseVolumeDao applyContractPurchaseVolumeMapperDao;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;


    @Autowired
    private ContractPurchaseVolumeDao contractPurchaseVolumeDao;

    @Autowired
    private ApplyContractFileMapper applyContractFileMapper;

    @Autowired
    private ContractFileMapper contractFileMapper;

    @Autowired
    private ApplyContractPurchaseGroupMapper applyContractPurchaseGroupMapper;

    @Autowired
    private ContractPurchaseGroupMapper contractPurchaseGroupMapper;

    @Autowired
    private ApplyContractPlanTypeMapper applyContractPlanTypeMapper;

    @Autowired
    private ContractService contractService;

    /**
     * 分页获取申请合同列表
     *
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryApplyContractResVo> findApplyContractList(QueryApplyContractReqVo vo) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                vo.setCompanyCode(authToken.getCompanyCode());
                vo.setApplyBy(authToken.getPersonName());
            }
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            List<QueryApplyContractResVo> applyContractList = applyContractDao.selectBySelectApplyContract(vo);
            BasePage<QueryApplyContractResVo> basePage = PageUtil.getPageList(vo.getPageNo(),applyContractList);
            return basePage;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("分页查询失败");
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 保存申请合同
     *
     * @param applyContractReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int saveApplyContract(ApplyContractReqVo applyContractReqVo) {

        // 转化成数据库访问实体
        ApplyContractDTO applyContractDTO = new ApplyContractDTO();
        BeanCopyUtils.copy(applyContractReqVo,applyContractDTO);
        //生成合同申请编号
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.APPLY_CONTRACT_CODE+applyContractReqVo.getContractTypeCode());
        String code = applyContractReqVo.getContractTypeCode()+DateUtils.getCurrentDateTime(DateUtils.YEAR_FORMAT)+fillZero(encodingRule.getNumberingValue());
        applyContractDTO.setApplyContractCode(code);
        //申请状态
        applyContractDTO.setApplyStatus(ApplyStatus.PENDING.getNumber());
        //申请类型
        applyContractDTO.setApplyType(Byte.parseByte("0"));
        StringBuffer purchasingGroupCode = new StringBuffer();
        StringBuffer purchasingGroupName = new StringBuffer();
        if(CollectionUtils.isNotEmptyCollection(applyContractReqVo.getPurchaseGroupReqVos())) {
            applyContractReqVo.getPurchaseGroupReqVos().forEach(item->{
                purchasingGroupCode.append(item.getPurchasingGroupCode()).append(",");
                purchasingGroupName.append(item.getPurchasingGroupName()).append(",");
            });
        }
        applyContractDTO.setPurchasingGroupCode(purchasingGroupCode.toString().substring(0,purchasingGroupCode.toString().length()-1));
        applyContractDTO.setPurchasingGroupName(purchasingGroupName.toString().substring(0,purchasingGroupName.toString().length()-1));


        //保存合同申请主体
        Long k = ((ApplyContractService) AopContext.currentProxy()).insertApplyContractDetails(applyContractDTO);

        //将id set到实体里面
         applyContractDTO.setId(k);
        // 日志
         supplierCommonService.getInstance(applyContractDTO.getApplyContractCode()+"", HandleTypeCoce.APPLY_ADD_CONTRACT.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(), applyContractReqVo,HandleTypeCoce.APPLY_ADD_CONTRACT.getName());
        // 更新编码数据中的最大编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        if (k > 0) {
            if (applyContractDTO.getRebateClause().equals((byte)1)){
                List<ApplyContractPlanType> typeList = BeanCopyUtils.copyList(applyContractReqVo.getPlanTypeList(),ApplyContractPlanType.class);
                typeList.stream().forEach(planType -> planType.setApplyContractCode(applyContractDTO.getApplyContractCode()));
                savePlanTypeList(typeList);
            }
            // 转化进货额list
            try {
                List<ApplyContractPurchaseVolumeDTO> purchaseLists = BeanCopyUtils.copyList( applyContractReqVo.getPurchaseVolumeReqVos(),ApplyContractPurchaseVolumeDTO.class);
                if (purchaseLists != null && purchaseLists.size() > 0) {
                    // 设置关联编码
                    purchaseLists.stream().forEach(purchase -> purchase.setApplyContractCode(applyContractDTO.getApplyContractCode()));
                   int s = ((ApplyContractService) AopContext.currentProxy()).saveList(purchaseLists);
                    if (s > 0) {
                        List<ApplyContractFile> files = BeanCopyUtils.copyList( applyContractReqVo.getFileReqVos(),ApplyContractFile.class);
                        if(CollectionUtils.isNotEmptyCollection(files)){
                            files.stream().forEach(file ->{
                                file.setApplyContractCode(applyContractDTO.getApplyContractCode());
                            });
                            ((ApplyContractService) AopContext.currentProxy()).saveFileList(files);
                        }
                        List<ApplyContractPurchaseGroup> purchaseGroups = BeanCopyUtils.copyList( applyContractReqVo.getPurchaseGroupReqVos(),ApplyContractPurchaseGroup.class);
                        if(CollectionUtils.isNotEmptyCollection(purchaseGroups)){
                            purchaseGroups.stream().forEach(group ->{
                                group.setApplyContractCode(applyContractDTO.getApplyContractCode());
                            });
                            ((ApplyContractService) AopContext.currentProxy()).savePurchaseGroupList(purchaseGroups);
                        }
                        // 推送至审批流程
                        try{
                            workFlow(k);
                        return  s;
                        }catch (Exception e){
                            log.error("推送流程节点报错");
                            throw new GroundRuntimeException("推送流程节点报错");
                        }
                    } else {
                        log.error("保存进货额失败");
                        throw new GroundRuntimeException("合同申请进货额保存失败");
                    }
                } else {
                    log.error("进货额转化后的对象为空");
                    throw new GroundRuntimeException("进货额不能为空");
                }
            } catch (Exception e) {
                log.error("进货额对象转化失败");
                throw new GroundRuntimeException("转化实体出错");
            }
        } else {
            log.error("保存申请合同主体失败");
            throw new GroundRuntimeException("合同申请保存失败");
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePlanTypeList(List<ApplyContractPlanType> typeList) {
        if (CollectionUtils.isEmptyCollection(typeList)) {
            log.info("需要保存的实体为空");
            return;
        }
        int i = applyContractPlanTypeMapper.insertBatch(typeList);
        if (i != typeList.size()) {
            throw new BizException(ResultCode.SAVE_PLAN_TYPE_FAILED);
        }

    }


    /**
     * 根据id查询合同详情
     *
     * @param id
     * @return
     */
    @Override
    public ApplyContractViewResVo findApplyContractDetail(Long id) {
        ApplyContractViewResVo applyContractResVo = new ApplyContractViewResVo();
        if (id != null) {
            ApplyContractDTO entity = applyContractDao.selectByPrimaryKey(id);
            BeanCopyUtils.copy(entity, applyContractResVo);
            applyContractResVo.setApplyType(entity.getApplyType());
            if (Objects.equals(0, entity.getApplyType().intValue())) {
                //新增修改
                applyContractResVo.setApplyBy(entity.getCreateBy());
                applyContractResVo.setApplyDate(entity.getCreateTime());
            } else if (Objects.equals(1, entity.getApplyType().intValue())) {
                applyContractResVo.setApplyBy(entity.getUpdateBy());
                applyContractResVo.setApplyDate(entity.getUpdateTime());
                // 根据申请合同编码去获取合同编号
                ContractDTO contractDTO = contractDao.selectByApplyCode(entity.getApplyContractCode());
                // 将正式合同编码set进去
                applyContractResVo.setContractCode(contractDTO.getContractCode());
            }
            applyContractResVo.setModelType("合同");
            applyContractResVo.setModelTypeCode("4");
            applyContractResVo.setStatus(entity.getApplyStatus().toString());
            applyContractResVo.setEnable(StatusTypeCode.DEL_FLAG.getStatus().equals(entity.getDelFlag()) ? StatusTypeCode.DIS_ABLE.getName() : StatusTypeCode.EN_ABLE.getName());

            if (null != applyContractResVo) {
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.APPLY_CONTRACT.getStatus());
                operationLogVo.setObjectId(applyContractResVo.getApplyContractCode());
                BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo, 62);
                List<LogData> logDataList = new ArrayList<>();
                if (null != pageList.getDataList() && pageList.getDataList().size() > 0) {
                    logDataList = pageList.getDataList();
                }
                applyContractResVo.setLogDataList(logDataList);
                List<ApplyContractPurchaseVolumeDTO> purchaseVolume = applyContractPurchaseVolumeMapperDao.selectByApplyContractPurchaseVolume(entity.getApplyContractCode());
                List<ApplyContractFile> applyContractFiles = applyContractFileMapper.selectByApplyContractCode(entity.getApplyContractCode());
                List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = applyContractPurchaseGroupMapper.selectByApplyContractCode(entity.getApplyContractCode());
                List<ApplyContractPlanType> planTypeList = applyContractPlanTypeMapper.selectByApplyContratCode(entity.getApplyContractCode());
                try {
                    // 转化成返回申请合同进货额list
                    if(CollectionUtils.isNotEmptyCollection(purchaseVolume)){
                        List<ApplyContractPurchaseVolumeResVo> list = BeanCopyUtils.copyList(purchaseVolume, ApplyContractPurchaseVolumeResVo.class);
                        applyContractResVo.setPurchaseList(list);
                    }
                    if(CollectionUtils.isNotEmptyCollection(planTypeList)){
                        List<PlanTypeReqVO> list = BeanCopyUtils.copyList(planTypeList, PlanTypeReqVO.class);
                        applyContractResVo.setPlanTypeList(list);
                    }
                    if(CollectionUtils.isNotEmptyCollection(applyContractFiles)){
                        List<ApplyContractFileResVo> fileResVos = BeanCopyUtils.copyList(applyContractFiles, ApplyContractFileResVo.class);
                        applyContractResVo.setFileResVos(fileResVos);
                    }
                    if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                        List<ApplyContractPurchaseGroupResVo> purchaseGroupResVos = BeanCopyUtils.copyList(applyContractPurchaseGroups, ApplyContractPurchaseGroupResVo.class);
                        applyContractResVo.setPurchaseGroupResVos(purchaseGroupResVos);
                    }
                } catch (Exception e) {
                    log.error("进货额转化实体出错");
                    throw new GroundRuntimeException("查询进货额失败");
                }
            }
            return applyContractResVo;
        }
        throw new GroundRuntimeException("查看失败");
    }


    /**
     * 查找要修改合同的详情
     *
     * @param applyContractCode
     * @return
     */
    @Override
    public ApplyContractUpdateResVo findUpdateContractDetail(String applyContractCode) {

        // 根据合同关联编码去查找申请合同详情
       ContractDTO  contractDTO = contractDao.selectByApplyCode(applyContractCode);
        ApplyContractUpdateResVo applyContractResVo = new ApplyContractUpdateResVo();
        applyContractResVo.setApplyContractCode(contractDTO.getApplyContractCode());
        BeanCopyUtils.copy(contractDTO, applyContractResVo);
        applyContractResVo.setApplyContractCode(contractDTO.getApplyContractCode());
        // 将正式合同编码set进去
        applyContractResVo.setContractCode(contractDTO.getContractCode());

        List<ContractPurchaseVolumeDTO> purchaseLists = contractPurchaseVolumeDao.selectByContractPurchaseVolume(contractDTO.getContractCode());
        List<ContractFile> contractFiles = contractFileMapper.selectByContractCode(contractDTO.getContractCode());
        List<ContractPurchaseGroup> contractPurchaseGroups = contractPurchaseGroupMapper.selectByContractCode(contractDTO.getContractCode());
        //  转化成返回的进货实体
        try {
            if (CollectionUtils.isNotEmptyCollection(purchaseLists)) {
                List<ApplyContractPurchaseVolumeResVo> list =BeanCopyUtils.copyList(purchaseLists,ApplyContractPurchaseVolumeResVo.class);
                applyContractResVo.setPurchaseList(list);
            }
            if(CollectionUtils.isNotEmptyCollection(contractFiles)){
                List<ContractFileResVo>  fileResVos = BeanCopyUtils.copyList(contractFiles, ContractFileResVo.class);
                applyContractResVo.setFileResVos(fileResVos);
            }
            if(CollectionUtils.isNotEmptyCollection(contractPurchaseGroups)){
                List<ContractPurchaseGroupResVo> purchaseGroupResVos = BeanCopyUtils.copyList(contractPurchaseGroups, ContractPurchaseGroupResVo.class);
                applyContractResVo.setPurchaseGroupResVos(purchaseGroupResVos);
            }
        } catch (Exception e) {
            log.error("进货额转化实体失败");
            throw new GroundRuntimeException("查询进货额失败");
        }
        return applyContractResVo;
    }


    /**
     * 修改申请合同
     *
     * @param updateApplyContractReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateApplyContract(UpdateApplyContractReqVo updateApplyContractReqVo) {

        // 查询旧的数据
        ApplyContractDTO oldApplyContractDTO = applyContractDao.selectApplyContractByApplyContractCode(updateApplyContractReqVo.getApplyContractCode());
        ApplyContractDTO applyContractDTO = new ApplyContractDTO();
        BeanCopyUtils.copy(updateApplyContractReqVo,applyContractDTO);
        //设置id
        applyContractDTO.setId(oldApplyContractDTO.getId());
        //申请状态
        applyContractDTO.setApplyStatus(ApplyStatus.PENDING.getNumber());
        //申请类型
        applyContractDTO.setApplyType(Byte.parseByte("1"));
        StringBuffer purchasingGroupCode = new StringBuffer();
        StringBuffer purchasingGroupName = new StringBuffer();
        if(CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getPurchaseGroupReqVos())) {
            updateApplyContractReqVo.getPurchaseGroupReqVos().forEach(item->{
                purchasingGroupCode.append(item.getPurchasingGroupCode()).append(",");
                purchasingGroupName.append(item.getPurchasingGroupName()).append(",");
            });
        }
        applyContractDTO.setPurchasingGroupCode(purchasingGroupCode.toString().substring(0,purchasingGroupCode.toString().length()-1));
        applyContractDTO.setPurchasingGroupName(purchasingGroupName.toString().substring(0,purchasingGroupName.toString().length()-1));

        int k = ((ApplyContractService) AopContext.currentProxy()).updateApplyContractDetails(applyContractDTO);


        supplierCommonService.getInstance(updateApplyContractReqVo.getApplyContractCode()+"", HandleTypeCoce.APPLY_UPDATE_CONTRACT.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(),updateApplyContractReqVo ,HandleTypeCoce.APPLY_UPDATE_CONTRACT.getName());
        if(oldApplyContractDTO.getRebateClause().equals(((byte)1))){
            int i = applyContractPlanTypeMapper.deleteByContractCode(applyContractDTO.getApplyContractCode());
        }
        if (applyContractDTO.getRebateClause().equals((byte)1)){
            List<ApplyContractPlanType> typeList = BeanCopyUtils.copyList(updateApplyContractReqVo.getPlanTypeList(),ApplyContractPlanType.class);
            if(CollectionUtils.isEmptyCollection(typeList)){
                throw new GroundRuntimeException("请选择时间!");
            }
            typeList.stream().forEach(planType -> planType.setApplyContractCode(applyContractDTO.getApplyContractCode()));
            savePlanTypeList(typeList);
        }
        if (k > 0) {
            List<UpdateApplyContractPurchaseVolumeReqVo> purchaseLists = updateApplyContractReqVo.getPurchaseVolumeReqVos();
            try {
                //转化成访问数据库实体
                List<ApplyContractPurchaseVolumeDTO> list =BeanCopyUtils.copyList(purchaseLists,ApplyContractPurchaseVolumeDTO.class);
                if (list != null && list.size() > 0) {
                    //删除旧的关联进货额
                    int s  =((ApplyContractService) AopContext.currentProxy()).deleteByPrimaryKey(updateApplyContractReqVo.getApplyContractCode());
                    if (s > 0) {
                        //设置新的关联编码
                        list.stream().forEach(purchase -> purchase.setApplyContractCode(String.valueOf(updateApplyContractReqVo.getApplyContractCode())));
                                  int p = ((ApplyContractService) AopContext.currentProxy()).saveList(list);
                                  if(p>0){
                                      ((ApplyContractService) AopContext.currentProxy()).deleteFiles(updateApplyContractReqVo.getApplyContractCode());
                                      List<ApplyContractFile> files = BeanCopyUtils.copyList(updateApplyContractReqVo.getFileReqVos(),ApplyContractFile.class);
                                      if(CollectionUtils.isNotEmptyCollection(files)){
                                          files.stream().forEach(file ->{
                                              file.setApplyContractCode(updateApplyContractReqVo.getApplyContractCode());
                                          });
                                          ((ApplyContractService) AopContext.currentProxy()).saveFileList(files);
                                      }
                                      ((ApplyContractService) AopContext.currentProxy()).deletePurchaseGroups(updateApplyContractReqVo.getApplyContractCode());
                                      List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = BeanCopyUtils.copyList(updateApplyContractReqVo.getPurchaseGroupReqVos(),ApplyContractPurchaseGroup.class);
                                      if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                                          applyContractPurchaseGroups.stream().forEach(item ->{
                                              item.setApplyContractCode(updateApplyContractReqVo.getApplyContractCode());
                                          });
                                          ((ApplyContractService) AopContext.currentProxy()).savePurchaseGroupList(applyContractPurchaseGroups);
                                      }

                                         workFlow(oldApplyContractDTO.getId());
                                      // 修改合同状态防止在审核中修改合同
                                      int  kp = contractDao.updateByCode(updateApplyContractReqVo.getApplyContractCode(),Byte.valueOf("1"));
                                             return kp;
                                  }else {
                                      log.error("保存进货额失败");
                                      throw new GroundRuntimeException("合同申请进货额修改失败");
                                  }
                    } else {
                        log.error("删除旧的进货额失败");
                        throw new GroundRuntimeException("合同申请进货额修改失败");
                    }
                } else {
                    log.error("进货额转化后的实体为空");
                    throw new GroundRuntimeException("进货额不能为空");
                }

            } catch (Exception e) {
                log.error("进货额转化实体出错");
                e.printStackTrace();
            }
            return k;
        } else {
            log.error("申请合同修改失败");
            throw new GroundRuntimeException("合同修改失败");
            }

    }


    /**
     * 撤销申请合同
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateStatusApplyContract(Long id) {
        int k = 0;
        if (id != null) {
            ApplyContractDTO applyContractDTO1 = applyContractDao.selectByPrimaryKey(id);
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormNo(applyContractDTO1.getFormNo());
            // 调用审批流的撤销接口
            WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
             if(workFlowRespVO.getSuccess().equals(true)){
                     return k;
             }else {
                 log.error("申请合同主键为"+id+"撤销失败");
                 throw  new GroundRuntimeException("撤销失败");
             }

        }  log.error("撤销申请合同id为空");
        throw new GroundRuntimeException("id 不能为空");

    }


    /**
     * 保存申请合同详情
     *
     * @param applyContractDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public Long  insertApplyContractDetails(ApplyContractDTO applyContractDTO) {

        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            applyContractDTO.setCompanyCode(authToken.getCompanyCode());
            applyContractDTO.setCompanyName(authToken.getCompanyName());
        }
        Long k = applyContractDao.insert(applyContractDTO);
        if (k > 0) {
            return applyContractDTO.getId();
        } else {
            throw new GroundRuntimeException("合同主体保存失败");
        }
    }

    /**
     * 批量保存进货额
     *
     * @param applyContractPurchaseVolumes
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int saveList(List<ApplyContractPurchaseVolumeDTO> applyContractPurchaseVolumes) {
        int k = 0;
        if (applyContractPurchaseVolumes != null && applyContractPurchaseVolumes.size() > 0) {
            k = applyContractPurchaseVolumeMapperDao.insertApplyContractPurchaseVolume(applyContractPurchaseVolumes);
            if (k > 0) {
                return k;
            } else {
                throw new GroundRuntimeException("保存进货额失败");
            }
        } else {
            throw new GroundRuntimeException("applyContractPurchaseVolumes 不能为空");
        }
    }

    /**
     * 批量保存文件
     *
     * @param files
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int saveFileList(List<ApplyContractFile> files) {
        int k = 0;
        if (CollectionUtils.isNotEmptyCollection(files)) {
            k = applyContractFileMapper.insertBatch(files);
        }
        return k;
    }

    /**
     * 批量保存采购组
     *
     * @param contractPurchaseGroups
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int savePurchaseGroupList(List<ApplyContractPurchaseGroup> contractPurchaseGroups) {
        int k = 0;
        if (CollectionUtils.isNotEmptyCollection(contractPurchaseGroups)) {
            k = applyContractPurchaseGroupMapper.insertBatch(contractPurchaseGroups);
        }
        return k;
    }

    /**
     * 更新申请合同主体
     *
     * @param applyContractDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateApplyContractDetails(ApplyContractDTO applyContractDTO) {
        int k = applyContractDao.updateByPrimaryKeySelective(applyContractDTO);
        if (k > 0) {
            return k;
        } else {
            throw new GroundRuntimeException("合同主体跟新失败");
        }
    }

    /**
     * 根据关联编码删除进货额
     * @param applyContractCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int deleteByPrimaryKey(String applyContractCode) {
       int k =applyContractPurchaseVolumeMapperDao.deleteByPrimaryKey(applyContractCode);
       if(k>0){
           return k;
       }else {
           throw new GroundRuntimeException("进货额删除失败");
       }
    }

    /**
     * 根据关联编码删除文件信息
     *
     * @param applyContractCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int deleteFiles(String applyContractCode) {
        return applyContractFileMapper.deleteByApplyCode(applyContractCode);
    }

    /**
     * 根据关联编码删除采购组
     *
     * @param applyContractCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int deletePurchaseGroups(String applyContractCode) {
        return applyContractPurchaseGroupMapper.deleteByApplyCode(applyContractCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workFlow(Long id) {
        log.info("ApplyContractServiceImplSupplier-workFlow-传入参数是：[{}]", JSON.toJSONString(id));
         ApplyContractDTO applyContractDTO = applyContractDao.selectByPrimaryKey(id);
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
            workFlowVO.setFormUrl(workFlowBaseUrl.applyContractUrl+"?applyType="+applyContractDTO.getApplyType()+"&applyCode="+applyContractDTO.getApplyContractCode()+"&id="+applyContractDTO.getId()+"&itemCode=4"+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setFormNo("HT"+new IdSequenceUtils().nextId());
            workFlowVO.setTitle(applyContractDTO.getYear()+"年度-"+applyContractDTO.getYearName()+"合同名称"+"-"+WorkFlow.APPLY_CONTRACT.getTitle());
            workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl+WorkFlow.APPLY_CONTRACT.getNum());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("auditPersonId",applyContractDTO.getDirectSupervisorCode());
            workFlowVO.setVariables(jsonObject.toString());
            WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_CONTRACT);
            if(workFlowRespVO.getSuccess()){
                ApplyContractDTO applyContractDTO1 = new ApplyContractDTO();
                applyContractDTO1.setId(id);
                //状态变为审核中
                applyContractDTO1.setApplyStatus(ApplyStatus.APPROVAL.getNumber());
                // 设置流程id
                applyContractDTO1.setFormNo(workFlowVO.getFormNo());
                int i = applyContractDao.updateByPrimaryKeySelective(applyContractDTO1);
                if(i<=0){
                    throw new GroundRuntimeException("审核状态修改失败");
                }
            }else {
                throw new GroundRuntimeException();
                }
        }catch (Exception e) {
          throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo1) {
        log.info("ApplyContractServiceImplSupplier-workFlowCallback-传入参数是：[{}]",JSON.toJSONString(vo1));
        //通过编码查询实体
        ApplyContractDTO account = applyContractDao.selectByFormNO(vo1.getFormNo());
        WorkFlowCallbackVO vo = updateSupStatus(vo1);

        if(Objects.isNull(account)){
            log.error("合同申请数据不存在");
         return "false";
        }
        // 如果该合同已被撤销
        if(account.getApplyStatus().equals(ApplyStatus.REVOKED.getNumber()) ){
            return "success";
            }
        account.setApplyStatus(vo.getApplyStatus());
        account.setAuditorBy(vo.getApprovalUserName());
        account.setAuditorTime(new Date());
//        if(account.getApplyStatus().intValue()== ApplyStatus.APPROVAL.getNumber()){

            if(vo.getApplyStatus().intValue()== ApplyStatus.APPROVAL_SUCCESS.getNumber()){

                int i = applyContractDao.updateByPrimaryKeySelective(account);
                //审批通过之后，分两种情况一种是添加申请，一种是修改申请
                if(account.getApplyType()==0) {
                    //更新申请数据

                    //通过插入正式数据
                    ContractDTO contractDTO = new ContractDTO();
                    BeanCopyUtils.copy(account,contractDTO);
                    //设置编码规则
                    EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.CONTRACT_CODE+account.getContractTypeCode());
                    String code = account.getContractTypeCode()+DateUtils.getCurrentDateTime(DateUtils.YEAR_FORMAT)+fillZero(encodingRule.getNumberingValue());
                    contractDTO.setContractCode(code);
                    contractDTO.setId(null);
                    contractDTO.setApplyContractCode(account.getApplyContractCode());
                    contractDTO.setAuditorBy(vo.getApprovalUserName());
                    contractDTO.setAuditorTime(new Date());
                    contractDTO.setApplyStatus(Byte.valueOf("2"));
                    contractDao.insertSelective(contractDTO);
                    ContractReqVo contractReqVo = new ContractReqVo();
                    BeanCopyUtils.copy(contractDTO,contractReqVo);
                    //更新数据库编码最大值
                    encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
                    log.info("合同正式数据保存成功");
                    //查询文件信息
                    List<ApplyContractFile> files = applyContractFileMapper.selectByApplyContractCode(account.getApplyContractCode());
                    if(CollectionUtils.isNotEmptyCollection(files)){
                        try {
                            List<ContractFile> contractFiles = BeanCopyUtils.copyList(files, ContractFile.class);
                            contractFiles.stream().forEach(item->item.setContractCode(contractDTO.getContractCode()));
                            contractFileMapper.insertBatch(contractFiles);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new GroundRuntimeException("合同文件保存失败");
                        }
                    }
                    //查询关联的采购组
                    List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = applyContractPurchaseGroupMapper.selectByApplyContractCode(account.getApplyContractCode());
                    if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                        try {
                            List<ContractPurchaseGroup> purchaseGroups = BeanCopyUtils.copyList(applyContractPurchaseGroups,ContractPurchaseGroup.class);
                            purchaseGroups.stream().forEach(item->item.setContractCode(contractDTO.getContractCode()));
                            contractPurchaseGroupMapper.insertBatch(purchaseGroups);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new GroundRuntimeException("合同采购组存失败");
                        }
                    }
                    List<ApplyContractPlanType> planTypelist = applyContractPlanTypeMapper.selectByApplyContratCode(account.getApplyContractCode());
                    if(CollectionUtils.isNotEmptyCollection(planTypelist)){
                            List<ContractPlanType> ContractPlanType = BeanCopyUtils.copyList(planTypelist,ContractPlanType.class);
                            ContractPlanType.stream().forEach(item->item.setContractCode(contractDTO.getContractCode()));
                            contractService.savePlanTypeList(ContractPlanType);
                    }
                    // 查询关联的进货额
                    List<ApplyContractPurchaseVolumeDTO> purchaseLists = applyContractPurchaseVolumeMapperDao.selectByApplyContractPurchaseVolume(account.getApplyContractCode());
                    try {
                        List<ContractPurchaseVolumeDTO> list = BeanCopyUtils.copyList(purchaseLists,ContractPurchaseVolumeDTO.class);
                        list.stream().forEach(purchaselist -> purchaselist.setContractCode(contractDTO.getContractCode()));
                        int s = contractPurchaseVolumeDao.insertContractPurchaseVolume(list);
                        if(s > 0){
                            //保存日志信息
                            List<ContractPurchaseVolumeReqVo> contractPurchaseVolumeReqVos = BeanCopyUtils.copyList(list,ContractPurchaseVolumeReqVo.class);
                            contractReqVo.setPurchaseCount(contractPurchaseVolumeReqVos);
                            supplierCommonService.getInstance(contractDTO.getContractCode(),HandleTypeCoce.ADD_CONTRACT.getStatus(),ObjectTypeCode.CONTRACT.getStatus(),contractReqVo,HandleTypeCoce.ADD_CONTRACT.getName());
                            return "success";
                        }else {
                            throw new GroundRuntimeException("合同进货额保存失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new GroundRuntimeException("合同进货额保存失败");
                    }

                }else {
                    //修改申请
                    try {
                        // 根据关联编码去获取旧合同详情，其中用的有合同id。合同编码
                        ContractDTO oldContractDTO = contractDao.selectByApplyCode(account.getApplyContractCode());
                        //日志保存实体
                         ContractReqVo   contractReqVo = new ContractReqVo();
                        //转化成访问数据库实体
                        ContractDTO newContractDTO =  new ContractDTO();
                        BeanCopyUtils.copy(account,newContractDTO);
                        BeanCopyUtils.copy(account,contractReqVo);
                        //输入id
                        newContractDTO.setId(oldContractDTO.getId());
                        //  输入编码
                        newContractDTO.setContractCode(oldContractDTO.getContractCode());
                        newContractDTO.setAuditorBy(vo.getApprovalUserName());
                        newContractDTO.setAuditorTime(new Date());
                        newContractDTO.setApplyStatus(Byte.valueOf("2"));
                        // 跟新活动主体
                        int kp = contractDao.updateByPrimaryKeySelective(newContractDTO);

                        //更新文件信息
                        //删除旧的信息
                        contractFileMapper.deleteByContractCode(oldContractDTO.getContractCode());
                        //查询文件信息
                        List<ApplyContractFile> files = applyContractFileMapper.selectByApplyContractCode(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(files)){
                            try {
                                List<ContractFile> contractFiles = BeanCopyUtils.copyList(files, ContractFile.class);
                                //  设置关联编码
                                contractFiles.stream().forEach(item->item.setContractCode(oldContractDTO.getContractCode()));
                                int cf = contractFileMapper.insertBatch(contractFiles);
                                throw new GroundRuntimeException("合同文件保存失败");
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.info("转化对象失败");
                            }
                        }
                        //更新采购组信息
                        //删除旧的信息
                        contractPurchaseGroupMapper.deleteByContractCode(oldContractDTO.getContractCode());

                        List<ApplyContractPlanType> applyContractPlanTypelist = applyContractPlanTypeMapper.selectByApplyContratCode(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(applyContractPlanTypelist)){
                            contractService.deletePlanTypeList(oldContractDTO.getContractCode());
                            List<ContractPlanType> ContractPlanType = BeanCopyUtils.copyList(applyContractPlanTypelist,ContractPlanType.class);
                            ContractPlanType.stream().forEach(item->item.setContractCode(oldContractDTO.getContractCode()));
                            contractService.savePlanTypeList(ContractPlanType);
                        }
                        //查询关联的采购组
                        List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = applyContractPurchaseGroupMapper.selectByApplyContractCode(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                            try {
                                List<ContractPurchaseGroup> purchaseGroups = BeanCopyUtils.copyList(applyContractPurchaseGroups,ContractPurchaseGroup.class);
                                purchaseGroups.stream().forEach(purchaseGroup->purchaseGroup.setContractCode(oldContractDTO.getContractCode()));
                                int pn = contractPurchaseGroupMapper.insertBatch(purchaseGroups);
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new GroundRuntimeException("合同采购组存失败");
                            }
                        }
                        //删除旧的关联进货额
                        if(kp>0)  {
                            int k = contractPurchaseVolumeDao.deleteByPrimaryKey(oldContractDTO.getContractCode());
                            if(k>0){
                                // 查询申请表关联的进货额
                                List<ApplyContractPurchaseVolumeDTO> purchaseLists = applyContractPurchaseVolumeMapperDao.selectByApplyContractPurchaseVolume(account.getApplyContractCode());
                                // 转化成数据库访问实体列表
                                List<ContractPurchaseVolumeDTO> list =BeanCopyUtils.copyList(purchaseLists,ContractPurchaseVolumeDTO.class);
                                //  设置关联编码
                                list.stream().forEach(volumeDTO -> volumeDTO.setContractCode(String.valueOf(oldContractDTO.getContractCode())));
                                // 保存实体
                                int s = contractPurchaseVolumeDao.insertContractPurchaseVolume(list);
                                if(s > 0){
                                    List<ContractPurchaseVolumeReqVo> contractPurchaseVolumeReqVos = BeanCopyUtils.copyList(list,ContractPurchaseVolumeReqVo.class);
                                    contractReqVo.setPurchaseCount(contractPurchaseVolumeReqVos);
                                    supplierCommonService.getInstance(newContractDTO.getContractCode(),HandleTypeCoce.UPDATE_CONTRACT.getStatus(),ObjectTypeCode.CONTRACT.getStatus(),contractReqVo,HandleTypeCoce.UPDATE_CONTRACT.getName());
                                    return "success";
                                }else {
                                    throw new GroundRuntimeException("合同进货额保存失败");
                                }
                            }else{
                                throw new GroundRuntimeException("合同修改失败");
                            }
                        }else {
                            throw new GroundRuntimeException("合同修改失败");
                        }
                    }catch (Exception e) {
                        throw new GroundRuntimeException("合同修改失败");
                    }
                }
            }else if (vo.getApplyStatus().intValue()==ApplyStatus.APPROVAL_FAILED.getNumber()){
                //更新申请数据
                account.setApplyStatus(Byte.valueOf("3"));
                int i = applyContractDao.updateByPrimaryKeySelective(account);
                // 修改审核合同状态
                contractDao.updateByCode(account.getApplyContractCode(),Byte.valueOf("2"));

                return "success";
            }else if(vo.getApplyStatus().intValue()==ApplyStatus.APPROVAL.getNumber()){
                //传入的是审批中，继续该流程
                return "success";
            }else if(vo.getApplyStatus().intValue()==ApplyStatus.REVOKED.getNumber()){
                account.setApplyStatus(Byte.parseByte("4"));
               ((ApplyContractService) AopContext.currentProxy()).updateApplyContractDetails(account);
                // 打印撤销的日志
                supplierCommonService.getInstance(account.getApplyContractCode()+"", HandleTypeCoce.APPLY_UPDATE_REVOKE_CONTRACT.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(),account ,HandleTypeCoce.APPLY_UPDATE_REVOKE_CONTRACT.getName());
                if(account.getApplyType()==1)
                {// 修改审核合同状态
                    contractDao.updateByCode(account.getApplyContractCode(),Byte.valueOf("2"));
                }
                return "success";

            }else{
                return "false";
          }
    }

    /**
     * 查询供货单位账户列表
     *
     * @param querySupplierReqVO
     * @return
     */
    @Override
    public List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO) {

        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            querySupplierReqVO.setCompanyCode(authToken.getCompanyCode());
            querySupplierReqVO.setApplyBy(authToken.getPersonName());
        }
        return applyContractDao.queryApplyList(querySupplierReqVO);

    }

    private String fillZero(Long code){
        if (code >=0 && code <10) {
            return "00"+code;
        } else if (code >= 10 && code < 100) {
            return "0"+code;
        } else {
            return code+"";
        }
    }
}

