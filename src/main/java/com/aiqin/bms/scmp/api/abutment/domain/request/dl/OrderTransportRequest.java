package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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

    @ApiModelProperty(value="物流公司单号")
    @JsonProperty("transport_company_number")
    private String transportCompanyNumber;

    @ApiModelProperty(value="送货方式编码")
    @JsonProperty("distribution_mode_code")
    private String distributionModeCode;

    @ApiModelProperty(value="送货方式名称")
    @JsonProperty("distribution_mode_name")
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
    private Long transportTotalCount;

    @ApiModelProperty(value="总体积")
    @JsonProperty("total_volume")
    private Long totalVolume;

    @ApiModelProperty(value="总重量")
    @JsonProperty("total_weight")
    private Long totalWeight;

    @ApiModelProperty(value="备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="销售单号")
    @JsonProperty("order_codes")
    private List<String> orderCodes;
}
