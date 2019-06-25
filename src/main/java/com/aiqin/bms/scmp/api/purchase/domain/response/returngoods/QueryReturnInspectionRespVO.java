package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import com.aiqin.bms.scmp.api.base.ReturnOrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-20
 * @time: 10:13
 */
@Data
@ApiModel("退货验货返回vo")
public class QueryReturnInspectionRespVO {

    @ApiModelProperty("退货订单编码")
    private String returnOrderCode;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("退货单状态")
    private String orderStatus;

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = ReturnOrderStatus.getAllStatus().get(orderStatus).getBackgroundOrderStatus();
    }

    @ApiModelProperty("退货类型：客户退货、缺货退货、售后退货")
    private String returnOrderType;

    @ApiModelProperty("订单类型")
    private String orderType;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("商品总金额")
    private Long productTotalAmount;

    @ApiModelProperty("退货金额")
    private Long returnOrderAmount;

}
