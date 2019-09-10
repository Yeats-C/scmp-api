package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.DetailApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.RequsetParamReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplyService
 * @date 2019/4/8 09:34

 */
public interface ApplyService {


    /**
     * 供应商申请列表
     * @param querySupplierReqVO 查询vo
     * @return
     */
    BasePage<ApplyListRespVo> selectApplyList(QueryApplyReqVo querySupplierReqVO);

    HttpResponse detail(DetailApplyReqVo applyReqVo);

    void cancel(DetailApplyReqVo applyReqVo);

    DetailRequestRespVo getRequsetParam(RequsetParamReqVo requsetParamReqVo);
}
