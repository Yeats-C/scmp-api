package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.RejectRecordStatus;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupply;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.FileReaderUtil;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    @Resource
    private OutboundService outboundService;
    @Resource
    private PurchaseGroupService purchaseGroupService;


    @Override
    public HttpResponse rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest) {
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup();
        if (CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.success();
        }
        rejectApplyQueryRequest.setGroupList(groupVoList);
        List<RejectApplyQueryResponse> list = rejectApplyRecordDao.list(rejectApplyQueryRequest);
        Integer count = rejectApplyRecordDao.listCount(rejectApplyQueryRequest);
        return HttpResponse.success(new PageResData(count, list));
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpResponse rejectApply(RejectApplyRequest rejectApplyQueryRequest) {
        try {
            EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.GOODS_REJECT_APPLY_CODE);
            //总退供数量
            Integer sumCount = 0;
            //总退供金额
            Long sumAmount = 0L;
            RejectApplyRecord rejectApplyRecord = new RejectApplyRecord();
            BeanUtils.copyProperties(rejectApplyQueryRequest,rejectApplyRecord);
            String rejectCode = "RAR" + encodingRule.getNumberingValue();
            rejectApplyRecord.setRejectApplyRecordCode(rejectCode);
            //处理数据
            this.rejectApplyData(rejectApplyQueryRequest, sumCount, sumAmount, rejectCode);
            rejectApplyRecord.setSumAmount(sumAmount);
            rejectApplyRecord.setSumCount(sumCount);
            rejectApplyRecord.setApplyRecordStatus(1);
            //sku数量等于商品列表条数
            rejectApplyRecord.setSumSku(rejectApplyQueryRequest.getDetailList().size());
            Integer counts = rejectApplyRecordDao.insert(rejectApplyRecord);
            LOGGER.info("添加退供申请影响条数:{}", counts);
            //添加详情
            Integer detailCount = rejectApplyRecordDetailDao.insertAll(rejectApplyQueryRequest.getDetailList());
            LOGGER.info("添加退供申请详情影响条数:{}", detailCount);
            //更新编码
            encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
        } catch (Exception e) {
            LOGGER.error("添加退供申请单异常:{}",e.getMessage());
            throw new RuntimeException(String.format("添加退供申请单异常:{}", e.getMessage()));
        }
        return HttpResponse.success();
    }

    /**
     * 处理退供申请单数据
     */
    private void rejectApplyData(RejectApplyRequest rejectApplyQueryRequest, Integer sumCount, Long sumAmount, String rejectCode) {
        for (RejectApplyRecordDetail detail : rejectApplyQueryRequest.getDetailList()) {
            //详情的id
            detail.setRejectApplyRecordDetailId(IdUtil.uuid());
            detail.setRejectApplyRecordCode(rejectCode);
            sumAmount += detail.getProductTotalAmount();
            sumCount += detail.getProductCount();
            detail.setCreateById(rejectApplyQueryRequest.getCreateById());
            detail.setCreateByName(rejectApplyQueryRequest.getCreateByName());
        }
        //添加详情
        Integer detailCount = rejectApplyRecordDetailDao.insertAll(rejectApplyQueryRequest.getDetailList());
        LOGGER.info("添加退供申请详情影响条数:{}", detailCount);
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpResponse updateRejectApply(RejectApplyRequest rejectApplyRequest) {
        try {
            RejectApplyRecord rejectApplyRecord = rejectApplyRecordDao.selectByRejectCode(rejectApplyRequest.getRejectApplyRecordCode());
            if (rejectApplyRecord == null) {
                LOGGER.error("未查询到退供申请单信息:{}", rejectApplyRequest.getRejectApplyRecordCode());
                return HttpResponse.failure(ResultCode.NOT_HAVE_REJECT_APPLY_RECORD);
            }
            //总退供数量
            Integer sumCount = 0;
            //总退供金额
            Long sumAmount = 0L;
            //删除原申请单详情信息
            rejectApplyRecordDetailDao.deleteAll(rejectApplyRecord.getRejectApplyRecordCode());
            //处理数据
            this.rejectApplyData(rejectApplyRequest, sumCount, sumAmount, rejectApplyRecord.getRejectApplyRecordCode());
            rejectApplyRecord.setSumAmount(sumAmount);
            rejectApplyRecord.setSumCount(sumCount);
            //sku数量等于商品列表条数
            rejectApplyRecord.setSumSku(rejectApplyRequest.getDetailList().size());
            //更新退供申请单信息
            Integer count = rejectApplyRecordDao.updateByRejectCode(rejectApplyRequest);
            LOGGER.info("修改退供申请详情影响条数:{}", count);
        } catch (Exception e) {
            throw new RuntimeException(String.format("修改退供申请单异常:{}", e.getMessage()));
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse selectRejectApply(String rejectApplyCode) {
        RejectApplyRecord rejectApplyRecord = rejectApplyRecordDao.selectByRejectCode(rejectApplyCode);
        if (rejectApplyRecord == null) {
            LOGGER.error("未查询到退供申请单信息:{}", rejectApplyCode);
            return HttpResponse.failure(ResultCode.NOT_HAVE_REJECT_APPLY_RECORD);
        }
        RejectApplyResponse response = new RejectApplyResponse();
        BeanUtils.copyProperties(rejectApplyRecord, response);
        List<RejectApplyDetailResponse> list = rejectApplyRecordDetailDao.selectByRejectCode(rejectApplyCode);
        response.setDetailList(list);
        return HttpResponse.success(response);
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
                    //todo 通过sku查询商品的品类,品牌,规格,供应商 ,税率,库存数量,总价,仓库,库房 ,批次信息 ,结算方式

                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("退供申请单导入异常:{}", e.getMessage());
            return HttpResponse.failure(ResultCode.IMPORT_REJECT_APPLY_ERROR);
        }
    }
    @Override
    public HttpResponse rejectApplyDetailInfo(RejectApplyRequest response) {
        List<RejectApplyRecordDetail> detailList = rejectApplyRecordDetailDao.listByCondition(response.getSupplierCode(), response.getPurchaseGroupCode(), response.getSettlementMethodCode(),
                response.getTransportCenterCode(), response.getWarehouseCode(), response.getRejectApplyRecordCodes());
        for (RejectApplyRecordDetail detailResponse : detailList) {
            //todo 根据sku查询实际库存

        }
        return HttpResponse.success(detailList);
    }

    @Override
    public HttpResponse rejectApplyInfo(RejectApplyRequest request) {
        if (CollectionUtils.isEmpty(request.getRejectApplyRecordCodes())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        //退供申请单分组后的列表
        List<RejectApplyResponse> list = rejectApplyRecordDetailDao.listForRejectRecord(request);
        SupplyCompany company ;
        for (RejectApplyResponse response : list) {
            company = rejectApplyRecordDetailDao.selectSupplyCompany(response.getSupplierCode());
            if(company!=null){
                response.setCityId(company.getCityId());
                response.setAddress(company.getAddress());
                response.setCityName(company.getCityName());
                response.setProvinceId(company.getProvinceId());
                response.setProvinceName(company.getProvinceName());
                response.setDistrictId(company.getDistrictId());
                response.setDistrictName(company.getDistrictName());
            }else{
                response.setCityId("");
                response.setAddress("");
                response.setCityName("");
                response.setProvinceId("");
                response.setProvinceName("");
                response.setDistrictId("");
                response.setDistrictName("");
            }
        }
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse addReject(RejectRequest request) {
        if (CollectionUtils.isEmpty(request.getDetailList())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.GOODS_REJECT_APPLY_CODE);
        RejectRecord rejectRecord = new RejectRecord();
        BeanUtils.copyProperties(request, rejectRecord);
        String rejectCode = "RR" + encodingRule.getNumberingName();
        String rejectId = IdUtil.rejectRecordId();
        rejectRecord.setRejectRecordCode(rejectCode);
        rejectRecord.setRejectRecordId(rejectId);
        rejectRecord.setExpectTime(new DateTime(request.getExpectTime()).toDate());
        rejectRecord.setValidDay(new DateTime(request.getValidDay()).toDate());
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
        List<RejectApplyRecordDetail> detailList = rejectApplyRecordDetailDao.listByCondition(request.getSupplierCode(), request.getPurchaseGroupCode(), request.getSettlementMethodCode(),
                request.getTransportCenterCode(), request.getWarehouseCode(), request.getRejectApplyRecordCodes());
        for (RejectApplyRecordDetail detailResponse : detailList) {
            //计算总数
            sumCount += detailResponse.getProductCount();
            sumSingleCount += detailResponse.getProductCount();
            sumAmount += detailResponse.getProductTotalAmount();
            sumSingleAmount += detailResponse.getProductTotalAmount();
            if (detailResponse.getProductType().equals(2)) {
                returnCount += detailResponse.getProductCount();
                returnAmount += detailResponse.getProductTotalAmount();
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
        LOGGER.info("添加退供影响条数:{}", count);
        //添加退供单详情
        Integer detailCount = rejectRecordDetailDao.insertAll(request.getDetailList(), rejectId, rejectCode, request.getCreateById(), request.getCreateByName());
        LOGGER.info("添加退供详情影响条数:{}", detailCount);
        //更改退供申请详情部分记录(reject_apply_record_detail)更改为已提交
        List<String> detailIds = detailList.stream().map(RejectApplyRecordDetail::getRejectApplyRecordDetailId).collect(Collectors.toList());
        Integer updateCount = rejectApplyRecordDetailDao.updateByDetailIds(detailIds);
        LOGGER.info("更改退供申请详情影响条数:{}", updateCount);
        //更新编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
        //TODO 调用上传文件

        return HttpResponse.success();
    }

    @Override
    public HttpResponse updateReject(RejectApplyRequest rejectApplyQueryRequest) {


        return null;
    }

    @Override
    public HttpResponse rejectList(RejectQueryRequest rejectApplyQueryRequest) {
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup();
        if (CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.success();
        }
        rejectApplyQueryRequest.setGroupList(groupVoList);
        List<RejectRecord> list = rejectRecordDao.list(rejectApplyQueryRequest);
        Integer count = rejectRecordDao.listCount(rejectApplyQueryRequest);
        return HttpResponse.success(new PageResData(count, list));
    }

    @Transactional
    public HttpResponse rejectSupplier(String rejectRecordId) {
        try {
            RejectRecord rejectRecord = rejectRecordDao.selectByRejectId(rejectRecordId);
            if (rejectRecord == null) {
                return HttpResponse.failure(ResultCode.NOT_HAVE_REJECT_RECORD);
            }
            RejectRecord request = new RejectRecord();
            request.setRejectRecordId(rejectRecordId);
            request.setRejectStatus(RejectRecordStatus.REJECT_STATUS_DEFINE);
            Integer count = rejectRecordDao.updateStatus(request);
            LOGGER.info("供应商确认-更改退供申请详情影响条数:{}", count);
            List<RejectRecordDetail> list = rejectRecordDetailDao.selectByRejectId(rejectRecordId);
            ReturnSupplyToOutBoundReqVo reqVo = new ReturnSupplyToOutBoundReqVo();
            reqVo.setRejectRecord(rejectRecord);
            reqVo.setRejectRecordDetails(list);
            LOGGER.info("调用退供出库:{}", request);
            outboundService.returnSupplySave(reqVo);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("更新退供单异常:{}",e.getMessage());
            throw new RuntimeException(String.format("更新退供单异常:{%s}", e.getMessage()));
        }
    }

    @Override
    public HttpResponse rejectTransport(RejectRecord rejectRecord) {
        RejectRecord record = rejectRecordDao.selectByRejectId(rejectRecord.getRejectRecordId());
        if (record == null) {
            return HttpResponse.failure(ResultCode.NOT_HAVE_REJECT_RECORD);
        }
        rejectRecord.setRejectStatus(RejectRecordStatus.REJECT_STATUS_TRANSPORTED);
        Integer count = rejectRecordDao.updateStatus(rejectRecord);
        LOGGER.info("退供发运-更改退供申请详情影响条数:{}", count);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse rejectTransportFinish(String rejectRecordId) {
        RejectRecord record = rejectRecordDao.selectByRejectId(rejectRecordId);
        if (record == null) {
            return HttpResponse.failure(ResultCode.NOT_HAVE_REJECT_RECORD);
        }
        RejectRecord rejectRecord = new RejectRecord();
        rejectRecord.setRejectRecordId(rejectRecordId);
        rejectRecord.setRejectStatus(RejectRecordStatus.REJECT_STATUS_FINISH);
        Integer count = rejectRecordDao.updateStatus(rejectRecord);
        LOGGER.info("退供完成-更改退供申请详情影响条数:{}", count);
        return HttpResponse.success();
    }


    @Override
    public HttpResponse rejectInfo(String rejectRecordId) {
        RejectResponse rejectResponse = new RejectResponse();
        RejectRecord rejectRecord = rejectRecordDao.selectByRejectId(rejectRecordId);
        BeanUtils.copyProperties(rejectRecord, rejectResponse);
        List<RejectRecordDetail> batchList = rejectRecordDetailDao.selectByRejectId(rejectRecordId);
        List<RejectRecordDetailResponse> productList = rejectRecordDetailDao.selectProductByRejectId(rejectRecordId);
        rejectResponse.setBatchList(batchList);
        rejectResponse.setProductList(productList);
        return HttpResponse.success(rejectResponse);
    }

    /**
     * 出库完成,更新退供单 出库时间 退供单状态
     */
    @Transactional
    public void finishStock(RejectStockRequest request) {
        try {
            LOGGER.info("出库完成,更新退供单信息:{}", request.toString());
            RejectRecord rejectRecord = new RejectRecord();
            rejectRecord.setRejectRecordCode(request.getRejectRecordCode());
            rejectRecord.setOutStockTime(request.getOutStockTime());
            rejectRecord.setRejectStatus(RejectRecordStatus.REJECT_STATUS_STOCKED);
            Integer count = rejectRecordDao.updateStatus(rejectRecord);
            LOGGER.info("更新退供单信息影响条数:{}", count);
            if (CollectionUtils.isNotEmpty(request.getDetailList())) {
                for (RejectDetailStockRequest detailResponse : request.getDetailList()) {
                    rejectRecordDetailDao.updateByDetailId(detailResponse);
                }
            }
        } catch (Exception e) {
            LOGGER.error("更新退供单异常:{}", e.getMessage());
            throw new RuntimeException(String.format("更新退供单异常:{%s}", e.getMessage()));
        }

    }




}
