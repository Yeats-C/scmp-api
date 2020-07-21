package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.dl.BatchRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.ProductRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.StockChangeRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.purchase.ScmpPurchaseBatch;
import com.aiqin.bms.scmp.api.abutment.service.DlAbutmentService;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.constant.RejectRecordStatus;
import com.aiqin.bms.scmp.api.product.dao.OutboundDao;
import com.aiqin.bms.scmp.api.product.dao.StockBatchDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuBatchMapper;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectDetailStockRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectStockRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.reject.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.wms.CancelSource;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyAndTransportResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyDetailHandleResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectApplyGroupResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectResponse;
import com.aiqin.bms.scmp.api.purchase.manager.DataManageService;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.WmsCancelService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.service.FormOperateService;
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
    private OutboundService outboundService;
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
    @Resource
    private OutboundDao outboundDao;
    @Resource
    private StockService stockService;
    @Resource
    private SapBaseDataService sapBaseDataService;
    @Resource
    private FormOperateService formOperateService;
    @Resource
    private WmsCancelService wmsCancelService;
    @Resource
    private ProductSkuBatchMapper productSkuBatchDao;
    @Resource
    private DlAbutmentService dlService;

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
            if(response.getBatchManage().equals(Global.BATCH_MANAGE_0)){
                response.setSkuBatchManage(Global.WAREHOUSE_BATCH_MANAGE_SKU_1);
                continue;
            }else if(response.getBatchManage().equals(Global.BATCH_MANAGE_2)){
                Integer exist = productSkuBatchDao.productSkuBatchExist(response.getSkuCode(), response.getWarehouseCode());
                if(exist > 0){
                    response.setSkuBatchManage(Global.WAREHOUSE_BATCH_MANAGE_SKU_0);
                }else {
                    response.setSkuBatchManage(Global.WAREHOUSE_BATCH_MANAGE_SKU_1);
                }
            }else {
                response.setSkuBatchManage(Global.WAREHOUSE_BATCH_MANAGE_SKU_0);
            }
            // 查询对应的批次信息
            String key = this.rejectBatch(response, rejectApply);
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
        List<FileRecord> fileRecords = fileRecordDao.fileList(rejectApplyRecordCode);
        if(CollectionUtils.isNotEmpty(fileRecords)){
            response.setFileList(fileRecords);
        }
        return HttpResponse.successGenerics(response);
    }

    private String rejectBatch(RejectApplyDetailHandleResponse response, Map<String, List<StockBatch>> rejectApply){
        // 查询对应的批次信息
        String key;
        if(response.getBatchManage() == 2){
            // 为部分指点批次时候，查询sku是否有批次
            Integer exist = productSkuBatchDao.productSkuBatchExist(response.getSkuCode(), response.getWarehouseCode());
            key = String.format("%s,%s,%s", response.getSkuCode(), response.getWarehouseCode(), null);
            if(exist > 0){
                if(rejectApply.get(key) == null){
                    rejectApply.put(key, stockBatchDao.stockBatchByReject(response.getSkuCode(), response.getWarehouseCode(), null));
                }
            }else {
                rejectApply.put(key, null);
            }
        }else {
            key = String.format("%s,%s,%s", response.getSkuCode(), response.getWarehouseCode(), response.getSupplierCode());
            if(rejectApply.get(key) == null){
                rejectApply.put(key, stockBatchDao.stockBatchByReject(response.getSkuCode(), response.getWarehouseCode(), response.getSupplierCode()));
            }
        }
        return key;
    }

    @Override
    public HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyProduct(RejectApplyQueryRequest request) {
        List<RejectApplyRecordDetail> list = rejectApplyRecordDetailDao.rejectApplyRecordDetailList(request);
        Integer count = rejectApplyRecordDetailDao.rejectApplyRecordDetailCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<RejectApplyRecordDetail>> selectRejectApplyBatch(RejectApplyQueryRequest request) {
        List<RejectApplyRecordDetail> list = rejectApplyRecordDetailDao.rejectApplyRecordBatchList(request);
        Integer count = rejectApplyRecordDetailDao.rejectApplyRecordBatchCount(request);
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
                        rejectApplyRecordDetail.setBatchPurchasePrice(product.getPurchasePrice());
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
            WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(response.getWarehouseCode());
            response.setBatchManage(warehouse.getBatchManage());
            if(response.getBatchManage() == 0){
                continue;
            }
            // 查询对应的批次信息
            String key = this.rejectBatch(response, rejectApply);
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

            Map<String, List<StockBatch>> rejectApply = new HashMap<>();
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
                    if(warehouse.getBatchManage() == 0){
                        continue;
                    }
                    // 查询对应的批次信息
                    String key = this.rejectBatch(response, rejectApply);
                    response.setBatchList(rejectApply.get(key));
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

        // 获取当前登录人的信息
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (authToken == null) {
            LOGGER.info("获取当前登录信息失败");
            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
        }
        try {
            // 获取退供申请单编码
            String rejectApplyCode;
            EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.GOODS_REJECT_CODE);
            RejectApplyRecord rejectApplyRecord = BeanCopyUtils.copy(request, RejectApplyRecord.class);
            if(request.getChoiceType() == 0){
                rejectApplyCode = "TS" + encodingRule.getNumberingValue();
                rejectApplyRecord.setCreateById(authToken.getPersonId());
                rejectApplyRecord.setCreateByName(authToken.getPersonName());
            }else {
                rejectApplyCode = request.getRejectApplyRecordCode();
            }
            rejectApplyRecord.setRejectApplyRecordCode(rejectApplyCode);
            rejectApplyRecord.setCompanyCode(authToken.getCompanyCode());
            rejectApplyRecord.setCompanyName(authToken.getCompanyName());

            rejectApplyRecord.setUpdateById(authToken.getPersonId());
            rejectApplyRecord.setUpdateByName(authToken.getPersonName());
            rejectApplyRecord.setApplyType(0);
            if(request.getSubmitType() == 0){
                rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_NO_SUBMIT);
            }else {
                rejectApplyRecord.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_TO_REVIEW);
            }

            for(RejectApplyRecordTransportCenter center:request.getCenterList()){
                center.setRejectApplyRecordCode(rejectApplyCode);
                center.setRejectApplyRecordName(rejectApplyRecord.getRejectApplyRecordName());
            }
            Integer i = 1;
            for(RejectApplyRecordDetail detail:request.getDetailList()){
                detail.setProductCount(detail.getTotalCount());
                detail.setRejectApplyRecordDetailId(IdUtil.uuid());
                detail.setRejectApplyRecordCode(rejectApplyCode);
                detail.setCreateById(authToken.getPersonId());
                detail.setCreateByName(authToken.getPersonName());
                detail.setUpdateById(authToken.getPersonId());
                detail.setUpdateByName(authToken.getPersonName());
                detail.setLineCode(i);
                ++ i;
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
    public HttpResponse<PageResData<RejectRecordDetail>> rejectProductInfo(RejectQueryRequest request){
        List<RejectRecordDetail> list = rejectRecordDetailDao.list(request);
        Integer count = rejectRecordDetailDao.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<RejectRecordBatch>> rejectBatchInfo(RejectQueryRequest request){
        List<RejectRecordBatch> list = rejectRecordBatchDao.list(request);
        Integer count = rejectRecordBatchDao.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse operationRejectRecord(RejectRecord rejectRecord){
        if(rejectRecord == null || StringUtils.isBlank(rejectRecord.getRejectRecordCode()) ||
                StringUtils.isBlank(rejectRecord.getRejectStatus().toString())){
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
                if(!record.getRejectStatus().equals(RejectRecordStatus.REJECT_NO_SUBMIT)){
                    LOGGER.info("退供单非待确认状态，不可以进行供应商确认的操作:{}", JsonUtil.toJson(record));
                    return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退供单非待确认状态，不可以进行供应商确认的操作"));
                }
                // 退供单供应商确认
                log.setOperationContent("供应商确认");
                record.setRejectStatus(RejectRecordStatus.REJECT_TO_OUTBOUND);
                break;
            case 4:
                if(!record.getRejectStatus().equals(RejectRecordStatus.REJECT_NO_SUBMIT) &&
                        !record.getRejectStatus().equals(RejectRecordStatus.REJECT_TO_OUTBOUND)){
                    LOGGER.info("退供单非待确认、待出库状态，不可以进行撤销的操作:{}", JsonUtil.toJson(record));
                    return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退供单非待确认、待出库状态，不可以进行撤销的操作"));
                }
                // 退供单撤销
                log.setOperationContent("退供单撤销：" + rejectRecordCode);
                record.setRejectStatus(RejectRecordStatus.REJECT_REVOKE);
                // 解锁库存、批次库存
                this.rejectStock(3, rejectRecord);
                // 撤销wms退供单
                if(record.getRejectStatus().equals(RejectRecordStatus.REJECT_TO_OUTBOUND)){
                    CancelSource cancelSource = new CancelSource();
                    cancelSource.setOrderType("4");
                    cancelSource.setOrderCode(record.getRejectRecordCode());
                    cancelSource.setWarehouseCode(record.getWarehouseCode());
                    cancelSource.setWarehouseName(record.getWarehouseName());
                    HttpResponse response = wmsCancelService.wmsCancel(cancelSource);
                    if(!response.getCode().equals(MessageId.SUCCESS_CODE)){
                        LOGGER.info("取消退供单失败：{}", response.getMessage());
                        return HttpResponse.failure(MessageId.create(Project.SCMP_API, 200, response.getMessage()));
                    }
                }
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
        RejectRecord rejectRecord = BeanCopyUtils.copy(rejectApplyRecord, RejectRecord.class);
        rejectRecord.setRejectRecordName(rejectApplyRecord.getRejectApplyRecordName());
        rejectRecord.setSourceType(0);
        rejectRecord.setSourceCode(rejectApplyRecord.getRejectApplyRecordCode());
        rejectRecord.setRejectStatus(RejectRecordStatus.REJECT_TO_OUTBOUND);
        rejectRecord.setApprovalCode(rejectApplyRecord.getRejectApplyRecordCode());

        // 查询退供单规则单号
        String code = DateUtils.currentDate().replaceAll("-","");
        List<RejectRecordBatch> batchList;
        ReturnSupplyToOutBoundReqVo reqVo;
        for(RejectApplyRecordTransportCenter center:applyCenters){

            String recordCode = rejectRecordDao.rejectRecordByCode(code);
            Long rejectRecordCode;

            // 查询对应库房的批次管理
            WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(center.getWarehouseCode());
            LOGGER.info("生成退供单，查询对应库房的批次管理信息：{}", JsonUtil.toJson(warehouse));

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

            // 查询对应库房的商品信息
            List<RejectRecordDetail> recordDetails = rejectApplyRecordDetailDao.rejectApplyByWarehouseProduct(rejectApplyRecordCode, center.getWarehouseCode());
            // 每个sku的批次信息
            Map<String, List<RejectRecordBatch>> rejectBatchMap = null;
            if(!warehouse.getBatchManage().equals(0)){
                rejectBatchMap = new HashMap<>();
                for(RejectRecordDetail detail:recordDetails) {
                    String key = String.format("%s,%s,%s", rejectApplyRecordCode, center.getWarehouseCode(), detail.getSkuCode(),
                            rejectRecord.getSupplierCode(), rejectRecord.getSettlementMethodCode());
                    if(rejectBatchMap.get(key) == null){
                        rejectBatchMap.put(key, rejectApplyRecordDetailDao.rejectApplyByWarehouseBatch(rejectApplyRecordCode,
                                center.getWarehouseCode(), detail.getSkuCode(), rejectRecord.getSupplierCode(), rejectRecord.getSettlementMethodCode(),
                                detail.getProductType()));
                    }
                }
            }

            Long productCount = 0L, returnCount = 0L, giftCount = 0L, totalCount = 0L;
            BigDecimal productTaxAmount = big, returnTaxAmount = big, giftTaxAmount = big;
            Integer i = 1;
            batchList = Lists.newArrayList();
            for(RejectRecordDetail detail:recordDetails){
                detail.setRejectRecordDetailId(IdUtil.uuid());
                detail.setRejectRecordId(rejectRecord.getRejectRecordId());
                detail.setRejectRecordCode(rejectRecord.getRejectRecordCode());
               // 计算退供单的总商品数量与金额 0商品 1赠品 2实物返
               if(detail.getProductType().equals(Global.PRODUCT_TYPE_0)){
                   productCount += detail.getProductCount();
                   productTaxAmount = productTaxAmount.add(detail.getProductTotalAmount());
               }else if(detail.getProductType().equals(Global.PRODUCT_TYPE_1)){
                   giftCount += detail.getProductCount();
                   giftTaxAmount = giftTaxAmount.add(detail.getProductTotalAmount());
               }else if(detail.getProductType().equals(Global.PRODUCT_TYPE_2)){
                   returnCount += detail.getProductCount();
                   returnTaxAmount = returnTaxAmount.add(detail.getProductTotalAmount());
               }
                totalCount += detail.getTotalCount();
                detail.setLineCode(i);

                if(!warehouse.getBatchManage().equals(0)){
                    // 查询对应的批次
                    String key = String.format("%s,%s,%s", rejectApplyRecordCode, center.getWarehouseCode(), detail.getSkuCode(),
                            rejectRecord.getSupplierCode(), rejectRecord.getSettlementMethodCode());
                    List<RejectRecordBatch> rejectRecordBatches = rejectBatchMap.get(key);
                    for(RejectRecordBatch recordBatch : rejectRecordBatches){
                        recordBatch.setRejectRecordCode(rejectRecord.getRejectRecordCode());
                        recordBatch.setLineCode(i);
                        batchList.add(recordBatch);
                    }
                }
                ++i;
            }
            rejectRecord.setProductCount(productCount);
            rejectRecord.setGiftCount(giftCount);
            rejectRecord.setReturnCount(returnCount);
            rejectRecord.setTotalCount(totalCount);
            rejectRecord.setProductTaxAmount(productTaxAmount);
            rejectRecord.setReturnTaxAmount(returnTaxAmount);
            rejectRecord.setGiftTaxAmount(giftTaxAmount);

            Integer rejectCount = rejectRecordDao.insert(rejectRecord);
            LOGGER.info("添加退供单信息条数：" + rejectCount);

            Integer detailCount = rejectRecordDetailDao.insertAll(recordDetails);
            LOGGER.info("添加退供单商品信息条数：" + detailCount);

            if(CollectionUtils.isNotEmpty(batchList)){
                Integer batchCount = rejectRecordBatchDao.insertAll(batchList);
                LOGGER.info("添加退供单批次信息条数：" + batchCount);
            }

            if(rejectCount > 0){
                // 调用生成出库单
                reqVo = new ReturnSupplyToOutBoundReqVo();
                reqVo.setRejectRecord(rejectRecord);
                reqVo.setDetailList(recordDetails);
                reqVo.setBatchList(batchList);
                LOGGER.info("调用退供出库单:{}", JsonUtil.toJson(reqVo));
                outboundService.returnSupplySave(reqVo);
                // 指定批次的sku锁定库存、批次库存
                this.rejectStock(1, rejectRecord);
            }
        }
        return HttpResponse.success();
    }

    private HttpResponse rejectStock(Integer type, RejectRecord rejectRecord) {
        LOGGER.info("开始操作退供单的库存、批次库存:", rejectRecord);
        ChangeStockRequest request = new ChangeStockRequest();
        request.setOperationType(type);
        List<StockInfoRequest> list = Lists.newArrayList();
        StockInfoRequest stockInfo;
        List<StockBatchInfoRequest> stockBatchList = Lists.newArrayList();
        StockBatchInfoRequest stockBatchInfo;

        // 查询出库单号
        Outbound outbound = outboundDao.selectBySourceCode(rejectRecord.getRejectRecordCode(), String.valueOf(OutboundTypeEnum.RETURN_SUPPLY.getCode()));
        // 查询库房类型
        WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(rejectRecord.getWarehouseCode());
        // 查询退供单的商品
        List<RejectRecordDetail> detailList = rejectRecordDetailDao.selectByRejectId(rejectRecord.getRejectRecordId());
        if (CollectionUtils.isEmpty(detailList)) {
            LOGGER.info("锁库存、批次库存，退供单的商品信息为空:", detailList);
            return HttpResponse.failure(ResultCode.REJECT_PRODUCT_NULL);
        }

        for (RejectRecordDetail product : detailList) {
            stockInfo = BeanCopyUtils.copy(product, StockInfoRequest.class);
            if (warehouse != null) {
                stockInfo.setWarehouseType(warehouse.getWarehouseTypeCode().intValue());
            }
            stockInfo.setCompanyCode(rejectRecord.getCompanyCode());
            stockInfo.setCompanyName(rejectRecord.getCompanyName());
            stockInfo.setTransportCenterCode(rejectRecord.getTransportCenterCode());
            stockInfo.setTransportCenterName(rejectRecord.getTransportCenterName());
            stockInfo.setWarehouseCode(rejectRecord.getWarehouseCode());
            stockInfo.setWarehouseName(rejectRecord.getWarehouseName());
            stockInfo.setDocumentCode(outbound.getOutboundOderCode());
            stockInfo.setDocumentType(Global.OUTBOUND_TYPE);
            stockInfo.setSourceDocumentCode(rejectRecord.getRejectRecordCode());
            stockInfo.setSourceDocumentType(2);
            stockInfo.setOperatorId(rejectRecord.getCreateById());
            stockInfo.setOperatorName(rejectRecord.getCreateByName());
            stockInfo.setChangeCount(product.getTotalCount());
            list.add(stockInfo);
        }
        request.setStockList(list);

        // 查询退供单的批次信息
        List<RejectRecordBatch> recordBatches = rejectRecordBatchDao.rejectBatchInfoList(rejectRecord.getRejectRecordCode());
        if(CollectionUtils.isNotEmpty(recordBatches) && recordBatches.size() > 0) {
            if (warehouse.getBatchManage().equals(Global.BATCH_MANAGE_1) || warehouse.getBatchManage().equals(Global.BATCH_MANAGE_2)) {
                String key;
                Map<String, Long> actualTotalCountMap = new HashMap<>();
                for (RejectRecordBatch batch : recordBatches) {
                    key = String.format("%s,%s", batch.getSkuCode(), batch.getBatchCode());
                    if (actualTotalCountMap.get(key) == null) {
                        actualTotalCountMap.put(key, batch.getActualTotalCount());
                    } else {
                        actualTotalCountMap.put(key, batch.getActualTotalCount() + actualTotalCountMap.get(batch.getSkuCode()));
                    }
                }

                Map<String, StockBatchInfoRequest> actualMap = new HashMap<>();
                for (RejectRecordBatch batch : recordBatches) {
                    // 判断sku是否为指定批次，退供为指定批次时减批次可用库存并锁定批次库存
                    Integer exist = productSkuBatchDao.productSkuBatchExist(batch.getSkuCode(), warehouse.getWarehouseCode());
                    if (exist <= 0 || warehouse.getBatchManage().equals(Global.BATCH_MANAGE_2)) {
                        continue;
                    }
                    key = String.format("%s,%s", batch.getSkuCode(), batch.getBatchCode());

                    if(actualMap.get(key) == null){
                        stockBatchInfo = BeanCopyUtils.copy(batch, StockBatchInfoRequest.class);
                        if (warehouse != null) {
                            stockBatchInfo.setWarehouseType(warehouse.getWarehouseTypeCode().toString());
                        }
                        stockBatchInfo.setCompanyCode(rejectRecord.getCompanyCode());
                        stockBatchInfo.setCompanyName(rejectRecord.getCompanyName());
                        stockBatchInfo.setTransportCenterCode(rejectRecord.getTransportCenterCode());
                        stockBatchInfo.setTransportCenterName(rejectRecord.getTransportCenterName());
                        stockBatchInfo.setWarehouseCode(rejectRecord.getWarehouseCode());
                        stockBatchInfo.setWarehouseName(rejectRecord.getWarehouseName());
                        stockBatchInfo.setDocumentCode(outbound.getOutboundOderCode());
                        stockBatchInfo.setDocumentType(Global.OUTBOUND_TYPE);
                        stockBatchInfo.setSourceDocumentCode(rejectRecord.getRejectRecordCode());
                        stockBatchInfo.setSourceDocumentType(Global.DOCUMENT_TYPE_2);
                        stockBatchInfo.setOperatorId(rejectRecord.getCreateById());
                        stockBatchInfo.setOperatorName(rejectRecord.getCreateByName());
                        stockBatchInfo.setChangeCount(actualTotalCountMap.get(key));
                        stockBatchInfo.setTaxCost(batch.getPurchasePrice());
                        stockBatchList.add(stockBatchInfo);

                        actualMap.put(key, stockBatchInfo);
                    }
                }
                request.setStockBatchList(stockBatchList);
            }
        }
        LOGGER.info("操作退供单的库存、批次库存的参数：{}", JsonUtil.toJson(request));
        HttpResponse response = stockService.stockAndBatchChange(request);
        if(response.getCode().equals(MessageId.SUCCESS_CODE)){
            LOGGER.info("操作退供单的库存、批次库存成功");
            return HttpResponse.success();
        }else {
            LOGGER.info("操作退供单的库存、批次库存失败:", response.getMessage());
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, response.getMessage()));
        }
    }

    /**
     * 根据品类code 查询所有的名称(包含父级)
     */
    public String selectCategoryName(String categoryCode) {
        return dataManageService.selectCategoryName(categoryCode);
    }

    @Override
    public HttpResponse rejectRecordWms(RejectStockRequest request){
        LOGGER.info("wms回传，开始更新退供单参数：{}", JsonUtil.toJson(request));
        if(request == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询对应的退供单信息
        RejectRecord rejectRecord = rejectRecordDao.selectByRejectCode(request.getRejectRecordCode());
        rejectRecord.setOutStockTime(Calendar.getInstance().getTime());
        rejectRecord.setRejectStatus(RejectRecordStatus.REJECT_OUTBOUND_COMPLETE);

        RejectRecordDetail rejectRecordDetail;
        RejectRecordBatch recordBatch;
        List<RejectRecordBatch> recordBatches = Lists.newArrayList();
        Long actualProductCount = 0L, actualReturnCount = 0L, actualGiftCount = 0L, actualTotalCount = 0L;
        BigDecimal actualProductAmount = BigDecimal.ZERO, actualReturnAmount = BigDecimal.ZERO, actualGiftAmount = BigDecimal.ZERO;

        for(RejectDetailStockRequest detail:request.getDetailList()){
            // 查询对应的退供单商品信息
            rejectRecordDetail = rejectRecordDetailDao.rejectRecordByLineCode(request.getRejectRecordCode(), detail.getLineCode());
            rejectRecordDetail.setActualTotalCount(detail.getActualCount());
            rejectRecordDetail.setActualProductTotalAmount(detail.getActualAmount());
            Integer count = rejectRecordDetailDao.update(rejectRecordDetail);
            LOGGER.info("更新退供单商品回传实际数值：{}", count);

            // 商品类型  0商品 1赠品 2实物返
            if(rejectRecordDetail.getProductType().equals(Global.PRODUCT_TYPE_0)){
                actualProductCount += detail.getActualCount();
                actualProductAmount = actualProductAmount.add(detail.getActualAmount());
            }else if(rejectRecordDetail.getProductType().equals(Global.PRODUCT_TYPE_1)){
                actualGiftCount += detail.getActualCount();
                actualGiftAmount = actualGiftAmount.add(detail.getActualAmount());
            }else if(rejectRecordDetail.getProductType().equals(Global.PRODUCT_TYPE_2)){
                actualReturnCount += detail.getActualCount();
                actualReturnAmount = actualReturnAmount.add(detail.getActualAmount());
            }
            actualTotalCount += detail.getActualCount();

            // 自动批次管理，wms回传添加退供单的批次
            if(request.getBatchManage().equals(0)){
                recordBatch = new RejectRecordBatch();
                recordBatch.setRejectRecordCode(rejectRecord.getRejectRecordCode());
                String batchCode = DateUtils.currentDate().replaceAll("-","");
                recordBatch.setBatchCode(batchCode);
                String batchInfoCode = rejectRecordDetail.getSkuCode() + "_" + rejectRecord.getWarehouseCode() + "_" +
                        batchCode + "_" + rejectRecord.getSupplierCode() + "_" +
                        rejectRecordDetail.getProductAmount().stripTrailingZeros().toPlainString();
                recordBatch.setBatchInfoCode(batchInfoCode);
                recordBatch.setSkuCode(rejectRecordDetail.getSkuCode());
                recordBatch.setSkuName(rejectRecordDetail.getSkuName());
                recordBatch.setSupplierCode(rejectRecord.getSupplierCode());
                recordBatch.setSupplierName(rejectRecord.getSupplierName());
                recordBatch.setProductDate(DateUtils.currentDate());
                recordBatch.setTotalCount(rejectRecordDetail.getTotalCount());
                recordBatch.setActualTotalCount(detail.getActualCount());
                recordBatch.setLineCode(detail.getLineCode());
                recordBatches.add(recordBatch);
            }
        }
        rejectRecord.setActualProductCount(actualProductCount);
        rejectRecord.setActualReturnCount(actualReturnCount);
        rejectRecord.setActualGiftCount(actualGiftCount);
        rejectRecord.setActualTotalCount(actualTotalCount);
        rejectRecord.setActualProductTaxAmount(actualProductAmount);
        rejectRecord.setActualGiftTaxAmount(actualGiftAmount);
        rejectRecord.setActualReturnTaxAmount(actualReturnAmount);

        // 更新批次的实际信息
        if(CollectionUtils.isNotEmpty(request.getBatchList()) && request.getBatchList().size() > 0 &&
                !request.getBatchManage().equals(Global.BATCH_MANAGE_0)){

            RejectRecordBatch rejectBatchListOne;
            List<RejectRecordBatch> notRecordBatches = Lists.newArrayList();

            for (RejectRecordBatch batch : request.getBatchList()){
                Integer exist = 0;
                if(request.getBatchManage().equals(Global.BATCH_MANAGE_2)){
                    exist = productSkuBatchDao.productSkuBatchExist(batch.getSkuCode(), rejectRecord.getWarehouseCode());
                }
                // 指定批次
                if(exist > 0 || request.getBatchManage().equals(Global.BATCH_MANAGE_1)){
                    rejectBatchListOne = rejectRecordBatchDao.rejectBatchListOne(batch.getBatchCode(), rejectRecord.getRejectRecordCode(), batch.getLineCode());
                    if(rejectBatchListOne == null){
                        rejectBatchListOne = rejectRecordBatchDao.rejectBatchListOne(null, rejectRecord.getRejectRecordCode(), batch.getLineCode());
                    }
                    if(rejectBatchListOne != null){
                        rejectBatchListOne.setActualTotalCount(batch.getActualTotalCount());
                        Integer update = rejectRecordBatchDao.update(rejectBatchListOne);
                        LOGGER.info("更新退供的批次信息:{}", update);
                    }else {
                        notRecordBatches.add(batch);
                    }
                }else {
                    // 非指定批次
                    List<StockBatch> batchList = stockBatchDao.stockBatchByOutbound(batch.getSkuCode(),
                            rejectRecord.getWarehouseCode(), batch.getBatchCode());
                    BigDecimal amount = BigDecimal.ZERO;
                    if(CollectionUtils.isNotEmpty(batchList) && batchList.size() > 0){
                        amount = batchList.get(0).getPurchasePrice() == null ? BigDecimal.ZERO : batchList.get(0).getPurchasePrice();
                    }
                    recordBatch = new RejectRecordBatch();
                    recordBatch.setRejectRecordCode(rejectRecord.getRejectRecordCode());
                    recordBatch.setBatchCode(batch.getBatchCode());
                    String batchInfoCode = batch.getSkuCode() + "_" + rejectRecord.getWarehouseCode() + "_" +
                            batch.getBatchCode() + "_" + rejectRecord.getSupplierCode() + "_" +
                            amount.stripTrailingZeros().toPlainString();
                    recordBatch.setBatchInfoCode(batchInfoCode);
                    recordBatch.setSkuCode(batch.getSkuCode());
                    recordBatch.setSkuName(batch.getSkuName());
                    recordBatch.setSupplierCode(rejectRecord.getSupplierCode());
                    recordBatch.setSupplierName(rejectRecord.getSupplierName());
                    recordBatch.setProductDate(DateUtils.currentDate());
                    recordBatch.setTotalCount(batch.getTotalCount());
                    recordBatch.setActualTotalCount(batch.getActualTotalCount());
                    recordBatch.setLineCode(batch.getLineCode());
                    recordBatch.setPurchasePrice(amount);
                    recordBatches.add(recordBatch);
                }
            }
            if(CollectionUtils.isNotEmpty(notRecordBatches) && notRecordBatches.size() > 0){
                LOGGER.info("wms回传成功，指定批次未查询到的批次信息：{}", JsonUtil.toJson(notRecordBatches));
            }
        }

        if(CollectionUtils.isNotEmpty(recordBatches) && recordBatches.size() > 0){
            Integer count = rejectRecordBatchDao.insertAll(recordBatches);
            LOGGER.info("wms回传成功，新增退供批次信息：{}", count);
        }

        Integer count = rejectRecordDao.update(rejectRecord);
        LOGGER.info("wms回传成功，更新退供单：{}", count);
        if(count > 0){
            // 调用sap 传送退供单的数据给sap
            //sapBaseDataService.purchaseAndReject(rejectRecord.getRejectRecordCode(), 1);

            // 调用DL，把退供的库存变更推送给DL
            StockChangeRequest dlRequest = new StockChangeRequest();
            dlRequest.setOrderCode(rejectRecord.getRejectRecordCode());
            dlRequest.setOrderType(Global.DL_ORDER_TYPE_1);
            dlRequest.setOperationType(Global.DL_OPERATION_TYPE_1);
            dlRequest.setSupplierCode(rejectRecord.getSupplierCode());
            dlRequest.setWarehouseCode(rejectRecord.getWarehouseCode());
            dlRequest.setWarehouseName(rejectRecord.getWarehouseName());
            dlRequest.setOperationCode(rejectRecord.getUpdateById());
            dlRequest.setOperationName(rejectRecord.getUpdateByName());
            dlRequest.setPreArrivalTime(rejectRecord.getPreDeliverTime());
            dlRequest.setTotalCount(rejectRecord.getActualTotalCount());

            List<ProductRequest> dlProductList = Lists.newArrayList();
            List<BatchRequest> dlBatchList;
            ProductRequest dlProduct;
            BatchRequest dlBatch;

            for(RejectDetailStockRequest detail:request.getDetailList()){
                rejectRecordDetail = rejectRecordDetailDao.rejectRecordByLineCode(request.getRejectRecordCode(), detail.getLineCode());
                dlProduct = new ProductRequest();
                dlProduct.setLineCode(detail.getLineCode());
                dlProduct.setSkuCode(rejectRecordDetail.getSkuCode());
                dlProduct.setTotalCount(rejectRecordDetail.getActualTotalCount());
                dlProduct.setProductAmount(rejectRecordDetail.getActualProductTotalAmount());
                dlProduct.setTaxRate(rejectRecordDetail.getTaxRate());
                Integer productType = rejectRecordDetail.getProductType() == 3 ? 1 : (rejectRecordDetail.getProductType() == 2 ? 2 : 1);
                dlProduct.setProductType(productType);
                BigDecimal noTaxPrice = Calculate.computeNoTaxPrice(rejectRecordDetail.getProductAmount(), rejectRecordDetail.getTaxRate());
                dlProduct.setNotProductAmount(noTaxPrice);
                dlProductList.add(dlProduct);
                // 查询批次信息
                List<ScmpPurchaseBatch> batches = rejectRecordBatchDao.rejectBatchListBySap(rejectRecordDetail.getSkuCode(), rejectRecordDetail.getRejectRecordCode(), rejectRecordDetail.getLineCode());
                if(CollectionUtils.isNotEmpty(batches) && batches.size() > 0){
                    dlBatchList = Lists.newArrayList();
                    for (ScmpPurchaseBatch batch : batches){
                        dlBatch = new BatchRequest();
                        dlBatch.setLineCode(rejectRecordDetail.getLineCode());
                        dlBatch.setSkuCode(rejectRecordDetail.getSkuCode());
                        dlBatch.setBatchCode(batch.getBatchNo());
                        dlBatch.setProductDate(batch.getProductDate());
                        dlBatch.setActualTotalCount(batch.getActualTotalCount());
                        dlBatchList.add(dlBatch);
                    }
                    dlProduct.setBatchList(dlBatchList);
                }
            }
            dlRequest.setProductList(dlProductList);
            LOGGER.info("退供完成之后调用DL， 传送DL库存变更的参数：{}", JsonUtil.toJson(dlRequest));
            dlService.stockChange(dlRequest);
        }
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse rejectApplyRecordCode(String rejectApplyRecordCode){

        // 获取当前登录人的信息
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (currentAuthToken == null) {
            LOGGER.info("获取当前登录信息失败");
            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
        }
        // 查询退供申请单  0.待提交 1 待审核 2.审核中 3.审核通过 4.审核不通过 5. 撤销
        RejectApplyRecord rejectApplyRecord = rejectApplyRecordDao.selectByRejectCode(rejectApplyRecordCode);
        RejectApplyRecord record = new RejectApplyRecord();
        record.setRejectApplyRecordCode(rejectApplyRecordCode);
        record.setApplyRecordStatus(RejectRecordStatus.REJECT_APPLY_REVOKE);
        record.setUpdateById(currentAuthToken.getPersonId());
        record.setUpdateByName(currentAuthToken.getPersonName());
        if(rejectApplyRecord.getApplyRecordStatus().equals(RejectRecordStatus.REJECT_APPLY_TO_REVIEW) ||
                rejectApplyRecord.getApplyRecordStatus().equals(RejectRecordStatus.REJECT_APPLY_UNDER_REVIEW)){
            // 撤销审批流
            HttpResponse response = formOperateService.commonCancel(rejectApplyRecordCode, currentAuthToken.getPersonId());
            if(response.getCode().equals(MessageId.SUCCESS_CODE)){
                LOGGER.info("退供申请单撤销审批成功：", rejectApplyRecordCode);
            }else {
                LOGGER.info("退供申请单撤销审批失败：", rejectApplyRecordCode);
                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退供申请单撤销审批失败:" + rejectApplyRecordCode));
            }
        }
        Integer count = rejectApplyRecordDao.update(record);
        LOGGER.info("退供申请单撤销数量：", count);
        return HttpResponse.success();
    }

}
