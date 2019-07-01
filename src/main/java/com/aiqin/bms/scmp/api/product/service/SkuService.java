package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ocenter.QueryCenterSkuListReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.OmsProductSkuListReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.QuerySkuListPageReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOmsSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOrderReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.CheckPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.QueryPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryMerchantSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryStoreProductListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryStoreSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryStoreSkusReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuResponse;
import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.merchant.MerchantSkuItemRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ocenter.QueryCenterSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.OmsProductListItemResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.OmsProductSkuItemResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.QueryOmsSkusPageResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.QuerySkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.SupervisoryWarehouseSkuRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.ProductItemStoreRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.StoreSkuDetailRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.StoreSkuItemRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.StoreSkuListRespVO;

import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:17
 */
public interface SkuService {

    BasePage<ProductSkuInfo> getSkuList(QuerySkuListReqVO querySkuListReqVO);

    /**
     * 采购查询sku分页列表
     * @param queryPurchaseSkuReqVO
     * @return
     */
    BasePage<PurchaseItemRespVo> getPurchaseSkuList(QueryPurchaseSkuReqVO queryPurchaseSkuReqVO);

    /**
     * 根据skucode集合查询sku信息
     * @param queryMerchantSkuListReqVO
     * @return
     */
    List<MerchantSkuItemRespVO> getMerchantSkuListByCodes(QueryMerchantSkuListReqVO queryMerchantSkuListReqVO);

    /**
     * 根据商品编码及其他查询sku列表及信息
     * @param queryStoreSkuListReqVO
     * @return
     */
    StoreSkuListRespVO getSkuListByProductCode(QueryStoreSkuListReqVO queryStoreSkuListReqVO);

    /**
     * 根据sku编码查询门店sku详情
     * @param skuCode
     * @return
     */
    StoreSkuDetailRespVO getStoreSkuDetailByCode(String skuCode);

    /**
     * 根据条件查询门店下商品列表
     * @param queryStoreProductListReqVO
     * @return
     */
    BasePage<ProductItemStoreRespVO> getStoreProductItem(QueryStoreProductListReqVO queryStoreProductListReqVO);

    /**
     * 根据商品及其他条件查询门店sku基本信息列表
     * @param queryStoreSkuListReqVO
     * @return
     */
    List<StoreSkuItemRespVO> getProductDetailSkuList(QueryStoreSkuListReqVO queryStoreSkuListReqVO);

    /**
     * 校验采购sku列表是否存在
     * @param checkPurchaseSkuReqVO
     * @return
     */
    List<PurchaseItemRespVo> getCheckSkuList(CheckPurchaseSkuReqVO checkPurchaseSkuReqVO);

    List<ProductSkuResponse> selectSkuInfoListCanUseBySkuCodeList(List<String> skuCodeList);

    /**
     * 根据sku编码集合获取sku信息
     * @param skuCodeList
     * @return
     */
    List<PurchaseItemRespVo> getSalesSkuList(List<String> skuCodeList);

    Map<String, Long> getSkuConvertNumBySkuCodes(List<String> skuCodes);

    BasePage<QueryProductSkuListResp> getOmsSkuList(SearchOmsSkuListReqVO searchOmsSkuListReqVO);

    List<StoreSkuItemRespVO> getStoreSkuListByCodes(QueryStoreSkusReqVO queryStoreSkusReqVO);

    BasePage<OmsProductListItemResp> getOmsProductList(SearchOrderReqVO searchOrderReqVO);

    List<OmsProductSkuItemResp> getOmsProductSkuList(OmsProductSkuListReq omsProductSkuListReq);

    List<QuerySkuListResp> getOmsSkus(QueryStoreSkusReqVO queryStoreSkusReqVO);

    BasePage<QueryOmsSkusPageResp> queryOmsSkuPage(QuerySkuListPageReq querySkuListPageReq);

    ProductSkuResponse selectSkuInfoBySkuCode(String skuCode);

    /**
     * 根据skuCodes获取进出项税率
     * @param skuCodes
     * @return
     */
    List<ProductSkuCheckout> getSkuCheckOuts(List<String> skuCodes);



    /**
     * 通过编码获取供货单位详情
     * @param supplyCode
     * @return
     */
    SupplyComDetailRespVO  detailByCode(String supplyCode);

    /**
     * 运营中心查询SKU列表信息
     * @param querySkuListReqVo
     */
    BasePage<QueryCenterSkuListRespVo> queryOcenterSkuList(QueryCenterSkuListReqVo querySkuListReqVo);


    /**
     * 监管仓入库SKU查询
     * @return
     */
    BasePage<SupervisoryWarehouseSkuRespVo> getSupervisoryWarehouseSku(QuerySkuListReqVO querySkuListReqVO);
}
