package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceProject;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.AddPriceProjectReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceProjectReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.UpdatePriceProjectReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.PriceProjectRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.PriceProjetGroupType;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceProjectRespVo;
import com.aiqin.bms.scmp.api.product.mapper.PriceProjectMapper;
import com.aiqin.bms.scmp.api.product.service.PriceProjectService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceProjectServiceImpl
 * @date 2019/4/19 12:34
 * @description TODO
 */
@Service
@Slf4j
public class PriceProjectServiceImpl implements PriceProjectService {

    @Autowired
    private PriceProjectMapper priceProjectMapper;

    @Autowired
    private EncodingRuleDao encodingRuleDao;
    /**
     * 查询List
     *
     * @param reqVo
     * @return
     */
    @Override
    public BasePage<QueryPriceProjectRespVo> getList(QueryPriceProjectReqVo reqVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        List<QueryPriceProjectRespVo> list = priceProjectMapper.getList(reqVo);
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    /**
     * 查询所有启用
     *
     * @return
     */
    @Override
    public List<QueryPriceProjectRespVo> getAll() {
        QueryPriceProjectReqVo reqVo = new QueryPriceProjectReqVo();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
        return priceProjectMapper.getList(reqVo);
    }


    @Override
    public List<QueryPriceProjectRespVo> getByTypeCode(String type) {
        QueryPriceProjectReqVo reqVo = new QueryPriceProjectReqVo();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
        reqVo.setPriceCategory(type);
        return priceProjectMapper.getList(reqVo);
    }

    /**
     * 获取采购价格项目
     *
     * @return
     */
    @Override
    public QueryPriceProjectRespVo getPurchasePriceProject() {
        QueryPriceProjectReqVo reqVo = new QueryPriceProjectReqVo();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
        reqVo.setPriceType(PriceTypeEnum.PURCHASE.getTypeCode());
        reqVo.setPriceCategory(PriceTypeEnum.PURCHASE.getAttrCodes());
        List<QueryPriceProjectRespVo> list = priceProjectMapper.getList(reqVo);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取价格项目列表信息,按照价格类型分组
     *
     * @return
     */
    @Override
    public PriceProjetGroupType queryProjectGroupPriceType() {
        PriceProjetGroupType priceProjetGroupType = new PriceProjetGroupType();
        //组装查询条件
        QueryPriceProjectReqVo reqVo = new QueryPriceProjectReqVo();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
        List<QueryPriceProjectRespVo> list = priceProjectMapper.getList(reqVo);
        Map<String,List<QueryPriceProjectRespVo>> collect =
                list.stream().collect(Collectors.groupingBy(QueryPriceProjectRespVo::getPriceTypeCode));
        collect.forEach((k,v)->{
            if(Objects.equals(k,PriceTypeCode.CHANNEL_PRICE)){
                Map<String, List<QueryPriceProjectRespVo>> listMap = v.stream().collect(Collectors.groupingBy(QueryPriceProjectRespVo::getPriceCategoryCode));
                listMap.forEach((k1,v1)->{
                    if(Objects.equals(k1,PriceTypeCode.SALE_ATTR)){
                        priceProjetGroupType.setChannelPriceList(v1);
                    } else if(Objects.equals(k1,PriceTypeCode.TEMPORARY_ATTR)) {
                        priceProjetGroupType.setTemporaryChannelPriceList(v1);
                    }
                });
            } else if(Objects.equals(k,PriceTypeCode.DISTRIBUTION_PRICE)){
                Map<String, List<QueryPriceProjectRespVo>> listMap = v.stream().collect(Collectors.groupingBy(QueryPriceProjectRespVo::getPriceCategoryCode));
                listMap.forEach((k1,v1)->{
                    if(Objects.equals(k1,PriceTypeCode.SALE_ATTR)){
                        priceProjetGroupType.setDistributionPriceList(v1);
                    } else if(Objects.equals(k1,PriceTypeCode.TEMPORARY_ATTR)) {
                        priceProjetGroupType.setTemporaryDistributionPriceList(v1);
                    }
                });
            }else if(Objects.equals(k,PriceTypeCode.SALE_PRICE)){
                Map<String, List<QueryPriceProjectRespVo>> listMap = v.stream().collect(Collectors.groupingBy(QueryPriceProjectRespVo::getPriceCategoryCode));
                listMap.forEach((k1,v1)->{
                    if(Objects.equals(k1,PriceTypeCode.SALE_ATTR)){
                        priceProjetGroupType.setSalePriceList(v1);
                    } else if(Objects.equals(k1,PriceTypeCode.TEMPORARY_ATTR)) {
                        priceProjetGroupType.setTemporarySalePriceList(v1);
                    }
                });
            }
        });
        return priceProjetGroupType;
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Override
    public PriceProjectRespVo view(Long id) {
        if(Objects.isNull(id)) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        PriceProject priceProject = priceProjectMapper.selectByPrimaryKey(id);
        if(Objects.isNull(priceProject)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        PriceProjectRespVo respVo = new PriceProjectRespVo();
        BeanCopyUtils.copy(priceProject,respVo);
        return respVo;
    }

    /**
     * 新增
     *
     * @param addVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(AddPriceProjectReqVo addVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if (null != authToken) {
            companyCode = authToken.getCompanyCode();
        }
        // 验证名字是否重复
        Integer count = priceProjectMapper.checkName(addVo.getPriceProjectName(), null, companyCode);
        if(count>0) {
             throw new BizException(MessageId.create(Project.PRODUCT_API, 63,
                     addVo.getPriceProjectName()+"名称已存在,请重新输入"));
        }
        //价格类型和属性是否选择正确
        PriceTypeEnum priceTypeEnum = PriceTypeEnum.getPriceTypeEnumByTypeCode(addVo.getPriceTypeCode(),addVo.getPriceCategoryCode());
        if (null == priceTypeEnum) {
            throw new BizException(ResultCode.PRICE_TYPE_NO_EXITST);
        }
        //如果是采购,采购只允许存在一条数据
        if(Objects.equals(priceTypeEnum,PriceTypeEnum.PURCHASE)){
            QueryPriceProjectReqVo reqVo = new QueryPriceProjectReqVo();
            reqVo.setCompanyCode(companyCode);
            reqVo.setPriceType(priceTypeEnum.getTypeCode());
            reqVo.setPriceCategory(priceTypeEnum.getAttrCodes());
            reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
            List<QueryPriceProjectRespVo> list = priceProjectMapper.getList(reqVo);
            if(CollectionUtils.isNotEmpty(list)){
                throw new BizException(ResultCode.ONLY_ONE_PURCHASE_PRICE);
            }
        }
        PriceProject priceProject = new PriceProject();
        BeanCopyUtils.copy(addVo,priceProject);
        //设置编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.BASIC_PRICE_PROJECT);
        priceProject.setPriceProjectCode(String.valueOf(encodingRule.getNumberingValue()));
        // 更新编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        priceProject.setPriceProjectEnable(StatusTypeCode.EN_ABLE.getStatus());
        priceProject.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        return ((PriceProjectService) AopContext.currentProxy()).insertSelective(priceProject);
    }

    /**
     * 修改
     *
     * @param updateVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(UpdatePriceProjectReqVo updateVo) {
        if(Objects.isNull(updateVo.getId())) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        PriceProject priceProject = priceProjectMapper.selectByPrimaryKey(updateVo.getId());
        if(Objects.isNull(priceProject)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if (null != authToken) {
            companyCode = authToken.getCompanyCode();
        }
        Integer count = priceProjectMapper.checkName(updateVo.getPriceProjectName(), updateVo.getId(), companyCode);
        if(count>0) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 63,
                    updateVo.getPriceProjectName()+"名称已存在,请重新输入"));
        }
        //价格类型和属性是否选择正确
        PriceTypeEnum priceTypeEnum = PriceTypeEnum.getPriceTypeEnumByTypeCode(updateVo.getPriceTypeCode(),updateVo.getPriceCategoryCode());
        if (null == priceTypeEnum) {
            throw new BizException(ResultCode.PRICE_TYPE_NO_EXITST);
        }
        //如果是采购,采购只允许存在一条数据
        if(Objects.equals(priceTypeEnum,PriceTypeEnum.PURCHASE)){
            QueryPriceProjectReqVo reqVo = new QueryPriceProjectReqVo();
            reqVo.setCompanyCode(companyCode);
            reqVo.setPriceType(priceTypeEnum.getTypeCode());
            reqVo.setPriceCategory(priceTypeEnum.getAttrCodes());
            reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
            List<QueryPriceProjectRespVo> list = priceProjectMapper.getList(reqVo);
            if(CollectionUtils.isNotEmpty(list) && list.size() > 1){
                throw new BizException(ResultCode.ONLY_ONE_PURCHASE_PRICE);
            }
            if(!Objects.equals(list.get(0).getId(),updateVo.getId())){
                throw new BizException(ResultCode.ONLY_ONE_PURCHASE_PRICE);
            }
        }
        BeanCopyUtils.copy(updateVo,priceProject);
        return ((PriceProjectService) AopContext.currentProxy()).updateByPrimaryKeySelective(priceProject);
    }

    @Save
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer insertSelective(PriceProject priceProject){
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            priceProject.setCompanyCode(authToken.getCompanyCode());
            priceProject.setCompanyName(authToken.getCompanyName());
        }
        return  priceProjectMapper.insertSelective(priceProject);
    }

    @Update
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateByPrimaryKeySelective(PriceProject priceProject){

        return  priceProjectMapper.updateByPrimaryKeySelective(priceProject);
    }
}

