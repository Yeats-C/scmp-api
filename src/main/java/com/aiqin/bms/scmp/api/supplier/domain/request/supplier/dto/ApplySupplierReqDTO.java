package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @功能说明:供应商申请数据库请求实体
 * @author: wangxu
 * @date: 2018/12/5 0005 11:10
 */
@Data
public class ApplySupplierReqDTO extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty(value = "供应商编码",notes = "修改必传字段")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("简称")
    private String supplierAbbreviation;

    @ApiModelProperty("申请编号")
    private String applySupplierCode;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("申请状态")
    private Byte applyStatus;

    private String formNo;

    @ApiModelProperty("是否禁用")
    private Byte enable;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;

}
