package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/

@Api("采购申请")
@RequestMapping("/apply")
@RestController
public class PurchaseApplyController {

    @Resource
    private PurchaseApplyService purchaseApplyService;

    @GetMapping("/list")
    @ApiOperation(value = "采购申请列表")
    public HttpResponse<List<PurchaseApplyResponse>> rejectApplyList(@RequestBody PurchaseApplyRequest purchaseApplyRequest) {


        return purchaseApplyService.applyList(purchaseApplyRequest);
    }

}
