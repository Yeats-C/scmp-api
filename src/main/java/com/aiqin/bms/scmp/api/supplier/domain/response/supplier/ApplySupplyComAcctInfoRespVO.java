package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/7 0007 15:53
 */
@ApiModel("供货单位账户申请详情返回")
@Data
public class ApplySupplyComAcctInfoRespVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请开户银行")
    private String bankAccount;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("户名")
    private String accountName;

    @ApiModelProperty("最高付款额")
    private BigDecimal acctMaxPaymentAmount;
}
