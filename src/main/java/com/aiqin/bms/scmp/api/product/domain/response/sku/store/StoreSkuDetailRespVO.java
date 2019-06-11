package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/29 0029 11:24
 */
@ApiModel("门店查看单个sku详情")
@Data
public class StoreSkuDetailRespVO {

    @ApiModelProperty("sku基本信息")
    @JsonProperty("storeSkuBaseInfo")
    private StoreSkuBaseInfo storeSkuBaseInfo;

    @ApiModelProperty("生产厂家")
    @JsonProperty("storeSkuFactoryInfo")
    private StoreSkuFactoryInfo storeSkuFactoryInfo;

    @ApiModelProperty("销售信息")
    @JsonProperty("storeSkuSalesInfo")
    private StoreSkuSalesInfo storeSkuSalesInfo;

    @ApiModelProperty("图片及介绍")
    @JsonProperty("storeSkuPicInfo")
    private List<StoreSkuPicInfo> storeSkuPicInfo;

}
