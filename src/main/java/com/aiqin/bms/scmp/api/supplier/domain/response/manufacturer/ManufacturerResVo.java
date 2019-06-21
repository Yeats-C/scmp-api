package com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer;

import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Classname: ManufacturerResVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/2/14
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("制造商页面查看返回实体")
@Data
public class ManufacturerResVo {
    @ApiModelProperty("主键")
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
    private Long registeredCapital;

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

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("制造商编号")
    private String manufacturerCode;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("制造商关联品牌")
    private List<ManufacturerBrandResVo> list;

    @ApiModelProperty("操作日志")
    private List<LogData> logDataList;
}
