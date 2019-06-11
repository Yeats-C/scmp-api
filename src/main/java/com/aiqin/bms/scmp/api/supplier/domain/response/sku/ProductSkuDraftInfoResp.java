package com.aiqin.bms.scmp.api.supplier.domain.response.sku;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 11:18
 */
@Data
@ApiModel("sku所有草稿信息")
public class ProductSkuDraftInfoResp {

    @ApiModelProperty("sku基本信息")
    private ProductSkuDraft productSkuDraft;
    @ApiModelProperty("sku结账信息")
    private ProductSkuCheckoutDraft productSkuCheckoutDraft;
    @ApiModelProperty("sku图片及介绍")
    private List<ProductSkuPicturesDraft> productSkuPicturesDrafts;
    @ApiModelProperty("sku价格管理")
    private List<ProductSkuPriceDraft> productSkuPriceDrafts;
    @ApiModelProperty("sku商品说明")
    private List<ProductSkuPicDescDraft> productSkuPicDescDrafts;
    @ApiModelProperty("sku进货类信息")
    private ProductSkuPurchaseInfoDraft productSkuPurchaseInfoDraft;
    @ApiModelProperty("sku配送类信息")
    private ProductSkuDisInfoDraft productSkuDisInfoDraft;
    @ApiModelProperty("sku整箱商品包装信息")
    private ProductSkuBoxPackingDraft productSkuBoxPackingDraft;
    @ApiModelProperty("sku销售信息")
    private List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts;
    @ApiModelProperty("sku供货单位信息")
    private List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts;
    @ApiModelProperty("sku生产厂家")
    private List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts;
    @ApiModelProperty("sku文件管理")
    private List<ProductSkuFileDraft> productSkuFileDrafts;
    @ApiModelProperty("sku配置管理")
    private List<ProductSkuConfigDraft> productSkuConfigDrafts;
    @ApiModelProperty("sku质检信息")
    private List<ProductSkuInspReportDraft> productSkuInspReportDrafts;
}
