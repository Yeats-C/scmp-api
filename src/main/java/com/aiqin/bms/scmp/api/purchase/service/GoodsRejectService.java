package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectRequest;
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
    HttpResponse rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectApply(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectApplyInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse addReject(RejectRequest request);

    HttpResponse updateReject(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectList(RejectQueryRequest rejectApplyQueryRequest);

    HttpResponse rejectSupplier(String rejectApplyQueryRequest);

    HttpResponse rejectInfo(String rejectApplyQueryRequest);

    HttpResponse rejectApplyImport(MultipartFile file, String purchaseGroupCode);

    HttpResponse updateRejectApply(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse selectRejectApply(String rejectApplyQueryRequest);

    HttpResponse rejectTransport(RejectRecord request);

    HttpResponse rejectTransportFinish(String reject_record_id);
}
