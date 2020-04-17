package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author ch
 * @description 新建调拨单手动选择商品
 * @date 2020/04/07 15:40
 */
@Data
@ApiModel("调拨单选择商品req")
public class ManualChoseProductReq extends PagesRequest {

    @ApiModelProperty("仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品类code")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品牌code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("商品属性")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("spu编号")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty("spu名称")
    @JsonProperty("spu_name")
    private String spuName;

}
