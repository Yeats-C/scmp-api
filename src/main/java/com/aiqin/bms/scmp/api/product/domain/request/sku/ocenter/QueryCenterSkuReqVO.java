package com.aiqin.bms.scmp.api.product.domain.request.sku.ocenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/15 0015 11:02
 */
@Data
@ApiModel("运营中心查询sku列表请求")
public class QueryCenterSkuReqVO {

    @ApiModelProperty("sku编码集合")
    @JsonProperty("sku_list")
    private List<String> skuList;

}
