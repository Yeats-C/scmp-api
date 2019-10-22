package com.aiqin.bms.scmp.api.service.test;

import com.aiqin.bms.scmp.api.ScmpApiBootApplication;
import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.purchase.jobs.SynchronizationStockService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 * <p>
 * Date: 21/10/2019
 * Time: 11:44
 *
 * @author heroin.nee@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScmpApiBootApplication.class)
public class SynchronizationStockServiceImplTest {

    @Resource
    private SynchronizationStockService synchronizationStockService;

    @Test
    public void synchronizationStock() {
        PagesRequest request = new PagesRequest();
        request.setPageNo(1);
        request.setPageSize(500);
        synchronizationStockService.synchronizationStock(request);
    }
}
