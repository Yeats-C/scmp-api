package com.aiqin.bms.scmp.api.supplier.domain.response.order;

import com.aiqin.bms.scmp.api.base.ReturnOrderStatus;
import com.aiqin.bms.scmp.api.base.ReturnOrderType;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * 退货单详情vo
 * @author: zth
 * @date: 2019-01-02
 * @time: 16:43
 */
@ApiModel("退货单详情vo")
@Data
public class SupplyReturnOrderInfoRespVO {

    @ApiModelProperty("退货订单编码")
    private String returnOrderCode;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty("类型")
    private String orderType;

    public void setOrderType(Byte orderType) {
        this.orderType = ReturnOrderType.getAllStatus().get(orderType.intValue()).getInfo();
    }

    @ApiModelProperty("是否锁定(0否1是）")
    private Long beLock;

    @ApiModelProperty("是否删除(0否1是)")
    private Byte delFlag;

    @ApiModelProperty("支付状态")
    private Byte paymentStatus;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("配送方式")
    private String distributionMode;

    @ApiModelProperty("订单状态(状态有点多，后面补)")
    private String orderStatus;

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = ReturnOrderStatus.getAllStatus().get(orderStatus).getBackgroundOrderStatus();
    }

    @ApiModelProperty("收货人手机号")
    private String consigneePhone;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String districtName;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("支付方式")
    private String paymentType;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("商品总金额")
    private Long productTotalAmount;

    @ApiModelProperty("退货金额")
    private Long orderAmount;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("收货时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivingTime;

    @ApiModelProperty("发货时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("操作时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;

    @ApiModelProperty("重量")
    private Long weight;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("商品信息列表")
    private List<SupplyReturnOrderProductItemRespVO> orderItems;

    @ApiModelProperty("日志信息")
    private List<LogData> logs;

    public String getAddress() {
        return this.provinceName + this.cityName + this.districtName;
    }
}
