package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryProductUniqueCodeListRespVO;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
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
        log.info("OrderController---saveOrder---param：[{}]", JSONObject.toJSONString(reqVO));
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
        log.info("OrderController---list---param：[{}]", JSONObject.toJSONString(reqVO));
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
        log.info("OrderController---view---param：[{}]", orderCode);
        try {
            return HttpResponse.success(orderService.view(orderCode));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("修改订单状态")
    @GetMapping("/changeStatus")
    public HttpResponse<Boolean> changeStatus(@RequestBody ChangeOrderStatusReqVO reqVO){
        log.info("OrderController---changeStatus---param：[{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(orderService.changeStatus(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    @ApiOperation("5配货/11发货/97缺货终止")
    @GetMapping("/distribution")
    public HttpResponse<Boolean> distribution(@RequestParam String orderCode, @RequestParam Integer status){
        log.info("OrderController---distribution---param：[{}],[{}]", orderCode,status);
        try {
            return HttpResponse.success(orderService.distribution(orderCode, status));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("订单商品列表")
    @PostMapping("/orderProductList")
    public HttpResponse<BasePage<QueryOrderProductListRespVO>> orderProductList(@RequestBody QueryOrderProductListReqVO reqVO){
        try {
            return HttpResponse.success(orderService.orderProductList(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @ApiOperation("商品唯一码")
    @PostMapping("/productUniqueCodeList")
    public HttpResponse<BasePage<QueryProductUniqueCodeListRespVO>> productUniqueCodeList(@RequestBody QueryProductUniqueCodeListReqVO reqVO){
        log.info("OrderController---productUniqueCodeList---param：[{}]", JSONObject.toJSONString(reqVO));
        try {
            return HttpResponse.success(orderService.productUniqueCodeList(reqVO));
        } catch (BizException e){
            return HttpResponse.failure(e.getMessageId());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

}
