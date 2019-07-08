package com.aiqin.bms.scmp.api.product.domain.response.price;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuPriceRespVo
 * @date 2019/7/5 19:58
 * @description TODO
 */
@ApiModel("临时表价格详情返回Vo")
@Data
public class ProductSkuPriceRespVo {
    @ApiModelProperty(value = "sku编码",hidden = true)
    private String skuCode;

    @ApiModelProperty(value = "sku名称",hidden = true)
    private String skuName;

    @ApiModelProperty("价格项目编码")
    private String priceItemCode;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;

    @ApiModelProperty("价格类型")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格属性编码")
    private String priceAttributeCode;

    @ApiModelProperty("价格数据名称")
    private String priceAttributeName;

    @ApiModelProperty("含税金额")
    private Long priceTax;

    @ApiModelProperty("开始生效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeStart;
}
