package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @功能说明: 供货单位详情，包括结算和收货信息
 * @author: wangxu
 * @date: 2018/12/12 0012 15:44
 */
@Data
public class SupplyCompanyDetailDTO {
    @ApiModelProperty("供应商Id")
    private Long supplyComId;

    @ApiModelProperty("供应商属性 0:自营 1:平台")
    private String property;

    @ApiModelProperty("付款方式编号")
    private String payTypeCode;
    @ApiModelProperty("付款方式名称")
    private String payTypeName;

    @ApiModelProperty("供应商编号")
    private String supplyCode;

    @ApiModelProperty("申请修改时需要展示的供应商编码")
    private String formalCode;

    @ApiModelProperty("所属集团名称")
    private String supplierName;

    @ApiModelProperty("所属集团编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplyName;

    @ApiModelProperty("供应商类型")
    private String supplyType;

    @ApiModelProperty("供应商类型")
    private String supplyTypeName;

    @ApiModelProperty("供应商简称")
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

    @ApiModelProperty("区县")
    private String districtName;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("联系姓名")
    private String contactName;

    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("传真")
    private String fax;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("公司网站")
    private String companyWebsite;

    @ApiModelProperty("采购组编码")
    private String supplyPurchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String supplyPurchasingGroupName;

    @ApiModelProperty("税号")
    private String taxId;

    @ApiModelProperty("法人代表")
    private String corporateRepresentative;

    @ApiModelProperty("注册资金")
    private BigDecimal registeredCapital;

    @ApiModelProperty("是否禁用")
    private Byte enable;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("最低订货金额")
    private BigDecimal minOrderAmount;

    @ApiModelProperty("最高订货金额")
    private BigDecimal maxOrderAmount;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("评分")
    private BigDecimal starScore;

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

    @ApiModelProperty("结款方式")
    private String paymentMethod;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("供货区域")
    private String deliveryArea;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审批名称")
    private String approvalName;

}
