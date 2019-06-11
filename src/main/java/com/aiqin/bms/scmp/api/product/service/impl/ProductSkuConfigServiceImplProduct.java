package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowVO;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.ApplyStatus;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.*;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.workflow.WorkFlowRespVO;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductSkuConfigService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:44
 */
@Service
@Slf4j
public class ProductSkuConfigServiceImplProduct extends ProductBaseServiceImpl implements ProductSkuConfigService {


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
     * @param configReqVos
     * @return
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public Integer updateDraftList(List<UpdateSkuConfigReqVo> configReqVos) {
        Integer num = 0;
        List<ProductSkuConfigDraft> drafts;
        List<SpareWarehouseReqVo> spareWarehouseReqVos = Lists.newArrayList();
        configReqVos.forEach(item->{
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
        });
        num = draftMapper.insertBatch(drafts);
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
    public List<SkuConfigsRepsVo> findDraftList(String companyCode) {
        return draftMapper.getList(companyCode);
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
        applyProductSkuConfigs.forEach(item->{
            item.setId(null);
            item.setApplyCode(code);
            item.setFormNo(formNo);
            item.setBeEffective(Global.UN_EFFECTIVE);
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
        applySpareWarehouseMapper.insertBatch(applyProductSkuConfigSpareWarehouses);
        //更新编码
        encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
        //删除临时表配置信息
        draftMapper.deleteByConfigCodes(reqVo.getSkuConfigs());
        //删除临时表备用仓库信息
        spareWarehouseDraftMapper.deleteByConfigCodes(reqVo.getSkuConfigs());
        //调用审批的接口
        workFlow(formNo,code,currentAuthToken.getPersonName());
        return num;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void workFlow(String formNo, String applyCode, String userName) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormUrl(workFlowBaseUrl.applySkuConfig + "?code=" + applyCode + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(formNo);
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_GOODS_CONFIG.getNum());
        workFlowVO.setTitle(userName + "创建商品配置审批");
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
    public String workFlowCallback(WorkFlowCallbackVO vo) throws Exception {
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
        if(!list.get(0).getAuditorStatus().equals(ApplyStatus.APPROVAL.getNumber().intValue())){
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常，不是在审批中的数据！"));
        }
        //审批驳回
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
            updateApplyInfoByVO(newVO);
            return WorkFlowReturn.SUCCESS;
        }
        //撤销
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
            updateApplyInfoByVO(newVO);
            return WorkFlowReturn.SUCCESS;
        }
        //审批通过
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            updateApplyInfoByVO(newVO);
            //通过formNo查询备用仓库
            List<ApplyProductSkuConfigSpareWarehouse> applySpareWarehouses = applySpareWarehouseMapper.
                    selectByFormNo(newVO.getFormNo());
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
            //保存商品配置正式数据
            saveOfficial(newVO, list);
            return WorkFlowReturn.SUCCESS;
        }
        return WorkFlowReturn.FALSE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApplyInfoByVO(WorkFlowCallbackVO newVO) {
        //处理数据
        ApplyProductSkuConfigReqVo req = dealData(newVO);
        //批量更新数据
        updateApplyInfoByVO(req);
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
//        QuerySkuConfigReqVo reqVo = new QuerySkuConfigReqVo();
//        reqVo.setSkuCode(skuCode);
//        List<SkuConfigsRepsVo> list = mapper.getList(reqVo);
//        repsVo.setConfigs(list);
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
        Set<String> skuCodes = Sets.newHashSet();
        //统计sku数量
        for (SkuConfigsRepsVo repsVo : list) {
            skuCodes.add(repsVo.getSkuCode());
        }
        resp.setSkuNum(skuCodes.size());
        resp.setData(list);
        return resp;
    }
}
