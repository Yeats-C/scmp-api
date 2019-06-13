package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description:
 * 订单控制器
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:22
 */
@RestController
@Slf4j
@Api(description = "订单相关")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("订单同步")
    @PostMapping("/save")
    public HttpResponse<Boolean> saveOrder(@RequestBody @Valid OrderInfoReqVO reqVO){
        try {
            return HttpResponse.success(orderService.save(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
