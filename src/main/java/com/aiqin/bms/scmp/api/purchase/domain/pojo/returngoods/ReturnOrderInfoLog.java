package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("退货订单日志表")
@Data
public class ReturnOrderInfoLog {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("状态描述")
    @JsonProperty("status_desc")
    private String statusDesc;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作时间")
    @JsonProperty("operator_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

}