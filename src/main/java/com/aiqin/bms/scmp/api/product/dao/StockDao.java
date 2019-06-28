package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.QueryMerchantStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockBatchSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.QueryMerchantStockRepVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchProductSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockDao {

    List<StockRespVO> selectWarehouseStockInfoByPage(StockRequest stockRequest);

    Integer countWarehouseStockInfoByPage(StockRequest stockRequest);

    List<StockRespVO> selectTransportStockInfoByPage(StockRequest stockRequest);

    Integer countTransportStockInfoByPage(StockRequest stockRequest);

    List<StockRespVO> selectStorehouseStockInfoByPage(StockRequest stockRequest);

    Integer countStorehouseStockInfoByPage(StockRequest stockRequest);

    /**
     * 查询商品总库存
     * @author gary.diao
     * @date 2019/1/7
     *
     **/
    List<StockRespVO> selectStockSumInfoByPage(StockRequest stockRequest);

    Integer countStockSumInfoByPage(StockRequest stockRequest);

    StockRespVO selectOneStockInfoByStockId(Long stockId);


    /**
     *
     * 功能描述: 查询库存商品(采购退供使用) list
     *
     * @param vo
     * @return List<QueryStockSkuRespVo>
     * @auther knight.xie
     * @date 2019/1/7 9:36
     */
    List<QueryStockSkuRespVo> selectStockSkuInfoByPage(QueryStockSkuReqVo vo);

    /**
     * 批量更新库存
     * @param vos
     * @return
     */
    Integer updateBatchStock(List<UpdateStockReqVo> vos);

    /**
     * 根据公司编码和sku集合查询商品库存
     * @param reqVo
     * @return
     */
    List<QueryMerchantStockRepVo> selectStockByCompanyCodeAndSkuList(QueryMerchantStockReqVo reqVo);

    Integer insertBatch(@Param("list") List<Stock> stocks);

    Integer updateByPrimaryKey(Stock stock);

    List<Stock> selectListByCodesAndSkuCode(@Param("stockVoRequests") List<StockVoRequest> stockVoRequests);

    void updateBatch(@Param("stocks") List<Stock> stocks);

    List<Stock> getLatestPurchasePriceBySkuCodes(@Param("list") List<String> list);

    List<Stock> selectByQueryList(@Param("centerCodes") List<String> centerCodes, @Param("warehouseCodes") List<String> warehouseCodes, @Param("skuCodes") List<String> skuCodes, @Param("companyCode") String companyCode);

    List<StockFlow> selectStockLogs(StockLogsRequest stockLogsRequest);

    List<Stock> selectGroup();

    List<Stock> selectListByWareHouseCode(Stock stock);

    /**
     * 批次库存管理查询
     * @param stockBatchRequest
     * @return
     */
    List<StockBatchRespVO> selectStockBatchInfoByPage(StockBatchRequest stockBatchRequest);

    Integer countStockBatchInfoByPage(StockBatchRequest stockBatchRequest);

    /**
     * 批量插入数据
     */
    List<StockBatchRespVO> selectStockBatchDistinct();
    List<StockBatchProductSkuRespVO> selectProductSku();
    Integer insertStockBatch(@Param("list") List<StockBatch> stockBatches);

    /**
     * 根据stockBatchId查询单个库存信息
     *
     * @param stockBatchId
     * @return
     */
    StockBatchRespVO selectOneStockBatchInfoByStockBatchId(Long stockBatchId);

    List<PurchaseApplyDetailResponse> purchaseProductList(PurchaseApplyRequest purchases);

    Integer purchaseProductCount(PurchaseApplyRequest purchases);

    /**
     *
     * 功能描述: 批次查询库存商品(采购退供使用) list
     *
     * @param vo
     * @return List<QueryStockBatchSkuRespVo>
     * @date 2019/6/26 16:10
     */
    List<QueryStockBatchSkuRespVo> selectStockBatchSkuInfoByPage(QueryStockBatchSkuReqVo vo);

    List<QueryStockBatchSkuRespVo> selectSkuBatchCodeList();

    /**
     * 库房管理新增调拨,移库,报废列表查询
     * @param reqVO
     * @return
     */
    List<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO);


}