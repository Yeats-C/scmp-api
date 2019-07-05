package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-05
 * @time: 20:51
 */
@Data
public class SkuBatchReqVO {
    @ApiModelProperty("仓库编码")
    private String transportCenterCode;
    @ApiModelProperty("库房编码")
    private String warehouseCode;
    @ApiModelProperty("sku集合")
    private List<String> skuCodes;

}
