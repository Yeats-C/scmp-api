package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel("返利时间范围表")
public class ApplyContractPlanType {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("合同编码")
    private String applyContractCode;

    @ApiModelProperty("时间范围1月2季3半年4年")
    private Integer planType;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyContractCode() {
        return applyContractCode;
    }

    public void setApplyContractCode(String applyContractCode) {
        this.applyContractCode = applyContractCode == null ? null : applyContractCode.trim();
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}