package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyUseTagRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @ApiModelProperty(value = "sku基本信息",position = 1)
    private ProductSkuRespVo productSkuInfo;

    @ApiModelProperty(value = "spu信息",position = 1)
    private NewProductResponseVO spuInfo;

    @ApiModelProperty(value = "商品标签信息",position = 2)
    List<ApplyUseTagRecord> tagInfoList;

    @ApiModelProperty(value = "sku渠道信息",position = 2)
    private List<ProductSkuChannelRespVo> productSkuChannels;

    @ApiModelProperty(value = "进销存信息",position = 3)
    private List<PurchaseSaleStockRespVo> purchaseSaleStocks;

    @ApiModelProperty(value = "sku整箱商品包装信息",position = 4)
    private List<ProductSkuBoxPackingRespVo> productSkuBoxPackings;

    @ApiModelProperty(value = "sku结账信息",position = 5)
    private ProductSkuCheckoutRespVo productSkuCheckout;

    @ApiModelProperty(value = "sku供应商信息",position = 6)
    private List<ProductSkuSupplyUnitRespVo> productSkuSupplyUnits;

    @ApiModelProperty(value = "sku价格管理",name = "productSkuPrices",position = 8)
    @JsonProperty("productSkuPrices")
    private List<ProductSkuPriceRespVo> productSkuPrices;

    @ApiModelProperty(value = "sku配置管理",name = "productSkuConfigs",position = 9)
    @JsonProperty("productSkuConfigs")
    private  List<SkuConfigsRepsVo> productSkuConfigs;

    @ApiModelProperty(value = "关联商品",position = 10)
    private List<ProductSkuAssociatedGoodsRespVo> productAssociatedGoods;

    @ApiModelProperty(value = "sku生产厂家",position = 11)
    private List<ProductSkuManufacturerRespVo> productSkuManufacturers;

    @ApiModelProperty(value = "sku图片及介绍",position = 11)
    private List<ProductSkuPicturesRespVo> productSkuPictures;

    @ApiModelProperty(value = "sku商品说明",position = 13)
    private List<ProductSkuPicDescRespVo> productSkuPicDescs;

    @ApiModelProperty(value = "sku文件管理",position = 14)
    private List<ProductSkuFileRespVO> productSkuFiles;

    @ApiModelProperty(value = "sku质检信息", position = 15)
    private List<ProductSkuInspReportRespVo> productSkuInspReports;

    @ApiModelProperty(value = "组合商品子SKU列表",position = 10)
    private List<ProductSkuSubRespVo> productSkuSubRespVos;
}
