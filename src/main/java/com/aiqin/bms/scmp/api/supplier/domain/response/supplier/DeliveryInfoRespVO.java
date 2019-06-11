package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:发货信息返回对象
 * @author: wangxu
 * @date: 2018/12/12 0012 15:59
 */
@Data
@ApiModel("发货信息返回对象")
public class DeliveryInfoRespVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("发送至")
    private String sendTo;

    @ApiModelProperty("发送地址")
    private String sendingAddress;

    @ApiModelProperty("送货天数")
    private Long deliveryDays;

    @ApiModelProperty("省id")
    private String sendProvinceId;

    @ApiModelProperty("省")
    private String sendProvinceName;

    @ApiModelProperty("市id")
    private String sendCityId;

    @ApiModelProperty("市")
    private String sendCityName;

    @ApiModelProperty("区县id")
    private String sendDistrictId;

    @ApiModelProperty("区县")
    private String sendDistrictName;

    @ApiModelProperty("发货类型(0 发货 1退货)")
    private Byte deliveryType;
}
