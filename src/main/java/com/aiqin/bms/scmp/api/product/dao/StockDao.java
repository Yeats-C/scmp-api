package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.response.StockResponse;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.SkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.QueryStockSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.BatchInfo;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.PriceChannelForChangePrice;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseStockResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockDao {

    List<StockRespVO> selectTransportStockInfoByPage(StockRequest stockRequest);

    Integer selectTransportStockInfoByPageCount(StockRequest stockRequest);

    List<StockRespVO> selectStorehouseStockInfoByPage(StockRequest stockRequest);

    StockRespVO stockWarehouseInfo(String stockCode);

    Integer countStorehouseStockInfoByPage(StockRequest stockRequest);

    Integer selectWarehouseType(@Param("warehouseCode") String warehouseCode);

    /** 查询商品总库存*/
    List<StockRespVO> selectStockSumInfoByPage(StockRequest stockRequest);

    Integer selectStockSumInfoByPageCount(StockRequest stockRequest);

    List<StockFlowRespVo> selectOneStockInfoByStockId(@Param("stockCode") String stockCode);

    /** 功能描述: 查询库存商品(采购退供使用) list*/
    List<QueryStockSkuRespVo> selectStockSkuInfoByPage(QueryStockSkuReqVo vo);

    Integer insertBatch(@Param("list") List<Stock> stocks);

    Integer updateByPrimaryKey(Stock stock);

    List<Stock> stockByWarehouseAndSku(@Param("stockList") List<StockInfoRequest> stockList);

    void updateBatch(@Param("stocks") List<Stock> stocks);

    List<Stock> getLatestPurchasePriceBySkuCodes(@Param("list") List<String> list);

    List<StockFlow> selectStockLogs(StockLogsRequest stockLogsRequest);

    List<Stock> selectGroup();

    List<Stock> selectListByWareHouseCode(Stock stock);

    RejectApplyDetailHandleResponse rejectProductInfo(@Param("supplierCode") String supplierCode, @Param("productType") Integer productType, @Param("purchaseGroupCode") String procurementSectionCode, @Param("transportCenterCode") String transportCenterCode, @Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);

    /** 库房管理新增调拨,移库,报废列表查询*/
    List<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO);

    List<QueryStockSkuListRespVo> selectSkuCodeByQueryBatchCodeList(@Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);

    void updateStorehouseById(@Param("list") List<StockRespVO> stockRespVO);

    Long selectSkuCodeByQueryAvailableSum(@Param("skuCode") String skuCode);

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

    /**销售库存量*/
    Long selectSkuCodeBySaleSum(@Param("skuCode") String skuCode, @Param("companyCode") String companyCode);

    /** 销售特卖库存量*/
    Long selectSkuCodeBySpecialSum(@Param("skuCode") String skuCode, @Param("companyCode") String companyCode);

    /**销售可用*/
    Long selectSkuAndCompanyByQueryAvailableSum(@Param("skuCode")String skuCode,@Param("companyCode") String companyCode);

    /** 查询可以使用sku 以及库存数量*/
    List<String> byCityCodeAndprovinceCode(@Param("provinceCode") String provinceCode, @Param("cityCode") String cityCode, @Param("tagCode") String tagCode,
                                           @Param("exitStock") String exitStock,@Param("orderByType") String orderByType,@Param("companyCode") String companyCode);
    /** 查询可以使用 sku以及仓库*/
    StockBatchRespVO byCityAndProvinceAndskuCode(@Param("skuCode") String skuCode,
                                                 @Param("provinceCode")  String provinceCode,
                                                 @Param("cityCode") String cityCode);

    /** 查询可以使用 sku以及仓库*/
    StockBatchRespVO byCityAndProvinceAndtransportCenterCode(@Param("transportCenterCode") String transportCenterCode,
                                                             @Param("provinceCode")  String provinceCode,
                                                             @Param("cityCode") String cityCode);

    SkuConfigsRepsVo findSpareWarehouse(StockBatchRespVO stockBatchRespVO);

    StockBatchDetailResponse stockInfoByBatchDetail( @Param("skuCode") String skuCode,
                                               @Param("warehouseCode") String warehouseCode);

    Integer stockCountByReject(@Param("skuCode") String skuCode, @Param("warehouseCode") String warehouseCode);

    Stock centerStock(@Param("skuCode")String skuCode, @Param("warehouseType") Integer warehouseType);

}