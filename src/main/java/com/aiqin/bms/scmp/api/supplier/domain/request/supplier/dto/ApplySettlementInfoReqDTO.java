package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:结算信息
 * @author: wangxu
 * @date: 2018/11/30 0030 17:42
 */
@Data
public class ApplySettlementInfoReqDTO extends CommonBean{
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("结算周期")
    private String settlementCycle;

    @ApiModelProperty("结算方式")
    private String settlementMethod;

    @ApiModelProperty("结算组")
    private String settlementGroup;

    @ApiModelProperty("最低订货金额")
    private Long minOrderAmount;

    @ApiModelProperty("最高订货金额")
    private Long maxOrderAmount;

    @ApiModelProperty("所属供货单位")
    private String applySupplyCompanyCode;

    @ApiModelProperty("所属供货单位")
    private String applySupplyCompanyName;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty("申请类型")
    private Byte applyType;

}
