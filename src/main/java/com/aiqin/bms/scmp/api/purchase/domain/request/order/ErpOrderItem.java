package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单商品明细行实体
 */
@Data
public class ErpOrderItem {

    @ApiModelProperty(value = "订单号")
    @JsonProperty("order_store_code")
    private String orderStoreCode;

    @ApiModelProperty(value = "行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty(value = "spu编码")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value = "spu名称")
    @JsonProperty("spu_name")
    private String spuName;

    @ApiModelProperty(value = "sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "条形码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty(value = "图片url")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty(value = "规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value = "颜色编码")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value = "颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value = "型号")
    @JsonProperty("model_code")
    private String modelCode;

    @ApiModelProperty(value = "单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value = "单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value = "折零系数")
    @JsonProperty("zero_disassembly_coefficient")
    private Long zeroDisassemblyCoefficient;

    @ApiModelProperty(value = "商品类型 0商品（本品） 1赠品")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value = "商品类型描述")
    @JsonProperty("product_type_desc")
    private String productTypeDesc;

    @ApiModelProperty(value = "商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty(value = "商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value = "供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "数量")
    @JsonProperty("product_count")
    private Long productCount;

    @ApiModelProperty(value = "单价")
    @JsonProperty("product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value = "含税采购价")
    @JsonProperty("purchase_amount")
    private BigDecimal purchaseAmount;

    @ApiModelProperty(value = "行总价")
    @JsonProperty("total_product_amount")
    private BigDecimal totalProductAmount;

    @ApiModelProperty(value = "实际商品总价（发货商品总价）")
    @JsonProperty("actual_total_product_amount")
    private BigDecimal actualTotalProductAmount;

    @ApiModelProperty(value = "优惠分摊总金额（分摊后金额）")
    @JsonProperty("total_preferential_amount")
    private BigDecimal totalPreferentialAmount;

    @ApiModelProperty(value = "分摊后单价")
    @JsonProperty("preferential_amount")
    private BigDecimal preferentialAmount;

    @ApiModelProperty(value = "活动优惠总金额")
    @JsonProperty("total_acivity_amount")
    private BigDecimal totalAcivityAmount;

    @ApiModelProperty(value = "门店实收数量")
    @JsonProperty("actual_inbound_count")
    private Long actualInboundCount;

    @ApiModelProperty(value = "实发数量")
    @JsonProperty("actual_product_count")
    private Long actualProductCount;

    @ApiModelProperty(value = "退货数量")
    @JsonProperty("return_product_count")
    private Long returnProductCount;

    @ApiModelProperty(value = "税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty(value = "活动编码(多个，隔开）")
    @JsonProperty("activity_code")
    private String activityCode;

    @ApiModelProperty(value = "赠品行号")
    @JsonProperty("gift_line_code")
    private Long giftLineCode;

    @ApiModelProperty(value = "公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value = "签收数量差异原因")
    @JsonProperty("sign_difference_reason")
    private String signDifferenceReason;

    @ApiModelProperty(value = "0. 启用   1.禁用")
    @JsonProperty("use_status")
    private String useStatus;

    @ApiModelProperty(value = "创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "更新人编码")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value = "更新人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
