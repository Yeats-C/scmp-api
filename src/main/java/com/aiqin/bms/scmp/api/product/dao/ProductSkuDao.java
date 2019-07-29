package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.SkuWarehouseStockNum;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.QueryProductSaleAreaForSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ocenter.QueryCenterSkuListReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.OmsProductSkuListReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.QuerySkuListPageReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOmsSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOrderReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.CheckPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.QueryPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryMerchantSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryStoreProductListReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryStoreSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryStoreSkusReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.PriceChannelForChangePrice;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.ProductSaleAreaSupplierInfo;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.merchant.MerchantSkuItemRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ocenter.QueryCenterSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.SupervisoryWarehouseSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.*;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterApiResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuDao {

    ProductSkuRespVo getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ApplyProductSku> getSkuApplyList(String applyCode);
    /**
     *
     * @param querySkuListReqVO
     * @return根据查询条件查询商品SKU列表
     */
    List<ProductSkuInfo> getProductSkuInfoList(QuerySkuListReqVO querySkuListReqVO);

    /**
     * 采购获取sku分页列表
     * @param queryPurchaseSkuReqVO
     * @return
     */
    List<PurchaseItemRespVo> getPurchaseSkuList(QueryPurchaseSkuReqVO queryPurchaseSkuReqVO);

    /**
     * 根据sku集合查询sku信息
     * @param queryMerchantSkuListReqVO
     * @return
     */
    List<MerchantSkuItemRespVO> getMerchantSkuListByCodes(QueryMerchantSkuListReqVO queryMerchantSkuListReqVO);

    /**
     * 根据商品编码及颜色等查询sku信息
     * @param queryStoreSkuListReqVO
     * @return
     */
    List<StoreSkuItemRespVO> getSkuListByProductCode(QueryStoreSkuListReqVO queryStoreSkuListReqVO);

    /**
     * 根据SKU编码查询基本信息
     * @param skuCode
     * @return
     */
    StoreSkuBaseInfo getStoreSkuBaseByCode(String skuCode);

    /**
     * 根据sku获取默认厂商
     * @param skuCode
     * @return
     */
    StoreSkuFactoryInfo getDefaultSkuFactory(String skuCode);

    /**
     * 根据sku编码获取默认销售信息
     * @param skuCode
     * @return
     */
    StoreSkuSalesInfo getDefaultSkuSales(String skuCode);

    /**
     * 根据sku编码获取图片列表信息
     * @param skuCode
     * @return
     */
    List<StoreSkuPicInfo> getSkuPicList(String skuCode);

    /**
     * 根据库存集合和sku集合查询仓库下sku库存量
     * @param skuWarehouseStockNum
     * @return
     */
    List<SkuWarehouseStockNum> getSkuWarehouseStockNum(SkuWarehouseStockNum skuWarehouseStockNum);

    /**
     * 根据门店条件查询商品列表
     * @param queryStoreProductListReqDTO
     * @return
     */
    List<ProductItemStoreRespVO> getProductItemList(QueryStoreProductListReqDTO queryStoreProductListReqDTO);

    /**
     * 根据sku集合等检查sku列表
     * @param checkPurchaseSkuReqVO
     * @return
     */
    List<PurchaseItemRespVo> getCheckSkuList(CheckPurchaseSkuReqVO checkPurchaseSkuReqVO);

    /**
     * 根据sku集合查询sku基本信息列表
     * @param skuCodeList
     * @return
     */
    List<PurchaseItemRespVo> getSalesSkuList(List<String> skuCodeList);

    ProductSkuRespVo getSkuDraft(String skuCode);

    ProductSkuRespVo getSkuInfoResp(String skuCode);

    ProductSkuInfo getSkuInfo(String skuCode);

    /**
     * sku管理查询
     * @param querySkuListReqVO
     * @return
     */
    List<QueryProductSkuListResp> querySkuList(QuerySkuListReqVO querySkuListReqVO);

    /**
     * sku管理查询通过供应商
     * @param supplyUnitCode
     * @return
     */
    List<QueryProductSkuListResp> querySkuListBySupplyUnitCode(String supplyUnitCode);

    /**
     * 查询可选择的商品草稿列表
     * @return
     */
    List<ProductDraftListResp> getProductDraftList();

    /**
     * 查询可选择的sku草稿列表
     * @return
     */
    List<ProductSkuDraft> getProductSkuDraftList();

    /**
     * 根据sku集合查询草稿
     * @param skuCodes
     * @return
     */
    List<ProductSkuDraft> getSkuDraftByCodes(List<String> skuCodes);

    /**
     * 根据商品集合查询草稿
     * @param productCodes
     * @return
     */
    List<ProductSkuDraft> getSkuDraftByProductCodes(List<String> productCodes);

    /**
     * 根据sku集合删除草稿
     * @param skuCodes
     * @return
     */
    int deleteSkuDraftByCodes(List<String> skuCodes);

    /**
     * 根据商品集合删除草稿
     * @param productCodes
     * @return
     */
    int deleteSkuDraftByProductCodes(List<String> productCodes);

    /**
     * 批量新增sku申请信息
     * @param applyProductSkus
     * @return
     */
    int insertApplySkuList(@Param("applyProductSkus") List<ApplyProductSku> applyProductSkus);

    List<ApplyDetailProductListResp> getApplyProductList(String applyCode);

    List<ApplyDetailSkuListResp> getApplySkuList(String applyCode);

    List<QueryProductSkuListResp> queryOmsSkuList(SearchOmsSkuListReqVO searchOmsSkuListReqVO);

    List<StoreSkuItemRespVO> getStoreSkuListByCodes(QueryStoreSkusReqVO queryStoreSkusReqVO);

    List<OmsProductListItemResp> getOmsProductList(SearchOrderReqVO searchOrderReqVO);

    List<OmsSkuTagResp> getOmsSkuTagList(List<OmsProductListItemResp> list);

    List<OmsProductSkuItemResp> getOmsProductSkuList(OmsProductSkuListReq omsProductSkuListReq);

    List<OmsSkuBrandResp> getOmsProductBrandList(List<OmsProductListItemResp> list);

    List<OmsSkuCategoryResp> getOmsProductCategoryList(List<OmsProductListItemResp> list);

    List<QuerySkuListResp> getOmsSkus(QueryStoreSkusReqVO queryStoreSkusReqVO);

    List<QueryOmsSkusPageResp> queryOmsSkuPage(QuerySkuListPageReq querySkuListPageReq);

    /**
     * 根据物流中心查询配置的skucode集合
     * @param list
     * @return
     */
    List<String> getSkuCodesByCenter(List<LogisticsCenterApiResVo> list);

    List<ApplyProductSku> getApplySkuByFormNo(String formNo);


    List<QueryCenterSkuListRespVo> queryOcenterSkuList(QueryCenterSkuListReqVo querySkuListReqVo);


    List<ProductSkuRespVo> getProductSkuInfos(QuerySkuReqVO reqVO);

    /**
     * 销售类变价查询sku列表
     * @author NullPointException
     * @date 2019/5/28
     * @param vo
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QuerySkuInfoRespVO>
     */
    List<QuerySkuInfoRespVO> selectSkuListForSalePrice(QuerySkuInfoReqVO vo);
    /**
     * 采购类变价查询sku列表
     * @author NullPointException
     * @date 2019/5/29
     * @param vo
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QuerySkuInfoRespVO>
     */
    List<QuerySkuInfoRespVO> selectSkuListForPurchasePrice(QuerySkuInfoReqVO vo);
    /**
     * 查直送供应商
     * @author NullPointException
     * @date 2019/6/5
     * @param collect
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaRespVO>
     */
    List<QueryProductSaleAreaRespVO> selectDirectSupplierBySkuCodes(@Param("items") List<String> collect);
    /**
     * 销售区域查询sku信息
     * @author NullPointException
     * @date 2019/6/6
     * @param ids
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.salearea.QueryProductSaleAreaForSkuRespVO>
     */
    List<QueryProductSaleAreaForSkuRespVO> selectSkuListForSaleArea(List<Long> ids);
    /**
     * 查数量
     * @author NullPointException
     * @date 2019/7/10
     * @param reqVO
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaForSkuRespVO>
     */
    List<Long> selectSkuListForSaleAreaCount(QueryProductSaleAreaForSkuReqVO reqVO);

    List<SupervisoryWarehouseSkuRespVo> getSupervisoryWarehouseSku(QuerySkuListReqVO querySkuListReqVO);

    List<PriceChannelForChangePrice>  getSaleChannelList();

    List<ProductSaleAreaSupplierInfo> getSupplier();

    List<ProductSkuInfo> getSkuInfoByCodeList(@Param("skuCodeList")List<String> skuCodeList);

    List<PurchaseApplyDetailResponse> purchaseProductList(PurchaseApplyRequest purchases);

    Integer purchaseProductCount(PurchaseApplyRequest purchases);

    List<String> contrastPropertySku(String productCategoryCode);

    PurchaseApplyDetailResponse purchaseBySkuStock(@Param("purchaseGroupCode")String purchaseGroupCode, @Param("skuCode")String skuCode,
                                            @Param("supplierCode")String supplierCode, @Param("transportCenterCode")String transportCenterCode);
}