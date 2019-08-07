package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ApiModel("供应商到货率respVo")
@Data
public class SupplierArrivalRateRespVo implements Serializable {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("日期时间")
    @JsonProperty("inbound_time")
    private String inboundTime;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商name")
    @JsonProperty(value = "supplier_name")
    private String supplierName;

    @ApiModelProperty("品类编号")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("订货数量")
    @JsonProperty("pre_inbound_num")
    private Long preInboundNum;

    @ApiModelProperty("订货金额")
    @JsonProperty("pre_tax_amount")
    private Long preTaxAmount;

    @ApiModelProperty("入库数量")
    @JsonProperty("pra_inbound_num")
    private Long praInboundNum;

    @ApiModelProperty("入库金额")
    @JsonProperty("pra_tax_amount")
    private Long praTaxAmount;

    @ApiModelProperty("入库金额满足率")
    @JsonProperty("pra_tax_amount_rate")
    private Double praTaxAmountRate;

    @ApiModelProperty("采购组负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty("采购组负责人编码")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("订货数量合计")
    @JsonProperty("pre_inbound_nums")
    private String preInboundNums;

    @ApiModelProperty("订货金额合计")
    @JsonProperty("pre_tax_amounts")
    private Long preTaxAmounts;

    @ApiModelProperty("入库数量合计")
    @JsonProperty("pra_inbound_nums")
    private Long praInboundNums;

    @ApiModelProperty("入库金额合计")
    @JsonProperty("pra_tax_amounts")
    private Long praTaxAmounts;

    @ApiModelProperty("入库金额满足率合计")
    @JsonProperty("pra_tax_amount_rates")
    private Double praTaxAmountRates;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
