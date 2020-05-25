package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
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

    @PostMapping("/purchase/order")
    @ApiOperation("保存采购单")
    public HttpResponse purchaseOrder(@RequestBody PurchaseOrderRequest purchaseOrderRequest) {
        return purchaseManageService.purchaseOrder(purchaseOrderRequest);
    }

    @GetMapping("/order/list")
    @ApiOperation("采购单列表")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "create_begin_time", value = "创建开始时间", type = "String"),
                @ApiImplicitParam(name = "create_finish_time", value = "创建结束时间", type = "String"),
                @ApiImplicitParam(name = "update_begin_time", value = "操作开始时间", type = "String"),
                @ApiImplicitParam(name = "update_finish_time", value = "操作结束时间", type = "String"),
                @ApiImplicitParam(name = "purchase_group_code", value = "采购组编码", type = "String"),
                @ApiImplicitParam(name = "purchase_order_code", value = "采购单号", type = "String"),
                @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
                @ApiImplicitParam(name = "supplier_name", value = "供应商名称", type = "String"),
                @ApiImplicitParam(name = "transport_center_code", value = "仓库", type = "String"),
                @ApiImplicitParam(name = "warehouse_code", value = "库房", type = "String"),
                @ApiImplicitParam(name = "purchase_order_status", value = "采购单审核状态 " +
                        "3.备货确认 4.发货确认  5.入库开始 6.入库中 7.已入库  8.完成 9.取消", type = "Integer"),
                @ApiImplicitParam(name = "purchase_source", value = "采购单来源 0.采购申请 1.订单 ", type = "Integer"),
                @ApiImplicitParam(name = "purchase_apply_code", value = "采购单来源单号", type = "String"),
                @ApiImplicitParam(name = "company_code", value = "公司编码", type = "String"),
                @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
                @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer")})
    public HttpResponse<List<PurchaseOrder>> applyProductList(@RequestParam(value = "create_begin_time", required = false) String createBeginTime,
                                                                      @RequestParam(value = "create_finish_time", required = false) String createFinishTime,
                                                                      @RequestParam(value = "update_begin_time", required = false) String updateBeginTime,
                                                                      @RequestParam(value = "update_finish_time", required = false) String updateFinishTime,
                                                                      @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                                                      @RequestParam(value = "purchase_order_code", required = false) String purchaseOrderCode,
                                                                      @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                                                      @RequestParam(value = "supplier_name", required = false) String supplierName,
                                                                      @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                                      @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                                      @RequestParam(value = "purchase_order_status", required = false) Integer purchaseOrderStatus,
                                                                      @RequestParam(value = "purchase_source", required = false) Integer purchaseSource,
                                                                      @RequestParam(value = "purchase_apply_code", required = false) String purchaseApplyCode,
                                                                      @RequestParam(value = "company_code", required = false) String companyCode,
                                                                      @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                                      @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PurchaseApplyRequest purchaseApplyRequest = new PurchaseApplyRequest(purchaseGroupCode, createBeginTime, createFinishTime, updateBeginTime, updateFinishTime,
                purchaseOrderCode, supplierCode, supplierName, transportCenterCode, warehouseCode, purchaseOrderStatus, purchaseSource, purchaseApplyCode, companyCode);
        purchaseApplyRequest.setPageSize(pageSize);
        purchaseApplyRequest.setPageNo(pageNo);
        return purchaseManageService.purchaseOrderList(purchaseApplyRequest);
    }

    @GetMapping("/order/status")
    @ApiOperation("变更采购单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchase_order_status", value = "采购单状态 0.待确认  3.备货确认 4.发货确认 5.入库开始 6.入库中 7.已入库  8.完成 9.取消 11.重发", type = "Integer"),
            @ApiImplicitParam(name = "purchase_order_id", value = "采购单id", type = "String"),
            @ApiImplicitParam(name = "cancel_reason", value = "取消原因", type = "String"),
            @ApiImplicitParam(name = "cancel_remark", value = "取消备注", type = "String")})
    public HttpResponse cancelPurchaseOrder(@RequestParam("purchase_order_status") Integer purchaseOrderStatus,
                                            @RequestParam("purchase_order_id") String purchaseOrderId,
                                            @RequestParam(value = "cancel_reason", required = false) String cancelReason,
                                            @RequestParam(value = "cancel_remark", required = false) String cancelRemark) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseOrderId);
        purchaseOrder.setPurchaseOrderStatus(purchaseOrderStatus);
        purchaseOrder.setCancelReason(cancelReason);
        purchaseOrder.setCancelRemark(cancelRemark);
        return purchaseManageService.cancelPurchaseOrder(purchaseOrder);
    }

    @GetMapping("/order/details")
    @ApiOperation("查询采购单详情-采购信息")
    public HttpResponse<PurchaseOrder> purchaseOrderDetails(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderDetails(purchaseOrderId);
    }

    @GetMapping("/order/product")
    @ApiOperation("查询采购单商品-商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchase_order_id", value = "采购单id", type = "String"),
            @ApiImplicitParam(name = "is_page", value = "是否分页 0.分页 1.不分页", type = "Integer"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer")})
    public HttpResponse<PurchaseOrderProduct> purchaseOrderProduct(@RequestParam("purchase_order_id") String purchaseOrderId,
                                                                   @RequestParam(value = "is_page", required = false) Integer isPage,
                                                                   @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                                   @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest(purchaseOrderId, isPage);
        request.setPageSize(pageSize);
        request.setPageNo(pageNo);
        return purchaseManageService.purchaseOrderProduct(request);
    }

    @GetMapping("/order/log")
    @ApiOperation("查询采购单-操作日志")
    public HttpResponse<List<OperationLog>> purchaseOrderLog(@RequestParam("operation_id") String operationId) {
        return purchaseManageService.purchaseOrderLog(operationId);
    }

    @GetMapping("/order/amount")
    @ApiOperation("查询采购单-采购数量金额/实际数量金额")
    public HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseManageService.purchaseOrderAmount(purchaseOrderId);
    }

    @PostMapping("/warehousing")
    @ApiOperation("入库")
    public HttpResponse getWarehousing(@RequestBody PurchaseStorageRequest purchaseStorageRequest) {
        return purchaseManageService.getWarehousing(purchaseStorageRequest);
    }

    @GetMapping("/warehouse/receipt")
    @ApiOperation("查询采购单对应的入库单")
    public HttpResponse<List<Inbound>> receipt(@RequestParam("purchase_order_code") String purchaseOrderCode) {
        return purchaseManageService.receipt(purchaseOrderCode);
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

    @GetMapping("/sku/supply")
    @ApiOperation("查询sku对应的供应商")
    public HttpResponse<PurchaseFormResponse> skuSupply(@RequestParam("sku_code") String skuCode,
                                                        @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                        @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                        @RequestParam(value = "settlement_method_code", required = false) String settlementMethodCode,
                                                        @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode) {
        return purchaseManageService.skuSupply(skuCode, transportCenterCode, warehouseCode, settlementMethodCode, purchaseGroupCode);
    }

    @GetMapping("/order/pre")
    @ApiOperation("查询采购的预采购单号")
    public HttpResponse purchaseOrderPre(@RequestParam("purchase_group_code") String purchaseGroupCode,
                                         @RequestParam("purchase_order_type_code") Integer purchaseOrderTypeCode,
                                         @RequestParam(value = "purchase_order_code", required = false) String purchaseOrderCode) {
        return purchaseManageService.purchaseOrderPre(purchaseGroupCode, purchaseOrderTypeCode, purchaseOrderCode);
    }
}