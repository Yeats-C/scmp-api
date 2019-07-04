package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.response.QueryStockBatchSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.LockOrderItemBatchReqVO;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.MerchantLockStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.QueryMerchantStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.PurchaseOutBoundRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.MerchantLockStockRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.QueryMerchantStockRepVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author knight.xie
 * @className StockService
 * @date 2019/1/4 16:08
 * @description 库存
 * @version 1.0
 */
public interface StockService {


    /**
     *
     * 功能描述: =查询库存商品(采购退供使用) 分页
     *
     * @param reqVO
     * @return PageInfo
     * @auther knight.xie
     * @date 2019/1/4 17:23
     */
    PageInfo<QueryStockSkuRespVo> selectStockSkuPage(QueryStockSkuReqVo reqVO);


    /**
     *
     * 功能描述: 查询库存商品(采购退供使用) 不分页
     *
     * @param reqVO
     * @return List
     * @auther knight.xie
     * @date 2019/1/7 17:38
     */
    List<QueryStockSkuRespVo> selectStockSkus(QueryStockSkuReqVo reqVO);

    /**
     * 根据搜索条件查询库房库存stock信息
     *
     * @param stockRequest
     * @return
     */
    PageResData selectWarehouseStockInfoByPage(StockRequest stockRequest);

    /**
     * 根据搜索条件查询物流中心stock信息
     *
     * @param stockRequest
     * @return
     */
    PageResData selectTransportStockInfoByPage(StockRequest stockRequest);

    /**
     * 库房库存
     * @param stockRequest
     * @return
     */
    PageResData selectStorehouseStockInfoByPage(StockRequest stockRequest);

    List<QueryStockSkuRespVo> selectStockSku(QueryStockSkuReqVo reqVO);

    /**
     * 根据搜索条件查询stock信息
     *
     * @param stockRequest
     * @return
     */
    PageResData selectStockSumInfoByPage(StockRequest stockRequest);

    /**
     * 根据stockid查询单个库存信息
     *
     * @param stockId
     * @return
     */
    PageInfo<StockRespVO> selectOneStockInfoByStockId(Long stockId,Integer page_no,Integer page_size);


    /**
     *
     * 功能描述: 验证退供商品信息,有错误则会返回list,否则list为空
     *
     * @param reqVo
     * @return HttpResponse<List<VerifyReturnSupplyErrorRespVo>>
     * @auther knight.xie
     * @date 2019/1/8 15:14
     */
    HttpResponse<PurchaseOutBoundRespVO> verifyReturnSupply(VerifyReturnSupplyReqVo reqVo);

    /**
     * 库存锁定
     * @param reqVO
     */
    PurchaseOutBoundRespVO lockStock(ILockStockReqVO reqVO);

    /**
     * 退供锁定库存
     * @author zth
     * @date 2019/3/26
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean returnSupplyLockStock(ILockStockReqVO reqVO);
    /**
     * 退供解锁库存
     * @author zth
     * @date 2019/3/26
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean returnSupplyUnLockStock(ILockStockReqVO reqVO);

    /**
     * 解锁库存
     * @param reqVo 原始订单号
     * @return
     */
    HttpResponse unLockStock(UnLockStockReqVo reqVo);

    /**
     * 减少并解锁库存
     * @param reqVo
     * @return
     */
    HttpResponse reduceUnlockStock(UpdateOutBoundReqVO reqVo);

    /**
     * 根据公司编码和sku集合查询商品库存
     * @param reqVo
     * @return
     */
    List<QueryMerchantStockRepVo> selectStockByCompanyCodeAndSkuList(QueryMerchantStockReqVo reqVo);

    /**
     * 门店库存锁定
     * @param vo
     * @return
     */
    List<MerchantLockStockRespVo> lockMerchantStock(MerchantLockStockReqVo vo);
    /**
     * 通过条件查询库存数
     * @author zth
     * @date 2019/3/16
     * @param centerCodes 物流中心编码集合
     * @param warehouseCodes 库房编码集合
     * @param skuCodes sku 编码集合
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.Stock> 库存集合
     */
    List<Stock> selectByQueryList(List<String> centerCodes, List<String> warehouseCodes, List<String> skuCodes, String companyCode);

    /**
     * 接受采购单,保存或更新库存,保存入库流水
     * @param reqVo
     * @return
     */
    InboundReqVo save(InboundReqVo reqVo);

    Boolean purchaseSaveStockInfo(InboundReqVo reqVo) throws Exception;

    /**
     * 保存库存
     * @param reqVo
     * @return
     */
    Integer saveStock(InboundReqVo reqVo);

    String stockFlow(StockFlowRequest reqVo);

    HttpResponse changeStock(StockChangeRequest stockChangeRequest);

    boolean changeWayNum(StockWayNumRequest stockWayNumRequest) throws Exception;

    Map<String,Long>getLatestPurchasePriceBySkuCodes(@Param("skuCodes") List<String> skuCodes);


    HttpResponse logs(StockLogsRequest stockLogsRequest);

    /**
     * 
     * @return
     */
    List<Stock> selectGroup();

    List<Stock> selectListByWareHouseCode(Stock stock);
    /**
     * TODO 订单锁库的方法
     * @author NullPointException
     * @date 2019/6/21
     * @param vo
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch>
     */
    List<OrderInfoItemProductBatch> lockBatchStock(List<LockOrderItemBatchReqVO> vo);

    /**
     * 批次库存
     * @param stockBatchRequest
     * @return
     */
    PageResData selectStockBatchInfoByPage(StockBatchRequest stockBatchRequest);

    /**
     * 根据stockBatchId查询单个stockBatch信息
     *
     * @param stockBatchId
     * @return
     */
    PageInfo<StockBatchRespVO> selectOneStockBatchInfoByStockBatchId(Long stockBatchId,Integer page_no,Integer page_size);

    HttpResponse operationStockBatch(StockChangeRequest stockChangeRequest);

    /**
     *
     * 功能描述: 批次查询库存商品(采购退供使用) 分页
     *
     * @param reqVO
     * @return PageInfo
     * @date 2019/6/25 16:06
     */
    PageInfo<QueryStockBatchSkuRespVo> selectStockBatchSkuPage(QueryStockBatchSkuReqVo reqVO);

    /**
     * 库房管理新增调拨,移库,报废列表查询
     * @param reqVO
     * @return
     */
    PageInfo<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO);

    HttpResponse updateStorehouseById(List<StockRespVO> stockRespVO);

    /**
     * 退供锁定批次库存
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean returnSupplyLockStockBatch(ILockStockBatchReqVO reqVO);
    /**
     * 退供解锁批次库存
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean returnSupplyUnLockStockBatch(ILockStockBatchReqVO reqVO);


}
