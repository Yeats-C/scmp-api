package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname: ReturnOutboundProduct
 * 描述:  出库单回传，查询sku返回实体，还有进项，销项税率
 * @Author: Kt.w
 * @Date: 2019/3/26
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("出库单回传，查询sku批次返回实体，还有进项，销项税率")
@Data
public class ReturnOutboundBatch {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("批次号")
    private String outboundBatchCode;

    @ApiModelProperty("预计数量")
    private Long preQty;

    @ApiModelProperty("实际数量")
    private Long praQty;

    @ApiModelProperty("库位号")
    private String storeHouseCode;

    @ApiModelProperty("生产日期")
    private Date manufactureTime;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("行号")
    private Long linenum;

}
