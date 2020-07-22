package com.aiqin.bms.scmp.api.abutment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class DlStoreInfo {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="门店编码")
    @JsonProperty("store_code")
    private String storeCode;

    @ApiModelProperty(value="门店名称")
    @JsonProperty("store_name")
    private String storeName;

    @ApiModelProperty(value="联系人")
    @JsonProperty("contacts")
    private String contacts;

    @ApiModelProperty(value="联系人电话")
    @JsonProperty("contacts_phone")
    private String contactsPhone;

    @ApiModelProperty(value="身份证号")
    @JsonProperty("id_number")
    private String idNumber;

    @ApiModelProperty(value="省编码")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty(value="省")
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

    @ApiModelProperty(value="是否启用 0 启用 1.禁用")
    @JsonProperty("use_status")
    private Integer useStatus;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

}