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
        return HttpResponse.success(dashboardService.selectDashboardDepartAnnualSalesStati());
    }

    @GetMapping("/dashboard/monthly/sales/stati")
    @ApiOperation("月销售情况（不累计）")
    public HttpResponse<List<DashboardMonthlySalesStatiRespVo>> selectDashboardMonthlySalesStati(){
        return HttpResponse.success(dashboardService.selectDashboardMonthlySalesStati());
    }

    @GetMapping("/dashboard/monthly/sales/stati/acc")
    @ApiOperation("月销售情况（月累计）")
    public HttpResponse<List<DashboardMonthlySalesStatiAccRespVo>> selectDashboardMonthlySalesStatiAcc(){
        return HttpResponse.success(dashboardService.selectDashboardMonthlySalesStatiAcc());
    }

    @GetMapping("/dashboard/monthly/loss/amount")
    @ApiOperation("月亏损")
    public HttpResponse<List<DashboardMonthlyLossAmountRespVo>> selectDashboardMonthlyLossAmount(){
        return HttpResponse.success(dashboardService.selectDashboardMonthlyLossAmount());
    }

    @GetMapping("/dashboard/dep/monthly/homocyclic/ratio")
    @ApiOperation("当月部门销售同环比")
    public HttpResponse<List<DashboardDepMonthlyHomocyclicRatioRespVo>> selectDashboardDepMonthlyHomocyclicRatio(){
        DashboardDepMonthlyHomocyclicRatioReqVo dashboardDepMonthlyHomocyclicRatioReqVo = new DashboardDepMonthlyHomocyclicRatioReqVo();
        return HttpResponse.success(dashboardService.selectDashboardDepMonthlyHomocyclicRatio(dashboardDepMonthlyHomocyclicRatioReqVo));
    }

    @GetMapping("/dashboard/all/kinds/loss/ratio")
    @ApiOperation("今年各亏损占比")
    public HttpResponse<List<DashboardAllKindsLossRatioRespVo>> selectDashboardAllKindsLossRatio(){
        DashboardAllKindsLossRatioReqVo dashboardAllKindsLossRatioReqVo = new DashboardAllKindsLossRatioReqVo();
        return HttpResponse.success(dashboardService.selectDashboardAllKindsLossRatio(dashboardAllKindsLossRatioReqVo));
    }

    @GetMapping("/dashboard/dep/cate/proper/sales/amount")
    @ApiOperation("当月各部门品类属性下的销售情况")
    public HttpResponse<List<DashboardDepCateProperSalesAmountRespVo>> selectDashboardDepCateProperSalesAmount(){
        DashboardDepCateProperSalesAmountReqVo dashboardDepCateProperSalesAmountReqVo = new DashboardDepCateProperSalesAmountReqVo();
        return HttpResponse.success(dashboardService.selectDashboardDepCateProperSalesAmount(dashboardDepCateProperSalesAmountReqVo));
    }

    @GetMapping("/dashboard/dep/proper/sales/amount")
    @ApiOperation("当月各部门属性下的销售情况")
    public HttpResponse<List<DashboardDepProperSalesAmountRespVo>> selectDashboardDepProperSalesAmount(){
        DashboardDepProperSalesAmountReqVo dashboardDepProperSalesAmountReqVo = new DashboardDepProperSalesAmountReqVo();
        return HttpResponse.success(dashboardService.selectDashboardDepProperSalesAmount(dashboardDepProperSalesAmountReqVo));
    }

    @GetMapping("/dashboard/dep/cate/sales/amount")
    @ApiOperation("当月各部门品类下的销售情况")
    public HttpResponse<List<DashboardDepCateSalesAmountRespVo>> selectDashboardDepCateSalesAmount(){
        DashboardDepCateSalesAmountReqVo dashboardDepCateSalesAmountReqVo = new DashboardDepCateSalesAmountReqVo();
        return HttpResponse.success(dashboardService.selectDashboardDepCateSalesAmount(dashboardDepCateSalesAmountReqVo));
    }
}
