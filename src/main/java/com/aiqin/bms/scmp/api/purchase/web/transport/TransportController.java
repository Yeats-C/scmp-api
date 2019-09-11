package com.aiqin.bms.scmp.api.purchase.web.transport;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.Transport;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportAddRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportLogsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportOrdersResquest;
import com.aiqin.bms.scmp.api.purchase.domain.request.transport.TransportRequest;
import com.aiqin.bms.scmp.api.purchase.service.TransportService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 运输管理控制器
 */
@RestController
@RequestMapping("purchase/transport")
@Slf4j
public class TransportController {
    @Autowired
    private TransportService transportService;

    @ApiOperation("获取发运单列表")
    @PostMapping("/list")
    public HttpResponse list(@RequestBody TransportRequest transportRequest){
        try {
            return HttpResponse.success(transportService.selectPage(transportRequest));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API,-1,e.getMessage()));
        }
    }

    @ApiOperation("获取未发运订单列表")
    @PostMapping("/transportorders")
    public HttpResponse transportOrders(@RequestBody TransportOrdersResquest transportOrdersResquest){
        try {
            return HttpResponse.success(transportService.selectTransportOrdersPage(transportOrdersResquest));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API,-1,e.getMessage()));
        }
    }
    @ApiOperation("获取未发货订单列表")
    @PostMapping("/deliverorders")
    public HttpResponse deliverOrders(@RequestBody TransportOrdersResquest transportOrdersResquest){
        try {
            return HttpResponse.success(transportService.selectDeliverOrdersPage(transportOrdersResquest));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API,-1,e.getMessage()));
        }
    }
    @ApiOperation("保存发运单")
    @PostMapping("/savetransport")
    public HttpResponse saveTransport(@RequestBody TransportAddRequest transportAddRequest){
        try {
            return transportService.saveTransport(transportAddRequest);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API,-1,e.getMessage()));
        }
    }
    @ApiOperation("发运操作")
    @PostMapping("/deliver")
    public HttpResponse deliver(@RequestBody List<String> transportCode){
        try {
            return transportService.deliver(transportCode);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API,-1,e.getMessage()));
        }
    }
    @ApiOperation("发运操作")
    @PostMapping("/sign")
    public HttpResponse sign(@RequestBody List<String> transportCode){
        try {
            return transportService.sign(transportCode);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API,-1,e.getMessage()));
        }
    }

    @ApiOperation("发运单日志列表")
    @PostMapping("/transportlogs")
    public HttpResponse transportLogs(@RequestBody TransportLogsRequest transportLogsRequest){
        try {
            return HttpResponse.success(transportService.transportLogs(transportLogsRequest));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API,-1,e.getMessage()));
        }
    }

    @ApiOperation("发运单详细")
    @GetMapping("/detail")
    public HttpResponse<Transport> detail(@RequestParam String transportCode){
        try {
            return transportService.detail(transportCode);
        } catch (GroundRuntimeException ge){
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API, 500, ge.getMessage()));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @ApiOperation("发运单订单列表")
    @PostMapping("/orders")
    public HttpResponse<BasePage<TransportOrders>> orders(@RequestBody TransportRequest transportRequest){
        try {
            return HttpResponse.success(transportService.orders(transportRequest));
        } catch (GroundRuntimeException ge){
            return HttpResponse.failure(MessageId.create(Project.PURCHASE_API, 500, ge.getMessage()));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
