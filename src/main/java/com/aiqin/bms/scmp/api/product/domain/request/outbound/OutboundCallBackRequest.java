package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: scmp-api
 * @author: zhao shuai
 * @create: 2019-12-27
 **/
@Data
public class OutboundCallBackRequest {

    @ApiModelProperty("来源单号")
    @NotEmpty(message = "来源单号不能为空")
    @JsonProperty("source_oder_code")
    private String sourceOderCode;

    @ApiModelProperty("出库时间")
    @NotEmpty(message = "出库时间不能为空")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("outbound_time")
    private Date outboundTime;

    @ApiModelProperty("实际销售数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty("发货人")
    @JsonProperty("delivery_person")
    private String deliveryPerson;

    @ApiModelProperty("回调详情列表")
    @JsonProperty("detail_id")
    private List<OutboundCallBackDetailRequest> detailList;

    @ApiModelProperty("回调sku列表")
    @JsonProperty("batch_list")
    private List<OutboundCallBackBatchRequest> batchList;

}
