package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.base.PageReportResData;
import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.service.ReportService;
import com.aiqin.bms.scmp.api.purchase.web.FileRecordController;
import com.aiqin.bms.scmp.api.util.ExportExcelReportHigh;
import com.aiqin.bms.scmp.api.util.ExportExcelReportLow;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author ch
 * @description 报表
 */
@CrossOrigin
@RestController
@Api(tags = "报表接口")
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/search/supplier/arrival/rate")
    @ApiOperation("供应商到货率(带分页)")
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
        supplierArrivalRateReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectSupplierArrivalRate(supplierArrivalRateReqVo));
    }

    @GetMapping("/search/goods/buy/sales")
    @ApiOperation("批次商品进销存(带分页)")
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
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
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
    @ApiOperation("赠品进销存(带分页)")
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
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
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
    @ApiOperation("供应商退货(退供(带分页))")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouse_name", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "begin_out_stock_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_out_stock_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<SupplierReturnRespVo>> selectSupplierReturn(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouse_name", required = false) String warehouseName,
            @RequestParam(value = "begin_out_stock_time", required = false) String beginOutStockTime,
            @RequestParam(value = "finish_out_stock_time", required = false) String finishOutStockTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        SupplierReturnReqVo supplierReturnReqVo = new SupplierReturnReqVo(supplierCode,supplierName,transportCenterCode,transportCenterName,warehouseCode,warehouseName,beginOutStockTime,finishOutStockTime);
        supplierReturnReqVo.setPageNo(pageNo);
        supplierReturnReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectSupplierReturn(supplierReturnReqVo));
    }

    @GetMapping("/search/new/product/batch/moving/rate")
    @ApiOperation("新品批次动销率(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<NewProductBatchMovingRateRespVo>> selectNewProductBatchMovingRate(
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
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo = new NewProductBatchMovingRateReqVo(skuCode,skuName,orderCode,orderOriginal,supplierCode,supplierName,categoryCode,categoryName,beginInboundTime,finishInboundTime,batchCode,beginChannelMaoriRate,finishChannelMaoriRate,
                productSortCode,productSortName);
        newProductBatchMovingRateReqVo.setPageNo(pageNo);
        newProductBatchMovingRateReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectNewProductBatchMovingRate(newProductBatchMovingRateReqVo));
    }

    @GetMapping("/search/store/repurchase/rate")
    @ApiOperation("门店复购率(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<StoreRepurchaseRateRespVo>> selectStoreRepurchaseRate(
            @RequestParam(value = "province_name", required = false) String provinceName,
            @RequestParam(value = "city_name", required = false) String cityName,
            @RequestParam(value = "district_name", required = false) String districtName,
            @RequestParam(value = "category_code", required = false) String categoryCode,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        StoreRepurchaseRateReqVo storeRepurchaseRateReqVo = new StoreRepurchaseRateReqVo(provinceName,cityName,districtName,categoryCode,categoryName,orderCode,orderOriginal,beginCreateTime,finishCreateTime);
        storeRepurchaseRateReqVo.setPageNo(pageNo);
        storeRepurchaseRateReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectStoreRepurchaseRate(storeRepurchaseRateReqVo));
    }

    @GetMapping("/search/negative/margin")
    @ApiOperation("负毛利(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<NegativeMarginRespVo>> selectNegativeMargin(
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
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        NegativeMarginReqVo negativeMarginReqVo = new NegativeMarginReqVo(skuCode,skuName,categoryCode,categoryName,productBrandCode,productBrandName,orderCode,orderOriginal,beginCreateTime,finishCreateTime,productSortCode,productSortName);
        negativeMarginReqVo.setPageNo(pageNo);
        negativeMarginReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectNegativeMargin(negativeMarginReqVo));
    }

    @GetMapping("/search/suggest/replenishment")
    @ApiOperation("建议补货(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<SuggestReplenishmentRespVo>> selectSuggestReplenishment(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
            @RequestParam(value = "product_brand_name", required = false) String productBrandName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        SuggestReplenishmentReqVo suggestReplenishmentReqVo = new SuggestReplenishmentReqVo(skuCode,skuName,productCategoryCode,productCategoryName,productBrandCode,productBrandName,transportCenterCode,transportCenterName,beginCreateTime,finishCreateTime);
        suggestReplenishmentReqVo.setPageNo(pageNo);
        suggestReplenishmentReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectSuggestReplenishment(suggestReplenishmentReqVo));
    }

    @GetMapping("/search/low/inventory")
    @ApiOperation("低库存(带分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<LowInventoryRespVo>> selectLowInventory(
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginCreateTime,finishCreateTime,productSortCode,
                productSortName);
        highLowInventoryReqVo.setPageNo(pageNo);
        highLowInventoryReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectLowInventory(highLowInventoryReqVo));
    }

    @GetMapping("/search/high/inventory")
    @ApiOperation("高库存(带分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<HighInventoryRespVo>> selectHighInventory(
            @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
            @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginCreateTime,finishCreateTime,productSortCode,
                productSortName);
        highLowInventoryReqVo.setPageNo(pageNo);
        highLowInventoryReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectHighInventory(highLowInventoryReqVo));
    }

    @GetMapping("/search/brand/sale")
    @ApiOperation("品牌促销(带分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "create_time", value = "日期", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "department_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "department_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<BrandSaleRespVo>> selectBrandSale(
            @RequestParam(value = "create_time", required = false) String createTime,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "department_code", required = false) String departmentCode,
            @RequestParam(value = "department_name", required = false) String departmentName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        CategorySaleReqVo brandSaleReqVo = new CategorySaleReqVo(createTime,orderCode,orderOriginal,departmentCode,departmentName);
        brandSaleReqVo.setPageNo(pageNo);
        brandSaleReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectBrandSale(brandSaleReqVo));
    }

    @GetMapping("/search/category/sale")
    @ApiOperation("品类促销(带分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "create_time", value = "日期", type = "String"),
            @ApiImplicitParam(name = "order_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "order_original", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "department_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "department_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<CategorySaleRespVo>> selectCategorySale(
            @RequestParam(value = "create_time", required = false) String createTime,
            @RequestParam(value = "order_code", required = false) String orderCode,
            @RequestParam(value = "order_original", required = false) String orderOriginal,
            @RequestParam(value = "department_code", required = false) String departmentCode,
            @RequestParam(value = "department_name", required = false) String departmentName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        CategorySaleReqVo categorySaleReqVo = new CategorySaleReqVo(createTime,orderCode,orderOriginal,departmentCode,departmentName);
        categorySaleReqVo.setPageNo(pageNo);
        categorySaleReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectCategorySale(categorySaleReqVo));
    }
    
    @GetMapping("/search/big/effect")
    @ApiOperation("大效期(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<BigEffectRespVo>> selectBigEffect(
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
            @RequestParam(value = "big_effect_period_warn_day", required = false) String bigEffectPeriodWarnDay,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        BigEffectReqVo bigEffectReqVo = new BigEffectReqVo(createTime,skuCode,skuName,productBrandCode,productBrandName,categoryCode,categoryName,supplyCode,supplyName,procurementSectionCode,procurementSectionName,transportCenterCode,transportCenterName,warehouseCode,warehouseName,bigEffectPeriodWarnDay);
        bigEffectReqVo.setPageNo(pageNo);
        bigEffectReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectBigEffect(bigEffectReqVo));
    }

    @GetMapping("/search/monthly/sales")
    @ApiOperation("月销售情况(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<MonthlySalesRespVo>> selectMonthlySales(
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
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthlySalesReqVo monthlySalesReqVo = new MonthlySalesReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime);
        monthlySalesReqVo.setPageNo(pageNo);
        monthlySalesReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthlySales(monthlySalesReqVo));
    }

    @GetMapping("/search/month/gross/margin")
    @ApiOperation("月毛利率情况(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<MonthlyGrossMarginRespVo>> selectMonthlyGrossMargin(
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
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthlySalesReqVo monthlyGrossMarginReqVo = new MonthlySalesReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime);
        monthlyGrossMarginReqVo.setPageNo(pageNo);
        monthlyGrossMarginReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthlyGrossMargin(monthlyGrossMarginReqVo));
    }

    @GetMapping("/search/month/sales/achievement")
    @ApiOperation("月销售达成情况(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<MonthSalesAchievementRespVo>> selectMonthSalesAchievement(
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
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthSalesAchievementReqVo monthSalesAchievementReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime,categoryCode,categoryName);
        monthSalesAchievementReqVo.setPageNo(pageNo);
        monthSalesAchievementReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthSalesAchievement(monthSalesAchievementReqVo));
    }

    @GetMapping("/search/month/cumulative/brand/sales")
    @ApiOperation("月累计品类销售情况(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<MonthCumulativeBrandSalesRespVo>> selectMonthCumulativeBrandSales(
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
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthSalesAchievementReqVo monthCumulativeBrandSalesReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime,categoryCode,categoryName);
        monthCumulativeBrandSalesReqVo.setPageNo(pageNo);
        monthCumulativeBrandSalesReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectMonthCumulativeBrandSales(monthCumulativeBrandSalesReqVo));
    }

    @GetMapping("/search/month/cumulative/gross/profit/margin")
    @ApiOperation("月累计品类毛利率情况(带分页)")
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
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<PageReportResData<MonthCumulativeGrossProfitMarginRespVo>> selectMonthCumulativeGrossProfitMargin(
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
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        MonthSalesAchievementReqVo monthCumulativeGrossProfitMarginReqVo = new MonthSalesAchievementReqVo(productSortCode,productSortName,orderCode,orderOriginal,storeTypeCode,storeType,dataTypeCode,dataType,beginCreateTime,finishCreateTime,categoryCode,categoryName);
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

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRecordController.class);

    @GetMapping("/excel/high")
    @ApiOperation("高库存导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public void exportProductHigh( @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
                                   @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
                                   @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
                                   @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
                                   @RequestParam(value = "product_sort_code", required = false) String productSortCode,
                                   @RequestParam(value = "product_sort_name", required = false) String productSortName,
                                   @RequestParam(value = "page_no", required = false) Integer pageNo,
                                   @RequestParam(value = "page_size", required = false) Integer pageSize, HttpServletResponse response) throws IOException {
        OutputStream outputStream = null;
        try {
            HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginCreateTime,finishCreateTime,productSortCode,
                    productSortName);
            List<HighInventoryRespVo> highInventoryRespVo = reportService.selectHighInventorys(highLowInventoryReqVo);
            XSSFWorkbook wb = ExportExcelReportHigh.exportData(highInventoryRespVo);
            String excelName = "高库存数据导出";
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(excelName, "UTF-8") + ".xlsx");

            ServletOutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
            /*Map<String, String> map = System.getenv();
            String userName = map.get("USERNAME");// 获取用户名
            // 创建一个文件
            File file = new File("C:/Users/"+ userName + "/Downloads" + highLowInventoryReqVo.getFileName());
            try {
                file.createNewFile();
                // 将excel内容存盘
                FileOutputStream stream = FileUtils.openOutputStream(file);
                wb.write(stream);
                stream.close();
            } catch (Exception e) {
                LOGGER.error("导出数据异常,message:{},cause:{}", e.getMessage(), e.getCause());
            }
            */
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/excel/low")
    @ApiOperation("低库存导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组编码", type = "String"),
            @ApiImplicitParam(name = "procurement_section_name", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public void exportProductLow(@RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
                                 @RequestParam(value = "procurement_section_name", required = false) String procurementSectionName,
                                 @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
                                 @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
                                 @RequestParam(value = "product_sort_code", required = false) String productSortCode,
                                 @RequestParam(value = "product_sort_name", required = false) String productSortName,
                                 @RequestParam(value = "page_no", required = false) Integer pageNo,
                                 @RequestParam(value = "page_size", required = false) Integer pageSize, HttpServletResponse response) throws IOException {
        OutputStream outputStream = null;
        try {
            HighLowInventoryReqVo highLowInventoryReqVo = new HighLowInventoryReqVo(procurementSectionCode,procurementSectionName,beginCreateTime,finishCreateTime,productSortCode,
                    productSortName);
            List<LowInventoryRespVo> lowInventoryRespVo = reportService.selectLowInventorys(highLowInventoryReqVo);
            XSSFWorkbook wb = ExportExcelReportLow.exportData(lowInventoryRespVo);
            String excelName = "低库存数据导出";
          //  excelName = URLEncoder.encode(excelName,"UTF-8");
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(excelName, "UTF-8") + ".xlsx");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }
}
