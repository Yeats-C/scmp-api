package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.common.TagTypeCode;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockFlowRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyUseTagRecord;
import com.aiqin.bms.scmp.api.supplier.service.ApplyUseTagRecordService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyongqiang
 * @description 库存管理请求控制器
 * @date 2018/11/20 15:04
 */
@RestController
@Api(tags = "库存库房接口")
@RequestMapping("/stock")
@Slf4j
public class StockController {

    @Resource
    private StockService stockService;
    @Autowired
    private ApplyUseTagRecordService applyUseTagRecordService;

    @PostMapping("/search/transport/page")
    @ApiOperation(value = "仓库库存管理列表")
    public HttpResponse<PageResData<StockRespVO>> selectTransportStockInfoByPage(@RequestBody StockRequest stockRequest) {
        return HttpResponse.success(stockService.selectTransportStockInfoByPage(stockRequest));
    }

    @PostMapping("/search/storehouse/page")
    @ApiOperation(value = "库房库存管理列表")
    public HttpResponse<PageResData<StockRespVO>> selectStorehouseStockInfoByPage(@RequestBody StockRequest stockRequest) {
        return HttpResponse.success(stockService.selectStorehouseStockInfoByPage(stockRequest));
    }

    @PostMapping("/search/sum/page")
    @ApiOperation(value = "中央库存列表")
    public HttpResponse<PageResData<StockRespVO>> getListSumByPage(@RequestBody StockRequest stockRequest) {
        return HttpResponse.success(stockService.selectStockSumInfoByPage(stockRequest));
    }

    @GetMapping("/search/warehouse/info")
    public HttpResponse<StockRespVO> stockWarehouseInfo(@RequestParam(value = "stock_code") String stockCode){
        return stockService.stockWarehouseInfo(stockCode);
    }


    @GetMapping("/search/stock/flow")
    @ApiOperation(value = "查询库房库存详情流水")
    public HttpResponse<PageResData<StockFlow>> selectStockFlow(@RequestParam(value = "stock_code") String stockCode,
                                                                @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                                @RequestParam(value = "page_size", required = false) Integer pageSize) {
        StockLogsRequest request = new StockLogsRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setStockCode(stockCode);
        return HttpResponse.success(stockService.selectStockFlow(request));
    }

    @PostMapping("/search/sku/page")
    @ApiOperation(value = "查询库存商品(分页)")
    public HttpResponse<PageInfo<QueryStockSkuRespVo>> selectStockSkuByPage(@RequestBody QueryStockSkuReqVo reqVO) {
        reqVO.setCompanyCode(null != AuthenticationInterceptor.getCurrentAuthToken() ? AuthenticationInterceptor.getCurrentAuthToken().getCompanyCode() : null);
        PageInfo<QueryStockSkuRespVo> queryStockSkuRespVoPageInfo = stockService.selectStockSkuPage(reqVO);
        return HttpResponse.success(queryStockSkuRespVoPageInfo);
    }

    @PostMapping("/search/sku/nopage")
    @ApiOperation(value = "查询库存商品")
    public HttpResponse<List<QueryStockSkuRespVo>> selectStockSku(@RequestBody QueryStockSkuReqVo reqVO) {
        return HttpResponse.success(stockService.selectStockSku(reqVO));
    }

    @PostMapping("/search/sku/list")
    @ApiOperation(value = "查询库存商品(不分页)")
    public HttpResponse<List<QueryStockSkuRespVo>> selectStockSkus(@RequestBody QueryStockSkuReqVo reqVO) {
        List<QueryStockSkuRespVo> queryStockSkuRespVos = stockService.selectStockSkus(reqVO);
        return HttpResponse.success(queryStockSkuRespVos);
    }

    //因为目前对接中心没介入,所以直接接收采购单
    @ApiOperation(value = "采购单审批通过,接收采购单生成入库单")
    @PostMapping("/inbound/save")
    public HttpResponse<InboundReqVo> save(@RequestBody InboundReqVo reqVo){
        return HttpResponse.success(stockService.save(reqVo));
    }

