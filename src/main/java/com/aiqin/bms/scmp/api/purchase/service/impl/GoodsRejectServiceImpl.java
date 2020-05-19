package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.RejectRecordStatus;
import com.aiqin.bms.scmp.api.product.dao.StockBatchDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.ILockStocksItemReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.ILockStocksReqVO;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyAndTransportResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyGroupResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectResponse;
import com.aiqin.bms.scmp.api.purchase.manager.DataManageService;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.FileReaderUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsRejectServiceImpl extends BaseServiceImpl implements GoodsRejectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectServiceImpl.class);

    private static final BigDecimal big = BigDecimal.ZERO;

    private static final String[] importRejectApplyHeaders = new String[]{
            "SKU编号", "SKU名称", "供应商名称", "仓库名称", "库房名称", "商品批次号", "退供类型", "退供数量", "含税单价",
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
    private PurchaseGroupService purchaseGroupService;
    @Resource
    private FileRecordDao fileRecordDao;
    @Resource
    private StockDao stockDao;
    @Resource
    private GoodsRejectApprovalServiceImpl goodsRejectApprovalService;
    @Resource
    private StockService stockService;
    @Resource
    private SupplyCompanyDao supplyCompanyDao;
    @Resource
    private LogisticsCenterDao logisticsCenterDao;
    @Resource
    private WarehouseDao warehouseDao;
    @Resource
    private DataManageService dataManageService;
    @Resource
    private StockBatchDao stockBatchDao;
    @Resource
    private RejectApplyRecordTransportCenterDao rejectApplyRecordTransportCenterDao;
    @Resource
    private RejectRecordBatchDao rejectRecordBatchDao;

    @Override
    public HttpResponse<PageResData<RejectApplyRecord>> rejectApplyList(RejectApplyQueryRequest rejectApplyQueryRequest) {
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
        if (CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.successGenerics(new PageResData<>());
        }
        rejectApplyQueryRequest.setGroupList(groupVoList);
        List<RejectApplyRecord> list = rejectApplyRecordDao.list(rejectApplyQueryRequest);
        Integer count = rejectApplyRecordDao.listCount(rejectApplyQueryRequest);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<RejectApplyDetailHandleResponse>> rejectStockProduct(RejectProductRequest rejectQueryRequest) {
        List<RejectApplyDetailHandleResponse> list = stockDao.rejectProductList(rejectQueryRequest);
        Map<String, List<StockBatch>> rejectApply = new HashMap<>();
        for (RejectApplyDetailHandleResponse response : list) {
            // 赋值四级品类名称
            response.setCategoryName(selectCategoryName(response.getCategoryId()));
            // 查询对应的批次信息
            String key = String.format("%s,%s,%s", response.getSkuCode(), response.getWarehouseCode(), response.getSupplierCode());
            if(rejectApply.get(key) == null){
                rejectApply.put(key, stockBatchDao.stockBatchByReject(response.getSkuCode(), response.getWarehouseCode(), response.getSupplierCode()));
            }
            response.setBatchList(rejectApply.get(key));
        }
        Integer count = stockDao.rejectProductListCount(rejectQueryRequest);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<RejectApplyAndTransportResponse> selectRejectApply(String rejectApplyRecordCode, String warehouseCode) {
        RejectApplyRecord rejectApplyRecord = rejectApplyRecordDao.selectByRejectCode(rejectApplyRecordCode);
        if (rejectApplyRecord == null) {
            LOGGER.error("未查询到退供申请单信息:{}", rejectApplyRecordCode);
            return HttpResponse.failure(ResultCode.NOT_HAVE_REJECT_APPLY_RECORD);
        }
        RejectApplyAndTransportResponse response = BeanCopyUtils.copy(rejectApplyRecord, RejectApplyAndTransportResponse.class);
        // 查询退供单的分仓信息
        RejectApplyRecordTransportCenter center = new RejectApplyRecordTransportCenter();
        center.setRejectApplyRecordCode(rejectApplyRecordCode);
        center.setWarehouseCode(warehouseCode);
        List<RejectApplyRecordTransportCenter> recordCenterList = rejectApplyRecordTransportCenterDao.rejectApplyTransportCenterInfo(center);
        response.setRejectTransportList(recordCenterList);
        return HttpResponse.successGenerics(response);
    }

    @Override
    public HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyProduct(String rejectApplyRecordCode) {
        List<RejectApplyRecordDetail> list = rejectApplyRecordDetailDao.rejectApplyRecordDetailList(rejectApplyRecordCode);
        Integer count = rejectApplyRecordDetailDao.rejectApplyRecordDetailCount(rejectApplyRecordCode);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyBatch(String rejectApplyRecordCode) {
        List<RejectApplyRecordDetail> list = rejectApplyRecordDetailDao.rejectApplyRecordBatchList(rejectApplyRecordCode);
        Integer count = rejectApplyRecordDetailDao.rejectApplyRecordBatchCount(rejectApplyRecordCode);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<RejectApplyGroupResponse> productGroup(List<RejectApplyDetailRequest> request){
        if(CollectionUtils.isEmpty(request)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 根据商品信息根据采购组、供应商、结算方式进行分组
        List<RejectApplyDetailRequest> details = request.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(o -> o.getSupplierCode() + ";" + o.getPurchaseGroupCode() + ";" + o.getSettlementMethodCode()))), ArrayList::new));

        if(CollectionUtils.isEmpty(details)){
            LOGGER.info("分组下的商品信息为空");
            return HttpResponse.failure(ResultCode.REJECT_APPLY_PRODUCT_NULL);
        }

        List<RejectApplyGroupResponse> list = Lists.newArrayList();
        RejectApplyGroupResponse response;
        List<RejectApplyDetailRequest> productList;
        List<RejectApplyRecordTransportCenter> transportList;
        RejectApplyRecordTransportCenter transportCenter;
        List<RejectApplyRecordDetail> rejectProductList;
        RejectApplyRecordDetail rejectApplyRecordDetail;
        Map<String, RejectApplyRecordDetail> rejectProduct;

        for(RejectApplyDetailRequest detail:details){
            response = BeanCopyUtils.copy(detail, RejectApplyGroupResponse.class);
            // 查询对应的供应商信息
            ApplySupplyCompany supplyCompany = supplyCompanyDao.selectBySupplierCode2(detail.getSupplierCode());
            if(supplyCompany != null){
                response.setSupplierPerson(supplyCompany.getContactName());
                response.setSupplierMobile(supplyCompany.getMobilePhone());
            }

            productList = request.stream().filter(s->s.getSupplierCode().equals(detail.getSupplierCode())
                    && s.getSettlementMethodCode().equals(detail.getSettlementMethodCode())
                    && s.getPurchaseGroupCode().equals(detail.getPurchaseGroupCode())
            ).collect(Collectors.toList());

            // 计算商品的分仓信息
            List<RejectApplyDetailRequest> centers = productList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                    new TreeSet<>(Comparator.comparing(o -> o.getWarehouseCode()))), ArrayList::new));
            // 计算采购申请单的总和
            BigDecimal productCenterAmount = big, returnCenterAmount = big, giftCenterAmount = big;
            Long totalCountSum = 0L, productCountSum = 0L, giftCountSum = 0L, returnCountSum = 0L;
            transportList = Lists.newArrayList();
            for(RejectApplyDetailRequest center:centers){
                // 便利商品
                transportCenter = new RejectApplyRecordTransportCenter();
                transportCenter.setTransportCenterCode(center.getTransportCenterCode());
                transportCenter.setTransportCenterName(center.getTransportCenterName());
                transportCenter.setWarehouseCode(center.getWarehouseCode());
                transportCenter.setWarehouseName(center.getWarehouseName());
                Long totalCount = 0L, productCount = 0L, giftCount = 0L, returnCount = 0L;
                BigDecimal productAmount = big, returnAmount = big, giftAmount = big;
                rejectProductList = Lists.newArrayList();
                for(RejectApplyDetailRequest product:productList){
                    // 计算商品数量
                    if(center.getWarehouseCode().equals(product.getWarehouseCode())){
                        // 计算每个仓库的商品、实物返、赠品、最小单位数量总和  0商品 1赠品 2实物返回
                        if(product.getProductType().equals(0)){
                            productAmount = productAmount.add(product.getProductTotalAmount());
                            productCount += product.getTotalCount();
                        }else if(product.getProductType().equals(1)){
                            giftAmount = giftAmount.add(product.getProductTotalAmount());
                            giftCount += product.getTotalCount();
                        }else {
                            returnAmount = returnAmount.add(product.getProductTotalAmount());
                            returnCount += product.getTotalCount();
                        }
                        totalCount += product.getTotalCount();
                    }
                    // 组合商品 批次信息
                    rejectProduct = new HashMap<>();
                    String amount = product.getProductAmount().stripTrailingZeros().toPlainString();
                    String productKey = String.format("%s,%s,%s,%s,%s", product.getSkuCode(), product.getWarehouseCode(),
                            product.getProductType(), amount, product.getBatchCode());
                    if(rejectProduct.get(productKey) == null){
                        rejectApplyRecordDetail = BeanCopyUtils.copy(product, RejectApplyRecordDetail.class);
                        rejectProduct.put(productKey, rejectApplyRecordDetail);
                    }
                    rejectProductList.add(rejectProduct.get(productKey));
                }
                transportCenter.setTotalCount(totalCount);
                transportCenter.setProductTaxAmount(productAmount);
                transportCenter.setReturnTaxAmount(returnAmount);
                transportCenter.setGiftTaxAmount(giftAmount);
                productCenterAmount = productCenterAmount.add(productAmount);
                returnCenterAmount = returnCenterAmount.add(returnAmount);
                giftCenterAmount = giftCenterAmount.add(giftAmount);
                totalCountSum += totalCount;
                productCountSum += productCount;
                giftCountSum += giftCount;
                returnCountSum += returnCount;

                transportList.add(transportCenter);
                response.setDetailList(rejectProductList);
            }
            // 审批名称
            String name = detail.getSupplierName() + productList.get(0).getBrandName()
                    + "商品金额" + productCenterAmount + "实物返金额" + returnCenterAmount
                    + "赠品金额" + giftCenterAmount;
            response.setRejectApplyRecordName(name);
            response.setTotalCount(totalCountSum);
            response.setProductCount(productCountSum);
            response.setGiftCount(giftCountSum);
            response.setReturnCount(returnCountSum);
            response.setProductTaxAmount(productCenterAmount);
            response.setGiftTaxAmount(giftCenterAmount);
            response.setReturnTaxAmount(returnCenterAmount);
            response.setCenterList(transportList);
            list.add(response);
        }
        LOGGER.info("分组后的商品信息：" + list.toString());

        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<RejectApplyDetailHandleResponse> applyProductEdit(String rejectApplyRecordCode){
        List<RejectApplyDetailHandleResponse> list = rejectApplyRecordDetailDao.rejectApplyRecordDetailByEdit(rejectApplyRecordCode);
        Map<String, List<StockBatch>> rejectApply = new HashMap<>();
        for (RejectApplyDetailHandleResponse response : list) {
            // 查询对应的批次信息
            String key = String.format("%s,%s,%s", response.getSkuCode(), response.getWarehouseCode(), response.getSupplierCode());
            if(rejectApply.get(key) == null){
                rejectApply.put(key, stockBatchDao.stockBatchByReject(response.getSkuCode(), response.getWarehouseCode(), response.getSupplierCode()));
            }
            response.setBatchList(rejectApply.get(key));
        }
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<List<RejectImportResponse>> rejectApplyImport(MultipartFile file, String purchaseGroupCode) {
        if (file == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<String> productTypeList = Arrays.asList("商品", "赠品", "实物返");
        try {
            String[][] result = FileReaderUtil.readExcel(file, importRejectApplyHeaders.length);
            if (result.length < 2) {
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            List<RejectImportResponse> list = new ArrayList<>();
            String validResult = FileReaderUtil.validStoreValue(result, importRejectApplyHeaders);
            if (StringUtils.isNotBlank(validResult)) {
                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 88888, validResult));
            }
            String[] record;
            SupplyCompany supplier;
            Warehouse warehouse;
            LogisticsCenter logisticsCenter;
            RejectImportResponse response;
            RejectApplyDetailHandleResponse rejectApplyDetailHandleResponse;
            for (int i = 1; i <= result.length - 1; i++) {
                record = result[i];
                response = new RejectImportResponse();
                if (StringUtils.isBlank(record[0]) || StringUtils.isBlank(record[1]) || StringUtils.isBlank(record[2]) || StringUtils.isBlank(record[4]) || StringUtils.isBlank(record[3])||StringUtils.isBlank(record[6])) {
                    response.setErrorInfo(String.format("第%d行,导入的数据不全",i+1));
                    list.add(response);
                    continue;
                }
                supplier = supplyCompanyDao.selectBySupplierName(record[2]);
                if (supplier == null) {
                    response.setErrorInfo(String.format("第%d行,未查询到供应商信息",i));
                    list.add(response);
                    continue;
                }
                logisticsCenter = logisticsCenterDao.selectByCenterName(record[3]);
                if (logisticsCenter == null) {
                    response.setErrorInfo(String.format("第%d行,未查询到仓库信息",i));
                    list.add(response);
                    continue;
                }
                warehouse = warehouseDao.selectByWarehouseName(record[4]);
                if (warehouse == null) {
                    response.setErrorInfo(String.format("第%d行,未查询到库房信息",i));
                    list.add(response);
                    continue;
                }
                rejectApplyDetailHandleResponse = stockDao.rejectProductInfo(supplier.getSupplyCode(),productTypeList.indexOf(record[6]),
                        purchaseGroupCode, logisticsCenter.getLogisticsCenterCode(), warehouse.getWarehouseCode(), record[0]);
                if (rejectApplyDetailHandleResponse != null) {
                    BeanUtils.copyProperties(rejectApplyDetailHandleResponse, response);
                    Integer rejectCount = new Double(record[7]).intValue();
                    response.setProductCount(rejectCount);
                    response.setProductAmount(new BigDecimal(record[8]));
                    if (productTypeList.contains(record[6])) {
                        response.setProductType(productTypeList.indexOf(record[6]));
                    }
                    response.setProductTotalAmount(new BigDecimal(record[8]).multiply(new BigDecimal(rejectCount).setScale(4, BigDecimal.ROUND_HALF_UP)));
                    if (rejectApplyDetailHandleResponse.getStockCount() < rejectCount) {
                        response.setErrorInfo(String.format("第%d行,可用库存数量小于销售数量",i));
                    }
                    List<StockBatch> batchList = stockBatchDao.stockBatchByReject(response.getSkuCode(), response.getWarehouseCode(), response.getSupplierCode());
                    response.setBatchList(batchList);
                } else {
                    response.setErrorInfo(String.format("第%d行,未查询到对应的商品",i));
                }
                list.add(response);
            }
            return HttpResponse.successGenerics(list);
        } catch (Exception e) {
            LOGGER.error("退供申请单导入异常:{}", e);
            return HttpResponse.failure(ResultCode.IMPORT_REJECT_APPLY_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse addApplyReject(RejectApplyGroupRequest request) {
        if(request == null || CollectionUtils.isEmpty(request.getCenterList()) || CollectionUtils.isEmpty(request.getDetailList())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        try {
            // 获取退供申请单编码
            String rejectApplyCode;
            EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.GOODS_REJECT_CODE);
            if(request.getChoiceType() == 0){
                rejectApplyCode = "TS" + encodingRule.getNumberingValue();
            }else {
                rejectApplyCode = request.getRejectApplyRecordCode();
            }
            RejectApplyRecord rejectApplyRecord = BeanCopyUtils.copy(request, RejectApplyRecord.class);
            rejectApplyRecord.setRejectApplyRecordCode(rejectApplyCode);
            if(request.getSubmitType() == 0){
                rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_NO_SUBMIT);
            }else {
                rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_TO_REVIEW);
            }

            for(RejectApplyRecordTransportCenter center:request.getCenterList()){
                center.setRejectApplyRecordCode(rejectApplyCode);
            }
            for(RejectApplyRecordDetail detail:request.getDetailList()){
                detail.setRejectApplyRecordDetailId(IdUtil.uuid());
                detail.setRejectApplyRecordCode(rejectApplyCode);
            }

            // 新增
            if(request.getChoiceType() == 0){
                // 保存退供申请单信息
                Integer rejectCount = rejectApplyRecordDao.insert(rejectApplyRecord);
                LOGGER.info("保存退供申请单条数:{}", rejectCount);

                //更新编码
                encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
            }else {
                Integer rejectCount = rejectApplyRecordDao.update(rejectApplyRecord);
                LOGGER.info("修改退供申请单条数:{}", rejectCount);

                // 删除商品信息
                Integer detailCount = rejectApplyRecordDetailDao.delete(rejectApplyCode);
                LOGGER.info("修改退供申请单删除商品批次条数:{}", detailCount);
                // 删除分仓信息
                Integer centerCount = rejectApplyRecordTransportCenterDao.delete(rejectApplyCode);
                LOGGER.info("修改退供申请单删除分仓信息条数:{}", centerCount);
                // 删除文件信息
                Integer fileCount = fileRecordDao.delete(rejectApplyCode);
                LOGGER.info("修改退供申请单删除文件条数:{}", fileCount);
            }

            // 保存退供申请单商品信息
            Integer productCount = rejectApplyRecordDetailDao.insertAll(request.getDetailList());
            LOGGER.info("保存退供申请单商品信息条数:{}", productCount);

            // 保存退供申请单分仓信息
            Integer centerCount = rejectApplyRecordTransportCenterDao.insertAll(request.getCenterList());
            LOGGER.info("保存退供申请单分仓信息条数:{}", centerCount);

            //保存上传的文件
            if (CollectionUtils.isNotEmpty(request.getFileList())) {
                Integer fileCount = fileRecordDao.insertAll(rejectApplyCode, request.getFileList());
                LOGGER.info("上传文件条数:{}", fileCount);
            }
            //提交退供审批
            if(request.getSubmitType() == 1){
                goodsRejectApprovalService.workFlow(rejectApplyCode, request.getCreateByName(),
                        request.getDirectSupervisorCode(), request.getPositionCode());
            }
        } catch (GroundRuntimeException e) {
            LOGGER.error("新增退供申请单异常:{}", e);
            throw new GroundRuntimeException(String.format("新增退供申请单异常:{%s}", e.getMessage()));
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<PageResData<RejectRecord>> rejectList(RejectQueryRequest rejectApplyQueryRequest) {
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
        if (CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.successGenerics(new PageResData<>());
        }
        rejectApplyQueryRequest.setGroupList(groupVoList);
        List<RejectRecord> list = rejectRecordDao.list(rejectApplyQueryRequest);
        Integer count = rejectRecordDao.listCount(rejectApplyQueryRequest);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<RejectResponse> rejectInfo(String rejectRecordCode) {
        RejectRecord rejectRecord = rejectRecordDao.selectByRejectCode(rejectRecordCode);
        RejectResponse response = BeanCopyUtils.copy(rejectRecord, RejectResponse.class);
        // 查询退供文件
        List<FileRecord> fileList = fileRecordDao.fileList(rejectRecord.getRejectApplyRecordCode());
        // 查询退供日志
        List<OperationLog> operationLogList = operationLogDao.list(rejectRecord.getRejectRecordId());
        response.setLogList(operationLogList);
        response.setFileList(fileList);
        return HttpResponse.successGenerics(response);
    }

    @Override
    public HttpResponse<PageResData<RejectRecordDetail>> rejectProductInfo(String rejectRecordCode){
        List<RejectRecordDetail> list = rejectRecordDetailDao.list(rejectRecordCode);
        Integer count = rejectRecordDetailDao.listCount(rejectRecordCode);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<RejectRecordBatch>> rejectBatchInfo(String rejectRecordCode){
        List<RejectRecordBatch> list = rejectRecordBatchDao.list(rejectRecordCode);
        Integer count = rejectRecordBatchDao.listCount(rejectRecordCode);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse operationRejectRecord(RejectRecord rejectRecord){
        if(rejectRecord == null || StringUtils.isBlank(rejectRecord.getRejectRecordCode()) ||
                StringUtils.isBlank(rejectRecord.getSynchrStatus().toString())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 获取当前登录人的信息
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (currentAuthToken == null) {
            LOGGER.info("获取当前登录信息失败");
            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
        }

        String rejectRecordCode = rejectRecord.getRejectRecordCode();
        // 查询当前的采购单信息
        RejectRecord record = rejectRecordDao.selectByRejectCode(rejectRecordCode);
        if (null == record) {
            LOGGER.info("退供单的信息为空");
            return HttpResponse.failure(ResultCode.REJECT_RECORD_NULL);
        }
        String personId = currentAuthToken.getPersonId();
        String personName = currentAuthToken.getPersonName();
        record.setUpdateById(personId);
        record.setUpdateByName(personName);

        // 退供单状态: 0 待确认 1.待出库 2.出库开始 3.已完成 4.已撤销 5.重发
        Integer status = rejectRecord.getRejectStatus();
        OperationLog log = new OperationLog();
        log.setOperationId(rejectRecordCode);
        log.setCreateById(personId);
        log.setCreateByName(personName);
        log.setOperationType(status);
        log.setRemark("手动");
        switch (status) {
            case 0:
                // 退供单供应商确认
                log.setOperationContent("供应商确认");
                record.setRejectStatus(RejectRecordStatus.REJECT_TO_OUTBOUND);
                break;
            case 4:
                // 退供单撤销
                log.setOperationContent("退供单撤销：" + rejectRecordCode);
                record.setRejectStatus(RejectRecordStatus.REJECT_REVOKE);
                break;
            case 5:
                // 退供单重发

                // 重发成功，变为待出库
                log.setOperationContent("退供单重发：" + rejectRecordCode);
                record.setRejectStatus(RejectRecordStatus.REJECT_TO_OUTBOUND);
                break;
        }
        Integer count = rejectRecordDao.update(record);
        LOGGER.info("操作退供单状态条数：" + count);
        // 添加日志
        Integer logCount = operationLogDao.insert(log);
        LOGGER.info("退供单日志添加条数：" + logCount);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse generateRejectRecord(String rejectApplyRecordCode){
        if(StringUtils.isBlank(rejectApplyRecordCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询退供申请单详情
        RejectApplyRecord rejectApplyRecord = rejectApplyRecordDao.selectByRejectCode(rejectApplyRecordCode);
        LOGGER.info("退供申请单信息：" + rejectApplyRecord.toString());
        if(rejectApplyRecord == null){
            return HttpResponse.failure(ResultCode.REJECT_APPLY_NULL);
        }

        // 查询退供申请单的分仓信息
        RejectApplyRecordTransportCenter applyCenter = new RejectApplyRecordTransportCenter();
        applyCenter.setRejectApplyRecordCode(rejectApplyRecordCode);
        List<RejectApplyRecordTransportCenter> applyCenters = rejectApplyRecordTransportCenterDao.rejectApplyTransportCenterInfo(applyCenter);
        LOGGER.info("退供申请单分仓信息：" + applyCenters.toString());
        if(CollectionUtils.isEmpty(applyCenters)) {
            return HttpResponse.failure(ResultCode.REJECT_APPLY_CENTER_NULL);
        }
        List<RejectRecord> rejectRecordList = Lists.newArrayList();
        RejectRecord rejectRecord = BeanCopyUtils.copy(rejectApplyRecord, RejectRecord.class);
        rejectRecord.setRejectRecordName(rejectApplyRecord.getRejectApplyRecordName());
        rejectRecord.setSourceType(0);
        rejectRecord.setSourceCode(rejectApplyRecord.getRejectApplyRecordCode());
        rejectRecord.setRejectStatus(RejectRecordStatus.REJECT_TO_OUTBOUND);
        rejectRecord.setApprovalCode(rejectApplyRecord.getRejectApplyRecordCode());

        // 查询退供单规则单号
        String code = DateUtils.currentDate().replaceAll("-","");
        String recordCode = rejectRecordDao.rejectRecordByCode(code);

        for(RejectApplyRecordTransportCenter center:applyCenters){
            Long rejectRecordCode;
            if(StringUtils.isBlank(recordCode)){
                String newRecordCode = code + "0001";
                rejectRecordCode = Long.valueOf(newRecordCode);
            }else {
                rejectRecordCode = Long.valueOf(recordCode) + 1;
            }
            rejectRecord.setRejectRecordId(IdUtil.rejectRecordId());
            rejectRecord.setRejectRecordCode(rejectRecordCode.toString());
            rejectRecord.setValidTime(center.getValidTime());
            rejectRecord.setPreDeliverTime(center.getPreDeliverTime());
            rejectRecord.setTransportCenterCode(center.getTransportCenterCode());
            rejectRecord.setTransportCenterName(center.getTransportCenterName());
            rejectRecord.setWarehouseCode(center.getWarehouseCode());
            rejectRecord.setWarehouseName(center.getWarehouseName());

            ++rejectRecordCode;
        }






        return HttpResponse.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpResponse rejectCancel(String rejectRecordId) {
        try {
            RejectRecord record = rejectRecordDao.selectByRejectId(rejectRecordId);
            if (record == null) {
                return HttpResponse.failure(ResultCode.NOT_HAVE_REJECT_RECORD);
            }
            RejectRecord rejectRecord = new RejectRecord();
            rejectRecord.setRejectRecordId(rejectRecordId);
            //rejectRecord.setRejectStatus(RejectRecordStatus.REJECT_STATUS_CANCEL);
            Integer count = rejectRecordDao.updateStatus(rejectRecord);
            LOGGER.info("取消-更改退供申请详情影响条数:{}", count);
            List<RejectRecordDetail> list = rejectRecordDetailDao.selectByRejectId(rejectRecordId);
            //解锁库存
            ILockStocksReqVO iLockStockBatchReqVO = handleStockParam(list, record);
            Boolean stockStatus = stockService.returnSupplyUnLockStocks(iLockStockBatchReqVO);
            if (!stockStatus) {
                LOGGER.error("解锁库存异常:{}", rejectRecord.toString());
                throw new GroundRuntimeException("解锁库存异常");
            }
//            if(record.getRejectStatus().equals(RejectRecordStatus.REJECT_STATUS_AUDIT)||record.getRejectStatus().equals(RejectRecordStatus.REJECT_STATUS_AUDITTING)){
//                WorkFlowVO w = new WorkFlowVO();
//                w.setFormNo(record.getRejectRecordCode());
//                WorkFlowRespVO workFlowRespVO = cancelWorkFlow(w);
//                if (!workFlowRespVO.getSuccess()) {
//                    throw new GroundRuntimeException("审批流撤销失败!");
//                }
//            }
            return HttpResponse.success();
        } catch (GroundRuntimeException e) {
            LOGGER.error("取消-更改退供申请异常:{}", e);
            throw new GroundRuntimeException(String.format("取消-更改退供申请异常:{%s}", e.getMessage()));
        }
    }

    public ILockStocksReqVO handleStockParam(List<RejectRecordDetail> detailList, RejectRecord rejectRecord) {
        ILockStocksReqVO ILockStocksReqVO = new ILockStocksReqVO();
        ILockStocksReqVO.setCompanyCode(rejectRecord.getCompanyCode());
        ILockStocksReqVO.setTransportCenterCode(rejectRecord.getTransportCenterCode());
        ILockStocksReqVO.setWarehouseCode(rejectRecord.getWarehouseCode());
        ILockStocksReqVO.setPurchaseGroupCode(rejectRecord.getPurchaseGroupCode());
        List<ILockStocksItemReqVo> list = new ArrayList<>();
        ILockStocksItemReqVo itemReqVo;
        for (RejectRecordDetail detail : detailList) {
            itemReqVo = new ILockStocksItemReqVo();
            //itemReqVo.setNum(Long.valueOf(detail.getSingleCount()));
            itemReqVo.setSkuCode(detail.getSkuCode());
            itemReqVo.setSkuName(detail.getSkuName());
            itemReqVo.setDocumentType(2);
            itemReqVo.setDocumentNum(detail.getRejectRecordCode());
            itemReqVo.setRemark(rejectRecord.getRemark());
            itemReqVo.setOperator(rejectRecord.getCreateByName());
            list.add(itemReqVo);
        }
        ILockStocksReqVO.setItemReqVos(list);
        return ILockStocksReqVO;
    }

    /**
     * 根据品类code 查询所有的名称(包含父级)
     */
    public String selectCategoryName(String categoryCode) {
        return dataManageService.selectCategoryName(categoryCode);
    }

}
