package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.bireport.domain.BiGoodsSalesReport;
import com.aiqin.bms.scmp.api.bireport.domain.request.ProductAndStockRequest;
import com.aiqin.bms.scmp.api.bireport.domain.response.ProductAndStockResponse;
import com.aiqin.bms.scmp.api.bireport.service.ReportFormService;
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
 * @create: 2019-08-12
 **/
@Api(tags = "查询报表")
@RequestMapping("/report/form")
@RestController
public class ReportFormController {

    @Resource
    private ReportFormService reportFormService;

    @GetMapping("/product/sale")
    @ApiOperation("商品销售查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
            @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "lv1", value = "一级品类编码", type = "String"),
            @ApiImplicitParam(name = "lv2", value = "二级品类编码", type = "String"),
            @ApiImplicitParam(name = "lv3", value = "三级品类编码", type = "String"),
            @ApiImplicitParam(name = "lv4", value = "四级品类编码", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商名称", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
            @ApiImplicitParam(name = "is_page", value = "是否分页", type = "Integer")})
    public HttpResponse<BiGoodsSalesReport> applyList(@RequestParam(value = "begin_time", required = false) String beginTime,
                                                      @RequestParam(value = "finish_time", required = false) String finishTime,
                                                      @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                                      @RequestParam(value = "product_sort_code", required = false) String productSortCode,
                                                      @RequestParam(value = "lv1", required = false) String lv1,
                                                      @RequestParam(value = "lv2", required = false) String lv2,
                                                      @RequestParam(value = "lv3", required = false) String lv3,
                                                      @RequestParam(value = "lv4", required = false) String lv4,
                                                      @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
                                                      @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                      @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                      @RequestParam(value = "sku_code", required = false) String skuCode,
                                                      @RequestParam(value = "sku_name", required = false) String skuName,
                                                      @RequestParam(value = "supplier_name", required = false) String supplierName,
                                                      @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                      @RequestParam(value = "page_size", required = false) Integer pageSize,
                                                      @RequestParam(value = "is_page", required = false) Integer isPage) {
        ProductAndStockRequest productAndStock = new ProductAndStockRequest(beginTime, finishTime, skuCode, skuName,
                supplierCode, productSortCode, productBrandCode, lv1, lv2, lv3, lv4, transportCenterCode, warehouseCode, supplierName, isPage);
        productAndStock.setPageSize(pageSize);
        productAndStock.setPageNo(pageNo);
        return reportFormService.productSaleInfo(productAndStock);
    }

    @GetMapping("/stock/turnover")
    @ApiOperation("库存周转查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
            @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "turnover_days_min", value = "库存周转天数最小值", type = "Integer"),
            @ApiImplicitParam(name = "turnover_days_max", value = "库存周转天数最大值", type = "Integer"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
            @ApiImplicitParam(name = "is_page", value = "是否分页", type = "Integer") })
    public HttpResponse<ProductAndStockResponse> stockTurnover(@RequestParam(value = "begin_time", required = false) String beginTime,
                                                               @RequestParam(value = "finish_time", required = false) String finishTime,
                                                               @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                                               @RequestParam(value = "product_sort_code", required = false) String productSortCode,
                                                               @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
                                                               @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                               @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                               @RequestParam(value = "sku_code", required = false) String skuCode,
                                                               @RequestParam(value = "sku_name", required = false) String skuName,
                                                               @RequestParam(value = "turnover_days_min", required = false) Integer turnoverDaysMin,
                                                               @RequestParam(value = "turnover_days_max", required = false) Integer turnoverDaysMax,
                                                               @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                               @RequestParam(value = "page_size", required = false) Integer pageSize,
                                                               @RequestParam(value = "is_page", required = false) Integer isPage){
        ProductAndStockRequest productAndStock = new ProductAndStockRequest(beginTime, finishTime, skuCode, skuName,
                supplierCode, productSortCode, productBrandCode, transportCenterCode, warehouseCode,
                turnoverDaysMin, turnoverDaysMax, isPage);
        productAndStock.setPageSize(pageSize);
        productAndStock.setPageNo(pageNo);
        return reportFormService.stockTurnover(productAndStock);
    }

    @GetMapping("/stock/way/turnover")
    @ApiOperation("库存在途周转查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "begin_time", value = "开始时间", type = "String"),
            @ApiImplicitParam(name = "finish_time", value = "结束时间", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "turnover_days_min", value = "库存周转天数最小值", type = "Integer"),
            @ApiImplicitParam(name = "turnover_days_max", value = "库存周转天数最大值", type = "Integer"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
            @ApiImplicitParam(name = "is_page", value = "是否分页", type = "Integer") })
    public HttpResponse<ProductAndStockResponse> stockWayTurnover(@RequestParam(value = "begin_time", required = false) String beginTime,
                                                                @RequestParam(value = "finish_time", required = false) String finishTime,
                                                                @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                                                @RequestParam(value = "product_sort_code", required = false) String productSortCode,
                                                                @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
                                                                @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                                @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                                @RequestParam(value = "sku_code", required = false) String skuCode,
                                                                @RequestParam(value = "sku_name", required = false) String skuName,
                                                                @RequestParam(value = "turnover_days_min", required = false) Integer turnoverDaysMin,
                                                                @RequestParam(value = "turnover_days_max", required = false) Integer turnoverDaysMax,
                                                                @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                                @RequestParam(value = "page_size", required = false) Integer pageSize,
                                                                @RequestParam(value = "is_page", required = false) Integer isPage){
        ProductAndStockRequest productAndStock = new ProductAndStockRequest(beginTime, finishTime, skuCode, skuName,
                supplierCode, productSortCode, productBrandCode, transportCenterCode, warehouseCode,
                turnoverDaysMin, turnoverDaysMax, isPage);
        productAndStock.setPageSize(pageSize);
        productAndStock.setPageNo(pageNo);
        return reportFormService.stockWayTurnover(productAndStock);
    }

}
