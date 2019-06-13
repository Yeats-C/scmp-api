package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

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

    HttpResponse rejectApplyImport(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectApplyInfo(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse addReject(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse updateReject(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectList(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectSupplier(RejectApplyRequest rejectApplyQueryRequest);

    HttpResponse rejectInfo(RejectApplyRequest rejectApplyQueryRequest);
}
