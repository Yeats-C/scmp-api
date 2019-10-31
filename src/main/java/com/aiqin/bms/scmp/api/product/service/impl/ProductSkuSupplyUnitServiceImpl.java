package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ConfigSearchVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.ApplyProductSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.UpdateProductSkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.UpdateSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.ApplySkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.QuerySkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.UpdateSkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceProjectRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitCapacityRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.QueryProductSkuSupplyUnitsRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.SkuSupplierDetailRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSupplyUnitDraftMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.service.ApprovalFileInfoService;
import com.aiqin.bms.scmp.api.util.*;
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
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.Object;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:32
 */
@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.APPLY_GOODS_COMPANY)
public class ProductSkuSupplyUnitServiceImpl extends BaseServiceImpl implements ProductSkuSupplyUnitService  , WorkFlowHelper {
    @Autowired
    ProductSkuSupplyUnitDao productSkuSupplyUnitDao;

    @Autowired
    private ProductSkuSupplyUnitDraftMapper draftMapper;

    @Autowired
    private ProductSkuSupplyUnitCapacityService productSkuSupplyUnitCapacityService;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;

    @Autowired
    private ApprovalFileInfoService approvalFileInfoService;

    @Autowired
    private PriceProjectService priceProjectService;

    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private ProductSkuPriceInfoService productSkuPriceInfoService;

