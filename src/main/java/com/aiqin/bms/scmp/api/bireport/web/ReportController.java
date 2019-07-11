package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.bireport.domain.request.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
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
            @ApiImplicitParam(name = "category_level_code", value = "一级品类编号", type = "String"),
            @ApiImplicitParam(name = "category_level_name", value = "一级品类名称", type = "String"),
            @ApiImplicitParam(name = "begin_inbound_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_inbound_time", value = "时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<List<SupplierArrivalRateRespVo>> selectSupplierArrivalRate(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "category_level_code", required = false) String categoryLevelCode,
            @RequestParam(value = "category_level_name", required = false) String categoryLevelName,
            @RequestParam(value = "begin_inbound_time", required = false) String beginInboundTime,
            @RequestParam(value = "finish_inbound_time", required = false) String finishInboundTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        SupplierArrivalRateReqVo supplierArrivalRateReqVo = new SupplierArrivalRateReqVo(supplierCode,supplierName,transportCenterCode,categoryLevelCode,categoryLevelName,beginInboundTime,finishInboundTime);
        supplierArrivalRateReqVo.setPageNo(pageNo);
        supplierArrivalRateReqVo.setPageNo(pageSize);
        return HttpResponse.success(reportService.selectSupplierArrivalRate(supplierArrivalRateReqVo));
    }

    @GetMapping("/search/goods/buy/sales")
    @ApiOperation("批次商品进销存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库编码", type = "String"),
            @ApiImplicitParam(name = "transport_center_name", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "product_category_name", value = "品类名称", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "入库时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "入库时间finish", type = "String"),
            @ApiImplicitParam(name = "inbound_days", value = "库存日期", type = "Integer"),
            @ApiImplicitParam(name = "begin_turnover_days", value = "周转天数begin", type = "Integer"),
            @ApiImplicitParam(name = "finish_turnover_days", value = "周转天数finish", type = "Integer"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouseName", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "product_category_one", value = "一级品类", type = "String"),
            @ApiImplicitParam(name = "product_category_two", value = "二级品类", type = "String"),
            @ApiImplicitParam(name = "product_category_three", value = "三级品类", type = "String"),
            @ApiImplicitParam(name = "product_category_four", value = "四级品类", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<List<GoodsBuySalesRespVo>> selectGoodsBuySales(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "product_category_name", required = false) String productCategoryName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "inbound_days", required = false) Integer inboundDays,
            @RequestParam(value = "begin_turnover_days", required = false) Integer beginTurnoverDays,
            @RequestParam(value = "finish_turnover_days", required = false) Integer finishTurnoverDays,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouseName", required = false) String warehouseName,
            @RequestParam(value = "product_category_one", required = false) String productCategoryOne,
            @RequestParam(value = "product_category_two", required = false) String productCategoryTwo,
            @RequestParam(value = "product_category_three", required = false) String productCategoryThree,
            @RequestParam(value = "product_category_four", required = false) String productCategoryFour,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        GoodsBuySalesReqVo goodsBuySalesReqVo = new GoodsBuySalesReqVo(supplierCode,supplierName,skuCode,skuName,transportCenterCode,transportCenterName,
                productCategoryName,productCategoryCode,productSortCode,productSortName,beginCreateTime,finishCreateTime,inboundDays,beginTurnoverDays,
                finishTurnoverDays,warehouseCode,warehouseName,productCategoryOne,productCategoryTwo,productCategoryThree,productCategoryFour);
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
            @ApiImplicitParam(name = "inbound_days", value = "库存日期", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "入库时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "入库时间finish", type = "String"),
            @ApiImplicitParam(name = "warehouse_code", value = "库房编码", type = "String"),
            @ApiImplicitParam(name = "warehouseName", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "begin_turnover_days", value = "周转天数begin", type = "Integer"),
            @ApiImplicitParam(name = "finish_turnover_days", value = "周转天数finish", type = "Integer"),
            @ApiImplicitParam(name = "product_category_one", value = "一级品类", type = "String"),
            @ApiImplicitParam(name = "product_category_two", value = "二级品类", type = "String"),
            @ApiImplicitParam(name = "product_category_three", value = "三级品类", type = "String"),
            @ApiImplicitParam(name = "product_category_four", value = "四级品类", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<List<GiftsBuySalesRespVo>> selectGiftsBuySales(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "inbound_days", required = false) Integer inboundDays,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouseName", required = false) String warehouseName,
            @RequestParam(value = "begin_turnover_days", required = false) Integer beginTurnoverDays,
            @RequestParam(value = "finish_turnover_days", required = false) Integer finishTurnoverDays,
            @RequestParam(value = "product_category_one", required = false) String productCategoryOne,
            @RequestParam(value = "product_category_two", required = false) String productCategoryTwo,
            @RequestParam(value = "product_category_three", required = false) String productCategoryThree,
            @RequestParam(value = "product_category_four", required = false) String productCategoryFour,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        GiftsBuySalesReqVo giftsBuySalesReqVo = new GiftsBuySalesReqVo(skuCode,skuName,transportCenterCode,transportCenterName,inboundDays,productSortCode,productSortName,beginCreateTime,finishCreateTime,
                warehouseCode,warehouseName,beginTurnoverDays,finishTurnoverDays,productCategoryOne,productCategoryTwo,productCategoryThree,productCategoryFour);
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
            @ApiImplicitParam(name = "warehouseName", value = "库房名称", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "入库时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "入库时间finish", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<List<SupplierReturnRespVo>> selectSupplierReturn(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "transport_center_name", required = false) String transportCenterName,
            @RequestParam(value = "warehouse_code", required = false) String warehouseCode,
            @RequestParam(value = "warehouseName", required = false) String warehouseName,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        SupplierReturnReqVo supplierReturnReqVo = new SupplierReturnReqVo(supplierCode,supplierName,transportCenterCode,transportCenterName,warehouseCode,warehouseName,beginCreateTime,finishCreateTime);
        supplierReturnReqVo.setPageNo(pageNo);
        supplierReturnReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectSupplierReturn(supplierReturnReqVo));
    }

    @GetMapping("/search/new/product/batch/moving/rate")
    @ApiOperation("新品批次动销率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "销售渠道", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "supplier_name", value = "供应商name", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类编码", type = "String"),
            @ApiImplicitParam(name = "begin_create_time", value = "入库时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_create_time", value = "入库时间finish", type = "String"),
            @ApiImplicitParam(name = "batch_code", value = "批次号", type = "String"),
            @ApiImplicitParam(name = "begin_maori_rate", value = "毛利率begin", type = "Integer"),
            @ApiImplicitParam(name = "finish_maori_rate", value = "毛利率finish", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<List<NewProductBatchMovingRateRespVo>> selectNewProductBatchMovingRate(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "sku_name", required = false) String skuName,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
            @RequestParam(value = "begin_create_time", required = false) String beginCreateTime,
            @RequestParam(value = "finish_create_time", required = false) String finishCreateTime,
            @RequestParam(value = "batch_code", required = false) String batchCode,
            @RequestParam(value = "begin_maori_rate", required = false) Integer beginMaoriRate,
            @RequestParam(value = "finish_maori_rate", required = false) Integer finishMaoriRate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_code", required = false) String productSortName,
            @RequestParam(value = "page_no", required = false) Integer pageNo,
            @RequestParam(value = "page_size", required = false) Integer pageSize){
        NewProductBatchMovingRateReqVo newProductBatchMovingRateReqVo = new NewProductBatchMovingRateReqVo(skuCode,skuName,priceChannelName,supplierCode,supplierName,productCategoryCode,beginCreateTime,finishCreateTime,batchCode,beginMaoriRate,finishMaoriRate,
                productSortCode,productSortName);
        newProductBatchMovingRateReqVo.setPageNo(pageNo);
        newProductBatchMovingRateReqVo.setPageSize(pageSize);
        return HttpResponse.success(reportService.selectNewProductBatchMovingRate(newProductBatchMovingRateReqVo));
    }

}
