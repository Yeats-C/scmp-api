package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyTransportCenter;
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
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

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
            @ApiImplicitParam(name = "create_begin_time", value = "创建开始时间", type = "String"),
            @ApiImplicitParam(name = "create_finish_time", value = "创建结束时间", type = "String"),
            @ApiImplicitParam(name = "update_begin_time", value = "修改开始时间", type = "String"),
            @ApiImplicitParam(name = "update_finish_time", value = "修改结束时间", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "purchase_apply_code", value = "采购申请单号", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商名称", type = "String"),
            @ApiImplicitParam(name = "pre_purchase_type", value = "预采购类型 0 普通采购 1.预采购", type = "Integer"),
            @ApiImplicitParam(name = "apply_type", value = "采购申请单类型: 0 手动 1自动", type = "Integer"),
            @ApiImplicitParam(name = "purchase_source", value = "采购价来源 0.读取 1.录入", type = "Integer"),
            @ApiImplicitParam(name = "apply_status", value = "采购申请状态 0.待提交 1.已完成  2.待审核 3.审核中 4.审核通过 5.审核不通过 6.撤销", type = "Integer"),
            @ApiImplicitParam(name = "company_code", value = "公司编码", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer") })
    public HttpResponse applyList(@RequestParam(value = "create_begin_time", required = false) String createBeginTime,
                                   @RequestParam(value = "create_finish_time", required = false) String createFinishTime,
                                   @RequestParam(value = "update_begin_time", required = false) String updateBeginTime,
                                   @RequestParam(value = "update_finish_time", required = false) String updateFinishTime,
                                   @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                   @RequestParam(value = "purchase_apply_code", required = false) String purchaseApplyCode,
                                   @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                   @RequestParam(value = "supplier_name", required = false) String supplierName,
                                   @RequestParam(value = "pre_purchase_type", required = false) Integer prePurchaseType,
                                   @RequestParam(value = "apply_type", required = false) Integer applyType,
                                   @RequestParam(value = "purchase_source", required = false) Integer purchaseSource,
                                   @RequestParam(value = "apply_status", required = false) Integer applyStatus,
                                   @RequestParam(value = "company_code", required = false) String companyCode,
                                   @RequestParam(value = "page_no", required = false) Integer pageNo,
                                   @RequestParam(value = "page_size", required = false) Integer pageSize) {
            PurchaseApplyRequest purchaseApplyRequest = new PurchaseApplyRequest(createBeginTime, createFinishTime, updateBeginTime, updateFinishTime,
                    purchaseGroupCode, purchaseApplyCode, supplierCode, supplierName, prePurchaseType, applyType, purchaseSource, applyStatus, companyCode);
            purchaseApplyRequest.setPageSize(pageSize);
            purchaseApplyRequest.setPageNo(pageNo);
        return purchaseApplyService.applyList(purchaseApplyRequest);
    }

    @PostMapping("/product/list")
    @ApiOperation("查询采购申请单商品列表（手动选择商品）")
    public HttpResponse applyProductList(@RequestBody PurchaseApplyRequest purchaseApplyRequest){
        return purchaseApplyService.applyProductList(purchaseApplyRequest);
    }

    @GetMapping("/product")
    @ApiOperation("查询申请采购单，商品详情列表")
    public HttpResponse searchApplyProduct(@RequestParam("apply_product_id") String applyProductId) {
        return purchaseApplyService.searchApplyProduct(applyProductId);
    }

    @GetMapping("/transport/center/info")
    @ApiOperation("查询申请采购单，分仓采购信息")
    public HttpResponse<List<PurchaseApplyTransportCenter>> transportCenterPurchase(
            @RequestParam("purchase_apply_code") String purchaseApplyCode,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode) {
        return purchaseApplyService.transportCenterPurchase(purchaseApplyCode, transportCenterCode);
    }

    @DeleteMapping("/product")
    @ApiOperation("删除申请采购商品信息")
    public HttpResponse deleteApplyProduct(@RequestParam("apply_product_id") String applyProductId) {
        return purchaseApplyService.deleteApplyProduct(applyProductId);
    }

    @PostMapping("/apply/import")
    @ApiOperation(value = "批量导入采购申请单")
    public HttpResponse purchaseApplyImport(MultipartFile file, @RequestParam(name = "purchase_group_code") String purchaseGroupCode) {
        return purchaseApplyService.purchaseApplyImport(file, purchaseGroupCode);
    }

    @PostMapping("/purchase/form")
    @ApiOperation("生成采购申请单")
    public HttpResponse purchaseApplyForm(@RequestBody PurchaseApplyProductRequest applyProductRequest) {
        return purchaseApplyService.purchaseApplyForm(applyProductRequest);
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

    @PutMapping("/apply/status")
    @ApiOperation(value = "修改采购申请单的状态")
    public HttpResponse purchaseApplyStatus(@RequestBody PurchaseApply purchaseApply) {
        return purchaseApplyService.purchaseApplyStatus(purchaseApply);
    }

    @GetMapping("/apply/product/detail")
    @ApiOperation("查询采购申请单商品的详情")
    public HttpResponse<PurchaseFlowPathResponse> purchase(@RequestParam("single_count") Integer singleCount,
                                                           @RequestParam("product_purchase_amount") BigDecimal productPurchaseAmount,
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
    public HttpResponse importPdf(@RequestParam("purchase_order_code") String purchaseOrderCode, HttpServletResponse response) {
        return  purchaseApplyService.importPdf(purchaseOrderCode, response);
    }

    @GetMapping("/delete")
    @ApiOperation("删除采购申请单")
    public HttpResponse purchaseDelete(@RequestParam("purchase_order_id") String purchaseOrderId) {
        return purchaseApplyService.purchaseDelete(purchaseOrderId);
    }

}
