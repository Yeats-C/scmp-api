package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname: QueryOutboundReqVo
 * 描述:出库单列表展示和模糊搜索请求实体
 * @Author: Kt.w
 * @Date: 2019/3/4
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("出库单列表展示和模糊搜索请求实体")
public class QueryOutboundReqVo  extends PageReq {

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("创建时间起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建结束时间")
    private Date createEndTime;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("出库类型编码")
    private Byte outboundTypeCode;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("供货单位名称")
    private String supplierName;

    @ApiModelProperty("出库状态编码")
    private Byte outboundStatusCode;
}
