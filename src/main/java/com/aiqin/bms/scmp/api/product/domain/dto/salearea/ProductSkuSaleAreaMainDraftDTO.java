package com.aiqin.bms.scmp.api.product.domain.dto.salearea;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaChannelDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaMainDraft;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-04
 * @time: 14:19
 */
@Data
public class ProductSkuSaleAreaMainDraftDTO extends ProductSkuSaleAreaMainDraft {
    /**
     * 渠道信息
     */
    private List<ProductSkuSaleAreaChannelDraft> channelList;
    /**
     * sku信息
     */
    private List<ProductSkuSaleAreaDraft> skuList;
    /**
     * 区域信息
     */
    private List<ProductSkuSaleAreaInfoDraft> areaList;
}
