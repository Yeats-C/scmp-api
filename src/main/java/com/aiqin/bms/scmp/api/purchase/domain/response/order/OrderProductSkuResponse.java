package com.aiqin.bms.scmp.api.purchase.domain.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Data
public class OrderProductSkuResponse {

    @ApiModelProperty("税率")
    @JsonProperty("tax")
    private BigDecimal tax;

    @ApiModelProperty("商品品牌code")
    @JsonProperty("brand_code")
    private String brandCode;

    @ApiModelProperty("商品品牌name")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty("商品分类name")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("商品分类code")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("商品sku")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("商品类型")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("图片地址")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("颜色编码")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("型号")
    @JsonProperty("model")
    private String model;

    @ApiModelProperty("拆零系数")
    @JsonProperty("zero_disassembly_coefficient")
    private Integer zeroDisassemblyCoefficient;

    @ApiModelProperty("商品单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("商品单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("基商品含量")
    @JsonProperty("base_product_content")
    private String baseProductContent;

    @ApiModelProperty("体积")
    @JsonProperty("box_volume")
    private BigDecimal boxVolume;

    @ApiModelProperty("重量")
    @JsonProperty("box_gross_weight")
    private BigDecimal boxGrossWeight;

}
