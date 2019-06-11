package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description:
 * 查询重复的vo
 * @author: NullPointException
 * @date: 2019-05-22
 * @time: 10:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryChangePriceRepeatVO {

    @ApiModelProperty("变价类型编码")
    private String changePriceType;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("sku编码")
    private List<String> skuCode;

    @ApiModelProperty("供应商编码")
    private List<String> supplierCode;

    @ApiModelProperty("仓库批次号编码")
    private List<String> warehouseBatchNumber;

    @ApiModelProperty("仓库编码")
    private List<String> transportCenterCode;

    @ApiModelProperty("库房编码")
    private List<String> warehouseCode;

    @ApiModelProperty("区域或门店编码")
    private List<String> code;

    @ApiModelProperty("价格项目编码")
    private List<String> priceItemCode;

}
