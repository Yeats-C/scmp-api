package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 16:23
 */
@ApiModel("申请供货单位账户")
@Data
public class ApplySupplyCompanyAcctReqVO{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("申请开户行支行")
    private String accountOpeningBranch;

    @NotEmpty(message = "申请开户银行不能为空")
    @ApiModelProperty("申请开户银行")
    private String bankAccount;

    @NotEmpty(message = "账户不能为空")
    @ApiModelProperty("账户")
    private String account;

    @NotEmpty(message = "户名不能为空")
    @ApiModelProperty("户名")
    private String accountName;

    @NotNull
    @Min(1)
    @ApiModelProperty("最高付款额")
    private Long maxPaymentAmount;

    @ApiModelProperty("银联编号")
    private String unionpayNumber;

    @ApiModelProperty("开户行编号")
    private String bankNumber;

    @ApiModelProperty(value = "申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )",hidden = true)
    private Byte applyStatus;

    @ApiModelProperty(value = "申请编码",hidden = true)
    private Long applyCode;

    @ApiModelProperty(value = "申请类型(1新增2：修改",hidden = true)
    private Byte applyType;

    @ApiModelProperty(value = "是否删除")
    private Byte delFlag;

    @ApiModelProperty("申请供货单位编码")
    private String applySupplyCompanyCode;

    @ApiModelProperty("申请供货单位名称")
    private String applySupplyCompanyName;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

}
