package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.QueryMerchantStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.QueryMerchantStockRepVo;
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
     * 批次库存列表查询
     * @return
     */
    List<StockBatchRespVO> selectStockBatchAllData();

    Integer countStockBatchAllData();

    /**
     * 批次库存条件查询
     * @param stockBatchRequest
     * @return
     */
    List<StockBatchRespVO> selectStockBatchInfoByPage(StockBatchRequest stockBatchRequest);

    Integer countStockBatchInfoByPage(StockBatchRequest stockBatchRequest);

    /**
     * 根据stockBatchId查询单个库存信息
     *
     * @param stockBatchId
     * @return
     */
    StockBatchRespVO selectOneStockBatchInfoByStockBatchId(Long stockBatchId);

    List<PurchaseApplyDetailResponse> purchaseProductList(PurchaseApplyRequest purchases);

    Integer purchaseProductCount(PurchaseApplyRequest purchases);
}