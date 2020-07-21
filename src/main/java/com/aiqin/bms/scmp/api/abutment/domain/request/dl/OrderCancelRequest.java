package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("DL- 单据取消")
public class OrderCancelRequest {

    @NotNull(message = "单号不能为空")
    @ApiModelProperty(value="单据号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="取消类型 1:销售-销售单 2:退货-入库单")
    @JsonProperty("cancel_type")
    private Integer cancelType;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="wms库房类型")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;

    @ApiModelProperty(value="取消人员编码")
    @JsonProperty("operator_id")
    private String operatorId;

    @ApiModelProperty(value="取消人员名称")
    @JsonProperty("operator_name")
    private String operatorName;

}
