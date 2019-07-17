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
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("order_original_code")
    private String orderOriginalCode;

    @ApiModelProperty("渠道名称")
    @JsonProperty("order_original_name")
    private String orderOriginalName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    public NegativeMarginReqVo(String skuCode, String skuName, String productCategoryCode, String productCategoryName, String productBrandCode, String productBrandName, String orderOriginalCode, String orderOriginalName, String beginRunTime, String finishRunTime, String productSortCode, String productSortName) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.productBrandCode = productBrandCode;
        this.productBrandName = productBrandName;
        this.orderOriginalCode = orderOriginalCode;
        this.orderOriginalName = orderOriginalName;
        this.beginRunTime = beginRunTime;
        this.finishRunTime = finishRunTime;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public NegativeMarginReqVo() {
    }
}
