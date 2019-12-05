package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class ApplyPurchaseOrder {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="采购单号")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="采购信息完善状态 0.未提交  1.已提交")
    @JsonProperty("info_status")
    private Integer infoStatus;

    @ApiModelProperty(value="采购单状态 0.待审核 1.审核中 2.审核通过  3.备货确认 4.发货确认  5.入库开始 6.入库中 7.已入库  8.完成 9.取消 10.审核不通过")
    @JsonProperty("purchase_order_status")
    private Integer purchaseOrderStatus;

    @ApiModelProperty(value="仓储状态 0.未开始  1.确认中 2.完成")
    @JsonProperty("storage_status")
    private Integer storageStatus;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="商品含税金额")
    @JsonProperty("product_total_amount")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value="实物返含税金额")
    @JsonProperty("return_amount")
    private BigDecimal returnAmount;

    @ApiModelProperty(value="采购方式 0 配送  1.铺采直送")
    @JsonProperty("purchase_mode")
    private Integer purchaseMode;

    @ApiModelProperty(value="结算方式编码")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="结算方式名称")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="取消原因")
    @JsonProperty("cancel_reason")
    private String cancelReason;

    @ApiModelProperty(value="取消备注")
    @JsonProperty("cancel_remark")
    private String cancelRemark;

    @ApiModelProperty(value="采购单的类型（手动，自动）")
    @JsonProperty("apply_type_form")
    private String applyTypeForm;

    @ApiModelProperty(value="关联审批单")
    @JsonProperty("approval_code")
    private String approvalCode;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("gift_tax_sum")
    private BigDecimal giftTaxSum;

    @ApiModelProperty(value="公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建人id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="修改人id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

}