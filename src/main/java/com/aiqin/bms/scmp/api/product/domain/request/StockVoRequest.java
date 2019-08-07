package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("库存修改model")
@Data
public class StockVoRequest {
    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("物流中心编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("仓库code")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("仓库类型")
    @JsonProperty(value = "warehouse_type")
    private String warehouseType;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("采购组编码")
    @JsonProperty(value = "purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty(value = "purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty("税率")
    @JsonProperty(value = "tax_rate")
    private Long taxRate;

    @ApiModelProperty("变化数")
    @JsonProperty(value = "change_num")
    private Long changeNum;

    @ApiModelProperty("最新供货单位编码")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("最新采购价")
    @JsonProperty(value = "new_purchase_price")
    private Long newPurchasePrice;

    @ApiModelProperty("单据类型 0出库 1入库 2退供 3采购 4调拨 5退货 6移库 7监管仓入库 8报废 9订单 10监管仓出库")
    @JsonProperty(value = "document_type")
    private Integer documentType;

    @ApiModelProperty("单据号")
    @JsonProperty(value = "document_num")
    private String documentNum;

    @ApiModelProperty("来源单据类型 0出库 1入库 2退供 3采购 4调拨 5退货 6移库 7监管仓入库 8报废 9订单 10监管仓出库")
    @JsonProperty(value = "source_document_type")
    private Integer sourceDocumentType;

    @ApiModelProperty("来源单据号")
    @JsonProperty(value = "source_document_num")
    private String sourceDocumentNum;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operator")
    private String operator;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("库存成本")
    @JsonProperty(value = "stock_cost")
    private Long stockCost;
}
