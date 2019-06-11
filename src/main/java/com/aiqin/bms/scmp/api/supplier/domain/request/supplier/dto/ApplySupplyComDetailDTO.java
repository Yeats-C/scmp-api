package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @功能说明:供货单位申请详情映射实体
 * @author: wangxu
 * @date: 2018/12/7 0007 14:28
 */
@Data
public class ApplySupplyComDetailDTO {
    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("申请类型")
    private String applyTypeName;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date applyTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date auditorTime;

    @ApiModelProperty("审核流程编码")
    private String formNo;

    @ApiModelProperty("状态")
    private Byte applyStatus;

    @ApiModelProperty("申请供货单位名称")
    private String applySupplyName;

    @ApiModelProperty("申请供货单位编码")
    private String applySupplyCode;

    @ApiModelProperty("申请供货单位类型")
    private String applySupplyType;

    @ApiModelProperty("所属供应商编码")
    private String supplierCode;

    @ApiModelProperty("所属供应商名称")
    private String supplierName;

    @ApiModelProperty("简称")
    private String supplyAbbreviation;

    @ApiModelProperty("省id")
    private String provinceId;

    @ApiModelProperty("省")
    private String provinceName;

    @ApiModelProperty("市id")
    private String cityId;

    @ApiModelProperty("市")
    private String cityName;

    @ApiModelProperty("区县id")
    private String districtId;

    @ApiModelProperty("区县名称")
    private String districtName;

    @ApiModelProperty("地址")
    private String supplyAddress;

    @ApiModelProperty("联系人")
    private String supplyContactName;

    @ApiModelProperty("联系电话")
    private String supplyMobilePhone;

    @ApiModelProperty("电话")
    private String supplyPhone;

    @ApiModelProperty("传真")
    private String supplyFax;

    @ApiModelProperty("邮箱")
    private String supplyEmail;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("供货单位采购组")
    private String supplyPurchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String supplyPurchasingGroupName;

    @ApiModelProperty("税号")
    private String taxId;

    @ApiModelProperty("法人代表")
    private String corporateRepresentative;

    @ApiModelProperty("注册资金")
    private Long registeredCapital;

    @ApiModelProperty("公司网址")
    private String companyWebsite;

    @ApiModelProperty("是否新增账户信息")
    private Byte addAccount;

    @ApiModelProperty("是否禁用")
    private String enable;

    @ApiModelProperty("最低订货金额")
    private Long minOrderAmount;

    @ApiModelProperty("最高订货金额")
    private Long maxOrderAmount;

    @ApiModelProperty("最高付款额")
    private Long maxPaymentAmount;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("评分")
    private BigDecimal starScore;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;


}
