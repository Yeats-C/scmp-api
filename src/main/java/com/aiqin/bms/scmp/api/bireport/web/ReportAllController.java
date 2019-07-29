package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.base.PageImportResData;
import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.service.ReportAllService;
import com.aiqin.bms.scmp.api.bireport.service.ReportService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author ch
 * @description 报表
 */
@RestController
@Api(tags = "报表接口")
@RequestMapping("/report")
@Slf4j
public class ReportAllController {

    @Autowired
    private ReportAllService reportAllService;


    @GetMapping("/search/supplier/arrival/rate/all")
    @ApiOperation("供应商到货率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编号", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "begin_inbound_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_inbound_time", value = "时间finish", type = "String"),
    })
    public HttpResponse<PageImportResData<SupplierArrivalRateRespVo>> selectSupplierArrivalRate(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "begin_inbound_time", required = false) String beginInboundTime,
            @RequestParam(value = "finish_inbound_time", required = false) String finishInboundTime){
        SupplierArrivalRateReqVo supplierArrivalRateReqVo = new SupplierArrivalRateReqVo(supplierCode,supplierName,transportCenterCode,categoryCode,categoryName,beginInboundTime,finishInboundTime);
        return HttpResponse.success(reportAllService.selectSupplierArrivalRate(supplierArrivalRateReqVo));
    }

    @GetMapping("/search/goods/buy/sales/all")
    @ApiOperation("批次商品进销存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "begin_inbound_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_inbound_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "inbound_days", value = "库存日期", type = "Integer"),
            @ApiImplicitParam(name = "begin_turnover_days", value = "周转天数begin", type = "Integer"),
            @ApiImplicitParam(name = "finish_turnover_days", value = "周转天数finish", type = "Integer"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_name", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "category_code_one", value = "一级品类", type = "String"),
            @ApiImplicitParam(name = "category_code_two", value = "二级品类", type = "String"),
            @ApiImplicitParam(name = "category_code_three", value = "三级品类", type = "String"),
    })
    public HttpResponse<List<GoodsBuySalesRespVo>> selectGoodsBuySales(
            @RequestParam(value = "begin_inbound_time", required = false) String beginInboundTime,
            @RequestParam(value = "finish_inbound_time", required = false) String finishInboundTime,
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName,
            @RequestParam(value = "inbound_days", required = false) Integer inboundDays,
            @RequestParam(value = "begin_turnover_days", required = false) Integer beginTurnoverDays,
            @RequestParam(value = "finish_turnover_days", required = false) Integer finishTurnoverDays,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouse_name", required = false) String warehouseName,
            @RequestParam(value = "category_code_one", required = false) String categoryCodeOne,
            @RequestParam(value = "category_code_two", required = false) String categoryCodeTwo,
            @RequestParam(value = "category_code_three", required = false) String categoryCodeThree){
        GoodsBuySalesReqVo goodsBuySalesReqVo = new GoodsBuySalesReqVo(beginInboundTime,finishInboundTime,supplierCode,supplierName,skuCode,skuName,transportCenterCode,transportCenterName,
                categoryName,categoryCode,productSortCode,productSortName,inboundDays,beginTurnoverDays,finishTurnoverDays,warehouseCode,warehouseName,categoryCodeOne,categoryCodeTwo,categoryCodeThree);
        return HttpResponse.success(reportAllService.selectGoodsBuySales(goodsBuySalesReqVo));
    }

    @GetMapping("/search/gifts/buy/sales/all")
    @ApiOperation("赠品进销存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "inbound_days", value = "库存日期", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "begin_inbound_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_inbound_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_name", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "begin_turnover_days", value = "周转天数begin", type = "Integer"),
            @ApiImplicitParam(name = "finish_turnover_days", value = "周转天数finish", type = "Integer"),
            @ApiImplicitParam(name = "category_code_one", value = "一级品类", type = "String"),
            @ApiImplicitParam(name = "category_code_two", value = "二级品类", type = "String"),
            @ApiImplicitParam(name = "category_code_three", value = "三级品类", type = "String"),
    })
    public HttpResponse<List<GiftsBuySalesRespVo>> selectGiftsBuySales(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "inbound_days", required = false) Integer inboundDays,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName,
            @RequestParam(value = "begin_inbound_time", required = false) String beginInboundTime,
            @RequestParam(value = "finish_inbound_time", required = false) String finishInboundTime,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouse_name", required = false) String warehouseName,
            @RequestParam(value = "begin_turnover_days", required = false) Integer beginTurnoverDays,
            @RequestParam(value = "finish_turnover_days", required = false) Integer finishTurnoverDays,
            @RequestParam(value = "category_code_one", required = false) String categoryCodeOne,
            @RequestParam(value = "category_code_two", required = false) String categoryCodeTwo,
            @RequestParam(value = "category_code_three", required = false) String categoryCodeThree){
        GiftsBuySalesReqVo giftsBuySalesReqVo = new GiftsBuySalesReqVo(skuCode,skuName,transportCenterCode,transportCenterName,categoryName,categoryCode,inboundDays,productSortCode,productSortName,beginInboundTime,finishInboundTime,
                warehouseCode,warehouseName,beginTurnoverDays,finishTurnoverDays,categoryCodeOne,categoryCodeTwo,categoryCodeThree);
        return HttpResponse.success(reportAllService.selectGiftsBuySales(giftsBuySalesReqVo));
    }

    @GetMapping("/search/supplier/return/all")
    @ApiOperation("供应商退货(退供)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_name", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "begin_out_stock_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_out_stock_time", value = "时间finish", type = "String"),
    })
    public HttpResponse<PageImportResData<SupplierReturnRespVo>> selectSupplierReturn(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouse_name", required = false) String warehouseName,
            @RequestParam(value = "begin_out_stock_time", required = false) String beginOutStockTime,
            @RequestParam(value = "finish_out_stock_time", required = false) String finishOutStockTime){
        SupplierReturnReqVo supplierReturnReqVo = new SupplierReturnReqVo(supplierCode,supplierName,transportCenterCode,transportCenterName,warehouseCode,warehouseName,beginOutStockTime,finishOutStockTime);
        return HttpResponse.success(reportAllService.selectSupplierReturn(supplierReturnReqVo));
    }

    @GetMapping("/search/new/product/batch/moving/rate/all")
    @ApiOperation("新品批次动销率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "销售渠道code", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "销售渠道", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "begin_inbound_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_inbound_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "batch_code", value = "批次号", type = "String"),
            @ApiImplicitParam(name = "begin_channel_maori_rate", value = "毛利率begin", type = "Double"),
            @ApiImplicitParam(name = "finish_channel_maori_rate", value = "毛利率finish", type = "Double"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
    })
    public HttpResponse<List<NewProductBatchMovingRateRespVo>> selectNewProductBatchMovingRate(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "begin_inbound_time", required = false) String beginInboundTime,
            @RequestParam(value = "finish_inbound_time", required = false) String finishInboundTime,
            @RequestParam(value = "batch_code", required = false) String batchCode,
            @RequestParam(value = "begin_channel_maori_rate", required = false) Double beginChannelMaoriRate,
            @RequestParam(value = "finish_channel_maori_rate", required = false) Double finishChannelMaoriRate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName){
        NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo = new NewProductBatchMovingRateReqVo(skuCode,skuName,orderCode,orderOriginal,supplierCode,supplierName,categoryCode,categoryName,beginInboundTime,finishInboundTime,batchCode,beginChannelMaoriRate,finishChannelMaoriRate,
                productSortCode,productSortName);
        return HttpResponse.success(reportAllService.selectNewProductBatchMovingRate(newProductBatchMovingRateReqVo));
    }

    @GetMapping("/search/store/repurchase/rate/all")
    @ApiOperation("门店复购率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province_name", value = "省区", type = "String"),
            @ApiImplicitParam(name = "city_name", value = "市", type = "String"),
            @ApiImplicitParam(name = "district_name", value = "区", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道名称", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
    })
    public HttpResponse<List<StoreRepurchaseRateRespVo>> selectStoreRepurchaseRate(
            @RequestParam(value = "province_name", required = false) String provinceName,
            @RequestParam(value = "city_name", required = false) String cityName,
            @RequestParam(value = "district_name", required = false) String districtName,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime){
        StoreRepurchaseRateReqVo storeRepurchaseRateReqVo = new StoreRepurchaseRateReqVo(provinceName,cityName,districtName,categoryCode,categoryName,orderCode,orderOriginal,beginCreateTime,finishCreateTime);
        return HttpResponse.success(reportAllService.selectStoreRepurchaseRate(storeRepurchaseRateReqVo));
    }

    @GetMapping("/search/negative/margin/all")
    @ApiOperation("负毛利")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "product_brand_name", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道名称", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
    })
    public HttpResponse<PageImportResData<NegativeMarginRespVo>> selectNegativeMargin(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
            @RequestParam(value = "product_brand_name", required = false) String productBrandName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName){
        NegativeMarginReqVo negativeMarginReqVo = new NegativeMarginReqVo(skuCode,skuName,categoryCode,categoryName,productBrandCode,productBrandName,orderCode,orderOriginal,beginCreateTime,finishCreateTime,productSortCode,productSortName);
        return HttpResponse.success(reportAllService.selectNegativeMargin(negativeMarginReqVo));
    }

    @GetMapping("/search/suggest/replenishment/all")
    @ApiOperation("建议补货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "product_category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "product_brand_name", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
    })
    public HttpResponse<List<SuggestReplenishmentRespVo>> selectSuggestReplenishment(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
            @RequestParam(value = "product_brand_name", required = false) String productBrandName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime){
        SuggestReplenishmentReqVo suggestReplenishmentReqVo = new SuggestReplenishmentReqVo(skuCode,skuName,productCategoryCode,productCategoryName,productBrandCode,productBrandName,transportCenterCode,transportCenterName,beginCreateTime,finishCreateTime);
        return HttpResponse.success(reportAllService.selectSuggestReplenishment(suggestReplenishmentReqVo));
    }

    @GetMapping("/search/low/inventory/all")
    @ApiOperation("低库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
    })
    public HttpResponse<List<LowInventoryRespVo>> selectLowInventory(
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName){
        HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginCreateTime,finishCreateTime,productSortCode,
                productSortName);
        return HttpResponse.success(reportAllService.selectLowInventory(highLowInventoryReqVo));
    }

    @GetMapping("/search/high/inventory/all")
    @ApiOperation("高库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
    })
    public HttpResponse<List<HighInventoryRespVo>> selectHighInventory(
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName){
        HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginCreateTime,finishCreateTime,productSortCode,
                productSortName);
        return HttpResponse.success(reportAllService.selectHighInventory(highLowInventoryReqVo));
    }

    @GetMapping("/search/brand/sale/all")
    @ApiOperation("品牌促销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "create_time", value = "日期", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "department_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "department_name", value = "所属部门", type = "String"),
    })
    public HttpResponse<PageImportResData<BrandSaleRespVo>> selectBrandSale(
            @RequestParam(value = "create_time", required = false) String createTime,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "department_code", required = false) String departmentCode,
            @RequestParam(value = "department_name", required = false) String departmentName){
        CategorySaleReqVo brandSaleReqVo = new CategorySaleReqVo(createTime,orderCode,orderOriginal,departmentCode,departmentName);
        return HttpResponse.success(reportAllService.selectBrandSale(brandSaleReqVo));
    }

    @GetMapping("/search/category/sale/all")
    @ApiOperation("品类促销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "create_time", value = "日期", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "department_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "department_name", value = "所属部门", type = "String"),
    })
    public HttpResponse<PageImportResData<CategorySaleRespVo>> selectCategorySale(
            @RequestParam(value = "create_time", required = false) String createTime,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "department_code", required = false) String departmentCode,
            @RequestParam(value = "department_name", required = false) String departmentName){
        CategorySaleReqVo categorySaleReqVo = new CategorySaleReqVo(createTime,orderCode,orderOriginal,departmentCode,departmentName);
        return HttpResponse.success(reportAllService.selectCategorySale(categorySaleReqVo));
    }
    
    @GetMapping("/search/big/effect/all")
    @ApiOperation("大效期")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "create_time", value = "日期", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "product_brand_name", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "supply_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supply_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_name", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "big_effect_period_warn_day", value = "状态", type = "String"),
    })
    public HttpResponse<List<BigEffectRespVo>> selectBigEffect(
            @RequestParam(value = "create_time", required = false) String createTime,
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
            @RequestParam(value = "product_brand_name", required = false) String productBrandName,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "supply_code", required = false) String supplyCode,
            @RequestParam(value = "supply_name", required = false) String supplyName,
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouse_name", required = false) String warehouseName,
            @RequestParam(value = "big_effect_period_warn_day", required = false) String bigEffectPeriodWarnDay){
        BigEffectReqVo bigEffectReqVo = new BigEffectReqVo(createTime,skuCode,skuName,productBrandCode,productBrandName,categoryCode,categoryName,supplyCode,supplyName,procurementSectionCode,procurementSectionName,transportCenterCode,transportCenterName,warehouseCode,warehouseName,bigEffectPeriodWarnDay);
        return HttpResponse.success(reportAllService.selectBigEffect(bigEffectReqVo));
    }

    @GetMapping("/search/monthly/sales/all")
    @ApiOperation("月销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_type_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_type", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
    })
    public HttpResponse<List<MonthlySalesRespVo>> selectMonthlySales(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type", required = false) String storeType,
            @RequestParam(value = "data_type_code", required = false) String dataTypeCode,
            @RequestParam(value = "data_type", required = false) String dataType,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime){
        MonthlySalesReqVo monthlySalesReqVo = new MonthlySalesReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime);
        return HttpResponse.success(reportAllService.selectMonthlySales(monthlySalesReqVo));
    }

    @GetMapping("/search/month/gross/margin/all")
    @ApiOperation("月毛利率情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_type_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_type", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
    })
    public HttpResponse<List<MonthlyGrossMarginRespVo>> selectMonthlyGrossMargin(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type", required = false) String storeType,
            @RequestParam(value = "data_type_code", required = false) String dataTypeCode,
            @RequestParam(value = "data_type", required = false) String dataType,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime){
        MonthlySalesReqVo monthlyGrossMarginReqVo = new MonthlySalesReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime);
        return HttpResponse.success(reportAllService.selectMonthlyGrossMargin(monthlyGrossMarginReqVo));
    }

    @GetMapping("/search/month/sales/achievement/all")
    @ApiOperation("月销售达成情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_type_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_type", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
    })
    public HttpResponse<List<MonthSalesAchievementRespVo>> selectMonthSalesAchievement(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type", required = false) String storeType,
            @RequestParam(value = "data_type_code", required = false) String dataTypeCode,
            @RequestParam(value = "data_type", required = false) String dataType,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName){
        MonthSalesAchievementReqVo monthSalesAchievementReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime,categoryCode,categoryName);
        return HttpResponse.success(reportAllService.selectMonthSalesAchievement(monthSalesAchievementReqVo));
    }

    @GetMapping("/search/month/cumulative/brand/sales/all")
    @ApiOperation("月累计品类销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_type_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_type", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
    })
    public HttpResponse<List<MonthCumulativeBrandSalesRespVo>> selectMonthCumulativeBrandSales(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type", required = false) String storeType,
            @RequestParam(value = "data_type_code", required = false) String dataTypeCode,
            @RequestParam(value = "data_type", required = false) String dataType,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName){
        MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime,categoryCode,categoryName);
        return HttpResponse.success(reportAllService.selectMonthCumulativeBrandSales(monthCumulativeBrandSalesReqVo));
    }

    @GetMapping("/search/month/cumulative/gross/profit/margin/all")
    @ApiOperation("月累计品类毛利率情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_type_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_type", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
    })
    public HttpResponse<List<MonthCumulativeGrossProfitMarginRespVo>> selectMonthCumulativeGrossProfitMargin(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type", required = false) String storeType,
            @RequestParam(value = "data_type_code", required = false) String dataTypeCode,
            @RequestParam(value = "data_type", required = false) String dataType,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName){
        MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime,categoryCode,categoryName);
        return HttpResponse.success(reportAllService.selectMonthCumulativeGrossProfitMargin(monthCumulativeGrossProfitMarginReqVo));
    }

}
