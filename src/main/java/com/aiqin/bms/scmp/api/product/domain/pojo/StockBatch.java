package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("批次库存实体Model")
@Data
public class StockBatch extends PagesRequest {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("批次库存编码")
    @JsonProperty(value = "stock_batch_code")
    private String stockBatchCode;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("仓编码(物流中心编码)")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓名称(物流中心名称)")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房类型")
    @JsonProperty(value = "warehouse_type")
    private String warehouseType;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("批次号")
    @JsonProperty(value = "batch_code")
    private String batchCode;

    @ApiModelProperty("生产日期")
    @JsonProperty(value = "production_date")
    private String productionDate;

    @ApiModelProperty("批次备注")
    @JsonProperty(value = "batch_remark")
    private String batchRemark;

    @ApiModelProperty("品类编码")
    @JsonProperty(value = "category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty(value = "category_type_name")
    private String categoryTypeName;

    @ApiModelProperty("规格")
    @JsonProperty(value = "spec")
    private String spec;

    @ApiModelProperty("颜色code")
    @JsonProperty(value = "color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty(value = "color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty(value = "model_number")
    private String modelNumber;

    @ApiModelProperty("单位编码")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("包装")
    @JsonProperty(value = "pack")
    private String pack;

    @ApiModelProperty("状态(进货销售的)")
    @JsonProperty(value = "config_status")
    private Long configStatus;

    @ApiModelProperty("总库存数")
    @JsonProperty(value = "inventory_num")
    private Long inventoryNum;

    @ApiModelProperty("可用库存数")
    @JsonProperty(value = "available_num")
    private Long availableNum;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_num")
    private Long lockNum;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty(value = "supplier_name")
    private String supplierName;

    @ApiModelProperty("最新供货单位编码")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("采购价")
    @JsonProperty(value = "purchase_price")
    private BigDecimal purchasePrice;

    @ApiModelProperty("税率")
    @JsonProperty(value = "tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty("创建人")
    @JsonProperty(value = "create_by")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonProperty(value = "create_time")
    private Date createTime;

    @ApiModelProperty("更新人")
    @JsonProperty(value = "update_by")
    private String updateBy;

    @ApiModelProperty("更新时间")
    @JsonProperty(value = "update_time")
    private Date updateTime;

}