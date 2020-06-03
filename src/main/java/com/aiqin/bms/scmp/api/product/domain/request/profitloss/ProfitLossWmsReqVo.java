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
    private String orderCode;

    @ApiModelProperty("仓库编号")
    private String transportCenterCode;

    @ApiModelProperty("仓库编号")
    private String transportCenterName;

    @ApiModelProperty("库房编号")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("单据类型编号")
    private Integer orderTypeCode;

    @ApiModelProperty("单据类型名称(损溢类别:指定损溢--0、盘点损溢--1)")
    private String orderTypeName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;

    @ApiModelProperty("创建人")
    private String createByName;

    @ApiModelProperty("修改人")
    private String updateByName;

    @ApiModelProperty("创建人")
    private String createById;

    @ApiModelProperty("修改人")
    private String updateById;

    @ApiModelProperty("损溢wms回传商品表")
    private List<ProfitLossProductWmsReqVo> detailList;

    @ApiModelProperty("损溢wms回传商品批次表")
    private List<ProfitLossBatchWmsReqVo> batchList;


}
