package com.aiqin.bms.scmp.api.purchase.domain.request.purchase;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryPurchaseOrderArrivalSubscribeVo
 * @date 2019/6/28 18:42
 * @description TODO
 */
@ApiModel("采购到货预约查询Vo")
@Data
public class QueryPurchaseOrderArrivalSubscribeVo extends PageReq {

    @ApiModelProperty("到货预约开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalSubscribeTimeStart;

    @ApiModelProperty("到货预约结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalSubscribeTimeEnd;

    @ApiModelProperty("仓库编号")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("'到货预约状态(0:未预约 1:已预约 2:未预约待确认")
    private Byte arrivalSubscribeStatus;

    @ApiModelProperty("采购单号")
    private String purchaseOrderCode;

    @ApiModelProperty("车牌号")
    private String licensePlate;




}
