package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("赠品进销存respVo")
@Data
public class GiftsBuySalesRespVo  implements Serializable {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

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

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("厂商指导价")
    @JsonProperty("manufacturer_guide_price")
    private Integer manufacturerGuidePrice;

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

    @ApiModelProperty("库存数量")
    @JsonProperty("inventory_num")
    private Integer inventoryNum;

    @ApiModelProperty("近一个月销售数量")
    @JsonProperty("sales_num_one_month")
    private Integer salesNumOneMonth;

    @ApiModelProperty("近一个月销售金额")
    @JsonProperty("sales_money_one_month")
    private Integer salesMoneyOneMonth;

    @ApiModelProperty("周转天数")
    @JsonProperty("turnover_days")
    private Integer turnoverDays;

    @ApiModelProperty("入库开始时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("已经入库天数")
    @JsonProperty("inbound_days")
    private Integer inboundDays;

    @ApiModelProperty("采购组负责人编码")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("采购组负责人名称")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;


}
