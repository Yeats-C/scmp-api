package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.product.domain.response.PositionRespVo;
import com.aiqin.bms.scmp.api.supplier.service.BankService;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.aiqin.ground.util.http.AbstractHttpClient;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.http.HttpMethod;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;


/**
 * 描述: 人员职位查询
 *
 * @Author: xieqing
 * @Date: 2019/12/3
 */
@RestController
@RequestMapping("/position")
@Slf4j
@Api(tags = "职位查询接口")
public class PositionController {
    @Autowired
    private UrlConfig urlConfig;

    @GetMapping("")
    @ApiOperation("获取职位信息")
    public HttpResponse getBankList(String accountId, String page, String size,String ticketPersonId,String personName){
        //http://control.api.aiqin.com/person/user/position/c0ee64bff2e54c639447d458f08935f2?page=1&ticket_person_id=11182&person_name=%E5%BE%90%E5%AD%A6%E6%B5%A9&size=20
        StringBuilder sb = new StringBuilder();
        sb.append(urlConfig.CENTRAL_URL).append("/person/user/position/").append(accountId);
        // HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(sb.toString()));
        HttpClient httpClient = HttpClient.get(sb.toString());
        httpClient.addParameter("page", page);
        httpClient.addParameter("size", size);
        httpClient.addParameter("ticket_person_id", ticketPersonId);
        httpClient.addParameter("person_name", personName);
        HttpResponse<BasePage<PositionRespVo>> response = httpClient.action().result(new TypeReference<HttpResponse<BasePage<PositionRespVo>>>(){
        });
        BasePage<PositionRespVo> positionRespVo = response.getData();
        if (positionRespVo != null) {
            return HttpResponse.successGenerics(positionRespVo);
        }
        return response;
    }
}
