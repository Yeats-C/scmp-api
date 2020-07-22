package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("DL- 库存变动推送")
public class StockChangeRequest {

    @NotNull(message = "单据不能为空")
    @ApiModelProperty(value="单据号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="单据类型 1.采购 2.退供 3.调拨出 7调拨入 4.移库出 8移库入 5.报损 9报溢 6.预定订单")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty(value="操作类型 1. 加库存  2.减库存")
    @JsonProperty("operation_type")
    private Integer operationType;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="供应商编码(采购、退供)")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="wms库房类型 1 存储 2 退货")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;

    @ApiModelProperty(value="操作人编码")
    @JsonProperty("operation_code")
    private String operationCode;

    @ApiModelProperty(value="操作人名称")
    @JsonProperty("operation_name")
    private String operationName;

    @ApiModelProperty("预计到货时间(采购、退供)")
    @JsonProperty("pre_arrival_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date preArrivalTime;

    @ApiModelProperty(value="总数(采购、退供)")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value="商品信息")
    @JsonProperty("product_list")
    private List<ProductRequest> productList;

}
