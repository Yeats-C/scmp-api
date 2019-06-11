package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:申请信息返回对象
 * @author: wangxu
 * @date: 2018/12/7 0007 15:30
 */
@ApiModel("申请信息返回对象")
@Data
public class ApplyInfoRespVO {
    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("申请类型")
    private String applyTypeName;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date applyTime;

    @ApiModelProperty("审核流程编码")
    private String formNo;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date auditorTime;

    @ApiModelProperty("功能项")
    private String modelType;
    @ApiModelProperty("功能项编号")
    private String modelTypeCode;

    @ApiModelProperty("状态")
    private String status;
}
