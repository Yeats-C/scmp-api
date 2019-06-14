package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value="商品属性 1.A 2.B 3.C 4.D")
    @JsonProperty("product_nature")
    private Integer productNature;

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

    @ApiModelProperty(value="类别  0.OEM 1.服纺  2.辅采")
    @JsonProperty("product_category")
    private Integer productCategory;

    @ApiModelProperty(value="商品类型 0赠品 1商品 2实物返回")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="含税采购价")
    @JsonProperty("product_purchase_amount")
    private Integer productPurchaseAmount;

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
    private Date lastTime;

    @ApiModelProperty(value="近90天销量")
    @JsonProperty("sales_volume")
    private Integer salesVolume;

    @ApiModelProperty(value="采购包装数量")
    @JsonProperty("pack_number")
    private Integer packNumber;

    @ApiModelProperty(value="采购包装单位")
    @JsonProperty("pack_unit")
    private String packUnit;

    @ApiModelProperty(value="预测采购件数")
    @JsonProperty("purchase_number")
    private Integer purchaseNumber;

    @ApiModelProperty(value="预测到货时间")
    @JsonProperty("receipt_time")
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

    @ApiModelProperty(value="错误原因")
    @JsonProperty("error_info")
    private String errorInfo;

    @ApiModelProperty(value="0 禁用  1.启用")
    @JsonProperty("apply_product_status")
    private Integer applyProductStatus;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value="修改人")
    @JsonProperty("update_by")
    private String updateBy;

}