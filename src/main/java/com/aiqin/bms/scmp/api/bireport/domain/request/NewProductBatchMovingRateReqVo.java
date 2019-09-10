package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("新品批次动销率request")
@Data
public class NewProductBatchMovingRateReqVo extends PagesRequest implements Serializable {

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("销售渠道code")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("销售渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_inbound_time")
    private String beginInboundTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_inbound_time")
    private String finishInboundTime;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("毛利率begin")
    @JsonProperty("begin_channel_maori_rate")
    private Double beginChannelMaoriRate;

    @ApiModelProperty("毛利率finish")
    @JsonProperty("finish_channel_maori_rate")
    private Double finishChannelMaoriRate;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;


    public NewProductBatchMovingRateReqVo(String skuCode, String skuName, String orderCode, String orderOriginal, String supplierCode, String supplierName, String categoryCode, String categoryName, String beginInboundTime, String finishInboundTime, String batchCode, Double beginChannelMaoriRate, Double finishChannelMaoriRate, String productSortCode, String productSortName) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.orderCode = orderCode;
        this.orderOriginal = orderOriginal;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.beginInboundTime = beginInboundTime;
        this.finishInboundTime = finishInboundTime;
        this.batchCode = batchCode;
        this.beginChannelMaoriRate = beginChannelMaoriRate;
        this.finishChannelMaoriRate = finishChannelMaoriRate;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public NewProductBatchMovingRateReqVo() {
    }
}
