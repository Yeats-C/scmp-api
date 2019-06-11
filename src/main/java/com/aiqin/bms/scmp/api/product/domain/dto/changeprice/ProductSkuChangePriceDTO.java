package com.aiqin.bms.scmp.api.product.domain.dto.changeprice;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceAreaInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-24
 * @time: 11:23
 */
@Data
public class ProductSkuChangePriceDTO extends ProductSkuChangePrice{

    @ApiModelProperty("sku信息")
    List<ProductSkuChangePriceInfo> infos;

    @ApiModelProperty("区域信息")
    List<ProductSkuChangePriceAreaInfo> areaInfos;

}
