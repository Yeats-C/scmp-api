package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.abutment.domain.response.DLResponse;
import com.aiqin.ground.util.http.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@Slf4j
public class DLHttpClientUtil {

    @Value("${dl.key}")
    private String key;

    public DLResponse HttpHandler1(String data, String url){
        String sign = DLRequestUtil.EncoderByMd5(key, data);
        HttpClient httpClient = HttpClient.post(url, "UTF-8");
        httpClient.setHeader("Content-Encoding", "UTF-8");
        httpClient.setHeader("key", key);//双方约定的密钥
        httpClient.setHeader("sign", sign);
        try {
            httpClient.addParameter("data", URLEncoder.encode(data, "UTF-8")).timeout(60000);
        } catch (UnsupportedEncodingException e) {
            log.info("熙耘->DL，调用失败:{}", e.getMessage());
        }
        return httpClient.action().result(DLResponse.class);
    }

}
