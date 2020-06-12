package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

//    @ApiModelProperty("订单状态")
//    @JsonProperty("order_status")
//    private Integer orderStatus;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("退货类型 1.客户取消 2.缺货取消 3.划单取消 4.售后退货")
    @JsonProperty("return_order_type")
    private Integer returnOrderType;

    @ApiModelProperty("订单类型 1.直送 2.配送 3.辅采")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty("订单状态 1.待审核 2.审核通过 3.退单同步中 4.等待退货验货 5.等待退货入库 " +
            " 11.退货完成 12.退款完成 97.退货异常终止 98.审核不通过 99.已取消")
    @JsonProperty("order_status")
    private List<Integer> orderStatus;

}
