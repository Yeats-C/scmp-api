package com.aiqin.bms.scmp.api.product.domain.response.inbound;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
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
@ApiModel("入库列表展示返回实体")
public class QueryInboundResVo extends PageReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("入库类型编码")
    private Byte inboundTypeCode;

    @ApiModelProperty("入库类型名称")
    private String inboundTypeName;

    @ApiModelProperty("来源单号")
    private String sourceOderCode;

    @ApiModelProperty("物流中心编码")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("供货单位编码")
    private String supplierCode;

    @ApiModelProperty("供货单位名称")
    private String supplierName;

    @ApiModelProperty("入库状态编码")
    private Byte inboundStatusCode;

    @ApiModelProperty("入库状态名称")
    private String inboundStatusName;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("预计入库数量")
    private Long preInboundNum;

    @ApiModelProperty("实际入库数量")
    private Long praInboundNum;

    @ApiModelProperty("预计入库含税总金额")
    private BigDecimal preTaxAmount;

    @ApiModelProperty("实际入库含税总金额")
    private BigDecimal praTaxAmount;
}
