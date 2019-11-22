package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.product.domain.excel.SkuManufactureImport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuManufacturerRespVo
 * @date 2019/11/20 14:04
 */
@Data
@ApiModel("sku商品制造商管理详情返回vo")
public class ProductSkuManufacturerDetailRespVo {
    @ApiModelProperty("sku信息")
    private ProductSkuRespVo productSkuInfo;

    @ApiModelProperty("生产厂家信息")
    private List<ProductSkuManufacturerRespVo> productSkuManufacturerRespVo;
}
