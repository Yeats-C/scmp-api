package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseInspectionReport;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyProductInfoResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseOrderResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-14 17:33
 **/

@Api(tags = "采购单管理")
@RequestMapping("/manage")
@RestController
public class PurchaseManageController {

    @Resource
    private PurchaseManageService purchaseManageService;

    @PostMapping("/purchase/form")
    @ApiOperation("查询要生成的采购单")
    public HttpResponse selectPurchaseForm(@RequestBody List<String> applyIds) {
        return purchaseManageService.selectPurchaseForm(applyIds);
    }

    @PostMapping("/purchase/apply/list")
    @ApiOperation("生成采购单-采购申请单商品列表")
    public HttpResponse purchaseApplyList(@RequestBody PurchaseFormRequest purchaseFormRequest) {
        return purchaseManageService.purchaseApplyList(purchaseFormRequest);
    }

    @PostMapping("/purchase/product/list")
    @ApiOperation("生成采购单-商品列表")
    public HttpResponse purchaseProductList(@RequestBody PurchaseFormRequest purchaseFormRequest) {
        return purchaseManageService.purchaseProductList(purchaseFormRequest);
    }

    @PostMapping("/purchase/order")
    @ApiOperation("提交采购单")
    public HttpResponse purchaseOrder(@RequestBody PurchaseOrderRequest purchaseOrderRequest) {
        return purchaseManageService.purchaseOrder(purchaseOrderRequest);
    }

    @GetMapping("/order/list")
    @ApiOperation("采购单列表")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "purchase_order_code", value = "采购单号", type = "String"),
                @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
                @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
                @ApiImplicitParam(name = "purchase_group_code", value = "采购组 code", type = "String"),
                @ApiImplicitParam(name = "supplier_code", value = "供应商名称", type = "String"),
                @ApiImplicitParam(name = "transport_center_code", value = "仓库", type = "String"),
                @ApiImplicitParam(name = "warehouse_code", value = "库房", type = "String"),
                @ApiImplicitParam(name = "purchase_order_status", value = "采购单审核状态" +
         "0.待审核 1.审核中 2.审核通过  3.备货确认 4.发货确认  5.入库开始 6.入库中 7.已入库  8.完成 9.取消 10.审核不通过", type = "Integer"),
                @ApiImplicitParam(name = "storage_status", value = "仓储状态 0.未开始  1.确认中 2.完成", type = "Integer"),
                @ApiImplicitParam(name = "purchase_mode", value = "采购方式 0 配送  1.铺采直送", type = "Integer"),
                @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
                @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer")})
    public HttpResponse<List<PurchaseOrderResponse>> applyProductList(@RequestParam(value = "purchase_order_code", required = false) String purchaseOrderCode,
                                                                      @RequestParam(value = "begin_time", required = false) String beginTime,
                                                                      @RequestParam(value = "finish_time", required = false) String finishTime,
                                                                      @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                                                      @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                                                      @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                                      @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                                      @RequestParam(value = "purchase_order_status", required = false) Integer purchaseOrderStatus,
                                                                      @RequestParam(value = "storage_status", required = false) Integer storageStatus,
                                                                      @RequestParam(value = "purchase_mode", required = false) Integer purchaseMode,
                                                                      @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                                      @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PurchaseApplyRequest purchaseApplyRequest = new PurchaseApplyRequest(purchaseGroupCode, beginTime, finishTime, supplierCode,
                transportCenterCode, purchaseOrderCode, warehouseCode, purchaseOrderStatus, storageStatus, purchaseMode);
        purchaseApplyRequest.setPageSize(pageSize);
        purchaseApplyRequest.setPageNo(pageNo);
        return purchaseManageService.purchaseOrderList(purchaseApplyRequest);
    }

    @PutMapping("/order/status")
    @ApiOperation("变更采购单状态")
    public HttpResponse cancelPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseManageService.cancelPurchaseOrder(purchaseOrder);
    }

    @GetMapping("/order/details")
    @ApiOperation("查询采购单详情-采购信息")
    public HttpResponse<PurchaseApplyDetailResponse> purchaseOrderDetails(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderDetails(purchaseOrderId);
    }

    @GetMapping("/order/product")
    @ApiOperation("查询采购单商品-商品列表")
    public HttpResponse purchaseOrderProduct(@RequestParam("purchase_order_id") String purchaseOrderId,
                                             @RequestParam(value = "is_page", required = false) Integer isPage,
                                             @RequestParam(value = "page_no", required = false) Integer pageNo,
                                             @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest(purchaseOrderId, isPage);
        request.setPageSize(pageSize);
        request.setPageNo(pageNo);
        return purchaseManageService.purchaseOrderProduct(request);
    }

    @GetMapping("/order/file")
    @ApiOperation("查询采购单文件-文件信息")
    public HttpResponse purchaseOrderFile(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderFile(purchaseOrderId);
    }

    @GetMapping("/order/log")
    @ApiOperation("查询采购单-操作日志")
    public HttpResponse purchaseOrderLog(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderLog(purchaseOrderId);
    }

    @GetMapping("/order/amount")
    @ApiOperation("查询采购单-采购数量金额/实际数量金额")
    public HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderAmount(purchaseOrderId);
    }

    @PostMapping("/order/stock")
    @ApiOperation("采购单-开始备货")
    public HttpResponse purchaseOrderStock(@RequestBody PurchaseStorageRequest purchaseStorage) {
        return purchaseManageService.purchaseOrderStock(purchaseStorage);
    }

    @PostMapping("/warehousing")
    @ApiOperation("入库")
    public HttpResponse getWarehousing(@RequestBody PurchaseStorageRequest purchaseStorageRequest) {
        return purchaseManageService.getWarehousing(purchaseStorageRequest);
    }

    @GetMapping("/sku/info")
    @ApiOperation("查询质检报告对应的sku")
    public HttpResponse reportSku(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.reportSku(purchaseOrderId);
    }

    @GetMapping("/warehouse/receipt")
    @ApiOperation("查询采购单对应的入库单")
    public HttpResponse<List<Inbound>> receipt(@RequestParam("purchase_order_code") String purchaseOrderCode) {
        return purchaseManageService.receipt(purchaseOrderCode);
    }

    @PostMapping("/storage/confirm")
    @ApiOperation("仓储确认-质检报告、供应商评分 ")
    public HttpResponse storageConfirm(@RequestBody PurchaseStorageRequest storageRequest) {
        return purchaseManageService.storageConfirm(storageRequest);
    }

    @GetMapping("/warehouse/receipt/product")
    @ApiOperation("查询采购单对应的入库单的商品信息")
    public HttpResponse<PurchaseApplyDetailResponse> receiptProduct(@RequestParam("purchase_order_code") String purchaseOrderCode,
                                                       @RequestParam("purchase_num") Integer purchaseNum,
                                                       @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                       @RequestParam(value = "page_size", required = false) Integer pageSize) {
        return purchaseManageService.receiptProduct(purchaseOrderCode, purchaseNum, pageNo, pageSize);
    }

    @PostMapping("/add/log")
    @ApiOperation("添加采购单-操作日志")
    public HttpResponse addLog(@RequestBody OperationLog operationLog) {
        return purchaseManageService.addLog(operationLog);
    }

    @GetMapping("/purchase/inspection/report")
    @ApiOperation("查询采购单对应的质检报告")
    public HttpResponse<PurchaseInspectionReport> inspectionReport(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.inspectionReport(purchaseOrderId);
    }

}
