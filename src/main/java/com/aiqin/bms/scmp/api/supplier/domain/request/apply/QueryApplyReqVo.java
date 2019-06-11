package com.aiqin.bms.scmp.api.supplier.domain.request.apply;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryApplyReqVo
 * @date 2019/4/4 15:52
 * @description TODO
 */
@ApiModel("申请查询条件")
@Data
public class QueryApplyReqVo extends PageReq {

    @ApiModelProperty("创建时间 时间格式:yyyy-MM-dd")
    private String createTimeStart;

    @ApiModelProperty("创建时间 时间格式:yyyy-MM-dd")
    private String createTimeEnd;

    @ApiModelProperty("申请类型 1:新增申请类型 2:修改申请类型")
    private String applyType;

    @ApiModelProperty("功能项 1:供应商 2:供应商集团 3:账户 4:合同")
    @NotEmpty(message = "功能项不能为空")
    private String itemCode;

    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("申请状态 0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销")
    private String applyStatus;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "申请人", hidden = true)
    private String applyBy;
}
