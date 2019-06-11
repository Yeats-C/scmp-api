package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author knight.xie
 * @className QueryStockSkuReqVo
 * @date 2019/1/4 16:12
 * @description 查询库存商品sku请求VO
 * @version 1.0
 */
@Data
@ApiModel("查询库存商品sku请求VO")
public class QueryStockSkuReqVo extends PageReq implements Serializable {

    @ApiModelProperty("公司编码")
    @NotEmpty(message = "公司编码不能为空")
    private String companyCode;

    @ApiModelProperty("供货单位")
    private String supplyCode;

    @ApiModelProperty("物流中心")
    @NotEmpty(message = "物流中心不能为空")
    private String transportCenterCode;

    @ApiModelProperty("库房")
    @NotEmpty(message = "库房不能为空")
    private String warehouseCode;

    @ApiModelProperty("采购组")
    @NotEmpty(message = "采购组不能为空")
    private String purchaseGroupCode;

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

    @ApiModelProperty("sku编码集合")
    private List<String> skuList;

}
