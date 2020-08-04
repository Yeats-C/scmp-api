package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.abutment.web.SapAbutmentController;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannel;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.AddPriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.CommonPriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.UpdatePriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.PriceChannelRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceChannelRespVo;
import com.aiqin.bms.scmp.api.product.mapper.PriceChannelItemMapper;
import com.aiqin.bms.scmp.api.product.mapper.PriceChannelMapper;
import com.aiqin.bms.scmp.api.product.service.PriceChannelService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceChannelServiceImpl
 * @date 2019/4/19 15:30

 */
@Service
@Slf4j
public class PriceChannelServiceImpl implements PriceChannelService {

    private static Logger LOGGER = LoggerFactory.getLogger(PriceChannelServiceImpl.class);


    @Autowired
    private PriceChannelMapper priceChannelMapper;

    @Autowired
    private PriceChannelItemMapper priceChannelItemMapper;

    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private CodeUtils codeUtils;

    /**
     * 查询List
     *
     * @param reqVo
     * @return
     */
    @Override
    public BasePage<QueryPriceChannelRespVo> getList(QueryPriceChannelReqVo reqVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        List<QueryPriceChannelRespVo> list = priceChannelMapper.getList(reqVo);
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    /**
     * 查询所有启用
     *
     * @return
     */
    @Override
    public List<QueryPriceChannelRespVo> getAll() {
        QueryPriceChannelReqVo reqVo = new QueryPriceChannelReqVo();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
        return  priceChannelMapper.getList(reqVo);
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Override
    public PriceChannelRespVo view(Long id) {
        if(Objects.isNull(id)) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        // 查询渠道管理主表
        PriceChannel priceChannel = priceChannelMapper.selectByPrimaryKey(id);
        if(Objects.isNull(priceChannel)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        // 查询渠道管理详情表
        List<PriceChannelItem> priceChannelItems = priceChannelItemMapper.selectByPriceChannelCode(priceChannel.getPriceChannelCode());
        List<CommonPriceChannelReqVo> priceList = BeanCopyUtils.copyList(priceChannelItems, CommonPriceChannelReqVo.class);
        PriceChannelRespVo respVo = new PriceChannelRespVo();
        BeanCopyUtils.copy(priceChannel,respVo);
        respVo.setPriceList(priceList);
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
    public int add(AddPriceChannelReqVo addVo) {
        LOGGER.info("渠道管理新增数据信息:{}", JSON.toJSON(addVo));
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if (null != authToken) {
            companyCode = authToken.getCompanyCode();
        }
        Integer count = priceChannelMapper.checkName(addVo.getPriceChannelName(), null, companyCode);
        if(count>0) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 63,
                    addVo.getPriceChannelName()+"名称已存在,请重新输入"));
        }
        PriceChannel priceChannel = new PriceChannel();
        BeanCopyUtils.copy(addVo,priceChannel);
//        //设置编码
//        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.BASIC_PRICE_CHANNEL);
        String redisCode = codeUtils.getRedisCode(EncodingRuleType.BASIC_PRICE_CHANNEL);
        priceChannel.setPriceChannelCode(redisCode);
        // 更新编码
//        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        priceChannel.setPriceChannelEnable(StatusTypeCode.EN_ABLE.getStatus());
        priceChannel.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        Integer m = ((PriceChannelService) AopContext.currentProxy()).insertSelective(priceChannel);
        List<PriceChannelItem> items = reqTransFormItem(addVo.getPriceList(), priceChannel.getPriceChannelCode(), priceChannel.getPriceChannelName());
        //先删除原有的
        priceChannelItemMapper.deleteByPriceChannelCode(priceChannel.getPriceChannelCode());
        LOGGER.info("需要保存的明细数据:{}", JSON.toJSON(items));
        ((PriceChannelService) AopContext.currentProxy()).insertBatchItem(items);
        return m;
    }

    /**
     * 插入
     *
     * @param priceChannel
     * @return
     */
    @Save
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer insertSelective(PriceChannel priceChannel) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            priceChannel.setCompanyCode(authToken.getCompanyCode());
            priceChannel.setCompanyName(authToken.getCompanyName());
        }
        log.info("需要插入的数据:{}", JSON.toJSON(priceChannel));
        return  priceChannelMapper.insertSelective(priceChannel);
    }

    /**
     * 修改
     *
     * @param updateVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(UpdatePriceChannelReqVo updateVo) {
        LOGGER.info("渠道管理修改数据信息:{}", JSON.toJSON(updateVo));
        if(Objects.isNull(updateVo.getId())) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        PriceChannel priceChannel = priceChannelMapper.selectByPrimaryKey(updateVo.getId());
        if(Objects.isNull(priceChannel)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if (null != authToken) {
            companyCode = authToken.getCompanyCode();
        }
        Integer count = priceChannelMapper.checkName(updateVo.getPriceChannelName(), updateVo.getId(), companyCode);
        if(count>0) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 63,
                    updateVo.getPriceChannelName()+"名称已存在,请重新输入"));
        }
        BeanCopyUtils.copy(updateVo,priceChannel);
        int m = ((PriceChannelService) AopContext.currentProxy()).updateByPrimaryKeySelective(priceChannel);
        List<PriceChannelItem> items = reqTransFormItem(updateVo.getPriceList(), priceChannel.getPriceChannelCode(), priceChannel.getPriceChannelName());
        //先删除原有的
        priceChannelItemMapper.deleteByPriceChannelCode(priceChannel.getPriceChannelCode());
        LOGGER.info("需要保存的明细数据:{}", JSON.toJSON(items));
        ((PriceChannelService) AopContext.currentProxy()).insertBatchItem(items);
        return m;
    }

    /**
     * 修改
     *
     * @param priceChannel
     * @return
     */
    @Update
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateByPrimaryKeySelective(PriceChannel priceChannel) {
        log.info("需要修改的数据:{}", JSON.toJSON(priceChannel));
        return  priceChannelMapper.updateByPrimaryKeySelective(priceChannel);
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public Integer insertBatchItem(List<PriceChannelItem> items) {
        log.info("需要保存的明细数据:{}", JSON.toJSON(items));
        return priceChannelItemMapper.insertBach(items);
    }

    @Override
    public List<PriceChannelItem> selectByChannelCodes(List<String> codes) {
        if(CollectionUtils.isEmptyCollection(codes)){
            return Lists.newArrayList();
        }
        return priceChannelItemMapper.selectByChannelCodes(codes,2);
    }

    @Override
    public Map<String, PriceChannel> selectByChannelNames(Set<String> channelList, String companyCode) {
        return priceChannelMapper.selectByChannelNames(channelList,companyCode);
    }

//    private void itemTransFormChannelResp(PriceChannelRespVo respVo){
//        if(Objects.isNull(respVo)){
//            throw new BizException(ResultCode.OBJECT_EMPTY);
//        }
//        List<PriceChannelItem> priceChannelItems = priceChannelItemMapper.selectByPriceChannelCode(respVo.getPriceChannelCode());
//        priceChannelItems.forEach(item->{
//            if(Objects.equals(PriceTypeCode.CHANNEL_PRICE,item.getPriceTypeCode())){
//                if(Objects.equals(PriceTypeCode.SALE_ATTR,item.getPriceCategoryCode())){
//                    respVo.setChannelPriceCode(item.getPriceProjectCode());
//                    respVo.setChannelPriceName(item.getPriceProjectName());
//                } else {
//                    respVo.setTemporaryChannelPriceCode(item.getPriceProjectCode());
//                    respVo.setTemporaryChannelPriceName(item.getPriceProjectName());
//                }
//            } else if(Objects.equals(PriceTypeCode.DISTRIBUTION_PRICE,item.getPriceTypeCode())){
//                if(Objects.equals(PriceTypeCode.SALE_ATTR,item.getPriceCategoryCode())){
//                    respVo.setDistributionPriceCode(item.getPriceProjectCode());
//                    respVo.setDistributionPriceName(item.getPriceProjectName());
//                } else {
//                    respVo.setTemporaryDistributionPriceCode(item.getPriceProjectCode());
//                    respVo.setTemporaryDistributionPriceName(item.getPriceProjectName());
//                }
//            } else if(Objects.equals(PriceTypeCode.SALE_PRICE,item.getPriceTypeCode())){
//                if(Objects.equals(PriceTypeCode.SALE_ATTR,item.getPriceCategoryCode())){
//                    respVo.setSalePriceCode(item.getPriceProjectCode());
//                    respVo.setSalePriceName(item.getPriceProjectName());
//                } else {
//                    respVo.setTemporarySalePriceCode(item.getPriceProjectCode());
//                    respVo.setTemporarySalePriceName(item.getPriceProjectName());
//                }
//            }
//        });
//    }

    private List<PriceChannelItem> reqTransFormItem(List<CommonPriceChannelReqVo> reqVos, String priceChannelCode, String priceChannelName){
        List<PriceChannelItem> items = Lists.newArrayList();
        for (CommonPriceChannelReqVo reqVo : reqVos) {
            PriceChannelItem item = new PriceChannelItem();
            item.setPriceChannelCode(priceChannelCode);
            item.setPriceChannelName(priceChannelName);
            item.setPriceProjectCode(reqVo.getPriceProjectCode());
            item.setPriceProjectName(reqVo.getPriceProjectName());
            item.setPriceTypeCode(reqVo.getPriceTypeCode());
            item.setPriceTypeName(reqVo.getPriceTypeName());
            item.setPriceCategoryCode(reqVo.getPriceCategoryCode());
            item.setPriceCategoryName(reqVo.getPriceCategoryName());
            item.setEnable(reqVo.getEnable());
            items.add(item);
        }
        return items;
//        item.setPriceChannelCode(priceChannelCode);
//        item.setPriceChannelName(priceChannelName);
//        item.setPriceProjectCode(reqVo.getChannelPriceCode());
//        item.setPriceProjectName(reqVo.getChannelPriceName());
//        item.setPriceTypeCode(PriceTypeCode.CHANNEL_PRICE);
//        item.setPriceTypeName(PriceTypeCode.CHANNEL_PRICE_NAME);
//        item.setPriceCategoryCode(PriceTypeCode.SALE_ATTR);
//        item.setPriceCategoryName(PriceTypeCode.SALE_ATTR_NAME);
//        items.add(item);
//        item = new PriceChannelItem();
//        item.setPriceChannelCode(priceChannelCode);
//        item.setPriceChannelName(priceChannelName);
//        item.setPriceProjectCode(reqVo.getTemporaryChannelPriceCode());
//        item.setPriceProjectName(reqVo.getTemporaryChannelPriceName());
//        item.setPriceTypeCode(PriceTypeCode.CHANNEL_PRICE);
//        item.setPriceTypeName(PriceTypeCode.CHANNEL_PRICE_NAME);
//        item.setPriceCategoryCode(PriceTypeCode.TEMPORARY_ATTR);
//        item.setPriceCategoryName(PriceTypeCode.TEMPORARY_ATTR_NAME);
//        items.add(item);
//        item = new PriceChannelItem();
//        item.setPriceChannelCode(priceChannelCode);
//        item.setPriceChannelName(priceChannelName);
//        item.setPriceProjectCode(reqVo.getDistributionPriceCode());
//        item.setPriceProjectName(reqVo.getDistributionPriceName());
//        item.setPriceTypeCode(PriceTypeCode.DISTRIBUTION_PRICE);
//        item.setPriceTypeName(PriceTypeCode.DISTRIBUTION_PRICE_NAME);
//        item.setPriceCategoryCode(PriceTypeCode.SALE_ATTR);
//        item.setPriceCategoryName(PriceTypeCode.SALE_ATTR_NAME);
//        items.add(item);
//        item = new PriceChannelItem();
//        item.setPriceChannelCode(priceChannelCode);
//        item.setPriceChannelName(priceChannelName);
//        item.setPriceProjectCode(reqVo.getTemporaryDistributionPriceCode());
//        item.setPriceProjectName(reqVo.getTemporaryDistributionPriceName());
//        item.setPriceTypeCode(PriceTypeCode.DISTRIBUTION_PRICE);
//        item.setPriceTypeName(PriceTypeCode.DISTRIBUTION_PRICE_NAME);
//        item.setPriceCategoryCode(PriceTypeCode.TEMPORARY_ATTR);
//        item.setPriceCategoryName(PriceTypeCode.TEMPORARY_ATTR_NAME);
//        items.add(item);
//        item = new PriceChannelItem();
//        item.setPriceChannelCode(priceChannelCode);
//        item.setPriceChannelName(priceChannelName);
//        item.setPriceProjectCode(reqVo.getSalePriceCode());
//        item.setPriceProjectName(reqVo.getSalePriceName());
//        item.setPriceTypeCode(PriceTypeCode.SALE_PRICE);
//        item.setPriceTypeName(PriceTypeCode.SALE_PRICE_NAME);
//        item.setPriceCategoryCode(PriceTypeCode.SALE_ATTR);
//        item.setPriceCategoryName(PriceTypeCode.SALE_ATTR_NAME);
//        items.add(item);
//        item = new PriceChannelItem();
//        item.setPriceChannelCode(priceChannelCode);
//        item.setPriceChannelName(priceChannelName);
//        item.setPriceProjectCode(reqVo.getTemporarySalePriceCode());
//        item.setPriceProjectName(reqVo.getTemporarySalePriceName());
//        item.setPriceTypeCode(PriceTypeCode.SALE_PRICE);
//        item.setPriceTypeName(PriceTypeCode.SALE_PRICE_NAME);
//        item.setPriceCategoryCode(PriceTypeCode.TEMPORARY_ATTR);
//        item.setPriceCategoryName(PriceTypeCode.TEMPORARY_ATTR_NAME);
//        items.add(item);
    }


}
