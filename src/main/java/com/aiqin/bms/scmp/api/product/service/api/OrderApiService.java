package com.aiqin.bms.scmp.api.product.service.api;

import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Createed by sunx on 2019/4/9.<br/>
 */
@Service
@Slf4j
public class OrderApiService {

    @Autowired
    private UrlConfig urlConfig;

    public List<String> getDisUnsoldProductSkuCodes(String distributorId) {

        List<String> re = Lists.newArrayList();

        String url = urlConfig.ORDER_API_URL + "order/statistical/product/unsold";

        HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(url).addParameter("distributor_id", distributorId));

        HttpResponse<List<String>> httpResponse = httpClient.action().result(new TypeReference<HttpResponse<List<String>>>() {
        });

        if (Objects.nonNull(httpResponse) && Objects.equals("0", httpResponse.getCode())) {
            return httpResponse.getData();
        }
        return re;
    }
}
