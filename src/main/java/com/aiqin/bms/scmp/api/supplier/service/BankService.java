package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * 描述:描述: 银行接口接口层
 *
 * @Author: Kt.w
 * @Date: 2018/12/29
 * @Version 1.0
 * @since 1.0
 */
public interface BankService {

    /**
     * 查询银行
     * @param bank_name
     * @return
     */
    HttpResponse getBankList(String bank_name);
}
