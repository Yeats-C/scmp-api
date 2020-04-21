package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *  wms回传订单实体
 *
 * @author: ch
 * @version: v1.0.0
 * @date 2020/04/20 9:49
 */
@Data
public class WmsOrderInfo {

    @ApiModelProperty(value = "销售单号")
    @JsonProperty("order_store_code")
    private String orderStoreCode;

    @ApiModelProperty(value = "拣货时间")
    @JsonProperty("picking_date")
    private Date pickingDate;

    @ApiModelProperty(value = "扫描时间")
    @JsonProperty("scanning_date")
    private Date scanningDate;

    @ApiModelProperty(value = "出库时间")
    @JsonProperty("outbound_date")
    private Date outboundDate;

    @ApiModelProperty(value = "出库操作人编码")
    @JsonProperty("outbound_operator_code")
    private String outboundOperatorCode;

    @ApiModelProperty(value = "出库操作人名称")
    @JsonProperty("outbound_operator")
    private String outboundOperator;

    @ApiModelProperty(value = "状态(1开始拣货 2扫描完成 3已全部发货)")
    @JsonProperty("order_status")
    private Integer orderStatus;

    @ApiModelProperty(value = "wms回传订单商品明细行")
    @JsonProperty("item_list")
    private List<WmsOrderItem> itemList;

    @ApiModelProperty(value = "wms回传订单商品批次明细表")
    @JsonProperty("item_batch_list")
    private List<WmsOrderBatchItem> itemBatchList;

}
