package com.aiqin.bms.scmp.api.purchase.domain.bi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class BiStockoutDetail {

    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="是否缺货（0 为缺货，1为 不缺货）")
    @JsonProperty("is_stockout")
    private Integer isStockout;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonProperty("update_time")
    private Date updateTime;
}