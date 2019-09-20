package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wangxu
 */
@ApiModel("供应商修改")
@Data
public class SupplierUpdateReqVO {

    @ApiModelProperty(value = "供应商编码",notes = "修改必传字段")
    private String supplierCode;

    @NotEmpty(message = "供应商名称不能为空")
    @ApiModelProperty("供应商名称")
    private String supplierName;

//    @NotEmpty(message = "简称不能为空")
    @ApiModelProperty("简称")
    private String supplierAbbreviation;

    @ApiModelProperty("是否禁用")
    private Byte enable;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;


}