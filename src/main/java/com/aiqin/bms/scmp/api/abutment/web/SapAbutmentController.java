package com.aiqin.bms.scmp.api.abutment.web;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/sap/init")
public class SapAbutmentController {
    private static Logger LOGGER = LoggerFactory.getLogger(SapAbutmentController.class);

    @Resource
    private SapBaseDataService sapBaseDataService;

    @GetMapping("/product")
    @ApiOperation("商品")
    public HttpResponse productSynchronization(@RequestParam("begin_time") String beginTime, @RequestParam("finish_time") String finishTime) {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        LOGGER.info("商品同步sap:{}", JsonUtil.toJson(sapOrderRequest));
        sapBaseDataService.productSynchronization(sapOrderRequest);
        return HttpResponse.success();
    }

//    @GetMapping("/order")
//    @ApiOperation("订单")
//    public HttpResponse saleSynchronization(@RequestParam("begin_time") String beginTime, @RequestParam("finish_time") String finishTime) {
//        SapOrderRequest sapOrderRequest = new SapOrderRequest();
//        sapOrderRequest.setBeginTime(beginTime);
//        sapOrderRequest.setFinishTime(finishTime);
//        LOGGER.info("订单同步sap:{}", JsonUtil.toJson(sapOrderRequest));
//        sapBaseDataService.saleSynchronization(sapOrderRequest);
//        return HttpResponse.success();
//    }

    @GetMapping("/stock")
    @ApiOperation("出入库")
    public HttpResponse stockSynchronization(@RequestParam("begin_time") String beginTime, @RequestParam("finish_time") String finishTime) {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        LOGGER.info("出入库同步sap:{}", JsonUtil.toJson(sapOrderRequest));
        sapBaseDataService.stockSynchronization(sapOrderRequest);
        return HttpResponse.success();
    }

    @GetMapping("/supply")
    @ApiOperation("供应商")
    public HttpResponse supplySynchronization(@RequestParam("begin_time") String beginTime, @RequestParam("finish_time") String finishTime) {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        LOGGER.info("供应商同步sap:{}", JsonUtil.toJson(sapOrderRequest));
        sapBaseDataService.supplySynchronization(sapOrderRequest);
        return HttpResponse.success();
    }

    @GetMapping("/purchase/reject")
    @ApiOperation("采购与退供")
    public HttpResponse purchaseSynchronization(@RequestParam("order_code") String orderCode,
                                                @RequestParam("data_type") Integer dataType) {
        LOGGER.info("采购同步sap,采购单号:{}", JsonUtil.toJson(orderCode));
        sapBaseDataService.purchaseAndReject(orderCode, dataType);
        return HttpResponse.success();
    }

    @GetMapping("/sale/return")
    @ApiOperation("订单&退货")
    public HttpResponse saleSynchronization(@RequestParam("order_code") String orderCode,
                                             @RequestParam("data_type") Integer dataType) {
        LOGGER.info("订单同步sap.订单单号:{}", JsonUtil.toJson(orderCode));
        sapBaseDataService.saleAndReturn(orderCode, dataType);
        return HttpResponse.success();
    }

    @GetMapping("/allocation/profitLoss")
    @ApiOperation("调拨&损溢")
    public HttpResponse allocationSynchronization(@RequestParam("order_code") String orderCode) {
        LOGGER.info("调拨/损溢单同步sap.调拨/损溢单单号:{}", JsonUtil.toJson(orderCode));
        sapBaseDataService.allocationAndprofitLoss(orderCode);
        return HttpResponse.success();
    }

}
