package com.aiqin.bms.scmp.api.product.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.StockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.VerifyReturnSupplyReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.MerchantLockStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.QueryMerchantStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.PurchaseOutBoundRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.MerchantLockStockRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.QueryMerchantStockRepVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
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
@Api(description = "库存库房接口")
@RequestMapping("/stock")
@Slf4j
public class StockController {
    @Resource
    private StockService stockService;
    @Autowired
    private InboundService inboundService;

    @PostMapping("/search/page")
    @ApiOperation(value = "总库存管理列表")
    public HttpResponse<PageResData<Stock>> selectWarehouseStockInfoByPage(@RequestBody StockRequest stockRequest) {
        return HttpResponse.success(stockService.selectWarehouseStockInfoByPage(stockRequest));
    }

    @PostMapping("/search/transport/page")
    @ApiOperation(value = "物流中心库存管理列表")
    public HttpResponse<PageResData<Stock>> selectTransportStockInfoByPage(@RequestBody StockRequest stockRequest) {
        return HttpResponse.success(stockService.selectTransportStockInfoByPage(stockRequest));
    }

    @PostMapping("/search/storehouse/page")
    @ApiOperation(value = "库房库存管理列表")
    public HttpResponse<PageResData<Stock>> selectStorehouseStockInfoByPage(@RequestBody StockRequest stockRequest) {
        return HttpResponse.success(stockService.selectStorehouseStockInfoByPage(stockRequest));
    }

    @PostMapping("/search/sum/page")
    @ApiOperation(value = "获取总库存列表")
    public HttpResponse<PageResData<Stock>> getListSumByPage(@RequestBody StockRequest stockRequest) {
        return HttpResponse.success(stockService.selectStockSumInfoByPage(stockRequest));
    }

    @GetMapping("/search/one/info")
    @ApiOperation(value = "根据stockId查询单个stock信息")
    public HttpResponse<StockRespVO> selectOneStockInfoByStockId(@RequestParam(value = "stock_id") Long stockId) {
        return HttpResponse.success(stockService.selectOneStockInfoByStockId(stockId));
    }

    @PostMapping("/search/sku/page")
    @ApiOperation(value = "查询库存商品(分页)")
    public HttpResponse<PageInfo<QueryStockSkuRespVo>> selectStockSkuByPage(@RequestBody QueryStockSkuReqVo reqVO) {
        //数据不全，暂时不传 TODO
//        reqVO.setCompanyCode(null);
//        reqVO.setSupplyCode(null);
//        reqVO.setTransportCenterCode(null);
//        reqVO.setWarehouseCode(null);
        reqVO.setCompanyCode(null != AuthenticationInterceptor.getCurrentAuthToken() ? AuthenticationInterceptor.getCurrentAuthToken().getCompanyCode() : null);
        PageInfo<QueryStockSkuRespVo> queryStockSkuRespVoPageInfo = stockService.selectStockSkuPage(reqVO);
        return HttpResponse.success(queryStockSkuRespVoPageInfo);
    }

    @PostMapping("/search/sku/nopage")
    @ApiOperation(value = "查询库存商品")
    public HttpResponse<List<QueryStockSkuRespVo>> selectStockSku(@RequestBody QueryStockSkuReqVo reqVO) {
        //数据不全，暂时不传 TODO
//        reqVO.setCompanyCode(null);
//        reqVO.setSupplyCode(null);
//        stockService.selectStockSku(reqVO);
        return HttpResponse.success(stockService.selectStockSku(reqVO));
    }

    @PostMapping("/search/sku/list")
    @ApiOperation(value = "查询库存商品(不分页)")
    public HttpResponse<List<QueryStockSkuRespVo>> selectStockSkus(@RequestBody QueryStockSkuReqVo reqVO) {
        List<QueryStockSkuRespVo> queryStockSkuRespVos = stockService.selectStockSkus(reqVO);
        return HttpResponse.success(queryStockSkuRespVos);
    }

    @PostMapping("/verify/returnSupply")
    @ApiOperation(value = "验证退供商品信息,有错误则会返回list,否则list为空")
    public HttpResponse<PurchaseOutBoundRespVO> verifyReturnSupply(@RequestBody VerifyReturnSupplyReqVo reqVO) {
        return stockService.verifyReturnSupply(reqVO);
    }
    @PostMapping("/unLock/returnSupply")
    @ApiOperation(value = "审核失败解锁库存")
    public HttpResponse<Boolean> unLockReturnSupply(@RequestBody VerifyReturnSupplyReqVo reqVO) {
        return HttpResponse.success(stockService.returnSupplyUnLockStock(reqVO));
    }
    @PostMapping("/unLock")
    @ApiOperation(value = "库存解锁")
    public HttpResponse unLockStock(@RequestBody UnLockStockReqVo reqVo) {
        return stockService.unLockStock(reqVo);
    }

    @PostMapping("/reduceUnlock")
    @ApiOperation(value = "减少并解锁库存")
    public HttpResponse reduceUnlockStock(@RequestBody UpdateOutBoundReqVO reqVo){
        return stockService.reduceUnlockStock(reqVo);
    }


    @PostMapping("/search/merchant")
    @ApiOperation(value = "门店库存查询")
    public HttpResponse<List<QueryMerchantStockRepVo>> queryMerchantStock(@RequestBody QueryMerchantStockReqVo reqVo){
        List<QueryMerchantStockRepVo> queryMerchantStockRepVos = stockService.selectStockByCompanyCodeAndSkuList(reqVo);
        return HttpResponse.success(queryMerchantStockRepVos);
    }

    //因为目前对接中心没介入,所以直接接收采购单
    @ApiOperation(value = "采购单审批通过,接收采购单生成入库单")
    @PostMapping("/inbound/save")
    public HttpResponse<InboundReqVo> save(@RequestBody InboundReqVo reqVo){
        return HttpResponse.success(stockService.save(reqVo));
    }

    @PostMapping("/lock/merchant")
    @ApiOperation(value = "门店库存锁定")
    public HttpResponse<List<MerchantLockStockRespVo>> lockMerchantStock(@RequestBody MerchantLockStockReqVo reqVo) {
        List<MerchantLockStockRespVo> queryMerchantStockRepVos = stockService.lockMerchantStock(reqVo);
        return HttpResponse.success(queryMerchantStockRepVos);
    }
    //因为目前对接中心没介入,所以直接接收采购单 这里传之前锁定生成的出库单号
    @ApiOperation(value = "退供供应商确认后,出库单")
    @GetMapping("/outBound/save")
    public HttpResponse<Boolean> save(@RequestParam String outBoundCode){
        UpdateOutBoundReqVO updateOutBoundReqVO = new UpdateOutBoundReqVO();
        updateOutBoundReqVO.setSourceOrderCode(outBoundCode);
        return HttpResponse.success(stockService.reduceUnlockStock(updateOutBoundReqVO));
    }
    @PostMapping("/lock/flow")
    @ApiOperation(value = "解锁库存并加流水")
    public HttpResponse lockFlow(@RequestBody StockFlowRequest reqVo){
        return HttpResponse.success(stockService.stockFlow(reqVo));
    }
    @PostMapping("change")
    @ApiOperation(value = "库存修改")
    public HttpResponse changeStock(@RequestBody StockChangeRequest stockChangeRequest) throws Exception {
        return stockService.changeStock(stockChangeRequest);
    }
    @PostMapping("/logs")
    public HttpResponse logs(@RequestBody StockLogsRequest stockLogsRequest){
        return stockService.logs(stockLogsRequest);
    }



}
