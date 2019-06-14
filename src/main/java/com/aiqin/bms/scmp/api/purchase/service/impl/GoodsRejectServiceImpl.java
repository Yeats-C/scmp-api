package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyQueryResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
@Service
public class GoodsRejectServiceImpl implements GoodsRejectService {


    @Resource
    private RejectApplyRecordDao rejectApplyRecordDao;
    @Resource
    private RejectApplyRecordDetailDao rejectApplyRecordDetailDao;
    @Resource
    private RejectRecordDao rejectRecordDao;
    @Resource
    private RejectRecordDetailDao rejectRecordDetailDao;
    @Resource
    private OperationLogDao operationLogDao;

    @Override
    public HttpResponse rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest) {
        List<RejectApplyQueryResponse> list = rejectApplyRecordDao.list(rejectApplyQueryRequest);
        Integer count = rejectApplyRecordDao.listCount(rejectApplyQueryRequest);
        return HttpResponse.success(new PageResData(count, list));
    }

    @Override
    public HttpResponse rejectApply(RejectApplyRequest rejectApplyQueryRequest) {
        return null;
    }

    @Override
    public HttpResponse rejectApplyImport(MultipartFile file, String purchaseGroupCode) {
        if (file == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        return null;
    }

    @Override
    public HttpResponse rejectApplyInfo(RejectApplyRequest rejectApplyQueryRequest) {
        return null;
    }

    @Override
    public HttpResponse addReject(RejectApplyRequest rejectApplyQueryRequest) {
        return null;
    }

    @Override
    public HttpResponse updateReject(RejectApplyRequest rejectApplyQueryRequest) {
        return null;
    }

    @Override
    public HttpResponse rejectList(RejectApplyRequest rejectApplyQueryRequest) {
        return null;
    }

    @Override
    public HttpResponse rejectSupplier(RejectApplyRequest rejectApplyQueryRequest) {
        return null;
    }

    @Override
    public HttpResponse rejectInfo(RejectApplyRequest rejectApplyQueryRequest) {
        return null;
    }
}
