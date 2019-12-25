package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 17:42
 */
@ApiModel("结算信息")
@Data
public class ApplySettlementInfoReqVO{

    @NotEmpty(message = "结算周期不能为空")
    @ApiModelProperty("结算周期")
    private String settlementCycle;

    @NotEmpty(message = "结算方式不能为空")
    @ApiModelProperty("结算方式")
    private String settlementMethod;

    @NotEmpty(message = "结算组不能为空")
    @ApiModelProperty("结算组")
    private String settlementGroup;

    @NotNull(message = "最低订货金额不能为空")
    @ApiModelProperty("最低订货金额")
    private BigDecimal minOrderAmount;

    @NotNull(message = "最高订货金额不能为空")
    @ApiModelProperty("最高订货金额")
    private BigDecimal maxOrderAmount;

}
