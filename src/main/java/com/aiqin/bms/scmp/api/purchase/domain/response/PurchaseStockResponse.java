package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-08-09
 **/
@Data
public class PurchaseStockResponse {

    @ApiModelProperty(value="sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="可用库存数")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty(value="昨日成本")
    @JsonProperty("tax_cost")
    private Long taxCost;

    @ApiModelProperty(value="大库存预警天数")
    @JsonProperty("large_inventory_warn_day")
    private Integer LargeInventoryWarnDay;

    @ApiModelProperty(value="库存周转天数")
    @JsonProperty("days_turnover")
    private Double daysTurnover;

    @ApiModelProperty(value="近三月平均日销量")
    @JsonProperty("sales_avg_3_month_num")
    private Double salesAvgMonthNum;

}
