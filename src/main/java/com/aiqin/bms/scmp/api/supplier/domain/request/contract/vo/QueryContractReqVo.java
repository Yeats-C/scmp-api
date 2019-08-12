package com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @description:
 * @author:曾兴旺
 * @date: 2018/11/29
 */
@Data
@ApiModel("合同列表请求")
public class QueryContractReqVo  extends PageReq {

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

    @ApiModelProperty("是否禁用 启用:0, 禁用:1 全部:空字符串")
    private String enable;

    @ApiModelProperty("合同开始-开始时间 时间格式:yyyy-MM-dd")
    private String startTimeStart;

    @ApiModelProperty("合同开始-结束时间 时间格式:yyyy-MM-dd")
    private String startTimeEnd;

    @ApiModelProperty("合同结束-开始时间 时间格式:yyyy-MM-dd")
    private String endTimeStart;

    @ApiModelProperty("合同结束-结束时间 时间格式:yyyy-MM-dd")
    private String endTimeEnd;

    @ApiModelProperty("到期开始时间")
    private Integer dueStartTime;

    @ApiModelProperty("到期结束时间")
    private Integer dueEndTime;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;


}


