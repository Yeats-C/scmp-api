package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 17:42
 */
@ApiModel("结算信息")
@Data
public class ApplySettlementDTO {

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("结算周期")
    private String settlementCycle;

    @ApiModelProperty("结算方式")
    private String settlementMethod;

    @ApiModelProperty("结算组")
    private String settlementGroup;

    @ApiModelProperty("最低订货金额")
    private BigDecimal minOrderAmount;

    @ApiModelProperty("最高订货金额")
    private BigDecimal maxOrderAmount;

    @ApiModelProperty("申请所属供货单位code")
    private String applySupplyCompanyCode;
    @ApiModelProperty("申请所属供货单位code")
    private String applySupplyCompanyName;
    /**
     * 申请类型
     */
    private Byte applyType;

}
