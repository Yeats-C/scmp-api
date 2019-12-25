package com.aiqin.bms.scmp.api.product.domain.response.profitloss;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryProfitLossRespVo
 * @date 2019/6/28 11:41
 */
@ApiModel("列表查询数据返回")
@Data
public class QueryProfitLossRespVo {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("订单号")
    private String orderCode;

    @ApiModelProperty("订单类型(0:报损 1:报益)")
    private Byte orderType;

    @ApiModelProperty("订单类型名称")
    private String orderTypeName;

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

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;


}
