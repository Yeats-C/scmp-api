package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 14:48
 */
@ApiModel("新增SKU信息对象")
@Data
public class AddSkuInfoReqVO {

    @ApiModelProperty(value = "sku基本信息",name = "productSkuInfo",position = 1)
    @JsonProperty("productSkuInfo")
    private ProductSkuDraft productSkuDraft;

    @ApiModelProperty(value = "sku渠道信息",name = "productSkuChannels",position = 2)
    @JsonProperty("productSkuChannels")
    private List<ProductSkuChannelDraft> productSkuChannelDrafts;

    @ApiModelProperty(value = "进销存信息",name = "purchaseSaleStocks",position = 3)
    @JsonProperty("purchaseSaleStocks")
    private List<PurchaseSaleStockReqVo> purchaseSaleStockReqVos;

    @ApiModelProperty(value = "sku整箱商品包装信息",name = "productSkuBoxPackings",position = 4)
    @JsonProperty("productSkuBoxPackings")
    private List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts;

    @ApiModelProperty(value = "sku结账信息",name = "productSkuCheckout",position = 5)
    @JsonProperty("productSkuCheckout")
    private ProductSkuCheckoutDraft productSkuCheckoutDraft;

    @ApiModelProperty(value = "sku供应商信息",name = "productSkuSupplyUnits",position = 6)
    @JsonProperty("productSkuSupplyUnits")
    private List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts;

    @ApiModelProperty(value = "sku价格管理",name = "productSkuPrices",position = 8)
    @JsonProperty("productSkuPrices")
    private List<ProductSkuPriceDraft> productSkuPriceDrafts;

    @ApiModelProperty(value = "sku配置管理",name = "productSkuConfigs",position = 9)
    @JsonProperty("productSkuConfigs")
    private List<ProductSkuConfigDraft> productSkuConfigDrafts;

    @ApiModelProperty(value = "关联商品",name = "productAssociatedGoods",position = 10)
    @JsonProperty("productAssociatedGoods")
    private List<ProductSkuAssociatedGoodsDraft> productSkuAssociatedGoodsDrafts;

    @ApiModelProperty(value = "sku生产厂家",name = "productSkuManufacturers",position = 11)
    @JsonProperty("productSkuManufacturers")
    private List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts;

    @ApiModelProperty(value = "sku图片及介绍",name = "productSkuPictures",position = 11)
    @JsonProperty("productSkuPictures")
    private List<ProductSkuPicturesDraft> productSkuPicturesDrafts;

    @ApiModelProperty(value = "sku商品说明",name = "productSkuPicDescs",position = 13)
    @JsonProperty("productSkuPicDescs")
    private List<ProductSkuPicDescDraft> productSkuPicDescDrafts;

    @ApiModelProperty(value = "sku文件管理",name = "productSkuFiles",position = 14)
    @JsonProperty("productSkuFiles")
    private List<ProductSkuFileDraft> productSkuFileDrafts;

    @ApiModelProperty(value = "sku质检信息", name = "productSkuInspReports",position = 15)
    @JsonProperty("productSkuInspReports")
    private List<ProductSkuInspReportDraft> productSkuInspReportDrafts;
}
