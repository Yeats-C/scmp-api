package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReport;
import com.aiqin.bms.scmp.api.product.service.ProductSkuInspReportService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuInspReportController
 * @date 2019/7/2 17:43
 * @description TODO
 */
@RestController
@Api(tags = "质检报告接口")
@RequestMapping("/product/sku/inspreport")
@Slf4j
public class ProductSkuInspReportController {

    @Autowired
    private ProductSkuInspReportService productSkuInspReportService;

    @PostMapping("/save")
    @ApiOperation(value = "保存质检报告")
    public HttpResponse<Integer> saveProductSkuInspReport(@RequestBody List<ProductSkuInspReport> productSkuInspReports){
        try {
            return HttpResponse.success(productSkuInspReportService.insertList(productSkuInspReports));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @GetMapping("/get/skuCode")
    @ApiOperation(value = "通过SKU查询质检报告")
    public HttpResponse<Integer> getList(@RequestParam String skuCode){
        try {
            return HttpResponse.success(productSkuInspReportService.getList(skuCode));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


}
