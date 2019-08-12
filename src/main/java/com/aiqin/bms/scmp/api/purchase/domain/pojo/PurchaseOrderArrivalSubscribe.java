package com.aiqin.bms.scmp.api.purchase.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("采购到货预约")
@Data
public class PurchaseOrderArrivalSubscribe extends CommonBean {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("采购单号")
    private String purchaseOrderCode;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("到货预约时间")
    private Date arrivalSubscribeTime;

    @ApiModelProperty("车牌号")
    private String licensePlate;

    @ApiModelProperty("司机姓名")
    private String driverName;

    @ApiModelProperty("手机号")
    private String phoneMobile;

    @ApiModelProperty("'到货预约状态(0:未预约 1:已预约 2:已预约未确认")
    private Byte arrivalSubscribeStatus;

    @ApiModelProperty("备注")
    private String remark;

}