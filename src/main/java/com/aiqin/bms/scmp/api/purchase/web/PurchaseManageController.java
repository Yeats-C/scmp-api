package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseFormRequest;
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

    @PostMapping("/purchase/product/list")
    @ApiOperation("查询申请单商品列表及商品列表详情")
    public HttpResponse purchaseProductList(@RequestBody PurchaseFormRequest purchaseFormRequest) {
        return purchaseManageService.purchaseProductList(purchaseFormRequest);
    }

//    @PostMapping("/purchase/form")
//    @ApiOperation("生成采购单")
//    public HttpResponse purchaseForm(@RequestBody List<String> applyIds) {
//        return purchaseManageService.purchaseForm(applyIds);
//    }

}
