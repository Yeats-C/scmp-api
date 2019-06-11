package com.aiqin.bms.scmp.api.product.domain.response.sku.oms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/26 0026 14:48
 */
@Data
@ApiModel("oms商品数据返回参数")
public class OmsProductListItemResp {
    @ApiModelProperty("商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("标签列表")
    @JsonProperty("tag_list")
    private List<String> tagList;

    @ApiModelProperty("最低价")
    @JsonProperty("low_price")
    private Long lowPrice;

    @ApiModelProperty("最高价")
    @JsonProperty("high_price")
    private Long highPrice;

    @ApiModelProperty("图片路径")
    @JsonProperty("image_path")
    private String imagePath;

    @JsonProperty("brand_ids")
    @ApiModelProperty("包含品牌")
    private List<String> brandIds;

    @JsonProperty("category_ids")
    @ApiModelProperty("包含品类")
    private List<String> categoryIds;


}
