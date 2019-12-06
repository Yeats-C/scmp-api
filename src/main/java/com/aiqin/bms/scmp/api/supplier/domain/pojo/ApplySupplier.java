package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("申请供应商集团")
@Data
public class ApplySupplier extends CommonBean {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商集团名称")
    private String supplierName;

    @ApiModelProperty("简称")
    private String supplierAbbreviation;

    @ApiModelProperty("供应商集团编号")
    private String supplierCode;

    @ApiModelProperty("申请供应商集团编号")
    private String applySupplierCode;

    @ApiModelProperty("申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )")
    private Byte applyStatus;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("审批表单号")
    private String formNo;

    @ApiModelProperty("申请类型")
    private Byte applyType;

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