package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: zth
 * @date: 2018-12-07
 * @time: 17:27
 */
@Data
@ApiModel("供货单位账户查询Vo")
public class QuerySupplierComAcctReqVo extends PageReq {

    @ApiModelProperty("创建时间从")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDateStart;

    @ApiModelProperty("创建时间到")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDateEnd;

    @ApiModelProperty("开户银行")
    private String bankAccount;

    @ApiModelProperty("账号编码")
    private String supplyCompanyAccountCode;

    @ApiModelProperty("供货单位编码")
    private String supplyCompanyCode;

    @ApiModelProperty("供货单位名称")
    private String supplyCompanyName;

    @ApiModelProperty("是否禁用")
    private Byte delFlag;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "申请人", hidden = true)
    private String applyBy;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;
}
