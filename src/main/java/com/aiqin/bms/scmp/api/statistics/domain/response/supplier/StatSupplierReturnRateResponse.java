package com.aiqin.bms.scmp.api.statistics.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2019-09-10
 **/
@Data
public class StatSupplierReturnRateResponse {

    @ApiModelProperty(value="仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="退货数量")
    @JsonProperty("single_count")
    private Long singleCount;

    @ApiModelProperty(value="退货金额")
    @JsonProperty("amt")
    private Long amt;

    @ApiModelProperty(value="销售金额")
    @JsonProperty("sales_amount")
    private Long salesAmount;

    @ApiModelProperty(value="退货率")
    @JsonProperty("return_rate")
    private BigDecimal returnRate;
}
