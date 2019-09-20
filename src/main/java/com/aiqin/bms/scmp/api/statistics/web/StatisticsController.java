package com.aiqin.bms.scmp.api.statistics.web;

import com.aiqin.bms.scmp.api.statistics.domain.request.InventoryStatisticsRequest;
import com.aiqin.bms.scmp.api.statistics.domain.request.ProductRequest;
import com.aiqin.bms.scmp.api.statistics.domain.request.SaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.request.SupplierRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.InventoryStatisticsResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.ProductMovableResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.category.CategoryResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.negative.NegativeSumResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.product.ProductStockOutResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.sale.SaleSumResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.SupplierDeliveryResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.supplier.SupplierReturnResponse;
import com.aiqin.bms.scmp.api.statistics.service.*;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2019-08-19
 **/
@RestController
@Api(tags = "统计报表")
@RequestMapping("/statistics/")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;
    @Resource
    private SalesStatisticsService salesStatisticsService;
    @Resource
    private SupplierStatisticsService supplierStatisticsService;
    @Resource
    private ProductStatisticsService productStatisticsService;
    @Resource
    private InventoryStatisticsService inventoryStatisticsService;

    @GetMapping("/store/repurchase/rate")
    @ApiOperation("门店复购率统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<StoreRepurchaseRateResponse> storeRepurchaseRate(
            @RequestParam("date") String date,  @RequestParam("type") Integer type,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        return statisticsService.storeRepurchaseRate(date, type, productSortCode);
    }

    @GetMapping("/negative/gross/profit")
    @ApiOperation("负毛利统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "report_type", value = "报表类型: 0 年报 1 季报 2 月报", type = "Integer"),
            @ApiImplicitParam(name = "season_type", value = "季节: 1 第一季节 2 第二季节 3 第三季节  4 第四季节", type = "Long"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<NegativeSumResponse> negativeGross(
            @RequestParam("date") String date,  @RequestParam("type") Integer type,
            @RequestParam(value = "report_type") Integer reportType,
            @RequestParam(value = "season_type", required = false) Long seasonType,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        return statisticsService.negativeGross(date, type, reportType, seasonType, productSortCode);
    }

    @GetMapping("/sale")
    @ApiOperation("销售统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "report_type", value = "报表类型: 0 年报 2 月报", type = "Integer"),
            @ApiImplicitParam(name = "data_type_code", value = "数据类型 0 经营数据,1 部门数据", type = "Integer"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性 1 A品，2 B品，3 C品，5 D品，6 其他", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<SaleSumResponse> saleInfo(
            @RequestParam("date") String date,  @RequestParam("type") Integer type,
            @RequestParam(value = "report_type") Integer reportType,
            @RequestParam(value = "data_type_code", required = false) Integer dataTypeCode,
            @RequestParam(value = "product_property_code", required = false) Integer productPropertyCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        SaleRequest saleRequest = new SaleRequest(date, type, reportType, dataTypeCode, productPropertyCode, productSortCode);
        return salesStatisticsService.saleInfo(saleRequest);
    }

    @GetMapping("/month/sale")
    @ApiOperation("月累计销售统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "data_type_code", value = "数据类型 0 经营数据,1 部门数据", type = "Integer"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性 1 A品，2 B品，3 C品，5 D品，6 其他", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<SaleSumResponse> monthSaleInfo(
            @RequestParam("date") String date,  @RequestParam("type") Integer type,
            @RequestParam(value = "data_type_code", required = false) Integer dataTypeCode,
            @RequestParam(value = "product_property_code", required = false) Integer productPropertyCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        SaleRequest saleRequest = new SaleRequest(date, type, dataTypeCode, productPropertyCode, productSortCode);
        return salesStatisticsService.monthSaleInfo(saleRequest);
    }

    @GetMapping("/category/promotion")
    @ApiOperation("品类促销统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<CategoryResponse> categoryPromotion(
            @RequestParam("date") String date,  @RequestParam("type") Integer type,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        SaleRequest saleRequest = new SaleRequest(date, type, productSortCode);
        return salesStatisticsService.categoryPromotion(saleRequest);
    }

    @GetMapping("/supplier/delivery/rate")
    @ApiOperation("供应商到货率统计-部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "report_type", value = "报表类型: 0 年报 2 月报", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<SupplierDeliveryResponse> supplierDelivery(@RequestParam("date") String date,
                                                                   @RequestParam("report_type") Integer reportType,
                                                                   @RequestParam("product_sort_code") String productSortCode) {
        SupplierRequest supplierRequest = new SupplierRequest(date, reportType, productSortCode);
        return supplierStatisticsService.supplierDelivery(supplierRequest);
    }

    @GetMapping("/supplier/retreat/rate")
    @ApiOperation("供应商退供率统计-部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "report_type", value = "报表类型: 0 年报 2 月报", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<SupplierReturnResponse> supplierRetreat(@RequestParam("date") String date,
                                                                @RequestParam("report_type") Integer reportType,
                                                                @RequestParam("product_sort_code") String productSortCode) {
        SupplierRequest supplierRequest = new SupplierRequest(date, reportType, productSortCode);
        return supplierStatisticsService.supplierRetreat(supplierRequest);
    }

    @GetMapping("/product/movable/pin")
    @ApiOperation("新品动销率统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<ProductMovableResponse> productMovable(@RequestParam("date") String date,
                                                             @RequestParam("type") Integer type,
                                                             @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        ProductRequest request = new ProductRequest(date, type, productSortCode);
        return productStatisticsService.productMovable(request);
    }

    @GetMapping("/stock/out")
    @ApiOperation("缺货统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<ProductStockOutResponse> productShortage(@RequestParam("date") String date,
                                                                 @RequestParam("type") Integer type,
                                                                 @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        ProductRequest request = new ProductRequest(date, type, productSortCode);
        return productStatisticsService.productStockOut(request);
    }

    @GetMapping("/inventory")
    @ApiOperation("库存统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "type", value = "组织类型: 0 公司 1 部门", type = "Integer"),
            @ApiImplicitParam(name = "stock_type", value = "库存类型 0 低库存 1 高库存门", type = "Integer"),
            @ApiImplicitParam(name = "report_type", value = "报表类型: 0 年报 1 季报 2 月报 3 周报", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse<InventoryStatisticsResponse> inventory(@RequestParam("date") String date,
                                                                  @RequestParam("type") Integer type,
                                                                  @RequestParam("stock_type") Integer stockType,
                                                                  @RequestParam("report_type") Integer reportType,
                                                                  @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        InventoryStatisticsRequest request = new InventoryStatisticsRequest(date, type, stockType, reportType, productSortCode);
        return inventoryStatisticsService.inventory(request);
    }

}
