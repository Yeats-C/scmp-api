package com.aiqin.bms.scmp.api.product.domain.response.newproduct;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("商品查询返回实体")
@Data
public class NewSkuDetailsResponseVO {
    @ApiModelProperty("sku id")
    private Long id;
    @ApiModelProperty("sku编号")
    private String skuCode;
    @ApiModelProperty("sku 名称")
    private String skuName;
    @ApiModelProperty("商品类别名称")
    private String productSortName;
    @ApiModelProperty("商品属性名称")
    private String productPropertyName;
    @ApiModelProperty("商品品类名称")
    private String productCategoryName;
    @ApiModelProperty("商品品牌名称")
    private String productBrandName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

}
