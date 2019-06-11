package com.aiqin.bms.scmp.api.product.domain.dto.salearea;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleArea;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleAreaChannel;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleAreaMain;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-04
 * @time: 19:01
 */
@Data
public class ApplyProductSkuSaleAreaMainDTO extends ApplyProductSkuSaleAreaMain {
    /**
     * sku信息
     */
    private List<ApplyProductSkuSaleArea> skuList;
    /**
     * 区域信息
     */
    private List<ApplyProductSkuSaleArea> areaList;
    /**
     * 渠道信息
     */
    private List<ApplyProductSkuSaleAreaChannel> channelList;
}
