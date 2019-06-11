package com.aiqin.bms.scmp.api.supplier.domain.response.order;

import com.aiqin.bms.scmp.api.base.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * 订单列表展示返回vo
 * @author: zth
 * @date: 2018-12-25
 * @time: 13:48
 */
@ApiModel("订单列表展示返回vo")
@Data
public class QuerySupplyOrderListRespVO {

    @ApiModelProperty("订单信息主表id")
    private Long id;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送")
    private String orderType;

    public void setOrderType(Byte orderType) {
        String type = "";
        if(orderType.intValue()== OrderType.DISTRIBUTION.getNum()){
            type = OrderType.DISTRIBUTION.getInfo();
        }else if(orderType.intValue()==OrderType.DIRECT_DELIVERY.getNum()){
            type =  OrderType.DIRECT_DELIVERY.getInfo();
        }else if(orderType.intValue()==OrderType.FIRST_ORDER.getNum()){
            type =  OrderType.DIRECT_DELIVERY.getInfo();
        }else if(orderType.intValue()==OrderType.FIRST_GIFT.getNum()){
            type =  OrderType.FIRST_GIFT.getInfo();
        }
        this.orderType = type;
    }
    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("订单状态(状态有点多，后面补)")
    private String orderStatus;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("商品总金额")
    private Long productTotalAmount;

    @ApiModelProperty("订单金额")
    private Long orderAmount;
}
