package com.aiqin.bms.scmp.api.product.domain.response.variableprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("价格日志")
@Data
public class PriceLogResponse {
    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("金额")
    private Long amount;

    @ApiModelProperty("生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeEffectTime;

    @ApiModelProperty("失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date failureTime;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("备注")
    private String remarks;
}
