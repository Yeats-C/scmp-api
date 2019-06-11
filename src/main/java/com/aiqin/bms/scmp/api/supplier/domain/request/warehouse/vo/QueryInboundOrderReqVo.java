package com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:入库列表请求实体
 *
 * @Author: Gary Diao
 * @Date: 2019/1/4
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("入库列表请求实体")
public class QueryInboundOrderReqVo {

    @ApiModelProperty("物流中心")
    private String transportCenterCode;

    @ApiModelProperty("供货单位")
    private String supplyCompanyCode;

    @ApiModelProperty("库房")
    private String warehouseCode;

    @ApiModelProperty("入库单号")
    private String inboundOrderCode;

    @ApiModelProperty("入库类型")
    private String inboundType;

    @ApiModelProperty("创建时间开始")
    private Date createTimeFrom;

    @ApiModelProperty("状态")
    private Byte status;

    @ApiModelProperty("创建时间结束")
    private Date createTimeTo;

}
