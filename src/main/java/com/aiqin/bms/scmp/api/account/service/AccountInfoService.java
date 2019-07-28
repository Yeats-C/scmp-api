package com.aiqin.bms.scmp.api.account.service;

import com.aiqin.bms.scmp.api.account.domain.Account;
import com.aiqin.bms.scmp.api.account.domain.request.AccountRequest;
import com.aiqin.bms.scmp.api.account.domain.response.AccountResponse;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.supplier.domain.response.account.Role;
import com.aiqin.ground.util.protocol.http.HttpResponse;

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
public interface AccountInfoService {
    HttpResponse<PageResData<Account>> accountList(AccountRequest account);

    HttpResponse addAccount(AccountRequest request, String ticket, String personId);

    HttpResponse<List<Role>> selectRole(String personId, String ticket);

    HttpResponse updateAccount(AccountRequest account, String ticket, String personId);

    HttpResponse<AccountResponse> accountInfo(String username);

}
