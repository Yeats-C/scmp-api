package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.request.ILockStocksReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyAndTransportResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyGroupResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsRejectService {

    HttpResponse<PageResData<RejectApplyRecord>> rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest);

    HttpResponse<PageResData<RejectApplyDetailHandleResponse>> rejectStockProduct(RejectProductRequest rejectQueryRequest);

    HttpResponse<RejectApplyAndTransportResponse> selectRejectApply(String rejectApplyRecordCode, String warehouseCode);

    HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyProduct(String rejectApplyRecordCode);

    HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyBatch(String rejectApplyRecordCode);

    HttpResponse<RejectApplyGroupResponse> productGroup(List<RejectApplyDetailRequest> request);

    HttpResponse<RejectApplyDetailHandleResponse> applyProductEdit(String rejectApplyRecordCode);

    HttpResponse<List<RejectImportResponse>> rejectApplyImport(MultipartFile file, String purchaseGroupCode);

    HttpResponse addApplyReject(RejectApplyGroupRequest request);

    HttpResponse<PageResData<RejectRecord>> rejectList(RejectQueryRequest rejectApplyQueryRequest);

    HttpResponse<RejectResponse> rejectInfo(String rejectRecordCode);

    HttpResponse<PageResData<RejectRecordDetail>> rejectProductInfo(String rejectRecordCode);

    HttpResponse<PageResData<RejectRecordBatch>> rejectBatchInfo(String rejectRecordCode);

    HttpResponse operationRejectRecord(RejectRecord rejectRecord);



    void finishStock(RejectStockRequest request);

    HttpResponse rejectCancel(String reject_record_id);

    ILockStocksReqVO handleStockParam(List<RejectRecordDetail> detailList, RejectRecord rejectRecord);

    String selectCategoryName(String categoryCode);


}
