package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.request.ILockStocksReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.RejectProductRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsRejectService {
    HttpResponse<PageResData<RejectApplyRecord>> rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectApply(RejectApplyHandleRequest rejectApplyQueryRequest);

    HttpResponse<List<RejectApplyResponse>> rejectApplyInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse addReject(RejectRequest request);

    HttpResponse updateReject(String rejectApplyQueryRequest);

    HttpResponse<PageResData<RejectRecord>> rejectList(RejectQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectSupplier(RejectRecord rejectApplyQueryRequest, String create_by_company_code);

    HttpResponse<RejectResponse> rejectInfo(String rejectApplyQueryRequest);

    HttpResponse<List<RejectImportResponse>> rejectApplyImport(MultipartFile file, String purchaseGroupCode);

    HttpResponse updateRejectApply(RejectApplyHandleRequest rejectApplyQueryRequest);

    HttpResponse<RejectApplyHandleResponse> selectRejectApply(String rejectApplyQueryRequest);

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
