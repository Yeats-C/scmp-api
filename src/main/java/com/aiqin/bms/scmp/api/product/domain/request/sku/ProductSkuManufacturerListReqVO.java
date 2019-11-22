package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @className ProductSkuManufacturerReqVO
 * @date 2019/11/20
 */
@ApiModel("sku商品制造商请求VO")
@Data
public class ProductSkuManufacturerListReqVO extends PageReq {

    @ApiModelProperty("保存的制造商信息列表")
    private List<ProductSkuManufacturer> manufacturerList;
}