    // 暂时保留
    @PostMapping("change1")
    @ApiOperation(value = "库存修改")
    public HttpResponse changeStock(@RequestBody StockChangeRequest stockChangeRequest) throws Exception {
        return stockService.changeStock(stockChangeRequest);
    }

    @PostMapping("/change")
    @ApiOperation(value = "库存修改")
    public HttpResponse stockAndBatchChange(@RequestBody ChangeStockRequest request) {
        return stockService.stockAndBatchChange(request);
    }

    @PostMapping("/change/stock/batch")
    @ApiOperation(value = "批次库存修改")
    public HttpResponse changeStockBatch(@RequestBody ChangeStockRequest request){
        return stockService.changeStockBatch(request);
    }

    @PostMapping("/logs")
    public HttpResponse logs(@RequestBody StockLogsRequest stockLogsRequest){
        return stockService.logs(stockLogsRequest);
    }

    @GetMapping("/search/stock/sku/page")
    @ApiOperation(value = "库房管理新增调拨,移库,报废列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "out_transport_center_code", value = "调出仓库", type = "String"),
            @ApiImplicitParam(name = "out_warehouse_code", value = "调出库房", type = "String"),
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "in_transport_center_code", value = "调入仓库", type = "String"),
            @ApiImplicitParam(name = "in_warehouse_code", value = "调入库房", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编号", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类", type = "String"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性", type = "String"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<List<QueryStockSkuListRespVo>> selectStockSkuList(@RequestParam(value = "out_transport_center_code", required = false) String outTransportCenterCode,
                                                                          @RequestParam(value = "out_warehouse_code", required = false) String outWarehouseCode,
                                                                          @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
                                                                          @RequestParam(value = "in_transport_center_code", required = false) String inTransportCenterCode,
                                                                          @RequestParam(value = "in_warehouse_code", required = false) String inWarehouseCode,
                                                                          @RequestParam(value = "sku_code", required = false) String skuCode,
                                                                          @RequestParam(value = "sku_name", required = false) String skuName,
                                                                          @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
                                                                          @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
                                                                          @RequestParam(value = "product_property_code", required = false) String productPropertyCode,
                                                                          @RequestParam(value = "page_no", required = false) Integer page_no,
                                                                          @RequestParam(value = "page_size", required = false) Integer page_size) {
        QueryStockSkuListReqVo reqVO = new QueryStockSkuListReqVo(outTransportCenterCode,outWarehouseCode,procurementSectionCode,inTransportCenterCode,inWarehouseCode,skuCode,skuName,productBrandCode,productCategoryCode,productPropertyCode);
        reqVO.setPageNo(page_no);
        reqVO.setPageSize(page_size);
        return HttpResponse.success(stockService.selectStockSkuList(reqVO));
    }

    @PostMapping("/update/storehouse")
    @ApiOperation(value = "库房管理列表数据保存")
    public HttpResponse updateStorehouseById(@RequestBody List<StockRespVO> stockRespVO){
        stockService.updateStorehouseById(stockRespVO);
        return  HttpResponse.success();
    }

