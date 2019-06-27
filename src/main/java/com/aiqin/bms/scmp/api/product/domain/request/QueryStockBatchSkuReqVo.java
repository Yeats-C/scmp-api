package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *
 * @className QueryStockBatchSkuReqVo
 * @date 2019/6/27 9:24
 * @description 查询批次库存商品sku请求VO
 *
 */
@Data
@ApiModel("查询批次库存商品sku请求VO")
public class QueryStockBatchSkuReqVo extends PageReq implements Serializable {

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("物流中心")
    @NotEmpty(message = "物流中心不能为空")
    private String transportCenterCode;

    @ApiModelProperty("库房")
    @NotEmpty(message = "库房不能为空")
    private String warehouseCode;

    @ApiModelProperty("采购组")
    @NotEmpty(message = "采购组不能为空")
    private String procurementSectionCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("sku品类名称")
    private String productCategoryName;

    @ApiModelProperty("sku品牌名称")
    private String productBrandName;

    @ApiModelProperty("商品属性名称")
    private String productPropertyName;

}
