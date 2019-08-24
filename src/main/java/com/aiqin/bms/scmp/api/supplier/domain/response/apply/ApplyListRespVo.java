package com.aiqin.bms.scmp.api.supplier.domain.response.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @date 2019/4/4 16:04

 */
@ApiModel("申请列表返回数据")
@Data
public class ApplyListRespVo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("流程编号")
    private String formNo;

    @ApiModelProperty("申请类型")
    private String applyType;

    public String getApplyType() {
        if("4".equals(this.modelTypeCode)){
            return "0".equals(this.applyType)?"1":"2";
        }
        else {
            return this.applyType;
        }
    }

    @ApiModelProperty("功能项")
    private String modelType;

    @ApiModelProperty("功能项")
    private String modelTypeCode;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8",pattern ="yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty("审核人")
    private String approvalBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalTime;

    @ApiModelProperty("状态")
    private String status;

}
