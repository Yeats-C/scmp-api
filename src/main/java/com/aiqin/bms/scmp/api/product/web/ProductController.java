package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.Product;
import com.aiqin.bms.scmp.api.product.domain.ProductDistributor;
import com.aiqin.bms.scmp.api.product.domain.ProductPriceChange;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.service.ProductService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 爱亲 on 2018/11/13.
 */
@RestController
@Api("商品相关接口")
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/sku/info")
    @ApiOperation(value = "查询微商城SKU信息")
    public HttpResponse selectWeiShopSkuInfo(@RequestParam(value = "distributor_id") String distributorId) {
        return productService.selectWeiShopSkuInfo(distributorId);
    }

    @GetMapping("/shop/sku")
    @ApiOperation(value = "查询SKU信息")
    public HttpResponse selectShopSku(@RequestParam(value = "distributor_id") String distributorId) {
        return productService.selectShopSku(distributorId);
    }

    @GetMapping("/top/sku/info")
    @ApiOperation(value = "查询微商城已上架SKU信息")
    public HttpResponse selectWeiShopTopSkuInfo(@RequestParam(value = "distributor_id") String distributorId) {
        return productService.selectWeiShopTopSkuInfo(distributorId);
    }

    @GetMapping("/top/shop/sku")
    @ApiOperation(value = "查询门店已上架SKU信息")
    public HttpResponse selectShopTopSkuInfo(@RequestParam(value = "distributor_id") String distributorId) {
        return productService.selectShopTopSku(distributorId);
    }

    @GetMapping("/warning/stock/info")
    @ApiOperation(value = "查询预警库存SKU信息")
    public HttpResponse selectWarningStockSkuInfo(@RequestParam(value = "distributor_id") String distributorId) {
        return productService.selectWarningStockSkuInfo(distributorId);
    }

    @GetMapping("/find/category/info")
    @ApiOperation(value = "查询商品分类信息")
    public HttpResponse selectProductCategoryListInfo() {
        return productService.selectProductCategoryListInfo();
    }

    @GetMapping("/search/product/info")
    @ApiOperation(value = "查询商品信息")
    public HttpResponse selectProductListInfo(@RequestParam(value = "category_id", required = false) String categoryId,
                                              @RequestParam(value = "brand_id", required = false) String brandId,
                                              @RequestParam(value = "product_name", required = false) String productName) {
        return productService.selectProductListInfo(new Product(productName, brandId, categoryId));
    }

    @GetMapping("/find/info")
    @ApiOperation(value = "根据skuCode查询商品信息")
    public HttpResponse<ProductDistributor> selectProductInfoBySkuCode(@RequestParam(name = "sku_code", required = false) String skuCode,
                                                                       @RequestParam(name = "distributor_id", required = false) String distributorId) {
        return productService.selectProductInfoBySkuCode(skuCode, distributorId);
    }

    @PutMapping("/replace/info")
    @ApiOperation(value = "根据商品id修改商品信息")
    public HttpResponse updateProductInfoByProductId(@RequestBody ProductRequest productRequest) {
        return productService.updateProductInfoByProductId(productRequest);
    }

    @PostMapping("/upload/images")
    @ApiOperation(value = "上传封面图 (请以data:image或data:img开头)")
    @ApiImplicitParam(name = "base64", value = "图片base64码", dataType = "String", paramType = "body", required = true)
    public HttpResponse uploadImage(@RequestBody String base64) {
        return productService.uploadImage(base64);
    }

    @DeleteMapping("/replace/channel")
    @ApiOperation(value = "将商品从微商城删除")
    public HttpResponse updateChannelBySkuCode(@RequestParam(value = "sku_code") String skuCode,
                                               @RequestParam(value = "distributor_id") String distributorId,
                                               @RequestParam(value = "update_by") String updateBy) {
        return productService.updateChannelBySkuCode(new ProductDistributor(skuCode, distributorId, updateBy));
    }

    @PostMapping("/search/info/by")
    @ApiOperation(value = "根据商品codeList查询商品信息")
    public HttpResponse selectProductInfoByProductCodeList(@RequestBody List<String> productCodeList) {
        return productService.selectProductInfoByProductCodeList(productCodeList);
    }

    @GetMapping("/search/info")
    @ApiOperation(value = "根据搜索条件查询商品信息")
    public HttpResponse selectProductInfoBySearch(@RequestParam(value = "category_id", required = false) String categoryId,
                                                  @RequestParam(value = "brand_id", required = false) String brandId) {
        return productService.selectProductInfoBySearch(new Product(brandId, categoryId));
    }

    @GetMapping("/coupon/info")
    @ApiOperation(value = "根据code或者分类查询商品信息")
    public HttpResponse selectProductInfoByCategoryAndCode(@RequestParam(value = "product_code", required = false) List<String> productCodeList,
                                                           @RequestParam(value = "category_id", required = false) String categoryId,
                                                           @RequestParam(value = "page_size", required = false) Integer pageSize,
                                                           @RequestParam(value = "page_no", required = false) Integer pageNo) {
        return productService.selectProductInfoByCategoryAndCode(new Product(categoryId, productCodeList, pageSize, pageNo));
    }

    @GetMapping("/find/category")
    @ApiOperation(value = "根据搜索条件查询商品分类信息")
    public HttpResponse selectCategoryThirdByCategoryId(@RequestParam(value = "category_id", required = false) String categoryId) {
        return productService.selectCategoryThirdByCategoryId(categoryId);
    }

    @GetMapping("/find/product")
    @ApiOperation(value = "根据code或者名称或者销售码查询商品信息")
    public HttpResponse selectProductInfoByIdOrCode(@RequestParam(value = "text", required = false) String productName,
                                                    @RequestParam(value = "distributor_id") String distributorId,
                                                    @RequestParam(value = "page_size", required = false) Integer pageSize,
                                                    @RequestParam(value = "page_no", required = false) Integer pageNo) {
        return productService.selectProductInfoByIdOrCode(new ProductDistributor(distributorId, productName, pageSize, pageNo));
    }

    @GetMapping("/find/all/category")
    @ApiOperation(value = "查询品类数量")
    public HttpResponse countAllCategory(@RequestParam(value = "distributor_id") String distributorId) {
        return productService.countAllCategory(distributorId);
    }

    @GetMapping("/find/price")
    @ApiOperation(value = "查询商品价格跟零售价")
    public HttpResponse selectPriceByProductIdList(@RequestParam(value = "product_id_list", required = false) List<String> productIdList) {
        return productService.selectPriceByProductIdList(new Product(productIdList));
    }

    @PostMapping("/find/cycle")
    @ApiOperation(value = "根据商品id查询商品消耗周期")
    public HttpResponse selectProductCycleByProductIdList(@RequestBody List<String> productIdList) {
        return productService.selectProductCycleByProductIdList(productIdList);
    }

    @PostMapping("/find/product/by")
    @ApiOperation(value = "根据商品skuCode集合查询商品信息")
    public HttpResponse selectProductInfoByProductIdList(@RequestBody ProductSkuRequest productSkuRequest) {
        return productService.selectProductInfoByProductIdList(productSkuRequest);
    }

    @GetMapping("/find/all/top")
    @ApiOperation(value = "查询所有上架sku数量")
    public HttpResponse countAllTopSku(@RequestParam(name = "distributor_id") String distributorId) {
        return productService.countAllTopSku(distributorId);
    }

    @GetMapping("/find/all/down")
    @ApiOperation(value = "查询所有下架sku数量")
    public HttpResponse countAllDownSku(@RequestParam(name = "distributor_id") String distributorId) {
        return productService.countAllDownSku(distributorId);
    }

    @GetMapping("/sold/out/shop")
    @ApiOperation(value = "查询门店售罄sku数量")
    public HttpResponse countSoldOutShopSku(@RequestParam(name = "distributor_id") String distributorId) {
        return productService.countSoldOutShopSku(distributorId);
    }

    @GetMapping("/sold/out/wei")
    @ApiOperation(value = "查询微商城售罄sku数量")
    public HttpResponse countSoldOutWeiShopSku(@RequestParam(name = "distributor_id") String distributorId) {
        return productService.countSoldOutWeiShopSku(distributorId);
    }

    /****分销机构商品批量操作*****/
    @PostMapping("/batch/modify/distributor")
    @ApiOperation(value = "分销机构商品批量操作",notes = "sku_code、distributor_id为必传字段")
    public HttpResponse batchModifyDistributor(@RequestBody List<ProductInfoRequest> productInfoRequestList) {
        return productService.updateProductInfoBySearch(productInfoRequestList);
    }

    @ApiOperation("门店修改商品价格")
    @PutMapping("/shop/product/price")
    public HttpResponse<Integer> updatePrice(@RequestBody ProductPriceRequest request){

        return productService.updateProductPrice(request);
    }

    @PostMapping("/shop/product/price/list")
    @ApiOperation("门店查询 变价记录")
    public HttpResponse<PageResData<ProductPriceChange>> getProductPriceChangeList(@RequestBody ProductPriceChangeQuery query){
        return productService.listProductPriceChange(query);
    }
}