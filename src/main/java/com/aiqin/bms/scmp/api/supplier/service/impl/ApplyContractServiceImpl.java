package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductBrandTypeDao;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductCategoryRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryProductBrandRespVO;
import com.aiqin.bms.scmp.api.product.service.ProductCategoryService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.applycontract.ApplyContractDao;
import com.aiqin.bms.scmp.api.supplier.dao.applycontract.ApplyContractPurchaseVolumeDao;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractDao;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractPurchaseVolumeDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.excel.ContractImportResp;
import com.aiqin.bms.scmp.api.supplier.domain.excel.check.CheckContract;
import com.aiqin.bms.scmp.api.supplier.domain.excel.im.ContractImportNew;
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
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.PlanTypeReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractBrandResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractCategoryResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractFileResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractPurchaseGroupResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.mapper.*;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
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
import java.util.function.Function;
import java.util.stream.Collectors;

;

/**
 * @description: 合同申请实现层
 * @author:曾兴旺
 * @date: 2018/11/30
 */
@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.APPLY_CONTRACT)
public class ApplyContractServiceImpl extends BaseServiceImpl implements ApplyContractService, WorkFlowHelper {

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
    private ApplyContractBrandMapper applyContractBrandMapper;

    @Autowired
    private ContractBrandMapper contractBrandMapper;

    @Autowired
    private ApplyContractCategoryMapper applyContractCategoryMapper;

    @Autowired
    private ContractCategoryMapper contractCategoryMapper;

    @Autowired
    private ApplyContractPlanTypeMapper applyContractPlanTypeMapper;

    @Autowired
    private ContractService contractService;

    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;

    @Autowired
    private SupplyCompanyDao supplyCompanyDao;

    @Autowired
    private PurchaseGroupService purchaseGroupService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductBrandTypeDao productBrandTypeDao;

