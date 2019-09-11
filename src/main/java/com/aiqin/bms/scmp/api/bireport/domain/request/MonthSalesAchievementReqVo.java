package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("月销售达成情况request")
@Data
public class MonthSalesAchievementReqVo extends PagesRequest implements Serializable {

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private String month;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("数据类型code")
    @JsonProperty("data_type_code")
    private String dataTypeCode;

    @ApiModelProperty("数据类型name")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty("门店类型code")
    @JsonProperty("store_type_code")
    private String storeTypeCode;

    @ApiModelProperty("门店类型名称")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("商品属性code(AB品)")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性(AB品)")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    public MonthSalesAchievementReqVo(String month, String priceChannelCode, String priceChannelName, String dataTypeCode, String dataType, String storeTypeCode, String storeType, String categoryCode, String categoryName, String productPropertyCode, String productPropertyName) {
        this.month = month;
        this.priceChannelCode = priceChannelCode;
        this.priceChannelName = priceChannelName;
        this.dataTypeCode = dataTypeCode;
        this.dataType = dataType;
        this.storeTypeCode = storeTypeCode;
        this.storeType = storeType;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.productPropertyCode = productPropertyCode;
        this.productPropertyName = productPropertyName;
    }

    public MonthSalesAchievementReqVo() {
    }
}
