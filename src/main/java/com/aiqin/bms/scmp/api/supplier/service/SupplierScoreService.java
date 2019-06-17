package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierScore;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.QueryScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SavePurchaseScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SaveRejectScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SaveScoreReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.score.DetailScoreRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.score.ScoreListRespVo;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplierScoreService
 * @date 2019/5/23 15:33
 * @description TODO
 */
public interface SupplierScoreService {

    /**
     * 列表查询
     * @param reqVo
     * @return
     */
    BasePage<ScoreListRespVo> list(QueryScoreReqVo reqVo);

    /**
     * 保存
     * @param reqVo
     * @return
     */
    Integer save(SaveScoreReqVo reqVo);

    /**
     * 数据插入
     * @param supplierScore
     * @return
     */
    Integer insert(SupplierScore supplierScore);

    /**
     * 详情查看
     * @param id
     * @return
     */
    DetailScoreRespVo detail(Long id);


    /**
     * 退供评分保存
     * @param reqVo
     * @return
     */
    Integer saveByReject(SaveRejectScoreReqVo reqVo);

    /**
     * 采购评分保存
     * @param reqVo
     * @return
     */
    Integer saveByPurchase(SavePurchaseScoreReqVo reqVo);
}
