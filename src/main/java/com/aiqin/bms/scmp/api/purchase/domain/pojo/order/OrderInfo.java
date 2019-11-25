package com.aiqin.bms.scmp.api.purchase.domain.pojo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel("订单主表")
@Data
public class OrderInfo {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送.辅采直送")
    private String orderType;

    @ApiModelProperty("类型编码")
    private Integer orderTypeCode;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("订单状态(状态有点多，后面补)")
    private Integer orderStatus;

    @ApiModelProperty("是否锁定(0否1是）")
    private Integer beLock = 0;

    @ApiModelProperty("锁定原因")
    private String lockReason;

    @ApiModelProperty("是否是异常订单(0否1是)")
    private Integer beException = 0;

    @ApiModelProperty("异常原因")
    private String exceptionReason;

    @ApiModelProperty("是否删除(0否1是)")
    private Integer beDelete = 0;

    @ApiModelProperty("支付状态")
    private Integer paymentStatus;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("配送方式")
    private String distributionMode;

    @ApiModelProperty("配送方式编码")
    private String distributionModeCode;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String consigneePhone;

    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String districtCode;

    @ApiModelProperty("区名称")
    private String districtName;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("支付方式")
    private String paymentType;

    @ApiModelProperty("支付方式编码")
    private String paymentTypeCode;

    @ApiModelProperty("运费")
    private Long deliverAmount;

    @ApiModelProperty("商品分销价总金额")
    private BigDecimal productTotalAmount;

    @ApiModelProperty("商品渠道价总金额")
    private BigDecimal productChannelTotalAmount;

    @ApiModelProperty("优惠额度")
    private BigDecimal discountAmount;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("支付日期")
    private Date paymentTime;

    @ApiModelProperty("发运时间")
    private Date transportTime;

    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @ApiModelProperty("收货时间")
    private Date receivingTime;

    @ApiModelProperty("不开、增普、增专")
    private String invoiceType;

    @ApiModelProperty("发票类型编码")
    private String invoiceTypeCode;

    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("活动优惠")
    private Long activityDiscount;

    @ApiModelProperty("重量")
    private Long weight;

    @ApiModelProperty("体积")
    private Long volume;

    @ApiModelProperty("是否父订单(0不是1是)")
    private Integer beMasterOrder;

    @ApiModelProperty("父订单号")
    private String masterOrderCode;

    @ApiModelProperty("订单来源")
    private String orderOriginal;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("减免比例")
    private Integer logisticsRemissionRatio;

    @ApiModelProperty("发运状态")
    private Integer transportStatus = 0;

    @ApiModelProperty("门店类型")
    private String storeType;

    @ApiModelProperty("门店类型编码")
    private String storeTypeCode;

    @ApiModelProperty("订单类别名称")
    private String orderCategory;

    @ApiModelProperty("订单类别编码")
    private String orderCategoryCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("创建人")
    private String createByName;

    @ApiModelProperty("创建人编码")
    private String createById;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateByName;

    @ApiModelProperty("修改人编码")
    private String updateById;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("实际渠道总价")
    private BigDecimal actualProductChannelTotalAmount;

    @ApiModelProperty("实际订单金额")
    private BigDecimal actualOrderAmount;

    @ApiModelProperty("实际发货数量")
    private Long actualProductNum;

    @ApiModelProperty("订单来源名称")
    private String orderOriginalName;

    @ApiModelProperty("实际重量")
    private Long actualWeight;

    @ApiModelProperty("实际体积")
    private Long actualVolume;

    @ApiModelProperty("实际分销总价")
    private BigDecimal actualProductTotalAmount;

    @ApiModelProperty("运输公司")
    private String transportCompany;

    @ApiModelProperty("运输公司编码")
    private String transportCompanyCode;

    @ApiModelProperty("运输单号")
    private String transportNumber;

    /**以下字段为了dl回调销售单生成出库单和库存变动需要*/

    @ApiModelProperty("预计商品数量")
    private Long preProductNum;

    @ApiModelProperty("订单详情")
    private List<OrderInfoItem> detailList;

    @ApiModelProperty("订单详情")
    private List<OrderInfoItemProductBatch> detailBatchList;
}