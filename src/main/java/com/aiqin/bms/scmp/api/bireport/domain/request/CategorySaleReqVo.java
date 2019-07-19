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

    @ApiModelProperty("时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("渠道编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("department_name")
    private String departmentName;

    public CategorySaleReqVo(String createTime, String orderCode, String orderOriginal, String departmentCode, String departmentName) {
        this.createTime = createTime;
        this.orderCode = orderCode;
        this.orderOriginal = orderOriginal;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public CategorySaleReqVo() {
    }
}
