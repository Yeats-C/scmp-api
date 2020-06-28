package com.aiqin.bms.scmp.api.purchase.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("DL- 销售物流单回传参数")
public class OrderTransportRequest {

    @ApiModelProperty(value="物流单号")
    @JsonProperty("transport_code")
    private String transportCode;

    @ApiModelProperty(value="物流公司编码")
    @JsonProperty("transport_company_code")
    private String transportCompanyCode;

    @ApiModelProperty(value="物流公司名称")
    @JsonProperty("transport_company_name")
    private String transportCompanyName;

    @ApiModelProperty(value="物流单号")
    @JsonProperty("transport_company_number")
    private String transportCompanyNumber;

    @ApiModelProperty(value="送货方式编码")
    @JsonProperty("distribution_mode_code")
    private String distributionModeCode;

    @ApiModelProperty(value="送货方式名称")
    @JsonProperty("distribution_mode_code")
    private String distributionModeName;

    @ApiModelProperty(value="标准物流费")
    @JsonProperty("standard_transport_amount")
    private BigDecimal standardTransportAmount;

    @ApiModelProperty(value="选加物流费")
    @JsonProperty("choose_transport_amount")
    private BigDecimal chooseTransportAmount;

    @ApiModelProperty(value="总件数")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="物流包装数")
    @JsonProperty("transport_total_count")
    private String transportTotalCount;

    @ApiModelProperty(value="总体积")
    @JsonProperty("total_volume")
    private String totalVolume;

    @ApiModelProperty(value="总重量")
    @JsonProperty("total_weight")
    private String totalWeight;

    @ApiModelProperty(value="备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="销售单号")
    @JsonProperty("order_codes")
    private List<String> orderCodes;
}
