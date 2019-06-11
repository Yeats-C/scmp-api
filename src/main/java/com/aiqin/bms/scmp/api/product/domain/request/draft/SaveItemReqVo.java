package com.aiqin.bms.scmp.api.product.domain.request.draft;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveItemReqVo
 * @date 2019/5/17 15:16
 * @description TODO
 */

@ApiModel("新增SKU/SPU")
@Data
public class SaveItemReqVo {
    @ApiModelProperty("商品编码集合")
    private List<String> productCodes;

    @ApiModelProperty("sku编码集合")
    private List<String> skuCodes;
}
