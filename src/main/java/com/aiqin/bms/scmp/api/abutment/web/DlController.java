package com.aiqin.bms.scmp.api.abutment.web;

import com.aiqin.bms.scmp.api.abutment.domain.DlStoreInfo;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.*;
import com.aiqin.bms.scmp.api.abutment.domain.request.product.ProductInspectionRequest;
import com.aiqin.bms.scmp.api.abutment.jobs.DlMonthStockService;
import com.aiqin.bms.scmp.api.abutment.service.DlAbutmentService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "DL单据等信息传送")
@RequestMapping("/dl")
public class DlController {

    @Resource
    private DlAbutmentService dlService;
    @Resource
    private DlMonthStockService dlMonthStockService;

    @PostMapping("/order/info")
    @ApiOperation(value = "DL->熙耘，推送耘链销售单")
    public HttpResponse orderInfo(@RequestBody OrderInfoRequest request) {
        return dlService.orderInfo(request);
    }

    @PostMapping("/return/info")
    @ApiOperation(value = "DL推送耘链退货单")
    public HttpResponse returnInfo(@RequestBody ReturnOrderInfoRequest request) {
        return dlService.returnInfo(request);
    }

    @PostMapping("/order/transport")
    @ApiOperation(value = "耘链回传DL销售物流单")
    public HttpResponse orderTransport(@RequestBody OrderTransportRequest request) {
        return dlService.orderTransport(request);
    }

    @PostMapping("/order/echo")
    @ApiOperation(value = "耘链回传DL单据信息")
    public HttpResponse echoOrderInfo(@RequestBody EchoOrderRequest request) {
        return dlService.echoOrderInfo(request);
    }

    @PostMapping("/stock/change")
    @ApiOperation(value = "耘链回传DL库存变动")
    public HttpResponse stockChange(@RequestBody StockChangeRequest request) {
        return dlService.stockChange(request);
    }

    @PostMapping("/store/info")
    @ApiOperation(value = "DL同步耘链门店信息")
    public HttpResponse storeInfo(@RequestBody DlStoreInfo request) {
        return dlService.storeInfo(request);
    }

    @PostMapping("/order/cancel")
    @ApiOperation(value = "DL同步耘链单据取消")
    public HttpResponse orderCancel(@RequestBody OrderCancelRequest request) {
        return dlService.orderCancel(request);
    }

    @PostMapping("/supplier")
    @ApiOperation(value = "DL->熙耘，供应商信息推送")
    public HttpResponse supplierInfo(@RequestBody SupplierInfoRequest request) {
        return dlService.supplierInfo(request);
    }

    @GetMapping("/syn/month/stock")
    @ApiOperation(value = "熙耘->DL，德邦/京东月份批次库存同步")
    public HttpResponse<List<MonthStockRequest>> monthStockDl(@RequestParam(value = "warehouse_code",required = false) String warehouseCode) {
        return dlMonthStockService.monthStockDl(warehouseCode);
    }

    @PostMapping("/product/inspection")
    @ApiOperation(value = "熙耘->DL，质检报告的推送")
    public HttpResponse productInspection(@RequestBody ProductInspectionDlRequest request) {
        return dlService.productInspection(request);
    }

    @PostMapping("/manual/inspection")
    @ApiOperation(value = "手动批量推送质检报告")
    public HttpResponse manualInspection() {
        return dlService.manualInspection();
    }

//    @PostMapping("/product")
//    @ApiOperation(value = "耘链 DL商品信息")
//    public HttpResponse productInfo(@RequestBody ProductInfoRequest request) {
//        return dlService.productInfo(request);
//    }
}
