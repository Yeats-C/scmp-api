package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("供应商文件")
@Data
public class SupplierFile extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请类型")
    private String applyType;

    @ApiModelProperty("供应商文件路径")
    private String filePath;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("供应商")
    private String supplierName;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件编码")
    private String fileCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("供应商code")
    private String supplierCode;

}