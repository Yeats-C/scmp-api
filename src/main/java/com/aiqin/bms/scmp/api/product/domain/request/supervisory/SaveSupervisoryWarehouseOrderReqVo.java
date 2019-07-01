package com.aiqin.bms.scmp.api.product.domain.request.supervisory;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveSupervisoryWarehouseOrderReqVo
 * @date 2019/6/29 18:05
 * @description TODO
 */
@ApiModel("保存监管仓出入库单据")
@Data
public class SaveSupervisoryWarehouseOrderReqVo {

    @ApiModelProperty("订单类型(1:入库 2:出库)")
    private Byte orderType;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户订单号")
    private String customerOrderCode;

    @ApiModelProperty("订单日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    @ApiModelProperty("仓编码(物流中心编码)")
    private String transportCenterCode;

    @ApiModelProperty("仓名称(物流中心名称)")
    private String transportCenterName;

    @ApiModelProperty("业务编号")
    private String businessCode;

    @ApiModelProperty("业务名称")
    private String businessName;

    @ApiModelProperty("预约时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date subscribeTime;

    @ApiModelProperty("有效期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("开单人")
    private String openOrderName;

    @ApiModelProperty("商品列表")
    List<SupervisoryWarehouseOrderProductReqVo> products;
}
