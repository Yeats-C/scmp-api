package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiModel("新品批次动销率respVo")
@Data
public class NewProductBatchMovingRateRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年月日")
    @JsonProperty("state_date")
    private String stateDate;

    @ApiModelProperty("仓库code")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库name")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

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

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("一级品类")
    @JsonProperty("category_code_one")
    private String categoryCodeOne;

    @ApiModelProperty("一级品类名称")
    @JsonProperty("category_name_one")
    private String categoryNameOne;

    @ApiModelProperty("二级品类")
    @JsonProperty("category_code_two")
    private String categoryCodeTwo;

    @ApiModelProperty("二级品类名称")
    @JsonProperty("category_name_two")
    private String categoryNameTwo;

    @ApiModelProperty("三级品类")
    @JsonProperty("category_code_three")
    private String categoryCodeThree;

    @ApiModelProperty("三级品类名称")
    @JsonProperty("category_name_three")
    private String categoryNameThree;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("含税进价")
    @JsonProperty("purchase_price")
    private Long purchasePrice;

    @ApiModelProperty("厂商指导价")
    @JsonProperty("manufacturer_guide_price")
    private Long manufacturerGuidePrice;

    @ApiModelProperty("库存数量")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty("库存成本")
    @JsonProperty("tax_cost")
    private Long taxCost;

    @ApiModelProperty("销售数量")
    @JsonProperty("num")
    private Long num;

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_costs")
    private Long salesCosts;

    @ApiModelProperty("销售渠道code")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("销售渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("渠道销售金额")
    @JsonProperty("channel_amount")
    private Long channelAmount;

    @ApiModelProperty("渠道毛利额")
    @JsonProperty("channel_maori")
    private Long channelMaori;

    @ApiModelProperty("渠道毛利率")
    @JsonProperty("channel_maori_rate")
    private BigDecimal channelMaoriRate;

    @ApiModelProperty("分销销售成本")
    @JsonProperty("distribution_sales_costs")
    private Long distributionSalesCosts;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("distribution_maori")
    private Long distributionMaori;

    @ApiModelProperty("分销毛利率")
    @JsonProperty("distribution_maori_rate")
    private BigDecimal distributionMaoriRate;

    @ApiModelProperty("近一个月销售数量")
    @JsonProperty("sales_num_one_month")
    private Long salesNumOneMonth;

    @ApiModelProperty("近一个月销售成本")
    @JsonProperty("sales_coses_one_month")
    private Long salesCosesOneMonth;

    @ApiModelProperty("周转天数")
    @JsonProperty("turnover_days")
    private Long turnoverDays;

    @ApiModelProperty("第一次入库时间")
    @JsonProperty("inbound_time")
    private String inboundTime;

    @ApiModelProperty("已经入库天数")
    @JsonProperty("inbound_days")
    private Long inboundDays;

    @ApiModelProperty("商品状态")
    @JsonProperty("config_status")
    private Integer configStatus;

    @ApiModelProperty("采购组负责人编码")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("采购组负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;

}
