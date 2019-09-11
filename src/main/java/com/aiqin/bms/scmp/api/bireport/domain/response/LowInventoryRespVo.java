package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiModel("低库存respVo")
@Data
public class LowInventoryRespVo {

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

    @ApiModelProperty("上周全国合计_总SKU数量")
    @JsonProperty("szqg_total_sku_num")
    private Long szqgTotalSkuNum;

    @ApiModelProperty("上周全国合计_低库存SKU数量")
    @JsonProperty("szqg_low_sku_num")
    private Long szqgLowSkuNum;

    @ApiModelProperty("上周全国合计_低库存占比")
    @JsonProperty("szqg_low_inventory_ratio")
    private BigDecimal szqgLowInventoryRatio;

    @ApiModelProperty("本周全国合计_总SKU数量")
    @JsonProperty("bzqg_total_sku_num")
    private Long bzqgTotalSkuNum;

    @ApiModelProperty("本周全国合计_低库存SKU数量")
    @JsonProperty("bzqg_low_sku_num")
    private Long bzqgLowSkuNum;

    @ApiModelProperty("本周全国合计_低库存占比")
    @JsonProperty("bzqg_low_inventory_ratio")
    private BigDecimal bzqgLowInventoryRatio;

    @ApiModelProperty("华北仓_总SKU数量")
    @JsonProperty("hb_total_sku_num")
    private Long hbTotalSkuNum;

    @ApiModelProperty("华北仓_低库存SKU数量")
    @JsonProperty("hb_low_sku_num")
    private Long hbLowSkuNum;

    @ApiModelProperty("华北仓_低库存占比")
    @JsonProperty("hb_low_inventory_ratio")
    private BigDecimal hbLowInventoryRatio;

    @ApiModelProperty("华南仓_总SKU数量")
    @JsonProperty("hn_total_sku_num")
    private Long hnTotalSkuNum;

    @ApiModelProperty("华南仓_低库存SKU数量")
    @JsonProperty("hn_low_sku_num")
    private Long hnLowSkuNum;

    @ApiModelProperty("华南仓_低库存占比")
    @JsonProperty("hn_low_inventory_ratio")
    private BigDecimal hnLowInventoryRatio;

    @ApiModelProperty("西南仓_总SKU数量")
    @JsonProperty("xn_total_sku_num")
    private Long xnTotalSkuNum;

    @ApiModelProperty("西南仓_低库存SKU数量")
    @JsonProperty("xn_low_sku_num")
    private Long xnLowSkuNum;

    @ApiModelProperty("西南仓_低库存占比")
    @JsonProperty("xn_low_inventory_ratio")
    private BigDecimal xnLowInventoryRatio;

    @ApiModelProperty("华东仓_总SKU数量")
    @JsonProperty("hd_total_sku_num")
    private Long hdTotalSkuNum;

    @ApiModelProperty("华东仓_低库存SKU数量")
    @JsonProperty("hd_low_sku_num")
    private Long hdLowSkuNum;

    @ApiModelProperty("华东仓_低库存占比")
    @JsonProperty("hd_low_inventory_ratio")
    private BigDecimal hdLowInventoryRatio;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
