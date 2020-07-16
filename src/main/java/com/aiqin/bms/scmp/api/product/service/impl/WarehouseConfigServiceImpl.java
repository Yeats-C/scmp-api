package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.dao.WarehouseConfigDao;
import com.aiqin.bms.scmp.api.product.domain.request.WarehouseConfigReq;
import com.aiqin.bms.scmp.api.product.domain.response.WarehouseConfigResp;
import com.aiqin.bms.scmp.api.product.service.WarehouseConfigService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Auther: mamingze
 * @Date: 2020-04-09 17:39
 * @Description:
 */
@Service
@Slf4j
public class WarehouseConfigServiceImpl implements WarehouseConfigService {
    @Autowired
    private WarehouseConfigDao warehouseConfigDao;
    @Autowired
    private UrlConfig urlConfig;
    @Override
    public BasePage<WarehouseConfigResp> getPage(WarehouseConfigReq warehouseConfigReq) {
        try {
//            PageHelper.startPage(warehouseConfigReq.getPageNo(),warehouseConfigReq.getPageSize());
//            List<WarehouseConfigResp> warehouseConfigRespPageInfo=warehouseConfigDao.getList(warehouseConfigReq);
//            return PageUtil.getPageList(warehouseConfigReq.getPageNo(),warehouseConfigRespPageInfo);

            StringBuilder url = new StringBuilder();
            url.append(urlConfig.WMS_API_URL2).append("/storehouseConfig/search/page" );
//            HttpClient httpClient = HttpClient.get(url.toString());
            HttpClient httpClient = HttpClient.post(String.valueOf(url)).json(warehouseConfigReq).timeout(30000);
            HttpResponse<BasePage<WarehouseConfigResp>> result = httpClient.action().result(new TypeReference<HttpResponse<BasePage<WarehouseConfigResp>>>(){
            });
            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                log.info("传入wmsa失败，传入参数是[{}]", JSON.toJSONString(warehouseConfigReq));
            }
            return result.getData();
        } catch (Exception e) {
            log.error("查询仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public Boolean save(WarehouseConfigReq warehouseConfigReq) {

       AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        warehouseConfigReq.setCreateBy(currentAuthToken.getPersonName());
        warehouseConfigReq.setCreateById(currentAuthToken.getPersonId());
        warehouseConfigReq.setUpdateBy(currentAuthToken.getPersonName());
        warehouseConfigReq.setUpdateById(currentAuthToken.getPersonId());
        try {
            log.info("库房配置保存传入wmsa，传入参数是[{}]", JSON.toJSONString(warehouseConfigReq));
           warehouseConfigDao.insert(warehouseConfigReq);
            StringBuilder url = new StringBuilder();
            url.append(urlConfig.WMS_API_URL2).append("/storehouseConfig/save" );
//            HttpClient httpClient = HttpClient.get(url.toString());
            HttpClient httpClient = HttpClient.post(String.valueOf(url)).json(warehouseConfigReq).timeout(30000);
            HttpResponse<Boolean> result = httpClient.action().result(new TypeReference<HttpResponse<Boolean>>(){
            });
            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                log.info("传入wmsa失败，传入参数是[{}]", JSON.toJSONString(warehouseConfigReq));
            }
            return result.getData();
        } catch (Exception e) {
            log.error("新增仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public WarehouseConfigResp load(Long id) {
        try {
            StringBuilder url = new StringBuilder();
            url.append(urlConfig.WMS_API_URL2).append("/storehouseConfig/load" );
//            HttpClient httpClient = HttpClient.get(url.toString());
            HttpClient httpClient = HttpClient.get(String.valueOf(url)).timeout(30000);
            httpClient.addParameter("id", String.valueOf(id));
            HttpResponse<WarehouseConfigResp> result = httpClient.action().result(new TypeReference<HttpResponse<WarehouseConfigResp>>(){
            });
            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                log.info("传入wmsa失败，传入参数是[{}]", id);
            }
            return result.getData();
        } catch (Exception e) {
            log.error("查询仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public Boolean update(WarehouseConfigReq req) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        req.setUpdateBy(currentAuthToken.getPersonName());
        try {
            log.info("库房配置更新传入wmsa，传入参数是[{}]", JSON.toJSONString(req));
            StringBuilder url = new StringBuilder();
            url.append(urlConfig.WMS_API_URL2).append("/storehouseConfig/update" );
//            HttpClient httpClient = HttpClient.get(url.toString());
            HttpClient httpClient = HttpClient.post(String.valueOf(url)).json(req).timeout(30000);
            HttpResponse<Boolean> result = httpClient.action().result(new TypeReference<HttpResponse<Boolean>>(){
            });
            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                log.info("穿入wmsa失败，传入参数是[{}]", JSON.toJSONString(req));
            }
            return result.getData();
        } catch (Exception e) {
            log.error("修改仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public WarehouseConfigResp refresh(String warehouseCode) {
        try {
           return warehouseConfigDao.refresh(warehouseCode);

        } catch (Exception e) {
            log.error("修改仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());
        }
    }
}
