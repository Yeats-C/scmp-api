package com.aiqin.bms.scmp.api.abutment.web;

import com.aiqin.bms.scmp.api.abutment.domain.manual.ManualOrderRequest;
import com.aiqin.bms.scmp.api.abutment.service.ManualOrderService;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BaseController;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "手动推送失败单据")
@RequestMapping("/manual")
public class ManualOrderControl extends BaseController {

    @Resource
    private ManualOrderService manualOrderService;

    @PostMapping("/dl/order")
    @ApiOperation(value = "DL->熙耘，推送耘链销售/退货单")
    public synchronized HttpResponse<List<String>> dlOrder(@Valid @RequestBody ManualOrderRequest request, BindingResult br) {
        super.checkParameters(br);
        try {
            return manualOrderService.dlOrder(request);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.HAND_PUSH_ERROR);
        }
    }

    @PostMapping("/order/dl")
    @ApiOperation(value = "熙耘->DL，推送耘链销售/退货/采购/退供单")
    public synchronized HttpResponse<List<String>> orderDL(@Valid @RequestBody ManualOrderRequest request) {
        try {
            return manualOrderService.orderDl(request);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.HAND_PUSH_ERROR);
        }
    }
}
