package com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname: ManufacturerUpdateReqVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/2/14
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("制造商修改请求实体")
@Data
public class ManufacturerUpdateReqVo{
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("制造商名称")
    private String name;

    @ApiModelProperty("产地")
    private String placeOrigin;

    @ApiModelProperty("联系人姓名")
    private String contactName;

    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("法人")
    private String legalPerson;

    @ApiModelProperty("注册资金")
    private BigDecimal registeredCapital;

    @ApiModelProperty("公司注册号")
    private String registrationNumber;

    @ApiModelProperty("银联编号")
    private String unionpayNumber;

    @ApiModelProperty("开户行编号")
    private String bankNumber;

    @ApiModelProperty("开户银行")
    private String bankAccount;

    @ApiModelProperty("开户行支行")
    private String accountOpeningBranch;

    @ApiModelProperty("账户名")
    private String accountName;

    @ApiModelProperty("银行账户")
    private String account;

    @ApiModelProperty("制造商编号")
    private String manufacturerCode;

    @ApiModelProperty("关联品牌")
    @Valid
    private List<ManufacturerBrandUpdateReqVo> list;

}
