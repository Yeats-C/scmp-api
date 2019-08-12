package com.aiqin.bms.scmp.api.dao.test.account;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.account.domain.request.AccountRequest;
import com.aiqin.bms.scmp.api.account.service.AccountInfoService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
        HttpResponse response = accountInfoService.selectRole("11976","635ff2f7a55d4b5baf7d8cf0a974df7f");
    }

    @Test
    public void addAccount(){
        AccountRequest account =    new AccountRequest();
        account.setAccountName("123213");
        account.setCreateById("123213");
        account.setCreateByName("创建人");
        account.setGender(1);
        account.setMobile("123213");
        account.setPassword("2134123");
        account.setRemark("123213");
        account.setRoleIds(Arrays.asList("JS0049","JS0048"));
        account.setSupplierCode("10000080");
        account.setSupplierName("XQ供应商0012");
        HttpResponse response = accountInfoService.addAccount(account, "3f54b58dbe5747c1910930269ad5f8e9","11976");
        System.out.println(response.getCode());
    }



}
