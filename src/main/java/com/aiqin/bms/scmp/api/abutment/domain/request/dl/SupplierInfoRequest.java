package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("对接DL供应商主表")
public class SupplierInfoRequest {

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商简称")
    @JsonProperty("supplier_abbreviation")
    private String supplierAbbreviation;

    @ApiModelProperty(value="供应商类型")
    @JsonProperty("supplier_type")
    private String supplierType;

    @ApiModelProperty(value="电话")
    @JsonProperty("mobile")
    private String mobile;

    @ApiModelProperty(value="传真")
    @JsonProperty("fax")
    private String fax;

    @ApiModelProperty(value="网址")
    @JsonProperty("company_website")
    private String companyWebsite;

    @ApiModelProperty(value="税号")
    @JsonProperty("tax_id")
    private String taxId;

    @ApiModelProperty(value="注册资金")
    @JsonProperty("registered_capital")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value="法人代表")
    @JsonProperty("corporate_representative")
    private String corporateRepresentative;

    @ApiModelProperty(value="最低订货金额")
    @JsonProperty("min_order_amount")
    private BigDecimal minOrderAmount;

    @ApiModelProperty(value="最高订货金额")
    @JsonProperty("max_order_amount")
    private BigDecimal maxOrderAmount;

    @ApiModelProperty(value="联系人手机号")
    @JsonProperty("contacts_phone")
    private String contactsPhone;

    @ApiModelProperty(value="联系人")
    @JsonProperty("contacts")
    private String contacts;

    @ApiModelProperty(value="省编码")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty(value="省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市编码")
    @JsonProperty("city_code")
    private String cityCode;

    @ApiModelProperty(value="市")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="区/县编码")
    @JsonProperty("district_code")
    private String districtCode;

    @ApiModelProperty(value="区/县")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="详细地址")
    @JsonProperty("detail_address")
    private String detailAddress;

    @ApiModelProperty(value="邮编")
    @JsonProperty("zip_code")
    private String zipCode;

    @ApiModelProperty(value="邮箱")
    @JsonProperty("email")
    private String email;

    @ApiModelProperty(value="结款方式")
    @JsonProperty("payment_method")
    private String paymentMethod;

    @ApiModelProperty(value="是否启用 0:启用 1:禁用")
    @JsonProperty("enable")
    private Integer enable;

    @ApiModelProperty(value="供应商属性  0:自营 1:平台")
    @JsonProperty("property")
    private String property;

    @ApiModelProperty(value="供应商集团编码")
    @JsonProperty("supplier_company_code")
    private String supplierCompanyCode;

    @ApiModelProperty(value="供应商集团名称")
    @JsonProperty("supplier_company_name")
    private String supplierCompanyName;

    @ApiModelProperty(value="开户行")
    @JsonProperty("bank_account")
    private String bankAccount;

    @ApiModelProperty(value="户名")
    @JsonProperty("account_name")
    private String accountName;

    @ApiModelProperty(value="银行账号")
    @JsonProperty("account")
    private String account;

    @ApiModelProperty(value="最高付款金额")
    @JsonProperty("max_payment_amount")
    private BigDecimal maxPaymentAmount;

    @ApiModelProperty(value="采购组")
    @JsonProperty("purchase_group_name")
    private List<String> purchaseGroupName;

    @ApiModelProperty(value="发货退货信息")
    @JsonProperty("delivery_list")
    private List<SupplierDeliveryRequest> deliveryList;
}
