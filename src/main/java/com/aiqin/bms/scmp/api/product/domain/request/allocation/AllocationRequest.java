package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AllocationRequest {

    @ApiModelProperty(value = "调拨单号")
    @JsonProperty("allocation_code")
    private String allocationCode;

    @ApiModelProperty(value = "")
    @JsonProperty("out_stock_time")
    private Date outStockTime;

    @ApiModelProperty(value = "调拨回传商品信息")
    @JsonProperty("detail_list")
    private List<AllocationDetailRequest> detailList;

    @ApiModelProperty(value = "调拨回传商品批次信息")
    @JsonProperty("batch_list")
    private List<AllocationBatchRequest> batchList;
}
