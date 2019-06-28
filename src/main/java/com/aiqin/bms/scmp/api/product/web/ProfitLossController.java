package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.QueryProfitLossVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.DetailProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.QueryProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.service.ProfitLossService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProfitLossController
 * @date 2019/6/28 12:48
 * @description TODO
 */
@RestController
@Api(tags = "损益管理接口")
@RequestMapping("/profitLoss")
@Slf4j
public class ProfitLossController {

    @Autowired
    private ProfitLossService profitLossService;


    @PostMapping("/page")
    @ApiOperation(value = "分页查询")
    public HttpResponse<BasePage<QueryProfitLossRespVo>> findPage (@RequestBody QueryProfitLossVo vo){
        try {
            return HttpResponse.success(profitLossService.findPage(vo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/view")
    @ApiOperation(value = "详情")
    public HttpResponse<DetailProfitLossRespVo> view(@RequestParam("id") Long id){
        try {
            return HttpResponse.success(profitLossService.view(id));
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
