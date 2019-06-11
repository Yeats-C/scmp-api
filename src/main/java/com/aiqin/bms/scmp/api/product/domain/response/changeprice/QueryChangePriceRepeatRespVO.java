package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-22
 * @time: 10:39
 */
@Data
public class QueryChangePriceRepeatRespVO {
    @ApiModelProperty("变价类型编码")
    private String changePriceName;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("仓库批次号编码")
    private String warehouseBatchNumber;

    @ApiModelProperty("仓库Name")
    private String transportCenterName;

    @ApiModelProperty("库房Name")
    private String warehouseName;

    @ApiModelProperty("区域或门店Name")
    private String Name;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;
}
