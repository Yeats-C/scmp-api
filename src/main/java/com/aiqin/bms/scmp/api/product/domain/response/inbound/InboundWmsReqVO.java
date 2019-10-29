package com.aiqin.bms.scmp.api.product.domain.response.inbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Classname: InboundWmsReqVO
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/7
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("WMS传入入库单实体")
public class InboundWmsReqVO {

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("入库id")
    private Long id;

    @ApiModelProperty("供货单位编码")
    private String supplierCode;

    @ApiModelProperty("供货单位名称")
    private String supplierName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("创建人")
    private String createById;

    @ApiModelProperty("创建人名称")
    private String createByName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("预计到货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date preArrivalTime;

    @ApiModelProperty("预计主单位数量")
    private Long preMainUnitNum;

    @ApiModelProperty(value = "明细信息 必填")
    private List<InboundProductWmsReqVO>  list;

}
