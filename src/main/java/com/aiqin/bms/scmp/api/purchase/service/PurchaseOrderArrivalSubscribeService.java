package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.PurchaseOrderArrivalSubscribe;
import com.aiqin.bms.scmp.api.purchase.domain.request.purchase.QueryPurchaseOrderArrivalSubscribeVo;
import com.aiqin.bms.scmp.api.purchase.domain.request.purchase.SavePurchaseOrderArrivalSubscribeVo;
import com.aiqin.bms.scmp.api.purchase.domain.response.purchase.QueryPurchaseOrderArrivalSubscribeRespVo;

/**
 * @author knight.xie
 * @version 1.0
 * @className PurchaseOrderArrivalSubscribeService
 * @date 2019/6/28 19:26
 * @description TODO
 */
public interface PurchaseOrderArrivalSubscribeService {

    /**
     * 分页查询
     * @param reqVo
     * @return
     */
    BasePage<QueryPurchaseOrderArrivalSubscribeRespVo> findPage(QueryPurchaseOrderArrivalSubscribeVo reqVo);

    /**
     * 保存预约到货
     * @param reqVo
     * @return
     */
    int save(SavePurchaseOrderArrivalSubscribeVo reqVo);

    /**
     * 插入数据库
     * @param record
     * @return
     */
    int insertSelective(PurchaseOrderArrivalSubscribe record);

    /**
     * 修改入数据库库
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PurchaseOrderArrivalSubscribe record);
}

