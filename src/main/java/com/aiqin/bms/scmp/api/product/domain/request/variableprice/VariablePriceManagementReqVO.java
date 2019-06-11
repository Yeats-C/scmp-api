package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("变价管理")
public class VariablePriceManagementReqVO extends PageReq {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("变价名称")
    private String variablePriceName;
    @ApiModelProperty("价格类型code")
    private String priceTypeCode;
    @ApiModelProperty("采购组")
    private String procurementSectionCode;

    @ApiModelProperty("创建起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStaTime;

    @ApiModelProperty("创建结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndTime;

    @ApiModelProperty("(0:待提交,1:等待审核，2：审核通过3:审核不通过)")
    private Byte status;

    @ApiModelProperty("变价code")
    private String variablePriceCode;

}
