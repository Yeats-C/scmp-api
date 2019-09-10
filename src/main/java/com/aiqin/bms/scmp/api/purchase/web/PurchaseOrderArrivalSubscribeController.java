package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.domain.request.purchase.QueryPurchaseOrderArrivalSubscribeVo;
import com.aiqin.bms.scmp.api.purchase.domain.request.purchase.SavePurchaseOrderArrivalSubscribeVo;
import com.aiqin.bms.scmp.api.purchase.domain.response.purchase.QueryPurchaseOrderArrivalSubscribeRespVo;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseOrderArrivalSubscribeService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author knight.xie
 * @version 1.0
 * @className PurchaseOrderArrivalSubscribeController
 * @date 2019/6/28 20:07

 */
@Api(tags = "采购订货预约")
@RequestMapping("/purchase/arrival/subscribe")
@RestController
@Slf4j
public class PurchaseOrderArrivalSubscribeController {
    @Autowired
    private PurchaseOrderArrivalSubscribeService purchaseOrderArrivalSubscribeService;

    @PostMapping("/page")
    @ApiOperation("分页查询")
    public HttpResponse<BasePage<QueryPurchaseOrderArrivalSubscribeRespVo>> findPage(@RequestBody QueryPurchaseOrderArrivalSubscribeVo reqVo) {
        try {
            return HttpResponse.success(purchaseOrderArrivalSubscribeService.findPage(reqVo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/save")
    @ApiOperation("保存数据")
    public HttpResponse<Integer> save(@RequestBody SavePurchaseOrderArrivalSubscribeVo reqVo) {
        try {
            return HttpResponse.success(purchaseOrderArrivalSubscribeService.save(reqVo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
