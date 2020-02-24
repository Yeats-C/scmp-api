package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.bireport.dao.ProSuggestReplenishmentDao;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.common.PurchaseOrderLogEnum;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApprovalService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierScoreService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: zhao shuai
 * @create: 2019-06-14 17:49
 **/
@Service
public class PurchaseManageServiceImpl extends BaseServiceImpl implements PurchaseManageService {

    private static final BigDecimal big = BigDecimal.valueOf(0);

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseManageServiceImpl.class);

    @Resource
    private PurchaseApplyDao purchaseApplyDao;
    @Resource
    private PurchaseApplyProductDao purchaseApplyProductDao;
    @Resource
    private PurchaseOrderDao purchaseOrderDao;
    @Resource
    private PurchaseOrderProductDao purchaseOrderProductDao;
    @Resource
    private PurchaseOrderDetailsDao purchaseOrderDetailsDao;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private FileRecordDao fileRecordDao;
    @Resource
    private ProductSkuPurchaseInfoDao productSkuPurchaseInfoDao;
    @Resource
    private InboundService inboundService;
    @Resource
    private InboundDao inboundDao;
    @Resource
    private InboundProductDao inboundProductDao;
    @Resource
    private OperationLogDao operationLogDao;
    @Resource
    private SupplierScoreService scoreService;
    @Resource
    private ProductSkuInspReportDao productSkuInspReportDao;
    @Resource
    private PurchaseApprovalService purchaseApprovalService;
    @Resource
    private ProductSkuPicturesDao productSkuPicturesDao;
    @Resource
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Resource
    private StockService stockService;
    @Resource
    private PurchaseGroupService purchaseGroupService;
    @Resource
    private PurchaseInspectionReportDao purchaseInspectionReportDao;
    @Resource
    private ApplyPurchaseOrderDao applyPurchaseOrderDao;
    @Resource
    private ApplyPurchaseOrderDetailsDao applyPurchaseOrderDetailsDao;
    @Resource
    private ApplyPurchaseOrderProductDao applyPurchaseOrderProductDao;
    @Resource
    private StockDao stockDao;
    @Resource
    private ProSuggestReplenishmentDao proSuggestReplenishmentDao;

    @Override
    public HttpResponse selectPurchaseForm(List<String> applyIds){
        if(CollectionUtils.isEmptyCollection(applyIds)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询要生成采购单的信息
        List<PurchaseFormResponse> purchaseForms = purchaseApplyProductDao.selectPurchaseForm(applyIds);
        if(CollectionUtils.isNotEmptyCollection(purchaseForms)){
            PurchaseFormRequest apply;
            for(PurchaseFormResponse form:purchaseForms){
                apply = new PurchaseFormRequest();
                apply.setPurchaseGroupCode(form.getPurchaseGroupCode());
                apply.setSupplierCode(form.getSupplierCode());
                apply.setTransportCenterCode(form.getTransportCenterCode());
                apply.setWarehouseCode(form.getWarehouseCode());
                apply.setApplyIds(applyIds);
                List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormProduct(apply);
                // 计算sku数量，单品数量，含税采购金额，实物返金额
                Integer singleCount = 0, wholeCount = 0;
                BigDecimal productTotalAmount = big, returnAmount = big, giftTaxSum = big;
                if(CollectionUtils.isNotEmptyCollection(details)){
                    Set<Integer> setType = new HashSet<>();
                    for(PurchaseApplyDetailResponse detail:details){
                        setType.add(detail.getApplyType());
                        Integer purchaseWhole = detail.getPurchaseWhole() == null ? 0 : detail.getPurchaseWhole();
                        Integer purchaseSingle = detail.getPurchaseSingle() == null ? 0 : detail.getPurchaseSingle();
                        Integer packNumber = detail.getBaseProductContent() == null ? 0 : detail.getBaseProductContent();
                        BigDecimal amount = detail.getProductPurchaseAmount() == null ? big : detail.getProductPurchaseAmount();
                        Integer number = purchaseWhole * packNumber + purchaseSingle;
                        BigDecimal amountSum = amount.multiply(BigDecimal.valueOf(number)).setScale(4, BigDecimal.ROUND_HALF_UP);
                        // 单品数量
                        singleCount += number;
                        wholeCount += purchaseWhole;
                        if(detail.getProductType().equals(Global.PRODUCT_TYPE_2)){
                            returnAmount = amountSum.add(returnAmount);
                        }else if(detail.getProductType().equals(Global.PRODUCT_TYPE_1)){
                            giftTaxSum = amountSum.add(giftTaxSum);
                        }else if(detail.getProductType().equals(Global.PRODUCT_TYPE_0)){
                            productTotalAmount = amountSum.add(productTotalAmount);
                        }
                    }
                    if(setType != null){
                        if(setType.size() == 2){
                            form.setApplyTypeForm("手动/自动");
                        }else {
                            String type = "";
                            for (Integer i:setType){
                                if(i == 0){
                                    type = "手动";
                                }else {
                                    type = "自动";
                                }
                            }
                            form.setApplyTypeForm(type);
                        }
                    }
                }
                form.setProductTotalAmount(productTotalAmount);
                form.setReturnAmount(returnAmount);
                form.setSingleCount(singleCount);
                form.setGiftTaxSum(giftTaxSum);
                form.setSkuCount(wholeCount);
            }
        }
        return HttpResponse.success(purchaseForms);
    }

    @Override
    public HttpResponse purchaseApplyList(PurchaseFormRequest purchaseFormRequest){
        if(purchaseFormRequest == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询采购申请单商品列表
        List<PurchaseFormResponse> forms = purchaseApplyProductDao.applyByProduct(purchaseFormRequest);
        if(CollectionUtils.isNotEmptyCollection(forms)){
            for(PurchaseFormResponse form:forms){
                purchaseFormRequest.getApplyIds().clear();
                purchaseFormRequest.getApplyIds().add(form.getPurchaseApplyId());
                List<PurchaseApplyDetailResponse> formDetails = purchaseApplyProductDao.purchaseFormProduct(purchaseFormRequest);
                BigDecimal productTotalAmount = big, returnAmount = big;
                Integer singleCount = 0;
                for(PurchaseApplyDetailResponse apply:formDetails){
                    // 计算单品数量， 采购含税金额， 实物返金额
                    Integer purchaseWhole = apply.getPurchaseWhole() == null ? 0 : apply.getPurchaseWhole();
                    Integer purchaseSingle = apply.getPurchaseSingle() == null ? 0 : apply.getPurchaseSingle();
                    Integer packNumber = apply.getBaseProductContent() == null ? 0 : apply.getBaseProductContent();
                    BigDecimal amount = apply.getProductPurchaseAmount() == null ? big : apply.getProductPurchaseAmount();
                    Integer number = purchaseWhole * packNumber + purchaseSingle;
                    singleCount += number;
                    if(apply.getProductType().equals(Global.PRODUCT_TYPE_2)){
                        returnAmount = amount.multiply(BigDecimal.valueOf(number)).setScale(4, BigDecimal.ROUND_HALF_UP).add(returnAmount);
                    }
                    if(apply.getProductType().equals(Global.PRODUCT_TYPE_0)){
                        productTotalAmount = amount.multiply(BigDecimal.valueOf(number)).setScale(4, BigDecimal.ROUND_HALF_UP).add(productTotalAmount);
                    }
                }
                form.setSingleCount(singleCount);
                form.setProductTotalAmount(productTotalAmount);
                form.setReturnAmount(returnAmount);
                // 计算选中sku数量
                Integer count = purchaseApplyProductDao.formSkuCount(purchaseFormRequest);
                form.setSkuCount(count);
            }
        }
        return HttpResponse.success(forms);
    }

    @Override
    public HttpResponse purchaseProductList(PurchaseFormRequest purchaseFormRequest){
        PageResData pageResData = new PageResData();
        if(purchaseFormRequest == null){
            return HttpResponse.successGenerics(pageResData);
        }
        // 根据sku去重
        List<PurchaseApplyDetailResponse> skuList = purchaseApplyProductDao.productBySku(purchaseFormRequest);
        Integer num = 0;
        if(CollectionUtils.isNotEmptyCollection(skuList)) {
            for (PurchaseApplyDetailResponse sku : skuList) {
                purchaseFormRequest.setSkuCode(sku.getSkuCode());
                // 提交采购单页面商品列表
                List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormList(purchaseFormRequest);
                if(CollectionUtils.isNotEmptyCollection(details)){
                    BigDecimal amount = big;
                    Integer  number = 0, singCount = 0, whole = 0, single = 0;
                    for (PurchaseApplyDetailResponse detail : details) {
                        if(details.size() > 1){
                            for(int i = 0; i < details.size(); i++){
                                for(int j = 0; j < details.size(); j++){
                                    if(!details.get(i).getProductPurchaseAmount().equals(details.get(j).getProductPurchaseAmount())){
                                        num =1;
                                        break;
                                    }
                                }
                            }
                        }
                        // 计算单品数量， 含税总价
                        Integer purchaseWhole = detail.getPurchaseWhole() == null ? 0 : detail.getPurchaseWhole();
                        Integer purchaseSingle = detail.getPurchaseSingle() == null ? 0 : detail.getPurchaseSingle();
                        Integer packNumber = detail.getBaseProductContent() == null ? 0 : detail.getBaseProductContent();
                        amount = detail.getProductPurchaseAmount() == null ? big : detail.getProductPurchaseAmount();
                        whole += purchaseWhole;
                        single += purchaseSingle;
                        Integer purchaseNumber = purchaseWhole * packNumber + purchaseSingle;
                        number += purchaseNumber;
                        singCount += detail.getSingleCount();
                    }
                    sku.setPurchaseWhole(whole);
                    sku.setPurchaseSingle(single);
                    sku.setSingleCount(number);
                    sku.setProductAmount(amount);
                    sku.setProductTotalAmount(amount.multiply(BigDecimal.valueOf(number)).setScale(4, BigDecimal.ROUND_HALF_UP));
                    sku.setSingleCount(singCount);
                    this.stockAmount(sku);
                }
            }
        }
        if(num == 1){
            List<PurchaseApplyDetailResponse> list = this.repeatProduct(purchaseFormRequest);
            pageResData.setDataList(list);
            Integer count = purchaseApplyProductDao.purchaseFormByRepeatCount(purchaseFormRequest);
            pageResData.setTotalCount(count);
        }else {
            pageResData.setDataList(skuList);
            Integer count = purchaseApplyProductDao.purchaseFormCount(purchaseFormRequest);
            pageResData.setTotalCount(count);
        }
        pageResData.setIsRepeat(num);
        return HttpResponse.success(pageResData);
    }

    // 查询采购单重复的商品
    private List<PurchaseApplyDetailResponse> repeatProduct(PurchaseFormRequest purchaseFormRequest) {
        List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormByRepeat(purchaseFormRequest);
        // 提交采购单页面商品列表
        if (CollectionUtils.isNotEmptyCollection(details)) {
            for (PurchaseApplyDetailResponse detail : details) {
                // 计算单品数量， 含税总价
                Integer purchaseWhole = detail.getPurchaseWhole() == null ? 0 : detail.getPurchaseWhole();
                Integer purchaseSingle = detail.getPurchaseSingle() == null ? 0 : detail.getPurchaseSingle();
                Integer packNumber = detail.getBaseProductContent() == null ? 0 : detail.getBaseProductContent();
                BigDecimal amount = detail.getProductPurchaseAmount() == null ? big : detail.getProductPurchaseAmount();
                Integer number = purchaseWhole * packNumber + purchaseSingle;
                detail.setSingleCount(number);
                detail.setProductAmount(amount);
                detail.setProductTotalAmount(amount.multiply(BigDecimal.valueOf(number)).setScale(4, BigDecimal.ROUND_HALF_UP));
                this.stockAmount(detail);
            }
        }
        return details;
    }

    private void stockAmount(PurchaseApplyDetailResponse detail) {
        // 查询库存数量，库存金额
        Stock stock = new Stock();
        stock.setSkuCode(detail.getSkuCode());
        stock.setTransportCenterCode(detail.getTransportCenterCode());
        stock.setWarehouseCode(detail.getWarehouseCode());
        stock.setCompanyCode(detail.getCompanyCode());
        Stock info = stockDao.stockInfo(stock);
        if (info != null) {
            detail.setStockCount(info.getInventoryNum().intValue());
            detail.setStockAmount(info.getTaxCost().multiply(BigDecimal.valueOf(info.getInventoryNum())).setScale(4, BigDecimal.ROUND_HALF_UP));
            Long avgSales = proSuggestReplenishmentDao.biAppSuggestReplenishmentAll(detail.getTransportCenterCode(), detail.getSkuCode(),
                    detail.getWarehouseCode());
            // 库存周转期， 预计到货周转期
            Integer stockTurnover = (avgSales == null || avgSales == 0) ? 0 : info.getInventoryNum().intValue() / avgSales.intValue();
            Integer num = info.getInventoryNum().intValue() + info.getTotalWayNum().intValue() + detail.getSingleCount();
            Integer receiptTurnover = (avgSales == null || avgSales == 0) ? 0 : num / avgSales.intValue();
            detail.setStockTurnover(stockTurnover);
            detail.setReceiptTurnover(receiptTurnover);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseOrder(PurchaseOrderRequest request) {
        if (request == null || request.getPurchaseOrder() == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 获取当前登录人的信息
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (currentAuthToken == null) {
            LOGGER.info("获取当前登录信息失败");
            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
        }

        // 获取采购单编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_ORDER_CODE);
        // 新增采购单
        String purchaseId = IdUtil.purchaseId();
        PurchaseOrder purchaseOrder = request.getPurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseId);
        String purchaseProductCode = String.valueOf(encodingRule.getNumberingValue());
        purchaseOrder.setPurchaseOrderCode(purchaseProductCode);
        purchaseOrder.setApprovalCode(purchaseProductCode);
        purchaseOrder.setInfoStatus(Global.PURCHASE_APPLY_STATUS_0);
        purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_0);
        purchaseOrder.setPurchaseMode(0);
        purchaseOrder.setCreateById(currentAuthToken.getPersonId());
        purchaseOrder.setCreateByName(currentAuthToken.getPersonName());
        purchaseOrder.setUpdateById(currentAuthToken.getPersonId());
        purchaseOrder.setUpdateById(currentAuthToken.getPersonName());
        // 添加采购单
        Integer orderCount = purchaseOrderDao.insert(purchaseOrder);
        LOGGER.info("添加采购申信息:{}", orderCount);

        // 变更采购单号
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());

        // 添加操作日志
//        log(purchaseId, purchaseOrderRequest.getPersonId(), purchaseOrderRequest.getPersonName(),
//            PurchaseOrderLogEnum.INSERT_ORDER.getCode(), PurchaseOrderLogEnum.INSERT_ORDER.getName(), purchaseOrder.getApplyTypeForm());

        // 添加采购单商品
        if(CollectionUtils.isEmptyCollection(request.getProductList())){
            LOGGER.info("添加采购申商品信息为空");
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseOrderProduct> productList = request.getProductList();
        // 行号
        Integer i = 1;
        for(PurchaseOrderProduct product:productList){
            product.setOrderProductId(IdUtil.uuid());
            product.setPurchaseOrderId(purchaseId);
            product.setPurchaseOrderCode(purchaseProductCode);
            product.setLinnum(i);
        }
        Integer productCount = purchaseOrderProductDao.insertAll(productList);
        LOGGER.info("添加采购申商品信息", productCount);

        return HttpResponse.success();
    }

    @Override
    public HttpResponse<List<PurchaseOrder>> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest){
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
        PageResData pageResData = new PageResData();
        if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.success(pageResData);
        }
        purchaseApplyRequest.setGroupList(groupVoList);
        List<PurchaseOrderResponse> list = purchaseOrderDao.purchaseOrderList(purchaseApplyRequest);
        Integer count = purchaseOrderDao.purchaseOrderCount(purchaseApplyRequest);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder){
        if(purchaseOrder == null || StringUtils.isBlank(purchaseOrder.getPurchaseOrderId())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String purchaseOrderId = purchaseOrder.getPurchaseOrderId();
        String createById = purchaseOrder.getCreateById();
        String createByName = purchaseOrder.getCreateByName();
        PurchaseOrder order = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if(null == order){
            return HttpResponse.failure(ResultCode.OBJECT_NOT_FOUND);
        }
        purchaseOrder.setUpdateByName(createByName);
        purchaseOrder.setUpdateById(createById);
        if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_9)){
            if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)
                    || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_1)){
                WorkFlowVO w = new WorkFlowVO();
                w.setFormNo(order.getPurchaseOrderCode());
                w.setUsername(createById);
                WorkFlowRespVO workFlowRespVO = cancelWorkFlow(w);
                if (!workFlowRespVO.getSuccess()) {
                    throw new GroundRuntimeException("审批流撤销失败!");
                }
                applyPurchaseOrderDao.update(purchaseOrder);
            }else {
                // 取消在途数
                this.wayNum(order);
            }
        }
        // 添加操作日志
        PurchaseOrderDetails detail;
        if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_4)){
            // 开始发货
            detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setDeliveryTime(Calendar.getInstance().getTime());
            detail.setUpdateById(createById);
            detail.setUpdateByName(createByName);
            purchaseOrderDetailsDao.update(detail);
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.DELIVER_GOODS.getCode(),
                    PurchaseOrderLogEnum.DELIVER_GOODS.getName(), order.getApplyTypeForm());
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_9)) {
            // 取消
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.REVOKE.getCode(),
                    PurchaseOrderLogEnum.REVOKE.getName(), order.getApplyTypeForm());
            if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_4)
                    || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_5)
                    || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_6)){
                // 查询入库单号的id
                String id = inboundDao.cancelById(order.getPurchaseOrderCode());
                if(StringUtils.isBlank(id)){
                    return HttpResponse.failure(ResultCode.INBOUND_INFO_NULL);
                }
                String s = inboundService.repealOrder(id, createById, createByName, purchaseOrder.getCancelRemark());
                if(!s.equals("0")){
                    return HttpResponse.failure(ResultCode.DL_CANCEL);
                }else {
                    // 将入库单状态修改为取消
                    Inbound inbound = new Inbound();
                    inbound.setId(Long.valueOf(id));
                    inbound.setInboundStatusCode(InOutStatus.CALL_OFF.getCode());
                    inbound.setInboundStatusName(InOutStatus.CALL_OFF.getName());
                    inboundDao.updateByPrimaryKeySelective(inbound);
                }
            }
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)){
            // 添加入库完成时间
            detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setWarehouseTime(Calendar.getInstance().getTime());
            detail.setUpdateByName(createById);
            detail.setUpdateById(createByName);
            purchaseOrderDetailsDao.update(detail);
            // 手动入库完成 撤销未完成的入库单
            String id = inboundDao.cancelById(order.getPurchaseOrderCode());
            if(StringUtils.isBlank(id)){
                return HttpResponse.failure(ResultCode.INBOUND_INFO_NULL);
            }
            String s = inboundService.repealOrder(id, createById, createByName, purchaseOrder.getCancelRemark());
            if(!s.equals("0")){
                return HttpResponse.failure(ResultCode.DL_CANCEL);
            }
            // 将入库单状态修改为取消
            Inbound inbound = new Inbound();
            inbound.setId(Long.valueOf(id));
            inbound.setInboundStatusCode(InOutStatus.CALL_OFF.getCode());
            inbound.setInboundStatusName(InOutStatus.CALL_OFF.getName());
            inboundDao.updateByPrimaryKeySelective(inbound);
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName(), order.getApplyTypeForm());
            if(!order.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
                log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                        PurchaseOrderLogEnum.PURCHASE_FINISH.getName(), order.getApplyTypeForm());
                purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
            }
        }
        Integer count = purchaseOrderDao.update(purchaseOrder);
        if(count == 0){
            LOGGER.error("变更采购单的状态失败......");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<PurchaseOrder> purchaseOrderDetails(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseOrder detail = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        return HttpResponse.success(detail);
    }

    @Override
    public HttpResponse<PurchaseOrderProduct> purchaseOrderProduct(PurchaseOrderProductRequest request){
        PageResData pageResData = new PageResData();
        if(StringUtils.isBlank(request.getPurchaseOrderId())){
            return HttpResponse.success(pageResData);
        }
        List<PurchaseOrderProduct> list = purchaseOrderProductDao.purchaseOrderList(request);
        Integer count = purchaseOrderProductDao.purchaseOrderCount(request);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse purchaseOrderFile(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<FileRecord> files = fileRecordDao.fileList(purchaseOrderId);
        return HttpResponse.success(files);
    }

    @Override
    public HttpResponse<List<OperationLog>> purchaseOrderLog(String operationId){
        if(StringUtils.isBlank(operationId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<OperationLog> list = operationLogDao.list(operationId);
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 计算采购单的数量与金额
        PurchaseApplyProductInfoResponse amountResponse = new PurchaseApplyProductInfoResponse();
        Integer productPieceSum = 0, matterPieceSum = 0, giftPieceSum = 0;
        Integer productSingleSum= 0, matterSingleSum = 0, giftSingleSum = 0;
        Integer actualProductPieceSum = 0, actualMatterPieceSum = 0, actualGiftPieceSum = 0;
        BigDecimal productTaxSum = big, matterTaxSum = big, giftTaxSum = big;
        Integer  singleSum = 0, priceSum = 0, actualSingleSum = 0, actualPriceSum = 0;
        Integer actualProductSingleSum= 0, actualMatterSingleSum = 0, actualGiftSingleSum = 0;
        BigDecimal actualProductTaxSum = big, actualMatterTaxSum = big, actualGiftTaxSum = big;
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest();
        request.setPurchaseOrderId(purchaseOrderId);
        request.setIsPage(1);
        List<PurchaseOrderProduct> orderProducts = purchaseOrderProductDao.purchaseOrderList(request);
        if(CollectionUtils.isNotEmptyCollection(orderProducts)){
            for(PurchaseOrderProduct order:orderProducts){
                // 商品采购件数量
                Integer purchaseWhole = order.getPurchaseWhole() == null ? 0 : order.getPurchaseWhole();
                Integer purchaseSingle = order.getPurchaseSingle() == null ? 0 : order.getPurchaseSingle();
                // 包装数量
                Integer packNumber = order.getBaseProductContent() == null ? 0 : order.getBaseProductContent();
                BigDecimal amount = order.getProductAmount() == null ? big : order.getProductAmount();
                Integer singleCount = purchaseWhole * packNumber + purchaseSingle;
                singleSum += singleCount;
                priceSum += purchaseWhole;
                // 实际
                Integer actualSingleCount = order.getActualSingleCount() == null ? 0: order.getActualSingleCount();
                Integer actualWhole = 0;
                if (packNumber != 0 ) {
                    actualWhole = actualSingleCount / packNumber;
                }
                actualPriceSum += actualWhole;
                actualSingleSum += actualSingleCount;
                if(order.getProductType().equals(Global.PRODUCT_TYPE_0)){
                    productPieceSum += purchaseWhole;
                    productSingleSum += singleCount;
                    productTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(productTaxSum);
                    actualProductPieceSum += actualWhole;
                    actualProductSingleSum += actualSingleCount;
                    actualProductTaxSum = amount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(actualProductTaxSum);
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_2)){
                    matterPieceSum += purchaseWhole;
                    matterSingleSum += singleCount;
                    matterTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(matterTaxSum);
                    actualMatterPieceSum += actualWhole;
                    actualMatterSingleSum += actualSingleCount;
                    actualMatterTaxSum = amount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(actualMatterTaxSum);
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_1)){
                    giftPieceSum += purchaseWhole;
                    giftSingleSum += singleCount;
                    giftTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(giftTaxSum);
                    actualGiftPieceSum += actualWhole;
                    actualGiftSingleSum += actualSingleCount;
                    actualGiftTaxSum = amount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(actualGiftTaxSum);
                }
            }
            // 采购
            amountResponse.setProductPieceSum(productPieceSum);
            amountResponse.setProductSingleSum(productSingleSum);
            amountResponse.setProductTaxSum(productTaxSum);
            amountResponse.setMatterPieceSum(matterPieceSum);
            amountResponse.setMatterSingleSum(matterSingleSum);
            amountResponse.setMatterTaxSum(matterTaxSum);
            amountResponse.setGiftPieceSum(giftPieceSum);
            amountResponse.setGiftSingleSum(giftSingleSum);
            amountResponse.setGiftTaxSum(giftTaxSum);
            amountResponse.setSingleSum(singleSum);
            amountResponse.setPieceSum(priceSum);

            // 实际
            amountResponse.setActualProductPieceSum(actualProductPieceSum);
            amountResponse.setActualProductSingleSum(actualProductSingleSum);
            amountResponse.setActualProductTaxSum(actualProductTaxSum);
            amountResponse.setActualMatterPieceSum(actualMatterPieceSum);
            amountResponse.setActualMatterSingleSum(actualMatterSingleSum);
            amountResponse.setActualMatterTaxSum(actualMatterTaxSum);
            amountResponse.setActualGiftPieceSum(actualGiftPieceSum);
            amountResponse.setActualGiftSingleSum(actualGiftSingleSum);
            amountResponse.setActualGiftTaxSum(actualGiftTaxSum);
            amountResponse.setActualPieceSum(actualPriceSum);
            amountResponse.setActualSingleSum(actualSingleSum);
        }
        return HttpResponse.success(amountResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseOrderStock(PurchaseStorageRequest purchaseStorage){
        if(purchaseStorage == null || StringUtils.isBlank(purchaseStorage.getPurchaseOrderCode())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String purchaseOrderCode = purchaseStorage.getPurchaseOrderCode();
        // 查询采购单的详情
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderCode(purchaseOrderCode);
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrderInfo(order);
        if(purchaseOrder == null){
            LOGGER.info("未查询到采购单的信息{}", purchaseOrderCode);
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
        // 变更采购单的状态（开始备货）
        if(!purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_2)) {
            LOGGER.error("该采购单未审核通过， 不能开始备货");
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CHECK);
        }
        order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_3);
        order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        order.setUpdateById(purchaseStorage.getCreateById());
        order.setUpdateByName(purchaseStorage.getCreateByName());
        Integer count = purchaseOrderDao.update(order);
        if(count == 0){
            LOGGER.error("采购单开始备货状态修改失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.STOCK_UP.getCode(),
                PurchaseOrderLogEnum.STOCK_UP.getName() , purchaseOrder.getApplyTypeForm());
        // 开始备货， 生成入库单
        purchaseStorage.setPurchaseNum(1);
        // 查询采购单商品
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest();
        request.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        request.setIsPage(1);
        List<PurchaseOrderProduct> products = purchaseOrderProductDao.purchaseOrderList(request);
        if(CollectionUtils.isEmptyCollection(products)){
            LOGGER.error("此采购单没有商品");
            return HttpResponse.failure(ResultCode.PRODUCT_NO_EXISTS);
        }
        String inboundOderCode = purchaseOrderCode + "01";
        purchaseStorage.setInboundOderCode(inboundOderCode);
        InboundReqSave reqSave = this.InboundReqSave(purchaseOrder, purchaseStorage, products);
        String s = inboundService.saveInbound(reqSave);
        if(StringUtils.isBlank(s)){
            LOGGER.error("生成入库单失败....");
            return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
        }
        return HttpResponse.success();
    }

    private InboundReqSave InboundReqSave(PurchaseOrder purchaseOrder, PurchaseStorageRequest purchaseStorage, List<PurchaseOrderProduct> productList){
        InboundReqSave save = new InboundReqSave();
        save.setInboundOderCode(purchaseStorage.getInboundOderCode());
        save.setCompanyCode(purchaseStorage.getCompanyCode());
        save.setCompanyName(purchaseStorage.getCompanyName());
        save.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        save.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
        save.setInboundTypeCode(InboundTypeEnum.RETURN_SUPPLY.getCode());
        save.setInboundTypeName(InboundTypeEnum.RETURN_SUPPLY.getName());
        save.setSourceOderCode(purchaseOrder.getPurchaseOrderCode());
        save.setLogisticsCenterCode(purchaseOrder.getTransportCenterCode());
        save.setLogisticsCenterName(purchaseOrder.getTransportCenterName());
        save.setWarehouseCode(purchaseOrder.getWarehouseCode());
        save.setWarehouseName(purchaseOrder.getWarehouseName());
        save.setSupplierCode(purchaseOrder.getSupplierCode());
        save.setSupplierName(purchaseOrder.getSupplierName());
        save.setPurchaseNum(purchaseStorage.getPurchaseNum());
        save.setCreateBy(purchaseStorage.getCreateByName());
        save.setUpdateBy(purchaseStorage.getCreateByName());
        save.setCreateTime(Calendar.getInstance().getTime());
        save.setUpdateTime(Calendar.getInstance().getTime());
        // 预计到货时间
        PurchaseApplyDetailResponse detail = purchaseOrderDetailsDao.purchaseOrderDetail(purchaseOrder.getPurchaseOrderCode());
        if(detail != null){
            save.setPreArrivalTime(detail.getExpectArrivalTime());
        }
        Long preInboundNum = 0L, preInboundMainNum = 0L;
        BigDecimal preTaxAmount = big, preNoTaxAmount = big;

        InboundProductReqVo reqVo;
        // 入库sku商品
        List<InboundProductReqVo> list = Lists.newArrayList();
        List<InboundBatchReqVo> batchReqVoList = Lists.newArrayList();
        // 查询是否有商品可以入库
        if(CollectionUtils.isNotEmptyCollection(productList)){
            InboundBatchReqVo inboundBatchReqVo;
            ProductSkuPictures productSkuPicture;
            ProductSkuPurchaseInfo skuPurchaseInfo;
            for(PurchaseOrderProduct product:productList){
                Integer singleCount = product.getSingleCount() == null ? 0 : product.getSingleCount();
                Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().intValue();
                if(singleCount - actualSingleCount == 0){
                    continue;
                }
                reqVo = new InboundProductReqVo();
                reqVo.setInboundOderCode(purchaseStorage.getInboundOderCode());
                reqVo.setSkuCode(product.getSkuCode());
                reqVo.setSkuName(product.getSkuName());
                productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(product.getSkuCode());
                if(productSkuPicture != null && StringUtils.isNotBlank(productSkuPicture.getProductPicturePath())){
                    reqVo.setPictureUrl(productSkuPicture.getProductPicturePath());
                }
                reqVo.setNorms(product.getProductSpec());
                reqVo.setColorName(product.getColorName());
                reqVo.setColorCode(null);
                reqVo.setModel(product.getModelNumber());
                reqVo.setInboundNorms(product.getProductSpec());
                skuPurchaseInfo = productSkuPurchaseInfoDao.getInfo(product.getSkuCode());
                if(skuPurchaseInfo != null){
                    reqVo.setUnitCode(skuPurchaseInfo.getUnitCode());
                    reqVo.setUnitName(skuPurchaseInfo.getUnitName());
                    reqVo.setInboundBaseUnit(skuPurchaseInfo.getZeroRemovalCoefficient().toString());
                    reqVo.setInboundBaseContent(skuPurchaseInfo.getBaseProductContent().toString());
                }
                Integer purchaseWhole = product.getPurchaseWhole() == null ? 0 : product.getPurchaseWhole().intValue();
                Integer baseProductContent = product.getBaseProductContent() == null ? 0 : product.getBaseProductContent().intValue();
                BigDecimal amount = product.getProductAmount() == null ? big : product.getProductAmount();
                if(actualSingleCount > 0 && baseProductContent > 0){
                    purchaseWhole = purchaseWhole - actualSingleCount / baseProductContent;
                }
                reqVo.setPreInboundMainNum(singleCount.longValue() - actualSingleCount.longValue());
                reqVo.setPreInboundNum(purchaseWhole.longValue());
                reqVo.setPreTaxPurchaseAmount(product.getProductAmount());
                BigDecimal productTotalAmount = product.getProductTotalAmount() == null ? big : product.getProductTotalAmount();
                reqVo.setPreTaxAmount(productTotalAmount);
                reqVo.setLinenum(product.getLinnum().longValue());
                reqVo.setCreateBy(purchaseStorage.getCreateByName());
                reqVo.setCreateTime(Calendar.getInstance().getTime());
                reqVo.setTax(product.getTaxRate());
                preInboundMainNum += reqVo.getPreInboundMainNum();
                preInboundNum += purchaseWhole;
                Integer num = singleCount - actualSingleCount;
                BigDecimal totalAmount = amount.multiply(BigDecimal.valueOf(num)).setScale(4, BigDecimal.ROUND_HALF_UP);
                preTaxAmount = totalAmount.add(preTaxAmount);
                BigDecimal noTax = Calculate.computeNoTaxPrice(totalAmount, BigDecimal.valueOf(product.getTaxRate().longValue()));
                preNoTaxAmount = noTax.add(preNoTaxAmount);
                list.add(reqVo);
                //出库加入供应商与商品关系
                inboundBatchReqVo = new InboundBatchReqVo();
                inboundBatchReqVo.setInboundOderCode(purchaseStorage.getInboundOderCode());
                inboundBatchReqVo.setSkuName(product.getSkuName());
                inboundBatchReqVo.setSkuCode(product.getSkuCode());
                inboundBatchReqVo.setSupplierCode(purchaseOrder.getSupplierCode());
                inboundBatchReqVo.setSupplierName(purchaseOrder.getSupplierName());
                inboundBatchReqVo.setPraQty(product.getSingleCount().longValue());
                inboundBatchReqVo.setCreateBy(purchaseOrder.getCreateByName());
                inboundBatchReqVo.setUpdateBy(purchaseOrder.getUpdateByName());
                batchReqVoList.add(inboundBatchReqVo);
            }
        }
        save.setPreInboundNum(preInboundNum);
        save.setPreMainUnitNum(preInboundMainNum);
        save.setPreTaxAmount(preTaxAmount);
        save.setPreAmount(preNoTaxAmount);
        save.setPreTax(preTaxAmount.subtract(preNoTaxAmount));
        save.setRemark(null);
        save.setList(list);
        save.setInboundBatchReqVos(batchReqVoList);
        return save;
    }

    @Override
   // @Transactional(rollbackFor = Exception.class)
    public HttpResponse getWarehousing(PurchaseStorageRequest purchaseStorage){
        if(purchaseStorage == null || CollectionUtils.isEmptyCollection(purchaseStorage.getOrderList())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer num = purchaseStorage.getPurchaseNum();
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrderInfo(order);
        order.setStorageStatus(Global.STORAGE_STATUS_1);
        if(num == 1){
            order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_6);
        }
        order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        Integer count = purchaseOrderDao.update(order);
        if(count == 0){
            LOGGER.error("采购单入库中状态修改失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 变更采购的入库的实际单品数量
        List<PurchaseOrderProduct> list = purchaseStorage.getOrderList();
        List<PurchaseOrderProduct> productList = Lists.newArrayList();
        for(PurchaseOrderProduct product:list){
            product.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
            PurchaseOrderProduct orderProduct= purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(
                    purchaseStorage.getPurchaseOrderCode(), product.getSkuCode(), product.getLinnum());
            Integer actualCount = orderProduct.getActualSingleCount() == null ? 0 : orderProduct.getActualSingleCount().intValue();
            product.setActualSingleCount(actualCount + product.getActualSingleCount());
            Integer count1 = purchaseOrderProductDao.update(product);
            if(count1 == 0){
                return HttpResponse.failure(ResultCode.UPDATE_ERROR);
            }
            PurchaseOrderProduct purchaseOrderProduct = purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(
                    purchaseStorage.getPurchaseOrderCode(), product.getSkuCode(), product.getLinnum());
            Integer singleCount = purchaseOrderProduct.getSingleCount() == null ? 0 : purchaseOrderProduct.getSingleCount().intValue();
            Integer actualSingleCount = purchaseOrderProduct.getActualSingleCount() == null ? 0 : purchaseOrderProduct.getActualSingleCount().intValue();
            if(singleCount - actualSingleCount > 0){
                Integer code = inboundDao.selectMaxPurchaseNumBySourceOderCode(purchaseStorage.getPurchaseOrderCode());
                purchaseStorage.setPurchaseNum(code + 1);
                productList.add(purchaseOrderProduct);
            }
        }
        // 是否入库完成
        if(purchaseStorage.getPurchaseNum() > num){
            if(purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)
                    || purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_8)
                    || purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_9)){
                return HttpResponse.success();
            }
            String code;
            if(purchaseStorage.getPurchaseNum()  <= 9){
                code = "0" + purchaseStorage.getPurchaseNum();
            }else {
                code = purchaseStorage.getPurchaseNum().toString();
            }
            String inboundOderCode = purchaseStorage.getPurchaseOrderCode() + code;
            purchaseStorage.setInboundOderCode(inboundOderCode);
            InboundReqSave save = this.InboundReqSave(purchaseOrder, purchaseStorage, productList);
            String s = inboundService.saveInbound(save);
            if(StringUtils.isBlank(s)){
                LOGGER.error("生成入库单失败....");
                return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
            }
        }else {
            order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_7);
            order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            Integer count1 = purchaseOrderDao.update(order);
            if(count1 == 0){
                LOGGER.error("采购单入库完成状态修改失败");
                return HttpResponse.failure(ResultCode.UPDATE_ERROR);
            }
            // 添加入库完成时间
            PurchaseOrderDetails detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            detail.setWarehouseTime(Calendar.getInstance().getTime());
            detail.setUpdateByName(purchaseStorage.getCreateById());
            detail.setUpdateById(purchaseStorage.getCreateByName());
            purchaseOrderDetailsDao.update(detail);
            log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName() , purchaseOrder.getApplyTypeForm());
            // 仓储确认判断是否入库完成
            if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7) && !purchaseOrder.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
                log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                        PurchaseOrderLogEnum.PURCHASE_FINISH.getName() , purchaseOrder.getApplyTypeForm());
                this.wayNum(purchaseOrder);
            }
        }
        return HttpResponse.success();
    }

    private void log(String purchaseOrderId, String createById, String createByName, Integer code, String name, String remark){
        OperationLog log = new OperationLog();
        log.setOperationId(purchaseOrderId);
        log.setCreateById(createById);
        log.setCreateByName(createByName);
        log.setOperationType(code);
        log.setOperationContent(name);
        log.setRemark(remark);
        if(code.equals(10)){
            Calendar calendar = Calendar.getInstance ();
            calendar.add (Calendar.SECOND, 3);
            log.setCreateTime(calendar.getTime());
        }else {
            log.setCreateTime(Calendar.getInstance().getTime());
        }
        operationLogDao.insertByTime(log);
    }

    @Override
    public HttpResponse reportSku(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseOrderProduct> list = purchaseOrderProductDao.orderBySku(purchaseOrderId);
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<List<Inbound>> receipt(String purchaseOrderCode){
        if(StringUtils.isBlank(purchaseOrderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<Inbound> inbound = inboundDao.selectTimeAndSatusBySourchAndNum(purchaseOrderCode);
        return HttpResponse.success(inbound);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse storageConfirm(PurchaseStorageRequest storageRequest){
        if(StringUtils.isBlank(storageRequest.getPurchaseOrderId())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String purchaseOrderId = storageRequest.getPurchaseOrderId();
        PurchaseOrder order = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if(!order.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
            LOGGER.info("采购仓储状态非确认中状态， 不能确认");
            return HttpResponse.failure(ResultCode.STORAGE_NOT_CONFIRM);
        }
        // 变更采购单的状态
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseOrderId);
        if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)){
            purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
        }
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_2);
        purchaseOrder.setUpdateById(storageRequest.getCreateById());
        purchaseOrder.setUpdateByName(storageRequest.getCreateByName());
        Integer count = purchaseOrderDao.update(purchaseOrder);
        if(count == 0){
            LOGGER.error("变更采购单的仓储状态失败......");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 保存质检报告
        if(CollectionUtils.isNotEmptyCollection(storageRequest.getInspectionReport())){
            List<PurchaseInspectionReport> purchaseInspectionReports = purchaseInspectionReportDao.inspectionReportInfo(purchaseOrderId);
            if(CollectionUtils.isNotEmptyCollection(purchaseInspectionReports)){
                PurchaseInspectionReport report = new PurchaseInspectionReport();
                report.setPurchaseOrderId(purchaseOrderId);
                report.setStatus(Global.USER_OFF);
                report.setUpdateById(storageRequest.getCreateById());
                report.setUpdateByName(storageRequest.getCreateByName());
                purchaseInspectionReportDao.update(report);
            }
            purchaseInspectionReportDao.insertAll(storageRequest.getInspectionReport());
            // 替换商品的对应sku与日期的质检报告
            List<PurchaseInspectionReport> inspection = storageRequest.getInspectionReport();
            ProductSkuInspReport inspReport;
            List<ProductSkuInspReport> productSkuInspReportList = Lists.newArrayList();
            QueryProductSkuInspReportReqVo vo;
            for(PurchaseInspectionReport pir:inspection){
                // 判断商品的质检报告是否存在， 存在根据sku与生产日期更新上传路径，否则新增
                vo = new QueryProductSkuInspReportReqVo();
                vo.setSkuCode(pir.getSkuCode());
                vo.setProductionDate(pir.getProductionDate());
                List<ProductSkuInspReportRespVo> list = productSkuInspReportDao.getListBySkuCodeAndProductDate(vo);
                inspReport = new ProductSkuInspReport();
                inspReport.setSkuCode(pir.getSkuCode());
                inspReport.setProductionDate(pir.getProductionDate());
                if(list.size() > 0){
                    inspReport.setUpdateBy(storageRequest.getCreateByName());
                    productSkuInspReportDao.updateInspection(inspReport);
                }else {
                    inspReport.setSkuName(pir.getSkuName());
                    inspReport.setInspectionReportPath(pir.getInspectionReportPath());
                    inspReport.setCreateBy(storageRequest.getCreateByName());
                    productSkuInspReportList.add(inspReport);
                }
            }
            if(CollectionUtils.isNotEmptyCollection(productSkuInspReportList)){
                productSkuInspReportDao.insertInspReportList(productSkuInspReportList);
            }
        }
        // 保存供应商评分
        if(storageRequest.getScoreRequest() != null){
            String code = scoreService.saveByPurchase(storageRequest.getScoreRequest());
            if(StringUtils.isBlank(code)){
                LOGGER.error("保存采购单对应的评分失败");
                return HttpResponse.failure(ResultCode.ADD_ERROR);
            }
            // 评分编码存入采购单详情
            PurchaseOrderDetails detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setScoreCode(code);
            detail.setUpdateByName(storageRequest.getCreateByName());
            detail.setUpdateById(storageRequest.getCreateById());
            purchaseOrderDetailsDao.update(detail);
        }
        // 新增操作日志
        log(purchaseOrderId, storageRequest.getCreateById(), storageRequest.getCreateByName(), PurchaseOrderLogEnum.STORAGE_FINISH.getCode(),
                PurchaseOrderLogEnum.STORAGE_FINISH.getName() , order.getApplyTypeForm());
        // 仓储确认判断是否入库完成
        if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)){
            log(purchaseOrderId, storageRequest.getCreateById(), storageRequest.getCreateByName(), PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                    PurchaseOrderLogEnum.PURCHASE_FINISH.getName() , order.getApplyTypeForm());
            this.wayNum(order);
        }
        return HttpResponse.success();
    }

    // 修改库存在途数
    private void wayNum(PurchaseOrder order){
        StockChangeRequest stock = new StockChangeRequest();
        stock.setOperationType(11);
        List<StockVoRequest> list = Lists.newArrayList();
        StockVoRequest stockVo;
        // 查询该采购单的商品
        List<PurchaseApplyDetailResponse> products = purchaseOrderProductDao.orderProductInfoByGroup(order.getPurchaseOrderId());
        if(CollectionUtils.isNotEmptyCollection(products)){
            for(PurchaseApplyDetailResponse product:products){
                long singleCount =  product.getSingleCount() == null ? 0 : product.getSingleCount().longValue();
                long actualSingleCount =  product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().longValue();
                if(singleCount - actualSingleCount == 0){
                    continue;
                }
                stockVo = new StockVoRequest();
                stockVo.setTransportCenterCode(product.getTransportCenterCode());
                stockVo.setTransportCenterName(product.getTransportCenterName());
                stockVo.setWarehouseCode(product.getWarehouseCode());
                stockVo.setWarehouseName(product.getWarehouseName());
                stockVo.setOperator(product.getCreateByName());
                stockVo.setSkuCode(product.getSkuCode());
                stockVo.setSkuName(product.getSkuName());
                stockVo.setChangeNum(singleCount - actualSingleCount);
                stockVo.setDocumentNum(product.getPurchaseOrderCode());
                stockVo.setDocumentType(3);
                stockVo.setTaxRate(product.getTaxRate());
                stockVo.setCompanyCode(order.getCompanyCode());
                stockVo.setCompanyName(order.getCompanyName());
                list.add(stockVo);
            }
            stock.setStockVoRequests(list);
            stockService.changeStock(stock);
        }
    }

    @Override
    public HttpResponse<PurchaseApplyDetailResponse> receiptProduct(String purchaseOrderCode, Integer purchaseNum, Integer pageNo, Integer pageSize){
        if(StringUtils.isBlank(purchaseOrderCode) || purchaseNum == null){
            return HttpResponse.success(new PageResData<>());
        }
        Inbound inbound = new Inbound();
        inbound.setPageSize(pageSize);
        inbound.setPageNo(pageNo);
        inbound.setSourceOderCode(purchaseOrderCode);
        inbound.setPurchaseNum(purchaseNum);
        List<PurchaseApplyDetailResponse> list = inboundProductDao.selectPurchaseInfoByPurchaseNum(inbound);
        // 查询对应采购数据
        if(CollectionUtils.isNotEmptyCollection(list)){
            for(PurchaseApplyDetailResponse product:list){
                // 查询厂商sku
                String factorySkuCode = productSkuSupplyUnitDao.getFactorySkuCode(product.getSkuCode(), product.getSupplierCode());
                if(StringUtils.isNotBlank(factorySkuCode)){
                    product.setFactorySkuCode(factorySkuCode);
                }
                if(product != null){
                    PurchaseApplyDetailResponse orderProduct = purchaseOrderProductDao.warehousingInfo(product.getSourceOderCode(), product.getLinenum().intValue());
                    if(orderProduct != null){
                        Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount();
                        BigDecimal productAmount = orderProduct.getProductAmount() == null ? big : orderProduct.getProductAmount();
                        BeanUtils.copyProperties(orderProduct, product);
                        product.setActualSingleCount(actualSingleCount);
                        product.setActualTaxSum(productAmount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        }
        Integer count = inboundProductDao.countPurchaseInfoByPurchaseNum(inbound);
        return HttpResponse.success(new PageResData<>(count, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse addLog(OperationLog operationLog){
        if(operationLog == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        operationLogDao.insert(operationLog);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<PurchaseInspectionReport> inspectionReport(String purchaseOrderId){
       if(StringUtils.isBlank(purchaseOrderId)){
           return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
       }
        List<PurchaseInspectionReport> inspectionReport = purchaseInspectionReportDao.inspectionReportInfo(purchaseOrderId);
        return HttpResponse.success(inspectionReport);
    }

    @Override
    public HttpResponse<PurchaseFormResponse> skuSupply(String skuCode){
        if(StringUtils.isBlank(skuCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseFormResponse> purchaseFormResponses = productSkuSupplyUnitDao.supplyList(skuCode);
        return HttpResponse.success(purchaseFormResponses);
    }

    @Override
    public HttpResponse<PurchaseApplyDetailResponse> applyDetails(String purchaseOrderCode){
        if(StringUtils.isBlank(purchaseOrderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseApplyDetailResponse detail = applyPurchaseOrderDetailsDao.applyOrderDetails(purchaseOrderCode);
        return HttpResponse.success(detail);
    }

    @Override
    public HttpResponse applyOrderProduct(PurchaseOrderProductRequest request){
        PageResData pageResData = new PageResData();
        if(StringUtils.isBlank(request.getPurchaseOrderId())){
            return HttpResponse.success(pageResData);
        }
        List<ApplyPurchaseOrderProduct> list = applyPurchaseOrderProductDao.applyPurchaseOrderList(request);
        Integer count = applyPurchaseOrderProductDao.applyPurchaseOrderCount(request);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse<PurchaseApplyProductInfoResponse>  applyOrderAmount(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 计算采购单的数量与金额
        PurchaseApplyProductInfoResponse amountResponse = new PurchaseApplyProductInfoResponse();
        Integer productPieceSum = 0, matterPieceSum = 0, giftPieceSum = 0;
        Integer productSingleSum = 0, matterSingleSum = 0, giftSingleSum = 0;
        BigDecimal productTaxSum = big, matterTaxSum = big, giftTaxSum = big;
        Integer singleSum = 0, priceSum = 0;
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest();
        request.setPurchaseOrderId(purchaseOrderId);
        request.setIsPage(1);
        List<ApplyPurchaseOrderProduct> orderProducts = applyPurchaseOrderProductDao.applyPurchaseOrderList(request);
        if(CollectionUtils.isNotEmptyCollection(orderProducts)){
            for(ApplyPurchaseOrderProduct order:orderProducts){
                // 商品采购件数量
                Integer purchaseWhole = order.getPurchaseWhole() == null ? 0 : order.getPurchaseWhole();
                Integer purchaseSingle = order.getPurchaseSingle() == null ? 0 : order.getPurchaseSingle();
                // 包装数量
                Integer packNumber = order.getBaseProductContent() == null ? 0 : order.getBaseProductContent();
                BigDecimal amount = order.getProductAmount() == null ? big : order.getProductAmount();
                Integer singleCount = purchaseWhole * packNumber + purchaseSingle;
                singleSum += singleCount;
                priceSum += purchaseWhole;
                if(order.getProductType().equals(Global.PRODUCT_TYPE_0)){
                    productPieceSum += purchaseWhole;
                    productSingleSum += singleCount;
                    //进行运算
                    productTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(productTaxSum);
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_2)){
                    matterPieceSum += purchaseWhole;
                    matterSingleSum += singleCount;
                    matterTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(matterTaxSum);
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_1)){
                    giftPieceSum += purchaseWhole;
                    giftSingleSum += singleCount;
                    giftTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(giftTaxSum);
                }
            }
            // 采购
            amountResponse.setProductPieceSum(productPieceSum);
            amountResponse.setProductSingleSum(productSingleSum);
            amountResponse.setProductTaxSum(productTaxSum);
            amountResponse.setMatterPieceSum(matterPieceSum);
            amountResponse.setMatterSingleSum(matterSingleSum);
            amountResponse.setMatterTaxSum(matterTaxSum);
            amountResponse.setGiftPieceSum(giftPieceSum);
            amountResponse.setGiftSingleSum(giftSingleSum);
            amountResponse.setGiftTaxSum(giftTaxSum);
            amountResponse.setSingleSum(singleSum);
            amountResponse.setPieceSum(priceSum);
        }
        return HttpResponse.success(amountResponse);
    }

    @Override
    public HttpResponse purchaseOrderPre(String purchaseGroupCode, Integer purchaseOrderTypeCode, String purchaseOrderCode){
        if(StringUtils.isBlank(purchaseGroupCode) || purchaseOrderTypeCode == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<String> list = purchaseOrderDao.getPurchaseOrderPre(purchaseGroupCode, purchaseOrderTypeCode, purchaseOrderCode);
        return HttpResponse.success(list);
    }
}
