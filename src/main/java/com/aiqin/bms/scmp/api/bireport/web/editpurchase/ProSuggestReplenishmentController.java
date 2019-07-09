package com.aiqin.bms.scmp.api.bireport.web.editpurchase;

import com.aiqin.bms.scmp.api.bireport.domain.pojo.BiSuggestReplenishment;
import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.ProReplenishmentOutStockRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.bms.scmp.api.bireport.service.ProSuggestReplenishmentService;
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
 * @description 14大A品建议补货请求控制器
 */
@RestController
@Api(tags = "补货缺货接口")
@RequestMapping("/replenishment")
@Slf4j
public class ProSuggestReplenishmentController {

    @Autowired
    private ProSuggestReplenishmentService proSuggestReplenishmentService;

    @GetMapping("/search/Replenishment")
    @ApiOperation("获取14大A品建议补货skuCode")
    public HttpResponse<List<ProReplenishmentOutStockRespVo>> selectSuggestReplenishmentByPro(){
        return HttpResponse.success(proSuggestReplenishmentService.selectSuggestReplenishmentByPro());
    }

    @GetMapping("/search/sell/Replenishment")
    @ApiOperation("获取畅销建议补货skuCode")
    public HttpResponse<List<ProReplenishmentOutStockRespVo>> selectSuggestReplenishmentBySell(){
        return HttpResponse.success(proSuggestReplenishmentService.selectSuggestReplenishmentBySell());
    }

    @GetMapping("/search/outstock")
    @ApiOperation("获取14大A品缺货")
    public HttpResponse<List<ProReplenishmentOutStockRespVo>> selectOutStockByPro(){
        return HttpResponse.success(proSuggestReplenishmentService.selectOutStockByPro());
    }

    @GetMapping("/search/sell/outstock")
    @ApiOperation("获取畅销缺货")
    public HttpResponse<List<ProReplenishmentOutStockRespVo>> selectOutStockBySell(){
        return HttpResponse.success(proSuggestReplenishmentService.selectOutStockBySell());
    }

    @GetMapping("/search/purchase")
    @ApiOperation("获取采购申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_code", value = "sku编码", type = "String"),
            @ApiImplicitParam(name = "supplier_code", value = "供应商code", type = "String"),
            @ApiImplicitParam(name = "transport_center_code", value = "仓库名称", type = "String") })
    public HttpResponse<List<PurchaseApplyRespVo>> selectPurchaseApplySkuList(
            @RequestParam(value = "sku_code", required = false) String skuCode,
            @RequestParam(value = "supplier_code", required = false) String supplierCode,
            @RequestParam(value = "transport_center_code", required = false) String transportCenterName){
        PurchaseApplyReqVo purchaseApplyReqVo = new PurchaseApplyReqVo(skuCode,supplierCode,transportCenterName);
        return HttpResponse.success(proSuggestReplenishmentService.selectPurchaseApplySkuList(purchaseApplyReqVo));
    }
}
