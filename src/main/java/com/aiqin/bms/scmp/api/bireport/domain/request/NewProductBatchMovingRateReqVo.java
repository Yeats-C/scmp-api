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

    @ApiModelProperty("入库时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("入库时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("毛利率begin")
    @JsonProperty("begin_maori_rate")
    private Integer beginMaoriRate;

    @ApiModelProperty("毛利率finish")
    @JsonProperty("finish_maori_rate")
    private Integer finishMaoriRate;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;


    public NewProductBatchMovingRateReqVo(String skuCode, String skuName, String priceChannelName, String supplierCode, String supplierName, String productCategoryCode, String beginCreateTime, String finishCreateTime, String batchCode, Integer beginMaoriRate, Integer finishMaoriRate, String productSortCode, String productSortName) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.priceChannelName = priceChannelName;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.productCategoryCode = productCategoryCode;
        this.beginCreateTime = beginCreateTime;
        this.finishCreateTime = finishCreateTime;
        this.batchCode = batchCode;
        this.beginMaoriRate = beginMaoriRate;
        this.finishMaoriRate = finishMaoriRate;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public NewProductBatchMovingRateReqVo() {
    }
}
