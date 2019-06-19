package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:申请合同查询List 请求vo
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("申请合同查询List 请求vo")
public class QueryApplyContractReqVo  extends PageReq {

    @ApiModelProperty("创建时间起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建结束时间")
    private Date createEndTime;

    @ApiModelProperty("申请合同编号")
    private String applyContractCode;

    @ApiModelProperty("供货单位编号")
    private String supplierCode;

    @ApiModelProperty("供货单位名称")
    private String supplierName;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "申请人", hidden = true)
    private String applyBy;

    @ApiModelProperty("合同名称")
    private String yearName;
}
