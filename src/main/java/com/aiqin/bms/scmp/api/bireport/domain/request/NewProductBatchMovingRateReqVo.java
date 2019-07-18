package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("新品批次动销率request")
@Data
public class NewProductBatchMovingRateReqVo extends PageReq implements Serializable {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("销售渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("销售渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("毛利率begin")
    @JsonProperty("begin_qun_maori_rate")
    private Double beginQunMaoriRate;

    @ApiModelProperty("毛利率finish")
    @JsonProperty("finish_qun_maori_rate")
    private Double finishQunMaoriRate;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;


    public NewProductBatchMovingRateReqVo(String skuCode, String skuName, String priceChannelCode, String priceChannelName, String supplierCode, String supplierName, String productCategoryCode, String productCategoryName, String beginRunTime, String finishRunTime, String batchCode, Double beginQunMaoriRate, Double finishQunMaoriRate, String productSortCode, String productSortName) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.priceChannelCode = priceChannelCode;
        this.priceChannelName = priceChannelName;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.beginRunTime = beginRunTime;
        this.finishRunTime = finishRunTime;
        this.batchCode = batchCode;
        this.beginQunMaoriRate = beginQunMaoriRate;
        this.finishQunMaoriRate = finishQunMaoriRate;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public NewProductBatchMovingRateReqVo() {
    }
}
