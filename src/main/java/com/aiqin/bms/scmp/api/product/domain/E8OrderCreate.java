package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author:cg
 * @Date:2020/7/13
 * @Content:
 */

@Data
@ApiModel("E8下单数据-德邦发运调用第三方")
public class E8OrderCreate
{
    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="客户单号")
    @JsonProperty("ccode")
    private String ccode;

    @ApiModelProperty(value="寄件人")
    @JsonProperty("send_man")
    private String sendMan;

    @ApiModelProperty(value="寄件人电话")
    @JsonProperty("send_phone")
    private String sendPhone;

    @ApiModelProperty(value="寄件省份")
    @JsonProperty("send_province")
    private String sendProvince;

    @ApiModelProperty(value="寄件市")
    @JsonProperty("send_city")
    private String sendCity;

    @ApiModelProperty(value="寄件区县")
    @JsonProperty("send_district")
    private String sendDistrict;

    @ApiModelProperty(value="寄件乡镇")
    @JsonProperty("send_town")
    private String sendTown;

    @ApiModelProperty(value="寄件具体地址")
    @JsonProperty("send_street_no")
    private String sendStreetNo;

    @ApiModelProperty(value="收件人")
    @JsonProperty("receive_man")
    private String receiveMan;

    @ApiModelProperty(value="收件人电话")
    @JsonProperty("receive_phone")
    private String receivePhone;

    @ApiModelProperty(value="收件省份")
    @JsonProperty("receive_province")
    private String receiveProvince;

    @ApiModelProperty(value="收件市")
    @JsonProperty("receive_city")
    private String receiveCity;

    @ApiModelProperty(value="收件区县")
    @JsonProperty("receive_district")
    private String receiveDistrict;

    @ApiModelProperty(value="收件乡镇")
    @JsonProperty("receive_town")
    private String receiveTown;

    @ApiModelProperty(value="收件具体地址")
    @JsonProperty("receive_street_no")
    private String receiveStreetNo;

    @ApiModelProperty(value="件数")
    @JsonProperty("amount")
    private Integer amount;

    @ApiModelProperty(value="体积")
    @JsonProperty("volume")
    private Double volume;

    @ApiModelProperty(value="下单重量")
    @JsonProperty("weight")
    private Double weight;

    @ApiModelProperty(value="服务方式")
    @JsonProperty("service_mode")
    private String serviceMode;

    @ApiModelProperty(value="保价额")
    @JsonProperty("insurance_limit")
    private Double insuranceLimit;

    @ApiModelProperty(value="支付方式")
    @JsonProperty("settle_type")
    private String settleType;

    @ApiModelProperty(value="代收货款金额")
    @JsonProperty("cod")
    private Double cod;

    @ApiModelProperty(value="需要网点上门提货(货运站true  收货人地址 false)")
    @JsonProperty("if_visit")
    private Boolean ifVisit;

    @ApiModelProperty(value="快件")
    @JsonProperty("if_fast")
    private Boolean ifFast;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;
}