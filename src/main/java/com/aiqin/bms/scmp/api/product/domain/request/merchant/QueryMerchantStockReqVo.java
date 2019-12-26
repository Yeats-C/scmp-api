package com.aiqin.bms.scmp.api.product.domain.request.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryMerchantStockReqVo
 * @date 2019/1/14 17:04
 */
@ApiModel("门店订货sku库存查询VO")
@Data
public class QueryMerchantStockReqVo implements Serializable {

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("SKU集合")
    @JsonProperty("sku_list")
    private List<String> skuList;

    @ApiModelProperty("省编码")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty("市编码")
    @JsonProperty("city_code")
    private String cityCode;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;
}
