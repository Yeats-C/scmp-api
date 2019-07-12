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

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("入库时间begin")
    @JsonProperty("begin_create_time")
    private String beginTimeMonth;

    @ApiModelProperty("入库时间finish")
    @JsonProperty("finish_create_time")
    private String finishTimeMonth;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    public NegativeMarginReqVo(String skuCode, String skuName, String productCategoryCode, String productCategoryName, String productBrandCode, String productBrandName, String orderOriginal, String beginTimeMonth, String finishTimeMonth, String productSortCode, String productSortName) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.productBrandCode = productBrandCode;
        this.productBrandName = productBrandName;
        this.orderOriginal = orderOriginal;
        this.beginTimeMonth = beginTimeMonth;
        this.finishTimeMonth = finishTimeMonth;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public NegativeMarginReqVo() {
    }
}
