package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:供应商申请列表返回
 * @author: wangxu
 * @date: 2018/12/7 0007 17:45
 */
@ApiModel("供应商申请列表返回")
@Data
public class ApplySupplierListRespVO {
    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("申请类型")
    private String applyTypeName;

    @ApiModelProperty("供应商编码")
    private String suppierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商类型")
    private String supplierType;

    @ApiModelProperty("审核状态")
    private String applyStatusName;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

}
