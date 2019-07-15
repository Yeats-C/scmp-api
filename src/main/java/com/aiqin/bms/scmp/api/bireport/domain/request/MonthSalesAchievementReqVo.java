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

    @ApiModelProperty("门店类型")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty("数据类型")
    @JsonProperty("data_style")
    private String dataStyle;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_type_name")
    private String categoryTypeName;

    public MonthSalesAchievementReqVo(String productSortCode, String productSortName, String priceChannelCode, String priceChannelName, String storeType, String dataStyle, String beginCreateTime, String finishCreateTime, String categoryTypeCode, String categoryTypeName) {
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
        this.priceChannelCode = priceChannelCode;
        this.priceChannelName = priceChannelName;
        this.storeType = storeType;
        this.dataStyle = dataStyle;
        this.beginCreateTime = beginCreateTime;
        this.finishCreateTime = finishCreateTime;
        this.categoryTypeCode = categoryTypeCode;
        this.categoryTypeName = categoryTypeName;
    }

    public MonthSalesAchievementReqVo() {
    }
}
