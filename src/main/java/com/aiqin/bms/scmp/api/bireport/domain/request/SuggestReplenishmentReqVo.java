package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("建议补货request")
@Data
public class SuggestReplenishmentReqVo extends PageReq implements Serializable {

    @ApiModelProperty("商品编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

    public SuggestReplenishmentReqVo(String skuCode, String skuName, String productCategoryCode, String productCategoryName, String productBrandCode, String productBrandName, String transportCenterCode, String transportCenterName, String beginCreateTime, String finishCreateTime) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.productBrandCode = productBrandCode;
        this.productBrandName = productBrandName;
        this.transportCenterCode = transportCenterCode;
        this.transportCenterName = transportCenterName;
        this.beginCreateTime = beginCreateTime;
        this.finishCreateTime = finishCreateTime;
    }

    public SuggestReplenishmentReqVo() {
    }
}
