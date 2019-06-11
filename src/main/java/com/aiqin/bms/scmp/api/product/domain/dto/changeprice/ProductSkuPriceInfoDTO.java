package com.aiqin.bms.scmp.api.product.domain.dto.changeprice;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceAreaInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-27
 * @time: 14:52
 */
@Data
public class ProductSkuPriceInfoDTO extends ProductSkuPriceInfo {

    @ApiModelProperty("区域信息")
    private List<ProductSkuPriceAreaInfo> areaInfos;
}
