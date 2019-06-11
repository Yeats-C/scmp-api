package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 申请供货单位账户返回vo
 * @author zth
 * @date 2018/12/7
 */
@Getter
@Setter
@ApiModel("申请供货单位账户返回Vo")
public class QueryApplySupplierComAcctRespVo implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;

//    @ApiModelProperty("申请开户行支行")
//    private String accountOpeningBranch;

    @ApiModelProperty("申请开户银行")
    private String bankAccount;

//    @ApiModelProperty("账户")
//    private String account;
//
//    @ApiModelProperty("户名")
//    private String accountName;
//
//    @ApiModelProperty("最高付款额")
//    private Long maxPaymentAmount;

//    @ApiModelProperty("删除标记(0:正常 1:删除)")
//    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:ss:mm")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

//    @ApiModelProperty("修改时间")
//    private Date updateTime;
//
//    @ApiModelProperty("修改人")
//    private String updateBy;

//    @ApiModelProperty("银联编号")
//    private String unionpayNumber;
//
//    @ApiModelProperty("开户行编号")
//    private String bankNumber;

    @ApiModelProperty("申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )")
    private Byte applyStatus;

    @ApiModelProperty("申请类型（0新增1修改）")
    private String applyType;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:ss:mm")
    private Date auditorTime;

    @ApiModelProperty("申请供货单位code(供货单位code)")
    private String applySupplyCompanyCode;

    @ApiModelProperty("申请供货单位名称(供货单位名称）")
    private String applySupplyCompanyName;

    @ApiModelProperty("申请供货单位账号code(申请编码)")
    private String applyCompanyAccountCode;

    @ApiModelProperty("供货单位账号code(账号编码)")
    private String supplyCompanyAccountCode;

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
