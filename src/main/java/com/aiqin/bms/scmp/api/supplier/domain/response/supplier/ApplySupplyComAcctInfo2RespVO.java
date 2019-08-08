package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 申请供货单位详情页面展示vo
 * @author zth
 * @date 2018/12/10
 */
@ApiModel("供货单位账户信息")
@Data
public class ApplySupplyComAcctInfo2RespVO {
    @ApiModelProperty("申请开户银行")
    private String bankAccount;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("户名")
    private String accountName;

    @ApiModelProperty("最高付款额")
    private Long maxPaymentAmount;

    @ApiModelProperty("申请编码")
    private String applyCompanyAccountCode;

    @ApiModelProperty("申请类型")
    private String applyType;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date applyDate;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date auditorTime;

    @ApiModelProperty("申请供货单位名称")
    private String applySupplyCompanyName;

    @ApiModelProperty("账号编码")
    private String supplyCompanyAccountCode;

    @ApiModelProperty("日志信息")
    private List<LogData> logData;

    @ApiModelProperty("审批流单号")
    private String formNo;

    @ApiModelProperty("功能项")
    private String modelType;
    @ApiModelProperty("功能项编号")
    private String modelTypeCode;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("是否禁用")
    private String enable;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(Byte applyType) {
        if(applyType.equals((byte)1)){
            this.applyType = "新增申请";
        }else if(applyType.equals((byte)2)){
            this.applyType = "修改申请";
        }else {
            this.applyType = "";
        }
    }
}
