package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 描述: 新增申请合同接受进货额
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("新增申请合同接受进货额")
public class ApplyContractPurchaseVolumeReqVo {

    @ApiModelProperty("0:无税1:有税")
    @NotNull(message = "有税无税必须选择")
    private Byte tax;

    @ApiModelProperty("金额(以分为单位)")
    @NotNull(message = "进货额不能为空")
    private BigDecimal amountMoney;

    @ApiModelProperty("按比例")
    private BigDecimal proportion;

    @ApiModelProperty("或者金额(以分为单位)")
    private BigDecimal orAmountMoney;

    @ApiModelProperty("计划类型(月度,季度,半年,全年)")
    private Byte planType;

}
