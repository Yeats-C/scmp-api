package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoLog;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QueryProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.request.price.QueryProductSkuPriceInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.QueryProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoLogMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChangePriceService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPriceInfoService;
import com.aiqin.bms.scmp.api.purchase.manager.DataManageService;
import com.aiqin.bms.scmp.api.util.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-30
 * @time: 11:14
 */
@Service
@Slf4j
public class ProductSkuPriceInfoServiceImpl extends BaseServiceImpl implements ProductSkuPriceInfoService {
    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;

    @Autowired
    private ProductSkuPriceInfoDraftMapper productSkuPriceInfoDraftMapper;
    @Autowired
    private ApplyProductSkuPriceInfoMapper applyProductSkuPriceInfoMapper;
    @Autowired
    private ProductSkuPriceInfoLogMapper productSkuPriceInfoLogMapper;
    @Autowired
    private DataManageService dataManageService;
    @Autowired
    private ProductSkuChangePriceService productSkuChangePriceService;
    @Override
    public BasePage<QueryProductSkuPriceInfoRespVO> list(QueryProductSkuPriceInfoReqVO reqVO) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        reqVO.setCompanyCode(currentAuthToken.getCompanyCode());
        String categoryId;
        Integer count = productSkuPriceInfoMapper.selectListByQueryVOCount(reqVO);
        List<QueryProductSkuPriceInfoRespVO> list = productSkuPriceInfoMapper.selectListByQueryVO(reqVO);
        if(CollectionUtils.isNotEmptyCollection(list)){
            for(QueryProductSkuPriceInfoRespVO sku:list){
                categoryId = sku.getProductCategoryCode();
                if (StringUtils.isNotBlank(categoryId)) {
                    sku.setProductCategoryName(dataManageService.selectCategoryName(categoryId));
                }
            }
        }
        return PageUtil.getPageList(reqVO.getPageNo(),reqVO.getPageSize(),count,list);
    }

    @Override
    public ProductSkuPriceInfoRespVO view(String code) {
        return productSkuPriceInfoMapper.selectInfoByCode(code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSkuPriceDraft(List<SkuPriceDraftReqVO> reqVOList){
        if(CollectionUtils.isEmptyCollection(reqVOList)){
            throw new BizException(ResultCode.PRICE_DATA_CAN_NOT_BE_NULL);
        }
        List<ProductSkuPriceInfoDraft> drafts = BeanCopyUtils.copyList(reqVOList, ProductSkuPriceInfoDraft.class);
        for (ProductSkuPriceInfoDraft o : drafts) {
            o.setCode("pp"+UUIDUtils.getUUID());
            o.setBeContainArea(0);
        }
        int i = productSkuPriceInfoDraftMapper.insertBatch(drafts);
        if(i!=reqVOList.size()){
            throw new BizException(ResultCode.SAVE_PRICE_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public Integer saveSkuPriceDraft(String applyCode) {
        List<ApplyProductSkuPriceInfo> applyProductSkuPriceInfos = applyProductSkuPriceInfoMapper.selectByApplyCode(applyCode);
        if(CollectionUtils.isNotEmptyCollection(applyProductSkuPriceInfos)){
            List<ProductSkuPriceInfoDraft> drafts = BeanCopyUtils.copyList(applyProductSkuPriceInfos, ProductSkuPriceInfoDraft.class);
            for (ProductSkuPriceInfoDraft o : drafts) {
                o.setCode("pp"+UUIDUtils.getUUID());
                o.setBeContainArea(0);
                o.setId(null);
            }
            return productSkuPriceInfoDraftMapper.insertBatch(drafts);
        }
        return 0;
    }

    @Override
    public List<ProductSkuPriceInfoDraft> getSkuPriceListDraftBySkuCodes(List<String> skuCode){
        if(CollectionUtils.isEmptyCollection(skuCode)){
            return Lists.newArrayList();
        }
        return productSkuPriceInfoDraftMapper.selectBySkuCodes(skuCode);
    }

    @Override
    public List<ApplyProductSkuPriceInfo> getSkuPriceListApplyBySkuCodes(List<String> skuCode,String applyCode){
        if(CollectionUtils.isEmptyCollection(skuCode)){
            return Lists.newArrayList();
        }
        return applyProductSkuPriceInfoMapper.selectBySkuCodes(skuCode,applyCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSkuPriceDraft(List<String> SkuCode){
        if(CollectionUtils.isEmptyCollection(SkuCode)){
            return Boolean.TRUE;
        }
        List<ProductSkuPriceInfoDraft> draftList = getSkuPriceListDraftBySkuCodes(SkuCode);
        if (CollectionUtils.isEmptyCollection(draftList)) {
            return Boolean.TRUE;
        }
        int i = productSkuPriceInfoDraftMapper.deleteBySkuCodes(SkuCode);
        if(i!=draftList.size()){
            throw new BizException(ResultCode.DELETE_SKU_PRICE_DRAFT_FAILED);
        }
        return Boolean.TRUE;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSkuPriceApply(List<ApplyProductSkuPriceInfo> applyList){
        if(CollectionUtils.isEmptyCollection(applyList)){
            throw new BizException(ResultCode.PRICE_DATA_CAN_NOT_BE_NULL);
        }
        int i = applyProductSkuPriceInfoMapper.insertBatch(applyList);
        if(i!=applyList.size()){
            throw new BizException(ResultCode.SAVE_PRICE_FAILED);
        }
        return Boolean.TRUE;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSkuPriceOfficial(List<ProductSkuPriceInfo> list){
        Date date = new Date();
        if(CollectionUtils.isEmptyCollection(list)){
            throw new BizException(ResultCode.PRICE_DATA_CAN_NOT_BE_NULL);
        }
        List<ProductSkuPriceInfoLog> logs = Lists.newArrayList();
        for (ProductSkuPriceInfo info : list) {
            ProductSkuPriceInfoLog infoLog = null;
            info.setBeContainArea(0);
            if(!info.getEffectiveTimeStart().after(date)){
                info.setBeSynchronous(1);
                infoLog =  new ProductSkuPriceInfoLog(info.getCode(),info.getPriceTax(),info.getPriceNoTax(),info.getTax(),info.getEffectiveTimeStart(),null,1,info.getCreateBy(),date);
            }else {
                info.setBeSynchronous(0);
                infoLog = new ProductSkuPriceInfoLog(info.getCode(),info.getPriceTax(),info.getPriceNoTax(),info.getTax(),info.getEffectiveTimeStart(),null,0,info.getCreateBy(),date);
            }
            logs.add(infoLog);
        }
        int i = productSkuPriceInfoMapper.insertBatch(list);
        if(i!=list.size()){
            throw new BizException(ResultCode.SAVE_PRICE_FAILED);
        }
        int i1 = productSkuPriceInfoLogMapper.insertBatch(logs);
        if(i1!=logs.size()){
            throw new BizException(ResultCode.SAVE_PRICE_LOG_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSkuPurchasePriceOfficial(List<ProductSkuPriceInfo> list){
        Date date = new Date();
        if(CollectionUtils.isEmptyCollection(list)){
            throw new BizException(ResultCode.PRICE_DATA_CAN_NOT_BE_NULL);
        }
        List<ProductSkuPriceInfoLog> logs = Lists.newArrayList();
        List<String> skuCodes = list.stream().map(ProductSkuPriceInfo::getSkuCode).distinct().collect(Collectors.toList());
        //验重
        QueryProductSkuPriceInfo queryVO = new QueryProductSkuPriceInfo(list.get(0).getCompanyCode(), skuCodes, Lists.newArrayList(), Lists.newArrayList(), Lists.newArrayList(), Lists.newArrayList(), Lists.newArrayList(), "1");
        List<ProductSkuPriceInfo> all = productSkuPriceInfoMapper.checkRepeat(queryVO);
        Map<String, ProductSkuPriceInfo> infoMap = all.stream().collect(Collectors.toMap(o -> o.getSkuCode() + o.getSupplierCode(), Function.identity(),(k1, k2)->k2));
        //数据对比，分类
        List<ProductSkuPriceInfo> repeat = list.stream().filter(o -> Objects.nonNull(infoMap.get(o.getSkuCode() + o.getSupplierCode()))).collect(Collectors.toList());
        list.removeAll(repeat);
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
            for (ProductSkuPriceInfo priceInfo : list) {
                priceInfo.setCode("PP" + IdSequenceUtils.getInstance().nextId());
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,1, Optional.ofNullable(priceInfo.getUpdateBy()).orElse(priceInfo.getCreateBy()),new Date());
                //判断生效日期
                if (Optional.ofNullable(priceInfo.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
                    //未生效的
                    log.setStatus(0);
                    priceInfo.setBeSynchronous(0);
                } else {
                    //生效的
                    priceInfo.setBeSynchronous(1);
                }
                logList.add(log);
            }
        }
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(repeat)) {
            for (ProductSkuPriceInfo priceInfo : repeat) {
                ProductSkuPriceInfo productSkuPriceInfo = infoMap.get(priceInfo.getSkuCode() + priceInfo.getSupplierCode());
                priceInfo.setCode(productSkuPriceInfo.getCode());
                priceInfo.setId(productSkuPriceInfo.getId());
                priceInfo.setBeContainArea(0);
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,1,Optional.ofNullable(priceInfo.getUpdateBy()).orElse(priceInfo.getCreateBy()),new Date());
                //判断生效日期
                if (Optional.ofNullable(priceInfo.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
                    //未生效的
                    //这里在日志表中插入一条未生效的数据
                    log.setStatus(0);
                    priceInfo.setBeSynchronous(0);
                } else {
                    //生效的
                    priceInfo.setBeSynchronous(1);
                    //插入失效日志，再更新数据, 插入生效的日志
                    ProductSkuPriceInfoLog log2 = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),new Date(),2,Optional.ofNullable(priceInfo.getUpdateBy()).orElse(priceInfo.getCreateBy()),new Date());
                    logList.add(log2);
                }
                logList.add(log);
            }
        }
        productSkuChangePriceService.saveData(list, Lists.newArrayList(), repeat,Lists.newArrayList(),logList);
        return Boolean.TRUE;
    }

    @Override
    public List<ProductSkuPriceRespVo> getSkuPriceBySkuCodeForOfficial(String skuCode) {
        return productSkuPriceInfoMapper.selectBySkuCodeForOfficial(skuCode,getUser().getCompanyCode());
    }

    @Override
    public List<ProductSkuPriceRespVo> getSkuPriceBySkuCodeForApply(String skuCode, String applyCode) {
        return productSkuPriceInfoMapper.selectBySkuCodeForApply(skuCode,applyCode,null);
    }

    @Override
    public List<ProductSkuPriceRespVo> getSkuPriceBySkuCodeForDraft(String skuCode) {
        return productSkuPriceInfoMapper.selectBySkuCodeForDraft(skuCode,getUser().getCompanyCode());
    }
}
