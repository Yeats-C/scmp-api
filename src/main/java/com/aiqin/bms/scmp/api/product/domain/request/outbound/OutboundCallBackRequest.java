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

    @ApiModelProperty("订单号")
    @NotEmpty(message = "订单号")
    @JsonProperty("oder_code")
    private String oderCode;

    @ApiModelProperty("发货时间")
    @NotEmpty(message = "发货时间不能为空")
    @JsonProperty("delivery_time")
    private Date deliveryTime;

    @ApiModelProperty("实际销售数量")
    @JsonProperty("actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty("发货人")
    @JsonProperty("delivery_person")
    private String deliveryPerson;

    @ApiModelProperty("签收时间（数据库两个发运时间）")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("receive_time")
    private Date receiveTime;

    @ApiModelProperty(value = "操作人id")
    @JsonProperty("person_id")
    private String personId;

    @ApiModelProperty(value = "操作人姓名")
    @JsonProperty("person_name")
    private String personName;

    @ApiModelProperty("商品详情列表")
    @JsonProperty("detail_id")
    private List<OutboundCallBackDetailRequest> detailList;

    @ApiModelProperty("商品批次列表")
    @JsonProperty("batch_list")
    private List<OutboundCallBackBatchRequest> batchList;

}