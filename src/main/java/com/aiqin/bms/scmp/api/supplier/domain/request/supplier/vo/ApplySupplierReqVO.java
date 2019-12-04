package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wangxu
 */
@ApiModel("供应商申请")
@Data
public class ApplySupplierReqVO{

    @NotEmpty(message = "供应商名称不能为空")
    @ApiModelProperty("供应商名称")
    private String supplierName;

//    @NotEmpty(message = "简称不能为空")
    @ApiModelProperty("简称")
    private String supplierAbbreviation;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    @ApiModelProperty("职位编码")
    @NotEmpty(message = "职位编码不能为空！")
    private String positionCode;

    @ApiModelProperty("职位名称")
    @NotEmpty(message = "职位名称不能为空！")
    private String positionName;



}