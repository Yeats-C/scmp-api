package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.request.ILockStocksReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
public interface GoodsRejectService {
    HttpResponse<PageResData<RejectApplyQueryResponse>> rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectApply(RejectApplyHandleRequest rejectApplyQueryRequest);

    HttpResponse<List<RejectApplyResponse>> rejectApplyInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse addReject(RejectRequest request);

    HttpResponse updateReject(String rejectApplyQueryRequest);

    HttpResponse<PageResData<RejectRecord>> rejectList(RejectQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectSupplier(RejectRecord rejectApplyQueryRequest);

    HttpResponse<RejectResponse> rejectInfo(String rejectApplyQueryRequest);

    HttpResponse<PageResData<RejectImportResponse>> rejectApplyImport(MultipartFile file, String purchaseGroupCode);

    HttpResponse updateRejectApply(RejectApplyHandleRequest rejectApplyQueryRequest);

    HttpResponse<RejectApplyHandleResponse> selectRejectApply(String rejectApplyQueryRequest);

    HttpResponse rejectTransport(RejectRecord request);

    HttpResponse rejectTransportFinish(String reject_record_id);

    void finishStock(RejectStockRequest request);

    HttpResponse<PageResData<RejectApplyRecordDetail>> rejectApplyDetailInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectCancel(String reject_record_id);

    ILockStocksReqVO handleStockParam(List<RejectRecordDetail> detailList, RejectRecord rejectRecord);

    HttpResponse<PageResData<RejectApplyDetailHandleResponse>> rejectStockProduct(RejectProductRequest rejectQueryRequest);

    String selectCategoryName(String categoryCode);
}
