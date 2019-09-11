package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.supplier.service.BankService;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述: 银行接口实现层
 *
 * @Author: Kt.w
 * @Date: 2018/12/29
 * @Version 1.0
 * @since 1.0
 */
@Service
public class BankServiceImpl implements BankService {
    @Autowired
     private UrlConfig urlConfig;

    @Override
    public HttpResponse getBankList(String bank_name) {
        StringBuilder  stringBuilder = new StringBuilder();
        stringBuilder.append(urlConfig.CENTRAL_URL).append("/bank/info/find/bank");
        BasicNameValuePair param1 = new BasicNameValuePair("bank_name",bank_name);
        BasicNameValuePair param2 = new BasicNameValuePair("page_no", "1");
        BasicNameValuePair param3 = new BasicNameValuePair("page_size", "10");
        HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(stringBuilder.toString()).addParameters(param1,param2,param3));
        HttpResponse orderDto = httpClient.action().result(HttpResponse.class);

        return orderDto;
    }
}
