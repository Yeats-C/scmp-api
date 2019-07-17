package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("新品批次动销率respVo")
@Data
public class NewProductBatchMovingRateRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("一级品类")
    @JsonProperty("product_category_one")
    private String productCategoryOne;

    @ApiModelProperty("二级品类")
    @JsonProperty("product_category_two")
    private String productCategoryTwo;

    @ApiModelProperty("三级品类")
    @JsonProperty("product_category_three")
    private String productCategoryThree;

    @ApiModelProperty("四级品类")
    @JsonProperty("product_category_four")
    private String productCategoryFour;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("含税进价")
    @JsonProperty("purchase_price")
    private Integer purchasePrice;

    @ApiModelProperty("厂商指导价")
    @JsonProperty("manufacturer_guide_price")
    private Integer manufacturerGuidePrice;

    @ApiModelProperty("库存数量")
    @JsonProperty("inventory_num")
    private Integer inventoryNum;

    @ApiModelProperty("库存成本")
    @JsonProperty("tax_cost")
    private Integer taxCost;

    @ApiModelProperty("销售渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("销售数量")
    @JsonProperty("num")
    private Integer num;

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_costs")
    private Integer salesCosts;

    @ApiModelProperty("渠道销售金额")
    @JsonProperty("qun_amount")
    private Integer qunAmount;

    @ApiModelProperty("渠道毛利额")
    @JsonProperty("qun_maori")
    private Integer qunMaori;

    @ApiModelProperty("渠道毛利率")
    @JsonProperty("qun_maori_rate")
    private Double qunMaoriRate;

    @ApiModelProperty("分销销售成本")
    @JsonProperty("fen_sales_costs")
    private Integer fenSalesCosts;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("fen_maori")
    private Integer fenMaori;

    @ApiModelProperty("分销毛利率")
    @JsonProperty("fen_maori_rate")
    private Double fenMaoriRate;

    @ApiModelProperty("近一个月销售数量")
    @JsonProperty("sales_num_one_month")
    private Integer salesNumOneMonth;

    @ApiModelProperty("近一个月销售成本")
    @JsonProperty("sales_coses_one_month")
    private Integer salesCosesOneMonth;

    @ApiModelProperty("周转天数")
    @JsonProperty("turnover_days")
    private String turnoverDays;

    @ApiModelProperty("第一次入库时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("已经入库天数")
    @JsonProperty("inbound_days")
    private Integer inboundDays;

    @ApiModelProperty("商品状态")
    @JsonProperty("config_status")
    private Integer configStatus;

    @ApiModelProperty("采购组负责人编码")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("采购组负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;
}
