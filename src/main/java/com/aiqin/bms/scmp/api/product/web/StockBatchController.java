package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatchFlow;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockBatchSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchDetailResponse;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchResponse;
import com.aiqin.bms.scmp.api.product.service.StockBatchService;
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
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-03-26 17:16
 **/
@RestController
@Api(tags = "批次库存")
@RequestMapping("/stock/batch")
public class StockBatchController {

    @Resource
    private StockBatchService stockBatchService;

    @GetMapping("/list")
    @ApiOperation(value = "批次库存管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transport_center_code", value = "物流中心", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "config_status", value = "状态 0:再用 1:停止进货 2:停止配送 3:停止销售", type = "Integer"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性", type = "String"),
            @ApiImplicitParam(name = "product_property_name", value = "商品属性名称", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "sku品类编码", type = "String"),
            @ApiImplicitParam(name = "product_category_name", value = "sku品类名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "sku品牌编码", type = "String"),
            @ApiImplicitParam(name = "product_brand_name", value = "sku品牌名称", type = "String"),
            @ApiImplicitParam(name = "purchase_group_code", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "batch_code", value = "批次号", type = "String"),
            @ApiImplicitParam(name = "begin_inventory_count", value = "开始库存数量", type = "Long"),
            @ApiImplicitParam(name = "finish_inventory_count", value = "结束库存数量", type = "Long"),
            @ApiImplicitParam(name = "begin_available_count", value = "开始可用库存数", type = "Long"),
            @ApiImplicitParam(name = "finish_available_count", value = "结束可用库存数", type = "Long"),
            @ApiImplicitParam(name = "product_date", value = "生产日期", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<StockBatchResponse>> stockBatchList(@RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
                                                                        @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
                                                                        @RequestParam(value = "sku_code", required = false) String skuCode,
                                                                        @RequestParam(value = "sku_name", required = false) String skuName,
                                                                        @RequestParam(value = "config_status", required = false) Integer configStatus,
                                                                        @RequestParam(value = "product_property_code", required = false) String productPropertyCode,
                                                                        @RequestParam(value = "product_property_name", required = false) String productPropertyName,
                                                                        @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
                                                                        @RequestParam(value = "product_category_name", required = false) String productCategoryName,
                                                                        @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
                                                                        @RequestParam(value = "product_brand_name", required = false) String productBrandName,
                                                                        @RequestParam(value = "purchase_group_code", required = false) String purchaseGroupCode,
                                                                        @RequestParam(value = "batch_code", required = false) String batchCode,
                                                                        @RequestParam(value = "begin_inventory_count", required = false) Long beginInventoryCount,
                                                                        @RequestParam(value = "finish_inventory_count", required = false) Long finishInventoryCount,
                                                                        @RequestParam(value = "begin_available_count", required = false) Long beginAvailableCount,
                                                                        @RequestParam(value = "finish_available_count", required = false) Long finishAvailableCount,
                                                                        @RequestParam(value = "product_date", required = false) String productDate,
                                                                        @RequestParam(value = "supplier_code", required = false) String supplierCode,
                                                                        @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                                        @RequestParam(value = "page_size", required = false) Integer pageSize) {
        QueryStockBatchSkuReqVo request = new QueryStockBatchSkuReqVo(transportCenterCode, warehouseCode, skuCode, skuName,
                configStatus, productPropertyCode, productPropertyName, productCategoryCode, productCategoryName, productBrandCode ,
                productBrandName, purchaseGroupCode, batchCode, beginInventoryCount, finishInventoryCount, beginAvailableCount,
                finishAvailableCount, productDate, supplierCode);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return  stockBatchService.stockBatchList(request);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "批次库存管理详情")
    public HttpResponse<StockBatchDetailResponse> stockBatchDetail(@RequestParam("stock_batch_code") String stockBatchCode){
        return stockBatchService.stockBatchDetail(stockBatchCode);
    }

    @GetMapping("/flow")
    @ApiOperation(value = "批次库存管理流水")
    @ApiImplicitParams({ @ApiImplicitParam(name = "stock_batch_code", value = "批次库存单号", type = "String"),
                         @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
                         @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<StockBatchFlow>> stockBatchFlow(@RequestParam("stock_batch_code") String stockBatchCode,
                                                                    @RequestParam("page_no") Integer pageNo,
                                                                    @RequestParam("page_size") Integer pageSize){
        QueryStockBatchSkuReqVo request = new QueryStockBatchSkuReqVo();
        request.setStockBatchCode(stockBatchCode);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return stockBatchService.stockBatchFlow(request);
    }

    @GetMapping("/sku")
    @ApiOperation(value = "查询sku对应的批次管理信息")
    public HttpResponse<List<StockBatch>>  stockBatchSku(@RequestParam("sku_code") String skuCode){
        return stockBatchService.stockBatchSku(skuCode);
    }

}
