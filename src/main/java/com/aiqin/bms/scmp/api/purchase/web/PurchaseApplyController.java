package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
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
 * @create: 2019-06-13
 **/

@Api(tags = "采购申请")
@RequestMapping("/apply")
@RestController
public class PurchaseApplyController {

    @Resource
    private PurchaseApplyService purchaseApplyService;

    @GetMapping("/list")
    @ApiOperation("采购申请单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchase_apply_code", value = "采购申请单号", type = "String"),
            @ApiImplicitParam(name = "apply_type", value = "采购申请单类型: 0 手动 1自动", type = "Integer"),
            @ApiImplicitParam(name = "apply_status", value = "采购申请单状态: 0 已完成 1 待提交", type = "Integer"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组 code", type = "String"),
            @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
            @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "每页条数", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "当前页", type = "Integer") })
    public HttpResponse<List<PurchaseApplyResponse>> applyList(@RequestParam(value = "purchase_apply_code", required = false) String purchaseApplyCode,
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
            @ApiImplicitParam(name = "product_nature", value = "商品属性", type = "String"),
            @ApiImplicitParam(name = "category_id", value = "品类", type = "String"),
            @ApiImplicitParam(name = "brand_id", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "spu_code", value = "spu 编码", type = "String"),
            @ApiImplicitParam(name = "product_name", value = "spu 名称", type = "String"),
            @ApiImplicitParam(name = "a_replenish_type", value = "14大A品建议补货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "product_replenish_type", value = "畅销商品建议补货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "a_shortage_type", value = "14大A品缺货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "product_shortage_type", value = "畅销商品缺货传值类型是否有值：0.是 1.否", type = "Integer"),
            @ApiImplicitParam(name = "page_no", value = "每页条数", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "当前页", type = "Integer")})
    public HttpResponse<List<PurchaseApplyProduct>> applyProductList(
                                         @RequestParam(value = "purchase_apply_id") String purchaseApplyId,
                                         @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                         @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                         @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                         @RequestParam(value = "sku_code", required = false) String skuCode,
                                         @RequestParam(value = "sku_name", required = false) String skuName,
                                         @RequestParam(value = "product_nature", required = false) Integer productNature,
                                         @RequestParam(value = "category_id", required = false) String categoryId,
                                         @RequestParam(value = "brand_id", required = false) String brandId,
                                         @RequestParam(value = "spu_code", required = false) String spuCode,
                                         @RequestParam(value = "product_name", required = false) String productName,
                                         @RequestParam(value = "a_replenish_type", required = false) Integer aReplenishType,
                                         @RequestParam(value = "product_replenish_type", required = false) Integer productReplenishType,
                                         @RequestParam(value = "a_shortage_type", required = false) Integer aShortageType,
                                         @RequestParam(value = "product_shortage_type", required = false) Integer productShortageType,
                                         @RequestParam(value = "page_no", required = false) Integer pageNo,
                                         @RequestParam(value = "page_size", required = false) Integer pageSize) {
        PurchaseApplyRequest purchaseApplyRequest = new PurchaseApplyRequest(purchaseApplyId, purchaseGroupCode, skuCode,
                skuName, spuCode, productName, supplierCode, transportCenterCode, brandId, categoryId, productNature,
                aReplenishType, productReplenishType, aShortageType, productShortageType);
        purchaseApplyRequest.setPageSize(pageSize);
        purchaseApplyRequest.setPageNo(pageNo);
        return purchaseApplyService.applyProductList(purchaseApplyRequest);
    }

    @PostMapping("/product/list")
    @ApiOperation("保存申请单商品列表")
    public HttpResponse insertApplyProduct(@RequestBody PurchaseApplyProduct purchaseApplyProduct) {
        return purchaseApplyService.insertApplyProduct(purchaseApplyProduct);
    }

    @GetMapping("/product")
    @ApiOperation("查询/复制申请采购商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apply_product_id", value = "采购申请单商品id", type = "String")
    })
    public HttpResponse searchApplyProduct(@RequestParam("apply_product_id") String applyProductId) {
        return purchaseApplyService.searchApplyProduct(applyProductId);
    }

    @DeleteMapping("/product")
    @ApiOperation("删除申请采购商品信息")
    public HttpResponse deleteApplyProduct(@RequestParam("apply_product_id") String applyProductId) {
        return purchaseApplyService.deleteApplyProduct(applyProductId);
    }

    @GetMapping("/product/basic")
    @ApiOperation("查询申请采购单的商品基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchase_apply_id", value = "采购申请单id", type = "String")
    })
    public HttpResponse applyProductBasic(@RequestParam("purchase_apply_id") String purchaseApplyId) {
        return purchaseApplyService.applyProductBasic(purchaseApplyId);
    }


}
