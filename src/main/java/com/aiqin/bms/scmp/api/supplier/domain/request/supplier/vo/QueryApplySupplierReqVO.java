package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 19:11
 */
@ApiModel("供应商申请管理查询")
@Data
public class QueryApplySupplierReqVO extends PageReq {
    @ApiModelProperty("创建时间开始 yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTimeStart;

    @ApiModelProperty("创建时间结束 yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTimeEnd;

    @ApiModelProperty(value = "申请类型",notes = "1为新增,2为修改,4为撤销")
    private Byte applyType;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String applySupplierName;

    @ApiModelProperty(value = "审核状态",notes = "0为待审,1为审核中,2为审核通过,3为审核未通过,4为已撤销")
    private Byte reviewerStatus;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "申请人", hidden = true)
    private String applyBy;


}
