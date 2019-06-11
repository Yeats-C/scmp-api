package com.aiqin.bms.scmp.api.supplier.domain.response.sku;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 15:55
 */
@Data
@ApiModel("sku所有信息详情")
public class ProductSkuDetailResp {
    @ApiModelProperty("正式sku信息")
    private ProductSkuInfo productSkuInfo;
    @ApiModelProperty("正式sku结账信息")
    private ProductSkuCheckout productSkuCheckout;
    @ApiModelProperty("正式sku图片及介绍")
    private List<ProductSkuPictures> productSkuPictures;
    @ApiModelProperty("正式sku价格管理")
    private List<ProductSkuPrice> productSkuPrices;
    @ApiModelProperty("正式sku商品说明")
    private List<ProductSkuPicDesc> productSkuPicDescs;
    @ApiModelProperty("正式sku进货类信息")
    private ProductSkuPurchaseInfo productSkuPurchaseInfo;
    @ApiModelProperty("正式sku配送类信息")
    private ProductSkuDistributionInfo productSkuDistributionInfo;
    @ApiModelProperty("正式sku整箱商品包装信息")
    private ProductSkuBoxPacking productSkuBoxPacking;
    @ApiModelProperty("正式sku销售信息")
    private List<ProductSkuSalesInfo> productSkuSalesInfos;
    @ApiModelProperty("正式sku供货单位信息")
    private List<ProductSkuSupplyUnit> productSkuSupplyUnits;
    @ApiModelProperty("正式sku生产厂家")
    private List<ProductSkuManufacturer> productSkuManufacturers;
    @ApiModelProperty("正式sku文件管理")
    private List<ProductSkuFile> productSkuFiles;
    @ApiModelProperty("正式sku配置管理")
    private List<ProductSkuConfig> productSkuConfigs;
    @ApiModelProperty("正式质检报告")
    private List<ProductSkuInspReport> productSkuInspReports;
}
