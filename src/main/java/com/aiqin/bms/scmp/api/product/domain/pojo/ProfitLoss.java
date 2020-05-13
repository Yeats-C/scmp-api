package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("损益管理")
@Data
public class ProfitLoss extends CommonBean {
    @ApiModelProperty("主键Id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("订单类型(0:报损 1:报益)")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty("仓库编号")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("报溢数量")
    @JsonProperty("profit_quantity")
    private Long profitQuantity;

    @ApiModelProperty("报溢含税总成本")
    @JsonProperty("profit_total_cost_rate")
    private BigDecimal profitTotalCostRate;

    @ApiModelProperty("报损数量")
    @JsonProperty("loss_quantity")
    private Long lossQuantity;

    @ApiModelProperty("报损含税总成本")
    @JsonProperty("loss_total_cost_rate")
    private BigDecimal lossTotalCostRate;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty("订单状态:0完成")
    @JsonProperty("order_status_code")
    private Integer orderStatusCode;

    @ApiModelProperty("订单状态名称")
    @JsonProperty("order_status_name")
    private String orderStatusName;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

}