package com.aiqin.bms.scmp.api.statistics.web;

import com.aiqin.bms.scmp.api.statistics.domain.response.StoreRepurchaseRateResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import com.aiqin.bms.scmp.api.statistics.service.StatisticsService;
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

    @GetMapping("/supplier/delivery/rate")
    @ApiOperation("供应商到货率统计-部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", type = "String"),
            @ApiImplicitParam(name = "form_type", value = "供应商到货率统计类型: 0 年报 1 月报", type = "Integer") })
    public HttpResponse<SupplierDeliveryResponse> supplierDelivery(@RequestParam("date") String date,
                                                                   @RequestParam("form_type") Integer formType) {
        return statisticsService.supplierDelivery(formType, date);
    }


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
            @ApiImplicitParam(name = "season_type", value = "季节: 1 第一季节 2 第二季节 3 第三季节  4 第四季节", type = "Integer"),
            @ApiImplicitParam(name = "product_sort_code", value = "所属部门", type = "String")})
    public HttpResponse negativeGross(
            @RequestParam("date") String date,  @RequestParam("type") Integer type,
            @RequestParam(value = "report_type") Integer reportType,
            @RequestParam(value = "season_type", required = false) Integer seasonType,
            @RequestParam(value = "product_sort_code", required = false) String productSortCode) {
        return statisticsService.negativeGross(date, type, reportType, seasonType, productSortCode);
    }

}