    @GetMapping("/search/stock/sku/import")
    @ApiOperation(value = "库房管理新增列表查询导入操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "out_transport_center_code", value = "调出仓库", type = "String"),
            @ApiImplicitParam(name = "out_warehouse_code", value = "调出库房", type = "String"),
            @ApiImplicitParam(name = "procurement_section_code", value = "采购组", type = "String"),
            @ApiImplicitParam(name = "in_transport_center_code", value = "调入仓库", type = "String"),
            @ApiImplicitParam(name = "in_warehouse_code", value = "调入库房", type = "String"),
            @ApiImplicitParam(name = "sku_code", value = "sku编号", type = "String"),
            @ApiImplicitParam(name = "sku_name", value = "sku名称", type = "String"),
            @ApiImplicitParam(name = "product_brand_code", value = "品牌", type = "String"),
            @ApiImplicitParam(name = "product_category_code", value = "品类", type = "String"),
            @ApiImplicitParam(name = "product_property_code", value = "商品属性", paramType = "query"),
            @ApiImplicitParam(name = "sku_list", value = "sku编码集合", type = "List<String>"),
            @ApiImplicitParam(name = "page_no", value = "当前页", type = "Integer"),
            @ApiImplicitParam(name = "page_size", value = "每页条数", type = "Integer"),
    })
    public HttpResponse<List<QueryStockSkuListRespVo>> importStockSkuList(@RequestParam(value = "out_transport_center_code", required = false) String outTransportCenterCode,
                                                                          @RequestParam(value = "out_warehouse_code", required = false) String outWarehouseCode,
                                                                          @RequestParam(value = "procurement_section_code", required = false) String procurementSectionCode,
                                                                          @RequestParam(value = "in_transport_center_code", required = false) String inTransportCenterCode,
                                                                          @RequestParam(value = "in_warehouse_code", required = false) String inWarehouseCode,
                                                                          @RequestParam(value = "sku_code", required = false) String skuCode,
                                                                          @RequestParam(value = "sku_name", required = false) String skuName,
                                                                          @RequestParam(value = "product_brand_code", required = false) String productBrandCode,
                                                                          @RequestParam(value = "product_category_code", required = false) String productCategoryCode,
                                                                          @RequestParam(value = "product_property_code", required = false) String productPropertyCode,
                                                                          @RequestParam(value = "sku_list", required = false) List<String> skuList,
                                                                          @RequestParam(value = "page_no", required = false) Integer page_no,
                                                                          @RequestParam(value = "page_size", required = false) Integer page_size) {
        QueryImportStockSkuListReqVo reqVO = new QueryImportStockSkuListReqVo(outTransportCenterCode,outWarehouseCode,procurementSectionCode,inTransportCenterCode,inWarehouseCode,skuCode,skuName,productBrandCode,productCategoryCode,productPropertyCode,skuList);
        reqVO.setPageNo(page_no);
        reqVO.setPageSize(page_size);
        return HttpResponse.success(stockService.importStockSkuList(reqVO));
    }

    @GetMapping("/search/byCityAndProvince")
    @ApiOperation(value = "总库存管理列表")
    public HttpResponse<String> byCityCodeAndprovinceCode(@RequestParam("provinceCode") String provinceCode,
                                                          @RequestParam("cityCode") String cityCode,
                                                          @RequestParam("tagCode") String tagCode,
                                                          @RequestParam("exitStock") String exitStock,
                                                          @RequestParam("orderByType") String orderByType) {
        return HttpResponse.success(stockService.byCityCodeAndprovinceCode(provinceCode,cityCode,tagCode,exitStock,orderByType));
    }

    @GetMapping("/search/byCityAndProvinceAndskuCode")
    @ApiOperation(value = "总库存管理列表")
    public HttpResponse<List<StockBatchRespVO>> byCityAndProvinceAndskuCode(@RequestParam("skuCode") String skuCode,@RequestParam("provinceCode") String provinceCode,@RequestParam("cityCode") String cityCode) {
        return HttpResponse.success(stockService.byCityAndProvinceAndskuCode(skuCode,provinceCode,cityCode));
    }

    @GetMapping("/search/getTagRecordList")
    @ApiOperation("查看")
    public HttpResponse<List<ApplyUseTagRecord>> getTagRecordList(@RequestParam String skuCode) {
        log.info("ProductSkuPriceInfoController---view---入参：[{}]", skuCode);
        return HttpResponse.success(applyUseTagRecordService.getApplyUseTagRecordByAppUseObjectCode(skuCode, TagTypeCode.SKU.getStatus()));
    }
}
