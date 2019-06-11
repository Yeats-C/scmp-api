package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-28
 * @time: 16:25
 */
@ApiModel("sku列表查询vo")
@Data
public class QuerySkuInfoReqVO extends PageReq {

    @ApiModelProperty("变价类型")
    @NotNull(message = "变价类型必传")
    private String changePriceType;

    @ApiModelProperty("采购组")
    @NotNull(message = "采购组编码必传")
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

    @ApiModelProperty("spu编码")
    private String spuName;

    @ApiModelProperty("spu编码")
    private String spuCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("sku编码编码集合")
    private List<String> skuCodes;

}
