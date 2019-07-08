package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.*;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.SkuStatusRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.DetailConfigSupplierRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductSkuConfigService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitCapacityService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:44
 */
@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.APPLY_GOODS_CONFIG)
public class ProductSkuConfigServiceImpl extends BaseServiceImpl implements ProductSkuConfigService , WorkFlowHelper {


    @Autowired
    private ProductSkuConfigDraftMapper draftMapper;
    @Autowired
    private ApplyProductSkuConfigMapper applyMapper;
    @Autowired
    private ProductSkuConfigMapper mapper;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;
    @Autowired
    private ProductSkuConfigSpareWarehouseDraftMapper spareWarehouseDraftMapper;
    @Autowired
    private ApplyProductSkuConfigSpareWarehouseMapper applySpareWarehouseMapper;
    @Autowired
    private ProductSkuConfigSpareWarehouseMapper spareWarehouseMapper;
    @Autowired
    private ProductSkuSupplyUnitService productSkuSupplyUnitService;

    @Autowired
    private ProductSkuSupplyUnitCapacityService productSkuSupplyUnitCapacityService;

    /**
     * 批量保存临时配置信息
     * @param configReqVos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertDraftList(List<SaveSkuConfigReqVo> configReqVos) {
        Integer num = 0;
        ProductSkuConfigDraft draft;
        List<SpareWarehouseReqVo> spareWarehouseReqVos = Lists.newArrayList();
        for (SaveSkuConfigReqVo item : configReqVos) {
            draft = new ProductSkuConfigDraft();
            BeanCopyUtils.copy(item,draft);
            draft.setApplyShow(Global.APPLY_SKU_CONFIG_UN_SHOW);
            draft.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
            num += ((ProductSkuConfigService)AopContext.currentProxy()).insertDraft(draft);
            if(CollectionUtils.isNotEmpty(item.getSpareWarehouses())){
                for (SpareWarehouseReqVo spareWarehouse : item.getSpareWarehouses()) {
                    spareWarehouse.setConfigCode(draft.getConfigCode());
                }
                spareWarehouseReqVos.addAll(item.getSpareWarehouses());
            }
        }
        List<ProductSkuConfigSpareWarehouseDraft> draftList = null;
        try {
            draftList = BeanCopyUtils.copyList(spareWarehouseReqVos,ProductSkuConfigSpareWarehouseDraft.class);
        } catch (Exception e) {
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
        ((ProductSkuConfigService)AopContext.currentProxy()).insertSpareWarehouseDraftList(draftList);
        return num;
    }

    /**
     * 批量修改临时配置信息
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateDraftList(UpdateSkuConfigSupplierReqVo reqVo) {
        //获取公司
        AuthToken authToken = getUser();
        //商品配置更新
        List<UpdateSkuConfigReqVo> configReqVos = reqVo.getConfigs();
        if(CollectionUtils.isEmpty(configReqVos)){
            throw new BizException(ResultCode.UPDATE_ERROR);
        }
        String configCode = configReqVos.get(0).getConfigCode();
        ProductSkuConfig productSkuConfig = mapper.selectByConfigCode(configCode);
        if(Objects.equals(productSkuConfig.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            throw new BizException(ResultCode.UN_SUBMIT_APPROVAL);
        }
        Integer num = 0;
        List<ProductSkuConfigDraft> drafts;
        List<SpareWarehouseReqVo> spareWarehouseReqVos = Lists.newArrayList();
        configReqVos.forEach(item->{
            item.getSpareWarehouses().forEach(item1->{
                item1.setConfigCode(item.getConfigCode());
            });
            spareWarehouseReqVos.addAll(item.getSpareWarehouses());
        });
        try {
           drafts = BeanCopyUtils.copyList(configReqVos,ProductSkuConfigDraft.class);
        } catch (Exception e) {
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
        drafts.forEach(item->{
            item.setApplyShow(Global.APPLY_SKU_CONFIG_SHOW);
            item.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
            item.setCompanyCode(authToken.getCompanyCode());
            item.setCompanyName(authToken.getCompanyName());
            item.setProductCode(reqVo.getProductCode());
            item.setProductName(reqVo.getProductName());
            item.setSkuCode(reqVo.getSkuCode());
            item.setSkuName(reqVo.getSkuName());
        });
        //插入临时表
        num =  ((ProductSkuConfigService)AopContext.currentProxy()).insertDraftBatch(drafts);
        //更新正式表申请状态
        ApplyProductSkuConfigReqVo req = new ApplyProductSkuConfigReqVo();
        req.setApplyCode(productSkuConfig.getApplyCode());
        req.setAuditorStatus(ApplyStatus.APPROVAL.getNumber());
        mapper.updateApplyStatusByApplyCode(req);
        List<ProductSkuConfigSpareWarehouseDraft> draftList = null;
        try {
            draftList = BeanCopyUtils.copyList(spareWarehouseReqVos,ProductSkuConfigSpareWarehouseDraft.class);
        } catch (Exception e) {
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
        //插入临时表
        ((ProductSkuConfigService)AopContext.currentProxy()).insertSpareWarehouseDraftList(draftList);
        //供应商更新
        if(CollectionUtils.isNotEmpty(reqVo.getUpdateProductSkuSupplyUnitReqVos())) {
            List<UpdateProductSkuSupplyUnitReqVo> supplyUnitReqVos = reqVo.getUpdateProductSkuSupplyUnitReqVos();
            List<ProductSkuSupplyUnitDraft> skuSupplyUnitDrafts = BeanCopyUtils.copyList(supplyUnitReqVos, ProductSkuSupplyUnitDraft.class);
            List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts = Lists.newArrayList();
            skuSupplyUnitDrafts.forEach(item -> {
                item.setProductSkuCode(reqVo.getSkuCode());
                item.setProductSkuName(reqVo.getSkuCode());
                item.setUsageStatus(StatusTypeCode.USE.getStatus());
                if (CollectionUtils.isNotEmpty(item.getProductSkuSupplyUnitCapacityDrafts())) {
                    item.getProductSkuSupplyUnitCapacityDrafts().forEach(item2 -> {
                        item2.setProductSkuCode(item.getProductSkuCode());
                        item2.setProductSkuName(item.getProductSkuName());
                        item2.setSupplyUnitCode(item.getSupplyUnitCode());
                        item2.setSupplyUnitName(item.getSupplyUnitName());
                    });
                    productSkuSupplyUnitCapacityDrafts.addAll(item.getProductSkuSupplyUnitCapacityDrafts());
                }
            });
            productSkuSupplyUnitService.insertDraftList(skuSupplyUnitDrafts);
            //供应商产能
            if (CollectionUtils.isNotEmpty(productSkuSupplyUnitCapacityDrafts)) {
                productSkuSupplyUnitCapacityService.insertDraftList(productSkuSupplyUnitCapacityDrafts);
            }
        }
        return num;
    }

    /**
     * 批量插入临时配置信息(数据库)
     *
     * @param drafts
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public Integer insertDraftBatch(List<ProductSkuConfigDraft> drafts) {
        return draftMapper.insertBatch(drafts);
    }

    /**
     * 保存临时配置信息
     *
     * @param draft

     * @return
     */
    @Override
    @Save
    @Transactional(rollbackFor = Exception.class)
    public Integer insertDraft(ProductSkuConfigDraft draft) {
        AuthToken token = AuthenticationInterceptor.getCurrentAuthToken();
        if (null != token) {
            draft.setCompanyCode(token.getCompanyCode());
            draft.setCompanyName(token.getCompanyName());
        }
        //设置编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.SKU_CONFIG_CODE);
        draft.setConfigCode(String.valueOf(encodingRule.getNumberingValue()));
        // 更新数据库编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        return draftMapper.insertSelective(draft);
    }

