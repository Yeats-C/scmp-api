package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:入库列表展示请求实体
 *
 * @author Kt.w
 * @date 2019/1/4
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("入库列表展示请求实体")
public class QueryInboundReqVo  extends PageReq {

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

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("入库类型编码")
    private Byte inboundTypeCode;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("供货单位名称")
    private String supplierName;

    @ApiModelProperty("入库状态编码")
    private Byte inboundStatusCode;
}
