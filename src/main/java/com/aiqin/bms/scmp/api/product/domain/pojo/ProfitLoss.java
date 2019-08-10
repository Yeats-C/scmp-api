package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("损益管理")
@Data
public class ProfitLoss extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("订单号")
    private String orderCode;

    @ApiModelProperty("订单类型(0:报损 1:报益)")
    private Byte orderType;

    @ApiModelProperty("仓库编号")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("报溢数量")
    private Long profitQuantity;

    @ApiModelProperty("报溢含税总成本")
    private Long profitTotalCostRate;

    @ApiModelProperty("报损数量")
    private Long lossQuantity;

    @ApiModelProperty("报损含税总成本")
    private Long lossTotalCostRate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("订单状态")
    private String orderStatusCode;

    @ApiModelProperty("订单状态名称")
    private String orderStatusName;

}