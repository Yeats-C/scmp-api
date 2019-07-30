package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyListResponse;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.purchase.web.GoodsRejectController;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@RestController
@RequestMapping("/order/callback")
public class OrderCallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectController.class);

    @Resource
    private OrderCallbackService orderCallbackService;

    @PostMapping("/outbound")
    @ApiOperation(value = "销售出库单回调")
    public HttpResponse outboundOrder(@RequestBody OutboundRequest request) {
        LOGGER.info("销售出库单回调,request:{}", request.toString());
        return orderCallbackService.outboundOrder(request);
    }
    @PostMapping("/return")
    @ApiOperation(value = "销售退货单回调")
    public HttpResponse returnOrder(@RequestBody ReturnRequest request) {
        LOGGER.info("销售退货单回调,request:{}", request.toString());
        return orderCallbackService.returnOrder(request);
    }


}
