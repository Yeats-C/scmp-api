package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-06
 * @time: 10:46
 */
@ApiModel("选sku信息")
@Data
public class QueryProductSaleAreaForSkuReqVO extends PageReq {
    @ApiModelProperty("供应商编码")
    @NotNull(message = "供应商编码不能为空")
    private String supplierCode;

    @ApiModelProperty("采购组编码")
    @NotNull(message = "采购组编码不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku编码")
    private String skuName;

    @ApiModelProperty("品类")
    private String productCategoryName;

    @ApiModelProperty("品牌")
    private String productBrandName;

    @ApiModelProperty("商品属性")
    private String productPropertyCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("sku集合")
    private String skuCodes;
}
