package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: SupplyComDetailByCodeRespVO
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/7
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("编码查询返回供货单位实体")
public class SupplyComDetailByCodeRespVO {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("供货单位编号")
    private String supplyCode;

    @ApiModelProperty("供应单位名称")
    private String supplyName;

    @ApiModelProperty("供应单位类型")
    private String supplyType;

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

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;
}
