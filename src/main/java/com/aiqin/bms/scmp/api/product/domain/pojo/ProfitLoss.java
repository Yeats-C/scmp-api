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
    private Integer orderType;

    @ApiModelProperty("仓库编号")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private Long totalCostRate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("订单状态")
    private String orderStatusCode;

    @ApiModelProperty("订单状态名称")
    private String orderStatusName;

}