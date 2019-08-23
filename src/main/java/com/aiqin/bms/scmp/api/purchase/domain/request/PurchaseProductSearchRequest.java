package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-08-22
 **/
@Data
public class PurchaseProductSearchRequest {

    @ApiModelProperty(value="sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="商品类型")
    @JsonProperty("product_type")
    private Integer productType;
}
