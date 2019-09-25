package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractDao;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractPurchaseVolumeDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractPurchaseVolumeDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractByUsernameReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.PlanTypeReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.QueryContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.*;
import com.aiqin.bms.scmp.api.supplier.mapper.*;
import com.aiqin.bms.scmp.api.supplier.service.ContractService;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description:合同Service
 * @author:曾兴旺
 * @date: 2018/11/30
 */
@Service
public class ContractServiceImpl extends BaseServiceImpl implements ContractService{

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private ContractPurchaseVolumeDao contractPurchaseVolumeDao;

    @Autowired
    private ContractPurchaseGroupMapper contractPurchaseGroupMapper;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ContractFileMapper contractFileMapper;

    @Autowired
    private ContractPlanTypeMapper contractPlanTypeMapper;

    @Autowired
    private ContractBrandMapper contractBrandMapper;

    @Autowired
    private ContractCategoryMapper contractCategoryMapper;

    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;

    /**
     * 分页获取合同列表
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryContractResVo> findContractList(QueryContractReqVo vo) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                vo.setCompanyCode(authToken.getCompanyCode());
                vo.setPersonId(authToken.getPersonId());
            }
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            List<QueryContractResVo> contractResDTOList = contractDao.selectBySelectContract(vo);
            BasePage<QueryContractResVo> basePage = PageUtil.getPageList(vo.getPageSize(),contractResDTOList);
            return basePage;
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 通过传过来申请通过的申请合同，保存合同
     * @param contractReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int saveContract(ContractReqVo contractReqVo) {
        // copy 成数据库访问实体
         ContractDTO contractDTO = new ContractDTO();
         BeanCopyUtils.copy(contractReqVo,contractDTO);

        //生成合同编号
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.CONTRACT_CODE);
        contractDTO.setContractCode(String.valueOf(encodingRule.getNumberingValue()));

        int k = ((ContractService) AopContext.currentProxy()).saveContractDetails(contractDTO);
        //更新数据库编码最大值
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        if(k > 0){
            try {
                if (contractDTO.getRebateClause().equals((byte)1)){
                    List<ContractPlanType> typeList = BeanCopyUtils.copyList(contractReqVo.getPlanTypeList(),ContractPlanType.class);
                    typeList.stream().forEach(planType -> planType.setContractCode(contractDTO.getContractCode()));
                    savePlanTypeList(typeList);
                }
                List<ContractPurchaseVolumeDTO> list = BeanCopyUtils.copyList(contractReqVo.getPurchaseCount(),ContractPurchaseVolumeDTO.class);
                list.stream().forEach(purchaselist -> purchaselist.setContractCode(contractDTO.getContractCode()));
                int s = ((ContractService) AopContext.currentProxy()).saveList(list);
                if(s > 0){
                        return s;
                }else {
                    throw new GroundRuntimeException("合同进货额保存失败");
                }
            } catch (Exception e) {
                throw new GroundRuntimeException("合同进货额保存失败");
            }
        }else {
            throw new GroundRuntimeException("合同保存失败");
        }
    }
    @Override
    public void savePlanTypeList(List<ContractPlanType> typeList) {
        if(CollectionUtils.isEmptyCollection(typeList)){
            return;
        }
        int i = contractPlanTypeMapper.insertBatch(typeList);
        if(i!=typeList.size()){
            throw new BizException(ResultCode.SAVE_PLAN_TYPE_FAILED);
        }
    }

    /**
     * 查看合同详情
     * @param id
     * @return
     */
    @Override
    public ContractResVo findContractDetail(Long id) {
        ContractResVo contractResVo = new ContractResVo();
        ContractDTO entity = contractDao.selectByPrimaryKey(id);
        if (null == entity) {
            throw new GroundRuntimeException("没有找到对应的信息");
        }
        BeanCopyUtils.copy(entity, contractResVo);
        List<String> dicNameList = Lists.newArrayList();
        dicNameList.add("结算方式");
        Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(dicNameList, getUser().getCompanyCode());
//        dicMap.forEach((k, v) -> {
//            if (Objects.equals(contractResVo.getSettlementMethod().toString(), v.getSupplierDictionaryValue())) {
//                contractResVo.setSettlementMethodName(v.getSupplierContent());
//            }
//        });
        List<ContractPurchaseVolumeDTO> purchaseVolume = contractPurchaseVolumeDao.selectByContractPurchaseVolume(contractResVo.getContractCode());
        List<ContractFile> contractFiles = contractFileMapper.selectByContractCode(contractResVo.getContractCode());
        List<ContractPurchaseGroup> contractPurchaseGroups = contractPurchaseGroupMapper.selectByContractCode(contractResVo.getContractCode());
        List<ContractBrand> contractBrands = contractBrandMapper.selectByContractCode(contractResVo.getContractCode());
        List<ContractCategory> contractCategories = contractCategoryMapper.selectByContractCode(contractResVo.getContractCode());
        List<ContractPlanType> planTypeList = contractPlanTypeMapper.selectByCode(contractResVo.getContractCode());
        try {
            if (CollectionUtils.isNotEmptyCollection(purchaseVolume)) {
                List<ContractPurchaseVolumeResVo> list = BeanCopyUtils.copyList(purchaseVolume, ContractPurchaseVolumeResVo.class);
                contractResVo.setPurchaseVolumeReqVos(list);
            }
            if (CollectionUtils.isNotEmptyCollection(planTypeList)) {
                List<PlanTypeReqVO> copyList = BeanCopyUtils.copyList(planTypeList, PlanTypeReqVO.class);
                contractResVo.setPlanTypeList(copyList);
            } else {
                contractResVo.setPlanTypeList(Lists.newArrayList());
            }
            if (CollectionUtils.isNotEmptyCollection(contractFiles)) {
                List<ContractFileResVo> fileResVos = BeanCopyUtils.copyList(contractFiles, ContractFileResVo.class);
                contractResVo.setFileResVos(fileResVos);
            }
            if (CollectionUtils.isNotEmptyCollection(contractPurchaseGroups)) {
                List<ContractPurchaseGroupResVo> purchaseGroupResVos = BeanCopyUtils.copyList(contractPurchaseGroups, ContractPurchaseGroupResVo.class);
                contractResVo.setPurchaseGroupResVos(purchaseGroupResVos);
            }

            if (CollectionUtils.isNotEmptyCollection(contractBrands)) {
                List<ContractBrandResVo> contractBrandResVos = BeanCopyUtils.copyList(contractBrands, ContractBrandResVo.class);
                contractResVo.setBrandResVos(contractBrandResVos);
            }
            if (CollectionUtils.isNotEmptyCollection(contractCategories)) {
                List<ContractCategoryResVo> contractCategoryResVos = BeanCopyUtils.copyList(contractCategories, ContractCategoryResVo.class);
                contractResVo.setCategoryResVos(contractCategoryResVos);
            }
            //获取操作日志
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setPageNo(1);
            operationLogVo.setPageSize(100);
            operationLogVo.setObjectType(ObjectTypeCode.CONTRACT.getStatus());
            operationLogVo.setObjectId(contractResVo.getContractCode());
            BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo, 62);
            List<LogData> logDataList = new ArrayList<>();
            if (null != pageList.getDataList() && pageList.getDataList().size() > 0) {
                logDataList = pageList.getDataList();
            }
            contractResVo.setLogDataList(logDataList);
            return contractResVo;

        } catch (Exception e) {
            throw new GroundRuntimeException("实体转化错误");
        }
    }

    /**
     * 修改合同类型转化
     * @param contractReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateContract(ContractReqVo contractReqVo) {

        try {
            // 根据关联编码去获取旧合同详情，其中用的有合同id。合同编码
            ContractDTO oldContractDTO = contractDao.selectByApplyCode(contractReqVo.getApplyContractCode());
            //转化成访问数据库实体
            ContractDTO newContractDTO =  new ContractDTO();
            BeanCopyUtils.copy(contractReqVo,newContractDTO);
            //输入id
            newContractDTO.setId(oldContractDTO.getId());
            //  输入编码
            newContractDTO.setContractCode(oldContractDTO.getContractCode());
            // 跟新活动主体
            int kp = ((ContractService)AopContext.currentProxy()).updateByPrimaryKeySelective(newContractDTO);
            //删除旧的关联进货额
          if(kp>0)  {
              int k = ((ContractService)AopContext.currentProxy()).deleteList(oldContractDTO.getContractCode());
                if(k>0){
                    // 保存
                    // 转化成数据库访问实体列表
                    List<ContractPurchaseVolumeDTO> list =BeanCopyUtils.copyList(contractReqVo.getPurchaseCount(),ContractPurchaseVolumeDTO.class);
                    //  设置关联编码
                    list.stream().forEach(purchaselist -> purchaselist.setContractCode(String.valueOf(oldContractDTO.getContractCode())));
                    // 保存实体
                    int s = ((ContractService) AopContext.currentProxy()).saveList(list);
                    if(s > 0){
                                   return s;
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

    /**
     * 逻辑删除合同
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteContract(Long id) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setId(id);
        contractDTO.setDelFlag(Byte.parseByte("1"));
        int k =((ContractService)AopContext.currentProxy()).updateByPrimaryKeySelective(contractDTO);
        if(k>0){
            return k;
        }else {
            throw new GroundRuntimeException("删除失败");
        }
    }

    /**
     * 保存合同主体
     * @param contractDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int saveContractDetails(ContractDTO contractDTO) {

        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            contractDTO.setCompanyCode(authToken.getCompanyCode());
            contractDTO.setCompanyName(authToken.getCompanyName());
        }
        int k = contractDao.insertSelective(contractDTO);
        if(k>0){
            return k;
        }else {
            throw new GroundRuntimeException("合同主体插入失败");
        }
    }

    /**
     * 批量保存进货额
     * @param purchaselists
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int saveList(List<ContractPurchaseVolumeDTO> purchaselists) {
        int k=0;
        if(purchaselists!= null && purchaselists.size()>0){
            k = contractPurchaseVolumeDao.insertContractPurchaseVolume(purchaselists);
            if(k > 0){
                return k;
            }else {
                throw new GroundRuntimeException("保存进货额失败");
            }
        }else {
            throw new GroundRuntimeException("applyContractPurchaseVolumes 不能为空");
        }
    }

    /**
     * 有选择的更新实体
     * @param contractDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateByPrimaryKeySelective(ContractDTO contractDTO) {
       int k = contractDao.updateByPrimaryKeySelective(contractDTO);
       if(k>0){
           return k;
       }else {
           throw new GroundRuntimeException("更新失败");
       }
    }


    /**
     * 修改合同主体
     * @param contractDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int updateContractDetails(ContractDTO contractDTO) {
        int k = contractDao.updateByPrimaryKey(contractDTO);
        if(k>0){
            return k;
        }else {
            throw new  GroundRuntimeException("修改合同主体失败");
        }
    }

    /**
     * 逻辑删除旧的进货额
     * @param contractCode
     * @return
     */
    @Override
    public int deleteList(String contractCode) {
        int k=0;
        if(StringUtils.isNotEmpty(contractCode)){
            k = contractPurchaseVolumeDao.deleteByPrimaryKey(contractCode);
            if(k > 0){
                return k;
            }else {
                throw new GroundRuntimeException("删除进货额失败");
            }
        }else {
            throw new GroundRuntimeException("合同编码不能为空");
        }
    }

    /**
     * 根据登陆人查询合同
     * @param reqVO
     * @return
     */
    @Override
    public List<ContractResVo> getContractByUsername(ContractByUsernameReqVo reqVO) {

        try {
            List<ContractDTO> contractByUsername = contractDao.getContractByUsername(reqVO);
            List<ContractResVo> list= BeanCopyUtils.copyList(contractByUsername,ContractResVo.class);
            for (ContractResVo resVo : list) {
                List<ContractPurchaseVolumeDTO> purchaseVolume = contractPurchaseVolumeDao.selectByContractPurchaseVolume(resVo.getContractCode());
                    List<ContractPurchaseVolumeResVo>  contractPurchaseVolumeResVos =BeanCopyUtils.copyList(purchaseVolume,ContractPurchaseVolumeResVo.class);
                resVo.setPurchaseVolumeReqVos(contractPurchaseVolumeResVos);
            }
            return list;
        } catch (Exception e) {
            throw new GroundRuntimeException("查询失败");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePlanTypeList(String contractCode) {
         contractPlanTypeMapper.deletePlanTypeList(contractCode);
    }


    /**
     * 功能描述: 限定采购组
     *
     * @return
     * @auther knight.xie
     * @date 2019/7/1 20:16
     */
    @Override
    public List<ContractPurchaseResVo> getContractByPurchaseGroup() {

        Map<String,String> map = Maps.newHashMap();
        AuthToken user = getUser();
        map.put("companyCode",user.getCompanyCode());
        map.put("personId",user.getPersonId());

        return contractDao.getContractByMap(map);
    }

    /**
     * 功能描述: 根据供应商查询合同
     *
     * @param supplierCode
     * @return
     * @auther knight.xie
     * @date 2019/8/3 12:11
     */
    @Override
    public List<ContractPurchaseResVo> getContractBySupplierCode(String supplierCode) {
        Map<String,String> map = Maps.newHashMap();
        AuthToken user = getUser();
        map.put("companyCode",user.getCompanyCode());
        map.put("supplierCode",supplierCode);
        return contractDao.getContractByMap(map);
    }
}
