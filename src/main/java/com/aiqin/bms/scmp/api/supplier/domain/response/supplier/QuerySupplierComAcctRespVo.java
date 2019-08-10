package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: zth
 * @date: 2018-12-07
 * @time: 17:45
 */
@Data
public class QuerySupplierComAcctRespVo {
    @ApiModelProperty("主键id")
    private Long id;

//    @ApiModelProperty("申请开户行支行")
//    private String accountOpeningBranch;

    @ApiModelProperty("申请开户银行")
    private String bankAccount;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("户名")
    private String accountName;

    @ApiModelProperty("最高付款额")
    private Long maxPaymentAmount;

//    @ApiModelProperty("删除标记(0:正常 1:删除)")
//    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

//    @ApiModelProperty("银联编号")
//    private String unionpayNumber;
//
//    @ApiModelProperty("开户行编号")
//    private String bankNumber;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

//    @ApiModelProperty("申请类型")
//    private Byte applyType;
//
//    @ApiModelProperty("审核人")
//    private String auditorBy;
//
//    @ApiModelProperty("审核时间")
//    private Date auditorTime;

    @ApiModelProperty("申请供货单位code")
    private String supplyCompanyCode;

    @ApiModelProperty("申请供货单位名称")
    private String supplyCompanyName;

//    @ApiModelProperty("申请供货单位账号code")
//    private String applyCompanyAccountCode;

    @ApiModelProperty("供货单位账号编码")
    private String supplyCompanyAccountCode;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;
}
