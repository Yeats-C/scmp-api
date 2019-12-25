package com.aiqin.bms.scmp.api.product.domain.response.price;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-09
 * @time: 15:52
 */
@Data
@ApiModel("sku查看价格返回VO")
public class SkuPriceRespVO {

    @ApiModelProperty("价格项目")
    private String priceItemName;

    @ApiModelProperty("含税金额")
    private BigDecimal priceTax;

    @ApiModelProperty("生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date effectiveTimeStart;

    @ApiModelProperty("供应商")
    private String supplierName;
}
