package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:供应商申请详情映射实体
 * @author: wangxu
 * @date: 2018/12/7 0007 14:28
 */
@Data
public class ApplySupplierDetailDTO {
    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("申请类型")
    private String applyTypeName;

    @ApiModelProperty("审批流程编码")
    private String formNo;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date applyTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date auditorTime;

    @ApiModelProperty("申请供应商集团主键id")
    private Long applySupplierId;

    @ApiModelProperty("申请供应商集团名称")
    private String applySupplierName;

    @ApiModelProperty("申请供应商集团简称")
    private String applySupplierAbbreviation;

    @ApiModelProperty("申请供应商集团编码")
    private String supplierCode;

    @ApiModelProperty("状态")
    private Byte applyStatus;

    @ApiModelProperty("是否禁用")
    private String enable;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

}
