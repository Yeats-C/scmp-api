package com.aiqin.bms.scmp.api.abutment.domain.request.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("熙耘采购&退供批次表")
public class ScmpPurchaseBatch {

    @ApiModelProperty(value="入库单号")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    @JsonProperty("batch_no")
    @ApiModelProperty("批次号")
    private String batchNo;

    @ApiModelProperty(value="批次编号")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value="生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value="实际最小单位数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value="库位号")
    @JsonProperty("location_code")
    private String locationCode;
    
    @ApiModelProperty("商品类型")
    @JsonProperty("product_type")
    private String productType;

}