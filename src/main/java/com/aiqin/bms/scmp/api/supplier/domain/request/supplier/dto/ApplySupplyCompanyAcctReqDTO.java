package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 16:23
 */
//@EqualsAndHashCode(callSuper = true)
@ApiModel("申请供货单位账户dto")
@Data
public class ApplySupplyCompanyAcctReqDTO extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请开户行支行")
    @NotEmpty(message = "申请开户行支行不能为空")
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
    private BigDecimal maxPaymentAmount;

    @ApiModelProperty("银联编号")
    private String unionpayNumber;

    @ApiModelProperty("开户行编号")
    private String bankNumber;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("申请供货单位编码")
    private String applySupplyCompanyCode;

    @ApiModelProperty("申请供货单位名称")
    private String applySupplyCompanyName;

    @ApiModelProperty("审核申请单号")
    private String formNo;

    @ApiModelProperty("是否展示此条记录")
    private Byte applyShow;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;
}
