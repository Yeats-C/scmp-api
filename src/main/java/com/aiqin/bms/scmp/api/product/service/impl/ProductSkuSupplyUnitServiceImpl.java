package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuSupplyUnitDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ConfigSearchVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.UpdateProductSkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.QuerySkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.supplier.UpdateSkuSupplyUnitReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitCapacityRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.QueryProductSkuSupplyUnitsRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.supplier.SkuSupplierDetailRepsVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSupplyUnitDraftMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitCapacityService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuSupplyUnitService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.Object;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:32
 */
@Service
@Slf4j
public class ProductSkuSupplyUnitServiceImpl implements ProductSkuSupplyUnitService {
    @Autowired
    ProductSkuSupplyUnitDao productSkuSupplyUnitDao;

    @Autowired
    private ProductSkuSupplyUnitDraftMapper draftMapper;

    @Autowired
    private ProductSkuSupplyUnitCapacityService productSkuSupplyUnitCapacityService;

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
        return draftMapper.deleteDraftById(id);
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
    public List<ProductSkuSupplyUnitCapacityRespVo> getCapacityInfoBySupplyUnitCodeAndProductSkuCode(String supplyUnitCode,String productSkuCode) {
        return productSkuSupplyUnitCapacityService.getCapacityInfoBySupplyUnitCodeAndProductSkuCode(supplyUnitCode,productSkuCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public Integer update(UpdateSkuSupplyUnitReqVo reqVo) {
        //新增数据
        List<UpdateProductSkuSupplyUnitReqVo> addList = reqVo.getUpdateProductSkuSupplyUnitReqVos().stream().filter(item -> Objects.equals(item.getChangeType(), "1")).collect(Collectors.toList());
        //修改数据
        List<UpdateProductSkuSupplyUnitReqVo> updateList = reqVo.getUpdateProductSkuSupplyUnitReqVos().stream().filter(item -> !Objects.equals(item.getChangeType(), "1")).collect(Collectors.toList());
        //根据SKU查询所有的供应商信息
        List<ProductSkuSupplyUnitRespVo> productSkuSupplyUnitRespVos = productSkuSupplyUnitDao.selectBySkuCode(reqVo.getSkuCode());
        //需要保存到临时表的数据
        List<UpdateProductSkuSupplyUnitReqVo> list = Lists.newArrayList();
        if(CollectionUtils.isNotEmptyCollection(productSkuSupplyUnitRespVos)){
            List<String> supplyCodes = productSkuSupplyUnitRespVos.stream().map(ProductSkuSupplyUnitRespVo::getSupplyUnitCode).collect(Collectors.toList());
            //判断新增的信息是否已经存在
            if(CollectionUtils.isNotEmptyCollection(addList)){
                List<String> addSupplyCodes = addList.stream().map(UpdateProductSkuSupplyUnitReqVo::getSupplyUnitCode).collect(Collectors.toList());
                if(supplyCodes.contains(addSupplyCodes)){
                    throw new BizException(ResultCode.REPEAT_DATA);
                }
                list.addAll(addList);
            }
            if (CollectionUtils.isNotEmptyCollection(updateList)) {
                //比较修改
                updateList.forEach(item -> {
                    if (diffData(item, reqVo.getSkuCode())) {
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
        //查询临时表数据 判断新增/修改



        return null;
    }

    private Boolean diffData(UpdateProductSkuSupplyUnitReqVo source,String skuCode) {
        Boolean flag = false;
        //比较主表信息
        List<String> supplyUnitCode = productSkuSupplyUnitDao.selectSupplyUnitCode(skuCode,source);
        //数据存在则说明主表信息没有发生变化
        if(CollectionUtils.isNotEmptyCollection(supplyUnitCode)){
            //比较产能信息
            if(productSkuSupplyUnitCapacityService.selectInfo(source.getProductSkuSupplyUnitCapacityDrafts())){
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }


}
