package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: scmp-api
 * @author: zhao shuai
 * @create: 2019-12-27
 **/
@Data
public class OutboundCallBackRequest {

    @ApiModelProperty("订单号")
    @NotEmpty(message = "订单号")
    @JsonProperty("oder_code")
    private String oderCode;

    @ApiModelProperty("订单id")
    @JsonProperty("order_id")
    private String orderId;

    @ApiModelProperty("订单类型 1配送、2直送、3货架直送、4采购直送")
    @NotEmpty(message = "订单类型")
    @JsonProperty("order_type_code")
    private Integer orderTypeCode;

    @ApiModelProperty("发货时间")
    @NotEmpty(message = "发货时间不能为空")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("delivery_time")
    private Date deliveryTime;

    @ApiModelProperty("实际销售数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty("发货人")
    @JsonProperty("delivery_person")
    private String deliveryPerson;

    @ApiModelProperty("签收时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("receive_time")
    private Date receiveTime;

    @ApiModelProperty(value = "操作人id")
    @JsonProperty("person_id")
    private String personId;

    @ApiModelProperty(value = "操作人姓名")
    @JsonProperty("person_name")
    private String personName;

    @ApiModelProperty(value = "订单状态")
    @JsonProperty("order_status")
    private Integer orderStatus;

    @ApiModelProperty(value = "运费")
    @JsonProperty("deliver_amount")
    private BigDecimal deliverAmount;

    @ApiModelProperty("商品详情列表")
    @JsonProperty("detail_list")
    private List<OutboundCallBackDetailRequest> detailList;

    @ApiModelProperty("商品批次列表")
    @JsonProperty("batch_list")
    private List<OutboundCallBackBatchRequest> batchList;

    @ApiModelProperty(value = "批次管理 0：自动批次管理 1：全部制定批次模式 2：部分指定批次模式")
    @JsonProperty("batch_manage")
    private Integer batchManage;

    @ApiModelProperty("包装数")
    @JsonProperty("packing_num")
    private Integer packingNum;

    @ApiModelProperty("总重量")
    @JsonProperty("total_weight")
    private BigDecimal totalWeight;

    @ApiModelProperty("第三方wms标记(1:京东)")
    @JsonProperty("flag")
    private Integer flag;

    @ApiModelProperty("物流公司编码")
    @JsonProperty("transport_company_code")
    private String transportCompanyCode;

    @ApiModelProperty("物流公司名称")
    @JsonProperty("transport_company_name")
    private String transportCompanyName;

    @ApiModelProperty("物流公司单号")
    @JsonProperty("transport_code")
    private String transportCode;

    @ApiModelProperty(value="是否发运 0是 1否")
    @JsonProperty("is_shipment")
    private Integer isShipment;

    @ApiModelProperty(value="业务类型 1优选、2天猫、3小红马")
    @JsonProperty("business_type")
    private Integer businessType;

}
