package com.aiqin.bms.scmp.api.supplier.domain.response.variableprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("价格日志")
public class PriceDetailedLogResponse {

    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("价格类型")
    private String priceTypeName;
    @ApiModelProperty("金额")
    private Long taxAmount;

    @ApiModelProperty("生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeEffectTime;

    @ApiModelProperty("失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date failureTime;
    @ApiModelProperty("操作时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("操作人")
    private String createBy;


    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("备注")
    private String remark;



}
