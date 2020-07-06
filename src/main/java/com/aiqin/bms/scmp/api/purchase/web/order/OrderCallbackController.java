package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementWmsOutReq;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementWmsReq;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.DeliveryCallBackRequest;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundCallBackRequest;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.ProfitLossWmsReqVo;
import com.aiqin.bms.scmp.api.product.service.MovementService;
import com.aiqin.bms.scmp.api.product.service.ProfitLossService;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.TransfersRequest;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.purchase.web.GoodsRejectController;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@RestController
@RequestMapping("/order/callback")
public class OrderCallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectController.class);

    @Resource
    private OrderCallbackService orderCallbackService;
    @Resource
    private ProfitLossService profitLossService;
    @Resource
    private MovementService movementService;

    @PostMapping("/outbound")
    @ApiOperation(value = "销售出库单回调")
    public HttpResponse outboundOrder(@RequestBody OutboundRequest request) {
        LOGGER.info("销售出库单回调,request:{}", request.toString());
        return orderCallbackService.outboundOrder(request);
    }

    @PostMapping("/return")
    @ApiOperation(value = "销售退货单回调")
    public HttpResponse returnOrder(@RequestBody ReturnRequest request) {
        LOGGER.info("销售退货单回调,request:{}", request.toString());
        return orderCallbackService.returnOrder(request);
    }

    @PostMapping("/transfers")
    @ApiOperation(value = "调拨回调")
    public HttpResponse transfersOrder(@RequestBody TransfersRequest request) {
        LOGGER.info("调拨回调,request:{}", request.toString());
        return orderCallbackService.transfersOrder(request);
    }

//    @PostMapping("/profitloss")
//    @ApiOperation(value = "报损报溢回传")
//    public HttpResponse profitLossOrder(@RequestBody ProfitLossRequest request) {
//        LOGGER.info("报损报溢回传,request:{}", request.toString());
//        return orderCallbackService.profitLossOrder(request);
//    }

    @PostMapping("/wms/profitloss")
    @ApiOperation(value = "wms报损报溢回传")
    public HttpResponse profitLossWmsEcho(@RequestBody ProfitLossWmsReqVo request) {
        LOGGER.info("报损报溢回传,request:{}", request.toString());
        return profitLossService.profitLossWmsEcho(request);
    }

    /**
     *  移库wms推送回调
     * @param request
     * @return
     */
    @PostMapping("/wms/transfers")
    @ApiOperation(value = "移库wms推送回调")
    public HttpResponse movementWmsEcho(@RequestBody MovementWmsReq request) {
        LOGGER.info("移库wms推送回调,request:{}", JsonUtil.toJson(request));
        return movementService.movementWmsEcho(request);
    }

    /**
     *  移库wms出库回调
     * @param request
     * @return
     */
    @PostMapping("/wms/out/transfers")
    @ApiOperation(value = "移库wms出库回调")
    public HttpResponse movementWmsOutEcho(@RequestBody MovementWmsOutReq request) {
        LOGGER.info("移库wms出库回调,request:{}", JSON.toJSON(request));
        return movementService.movementWmsOutEcho(request);
    }

    @PostMapping("/outbound/aiqin")
    @ApiOperation("销售出库单回调并且回传爱亲供应链")
    public HttpResponse outboundOrderCallBack(@RequestBody OutboundCallBackRequest request) {
        LOGGER.info("销售出库单回调并且回传爱亲供应链,request:{}", request.toString());
        return orderCallbackService.outboundOrderCallBack(request);
    }

    @PostMapping("/delivery/info")
    @ApiOperation("发运单的回传")
    public HttpResponse deliveryCallBack(@RequestBody DeliveryCallBackRequest request) {
        LOGGER.info("发运单的回传,request:{}", request.toString());
        return orderCallbackService.deliveryCallBack(request);
    }

}
