package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.SkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
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

import java.util.List;

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

    PageResData<StockRespVO> selectTransportStockInfoByPage(StockRequest stockRequest);

    PageResData<StockRespVO> selectStorehouseStockInfoByPage(StockRequest stockRequest);

    List<QueryStockSkuRespVo> selectStockSku(QueryStockSkuReqVo reqVO);

    /** 根据搜索条件查询stock信息*/
    PageResData selectStockSumInfoByPage(StockRequest stockRequest);

    HttpResponse<StockRespVO> stockWarehouseInfo(String stockCode);

    PageResData<StockFlow> selectStockFlow(StockLogsRequest request);

    Boolean returnSupplyLockStocks(ILockStocksReqVO reqVO);

    /** 接受采购单,保存或更新库存,保存入库流水*/
    InboundReqVo save(InboundReqVo reqVo);

    Boolean purchaseSaveStockInfo(InboundReqVo reqVo) throws Exception;

    HttpResponse changeStock(StockChangeRequest stockChangeRequest);

    HttpResponse stockAndBatchChange(ChangeStockRequest request);

    HttpResponse logs(StockLogsRequest stockLogsRequest);

    List<Stock> selectGroup();

    List<Stock> selectListByWareHouseCode(Stock stock);

    /** 订单锁库的方法*/
    List<OrderInfoItemProductBatch> lockBatchStock(List<LockOrderItemBatchReqVO> vo);

    /** 库房管理新增调拨,移库,报废列表查询*/
    PageInfo<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO);

    HttpResponse updateStorehouseById(List<StockRespVO> stockRespVO);

    /** 退供锁定批次库存*/
    Boolean returnSupplyLockStockBatch(ILockStockBatchReqVO reqVO);

    HttpResponse changeStockBatch(ChangeStockRequest request);

    PageInfo<QueryStockSkuListRespVo> importStockSkuList(QueryImportStockSkuListReqVo reqVO);

    BasePage<QuerySkuInfoRespVO> querySkuBatchList(QuerySkuInfoReqVO reqVO);

    List<SkuBatchRespVO> querySkuBatchList(SkuBatchReqVO reqVO);

    List<Stock> selectSkuCost();

    String byCityCodeAndprovinceCode(String provinceCode, String cityCode, String tagCode, String exitStock, String orderByType);

    List<StockBatchRespVO>  byCityAndProvinceAndskuCode(String skuCode, String provinceCode, String cityCode);
}
