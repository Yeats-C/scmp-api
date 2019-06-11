package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 17:47
 */
@ApiModel("发货信息")
@Data
public class ApplyDeliveryVO {
    @ApiModelProperty("主键Id")
    private Long id;

    @NotEmpty(message = "发送至不能为空")
    @ApiModelProperty("发送至")
    private String sendTo;

    @NotEmpty(message = "发送地址不能为空")
    @ApiModelProperty("发送地址")
    private String sendingAddress;

    @ApiModelProperty("发送送货天数")
    private Long deliveryDays;

    @ApiModelProperty("发送省id")
    private String sendProvinceId;

    @ApiModelProperty("发送省")
    private String sendProvinceName;

    @ApiModelProperty("发送市id")
    private String sendCityId;

    @ApiModelProperty("发送市")
    private String sendCityName;

    @ApiModelProperty("发送区县id")
    private String sendDistrictId;

    @ApiModelProperty("发送区县")
    private String sendDistrictName;


    @NotEmpty(message = "退货至不能为空")
    @ApiModelProperty("退货至")
    private String returnTo;

    @NotEmpty(message = "退货地址不能为空")
    @ApiModelProperty("退货发送地址")
    private String returnAddress;

    @ApiModelProperty("退货送货天数")
    private Long returnDays;

    @ApiModelProperty("退货省id")
    private String returnProvinceId;

    @ApiModelProperty("退货省")
    private String returnProvinceName;

    @ApiModelProperty("退货市id")
    private String returnCityId;

    @ApiModelProperty("退货市")
    private String returnCityName;

    @ApiModelProperty("退货区县id")
    private String returnDistrictId;

    @ApiModelProperty("退货区县")
    private String returnDistrictName;

}
