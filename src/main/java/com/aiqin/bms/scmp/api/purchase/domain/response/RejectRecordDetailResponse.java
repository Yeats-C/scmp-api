package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class RejectRecordDetailResponse {

    @ApiModelProperty(value = "")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value = "商品类型 0赠品 1商品 2实物返回")
    @JsonProperty("product_type")
    private Integer productType;

    @ApiModelProperty(value = "规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value = "颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value = "厂商sku")
    @JsonProperty("factory_sku_code")
    private String factorySkuCode;

    @ApiModelProperty(value = "型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value = "商品数量")
    @JsonProperty("product_count")
    private String productCount;

    @ApiModelProperty(value = "单品数量")
    @JsonProperty("single_count")
    private String singleCount;

    @ApiModelProperty(value = "单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value = "税率")
    @JsonProperty("tax_rate")
    private Integer taxRate;

    @ApiModelProperty(value = "含税单价")
    @JsonProperty("product_amount")
    private Long productAmount;

    @ApiModelProperty(value = "含税总价")
    @JsonProperty("product_total_amount")
    private Long productTotalAmount;

    @ApiModelProperty(value = "wms 传回来的实际数量")
    @JsonProperty("actual_count")
    private Integer actualCount;

    @ApiModelProperty(value = "wms 传回来的实际金额")
    @JsonProperty("actual_amount")
    private Long actualAmount;

}
