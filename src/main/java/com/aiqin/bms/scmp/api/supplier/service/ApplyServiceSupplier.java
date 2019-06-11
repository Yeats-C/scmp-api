package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.DetailApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplyServiceSupplier
 * @date 2019/4/8 09:34
 * @description TODO
 */
public interface ApplyServiceSupplier extends SupplierBaseService {


    /**
     * 供应商申请列表
     * @param querySupplierReqVO 查询vo
     * @return
     */
    BasePage<ApplyListRespVo> selectApplyList(QueryApplyReqVo querySupplierReqVO);

    HttpResponse detail(DetailApplyReqVo applyReqVo);

    void cancel(DetailApplyReqVo applyReqVo);
}
