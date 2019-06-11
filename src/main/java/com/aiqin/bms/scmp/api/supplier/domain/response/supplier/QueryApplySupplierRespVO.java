package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:供应商申请管理返回
 * @author: wangxu
 * @date: 2018/12/4 0004 11:06
 */
@Data
@ApiModel("供应商申请管理返回")
public class QueryApplySupplierRespVO {
    @ApiModelProperty("供应商申请ID")
    private Long id;

    @ApiModelProperty("申请类型")
    private String applyTypeName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String applySupplierName;

    @ApiModelProperty("供应商类型")
    private String type;

    @ApiModelProperty("审核状态")
    private String applyStatusName;

    @ApiModelProperty("审核人")
    private String reviewer;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewerTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
