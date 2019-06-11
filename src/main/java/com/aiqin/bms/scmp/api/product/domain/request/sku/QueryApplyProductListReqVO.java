package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/13 0013 10:27
 */
@Data
@ApiModel("商品申请管理请求参数")
public class QueryApplyProductListReqVO extends PageReq {
    @ApiModelProperty("创建时间开始 yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTimeStart;

    @ApiModelProperty("创建时间结束 yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTimeEnd;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("创建人")
    private String createBy;
}
