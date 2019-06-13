package com.aiqin.bms.scmp.api.api.store;

import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryStoreReqVO;
import com.aiqin.bms.scmp.api.supplier.service.impl.SupplierBaseServiceImpl;
import com.aiqin.bms.scmp.api.util.HttpClientHelper;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-16
 * @time: 15:29
 */
@Service
@Slf4j
public class StoreApi extends SupplierBaseServiceImpl {
    /**
     * 获取门店列表
     * @author NullPointException
     * @date 2019/5/16
     * @param req
     * @return com.aiqin.ground.util.protocol.http.HttpResponse<java.util.List<org.apache.catalina.Store>>
     */
    public HttpResponse storeList(QueryStoreReqVO req) {
        String url = getStoreApiUrl("/operate/store/info");
        HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(url));
        httpClient.addParameter("condition",req.getCondition());
        httpClient.addParameter("page_no",req.getPageNo().toString());
        httpClient.addParameter("page_size",req.getPageSize().toString());
        if(StringUtils.isNotBlank(req.getProvinceId())){
            httpClient.addParameter("province_id",req.getProvinceId());
        }
        if(StringUtils.isNotBlank(req.getCityId())){
            httpClient.addParameter("city_id",req.getCityId());
        }
        if(StringUtils.isNotBlank(req.getDistrictId())){
            httpClient.addParameter("district_id",req.getDistrictId());
        }
        HttpResponse result = httpClient.action().result(HttpResponse.class);
        if(Objects.isNull(result)|| !MsgStatus.SUCCESS.equals(result.getCode())){
            log.error("查询门店数据异常！传入参数是:[{}]", JSONObject.toJSONString(req));
            throw new BizException(MessageId.create(Project.SUPPLIER_API,98,"查询数据异常"));
        }
        return result;
    }
}
