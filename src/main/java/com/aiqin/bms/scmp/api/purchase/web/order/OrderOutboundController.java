package com.aiqin.bms.scmp.api.purchase.web.order;

import com.aiqin.bms.scmp.api.purchase.service.OrderOutboundService;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(description = "订单生成api")
@RequestMapping("/order/outbound")
public class OrderOutboundController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderOutboundService orderOutboundService;

    @PostMapping("/code/sale")
    @ApiOperation(value = "根据耘链的销售单号 生成对应出库单号")
    public HttpResponse insertOutboundByOrderCode(@RequestBody List<String> orderCodes) {
        LOGGER.info("根据耘链的销售单code参数，生成对应出库单号：[{}]", JsonUtil.toJson(orderCodes));
        return orderOutboundService.insertOutboundByOrderCode(orderCodes);
    }
}
