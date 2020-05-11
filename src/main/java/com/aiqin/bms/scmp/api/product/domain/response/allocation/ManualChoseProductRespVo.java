package com.aiqin.bms.scmp.api.product.domain.response.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ch
 * @description 新建调拨单手动选择商品返回实体类
 * @date 2020/04/08 10:57
 */
@Data
@ApiModel("调拨单选择商品resp")
public class ManualChoseProductRespVo {

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty(value = "商品分类code")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty(value = "商品分类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty(value = "品牌类型code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value = "品牌类型名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty(value = "商品/赠品(0:商品，1:赠品)")
    @JsonProperty("goods_gifts")
    private String goodsGifts;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty(value = "颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value = "颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value = "型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value = "单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value = "单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("可用库存数（选择批次号，数量为批次号数量，未选择批次号数量为商品的库存数）")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty("税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty("成本")
    @JsonProperty("tax_cost")
    private BigDecimal taxCost;

    @ApiModelProperty("批次管理 0：自动批次管理 1：全部制定批次模式 2：部分指定批次模式")
    @JsonProperty("batch_manage")
    private Integer batchManage;

    @ApiModelProperty("批次列表")
    @JsonProperty("sku_batch")
    private List<SkuBatchRespVO> skuBatchRespVOS;



}
