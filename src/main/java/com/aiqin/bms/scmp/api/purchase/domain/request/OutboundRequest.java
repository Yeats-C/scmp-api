package com.aiqin.bms.scmp.api.purchase.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Data
public class OutboundRequest {

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("传入值 12 交易全部完成")
    private Integer orderStatus = 12;

    @ApiModelProperty("是否是异常订单(0否1是)")
    private Integer beException = 0;

    @ApiModelProperty("订单支付状态 1 未支付 2 已支付")
    private Integer paymentStatus = 2;

    @ApiModelProperty("是否锁定(0否1是）")
    private Integer beLock = 0;

    @ApiModelProperty("类型：直送、配送、辅采直送")
    private String orderType;

    @ApiModelProperty("订单类别名称")
    private String orderCategory;

    @ApiModelProperty("类型编码")
    private Integer orderTypeCode;

    @ApiModelProperty("支付方式")
    private String paymentType = "转账";

    @ApiModelProperty("是否父订单(0不是1是)")
    private Integer beMasterOrder = 1;

    @ApiModelProperty("订单来源:渠道信息 ")
    private String orderOriginal;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("预计商品数量")
    private Long preProductNum;

    @ApiModelProperty("支付日期")
    private Date paymentTime;

    @ApiModelProperty("发运时间")
    private Date transportTime;

    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @ApiModelProperty("收货时间(回单确认时间)")
    private Date receivingTime;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户编码")
    private String customerCode;

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

    @ApiModelProperty("商品渠道价总金额")
    private Long productChannelTotalAmount;

    @ApiModelProperty(value = "商品详情")
    private List<OutboundDetailRequest> detail;

    @ApiModelProperty(value = "回单确认时间")
    private String receiptTime;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "创建人名称")
    private String createByName;

    @ApiModelProperty(value = "创建人Id")
    private String createById;

    @ApiModelProperty(value = "备注")
    private String remark;

}
