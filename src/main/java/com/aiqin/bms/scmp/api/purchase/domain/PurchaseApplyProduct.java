package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class PurchaseApplyProduct {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="采购申请单商品id")
    @JsonProperty("apply_product_id")
    private String applyProductId;

    @ApiModelProperty(value="采购申请id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;

    @ApiModelProperty(value="采购申请单")
    @JsonProperty("purchase_apply_code")
    private String purchaseApplyCode;

    @ApiModelProperty(value="sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="spu编码")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value="spu名称")
    @JsonProperty("product_name")
    private String productName;

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

    @ApiModelProperty(value="商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty(value="商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

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

    @ApiModelProperty(value="商品类型 0商品 1赠品 2实物返")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="含税采购价")
    @JsonProperty("product_purchase_amount")
    private BigDecimal productPurchaseAmount;

    @ApiModelProperty(value="最新采购价格")
    @JsonProperty("new_purchase_price")
    private BigDecimal newPurchasePrice;

    @ApiModelProperty(value="采购件数（整数）")
    @JsonProperty("purchase_whole")
    private Integer purchaseWhole;

    @ApiModelProperty(value="采购件数（零数）")
    @JsonProperty("purchase_single")
    private Integer purchaseSingle;

    @ApiModelProperty(value="库存单位")
    @JsonProperty("stock_unit_name")
    private String stockUnitName;

    @ApiModelProperty(value="库存数量")
    @JsonProperty("stock_count")
    private Integer stockCount;

    @ApiModelProperty(value="在途库存")
    @JsonProperty("total_way_num")
    private Integer totalWayNum;

    @ApiModelProperty(value="最后订货日期")
    @JsonProperty("last_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date lastTime;

    @ApiModelProperty(value="近90天销量")
    @JsonProperty("sales_volume")
    private Integer salesVolume;

    @ApiModelProperty(value="基商品含量")
    @JsonProperty("base_product_content")
    private Integer baseProductContent;

    @ApiModelProperty(value="采购包装")
    @JsonProperty("box_gauge")
    private String boxGauge;

    @ApiModelProperty(value="预测采购件数")
    @JsonProperty("purchase_number")
    private Integer purchaseNumber;

    @ApiModelProperty(value="预测到货时间")
    @JsonProperty("receipt_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date receiptTime;

    @ApiModelProperty(value="最高采购价")
    @JsonProperty("purchase_max")
    private BigDecimal purchaseMax;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="缺货影响的销售额")
    @JsonProperty("shortage_number")
    private BigDecimal shortageNumber;

    @ApiModelProperty(value="缺货天数")
    @JsonProperty("shortage_day")
    private Integer shortageDay;

    @ApiModelProperty(value="库存周转期")
    @JsonProperty("stock_turnover")
    private Integer stockTurnover;

    @ApiModelProperty(value="到货后周转期")
    @JsonProperty("receipt_turnover")
    private Integer receiptTurnover;

    @ApiModelProperty(value="错误原因")
    @JsonProperty("error_info")
    private String errorInfo;

    @ApiModelProperty(value="0 禁用  1.启用")
    @JsonProperty("apply_product_status")
    private Integer applyProductStatus;

    @ApiModelProperty(value="采购申请状态  0.未提交  1.已提交")
    @JsonProperty("info_status")
    private Integer infoStatus;

    @ApiModelProperty(value="厂商SKU编码")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建者id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="修改者id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="创建者")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改者")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="结算方式编码")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="结算方式名称")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

}