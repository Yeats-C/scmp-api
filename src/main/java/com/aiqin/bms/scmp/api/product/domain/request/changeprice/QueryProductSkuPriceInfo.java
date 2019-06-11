package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-24
 * @time: 15:37
 */
@Data
@ApiModel("查询vo")
@AllArgsConstructor
@NoArgsConstructor
public class QueryProductSkuPriceInfo {
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * sku编码
     */
    private List<String> skuCode;
    /**
     * 价格项目集合
     */
    private List<String> priceItemCode;
    /**
     * 供应商集合
     */
    private List<String> supplierCode;
    /**
     * 仓库编码集合
     */
    private List<String> transportCenterCode;
    /**
     * 库房编码集合
     */
    private List<String> warehouseCode;
    /**
     * 批次号集合
     */
    private List<String> warehouseBatchNumber;
}
