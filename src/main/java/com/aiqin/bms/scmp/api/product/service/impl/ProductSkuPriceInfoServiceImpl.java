package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoLog;
import com.aiqin.bms.scmp.api.product.domain.request.price.QueryProductSkuPriceInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.QueryProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoLogMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPriceInfoService;
import com.aiqin.bms.scmp.api.util.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @Override
    public BasePage<QueryProductSkuPriceInfoRespVO> list(QueryProductSkuPriceInfoReqVO reqVO) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        reqVO.setCompanyCode(currentAuthToken.getCompanyCode());
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(reqVO.getProductCategoryCodes())){
            try {
                reqVO.setProductCategoryLv1Code(reqVO.getProductCategoryCodes().get(0));
                reqVO.setProductCategoryLv2Code(reqVO.getProductCategoryCodes().get(1));
                reqVO.setProductCategoryLv3Code(reqVO.getProductCategoryCodes().get(2));
                reqVO.setProductCategoryLv4Code(reqVO.getProductCategoryCodes().get(3));
            } catch (Exception e) {
                log.info("不做处理,让程序继续执行下去");
            }
        }
        List<Long> ids = productSkuPriceInfoMapper.selectListByQueryVOCount(reqVO);
        if(org.apache.commons.collections.CollectionUtils.isEmpty(ids)){
            return PageUtil.getPageList(reqVO.getPageNo(), Lists.newArrayList());
        }
        List<QueryProductSkuPriceInfoRespVO> list = productSkuPriceInfoMapper.selectListByQueryVO(PageUtil.myPage(ids, reqVO));
        return PageUtil.getPageList(reqVO.getPageNo(),reqVO.getPageSize(),ids.size(),list);
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
