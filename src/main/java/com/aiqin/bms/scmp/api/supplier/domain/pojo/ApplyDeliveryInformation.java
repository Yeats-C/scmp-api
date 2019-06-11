package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@ApiModel("申请发货信息")
@Data
public class ApplyDeliveryInformation extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("申请编码")
    private String applyCode;

    @NotEmpty(message = "发送至不能为空")
    @ApiModelProperty("发送至")
    private String sendTo;

    @NotEmpty(message = "发送地址不能为空")
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

    @ApiModelProperty("所属申请供货单位")
    private String applySupplyCompanyCode;

    @ApiModelProperty("所属申请供货单位")
    private String applySupplyCompanyName;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("所属申请供货单位")
    private String supplyCompanyCode;

    @ApiModelProperty("所属申请供货单位")
    private String supplyCompanyName;

    @ApiModelProperty("申请发货信息code")
    private String applyDeliveryInformationCode;

}