package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("图表request")
@Data
public class ChartReqVo {

    @ApiModelProperty("日期")
    @JsonProperty("month")
    private String month;

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

    @ApiModelProperty("渠道销售")
    @JsonProperty("qun_sale")
    private String qunSale;

    @ApiModelProperty("分销销售")
    @JsonProperty("fen_sale")
    private String fenSale;

    public ChartReqVo(String month, String productSortCode, String productSortName, String priceChannelCode, String priceChannelName, String storeTypeCode, String storeTypeName, String dataStyleCode, String dataStyleName, String qunSale, String fenSale) {
        this.month = month;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
        this.priceChannelCode = priceChannelCode;
        this.priceChannelName = priceChannelName;
        this.storeTypeCode = storeTypeCode;
        this.storeTypeName = storeTypeName;
        this.dataStyleCode = dataStyleCode;
        this.dataStyleName = dataStyleName;
        this.qunSale = qunSale;
        this.fenSale = fenSale;
    }

    public ChartReqVo() {
    }
}
