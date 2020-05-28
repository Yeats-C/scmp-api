package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq2;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuBatchReqVO;
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
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBatchRespVo;
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
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo2;

import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:17
 */
public interface ProductSkuBatchService {




    BasePage<ProductSkuBatchRespVo> getList(QueryProductSkuBatchReqVO queryProductSkuBatchReqVO);

    Boolean banById(Long id);

    Boolean add(ProductSkuBatchReq2 productSkuBatchReq2);

    List<WarehouseDTO> getWarehousetList();
}
