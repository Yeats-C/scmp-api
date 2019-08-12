package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseOrderProductDao {

    Integer insert(PurchaseOrderProduct record);

    Integer update(PurchaseOrderProduct record);

    Integer insertAll(@Param("list")List<PurchaseOrderProduct> list);

    List<PurchaseOrderProduct> purchaseOrderList(PurchaseOrderProductRequest request);

    Integer purchaseOrderCount(PurchaseOrderProductRequest request);

    List<PurchaseOrderProduct> orderBySku(String purchaseOrderId);

    PurchaseApplyDetailResponse  warehousingInfo(@Param("purchaseOrderCode")String purchaseOrderCode,@Param("id") Long id);

    List<PurchaseOrderProduct> orderProductInfo(String purchaseOrderId);

    List<PurchaseApplyDetailResponse> orderStatusByCount(@Param("skuCode") String skuCode, @Param("transportCenterCode") String transportCenterCode);

    PurchaseOrderProduct selectPreNumAndPraNumBySkuCodeAndSource(@Param("code")String code, @Param("skuCode")String skuCode, @Param("id")Long id);

    List<PurchaseApplyDetailResponse> orderProductInfoByGroup(String purchaseOrderId);

    List<PurchaseApplyDetailResponse> orderProductList(String purchaseOrderId);
}