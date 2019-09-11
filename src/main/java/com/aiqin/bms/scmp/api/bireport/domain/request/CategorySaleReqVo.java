package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("品类促销request")
@Data
public class CategorySaleReqVo extends PagesRequest implements Serializable {

    @ApiModelProperty("时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    public CategorySaleReqVo(String createTime, String priceChannelCode, String priceChannelName, String productSortCode, String productSortName) {
        this.createTime = createTime;
        this.priceChannelCode = priceChannelCode;
        this.priceChannelName = priceChannelName;
        this.productSortCode = productSortCode;
        this.productSortName = productSortName;
    }

    public CategorySaleReqVo() {
    }
}
