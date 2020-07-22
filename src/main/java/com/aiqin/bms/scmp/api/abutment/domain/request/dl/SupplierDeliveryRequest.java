package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("对接DL供应商发货退货信息")
public class SupplierDeliveryRequest {

    @ApiModelProperty(value="发货退货类型 0 发货 1退货 ")
    @JsonProperty("delivery_type")
    private Integer deliveryType;

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
}
