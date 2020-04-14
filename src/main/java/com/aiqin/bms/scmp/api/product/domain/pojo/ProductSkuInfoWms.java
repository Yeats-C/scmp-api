package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
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


    @ApiModelProperty("商品/赠品(0:商品，1:赠品 2:组合商品)")
    @JsonProperty("goods_gifts")
    private Byte goodsGifts;


    @ApiModelProperty("厂家指导价")
    @JsonProperty("manufacturer_guide_price")
    private BigDecimal manufacturerGuidePrice;



}