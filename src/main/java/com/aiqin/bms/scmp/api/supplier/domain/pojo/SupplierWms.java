package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("wms供货商传输单位")
@Data
public class SupplierWms extends CommonBean {

    @ApiModelProperty("供应商名称")
    @JsonProperty("supply_name")
    private String supplyName;

    @ApiModelProperty("供应商编号")
    @JsonProperty("supply_code")
    private String supplyCode;

    @ApiModelProperty("供应商单位类型")
    @JsonProperty("supply_type")
    private String  supplyType;

    @ApiModelProperty("供应商集团简称")
    @JsonProperty("supplier_abbreviation")
    private String supplierAbbreviation;

    @ApiModelProperty("联系姓名")
    @JsonProperty("contact_name")
    private String contactName;

    @ApiModelProperty("手机号码")
    @JsonProperty("mobile_phone")
    private String mobilePhone;

    @ApiModelProperty("邮箱")
    @JsonProperty("email")
    private String email;


    @ApiModelProperty("地区")
    @JsonProperty("district_name")
    private String area;

    @ApiModelProperty("地址")
    @JsonProperty("address")
    private String address;


    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty("是否禁用(0:正常 1:禁用)")
    @JsonProperty("enable")
    private Byte enable;

    @ApiModelProperty("修改时间")
    @JsonProperty("update_time")
    private Date updateTime;
}