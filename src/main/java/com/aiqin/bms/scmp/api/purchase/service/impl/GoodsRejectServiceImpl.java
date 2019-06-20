package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectQueryRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyQueryResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.RejectApplyResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.FileReaderUtil;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            "SKU编号", "SKU名称", "供应商编号", "仓库编号", "库房编号", "商品批次号", "退供类型", "退供数量", "含税单价",
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
    @Resource
    private EncodingRuleDao encodingRuleDao;

    @Override
    public HttpResponse rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest) {
        List<RejectApplyQueryResponse> list = rejectApplyRecordDao.list(rejectApplyQueryRequest);
        Integer count = rejectApplyRecordDao.listCount(rejectApplyQueryRequest);
        return HttpResponse.success(new PageResData(count, list));
    }

    @Override
    public HttpResponse rejectApply(RejectApplyRequest rejectApplyQueryRequest) {
        EncodingRule encodingRule =encodingRuleDao.getNumberingType(EncodingRuleType.GOODS_REJECT_APPLY_CODE);

        Integer counts = rejectApplyRecordDao.insert(new RejectApplyRecord());
        LOGGER.info("添加退供申请影响条数:{}",counts);
        //添加详情
        Integer detailCount = rejectApplyRecordDetailDao.insertAll(new ArrayList<>());
        LOGGER.info("添加退供申请详情影响条数:{}",detailCount);
        //更新编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        return HttpResponse.success();
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
                    return HttpResponse.failure(MessageId.create(Project.SCMP_API, 88888, validResult));
                }
                String[] record;
                for (int i = 3; i <= result.length - 1; i++) {
                    record = result[i];
                    //todo 通过sku查询商品的品类,品牌,规格,供应商 ,税率,库存数量,总价,仓库,库房
                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("退供申请单导入异常:{}", e.getMessage());
            return HttpResponse.failure(ResultCode.IMPORT_REJECT_APPLY_ERROR);
        }
    }

    @Override
    public HttpResponse rejectApplyInfo(RejectApplyRequest request) {
        if (CollectionUtils.isEmpty(request.getRejectApplyRecordCodes())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        //退供申请单分组后的列表
        List<RejectApplyResponse> list = rejectApplyRecordDetailDao.listForRejectRecord(request);
        List<RejectApplyDetailResponse> detailList;
        for (RejectApplyResponse response : list) {
            //查询商品批次列表
            detailList = rejectApplyRecordDetailDao.listByCondition(response.getSupplierCode(), response.getPurchaseGroupCode(), response.getSettlementMethod(),
                    response.getTransportCenterCode(), response.getWarehouseCode(), request.getRejectApplyRecordCodes());
            response.setDetailList(detailList);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse addReject(RejectRequest request) {
        EncodingRule encodingRule =encodingRuleDao.getNumberingType(EncodingRuleType.GOODS_REJECT_APPLY_CODE);
        RejectRecord rejectRecord = new RejectRecord();
        BeanUtils.copyProperties(request,rejectRecord);
        String rejectCode = "RR"+encodingRule.getNumberingName();
        String rejectId = IdUtil.rejectRecordId();
        rejectRecord.setRejectRecordCode(rejectCode);
        rejectRecord.setRejectRecordId(rejectId);
        //总退供数量
        Integer sumCount = 0;
        //总退供金额
        Long sumAmount = 0L;
        //总单品数量
        Integer sumSingleCount = 0;
        //总单品金额
        Long sumSingleAmount = 0L;
        //总实物返回数量
        Integer returnCount = 0;
        //总实物返回金额
        Long returnAmount = 0L;
        for (RejectApplyDetailResponse detailResponse : request.getDetailList()) {
            //计算总数
            sumCount+=detailResponse.getProductCount();
            sumSingleCount+=detailResponse.getProductCount();
            sumAmount+=detailResponse.getProductTotalAmount();
            sumSingleAmount+=detailResponse.getProductTotalAmount();
            if(detailResponse.getProductType().equals(2)){
                returnCount+=detailResponse.getProductCount();
                returnAmount+=detailResponse.getProductTotalAmount();
            }
        }
        rejectRecord.setSumAmount(sumAmount);
        rejectRecord.setSumCount(sumCount);
        rejectRecord.setSingleAmount(sumSingleAmount);
        rejectRecord.setSingleCount(sumSingleCount);
        rejectRecord.setReturnAmount(returnAmount);
        rejectRecord.setReturnCount(returnCount);
        //添加退供单记录
        Integer count = rejectRecordDao.insert(rejectRecord);
        LOGGER.info("添加退供影响条数:{}",count);
        //添加退供单详情
        Integer detailCount = rejectRecordDetailDao.insertAll(request.getDetailList(),rejectId,rejectCode,request.getCreateById(),request.getCreateByName());
        LOGGER.info("添加退供详情影响条数:{}",detailCount);
        //更改退供申请详情部分记录(reject_apply_record_detail)更改为已提交
        List<String> detailIds = request.getDetailList().stream().map(rejectApplyDetailResponse -> rejectApplyDetailResponse.getRejectApplyRecordDetailId()).collect(Collectors.toList());
        Integer updateCount = rejectApplyRecordDetailDao.updateByDetailIds(detailIds);
        LOGGER.info("更改退供申请详情影响条数:{}",updateCount);
        //更新编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        //TODO 调用上传文件

        return HttpResponse.success();
    }

    @Override
    public HttpResponse updateReject(RejectApplyRequest rejectApplyQueryRequest) {


        return null;
    }

    @Override
    public HttpResponse rejectList(RejectQueryRequest rejectApplyQueryRequest) {
        List<RejectRecord> list = rejectRecordDao.list(rejectApplyQueryRequest);
        Integer count = rejectRecordDao.listCount(rejectApplyQueryRequest);
        return HttpResponse.success(new PageResData(count, list));
    }

    @Override
    public HttpResponse rejectSupplier(String rejectApplyQueryRequest) {
        Integer count = rejectRecordDao.updateStatus(rejectApplyQueryRequest,"");
        return null;
    }

    @Override
    public HttpResponse rejectInfo(String rejectApplyQueryRequest) {
        return null;
    }
}
