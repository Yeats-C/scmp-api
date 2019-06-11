package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description: 供货单位账户DTO 用于数据转换
 *
 * @author: zth
 * @date: 2018-12-11
 * @time: 10:01
 */
@Data
public class SupplyCompanyAccountDTO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请开户行支行")
    private String accountOpeningBranch;

    @ApiModelProperty("开户银行")
    private String bankAccount;

    @ApiModelProperty("户名")
    private String accountName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("最高付款额")
    private Long maxPaymentAmount;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("银联编号")
    private String unionpayNumber;

    @ApiModelProperty("开户行编号")
    private String bankNumber;

    @ApiModelProperty("申请供货单位账号code")
    private String applyCompanyAccountCode;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )")
    private Byte applyStatus;

    @ApiModelProperty("供货单位code")
    private String supplyCompanyCode;

    @ApiModelProperty("供货单位名称")
    private String supplyCompanyName;

    @ApiModelProperty("供货单位账号code")
    private String supplyCompanyAccountCode;


}
