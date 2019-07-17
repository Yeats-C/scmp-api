package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("月销售情况request")
@Data
public class MonthlySalesReqVo extends PageReq implements Serializable {

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("门店类型code")
    @JsonProperty("store_type_code")
    private String storeTypeCode;

    @ApiModelProperty("门店类型名称")
    @JsonProperty("store_type_name")
    private String storeTypeName;

    @ApiModelProperty("数据类型code")
    @JsonProperty("data_style_code")
    private String dataStyleCode;

    @ApiModelProperty("数据类型name")
    @JsonProperty("data_style_name")
    private String dataStyleName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    public MonthlySalesReqVo(String productSortCode, String productSortName, String priceChannelCode, String priceChannelName, String storeTypeCode, String storeTypeName, String dataStyleCode, String dataStyleName, String beginRunTime, String finishRunTime) {
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
        this.priceChannelCode = priceChannelCode;
        this.priceChannelName = priceChannelName;
        this.storeTypeCode = storeTypeCode;
        this.storeTypeName = storeTypeName;
        this.dataStyleCode = dataStyleCode;
        this.dataStyleName = dataStyleName;
        this.beginRunTime = beginRunTime;
        this.finishRunTime = finishRunTime;
    }

    public MonthlySalesReqVo() {
    }
}
