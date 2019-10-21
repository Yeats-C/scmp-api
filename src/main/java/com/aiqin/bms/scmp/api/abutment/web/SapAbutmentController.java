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

    @GetMapping("/order")
    @ApiOperation("订单")
    public HttpResponse saleSynchronization(@RequestParam("begin_time") String beginTime, @RequestParam("finish_time") String finishTime) {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        LOGGER.info("订单同步sap:{}", JsonUtil.toJson(sapOrderRequest));
        sapBaseDataService.saleSynchronization(sapOrderRequest);
        return HttpResponse.success();
    }

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

    @GetMapping("/purchase")
    @ApiOperation("采购")
    public HttpResponse purchaseSynchronization(@RequestParam("begin_time") String beginTime, @RequestParam("finish_time") String finishTime) {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime(beginTime);
        sapOrderRequest.setFinishTime(finishTime);
        LOGGER.info("采购同步sap:{}", JsonUtil.toJson(sapOrderRequest));
        sapBaseDataService.purchaseSynchronization(sapOrderRequest);
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

}
