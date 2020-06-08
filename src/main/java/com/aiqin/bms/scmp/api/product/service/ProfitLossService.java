package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.ProfitLossWmsReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.QueryProfitLossVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.DetailProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.QueryProfitLossRespVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProfitLossService
 * @date 2019/6/28 11:36

 */
public interface ProfitLossService extends BaseService {

    /**
     * 分页查询
     * @param vo
     * @return
     */
    BasePage<QueryProfitLossRespVo> findPage(QueryProfitLossVo vo);

    /**
     * 根据Id查询详情
     * @param id
     * @return
     */
    DetailProfitLossRespVo view(Long id);


    HttpResponse profitLossWmsEcho(ProfitLossWmsReqVo request);
}
