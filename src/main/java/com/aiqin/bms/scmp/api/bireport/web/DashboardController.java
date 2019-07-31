package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.bireport.domain.request.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.domain.response.dashboard.*;
import com.aiqin.bms.scmp.api.bireport.service.DashboardService;
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

@RestController
@Api(tags = "仪表盘接口")
@RequestMapping("/report/search")
@Slf4j
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

      /* @ApiImplicitParams({
            @ApiImplicitParam(name = "create_time", value = "日期", type = "String"),
    })*/
    // @RequestParam(value = "create_time", required = false) String createTime

    @GetMapping("/dashboard/depart/annual/sales/stati")
    @ApiOperation("年销售情况(部门)")
    public HttpResponse<List<DashboardDepartAnnualSalesStatiRespVo>> selectDashboardDepartAnnualSalesStati(){
        DashboardDepartAnnualSalesStatiReqVo dashboardDepartAnnualSalesStatiReqVo = new DashboardDepartAnnualSalesStatiReqVo();
        return HttpResponse.success(dashboardService.selectDashboardDepartAnnualSalesStati(dashboardDepartAnnualSalesStatiReqVo));
    }

    @GetMapping("/dashboard/monthly/sales/stati")
    @ApiOperation("月销售情况（不累计）")
    public HttpResponse<List<DashboardMonthlySalesStatiRespVo>> selectDashboardMonthlySalesStati(){
        DashboardMonthlySalesStatiReqVo dashboardMonthlySalesStatiReqVo = new DashboardMonthlySalesStatiReqVo();
        return HttpResponse.success(dashboardService.selectDashboardMonthlySalesStati(dashboardMonthlySalesStatiReqVo));
    }

    @GetMapping("/dashboard/monthly/sales/stati/acc")
    @ApiOperation("月销售情况（月累计）")
    public HttpResponse<List<DashboardMonthlySalesStatiAccRespVo>> selectDashboardMonthlySalesStatiAcc(){
        DashboardMonthlySalesStatiAccReqVo dashboardMonthlySalesStatiAccReqVo = new DashboardMonthlySalesStatiAccReqVo();
        return HttpResponse.success(dashboardService.selectDashboardMonthlySalesStatiAcc(dashboardMonthlySalesStatiAccReqVo));
    }

    @GetMapping("/dashboard/monthly/loss/amount")
    @ApiOperation("月亏损")
    public HttpResponse<List<DashboardMonthlyLossAmountRespVo>> selectDashboardMonthlyLossAmount(){
        DashboardMonthlyLossAmountReqVo dashboardMonthlyLossAmountReqVo = new DashboardMonthlyLossAmountReqVo();
        return HttpResponse.success(dashboardService.selectDashboardMonthlyLossAmount(dashboardMonthlyLossAmountReqVo));
    }

    @GetMapping("/dashboard/dep/monthly/homocyclic/ratio")
    @ApiOperation("当月部门销售同环比")
    public HttpResponse<List<DashboardDepMonthlyHomocyclicRatioRespVo>> selectDashboardDepMonthlyHomocyclicRatio(){
        DashboardDepMonthlyHomocyclicRatioReqVo dashboardDepMonthlyHomocyclicRatioReqVo = new DashboardDepMonthlyHomocyclicRatioReqVo();
        return HttpResponse.success(dashboardService.selectDashboardDepMonthlyHomocyclicRatio(dashboardDepMonthlyHomocyclicRatioReqVo));
    }
}
