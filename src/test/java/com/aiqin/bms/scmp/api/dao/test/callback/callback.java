package com.aiqin.bms.scmp.api.dao.test.callback;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.DeliveryCallBackRequest;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.DeliveryDetailRequest;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.google.common.collect.Lists;
import lombok.Data;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class callback extends SpringBootTestContext {

    @Resource
    private InboundService inboundService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderCallbackService orderCallbackService;

    @Test
    public void listGroupBy() {
        List<BaseOrder> orderList = Lists.newArrayList();
        BaseOrder baseOrder = new BaseOrder(-1, "23");

        BaseOrder baseOrder1 = new BaseOrder(-2, "23");

        BaseOrder baseOrder2 = new BaseOrder(3, "23");

        orderList.add(baseOrder);
        orderList.add(baseOrder1);
        orderList.add(baseOrder2);
        Map<Integer, List<BaseOrder>> collect = orderList.stream().
                collect(Collectors.groupingBy(new Function<BaseOrder, Integer>() {
                    @Override
                    public Integer apply(BaseOrder baseOrder) {
                        if (baseOrder.count > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }));

        for (List<BaseOrder> baseOrders : collect.values()) {
            System.out.println(baseOrders.toString());
        }
    }

    @Test
    public void repealOrder() {
        String orderId = "2361";
        String createById ="123123";
        String createByName = "123123";
        String description = "123123";
        inboundService.repealOrder(orderId,createById,createByName, description);

    }

    @Data
    class BaseOrder {
        private Integer count;
        private String name;

        public BaseOrder(Integer count, String name) {
            this.count = count;
            this.name = name;
        }
    }

    @Test
    public void deliveryCallBack() {
        DeliveryCallBackRequest request = new DeliveryCallBackRequest();
        request.setCustomerCode("10086");
        request.setCustomerName("移动");
        request.setDeliveryCode("1000010");
        request.setTransportAmount(BigDecimal.valueOf(20));
        request.setTransportPerson("张三");
        request.setTransportDate(new Date());
        request.setTransportCenterCode("1081");
        request.setTransportCenterName("华北仓");
        request.setTransportCompanyCode("01");
        request.setTransportCompanyName("爱亲科技");
        request.setTransportCode("100888");
        List<DeliveryDetailRequest> details = Lists.newArrayList();
        DeliveryDetailRequest detail1 = new DeliveryDetailRequest();
        detail1.setOrderCode("100861");
        detail1.setTransportAmount(BigDecimal.TEN);
        details.add(detail1);
        DeliveryDetailRequest detail2 = new DeliveryDetailRequest();
        detail2.setOrderCode("100862");
        detail2.setTransportAmount(BigDecimal.TEN);
        details.add(detail2);
        request.setDetailList(details);
        orderCallbackService.deliveryCallBack(request);
    }

    @Test
    public void orderCancel() {
        String orderCode = "10086";
        String operatorId = "11111";
        String operatorName = "张三";
        orderService.orderCancel(orderCode, operatorId, operatorName);
    }

}
