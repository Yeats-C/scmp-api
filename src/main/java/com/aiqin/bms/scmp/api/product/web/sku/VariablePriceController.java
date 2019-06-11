package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.*;
import com.aiqin.bms.scmp.api.product.service.VariablePriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description="变价管理")
@RequestMapping("/variablePrice")
public class VariablePriceController {
    @Autowired
    private VariablePriceService variablePriceService;
    @PostMapping("/save")
    @ApiOperation("新建变价")
    public HttpResponse<Integer>saveVariablePrice(@RequestBody VariablePriceReqVo variablePriceReqVos){
        try {
            return HttpResponse.success(variablePriceService.saveList(variablePriceReqVos));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @PostMapping("/dataList")
    @ApiOperation("选择商品")
    public HttpResponse<BasePage<SkuDataListResponse>>getDataList(@RequestBody SkuDataListReqVo skuDataListReqVo){
        try {
            return HttpResponse.success(variablePriceService.getDataList(skuDataListReqVo));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @GetMapping("/variableDetail")
    @ApiOperation("变价详细")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "priceTypeCode", value = "价格类型code", required = true, paramType = "query"),
            @ApiImplicitParam(name = "variablePriceCode", value = "变价code", required = true, paramType = "query")
    })
    public HttpResponse<VariableDetailList>getVariableDetail(@RequestParam("priceTypeCode") String priceTypeCode,@RequestParam("variablePriceCode")  String variablePriceCode){
        try {
            return HttpResponse.success(variablePriceService.getVariableDetail(priceTypeCode, variablePriceCode));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @PostMapping("/batchImport/{priceTypeCode}")
    @ApiOperation("批量导入")
    public HttpResponse<List<ErrorVariableResponse>> batchImport(@RequestBody List<ExcelData>excelData, @PathVariable("priceTypeCode") String priceTypeCode){
        try {
            return HttpResponse.success(variablePriceService.batchImport(excelData, priceTypeCode));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @GetMapping("/confirm")
    @ApiOperation("确认变价")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "variablePriceCode", value = "变价code", required = true, paramType = "query"),
            @ApiImplicitParam(name = "priceTypeCode", value = "价格类型code", required = true, paramType = "query")
    })
    public HttpResponse<ConfirmPriceList> confirm(@RequestParam("variablePriceCode") String variablePriceCode,@RequestParam("priceTypeCode") String priceTypeCode){
        try {
            return HttpResponse.success(variablePriceService.getConfirm(variablePriceCode,priceTypeCode));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

    @PostMapping("/priceManagement")
    @ApiOperation("价格管理")
    public HttpResponse<BasePage<PriceManagement>> getManagement(@RequestBody PriceManagementReqVo priceManagementReqVo){
        try {
            return HttpResponse.success(variablePriceService.getManagement(priceManagementReqVo));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @PostMapping("/management/list")
    @ApiOperation("变价管理")
    public HttpResponse<BasePage<VariablePriceManagementResVO>> getManagement(@RequestBody VariablePriceManagementReqVO variablePriceManagementReqVO){
        try {
            return HttpResponse.success(variablePriceService.getPriceDataList(variablePriceManagementReqVO));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @GetMapping("/detailed/price/{productSkuPriceId}")
    @ApiOperation("价格详细")
    public HttpResponse<PriceDetailedResponse> getPriceSku(@PathVariable("productSkuPriceId") Long  productSkuPriceId){
        try {
            return HttpResponse.success(variablePriceService.getPriceSku(productSkuPriceId));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
    @PostMapping("/priceRevoke")
    @ApiOperation("变价撤销不撤销")
    public HttpResponse<Integer> priceRevoke(@RequestBody PriceRevokeReqVo priceRevokeReqVo){
        try {
            return HttpResponse.success(variablePriceService.priceRevoke(priceRevokeReqVo));
        }catch (Exception ex){
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

}