    @Override
    @SaveList
    @Transactional(rollbackFor = BizException.class)
    public int insertDraftList(List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts) {
        int num = productSkuSupplyUnitDao.insertDraftList(productSkuSupplyUnitDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<ProductSkuSupplyUnit> productSkuSupplyUnits) {
        int num = productSkuSupplyUnitDao.insertList(productSkuSupplyUnits);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        //通过申请编码查询供应商信息
        List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = productSkuSupplyUnitDao.getApply(skuCode,applyCode);
        if (CollectionUtils.isNotEmptyCollection(applyProductSkuSupplyUnits)){
            List<ProductSkuSupplyUnit> productSkuSupplyUnits = BeanCopyUtils.copyList(applyProductSkuSupplyUnits,ProductSkuSupplyUnit.class);
            productSkuSupplyUnitDao.deleteList(skuCode);
            return ((ProductSkuSupplyUnitService) AopContext.currentProxy()).insertList(productSkuSupplyUnits);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public int insertApplyList(List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits) {
        int num = productSkuSupplyUnitDao.insertApplyList(applyProductSkuSupplyUnits);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
        try {
            List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = new ArrayList<>();
            List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts = productSkuSupplyUnitDao.getDrafts(applyProductSkus);
            if (productSkuSupplyUnitDrafts != null && productSkuSupplyUnitDrafts.size() > 0){
                for (int i=0;i<productSkuSupplyUnitDrafts.size();i++){
                    ApplyProductSkuSupplyUnit applyProductSkuSupplyUnit = new ApplyProductSkuSupplyUnit();
                    BeanCopyUtils.copy(productSkuSupplyUnitDrafts.get(i),applyProductSkuSupplyUnit);
                    applyProductSkuSupplyUnit.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuSupplyUnits.add(applyProductSkuSupplyUnit);
                }
                //批量新增申请
                ((ProductSkuSupplyUnitService) AopContext.currentProxy()).insertApplyList(applyProductSkuSupplyUnits);
                //批量删除草稿
                productSkuSupplyUnitDao.deleteDrafts(applyProductSkus);
            }
            return 1;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 供应商信息-临时表
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuSupplyUnitRespVo> getDraftList(String skuCode) {
        return productSkuSupplyUnitDao.getDraft(skuCode);
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> getDraftList(List<String> skuCodes) {
        return productSkuSupplyUnitDao.getDraftBySkuCodes(skuCodes);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return draftMapper.delete(skuCodes);
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> selectBySkuCode(String skuCode) {
        List<ProductSkuSupplyUnitRespVo> list = productSkuSupplyUnitDao.selectBySkuCode(skuCode);
        if(CollectionUtils.isEmptyCollection(list)){
            return Lists.newArrayList();
        }
        return list;
    }

    /**
     * 功能描述: 根据Id批量查询临时表细腻些
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:09
     */
    @Override
    public List<ProductSkuSupplyUnitDraft> getDraftByIds(List<Long> ids) {
        return draftMapper.selectByIds(ids);
    }

    /**
     * 功能描述: 根据Id批量删除临时表信息
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDraftByIds(List<Long> ids) {
        return draftMapper.deleteDraftByIds(ids);
    }

    @Override
    public Integer deleteDraftById(Long id) {
        ProductSkuSupplyUnitDraft productSkuSupplyUnitDraft = draftMapper.selectById(id);
        if(null == productSkuSupplyUnitDraft){
            throw new BizException(ResultCode.DELETE_ERROR);
        }
        Integer delNum = draftMapper.deleteDraftById(id);
        productSkuSupplyUnitCapacityService.deleteDraftBySkuCodeAndSupplierCode(productSkuSupplyUnitDraft.getProductSkuCode(),productSkuSupplyUnitDraft.getSupplyUnitCode());
        return delNum;
    }

    /**
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:59
     */
    @Override
    public List<ProductSkuSupplyUnitRespVo> getApply(String skuCode, String applyCode) {
        List<ProductSkuSupplyUnitRespVo> list = productSkuSupplyUnitDao.getApplys(skuCode,applyCode);
        if(CollectionUtils.isEmptyCollection(list)){
            return Lists.newArrayList();
        }
        return list;
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> getList(String skuCode) {
        return productSkuSupplyUnitDao.getList(skuCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveListForChange(List<ApplyProductSkuSupplyUnit> unitList) {
        //通过申请编码查询供应商信息
        if (CollectionUtils.isNotEmptyCollection(unitList)){
            List<ProductSkuSupplyUnit> productSkuSupplyUnits = BeanCopyUtils.copyList(unitList,ProductSkuSupplyUnit.class);
            productSkuSupplyUnitDao.deleteList2(unitList.stream().map(ApplyProductSkuSupplyUnit::getProductSkuCode).collect(Collectors.toList()));
            return ((ProductSkuSupplyUnitService) AopContext.currentProxy()).insertList(productSkuSupplyUnits);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tobeEffective(List<ApplyProductSkuSupplyUnit> list) {
        //保存供应商数据
        saveListForChange(list);
        //保存产能数据
        productSkuSupplyUnitCapacityService.saveListForChange(list);
        //设置状态为同步完成
        productSkuSupplyUnitDao.updateBySynStatus(list);
        //保存采购价
        productSkuPriceInfoService.saveSkuPriceOfficial(getPriceInfo(list));
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> getSupplyList(ConfigSearchVo vo) {
        return productSkuSupplyUnitDao.getSupplyList(vo);
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> getApplyCode(String code) {
        List<ProductSkuSupplyUnitRespVo> list = productSkuSupplyUnitDao.getApplyByCode(code);
        if(CollectionUtils.isEmptyCollection(list)){
            return Lists.newArrayList();
        }
        return list;
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> selectApplyBySkuCode(String skuCode) {
        return productSkuSupplyUnitDao.selectApplyBySkuCode(skuCode);
    }

    @Override
    public List<ProductSkuSupplyUnitRespVo> selectApplyBySkuCodes(List<String> collect) {
        return productSkuSupplyUnitDao.selectApplyBySkuCodes(collect);
    }

    @Override
    public BasePage<QueryProductSkuSupplyUnitsRespVo> getListPage(QuerySkuSupplyUnitReqVo reqVo) {
        AuthToken token = AuthenticationInterceptor.getCurrentAuthToken();
        if (null != token) {
            reqVo.setCompanyCode(token.getCompanyCode());
            reqVo.setPersonId(token.getPersonId());
        }
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(reqVo.getProductCategoryCodes())){
            try {
                reqVo.setProductCategoryLv1Code(reqVo.getProductCategoryCodes().get(0));
                reqVo.setProductCategoryLv2Code(reqVo.getProductCategoryCodes().get(1));
                reqVo.setProductCategoryLv3Code(reqVo.getProductCategoryCodes().get(2));
                reqVo.setProductCategoryLv4Code(reqVo.getProductCategoryCodes().get(3));
            } catch (Exception e) {
                log.info("不做处理,让程序继续执行下去");
            }
        }
        PageHelper.startPage(reqVo.getPageNo(), reqVo.getPageSize());
        List<QueryProductSkuSupplyUnitsRespVo> list = productSkuSupplyUnitDao.getListPage(reqVo);
        list.forEach(item->{
            item.setCapacityList(productSkuSupplyUnitCapacityService.getCapacityInfoBySupplyUnitCodeAndProductSkuCode(item.getSupplyUnitCode(),item.getProductSkuCode()));
        });
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    @Override
    public SkuSupplierDetailRepsVo detail(String skuCode) {
        if(StringUtils.isBlank(skuCode)){
            throw new BizException(ResultCode.REQUIRED_PARAMETER);
        }
        SkuSupplierDetailRepsVo repsVo = productSkuSupplyUnitDao.detail(skuCode);
        if (null == repsVo) {
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
        return repsVo;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public Integer update(UpdateSkuSupplyUnitReqVo reqVo) {
        //验证数据提交的数据有没有重复的数据
        if(null == reqVo){
            throw new BizException(ResultCode.REQUIRED_PARAMETER);
        }
        if(CollectionUtils.isEmptyCollection(reqVo.getUpdateProductSkuSupplyUnitReqVos())){
            throw new BizException(ResultCode.SUMBIT_NOT_DATA);
        }
        long count = reqVo.getUpdateProductSkuSupplyUnitReqVos().stream().map(UpdateProductSkuSupplyUnitReqVo::getSupplyUnitCode).distinct().count();
        if(reqVo.getUpdateProductSkuSupplyUnitReqVos().size() != count){
            throw new BizException(ResultCode.REPEAT_DATA);
        }
        //新增数据
        List<UpdateProductSkuSupplyUnitReqVo> addList = reqVo.getUpdateProductSkuSupplyUnitReqVos().stream().filter(item -> Objects.equals(item.getApplyType(), Byte.valueOf("1"))).collect(Collectors.toList());
        //修改数据
        List<UpdateProductSkuSupplyUnitReqVo> updateList = reqVo.getUpdateProductSkuSupplyUnitReqVos().stream().filter(item -> Objects.equals(item.getApplyType(), Byte.valueOf("2"))).collect(Collectors.toList());
        //根据SKU查询所有的供应商信息
        List<ProductSkuSupplyUnitRespVo> productSkuSupplyUnitRespVos = productSkuSupplyUnitDao.selectBySkuCode(reqVo.getSkuCode());
        //需要保存到临时表的数据
        List<UpdateProductSkuSupplyUnitReqVo> list = Lists.newArrayList();
        if(CollectionUtils.isNotEmptyCollection(productSkuSupplyUnitRespVos)){
            List<String> supplyCodes = productSkuSupplyUnitRespVos.stream().map(ProductSkuSupplyUnitRespVo::getSupplyUnitCode).collect(Collectors.toList());
            Map<String, ProductSkuSupplyUnitRespVo> supplyCodeMap = productSkuSupplyUnitRespVos.stream().
                    collect(Collectors.toMap(ProductSkuSupplyUnitRespVo::getSupplyUnitCode, Function.identity(), (k1, k2) -> k2));
            //判断新增的信息是否已经存在
            if(CollectionUtils.isNotEmptyCollection(addList)){
                addList.forEach(item->{
                    if(supplyCodeMap.containsKey(item.getSupplyUnitCode())){
                        throw new BizException(ResultCode.REPEAT_DATA);
                    }
                });
                list.addAll(addList);
            }
            if (CollectionUtils.isNotEmptyCollection(updateList)) {
                //比较修改
                updateList.forEach(item -> {
                    if (diffData(item, reqVo.getSkuCode(),item.getSupplyUnitCode())) {
                        list.add(item);
                    }
                });
            }
        } else {
            list.addAll(reqVo.getUpdateProductSkuSupplyUnitReqVos());
        }
        if(CollectionUtils.isEmptyCollection(list)){
            throw new BizException(ResultCode.SUMBIT_NOT_DATA);
        }
        //删除重复的数据,插入数据
        List<String> deleteSupplyUnitCodes = list.stream().map(UpdateProductSkuSupplyUnitReqVo::getSupplyUnitCode).collect(Collectors.toList());
        //判断需要保存的数据在审批中是否存在
        List<ProductSkuSupplyUnitRespVo> list2 = this.selectApplyBySkuCode(reqVo.getSkuCode());
        List<String> list2SupplyUnitCodes = list2.stream().map(ProductSkuSupplyUnitRespVo::getSupplyUnitCode).collect(Collectors.toList());
        list2SupplyUnitCodes.retainAll(deleteSupplyUnitCodes);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list2SupplyUnitCodes)) {
            throw new BizException(ResultCode.UN_SUBMIT_APPROVAL);
        }
        draftMapper.deleteBySkuCodeAndSupplierCodes(reqVo.getSkuCode(),deleteSupplyUnitCodes);
        productSkuSupplyUnitCapacityService.deleteDraftBySkuCodeAndSupplierCodes(reqVo.getSkuCode(),deleteSupplyUnitCodes);
        //保存数据到临时表
        AuthToken authToken = getUser();
        List<ProductSkuSupplyUnitDraft> skuSupplyUnitDrafts = BeanCopyUtils.copyList(list, ProductSkuSupplyUnitDraft.class);
        List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts = Lists.newArrayList();
        skuSupplyUnitDrafts.forEach(item -> {
            item.setProductSkuCode(reqVo.getSkuCode());
            item.setProductSkuName(reqVo.getSkuName());
            item.setApplyShow(Global.APPLY_SKU_CONFIG_SHOW);
            item.setCompanyCode(authToken.getCompanyCode());
            item.setCompanyName(authToken.getCompanyName());
            item.setTaxRate(reqVo.getTaxRate());
            if (CollectionUtils.isNotEmptyCollection(item.getProductSkuSupplyUnitCapacityDrafts())) {
                item.getProductSkuSupplyUnitCapacityDrafts().forEach(item2 -> {
                    item2.setProductSkuCode(item.getProductSkuCode());
                    item2.setProductSkuName(item.getProductSkuName());
                    item2.setSupplyUnitCode(item.getSupplyUnitCode());
                    item2.setSupplyUnitName(item.getSupplyUnitName());
                });
                productSkuSupplyUnitCapacityDrafts.addAll(item.getProductSkuSupplyUnitCapacityDrafts());
            }
        });
        Integer num = this.insertDraftList(skuSupplyUnitDrafts);
        //供应商产能
        if (CollectionUtils.isNotEmptyCollection(productSkuSupplyUnitCapacityDrafts)) {
            productSkuSupplyUnitCapacityService.insertDraftList(productSkuSupplyUnitCapacityDrafts);
        }
        return num;
    }

    private Boolean diffData(UpdateProductSkuSupplyUnitReqVo source,String skuCode,String supplyUnitCode) {
        Boolean flag = false;
        //比较主表信息
        List<String> supplyUnitCodes = productSkuSupplyUnitDao.selectSupplyUnitCode(skuCode,source);
        //数据存在则说明主表信息没有发生变化
        if(CollectionUtils.isNotEmptyCollection(supplyUnitCodes)){
            //比较产能信息
            if(productSkuSupplyUnitCapacityService.selectInfo(skuCode,supplyUnitCode,source.getProductSkuSupplyUnitCapacityDrafts())){
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    @Override
    public BasePage<QueryProductSkuSupplyUnitsRespVo> getDraftListPage(QuerySkuSupplyUnitReqVo reqVo) {
        AuthToken token = AuthenticationInterceptor.getCurrentAuthToken();
        if (null != token) {
            reqVo.setCompanyCode(token.getCompanyCode());
            reqVo.setPersonId(token.getPersonId());
        }
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(reqVo.getProductCategoryCodes())){
            try {
                reqVo.setProductCategoryLv1Code(reqVo.getProductCategoryCodes().get(0));
                reqVo.setProductCategoryLv2Code(reqVo.getProductCategoryCodes().get(1));
                reqVo.setProductCategoryLv3Code(reqVo.getProductCategoryCodes().get(2));
                reqVo.setProductCategoryLv4Code(reqVo.getProductCategoryCodes().get(3));
            } catch (Exception e) {
                log.info("不做处理,让程序继续执行下去");
            }
        }
        PageHelper.startPage(reqVo.getPageNo(), reqVo.getPageSize());
        List<QueryProductSkuSupplyUnitsRespVo> list = draftMapper.getListPage(reqVo);
        list.forEach(item->{
            item.setCapacityList(productSkuSupplyUnitCapacityService.getCapacityDraftInfoBySupplyUnitCodeAndProductSkuCode(item.getSupplyUnitCode(),item.getProductSkuCode()));
        });
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    @Override
    public SkuSupplierDetailRepsVo draftDetail(Long id) {
        ProductSkuSupplyUnitRespVo productSkuSupplyUnitRespVo = productSkuSupplyUnitDao.selectDraftById(id);
        if(null == productSkuSupplyUnitRespVo) {
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        SkuSupplierDetailRepsVo detail = productSkuSupplyUnitDao.detail(productSkuSupplyUnitRespVo.getProductSkuCode());
        if(null == detail){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        List<ProductSkuSupplyUnitRespVo> respVos = Lists.newArrayList(productSkuSupplyUnitRespVo);
        detail.setSupplierList(respVos);
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer draftUpdate(UpdateSkuSupplyUnitReqVo reqVo) {
        if(CollectionUtils.isEmptyCollection(reqVo.getUpdateProductSkuSupplyUnitReqVos())){
            throw new BizException(ResultCode.SUMBIT_NOT_DATA);
        }
        AuthToken authToken = getUser();
        List<ProductSkuSupplyUnitDraft> skuSupplyUnitDrafts = BeanCopyUtils.copyList(reqVo.getUpdateProductSkuSupplyUnitReqVos(), ProductSkuSupplyUnitDraft.class);
        List<String> deleteSupplyUnitCodes = skuSupplyUnitDrafts.stream().map(ProductSkuSupplyUnitDraft::getSupplyUnitCode).collect(Collectors.toList());
        draftMapper.deleteBySkuCodeAndSupplierCodes(reqVo.getSkuCode(),deleteSupplyUnitCodes);
        productSkuSupplyUnitCapacityService.deleteDraftBySkuCodeAndSupplierCodes(reqVo.getSkuCode(),deleteSupplyUnitCodes);
        List<ProductSkuSupplyUnitCapacityDraft> productSkuSupplyUnitCapacityDrafts = Lists.newArrayList();
        skuSupplyUnitDrafts.forEach(item -> {
            item.setProductSkuCode(reqVo.getSkuCode());
            item.setProductSkuName(reqVo.getSkuName());
            item.setApplyShow(Global.APPLY_SKU_CONFIG_SHOW);
            item.setCompanyCode(authToken.getCompanyCode());
            item.setCompanyName(authToken.getCompanyName());
            if (CollectionUtils.isNotEmptyCollection(item.getProductSkuSupplyUnitCapacityDrafts())) {
                item.getProductSkuSupplyUnitCapacityDrafts().forEach(item2 -> {
                    item2.setProductSkuCode(item.getProductSkuCode());
                    item2.setProductSkuName(item.getProductSkuName());
                    item2.setSupplyUnitCode(item.getSupplyUnitCode());
                    item2.setSupplyUnitName(item.getSupplyUnitName());
                });
                productSkuSupplyUnitCapacityDrafts.addAll(item.getProductSkuSupplyUnitCapacityDrafts());
            }
        });
        Integer num = this.insertDraftList(skuSupplyUnitDrafts);
        //供应商产能
        if (CollectionUtils.isNotEmptyCollection(productSkuSupplyUnitCapacityDrafts)) {
            productSkuSupplyUnitCapacityService.insertDraftList(productSkuSupplyUnitCapacityDrafts);
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer applySave(ApplySkuSupplyUnitReqVo reqVo) {
        List<Long> supplierId = reqVo.getSupplierId();
        if (CollectionUtils.isEmptyCollection(supplierId)) {
            throw new BizException(ResultCode.SUMBIT_NOT_DATA);
        }
        int num = 0;
        String code;
        String formNo;
        synchronized (ProductSkuConfigServiceImpl.class) {
            //获取编码
            EncodingRule numberingType = encodingRuleDao.getNumberingType(EncodingRuleType.APPLY_SKU_CONFIG_CODE);
            code = "CF" + numberingType.getNumberingValue().toString();
            formNo = "SC" + IdSequenceUtils.getInstance().nextId();
            //更新编码
            encodingRuleDao.updateNumberValue(numberingType.getNumberingValue(), numberingType.getId());
        }

        Date currentDate = new Date();

        List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts = this.getDraftByIds(supplierId);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(productSkuSupplyUnitDrafts)) {
            List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits = BeanCopyUtils.copyList(productSkuSupplyUnitDrafts, ApplyProductSkuSupplyUnit.class);
            applyProductSkuSupplyUnits.forEach(item -> {
                item.setApplyCode(code);
                item.setFormNo(formNo);
                item.setApprovalName(reqVo.getApprovalName());
                item.setApprovalRemark(reqVo.getApprovalRemark());
                item.setBeEffective(Global.UN_EFFECTIVE);
                item.setCreateTime(currentDate);
                item.setCreateBy(getUser().getPersonName());
                item.setSelectionEffectiveTime(reqVo.getSelectionEffectiveTime());
                item.setSelectionEffectiveStartTime(reqVo.getSelectionEffectiveStartTime());
                item.setAuditorStatus(ApplyStatus.APPROVAL.getNumber());
                item.setDirectSupervisorCode(reqVo.getDirectSupervisorCode());
                item.setDirectSupervisorName(reqVo.getDirectSupervisorName());

            });
            num = this.insertApplyList(applyProductSkuSupplyUnits);
            this.deleteDraftByIds(supplierId);
            //供应商产能信息
            List<ProductSkuSupplyUnitCapacityDraft> supplyUnitCapacityDrafts =
                    productSkuSupplyUnitCapacityService.getDraftsBySupplyUnitDrafts(productSkuSupplyUnitDrafts);
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(supplyUnitCapacityDrafts)) {
                List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities = BeanCopyUtils.copyList(supplyUnitCapacityDrafts, ApplyProductSkuSupplyUnitCapacity.class);
                applyProductSkuSupplyUnitCapacities.forEach(item -> {
                    item.setApplyCode(code);
                });
                List<Long> ids = supplyUnitCapacityDrafts.stream().map(ProductSkuSupplyUnitCapacityDraft::getId).distinct().collect(Collectors.toList());
                //批量新增申请
                productSkuSupplyUnitCapacityService.insertApplyList(applyProductSkuSupplyUnitCapacities);
                //批量删除草稿
                productSkuSupplyUnitCapacityService.deleteDraftByIds(ids);
            }
        }
        //保存审批附件信息
        approvalFileInfoService.batchSave(reqVo.getApprovalFileInfos(),String.valueOf(code),formNo, ApprovalFileTypeEnum.GOODS_COMPANY.getType());
        workFlow(String.valueOf(code), formNo, reqVo.getDirectSupervisorCode(), reqVo.getApprovalName());
        return num;
    }

    @Override
    public void workFlow(String applyCode, String form, String directSupervisorCode, String approvalName) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormUrl(workFlowBaseUrl.applySkuSupplier + "?approvalType=2&code=" + applyCode + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(form);
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_GOODS_COMPANY.getNum());
        workFlowVO.setTitle(approvalName);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_GOODS_COMPANY);
        //判断是否成功
        if (!workFlowRespVO.getSuccess()) {
            throw new BizException(MessageId.create(Project.SCMP_API,999,workFlowRespVO.getMsg()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo) {
            WorkFlowCallbackVO newVO = updateSupStatus(vo);
            newVO.setAuditorTime(new Date());
            //审批中，直接返回成功
            if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }
            //查询供应商数据
            List<ApplyProductSkuSupplyUnit> unitList = productSkuSupplyUnitDao.selectByFormNo(newVO.getFormNo());
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(unitList)) {
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
                    updateApplyInfoByVO2(newVO, applyCode);
                    if (!b1) {
                        //供应商信息
                        this.saveListForChange(unitList);
                        //供应商产能信息
                        productSkuSupplyUnitCapacityService.saveListForChange(unitList);
                        //保存采购价
                        productSkuPriceInfoService.saveSkuPriceOfficial(getPriceInfo(unitList));
                    }
                }
            }
            return WorkFlowReturn.SUCCESS;
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

    private ApplyProductSkuConfigReqVo dealData(WorkFlowCallbackVO newVO) {
        ApplyProductSkuConfigReqVo temp = new ApplyProductSkuConfigReqVo();
        temp.setAuditorStatus(newVO.getApplyStatus());
        temp.setAuditorBy(newVO.getApprovalUserName());
        temp.setAuditorTime(newVO.getAuditorTime());
        temp.setFormNo(newVO.getFormNo());
        return temp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateApplyInfoByVO2(ApplyProductSkuConfigReqVo req) {
        return productSkuSupplyUnitDao.updateApplyInfo(req);
    }

    @Override
    public List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo) {
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        return productSkuSupplyUnitDao.queryApplyList(reqVo);
    }

    @Override
    public ProductApplyInfoRespVO applyView(String code) {
        List<ProductSkuSupplyUnitRespVo> productSkuSupplyUnitRespVos = this.getApplyCode(code);
        if(org.apache.commons.collections.CollectionUtils.isEmpty(productSkuSupplyUnitRespVos)){
            log.error("传入的编码是：[{}]",code);
            throw new BizException(MessageId.create(Project.PRODUCT_API,98,"数据异常，无法查询到该数据"));
        }
        //组装数据
        return dealApplyViewData(productSkuSupplyUnitRespVos);
    }

    @Override
    public DetailRequestRespVo getInfoByForm(String formNo) {
        DetailRequestRespVo respVo = new DetailRequestRespVo();
        List<ApplyProductSkuSupplyUnit> unitList = productSkuSupplyUnitDao.selectByFormNo(formNo);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(unitList)) {
            throw new BizException(ResultCode.OBJECT_EMPTY_BY_FORMNO);
        }
        String applyCode = unitList.get(0).getApplyCode();
        respVo.setApplyCode(applyCode);
        respVo.setItemCode("4");
        return respVo;
    }

    private ProductApplyInfoRespVO<SkuSupplierDetailRepsVo> dealApplyViewData(List<ProductSkuSupplyUnitRespVo> unitRespVos) {
        ProductApplyInfoRespVO<SkuSupplierDetailRepsVo> resp = new ProductApplyInfoRespVO<>();
        SkuSupplierDetailRepsVo repsVo = new SkuSupplierDetailRepsVo();
        ProductSkuSupplyUnitRespVo respVo = unitRespVos.get(0);
        resp.setApplyBy(respVo.getCreateBy());
        resp.setApplyTime(respVo.getCreateTime());
        resp.setApplyStatus(respVo.getAuditorStatus().intValue());
        resp.setAuditorBy(respVo.getUpdateBy());
        resp.setAuditorTime(respVo.getUpdateTime());
        resp.setApprovalName(respVo.getApprovalName());
        resp.setApprovalRemark(respVo.getApprovalRemark());
        resp.setSelectionEffectiveStartTime(respVo.getSelectionEffectiveStartTime());
        resp.setPurchaseGroupName(respVo.getPurchaseGroupName());
        resp.setSelectionEffectiveTime(respVo.getSelectionEffectiveTime());
        resp.setCode(respVo.getApplyCode());
        resp.setFormNo(respVo.getFormNo());
        resp.setSkuNum(unitRespVos.size());
        repsVo.setSupplierList(unitRespVos);
        List<SkuSupplierDetailRepsVo> repsVos = Lists.newArrayList();
        repsVos.add(repsVo);
        resp.setData(repsVos);
        resp.setApprovalFileInfos(approvalFileInfoService.selectByApprovalTypeAndApplyCode(ApprovalFileTypeEnum.GOODS_COMPANY.getType(), respVo.getApplyCode()));
        return resp;
    }

    private List<ProductSkuPriceInfo> getPriceInfo(List<ApplyProductSkuSupplyUnit> productSkuSupplyUnits) {
        List<ProductSkuPriceInfo> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmptyCollection(productSkuSupplyUnits)) {
            QueryPriceProjectRespVo purchasePriceProject = priceProjectService.getPurchasePriceProject();
            if (null == purchasePriceProject) {
                throw new BizException(ResultCode.SKU_PURCHASE_PRICE_IS_EMPTY);
            }
            //查询所有的采购组
            Set<String> skuList = Sets.newHashSet();
            productSkuSupplyUnits.forEach(o->{
                skuList.add(o.getProductSkuCode());
            });
            String companyCode = productSkuSupplyUnits.get(0).getCompanyCode();
            String companyName = productSkuSupplyUnits.get(0).getCompanyName();
            Map<String, ProductSkuInfo> stringProductSkuInfoMap = skuInfoService.selectBySkuCodes(skuList, companyCode);
            productSkuSupplyUnits.forEach(item -> {
                String purchaseGroupCode = null != stringProductSkuInfoMap.get(item.getProductSkuCode()) ? stringProductSkuInfoMap.get(item.getProductSkuCode()).getProcurementSectionCode() : "";
                String purchaseGroupName = null != stringProductSkuInfoMap.get(item.getProductSkuCode()) ? stringProductSkuInfoMap.get(item.getProductSkuCode()).getProcurementSectionName() : "";
                ProductSkuPriceInfo productSkuPriceInfo = new ProductSkuPriceInfo();
                productSkuPriceInfo.setSkuCode(item.getProductSkuCode());
                productSkuPriceInfo.setSkuName(item.getProductSkuName());
                productSkuPriceInfo.setCompanyCode(companyCode);
                productSkuPriceInfo.setCompanyName(companyName);
                productSkuPriceInfo.setPurchaseGroupCode(purchaseGroupCode);
                productSkuPriceInfo.setPurchaseGroupName(purchaseGroupName);
                productSkuPriceInfo.setPriceItemCode(purchasePriceProject.getPriceProjectCode());
                productSkuPriceInfo.setPriceItemName(purchasePriceProject.getPriceProjectName());
                productSkuPriceInfo.setPriceTypeCode(purchasePriceProject.getPriceTypeCode());
                productSkuPriceInfo.setPriceTypeName(purchasePriceProject.getPriceTypeName());
                productSkuPriceInfo.setPriceAttributeCode(purchasePriceProject.getPriceCategoryCode());
                productSkuPriceInfo.setPriceAttributeName(purchasePriceProject.getPriceCategoryName());
                productSkuPriceInfo.setPriceNoTax(item.getNoTaxPurchasePrice());
                productSkuPriceInfo.setPriceTax(item.getTaxIncludedPrice());
                productSkuPriceInfo.setEffectiveTimeStart(new Date());
                productSkuPriceInfo.setSupplierCode(item.getSupplyUnitCode());
                productSkuPriceInfo.setSupplierName(item.getSupplyUnitName());
                productSkuPriceInfo.setBeDefault(item.getIsDefault().intValue());
                productSkuPriceInfo.setCreateBy(item.getCreateBy());
                productSkuPriceInfo.setUpdateBy(item.getUpdateBy());
                productSkuPriceInfo.setCreateTime(item.getCreateTime());
                productSkuPriceInfo.setUpdateTime(item.getUpdateTime());
                list.add(productSkuPriceInfo);
            });
        }
        return list;
    }
}