    /**
     * 分页获取申请合同列表
     *
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryApplyContractResVo> findApplyContractList(QueryApplyContractReqVo vo) {
        AuthToken authToken = getUser();
        if(null != authToken){
            vo.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<QueryApplyContractResVo> applyContractList = applyContractDao.selectBySelectApplyContract(vo);
        BasePage<QueryApplyContractResVo> basePage = PageUtil.getPageList(vo.getPageNo(),applyContractList);
        return basePage;
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
        if(Objects.equals(Byte.valueOf("1"),applyContractReqVo.getSource())){
            applyContractDTO.setApplyStatus(ApplyStatus.PENDING_SUBMISSION.getNumber());
        } else {
            applyContractDTO.setApplyStatus(ApplyStatus.PENDING.getNumber());
        }
        //申请类型
        applyContractDTO.setApplyType(Byte.parseByte("0"));
        StringBuilder purchasingGroupCode = new StringBuilder();
        StringBuilder purchasingGroupName = new StringBuilder();
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
        String content = ApplyStatus.PENDING.getContent().replace(Global.CREATE_BY, applyContractDTO.getCreateBy()).replace(Global.APPLY_TYPE, "新增");
         supplierCommonService.getInstance(applyContractDTO.getApplyContractCode()+"", HandleTypeCoce.PENDING.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(),content ,null,HandleTypeCoce.PENDING.getName());
        // 更新编码数据中的最大编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        if (CollectionUtils.isNotEmptyCollection(applyContractReqVo.getPlanTypeList())){
            List<ApplyContractPlanType> typeList = BeanCopyUtils.copyList(applyContractReqVo.getPlanTypeList(),ApplyContractPlanType.class);
            typeList.stream().forEach(planType -> planType.setApplyContractCode(applyContractDTO.getApplyContractCode()));
            savePlanTypeList(typeList);
        }
        // 转化进货额list
        List<ApplyContractPurchaseVolumeDTO> purchaseLists = BeanCopyUtils.copyList( applyContractReqVo.getPurchaseVolumeReqVos(),ApplyContractPurchaseVolumeDTO.class);
        if (purchaseLists != null && purchaseLists.size() > 0) {
            // 设置关联编码
            purchaseLists.stream().forEach(purchase -> purchase.setApplyContractCode(applyContractDTO.getApplyContractCode()));
           ((ApplyContractService) AopContext.currentProxy()).saveList(purchaseLists);
        }
        if(CollectionUtils.isNotEmptyCollection(applyContractReqVo.getFileReqVos())){
            List<ApplyContractFile> files = BeanCopyUtils.copyList( applyContractReqVo.getFileReqVos(),ApplyContractFile.class);
            if(CollectionUtils.isNotEmptyCollection(files)){
                files.stream().forEach(file ->{
                    file.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                ((ApplyContractService) AopContext.currentProxy()).saveFileList(files);
            }
        }
        if(CollectionUtils.isNotEmptyCollection(applyContractReqVo.getPurchaseGroupReqVos())){
            List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = BeanCopyUtils.copyList( applyContractReqVo.getPurchaseGroupReqVos(),ApplyContractPurchaseGroup.class);
            if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                applyContractPurchaseGroups.stream().forEach(group ->{
                    group.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                ((ApplyContractService) AopContext.currentProxy()).savePurchaseGroupList(applyContractPurchaseGroups);
            }
        }
        if(CollectionUtils.isNotEmptyCollection(applyContractReqVo.getBrandReqVos())){
            List<ApplyContractBrand> brands = BeanCopyUtils.copyList( applyContractReqVo.getBrandReqVos(),ApplyContractBrand.class);
            if(CollectionUtils.isNotEmptyCollection(brands)){
                brands.stream().forEach(group ->{
                    group.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                ((ApplyContractService) AopContext.currentProxy()).saveBrandList(brands);
            }
        }

        if(CollectionUtils.isNotEmptyCollection(applyContractReqVo.getCategoryReqVos())){
            List<ApplyContractCategory> categories = BeanCopyUtils.copyList( applyContractReqVo.getCategoryReqVos(),ApplyContractCategory.class);
            if(CollectionUtils.isNotEmptyCollection(categories)){
                categories.stream().forEach(group ->{
                    group.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                ((ApplyContractService) AopContext.currentProxy()).saveCategoryList(categories);
            }
        }
        if (!Byte.valueOf("1").equals(applyContractReqVo.getSource())) {
             workFlow(applyContractDTO.getId(),applyContractReqVo.getPositionCode());
        }
        return 0;
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
        if (null == id) {
            throw new GroundRuntimeException("查看失败");
        }
        ApplyContractViewResVo applyContractResVo = new ApplyContractViewResVo();
        ApplyContractDTO entity = applyContractDao.selectByPrimaryKey(id);
        if(Objects.isNull(entity)){
            throw new GroundRuntimeException("查看失败");
        }
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
            if (Objects.isNull(contractDTO)) {
                throw new BizException(ResultCode.FIND_CONTRACT_ERROR);
            }
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
            List<ApplyContractBrand> applyContractBrands = applyContractBrandMapper.selectByApplyContractCode(entity.getApplyContractCode());
            List<ApplyContractCategory> applyContractCategories = applyContractCategoryMapper.selectByApplyContractCode(entity.getApplyContractCode());
            List<ApplyContractPlanType> planTypeList = applyContractPlanTypeMapper.selectByApplyContratCode(entity.getApplyContractCode());
            try {
                // 转化成返回申请合同进货额list
                if (CollectionUtils.isNotEmptyCollection(purchaseVolume)) {
                    List<ApplyContractPurchaseVolumeResVo> list = BeanCopyUtils.copyList(purchaseVolume, ApplyContractPurchaseVolumeResVo.class);
                    applyContractResVo.setPurchaseList(list);
                }
                if (CollectionUtils.isNotEmptyCollection(planTypeList)) {
                    List<PlanTypeReqVO> list = BeanCopyUtils.copyList(planTypeList, PlanTypeReqVO.class);
                    applyContractResVo.setPlanTypeList(list);
                }
                if (CollectionUtils.isNotEmptyCollection(applyContractFiles)) {
                    List<ApplyContractFileResVo> fileResVos = BeanCopyUtils.copyList(applyContractFiles, ApplyContractFileResVo.class);
                    applyContractResVo.setFileResVos(fileResVos);
                }
                if (CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)) {
                    List<ApplyContractPurchaseGroupResVo> purchaseGroupResVos = BeanCopyUtils.copyList(applyContractPurchaseGroups, ApplyContractPurchaseGroupResVo.class);
                    applyContractResVo.setPurchaseGroupResVos(purchaseGroupResVos);
                }
                if (CollectionUtils.isNotEmptyCollection(applyContractBrands)) {
                    List<ApplyContractBrandResVo> applyContractBrandResVos = BeanCopyUtils.copyList(applyContractBrands, ApplyContractBrandResVo.class);
                    applyContractResVo.setBrandReqVos(applyContractBrandResVos);
                }
                if (CollectionUtils.isNotEmptyCollection(applyContractCategories)) {
                    List<ApplyContractCategoryResVo> applyContractCategoryResVos = BeanCopyUtils.copyList(applyContractCategories, ApplyContractCategoryResVo.class);
                    applyContractResVo.setCategoryReqVos(applyContractCategoryResVos);
                }
            } catch (Exception e) {
                log.error("进货额转化实体出错");
                throw new GroundRuntimeException("查询进货额失败");
            }
        }
        return applyContractResVo;
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
        List<ContractBrand> contractBrands = contractBrandMapper.selectByContractCode(contractDTO.getContractCode());
        List<ContractCategory> contractCategories = contractCategoryMapper.selectByContractCode(contractDTO.getContractCode());
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

            if(CollectionUtils.isNotEmptyCollection(contractBrands)){
                List<ContractBrandResVo> contractBrandResVos = BeanCopyUtils.copyList(contractBrands, ContractBrandResVo.class);
                applyContractResVo.setBrandResVos(contractBrandResVos);
            }
            if(CollectionUtils.isNotEmptyCollection(contractCategories)){
                List<ContractCategoryResVo> contractCategoryResVos = BeanCopyUtils.copyList(contractCategories, ContractCategoryResVo.class);
                applyContractResVo.setCategoryResVos(contractCategoryResVos);
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
//        applyContractDTO.setId(oldApplyContractDTO.getId());
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.APPLY_CONTRACT_CODE+updateApplyContractReqVo.getContractTypeCode());
        String code = updateApplyContractReqVo.getContractTypeCode()+DateUtils.getCurrentDateTime(DateUtils.YEAR_FORMAT)+fillZero(encodingRule.getNumberingValue());
        applyContractDTO.setApplyContractCode(code);
        applyContractDTO.setContractCode(oldApplyContractDTO.getContractCode());
        //申请状态
        applyContractDTO.setApplyStatus(ApplyStatus.PENDING.getNumber());
        //申请类型
        applyContractDTO.setApplyType(Byte.parseByte("1"));
        StringBuilder purchasingGroupCode = new StringBuilder();
        StringBuilder purchasingGroupName = new StringBuilder();
        if(CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getPurchaseGroupReqVos())) {
            updateApplyContractReqVo.getPurchaseGroupReqVos().forEach(item->{
                purchasingGroupCode.append(item.getPurchasingGroupCode()).append(",");
                purchasingGroupName.append(item.getPurchasingGroupName()).append(",");
            });
        }
        applyContractDTO.setPurchasingGroupCode(purchasingGroupCode.toString().substring(0,purchasingGroupCode.toString().length()-1));
        applyContractDTO.setPurchasingGroupName(purchasingGroupName.toString().substring(0,purchasingGroupName.toString().length()-1));

        Long k = ((ApplyContractService) AopContext.currentProxy()).insertApplyContractDetails(applyContractDTO);
        // 更新编码数据中的最大编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        String content = ApplyStatus.PENDING.getContent().replace(Global.CREATE_BY, applyContractDTO.getUpdateBy()).replace(Global.APPLY_TYPE, "修改");
        supplierCommonService.getInstance(applyContractDTO.getApplyContractCode()+"", HandleTypeCoce.PENDING.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(),content ,null,HandleTypeCoce.PENDING.getName());
        int i = applyContractPlanTypeMapper.deleteByContractCode(updateApplyContractReqVo.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getPlanTypeList())){
            List<ApplyContractPlanType> typeList = BeanCopyUtils.copyList(updateApplyContractReqVo.getPlanTypeList(),ApplyContractPlanType.class);
            if(CollectionUtils.isEmptyCollection(typeList)){
                throw new GroundRuntimeException("请选择时间!");
            }
            typeList.stream().forEach(planType -> planType.setApplyContractCode(applyContractDTO.getApplyContractCode()));
            savePlanTypeList(typeList);
        }
        List<UpdateApplyContractPurchaseVolumeReqVo> purchaseLists = updateApplyContractReqVo.getPurchaseVolumeReqVos();
        //删除旧的关联进货额
        ((ApplyContractService) AopContext.currentProxy()).deleteByPrimaryKey(oldApplyContractDTO.getApplyContractCode());
        if (purchaseLists != null && purchaseLists.size() > 0) {
            //转化成访问数据库实体
            List<ApplyContractPurchaseVolumeDTO> list =BeanCopyUtils.copyList(purchaseLists,ApplyContractPurchaseVolumeDTO.class);
            //设置新的关联编码
            list.stream().forEach(purchase -> purchase.setApplyContractCode(String.valueOf(applyContractDTO.getApplyContractCode())));
            int p = ((ApplyContractService) AopContext.currentProxy()).saveList(list);
        }
        //删除旧的文件数据
        ((ApplyContractService) AopContext.currentProxy()).deleteFiles(oldApplyContractDTO.getApplyContractCode());
        if(CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getFileReqVos())){
            List<ApplyContractFile> files = BeanCopyUtils.copyList(updateApplyContractReqVo.getFileReqVos(),ApplyContractFile.class);
            files.stream().forEach(file ->{
                file.setApplyContractCode(applyContractDTO.getApplyContractCode());
            });
            ((ApplyContractService) AopContext.currentProxy()).saveFileList(files);
        }
        //删除旧的采购组
        ((ApplyContractService) AopContext.currentProxy()).deletePurchaseGroups(oldApplyContractDTO.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getPurchaseGroupReqVos())) {
            List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = BeanCopyUtils.copyList(updateApplyContractReqVo.getPurchaseGroupReqVos(),ApplyContractPurchaseGroup.class);
            if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                applyContractPurchaseGroups.stream().forEach(item ->{
                    item.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                int mm = ((ApplyContractService) AopContext.currentProxy()).savePurchaseGroupList(applyContractPurchaseGroups);
            }
        }

        //删除旧的采购组
        ((ApplyContractService) AopContext.currentProxy()).deleteBrands(oldApplyContractDTO.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getBrandReqVos())) {
            List<ApplyContractBrand> applyContractBrands = BeanCopyUtils.copyList(updateApplyContractReqVo.getBrandReqVos(),ApplyContractBrand.class);
            if(CollectionUtils.isNotEmptyCollection(applyContractBrands)){
                applyContractBrands.stream().forEach(item ->{
                    item.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                int mm = ((ApplyContractService) AopContext.currentProxy()).saveBrandList(applyContractBrands);
            }
        }

        //删除旧的采购组
        ((ApplyContractService) AopContext.currentProxy()).deleteCategories(oldApplyContractDTO.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getCategoryReqVos())) {
            List<ApplyContractCategory> applyContractCategories = BeanCopyUtils.copyList(updateApplyContractReqVo.getCategoryReqVos(),ApplyContractCategory.class);
            if(CollectionUtils.isNotEmptyCollection(applyContractCategories)){
                applyContractCategories.stream().forEach(item ->{
                    item.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                int mm = ((ApplyContractService) AopContext.currentProxy()).saveCategoryList(applyContractCategories);
            }
        }
        workFlow(applyContractDTO.getId(),updateApplyContractReqVo.getPositionCode());
        // 修改合同状态防止在审核中修改合同
        int  kp = contractDao.updateByCode(oldApplyContractDTO.getApplyContractCode(),Byte.valueOf("1"),applyContractDTO.getApplyContractCode());
        return kp;
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

        applyContractDTO.setCompanyCode(getUser().getCompanyCode());
        applyContractDTO.setCompanyName(getUser().getCompanyName());
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
     * 批量保存品牌
     *
     * @param contractBrands
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int saveBrandList(List<ApplyContractBrand> contractBrands) {
        int k = 0;
        if (CollectionUtils.isNotEmptyCollection(contractBrands)) {
            k = applyContractBrandMapper.insertBatch(contractBrands);
        }
        return k;
    }

    /**
     * 批量保存品类
     *
     * @param contractCategories
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int saveCategoryList(List<ApplyContractCategory> contractCategories) {
        int k = 0;
        if (CollectionUtils.isNotEmptyCollection(contractCategories)) {
            k = applyContractCategoryMapper.insertBatch(contractCategories);
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
//       if(k>0){
           return k;
//       }else {
//           throw new GroundRuntimeException("进货额删除失败");
//       }
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
    @Transactional(rollbackFor = Exception.class)
    public int deletePurchaseGroups(String applyContractCode) {
        return applyContractPurchaseGroupMapper.deleteByApplyCode(applyContractCode);
    }

    /**
     * 根据关联编码删除品牌
     *
     * @param applyContractCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBrands(String applyContractCode) {
        return applyContractBrandMapper.deleteByApplyCode(applyContractCode);
    }

    /**
     * 根据关联编码删除品类
     *
     * @param applyContractCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCategories(String applyContractCode) {
        return applyContractCategoryMapper.deleteByApplyCode(applyContractCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void workFlow(Long id, String positionCode) {
        log.info("ApplyContractServiceImplSupplier-workFlow-传入参数是：[{}]", JSON.toJSONString(id));
         ApplyContractDTO applyContractDTO = applyContractDao.selectByPrimaryKey(id);
        try {
            WorkFlowVO workFlowVO = new WorkFlowVO();
//            workFlowVO.setPositionCode(positionCode);
            workFlowVO.setFormUrl(workFlowBaseUrl.applyContractUrl+"?applyType="+applyContractDTO.getApplyType()+"&applyCode="+applyContractDTO.getApplyContractCode()+"&id="+applyContractDTO.getId()+"&itemCode=4"+"&"+workFlowBaseUrl.authority);
            workFlowVO.setHost(workFlowBaseUrl.supplierHost);
            workFlowVO.setFormNo("HT"+IdSequenceUtils.getInstance().nextId());
            String title = "修改";
            if(Objects.equals(Byte.valueOf("0"),applyContractDTO.getApplyType())){
                title = "新增";
            }
            workFlowVO.setTitle(title+applyContractDTO.getYear()+"年度-"+applyContractDTO.getYearName()+"合同名称"+"-"+WorkFlow.APPLY_CONTRACT.getTitle());
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
                String content = ApplyStatus.APPROVAL.getContent().replace(Global.CREATE_BY, applyContractDTO.getUpdateBy()).replace(Global.APPLY_TYPE, title);
                //存日志
                supplierCommonService.getInstance(applyContractDTO.getApplyContractCode()+"", HandleTypeCoce.APPROVAL.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(),content,null,HandleTypeCoce.APPROVAL.getName());
            }else {
                throw new GroundRuntimeException(workFlowRespVO.getMsg());
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
                //审批通过之后，分两种情况一种是添加申请，一种是修改申请
                String content = ApplyStatus.APPROVAL_SUCCESS.getContent().replace(Global.CREATE_BY, account.getCreateBy()).replace(Global.AUDITOR_BY, vo.getApprovalUserName());
                supplierCommonService.getInstance(account.getApplyContractCode()+"", HandleTypeCoce.APPROVAL_SUCCESS.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(), content,null,HandleTypeCoce.APPROVAL_SUCCESS.getName(),vo.getApprovalUserName());
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
                    account.setContractCode(contractDTO.getContractCode());
                    log.info("合同正式数据保存成功");
                    //保存日志信息
                    supplierCommonService.getInstance(contractDTO.getContractCode(),HandleTypeCoce.ADD.getStatus(),ObjectTypeCode.CONTRACT.getStatus(),HandleTypeCoce.ADD_CONTRACT.getName(),null,HandleTypeCoce.ADD.getName(),vo.getApprovalUserName());
                    //查询文件信息
                    List<ApplyContractFile> files = applyContractFileMapper.selectByApplyContractCode(account.getApplyContractCode());
                    if(CollectionUtils.isNotEmptyCollection(files)){
                        try {
                            List<ContractFile> contractFiles = BeanCopyUtils.copyList(files, ContractFile.class);
                            contractFiles.stream().forEach(item->item.setContractCode(contractDTO.getContractCode()));
                            contractFileMapper.insertBatch(contractFiles);
                        } catch (Exception e) {
                            log.error(Global.ERROR, e);
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
                            log.error(Global.ERROR, e);
                            throw new GroundRuntimeException("合同采购组存失败");
                        }
                    }
                    //查询关联的品牌
                    List<ApplyContractBrand> applyContractBrands = applyContractBrandMapper.selectByApplyContractCode(account.getApplyContractCode());
                    if(CollectionUtils.isNotEmptyCollection(applyContractBrands)){
                        try {
                            List<ContractBrand> brands = BeanCopyUtils.copyList(applyContractBrands,ContractBrand.class);
                            brands.stream().forEach(item->item.setContractCode(contractDTO.getContractCode()));
                            contractBrandMapper.insertBatch(brands);
                        } catch (Exception e) {
                            log.error(Global.ERROR, e);
                            throw new GroundRuntimeException("合同品牌存失败");
                        }
                    }
                    //查询关联的品类
                    List<ApplyContractCategory> applyContractCategories = applyContractCategoryMapper.selectByApplyContractCode(account.getApplyContractCode());
                    if(CollectionUtils.isNotEmptyCollection(applyContractCategories)){
                        try {
                            List<ContractCategory> contractCategories = BeanCopyUtils.copyList(applyContractCategories,ContractCategory.class);
                            contractCategories.stream().forEach(item->item.setContractCode(contractDTO.getContractCode()));
                            contractCategoryMapper.insertBatch(contractCategories);
                        } catch (Exception e) {
                            log.error(Global.ERROR, e);
                            throw new GroundRuntimeException("合同品类存失败");
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
                   if(CollectionUtils.isNotEmptyCollection(purchaseLists)){
                       List<ContractPurchaseVolumeDTO> list = BeanCopyUtils.copyList(purchaseLists,ContractPurchaseVolumeDTO.class);
                       list.stream().forEach(purchaselist -> purchaselist.setContractCode(contractDTO.getContractCode()));
                       int s = contractPurchaseVolumeDao.insertContractPurchaseVolume(list);
                   }
                    applyContractDao.updateByPrimaryKeySelective(account);
                    return "success";
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
                        account.setContractCode(newContractDTO.getContractCode());
                        //保存日志
                        supplierCommonService.getInstance(newContractDTO.getContractCode(),HandleTypeCoce.UPDATE.getStatus(),ObjectTypeCode.CONTRACT.getStatus(),HandleTypeCoce.UPDATE_CONTRACT.getName(),null,HandleTypeCoce.UPDATE.getName(),vo.getApprovalUserName());
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
                                log.error(Global.ERROR, e);
                                log.info("转化对象失败");
                            }
                        }
                        //查询关联的计划
                        contractService.deletePlanTypeList(oldContractDTO.getContractCode());
                        List<ApplyContractPlanType> applyContractPlanTypelist = applyContractPlanTypeMapper.selectByApplyContratCode(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(applyContractPlanTypelist)) {
                            List<ContractPlanType> ContractPlanType = BeanCopyUtils.copyList(applyContractPlanTypelist, ContractPlanType.class);
                            ContractPlanType.stream().forEach(item -> item.setContractCode(oldContractDTO.getContractCode()));
                            contractService.savePlanTypeList(ContractPlanType);
                        }
                        //查询关联的采购组
                        contractPurchaseGroupMapper.deleteByContractCode(oldContractDTO.getContractCode());
                        List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = applyContractPurchaseGroupMapper.selectByApplyContractCode(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                            try {
                                List<ContractPurchaseGroup> purchaseGroups = BeanCopyUtils.copyList(applyContractPurchaseGroups,ContractPurchaseGroup.class);
                                purchaseGroups.stream().forEach(purchaseGroup->purchaseGroup.setContractCode(oldContractDTO.getContractCode()));
                                int pn = contractPurchaseGroupMapper.insertBatch(purchaseGroups);
                            } catch (Exception e) {
                                log.error(Global.ERROR, e);
                                throw new GroundRuntimeException("合同采购组存失败");
                            }
                        }
                        //查询关联的品牌
                        contractBrandMapper.deleteByContractCode(oldContractDTO.getContractCode());
                        List<ApplyContractBrand> applyContractBrands = applyContractBrandMapper.selectByApplyContractCode(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(applyContractBrands)){
                            try {
                                List<ContractBrand> brands = BeanCopyUtils.copyList(applyContractBrands,ContractBrand.class);
                                brands.stream().forEach(item->item.setContractCode(oldContractDTO.getContractCode()));
                                int pn =contractBrandMapper.insertBatch(brands);
                            } catch (Exception e) {
                                log.error(Global.ERROR, e);
                                throw new GroundRuntimeException("合同品牌存失败");
                            }
                        }
                        //查询关联的品类
                        contractCategoryMapper.deleteByContractCode(oldContractDTO.getContractCode());
                        List<ApplyContractCategory> applyContractCategories = applyContractCategoryMapper.selectByApplyContractCode(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(applyContractCategories)){
                            try {
                                List<ContractCategory> contractCategories = BeanCopyUtils.copyList(applyContractCategories,ContractCategory.class);
                                contractCategories.stream().forEach(item->item.setContractCode(oldContractDTO.getContractCode()));
                                int pn =contractCategoryMapper.insertBatch(contractCategories);
                            } catch (Exception e) {
                                log.error(Global.ERROR, e);
                                throw new GroundRuntimeException("合同品类存失败");
                            }
                        }
                        //删除旧的关联进货额
                        int k = contractPurchaseVolumeDao.deleteByPrimaryKey(oldContractDTO.getContractCode());
                        // 查询申请表关联的进货额
                        List<ApplyContractPurchaseVolumeDTO> purchaseLists = applyContractPurchaseVolumeMapperDao.selectByApplyContractPurchaseVolume(account.getApplyContractCode());
                        if(CollectionUtils.isNotEmptyCollection(purchaseLists)){
                            // 转化成数据库访问实体列表
                            List<ContractPurchaseVolumeDTO> list =BeanCopyUtils.copyList(purchaseLists,ContractPurchaseVolumeDTO.class);
                            //  设置关联编码
                            list.stream().forEach(volumeDTO -> volumeDTO.setContractCode(String.valueOf(oldContractDTO.getContractCode())));
                            // 保存实体
                            int s = contractPurchaseVolumeDao.insertContractPurchaseVolume(list);
                        }
                        applyContractDao.updateByPrimaryKeySelective(account);
                        return "success";
                    }catch (Exception e) {
                        throw new GroundRuntimeException("合同修改失败");
                    }
                }

            }else if (vo.getApplyStatus().intValue()==ApplyStatus.APPROVAL_FAILED.getNumber()){
                //更新申请数据
                account.setApplyStatus(Byte.valueOf("3"));
                int i = applyContractDao.updateByPrimaryKeySelective(account);
                // 修改审核合同状态
                contractDao.updateByCode(account.getApplyContractCode(),Byte.valueOf("2"),null);
                String content = ApplyStatus.APPROVAL_FAILED.getContent().replace(Global.CREATE_BY, account.getUpdateBy()).replace(Global.AUDITOR_BY, vo.getApprovalUserName());

                supplierCommonService.getInstance(account.getApplyContractCode()+"", HandleTypeCoce.APPROVAL_FAILED.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(),content ,null,HandleTypeCoce.APPROVAL_FAILED.getName(),vo.getApprovalUserName());

                return "success";
            }else if(vo.getApplyStatus().intValue()==ApplyStatus.APPROVAL.getNumber()){
                //传入的是审批中，继续该流程
                ((ApplyContractService) AopContext.currentProxy()).updateApplyContractDetails(account);
                String content = ApplyStatus.APPROVAL_SUCCESS.getContent().replace(Global.CREATE_BY, account.getUpdateBy()).replace(Global.AUDITOR_BY, vo.getApprovalUserName());
                supplierCommonService.getInstance(account.getApplyContractCode()+"", HandleTypeCoce.APPROVAL.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(), content,null,HandleTypeCoce.APPROVAL.getName(),vo.getApprovalUserName());
                return "success";
            }else if(vo.getApplyStatus().intValue()==ApplyStatus.REVOKED.getNumber()){
                account.setApplyStatus(Byte.parseByte("4"));
               ((ApplyContractService) AopContext.currentProxy()).updateApplyContractDetails(account);
                // 打印撤销的日志
                String content = ApplyStatus.REVOKED.getContent().replace(Global.CREATE_BY, account.getUpdateBy()).replace(Global.AUDITOR_BY, vo.getApprovalUserName());
                supplierCommonService.getInstance(account.getApplyContractCode()+"", HandleTypeCoce.REVOKED.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(), content,null,HandleTypeCoce.REVOKED.getName(),vo.getApprovalUserName());
                if(account.getApplyType()==1)
                {// 修改审核合同状态
                    contractDao.updateByCode(account.getApplyContractCode(),Byte.valueOf("2"),null);
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
            //querySupplierReqVO.setApplyBy(authToken.getPersonName());
        }
        if ("1".equals(querySupplierReqVO.getApplyType())) querySupplierReqVO.setApplyType("0");
        if ("2".equals(querySupplierReqVO.getApplyType())) querySupplierReqVO.setApplyType("1");
        return applyContractDao.queryApplyList(querySupplierReqVO);

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
        ApplyContractDTO contractDTO = applyContractDao.selectByFormNO(formNo);
        if(null == contractDTO){
            throw new BizException(ResultCode.OBJECT_EMPTY_BY_FORMNO);
        }
        DetailRequestRespVo detailRequestRespVo = new DetailRequestRespVo();
        detailRequestRespVo.setApplyCode(contractDTO.getApplyContractCode());
        detailRequestRespVo.setId(contractDTO.getId());
        detailRequestRespVo.setItemCode("4");
        return detailRequestRespVo;
    }


    /**
     * 功能描述: 导入处理
     *
     * @param file
     * @return
     * @auther knight.xie
     * @date 2019/9/2 15:50
     */
    @Override
    public ContractImportResp dealImport(MultipartFile file) {
        try {
            List<ContractImportNew> contractImportNews = ExcelUtil.readExcel(file, ContractImportNew.class, 1, 0);
            //验证数据
            dataValidation(contractImportNews);
            contractImportNews.remove(0);
            //查询出需要验证的字典数据
            List<String> dicName = Lists.newArrayList();
            dicName.add("合同类型");
            dicName.add("供货渠道类别");
            dicName.add("合同管理-付款方式");
            Map<String, SupplierDictionaryInfo> dictionaryInfoList = supplierDictionaryInfoDao.selectByName(dicName, getUser().getCompanyCode());
            log.info(JSON.toJSONString(dictionaryInfoList));
            //查询出所有供应商
            List<String> companyNameList = Lists.newArrayList();
            for (ContractImportNew contractImportNew : contractImportNews) {
                if (!companyNameList.contains(contractImportNew.getSupplierName())) {
                    companyNameList.add(contractImportNew.getSupplierName());
                }
            }
            Map<String, SupplyCompany> supplyCompanies = supplyCompanyDao.selectOfficialByCompanyNameList(companyNameList, getUser().getCompanyCode());
            //查出采购组
            List<PurchaseGroupVo> purchaseGroups = purchaseGroupService.getPurchaseGroup(null);
            Map<String, PurchaseGroupVo> purchaseGroupVoList = purchaseGroups.stream().collect(Collectors.toMap(PurchaseGroupVo::getPurchaseGroupName, Function.identity(), (k1, k2) -> k2));
            //查出品类
            List<ProductCategoryRespVO> categoryRespVos = productCategoryService.getTree((byte) 0, "0", getUser().getCompanyCode());
            Map<String, ProductCategoryRespVO> categoryList = categoryRespVos.stream().collect(Collectors.toMap(ProductCategoryRespVO::getCategoryName, Function.identity(), (k1, k2) -> k2));
            //查出品牌
            QueryProductBrandReqVO vo = new QueryProductBrandReqVO();
            vo.setBrandStatus(0);
            vo.setCompanyCode(getUser().getCompanyCode());
            List<QueryProductBrandRespVO> brandRespVOS = productBrandTypeDao.selectListByQueryVO(vo);
            Map<String, QueryProductBrandRespVO> brandList = brandRespVOS.stream().collect(Collectors.toMap(QueryProductBrandRespVO::getBrandName, Function.identity(), (k1, k2) -> k2));
            //封装返回值
            List<ApplyContractReqVo> applyList = Lists.newArrayList();
            List<String> errors = Lists.newArrayList();
            CheckContract checkContract = null;
            for (int i = 0; i < contractImportNews.size(); i++) {
                 checkContract = new CheckContract(contractImportNews.get(i), supplyCompanies,
                        dictionaryInfoList, purchaseGroupVoList, categoryList, brandList).checkCommonData();
                applyList.add(checkContract.getReqVo());
                if(CollectionUtils.isNotEmptyCollection(checkContract.getError())) {
                    errors.add("第" + (i + 2) + "行 " + StringUtils.join(checkContract.getError(),","));
                }
            }
            return new ContractImportResp(applyList,errors);
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_EXCEPTION);
        }
    }

    /**
     * 功能描述: 保存
     *
     * @param req
     * @return
     * @auther knight.xie
     * @date 2019/9/3 19:31
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveImportData(ContractImportResp req) {
        for (ApplyContractReqVo reqVO : req.getApplyList()) {
            reqVO.setSource(Byte.valueOf("1"));
            ((ApplyContractService)AopContext.currentProxy()).saveApplyContract(reqVO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUpdateApply(UpdateApplyContractReqVo updateApplyContractReqVo) {
        // 查询旧的数据
        ApplyContractDTO oldApplyContractDTO = applyContractDao.selectApplyContractByApplyContractCode(updateApplyContractReqVo.getApplyContractCode());
        ApplyContractDTO applyContractDTO  = BeanCopyUtils.copy(updateApplyContractReqVo,ApplyContractDTO.class);
        //设置id
        applyContractDTO.setId(oldApplyContractDTO.getId());
        applyContractDTO.setApplyContractCode(oldApplyContractDTO.getApplyContractCode());
        applyContractDTO.setContractCode(oldApplyContractDTO.getContractCode());
        //申请状态
        applyContractDTO.setApplyStatus(ApplyStatus.PENDING.getNumber());
        //申请类型
        applyContractDTO.setApplyType(oldApplyContractDTO.getApplyType());
        StringBuilder purchasingGroupCode = new StringBuilder();
        StringBuilder purchasingGroupName = new StringBuilder();
        if(CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getPurchaseGroupReqVos())) {
            updateApplyContractReqVo.getPurchaseGroupReqVos().forEach(item->{
                purchasingGroupCode.append(item.getPurchasingGroupCode()).append(",");
                purchasingGroupName.append(item.getPurchasingGroupName()).append(",");
            });
        }
        applyContractDTO.setPurchasingGroupCode(purchasingGroupCode.toString().substring(0,purchasingGroupCode.toString().length()-1));
        applyContractDTO.setPurchasingGroupName(purchasingGroupName.toString().substring(0,purchasingGroupName.toString().length()-1));
        //设置创建时间
        applyContractDTO.setCreateBy(getUser().getPersonName());
        applyContractDTO.setCreateTime(new Date());
        applyContractDTO.setUpdateBy(getUser().getPersonName());
        applyContractDTO.setUpdateTime(new Date());
        //更新数据
        ((ApplyContractService) AopContext.currentProxy()).updateByApplyId(applyContractDTO);

        String content = ApplyStatus.PENDING.getContent().replace(Global.CREATE_BY, getUser().getPersonName()).replace(Global.APPLY_TYPE, "修改");
        supplierCommonService.getInstance(applyContractDTO.getApplyContractCode()+"", HandleTypeCoce.PENDING.getStatus(), ObjectTypeCode.APPLY_CONTRACT.getStatus(),content ,null,HandleTypeCoce.PENDING.getName());

        applyContractPlanTypeMapper.deleteByContractCode(updateApplyContractReqVo.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getPlanTypeList())){
            List<ApplyContractPlanType> typeList = BeanCopyUtils.copyList(updateApplyContractReqVo.getPlanTypeList(),ApplyContractPlanType.class);
            if(CollectionUtils.isEmptyCollection(typeList)){
                throw new GroundRuntimeException("请选择时间!");
            }
            typeList.stream().forEach(planType -> planType.setApplyContractCode(applyContractDTO.getApplyContractCode()));
            savePlanTypeList(typeList);
        }
        List<UpdateApplyContractPurchaseVolumeReqVo> purchaseLists = updateApplyContractReqVo.getPurchaseVolumeReqVos();
        //删除旧的关联进货额
        ((ApplyContractService) AopContext.currentProxy()).deleteByPrimaryKey(oldApplyContractDTO.getApplyContractCode());
        if (purchaseLists != null && purchaseLists.size() > 0) {
            //转化成访问数据库实体
            List<ApplyContractPurchaseVolumeDTO> list =BeanCopyUtils.copyList(purchaseLists,ApplyContractPurchaseVolumeDTO.class);
            //设置新的关联编码
            list.stream().forEach(purchase -> purchase.setApplyContractCode(String.valueOf(applyContractDTO.getApplyContractCode())));
            int p = ((ApplyContractService) AopContext.currentProxy()).saveList(list);
        }
        //删除旧的文件数据
        ((ApplyContractService) AopContext.currentProxy()).deleteFiles(oldApplyContractDTO.getApplyContractCode());
        if(CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getFileReqVos())){
            List<ApplyContractFile> files = BeanCopyUtils.copyList(updateApplyContractReqVo.getFileReqVos(),ApplyContractFile.class);
            files.stream().forEach(file ->{
                file.setApplyContractCode(applyContractDTO.getApplyContractCode());
            });
            ((ApplyContractService) AopContext.currentProxy()).saveFileList(files);
        }
        //删除旧的采购组
        ((ApplyContractService) AopContext.currentProxy()).deletePurchaseGroups(oldApplyContractDTO.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getPurchaseGroupReqVos())) {
            List<ApplyContractPurchaseGroup> applyContractPurchaseGroups = BeanCopyUtils.copyList(updateApplyContractReqVo.getPurchaseGroupReqVos(),ApplyContractPurchaseGroup.class);
            if(CollectionUtils.isNotEmptyCollection(applyContractPurchaseGroups)){
                applyContractPurchaseGroups.stream().forEach(item ->{
                    item.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                int mm = ((ApplyContractService) AopContext.currentProxy()).savePurchaseGroupList(applyContractPurchaseGroups);
            }
        }

        //删除旧的brand
        ((ApplyContractService) AopContext.currentProxy()).deleteBrands(oldApplyContractDTO.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getBrandReqVos())) {
            List<ApplyContractBrand> applyContractBrands = BeanCopyUtils.copyList(updateApplyContractReqVo.getBrandReqVos(),ApplyContractBrand.class);
            if(CollectionUtils.isNotEmptyCollection(applyContractBrands)){
                applyContractBrands.stream().forEach(item ->{
                    item.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                int mm = ((ApplyContractService) AopContext.currentProxy()).saveBrandList(applyContractBrands);
            }
        }

        //删除旧的brand
        ((ApplyContractService) AopContext.currentProxy()).deleteCategories(oldApplyContractDTO.getApplyContractCode());
        if (CollectionUtils.isNotEmptyCollection(updateApplyContractReqVo.getCategoryReqVos())) {
            List<ApplyContractCategory> applyContractCategories = BeanCopyUtils.copyList(updateApplyContractReqVo.getCategoryReqVos(),ApplyContractCategory.class);
            if(CollectionUtils.isNotEmptyCollection(applyContractCategories)){
                applyContractCategories.stream().forEach(item ->{
                    item.setApplyContractCode(applyContractDTO.getApplyContractCode());
                });
                int mm = ((ApplyContractService) AopContext.currentProxy()).saveCategoryList(applyContractCategories);
            }
        }
        workFlow(applyContractDTO.getId(), updateApplyContractReqVo.getPositionCode());
        // 修改合同状态防止在审核中修改合同
//        int  kp = contractDao.updateByCode(oldApplyContractDTO.getApplyContractCode(),Byte.valueOf("1"),applyContractDTO.getApplyContractCode());
        return Boolean.TRUE;
    }

    @Override
    @Update
    public Integer updateByApplyId(ApplyContractDTO applyContractDTO) {
        return applyContractDao.updateByPrimaryKey(applyContractDTO);
    }

    private void dataValidation(List<ContractImportNew> contractImportNews) {
        if(CollectionUtils.isEmptyCollection(contractImportNews)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (contractImportNews.size()<2) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = ContractImportNew.HEADER;
        boolean equals = contractImportNews.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
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

