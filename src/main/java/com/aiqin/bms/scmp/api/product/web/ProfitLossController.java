package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.ProfitLossWmsReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.QueryProfitLossVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.DetailProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.QueryProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.service.ProfitLossService;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossRequest;
import com.aiqin.bms.scmp.api.purchase.web.GoodsRejectController;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProfitLossController
 * @date 2019/6/28 12:48
 */
@RestController
@Api(tags = "损益管理接口")
@RequestMapping("/profitLoss")
@Slf4j
public class ProfitLossController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitLossController.class);

    @Autowired
    private ProfitLossService profitLossService;


    @PostMapping("/page")
    @ApiOperation(value = "分页查询")
    public HttpResponse<BasePage<QueryProfitLossRespVo>> findPage (@RequestBody QueryProfitLossVo vo){
        try {
            return HttpResponse.success(profitLossService.findPage(vo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/view")
    @ApiOperation(value = "详情")
    public HttpResponse<DetailProfitLossRespVo> view(@RequestParam("id") Long id){
        try {
            return HttpResponse.success(profitLossService.view(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
