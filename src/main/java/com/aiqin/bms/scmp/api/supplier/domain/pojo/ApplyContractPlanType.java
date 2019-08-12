package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("返利时间范围表")
@Data
public class ApplyContractPlanType {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("合同编码")
    private String applyContractCode;

    @ApiModelProperty("时间范围1月2季3半年4年")
    private Byte planType;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

}