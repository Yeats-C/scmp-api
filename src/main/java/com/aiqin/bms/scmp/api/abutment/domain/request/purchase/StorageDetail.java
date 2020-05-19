package com.aiqin.bms.scmp.api.abutment.domain.request.purchase;

import java.util.List;

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

    @JsonProperty("sku_code")
    @ApiModelProperty("sku编码")
    private String skuCode;

    @JsonProperty("sku_name")
    @ApiModelProperty("sku名称")
    private String skuName;

    @JsonProperty("sap_sku_code")
    @ApiModelProperty(value = "sap物料编码", hidden = true)
    private String sapSkuCode;

    @JsonProperty("storage_location_code")
    @ApiModelProperty("库位号")
    private String storageLocationCode;

    @ApiModelProperty("单位")
    private String unit;

    @JsonProperty("sku_desc")
    @ApiModelProperty("颜色规格型号冗余信息")
    private String skuDesc;

    @JsonProperty("product_type")
    @ApiModelProperty("商品类型0 商品  5 实物返 10  赠品")
    private Integer productType;

    @JsonProperty("unit_count")
    @ApiModelProperty("单位含量")
    private Integer unitCount;

    @JsonProperty("trade_exponent")
    @ApiModelProperty("交易倍数")
    private Integer tradeExponent;

    @JsonProperty("tax_rate")
    @ApiModelProperty("税率")
    private Integer taxRate;

    @JsonProperty("expect_count")
    @ApiModelProperty("预计数量")
    private Integer expectCount;

    @JsonProperty("expect_min_unit_count")
    @ApiModelProperty("预计最小单位数量")
    private Integer expectMinUnitCount;

    @JsonProperty("expect_tax_price")
    @ApiModelProperty("预计含税单价")
    private String expectTaxPrice;

    @JsonProperty("single_count")
    @ApiModelProperty("实际数量")
    private Integer singleCount;

    @JsonProperty("min_unit_count")
    @ApiModelProperty("实际最小单位数量")
    private Integer minUnitCount;

    @JsonProperty("tax_price")
    @ApiModelProperty("实际含税单价")
    private String taxPrice;

    @JsonProperty("guide_price")
    @ApiModelProperty("厂商指导价")
    private String guidePrice;

    @JsonProperty("supplier_code")
    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @JsonProperty("supplier_name")
    @ApiModelProperty("供应商名称")
    private String supplierName;

    @JsonProperty("brand_code")
    @ApiModelProperty("品牌编码")
    private String brandCode;

    @JsonProperty("brand_name")
    @ApiModelProperty("品牌名称")
    private String brandName;

    @JsonProperty("category_code")
    @ApiModelProperty("品类编码")
    private String categoryCode;

    @JsonProperty("category_name")
    @ApiModelProperty("品类名称")
    private String categoryName;

    @JsonProperty("batch_list")
    @ApiModelProperty("批次信息")
    private List<ScmpStorageBatch> batchList;
    
}
