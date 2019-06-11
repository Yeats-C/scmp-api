package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请供应商文件")
@Data
public class SupplierFileReqDTO extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("供应商文件路径")
    private String filePath;

    @ApiModelProperty("申请供应商code")
    private String applySupplierCode;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("申请供应商名称")
    private String applySupplierName;

}