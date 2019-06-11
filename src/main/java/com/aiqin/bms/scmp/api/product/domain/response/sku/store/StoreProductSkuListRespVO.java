package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/17 0017 15:03
 */
@Data
@ApiModel("返回spu详情下sku列表只有sku基本信息")
public class StoreProductSkuListRespVO {
    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("sku主图")
    @JsonProperty("sku_image")
    private String skuImage;

    @ApiModelProperty("sku列表")
    @JsonProperty("sku_item_list")
    private List<StoreSkuItemRespVO> storeSkuItemRespVOList;
}
