package com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-01
 * @time: 14:38
 */
@ApiModel("目标返利vo")
@Data
public class PlanTypeReqVO {
    @ApiModelProperty("计划类型(月度,季度,半年,全年)")
    private Byte planType;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;
}
