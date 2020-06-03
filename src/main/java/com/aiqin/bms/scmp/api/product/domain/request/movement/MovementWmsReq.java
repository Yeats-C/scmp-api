package com.aiqin.bms.scmp.api.product.domain.request.movement;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("wms移库推送主表")
@Data
public class MovementWmsReq {

    @ApiModelProperty("移库单编号")
    @JsonProperty("movement_code")
    private String movementCode;

    @ApiModelProperty("调出仓库(物流中心)编号")
    @JsonProperty("call_out_logistics_center_code")
    private String callOutLogisticsCenterCode;

    @ApiModelProperty("调出仓库(物流中心)名称")
    @JsonProperty("call_out_logistics_center_name")
    private String callOutLogisticsCenterName;

    @ApiModelProperty("调出库房编码")
    @JsonProperty("call_out_warehouse_code")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    @JsonProperty("call_out_warehouse_name")
    private String callOutWarehouseName;

    @ApiModelProperty("创建人")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty("修改人")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty("创建人")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty("修改人")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty("调拨确认时间")
    @JsonProperty("receipt_time")
    private Date receiptTime;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime ;

    @ApiModelProperty("调入仓库(物流中心)编码")
    @JsonProperty("call_in_logistics_center_code")
    private String callInLogisticsCenterCode;

    @ApiModelProperty("调入仓库(物流中心)名称")
    @JsonProperty("call_in_logistics_center_name")
    private String callInLogisticsCenterName;

    @ApiModelProperty("调入库房编码")
    @JsonProperty("call_in_warehouse_code")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    @JsonProperty("call_in_warehouse_name")
    private String callInWarehouseName;

    @ApiModelProperty("负责人")
    @JsonProperty("principal")
    private String principal;

    @ApiModelProperty("数量")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("状态编码")
    @JsonProperty("allocation_status_code")
    private Byte allocationStatusCode;

    @ApiModelProperty("状态名称")
    @JsonProperty("allocation_status_name")
    private String allocationStatusName;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty("承担单位编码")
    @JsonProperty("undertaking_unit_code")
    private String undertakingUnitCode;

    @ApiModelProperty("承担单位名称")
    @JsonProperty("undertaking_unit_name")
    private String undertakingUnitName;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty("商品详情")
    @JsonProperty("detail_list")
    private List<MovementProductWmsReq> detailList;

    @ApiModelProperty("商品批次详情")
    @JsonProperty("batch_list")
    private List<MovementBatchWmsReq> batchList;
}
