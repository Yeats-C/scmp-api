package com.aiqin.bms.scmp.api.product.domain.request.profitloss;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("损溢wms回传主表")
@Data
public class ProfitLossWmsReqVo {

    @ApiModelProperty("订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("仓库编号")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编号")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("wms回传损溢单据类型（盘点损溢0 指定损溢1）")
    @JsonProperty("type")
    private Integer type;

    @ApiModelProperty("单据类型编号")
    @JsonProperty("order_type_code")
    private Integer orderTypeCode;

    @ApiModelProperty("单据类型名称(损溢类别:指定损溢--0、盘点损溢--1)")
    @JsonProperty("order_type_name")
    private String orderTypeName;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty("状态")
    @JsonProperty("status")
    private String status;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("create_time")
    private Date createTime ;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("update_time")
    private Date updateTime ;

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

    @ApiModelProperty("损溢wms回传商品表")
    @JsonProperty("detailList")
    private List<ProfitLossProductWmsReqVo> detailList;

    @ApiModelProperty("损溢wms回传商品批次表")
    @JsonProperty("batchList")
    private List<ProfitLossBatchWmsReqVo> batchList;


}
