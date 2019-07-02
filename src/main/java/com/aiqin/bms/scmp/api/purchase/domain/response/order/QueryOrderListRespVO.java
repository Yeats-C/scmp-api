package com.aiqin.bms.scmp.api.purchase.domain.response.order;

import com.aiqin.bms.scmp.api.base.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * 订单列表展示vo
 * @author: NullPointException
 * @date: 2019-06-14
 * @time: 13:37
 */
@ApiModel("订单列表展示vo")
@Data
public class QueryOrderListRespVO {

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送.辅采直送")
    private String orderType;

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

    @ApiModelProperty("商品总金额")
    private Long productTotalAmount;

    @ApiModelProperty("订单金额")
    private Long orderAmount;

    @ApiModelProperty("商品数量")
    private Long productNum;

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = OrderStatus.getAllStatus().get(orderStatus).getBackgroundOrderStatus();
    }
}
