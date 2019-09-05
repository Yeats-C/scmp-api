package com.aiqin.bms.scmp.api.supplier.domain.response.applycontract;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述: 申请制造商列表展示返回vo
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("申请制造商列表展示返回vo")
public class QueryApplyContractResVo {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请合同编号")
    private String applyContractCode;

    @ApiModelProperty("合同编号")
    private String contractCode;

    @ApiModelProperty("合同名称")
    private String yearName;

    @ApiModelProperty("供应商编号")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("采购组编号")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @ApiModelProperty("申请状态(0:待审 1:审批中 2:审批通过 3:审批失败 4:已撤销 5:待提交 )")
    private Byte applyStatus;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;


}
