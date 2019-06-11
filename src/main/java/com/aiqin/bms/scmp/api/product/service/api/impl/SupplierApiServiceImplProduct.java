package com.aiqin.bms.scmp.api.product.service.api.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.product.domain.request.WarehouseListReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.product.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.product.service.api.SupplierApiService;
import com.aiqin.bms.scmp.api.product.service.impl.ProductBaseServiceImpl;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-13
 * @time: 17:41
 */
@Service
@Slf4j
public class SupplierApiServiceImplProduct extends ProductBaseServiceImpl implements SupplierApiService {

    @Override
    public WarehouseResVo getWarehouseByCode(String code) {
        try {
            String url = getSupplierApiUrl("/warehouse/getWarehouseByCode?authority=0");
            HttpClient client = HttpClientHelper.getCurrentClient(HttpClient.get(url).addParameter("code", code));
            HttpResponse<WarehouseResVo> result = client.action().result(new TypeReference<HttpResponse<WarehouseResVo>>() {
            });
            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                log.info("SupplierApiServiceImplProduct----getWarehouseByCode----调用供应链获取库房详情失败，传入编码是：[{}]", code);
                return null;
            }
            return result.getData();
        } catch (GroundRuntimeException e) {
            e.printStackTrace();
            log.info("SupplierApiServiceImplProduct----getWarehouseByCode----调用供应链获取库房接口失败，传入编码是：[{}]", code);
        }
        return null;
    }

    @Override
    public List<LogisticsCenterApiResVo> getWarehouseApi(WarehouseListReqVo warehouseListReqVo) {
        try {
            String url = getSupplierApiUrl("/warehouse/getWarehouseApi?authority=0");
            HttpClient client = HttpClientHelper.getCurrentClient(HttpClient.post(url).json(warehouseListReqVo));
            HttpResponse<List<LogisticsCenterApiResVo>> result = client.action().result(new TypeReference<HttpResponse<List<LogisticsCenterApiResVo>>>() {
            });
            if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                log.info("SupplierApiServiceImplProduct----getWarehouseApi----调用供应链获根据地区编码查询库房信息失败，传入编码是：[{}]", JSON.toJSONString(warehouseListReqVo));
                return null;
            }
            return result.getData();
        } catch (GroundRuntimeException e) {
            e.printStackTrace();
            log.info("SupplierApiServiceImplProduct----getWarehouseApi----调用供应链获根据地区编码查询库房信息失败，传入编码是：[{}]", JSON.toJSONString(warehouseListReqVo));
        }
        return null;
    }
}
