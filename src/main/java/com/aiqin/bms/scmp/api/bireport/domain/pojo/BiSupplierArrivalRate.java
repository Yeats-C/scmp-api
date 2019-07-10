package com.aiqin.bms.scmp.api.bireport.domain.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商到货率实体Model")
@Data
public class BiSupplierArrivalRate {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("入库时间")
    @JsonProperty("inbound_time")
    private String inboundTime;

    @ApiModelProperty("供应商编号")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("一级品类编号")
    @JsonProperty("category_level_code")
    private String categoryLevelCode;

    @ApiModelProperty("一级品类名称")
    @JsonProperty("category_level_name")
    private String categoryLevelName;

    @ApiModelProperty("仓库名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty("采购组负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty("采购组负责人编号")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("订货数量")
    @JsonProperty("pre_inbound_num")
    private String preInboundNum;

    @ApiModelProperty("订货金额")
    @JsonProperty("pre_tax_amount")
    private String preTaxAmount;

    @ApiModelProperty("入库数量")
    @JsonProperty("pra_inbound_num")
    private String praInboundNum;

    @ApiModelProperty("入库金额")
    @JsonProperty("pra_tax_amount")
    private String praTaxAmount;

    @ApiModelProperty("入库金额满足率")
    @JsonProperty("pra_tax_amount_rate")
    private String praTaxAmountRate;

}
