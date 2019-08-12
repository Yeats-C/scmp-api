package com.aiqin.bms.scmp.api.product.domain.request.scrap;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname: QueryScrapReqVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("列表搜索分页查询模糊搜索")
public class QueryScrapReqVo extends PageReq {
    @ApiModelProperty("创建时间起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建结束时间")
    private Date createEndTime;

    @ApiModelProperty("报废单编码")
    private String scrapCode;

    @ApiModelProperty("状态编码")
    private Byte scrapStatusCode;

    @ApiModelProperty("所属仓库编码")
    private String logisticsCenterCode;

    @ApiModelProperty("所属仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("所属库房编码")
    private String warehouseCode;

    @ApiModelProperty("所属库房名称")
    private String warehouseName;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "调拨类型",hidden = true)
    private Byte allocationType;
}
