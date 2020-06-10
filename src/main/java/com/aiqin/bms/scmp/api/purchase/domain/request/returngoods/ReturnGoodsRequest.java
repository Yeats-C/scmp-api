package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("退货单请求类")
public class ReturnGoodsRequest extends PagesRequest {

    @ApiModelProperty(value="创建开始时间")
    @JsonProperty("create_begin_time")
    private String createBeginTime;

    @ApiModelProperty(value="创建结束时间")
    @JsonProperty("create_finish_time")
    private String createFinishTime;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("退货单号")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty("订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("客户编码")
    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty("订单状态")
    @JsonProperty("order_status")
    private Integer orderStatus;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("退货类型 0.客户取消 1.缺货取消 2.划单取消 3.售后退货")
    @JsonProperty("return_order_type")
    private Integer returnOrderType;

}
