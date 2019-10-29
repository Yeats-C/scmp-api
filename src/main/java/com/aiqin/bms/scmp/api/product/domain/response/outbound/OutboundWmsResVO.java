package com.aiqin.bms.scmp.api.product.domain.response.outbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Classname: OutboundWmsReqVO
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/7
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class OutboundWmsResVO {

    @ApiModelProperty("出库单编号")
    private String outboundOderCode;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("供货单位编码")
    private String supplierCode;

    @ApiModelProperty("创建人")
    private String createById;

    @ApiModelProperty("创建人名称")
    private String createByName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("预计主单位数量")
    private Long preMainUnitNum;

    @ApiModelProperty("实际主单位数量")
    private Long praMainUnitNum;

    @ApiModelProperty(value = "明细信息 必填")
    private List<OutboundProductWmsResVO> list;

}
