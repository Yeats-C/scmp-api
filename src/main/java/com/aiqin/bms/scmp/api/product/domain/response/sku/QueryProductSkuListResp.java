package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 17:26
 */
@Data
@ApiModel("sku管理返回对象")
public class QueryProductSkuListResp {
    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("商品上下架")
    private String onSale;

    @JsonProperty("sku_price")
    @ApiModelProperty("动销价")
    private Long skuPrice;

    @ApiModelProperty("类别")
    private String productSort;

}
