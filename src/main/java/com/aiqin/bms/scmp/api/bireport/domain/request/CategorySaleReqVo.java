package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("品类促销request")
@Data
public class CategorySaleReqVo extends PageReq implements Serializable {

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private int month;

    @ApiModelProperty("渠道编码")
    @JsonProperty("channel_code")
    private String channelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("channel_name")
    private String channelName;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("department_name")
    private String departmentName;

    public CategorySaleReqVo(int month, String channelCode, String channelName, String departmentCode, String departmentName) {
        this.month = month;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public CategorySaleReqVo() {
    }
}
