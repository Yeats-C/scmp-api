package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class BiAutomaticOrder {
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value="年月")
    @JsonProperty("order_year_month")
    private String orderYearMonth;

    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="仓库编号")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="库存天数")
    @JsonProperty("inventory_days")
    private Integer inventoryDays;

    @ApiModelProperty(value="近90日的日均销量")
    @JsonProperty("day_num")
    private Integer dayNum;

    @ApiModelProperty(value="到货周期")
    @JsonProperty("arrival_cycle")
    private Integer arrivalCycle;

    @ApiModelProperty(value="订货周期")
    @JsonProperty("order_cycle")
    private Integer orderCycle;

    @ApiModelProperty(value="大库存预警天数")
    @JsonProperty("large_inventory_warn_day")
    private Integer largeInventoryWarnDay;

    @ApiModelProperty(value="基本库存天数")
    @JsonProperty("basic_inventory_day")
    private Integer basicInventoryDay;

    @ApiModelProperty(value="方案1下单个数(1期缺但可以补2期 或者 1期富裕，只补2期)")
    @JsonProperty("case_one")
    private Integer caseOne;

    @ApiModelProperty(value="方案2下单个数(1期注定缺货，只补2期)")
    @JsonProperty("case_two")
    private Integer caseTwo;

    @ApiModelProperty(value="方案3下单个数(最大上限)")
    @JsonProperty("case_three")
    private Integer caseThree;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="原始订单表的创建时间")
    @JsonProperty("create_date")
    private Date createDate;

    @ApiModelProperty(value="计算时间")
    @JsonProperty("create_time")
    private Date createTime;

}