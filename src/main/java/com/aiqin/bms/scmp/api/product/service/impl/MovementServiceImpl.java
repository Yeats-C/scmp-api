package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.OutboundDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.MovementWmsProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.MovementWmsReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.movement.*;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductBatchResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.AllocationProductMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.BatchWmsInfo;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.impl.OrderCallbackServiceImpl;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Classname: MovementServiceImplProduct
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
@Service
@WorkFlowAnnotation(WorkFlow.MOVEMENT_ODER)
public class MovementServiceImpl extends BaseServiceImpl implements MovementService, WorkFlowHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCallbackServiceImpl.class);
    /**
     * 宁波熙耘
     */
    private final static String COMPANY_CODE = "09";
    private final static String COMPANY_NAME = "宁波熙耘科技有限公司";
    private static List<String> productTypeList = Arrays.asList("商品", "赠品", "实物返");

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private AllocationMapper allocationMapper;

    @Autowired
    private AllocationProductMapper allocationProductMapper;

    @Autowired
    private AllocationProductBatchMapper allocationProductBatchMapper;

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private GoodsRejectService goodsRejectService;
    @Autowired
    private OutboundService outboundService;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private StockService stockService;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private UrlConfig urlConfig;

    /**
     * 移库列表搜索
     * @param vo 列表搜索实体
     * @return  列表返回实体
     */
    @Override
    public BasePage<QueryMovementResVo> getList(QueryMovementReqVo vo) {
        vo.setAllocationType(AllocationTypeEnum.MOVE.getType());
        AuthToken authToken = getUser();
        vo.setCompanyCode(authToken.getCompanyCode());
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<QueryMovementResVo> list = allocationMapper.getMoveList(vo);
        BasePage<QueryMovementResVo> basePage = PageUtil.getPageList(vo.getPageNo(),list);
        return basePage;
    }


    /**
     * 新增移库单转化实体
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(MovementReqVo vo) {
        AllocationReqVo reqVo = new AllocationReqVo();
        BeanCopyUtils.copy(vo,reqVo);
        reqVo.setCallOutLogisticsCenterCode(vo.getLogisticsCenterCode());
        reqVo.setCallOutLogisticsCenterName(vo.getLogisticsCenterName());
        reqVo.setCallInLogisticsCenterCode(vo.getLogisticsCenterCode());
        reqVo.setCallInLogisticsCenterName(vo.getLogisticsCenterName());
        reqVo.setAllocationType(AllocationTypeEnum.MOVE.getType());
        reqVo.setAllocationTypeName(AllocationTypeEnum.MOVE.getTypeName());
        return allocationService.save(reqVo);
    }

    /**
     * 查询移库单详情
     *
     * @param id
     * @return
     */
    @Override
    public MovementResVo view(Long id) {
        MovementResVo movementResVo = allocationMapper.getMoveDetailById(id);
        if(null == movementResVo){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        movementResVo.setSkuList(allocationProductMapper.selectByAllocationCode(movementResVo.getMovementCode()));
        movementResVo.setBatchSkuList(allocationProductBatchMapper.selectByAllocationCode(movementResVo.getMovementCode()));
        // 获取日志
        if (null != movementResVo) {
            //获取操作日志
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setPageNo(1);
            operationLogVo.setPageSize(100);
            operationLogVo.setObjectType(ObjectTypeCode.ALLOCATION.getStatus());
            operationLogVo.setObjectId(movementResVo.getMovementCode());
            BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo,62);
            List<LogData> logDataList = new ArrayList<>();
            if (null != pageList.getDataList() && pageList.getDataList().size() > 0){
                logDataList = pageList.getDataList();
            }
            movementResVo.setLogDataList(logDataList);
        }
        return  movementResVo;
    }

    /**
     * 撤销移库单
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int revocation(Long id) {
        return allocationService.revocation(id);
    }

    @Override
    public HttpResponse movementWmsEcho(MovementWmsReq request) {
        try {
            //移库
            byte type = AllocationTypeEnum.MOVE.getType();
            String typeName = AllocationTypeEnum.MOVE.getTypeName();
            String inboundOderCode = "";
            String outboundOderCode = "";
            Integer boundRecordType  = (int) OutboundTypeEnum.MOVEMENT.getCode();
            OutboundTypeEnum outboundTypeEnum  = OutboundTypeEnum.MOVEMENT;
            InboundTypeEnum inboundTypeEnum = InboundTypeEnum.MOVEMENT;
            //查看单据是否重复
            Allocation response = allocationMapper.selectByCode(request.getMovementCode());
            if (response != null) {
                LOGGER.error("移库单据已存在:{}", request.getMovementCode());
                return HttpResponse.failure(ResultCode.ORDER_INFO_IS_HAVE);
            }
            List<String> skuList = request.getDetailList().stream().map(MovementProductWmsReq::getSkuCode).collect(Collectors.toList());
            List<OrderProductSkuResponse> productSkuList = productSkuDao.selectStockSkuInfoList(skuList);
            Map<String, OrderProductSkuResponse> productSkuMap = productSkuList.stream().collect(Collectors.toMap(OrderProductSkuResponse::getSkuCode, Function.identity()));
            //  添加单据
            Allocation addAllocation = addAllocation(request, productSkuMap);
            //生成出库单
            OutboundReqVo convert = handleTransferOutbound(addAllocation, productSkuMap, outboundTypeEnum);
            //调拨才有出库 出库单号
            outboundService.save(convert);
            // 完成直接减库存。
            ChangeStockRequest changeStockRequest = new ChangeStockRequest();
            changeStockRequest.setOperationType(4);
            handleProfitLossStockData(addAllocation,changeStockRequest);
            HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                LOGGER.error("wms回调:移库减库存异常");
                throw new GroundRuntimeException("wms回:调减库存异常");
            }
            //生成入库单
            InboundReqSave inboundReqSave = handleTransferInbound(addAllocation, productSkuMap, inboundTypeEnum);
            inboundService.saveInbound2(inboundReqSave);

            // 完成直接加库存。
            ChangeStockRequest stockRequest = new ChangeStockRequest();
            stockRequest.setOperationType(6);
            handleProfitLossStockData(addAllocation,stockRequest);
            HttpResponse stockResponse = stockService.stockAndBatchChange(stockRequest);
            if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
                LOGGER.error("wms回调:移库加库存异常");
                throw new GroundRuntimeException("wms回调:加库存异常");
            }

            //生成调拨单
            allocationInsert(addAllocation, type, typeName);
            return HttpResponse.success();
        } catch (GroundRuntimeException e) {
            LOGGER.error("订单回调异常:{}", e);
            throw new GroundRuntimeException("订单回调异常");
        }
    }

    @Override
    public HttpResponse movementWmsOutEcho(MovementWmsOutReq request) {
        if(request.getFlag() == null){
            return HttpResponse.failure(null,ResultCode.NOT_HAVE_PARAM);
        }
        LOGGER.info("wms回传成功，根据出库单信息，变更对应移库单的实际值：", JSON.toJSON(request));
        Allocation allocation1 = allocationMapper.selectByCode(request.getMovementCode());
        OutboundTypeEnum outboundTypeEnum  = OutboundTypeEnum.MOVEMENT;
        InboundTypeEnum inboundTypeEnum = InboundTypeEnum.MOVEMENT;
        List<String> skuList = request.getDetailList().stream().map(MovementProductWmsReq::getSkuCode).collect(Collectors.toList());
        List<OrderProductSkuResponse> productSkuList = productSkuDao.selectStockSkuInfoList(skuList);
        Map<String, OrderProductSkuResponse> productSkuMap = productSkuList.stream().collect(Collectors.toMap(OrderProductSkuResponse::getSkuCode, Function.identity()));
        // 标识 0出库单 1 入库单 2出入库单一起
        if(request.getFlag() == 0){
            // 状态0 要更新调拨单 出库单 出解锁库存 调用wms入库
            //设置移库待出库状态
            outbound(allocation1);
            // 出库单
            //生成出库单
            OutboundReqVo convert = handleTransferOutbound(allocation1, productSkuMap, outboundTypeEnum);
            //调拨才有出库 出库单号
            outboundService.save(convert);
            // 出解锁库存

            // 调用wms入库
            AllocationDTO allocation  = allocationMapper.selectByFormNO1(allocation1.getFormNo());
            List<AllocationProductResVo> aProductLists = allocationProductMapper.selectByAllocationCode(allocation1.getAllocationCode());
            List<AllocationProductBatchResVo> aProductBatchLists = allocationProductBatchMapper.selectByAllocationCode(allocation1.getAllocationCode());
            allocationService.movementWms(allocation, allocation1, aProductLists, aProductBatchLists);
        }else if (request.getFlag() == 1){
            // 状态1 要更新调拨单 入库单  入直接加库存
            // 设置移库完成状态
            finished(allocation1);
            // 入库单
            //生成入库单
            InboundReqSave inboundReqSave = handleTransferInbound(allocation1, productSkuMap, inboundTypeEnum);
            inboundService.saveInbound2(inboundReqSave);
            // 完成直接加库存。
            ChangeStockRequest stockRequest = new ChangeStockRequest();
            stockRequest.setOperationType(6);
            handleProfitLossStockData(allocation1,stockRequest);
            HttpResponse stockResponse = stockService.stockAndBatchChange(stockRequest);
            if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
                LOGGER.error("wms回调:移库加库存异常");
                throw new GroundRuntimeException("wms回调:加库存异常");
            }
        }else {
            // 状态1 要更新调拨单 出入库单 解锁库存  加库存
            finished(allocation1);

            // 出入库单
            OutboundReqVo convert = handleTransferOutbound(allocation1, productSkuMap, outboundTypeEnum);
            outboundService.save(convert);
            InboundReqSave inboundReqSave = handleTransferInbound(allocation1, productSkuMap, inboundTypeEnum);
            inboundService.saveInbound2(inboundReqSave);
            // 解锁库存

            // 加库存
            // 完成直接加库存。
            ChangeStockRequest stockRequest = new ChangeStockRequest();
            stockRequest.setOperationType(6);
            handleProfitLossStockData(allocation1,stockRequest);
            HttpResponse stockResponse = stockService.stockAndBatchChange(stockRequest);
            if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
                LOGGER.error("wms回调:移库加库存异常");
                throw new GroundRuntimeException("wms回调:加库存异常");
            }
        }
        // 更新移库单状态
        int k = allocationMapper.updateByPrimaryKeySelective(allocation1);
        return HttpResponse.success();
    }

    private void outbound(Allocation allocation) {
        allocation.setOutStockTime(Calendar.getInstance().getTime());
        allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_OUTBOUND.getStatus());
        allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_OUTBOUND.getName());
    }

    private void finished(Allocation allocation) {
        allocation.setInStockTime(Calendar.getInstance().getTime());
        allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
        allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
    }

    private void allocationInsert(Allocation allocation, byte type, String typeName) {
        // 获取编码
        String content = HandleTypeCoce.ADD_ALLOCATION.getName();
        //保存日志
        supplierCommonService.getInstance(allocation.getAllocationCode() + "", HandleTypeCoce.ADD.getStatus(), ObjectTypeCode.ALLOCATION.getStatus(), content, null, HandleTypeCoce.ADD.getName(),"wms同步");
        //设置状态(已完成)
        allocation.setAllocationStatusCode(AllocationEnum.ALLOCATION_TYPE_FINISHED.getStatus());
        allocation.setAllocationStatusName(AllocationEnum.ALLOCATION_TYPE_FINISHED.getName());
        allocation.setAllocationType(type);
        allocation.setAllocationTypeName(typeName);
        allocation.setPatternType(2);
        allocation.setPatternName("wms方发起");
        allocationMapper.insertSelective(allocation);
        //添加详情
        allocationProductMapper.saveList(allocation.getDetailList());
        //添加供应商和商品关系
        allocationProductBatchMapper.saveList(allocation.getDetailBatchList());
    }

    private void handleProfitLossStockData(Allocation addAllocation, ChangeStockRequest changeStockRequest) {
        List<StockInfoRequest> list = Lists.newArrayList();
        StockInfoRequest stockInfoRequest;
        List<StockBatchInfoRequest> batchList = Lists.newArrayList();
        StockBatchInfoRequest stockBatchInfoRequest;
        for (AllocationProduct itemReqVo : addAllocation.getDetailList()) {
            stockInfoRequest = new StockInfoRequest();
            stockInfoRequest.setCompanyCode(COMPANY_CODE);
            stockInfoRequest.setCompanyName(COMPANY_NAME);
           if(changeStockRequest.getOperationType() == 4){
               stockInfoRequest.setTransportCenterCode(addAllocation.getCallOutLogisticsCenterCode());
               stockInfoRequest.setTransportCenterName(addAllocation.getCallOutLogisticsCenterName());
               stockInfoRequest.setWarehouseCode(addAllocation.getCallOutWarehouseCode());
               stockInfoRequest.setWarehouseName(addAllocation.getCallOutWarehouseName());
           }else {
               stockInfoRequest.setTransportCenterCode(addAllocation.getCallInLogisticsCenterCode());
               stockInfoRequest.setTransportCenterName(addAllocation.getCallInLogisticsCenterName());
               stockInfoRequest.setWarehouseCode(addAllocation.getCallInWarehouseCode());
               stockInfoRequest.setWarehouseName(addAllocation.getCallInWarehouseName());
           }
            stockInfoRequest.setChangeCount(Math.abs(itemReqVo.getQuantity()));
            stockInfoRequest.setSkuCode(itemReqVo.getSkuCode());
            stockInfoRequest.setSkuName(itemReqVo.getSkuName());
            stockInfoRequest.setDocumentType(11);
            stockInfoRequest.setDocumentCode(itemReqVo.getAllocationCode());
            stockInfoRequest.setSourceDocumentType(11);
            stockInfoRequest.setSourceDocumentCode(itemReqVo.getAllocationCode());
            stockInfoRequest.setOperatorName(itemReqVo.getCreateBy());
            list.add(stockInfoRequest);

            if(addAllocation.getDetailBatchList() != null){
                for (AllocationProductBatch allocationProductBatch : addAllocation.getDetailBatchList()) {
                    if(itemReqVo.getSkuCode().equals(allocationProductBatch.getSkuCode())){
                        stockBatchInfoRequest = new StockBatchInfoRequest();
                        stockBatchInfoRequest.setCompanyCode(COMPANY_CODE);
                        stockBatchInfoRequest.setCompanyName(COMPANY_NAME);
                        stockBatchInfoRequest.setSkuCode(allocationProductBatch.getSkuCode());
                        stockBatchInfoRequest.setSkuName(allocationProductBatch.getSkuName());
                        if(changeStockRequest.getOperationType() == 4){
                            stockBatchInfoRequest.setBatchCode(allocationProductBatch.getCallOutBatchNumber());
                            stockBatchInfoRequest.setBatchInfoCode(allocationProductBatch.getCallOutBatchInfoCode());
                        }else {
                            stockBatchInfoRequest.setBatchCode(allocationProductBatch.getCallInBatchNumber());
                            stockBatchInfoRequest.setBatchInfoCode(allocationProductBatch.getCallInBatchInfoCode());
                        }
                        stockBatchInfoRequest.setProductDate(allocationProductBatch.getProductDate());
                        stockBatchInfoRequest.setBeOverdueDate(allocationProductBatch.getBeOverdueDate());
                        stockBatchInfoRequest.setBatchRemark(allocationProductBatch.getBatchNumberRemark());
                        stockBatchInfoRequest.setSupplierCode(allocationProductBatch.getSupplierCode());
                        stockBatchInfoRequest.setDocumentType(11);
                        stockBatchInfoRequest.setDocumentCode(allocationProductBatch.getAllocationCode());
                        stockBatchInfoRequest.setSourceDocumentType(11);
                        stockBatchInfoRequest.setSourceDocumentCode(addAllocation.getAllocationCode());
                        stockBatchInfoRequest.setChangeCount(allocationProductBatch.getQuantity());
                        stockBatchInfoRequest.setOperatorName(allocationProductBatch.getCreateBy());
                        batchList.add(stockBatchInfoRequest);
                    }
                }
            }
        }
        changeStockRequest.setStockList(list);
        changeStockRequest.setStockBatchList(batchList);

    }

    public InboundReqSave handleTransferInbound(Allocation allocation, Map<String, OrderProductSkuResponse> productSkuMap, InboundTypeEnum inboundTypeEnum) {
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.IN_BOUND_CODE);
        // 更新数据库编码尺度
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),  encodingRule.getId());

        InboundReqSave inbound = new InboundReqSave();
        OrderProductSkuResponse orderProductSkuResponse;
        InboundProductReqVo product;
        List<InboundProductReqVo> products = Lists.newArrayList();
        for (AllocationProduct allocationProduct : allocation.getDetailList()) {
            product = new InboundProductReqVo();
            product.setInboundOderCode(String.valueOf(encodingRule.getNumberingValue()));
            product.setPreInboundMainNum(allocationProduct.getQuantity());
            product.setPreInboundNum(allocationProduct.getQuantity());
            product.setPraInboundMainNum(allocationProduct.getQuantity());
            product.setPraInboundNum(allocationProduct.getQuantity());
            orderProductSkuResponse = productSkuMap.get(allocationProduct.getSkuCode());
            product.setCreateBy(allocation.getCreateBy());
            product.setCreateTime(allocation.getCreateTime());
            product.setSkuCode(allocationProduct.getSkuCode());
            if (orderProductSkuResponse != null) {
                //基商品含量固定1
                product.setInboundBaseContent(orderProductSkuResponse.getBaseProductContent());
                product.setInboundBaseUnit(String.valueOf(orderProductSkuResponse.getZeroDisassemblyCoefficient()));
                product.setNorms(orderProductSkuResponse.getSpec());
                product.setSkuName(orderProductSkuResponse.getProductName());
                product.setPictureUrl(orderProductSkuResponse.getPictureUrl());
                product.setColorCode(orderProductSkuResponse.getColorCode());
                product.setColorName(orderProductSkuResponse.getColorName());
                product.setModel(orderProductSkuResponse.getModel());
                product.setUnitCode(orderProductSkuResponse.getUnitCode());
                product.setUnitName(orderProductSkuResponse.getUnitName());
                product.setInboundNorms(orderProductSkuResponse.getSpec());
                product.setTax(orderProductSkuResponse.getTax());
                product.setModel(orderProductSkuResponse.getModel());
            } else {
                throw new GroundRuntimeException(String.format("未查询到商品信息,skuCode:%s", allocationProduct.getSkuCode()));
            }
            products.add(product);
        }

        List<InboundBatchReqVo> batchList = Lists.newArrayList();
        InboundBatchReqVo inboundBatchReqVo;
        for (AllocationProductBatch batch : allocation.getDetailBatchList()) {
            inboundBatchReqVo = new InboundBatchReqVo();
            inboundBatchReqVo.setInboundOderCode(String.valueOf(encodingRule.getNumberingValue()));
            inboundBatchReqVo.setSkuName(batch.getSkuName());
            inboundBatchReqVo.setSkuCode(batch.getSkuCode());
            inboundBatchReqVo.setBatchCode(batch.getCallInBatchNumber());
            inboundBatchReqVo.setBatchInfoCode(batch.getCallInBatchInfoCode());
            inboundBatchReqVo.setBatchRemark(batch.getBatchNumberRemark());
            inboundBatchReqVo.setProductDate(batch.getProductDate());
            inboundBatchReqVo.setBeOverdueDate(batch.getBeOverdueDate());
            inboundBatchReqVo.setSupplierCode(batch.getSupplierCode());
            inboundBatchReqVo.setSupplierName(batch.getSupplierName());
            inboundBatchReqVo.setTotalCount(batch.getQuantity());
            inboundBatchReqVo.setActualTotalCount(batch.getQuantity());
            inboundBatchReqVo.setLineCode(batch.getLineNum());
            inboundBatchReqVo.setCreateBy(allocation.getCreateBy());
            inboundBatchReqVo.setUpdateBy(allocation.getUpdateBy());
            batchList.add(inboundBatchReqVo);
        }
        //入库编码
        inbound.setInboundOderCode(String.valueOf(encodingRule.getNumberingValue()));
        //实际入库数量
        inbound.setPraInboundNum(allocation.getQuantity());
        //实际入库主数量
        inbound.setPraMainUnitNum(allocation.getQuantity());
        //预计入库数量
        inbound.setPreInboundNum(allocation.getQuantity());
        //预计入库主数量
        inbound.setPreMainUnitNum(allocation.getQuantity());
        //入库类型
        inbound.setInboundTypeCode(inboundTypeEnum.getCode());
        inbound.setInboundTypeName(inboundTypeEnum.getName());
        //入库状态
        inbound.setInboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //公司编码
        inbound.setCompanyCode(allocation.getCompanyCode());
        inbound.setCompanyName(allocation.getCompanyName());
        inbound.setInboundTime(allocation.getUpdateTime());
        inbound.setShipper(allocation.getUpdateBy());
        //来源单据号
        inbound.setSourceOderCode(allocation.getAllocationCode());
        //物流中心编码
        inbound.setLogisticsCenterCode(allocation.getCallInLogisticsCenterCode());
        inbound.setLogisticsCenterName(allocation.getCallInLogisticsCenterName());
        //预计到货时间
        inbound.setPreArrivalTime(allocation.getUpdateTime());
        inbound.setCreateTime(allocation.getCreateTime());
        inbound.setWarehouseCode(allocation.getCallInWarehouseCode());
        inbound.setWarehouseName(allocation.getCallInWarehouseName());
        //创建人
        inbound.setCreateBy(allocation.getCreateBy());
        inbound.setUpdateBy(allocation.getUpdateBy());
        inbound.setUpdateTime(allocation.getUpdateTime());

        inbound.setList(products);
        inbound.setInboundBatchReqVos(batchList);
        return inbound;
    }


    public OutboundReqVo handleTransferOutbound(Allocation allocation, Map<String, OrderProductSkuResponse> productSkuMap, OutboundTypeEnum outboundTypeEnum) {
        OutboundReqVo stockReqVO = new OutboundReqVo();
        BeanUtils.copyProperties(allocation, stockReqVO);

        List<OutboundProductReqVo> parts = Lists.newArrayList();
        OutboundProductReqVo outboundProduct;
        OrderProductSkuResponse orderProductSkuResponse;
        for (AllocationProduct item : allocation.getDetailList()) {
            outboundProduct = new OutboundProductReqVo();
            //sku
            outboundProduct.setSkuCode(item.getSkuCode());
            outboundProduct.setSkuName(item.getSkuName());
            orderProductSkuResponse = productSkuMap.get(item.getSkuCode());
            if (orderProductSkuResponse != null) {
                outboundProduct.setPictureUrl(orderProductSkuResponse.getPictureUrl());
                outboundProduct.setNorms(orderProductSkuResponse.getSpec());
                outboundProduct.setUnitCode(orderProductSkuResponse.getUnitCode());
                outboundProduct.setUnitName(orderProductSkuResponse.getUnitName());
                outboundProduct.setOutboundNorms(orderProductSkuResponse.getSpec());
                outboundProduct.setColorCode(orderProductSkuResponse.getColorCode());
                outboundProduct.setColorName(orderProductSkuResponse.getColorName());
                outboundProduct.setTax(orderProductSkuResponse.getTax());
                outboundProduct.setModel(orderProductSkuResponse.getModel());
                //预计出库数量
                outboundProduct.setPreOutboundNum(item.getQuantity());
                //预计出库主数量
                outboundProduct.setPreOutboundMainNum(item.getQuantity());
                //实际出库数量
                outboundProduct.setPraOutboundNum(item.getQuantity());
                //实际出库主数量
                outboundProduct.setPraOutboundMainNum(item.getQuantity());
                outboundProduct.setCreateBy(allocation.getCreateBy());
                outboundProduct.setUpdateBy(allocation.getUpdateBy());
                outboundProduct.setCreateTime(allocation.getCreateTime());
                outboundProduct.setUpdateTime(allocation.getUpdateTime());
                //行号
                outboundProduct.setLinenum(item.getLineNum());
                //基商品含量固定1
                outboundProduct.setOutboundBaseContent("1");
                outboundProduct.setOutboundBaseUnit("1");
            } else {
                throw new GroundRuntimeException(String.format("未查询到商品信息,skuCode:%s", item.getSkuCode()));
            }
            //不计算不含税单价
            parts.add(outboundProduct);
        }
        List<OutboundBatch> batchList = Lists.newArrayList();
        OutboundBatch outboundBatch;
        for (AllocationProductBatch batch : allocation.getDetailBatchList()) {
            outboundBatch = new OutboundBatch();
            outboundBatch.setSkuName(batch.getSkuName());
            outboundBatch.setSkuCode(batch.getSkuCode());
            outboundBatch.setSupplierCode(batch.getSupplierCode());
            outboundBatch.setSupplierName(batch.getSupplierName());
            outboundBatch.setBatchCode(batch.getCallOutBatchNumber());
            outboundBatch.setBatchInfoCode(batch.getCallOutBatchInfoCode());
            outboundBatch.setProductDate(batch.getProductDate());
            outboundBatch.setTotalCount(batch.getQuantity());
            outboundBatch.setActualTotalCount(batch.getQuantity());
            outboundBatch.setLineCode(batch.getLineNum());
            outboundBatch.setCreateById(allocation.getCreateById());
            outboundBatch.setCreateByName(allocation.getCreateByName());
            outboundBatch.setCreateTime(allocation.getCreateTime());
            outboundBatch.setUpdateById(allocation.getUpdateById());
            outboundBatch.setUpdateByName(allocation.getUpdateByName());
            outboundBatch.setUpdateTime(allocation.getUpdateTime());
            batchList.add(outboundBatch);
        }
        stockReqVO.setSourceOderCode(allocation.getAllocationCode());
        //配送中心
        stockReqVO.setLogisticsCenterCode(allocation.getCallInLogisticsCenterCode());
        stockReqVO.setLogisticsCenterName(allocation.getCallInLogisticsCenterName());
        //预计
        stockReqVO.setPreOutboundNum(allocation.getQuantity());
        stockReqVO.setPreMainUnitNum(allocation.getQuantity());
        //实际
        stockReqVO.setPraOutboundNum(allocation.getQuantity());
        stockReqVO.setPraMainUnitNum(allocation.getQuantity());
        stockReqVO.setConsigneeNumber(allocation.getUpdateBy());
        stockReqVO.setCreateBy(allocation.getCreateBy());
        stockReqVO.setUpdateBy(allocation.getUpdateBy());
        stockReqVO.setCreateTime(allocation.getCreateTime());
        stockReqVO.setUpdateTime(allocation.getUpdateTime());
        //状态
        stockReqVO.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        stockReqVO.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        //类型
        stockReqVO.setOutboundTypeCode(outboundTypeEnum.getCode());
        stockReqVO.setOutboundTypeName(outboundTypeEnum.getName());
        //出库时间
        stockReqVO.setOutboundTime(allocation.getUpdateTime());
        stockReqVO.setPreArrivalTime(allocation.getUpdateTime());

        stockReqVO.setList(parts);
        stockReqVO.setOutboundBatches(batchList);
        return stockReqVO;
    }

    private Allocation addAllocation(MovementWmsReq request, Map<String, OrderProductSkuResponse> productSkuMap) {
        OrderProductSkuResponse orderProductSku;
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(request, allocation);
        allocation.setAllocationCode(request.getMovementCode());
        allocation.setCompanyCode(COMPANY_CODE);
        allocation.setCompanyName(COMPANY_NAME);
        allocation.setCreateBy(request.getCreateByName());
//        allocation.setCreateTime(new DateTime(new Long(request.getCreateTime())).toDate());
        allocation.setCreateTime(request.getCreateTime());
        allocation.setUpdateBy(request.getUpdateByName());
        allocation.setPrincipal(request.getUpdateByName());
//        allocation.setUpdateTime(new DateTime(new Long(request.getReceiptTime())).toDate());
        allocation.setUpdateTime(request.getReceiptTime());
        List<AllocationProduct> detailList = new ArrayList<>();
        for (MovementProductWmsReq movementProductWmsReq : request.getDetailList()) {
            AllocationProduct aProduct = new AllocationProduct();
            aProduct.setAllocationCode(request.getMovementCode());
            aProduct.setLineNum(movementProductWmsReq.getLineCode());
            aProduct.setQuantity(movementProductWmsReq.getQuantity());
            orderProductSku = productSkuMap.get(movementProductWmsReq.getSkuCode());
            aProduct.setCreateBy(request.getCreateByName());
            aProduct.setCreateTime(request.getCreateTime());
            aProduct.setUpdateTime(request.getReceiptTime());
            aProduct.setUpdateBy(request.getUpdateByName());
            if(orderProductSku != null){
                aProduct.setSkuName(orderProductSku.getProductName());
                aProduct.setPictureUrl(orderProductSku.getPictureUrl());
                aProduct.setSpecification(orderProductSku.getSpec());
                aProduct.setColor(orderProductSku.getColorName());
                aProduct.setModel(orderProductSku.getModel());
                aProduct.setCategory(goodsRejectService.selectCategoryName(orderProductSku.getCategoryCode()));
                aProduct.setUnit(orderProductSku.getUnitName());
                aProduct.setBrand(orderProductSku.getBrandName());
                aProduct.setType(productTypeList.get(orderProductSku.getProductType()));
                aProduct.setTax(orderProductSku.getTax());
            }
            detailList.add(aProduct);
        }
        List<AllocationProductBatch> detailBatchList = new ArrayList<>();
        for (MovementBatchWmsReq batchList : request.getBatchList()) {
            AllocationProductBatch allocationProductBatch = new AllocationProductBatch();
            allocationProductBatch.setAllocationCode(request.getMovementCode());
            allocationProductBatch.setSkuCode(batchList.getSkuCode());
            allocationProductBatch.setSkuName(batchList.getSkuName());
            allocationProductBatch.setCallInBatchNumber(batchList.getCallInBatchCode());
            allocationProductBatch.setCallOutBatchNumber(batchList.getCallOutBatchCode());
            allocationProductBatch.setCallInBatchInfoCode(batchList.getCallInBatchInfoCode());
            allocationProductBatch.setCallOutBatchInfoCode(batchList.getCallOutBatchInfoCode());
            allocationProductBatch.setProductDate(batchList.getProductDate());
            allocationProductBatch.setBeOverdueDate(batchList.getBeOverdueDate());
            allocationProductBatch.setBatchNumberRemark(batchList.getBatchNumberRemark());
            allocationProductBatch.setQuantity(batchList.getQuantity());
            allocationProductBatch.setCallInActualTotalCount(batchList.getQuantity());
            allocationProductBatch.setCallOutActualTotalCount(batchList.getQuantity());
            allocationProductBatch.setLineNum(batchList.getLineCode());
            detailBatchList.add(allocationProductBatch);
        }
        allocation.setDetailList(detailList);
        allocation.setDetailBatchList(detailBatchList);
        return allocation;
    }

    /**
     * 审核回调接口
     *
     * @param vo
     * @return
     * @author zth
     * @date 2019/1/15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo) {
        return allocationService.nativeWorkFlowCallback(vo);
    }
}
