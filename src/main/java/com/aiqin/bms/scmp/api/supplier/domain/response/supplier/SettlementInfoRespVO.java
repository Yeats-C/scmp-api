package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:结算信息返回
 * @author: wangxu
 * @date: 2018/12/12 0012 15:58
 */
@Data
@ApiModel("结算信息返回")
public class SettlementInfoRespVO {
    @ApiModelProperty("主键id")
    private Long id;

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

}
