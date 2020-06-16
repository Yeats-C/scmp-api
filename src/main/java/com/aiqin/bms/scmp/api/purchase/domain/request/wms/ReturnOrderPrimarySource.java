package com.aiqin.bms.scmp.api.purchase.domain.request.wms;

import com.aiqin.bms.scmp.api.product.domain.response.wms.BatchInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author:zfy
 * @Date:2020/3/6
 * @Content:
 */
@Data
@ApiModel("退货入库单推送主表")
public class ReturnOrderPrimarySource{

    @ApiModelProperty(value="入库单号")
    @JsonProperty("inbound_order_code")
    private String inboundOrderCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="退货单创建时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="入库单创建时间")
    @JsonProperty("create_date")
    private String createDate;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id_sh")
    private String createByIdSH;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name_sh")
    private String createByNameSH;

    @ApiModelProperty(value="物流公司编码")
    @JsonProperty("transport_company_code")
    private String transportCompanyCode;

    @ApiModelProperty(value="单号")
    @JsonProperty("transport_number")
    private String transportNumber;

    @ApiModelProperty(value="收货人手机号")
    @JsonProperty("consignee_phone")
    private String consigneePhone;

    @ApiModelProperty(value="发货人")
    @JsonProperty("dd2")
    private String dd2;

    @ApiModelProperty(value="备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty(value="直营")
    @JsonProperty("order_type")
    private String orderType="直营";

    @ApiModelProperty(value="退货单号")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty(value="销售订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="客户编码")
    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty(value="客户名称")
    @JsonProperty("customer_name")
    private String customerName;

    @ApiModelProperty(value="退货入库单子表")
    @JsonProperty("return_order_childern_source")
    private List<ReturnOrderChildernSource> childrenSourceList;

    @ApiModelProperty(value="批次信息明细")
    @JsonProperty("batch_info")
    private List<BatchInfo> batchInfo;

}
