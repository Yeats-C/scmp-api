package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("损益管理")
@Data
public class ProfitLoss extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("订单号")
    private String orderCode;

    @ApiModelProperty("订单类型(0:报损 1:报益)")
    private Integer orderType;

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
    private BigDecimal profitTotalCostRate;

    @ApiModelProperty("报损数量")
    private Long lossQuantity;

    @ApiModelProperty("报损含税总成本")
    private BigDecimal lossTotalCostRate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("订单状态:0完成")
    private Integer orderStatusCode;

    @ApiModelProperty("订单状态名称")
    private String orderStatusName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

}