package com.aiqin.bms.scmp.api.serveice.test.order;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
@Service
public class OrderCallbackServiceTest extends SpringBootTestContext {

    @Resource
    private OrderCallbackService orderCallbackService;

    @Test
    public void outboundOrder() {

        OutboundRequest request = new OutboundRequest();
        request.setOrderCode("o123123");
//        request.setCreateDate(new Date());
//        request.setOrderStatus(12);
////        request.setBeException(0);
//        request.setPaymentStatus(0);
////        request.setBeLock(0);
//        request.setOrderType("直送");
//        request.setOrderCategory("正常补货");
////        request.setPaymentType("");
////        request.setBeException(1);
//        request.setOrderOriginal("小红马");
//        request.setWarehouseName("库房..名称");
//        request.setTransportCenterName("仓库");
//        request.setSupplierName("供应商");
//        request.setProductNum(200L);
//        request.setPaymentTime(new Date());
//        request.setTransportTime(new Date());
//        request.setDeliveryTime(new Date());
//        request.setReceivingTime(new Date());
//        request.setOperatorTime(new Date());
        request.setOperatorCode("1111");
        request.setCustomerCode("222");
        request.setCustomerName("名称");
        request.setConsignee("张三");
        request.setConsigneePhone("18514263652");
        request.setProvinceCode("001");
        request.setProvinceName("省");
        request.setCityCode("001");
        request.setCityName("市");
        request.setDistrictCode("001");
        request.setDistrictName("区");
        request.setDetailAddress("详细地址");
        request.setProductChannelTotalAmount(BigDecimal.valueOf(20000));

        List<OutboundDetailRequest> detail = new ArrayList<>();
        OutboundDetailRequest outboundDetailRequest = new OutboundDetailRequest();
        outboundDetailRequest.setProductLineNum(1L);
        outboundDetailRequest.setSkuCode("10000000201");
        outboundDetailRequest.setChannelUnitPrice(BigDecimal.valueOf(10));
        outboundDetailRequest.setNum(200L);
        outboundDetailRequest.setActualDeliverNum(200L);
        detail.add(outboundDetailRequest);
        request.setDetail(detail);


        orderCallbackService.outboundOrder(request);

    }


}
