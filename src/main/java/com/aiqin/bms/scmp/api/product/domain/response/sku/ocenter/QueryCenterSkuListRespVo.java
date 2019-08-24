package com.aiqin.bms.scmp.api.product.domain.response.sku.ocenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryCenterSkuListRespVo
 * @date 2019/4/16 16:28
 */
@Data
@ApiModel("运营中心查询根据sku编码集合查询sku列表返回")
public class QueryCenterSkuListRespVo {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("sku门店库存")
    private Long stock;
}
