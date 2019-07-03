package com.aiqin.bms.scmp.api.product.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @className QueryStockBatchSkuRespVo
 * @date 2019/6/27 9:26
 * @description 查询批次库存商品sku返回VO
 *
 */
@ApiModel("查询批次库存商品sku返回VO")
@Data
public class QueryStockBatchSkuRespVo implements Serializable {

    @ApiModelProperty("采购组code")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("商品条形码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("商品id")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("品类id")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌id")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("商品类型")
    @JsonProperty("goods_gifts")
    private Integer goodsGifts;

    @ApiModelProperty("商品颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("商品颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("商品单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("商品单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("税率")
    @JsonProperty("input_tax_rate")
    private Integer inputTaxRate;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("含税单价")
    @JsonProperty("purchase_price")
    private Long  purchasePrice;

    @ApiModelProperty(value = "含税成本")
    @JsonProperty("tax_cost")
    private Long taxCost;

    @ApiModelProperty("商品批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("批次创建时间")
    @JsonProperty("production_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String productionDate;

    @ApiModelProperty("批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty("商品结算方式code")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty("商品结算方式名称")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty("供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("可用库存数")
    @JsonProperty("available_num")
    private Long availableNum;

}
