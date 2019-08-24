package com.aiqin.bms.scmp.api.product.domain.response.sku.config;

import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className DetailConfigSupplierRespVo
 * @date 2019/7/4 21:52
 */
@ApiModel("商品配置返回Vo")
@Data
public class DetailConfigSupplierRespVo {

    @ApiModelProperty("商品配置")
    List<SkuConfigsRepsVo> configs;

    @ApiModelProperty("供应商信息")
    List<ProductSkuSupplyUnitRespVo> suppliers;
}
