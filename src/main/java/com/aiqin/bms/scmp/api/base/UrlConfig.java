package com.aiqin.bms.scmp.api.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @功能说明:服务器地址配置
 * @author: wangxu
 * @date: 2018/12/17 0017 13:49
 */
@Component
public class UrlConfig {

    @Value("${Central.url}")
    public String CENTRAL_URL;

    @Value("${Order.api}")
    public String Order_URL;

    @Value("${Store.url}")
    public String STORE_API_URL;

    @Value("${wms.url}")
    public String WMS_API_URL;

}
