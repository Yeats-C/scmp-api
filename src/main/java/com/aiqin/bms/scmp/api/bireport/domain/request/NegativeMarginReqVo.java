package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("负毛利request")
@Data
public class NegativeMarginReqVo extends PageReq implements Serializable {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("渠道名称")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    public NegativeMarginReqVo(String skuCode, String skuName, String categoryCode, String categoryName, String productBrandCode, String productBrandName, String orderCode, String orderOriginal, String beginCreateTime, String finishCreateTime, String productSortCode, String productSortName) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.productBrandCode = productBrandCode;
        this.productBrandName = productBrandName;
        this.orderCode = orderCode;
        this.orderOriginal = orderOriginal;
        this.beginCreateTime = beginCreateTime;
        this.finishCreateTime = finishCreateTime;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public NegativeMarginReqVo() {
    }
}
