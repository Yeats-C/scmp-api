package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.aiqin.bms.scmp.api.purchase.domain.request.order.BatchWmsInfo;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "调拨出库推送源数据")
@Data
public class AllocationWmsOutSource implements Serializable {

    @ApiModelProperty(value = "出库单号")
    //@JsonProperty("outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty(value = "调拨单号")
    //@JsonProperty("allocation_code")
    private String allocationCode;

    @ApiModelProperty(value = "库房编号")
    //@JsonProperty("call_out_warehouse_code")
    private String callOutWarehouseCode;

    @ApiModelProperty(value = "库房名称")
    //@JsonProperty("call_out_warehouse_name")
    private String callOutWarehouseName;

    @ApiModelProperty(value="调拨单创建时间(yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    //@JsonProperty("create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @ApiModelProperty(value = "调拨单创建人编码")
    //@JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "调拨单创建人名称")
    //@JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "收货人姓名")
    //@JsonProperty("receiver_name")
    private String receiverName;

    @ApiModelProperty(value = "手机号")
    //@JsonProperty("receiver_mobil")
    private String receiverMobil;

    @ApiModelProperty(value = "收货省")
    //@JsonProperty("receiver_province")
    private String receiverProvince;

    @ApiModelProperty(value = "收货市")
    //@JsonProperty("receiver_city")
    private String receiverCity;

    @ApiModelProperty(value = "收货区县")
    //@JsonProperty("receiver_county")
    private String receiverCounty;

    @ApiModelProperty(value = "详细地址")
    //@JsonProperty("address")
    private String Address;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="调拨出库推送源数据明细")
 //   @JsonProperty("allocation_wms_product_sources")
    private List<AllocationWmsProductSource> detailList;

    @ApiModelProperty(value="调拨出库推送源商品批次数据明细")
    //@JsonProperty("allocation_wms_product_batch_sources")
    private List<BatchWmsInfo> batchInfo;
}
