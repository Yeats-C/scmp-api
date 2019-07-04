package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.SaveProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuInspReportService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public HttpResponse<Integer> saveProductSkuInspReport(@RequestBody SaveProductSkuInspReportReqVo reportReqVo){
        try {
            return HttpResponse.success(productSkuInspReportService.saveProductSkuInspReport(reportReqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/get")
    @ApiOperation(value = "通过SKU查询质检报告")
    public HttpResponse<List<ProductSkuInspReportRespVo>> getList(@RequestBody QueryProductSkuInspReportReqVo reportReqVo){
        try {
            return HttpResponse.success(productSkuInspReportService.getList(reportReqVo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


}
