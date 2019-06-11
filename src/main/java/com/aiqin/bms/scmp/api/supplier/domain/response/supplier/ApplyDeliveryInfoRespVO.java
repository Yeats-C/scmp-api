package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:发货申请信息返回对象
 * @author: wangxu
 * @date: 2018/12/7 0007 15:40
 */
@ApiModel("发货申请信息返回对象")
@Data
public class ApplyDeliveryInfoRespVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("发送至")
    private String sendTo;

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

    @ApiModelProperty("发货类型(0 发货 1退货)")
    private Byte deliveryType;
}
