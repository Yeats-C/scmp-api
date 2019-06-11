package com.aiqin.bms.scmp.api.product.domain.dto.salearea;

import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-05
 * @time: 11:48
 */
@Data
public class ProductSkuSaleAreaMainDTO extends ProductSkuSaleAreaMain {
    /**
     * sku信息
     */
    private List<ProductSkuSaleArea> skuList;
    /**
     * 区域信息
     */
    private List<ProductSkuSaleAreaInfo> areaList;
    /**
     * 渠道信息
     */
    private List<ProductSkuSaleAreaChannel> channelList;
}