    /**
     * 查询临时表配置信息,排除掉不显示(从SKU新增来的数据)
     *
     * @return
     */
    @Override
    public DetailConfigSupplierRespVo findDraftList(String companyCode) {
        DetailConfigSupplierRespVo respVo = new DetailConfigSupplierRespVo();
        List<SkuConfigsRepsVo> list = draftMapper.getList(companyCode);
        respVo.setConfigs(list);
        List<ProductSkuSupplyUnitRespVo> skuSupplyUnitRespVos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(list)){
            skuSupplyUnitRespVos = productSkuSupplyUnitService.getDraftList(list.get(0).getSkuCode());
        }
        respVo.setSuppliers(skuSupplyUnitRespVos);
        return respVo;
    }

    /**
     * 删除临时表配置信息
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteDraftById(Long id) {
        int num = 0;
        ProductSkuConfigDraft productSkuConfigDraft = draftMapper.selectByPrimaryKey(id);
        if (null != productSkuConfigDraft) {
           num = draftMapper.deleteByPrimaryKey(id);
           //删除备用仓库信息
           spareWarehouseDraftMapper.deleteByConfigCode(productSkuConfigDraft.getConfigCode());
        }
        return num;
    }

    /**
     * 根据SkuCodes批量删除
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDraftBySkuCodes(List<String> skuCodes) {
        int num = 0;
        List<ProductSkuConfigDraft> draftMapperListBySkuCodes = draftMapper.getListBySkuCodes(skuCodes);
        if(CollectionUtils.isNotEmpty(draftMapperListBySkuCodes)){
            List<String> configCodes = draftMapperListBySkuCodes.stream().map(ProductSkuConfigDraft::getConfigCode).collect(Collectors.toList());
            num = draftMapper.deleteBySkuCodes(skuCodes);
            //删除备用仓库信息
            spareWarehouseDraftMapper.deleteByConfigCodes(configCodes);
        }
        return num;
    }

    /**
     * 保存申请信息
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertApplyList(ApplySkuConfigReqVo reqVo) {
        //获取登录人
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (null == currentAuthToken) {
            throw new BizException(ResultCode.LOGIN_ERROR);
        }
        //通过编码查询出配置信息数据
        List<ProductSkuConfigDraft> configDrafts = draftMapper.selectByCodes(reqVo.getSkuConfigs());
        if (CollectionUtils.isEmpty(configDrafts)) {
            throw new BizException(ResultCode.SUBMIT_ERROR);
        }
        //通过编码查询出备用仓库信息
        List<ProductSkuConfigSpareWarehouseDraft> spareWarehouseDrafts = spareWarehouseDraftMapper.
                getListByConfigCodes(reqVo.getSkuConfigs());
        //申请表配置信息
        List<ApplyProductSkuConfig> applyProductSkuConfigs;
        //申请表备用仓库信息
        List<ApplyProductSkuConfigSpareWarehouse> applyProductSkuConfigSpareWarehouses;
        try {
            applyProductSkuConfigs = BeanCopyUtils.copyList(configDrafts,ApplyProductSkuConfig.class);
            applyProductSkuConfigSpareWarehouses =  BeanCopyUtils.copyList(spareWarehouseDrafts,ApplyProductSkuConfigSpareWarehouse.class);
        } catch (Exception e) {
            throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
        //获取编码
        EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.APPLY_SKU_CONFIG_CODE);
        String code = numberingType.getNumberingValue().toString();
        String formNo = "SC" + new IdSequenceUtils().nextId();
        Date currentDate = new Date();
        applyProductSkuConfigs.forEach(item->{
            item.setId(null);
            item.setApplyCode(code);
            item.setFormNo(formNo);
            item.setBeEffective(Global.UN_EFFECTIVE);
            item.setCreateTime(currentDate);
            item.setSelectionEffectiveTime(reqVo.getSelectionEffectiveTime());
            item.setSelectionEffectiveStartTime(reqVo.getSelectionEffectiveStartTime());
            item.setAuditorStatus(ApplyStatus.APPROVAL.getNumber());
            item.setDirectSupervisorCode(reqVo.getDirectSupervisorCode());
            item.setDirectSupervisorName(reqVo.getDirectSupervisorName());
        });
        //批量保存配置信息
        Integer num = applyMapper.insertBatch(applyProductSkuConfigs);
        if (num != applyProductSkuConfigs.size()) {
            log.error("希望插入主表条数:[{}],实际插入主表条数：[{}]", applyProductSkuConfigs.size(), num);
            throw new BizException(ResultCode.SKU_CONFIG_SUBMIT_ERROR);
        }
        //批量保存备用仓库
        applySpareWarehouseMapper.insertBatch(applyProductSkuConfigSpareWarehouses,code);
        //更新编码
        encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
        //删除临时表配置信息
        draftMapper.deleteByConfigCodes(reqVo.getSkuConfigs());
        //删除临时表备用仓库信息
        spareWarehouseDraftMapper.deleteByConfigCodes(reqVo.getSkuConfigs());

        //供应商信息保存
        //根据供应商信息ID查询供应商信息
        List<Long> supplierId = reqVo.getSupplierId();
        List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts = productSkuSupplyUnitService.getDraftByIds(supplierId);
        if(CollectionUtils.isNotEmpty(productSkuSupplyUnitDrafts)){
            List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = BeanCopyUtils.copyList(productSkuSupplyUnitDrafts,ApplyProductSkuSupplyUnit.class);
            applyProductSkuSupplyUnits.forEach(item->{
                item.setApplyCode(code);
            });
            productSkuSupplyUnitService.insertApplyList(applyProductSkuSupplyUnits);
            productSkuSupplyUnitService.deleteDraftByIds(supplierId);
            //供应商产能信息
            List<ProductSkuSupplyUnitCapacityDraft> supplyUnitCapacityDrafts =
                    productSkuSupplyUnitCapacityService.getDraftsBySupplyUnitDrafts(productSkuSupplyUnitDrafts);
            if (CollectionUtils.isNotEmpty(supplyUnitCapacityDrafts)) {
                List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities = BeanCopyUtils.copyList(supplyUnitCapacityDrafts,ApplyProductSkuSupplyUnitCapacity.class);
                applyProductSkuSupplyUnitCapacities.forEach(item->{
                    item.setApplyCode(code);
                });
                List<Long> ids = supplyUnitCapacityDrafts.stream().map(ProductSkuSupplyUnitCapacityDraft::getId).distinct().collect(Collectors.toList());
                //批量新增申请
                productSkuSupplyUnitCapacityService.insertApplyList(applyProductSkuSupplyUnitCapacities);
                //批量删除草稿
                productSkuSupplyUnitCapacityService.deleteDraftByIds(ids);
            }
        }
        //调用审批的接口
        workFlow(formNo,code,currentAuthToken.getPersonName(),reqVo.getDirectSupervisorCode());
        return num;
    }

    /**
     * 外部调用保存到申请列表,不进入审批流
     *
     * @param applyProductSkus
     * @return
     */
    @Override
    public Integer outInsertApplyList(List<ApplyProductSku> applyProductSkus) {
        List<String> skuCodes = applyProductSkus.stream().map(ApplyProductSku::getSkuCode).distinct().collect(Collectors.toList());
        String code = applyProductSkus.get(0).getApplyCode();
        String formNo = applyProductSkus.get(0).getFormNo();
        Integer num = 0;
        //根据skuCodes查询出临时表信息
        List<ProductSkuConfigDraft> drafts = draftMapper.getListBySkuCodes(skuCodes);
        if(CollectionUtils.isNotEmpty(drafts)){
            List<String> configCodes = drafts.stream().map(ProductSkuConfigDraft::getConfigCode).distinct().collect(Collectors.toList());
            List<ApplyProductSkuConfig> applyProductSkuConfigs  = BeanCopyUtils.copyList(drafts,ApplyProductSkuConfig.class);
            //通过编码查询出备用仓库信息
            List<ProductSkuConfigSpareWarehouseDraft> spareWarehouseDrafts = spareWarehouseDraftMapper.
                    getListByConfigCodes(configCodes);
            List<ApplyProductSkuConfigSpareWarehouse>  applyProductSkuConfigSpareWarehouses =
                    BeanCopyUtils.copyList(spareWarehouseDrafts,ApplyProductSkuConfigSpareWarehouse.class);
            Date currentDate = new Date();
            applyProductSkuConfigs.stream().forEach(item->{
                item.setId(null);
                item.setApplyCode(code);
                item.setFormNo(formNo);
                item.setCreateTime(currentDate);
            });
            num = applyMapper.insertBatch(applyProductSkuConfigs);
            //批量保存备用仓库
            applySpareWarehouseMapper.insertBatch(applyProductSkuConfigSpareWarehouses,code);
            //删除临时表配置信息
            draftMapper.deleteOutByConfigCodes(configCodes);
            //删除临时表备用仓库信息
            spareWarehouseDraftMapper.deleteByConfigCodes(configCodes);
        }

        return num;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void workFlow(String formNo, String applyCode, String userName,String directSupervisorCode) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormUrl(workFlowBaseUrl.applySkuConfig + "?approvalType=2&code=" + applyCode + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(formNo);
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_GOODS_CONFIG.getNum());
        workFlowVO.setTitle(userName + "创建商品配置审批");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_GOODS_CONFIG);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            //TODO 这里暂时没有任何操作
        } else {
            throw new BizException(ResultCode.SKU_CONFIG_SUBMIT_ERROR);
        }
    }
    /**
     * 审核回调接口
     *
     * @param vo
     * @return
     * @author zth
     * @date 2019/1/15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo) {
        WorkFlowCallbackVO newVO = updateSupStatus(vo);
        //审批中，直接返回成功
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }
        //首先通过formNO查找数据
        List<ApplyProductSkuConfig> list = applyMapper.selectByFormNo(newVO.getFormNo());
        if (CollectionUtils.isEmpty(list)) {
            log.error("通过formNo查询数据异常,传入数据:{}", JSON.toJSONString(vo));
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "通过编码查询数据异常！"));

        }
        //判断查出来的是否是在审批中的数据
        if(!list.get(0).getAuditorStatus().equals(ApplyStatus.APPROVAL.getNumber())){
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常，不是在审批中的数据！"));
        }
        String applyCode = list.get(0).getApplyCode();
        //审批驳回
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
            updateApplyInfoByVO(newVO,applyCode);
            return WorkFlowReturn.SUCCESS;
        }
        //撤销
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
            updateApplyInfoByVO(newVO,applyCode);
            return WorkFlowReturn.SUCCESS;
        }
        //审批通过
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            try {
                updateApplyInfoByVO(newVO,applyCode);
                //通过applyCode查询备用仓库
                List<ApplyProductSkuConfigSpareWarehouse> applySpareWarehouses = applySpareWarehouseMapper.
                        selectByApplyCode(applyCode);
                //获取配置编号
                List<String> configCodes = applySpareWarehouses.stream().map(item -> item.getConfigCode()).distinct().
                        collect(Collectors.toList());
                //保存正式备用仓库信息
                //删除正式
                spareWarehouseMapper.deleteByConfigCodes(configCodes);
                //批量插入
                List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses = BeanCopyUtils.copyList(applySpareWarehouses,
                        ProductSkuConfigSpareWarehouse.class);
                ((ProductSkuConfigService)AopContext.currentProxy()).insertSpareWarehouseList(skuConfigSpareWarehouses);
                //供应商信息
                productSkuSupplyUnitService.saveList(applyCode);
                //供应商产能信息
                productSkuSupplyUnitCapacityService.saveList(applyCode);
                //保存商品配置正式数据
                saveOfficial(newVO, list);
                return WorkFlowReturn.SUCCESS;
            } catch (Exception e) {
                log.error(e.getMessage());
                return WorkFlowReturn.FALSE;
            }
        }
        return WorkFlowReturn.FALSE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApplyInfoByVO(WorkFlowCallbackVO newVO,String applyCode) {
        //处理数据
        ApplyProductSkuConfigReqVo req = dealData(newVO);
        req.setApplyCode(applyCode);
        //批量更新数据
        updateApplyInfoByVO(req);
        updateInfoByVo(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfficial(WorkFlowCallbackVO newVO, List<ApplyProductSkuConfig> list) {
        List<ProductSkuConfig> addList = Lists.newArrayList();
        List<ProductSkuConfig> updateList = Lists.newArrayList();
        //分类
        list.forEach(o -> {
            if (Objects.equals(StatusTypeCode.ADD_APPLY.getStatus(), o.getApplyType())) {
                addList.add(dealData(o,newVO));
            }
            if (Objects.equals(StatusTypeCode.UPDATE_APPLY.getStatus(), o.getApplyType())) {
                updateList.add(dealData(o,newVO));
            }
        });
        if (addList.size() + updateList.size() != list.size()) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常！"));
        }
        //保存
        if (CollectionUtils.isNotEmpty(addList)) {
            ((ProductSkuConfigService)AopContext.currentProxy()).insertBatch(addList);
        }
        //更新
        if (CollectionUtils.isNotEmpty(updateList)) {
            ((ProductSkuConfigService)AopContext.currentProxy()).updateBatch(updateList);
        }
    }

    /**
     * 批量新增正式表数据
     *
     * @param configs
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public Integer insertBatch(List<ProductSkuConfig> configs) {
        return mapper.insertBatch(configs);
    }

    /**
     * 批量修改正式表数据
     *
     * @param configs
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateList
    public Integer updateBatch(List<ProductSkuConfig> configs) {
        return mapper.updateBatch(configs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateApplyInfoByVO(ApplyProductSkuConfigReqVo req) {
        return applyMapper.updateApplyInfo(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateInfoByVo(ApplyProductSkuConfigReqVo req) {
        return mapper.updateApplyStatusByApplyCode(req);
    }

    /**
     * 查询正式表信息
     *
     * @param reqVo
     * @return
     */
    @Override
    public BasePage<SkuConfigsRepsVo> findList(QuerySkuConfigReqVo reqVo) {
        AuthToken token = AuthenticationInterceptor.getCurrentAuthToken();
        if (null != token) {
            reqVo.setCompanyCode(token.getCompanyCode());
        }
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        List<SkuConfigsRepsVo> list = mapper.getList(reqVo);
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    /**
     * 获取正式表配置详情根据SKU编码
     *
     * @param skuCode
     * @return
     */
    @Override
    public SkuConfigDetailRepsVo detail(String skuCode) {
        SkuConfigDetailRepsVo repsVo = mapper.detail(skuCode);
        List<ProductSkuSupplyUnitRespVo> list = productSkuSupplyUnitService.selectBySkuCode(skuCode);
        repsVo.setSupplierList(list);
        return repsVo;
    }

    /**
     * 查询申请审批列表信息
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo) {
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        return applyMapper.queryApplyList(reqVo);
    }

    /**
     * 根据申请编码查询申请详情
     *
     * @param code
     * @return
     */
    @Override
    public ProductApplyInfoRespVO<SkuConfigsRepsVo> applyView(String code) {
        List<SkuConfigsRepsVo> list = applyMapper.selectByApplyCode(code);
        if(CollectionUtils.isEmpty(list)){
            log.error("传入的编码是：[{}]",code);
            throw new BizException(MessageId.create(Project.PRODUCT_API,98,"数据异常，无法查询到该数据"));
        }
        //组装数据
        return dealApplyViewData(list);
    }

    /**
     * 申请取消
     *
     * @param applyCode
     * @return
     */
    @Override
    public Integer cancelApply(String applyCode) {
        String formNo = applyMapper.findFormNoByCode(applyCode);
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormNo(formNo);
        // 调用审批流的撤销接口
        WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
        if(workFlowRespVO.getSuccess().equals(true)){
            return 0;
        }else {
            throw  new GroundRuntimeException("撤销失败");
        }
    }

    /**
     * 批量保存临时备用仓困信息
     *
     * @param spareWarehouseReqVos
     * @return
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSpareWarehouseDraftList(List<ProductSkuConfigSpareWarehouseDraft> spareWarehouseReqVos) {
        int num = 0;
        if(CollectionUtils.isNotEmpty(spareWarehouseReqVos)){
            num = spareWarehouseDraftMapper.insertBatch(spareWarehouseReqVos);
        }
        return num;
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSpareWarehouseList(List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses) {
        return spareWarehouseMapper.insertBatch(skuConfigSpareWarehouses);
    }


    /**
     * 功能描述: 获取临时表数据根据SkuCode
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/5 20:10
     */
    @Override
    public List<SkuConfigsRepsVo> draftDetail(String skuCode) {
        return draftMapper.getListBySkuCode(skuCode);
    }

    /**
     * 功能描述: 根据配置信息计算SKU状态和销售状态
     *
     * @param skuConfigsRepsVo
     * @return
     * @auther knight.xie
     * @date 2019/7/6 19:44
     */
    @Override
    public SkuStatusRespVo calculationSkuStatus(List<SkuConfigsRepsVo> skuConfigsRepsVo) {
        SkuStatusRespVo respVo = new SkuStatusRespVo();
        List<Byte> configStatus = skuConfigsRepsVo.stream().map(item->item.getConfigStatus()).distinct().collect(Collectors.toList());
        if (configStatus.contains(SkuStatusEnum.IN_USE.getStatus())){
            respVo.setSkuStatus(SkuStatusEnum.IN_USE.getStatus());
            respVo.setOnSale(SkuSaleStatusEnum.NOT_IN_STOCK.getStatus());
        } else if(configStatus.contains(SkuStatusEnum.STOP_PURCHASE.getStatus())) {
            respVo.setSkuStatus(SkuStatusEnum.STOP_PURCHASE.getStatus());
        } else if(configStatus.contains(SkuStatusEnum.STOP_DISTRIBUTION.getStatus())) {
            respVo.setSkuStatus(SkuStatusEnum.STOP_DISTRIBUTION.getStatus());
        } else {
            respVo.setSkuStatus(SkuStatusEnum.STOP_SALES.getStatus());
            respVo.setOnSale(SkuSaleStatusEnum.DIE_OUT.getStatus());
        }
        return null;
    }

    /**
     * 功能描述: 获取申请表数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:33
     */
    @Override
    public List<SkuConfigsRepsVo> getApply(String skuCode, String applyCode) {
        return applyMapper.selectBySkuAndApplyCode(skuCode,applyCode);
    }

    /**
     * 数据转化为审批流更新申请数据状态
     * @param newVO
     * @return
     */
    private ApplyProductSkuConfigReqVo dealData(WorkFlowCallbackVO newVO) {
        ApplyProductSkuConfigReqVo temp = new ApplyProductSkuConfigReqVo();
        temp.setAuditorStatus(newVO.getApplyStatus());
        temp.setAuditorBy(newVO.getApprovalUserName());
        temp.setAuditorTime(new Date());
        temp.setFormNo(newVO.getFormNo());
        return temp;
    }

    /**
     * 数据转换为正式表数据
     * @param applyData
     * @param newVO
     * @return
     */
    private ProductSkuConfig dealData(ApplyProductSkuConfig applyData, WorkFlowCallbackVO newVO) {
        ProductSkuConfig config = new ProductSkuConfig();
        BeanCopyUtils.copy(applyData,config);
        config.setId(null);
        config.setApplyStatus(newVO.getApplyStatus());
        config.setAuditorBy(newVO.getApprovalUserName());
        config.setAuditorTime(new Date());
        return config;
    }

    /**
     * 组装申请详情数据
     * @param list
     * @return
     */
    private ProductApplyInfoRespVO<SkuConfigsRepsVo> dealApplyViewData(List<SkuConfigsRepsVo> list) {
        ProductApplyInfoRespVO<SkuConfigsRepsVo> resp = new ProductApplyInfoRespVO<>();
        //数据相同默认取第一个
        SkuConfigsRepsVo applyVO = list.get(0);
        ApplyProductSkuConfig applyProductSkuConfig = applyMapper.selectByPrimaryKey(applyVO.getId());
        resp.setApplyBy(applyProductSkuConfig.getCreateBy());
        resp.setApplyTime(applyProductSkuConfig.getCreateTime());
        resp.setApplyStatus(applyProductSkuConfig.getAuditorStatus().intValue());
        resp.setAuditorBy(applyProductSkuConfig.getAuditorBy());
        resp.setAuditorTime(applyProductSkuConfig.getAuditorTime());
        resp.setSelectionEffectiveStartTime(applyProductSkuConfig.getSelectionEffectiveStartTime());
        resp.setSelectionEffectiveTime(applyProductSkuConfig.getSelectionEffectiveTime());
        resp.setCode(applyProductSkuConfig.getApplyCode());
        resp.setFormNo(applyProductSkuConfig.getFormNo());
        //统计sku数量
        List<String> skuCodes = list.stream().map(SkuConfigsRepsVo::getSkuCode).distinct().collect(Collectors.toList());
        resp.setSkuNum(skuCodes.size());
        //统计SPU数量吗
        List<String> spuCodes = list.stream().map(SkuConfigsRepsVo::getProductCode).distinct().collect(Collectors.toList());
        resp.setSpuNum(spuCodes.size());
        resp.setData(list);
        return resp;
    }
}
