package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description 出入库明细信息
 * @date 2019-08-02
 */
@Data
@ApiModel("出入库明细信息1")
public class StorageDetail {
    /**
     * sku编码
     */
    @JsonProperty("sku_code")
    @ApiModelProperty("sku编码")
    private String skuCode;
    /**
     * sku名称
     */
    @JsonProperty("sku_name")
    @ApiModelProperty("sku名称")
    private String skuName;
    /**
     * sap物料编码
     */
    @JsonProperty("sap_sku_code")
    @ApiModelProperty(value = "sap物料编码", hidden = true)
    private String sapSkuCode;
    /**
     * 库位号
     */
    @JsonProperty("storage_location_code")
    @ApiModelProperty("库位号")
    private String storageLocationCode;
    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;
    /**
     * 颜色规格型号冗余信息
     */
    @JsonProperty("sku_desc")
    @ApiModelProperty("颜色规格型号冗余信息")
    private String skuDesc;
    /**
     * 单位含量
     */
    @JsonProperty("unit_count")
    @ApiModelProperty("单位含量")
    private Integer unitCount;
    /**
     * 交易倍数
     */
    @JsonProperty("trade_exponent")
    @ApiModelProperty("交易倍数")
    private Integer tradeExponent;
    /**
     * 税率
     */
    @JsonProperty("tax_rate")
    @ApiModelProperty("税率")
    private Integer taxRate;
    /**
     * 预计数量
     */
    @JsonProperty("expect_count")
    @ApiModelProperty("预计数量")
    private Integer expectCount;
    /**
     * 预计最小单位数量
     */
    @JsonProperty("expect_min_unit_count")
    @ApiModelProperty("预计最小单位数量")
    private Integer expectMinUnitCount;
    /**
     * 预计含税单价
     */
    @JsonProperty("expect_tax_price")
    @ApiModelProperty("预计含税单价")
    private Integer expectTaxPrice;
    /**
     * 实际数量
     */
    @JsonProperty("single_count")
    @ApiModelProperty("实际数量")
    private Integer singleCount;
    /**
     * 实际最小单位数量
     */
    @JsonProperty("min_unit_count")
    @ApiModelProperty("实际最小单位数量")
    private Integer minUnitCount;
    /**
     * 实际含税单价
     */
    @JsonProperty("tax_price")
    @ApiModelProperty("实际含税单价")
    private String taxPrice;

    /**
     * 厂商指导价
     */
    @JsonProperty("guide_price")
    @ApiModelProperty("厂商指导价")
    private String guidePrice;
}
