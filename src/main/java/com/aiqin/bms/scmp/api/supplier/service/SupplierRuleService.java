package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.rule.SaveReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplierRuleService
 * @date 2019/5/23 10:56
 * @description 供应商规则Service
 */
public interface SupplierRuleService {

    /**
     * 保存规则
     * @param reqVo
     * @return
     */
    Integer save(SaveReqVo reqVo);

    /**
     * 获取规则
     * @return
     */
    DetailRespVo findRule();

    /**
     * 保存规则
     * @param rule
     * @return
     */
    Integer insert(SupplierRule rule);

    /**
     * 更新规则
     * @param rule
     * @return
     */
    Integer update(SupplierRule rule);
}
