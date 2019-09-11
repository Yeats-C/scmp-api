package com.aiqin.bms.scmp.api.statistics.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: zhao shuai
 * @create: 2019-09-09
 **/
@Data
public class StatSupplierArrivalRateResponse {

    @ApiModelProperty(value="供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="所属部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="采购负责人code")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty(value="采购负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty(value="一级品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="一级品类name")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="预计入库数量")
    @JsonProperty("pre_inbound_num")
    private Long preInboundNum;

    @ApiModelProperty(value="实际入库数量")
    @JsonProperty("pra_inbound_num")
    private Long praInboundNum;

    @ApiModelProperty(value="预计含税金额")
    @JsonProperty("pre_tax_amount")
    private Long preTaxAmount;

    @ApiModelProperty(value="实际含税金额")
    @JsonProperty("pra_tax_amount")
    private Long praTaxAmount;

    @ApiModelProperty(value="入库金额满足率")
    @JsonProperty("inbound_amount_fill_rate")
    private BigDecimal inboundAmountFillRate;

    @ApiModelProperty(value="到货率")
    @JsonProperty("arrival_rate")
    private BigDecimal arrivalRate;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
