package com.aiqin.bms.scmp.api.product.web.sku;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ocenter.QueryCenterSkuListReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.OmsProductSkuListReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.QuerySkuListPageReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOmsSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOrderReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.CheckPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.QueryPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuResponse;
import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.merchant.MerchantSkuItemRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ocenter.QueryCenterSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.OmsProductListItemResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.OmsProductSkuItemResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.QuerySkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.*;
import com.aiqin.bms.scmp.api.product.service.InspectionReportService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 15:59
 */
@RestController
@Api(description="sku相关api")
@RequestMapping("/product/sku")
@Slf4j
public class ProductSkuController {
    @Resource
    private SkuService skuService;
    @Autowired
    InspectionReportService inspectionReportService;

    @PostMapping("/purchase/list")
    @ApiOperation("采购新建查询sku列表")
    public HttpResponse<BasePage<PurchaseItemRespVo>> getSkuList(@RequestBody @Validated QueryPurchaseSkuReqVO queryPurchaseSkuReqVO){
        return HttpResponse.success(skuService.getPurchaseSkuList(queryPurchaseSkuReqVO));
    }

    @PostMapping("/purchase/check")
    @ApiOperation("采购导入sku查询列表")
    public HttpResponse<List<PurchaseItemRespVo>> getCheckList(@RequestBody @Validated CheckPurchaseSkuReqVO checkPurchaseSkuReqVO){
        return HttpResponse.success(skuService.getCheckSkuList(checkPurchaseSkuReqVO));
    }

    @PostMapping("/purchase/getSkuConvertNumBySkuCode")
    @ApiOperation("通过sku编码集合获取主单位和采购单位之间的转换系数")
    public HttpResponse<Map<String,Long>> getSkuConvertNumBySkuCode(@RequestBody(required = true) List<String> skuCodes){
        //todo
        //当前先默认给每个编码返一个系数为10
        Map<String,Long> map=skuService.getSkuConvertNumBySkuCodes(skuCodes);
        return HttpResponse.success(map);
    }

    @PostMapping("/sales/list")
    @ApiOperation("销售查询sku基本信息列表")
    public HttpResponse<PurchaseItemRespVo> getSalesSkuList(@RequestBody List<String> skuList){
        return HttpResponse.success(skuService.getSalesSkuList(skuList));
    }

    @PostMapping("/shop/list")
    @ApiOperation("门店根据商品编码及其他sku属性查询sku列表")
    public HttpResponse<StoreSkuListRespVO> getSkuListByProductCode(@RequestBody QueryStoreSkuListReqVO queryStoreSkuListReqVO) {
        return HttpResponse.success(skuService.getSkuListByProductCode(queryStoreSkuListReqVO));
    }

    @PostMapping("/shop/spu/detail")
    @ApiOperation("门店根据商品编码及其他sku属性查询sku列表")
    public HttpResponse<List<StoreSkuItemRespVO>> getProductDetailSkuList(@RequestBody QueryStoreSkuListReqVO queryStoreSkuListReqVO){
        return HttpResponse.success(skuService.getProductDetailSkuList(queryStoreSkuListReqVO));
    }

    @PostMapping("/shop/search")
    @ApiOperation("门店根据条件查询商品列表")
    public HttpResponse<BasePage<ProductItemStoreRespVO>> searchSkuList(@RequestBody QueryStoreProductListReqVO queryStoreProductListReqVO){
        return HttpResponse.success(skuService.getStoreProductItem(queryStoreProductListReqVO));
    }

    @GetMapping("/shop/detail")
    @ApiOperation("门店根据sku编码查询sku详情")
    public HttpResponse<StoreSkuDetailRespVO> getSkuDetail(@RequestParam("sku_code") String skuCode){
        return HttpResponse.success(skuService.getStoreSkuDetailByCode(skuCode));
    }

    @PostMapping("/merchant/list")
    @ApiOperation("微商城sku列表")
    public HttpResponse<List<MerchantSkuItemRespVO>> getMerchantSkuListByCodes(@RequestBody QueryMerchantSkuListReqVO queryMerchantSkuListReqVO){
        return HttpResponse.success(skuService.getMerchantSkuListByCodes(queryMerchantSkuListReqVO));
    }

