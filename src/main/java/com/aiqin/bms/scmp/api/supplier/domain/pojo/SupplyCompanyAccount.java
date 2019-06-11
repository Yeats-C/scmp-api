package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("供货单位账号")
@Data
public class SupplyCompanyAccount extends CommonBean {
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

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;


}