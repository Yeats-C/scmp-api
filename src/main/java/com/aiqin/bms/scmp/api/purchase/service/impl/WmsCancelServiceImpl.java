package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.purchase.domain.request.wms.CancelSource;
import com.aiqin.bms.scmp.api.purchase.service.WmsCancelService;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2020-06-03
 **/
@Service
public class WmsCancelServiceImpl implements WmsCancelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WmsCancelServiceImpl.class);

    @Resource
    private UrlConfig urlConfig;

    @Override
    public HttpResponse wmsCancel(CancelSource cancelSource){
        LOGGER.info("wms取消订单参数：{}", JsonUtil.toJson(cancelSource));
        if(cancelSource == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String url = urlConfig.WMS_API_URL2 + "/source/cancel";
        HttpClient httpClient = HttpClient.post(url).json(cancelSource).timeout(20000);
        HttpResponse response = httpClient.action().result(HttpResponse.class);
        if (response.getCode().equals(MessageId.SUCCESS_CODE)) {
            LOGGER.info("取消wms单据成功");
            return HttpResponse.success();
        } else {
            LOGGER.error("取消wms单据失败:{}", response.getMessage());
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 200, response.getData().toString()));
        }
    }
}
