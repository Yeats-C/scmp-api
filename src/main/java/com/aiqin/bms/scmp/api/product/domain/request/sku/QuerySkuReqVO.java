package com.aiqin.bms.scmp.api.product.domain.request.sku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("不分页 SKU查询列表")
public class QuerySkuReqVO {
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品编码")
    private String productCode;
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("属性名称")
    private String productPropertyName;
    @ApiModelProperty("品类名称")
    private String productCategoryName;
    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;


}
