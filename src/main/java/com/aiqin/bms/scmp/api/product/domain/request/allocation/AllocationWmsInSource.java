package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.aiqin.bms.scmp.api.purchase.domain.request.order.BatchWmsInfo;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "调拨入库推送源数据")
@Data
public class AllocationWmsInSource implements Serializable {
    @ApiModelProperty(value = "入库单号")
    @JsonProperty("inbound_oder_code")
    private String inboundOderCode;

    @ApiModelProperty(value = "调拨单号")
    @JsonProperty("allocation_code")
    private String allocationCode;

    @ApiModelProperty(value = "调拨出库方库房编号")
    @JsonProperty("call_out_warehouse_code")
    private String callOutWarehouseCode;

    @ApiModelProperty(value = "调拨出库方库房名称")
    @JsonProperty("call_out_warehouse_name")
    private String callOutWarehouseName;

    @ApiModelProperty(value = "调拨入库方库房编号")
    @JsonProperty("call_in_warehouse_code")
    private String callInWarehouseCode;

    @ApiModelProperty(value = "调拨入库方库房名称")
    @JsonProperty("call_in_warehouse_name")
    private String callInWarehouseName;

    @ApiModelProperty(value="调拨单创建时间(yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JsonProperty("create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "调拨单创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "调拨单创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="调拨入库推送源数据明细")
    @JsonProperty("detail_list")
    private List<AllocationWmsProductSource> detailList;

    @ApiModelProperty(value="调拨入库推送源商品批次数据明细")
    @JsonProperty("batch_info")
    private List<BatchWmsInfo> batchInfo;
}
