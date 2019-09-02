package com.aiqin.bms.scmp.api.supplier.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商导入返回vo")
@Data
public class SupplierImport {

    @ApiModelProperty("申请供应商编码")
    @ExcelProperty(index = 0 , value = "供应商编码")
    private String applySupplyCode;

    @ApiModelProperty("申请供应商名称")
    @ExcelProperty(index = 1 , value = "供应商名称")
    private String applySupplyName;

    @ApiModelProperty("申请供应商类型")
    @ExcelProperty(index = 2 , value = "供应商类型")
    private String applySupplyType;

    @ApiModelProperty("供应商集团名称")
    @ExcelProperty(index = 3 , value = "供应商集团")
    private String supplierName;

    @ApiModelProperty("简称")
    @ExcelProperty(index = 4 , value = "简称")
    private String applyAbbreviation;

    @ApiModelProperty("电话")
    @ExcelProperty(index = 5 , value = "简称")
    private String phone;

    @ApiModelProperty("传真")
    @ExcelProperty(index = 6 , value = "传真")
    private String fax;

    @ApiModelProperty("省")
    @ExcelProperty(index = 7 , value = "省")
    private String provinceName;

    @ApiModelProperty("市")
    @ExcelProperty(index = 8 , value = "市")
    private String cityName;

    @ApiModelProperty("区县名称")
    @ExcelProperty(index = 9 , value = "区县名称")
    private String districtName;

    @ApiModelProperty("地址")
    @ExcelProperty(index = 10 , value = "地址")
    private String address;

    @ApiModelProperty("邮编")
    @ExcelProperty(index = 11 , value = "邮编")
    private String zipCode;

    @ApiModelProperty("邮箱")
    @ExcelProperty(index = 12 , value = "邮箱")
    private String email;

    @ApiModelProperty("公司网址")
    @ExcelProperty(index = 13 , value = "公司网址")
    private String companyWebsite;

    @ApiModelProperty("税号")
    @ExcelProperty(index = 14 , value = "税号")
    private String taxId;

    @ApiModelProperty("注册资金")
    @ExcelProperty(index = 15 , value = "注册资金")
    private String registeredCapital;

    @ApiModelProperty("法人代表")
    @ExcelProperty(index = 16 , value = "法人代表")
    private String corporateRepresentative;

    @ApiModelProperty("联系姓名")
    @ExcelProperty(index = 17 , value = "联系姓名")
    private String contactName;

    @ApiModelProperty("手机号码")
    @ExcelProperty(index = 18 , value = "手机号码")
    private String mobilePhone;

    @ApiModelProperty("最低订货金额")
    @ExcelProperty(index = 19 , value = "最低订货金额")
    private String minOrderAmount;

    @ApiModelProperty("最高订货金额")
    @ExcelProperty(index = 20 , value = "最高订货金额")
    private String maxOrderAmount;

    @ApiModelProperty("发送省")
    @ExcelProperty(index = 21 , value = "发送省")
    private String sendProvinceName;

    @ApiModelProperty("发送市")
    @ExcelProperty(index = 22 , value = "发送市")
    private String sendCityName;

    @ApiModelProperty("发送区县")
    @ExcelProperty(index = 23 , value = "发送区县")
    private String sendDistrictName;

    @ApiModelProperty("发送地址")
    @ExcelProperty(index = 24 , value = "发送地址")
    private String sendingAddress;

    @ApiModelProperty("发送至")
    @ExcelProperty(index = 25 , value = "发送至")
    private String sendTo;

    @ApiModelProperty("发送送货天数")
    @ExcelProperty(index = 26 , value = "发送送货天数")
    private String deliveryDays;

    @ApiModelProperty("退送省")
    @ExcelProperty(index = 27 , value = "退送省")
    private String returnProvinceName;

    @ApiModelProperty("退送市")
    @ExcelProperty(index = 28 , value = "退送市")
    private String returnCityName;

    @ApiModelProperty("退送区县")
    @ExcelProperty(index = 29 , value = "退送区县")
    private String returnDistrictName;

    @ApiModelProperty("退送地址")
    @ExcelProperty(index = 30 , value = "退送地址")
    private String returningAddress;

    @ApiModelProperty("退送至")
    @ExcelProperty(index = 31 , value = "退送至")
    private String returnTo;

    @ApiModelProperty("退送送货天数")
    @ExcelProperty(index = 32 , value = "退送送货天数")
    private String returnDays;


    @ApiModelProperty("开户银行")
    @ExcelProperty(index = 28 , value = "开户银行")
    private String bankAccount;

    @ApiModelProperty("银行账号")
    @ExcelProperty(index = 29 , value = "银行账号")
    private String account;

    @ApiModelProperty("户名")
    @ExcelProperty(index = 30 , value = "户名")
    private String accountName;

    @ApiModelProperty("最高付款金额")
    @ExcelProperty(index = 31 , value = "最高付款金额")
    private String maxPaymentAmount;

    @ApiModelProperty("错误信息")
    private String error;
}
