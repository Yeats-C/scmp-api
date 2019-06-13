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

    @Value("${Store.url}")
    public String STORE_API_URL;
    /**
     * 工作流
     */
    @Value("${Workflow.url}")
    public String WORKFLOW_URL;
    /**
     * 工作流撤销接口
     */
    @Value("${Workflow.cancelUrl}")
    public String WORKFLOW_CANCEL_URL;

    /**
     * 加密key
     */
    @Value("${Encryption.key}")
    public String ENCRYPTION_KEY;


    /**
     * 用户名,暂且写死,后期根据登陆用户获取
     */
    @Value("${UserName.key}")
    public String USER_NAME_KEY;

    /**
     * 用户名当前职位,暂且写死,后期根据登陆用户获取
     */
    @Value("${CurrentPositionCode.key}")
    public String CURRENT_POSITION_CODE_KEY;


    @Value("${WMS.url}")
    public String WMS_API_URL;

    /**
     * 订单中心api地址
     */
    @Value("${order.info.url}")
    public String ORDER_API_URL;
}
