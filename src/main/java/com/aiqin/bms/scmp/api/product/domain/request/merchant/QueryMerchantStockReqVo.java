package com.aiqin.bms.scmp.api.product.domain.request.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryMerchantStockReqVo
 * @date 2019/1/14 17:04
 * @description 门店订货sku库存查询VO
 */
@ApiModel("门店订货sku库存查询VO")
@Data
public class QueryMerchantStockReqVo implements Serializable {

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("SKU集合")
    private List<String> skuList;
}
