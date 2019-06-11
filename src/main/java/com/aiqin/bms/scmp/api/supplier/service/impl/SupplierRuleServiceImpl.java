package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.rule.SaveReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierRuleMapper;
import com.aiqin.bms.scmp.api.supplier.service.SupplierRuleService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplierRuleServiceImpl
 * @date 2019/5/23 11:02
 * @description TODO
 */
@Service
public class SupplierRuleServiceImpl implements SupplierRuleService {

    @Autowired
    private SupplierRuleMapper ruleMapper;
    /**
     * 保存规则
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(SaveReqVo reqVo) {
        Integer num;
        SupplierRule rule =  new SupplierRule();;
        if(null != reqVo.getId() && !Objects.equals(reqVo.getId(),MsgStatus.ZERO.longValue())){
           rule = ruleMapper.selectByPrimaryKey(reqVo.getId());
           if (null == rule) {
               throw new BizException(ResultCode.OBJECT_EMPTY);
           }
           rule.setAutoReturnGoodsDay(reqVo.getAutoReturnGoodsDay());
           num = ((SupplierRuleService)AopContext.currentProxy()).update(rule);
        } else {
            DetailRespVo detailRespVo = findRule();
            if(null != detailRespVo.getId()  && !Objects.equals(detailRespVo.getId(),MsgStatus.ZERO.longValue())){
                rule.setId(detailRespVo.getId());
                rule.setAutoReturnGoodsDay(reqVo.getAutoReturnGoodsDay());
                num = ((SupplierRuleService)AopContext.currentProxy()).update(rule);
            }else {
                rule.setAutoReturnGoodsDay(reqVo.getAutoReturnGoodsDay());
                num = ((SupplierRuleService)AopContext.currentProxy()).insert(rule);
            }
        }
        return num;
    }


    /**
     * 获取规则
     *
     * @return
     */
    @Override
    public DetailRespVo findRule() {
        String companyCode = "";
        AuthToken token = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != token){
            companyCode = token.getCompanyCode();
        }
        DetailRespVo detailRespVo = ruleMapper.findByCompanyCode(companyCode);
        if (null == detailRespVo) {
            detailRespVo = new DetailRespVo();
            detailRespVo.setAutoReturnGoodsDay(0);
        }
        return detailRespVo;
    }

    /**
     * 保存规则
     *
     * @param rule
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public Integer insert(SupplierRule rule) {
        AuthToken token = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != token){
            rule.setCompanyCode(token.getCompanyCode());
            rule.setCompanyName(token.getCompanyName());
        }
        return ruleMapper.insertSelective(rule);
    }

    /**
     * 更新规则
     *
     * @param rule
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Update
    public Integer update(SupplierRule rule) {
        return ruleMapper.updateByPrimaryKeySelective(rule);
    }
}
