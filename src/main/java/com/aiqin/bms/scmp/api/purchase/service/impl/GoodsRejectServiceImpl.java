package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyQueryResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.util.FileReaderUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectServiceImpl.class);

    private static final String[] importRejectApplyHeaders = new String[]{
            "SKU编号", "SKU名称", "供应商编号", "仓库编号", "库房编号", "商品类型", "退供数量", "含税单价"
    };
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
        try {
            String[][] result = FileReaderUtil.readExcel(file, 50);

            if (result != null) {
                String validResult = FileReaderUtil.validStoreValue(result, importRejectApplyHeaders);
                if (StringUtils.isNotBlank(validResult)) {
                    return HttpResponse.failure(MessageId.create(Project.PSC_API, 88888, validResult));
                }
                String[] record;
                for (int i = 3; i <= result.length - 1; i++) {
                    record = result[i];
                    //todo 通过sku查询商品的品类,品牌,规格,供应商 ,税率,库存数量,总价,仓库,库房
                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("退供申请单导入异常:{}",e.getMessage());
            return HttpResponse.failure(ResultCode.IMPORT_REJECT_APPLY_ERROR);
        }
    }

    @Override
    public HttpResponse rejectApplyInfo(RejectApplyRequest rejectApplyQueryRequest) {
        if (CollectionUtils.isEmpty(rejectApplyQueryRequest.getRejectApplyRecordCodes())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        //退供申请单分组后的列表
        List<RejectApplyResponse> list  = rejectApplyRecordDao.listForRejectRecord(rejectApplyQueryRequest);
        //todo 查询商品批次列表
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
