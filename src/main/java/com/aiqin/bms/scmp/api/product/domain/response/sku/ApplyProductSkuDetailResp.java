package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/13 0013 15:23
 */
@Data
@ApiModel("sku申请详情返回信息")
public class ApplyProductSkuDetailResp {
    @ApiModelProperty("申请sku信息")
    private ApplyProductSku applyProductSku;
    @ApiModelProperty("申请sku结账信息")
    private ApplyProductSkuCheckout applyProductSkuCheckout;
    @ApiModelProperty("申请sku图片及介绍")
    private List<ApplyProductSkuPictures> applyProductSkuPictures;
    @ApiModelProperty("申请sku价格管理")
    private List<ApplyProductSkuPrice> applyProductSkuPrices;
    @ApiModelProperty("申请sku商品说明")
    private List<ApplyProductSkuPicDesc> applyProductSkuPicDescs;
    @ApiModelProperty("申请sku进货类信息")
    private ApplyProductSkuPurchaseInfo applyProductSkuPurchaseInfo;
    @ApiModelProperty("申请sku配送类信息")
    private ApplyProductSkuDisInfo applyProductSkuDisInfo;
    @ApiModelProperty("申请sku整箱商品包装信息")
    private ApplyProductSkuBoxPacking productSkuBoxPacking;
    @ApiModelProperty("申请sku销售信息")
    private List<ApplyProductSkuSalesInfo> applyProductSkuSalesInfos;
    @ApiModelProperty("申请sku供货单位信息")
    private List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits;
    @ApiModelProperty("申请sku生产厂家")
    private List<ApplyProductSkuManufacturer> applyProductSkuManufacturers;
    @ApiModelProperty("申请sku文件管理")
    private List<ApplyProductSkuFile> applyProductSkuFiles;
    @ApiModelProperty("申请sku配置管理")
    private List<ApplyProductSkuConfig> applyProductSkuConfigs;
    @ApiModelProperty("申请质检报告")
    private List<ApplyProductSkuInspReport> applyProductSkuInspReports;
}
