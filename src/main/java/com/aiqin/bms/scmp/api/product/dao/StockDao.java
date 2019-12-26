package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.response.StockResponse;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatchFlow;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.SkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.QueryMerchantStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockBatchSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.BatchInfo;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.PriceChannelForChangePrice;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.QueryMerchantStockRepVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchProductSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockFlowRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseStockResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyDetailHandleResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockDao {

    List<StockRespVO> selectWarehouseStockInfoByPage(StockRequest stockRequest);

    Integer countWarehouseStockInfoByPage(StockRequest stockRequest);

    List<StockRespVO> selectTransportStockInfoByPage(StockRequest stockRequest);

    Integer selectTransportStockInfoByPageCount(StockRequest stockRequest);

    List<StockRespVO> countTransportStockInfoByPage(StockRequest stockRequest);

    List<StockRespVO> selectStorehouseStockInfoByPage(StockRequest stockRequest);

    Integer countStorehouseStockInfoByPage(StockRequest stockRequest);

    // 通过库房code查询库存类型name
    StockVoRequest selectWarehouseTypeBycode(@Param("warehouseCode") String warehouseCode);

    /**
     * 查询商品总库存
     *
     * @author gary.diao
     * @date 2019/1/7
     **/
    List<StockRespVO> selectStockSumInfoByPage(StockRequest stockRequest);

    Integer selectStockSumInfoByPageCount(StockRequest stockRequest);

    List<StockRespVO> countStockSumInfoByPage(StockRequest stockRequest);

    List<StockFlowRespVo> selectOneStockInfoByStockId(@Param("stockCode") String stockCode);

    Long selectOneStockInfoByStockIdInfoByPage(Long stockId);

    /**
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
     *
     * @param vos
     * @return
     */
    Integer updateBatchStock(List<UpdateStockReqVo> vos);

    /**
     * 根据公司编码和sku集合查询商品库存
     *
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
     *
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

    Integer updateStockBatch(@Param("list") List<StockBatch> stockBatches);

    Integer insertStockBatchFlow(@Param("list") List<StockBatchFlow> flows);

    /**
     * 根据stockBatchId查询单个库存信息
     *
     * @param stockBatchId
     * @return
     */
    List<StockBatchRespVO> selectOneStockBatchInfoByStockBatchId(Long stockBatchId);

    Long selectOneStockBatchInfoByStockBatchIdInfoByPage(Long stockBatchId);

    /**
     * 功能描述: 批次查询库存商品(采购退供使用) list
     *
     * @param vo
     * @return List<QueryStockBatchSkuRespVo>
     * @date 2019/6/26 16:10
     */
    List<QueryStockBatchSkuRespVo> selectStockBatchSkuInfoByPage(QueryStockBatchSkuReqVo vo);

    QueryStockBatchSkuRespVo selectSkuBatchCode(@Param("procurementSectionCode") String procurementSectionCode, @Param("transportCenterCode") String transportCenterCode, @Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode, @Param("batchCode") String batchCode);

    RejectApplyDetailHandleResponse rejectProductInfo(@Param("supplierCode") String supplierCode, @Param("productType") Integer productType, @Param("purchaseGroupCode") String procurementSectionCode, @Param("transportCenterCode") String transportCenterCode, @Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);

    /**
     * 库房管理新增调拨,移库,报废列表查询
     *
     * @param reqVO
     * @return
     */
    List<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO);

    List<QueryStockSkuListRespVo> selectSkuCodeByQueryBatchCodeList(@Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);

    void updateStorehouseById(@Param("list") List<StockRespVO> stockRespVO);

    List<StockBatch> selectListByCodesAndSkuCodeBatch(@Param("stockBatchVoRequest") List<StockBatchVoRequest> stockBatchVoRequest);

    void insertStockBatchFlows(@Param("stockBatchFlows") List<StockBatchFlow> stockBatchFlows);

    void updateBatchStocks(@Param("stockBatches") List<StockBatch> stockBatches);

    Integer insertBatchStockAdd(@Param("stockBatches") List<StockBatch> stockBatches);

    List<String> importStockSkuList();

    List<String> selectSkuCodeByQueryBatchCode(@Param("skuCode") String skuCode);

    Long selectSkuCodeByQueryAvailableNum(@Param("skuCode") String skuCode);

    Long selectSkuCodeByQueryAvailableSum(@Param("skuCode") String skuCode);

    List<String> selectSkuCodeByQueryProductionDateList(@Param("skuCode") String skuCode);

    List<QueryStockSkuListRespVo> queryStockBatch(QueryImportStockSkuListReqVo reqVO);

    List<QuerySkuInfoRespVO> getSkuBatchForChangePrice(QuerySkuInfoReqVO reqVO);

    List<BatchInfo> getBatch();

    List<PriceChannelForChangePrice> getSaleChannelList();

    List<SkuBatchRespVO> queryStockBatchForAllo(SkuBatchReqVO reqVO);

    List<RejectApplyDetailHandleResponse> rejectProductList(RejectProductRequest rejectQueryRequest);

    Integer rejectProductListCount(RejectProductRequest rejectQueryRequest);

    PurchaseStockResponse stockCountByOtherInfo(@Param("skuCode") String skuCode,
                                                @Param("transportCenterCode") String transportCenterCode,
                                                @Param("warehouseCode") String warehouseCode);

    List<Stock> selectSkuCost();

    Stock selectStockSum(Stock stock);

    List<StockResponse> listBySkuCodes(List<String> list);

    Integer insertReplaceAll(List<Stock> stockList);

    Stock stockInfo(Stock stock);

    List<Stock> stockInfoList(@Param("skuList") List<String> skuList);

    /**
     * 销售库存量
     *
     * @param
     * @param
     * @return
     */
    Long selectSkuCodeBySaleSum(@Param("skuCode") String skuCode, @Param("companyCode") String companyCode);
    /**
     * 销售特卖库存量
     *
     * @param
     * @param
     * @return
     */
    Long selectSkuCodeBySpecialSum(@Param("skuCode") String skuCode, @Param("companyCode") String companyCode);
    /**
     * 销售可用
     * @param
     * @return
     */
    Long selectSkuAndCompanyByQueryAvailableSum(@Param("skuCode")String skuCode,@Param("companyCode") String companyCode);

    /**
     * 查询可以使用sku 以及库存数量
     *
     * @param provinceCode
     * @param cityCode
     * @param orderByType
     * @return
     */
    List<String> byCityCodeAndprovinceCode(@Param("provinceCode") String provinceCode, @Param("cityCode") String cityCode, @Param("tagCode") String tagCode,
                                           @Param("exitStock") String exitStock,@Param("orderByType") String orderByType,@Param("companyCode") String companyCode);
    /**
     * 查询可以使用 sku以及仓库
     * @param skuCode
     * @param provinceCode
     * @param cityCode
     * @return
     */
    StockBatchRespVO byCityAndProvinceAndskuCode(@Param("skuCode") String skuCode,
                                                 @Param("provinceCode")  String provinceCode,
                                                 @Param("cityCode") String cityCode);

    /**
     * 查询可以使用 sku以及仓库
     * @param provinceCode
     * @param cityCode
     * @return
     */
    StockBatchRespVO byCityAndProvinceAndtransportCenterCode(@Param("transportCenterCode") String transportCenterCode,
                                                             @Param("provinceCode")  String provinceCode,
                                                             @Param("cityCode") String cityCode);

    SkuConfigsRepsVo findSpareWarehouse(StockBatchRespVO stockBatchRespVO);

    StockBatchRespVO byCityAndProvinceAndskuCode(String provinceCode, String cityCode);


}