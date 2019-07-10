package com.aiqin.bms.scmp.api.purchase.domain.response.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryPurchaseOrderArrivalSubscribeRespVo
 * @date 2019/6/28 19:13
 * @description TODO
 */
@ApiModel("采购到货预约查询查询Vo")
@Data
public class QueryPurchaseOrderArrivalSubscribeRespVo {

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("采购单号")
    private String purchaseOrderCode;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("仓库编码")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("预约到货状态")
    private Byte arrivalSubscribeStatus;

    @ApiModelProperty("预约到货状态名称")
    private String arrivalSubscribeStatusName;

    @ApiModelProperty("到货预约时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalSubscribeTime;

    @ApiModelProperty("车牌号")
    private String licensePlate;

    @ApiModelProperty("司机姓名")
    private String driverName;

    @ApiModelProperty("手机号")
    private String phoneMobile;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("预计到货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectArrivalTime;

    @ApiModelProperty("有效期")
    private Integer validTime;

    @ApiModelProperty("单品数量")
    private Integer num;

    @ApiModelProperty("含税金额")
    private Long taxAmount;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
