package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RejectStockRequest {

    @ApiModelProperty(value = "退供单号")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value = "")
    @JsonProperty("out_stock_time")
    private Date outStockTime;

    @ApiModelProperty(value = "回传商品信息")
    @JsonProperty("detail_list")
    private List<RejectDetailStockRequest> detailList;

    @ApiModelProperty(value = "回传批次")
    @JsonProperty("batch_list")
    private List<RejectRecordBatch> batchList;

}
