package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
@Service
public class OrderCallbackServiceImpl implements OrderCallbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCallbackServiceImpl.class);

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public HttpResponse outboundOrder(OutboundRequest request) {
        OrderInfo orderInfo = new OrderInfo();


        Integer count = orderInfoMapper.insert(orderInfo);
        LOGGER.info("添加订单:{}",count);
        return null;
    }

    @Override
    public HttpResponse returnOrder(ReturnRequest request) {
        return null;
    }
}
