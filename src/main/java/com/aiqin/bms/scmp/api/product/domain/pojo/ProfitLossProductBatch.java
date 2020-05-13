package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("损益批次商品管理")
@Data
public class ProfitLossProductBatch extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("仓库编号")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("调拨单编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品类")
    @JsonProperty("category")
    private String category;

    @ApiModelProperty("品牌")
    @JsonProperty("brand")
    private String brand;

    @ApiModelProperty("颜色")
    @JsonProperty("color")
    private String color;

    @ApiModelProperty("规格")
    @JsonProperty("specification")
    private String specification;

    @ApiModelProperty("型号")
    @JsonProperty("model")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    @JsonProperty("unit")
    private String unit;

    @ApiModelProperty("类别")
    @JsonProperty("classes")
    private String classes;

    @ApiModelProperty("类型")
    @JsonProperty("type")
    private String type;

    @ApiModelProperty("库存单位")
    @JsonProperty("inventory_unit")
    private String inventoryUnit;

    @ApiModelProperty("库存")
    @JsonProperty("inventory")
    private Long inventory;

    @ApiModelProperty("税率")
    @JsonProperty("tax")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    @JsonProperty("tax_price")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    @JsonProperty("tax_amount")
    private BigDecimal taxAmount;

    @ApiModelProperty("图片地址")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty("行号")
    @JsonProperty("line_num")
    private Long lineNum;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_number")
    private String batchNumber;

    @ApiModelProperty("批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty("生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="创建者")
    @JsonProperty("create_by_name")
    private String createByName;

}