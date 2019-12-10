package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sunx
 * @description 采购单明细
 * @date 2019-08-03
 */
@Data
@ApiModel("scmp采购单明细1")
public class PurchaseDetail {
    /**
     * 业务单id
     */
    @JsonProperty("order_id")
    @ApiModelProperty(value = "业务单id",hidden = true)
    private String orderId;
    /**
     * 主业务单类型
     */
    @JsonProperty("order_type")
    @ApiModelProperty(value = "主业务单类型",hidden = true)
    private Integer orderType;
    /**
     * sku码
     */
    @JsonProperty("sku_code")
    @ApiModelProperty("sku码")
    private String skuCode;
    /**
     * sku名称
     */
    @JsonProperty("sku_name")
    @ApiModelProperty("sku名称")
    private String skuName;
    /**
     * 颜色规格型号冗余信息
     */
    @JsonProperty("sku_desc")
    @ApiModelProperty("颜色规格型号冗余信息")
    private String skuDesc;
    /**
     * 单价
     */
    @ApiModelProperty("单价")
    private String price;
    /**
     * 数量
     */
    @JsonProperty("single_count")
    @ApiModelProperty("数量")
    private Integer singleCount;
    /**
     * 商品类型0 商品  5 实物返 10  赠品
     */
    @JsonProperty("product_type")
    @ApiModelProperty("商品类型0 商品  5 实物返 10  赠品")
    private Integer productType;
    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;
    /**
     * 批次号
     */
    @JsonProperty("batch_no")
    @ApiModelProperty("批次号")
    private String batchNo;
    /**
     * 进项税率
     */
    @JsonProperty("input_rate")
    @ApiModelProperty("进项税率")
    private BigDecimal inputRate;
    /**
     * 库存数量
     */
    @JsonProperty("storage_count")
    @ApiModelProperty("库存数量")
    private Integer storageCount;
    /**
     * 单位含量
     */
    @JsonProperty("unite_count")
    @ApiModelProperty("单位含量")
    private String uniteCount;
    /**
     * 分类编码 末级分类
     */
    @JsonProperty("category_code")
    @ApiModelProperty("分类编码 末级分类")
    private String categoryCode;
    /**
     * 分类名称
     */
    @JsonProperty("category_name")
    @ApiModelProperty("分类名称")
    private String categoryName;
    /**
     * 品牌编码
     */
    @JsonProperty("brand_code")
    @ApiModelProperty("品牌编码")
    private String brandCode;
    /**
     * 品牌名称
     */
    @JsonProperty("brand_name")
    @ApiModelProperty("品牌名称")
    private String brandName;
    /**
     * 厂商指导价
     */
    @JsonProperty("guide_price")
    @ApiModelProperty("厂商指导价")
    private String guidePrice;
    /**
     * 商品属性
     */
    @JsonProperty("product_property")
    @ApiModelProperty("商品属性 A B  C  D")
    private String productProperty;
}
