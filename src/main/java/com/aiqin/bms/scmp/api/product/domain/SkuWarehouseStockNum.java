package com.aiqin.bms.scmp.api.product.domain;

import com.aiqin.bms.scmp.api.product.domain.response.sku.store.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.StoreSkuItemRespVO;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/16 0016 14:04
 */
@Data
public class SkuWarehouseStockNum {
    private Long inventoryNum;
    private String skuCode;
    private String warehouseCode;
    private String warehouseName;
    private String companyCode;

    private List<StoreSkuItemRespVO> storeSkuItemRespVOList;
    private List<LogisticsCenterApiResVo> logisticsCenterApiResVoList;
}
