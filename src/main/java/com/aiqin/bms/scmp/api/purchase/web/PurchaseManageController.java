package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseFormRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderRequest;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-14 17:33
 **/

@Api(tags = "采购单管理")
@RequestMapping("/manage")
@RestController
public class PurchaseManageController {

    @Resource
    private PurchaseManageService purchaseManageService;

    @PostMapping("/purchase/form")
    @ApiOperation("查询要生成的采购单")
    public HttpResponse selectPurchaseForm(@RequestBody List<String> applyIds) {
        return purchaseManageService.selectPurchaseForm(applyIds);
    }

    @PostMapping("/purchase/apply/list")
    @ApiOperation("生成采购单-采购申请单商品列表")
    public HttpResponse purchaseApplyList(@RequestBody PurchaseFormRequest purchaseFormRequest) {
        return purchaseManageService.purchaseApplyList(purchaseFormRequest);
    }

    @PostMapping("/purchase/product/list")
    @ApiOperation("生成采购单-商品列表")
    public HttpResponse purchaseProductList(@RequestBody PurchaseFormRequest purchaseFormRequest) {
        return purchaseManageService.purchaseProductList(purchaseFormRequest);
    }


    @PostMapping("/purchase/order")
    @ApiOperation("提交采购单")
    public HttpResponse purchaseOrder(PurchaseOrderRequest purchaseOrderRequest) {
        return purchaseManageService.purchaseOrder(purchaseOrderRequest);
    }

}
