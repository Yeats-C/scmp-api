package com.aiqin.bms.scmp.api.supplier.domain.response.newproduct;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("商品查询返回实体")
@Data
public class NewProductResponseVO{
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("sku数量")
    private Integer skuNumber;
    
    @ApiModelProperty("条形码")
    private String barCode;

}
