package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-05
 * @time: 17:20
 */
@Data
public class ProductSaleAreaSupplierInfo {

    @ApiModelProperty("直送供应商编码")
    private String directDeliverySupplierCode;

    @ApiModelProperty("直送供应商名称")
    private String directDeliverySupplierName;
}
