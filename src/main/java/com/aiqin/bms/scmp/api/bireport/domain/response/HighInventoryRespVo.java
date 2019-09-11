package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiModel("高库存respVo")
@Data
public class HighInventoryRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("采购组编码")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("负责人编码")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("负责人名称")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty("上周全国合计_高库存金额")
    @JsonProperty("szqg_high_inventory_amount")
    private Long szqgHighInventoryAmount;

    @ApiModelProperty("上周全国合计_库存总金额")
    @JsonProperty("szqg_total_inventory_amount")
    private Long szqgTotalInventoryAmount;

    @ApiModelProperty("上周全国合计_高库存占比")
    @JsonProperty("szqg_rate")
    private BigDecimal szqgRate;

    @ApiModelProperty("本周全国合计_高库存金额")
    @JsonProperty("bzqg_high_inventory_amount")
    private Long bzqgHighInventoryAmount;

    @ApiModelProperty("本周全国合计_库存总金额")
    @JsonProperty("bzqg_total_inventory_amount")
    private Long bzqgTotalInventoryAmount;

    @ApiModelProperty("本周全国合计_高库存占比")
    @JsonProperty("bzqg_rate")
    private BigDecimal bzqgRate;

    @ApiModelProperty("华北仓_高库存金额")
    @JsonProperty("hb_high_inventory_amount")
    private Long hbHighInventoryAmount;

    @ApiModelProperty("华北仓_库存总金额")
    @JsonProperty("hb_total_inventory_amount")
    private Long hbTotalInventoryAmount;

    @ApiModelProperty("华北仓_高库存占比")
    @JsonProperty("hb_rate")
    private BigDecimal hbRate;

    @ApiModelProperty("华南仓_高库存金额")
    @JsonProperty("hn_high_inventory_amount")
    private Long hnHighInventoryAmount;

    @ApiModelProperty("华南仓_库存总金额")
    @JsonProperty("hn_total_inventory_amount")
    private Long hnTotalInventoryAmount;

    @ApiModelProperty("华南仓_高库存占比")
    @JsonProperty("hn_rate")
    private BigDecimal hnRate;

    @ApiModelProperty("西南仓_高库存金额")
    @JsonProperty("xn_high_inventory_amount")
    private Long xnHighInventoryAmount;

    @ApiModelProperty("西南仓_库存总金额")
    @JsonProperty("xn_total_inventory_amount")
    private Long xnTotalInventoryAmount;

    @ApiModelProperty("西南仓_高库存占比")
    @JsonProperty("xn_rate")
    private BigDecimal xnRate;

    @ApiModelProperty("华东仓_高库存金额")
    @JsonProperty("hd_high_inventory_amount")
    private Long hdHhighInventoryAmount;

    @ApiModelProperty("华东仓_库存总金额")
    @JsonProperty("hd_total_inventory_amount")
    private Long hdTotalInventoryAmount;

    @ApiModelProperty("华东仓_高库存占比")
    @JsonProperty("hd_rate")
    private BigDecimal hdRate;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
