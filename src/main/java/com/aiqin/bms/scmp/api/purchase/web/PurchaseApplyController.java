package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFlowPathResponse;
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

    @GetMapping("/product/list")
    @ApiOperation("采购申请单商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchase_apply_id", value = "采购申请单id", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库code", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组 code", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku 编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku 名称", type = "String"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性编码", type = "String"),
            @ApiImplicitParam(name = "product_property_name", value = "商品属性名称", type = "String"),
            @ApiImplicitParam(name = "category_id", value = "品类", type = "String"),
            @ApiImplicitParam(name = "brand_id", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "spu_code", value = "spu 编码", type = "String"),
            @ApiImplicitParam(name = "product_name", value = "spu 名称", type = "String"),
            @ApiImplicitParam(name = "a_replenish_type", value = "14大A品建议补货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "product_replenish_type", value = "畅销商品建议补货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "a_shortage_type", value = "14大A品缺货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "product_shortage_type", value = "畅销商品缺货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer")})
    public HttpResponse applyProductList(
                                         @RequestParam(value = "purchase_apply_id", required = false) String purchaseApplyId,
                                         @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                         @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                         @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                         @RequestParam(value = "sku_code", required = false) String skuCode,
                                         @RequestParam(value = "sku_name", required = false) String skuName,
                                         @RequestParam(value = "product_property_code", required = false) String productPropertyCode,
                                         @RequestParam(value = "product_property_name", required = false) String productPropertyName,
                                         @RequestParam(value = "category_id", required = false) String categoryId,
                                         @RequestParam(value = "category_name", required = false) String categoryName,
                                         @RequestParam(value = "brand_id", required = false) String brandId,
                                         @RequestParam(value = "brand_name", required = false) String brandName,
                                         @RequestParam(value = "spu_code", required = false) String spuCode,
                                         @RequestParam(value = "product_name", required = false) String productName,
                                         @RequestParam(value = "a_replenish_type", required = false) Integer aReplenishType,
                                         @RequestParam(value = "product_replenish_type", required = false) Integer productReplenishType,
                                         @RequestParam(value = "a_shortage_type", required = false) Integer aShortageType,
                                         @RequestParam(value = "product_shortage_type", required = false) Integer productShortageType,
                                         @RequestParam(value = "page_no", required = false) Integer pageNo,
                                         @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PurchaseApplyRequest purchaseApplyRequest = new PurchaseApplyRequest(purchaseApplyId, purchaseGroupCode, skuCode,
                skuName, spuCode, productName, supplierCode, transportCenterCode, brandId, brandName, categoryId, categoryName,
                productPropertyCode, productPropertyName, aReplenishType, productReplenishType, aShortageType, productShortageType);
        purchaseApplyRequest.setPageSize(pageSize);
        purchaseApplyRequest.setPageNo(pageNo);
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
                                                           @RequestParam("transport_center_code") String transportCenterCode) {
        return purchaseApplyService.applyProductDetail(singleCount, productPurchaseAmount, skuCode, supplierCode, transportCenterCode);
    }

    @GetMapping("/product/contrast")
    @ApiOperation("查询采购对比信息")
    public HttpResponse contrast(List<PurchaseApplyProduct> list) {
        return purchaseApplyService.contrast(list);
    }

    @PostMapping("/automatic/purchase")
    @ApiOperation("生成自动采购单")
    public HttpResponse automatic() {
        return automaticPurchaseService.automaticPurchase();
    }
}
