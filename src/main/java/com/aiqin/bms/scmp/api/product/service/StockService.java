package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.SkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.MerchantLockStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.response.PurchaseOutBoundRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockBatchSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockFlowRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.LockOrderItemBatchReqVO;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author knight.xie
 * @date 2019/1/4 16:08
 * @description 库存
 */
public interface StockService {

    /** 功能描述: =查询库存商品(采购退供使用) 分页*/
    PageInfo<QueryStockSkuRespVo> selectStockSkuPage(QueryStockSkuReqVo reqVO);

    /** 功能描述: 查询库存商品(采购退供使用) 不分页*/
    List<QueryStockSkuRespVo> selectStockSkus(QueryStockSkuReqVo reqVO);

    /** 根据搜索条件查询库房库存stock信息 */
    PageResData selectWarehouseStockInfoByPage(StockRequest stockRequest);

    /** 根据搜索条件查询物流中心stock信息*/
    PageResData selectTransportStockInfoByPage(StockRequest stockRequest);

    /** 库房库存*/
    PageResData selectStorehouseStockInfoByPage(StockRequest stockRequest);

    List<QueryStockSkuRespVo> selectStockSku(QueryStockSkuReqVo reqVO);

    /** 根据搜索条件查询stock信息*/
    PageResData selectStockSumInfoByPage(StockRequest stockRequest);

    /** 根据stockid查询单个库存信息*/
    PageInfo<StockFlowRespVo> selectOneStockInfoByStockId(String stockCode, Integer page_no, Integer page_size);

    /** 功能描述: 验证退供商品信息,有错误则会返回list,否则list为空*/
    HttpResponse<PurchaseOutBoundRespVO> verifyReturnSupply(VerifyReturnSupplyReqVo reqVo);

    /** 库存锁定*/
    PurchaseOutBoundRespVO lockStock(ILockStockReqVO reqVO);

    /** 退供锁定库存*/
    Boolean returnSupplyLockStock(ILockStockReqVO reqVO);

    Boolean returnSupplyLockStocks(ILockStocksReqVO reqVO);

    /**退供解锁库存*/
    Boolean returnSupplyUnLockStock(ILockStockReqVO reqVO);

    Boolean returnSupplyUnLockStocks(ILockStocksReqVO reqVO);

    /** 解锁库存*/
    HttpResponse unLockStock(UnLockStockReqVo reqVo);

    /** 减少并解锁库存*/
    HttpResponse reduceUnlockStock(UpdateOutBoundReqVO reqVo);

    /** 接受采购单,保存或更新库存,保存入库流水*/
    InboundReqVo save(InboundReqVo reqVo);

    Boolean purchaseSaveStockInfo(InboundReqVo reqVo) throws Exception;

    String stockFlow(StockFlowRequest reqVo);

    HttpResponse changeStock(StockChangeRequest stockChangeRequest);

    HttpResponse stockAndBatchChange(ChangeStockRequest request);

    Map<String,Long>getLatestPurchasePriceBySkuCodes(@Param("skuCodes") List<String> skuCodes);

    HttpResponse logs(StockLogsRequest stockLogsRequest);

    List<Stock> selectGroup();

    List<Stock> selectListByWareHouseCode(Stock stock);

    /** 订单锁库的方法*/
    List<OrderInfoItemProductBatch> lockBatchStock(List<LockOrderItemBatchReqVO> vo);

    /** 批次库存*/
    PageResData selectStockBatchInfoByPage(StockBatchRequest stockBatchRequest);

    /** 根据stockBatchId查询单个stockBatch信息*/
    PageInfo<StockBatchRespVO> selectOneStockBatchInfoByStockBatchId(Long stockBatchId,Integer page_no,Integer page_size);

    HttpResponse operationStockBatch(StockChangeRequest stockChangeRequest);

    /** 功能描述: 批次查询库存商品(采购退供使用) 分页*/
    PageInfo<QueryStockBatchSkuRespVo> selectStockBatchSkuPage(QueryStockBatchSkuReqVo reqVO);

    /** 库房管理新增调拨,移库,报废列表查询*/
    PageInfo<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO);

    HttpResponse updateStorehouseById(List<StockRespVO> stockRespVO);

    /** 退供锁定批次库存*/
    Boolean returnSupplyLockStockBatch(ILockStockBatchReqVO reqVO);

    HttpResponse changeStockBatch(StockChangeRequest stockChangeRequest);

    PageInfo<QueryStockSkuListRespVo> importStockSkuList(QueryImportStockSkuListReqVo reqVO);

    BasePage<QuerySkuInfoRespVO> querySkuBatchList(QuerySkuInfoReqVO reqVO);

    List<SkuBatchRespVO> querySkuBatchList(SkuBatchReqVO reqVO);

    List<Stock> selectSkuCost();

    HttpResponse lockErpStock(MerchantLockStockReqVo vo);

    String byCityCodeAndprovinceCode(String provinceCode, String cityCode, String tagCode, String exitStock, String orderByType);

    List<StockBatchRespVO>  byCityAndProvinceAndskuCode(String skuCode, String provinceCode, String cityCode);
}
