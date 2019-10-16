package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseNewContrastRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFlowPathResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseNewContrastResponse;
import com.aiqin.bms.scmp.api.purchase.jobs.AutomaticPurchaseService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/
@Api(tags = "采购申请")
@RequestMapping("/apply")
@RestController
public class PurchaseApplyController {

    @Resource
    private PurchaseApplyService purchaseApplyService;
    @Resource
    private AutomaticPurchaseService automaticPurchaseService;

    @GetMapping("/list")
    @ApiOperation("采购申请单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchase_apply_code", value = "采购申请单号", type = "String"),
            @ApiImplicitParam(name = "apply_type", value = "采购申请单类型: 0 手动 1自动", type = "Integer"),
            @ApiImplicitParam(name = "apply_status", value = "采购申请单状态: 0 待提交  1 已完成", type = "Integer"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组 code", type = "String"),
            @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
            @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer") })
    public HttpResponse applyList(@RequestParam(value = "purchase_apply_code", required = false) String purchaseApplyCode,
                                   @RequestParam(value = "apply_type", required = false) Integer applyType,
                                   @RequestParam(value = "apply_status", required = false) Integer applyStatus,
                                   @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                   @RequestParam(value = "begin_time", required = false) String beginTime,
                                   @RequestParam(value = "finish_time", required = false) String finishTime,
                                   @RequestParam(value = "page_no", required = false) Integer pageNo,
                                   @RequestParam(value = "page_size", required = false) Integer pageSize) {
            PurchaseApplyRequest purchaseApplyRequest = new PurchaseApplyRequest(purchaseApplyCode, applyType, applyStatus, purchaseGroupCode,
                    beginTime, finishTime);
            purchaseApplyRequest.setPageSize(pageSize);
            purchaseApplyRequest.setPageNo(pageNo);
        return purchaseApplyService.applyList(purchaseApplyRequest);
    }

    @PostMapping("/product/list")
    @ApiOperation("采购申请单商品列表")
    public HttpResponse applyProductList(@RequestBody PurchaseApplyRequest purchaseApplyRequest){
        return purchaseApplyService.applyProductList(purchaseApplyRequest);
    }

    @PostMapping("/purchase/form")
    @ApiOperation("生成采购申请单")
    public HttpResponse purchaseApplyForm(@RequestBody PurchaseApplyProductRequest applyProductRequest) {
        return purchaseApplyService.purchaseApplyForm(applyProductRequest);
    }

    @GetMapping("/product")
    @ApiOperation("查询申请采购商品信息")
    public HttpResponse searchApplyProduct(@RequestParam("apply_product_id") String applyProductId) {
        return purchaseApplyService.searchApplyProduct(applyProductId);
    }

    @DeleteMapping("/product")
    @ApiOperation("删除申请采购商品信息")
    public HttpResponse deleteApplyProduct(@RequestParam("apply_product_id") String applyProductId) {
        return purchaseApplyService.deleteApplyProduct(applyProductId);
    }

//    @GetMapping("/product/basic")
//    @ApiOperation("查询采购申请-编辑采购申请-商品基本信息")
//    public HttpResponse applyProductBasic(@RequestParam("purchase_apply_id") String purchaseApplyId) {
//        return purchaseApplyService.applyProductBasic(purchaseApplyId);
//    }

    @GetMapping("/selection/product")
    @ApiOperation("查询采购申请-编辑采购申请-选中商品列表")
    public HttpResponse applySelectionProduct(@RequestParam("purchase_apply_id") String purchaseApplyId) {
        return purchaseApplyService.applySelectionProduct(purchaseApplyId);
    }

    @PostMapping("/apply/import")
    @ApiOperation(value = "批量导入采购申请单")
    public HttpResponse purchaseApplyImport(MultipartFile file, @RequestParam(name = "purchase_group_code") String purchaseGroupCode) {
        return purchaseApplyService.purchaseApplyImport(file, purchaseGroupCode);
    }

    @PutMapping("/apply/status")
    @ApiOperation(value = "修改采购申请单的状态")
    public HttpResponse purchaseApplyStatus(@RequestBody PurchaseApply purchaseApply) {
        return purchaseApplyService.purchaseApplyStatus(purchaseApply);
    }

    @GetMapping("/apply/product/detail")
    @ApiOperation("查询采购申请单商品的详情")
    public HttpResponse<PurchaseFlowPathResponse> purchase(@RequestParam("single_count") Integer singleCount,
                                                           @RequestParam("product_purchase_amount") Integer productPurchaseAmount,
                                                           @RequestParam("sku_code") String skuCode,
                                                           @RequestParam("supplier_code") String supplierCode,
                                                           @RequestParam("transport_center_code") String transportCenterCode,
                                                           @RequestParam("product_count") Integer productCount) {
        return purchaseApplyService.applyProductDetail(singleCount, productPurchaseAmount, skuCode, supplierCode, transportCenterCode, productCount);
    }

    @PostMapping("/automatic/purchase")
    @ApiOperation("生成自动采购单")
    public HttpResponse automatic(@RequestParam("data")String data) {
        return automaticPurchaseService.automaticPurchase(data);
    }

    @GetMapping("/execute/warehousing")
    @ApiOperation("定时执行有效期到期入库完成（备货确认开始）")
    public HttpResponse executeWarehousing() {
        return automaticPurchaseService.executeWarehousing();
    }

    @GetMapping("/intellect/purchase")
    @ApiOperation("智能采购-生成建议补货数(yy-mmmm)")
    public HttpResponse intellect(@RequestParam("months") String months) {
        return automaticPurchaseService.intellect(months);
    }

    @PostMapping("/product/contrast/new")
    @ApiOperation("查询采购对比信息")
    public HttpResponse<PurchaseNewContrastResponse> purchaseContrast(@RequestBody PurchaseNewContrastRequest contrastRequest) {
        return purchaseApplyService.purchaseContrast(contrastRequest);
    }

    @GetMapping("/pdf")
    @ApiOperation("订货/收货单导出PDF模板")
    public HttpResponse importPdf(@RequestParam("file_path") String filePath,
                                  @RequestParam("purchase_order_code") String purchaseOrderCode) {
        return purchaseApplyService.importPdf(filePath, purchaseOrderCode);
    }
}
