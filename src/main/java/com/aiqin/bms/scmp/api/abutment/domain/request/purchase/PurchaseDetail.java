package com.aiqin.bms.scmp.api.abutment.domain.request.purchase;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description 采购单明细
 * @date 2019-08-03
 */
@Data
@ApiModel("scmp采购单明细1")
public class PurchaseDetail {

    @JsonProperty("order_id")
    @ApiModelProperty(value = "业务单id",hidden = true)
    private String orderId;

    @JsonProperty("order_type")
    @ApiModelProperty(value = "主业务单类型",hidden = true)
    private Integer orderType;

    @JsonProperty("sku_code")
    @ApiModelProperty("sku码")
    private String skuCode;

    @JsonProperty("sku_name")
    @ApiModelProperty("sku名称")
    private String skuName;

    @JsonProperty("sku_desc")
    @ApiModelProperty("颜色规格型号冗余信息")
    private String skuDesc;

    @ApiModelProperty("单价")
    private String price;

    @JsonProperty("single_count")
    @ApiModelProperty("数量")
    private Integer singleCount;

    @JsonProperty("product_type")
    @ApiModelProperty("商品类型0 商品  5 实物返 10  赠品")
    private Integer productType;

    @ApiModelProperty("单位")
    private String unit;

    @JsonProperty("input_rate")
    @ApiModelProperty("进项税率")
    private Integer inputRate;

    @JsonProperty("storage_count")
    @ApiModelProperty("库存数量")
    private Integer storageCount;

    @JsonProperty("unite_count")
    @ApiModelProperty("单位含量")
    private String uniteCount;

    @JsonProperty("category_code")
    @ApiModelProperty("分类编码 末级分类")
    private String categoryCode;

    @JsonProperty("category_name")
    @ApiModelProperty("分类名称")
    private String categoryName;

    @JsonProperty("brand_code")
    @ApiModelProperty("品牌编码")
    private String brandCode;

    @JsonProperty("brand_name")
    @ApiModelProperty("品牌名称")
    private String brandName;

    @JsonProperty("guide_price")
    @ApiModelProperty("厂商指导价")
    private String guidePrice;

    @JsonProperty("product_property")
    @ApiModelProperty("商品属性 A B  C  D")
    private String productProperty;
    
    @JsonProperty("batch_list")
    @ApiModelProperty("批次列表")
    private List<ScmpPurchaseBatch> batchList;
    
}
