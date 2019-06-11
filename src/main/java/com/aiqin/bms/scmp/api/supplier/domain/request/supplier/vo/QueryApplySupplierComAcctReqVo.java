package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 申请供货单位账户查询Vo
 * @author zth
 * @date 2018/12/7
 */
@Data
@ApiModel("申请供货单位账户查询Vo")
public class QueryApplySupplierComAcctReqVo extends PageReq {
    @ApiModelProperty("创建时间从")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDateStart;

    @ApiModelProperty("创建时间到")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDateEnd;

//    @ApiModelProperty("开户银行")
//    private String bankAccount;

    @ApiModelProperty("供货单位名称")
    private String applySupplyCompanyName;

    @ApiModelProperty("供货单位编码")
    private String applySupplyCompanyCode;

    @ApiModelProperty("(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )")
    private Byte applyStatus;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("申请账号编码")
    private String applyCompanyAccountCode;

    @ApiModelProperty("账号编码")
    private String supplyCompanyAccountCode;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "申请人", hidden = true)
    private String applyBy;
}
