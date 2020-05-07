package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsWmsRepsVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel("传输给Wms的SKU信息")
@Data
public class ProductSkuInfoWms extends CommonBean {


    @ApiModelProperty("商品品类code")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;


    @ApiModelProperty("货号")
    @JsonProperty("item_number")
    private String itemNumber; //TODO

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;


    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;


    @ApiModelProperty("条形码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;


    @ApiModelProperty("商品品牌code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("sku简称")
    @JsonProperty("sku_abbreviation")
    private String skuAbbreviation;


    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;


    @ApiModelProperty("保质管理（0:管理 1:不管理）")
    @JsonProperty("quality_assurance_management")
    private Byte qualityAssuranceManagement;


    @ApiModelProperty("保质日期")
    @JsonProperty("quality_date")
    private String qualityDate;


    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private Date updateTime;


    @ApiModelProperty("商品类型(0:商品，1:赠品 2:组合商品)")
    @JsonProperty("goods_gifts")
    private Byte goodsGifts;


    @ApiModelProperty("厂家指导价")
    @JsonProperty("manufacturer_guide_price")
    private BigDecimal manufacturerGuidePrice;


    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("包装箱子长度")
    @JsonProperty("box_length")
    private Long boxLength;

    @ApiModelProperty("宽度（mm）")
    @JsonProperty("box_width")
    private Long boxWidth;

    @ApiModelProperty("箱子高度")
    @JsonProperty("box_height")
    private Long boxHeight;

    @ApiModelProperty("箱子体积")
    @JsonProperty("box_volume")
    private Long boxVolume;

    @ApiModelProperty("毛重")
    @JsonProperty("box_gross_weight")
    private BigDecimal boxGrossWeight;

    @ApiModelProperty("净重")
    @JsonProperty("net_weight")
    private BigDecimal netWeight;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supply_unit_code")
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    @JsonProperty("supply_unit_name")
    private String supplyUnitName;

    @ApiModelProperty("sku状态，0为启用，1为禁用")
    @JsonProperty("sku_status")
    private Byte skuStatus;

    @ApiModelProperty("季节波段")
    @JsonProperty("season_band")
    private String seasonBand;

    @ApiModelProperty("改sku下仓库配置")
    @JsonProperty("sku_configs_wms_reps_vos")
    private List<SkuConfigsWmsRepsVo> skuConfigsWmsRepsVos;
}