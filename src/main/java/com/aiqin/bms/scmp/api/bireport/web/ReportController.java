package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.base.PageReportResData;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.service.ReportService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ch
 * @description 报表
 */
@RestController
@Api(tags = "报表接口")
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/search/supplier/arrival/rate")
    @ApiOperation("供应商到货率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "category_code", value = "品类编号", type = "String"),
            @ApiImplicitParam(name = "category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "begin_inbound_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_inbound_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<SupplierArrivalRateRespVo>> selectSupplierArrivalRate(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "begin_inbound_time", required = false) String beginInboundTime,
            @RequestParam(value = "finish_inbound_time", required = false) String finishInboundTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        SupplierArrivalRateReqVo supplierArrivalRateReqVo = new SupplierArrivalRateReqVo(supplierCode,supplierName,transportCenterCode,categoryCode,categoryName,beginInboundTime,finishInboundTime);
        supplierArrivalRateReqVo.setPageNo(pageNo);
        supplierArrivalRateReqVo.setPageNo(pageSize);
        return HttpResponse.success(reportService.selectSupplierArrivalRate(supplierArrivalRateReqVo));
    }

    @GetMapping("/search/goods/buy/sales")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<GoodsBuySalesRespVo>> selectGoodsBuySales(
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
            @RequestParam(value = "category_code_three", required = false) String categoryCodeThree,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        GoodsBuySalesReqVo goodsBuySalesReqVo = new GoodsBuySalesReqVo(beginInboundTime,finishInboundTime,supplierCode,supplierName,skuCode,skuName,transportCenterCode,transportCenterName,
                categoryName,categoryCode,productSortCode,productSortName,inboundDays,beginTurnoverDays,finishTurnoverDays,warehouseCode,warehouseName,categoryCodeOne,categoryCodeTwo,categoryCodeThree);
        goodsBuySalesReqVo.setPageNo(pageNo);
        goodsBuySalesReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectGoodsBuySales(goodsBuySalesReqVo));
    }

    @GetMapping("/search/gifts/buy/sales")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<GiftsBuySalesRespVo>> selectGiftsBuySales(
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
            @RequestParam(value = "category_code_three", required = false) String categoryCodeThree,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        GiftsBuySalesReqVo giftsBuySalesReqVo = new GiftsBuySalesReqVo(skuCode,skuName,transportCenterCode,transportCenterName,categoryName,categoryCode,inboundDays,productSortCode,productSortName,beginInboundTime,finishInboundTime,
                warehouseCode,warehouseName,beginTurnoverDays,finishTurnoverDays,categoryCodeOne,categoryCodeTwo,categoryCodeThree);
        giftsBuySalesReqVo.setPageNo(pageNo);
        giftsBuySalesReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectGiftsBuySales(giftsBuySalesReqVo));
    }

    @GetMapping("/search/supplier/return")
    @ApiOperation("供应商退货(退供)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_name", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<SupplierReturnRespVo>> selectSupplierReturn(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouse_name", required = false) String warehouseName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        SupplierReturnReqVo supplierReturnReqVo = new SupplierReturnReqVo(supplierCode,supplierName,transportCenterCode,transportCenterName,warehouseCode,warehouseName,beginRunTime,finishRunTime);
        supplierReturnReqVo.setPageNo(pageNo);
        supplierReturnReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectSupplierReturn(supplierReturnReqVo));
    }

    @GetMapping("/search/new/product/batch/moving/rate")
    @ApiOperation("新品批次动销率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "销售渠道code", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "销售渠道", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "product_category_name", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "batch_code", value = "批次号", type = "String"),
            @ApiImplicitParam(name = "begin_qun_maori_rate", value = "毛利率begin", type = "Double"),
            @ApiImplicitParam(name = "finish_qun_maori_rate", value = "毛利率finish", type = "Double"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<NewProductBatchMovingRateRespVo>> selectNewProductBatchMovingRate(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "batch_code", required = false) String batchCode,
            @RequestParam(value = "begin_qun_maori_rate", required = false) Double beginQunMaoriRate,
            @RequestParam(value = "finish_qun_maori_rate", required = false) Double finishQunMaoriRate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo = new NewProductBatchMovingRateReqVo(skuCode,skuName,priceChannelCode,priceChannelName,supplierCode,supplierName,productCategoryCode,productCategoryName,beginRunTime,finishRunTime,batchCode,beginQunMaoriRate,finishQunMaoriRate,
                productSortCode,productSortName);
        newProductBatchMovingRateReqVo.setPageNo(pageNo);
        newProductBatchMovingRateReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectNewProductBatchMovingRate(newProductBatchMovingRateReqVo));
    }

    @GetMapping("/search/store/repurchase/rate")
    @ApiOperation("门店复购率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province_name", value = "省区", type = "String"),
            @ApiImplicitParam(name = "city_name", value = "市", type = "String"),
            @ApiImplicitParam(name = "district_name", value = "区", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "product_category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "order_original_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original_name", value = "渠道名称", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<StoreRepurchaseRateRespVo>> selectStoreRepurchaseRate(
            @RequestParam(value = "province_name", required = false) String provinceName,
            @RequestParam(value = "city_name", required = false) String cityName,
            @RequestParam(value = "district_name", required = false) String districtName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "order_original_code", required = false) String orderOriginalCode,
            @RequestParam(value = "order_original_name", required = false) String orderOriginalName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        StoreRepurchaseRateReqVo storeRepurchaseRateReqVo = new StoreRepurchaseRateReqVo(provinceName,cityName,districtName,productCategoryCode,productCategoryName,orderOriginalCode,orderOriginalName,beginRunTime,finishRunTime);
        storeRepurchaseRateReqVo.setPageNo(pageNo);
        storeRepurchaseRateReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectStoreRepurchaseRate(storeRepurchaseRateReqVo));
    }

    @GetMapping("/search/negative/margin")
    @ApiOperation("负毛利")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "product_category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "product_brand_name", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "order_original_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original_name", value = "渠道名称", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<NegativeMarginRespVo>> selectNegativeMargin(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
            @RequestParam(value = "product_brand_name", required = false) String productBrandName,
            @RequestParam(value = "order_original_code", required = false) String orderOriginalCode,
            @RequestParam(value = "order_original_name", required = false) String orderOriginalName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        NegativeMarginReqVo negativeMarginReqVo = new NegativeMarginReqVo(skuCode,skuName,productCategoryCode,productCategoryName,productBrandCode,productBrandName,orderOriginalCode,orderOriginalName,beginRunTime,finishRunTime,productSortCode,productSortName);
        negativeMarginReqVo.setPageNo(pageNo);
        negativeMarginReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectNegativeMargin(negativeMarginReqVo));
    }

    @GetMapping("/search/suggest/replenishment")
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
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<SuggestReplenishmentRespVo>> selectSuggestReplenishment(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
            @RequestParam(value = "product_brand_name", required = false) String productBrandName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        SuggestReplenishmentReqVo suggestReplenishmentReqVo = new SuggestReplenishmentReqVo(skuCode,skuName,productCategoryCode,productCategoryName,productBrandCode,productBrandName,transportCenterCode,transportCenterName,beginRunTime,finishRunTime);
        suggestReplenishmentReqVo.setPageNo(pageNo);
        suggestReplenishmentReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectSuggestReplenishment(suggestReplenishmentReqVo));
    }

    @GetMapping("/search/low/inventory")
    @ApiOperation("低库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<LowInventoryRespVo>> selectLowInventory(
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginRunTime,finishRunTime,productSortCode,
                productSortName);
        highLowInventoryReqVo.setPageNo(pageNo);
        highLowInventoryReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectLowInventory(highLowInventoryReqVo));
    }

    @GetMapping("/search/high/inventory")
    @ApiOperation("高库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<HighInventoryRespVo>> selectHighInventory(
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginRunTime,finishRunTime,productSortCode,
                productSortName);
        highLowInventoryReqVo.setPageNo(pageNo);
        highLowInventoryReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectHighInventory(highLowInventoryReqVo));
    }

    @GetMapping("/search/brand/sale")
    @ApiOperation("品牌促销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "month", value = "月份", type = "Integer"),
            @ApiImplicitParam(name = "channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "department_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "department_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<CategorySaleRespVo>> selectBrandSale(
            @RequestParam(value = "month", required = false) int month,
            @RequestParam(value = "channel_code", required = false) String channelCode,
            @RequestParam(value = "channel_name", required = false) String channelName,
            @RequestParam(value = "department_code", required = false) String departmentCode,
            @RequestParam(value = "department_name", required = false) String departmentName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        CategorySaleReqVo brandSaleReqVo = new CategorySaleReqVo(month,channelCode,channelName,departmentCode,departmentName);
        brandSaleReqVo.setPageNo(pageNo);
        brandSaleReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectBrandSale(brandSaleReqVo));
    }

    @GetMapping("/search/category/sale")
    @ApiOperation("品类促销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "month", value = "月份", type = "int"),
            @ApiImplicitParam(name = "channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "department_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "department_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<CategorySaleRespVo>> selectCategorySale(
            @RequestParam(value = "month", required = false) int month,
            @RequestParam(value = "channel_code", required = false) String channelCode,
            @RequestParam(value = "channel_name", required = false) String channelName,
            @RequestParam(value = "department_code", required = false) String departmentCode,
            @RequestParam(value = "department_name", required = false) String departmentName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        CategorySaleReqVo categorySaleReqVo = new CategorySaleReqVo(month,channelCode,channelName,departmentCode,departmentName);
        categorySaleReqVo.setPageNo(pageNo);
        categorySaleReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectCategorySale(categorySaleReqVo));
    }
    
    @GetMapping("/search/big/effect")
    @ApiOperation("大效期")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "production_date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌编码", type = "String"),
            @ApiImplicitParam(name = "product_brand_name", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "supply_unit_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supply_unit_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "product_category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_name", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "big_effect_period_warn_day", value = "状态", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<BigEffectRespVo>> selectBigEffect(
            @RequestParam(value = "production_date", required = false) String productionDate,
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
            @RequestParam(value = "product_brand_name", required = false) String productBrandName,
            @RequestParam(value = "supply_unit_code", required = false) String supplyUnitCode,
            @RequestParam(value = "supply_unit_name", required = false) String supplyUnitName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouse_name", required = false) String warehouseName,
            @RequestParam(value = "big_effect_period_warn_day", required = false) String bigEffectPeriodWarnDay,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        BigEffectReqVo bigEffectReqVo = new BigEffectReqVo(productionDate,skuCode,skuName,productBrandCode,productBrandName,supplyUnitCode,supplyUnitName,productCategoryCode,productCategoryName,procurementSectionCode,procurementSectionName,transportCenterCode,transportCenterName,warehouseCode,warehouseName,bigEffectPeriodWarnDay);
        bigEffectReqVo.setPageNo(pageNo);
        bigEffectReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectBigEffect(bigEffectReqVo));
    }

    @GetMapping("/search/monthly/sales")
    @ApiOperation("月销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<MonthlySalesRespVo>> selectMonthlySales(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthlySalesReqVo monthlySalesReqVo = new MonthlySalesReqVo(productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,beginRunTime,finishRunTime);
        monthlySalesReqVo.setPageNo(pageNo);
        monthlySalesReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthlySales(monthlySalesReqVo));
    }

    @GetMapping("/search/month/gross/margin")
    @ApiOperation("月毛利率情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<MonthlyGrossMarginRespVo>> selectMonthlyGrossMargin(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthlySalesReqVo monthlyGrossMarginReqVo = new MonthlySalesReqVo(productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,beginRunTime,finishRunTime);
        monthlyGrossMarginReqVo.setPageNo(pageNo);
        monthlyGrossMarginReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthlyGrossMargin(monthlyGrossMarginReqVo));
    }

    @GetMapping("/search/month/sales/achievement")
    @ApiOperation("月销售达成情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "category_type_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_type_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<MonthSalesAchievementRespVo>> selectMonthSalesAchievement(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "category_type_code", required = false) String categoryTypeCode,
            @RequestParam(value = "category_type_name", required = false) String categoryTypeName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthSalesAchievementReqVo monthSalesAchievementReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,beginRunTime,finishRunTime,categoryTypeCode,categoryTypeName);
        monthSalesAchievementReqVo.setPageNo(pageNo);
        monthSalesAchievementReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthSalesAchievement(monthSalesAchievementReqVo));
    }

    @GetMapping("/search/month/cumulative/brand/sales")
    @ApiOperation("月累计品类销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "category_type_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_type_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<MonthCumulativeBrandSalesRespVo>> selectMonthCumulativeBrandSales(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "category_type_code", required = false) String categoryTypeCode,
            @RequestParam(value = "category_type_name", required = false) String categoryTypeName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,beginRunTime,finishRunTime,categoryTypeCode,categoryTypeName);
        monthCumulativeBrandSalesReqVo.setPageNo(pageNo);
        monthCumulativeBrandSalesReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthCumulativeBrandSales(monthCumulativeBrandSalesReqVo));
    }

    @GetMapping("/search/month/cumulative/gross/profit/margin")
    @ApiOperation("月累计品类毛利率情况(带分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "begin_run_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_run_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "category_type_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "category_type_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageResData<MonthCumulativeGrossProfitMarginRespVo>> selectMonthCumulativeGrossProfitMargin(
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "begin_run_time", required = false) String beginRunTime,
            @RequestParam(value = "finish_run_time", required = false) String finishRunTime,
            @RequestParam(value = "category_type_code", required = false) String categoryTypeCode,
            @RequestParam(value = "category_type_name", required = false) String categoryTypeName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,beginRunTime,finishRunTime,categoryTypeCode,categoryTypeName);
        monthCumulativeGrossProfitMarginReqVo.setPageNo(pageNo);
        monthCumulativeGrossProfitMarginReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthCumulativeGrossProfitMargin(monthCumulativeGrossProfitMarginReqVo));
    }

    @GetMapping("/search/store/type")
    @ApiOperation("查询所有门店类型")
    public HttpResponse<List<MonthlySalesRespVo>> selectStoreType(){
        return HttpResponse.success(reportService.selectStoreType());
    }

    @GetMapping("/search/data/style")
    @ApiOperation("查询所有数据类型")
    public HttpResponse<List<MonthlySalesRespVo>> selectDataStyle(){
        return HttpResponse.success(reportService.selectDataStyle());
    }

    @GetMapping("/search/one/category")
    @ApiOperation("查询所有一级品类")
    public HttpResponse<List<String>> selectAllOneCategory(){
        return HttpResponse.success(reportService.selectAllOneCategory());
    }

}
