package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.bireport.domain.request.ChartReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.*;
import com.aiqin.bms.scmp.api.bireport.service.ChartService;
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
 * @description 图表
 */
@RestController
@Api(tags = "图表接口")
@RequestMapping("/chart")
@Slf4j
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/search/monthly/sales")
    @ApiOperation("月销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "production_date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "qun_sale", value = "渠道销售", type = "String"),
            @ApiImplicitParam(name = "fen_sale", value = "分销销售", type = "String"),
    })
    public HttpResponse<List<MonthlySalesRespVo>> selectMonthlySales(
            @RequestParam(value = "production_date", required = false) String productionDate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "qun_sale", required = false) String qunSale,
            @RequestParam(value = "fen_sale", required = false) String fenSale){
        ChartReqVo chartReqVo = new ChartReqVo(productionDate,productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,qunSale,fenSale);
        return HttpResponse.success(chartService.selectMonthlySales(chartReqVo));
    }

    @GetMapping("/search/month/gross/margin")
    @ApiOperation("月毛利率情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "production_date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "qun_sale", value = "渠道销售", type = "String"),
            @ApiImplicitParam(name = "fen_sale", value = "分销销售", type = "String"),
    })
    public HttpResponse<List<MonthlyGrossMarginRespVo>> selectMonthlyGrossMargin(
            @RequestParam(value = "production_date", required = false) String productionDate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "qun_sale", required = false) String qunSale,
            @RequestParam(value = "fen_sale", required = false) String fenSale){
        ChartReqVo chartReqVo = new ChartReqVo(productionDate,productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,qunSale,fenSale);
        return HttpResponse.success(chartService.selectMonthlyGrossMargin(chartReqVo));
    }

    @GetMapping("/search/month/sales/achievement")
    @ApiOperation("月销售达成情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "production_date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "qun_sale", value = "渠道销售", type = "String"),
            @ApiImplicitParam(name = "fen_sale", value = "分销销售", type = "String"),
    })
    public HttpResponse<List<MonthSalesAchievementRespVo>> selectMonthSalesAchievement(
            @RequestParam(value = "production_date", required = false) String productionDate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "qun_sale", required = false) String qunSale,
            @RequestParam(value = "fen_sale", required = false) String fenSale){
        ChartReqVo chartReqVo = new ChartReqVo(productionDate,productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,qunSale,fenSale);
        return HttpResponse.success(chartService.selectMonthSalesAchievement(chartReqVo));
    }

    @GetMapping("/search/month/cumulative/brand/sales")
    @ApiOperation("月累计品类销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "production_date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "qun_sale", value = "渠道销售", type = "String"),
            @ApiImplicitParam(name = "fen_sale", value = "分销销售", type = "String"),
    })
    public HttpResponse<List<MonthCumulativeBrandSalesRespVo>> selectMonthCumulativeBrandSales(
            @RequestParam(value = "production_date", required = false) String productionDate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "qun_sale", required = false) String qunSale,
            @RequestParam(value = "fen_sale", required = false) String fenSale){
        ChartReqVo chartReqVo = new ChartReqVo(productionDate,productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,qunSale,fenSale);
        return HttpResponse.success(chartService.selectMonthCumulativeBrandSales(chartReqVo));
    }

    @GetMapping("/search/month/cumulative/gross/profit/margin")
    @ApiOperation("月累计品类毛利率情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "production_date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门编码", type = "String"),
            @ApiImplicitParam(name = "product_sort_name", value = "所属部门", type = "String"),
            @ApiImplicitParam(name = "price_channel_code", value = "渠道编码", type = "String"),
            @ApiImplicitParam(name = "price_channel_name", value = "渠道", type = "String"),
            @ApiImplicitParam(name = "store_type_code", value = "门店类型code", type = "String"),
            @ApiImplicitParam(name = "store_type_name", value = "门店类型", type = "String"),
            @ApiImplicitParam(name = "data_style_code", value = "数据类型code", type = "String"),
            @ApiImplicitParam(name = "data_style_name", value = "数据类型", type = "String"),
            @ApiImplicitParam(name = "qun_sale", value = "渠道销售", type = "String"),
            @ApiImplicitParam(name = "fen_sale", value = "分销销售", type = "String"),
    })
    public HttpResponse<List<MonthCumulativeGrossProfitMarginRespVo>> selectMonthCumulativeGrossProfitMargin(
            @RequestParam(value = "production_date", required = false) String productionDate,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode,
            @RequestParam(value = "product_sort_name", required = false) String productSortName,
            @RequestParam(value = "price_channel_code", required = false) String priceChannelCode,
            @RequestParam(value = "price_channel_name", required = false) String priceChannelName,
            @RequestParam(value = "store_type_code", required = false) String storeTypeCode,
            @RequestParam(value = "store_type_name", required = false) String storeTypeName,
            @RequestParam(value = "data_style_code", required = false) String dataStyleCode,
            @RequestParam(value = "data_style_name", required = false) String dataStyleName,
            @RequestParam(value = "qun_sale", required = false) String qunSale,
            @RequestParam(value = "fen_sale", required = false) String fenSale){
        ChartReqVo chartReqVo = new ChartReqVo(productionDate,productSortCode,productSortName,priceChannelCode,priceChannelName,storeTypeCode,storeTypeName,dataStyleCode,dataStyleName,qunSale,fenSale);
        return HttpResponse.success(chartService.selectMonthCumulativeGrossProfitMargin(chartReqVo));
    }
}
