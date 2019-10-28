package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuConfigImport;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuSupplierImport;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ConfigSearchVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.*;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuCheckoutRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.SkuStatusRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.DetailConfigSupplierRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SpareWarehouseRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.service.ApprovalFileInfoService;
import com.aiqin.bms.scmp.api.supplier.service.LogisticsCenterService;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
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
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private LogisticsCenterService logisticsCenterService;
    @Autowired
    private ProductSkuConfigMapper productSkuConfigMapper;
    @Autowired
    private SupplyComService supplyCompanyService;
    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;
    @Autowired
    private ProductSkuSupplyUnitDraftMapper productSkuSupplyUnitDraftMapper;
    @Autowired
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Autowired
    private ProductSkuSupplyUnitCapacityMapper productSkuSupplyUnitCapacityMapper;
    @Autowired
    private ApplyProductSkuConfigMapper applyProductSkuConfigMapper;

    @Autowired
    private ProductSkuConfigDraftMapper productSkuConfigDraftMapper;

    @Autowired
    private ApprovalFileInfoService approvalFileInfoService;

    @Autowired
    private ProductSkuCheckoutService productSkuCheckoutService;



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
     * 批量保存导入的临时配置信息
     * @param configReqVos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importSaveDraft(List<SaveSkuConfigReqVo> configReqVos) {
        //校验是否有申请中的数据
        List<ProductSkuConfig> officials = productSkuConfigMapper.selectByVo(configReqVos);
        Map<String, ProductSkuConfig> configMap = officials.stream().collect(Collectors.toMap(o -> o.getSkuCode() + o.getTransportCenterCode(), Function.identity(), (k1, k2) -> k2));
        Map<String, ProductSkuConfig> configSkuMap = officials.stream().collect(Collectors.toMap(o -> o.getSkuCode() + o.getTransportCenterCode(), Function.identity(), (k1, k2) -> k2));
        //草稿表的中的数据
        List<ProductSkuConfigDraft> drafts = draftMapper.getListBySkuVo(configReqVos);
        Map<String, ProductSkuConfigDraft> draftMap = drafts.stream().collect(Collectors.toMap(o -> o.getSkuCode() + o.getTransportCenterCode(), Function.identity(), (k1, k2) -> k2));
        Integer num = 0;
        List<Long> ids = Lists.newArrayList();
        List<String> error = Lists.newArrayList();
        Date date = new Date();
        List<ProductSkuConfigDraft> draftList = Lists.newArrayList();
        List<SpareWarehouseReqVo> spareWarehouseReqVos = Lists.newArrayList();
        for (SaveSkuConfigReqVo item : configReqVos) {
            if (Objects.nonNull(draftMap.get(item.getSkuCode() + item.getTransportCenterCode()))) {
                //临时表需要删除的数据
                ids.add(draftMap.get(item.getSkuCode() + item.getTransportCenterCode()).getId());
            }
            if (Objects.nonNull(configMap.get(item.getSkuCode() + item.getTransportCenterCode()))) {
                //判断是否有审核中的数据
                if (StatusTypeCode.REVIEW_STATUS.getStatus().equals(configMap.get(item.getSkuCode() + item.getTransportCenterCode()).getApplyStatus())) {
                    error.add("sku编码为"+item.getSkuCode()+"下的仓库名称为"+item.getTransportCenterCode());
                    continue;
                }
            }
            ProductSkuConfigDraft draft = new ProductSkuConfigDraft();
            BeanCopyUtils.copy(item,draft);
            draft.setApplyShow(Global.APPLY_SKU_CONFIG_SHOW);
            draft.setApplyType(StatusTypeCode.UPDATE_APPLY.getStatus());
            String configCode;
            ProductSkuConfig productSkuConfig = configSkuMap.get(draft.getSkuCode() + draft.getTransportCenterCode());
            if(null != productSkuConfig){
                configCode = productSkuConfig.getConfigCode();
            } else {
                synchronized (ProductSkuConfigServiceImpl.class) {
                    configCode = getCode("", EncodingRuleType.SKU_CONFIG_CODE);
                }
                draft.setApplyType(StatusTypeCode.ADD_APPLY.getStatus());
            }
            draft.setConfigCode(configCode);
            draft.setCompanyCode(getUser().getCompanyCode());
            draft.setCompanyName(getUser().getCompanyName());
            draft.setCreateBy(getUser().getPersonName());
            draft.setCreateTime(date);
            draft.setUpdateBy(getUser().getPersonName());
            draft.setUpdateTime(date);
            draftList.add(draft);
            if(CollectionUtils.isNotEmpty(item.getSpareWarehouses())){
                for (SpareWarehouseReqVo spareWarehouse : item.getSpareWarehouses()) {
                    spareWarehouse.setConfigCode(draft.getConfigCode());
                }
                spareWarehouseReqVos.addAll(item.getSpareWarehouses());
            }
        }
        if (CollectionUtils.isNotEmpty(error)) {
            error.add("有在审批中的数据。请删除数据，重新提交,或者在审批申请页面将重复的数据删除。");
            throw new BizException(MessageId.create(Project.SCMP_API, 100, StringUtils.strip(error.toString(), "[]")));
        }else {
            //删除草稿变数据
            deleteByIds(ids);
            //批量保存主表数据
            saveDraftBatch(draftList);
            //批量附表保存
            List<ProductSkuConfigSpareWarehouseDraft> draftList2 = BeanCopyUtils.copyList(spareWarehouseReqVos,ProductSkuConfigSpareWarehouseDraft.class);
            ((ProductSkuConfigService)AopContext.currentProxy()).insertSpareWarehouseDraftList(draftList2);
        }
        return num;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDraftBatch(List<ProductSkuConfigDraft> draftList) {
        int i = draftMapper.insertBatch(draftList);
        if (i != draftList.size()) {
            log.error("保存正式表数据异常！");
            throw new BizException(ResultCode.DRAFT_CONFIG_SAVE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            int i = draftMapper.deleteByIds(ids);
            if (i != ids.size()) {
                log.error("删除临时表数据异常！");
                throw new BizException(ResultCode.DRAFT_CONFIG_SAVE_ERROR);
            }
        }
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
        //获取公司，通过token来获取公司
        AuthToken authToken = getUser();
        //商品配置更新
        List<UpdateSkuConfigReqVo> configReqVos = reqVo.getConfigs();
        if(CollectionUtils.isEmpty(configReqVos)){
            throw new BizException(ResultCode.UPDATE_ERROR);
        }
        //看加入进来的仓库没有重复的
        long nowCount=  reqVo.getConfigs().stream().map(x->x.getTransportCenterCode()).distinct().count();
        if(configReqVos.size() != nowCount){
            throw new BizException(ResultCode.REPEAT_DATA2);
        }
        //新增数据
        List<UpdateSkuConfigReqVo> addList = reqVo.getConfigs().stream().filter(item -> Objects.equals(item.getApplyType(), Byte.valueOf("1"))).collect(Collectors.toList());
        //先把原来的给查出来配置查出来
        List<SkuConfigsRepsVo> ordBeginList=productSkuConfigMapper.getListBySkuCode(reqVo.getSkuCode());
        //修改数据
        List<UpdateSkuConfigReqVo> updateList = reqVo.getConfigs().stream().filter(item -> Objects.equals(item.getApplyType(), Byte.valueOf("2"))).collect(Collectors.toList());
        //需要保存到临时表的数据
        List<UpdateSkuConfigReqVo>  saveList=Lists.newArrayList();
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isNotEmptyCollection(ordBeginList)){
            List<String> supplyCodes = ordBeginList.stream().map(SkuConfigsRepsVo::getTransportCenterCode).collect(Collectors.toList());
            Map<String, SkuConfigsRepsVo> supplyCodeMap = ordBeginList.stream().
                    collect(Collectors.toMap(SkuConfigsRepsVo::getTransportCenterCode, Function.identity(), (k1, k2) -> k2));
            //判断新增的信息是否已经存在
            if(com.aiqin.bms.scmp.api.util.CollectionUtils.isNotEmptyCollection(addList)){
                addList.forEach(item->{
                    if(supplyCodeMap.containsKey(item.getTransportCenterCode())){
                        throw new BizException(ResultCode.REPEAT_DATA2);
                    }
                });
                saveList.addAll(addList);
            }
            if (com.aiqin.bms.scmp.api.util.CollectionUtils.isNotEmptyCollection(updateList)) {
                //比较修改
                updateList.forEach(item -> {
                    //对修改的项目进不进行添加
                    if (diffData(item, reqVo.getSkuCode(),item.getTransportCenterCode())) {
                        saveList.add(item);
                    }
                });
            }
        } else {
            saveList.addAll(reqVo.getConfigs());
        }
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(saveList)){
            throw new BizException(ResultCode.SUMBIT_NOT_DATA);
        }
        Integer num = 0;
        //删除重复的数据,插入数据
        List<String> deleteTransportCenterCodes = saveList.stream().map(UpdateSkuConfigReqVo::getTransportCenterCode).collect(Collectors.toList());
        List<String> configCodes = productSkuConfigDraftMapper.selectBySkuCodeAndTransportCenterCodes(reqVo.getSkuCode(),deleteTransportCenterCodes);
        if(CollectionUtils.isNotEmpty(configCodes)){
            productSkuConfigDraftMapper.deleteByConfigCodes(configCodes);
            spareWarehouseDraftMapper.deleteByConfigCodes(configCodes);
        }
        //进行过滤后的赋值，进行初始化赋值
        List<ProductSkuConfigDraft> drafts = BeanCopyUtils.copyList(saveList,ProductSkuConfigDraft.class);
        List<SpareWarehouseReqVo> spareWarehouseReqVos = Lists.newArrayList();
        drafts.forEach(item->{
            item.setApplyShow(Global.APPLY_SKU_CONFIG_SHOW);
            if(Objects.equals(item.getApplyType(),StatusTypeCode.ADD_APPLY.getStatus())){
                item.setConfigCode(getCode(null,EncodingRuleType.SKU_CONFIG_CODE));
            }
            item.setCompanyCode(authToken.getCompanyCode());
            item.setCompanyName(authToken.getCompanyName());
            item.setProductCode(reqVo.getProductCode());
            item.setProductName(reqVo.getProductName());
            item.setSkuCode(reqVo.getSkuCode());
            item.setSkuName(reqVo.getSkuName());
            if (com.aiqin.bms.scmp.api.util.CollectionUtils.isNotEmptyCollection(item.getSpareWarehouses())) {
                item.getSpareWarehouses().forEach(item2 -> {
                    item2.setConfigCode(item.getConfigCode());
                });
                spareWarehouseReqVos.addAll(item.getSpareWarehouses());
            }

        });
        num =  ((ProductSkuConfigService)AopContext.currentProxy()).insertDraftBatch(drafts);
        if(CollectionUtils.isNotEmpty(spareWarehouseReqVos)){
            List<ProductSkuConfigSpareWarehouseDraft> draftList = BeanCopyUtils.copyList(spareWarehouseReqVos,ProductSkuConfigSpareWarehouseDraft.class);
            //插入临时表
            ((ProductSkuConfigService)AopContext.currentProxy()).insertSpareWarehouseDraftList(draftList);
        }
        return num;
    }

    private Boolean diffData(UpdateSkuConfigReqVo source,String skuCode,String transportCenterCode) {
        Boolean flag = false;
            //比较里面的详细是否发生了改变
            if(selectInfo(skuCode,transportCenterCode,source)){
                flag = true;
            }

        return flag;
    }
   //查看备用仓库是否发生了修改


    //判断仓库常参数是否出现了改动
    private boolean selectInfo(String skuCode, String transportCenterCode, UpdateSkuConfigReqVo source) {
        QuerySkuConfigReqVo querySkuConfigReqVo=new QuerySkuConfigReqVo();
        querySkuConfigReqVo.setSkuCode(skuCode);
        List<SkuConfigsRepsVo> skuConfigsRepsVoList = mapper.getList2(querySkuConfigReqVo);
        for (SkuConfigsRepsVo skuConfigsRepsVo:
                skuConfigsRepsVoList  ) {
            UpdateSkuConfigReqVo updateSkuConfigReqVo=new UpdateSkuConfigReqVo();
            BeanCopyUtils.copy(skuConfigsRepsVo,updateSkuConfigReqVo);
           if(updateSkuConfigReqVo.getTransportCenterCode().equals(source.getTransportCenterCode())){
               //如果不一样
            if (!source.compare(updateSkuConfigReqVo)){
                //对参数进行判断
                    return true;
            }
               //原来的备用仓库数量
               Integer ordCount=skuConfigsRepsVo.getSpareWarehouses().size();
               //现在提交的仓库数量
               Integer nowCount=source.getSpareWarehouses().size();
               if(ordCount!=nowCount){
                   return true;
               }
               //原来的备用仓库名称
               List<SpareWarehouseRepsVo> ordCodes = skuConfigsRepsVo.getSpareWarehouses();
               //按照使用顺序进行排序
               Collections.sort(ordCodes, (a, b) -> b.getUseOrder().compareTo(a.getUseOrder()));
               //新的备用仓库名称
               List<SpareWarehouseReqVo> newCodes = source.getSpareWarehouses();
               Collections.sort(newCodes, (a, b) -> b.getUseOrder().compareTo(a.getUseOrder()));
               if(ordCount ==nowCount ){
                   for (int num=0;num<ordCodes.size();num++){
                       if(!ordCodes.get(num).getTransportCenterCode().equals(newCodes.get(num).getTransportCenterCode())){
                           return true;
                       }
                   }

               }
           }

        }
        return false;
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
        for (ProductSkuConfigDraft productSkuConfigDraft: drafts
             ) {
            List<ProductSkuConfigSpareWarehouseDraft> productSkuConfigSpareWarehouseDrafts=Lists.newArrayList();
            List<SpareWarehouseReqVo> spareWarehouseReqVoList=  productSkuConfigDraft.getSpareWarehouses();
            for (int i=0;i<spareWarehouseReqVoList.size();i++){
                SpareWarehouseReqVo spareWarehouseReqVo= spareWarehouseReqVoList.get(i);
                ProductSkuConfigSpareWarehouseDraft productSkuConfigSpareWarehouseDraft=new ProductSkuConfigSpareWarehouseDraft();
                spareWarehouseReqVo.setConfigCode(productSkuConfigDraft.getConfigCode());
                BeanCopyUtils.copy(spareWarehouseReqVo,productSkuConfigSpareWarehouseDraft);
                productSkuConfigSpareWarehouseDrafts.add(productSkuConfigSpareWarehouseDraft);
            }
            spareWarehouseDraftMapper.insertBatch(productSkuConfigSpareWarehouseDrafts);
        }
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
//        List<SkuConfigsRepsVo> list = draftMapper.getList(companyCode);
//        respVo.setConfigs(list);
//        List<ProductSkuSupplyUnitRespVo> skuSupplyUnitRespVos = Lists.newArrayList();
//        if(CollectionUtils.isNotEmpty(list)){
//            List<String> skuCodes = list.stream().map(SkuConfigsRepsVo::getSkuCode).collect(Collectors.toList());
//            skuSupplyUnitRespVos = productSkuSupplyUnitService.getDraftList(skuCodes);
//        }
//        respVo.setSuppliers(skuSupplyUnitRespVos);
        return respVo;
    }
    /**
     * 查询临时表配置信息,排除掉不显示(从SKU新增来的数据)
     *
     * @return
     */
    @Override
    public BasePage<SkuConfigsRepsVo> findConfigsList(ConfigSearchVo vo) {
        vo.setCompanyCode(getUser().getCompanyCode());
        vo.setPersonId(getUser().getPersonId());
        if(CollectionUtils.isNotEmpty(vo.getProductCategoryCodes())){
            try {
                vo.setProductCategoryLv1Code(vo.getProductCategoryCodes().get(0));
                vo.setProductCategoryLv2Code(vo.getProductCategoryCodes().get(1));
                vo.setProductCategoryLv3Code(vo.getProductCategoryCodes().get(2));
                vo.setProductCategoryLv4Code(vo.getProductCategoryCodes().get(3));
            } catch (Exception e) {
                log.info("不做处理,让程序继续执行下去");
            }
        }
        List<Long> longs = draftMapper.getListCount(vo);
        if(CollectionUtils.isEmpty(longs)){
            return PageUtil.getPageList(vo.getPageNo(), Lists.newArrayList());
        }
        List<SkuConfigsRepsVo> list = draftMapper.getList(PageUtil.myPage(longs, vo));
        return PageUtil.getPageList(vo.getPageNo(),vo.getPageSize(),longs.size(),list);
    }

    /**
     * 查询临时表配置信息,排除掉不显示(从SKU新增来的数据)
     *
     * @return
     */
    @Override
    public BasePage<ProductSkuSupplyUnitRespVo> findSupplierList(ConfigSearchVo vo) {
        vo.setCompanyCode(getUser().getCompanyCode());
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<ProductSkuSupplyUnitRespVo> list= productSkuSupplyUnitService.getSupplyList(vo);
        return PageUtil.getPageList(vo.getPageNo(),list);
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
        Date currentDate = new Date();
        //获取登录人
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (null == currentAuthToken) {
            throw new BizException(ResultCode.LOGIN_ERROR);
        }
        if (CollectionUtils.isEmpty(reqVo.getSkuConfigs())) {
            throw new BizException(ResultCode.AT_LEAST_ONE_DATA);
        }
        String code;
        String formNo;
        synchronized (ProductSkuConfigServiceImpl.class) {
            //获取编码
            EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.APPLY_SKU_CONFIG_CODE);
            code = "CF"+numberingType.getNumberingValue().toString();
            formNo = "SC" + IdSequenceUtils.getInstance().nextId();
            //更新编码
            encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
        }
        //验证是否有配置信息
        if (CollectionUtils.isNotEmpty(reqVo.getSkuConfigs())) {
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
                applyProductSkuConfigs = BeanCopyUtils.copyList(configDrafts, ApplyProductSkuConfig.class);
                applyProductSkuConfigSpareWarehouses = BeanCopyUtils.copyList(spareWarehouseDrafts, ApplyProductSkuConfigSpareWarehouse.class);
            } catch (Exception e) {
                throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
            }
            applyProductSkuConfigs.forEach(item -> {
                item.setId(null);
                item.setApplyCode(code);
                item.setFormNo(formNo);
                item.setBeEffective(Global.UN_EFFECTIVE);
                item.setCreateTime(currentDate);
                item.setApprovalName(reqVo.getApprovalName());
                item.setApprovalRemark(reqVo.getApprovalRemark());
                item.setCreateBy(getUser().getPersonName());
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

            if (CollectionUtils.isNotEmpty(applyProductSkuConfigSpareWarehouses)) {
                //批量保存备用仓库
                applySpareWarehouseMapper.insertBatch(applyProductSkuConfigSpareWarehouses, code);
            }
            //删除临时表配置信息
            draftMapper.deleteByConfigCodes(reqVo.getSkuConfigs());
            //删除临时表备用仓库信息
            spareWarehouseDraftMapper.deleteByConfigCodes(reqVo.getSkuConfigs());
            //更新正式表申请编码
            mapper.updateApplyCodeByConfigCodes(code, reqVo.getSkuConfigs());
        }
        //进行图片上传
        approvalFileInfoService.batchSave(reqVo.getApprovalFileInfos(),code,formNo,ApprovalFileTypeEnum.GOODS_WARHOUSE.getType());
        //调用审批的接口
        workFlow(formNo, code, currentAuthToken.getPersonName(), reqVo.getDirectSupervisorCode(),reqVo.getApprovalName(),reqVo.getApprovalRemark());
        return 1;
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
            Date currentDate = new Date();
            applyProductSkuConfigs.stream().forEach(item->{
                item.setId(null);
                item.setApplyCode(code);
                item.setFormNo(formNo);
                item.setCreateTime(currentDate);
            });
            num = applyMapper.insertBatch(applyProductSkuConfigs);
            //删除临时表配置信息
            draftMapper.deleteOutByConfigCodes(configCodes);

            //通过编码查询出备用仓库信息
            List<ProductSkuConfigSpareWarehouseDraft> spareWarehouseDrafts = spareWarehouseDraftMapper.
                    getListByConfigCodes(configCodes);
            if(CollectionUtils.isNotEmpty(spareWarehouseDrafts)){
                List<ApplyProductSkuConfigSpareWarehouse>  applyProductSkuConfigSpareWarehouses =
                        BeanCopyUtils.copyList(spareWarehouseDrafts,ApplyProductSkuConfigSpareWarehouse.class);
                //批量保存备用仓库
                applySpareWarehouseMapper.insertBatch(applyProductSkuConfigSpareWarehouses,code);
                //删除临时表备用仓库信息
                spareWarehouseDraftMapper.deleteByConfigCodes(configCodes);
            }
        }

        return num;
    }


    //把数据传输给审批流
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void workFlow(String formNo, String applyCode, String userName,String directSupervisorCode,String approvalName,String approvalRemark) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormUrl(workFlowBaseUrl.applySkuConfig + "?approvalType=2&code=" + applyCode + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        //流程编号
        workFlowVO.setFormNo(formNo);
        //进行编码修改
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_GOODS_CONFIG.getNum());
        //进行审批名称的传输
        workFlowVO.setTitle(approvalName);
        //添加备注
        workFlowVO.setRemark(approvalRemark);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        //传输进行
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_GOODS_CONFIG);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            //TODO 这里暂时没有任何操作
        } else {
            throw new BizException(MessageId.create(Project.SCMP_API,999,workFlowRespVO.getMsg()));
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
        newVO.setAuditorTime(new Date());
        //审批中，直接返回成功
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }
        //首先通过formNO查找数据 配置数据
        List<ApplyProductSkuConfig> list = applyMapper.selectByFormNo(newVO.getFormNo());
        //查询供应商数据
        List<ApplyProductSkuSupplyUnit> unitList = productSkuSupplyUnitDao.selectByFormNo(newVO.getFormNo());
        if (CollectionUtils.isEmpty(list) && CollectionUtils.isEmpty(unitList)) {
            throw new BizException(ResultCode.DATA_ERROR);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            if (!list.get(0).getAuditorStatus().equals(ApplyStatus.APPROVAL.getNumber())) {
                throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常，不是在审批中的数据！"));
            }
            String applyCode = list.get(0).getApplyCode();
            //审批驳回
            if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
                updateApplyInfoByVO(newVO, applyCode);
            }
            //撤销
            if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
                updateApplyInfoByVO(newVO, applyCode);
            }
            //审批通过
            if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
                //判断是否预约时间
                boolean b = list.get(0).getSelectionEffectiveTime() == 0 ? true : false;
                //判断是否不立即生效，进行防空判断
                boolean b1 = b && list.get(0).getSelectionEffectiveStartTime().after(new Date());
                try {
                    updateApplyInfoByVO(newVO, applyCode);
                    if (!b1) {
                        //通过applyCode查询备用仓库
                        List<ApplyProductSkuConfigSpareWarehouse> applySpareWarehouses = applySpareWarehouseMapper.
                                selectByApplyCode(applyCode);
                        //获取配置编号
                        List<String> configCodes = applySpareWarehouses.stream().map(item -> item.getConfigCode()).distinct().
                                collect(Collectors.toList());
                        //保存正式备用仓库信息
                        //删除正式
                        if (CollectionUtils.isNotEmpty(configCodes)) {
                            spareWarehouseMapper.deleteByConfigCodes(configCodes);
                        }
                        //批量插入
                        List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses = BeanCopyUtils.copyList(applySpareWarehouses,
                                ProductSkuConfigSpareWarehouse.class);
                        ((ProductSkuConfigService) AopContext.currentProxy()).insertSpareWarehouseList(skuConfigSpareWarehouses);
                        //保存商品配置正式数据
                        saveOfficial(newVO, list);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return WorkFlowReturn.FALSE;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(unitList)) {
            if (!unitList.get(0).getAuditorStatus().equals(ApplyStatus.APPROVAL.getNumber())) {
                throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常，不是在审批中的数据！"));
            }
            String applyCode = unitList.get(0).getApplyCode();
            //审批驳回
            if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
                updateApplyInfoByVO2(newVO, applyCode);
            }
            //撤销
            if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
                updateApplyInfoByVO2(newVO, applyCode);
            }
            //审批通过
            if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
                //判断是否预约时间
                boolean b = unitList.get(0).getSelectionEffectiveTime() == 0 ? true : false;
                //判断是否不立即生效
                boolean b1 = b && unitList.get(0).getSelectionEffectiveStartTime().after(new Date());
                try {
                    updateApplyInfoByVO2(newVO, applyCode);
                    if (!b1) {
                        //供应商信息
                        productSkuSupplyUnitService.saveListForChange(unitList);
                        //供应商产能信息
                        productSkuSupplyUnitCapacityService.saveListForChange(unitList);
                        //更新状态
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return WorkFlowReturn.FALSE;
                }
            }
        }
        return WorkFlowReturn.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tobeEffective(List<ApplyProductSkuConfig> list) {
        for (ApplyProductSkuConfig config : list) {
            //判断是否预约时间
            boolean b = config.getSelectionEffectiveTime() == 0 ? true : false;
            //判断是否不立即生效
            boolean b1 = b & config.getSelectionEffectiveStartTime().after(new Date());
            if (!b1) {
                String applyCode = config.getApplyCode();
                String skuCode = config.getSkuCode();
                WorkFlowCallbackVO newVO = new WorkFlowCallbackVO();
                newVO.setApprovalUserName(list.get(0).getAuditorBy());
                newVO.setApplyStatus((byte) 2);
                //通过applyCode查询备用仓库
                List<ApplyProductSkuConfigSpareWarehouse> applySpareWarehouses = applySpareWarehouseMapper.
                        selectByApplyCode(applyCode);
                //获取配置编号
                List<String> configCodes = applySpareWarehouses.stream().map(item -> item.getConfigCode()).distinct().
                        collect(Collectors.toList());
                //保存正式备用仓库信息
                //删除正式
                if (CollectionUtils.isNotEmpty(configCodes)) {
                    spareWarehouseMapper.deleteByConfigCodes(configCodes);
                }
                //批量插入
                List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses = BeanCopyUtils.copyList(applySpareWarehouses,
                        ProductSkuConfigSpareWarehouse.class);
                ((ProductSkuConfigService) AopContext.currentProxy()).insertSpareWarehouseList(skuConfigSpareWarehouses);
                //保存商品配置正式数据
                saveOfficial(newVO, list);
            }
        }
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
    public void updateApplyInfoByVO2(WorkFlowCallbackVO newVO, String applyCode) {
        //处理数据
        ApplyProductSkuConfigReqVo req = dealData(newVO);
        req.setApplyCode(applyCode);
        //批量更新数据
        updateApplyInfoByVO2(req);
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
        Map<String, List<ApplyProductSkuConfig>> map = list.stream().collect(Collectors.groupingBy(ApplyProductSkuConfig::getSkuCode));
        List<SkuStatusRespVo> respVos = Lists.newLinkedList();
        map.forEach((k,v)->{
            List<SkuConfigsRepsVo> skuConfigsRepsVo = BeanCopyUtils.copyList(v,SkuConfigsRepsVo.class);
            SkuStatusRespVo respVo = calculationSkuStatus(skuConfigsRepsVo);
            respVo.setSkuCode(k);
            respVos.add(respVo);
        });
        if(CollectionUtils.isNotEmpty(respVos)){
            skuInfoService.updateStatus(respVos);
        }
        //设置状态为同步完成
        if (CollectionUtils.isNotEmpty(list)) {
            applyMapper.updateBySynStatus(list);
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
    public Integer updateApplyInfoByVO2(ApplyProductSkuConfigReqVo req) {
        return productSkuSupplyUnitDao.updateApplyInfo(req);
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
            reqVo.setPersonId(token.getPersonId());
        }
        PageHelper.startPage(reqVo.getPageNo(), reqVo.getPageSize());
//        List<Long> longs = mapper.selectSkuListForSaleAreaCount(reqVo);
//        if(CollectionUtils.isEmpty(longs)){
//            return PageUtil.getPageList(reqVo.getPageNo(), Lists.newArrayList());
//        }
//        List<SkuConfigsRepsVo> list = mapper.getList(PageUtil.myPage(longs, reqVo));
        List<SkuConfigsRepsVo> list3 = mapper.getList3(reqVo);
        List<String> collect = list3.stream().map(SkuConfigsRepsVo::getConfigCode).distinct().collect(Collectors.toList());
        //查询备用仓
        Map<String,ProductSkuConfigSpareWarehouse> spareWarehouseMap =  spareWarehouseMapper.selectByConfigCode(collect);
        for (SkuConfigsRepsVo spareWarehouse : list3) {
            ProductSkuConfigSpareWarehouse productSkuConfigSpareWarehouse = spareWarehouseMap.get(spareWarehouse.getConfigCode());
            if (Objects.nonNull(productSkuConfigSpareWarehouse)) {
                //备用仓库设置
                spareWarehouse.setSpareWarehouse2(productSkuConfigSpareWarehouse.getTransportCenterName());
            }
}
        return PageUtil.getPageList(reqVo.getPageNo(),list3);
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
        //进行供应商列表增加
//        List<ProductSkuSupplyUnitRespVo> list = productSkuSupplyUnitService.selectBySkuCode(skuCode);
//        repsVo.setSupplierList(list);
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
    public ProductApplyInfoRespVO<SkuConfigDetailRepsVo> applyView(String code) {
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
            throw  new BizException(ResultCode.CANCEL_ERROR);
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
        if(CollectionUtils.isEmpty(skuConfigSpareWarehouses)){
            return 0;
        }
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
        return respVo;
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
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:39
     */
    @Override
    public List<SkuConfigsRepsVo> getList(String skuCode) {
        return mapper.getListBySkuCode(skuCode);
    }

    /**
     * 功能描述: 保存到正式表
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:00
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveList(WorkFlowCallbackVO workFlowCallbackVO, String skuCode, String applyCode) {
        List<ApplyProductSkuConfig> applyProductSkuConfigs = applyMapper.selectBySkuCodeAndApplyCode(skuCode, applyCode);

        //通过applyCode查询备用仓库
        List<ApplyProductSkuConfigSpareWarehouse> applySpareWarehouses = applySpareWarehouseMapper.
                selectByApplyCode(applyCode);
        //获取配置编号
        List<String> configCodes = applySpareWarehouses.stream().map(item -> item.getConfigCode()).distinct().
                collect(Collectors.toList());
        //保存正式备用仓库信息
        //删除正式
        if(CollectionUtils.isNotEmpty(configCodes)) {
            spareWarehouseMapper.deleteByConfigCodes(configCodes);
        }
        //批量插入
        List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses = BeanCopyUtils.copyList(applySpareWarehouses,
                ProductSkuConfigSpareWarehouse.class);
        ((ProductSkuConfigService)AopContext.currentProxy()).insertSpareWarehouseList(skuConfigSpareWarehouses);
        saveOfficial(workFlowCallbackVO,applyProductSkuConfigs);
        return null;
    }

    @Override
    public List<SaveSkuConfigReqVo> importData(MultipartFile file,String purchaseGroupCode) {
        try {
            List<SkuConfigImport> skuConfigImport = ExcelUtil.readExcel(file, SkuConfigImport.class, 1, 0);
            List<SaveSkuConfigReqVo> list = Lists.newArrayList();
            //校验
            dataValidation(skuConfigImport);
            skuConfigImport.remove(0);
            Set<String> skuList = Sets.newHashSet();
            Set<String> warehouseList = Sets.newHashSet();
            skuConfigImport.forEach(o->{
                skuList.add(o.getSkuCode());
                warehouseList.add(o.getTransportCenterName());
                if (StringUtils.isNotBlank(o.getSpareWarehouses())) {
                    warehouseList.addAll(Arrays.asList((o.getSpareWarehouses()).split(",")));
                }
            });
            Map<String,ProductSkuInfo> productSkuList = Maps.newHashMap();
            Map<String,LogisticsCenterDTO> centerList = Maps.newHashMap();
            //sku信息
            if (CollectionUtils.isNotEmpty(skuList)) {
                productSkuList = skuInfoService.selectBySkuCodes(skuList,getUser().getCompanyCode());
            }
            //仓库信息
            if (CollectionUtils.isNotEmpty(warehouseList)) {
                centerList = logisticsCenterService.selectByNameList(warehouseList, getUser().getCompanyCode());
            }
            //验证sku编码名称
            //k:仓+skuCode v:skuCode
            Map<String,String> skuCodeMap = Maps.newHashMap();
            for (int i = 0; i < skuConfigImport.size(); i++) {
                SaveSkuConfigReqVo reqVo = validData(productSkuList,centerList,skuCodeMap,skuConfigImport.get(i),purchaseGroupCode);
                String error = reqVo.getError();
                if (StringUtils.isNotBlank(error)) {
                    error = "第"+(i+1)+"行 "+error;
                    reqVo.setError(error);
                }
                list.add(reqVo);
            }
            return list;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }

    @Override
    public List<ProductSkuSupplyUnitDraft> importSupplyData(MultipartFile file,String purchaseGroupCode) {
        try {
            List<SkuSupplierImport> skuSupplierImport = ExcelUtil.readExcel(file, SkuSupplierImport.class, 1, 0);
            List<ProductSkuSupplyUnitDraft> list = Lists.newArrayList();
            //校验
            dataValidation2(skuSupplierImport);
            skuSupplierImport.remove(0);
            Set<String> skuList = Sets.newHashSet();
            Set<String> supplierList = Sets.newHashSet();
            skuSupplierImport.forEach(o->{
                skuList.add(o.getProductSkuCode());
                supplierList.add(o.getSupplyUnitCode());
            });
            Map<String,ProductSkuInfo> productSkuMap = Maps.newHashMap();
            Map<String, ProductSkuCheckoutRespVo> productSkuCheckoutMap = Maps.newHashMap();
            Map<String, SupplyCompany> supplyCompanyMap = Maps.newHashMap();
            //sku信息
            if (CollectionUtils.isNotEmpty(skuList)) {
                productSkuMap = skuInfoService.selectBySkuCodes(skuList,getUser().getCompanyCode());
                productSkuCheckoutMap = productSkuCheckoutService.selectBySkuCodes(skuList);
            }
            //供应商信息
            if (CollectionUtils.isNotEmpty(supplierList)) {
                supplyCompanyMap = supplyCompanyService.selectBySupplyComCodes(supplierList, getUser().getCompanyCode());
            }
            List<String> name = Lists.newArrayList();
            name.add("供货渠道类别");
            Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(name, getUser().getCompanyCode());
            //验证sku编码名称
            //k:供应商+skuCode v:skuCode
            Map<String,String> skuCodeMap = Maps.newHashMap();
            for (int i = 0; i < skuSupplierImport.size(); i++) {
                ProductSkuSupplyUnitDraft reqVo = validData2(productSkuMap,supplyCompanyMap,skuCodeMap,dicMap,productSkuCheckoutMap,skuSupplierImport.get(i),purchaseGroupCode);
                String error = reqVo.getError();
                if (StringUtils.isNotBlank(error)) {
                    error = "第"+(i+1)+"行 "+error;
                    reqVo.setError(error);
                }
                list.add(reqVo);
            }
            return list;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }

    @Override
    public DetailRequestRespVo getInfoByForm(String formNo) {
        DetailRequestRespVo respVo = new DetailRequestRespVo();
        List<ApplyProductSkuConfig> list = applyMapper.selectByFormNo(formNo);
        List<ApplyProductSkuSupplyUnit> unitList = productSkuSupplyUnitDao.selectByFormNo(formNo);
        if(CollectionUtils.isEmpty(list)&&CollectionUtils.isEmpty(unitList)){
            throw new BizException(ResultCode.OBJECT_EMPTY_BY_FORMNO);
        }
        String applyCode = null;
        if (CollectionUtils.isNotEmpty(list)) {
            applyCode = list.get(0).getApplyCode();
        }
        if(applyCode == null){
            applyCode = unitList.get(0).getApplyCode();
        }
        respVo.setApplyCode(applyCode);
        respVo.setItemCode("2");
        return respVo;
    }

    @Override
    public List<ApplyProductSkuConfig> selectUnSynData() {
        return applyMapper.selectUnSynData();
    }

    @Override
    public SkuConfigDetailRepsVo detailForDraft(String skuCode, Long draftId) {
        SkuConfigDetailRepsVo SkuConfigDetailRepsVo=productSkuConfigDraftMapper.detailForDraft(skuCode,draftId);
        return SkuConfigDetailRepsVo;
    }

    //进行修改
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(SkuConfigDetailRepsVo skuConfigDetailRepsVo) {
        List<SkuConfigsRepsVo> skuConfigsRepsVoList=skuConfigDetailRepsVo.getConfigs();
        int num =0;
        for (SkuConfigsRepsVo skuConfigsRepsVo : skuConfigsRepsVoList) {
            ProductSkuConfigDraft productSkuConfigDraft=new ProductSkuConfigDraft();
            BeanCopyUtils.copy(skuConfigsRepsVo,productSkuConfigDraft);
            productSkuConfigDraftMapper.updateByPrimaryKeySelective(productSkuConfigDraft);
            //先把改配置下面的所有的备用仓库给删了
            spareWarehouseDraftMapper.deleteByConfigCode(skuConfigsRepsVo.getConfigCode());
            List<SpareWarehouseRepsVo> spareWarehouseRepsVoList=skuConfigsRepsVo.getSpareWarehouses();
            for(SpareWarehouseRepsVo SpareWarehouseRepsVo :spareWarehouseRepsVoList){
                SpareWarehouseRepsVo.setConfigCode(skuConfigsRepsVo.getConfigCode());

            }
            List<ProductSkuConfigSpareWarehouseDraft> productSkuConfigSpareWarehouseDraftList =  BeanCopyUtils.copyList(spareWarehouseRepsVoList,ProductSkuConfigSpareWarehouseDraft.class);
            spareWarehouseDraftMapper.insertBatch(productSkuConfigSpareWarehouseDraftList);
            num++;
        }
        System.out.println("进来咯~");        return num;
    }

    @Override
    public Boolean saveImportSupply(List<ProductSkuSupplyUnitDraft> reqVo) {
        //查申请表数据
        List<ProductSkuSupplyUnitRespVo> list2 = productSkuSupplyUnitService.selectApplyBySkuCodes(reqVo.stream().map(ProductSkuSupplyUnitDraft::getProductSkuCode).distinct().collect(Collectors.toList()));
        Map<String, ProductSkuSupplyUnitRespVo> collect = list2.stream().collect(Collectors.toMap(o -> o.getProductSkuCode() + o.getSupplyUnitCode(), Function.identity(), (k1, k2) -> k2));
        List<String> error = Lists.newArrayList();
        //校验临时表是否含有该数据
        List<ProductSkuSupplyUnitDraft> list = productSkuSupplyUnitDraftMapper.selectByVo(reqVo);
        //k:skuCode+供应商编码
        Map<String, ProductSkuSupplyUnitDraft> draftMap = list.stream().collect(Collectors.toMap(o -> o.getProductSkuCode() + o.getSupplyUnitCode(), Function.identity(), (k1, k2) -> k2));
        List<Long> ids = Lists.newArrayList();
        List<ProductSkuSupplyUnitCapacityDraft> capacityDrafts = Lists.newArrayList();
        for (ProductSkuSupplyUnitDraft draft : reqVo) {
            draft.setApplyShow((byte) 0);
            draft.setCompanyCode(getUser().getCompanyCode());
            draft.setCompanyName(getUser().getCompanyName());
            if (collect.containsKey(draft.getProductSkuCode() + draft.getSupplyUnitCode())) {
                error.add("sku编码为"+draft.getProductSkuCode()+"下的供应商编码为"+draft.getSupplyUnitCode()+"已存在审批中的数据");
            }
            ProductSkuSupplyUnitDraft supplyUnitDraft = draftMap.get(draft.getProductSkuCode() + draft.getSupplyUnitCode());
            if (Objects.nonNull(supplyUnitDraft)) {
                ids.add(supplyUnitDraft.getId());
                ProductSkuSupplyUnitCapacityDraft capacityDraft = new ProductSkuSupplyUnitCapacityDraft();
                capacityDraft.setProductSkuCode(supplyUnitDraft.getProductSkuCode());
                capacityDraft.setSupplyUnitCode(supplyUnitDraft.getSupplyUnitCode());
                capacityDrafts.add(capacityDraft);
            }
        }
        if (CollectionUtils.isNotEmpty(error)) {
            throw new BizException(MessageId.create(Project.SCMP_API, 100, StringUtils.strip(error.toString(), "[]")));
        }
        if (CollectionUtils.isNotEmpty(ids)) {
            //删除数据
            int i = productSkuSupplyUnitDraftMapper.deleteDraftByIds(ids);
            if (i != ids.size()) {
                throw new BizException(ResultCode.IMPORT_DATA_SAVE_FAILED);
            }
            int j = productSkuSupplyUnitCapacityService.deleteDraftsByVos(capacityDrafts);
        }
        //保存
        int i = productSkuSupplyUnitService.insertDraftList(reqVo);
        if (i != reqVo.size()) {
            throw new BizException(ResultCode.IMPORT_DATA_SAVE_FAILED);
        }
        return Boolean.TRUE;
    }

    private ProductSkuSupplyUnitDraft validData2(Map<String, ProductSkuInfo> productSkuMap, Map<String, SupplyCompany> supplyCompanyMap, Map<String, String> skuCodeMap, Map<String, SupplierDictionaryInfo> dicMap,
                                                 Map<String,ProductSkuCheckoutRespVo> productSkuCheckoutMap, SkuSupplierImport skuSupplierImport,String purchaseGroupCode) {
        List<String> errorList = Lists.newArrayList();
        ProductSkuSupplyUnitDraft copy = BeanCopyUtils.copy(skuSupplierImport, ProductSkuSupplyUnitDraft.class);
        String s = skuCodeMap.get(skuSupplierImport.getSupplyUnitCode()+skuSupplierImport.getProductSkuCode());
        //重复校验
        if(StringUtils.isNotBlank(s)){
            errorList.add("该条数据与之前的数据重复");
            copy.setError(StringUtils.strip(errorList.toString(),"[]"));
            return copy;
        }
        //项目校验
        if(Objects.isNull(skuSupplierImport.getApplyType())){
            errorList.add("项目不能不能为空");
        }else{
            if (Objects.isNull(StatusTypeCode.getAll().get(skuSupplierImport.getApplyType()))) {
                errorList.add("项目不正确，请填写新增或者修改");
            }else {
                copy.setApplyType(StatusTypeCode.getAll().get(skuSupplierImport.getApplyType()).getStatus());
            }
        }
        //sku校验
        if (Objects.isNull(skuSupplierImport.getProductSkuCode())) {
            errorList.add("sku编码不能为空");
        } else {
            ProductSkuInfo productSkuInfo = productSkuMap.get(skuSupplierImport.getProductSkuCode().trim());
            if (Objects.isNull(productSkuInfo)) {
                errorList.add("该sku编码在库中不存在");
            } else {
                if (Objects.isNull(skuSupplierImport.getProductSkuName())) {
                    errorList.add("sku名称不能为空");
                } else {
                    if (!productSkuInfo.getSkuName().equals(skuSupplierImport.getProductSkuName())) {
                        errorList.add("sku名称与对应的sku编码不一致");
                    }else {
                        if(!purchaseGroupCode.equals(productSkuInfo.getProcurementSectionCode())){
                            errorList.add("该sku所属的采购组不是所选择的采购组");
                        }
                    }
                }
            }
        }
        //供应商校验
        if (Objects.isNull(skuSupplierImport.getSupplyUnitCode())) {
            errorList.add("供应商编码不能为空");
        } else {
            SupplyCompany supplyCompany = supplyCompanyMap.get(skuSupplierImport.getSupplyUnitCode().trim());
            if (Objects.isNull(supplyCompany)) {
                errorList.add("该供应商编码在库中不存在");
            } else {
                if (Objects.isNull(skuSupplierImport.getProductSkuName())) {
                    errorList.add("供应商名称不能为空");
                } else {
                    if (!supplyCompany.getSupplyName().equals(skuSupplierImport.getSupplyUnitName())) {
                        errorList.add("供应商名称与对应的供应商编码不一致");
                    }else {
                        skuCodeMap.put(skuSupplierImport.getSupplyUnitCode() + skuSupplierImport.getProductSkuCode(), skuSupplierImport.getProductSkuCode());
                    }
                }
            }
        }
        //含税金额
        if (Objects.isNull(skuSupplierImport.getTaxIncludedPrice())) {
            errorList.add("含税金额不能为空");
        }else {
            try {
                Long  i = NumberConvertUtils.stringParseLong(skuSupplierImport.getTaxIncludedPrice());
                ProductSkuCheckoutRespVo productSkuCheckout = productSkuCheckoutMap.get(skuSupplierImport.getProductSkuCode().trim());
                if(null == productSkuCheckout){
                    errorList.add("该sku编码在库中找不到对应的进项税率");
                }else{
                    copy.setTaxIncludedPrice(i);
                    copy.setNoTaxPurchasePrice(Calculate.computeNoTaxPrice(i,productSkuCheckout.getInputTaxRate()));
                    copy.setTaxRate(productSkuCheckout.getInputTaxRate());
                }
            } catch (Exception e) {
                errorList.add("含税金额格式不正确");
            }
        }
        //联营扣点(%)
        if (Objects.isNull(skuSupplierImport.getJointFranchiseRate())) {
            errorList.add("联营扣点(%)不能为空");
        }else {
            try {
              Long  i = NumberConvertUtils.stringParseLong(skuSupplierImport.getJointFranchiseRate());
              if (i < 0 || i > 10000) {
                  errorList.add("联营扣点(%)应在0-100之间");
              }else {
                  copy.setJointFranchiseRate(i);
              }
            } catch (Exception e) {
                errorList.add("联营扣点(%)格式不正确");
            }
        }
        //返点(%)
        if (Objects.isNull(skuSupplierImport.getPoint())) {
            errorList.add("返点(%)不能为空");
        }else {
            try {
                Long  i = NumberConvertUtils.stringParseLong(skuSupplierImport.getPoint());
                if (i < 0 || i > 10000) {
                    errorList.add("联营扣点(%)应在0-100之间");
                }else {
                    copy.setPoint(i);
                }
            } catch (Exception e) {
                errorList.add("返点(%)格式不正确");
            }
        }
        //厂商SKU编号
        if (Objects.isNull(skuSupplierImport.getFactorySkuCode())) {
            errorList.add("厂商SKU编号不能为空");
        }
        //供货渠道类别
        if (Objects.isNull(skuSupplierImport.getCategoriesSupplyChannelsName())) {
            errorList.add("供货渠道类别不能为空");
        }else {
            SupplierDictionaryInfo info = dicMap.get(skuSupplierImport.getCategoriesSupplyChannelsName());
            if (Objects.isNull(info)) {
                errorList.add("供货渠道类别填写不正确，请填写直送或配送或者全部");
            }else {
                copy.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
            }
        }
        //默认值
        if (Objects.isNull(skuSupplierImport.getIsDefault())) {
            errorList.add("默认值不能为空");
        }else {
            if (Objects.isNull(DefaultOrNot.getAll().get(skuSupplierImport.getIsDefault()))) {
                errorList.add("默认值不正确，请填写是或者否");
            }else {
                copy.setIsDefault(DefaultOrNot.getAll().get(skuSupplierImport.getIsDefault()).getValue());
            }
        }
        //状态
        if (Objects.isNull(skuSupplierImport.getUsageStatusName())) {
            errorList.add("状态不能为空");
        }else {
            if (Objects.isNull(UsageStatusEnum.getAll().get(skuSupplierImport.getUsageStatusName()))) {
                errorList.add("状态不正确，请填写在用或者禁用");
            }else {
                copy.setUsageStatus(UsageStatusEnum.getAll().get(skuSupplierImport.getUsageStatusName()).getType());
            }
        }
        if (CollectionUtils.isNotEmpty(errorList)) {
            copy.setError(StringUtils.strip(errorList.toString(),"[]"));
        }else{

        }
        return copy;
    }

    private void dataValidation2(List<SkuSupplierImport> skuSupplierImport) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuSupplierImport)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuSupplierImport.size()<2) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = SkuSupplierImport.HEAD;
        boolean equals = skuSupplierImport.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

    /**
     * 补充数据
     * @author NullPointException
     * @date 2019/7/18
     * @param productSkuList sku集合
     * @param centerList 仓库
     * @param skuCodeMap 验重集合
     * @param configImport 需要校验的实体
     * @return com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo
     */
    private SaveSkuConfigReqVo validData(Map<String,ProductSkuInfo> productSkuList, Map<String,LogisticsCenterDTO> centerList, Map<String, String> skuCodeMap,SkuConfigImport configImport,String purchaseGroupCode) {
        List<String> errorList = Lists.newArrayList();
        SaveSkuConfigReqVo copy = BeanCopyUtils.copy(configImport, SaveSkuConfigReqVo.class);
        String s = skuCodeMap.get(configImport.getTransportCenterName()+configImport.getSkuCode());
        //重复校验
        if(StringUtils.isNotBlank(s)){
            errorList.add("该条数据与之前的数据重复");
            copy.setError(StringUtils.strip(errorList.toString(),"[]"));
            return copy;
        }
        //
        //sku校验
        if (Objects.isNull(configImport.getSkuCode())) {
            errorList.add("sku编码不能为空");
        } else {
            ProductSkuInfo productSkuInfo = productSkuList.get(configImport.getSkuCode().trim());
            if (Objects.isNull(productSkuInfo)) {
                errorList.add("该sku编码在库中不存在");
            } else {
                if (Objects.isNull(configImport.getSkuName())) {
                    errorList.add("sku名称不能为空");
                } else {
                    if (!productSkuInfo.getSkuName().equals(configImport.getSkuName())) {
                        errorList.add("sku名称与对应的sku编码不一致");
                    }else {
                        if(!purchaseGroupCode.equals(productSkuInfo.getProcurementSectionCode())){
                            errorList.add("该sku所属的采购组不是所选择的采购组");
                        }else {
                            copy.setProductCode(productSkuInfo.getProductCode());
                            copy.setProductName(productSkuInfo.getProductName());
                        }
                    }
                }
            }
        }
        //校验仓库
        if(Objects.isNull(configImport.getTransportCenterName())){
            errorList.add("仓库不能为空");
        }else {
            LogisticsCenterDTO logisticsCenter = centerList.get(configImport.getTransportCenterName().trim());
            if (Objects.isNull(logisticsCenter)) {
                errorList.add("未找到该名称对应的仓库或仓库被禁用");
            }else {
                //成功后存值
                skuCodeMap.put(configImport.getTransportCenterName()+configImport.getSkuCode(),configImport.getSkuCode());
                copy.setTransportCenterCode(logisticsCenter.getLogisticsCenterCode());
            }
        }
        //状态
        if (Objects.isNull(configImport.getConfigStatusName())) {
            errorList.add("状态不能为空");
        }else {
            SkuStatusEnum skuStatusEnum = SkuStatusEnum.getAllStatus().get(configImport.getConfigStatusName().trim());
            if (Objects.isNull(skuStatusEnum)) {
                errorList.add("状态值有误");
            }else {
                copy.setConfigStatus(skuStatusEnum.getStatus());
            }
        }
        //订货周期
        if (Objects.isNull(configImport.getOrderCycle())) {
            errorList.add("订货周期不能为空");
        }else {
            try {
                Integer integer = Integer.valueOf((configImport.getOrderCycle().trim()));
                copy.setOrderCycle(integer);
            }catch (NumberFormatException e){
                errorList.add("订货周期应为整数");
            }
        }
         //到货周期
        if (Objects.isNull(configImport.getArrivalCycle())) {
            errorList.add("到货周期不能为空");
        }else {
            try {
                Integer integer = Integer.valueOf((configImport.getArrivalCycle().trim()));
                copy.setArrivalCycle(integer);
            }catch (NumberFormatException e){
                errorList.add("订货周期应为整数");
            }
        }
        //到货后周转期
        if (Objects.isNull(configImport.getTurnoverPeriodAfterArrival())) {
            errorList.add("到货后周转期不能为空");
        }else {
            try {
                Integer integer = Integer.valueOf((configImport.getTurnoverPeriodAfterArrival().trim()));
                copy.setTurnoverPeriodAfterArrival(integer);
            }catch (NumberFormatException e){
                errorList.add("到货后周转期应为整数");
            }
        }
        //基本库存天数
        if (Objects.isNull(configImport.getBasicInventoryDay())) {
            errorList.add("基本库存天数不能为空");
        }else {
            try {
                Integer integer = Integer.valueOf((configImport.getBasicInventoryDay()).trim());
                copy.setBasicInventoryDay(integer);
            }catch (NumberFormatException e){
                errorList.add("基本库存天数应为整数");
            }
        }
        //大库存预警天数
        if (Objects.isNull(configImport.getLargeInventoryWarnDay())) {
            errorList.add("大库存预警天数不能为空");
        }else {
            try {
                Integer integer = Integer.valueOf((configImport.getLargeInventoryWarnDay().trim()));
                copy.setLargeInventoryWarnDay(integer);
            }catch (NumberFormatException e){
                errorList.add("大库存预警天数应为整数");
            }
        }
        //大效期预警天数
        if (Objects.isNull(configImport.getBigEffectPeriodWarnDay())) {
            errorList.add("大效期预警天数不能为空");
        }else {
            try {
                Integer integer = Integer.valueOf((configImport.getBigEffectPeriodWarnDay().trim()));
                copy.setBigEffectPeriodWarnDay(integer);
            }catch (NumberFormatException e){
                errorList.add("大效期预警天数应为整数");
            }
        }
        //备用仓
        if (StringUtils.isNotBlank(configImport.getSpareWarehouses())) {
            List<SpareWarehouseReqVo> spareWarehouses = Lists.newArrayList();
            String warehouses = configImport.getSpareWarehouses();
            String[] split = warehouses.split(",");
            for (int i = 0; i < split.length; i++) {
                LogisticsCenterDTO logisticsCenter = centerList.get(split[i].trim());
                if (Objects.isNull(logisticsCenter)) {
                    errorList.add("未找到" + split[i] + "对应的仓库或仓库被禁用");
                }else {
                    if (split[i].equals(configImport.getTransportCenterName())) {
                        errorList.add("备用仓不能是自己");
                        continue;
                    }
                    SpareWarehouseReqVo spareWarehouseReqVo = new SpareWarehouseReqVo();
                    spareWarehouseReqVo.setTransportCenterCode(logisticsCenter.getLogisticsCenterCode());
                    spareWarehouseReqVo.setTransportCenterName(logisticsCenter.getLogisticsCenterName());
                    spareWarehouseReqVo.setUseOrder(i);
                    spareWarehouses.add(spareWarehouseReqVo);
                }
            }
            copy.setSpareWarehouses(spareWarehouses);
        }
        if(CollectionUtils.isNotEmpty(errorList)){
            copy.setError(StringUtils.strip(errorList.toString(),"[]"));
        }
        return copy;
    }

    /**
     * 校验
     * @author NullPointException
     * @date 2019/7/18
     * @param skuConfigImport
     * @return void
     */
    private void dataValidation(List<SkuConfigImport> skuConfigImport) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuConfigImport)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuConfigImport.size()<2) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = SkuConfigImport.HEDE;
        boolean equals = skuConfigImport.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
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
        temp.setAuditorTime(newVO.getAuditorTime());
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
    private ProductApplyInfoRespVO<SkuConfigDetailRepsVo> dealApplyViewData(List<SkuConfigsRepsVo> list) {
        ProductApplyInfoRespVO<SkuConfigDetailRepsVo> resp = new ProductApplyInfoRespVO<>();
        //数据相同默认取第一个
        SkuConfigDetailRepsVo repsVo = new SkuConfigDetailRepsVo();
        SkuConfigsRepsVo applyVO = list.get(0);
        ApplyProductSkuConfig applyProductSkuConfig = applyMapper.selectByPrimaryKey(applyVO.getId());
        resp.setApplyBy(applyProductSkuConfig.getCreateBy());
        resp.setApplyTime(applyProductSkuConfig.getCreateTime());
        resp.setApplyStatus(applyProductSkuConfig.getAuditorStatus().intValue());
        resp.setAuditorBy(applyProductSkuConfig.getAuditorBy());
        resp.setAuditorTime(applyProductSkuConfig.getAuditorTime());
        resp.setApprovalName(applyProductSkuConfig.getApprovalName());
        resp.setApprovalRemark(applyProductSkuConfig.getApprovalRemark());
        resp.setPurchaseGroupName(applyVO.getPurchasingGroupName());
        resp.setSelectionEffectiveStartTime(applyProductSkuConfig.getSelectionEffectiveStartTime());
        resp.setSelectionEffectiveTime(applyProductSkuConfig.getSelectionEffectiveTime());
        resp.setCode(applyProductSkuConfig.getApplyCode());
        resp.setFormNo(applyProductSkuConfig.getFormNo());
        //统计sku数量
        resp.setSkuNum(list.size());
        repsVo.setConfigs(list);
        List<SkuConfigDetailRepsVo> repsVos = Lists.newArrayList();
        repsVos.add(repsVo);
        resp.setData(repsVos);
         //放置附件
        resp.setApprovalFileInfos(approvalFileInfoService.selectByApprovalTypeAndApplyCode(ApprovalFileTypeEnum.GOODS_WARHOUSE.getType(),resp.getCode()));
        return resp;
    }
}
