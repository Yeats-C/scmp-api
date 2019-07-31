package com.aiqin.bms.scmp.api.serveice.test.order;

import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
public class OrderCallbackServiceTest {

    @Resource
    private OrderCallbackService orderCallbackService;

    @Test
    public void outboundOrder(){

        OutboundRequest request = new OutboundRequest();
        request.setOrderCode("o123123");
        request.setCreateDate(new Date());
        request.setOrderStatus(12);
//        request.setBeException(0);
        request.setPaymentStatus(0);
//        request.setBeLock(0);
        request.setOrderType("1");
        request.setOrderCategory("1");
//        request.setPaymentType("");
//        request.setBeException(1);
        request.setOrderOriginal("小红马");
        request.setWarehouseName("库房..名称");
        request.setTransportCenterName("仓库");
        request.setSupplierName("供应商");
        request.setProductNum(10L);
        request.setPaymentTime(new Date());





        orderCallbackService.outboundOrder(request);

    }


}
