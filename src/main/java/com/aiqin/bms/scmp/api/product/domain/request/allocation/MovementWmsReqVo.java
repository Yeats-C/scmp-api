package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.aiqin.bms.scmp.api.purchase.domain.request.order.BatchWmsInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Author:ch
 * @Date:2020/5/6
 * @Content:
 */
@Data
@ApiModel(value = "移库推送wms主表")
public class MovementWmsReqVo implements Serializable {

    @ApiModelProperty(value="移库单号")
    @JsonProperty("transfer_order_code")
    private String transferOrderCode;

    @ApiModelProperty(value="出库库房编号")
    @JsonProperty("outbound_warehouse_code")
    private String outboundWarehouseCode;

    @ApiModelProperty(value="出库库房名称")
    @JsonProperty("outbound_warehouse_name")
    private String outboundWarehouseName;

    @ApiModelProperty(value="出库单号")
    @JsonProperty("outbound_order_code")
    private String outboundOrderCode;

    @ApiModelProperty(value="入库库房编号")
    @JsonProperty("inbound_warehouse_code")
    private String inboundWarehouseCode;

    @ApiModelProperty(value="入库库房名称")
    @JsonProperty("inbound_warehouse_name")
    private String inboundWarehouseName;

    @ApiModelProperty(value="入库单号")
    @JsonProperty("inbound_order_code")
    private String inboundOrderCode;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="标识 0出库单 1 入库单 2出入库单一起")
    @JsonProperty("flag")
    @NotEmpty(message = "标识必填")
    private String flag;

    /*********德邦新增***********/
    @ApiModelProperty(value="下单联系人电话-入库单必填")
    @JsonProperty("order_contact_phone")
    private String orderContactPhone;

    @ApiModelProperty(value="下单联系人")
    @JsonProperty("order_contacts")
    private String orderContacts;

    @ApiModelProperty(value="收货人-德邦出库单必填")
    @JsonProperty("receiver_name")
    private String receiverName;

    @ApiModelProperty(value="收货电话")
    @JsonProperty("receiver_mobil")
    private String receiverMobil;

    @ApiModelProperty(value="收货省-德邦出库必填")
    @JsonProperty("receiver_province")
    private String receiverProvince;

    @ApiModelProperty(value="收货市-德邦出库必填")
    @JsonProperty("receiver_city")
    private String receiverCity;


    @ApiModelProperty(value="收货区/县-德邦出库必填")
    @JsonProperty("receiver_county")
    private String receiverCounty;

    @ApiModelProperty(value="详细地址-德邦出库必填")
    @JsonProperty("address")
    private String address;
    /*********德邦新增***********/

    @ApiModelProperty(value="移库子表商品明细")
    @JsonProperty("details")
    private List<MovementWmsProductReqVo> detailList;

    @ApiModelProperty(value="移库子表商品批次明细")
    @JsonProperty("batchInfo_list")
    private List<BatchWmsInfo> batchInfoList;
}
