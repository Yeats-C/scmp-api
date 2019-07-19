package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: zhao shuai
 * @create: 2019-06-18
 **/
@Data
public class PurchaseApplyDetailResponse {

    @ApiModelProperty(value = "采购申请单商品id")
    @JsonProperty("apply_product_id")
    private String applyProductId;

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="采购单号")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    @ApiModelProperty(value = "采购申请id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;

    @ApiModelProperty(value = "sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="商品类型 0商品 1赠品 2实物返 ")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="含税采购单价")
    @JsonProperty("product_purchase_amount")
    private Integer productPurchaseAmount;

    @ApiModelProperty(value="含税采购总价")
    @JsonProperty("product_purchase_sum")
    private Integer productPurchaseSum;

    @ApiModelProperty(value="采购件数（整数）")
    @JsonProperty("purchase_whole")
    private Integer purchaseWhole;

    @ApiModelProperty(value="采购件数（零数）")
    @JsonProperty("purchase_single")
    private Integer purchaseSingle;

    @ApiModelProperty(value="实物返数量（整数）")
    @JsonProperty("return_whole")
    private Integer returnWhole;

    @ApiModelProperty(value="实物返数量（零数）")
    @JsonProperty("return_single")
    private Integer returnSingle;

    @ApiModelProperty(value="单品总数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="基商品含量")
    @JsonProperty("base_product_content")
    private Integer baseProductContent;

    @ApiModelProperty(value="采购包装")
    @JsonProperty("box_gauge")
    private String boxGauge;

    @ApiModelProperty(value="库存单位")
    @JsonProperty("stock_unit_name")
    private String stockUnitName;

    @ApiModelProperty(value="库存数量")
    @JsonProperty("stock_count")
    private Integer stockCount;

    @ApiModelProperty(value="在途库存")
    @JsonProperty("total_way_num")
    private Integer totalWayNum;

    @ApiModelProperty(value="缺货影响的销售额")
    @JsonProperty("shortage_number")
    private Integer shortageNumber;

    @ApiModelProperty(value="缺货天数")
    @JsonProperty("shortage_day")
    private Integer shortageDay;

    @ApiModelProperty(value="库存周转期")
    @JsonProperty("stock_turnover")
    private Integer stockTurnover;

    @ApiModelProperty(value="到货后周转期")
    @JsonProperty("receipt_turnover")
    private Integer receiptTurnover;

    @ApiModelProperty(value="预测采购件数")
    @JsonProperty("purchase_number")
    private Integer purchaseNumber;

    @ApiModelProperty(value="预测到货时间")
    @JsonProperty("receipt_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date receiptTime;

    @ApiModelProperty(value="最高采购价")
    @JsonProperty("purchase_max")
    private Integer purchaseMax;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="商品类别编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="商品类别名称")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="最后订货日期")
    @JsonProperty("last_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date lastTime;

    @ApiModelProperty(value="近90天销量")
    @JsonProperty("sales_volume")
    private Integer salesVolume;

    @ApiModelProperty(value="商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty(value="商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value="最新采购价格")
    @JsonProperty("new_purchase_price")
    private Integer newPurchasePrice;

    @ApiModelProperty(value="结算方式编码")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="结算方式名称")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private Integer taxRate;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="账户编码")
    @JsonProperty("account_code")
    private String accountCode;

    @ApiModelProperty(value="账户名称")
    @JsonProperty("account_name")
    private String accountName;

    @ApiModelProperty(value="合同编码")
    @JsonProperty("contract_code")
    private String contractCode;

    @ApiModelProperty(value="合同名称")
    @JsonProperty("contract_name")
    private String contractName;

    @ApiModelProperty(value="负责人")
    @JsonProperty("charge_person")
    private String chargePerson;

    @ApiModelProperty(value="联系人")
    @JsonProperty("contact_person")
    private String contactPerson;

    @ApiModelProperty(value="电话")
    @JsonProperty("mobile")
    private String mobile;

    @ApiModelProperty(value="采购单状态 0.待审核 1.审核中 2.审核通过  3.备货确认 4.发货确认  5.入库开始 6.入库中 7.已入库  8.完成 9.取消 10.审核不通过")
    @JsonProperty("purchase_order_status")
    private Integer purchaseOrderStatus;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value="预计到货时间")
    @JsonProperty("expect_arrival_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expectArrivalTime;

    @ApiModelProperty(value="有效期")
    @JsonProperty("valid_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date validTime;

    @ApiModelProperty(value="关联订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="采购方式名称")
    @JsonProperty("order_type")
    private String orderType;

    @ApiModelProperty(value="供应商发货时间")
    @JsonProperty("delivery_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date deliveryTime;

    @ApiModelProperty(value="入库完成时间")
    @JsonProperty("warehouse_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date warehouseTime;

    @ApiModelProperty(value="取消原因")
    @JsonProperty("cancel_reason")
    private String cancelReason;

    @ApiModelProperty(value="取消备注")
    @JsonProperty("cancel_remark")
    private String cancelRemark;

    @ApiModelProperty(value="评分编码")
    @JsonProperty("score_code")
    private String scoreCode;

    @ApiModelProperty(value="实际单品数量")
    @JsonProperty("actual_single_count")
    private Integer actualSingleCount;

    @ApiModelProperty(value="实际含税总计")
    @JsonProperty("actual_tax_sum")
    private Integer actualTaxSum;

    @ApiModelProperty(value="入库单的来源单号")
    @JsonProperty("source_oder_code")
    private String sourceOderCode;

    @ApiModelProperty(value="含税单价")
    @JsonProperty("product_amount")
    private Integer productAmount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private Integer productTotalAmount;

    @ApiModelProperty(value="仓储状态 0.未开始  1.确认中 2.完成")
    @JsonProperty("storage_status")
    private Integer storageStatus;

}

