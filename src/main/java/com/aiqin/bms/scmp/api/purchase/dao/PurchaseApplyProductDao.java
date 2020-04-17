package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseApplyProductDao {

    Integer insert(PurchaseApplyProduct record);

    Integer update(PurchaseApplyProduct record);

    List<PurchaseApplyDetailResponse> applyProductList(PurchaseApplyRequest purchaseApplyRequest);

    Integer applyProductCount(PurchaseApplyRequest purchaseApplyRequest);

    PurchaseApplyProduct applyProduct(PurchaseApplyProduct purchaseApplyProduct);

    List<PurchaseApplyDetailResponse> productListByDetail(@Param("purchaseApplyId") String purchaseApplyId);

    Integer skuCount(@Param("purchaseApplyId") String purchaseApplyId, @Param("infoStatus")Integer infoStatus);

    Integer insertAll(@Param("list") List<PurchaseApplyProduct> purchaseApplyProduct);

    Integer delete(String purchaseApplyId);

    List<PurchaseApplyProduct> applyPurchaseProductList(String purchaseApplyId);

    List<PurchaseApplyDetailResponse> productCodeByDetail(@Param("purchaseApplyCode") String purchaseApplyCode,
                                                          @Param("warehouseCode")String warehouseCode);

    List<PurchaseApplyDetailResponse> productCodeByDetailSum(@Param("purchaseApplyCode") String purchaseApplyCode);


}