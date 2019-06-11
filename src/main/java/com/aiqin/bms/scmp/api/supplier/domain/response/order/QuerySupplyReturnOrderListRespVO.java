package com.aiqin.bms.scmp.api.supplier.domain.response.order;

import com.aiqin.bms.scmp.api.base.ReturnOrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * 退货订单列表返回vo
 * @author: zth
 * @date: 2019-01-02
 * @time: 16:17
 */
@Data
@ApiModel("退货订单列表返回vo")
public class QuerySupplyReturnOrderListRespVO {
    @ApiModelProperty("退货订单信息id")
    private Long id;

    @ApiModelProperty("退货订单编码(退货单号)")
    private String returnOrderCode;

    @ApiModelProperty("类型")
    private String orderType;

    public void setOrderType(Byte orderType) {
        this.orderType = ReturnOrderType.getAllStatus().get(orderType.intValue()).getInfo();
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
    private Integer orderStatus;

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
