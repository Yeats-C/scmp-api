package com.aiqin.bms.scmp.api.purchase.domain.request.transport;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("运输管理查询实体")
public class TransportRequest extends PageReq {
    @ApiModelProperty("客户名称")
    @JsonProperty(value = "customer_name")
    private String customerName;

    @ApiModelProperty("客户编码")
    @JsonProperty(value = "customer_code")
    private String customerCode;

    @ApiModelProperty("运输单号")
    @JsonProperty("transport_code")
    private String transportCode;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("物流公司编码")
    @JsonProperty("logistics_company")
    private String logisticsCompany;

    @ApiModelProperty("物流公司名称")
    @JsonProperty("logistics_company_name")
    private String logisticsCompanyName;

    @ApiModelProperty("运输单状态")
    @JsonProperty("status")
    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始创建时间")
    @JsonProperty("begin_create_time")
    private Date beginCreateTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束创建时间")
    @JsonProperty("finish_create_time")
    private Date finishCreateTime;
}
