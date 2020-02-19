package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyTransportCenter;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplySaveRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseNewContrastRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFlowPathResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseNewContrastResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public interface PurchaseApplyService {

    HttpResponse applyList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse applyProductList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse purchaseApplyForm(PurchaseApplyProductRequest applyProductRequest);

    HttpResponse searchApplyProduct(String applyProductId);

    HttpResponse<List<PurchaseApplyTransportCenter>> transportCenterPurchase(String purchaseApplyCode, String transportCenterCode);

    HttpResponse deleteApplyProduct(String applyProductId);

    HttpResponse productGroup(PurchaseApplyProductRequest request);

    HttpResponse applyPurchaseSave(PurchaseApplySaveRequest request);

    HttpResponse purchaseOrderFile(String fileId);

    HttpResponse purchaseCurrency(String purchaseApplyId);

    HttpResponse purchaseNewEdit(String purchaseApplyId);

    HttpResponse applyProductBasic(String purchaseApplyId);

    HttpResponse applySelectionProduct(String purchaseApplyId);

    HttpResponse purchaseApplyImport(MultipartFile file, String purchaseGroupCode);

    HttpResponse purchaseApplyStatus(PurchaseApply purchaseApply);

    HttpResponse<PurchaseFlowPathResponse> applyProductDetail(Integer singleCount, BigDecimal productPurchaseAmount, String skuCode,
                                                              String supplierCode, String transportCenterCode, Integer productCount);

    HttpResponse<PurchaseNewContrastResponse> purchaseContrast(PurchaseNewContrastRequest contrastRequest);

    HttpResponse importPdf(String purchaseOrderCode, HttpServletResponse response);

    HttpResponse purchaseDelete(String purchaseOrderId);
}
