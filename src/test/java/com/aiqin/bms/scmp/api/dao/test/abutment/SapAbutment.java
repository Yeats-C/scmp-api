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
        sapOrderRequest.setBeginTime("2019-09-02 00:00:00");
        sapOrderRequest.setFinishTime("2019-09-06 00:00:00");
        sapBaseDataService.saleSynchronization(sapOrderRequest);
    }
}
