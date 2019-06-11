package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/15 0015 14:20
 */
@Data
@ApiModel("商品下SKU数据信息")
public class StoreSkuListRespVO {
    @JsonProperty("store_sku_detail")
    @ApiModelProperty("第一个sku详情")
    private StoreSkuDetailRespVO storeSkuDetailRespVO;

    @JsonProperty("store_sku_item_list")
    @ApiModelProperty("sku列表")
    private List<StoreSkuItemRespVO> storeSkuItemRespVOList;
}
