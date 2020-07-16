package com.aiqin.bms.scmp.api.product.domain.request.movement;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel("wms移库推送主表")
@Data
public class MovementWmsOutReq {

    @ApiModelProperty("移库单编号")
    @JsonProperty("movement_code")
    private String movementCode;

    @ApiModelProperty("入库单编号")
    @JsonProperty("inbound_oder_code")
    private String inboundOderCode;

    @ApiModelProperty("出库单编号")
    @JsonProperty("outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty("数量")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("实际出数量")
    @JsonProperty("actual_out_total_count")
    private Long actualOutTotalCount;

    @ApiModelProperty("实际入数量")
    @JsonProperty("actual_in_total_count")
    private Long actualInTotalCount;

    @ApiModelProperty(value="(必填)标识 0出库单 1 入库单 2出入库单一起")
    @JsonProperty("flag")
    private Integer flag;

    @ApiModelProperty("修改人")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty("修改人")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty("商品详情")
    @JsonProperty("detail_list")
    private List<MovementProductWmsReq> detailList;

    @ApiModelProperty("商品批次详情")
    @JsonProperty("batch_list")
    private List<MovementBatchWmsReq> batchList;

}
