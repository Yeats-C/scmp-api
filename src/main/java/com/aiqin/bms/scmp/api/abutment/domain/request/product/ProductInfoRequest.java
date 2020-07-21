package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("DL- 商品推送主信息")
public class ProductInfoRequest {

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="货号")
    @JsonProperty("item_number")
    private String itemNumber;

    @ApiModelProperty(value="SKU简称")
    @JsonProperty("sku_abbreviation")
    private String skuAbbreviation;

    @ApiModelProperty(value="spu编码")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value="spu名称")
    @JsonProperty("spu_name")
    private String spuName;

    @ApiModelProperty(value="SPU简称")
    @JsonProperty("spu_abbreviation")
    private String spuAbbreviation;

    @ApiModelProperty(value="款号")
    @JsonProperty("style_number")
    private String styleNumber;

    @ApiModelProperty(value="品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty(value="品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty(value="类型 0商品、1赠品")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value="商品属性")
    @JsonProperty("product_property")
    private String productProperty;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="管理方式")
    @JsonProperty("management_style")
    private String managementStyle;

    @ApiModelProperty(value="是否管理保质期 0:管理 1:不管理")
    @JsonProperty("quality_assurance_management")
    private Integer qualityAssuranceManagement;

    @ApiModelProperty(value="保质期类型(1:天 2:月 3:年)")
    @JsonProperty("quality_type")
    private Integer qualityType;

    @ApiModelProperty(value="保质期天数")
    @JsonProperty("quality_date")
    private Integer qualityDate;

    @ApiModelProperty(value="供货渠道类别")
    @JsonProperty("supply_channel_category")
    private String supplyChannelCategory;

    @ApiModelProperty(value="助记码")
    @JsonProperty("mnemonic_code")
    private String mnemonicCode;

    @ApiModelProperty(value="厂商指导价")
    @JsonProperty("manufacturer_guide_price")
    private Integer manufacturerGuidePrice;

    @ApiModelProperty(value="适用起始月龄")
    @JsonProperty("applicable_month_age")
    private String applicableMonthAge;

    @ApiModelProperty(value="是否季节商品 0:是 1:否")
    @JsonProperty("seasonal_goods")
    private Integer seasonalGoods;

    @ApiModelProperty(value="结构性商品 0:是 1:否")
    @JsonProperty("structural_goods")
    private Integer structuralGoods;

    @ApiModelProperty(value="仓位类型")
    @JsonProperty("warehouse_type_name")
    private String warehouseTypeName;

    @ApiModelProperty(value="使用时长")
    @JsonProperty("use_time")
    private Integer useTime;

    @ApiModelProperty(value="库存模式 0:有库存销售 1:无库存销售")
    @JsonProperty("inventory_model")
    private Integer inventoryModel;

    @ApiModelProperty(value="唯一码管理 0:是 1:否")
    @JsonProperty("unique_code")
    private Integer uniqueCode;

    @ApiModelProperty(value="季节波段")
    @JsonProperty("season_band")
    private String seasonBand;

    @ApiModelProperty(value="等级")
    @JsonProperty("level")
    private String level;

    @ApiModelProperty(value="特征")
    @JsonProperty("feature_name")
    private String featureName;

    @ApiModelProperty(value="通货等级")
    @JsonProperty("currency_level_name")
    private String currencyLevelName;

    @ApiModelProperty(value="SKU状态编号 0:再用 1:停止进货 2:停止配送 3:停止销售")
    @JsonProperty("sku_status")
    private String skuStatus;

    @ApiModelProperty(value="覆盖范围")
    @JsonProperty("coverage")
    private String coverage;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="商品介绍")
    @JsonProperty("introduce")
    private String introduce;

    @ApiModelProperty(value="图片地址")
    @JsonProperty("picture_list")
    private List<ProductPictureRequest> pictureList;

    @ApiModelProperty(value="说明图片地址")
    @JsonProperty("explain_picture_list")
    private List<ProductPictureRequest> explainPictureList;

    @ApiModelProperty(value="采购、分销、门店销售、库存信息信息")
    @JsonProperty("unit_list")
    private List<ProductUnitRequest> unitList;

    @ApiModelProperty(value="商品供应商信息")
    @JsonProperty("supplier_list")
    private List<ProductSupplierRequest> supplierList;

    @ApiModelProperty(value="价格信息")
    @JsonProperty("price_list")
    private List<ProductPriceRequest> priceList;

    @ApiModelProperty(value="商品仓库信息")
    @JsonProperty("transport_list")
    private List<ProductTransportRequest> transportList;

    @ApiModelProperty(value="商品生产厂家信息")
    @JsonProperty("manufacturer_list")
    private List<ProductManufacturerRequest> manufacturerList;

    @ApiModelProperty(value="商品质检报告信息")
    @JsonProperty("inspection_list")
    private List<ProductInspectionRequest> inspectionList;

    @ApiModelProperty(value="商品文件信息")
    @JsonProperty("file_list")
    private List<ProductFileRequest> fileList;

}
