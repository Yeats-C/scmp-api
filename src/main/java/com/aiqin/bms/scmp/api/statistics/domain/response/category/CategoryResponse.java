package com.aiqin.bms.scmp.api.statistics.domain.response.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-06
 **/
@Data
@Api(tags = "品类促销统计实体")
public class CategoryResponse {

    @ApiModelProperty(value="门店类型的集合")
    @JsonProperty("category_list")
    private List<CategoryResponse> categoryList;

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty(value="门店类型code(0代表直营，1代表萌贝树，2代表小红马，3代表加盟）")
    @JsonProperty("store_type_code")
    private Long storeTypeCode;

    @ApiModelProperty(value="门店类型")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty(value="一级品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="一级品类")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="当前销售额")
    @JsonProperty("curr_sales_amount")
    private Long currSalesAmount;

    @ApiModelProperty(value="上期销售额")
    @JsonProperty("pre_sales_amount")
    private Long preSalesAmount;

    @ApiModelProperty(value="当前毛利")
    @JsonProperty("curr_margin")
    private Long currMargin;

    @ApiModelProperty(value="上期毛利")
    @JsonProperty("pre_margin")
    private Long preMargin;

    @ApiModelProperty(value="销售额环比增长率")
    @JsonProperty("sales_amount_link_rela_growth_rate")
    private BigDecimal salesAmountLinkRelaGrowthRate;

    @ApiModelProperty(value="毛利额环比增长率")
    @JsonProperty("margin_link_rela_growth_rate")
    private BigDecimal marginLinkRelaGrowthRate;

    @ApiModelProperty(value="占比")
    @JsonProperty("rate")
    private BigDecimal rate;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
