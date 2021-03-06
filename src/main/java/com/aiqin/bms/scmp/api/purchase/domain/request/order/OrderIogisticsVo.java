package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderIogisticsVo implements Serializable {
    //订单
    @ApiModelProperty(value = "发货时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("delivery_time")
    private Date deliveryTime;

    @ApiModelProperty(value = "订单号")
    @JsonProperty("order_store_code")
    @NotEmpty(message = "订单号不能为空")
    private String orderStoreCode;

    @ApiModelProperty("订单类型 1配送、2直送、3货架直送、4采购直送")
    @NotEmpty(message = "订单类型")
    @JsonProperty("order_type_code")
    private String orderTypeCode;

    @ApiModelProperty(value = "实际销售数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty(value = "发货人id")
    @JsonProperty("delivery_person")
    private String deliveryPerson;

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

    //订单明细
    @ApiModelProperty(value = "订单商品明细行")
    @JsonProperty("orders_store_detail")
    private List<OrderStoreDetail> orderStoreDetail;

    //订单批次明细（仓卡）
    @ApiModelProperty(value = "订单批次明细")
    @JsonProperty("orders_batch_store_detail")
    private List<OrderBatchStoreDetail> orderBatchStoreDetail;
}
