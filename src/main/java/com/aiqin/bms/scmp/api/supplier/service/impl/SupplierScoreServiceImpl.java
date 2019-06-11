package com.aiqin.bms.scmp.api.supplier.service.impl;

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
 * @description TODO
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
    public Integer save(SaveScoreReqVo reqVo) {
        SupplierScore score = new SupplierScore();
        BeanCopyUtils.copy(reqVo,score);
        //设置编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.SUPPLIER_SCORE_CODE);
        score.setScoreCode(String.valueOf(encodingRule.getNumberingValue()));
        // 更新编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        score.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        //评分写入数据库
        Integer num = ((SupplierScoreService) AopContext.currentProxy()).insert(score);
        BigDecimal starScore =calculateStarScore(score);
        comService.updateStarScore(reqVo.getSupplierCode(),starScore);
        //标签
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
        return num;
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
//        String[] split = starScore.toString().split("\\.");
//        String decimals = "5";
//        if (Integer.parseInt(split[1]) < 5 ) {
//            decimals = "0";
//        }
//        String scoreStr = split[0] + "." + decimals;
        return starScore;
    }
}
