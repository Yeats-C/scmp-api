package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("月销售达成情况request")
@Data
public class MonthSalesAchievementReqVo extends PageReq implements Serializable {

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

    @ApiModelProperty("品类编码")
    @JsonProperty("category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_type_name")
    private String categoryTypeName;

    public MonthSalesAchievementReqVo(String productSortCode, String productSortName, String priceChannelCode, String priceChannelName, String storeTypeCode, String storeTypeName, String dataStyleCode, String dataStyleName, String beginRunTime, String finishRunTime, String categoryTypeCode, String categoryTypeName) {
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
        this.categoryTypeCode = categoryTypeCode;
        this.categoryTypeName = categoryTypeName;
    }

    public MonthSalesAchievementReqVo() {
    }
}
