package com.aiqin.bms.scmp.api.product.domain.request.sku.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/6 0006 14:13
 */
@Data
@ApiModel("微商城查询sku列表请求参数")
public class QueryMerchantSkuListReqVO {

    @ApiModelProperty(value = "sku编码集合")
    @JsonProperty("sku_codes")
    private List<String> skuCodes;

    @JsonProperty("request_time")
    @ApiModelProperty("请求时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;
}
