package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:供货单位申请管理返回
 * @author: wangxu
 * @date: 2018/12/4 0004 19:25
 */
@Data
@ApiModel("供货单位申请管理返回")
public class QueryApplySupplyComRespVO {
    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("供货单位名称")
    private String supplyName;

    @ApiModelProperty("供货单位类型")
    private String supplyType;

    @ApiModelProperty("简称")
    private String abbreviation;

    @ApiModelProperty("联系人")
    private String contactName;

    @ApiModelProperty("手机号码")
    private Integer mobilePhone;

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
