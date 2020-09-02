package com.aiqin.bms.scmp.api.dao.test.abutment;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import org.junit.Test;

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
public class SapAbutment extends SpringBootTestContext {

    @Resource
    private SapBaseDataService sapBaseDataService;

    @Test
    public void saleOrderAbutment() {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime("2019-09-08 00:00:00");
        sapOrderRequest.setFinishTime("2019-09-010 00:00:00");
        sapBaseDataService.saleSynchronization(sapOrderRequest);
    }

    @Test
    public void stockSynchronization() {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime("2019-09-19 00:00:00");
        sapOrderRequest.setFinishTime("2019-09-20 00:00:00");
        sapBaseDataService.stockSynchronization(sapOrderRequest);
    }

    @Test
    public void purchaseSynchronization() {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime("2019-09-16 00:00:00");
        sapOrderRequest.setFinishTime("2019-09-17 00:00:00");
        //sapBaseDataService.purchaseSynchronization(sapOrderRequest);
    }

    @Test
    public void productSynchronization() {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime("2019-09-12 00:00:00");
        sapOrderRequest.setFinishTime("2019-09-13 00:00:00");
        sapBaseDataService.productSynchronization(sapOrderRequest);
    }

    @Test
    public void supplySynchronization() {
        SapOrderRequest sapOrderRequest = new SapOrderRequest();
        sapOrderRequest.setBeginTime("2019-09-19 00:00:00");
        sapOrderRequest.setFinishTime("2019-09-20 00:00:00");
        //sapBaseDataService.supplySynchronization(sapOrderRequest);
    }
}
