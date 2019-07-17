package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("大效期request")
@Data
public class BigEffectReqVo extends PageReq implements Serializable {

    @ApiModelProperty("日期")
    @JsonProperty("production_date")
    private String productionDate;

    @ApiModelProperty("商品编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("供应商code")
    @JsonProperty("supply_unit_code")
    private String supplyUnitCode;

    @ApiModelProperty("供应商name")
    @JsonProperty("supply_unit_name")
    private String supplyUnitName;

    @ApiModelProperty("采购组编码")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("状态")
    @JsonProperty("big_effect_period_warn_day")
    private String bigEffectPeriodWarnDay;

    public BigEffectReqVo(String productionDate, String skuCode, String skuName, String productBrandCode, String productBrandName, String productCategoryCode, String productCategoryName, String supplyUnitCode, String supplyUnitName, String procurementSectionCode, String procurementSectionName, String transportCenterCode, String transportCenterName, String warehouseCode, String warehouseName, String bigEffectPeriodWarnDay) {
        this.productionDate = productionDate;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.productBrandCode = productBrandCode;
        this.productBrandName = productBrandName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.supplyUnitCode = supplyUnitCode;
        this.supplyUnitName = supplyUnitName;
        this.procurementSectionCode = procurementSectionCode;
        this.procurementSectionName = procurementSectionName;
        this.transportCenterCode = transportCenterCode;
        this.transportCenterName = transportCenterName;
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.bigEffectPeriodWarnDay = bigEffectPeriodWarnDay;
    }

    public BigEffectReqVo() {
    }
}
