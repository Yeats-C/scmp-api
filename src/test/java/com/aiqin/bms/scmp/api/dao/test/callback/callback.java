package com.aiqin.bms.scmp.api.dao.test.callback;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.*;
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
    // 销售单发运的回传
    public void deliveryCallBack() {
        DeliveryCallBackRequest request = new DeliveryCallBackRequest();
        request.setCustomerCode("60000028");
        request.setCustomerName("爱掌柜兴创大厦1店");
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
        detail1.setOrderCode("20200108000029");
        detail1.setTransportAmount(BigDecimal.valueOf(20));
        details.add(detail1);
//        DeliveryDetailRequest detail2 = new DeliveryDetailRequest();
//        detail2.setOrderCode("100862");
//        detail2.setTransportAmount(BigDecimal.TEN);
//        details.add(detail2);
        request.setDetailList(details);
        orderCallbackService.deliveryCallBack(request);
    }

    @Test
    // 销售单发的取消回传
    public void orderCancel() {
        String orderCode = "10086";
        String operatorId = "11111";
        String operatorName = "张三";
        orderService.orderCancel(orderCode, operatorId, operatorName);
    }

    @Test
    // 销售单发货的回传
    public void outboundOrderCallBack() {
        OutboundCallBackRequest request = new OutboundCallBackRequest();
        request.setOderCode("20200108000029");
        request.setDeliveryTime(new Date());
        request.setActualTotalCount(1L);
        request.setDeliveryPerson("张三");
        request.setReceiveTime(new Date());
        request.setPersonId("888132");
        request.setPersonName("李四");
        List<OutboundCallBackDetailRequest> detailList = Lists.newArrayList();
        OutboundCallBackDetailRequest detail1 = new OutboundCallBackDetailRequest();
        detail1.setSkuCode("199807");
        detail1.setSkuName("199807名称");
        detail1.setLineCode(1L);
        detail1.setActualProductCount(1L);
        detailList.add(detail1);
//        OutboundCallBackDetailRequest detail2 = new OutboundCallBackDetailRequest();
//        detail2.setSkuCode("000002");
//        detail2.setSkuName("sku2");
//        detail2.setLineCode(2L);
//        detail2.setActualTotalCount(10L);
//        detailList.add(detail2);
        request.setDetailList(detailList);
        List<OutboundCallBackBatchRequest> batchList = Lists.newArrayList();
        OutboundCallBackBatchRequest batch1 = new OutboundCallBackBatchRequest();
        batch1.setSkuCode("199807");
        batch1.setSkuName("199807名称");
        batch1.setLineCode(1L);
        batch1.setActualTotalCount(1L);
        batch1.setBatchCode("77777");
        batch1.setBatchRemark("AAA");
        batch1.setTotalCount(1L);
        batch1.setProductDate(new Date().toString());
        batchList.add(batch1);
//        OutboundCallBackBatchRequest batch2 = new OutboundCallBackBatchRequest();
//        batch2.setSkuCode("000002");
//        batch2.setSkuName("sku2");
//        batch2.setLineCode(2L);
//        batch2.setActualTotalCount(10L);
//        batch2.setBatchCode("8888");
//        batch2.setBatchRemark("BBB");
//        batch2.setProductCount(10L);
//        batch2.setProductDate(new Date());
//        batchList.add(batch2);
        request.setBatchList(batchList);
        orderCallbackService.outboundOrderCallBack(request);
    }

}
