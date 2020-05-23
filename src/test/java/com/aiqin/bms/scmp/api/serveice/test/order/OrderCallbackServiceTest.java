package com.aiqin.bms.scmp.api.serveice.test.order;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.ground.util.http.HttpClient;
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

    @Test
    public void sendMsgToMine(){
        HttpClient httpClient = HttpClient.post("http://192.168.200.203:8680/purchase/source/inbound")
                .json("{\"supplierName\":\"CJYaaa\",\"operuserName\":\"张昀童\",\"batchInfo\":[{\"supplierName\":\"乐高（亚洲中国）智力开发有限公司\",\"createByName\":\"李三\",\"updateByName\":\"李三\",\"batchCode\":\"20200427\",\"updateTime\":\"2020-04-27 16:29:31\",\"supplierCode\":\"10000015\",\"totalCount\":10,\"productDate\":\"2020-04-27\",\"batchInfoCode\":\"10000002042_1086_20200427_10000015_52\",\"updateById\":\"12888\",\"lineCode\":1,\"skuName\":\"小骆驼羊奶500g\",\"createTime\":\"2020-04-27 16:29:31\",\"createById\":\"12888\",\"actualTotalCount\":8,\"skuCode\":\"10000002042\"},{\"supplierName\":\"乐高（亚洲中国）智力开发有限公司\",\"createByName\":\"李三\",\"updateByName\":\"李三\",\"batchCode\":\"20200427\",\"updateTime\":\"2020-04-27 16:29:31\",\"supplierCode\":\"10000015\",\"totalCount\":2,\"productDate\":\"2020-04-27\",\"batchInfoCode\":\"10000002042_1086_20200427_10000015_0\",\"updateById\":\"12888\",\"lineCode\":1,\"skuName\":\"小骆驼羊奶500g\",\"createTime\":\"2020-04-27 16:29:31\",\"createById\":\"12888\",\"actualTotalCount\":2,\"skuCode\":\"10000002042\"}],\"supplierCode\":\"10000007\",\"detailList\":[{\"lineCode\":\"1\",\"skuName\":\"最后一次\",\"colorName\":\"\",\"inboundUnitCode\":\"2\",\"inboundUnitName\":\"板\",\"modelNumber\":\"\",\"inboundNum\":\"0\",\"totalCount\":12,\"skuCode\":\"10000002042\"},{\"lineCode\":\"2\",\"skuName\":\"最后一次\",\"colorName\":\"\",\"inboundUnitCode\":\"2\",\"inboundUnitName\":\"板\",\"modelNumber\":\"\",\"inboundNum\":\"0\",\"totalCount\":7,\"skuCode\":\"10000002042\"}],\"warehouseName\":\"科捷天猫销售库\",\"warehouseCode\":\"1044\",\"preArrivalTime\":\"2020-05-13 12:14:47\",\"purchaseOrderCode\":\"100000332\",\"operuserCode\":\"12211\",\"operuserDate\":\"2020-05-06 13:42:53\",\"inboundOderCode\":\"10000033201\"}\t");

        com.aiqin.ground.util.protocol.http.HttpResponse result = httpClient.action().result(com.aiqin.ground.util.protocol.http.HttpResponse.class);
        System.out.printf("result=="+result.toString());

    }

}
