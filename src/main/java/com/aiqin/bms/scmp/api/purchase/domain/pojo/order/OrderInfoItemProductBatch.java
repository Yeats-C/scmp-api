package com.aiqin.bms.scmp.api.purchase.domain.pojo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("订单商品批次表")
@Data
public class OrderInfoItemProductBatch {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("订单编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("商品行号")
    @JsonProperty("product_line_num")
    private Long productLineNum;

    @ApiModelProperty("原行号")
    @JsonProperty("original_line_num")
    private Long originalLineNum;

    @ApiModelProperty(value = "sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("数量")
    @JsonProperty("num")
    private Long num;

    @ApiModelProperty("实发数量")
    @JsonProperty("actual_deliver_num")
    private Long actualDeliverNum;

    @ApiModelProperty("生产日期")
    @JsonProperty("product_time")
    private Date productTime;

    @ApiModelProperty("过期日期")
    @JsonProperty("be_overdue_data")
    private String beOverdueData;

    @ApiModelProperty("批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_number")
    private String batchNumber;

    @ApiModelProperty("锁定类型")
    @JsonProperty("lock_type")
    private Integer lockType;

    @ApiModelProperty("物流中心名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty("供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

}