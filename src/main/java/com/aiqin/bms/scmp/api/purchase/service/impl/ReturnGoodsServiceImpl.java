package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.dl.BatchRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.EchoOrderRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.dl.ProductRequest;
import com.aiqin.bms.scmp.api.abutment.service.DlAbutmentService;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.InboundBatchDao;
import com.aiqin.bms.scmp.api.product.dao.InboundDao;
import com.aiqin.bms.scmp.api.product.dao.InboundProductDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuCheckoutDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnGoodsRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnInspectionRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderInboundBatchResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderInspectionResponse;
import com.aiqin.bms.scmp.api.purchase.mapper.*;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.bms.scmp.api.purchase.service.asyn.AsynSaveDocuments;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:35
 */
@Service
@Slf4j
public class ReturnGoodsServiceImpl extends BaseServiceImpl implements ReturnGoodsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnGoodsServiceImpl.class);

    @Autowired
    private ReturnOrderInfoMapper returnOrderInfoMapper;
    @Autowired
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private ReturnOrderInfoInspectionItemMapper returnOrderInfoInspectionItemMapper;
    @Autowired
    private ReturnOrderInfoLogMapper returnOrderInfoLogMapper;
    @Autowired
    private InboundDao inboundDao;
    @Autowired
    private InboundProductDao inboundProductDao;
    @Autowired
    private InboundBatchDao inboundBatchDao;
    @Autowired
    private OrderInfoItemMapper orderInfoItemMapper;
    @Autowired
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;
    @Resource
    private SapBaseDataService sapBaseDataService;
    @Resource
    private WarehouseDao warehouseDao;
    @Resource
    private ProductSkuCheckoutDao productSkuCheckoutDao;
    @Resource
    @Lazy
    private DlAbutmentService dlAbutmentService;

    @Resource
    private AsynSaveDocuments asynSaveDocuments;

    @Override
    public HttpResponse<ReturnOrderDetailResponse> returnOrderDetail(String returnOrderCode) {
        LOGGER.info("查询退货单详情：", returnOrderCode);
        if (StringUtils.isBlank(returnOrderCode)) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(returnOrderCode);
        ReturnOrderDetailResponse response = BeanCopyUtils.copy(returnOrderInfo, ReturnOrderDetailResponse.class);
        // 查询退货单的日志信息
        List<ReturnOrderInfoLog> logs = returnOrderInfoLogMapper.returnOrderLog(returnOrderCode);
        response.setLogList(logs);
        // 查询入库单的基本信息
        List<Inbound> inbounds = inboundDao.inboundBySource(returnOrderCode, String.valueOf(InboundTypeEnum.ORDER.getCode()));
        response.setInboundList(inbounds);
        return HttpResponse.successGenerics(response);
    }

    @Override
    public HttpResponse inboundBatch(InboundBatchReqVo request) {
        List<ReturnOrderInboundBatchResponse> list = returnOrderInfoInspectionItemMapper.inboundBatchByReturnOrderList(request);
        Integer count = returnOrderInfoInspectionItemMapper.inboundBatchByReturnOrderCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfo>> returnOrderList(ReturnGoodsRequest request) {
        List<ReturnOrderInfo> list = returnOrderInfoMapper.list(request);
        Integer count = returnOrderInfoMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfoItem>> returnOrderProductList(ReturnGoodsRequest request) {
        List<ReturnOrderInfoItem> list = returnOrderInfoItemMapper.list(request);
        Integer count = returnOrderInfoItemMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfoInspectionItem>> returnOrderBatchList(ReturnGoodsRequest request) {
        List<ReturnOrderInfoInspectionItem> list = returnOrderInfoInspectionItemMapper.list(request);
        Integer count = returnOrderInfoInspectionItemMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse returnOrderCancel(ReturnOrderInfo returnOrderInfo) {
        if (null == returnOrderInfo && StringUtils.isNotBlank(returnOrderInfo.getReturnOrderCode())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfo orderInfo = returnOrderInfoMapper.selectByCode(returnOrderInfo.getReturnOrderCode());
        if (orderInfo.getOrderType().equals(Global.ORDER_TYPE_2)) {
            if (!orderInfo.getOrderStatus().equals(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStatusCode()) &&
                    !orderInfo.getOrderStatus().equals(ReturnOrderStatus.PENDING_REVIEW.getStatusCode()) &&
                    !orderInfo.getOrderStatus().equals(ReturnOrderStatus.EXAMINATION_PASSED.getStatusCode()) &&
                    !orderInfo.getOrderStatus().equals(ReturnOrderStatus.RETURN_ORDER_SYNCHRONIZATION.getStatusCode()) &&
                    !orderInfo.getOrderStatus().equals(ReturnOrderStatus.WAITING_FOR_RETURN_TO_INSPECTION.getStatusCode())) {
                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单非取消状态，取消失败"));
            }
        } else {
            if (!orderInfo.getOrderStatus().equals(ReturnOrderStatus.PENDING_REVIEW.getStatusCode()) &&
                    !orderInfo.getOrderStatus().equals(ReturnOrderStatus.EXAMINATION_PASSED.getStatusCode()) &&
                    !orderInfo.getOrderStatus().equals(ReturnOrderStatus.RETURN_ORDER_SYNCHRONIZATION.getStatusCode()) &&
                    !orderInfo.getOrderStatus().equals(ReturnOrderStatus.WAITING_FOR_RETURN_TO_INSPECTION.getStatusCode())) {
                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单非取消状态，取消失败"));
            }
        }

        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        orderInfo.setUpdateByName(returnOrderInfo.getUpdateByName());
        orderInfo.setUpdateById(returnOrderInfo.getUpdateById());
        log.setOperator(returnOrderInfo.getUpdateByName());
        orderInfo.setOrderStatus(ReturnOrderStatus.cancelled.getStatusCode());
        orderInfo.setReturnReasonContent(returnOrderInfo.getReturnReasonContent());
        Integer count = returnOrderInfoMapper.update(orderInfo);
        LOGGER.info("更改退货单异取消状态：{}", count);

        // 添加日志
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        log.setOrderCode(returnOrderInfo.getReturnOrderCode());
        log.setRemark(ReturnOrderStatus.cancelled.getStandardDescription());
        log.setStatus(ReturnOrderStatus.cancelled.getStatusCode());
        log.setStatusDesc(ReturnOrderStatus.cancelled.getExplain());
        log.setContent(ReturnOrderStatus.cancelled.getBackgroundOrderStatus());
        Integer logCount = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货单取消日志：{}", logCount);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse saveReturnInspection(ReturnInspectionRequest request) {
        LOGGER.info("退货单开始退货验货：{}", request.getReturnOrderCode());
        if (request == null || CollectionUtils.isEmptyCollection(request.getItemList())) {
            LOGGER.info("操作退货验货信息不正确：{}", JsonUtil.toJson(request));
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }

        // 查询验货退货数量
        Map<String, Long> skuMap = new HashMap<>();
        String skuKey;
        for (ReturnOrderInfoInspectionItem item : request.getItemList()){
            skuKey = String.format("%s,%s", item.getSkuCode(), item.getLineCode());
            Long count = item.getProductCount() == null ? 0L : item.getProductCount();
            if(skuMap.get(skuKey) == null){
                skuMap.put(skuKey, count);
            }else {
                skuMap.put(skuKey, count + skuMap.get(skuKey));
            }
        }

        // 判断商品可退数量
        for (ReturnOrderInfoInspectionItem item : request.getItemList()){
            skuKey = String.format("%s,%s", item.getSkuCode(), item.getLineCode());
            Long sum = skuMap.get(skuKey);
            if(sum > item.getReturnProductCount()){
                LOGGER.info("退货单验货数量大于商品退货数量:{}", item.getSkuCode());
                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单验货数量大于商品退货数量:" +
                        item.getSkuCode()));
            }
        }

        // 保存退货验货
        ReturnOrderInfo returnOrder = new ReturnOrderInfo();
        returnOrder.setReturnOrderCode(request.getReturnOrderCode());
        returnOrder.setInspectionRemark(request.getInspectionRemark());
        returnOrder.setUpdateById(getUser().getPersonId());
        returnOrder.setUpdateByName(getUser().getPersonName());
        returnOrder.setOrderStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStatusCode());
        Integer orderCount = returnOrderInfoMapper.update(returnOrder);
        LOGGER.info("更新退货单验货退货信息：{}", orderCount);

        // 查询商品信息
        String notKey;
        Map<String, ReturnOrderInfoInspectionItem> notMap = new HashMap<>();
        for (ReturnOrderInfoInspectionItem item : request.getItemList()) {
            notKey = String.format("%s,%s,%s,%s", item.getSkuCode(), item.getBatchCode(), item.getWarehouseCode(), item.getLineCode());
            if (notMap.get(notKey) == null) {
                notMap.put(notKey, item);
            } else {
                LOGGER.info("退货验货数据重复不可提交:{}", JsonUtil.toJson(item));
                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货验货数据重复不可提交"));
            }
        }

        if (CollectionUtils.isNotEmptyCollection(request.getItemList())) {
            request.getItemList().stream().forEach(o->o.setLockType(1));
            Integer batchCount = returnOrderInfoInspectionItemMapper.insertBatch(request.getItemList());
            LOGGER.info("保存退货单验货商品信息：{}", batchCount);
        }

        // 调用生成入库单 并传送wms
        getInboundReqSave(request.getReturnOrderCode());

        // 推送结算
        //sapBaseDataService.saleAndReturn(itemList.get(0).getReturnOrderCode(), 1);

        // 添加日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        log.setOperator(getUser().getPersonName());
        log.setOrderCode(request.getReturnOrderCode());
        log.setRemark(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStandardDescription());
        log.setStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStatusCode());
        log.setStatusDesc(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getExplain());
        log.setContent(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getBackgroundOrderStatus());
        Integer logCount = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货验货日志：{}", logCount);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse returnReceipt(List<ReturnOrderInfoItem> itemList) {
        if (CollectionUtils.isEmptyCollection(itemList)) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfoItem returnOrderInfoItem;
        Long actualProductCount = 0L;
        BigDecimal actualTotalProductAmount = BigDecimal.ZERO, actualTotalChannelAmount = BigDecimal.ZERO;
        for (ReturnOrderInfoItem item : itemList) {
            returnOrderInfoItem = new ReturnOrderInfoItem();
            returnOrderInfoItem.setId(item.getId());
            returnOrderInfoItem.setActualPrice(item.getPrice());
            returnOrderInfoItem.setActualChannelUnitPrice(item.getChannelUnitPrice());
            returnOrderInfoItem.setActualInboundNum(item.getActualInboundNum());
            Integer count = returnOrderInfoItemMapper.update(returnOrderInfoItem);
            LOGGER.info("直送退货单实退的商品数量：{}", count);

            actualProductCount += item.getActualInboundNum() == null ? 0 : item.getActualInboundNum();
            actualTotalProductAmount = actualTotalProductAmount.add(item.getActualAmount() == null ? BigDecimal.ZERO : item.getActualAmount());
            actualTotalChannelAmount = actualTotalChannelAmount.add(item.getActualTotalChannelPrice() == null ? BigDecimal.ZERO : item.getActualTotalChannelPrice());
        }

        ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
        returnOrderInfo.setActualVolume(0L);
        returnOrderInfo.setActualWeight(0L);
        // 计算实际体积/重量
        if (returnOrderInfo.getVolume() != null && returnOrderInfo.getVolume() > 0 && actualProductCount > 0) {
            returnOrderInfo.setActualVolume(actualProductCount / returnOrderInfo.getVolume());
        }
        if (returnOrderInfo.getWeight() != null && returnOrderInfo.getWeight() > 0 && actualProductCount > 0) {
            returnOrderInfo.setActualWeight(actualProductCount / returnOrderInfo.getWeight());
        }
        returnOrderInfo.setActualProductCount(actualProductCount);
        returnOrderInfo.setActualProductTotalAmount(actualTotalProductAmount);
        returnOrderInfo.setActualProductChannelTotalAmount(actualTotalChannelAmount);
        returnOrderInfo.setDeliveryTime(Calendar.getInstance().getTime());
        returnOrderInfo.setReturnOrderCode(itemList.get(0).getReturnOrderCode());
        returnOrderInfo.setUpdateById(itemList.get(0).getPersonId());
        returnOrderInfo.setUpdateByName(itemList.get(0).getPersonName());
        returnOrderInfo.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        returnOrderInfo.setTransportCompany(itemList.get(0).getTransportCompany());
        returnOrderInfo.setTransportCompanyCode(itemList.get(0).getTransportCompanyCode());
        returnOrderInfo.setTransportNumber(itemList.get(0).getTransportNumber());
        Integer returnCount = returnOrderInfoMapper.update(returnOrderInfo);
        LOGGER.info("退货单退货收货完成变更退货单状态：{}", returnCount);

        // 添加日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        log.setOperator(itemList.get(0).getPersonName());
        log.setOrderCode(itemList.get(0).getReturnOrderCode());
        log.setRemark(ReturnOrderStatus.RETURN_COMPLETED.getStandardDescription());
        log.setStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        log.setStatusDesc(ReturnOrderStatus.RETURN_COMPLETED.getExplain());
        log.setContent(ReturnOrderStatus.RETURN_COMPLETED.getBackgroundOrderStatus());
        Integer count = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货单退货收货的日志：{}", count);

        // 退货收货完成- 直送订单 回传运营中台
        HttpResponse response = changeParameter(itemList.get(0).getReturnOrderCode());

        // 推送结算
        //sapBaseDataService.saleAndReturn(itemList.get(0).getReturnOrderCode(), 1);

        //异步保存单据
        asynSaveDocuments.saveReject(itemList.get(0).getReturnOrderCode());
        return response;
    }

    @Override
    public HttpResponse<List<ReturnOrderInspectionResponse>> inspectionBatch(String returnOrderCode) {
        List<ReturnOrderInfoItem> list = returnOrderInfoItemMapper.selectByReturnOrderCode(returnOrderCode);
        List<ReturnOrderInspectionResponse> responses = BeanCopyUtils.copyList(list, ReturnOrderInspectionResponse.class);
        if (CollectionUtils.isNotEmptyCollection(responses)) {
            ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(returnOrderCode);
            for (ReturnOrderInspectionResponse response : responses) {
                if (StringUtils.isBlank(response.getSkuCode()) || response.getOrderLineCode() == null) {
                    continue;
                }
                List<OrderInfoItemProductBatch> batches = orderInfoItemProductBatchMapper.orderBatchList(
                        response.getSkuCode(), returnOrderInfo.getOrderCode(), response.getOrderLineCode().intValue());
                response.setBatchList(batches);
            }
        }
        return HttpResponse.successGenerics(responses);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse record(ReturnReq request) {
        LOGGER.info("运营中台调用耘链，开始生成退货单：{}", JsonUtil.toJson(request));
        // 进行主表添加
        if (request == null || request.getReturnOrderInfo() == null ||
                CollectionUtils.isEmptyCollection(request.getReturnOrderDetailReqList())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfoReq returnOrderInfo = request.getReturnOrderInfo();
        ReturnOrderInfo returnOrder = BeanCopyUtils.copy(request.getReturnOrderInfo(), ReturnOrderInfo.class);
        if (null != returnOrderInfo.getPlatformType() && returnOrderInfo.getPlatformType().equals(Global.PLATFORM_TYPE_1)) {
            returnOrder.setPlatformType(Global.PLATFORM_TYPE_1);
        } else {
            returnOrder.setPlatformType(Global.PLATFORM_TYPE_0);
        }

        if(StringUtils.isNotBlank(returnOrder.getWarehouseCode())){
            WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(returnOrder.getWarehouseCode());
            returnOrder.setWarehouseName(warehouse.getWarehouseName());
            returnOrder.setTransportCenterName(warehouse.getLogisticsCenterName());
        }
        returnOrder.setOrderOriginal(returnOrderInfo.getReturnOrderId());
        returnOrder.setOrderCode(returnOrderInfo.getOrderStoreCode());
        returnOrder.setCreateDate(returnOrderInfo.getCreateTime());
        returnOrder.setBeLock(returnOrderInfo.getReturnLock());
        returnOrder.setLockReason(returnOrderInfo.getReturnReason());
        returnOrder.setDetailAddress(returnOrderInfo.getReceiveAddress());
        returnOrder.setConsignee(returnOrderInfo.getReceivePerson());
        returnOrder.setConsigneePhone(returnOrderInfo.getReceivePerson());
        returnOrder.setDistributionMode(returnOrderInfo.getDistributionModeName());
        // 退货单状态
        if (returnOrderInfo.getOrderType().equals(Global.ORDER_TYPE_1)) {
            returnOrder.setOrderStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_INSPECTION.getStatusCode());
        } else {
            returnOrder.setOrderStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStatusCode());
        }
        returnOrder.setPaymentTypeCode(returnOrderInfo.getPaymentCode());
        returnOrder.setPaymentType(returnOrderInfo.getPaymentName());
        returnOrder.setProductCount(returnOrderInfo.getProductCount());
        returnOrder.setProductTotalAmount(returnOrderInfo.getReturnOrderAmount());
        returnOrder.setWeight(returnOrderInfo.getTotalWeight());
        returnOrder.setVolume(returnOrderInfo.getTotalVolume());
        returnOrder.setCompanyCode(Global.COMPANY_09);
        returnOrder.setCompanyName(Global.COMPANY_09_NAME);
        returnOrder.setPartnerCode(returnOrderInfo.getCopartnerAreaId());
        returnOrder.setPartnerName(returnOrderInfo.getCopartnerAreaName());
        returnOrder.setBusinessForm(returnOrderInfo.getBusinessForm());
        Integer count = returnOrderInfoMapper.insert(returnOrder);
        LOGGER.info("添加退货单条数：{}", count);

        List<ReturnOrderDetailReq> detailList = request.getReturnOrderDetailReqList();
        List<ReturnOrderInfoItem> details = Lists.newArrayList();
        ReturnOrderInfoItem returnOrderInfoItem;
        for (ReturnOrderDetailReq returnOrderDetail : detailList) {
            returnOrderInfoItem = BeanCopyUtils.copy(returnOrderDetail, ReturnOrderInfoItem.class);
            returnOrderInfoItem.setSpec(returnOrderDetail.getProductSpec());
            returnOrderInfoItem.setModel(returnOrderDetail.getModelCode());
            returnOrderInfoItem.setGivePromotion(returnOrderDetail.getProductType());
            returnOrderInfoItem.setPrice(returnOrderDetail.getProductAmount());
            returnOrderInfoItem.setNum(returnOrderDetail.getReturnProductCount());
            returnOrderInfoItem.setAmount(returnOrderDetail.getTotalProductAmount());
            returnOrderInfoItem.setProductLineNum(returnOrderDetail.getLineCode());
            if (returnOrder.getPlatformType().equals(Global.PLATFORM_TYPE_1)) {
                returnOrderInfoItem.setOrderLineCode(returnOrderDetail.getOrderLineCode());
            } else {
                returnOrderInfoItem.setOrderLineCode(returnOrderDetail.getLineCode());
            }
            returnOrderInfoItem.setProductStatus(returnOrderDetail.getProductStatus());
            returnOrderInfoItem.setCompanyCode(Global.COMPANY_09);
            returnOrderInfoItem.setCompanyName(Global.COMPANY_09_NAME);
            returnOrderInfoItem.setChannelUnitPrice(returnOrderDetail.getProductAmount());
            returnOrderInfoItem.setTax(returnOrderDetail.getTaxRate());
            returnOrderInfoItem.setChannelUnitPrice(returnOrderDetail.getChannelAmount());
            returnOrderInfoItem.setTotalChannelPrice(returnOrderDetail.getChannelTotalAmount());
            returnOrderInfoItem.setInsertType(1);
            details.add(returnOrderInfoItem);
        }
        Integer detailCount = returnOrderInfoItemMapper.insertList(details);
        LOGGER.info("添加退货单详情条数：{}", detailCount);

        // 添加退货单日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog();
        log.setOrderCode(returnOrder.getReturnOrderCode());
        log.setStatus(Integer.valueOf(InOutStatus.CREATE_INOUT.getCode()));
        log.setStatusDesc(InOutStatus.CREATE_INOUT.getName());
        log.setRemark(returnOrder.getRemake());
        log.setOperator(returnOrder.getCreateByName());
        log.setOperatorTime(returnOrder.getCreateTime());
        log.setCompanyCode(Global.COMPANY_09);
        log.setCompanyName(Global.COMPANY_09_NAME);
        Integer logCount = returnOrderInfoLogMapper.insert(log);
        LOGGER.info("添加退货单日志条数：", logCount);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse changeParameter(String returnOrderCode) {
        if (StringUtils.isBlank(returnOrderCode)) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        LOGGER.info("开始转换退货单参数传送运营中台：{}", returnOrderCode);
        // 查询退货单信息
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(returnOrderCode);
        if (returnOrderInfo == null) {
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单的信息为空"));
        }
        // 查询退货单商品信息
        List<ReturnOrderInfoItem> infoItems = returnOrderInfoItemMapper.selectByReturnOrderCode(returnOrderCode);
        if (CollectionUtils.isEmptyCollection(infoItems)) {
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单的商品信息为空"));
        }

        // 赋值传送运营中台的参数
        ReturnDLReq response = new ReturnDLReq();
        EchoOrderRequest request = new EchoOrderRequest();
        if (returnOrderInfo.getPlatformType().equals(Global.PLATFORM_TYPE_0)) {
            // 封装回调爱亲供应链的参数
            ReturnOrderInfoDLReq orderInfo = new ReturnOrderInfoDLReq();
            orderInfo.setReturnOrderCode(returnOrderCode);
            orderInfo.setActualProductCount(returnOrderInfo.getActualProductCount());
            orderInfo.setReturnById(returnOrderInfo.getUpdateById());
            orderInfo.setReturnTime(returnOrderInfo.getUpdateTime());
            response.setReturnOrderInfoDLReq(orderInfo);
        } else {
            // 封装回调dl的参数
            request.setOrderCode(returnOrderInfo.getReturnOrderCode());
            request.setOperationTime(returnOrderInfo.getDeliveryTime());
            request.setOperationType(4);
            request.setOperationCode(returnOrderInfo.getUpdateById());
            request.setOperationName(returnOrderInfo.getCreateByName());
            request.setOrderId(returnOrderInfo.getOrderOriginal());
            request.setBusinessForm(returnOrderInfo.getBusinessForm());
        }

        // 爱亲供应链的商品
        List<ReturnOrderDetailDLReq> orderItems = Lists.newArrayList();
        ReturnOrderDetailDLReq returnOrderItem;

        // DL的商品
        List<ProductRequest> productList = Lists.newArrayList();
        ProductRequest product;
        List<BatchRequest> dlBatchList;
        BatchRequest batchRequest;

        for (ReturnOrderInfoItem item : infoItems) {
            if(item.getActualInboundNum() == null){
                continue;
            }
            if (returnOrderInfo.getPlatformType().equals(Global.PLATFORM_TYPE_0)) {
                returnOrderItem = new ReturnOrderDetailDLReq();
                returnOrderItem.setActualReturnProductCount(item.getActualInboundNum().longValue());
                returnOrderItem.setLineCode(item.getProductLineNum());
                returnOrderItem.setSkuCode(item.getSkuCode());
                returnOrderItem.setSkuName(item.getSkuName());
                orderItems.add(returnOrderItem);
            } else {
                if(item.getActualInboundNum() == null){
                    continue;
                }
                // 如果平台类型为l 赋值回传dl的参数
                product = new ProductRequest();
                product.setLineCode(item.getProductLineNum().intValue());
                product.setSkuCode(item.getSkuCode());
                product.setActualTotalCount(item.getActualInboundNum().longValue());
                product.setWarehouseCode(returnOrderInfo.getWarehouseCode());
                product.setOrderLineCode(item.getOrderLineCode());

                dlBatchList = Lists.newArrayList();
                // 查询退货单对应的批次信息
                List<ReturnOrderInfoInspectionItem> productBatchItems =
                        returnOrderInfoInspectionItemMapper.returnBatchList(item.getSkuCode(), returnOrderInfo.getReturnOrderCode(), item.getProductLineNum().intValue());
                if (CollectionUtils.isNotEmptyCollection(productBatchItems)) {
                    for (ReturnOrderInfoInspectionItem batchItem : productBatchItems) {
                        // 查询库房信息
                        WarehouseDTO warehouse = warehouseDao.getWarehouseByCode(batchItem.getWarehouseCode());
                        if(warehouse == null || warehouse.getBatchManage().equals(Global.BATCH_MANAGE_0)){
                            continue;
                        }
                        if(batchItem.getActualProductCount() == null){
                            continue;
                        }
                        batchRequest = new BatchRequest();
                        batchRequest.setLineCode(batchItem.getLineCode().intValue());
                        batchRequest.setSkuCode(batchItem.getSkuCode());
                        batchRequest.setBatchCode(batchItem.getBatchCode());
                        batchRequest.setProductDate(batchItem.getProductDate());
                        batchRequest.setBeOverdueDate(batchItem.getBeOverdueDate());
                        batchRequest.setActualTotalCount(batchItem.getActualProductCount());
                        product.setWarehouseCode(batchItem.getWarehouseCode());
                        dlBatchList.add(batchRequest);
                    }
                    product.setBatchList(dlBatchList);
                }
                productList.add(product);
            }
        }

        HttpClient httpClient;
        HttpResponse httpResponse;
        StringBuilder sb = new StringBuilder();
        if (returnOrderInfo.getPlatformType().equals(Global.PLATFORM_TYPE_0)) {
            response.setReturnOrderDetailDLReqList(orderItems);

            // 封装回调爱亲供应链的批次信息
            List<ReturnOrderInfoInspectionItem> inspectionItems = returnOrderInfoInspectionItemMapper.returnOrderBatchList(returnOrderCode);
            if (CollectionUtils.isNotEmptyCollection(inspectionItems) && inspectionItems.size() > 0) {
                List<ReturnBatchDetailDLReq> batchList = Lists.newArrayList();
                ReturnBatchDetailDLReq batchInfo;

                for (ReturnOrderInfoInspectionItem batch : inspectionItems) {
                    batchInfo = new ReturnBatchDetailDLReq();
                    batchInfo.setSkuCode(batch.getSkuCode());
                    batchInfo.setSkuName(batch.getSkuName());
                    batchInfo.setLineCode(batch.getLineCode());
                    batchInfo.setBatchNum(batch.getProductCount().intValue());
                    batchInfo.setActualReturnProductCount(batch.getActualProductCount());
                    batchList.add(batchInfo);
                }
                response.setReturnBatchDetailDLReqList(batchList);
            }
            LOGGER.info("退货单回调爱亲供应链参数：{}", JsonUtil.toJson(response));
            sb.append(urlConfig.Order_URL).append("/reject/info");
            httpClient = HttpClient.post(String.valueOf(sb)).json(response).timeout(10000);
            httpResponse = httpClient.action().result(new TypeReference<HttpResponse<Boolean>>() {
            });
        } else {
            request.setProductList(productList);
            LOGGER.info("退货单回调DL参数：{}", JsonUtil.toJson(request));
            httpResponse = dlAbutmentService.echoOrderInfo(request);
        }

        // 判断回调是否成功
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            LOGGER.info("退货单回调成功");
            return HttpResponse.success();
        } else {
            LOGGER.info("退货单回调运营中台或者DL失败：{}", httpResponse.getMessage());
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货单回调运营中台或者DL失败"));
        }
    }

    @Override
    public HttpResponse recordWMS(String inboundOderCode) {
        // 查询入库单的信息
        Inbound inbound = inboundDao.selectByCode(inboundOderCode);
        if (inbound == null) {
            LOGGER.info("退货单wms回传入库单的信息为空:{}", JsonUtil.toJson(inbound));
            return HttpResponse.failure(ResultCode.INBOUND_INFO_NULL);
        }
        // 查询退货单的信息
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(inbound.getSourceOderCode());
        if (returnOrderInfo == null) {
            LOGGER.info("退货单数据查询失败:{}", inbound.getSourceOderCode());
            return HttpResponse.failure(ResultCode.CAN_NOT_FIND_RETURN_ORDER);
        }
        ReturnOrderInfo returnOrder = new ReturnOrderInfo();
        returnOrder.setReturnOrderCode(inbound.getSourceOderCode());

        Long inboundCount = inbound.getPraMainUnitNum() == null ? 0L : inbound.getPraMainUnitNum();
        Long returnCount = returnOrderInfo.getActualProductCount() == null ? 0L : returnOrderInfo.getActualProductCount();
        returnOrder.setActualProductCount(inboundCount + returnCount);

        // 判断所有入库单是否完成
        Integer isComplete = inboundDao.inboundIsComplete(returnOrderInfo.getReturnOrderCode(), String.valueOf(InboundTypeEnum.ORDER.getCode()));
        if(isComplete <= 0){
            returnOrder.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        }
        returnOrder.setUpdateByName(inbound.getUpdateBy());

        // 查询入库单的商品信息
        BigDecimal actualChannelAmount = BigDecimal.ZERO, actualProductAmount = BigDecimal.ZERO;
        ReturnOrderInfoItem returnOrderInfoItem;
        List<InboundProduct> inboundProducts = inboundProductDao.selectByInboundOderCode(inboundOderCode);

        OrderInfoItem orderInfoItem;
        for (InboundProduct product : inboundProducts) {
            // 查询对应的退货单商品信息
            returnOrderInfoItem = returnOrderInfoItemMapper.returnOrderOne(inbound.getSourceOderCode(), product.getSkuCode(), product.getLinenum());
            Integer productCount = returnOrderInfoItem.getActualInboundNum() == null ? 0 : returnOrderInfoItem.getActualInboundNum();
            Integer praInboundMainNum = product.getPraInboundMainNum() == null ? 0 : product.getPraInboundMainNum().intValue();
            if(praInboundMainNum == 0){
                continue;
            }
            returnOrderInfoItem.setActualInboundNum(productCount + praInboundMainNum);
            returnOrderInfoItem.setActualChannelUnitPrice(returnOrderInfoItem.getChannelUnitPrice());
            BigDecimal channelAmount = BigDecimal.valueOf(praInboundMainNum).multiply(
                    returnOrderInfoItem.getChannelUnitPrice()).setScale(4, BigDecimal.ROUND_HALF_UP);
            returnOrderInfoItem.setActualTotalChannelPrice(channelAmount.add(returnOrderInfoItem.getActualTotalChannelPrice()));
            returnOrderInfoItem.setActualAmount(returnOrderInfoItem.getPrice());
            BigDecimal totalAmount = BigDecimal.valueOf(praInboundMainNum).multiply(
                    returnOrderInfoItem.getPrice()).setScale(4, BigDecimal.ROUND_HALF_UP);
            returnOrderInfoItem.setActualPrice(totalAmount.add(returnOrderInfoItem.getActualPrice()));
            Integer returnInfoProduct = returnOrderInfoItemMapper.update(returnOrderInfoItem);
            log.info("更新退货单商品:", returnInfoProduct);

            // 更新订单的退货数量
            orderInfoItem = new OrderInfoItem();
            orderInfoItem.setSkuCode(returnOrderInfoItem.getSkuCode());
            orderInfoItem.setProductLineNum(returnOrderInfoItem.getProductLineNum());
            orderInfoItem.setOrderCode(returnOrderInfo.getOrderCode());
            orderInfoItem.setReturnNum(productCount + product.getPraInboundMainNum());
            Integer count = orderInfoItemMapper.updateByReturnCount(orderInfoItem);
            LOGGER.info("更改退货单对应销售单商品的实际退货数量：{}", count);

            actualChannelAmount = actualChannelAmount.add(channelAmount);
            actualProductAmount = actualProductAmount.add(totalAmount);
        }

        returnOrder.setActualProductChannelTotalAmount(actualChannelAmount);
        returnOrder.setActualProductTotalAmount(actualProductAmount);
        returnOrder.setActualVolume(0L);
        returnOrder.setActualWeight(0L);
        // 计算实际体积/重量
        if (returnOrder.getVolume() != null && returnOrder.getVolume() > 0 && returnOrder.getActualProductCount() > 0) {
            returnOrder.setActualVolume(returnOrder.getActualProductCount() / returnOrder.getVolume());
        }
        if (returnOrder.getWeight() != null && returnOrder.getWeight() > 0 && returnOrder.getActualProductCount() > 0) {
            returnOrder.setActualWeight(returnOrder.getActualProductCount() / returnOrder.getWeight());
        }
        // 查询入库单的批次信息
        List<InboundBatch> inboundBatches = inboundBatchDao.selectInboundBatchList(inboundOderCode);
        List<ReturnOrderInfoInspectionItem> batchList = Lists.newArrayList();
        ReturnOrderInfoInspectionItem returnBatchItem;
        ReturnOrderInfoInspectionItem returnBatch;
        OrderInfoItemProductBatch productBatch;
        for (InboundBatch batch : inboundBatches) {
            returnBatchItem = returnOrderInfoInspectionItemMapper.returnOrderInfo(batch.getBatchCode(),
                    inbound.getSourceOderCode(), batch.getLineCode(), batch.getSkuCode(), inbound.getWarehouseCode());

            if (returnBatchItem == null) {
                returnBatch = new ReturnOrderInfoInspectionItem();
                returnBatch.setReturnOrderCode(inbound.getSourceOderCode());
                returnBatch.setSkuCode(batch.getSkuCode());
                returnBatch.setSkuName(batch.getSkuName());
                returnBatch.setLineCode(batch.getLineCode().longValue());
                returnBatch.setReturnProductCount(batch.getTotalCount());
                returnBatch.setActualProductCount(batch.getActualTotalCount());
                returnBatch.setBatchInfoCode(batch.getBatchInfoCode());
                returnBatch.setBatchCode(batch.getBatchCode());
                returnBatch.setBeOverdueDate(batch.getBeOverdueDate());
                returnBatch.setProductDate(batch.getProductDate());
                returnBatch.setBatchRemark(batch.getBatchRemark());
                returnBatch.setTransportCenterCode(inbound.getLogisticsCenterCode());
                returnBatch.setTransportCenterName(inbound.getLogisticsCenterName());
                returnBatch.setWarehouseCode(inbound.getWarehouseCode());
                returnBatch.setWarehouseName(inbound.getWarehouseName());
                returnBatch.setSupplierCode(inbound.getSupplierCode());
                returnBatch.setSupplierName(inbound.getSupplierName());
                returnBatch.setLockType(1);
                batchList.add(returnBatch);
            } else {
                Long batchCount = returnBatchItem.getActualProductCount() == null ? 0L : returnBatchItem.getActualProductCount();
                returnBatchItem.setActualProductCount(batch.getActualTotalCount() + batchCount);
                returnBatchItem.setBatchCode(batch.getBatchCode());
                returnBatchItem.setBeOverdueDate(batch.getBeOverdueDate());
                returnBatchItem.setProductDate(batch.getProductDate());
                returnBatchItem.setBatchRemark(batch.getBatchRemark());
                returnBatchItem.setBatchInfoCode(batch.getBatchInfoCode());
                returnBatchItem.setSupplierCode(inbound.getSupplierCode());
                returnBatchItem.setSupplierName(inbound.getSupplierName());
                Integer i = returnOrderInfoInspectionItemMapper.update(returnBatchItem);
                LOGGER.info("更新退货单批次：{}", i);

                // 更新销售单对应的批次退货数量
                productBatch = new OrderInfoItemProductBatch();
                productBatch.setLineCode(batch.getLineCode().longValue());
                productBatch.setSkuCode(batch.getSkuCode());
                productBatch.setReturnTotalCount(batch.getActualTotalCount() + batchCount);
                productBatch.setOrderCode(returnOrderInfo.getOrderCode());
                orderInfoItemProductBatchMapper.updateByReturnBatchCount(productBatch);
                LOGGER.info("更新退货单对应的销售单的实际退货数量 ：{}", i);
            }
        }
        if (CollectionUtils.isNotEmptyCollection(batchList)) {
            Integer count = returnOrderInfoInspectionItemMapper.insertBatch(batchList);
            LOGGER.info("添加退货单批次：{}", count);
        }

        Integer returnInfo = returnOrderInfoMapper.update(returnOrder);
        log.info("更新退货单主信息：{}", returnInfo);

        // 回传运营中台信息
        if(isComplete <= 0){
            changeParameter(returnOrder.getReturnOrderCode());
        }
        return HttpResponse.success();
    }

    private List<InboundReqSave> getInboundReqSave(String returnOrderCode) {
        LOGGER.info("根据运营中台退货单，开始生成耘链入库单：{}", returnOrderCode);
        List<InboundReqSave> inbounds = Lists.newArrayList();
        // 查询退货单的信息
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(returnOrderCode);
        InboundReqSave inbound = BeanCopyUtils.copy(returnOrderInfo, InboundReqSave.class);
        inbound.setCompanyCode(Global.COMPANY_09);
        inbound.setCompanyName(Global.COMPANY_09_NAME);
        inbound.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
        inbound.setInboundTypeCode(InboundTypeEnum.ORDER.getCode());
        inbound.setInboundTypeName(InboundTypeEnum.ORDER.getName());
        inbound.setSourceOderCode(returnOrderInfo.getReturnOrderCode());
        inbound.setLogisticsCenterCode(returnOrderInfo.getTransportCenterCode());
        inbound.setLogisticsCenterName(returnOrderInfo.getTransportCenterName());
        inbound.setCountyCode(returnOrderInfo.getDistrictCode());
        inbound.setCountyName(returnOrderInfo.getDistrictName());
        inbound.setCreateBy(getUser().getPersonName());
        inbound.setUpdateBy(getUser().getPersonName());

        // 查询应有的库房信息
        List<ReturnOrderInfoInspectionItem> warehouses = returnOrderInfoInspectionItemMapper.returnOrderByWarehouse(returnOrderCode);
        if (CollectionUtils.isEmptyCollection(warehouses)) {
            LOGGER.info("退货单查询商品库房的信息为空：{}", JsonUtil.toJson(warehouses));
            throw new GroundRuntimeException(String.format("退货单查询商品库房的信息为空"));
        }
        for (ReturnOrderInfoInspectionItem warehouse : warehouses) {
            inbound.setWarehouseCode(warehouse.getWarehouseCode());
            inbound.setWarehouseName(warehouse.getWarehouseName());

            // 查询退货单批次信息
            List<ReturnOrderInfoInspectionItem> items =
                    returnOrderInfoInspectionItemMapper.returnOrderBatchListByWarehouse(returnOrderCode, warehouse.getWarehouseCode());

            Map<String, ReturnOrderInfoItem> map = new HashMap<>();
            Map<String, Long> skuMap = new HashMap<>();
            String key;

            if (CollectionUtils.isNotEmptyCollection(items)) {
                InboundBatch inboundBatch;
                List<InboundBatch> batchList = Lists.newArrayList();

                // 查询对应库房商品信息
                for (ReturnOrderInfoInspectionItem item : items) {
                    key = String.format("%s,%s,%s", item.getSkuCode(), item.getLineCode(), returnOrderCode);
                    if (map.get(key) == null) {
                        map.put(key, returnOrderInfoItemMapper.returnOrderOne(returnOrderCode, item.getSkuCode(), item.getLineCode()));
                    }

                    Long count = item.getProductCount() == null ? 0L : item.getProductCount();
                    if(skuMap.get(key) == null){
                       skuMap.put(key, count);
                    }else {
                        skuMap.put(key, count + skuMap.get(key));
                    }
                }

                for (ReturnOrderInfoInspectionItem item : items) {
                    if(StringUtils.isBlank(item.getBatchCode())){
                        continue;
                    }
                    inboundBatch = BeanCopyUtils.copy(item, InboundBatch.class);
                    inboundBatch.setInboundOderCode(inbound.getInboundOderCode());
                    inboundBatch.setTotalCount(item.getProductCount());
                    inboundBatch.setCreateById(returnOrderInfo.getUpdateById());
                    inboundBatch.setCreateByName(returnOrderInfo.getUpdateByName());
                    inboundBatch.setUpdateById(returnOrderInfo.getUpdateById());
                    inboundBatch.setUpdateByName(returnOrderInfo.getUpdateByName());
                    inboundBatch.setLineCode(item.getLineCode().intValue());
                    batchList.add(inboundBatch);
                }
                inbound.setInboundBatchList(batchList);
            }

            List<ReturnOrderInfoItem> itemList = map.values().stream().collect(Collectors.toList());
            List<InboundProductReqVo> list = Lists.newArrayList();
            InboundProductReqVo inboundProductReqVo;
            BigDecimal preAmount = BigDecimal.ZERO, preTaAmount = BigDecimal.ZERO;
            Long productCount = 0L;
            // 查询退货单商品信息
            for (ReturnOrderInfoItem detail : itemList) {
                key = String.format("%s,%s,%s", detail.getSkuCode(), detail.getProductLineNum(), returnOrderCode);
                Long aLong = skuMap.get(key);

                inboundProductReqVo = BeanCopyUtils.copy(detail, InboundProductReqVo.class);
                inboundProductReqVo.setNorms(detail.getSpec());
                inboundProductReqVo.setInboundNorms(detail.getSpec());
                inboundProductReqVo.setInboundBaseUnit(detail.getZeroDisassemblyCoefficient() == null ? "1" : detail.getZeroDisassemblyCoefficient().toString());
                inboundProductReqVo.setPreInboundNum(aLong);
                inboundProductReqVo.setPreInboundMainNum(aLong);
                inboundProductReqVo.setPreTaxPurchaseAmount(detail.getPrice());
                inboundProductReqVo.setPreTaxAmount(detail.getPrice().multiply(BigDecimal.valueOf(aLong)).setScale(4, BigDecimal.ROUND_HALF_UP));
                inboundProductReqVo.setLinenum(detail.getProductLineNum());
                inboundProductReqVo.setCreateBy(getUser().getPersonName());
                inboundProductReqVo.setUpdateBy(getUser().getPersonName());
                list.add(inboundProductReqVo);
                // 计算预计无税金额、税额
                if (detail.getInsertType() == 1) {
                    BigDecimal tax = BigDecimal.ZERO;
                    if (detail.getTax() == null) {
                        ProductSkuCheckout info = productSkuCheckoutDao.getInfo(detail.getSkuCode());
                        if (info != null) {
                            tax = info.getOutputTaxRate() == null ? BigDecimal.ZERO : info.getOutputTaxRate();
                        }
                    }
                    BigDecimal noTax = Calculate.computeNoTaxPrice(inboundProductReqVo.getPreTaxAmount(), tax);
                    preAmount = preAmount.add(noTax);
                    productCount += aLong;
                    preTaAmount = preTaAmount.add(inboundProductReqVo.getPreTaxAmount());
                }
            }
            inbound.setList(list);
            inbound.setPreInboundNum(productCount);
            inbound.setPreMainUnitNum(productCount);
            inbound.setPreTaxAmount(preTaAmount);
            inbound.setPreAmount(preAmount);
            inbound.setPreTax(inbound.getPreTaxAmount().subtract(preAmount));
            LOGGER.info("根据退货单转换生成入库单参数：{}", JsonUtil.toJson(inbound));
            inboundService.saveInbound(inbound);
            inbounds.add(inbound);
        }
        LOGGER.info("退货单根据库房生成多个入库单：{}", JsonUtil.toJson(inbounds));
        return inbounds;
    }

}
