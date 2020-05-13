package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.request.ILockStocksReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectApplyDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyAndTransportResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsRejectService {
    HttpResponse<PageResData<RejectApplyRecord>> rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectApply(RejectApplyHandleRequest rejectApplyQueryRequest);

    HttpResponse<RejectApplyAndTransportResponse> selectRejectApply(String rejectApplyRecordCode, String warehouseCode);

    HttpResponse<PageResData<RejectApplyRecordDetail>>  selectRejectApplyProduct(String rejectApplyRecordCode);

    HttpResponse<PageResData<RejectApplyBatch>> selectRejectApplyBatch(String rejectApplyRecordCode);

    HttpResponse productGroup(List<RejectApplyDetailRequest> request);

    HttpResponse<List<RejectApplyResponse>> rejectApplyInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse addReject(RejectRequest request);

    HttpResponse updateReject(String rejectApplyQueryRequest);

    HttpResponse<PageResData<RejectRecord>> rejectList(RejectQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectSupplier(RejectRecord rejectApplyQueryRequest, String create_by_company_code);

    HttpResponse<RejectResponse> rejectInfo(String rejectApplyQueryRequest);

    HttpResponse<List<RejectImportResponse>> rejectApplyImport(MultipartFile file, String purchaseGroupCode);

    HttpResponse updateRejectApply(RejectApplyHandleRequest rejectApplyQueryRequest);

    HttpResponse rejectTransport(RejectRecord request);

    HttpResponse rejectTransportFinish(String reject_record_id);

    void finishStock(RejectStockRequest request);

    HttpResponse<PageResData<RejectApplyRecordDetail>> rejectApplyDetailInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse<List<RejectApplyListResponse>> rejectApplyListInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectCancel(String reject_record_id);

    ILockStocksReqVO handleStockParam(List<RejectRecordDetail> detailList, RejectRecord rejectRecord);

    HttpResponse<PageResData<RejectApplyDetailHandleResponse>> rejectStockProduct(RejectProductRequest rejectQueryRequest);

    String selectCategoryName(String categoryCode);

    HttpResponse<RejectResponse> applyRejectInfo(String approvalCode);

    HttpResponse rejectDelete(String rejectApplyRecordCode);
}