    @PostMapping("/search/by/code")
    @ApiOperation(value = "根据skuCodeList查询sku信息")
    public HttpResponse<List<ProductSkuResponse>> selectSkuInfoListCanUseBySkuCodeList(@RequestBody List<String> skuCodeList){
        List<ProductSkuResponse> ProductSkuResponseList = skuService.selectSkuInfoListCanUseBySkuCodeList(skuCodeList);
        return HttpResponse.success(ProductSkuResponseList);
    }

    @GetMapping("/inspection/report/list")
    @ApiOperation("根据销售码查询对应质检报告信息")
    public HttpResponse<List<InspectionReportRespVO>> getInspectionReportByCode(String saleCode){
        return HttpResponse.success(inspectionReportService.getInspectionReportByCode(saleCode));
    }

    @PostMapping("/inspection/report/detail")
    @ApiOperation("根据生产日期和销售码查询质检信息")
    public HttpResponse<InspectionReportRespVO> getInspectionReport(@RequestBody QueryInspectionReportReqVO queryInspectionReportReqVO){
        return HttpResponse.success(inspectionReportService.getInspectionReport(queryInspectionReportReqVO));
    }

    @PostMapping("/store/list")
    @ApiOperation(value = "门店根据skuCodeList查询sku信息")
    public HttpResponse<List<StoreSkuItemRespVO>> getStoreSkuListByCodes(@RequestBody QueryStoreSkusReqVO queryStoreSkusReqVO){
        List<StoreSkuItemRespVO> storeSkuItemRespVOS = skuService.getStoreSkuListByCodes(queryStoreSkusReqVO);
        return HttpResponse.success(storeSkuItemRespVOS);
    }

    @PostMapping("/oms/order")
    @ApiOperation("oms查询订单")
    public HttpResponse<BasePage<OmsProductListItemResp>> getOmsOrderList(@RequestBody SearchOrderReqVO searchOrderReqVO){
        return HttpResponse.success(skuService.getOmsProductList(searchOrderReqVO));
    }

    @PostMapping("/oms/search")
    @ApiOperation("oms查询sku列表")
    public HttpResponse<BasePage<QueryProductSkuListResp>> getOmsSkuList(@RequestBody SearchOmsSkuListReqVO searchOmsSkuListReqVO){
        return HttpResponse.success(skuService.getOmsSkuList(searchOmsSkuListReqVO));
    }

    @PostMapping("/oms/skulist")
    @ApiOperation("oms根据商品编码查询sku列表")
    public HttpResponse<List<OmsProductSkuItemResp>> getOmsProductSkuList(@RequestBody OmsProductSkuListReq omsProductSkuListReq){
        return HttpResponse.success(skuService.getOmsProductSkuList(omsProductSkuListReq));
    }

    @PostMapping("/oms/skus")
    @ApiOperation("oms根据sku编码集合查询sku信息列表")
    public HttpResponse<List<QuerySkuListResp>> getOmsSkus(@RequestBody QueryStoreSkusReqVO queryStoreSkusReqVO) {
        return HttpResponse.success(skuService.getOmsSkus(queryStoreSkusReqVO));
    }
    @PostMapping("/oms/searchSku")
    @ApiOperation("oms根据查询条件分页查询sku列表")
    public HttpResponse getPageSkuList(@RequestBody QuerySkuListPageReq querySkuListPageReq){
        return HttpResponse.success(skuService.queryOmsSkuPage(querySkuListPageReq));
    }

    @GetMapping("/info/by/code")
    @ApiOperation("根据skuCode查询sku信息")
    public HttpResponse<ProductSkuResponse> selectSkuInfoBySkuCode(@RequestParam(value = "sku_code") String skuCode){
        return HttpResponse.success(skuService.selectSkuInfoBySkuCode(skuCode));
    }


    @PostMapping("/oCenter/skuList")
    @ApiOperation("ocenter查询sku列表")
    public HttpResponse<BasePage<QueryCenterSkuListRespVo>> querySkuList(@RequestBody QueryCenterSkuListReqVo querySkuListReqVo){
        try {
            return HttpResponse.success(skuService.queryOcenterSkuList(querySkuListReqVo));
        } catch (BizException be) {
            return HttpResponse.failure(be.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.QUERY_ERROR);
        }
    }
}
