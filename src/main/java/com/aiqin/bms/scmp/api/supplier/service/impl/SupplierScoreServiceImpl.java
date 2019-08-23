package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.common.TagTypeCode;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierScore;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.QueryScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SavePurchaseScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SaveRejectScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SaveScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordItemReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.UpdateUseNumReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.UseTagRecordReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.score.DetailScoreRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.score.ScoreListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierScoreMapper;
import com.aiqin.bms.scmp.api.supplier.service.SupplierScoreService;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.bms.scmp.api.supplier.service.TagInfoService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplierScoreServiceImpl
 * @date 2019/5/23 15:56

 */
@Service
public class SupplierScoreServiceImpl implements SupplierScoreService {

    @Autowired
    private SupplierScoreMapper scoreMapper;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private SupplyComService comService;

    @Autowired
    private TagInfoService tagInfoService;
    /**
     * 列表查询
     *
     * @param reqVo
     * @return
     */
    @Override
    public BasePage<ScoreListRespVo> list(QueryScoreReqVo reqVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        List<ScoreListRespVo> list = scoreMapper.getList(reqVo);
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    /**
     * 保存
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(SaveScoreReqVo reqVo) {
        SupplierScore score = new SupplierScore();
        BeanCopyUtils.copy(reqVo,score);
        //设置默认值
        //设置编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.SUPPLIER_SCORE_CODE);
        String code = String.valueOf(encodingRule.getNumberingValue());
        score.setScoreCode(code);
        // 更新编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        score.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        //评分写入数据库
        Integer num = ((SupplierScoreService) AopContext.currentProxy()).insert(score);
        BigDecimal starScore =calculateStarScore(score);
        comService.updateStarScore(reqVo.getSupplierCode(),starScore);
        //标签
        if(CollectionUtils.isNotEmptyCollection(reqVo.getTagInfoList())){
            List<UseTagRecordReqVo> useTagRecordReqVos = Lists.newArrayList();
            List<UpdateUseNumReqVo> updateUseNumReqVos = Lists.newArrayList();
            ArrayList<SaveUseTagRecordItemReqVo> itemReqVos = reqVo.getTagInfoList().stream()
                    .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(f -> f.getTagCode()))), ArrayList::new));
            itemReqVos.forEach(item->{
                UseTagRecordReqVo useTagRecordReqVo = new UseTagRecordReqVo();
                useTagRecordReqVo.setUseObjectCode(reqVo.getSupplierCode());
                useTagRecordReqVo.setUseObjectName(reqVo.getSupplierName());
                useTagRecordReqVo.setTagTypeCode(TagTypeCode.SUPPLIER.getStatus());
                useTagRecordReqVo.setTagTypeName(TagTypeCode.SUPPLIER.getName());
                useTagRecordReqVo.setSourceCode(score.getScoreCode());
                useTagRecordReqVo.setTagCode(item.getTagCode());
                useTagRecordReqVo.setTagName(item.getTagName());
                useTagRecordReqVos.add(useTagRecordReqVo);
                UpdateUseNumReqVo updateUseNumReqVo = new UpdateUseNumReqVo();
                updateUseNumReqVo.setChangeNum(1);
                updateUseNumReqVo.setTagCode(item.getTagCode());
                updateUseNumReqVos.add(updateUseNumReqVo);
            });
            //保存标签记录
            tagInfoService.saveRecordListRepeat(useTagRecordReqVos);
        }
        return code;
    }

    /**
     * 退供评分保存
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveByReject(SaveRejectScoreReqVo reqVo) {
        SaveScoreReqVo saveScoreReqVo = dealData(reqVo);
        return this.save(saveScoreReqVo);
    }

    /**
     * 采购评分保存
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveByPurchase(SavePurchaseScoreReqVo reqVo) {
        SaveScoreReqVo saveScoreReqVo = dealData(reqVo);
        return this.save(saveScoreReqVo);
    }

    private SaveScoreReqVo dealData(Object source){
        SaveScoreReqVo reqVo = new SaveScoreReqVo();
        BeanCopyUtils.copy(source,reqVo);
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String personName = "";
        if(null != authToken){
            personName = authToken.getPersonName();
        }
        reqVo.setScorerName(personName);
        reqVo.setDepartCode(Global.DEFAULT_DEPART_CODE);
        reqVo.setDepartName(Global.DEFAULT_DEPART_NAME);
        return reqVo;
    }
    /**
     * 数据插入
     *
     * @param supplierScore
     * @return
     */
    @Override
    @Save
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(SupplierScore supplierScore) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            supplierScore.setCompanyCode(authToken.getCompanyCode());
            supplierScore.setCompanyName(authToken.getCompanyName());
        }
        return scoreMapper.insertSelective(supplierScore);
    }

    /**
     * 详情查看
     *
     * @param id
     * @return
     */
    @Override
    public DetailScoreRespVo detail(Long id) {
        if(Objects.isNull(id)) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        SupplierScore score = scoreMapper.selectByPrimaryKey(id);
        if(Objects.isNull(score)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        return entityTransRespVo(score);
    }

    /**
     * 详情查看
     *
     * @param code
     * @return
     */
    @Override
    public DetailScoreRespVo detailByCode(String code) {
        if(Objects.isNull(code)) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        SupplierScore score = scoreMapper.selectByCode(code);
        if(Objects.isNull(score)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        return entityTransRespVo(score);
    }

    private DetailScoreRespVo entityTransRespVo(SupplierScore score){
        DetailScoreRespVo respVo = new DetailScoreRespVo();
        BeanCopyUtils.copy(score,respVo);
        //获取标签使用记录
        List<DetailTagUseRespVo> tagUseRespVos = tagInfoService.getUseTagRecordBySourceCode(score.getScoreCode(),
                TagTypeCode.SUPPLIER.getStatus());
        respVo.setTagUseRespVos(tagUseRespVos);
        return respVo;
    }

    /**
     * 计算综合评分
     * @param score
     * @return
     */
    private BigDecimal calculateStarScore(SupplierScore score){
        BigDecimal starScore;
        BigDecimal total = score.getDeliveryTimely().
                            add(score.getReturnTimely()).
                            add(score.getDamageRate()).
                            add(score.getCostSupport()).
                            add(score.getActivitySupport()).
                            add(score.getOrderFillRate()).
                            add(score.getInvoiceReturnTimely());
        starScore = total.divide(new BigDecimal(7),1,BigDecimal.ROUND_HALF_UP);
        return starScore;
    }
}
