package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.request.draft.DetailReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.draft.SaveReqVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.Map;
import java.util.Set;

/**
 * @author knight.xie
 * @version 1.0
 * @className DraftService
 * @date 2019/5/14 11:24

 */
public interface DraftService {

    /**
     * 根据审批类型获取审批单数据
     * @param approvalType
     * @return
     */
    HttpResponse list(Integer approvalType);

    /**
     * 获取商品申请单详情
     * @param reqVo
     * @return
     */
    HttpResponse detail(DetailReqVo reqVo);

    /**
     * 删除商品申请单明细
     * @param reqVo
     * @return
     */
    HttpResponse<Integer> delete(DetailReqVo reqVo);

    /**
     * 保存申请单
     * @param reqVo
     * @return
     */
    HttpResponse<Integer> save(SaveReqVo reqVo);

    /**
     * 通过编码查询
     * @param skuNameList
     * @param companyCode
     * @return
     */
    Map<String, ProductSkuDraft> selectBySkuCode(Set<String> skuNameList, String companyCode);

    Integer deleteSupply(Long id);
}
