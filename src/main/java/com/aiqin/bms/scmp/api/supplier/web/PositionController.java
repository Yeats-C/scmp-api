package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.product.domain.request.QueryPositionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.PositionRespVo;
import com.aiqin.bms.scmp.api.supplier.service.BankService;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 描述: 人员职位查询
 *
 * @Author: xieqing
 * @Date: 2019/12/3
 */
@RestController
@RequestMapping("/position")
@Api(tags = "职位查询接口")
public class PositionController {

    @Autowired
    private BankService bankService;
    @Autowired
    private UrlConfig urlConfig;

    @PostMapping("")
    @ApiOperation("获取职位信息")
    public HttpResponse getBankList(String accountId, String ticketPersonId, String personName, String page, String size){
//http://control.api.aiqin.com/person/user/position/c0ee64bff2e54c639447d458f08935f2?page=1&ticket_person_id=11182&person_name=%E5%BE%90%E5%AD%A6%E6%B5%A9&size=20
        StringBuilder sb = new StringBuilder();
        sb.append(urlConfig.CENTRAL_URL).append("/person/user/position/").append(accountId);
        HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(sb.toString()));
        httpClient.addParameter("page", page);
        httpClient.addParameter("size", size);
        httpClient.addParameter("ticket_person_id", ticketPersonId);
        httpClient.addParameter("person_name", personName);
        HttpResponse<PositionRespVo> response = httpClient.action().result(new TypeReference<HttpResponse<PositionRespVo>>(){
        });
        PositionRespVo positionRespVo = response.getData();
        return HttpResponse.successGenerics(positionRespVo);
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("control.api.aiqin.com/person/user/position/c0ee64bff2e54c639447d458f08935f2");
        HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(sb.toString()));
        httpClient.addParameter("page", "1");
        httpClient.addParameter("size", "20");
        httpClient.addParameter("ticket_person_id", "11182");
        httpClient.addParameter("person_name", "徐学浩");
        HttpResponse<List<PositionRespVo>> response = httpClient.action().result(new TypeReference<HttpResponse<List<PositionRespVo>>>(){
        });
        List<PositionRespVo> respVoList = response.getData();
        System.out.println(respVoList.toString());
    }
}
