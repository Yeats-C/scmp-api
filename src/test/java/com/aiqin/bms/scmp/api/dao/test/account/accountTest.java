package com.aiqin.bms.scmp.api.dao.test.account;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.account.service.AccountInfoService;
import com.aiqin.bms.scmp.api.dao.test.reject.AsyncService;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectApprovalService;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

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
public class accountTest extends SpringBootTestContext {
    private static Logger LOGGER = LoggerFactory.getLogger(accountTest.class);
    @Resource
    private AccountInfoService accountInfoService;


    @Test
    public void rejectApplyListInfo(){
        HttpResponse response = accountInfoService.selectRole("11976","bf9208cd083b4d62ba788d9315bd7370");
    }



}
