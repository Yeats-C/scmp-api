package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("选择商品")
public class SkuDataListReqVo extends PageReq {
    @ApiModelProperty("采购组code")
    private String procurementSectionCode;
    @ApiModelProperty("sku编号")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("商品属性")
    private String productProperty;
    @ApiModelProperty("品类")
    private String productCategory;
    @ApiModelProperty("品牌")
    private String productBrand;
    @ApiModelProperty("价格类型")
    private String priceTypeCode;
}
