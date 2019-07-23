package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("商品编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("仓位类型名称编码")
    @JsonProperty("warehouse_type_code")
    private String warehouseTypeCode;

    @ApiModelProperty("仓位类型名称(库房)")
    @JsonProperty("warehouse_type_name")
    private String warehouseTypeName;

    @ApiModelProperty("上周全国合计")
    @JsonProperty("lastweek_total_national")
    private String lastweekTotalNational;

    @ApiModelProperty("本周全国合计")
    @JsonProperty("thisweek_total_national")
    private String thisweekTotalNational;

    @ApiModelProperty("高库存金额")
    @JsonProperty("high_inventory_amount")
    private String highInventoryAmount;

    @ApiModelProperty("库存总金额")
    @JsonProperty("inventory_total_amount")
    private String inventoryTotalAmount;

    @ApiModelProperty("高库存占比")
    @JsonProperty("high_inventory_ratio")
    private Double highInventoryRatio;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
