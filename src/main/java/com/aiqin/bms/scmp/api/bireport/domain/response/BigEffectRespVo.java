package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("大效期respVo")
@Data
public class BigEffectRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("采购组编码")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supply_unit_code")
    private String supplyUnitCode;

    @ApiModelProperty("供应商")
    @JsonProperty("supply_unit_name")
    private String supplyUnitName;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("生产日期")
    @JsonProperty("production_date")
    private String productionDate;

    @ApiModelProperty("截止日期")
    @JsonProperty("end_date")
    private String endDate;

    @ApiModelProperty("保质期/日")
    @JsonProperty("quality_number")
    private String qualityNumber;

    @ApiModelProperty("状态")
    @JsonProperty("big_effect_period_warn_day")
    private Integer bigEffectPeriodWarnDay;

    @ApiModelProperty("仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;
}
