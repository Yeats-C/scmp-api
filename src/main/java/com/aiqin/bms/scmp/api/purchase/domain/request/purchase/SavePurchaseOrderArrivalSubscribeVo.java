package com.aiqin.bms.scmp.api.purchase.domain.request.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className SavePurchaseOrderArrivalSubscribeVo
 * @date 2019/6/28 19:24

 */
@ApiModel("保存供应商到货预约Vo")
@Data
public class SavePurchaseOrderArrivalSubscribeVo {

    @ApiModelProperty("采购单号,必填字段")
    private String purchaseOrderCode;

    @ApiModelProperty("入库单号,必填字段")
    private String inboundOderCode;

    @ApiModelProperty("到货预约时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalSubscribeTime;

    @ApiModelProperty("车牌号")
    private String licensePlate;

    @ApiModelProperty("司机姓名")
    private String driverName;

    @ApiModelProperty("手机号")
    private String phoneMobile;

    @ApiModelProperty("'到货预约状态(0:未预约 1:已预约 2:已预约待确认 3:驳回")
    private Byte arrivalSubscribeStatus;

    @ApiModelProperty("备注,确认填写")
    private String remark;
}
