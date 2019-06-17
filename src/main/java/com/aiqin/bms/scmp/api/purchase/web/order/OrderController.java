package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ChangeOrderStatusReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryOrderListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Description:
 * 订单控制器
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:22
 */
@RestController
@Slf4j
@Api(value = "订单相关")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("订单同步")
    @PostMapping("/save")
    public HttpResponse<Boolean> saveOrder(@RequestBody @Valid List<OrderInfoReqVO> reqVO){
        try {
            return HttpResponse.success(orderService.save(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("订单列表")
    @PostMapping("/list")
    public HttpResponse<BasePage<QueryOrderListRespVO>> list(@RequestBody @Valid QueryOrderListReqVO reqVO){
        try {
            return HttpResponse.success(orderService.list(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("订单详情")
    @GetMapping("/view")
    public HttpResponse<QueryOrderInfoRespVO> view(@RequestParam String orderCode){
        try {
            return HttpResponse.success(orderService.view(orderCode));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("")
    @GetMapping("/changeStatus")
    public HttpResponse<Boolean> changeStatus(@RequestParam ChangeOrderStatusReqVO reqVO){
        try {
            return HttpResponse.success(orderService.changeStatus(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}
