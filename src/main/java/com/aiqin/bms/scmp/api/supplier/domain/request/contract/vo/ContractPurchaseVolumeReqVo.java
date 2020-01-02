package com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 描述:合同请求保存和编辑进货额实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@ApiModel("合同请求保存和编辑进货额实体")
@Data
public class ContractPurchaseVolumeReqVo {

    @ApiModelProperty("0:无税1:有税")
    @NotNull(message = "请选择有税无税")
    private Byte tax;

    @ApiModelProperty("金额(以分为单位)")
    @NotNull(message = "进货额不能为空")
    private BigDecimal amountMoney;

    @ApiModelProperty("按比例")
    private BigDecimal proportion;

    @ApiModelProperty("或者金额(以分为单位)")
    private BigDecimal orAmountMoney;

    @ApiModelProperty("返利类型1月2季3半年4年")
    private Integer rebateType;
}
