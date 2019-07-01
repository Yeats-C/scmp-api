package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:修改提交供应商文件
 * @author: wangxu
 * @date: 2018/12/18 0018 10:54
 */
@Data
@ApiModel("供应商修改文件")
public class SupplierFileReqVO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty(value = "申请类型")
    private Byte applyType;

    @ApiModelProperty(value = "供应商文件路径")
    private String filePath;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("申请供应商code")
    private String applySupplierCode;

    @ApiModelProperty("申请供应商名称")
    private String applySupplierName;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

}
