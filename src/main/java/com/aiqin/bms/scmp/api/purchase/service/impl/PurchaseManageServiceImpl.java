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
import com.aiqin.bms.scmp.api.product.service.impl.InboundServiceImpl;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApprovalService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierScoreService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
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
import org.springframework.aop.framework.AopContext;
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
    private ApplyPurchaseOrderDetailsDao applyPurchaseOrderDetailsDao;
    @Resource
    private ApplyPurchaseOrderProductDao applyPurchaseOrderProductDao;
    @Resource
    private StockDao stockDao;
    @Resource
    private ProSuggestReplenishmentDao proSuggestReplenishmentDao;
    @Resource
    private PurchaseApplyService purchaseApplyService;
    @Resource
    private PurchaseApplyDao purchaseApplyDao;
    @Resource
    private PurchaseApplyTransportCenterDao purchaseApplyTransportCenterDao;

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
    //@Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseOrder(PurchaseOrderRequest request) {
        if (request == null || request.getPurchaseOrder() == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 获取当前登录人的信息
//        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
//        if (currentAuthToken == null) {
//            LOGGER.info("获取当前登录信息失败");
//            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
//        }

        // 获取采购单编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_ORDER_CODE);
        // 新增采购单
        String purchaseId = IdUtil.purchaseId();
        PurchaseOrder purchaseOrder = request.getPurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseId);
        String purchaseOrderCode = String.valueOf(encodingRule.getNumberingValue());
        purchaseOrder.setPurchaseOrderCode(purchaseOrderCode);
        purchaseOrder.setApprovalCode(purchaseOrderCode);
        //purchaseOrder.setInfoStatus(Global.PURCHASE_APPLY_STATUS_0);
        purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_0);
        purchaseOrder.setPurchaseMode(0);
        purchaseOrder.setCreateById(request.getPurchaseOrder().getCreateById());
        purchaseOrder.setCreateByName(request.getPurchaseOrder().getCreateByName());
        purchaseOrder.setUpdateById(request.getPurchaseOrder().getCreateById());
        purchaseOrder.setUpdateById(request.getPurchaseOrder().getCreateByName());
        // 添加采购单
        Integer orderCount = purchaseOrderDao.insert(purchaseOrder);
        LOGGER.info("添加采购申信息:{}", orderCount);

        // 变更采购单号
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());

        // 添加生成采购单操作日志
        log(purchaseId, request.getPurchaseOrder().getCreateById(), request.getPurchaseOrder().getCreateByName(),
            PurchaseOrderLogEnum.INSERT_ORDER.getCode(), PurchaseOrderLogEnum.INSERT_ORDER.getName(), purchaseOrder.getApplyTypeForm());

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
            product.setPurchaseOrderCode(purchaseOrderCode);
            product.setLinnum(i);
            ++i;
        }
        Integer productCount = purchaseOrderProductDao.insertAll(productList);
        LOGGER.info("添加采购单商品信息", productCount);

        // 保存采购单成功开始入库
        String inboundOderCode = purchaseOrderCode + "01";
        PurchaseInboundRequest inboundRequest = new PurchaseInboundRequest();
        inboundRequest.setInboundOrderCode(inboundOderCode);
        inboundRequest.setPurchaseNum(1);
        inboundRequest.setPurchaseOrderId(purchaseId);
        // 查询商品信息
        List<PurchaseOrderProduct> products = purchaseOrderProductDao.orderProductInfo(purchaseId);
        if(CollectionUtils.isEmptyCollection(products)){
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_PRODUCT_NULL);
        }
        inboundRequest.setProductList(products);
        InboundReqSave reqSave = this.InboundReqSave(inboundRequest);
        LOGGER.info("开始调用采购单入库");
        String s = inboundService.saveInbound(reqSave);
        if(StringUtils.isBlank(s)){
            LOGGER.error("生成入库单失败....");
            return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
        }else {
            LOGGER.info("调用采购单入库成功：" + s);
            // 增加采购在途数
            this.wayNum(purchaseOrder, 6);
        }
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
    public HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null || StringUtils.isBlank(purchaseOrder.getPurchaseOrderId())
                || purchaseOrder.getPurchaseOrderStatus() == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }

        // 获取当前登录人的信息
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (currentAuthToken == null) {
            LOGGER.info("获取当前登录信息失败");
            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
        }

        String purchaseOrderId = purchaseOrder.getPurchaseOrderId();
        // 查询当前的采购单信息
        PurchaseOrder order = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if (null == order) {
            LOGGER.info("采购单的信息为空");
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_NULL);
        }
        String personId = currentAuthToken.getPersonId();
        String personName = currentAuthToken.getPersonName();
        purchaseOrder.setUpdateById(personId);
        purchaseOrder.setUpdateByName(personName);

        Integer status = purchaseOrder.getPurchaseOrderStatus();
        String type = "手动";
        switch (status) {
            case 9:
                // 取消采购单
                // 判断采购单是否是待确认、备货确认、发货确认状态
                if (order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)
                        || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_3)
                        || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_4)) {
                    // 添加日志
                    log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.REVOKE.getCode(),
                            PurchaseOrderLogEnum.REVOKE.getName(), type);
                    // 调用取消入库单
                    if(order.getInfoStatus().equals(0)){
                        this.cancelInbound(order);
                    }
                }else {
                    LOGGER.info("采购单非待确认、备货确认、发货确认状态");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                break;
            case 3:
                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)){
                    LOGGER.info("采购单非待确认状态，不能进行备货确认");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                // 添加备货确认日志
                log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.STOCK_UP.getCode(),
                        PurchaseOrderLogEnum.STOCK_UP.getName(), type);
                break;
            case 4:
                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_3)){
                    LOGGER.info("采购单非待备货确认状态，不能进行发货确认");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                // 发货确认
                purchaseOrder.setDeliveryTime(Calendar.getInstance().getTime());
                // 添加发货确认日志
                log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.DELIVER_GOODS.getCode(),
                        PurchaseOrderLogEnum.DELIVER_GOODS.getName(), type);
                break;
            case 8:
                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_6)){
                    LOGGER.info("采购单非待入库中状态，不能进行完成采购确认");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                // 完成
                purchaseOrder.setWarehouseTime(Calendar.getInstance().getTime());
                // 添加完成确认日志
                log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                        PurchaseOrderLogEnum.PURCHASE_FINISH.getName(), type);
                // 调用入库单的取消
                this.cancelInbound(order);
                break;
            case 11:
                // 重发
                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)){
                    LOGGER.info("采购单非待确认状态，不能进行重发操作");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
                //InboundServiceImpl service = (InboundServiceImpl) AopContext.currentProxy();
                // 根据采购单号查询入库单号
                Integer num = inboundDao.selectMaxPurchaseNumBySourceOderCode(order.getPurchaseOrderCode());
                // 入库单号
                String code = num <= 9 ? ("0" + num.toString()) : num.toString();
                inboundService.wms(order.getPurchaseOrderCode() + code);
                break;
        }
        Integer count = purchaseOrderDao.update(purchaseOrder);
        LOGGER.error("变更采购单状态" + count);
        return HttpResponse.success();
    }

    // 撤销未完成的入库单
    public HttpResponse cancelInbound(PurchaseOrder order){
        // 查询入库单id
        String id = inboundDao.cancelById(order.getPurchaseOrderCode());
        if(StringUtils.isBlank(id)){
            LOGGER.info("未查询到入库单");
            return HttpResponse.failure(ResultCode.INBOUND_INFO_NULL);
        }
        String s = inboundService.repealOrder(id, order.getUpdateById(), order.getUpdateByName(), order.getCancelRemark());
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
//        String inboundOderCode = purchaseOrderCode + "01";
//        purchaseStorage.setInboundOderCode(inboundOderCode);
//        InboundReqSave reqSave = this.InboundReqSave(purchaseOrder, purchaseStorage, products);
//        String s = inboundService.saveInbound(reqSave);
//        if(StringUtils.isBlank(s)){
//            LOGGER.error("生成入库单失败....");
//            return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
//        }
        return HttpResponse.success();
    }

    private InboundReqSave InboundReqSave(PurchaseInboundRequest request) {

        // 获取当前登录人的信息
//        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
//        if (currentAuthToken == null) {
//            LOGGER.info("获取当前登录信息失败");
//            throw new GroundRuntimeException("获取当前登录信息失败！");
//        }

        // 查询采购单信息
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrder(request.getPurchaseOrderId());
        LOGGER.info("入库单的采购单的信息：" + purchaseOrder);
        InboundReqSave save = new InboundReqSave();
        // 赋值入库单字段
        save.setInboundOderCode(request.getInboundOrderCode());
        save.setCompanyCode(purchaseOrder.getCompanyCode());
        save.setCompanyName(purchaseOrder.getCompanyName());
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
        save.setPurchaseNum(request.getPurchaseNum());
        save.setCreateBy(purchaseOrder.getCreateByName());
        save.setUpdateBy(purchaseOrder.getCreateByName());
        save.setCreateTime(Calendar.getInstance().getTime());
        save.setUpdateTime(Calendar.getInstance().getTime());
        save.setPreArrivalTime(purchaseOrder.getPreArrivalTime());

        // 查询采购单商品信息
        List<PurchaseOrderProduct> products = request.getProductList();

        // 赋值入库单商品
        Long preInboundNum = 0L, preInboundMainNum = 0L;
        BigDecimal preTaxAmount = big, preNoTaxAmount = big;
        InboundProductReqVo reqVo;
        // 入库sku商品
        List<InboundProductReqVo> list = Lists.newArrayList();
        List<InboundBatchReqVo> batchReqVoList = Lists.newArrayList();
        InboundBatchReqVo inboundBatchReqVo;
        ProductSkuPictures productSkuPicture;
        ProductSkuPurchaseInfo skuPurchaseInfo;
        for (PurchaseOrderProduct product : products) {
            Integer singleCount = product.getSingleCount() == null ? 0 : product.getSingleCount();
            Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().intValue();
            if (singleCount - actualSingleCount == 0) {
                continue;
            }
            reqVo = new InboundProductReqVo();
            reqVo.setInboundOderCode(request.getInboundOrderCode());
            reqVo.setSkuCode(product.getSkuCode());
            reqVo.setSkuName(product.getSkuName());
            productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(product.getSkuCode());
            if (productSkuPicture != null && StringUtils.isNotBlank(productSkuPicture.getProductPicturePath())) {
                reqVo.setPictureUrl(productSkuPicture.getProductPicturePath());
            }
            reqVo.setNorms(product.getProductSpec());
            reqVo.setColorName(product.getColorName());
            reqVo.setColorCode(null);
            reqVo.setModel(product.getModelNumber());
            reqVo.setInboundNorms(product.getProductSpec());
            skuPurchaseInfo = productSkuPurchaseInfoDao.getInfo(product.getSkuCode());
            if (skuPurchaseInfo != null) {
                reqVo.setUnitCode(skuPurchaseInfo.getUnitCode());
                reqVo.setUnitName(skuPurchaseInfo.getUnitName());
                reqVo.setInboundBaseUnit(skuPurchaseInfo.getZeroRemovalCoefficient() == null ? "" :
                        skuPurchaseInfo.getZeroRemovalCoefficient().toString());
                reqVo.setInboundBaseContent(skuPurchaseInfo.getBaseProductContent().toString());
            }
            Integer purchaseWhole = product.getPurchaseWhole() == null ? 0 : product.getPurchaseWhole().intValue();
            Integer baseProductContent = product.getBaseProductContent() == null ? 0 : product.getBaseProductContent().intValue();
            BigDecimal amount = product.getProductAmount() == null ? big : product.getProductAmount();
            if (actualSingleCount > 0 && baseProductContent > 0) {
                purchaseWhole = purchaseWhole - actualSingleCount / baseProductContent;
            }
            reqVo.setPreInboundMainNum(singleCount.longValue() - actualSingleCount.longValue());
            reqVo.setPreInboundNum(purchaseWhole.longValue());
            reqVo.setPreTaxPurchaseAmount(product.getProductAmount());
            BigDecimal productTotalAmount = product.getProductTotalAmount() == null ? big : product.getProductTotalAmount();
            reqVo.setPreTaxAmount(productTotalAmount);
            reqVo.setLinenum(product.getLinnum().longValue());
            reqVo.setCreateBy(purchaseOrder.getCreateByName());
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
            inboundBatchReqVo.setInboundOderCode(request.getInboundOrderCode());
            inboundBatchReqVo.setSkuName(product.getSkuName());
            inboundBatchReqVo.setSkuCode(product.getSkuCode());
            inboundBatchReqVo.setSupplierCode(purchaseOrder.getSupplierCode());
            inboundBatchReqVo.setSupplierName(purchaseOrder.getSupplierName());
            inboundBatchReqVo.setPraQty(product.getSingleCount().longValue());
            inboundBatchReqVo.setCreateBy(purchaseOrder.getCreateByName());
            inboundBatchReqVo.setUpdateBy(purchaseOrder.getUpdateByName());
            batchReqVoList.add(inboundBatchReqVo);
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
    public HttpResponse getWarehousing(PurchaseStorageRequest purchaseStorage) {
        if (purchaseStorage == null || CollectionUtils.isEmptyCollection(purchaseStorage.getOrderList())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询采购单信息
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrderInfo(order);

        // 变更采购单、采购商品的实际商品数量,最小单位数量
        List<PurchaseOrderProduct> list = purchaseStorage.getOrderList();
        List<PurchaseOrderProduct> productList = Lists.newArrayList();

        Long actualTotalCount = 0L;
        BigDecimal actualProductAmount = big, actualReturnAmount = big, actualGiftAmount = big;
        for (PurchaseOrderProduct product : list) {
            product.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
            // 查询对应sku的商品信息
            PurchaseOrderProduct orderProduct = purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(
                    purchaseStorage.getPurchaseOrderCode(), product.getSkuCode(), product.getLinnum());
            Integer actualCount = orderProduct.getActualSingleCount() == null ? 0 : orderProduct.getActualSingleCount().intValue();
            product.setActualSingleCount(actualCount + product.getActualSingleCount());
            Integer count1 = purchaseOrderProductDao.update(product);
            LOGGER.info("采购入库回传，更改采购单商品信息的实际数量失败" + count1);

            // 预计单品数量
            Integer singleCount = orderProduct.getSingleCount() == null ? 0 : orderProduct.getSingleCount().intValue();
            // 实际单品数量
            Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().intValue();
            if (singleCount - actualSingleCount > 0) {
                // 判断可以再次入库的商品
                //Integer code = inboundDao.selectMaxPurchaseNumBySourceOderCode(purchaseStorage.getPurchaseOrderCode());
                orderProduct.setActualSingleCount(actualSingleCount);
                productList.add(orderProduct);
            }

            // 计算采购单的实际商品 0商品 1赠品 2实物返回
            // 查询含税单价
            BigDecimal amount = orderProduct.getProductAmount() == null ? BigDecimal.ZERO : orderProduct.getProductAmount();
            if(orderProduct.equals(Global.PRODUCT_TYPE_0)) {
                actualProductAmount = amount.add(actualProductAmount);
            }else if(orderProduct.equals(Global.PRODUCT_TYPE_1)) {
                actualGiftAmount = amount.add(actualGiftAmount);
            }else {
                actualReturnAmount = amount.add(actualReturnAmount);
            }
            actualTotalCount += actualSingleCount;
        }

        order.setActualTotalCount(actualTotalCount);
        order.setActualProductAmount(actualProductAmount);
        order.setActualGiftAmount(actualGiftAmount);
        order.setActualReturnAmount(actualReturnAmount);
        order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        // 判断入库次数 、入库是否完成
        purchaseStorage.setPurchaseNum(purchaseStorage.getPurchaseNum() + 1);
        if (purchaseOrder.getInboundLine() > 1 && purchaseStorage.getPurchaseNum() <= purchaseOrder.getInboundLine() &&
                productList.size() >= 1
        ) {
            if(!purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_6)){
                // 变更入库中状态
                order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_6);
                // 添加入库中日志
                log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.WAREHOUSING_IN.getCode(),
                        PurchaseOrderLogEnum.WAREHOUSING_IN.getName(), purchaseOrder.getApplyTypeForm());
            }
            order.setUpdateByName(purchaseStorage.getCreateById());
            order.setUpdateById(purchaseStorage.getCreateByName());
            Integer count = purchaseOrderDao.update(order);
            LOGGER.error("采购单入库中状态修改" + count);

            String code;
            if (purchaseStorage.getPurchaseNum() <= 9) {
                code = "0" + purchaseStorage.getPurchaseNum();
            } else {
                code = purchaseStorage.getPurchaseNum().toString();
            }
            String inboundOderCode = purchaseStorage.getPurchaseOrderCode() + code;
            purchaseStorage.setInboundOderCode(inboundOderCode);
            // 调用入库单接口， 传送wms
            PurchaseInboundRequest inboundRequest = new PurchaseInboundRequest();
            inboundRequest.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            inboundRequest.setInboundOrderCode(inboundOderCode);
            inboundRequest.setPurchaseNum(Integer.valueOf(code));
            inboundRequest.setProductList(productList);
            if(CollectionUtils.isEmptyCollection(productList)){
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_PRODUCT_NULL);
            }
            InboundReqSave save = this.InboundReqSave(inboundRequest);
            String s = inboundService.saveInbound(save);
            if (StringUtils.isBlank(s)) {
                LOGGER.error("生成入库单失败....");
                return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
            }
        } else {
            // 更改入库完成，添加入库完成时间
            order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
            order.setWarehouseTime(Calendar.getInstance().getTime());
            order.setUpdateByName(purchaseStorage.getCreateById());
            order.setUpdateById(purchaseStorage.getCreateByName());
            Integer count = purchaseOrderDao.update(order);
            LOGGER.error("采购单入库完成状态修改" + count);
            // 添加入库完成日志
            log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                    PurchaseOrderLogEnum.PURCHASE_FINISH.getName(), purchaseOrder.getApplyTypeForm());
            // 减在途数
            this.wayNum(purchaseOrder, 11);
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
    public HttpResponse storageConfirm(PurchaseStorageRequest storageRequest) {
        if (StringUtils.isBlank(storageRequest.getPurchaseOrderId())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String purchaseOrderId = storageRequest.getPurchaseOrderId();
        PurchaseOrder order = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if (!order.getStorageStatus().equals(Global.STORAGE_STATUS_1)) {
            LOGGER.info("采购仓储状态非确认中状态， 不能确认");
            return HttpResponse.failure(ResultCode.STORAGE_NOT_CONFIRM);
        }
        // 变更采购单的状态
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseOrderId);
        if (order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)) {
            purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
        }
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_2);
        purchaseOrder.setUpdateById(storageRequest.getCreateById());
        purchaseOrder.setUpdateByName(storageRequest.getCreateByName());
        Integer count = purchaseOrderDao.update(purchaseOrder);
        if (count == 0) {
            LOGGER.error("变更采购单的仓储状态失败......");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 保存质检报告
        if (CollectionUtils.isNotEmptyCollection(storageRequest.getInspectionReport())) {
            List<PurchaseInspectionReport> purchaseInspectionReports = purchaseInspectionReportDao.inspectionReportInfo(purchaseOrderId);
            if (CollectionUtils.isNotEmptyCollection(purchaseInspectionReports)) {
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
            for (PurchaseInspectionReport pir : inspection) {
                // 判断商品的质检报告是否存在， 存在根据sku与生产日期更新上传路径，否则新增
                vo = new QueryProductSkuInspReportReqVo();
                vo.setSkuCode(pir.getSkuCode());
                vo.setProductionDate(pir.getProductionDate());
                List<ProductSkuInspReportRespVo> list = productSkuInspReportDao.getListBySkuCodeAndProductDate(vo);
                inspReport = new ProductSkuInspReport();
                inspReport.setSkuCode(pir.getSkuCode());
                inspReport.setProductionDate(pir.getProductionDate());
                if (list.size() > 0) {
                    inspReport.setUpdateBy(storageRequest.getCreateByName());
                    productSkuInspReportDao.updateInspection(inspReport);
                } else {
                    inspReport.setSkuName(pir.getSkuName());
                    inspReport.setInspectionReportPath(pir.getInspectionReportPath());
                    inspReport.setCreateBy(storageRequest.getCreateByName());
                    productSkuInspReportList.add(inspReport);
                }
            }
            if (CollectionUtils.isNotEmptyCollection(productSkuInspReportList)) {
                productSkuInspReportDao.insertInspReportList(productSkuInspReportList);
            }
        }
        // 保存供应商评分
        if (storageRequest.getScoreRequest() != null) {
            String code = scoreService.saveByPurchase(storageRequest.getScoreRequest());
            if (StringUtils.isBlank(code)) {
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
        // 新增采购完成操作日志
        log(purchaseOrderId, storageRequest.getCreateById(), storageRequest.getCreateByName(), PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                PurchaseOrderLogEnum.PURCHASE_FINISH.getName(), order.getApplyTypeForm());
        //this.wayNum(order);
        return HttpResponse.success();
    }

    // 修改库存在途数
    private void wayNum(PurchaseOrder order, Integer type){
        StockChangeRequest stock = new StockChangeRequest();
        stock.setOperationType(type);
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
    public  HttpResponse<PurchaseFormResponse> skuSupply(String skuCode, String transportCenterCode, String warehouseCode,
                                                         String settlementMethodCode, String purchaseGroupCode){
        if(StringUtils.isBlank(skuCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseFormResponse> purchaseFormResponses = productSkuSupplyUnitDao.supplyList(skuCode);
        if(CollectionUtils.isNotEmptyCollection(purchaseFormResponses)){
            PurchaseApplyRequest request;
            for (PurchaseFormResponse response:purchaseFormResponses){
                request = new PurchaseApplyRequest();
                request.setSkuCode(response.getSkuCode());
                request.setSupplierCode(response.getSupplierCode());
                request.setTransportCenterCode(transportCenterCode);
                request.setSettlementMethodCode(settlementMethodCode);
                request.setWarehouseCode(warehouseCode);
                request.setPurchaseGroupCode(purchaseGroupCode);
                List<PurchaseApplyDetailResponse> responses = purchaseApplyService.productInfo(request);
                response.setSkuData(responses.get(0));
            }
        }
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


    @Override
    public HttpResponse historyDate(String code){
        // 查询旧采购单的数据
        List<PurchaseOrder> orderList = purchaseOrderDao.orderList(code);
        for(PurchaseOrder order:orderList){
            // 查询旧的采购详情数据放的采购单
            PurchaseOrderDetails detail = purchaseOrderDetailsDao.orderDetail(order.getPurchaseOrderCode());
            if(detail == null){
                continue;
            }
            order.setPurchaseApplyId(IdUtil.purchaseId());
            order.setPurchaseApplyCode(order.getPurchaseOrderCode());
            order.setPurchaseSource(0);
            order.setPreArrivalTime(detail.getExpectArrivalTime());
            order.setValidTime(detail.getValidTime());
            order.setChargePersonName(detail.getChargePerson());
            order.setSupplierPerson(detail.getContactPerson());
            order.setSupplierMobile(detail.getMobile());
            order.setDeliveryTime(detail.getDeliveryTime());
            order.setWarehouseTime(detail.getWarehouseTime());
            if(detail.getPaymentCode().equals("2")){
                order.setPaymentMode(1);
            }else if(detail.getPaymentCode().equals("3")){
                order.setPaymentMode(2);
            }else if(detail.getPaymentCode().equals("4")){
                order.setPaymentMode(3);
            }else {
                order.setPaymentMode(0);
            }
            order.setPaymentTime(detail.getPayableTime());
            order.setRemark(detail.getRemark());
            order.setPrePaymentAmount(detail.getAdvancePayment());
            order.setAccountCode(detail.getAccountCode());
            order.setAccountName(detail.getAccountName());
            order.setContractCode(detail.getContractCode());
            order.setContractName(detail.getContractName());
            order.setInboundLine(99);

            // 查询采购单的商品信息
            List<PurchaseOrderProduct> products = purchaseOrderProductDao.orderProductInfo(order.getPurchaseOrderId());
            Long totalCount = 0L;
            BigDecimal productAmount = BigDecimal.ZERO, returnAmount = BigDecimal.ZERO, giftAmount = BigDecimal.ZERO;
            if(CollectionUtils.isNotEmptyCollection(products)){
                for(PurchaseOrderProduct product:products){
                    Integer singCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount();
                    BigDecimal totalAmount = product.getProductTotalAmount() == null ? BigDecimal.ZERO : product.getProductTotalAmount();
                    if(product.getProductType() == 0){
                        productAmount =  productAmount.add(totalAmount);
                    }else if(product.getProductType() == 1){
                        giftAmount =  giftAmount.add(totalAmount);
                    }else {
                        returnAmount =  returnAmount.add(totalAmount);
                    }
                    totalCount += singCount;
                }
            }
            order.setActualTotalCount(totalCount);
            order.setActualProductAmount(productAmount);
            order.setActualReturnAmount(returnAmount);
            order.setActualGiftAmount(giftAmount);
            String brandName = "";
            if(CollectionUtils.isNotEmptyCollection(products)){
                brandName = products.get(0).getBrandName();
            }
            String name = order.getSupplierName() + brandName
                    + "商品金额" + productAmount + "实物返金额" + returnAmount
                    + "赠品金额" + giftAmount;
            order.setPurchaseApplyName(name);
            Integer update = purchaseOrderDao.update(order);
            LOGGER.info("更改采购单的信息" + update);

            // 生成采购申请单 和 对应仓库的信息
            applyInsert(order, detail, products);
        }

        return HttpResponse.success();
    }


    private void applyInsert(PurchaseOrder order, PurchaseOrderDetails detail, List<PurchaseOrderProduct> products){
        // 新增采购申请单
        PurchaseApply info = BeanCopyUtils.copy(order, PurchaseApply.class);
        info.setPurchaseApplyId(order.getPurchaseApplyId());

        info.setApplyType(order.getApplyTypeForm().equals("手动") ? 0 : 1);
        if(order.getPurchaseOrderStatus() == 9){
            info.setApplyStatus(6);
        }else if(order.getPurchaseOrderStatus() == 10){
            info.setApplyStatus(5);
        }else {
            info.setApplyStatus(4);
        }
        info.setStatus(0);
        info.setTotalCount(order.getSingleCount().longValue());
        info.setProductTaxAmount(order.getProductTotalAmount());
        info.setReturnTaxAmount(order.getReturnAmount());
        info.setGiftTaxAmount(order.getGiftTaxSum());
        info.setPurchaseSource(1);
        if(StringUtils.isNotBlank(detail.getPurchaseOrderPre())){
            info.setPrePurchaseCode(detail.getPurchaseOrderPre());
            info.setPrePurchaseType(1);
        }else {
            info.setPrePurchaseType(0);
        }


        info.setDirectSupervisorCode(detail.getDirectSupervisorCode());
        info.setDirectSupervisorName(detail.getDirectSupervisorName());

        // 查询商品信息
        List<PurchaseApplyProduct> orderItem = Lists.newArrayList();
        Long productCount = 0L, returnCount = 0L, giftCount = 0L;
        if(CollectionUtils.isNotEmptyCollection(products)){
            info.setBrandId(products.get(0).getBrandId());
            info.setBrandName(products.get(0).getBrandName());
            PurchaseApplyProduct applyProduct;
            for(PurchaseOrderProduct product: products){
                applyProduct = BeanCopyUtils.copy(product, PurchaseApplyProduct.class);
                applyProduct.setApplyProductId(IdUtil.uuid());
                applyProduct.setPurchaseApplyId(info.getPurchaseApplyId());
                applyProduct.setPurchaseApplyCode(info.getPurchaseApplyCode());
                applyProduct.setWarehouseCode(order.getWarehouseCode());
                applyProduct.setWarehouseName(order.getWarehouseName());
                applyProduct.setTransportCenterName(order.getTransportCenterName());
                applyProduct.setTransportCenterCode(order.getTransportCenterCode());
                applyProduct.setSupplierCode(order.getSupplierCode());
                applyProduct.setSupplierName(order.getSupplierName());
                applyProduct.setPurchaseGroupCode(order.getPurchaseGroupCode());
                applyProduct.setPurchaseGroupName(order.getPurchaseGroupName());
                applyProduct.setProductPurchaseAmount(product.getProductAmount());
                applyProduct.setApplyProductStatus(1);
                orderItem.add(applyProduct);
                if(product.getProductType() == 0){
                    productCount += product.getSingleCount();
                }else if(product.getProductType() == 1){
                    giftCount += product.getSingleCount();
                }else {
                    returnCount += product.getSingleCount();
                }
            }
        }
        info.setProductCount(productCount);
        info.setReturnCount(returnCount);
        info.setGiftCount(giftCount);
        Integer insert = purchaseApplyDao.insert(info);
        LOGGER.info("采购申请单新增" + insert);

        // 新增采购申请仓库信息
        PurchaseApplyTransportCenter center = BeanCopyUtils.copy(order, PurchaseApplyTransportCenter.class);
        center.setTotalCount(order.getSingleCount().longValue());
        center.setProductTaxAmount(order.getProductTotalAmount());
        center.setReturnTaxAmount(order.getReturnAmount());
        center.setGiftTaxAmount(order.getGiftTaxSum());
        center.setInboundLine(99);
        int insert1 = purchaseApplyTransportCenterDao.insert(center);
        LOGGER.info("采购申请单分仓新增" + insert1);

        if(CollectionUtils.isNotEmptyCollection(orderItem)){
            Integer insertAll = purchaseApplyProductDao.insertAll(orderItem);
            LOGGER.info("采购申请单商品的新增" + insertAll);
        }

    }
}
