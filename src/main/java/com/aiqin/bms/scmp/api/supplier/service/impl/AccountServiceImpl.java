package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.supplier.domain.response.account.UserDataVo;
import com.aiqin.bms.scmp.api.supplier.service.AccountService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author knight.xie
 * @version 1.0
 * @className AccountServiceImpl
 * @date 2019/3/20 20:38
 * @description TODO
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    /**
     * 根据accountId获取信息
     *
     * @param accountId
     * @return
     */
    @Value("${center.main.url}")
    private String centerMainUrl;
    @Value("${mgs.control.system-code}")
    private String systemCode;
    @Override
    public UserDataVo getAccountInfoByAccountId(String ticket, String personId, String accountId) {
        HttpClient httpClient = HttpClient.get(this.centerMainUrl + "/account/resources/" + accountId + "?ticket=" + ticket + "&ticket_person_id=" + personId+"&system=" + systemCode );
        HttpResponse<UserDataVo> httpResponse =  httpClient.action().result(new TypeReference<HttpResponse<UserDataVo>>() {});
        return httpResponse.getData();
    }
}
