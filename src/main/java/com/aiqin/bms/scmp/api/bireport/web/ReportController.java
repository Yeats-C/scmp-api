package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.request.SupplierArrivalRateReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.SupplierArrivalRateRespVo;
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
            @ApiImplicitParam(name = "transport_center_code", value = "仓库名称", type = "String"),
            @ApiImplicitParam(name = "category_level_code", value = "一级品类编号", type = "String"),
            @ApiImplicitParam(name = "category_level_name", value = "一级品类名称", type = "String"),
            @ApiImplicitParam(name = "begin_inbound_time", value = "时间begin", type = "String"),
            @ApiImplicitParam(name = "finish_inbound_time", value = "时间finish", type = "String")
    })
    public HttpResponse<List<SupplierArrivalRateRespVo>> selectSupplierArrivalRate(
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "supplier_name", required = false) String supplierName,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterCode,
            @RequestParam(value = "category_level_code", required = false) String categoryLevelCode,
            @RequestParam(value = "category_level_name", required = false) String categoryLevelName,
            @RequestParam(value = "begin_inbound_time", required = false) String beginInboundTime,
            @RequestParam(value = "finish_inbound_time", required = false) String finishInboundTime){
        SupplierArrivalRateReqVo SupplierArrivalRateReqVo = new SupplierArrivalRateReqVo(supplierCode,supplierName,transportCenterCode,categoryLevelCode,categoryLevelName,beginInboundTime,finishInboundTime);
        return HttpResponse.success(reportService.selectSupplierArrivalRate(SupplierArrivalRateReqVo));
    }
}
