package com.aiqin.bms.scmp.api.supplier.domain.response.variableprice;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("变价管理")
public class VariablePriceManagementResVO  {
    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("变价名称")
    private String variablePriceName;


    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeEffectTime;

    @ApiModelProperty("失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date failureTime;


    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;
    @ApiModelProperty("采购组")
    private String procurementSectionCode;
    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("(0:待提交,1:等待审核，2：审核通过3:审核不通过)")
    private Byte status;

    @ApiModelProperty("变价code")
    private String variablePriceCode;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("((0:不撤销,1:撤销))")
    private Byte priceRevoke;

}
