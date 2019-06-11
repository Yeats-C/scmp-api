package com.aiqin.bms.scmp.api.product.domain.request.sku.oms;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/25 0025 11:05
 */
@Data
@ApiModel("查询oms的sku列表请求参数")
public class SearchOmsSkuListReqVO extends PageReq{
    @JsonProperty("request_time")
    @ApiModelProperty("请求时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

    @JsonProperty("kew_word")
    @ApiModelProperty("关键字sku名称或编码")
    private String keyword;
}
