package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 供货单位详情页面展示vo
 * @author zth
 * @date 2018/12/10
 */
@ApiModel("供货单位账户信息详情2")
@Data
public class SupplyComAcctInfoRespVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("申请开户银行")
    private String bankAccount;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("户名")
    private String accountName;

    @ApiModelProperty("最高付款额")
    private Long maxPaymentAmount;

    @ApiModelProperty("供货单位账号编码")
    private String supplyCompanyAccountCode;

    @ApiModelProperty("供货单位code")
    private String supplyCompanyCode;

    @ApiModelProperty("供货单位名称")
    private String supplyCompanyName;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;
}
