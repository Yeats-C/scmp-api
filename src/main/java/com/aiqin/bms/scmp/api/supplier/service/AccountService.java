package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.response.account.Account;

/**
 * @author knight.xie
 * @version 1.0
 * @className AccountService
 * @date 2019/3/20 20:35
 * @description 根据accountId获取信息
 */
public interface AccountService {
    /**
     * 根据accountId获取信息
     * @param accountId
     * @return
     */
    Account getAccountInfoByAccountId(String accountId);
}
