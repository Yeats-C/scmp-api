package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiModel("批次商品进销存respVo")
@Data
public class GoodsBuySalesRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("日期时间")
    @JsonProperty("inbound_time")
    private String inboundTime;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

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

    @ApiModelProperty("品牌code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("厂商指导价")
    @JsonProperty("manufacturer_guide_price")
    private Long manufacturerGuidePrice;

    @ApiModelProperty("可用库存数量")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty("库存成本")
    @JsonProperty("tax_cost")
    private Long taxCost;

    @ApiModelProperty("近一个月销售数量")
    @JsonProperty("sales_num_one_month")
    private Long salesNumOneMonth;

    @ApiModelProperty("近一个月销售成本")
    @JsonProperty("sales_cost_one_month")
    private Long salesCostOneMonth;

    @ApiModelProperty("近一个月分销销售金额")
    @JsonProperty("distribution_sales_money_one_month")
    private Long distributionSalesMoneyOneMonth;

    @ApiModelProperty("近一个月分销毛利额")
    @JsonProperty("distribution_sales_maori_one_month")
    private Long distributionSalesMaoriOneMonth;

    @ApiModelProperty("近一个月分销毛利率")
    @JsonProperty("distribution_sales_maori_one_month_rate")
    private BigDecimal distributionSalesMaoriOneMonthRate;

    @ApiModelProperty("近一个月渠道销售金额")
    @JsonProperty("channel_sales_money_one_month")
    private Long channelSalesMoneyOneMonth;

    @ApiModelProperty("近一个月渠道毛利额")
    @JsonProperty("channel_sales_maori_one_month")
    private Long channelSalesMaoriOneMonth;

    @ApiModelProperty("近一个月渠道毛利率")
    @JsonProperty("channel_sales_maori_one_month_rate")
    private BigDecimal channelSalesMaoriOneMonthRate;

    @ApiModelProperty("周转天数")
    @JsonProperty("turnover_days")
    private Long turnoverDays;

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

    @ApiModelProperty("生产日期")
    @JsonProperty("production_date")
    private String productionDate;

    @ApiModelProperty("保质期")
    @JsonProperty("quality_number")
    private String qualityNumber;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("可用库存数量合计")
    @JsonProperty("available_nums")
    private Long availableNums;

    @ApiModelProperty("库存成本合计")
    @JsonProperty("tax_costs")
    private Long taxCosts;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
