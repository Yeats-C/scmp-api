package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.dto.allocation.AllocationDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationBatchRequest;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.MovementWmsProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.MovementWmsReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductCallBackRequest;
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
import com.aiqin.bms.scmp.api.purchase.domain.request.dl.StockChangeDlRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.BatchWmsInfo;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.impl.OrderCallbackServiceImpl;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
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

import java.math.BigDecimal;
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
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockBatchDao stockBatchDao;
    @Autowired
    private OutboundProductDao outboundProductDao;
    @Autowired
    private ProductCommonService productCommonService;
    @Autowired
    private OutboundDao outboundDao;
    @Autowired
    private OutboundBatchDao outboundBatchDao;
    @Autowired
    private InboundDao inboundDao;
    @Autowired
    private InboundProductDao inboundProductDao;
    @Autowired
    private InboundBatchDao inboundBatchDao;


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
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse movementWmsEcho(MovementWmsReq request) {
        try {
            WarehouseDTO warehouseByInCode = warehouseDao.getWarehouseByCode(request.getCallInWarehouseCode());
            WarehouseDTO warehouseByOutCode = warehouseDao.getWarehouseByCode(request.getCallOutWarehouseCode());
            request.setCallInLogisticsCenterCode(warehouseByInCode.getLogisticsCenterCode());
            request.setCallInLogisticsCenterName(warehouseByInCode.getLogisticsCenterName());
            request.setCallInWarehouseCode(warehouseByInCode.getWarehouseCode());
            request.setCallInWarehouseName(warehouseByInCode.getWarehouseName());
            request.setCallOutLogisticsCenterCode(warehouseByOutCode.getLogisticsCenterCode());
            request.setCallOutLogisticsCenterName(warehouseByOutCode.getLogisticsCenterName());
            request.setCallOutWarehouseCode(warehouseByOutCode.getWarehouseCode());
            request.setCallOutWarehouseName(warehouseByOutCode.getWarehouseName());
            //移库
            byte type = AllocationTypeEnum.MOVE.getType();
            String typeName = AllocationTypeEnum.MOVE.getTypeName();
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
            outboundService.saveOutbound(convert);
            // 完成直接减库存。
            ChangeStockRequest changeStockRequest = new ChangeStockRequest();
            changeStockRequest.setOperationType(4);
            handleProfitLossStockData(addAllocation,changeStockRequest);
            HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                LOGGER.error("wms回调:移库减库存异常");
                throw new GroundRuntimeException("wms回:调减库存异常");
            }

            List<AllocationProductResVo> aProductLists = allocationProductMapper.selectByAllocationCode(addAllocation.getAllocationCode());
            List<AllocationProductBatchResVo> aProductBatchLists = allocationProductBatchMapper.selectByAllocationCode(addAllocation.getAllocationCode());
            // 操作完库存 调用dl库存同步接口
            StockChangeDlRequest stockChangeDlRequest = new StockChangeDlRequest();
            stockChangeDlRequest.setOrderCode(addAllocation.getAllocationCode());
            stockChangeDlRequest.setOrderType(Global.DL_ORDER_TYPE_4);
            stockChangeDlRequest.setOperationType(Global.DL_OPERATION_TYPE_2);
            allocationService.synchrdlStockChange(addAllocation, aProductLists, aProductBatchLists, stockChangeDlRequest);
            LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDlRequest));
            stockService.dlStockChange(stockChangeDlRequest);
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
            // 操作完库存 调用dl库存同步接口
            StockChangeDlRequest stockChangeDl = new StockChangeDlRequest();
            stockChangeDl.setOrderCode(addAllocation.getAllocationCode());
            stockChangeDl.setOrderType(Global.DL_ORDER_TYPE_8);
            stockChangeDl.setOperationType(Global.DL_OPERATION_TYPE_1);
            allocationService.synchrdlStockChange(addAllocation, aProductLists, aProductBatchLists, stockChangeDl);
            LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDl));
            stockService.dlStockChange(stockChangeDl);

            //生成调拨单
            allocationInsert(addAllocation, type, typeName);
            return HttpResponse.success();
        } catch (GroundRuntimeException e) {
            LOGGER.error("订单回调异常:{}", e);
            throw new GroundRuntimeException("订单回调异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse movementWmsOutEcho(MovementWmsOutReq request) {
        if(request.getFlag() == null){
            return HttpResponse.failure(null,ResultCode.NOT_HAVE_PARAM);
        }

        LOGGER.info("wms回传成功，根据出库单信息，变更对应移库单的实际值：", JSON.toJSON(request));
        if (request.getFlag() == 0){
            Allocation allocation1 = allocationMapper.selectByOutOrderCode(request.getOutboundOderCode());
            request.setMovementCode(allocation1.getAllocationCode());
        }else if (request.getFlag() == 1){
            Allocation allocation1 = allocationMapper.selectByOutOrderCode(request.getInboundOderCode());
            request.setMovementCode(allocation1.getAllocationCode());
            request.setOutboundOderCode(allocation1.getOutboundOderCode());
        }else {
            Allocation allocation1 = allocationMapper.selectByOutOrderCode(request.getOutboundOderCode());
            request.setMovementCode(allocation1.getAllocationCode());
        }
        Allocation allocation1 = allocationMapper.selectByCode(request.getMovementCode());
        List<AllocationProduct> detailLists = new ArrayList<>();
        List<AllocationProductBatch> detailBatchList = new ArrayList<>();
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
//            更新出库状态
            updateOut(request, allocation1, detailLists, detailBatchList);

            // 入库单
            //生成入库单
            InboundReqSave inboundReqSave = handleTransferInbound(allocation1, productSkuMap, inboundTypeEnum);
            allocation1.setInboundOderCode(inboundReqSave.getInboundOderCode());
            allocation1.setInboundOderCode(inboundReqSave.getInboundOderCode());
            inboundService.saveInbound2(inboundReqSave);
            // 出解锁库存
            ChangeStockRequest changeStockRequest = new ChangeStockRequest();
            changeStockRequest.setOperationType(2);
            handleProfitLossStockData(allocation1,changeStockRequest);
            HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                LOGGER.error("wms移库回调:移库减并解锁库存异常: 参数{}", changeStockRequest);
                throw new GroundRuntimeException("wms移库回调:减并解锁库存异常");
            }
            AllocationDTO allocation  = allocationMapper.selectByFormNO1(allocation1.getFormNo());
            List<AllocationProductResVo> aProductLists = allocationProductMapper.selectByAllocationCode(allocation1.getAllocationCode());
            List<AllocationProductBatchResVo> aProductBatchLists = allocationProductBatchMapper.selectByAllocationCode(allocation1.getAllocationCode());
            // 调用wms入库
            allocationService.movementWms(allocation, allocation1, aProductLists, aProductBatchLists);
            // 调用完库存锁定调用同步dl库存数据
            StockChangeDlRequest stockChangeDlRequest = new StockChangeDlRequest();
            stockChangeDlRequest.setOrderCode(allocation.getAllocationCode());
            stockChangeDlRequest.setOrderType(Global.DL_ORDER_TYPE_4);
            stockChangeDlRequest.setOperationType(Global.DL_OPERATION_TYPE_2);
            allocationService.synchrdlStockChange(allocation, aProductLists, aProductBatchLists, stockChangeDlRequest);
            LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDlRequest));
            HttpResponse response = stockService.dlStockChange(stockChangeDlRequest);
            if (!response.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用完库存锁定调用同步dl库存数据异常信息:{}", response.getMessage());
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
        }else if (request.getFlag() == 1){
            // 状态1 要更新调拨单 入库单  入直接加库存
            // 设置移库完成状态
            finished(allocation1);
           //更新入库单
            updateIn(request, allocation1, detailLists, detailBatchList);

            // 完成直接加库存。
            ChangeStockRequest stockRequest = new ChangeStockRequest();
            stockRequest.setOperationType(6);
            handleProfitLossStockData(allocation1,stockRequest);
            HttpResponse stockResponse = stockService.stockAndBatchChange(stockRequest);
            if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
                LOGGER.error("wms移库回调:移库加库存异常: 参数{}", stockRequest);
                throw new GroundRuntimeException("wms移库回调:加库存异常");
            }

            AllocationDTO allocation  = allocationMapper.selectByFormNO1(allocation1.getFormNo());
            List<AllocationProductResVo> aProductLists = allocationProductMapper.selectByAllocationCode(allocation1.getAllocationCode());
            List<AllocationProductBatchResVo> aProductBatchLists = allocationProductBatchMapper.selectByAllocationCode(allocation1.getAllocationCode());
            // 调用完库存锁定调用同步dl库存数据
            StockChangeDlRequest stockChangeDlRequest = new StockChangeDlRequest();
            stockChangeDlRequest.setOrderCode(allocation1.getAllocationCode());
            stockChangeDlRequest.setOrderType(Global.DL_ORDER_TYPE_8);
            stockChangeDlRequest.setOperationType(Global.DL_OPERATION_TYPE_1);
            allocationService.synchrdlStockChange(allocation, aProductLists, aProductBatchLists, stockChangeDlRequest);
            LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDlRequest));
            HttpResponse response = stockService.dlStockChange(stockChangeDlRequest);
            if (!response.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用完库存锁定调用同步dl库存数据异常信息:{}", response.getMessage());
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
        }else {
            // 状态1 要更新调拨单 出入库单 解锁库存  加库存
            finished(allocation1);

            // 更新出入库单
//            OutboundReqVo convert = handleTransferOutbound(allocation1, productSkuMap, outboundTypeEnum);
//            outboundService.save(convert);
//            InboundReqSave inboundReqSave = handleTransferInbound(allocation1, productSkuMap, inboundTypeEnum);
//            inboundService.saveInbound2(inboundReqSave);
            updateOut(request, allocation1, detailLists, detailBatchList);
            updateIn(request, allocation1, detailLists, detailBatchList);
            // 解锁库存
            ChangeStockRequest changeStockRequest = new ChangeStockRequest();
            changeStockRequest.setOperationType(2);
            handleProfitLossStockData(allocation1,changeStockRequest);
            HttpResponse httpResponse = stockService.stockAndBatchChange(changeStockRequest);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                LOGGER.error("wms移库回调:移库减并解锁库存异常: 参数{}", changeStockRequest);
                throw new GroundRuntimeException("wms移库回调:减并解锁库存异常");
            }

            AllocationDTO allocation  = allocationMapper.selectByFormNO1(allocation1.getFormNo());
            List<AllocationProductResVo> aProductLists = allocationProductMapper.selectByAllocationCode(allocation1.getAllocationCode());
            List<AllocationProductBatchResVo> aProductBatchLists = allocationProductBatchMapper.selectByAllocationCode(allocation1.getAllocationCode());
            // 调用完库存锁定调用同步dl库存数据
            StockChangeDlRequest stockChangeDlRequest = new StockChangeDlRequest();
            stockChangeDlRequest.setOrderCode(allocation1.getAllocationCode());
            stockChangeDlRequest.setOrderType(Global.DL_ORDER_TYPE_4);
            stockChangeDlRequest.setOperationType(Global.DL_OPERATION_TYPE_2);
            allocationService.synchrdlStockChange(allocation, aProductLists, aProductBatchLists, stockChangeDlRequest);
            LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDlRequest));
            HttpResponse response = stockService.dlStockChange(stockChangeDlRequest);
            if (!response.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用完库存锁定调用同步dl库存数据异常信息:{}", response.getMessage());
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }

            // 加库存
            // 完成直接加库存。
            ChangeStockRequest stockRequest = new ChangeStockRequest();
            stockRequest.setOperationType(6);
            handleProfitLossStockData(allocation1,stockRequest);
            HttpResponse stockResponse = stockService.stockAndBatchChange(stockRequest);
            if (!MsgStatus.SUCCESS.equals(stockResponse.getCode())) {
                LOGGER.error("wms移库回调:移库加库存异常: 参数{}", stockRequest);
                throw new GroundRuntimeException("wms移库回调:加库存异常");
            }

            // 调用完库存锁定调用同步dl库存数据
            StockChangeDlRequest stockChangeDl = new StockChangeDlRequest();
            stockChangeDl.setOrderCode(allocation.getAllocationCode());
            stockChangeDl.setOrderType(Global.DL_ORDER_TYPE_8);
            stockChangeDl.setOperationType(Global.DL_OPERATION_TYPE_1);
            allocationService.synchrdlStockChange(allocation, aProductLists, aProductBatchLists, stockChangeDl);
            LOGGER.info("调用完库存锁定调用同步dl库存参数数据:{}", JsonUtil.toJson(stockChangeDl));
            HttpResponse responses = stockService.dlStockChange(stockChangeDl);
            if (!responses.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用完库存锁定调用同步dl库存数据异常信息:{}", responses.getMessage());
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
        }
        // 更新移库单状态
        int k = allocationMapper.updateByPrimaryKeySelective(allocation1);
        return HttpResponse.success();
    }

    private void updateIn(MovementWmsOutReq request, Allocation allocation1, List<AllocationProduct> detailLists, List<AllocationProductBatch> detailBatchList) {
        Inbound inbound = inboundDao.selectByCode(request.getInboundOderCode());

        String key;
        Map<String, InboundProduct> products = new HashMap<>();
        for (MovementProductWmsReq inboundProduct : request.getDetailList()) {
            key = String.format("%s,%s,%s", inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode());
            if (products.get(key) == null) {
                products.put(key, inboundProductDao.inboundByLineCode(inbound.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLineCode()));
            }
        }

        // 设置入库单默认值
        inbound.setPraInboundNum(0L);
        inbound.setPraMainUnitNum(0L);
        inbound.setPraTaxAmount(BigDecimal.ZERO);
        inbound.setPraTax(BigDecimal.ZERO);
        inbound.setPraAmount(BigDecimal.ZERO);
        inbound.setInboundStatusCode(InOutStatus.RECEIVE_INOUT.getCode());
        inbound.setInboundStatusName(InOutStatus.RECEIVE_INOUT.getName());

        // 更新主表数据 / 出库单商品表
        List<MovementProductWmsReq> detailList = request.getDetailList();
        Long praInboundNum = 0L, praMainUnitNum = 0L;
        BigDecimal praTaxAmount = BigDecimal.ZERO, praAmount = BigDecimal.ZERO;
        for (MovementProductWmsReq detail : detailList) {
            // 移库商品信息
            AllocationProduct allocationProduct = new AllocationProduct();
            allocationProduct.setAllocationCode(allocation1.getAllocationCode());
            allocationProduct.setQuantity(detail.getQuantity());
            allocationProduct.setSkuCode(detail.getSkuCode());
            allocationProduct.setLineNum(detail.getLineCode());
            allocationProductMapper.updateQuantityBySkuCodeAndSourceIn(allocationProduct);

            // 查询对应的出库单商品信息
            key = String.format("%s,%s,%s", inbound.getInboundOderCode(), detail.getSkuCode(), detail.getLineCode());
            InboundProduct product = products.get(key);
            Long actualTotalCount = detail.getQuantity();
            product.setPraInboundMainNum(actualTotalCount);
            Long baseContent = product.getInboundBaseContent() == null ? 1L : Long.valueOf(product.getInboundBaseContent());
            product.setPraInboundNum(actualTotalCount / baseContent);
            product.setPraTaxPurchaseAmount(product.getPreTaxPurchaseAmount());
            product.setPraTaxAmount(product.getPraTaxPurchaseAmount().multiply(BigDecimal.valueOf(actualTotalCount))
                    .setScale(4, BigDecimal.ROUND_HALF_UP));
            // 更新wms回传商品实际信息
            Integer count = inboundProductDao.update(product);
            LOGGER.info("更新入库单商品实际信息：{}" , count);

            // 计算入库单主表的实际值
            praInboundNum = inbound.getPraInboundNum() + product.getPraInboundNum();
            praMainUnitNum = inbound.getPraMainUnitNum() + product.getPraInboundMainNum();
            praTaxAmount = inbound.getPraTaxAmount().add(product.getPraTaxAmount());
            if(product.getTax() != null){
                BigDecimal amount = Calculate.computeNoTaxPrice(product.getPraTaxAmount(), product.getTax());
                praAmount = inbound.getPraAmount().add(amount);
            }
        }

        // 查询批次数据
        List<InboundBatch> inboundBatchList = Lists.newArrayList();
        InboundBatch productBatch = null;
        if(CollectionUtils.isNotEmpty(request.getBatchList()) && request.getBatchList().size() > 0){
            for (MovementBatchWmsReq batch : request.getBatchList()) {
                List<StockBatch> batchList = stockBatchDao.stockBatchByOutbound(batch.getSkuCode(), allocation1.getCallOutWarehouseCode(), batch.getBatchCode());
                if (CollectionUtils.isEmpty(batchList) && batchList.size() <= 0) {
                    LOGGER.info("wms回传出库单，未查询到sku的批次库存信息:{}", batch.getSkuCode());
                }else {
                    Integer count1 = allocationProductBatchMapper.selectCountByCode(allocation1.getAllocationCode(), batch.getSkuCode(), batch.getBatchCode());
                    if(count1 > 0){
                        // 更新
                        AllocationBatchRequest detail = new AllocationBatchRequest();
                        detail.setAllocationCode(allocation1.getAllocationCode());
                        detail.setActualTotalCount(batch.getQuantity());
                        detail.setBatchInfoCode(batchList.get(0).getBatchInfoCode());
                        allocationProductBatchMapper.updateByBatchIn(detail);
                    }else {
                        AllocationProductBatch allocationProductBatch = new AllocationProductBatch();
                        allocationProductBatch.setAllocationCode(allocation1.getAllocationCode());
                        allocationProductBatch.setCallInBatchNumber(batchList.get(0).getBatchCode());
                        allocationProductBatch.setCallInBatchInfoCode(batchList.get(0).getBatchInfoCode());
                        allocationProductBatch.setSupplierCode(batchList.get(0).getSupplierCode());
                        allocationProductBatch.setSkuCode(batch.getSkuCode());
                        allocationProductBatch.setSkuName(batch.getSkuName());
                        allocationProductBatch.setProductDate(batch.getProductDate());
                        allocationProductBatch.setCallInActualTotalCount(batch.getQuantity());
                        allocationProductBatch.setQuantity(batch.getQuantity());
                        allocationProductBatch.setLineNum(Long.valueOf(batch.getLineCode()));
                        allocationProductBatch.setTaxPrice(batchList.get(0).getTaxCost() == null ? BigDecimal.ZERO : batchList.get(0).getTaxCost());
                        allocationProductBatch.setTax(batchList.get(0).getTaxRate() == null ? BigDecimal.ZERO : batchList.get(0).getTaxRate());
                        allocationProductBatchMapper.insertSelective(allocationProductBatch);

                        productBatch = new InboundBatch();
                        productBatch.setInboundOderCode(inbound.getInboundOderCode());
                        productBatch.setBatchCode(allocationProductBatch.getCallInBatchNumber());
                        productBatch.setBatchInfoCode(allocationProductBatch.getCallInBatchInfoCode());
                        productBatch.setSupplierCode(inbound.getSupplierCode());
                        productBatch.setSupplierName(inbound.getSupplierName());
                        productBatch.setSkuCode(batch.getSkuCode());
                        productBatch.setSkuName(batch.getSkuName());
                        productBatch.setProductDate(batch.getProductDate());
                        productBatch.setTotalCount(batch.getQuantity());
                        productBatch.setActualTotalCount(batch.getQuantity());
                        productBatch.setLineCode(batch.getLineCode().intValue());
                        inboundBatchList.add(productBatch);

                        detailBatchList.add(allocationProductBatch);
                    }
                }
            }
        }
        allocation1.setDetailList(detailLists);
        allocation1.setDetailBatchList(detailBatchList);

        if(CollectionUtils.isNotEmpty(inboundBatchList) && inboundBatchList.size() > 0){
            Integer count = inboundBatchDao.insertAll(inboundBatchList);
            log.info("入库单添加批次信息:{}", count);
        }

        // 入库单主表的实际值
        inbound.setPraInboundNum(praInboundNum);
        inbound.setPraMainUnitNum(praMainUnitNum);
        inbound.setPraTaxAmount(praTaxAmount);
        inbound.setPraAmount(praAmount);
        // 保存日志
        productCommonService.instanceThreeParty(inbound.getInboundOderCode(), HandleTypeCoce.RETURN_INBOUND_ODER.getStatus(),
                ObjectTypeCode.INBOUND_ODER.getStatus(), request, HandleTypeCoce.RETURN_INBOUND_ODER.getName(), new Date(),
                null, null);
        // 更新入库单主表实际值
        Integer k = inboundDao.updateByPrimaryKeySelective(inbound);
        LOGGER.info("入库单主信息实际值更新:{}",k);
    }

    private boolean updateOut(MovementWmsOutReq request, Allocation allocation1, List<AllocationProduct> detailLists, List<AllocationProductBatch> detailBatchList) {
        // 更新出库主表
        Outbound outbound = outboundDao.selectByCode(request.getOutboundOderCode());
        if (outbound == null) {
            LOGGER.info("WMS回传出库单信息为空");
            return true;
        }
        outbound.setOutboundStatusCode(InOutStatus.COMPLETE_INOUT.getCode());
        outbound.setOutboundStatusName(InOutStatus.COMPLETE_INOUT.getName());
        outbound.setPraOutboundNum(0L);
        outbound.setPraMainUnitNum(0L);
        outbound.setPraTaxAmount(BigDecimal.ZERO);
        outbound.setPraTax(BigDecimal.ZERO);
        outbound.setPraAmount(BigDecimal.ZERO);
        outbound.setCompanyCode(Global.COMPANY_09);
        outbound.setCompanyName(Global.COMPANY_09_NAME);
        outbound.setOutboundTime(new Date());
        // 计算出库单总和
        Long praOutboundCount = 0L, praTotalOutboundCount = 0L;
        BigDecimal praOutboundAmount = BigDecimal.ZERO, praTotalOutboundAmount = BigDecimal.ZERO;

        // 更新主表数据 / 出库单商品表
        List<MovementProductWmsReq> detailList = request.getDetailList();
        OutboundProduct outboundProduct;
        for (MovementProductWmsReq detail : detailList) {
            // 移库商品信息
            AllocationProduct allocationProduct = new AllocationProduct();
            allocationProduct.setAllocationCode(allocation1.getAllocationCode());
            allocationProduct.setQuantity(detail.getQuantity());
            allocationProduct.setSkuCode(detail.getSkuCode());
            allocationProduct.setLineNum(detail.getLineCode());
            allocationProductMapper.updateQuantityBySkuCodeAndSource(allocationProduct);

            // 查询对应的出库单商品信息
            outboundProduct = outboundProductDao.selectByLineCode(allocation1.getOutboundOderCode(), detail.getSkuCode(), detail.getLineCode());
            if(outboundProduct != null) {
                Long actualTotalCount = detail.getQuantity();
                Long content = outboundProduct.getOutboundBaseContent() == null ? 1L : Long.valueOf(outboundProduct.getOutboundBaseContent());
                outboundProduct.setPraOutboundNum(actualTotalCount / content);
                outboundProduct.setPraOutboundMainNum(actualTotalCount);
                outboundProduct.setPraTaxPurchaseAmount(outboundProduct.getPreTaxPurchaseAmount());
                outboundProduct.setPraTaxAmount(BigDecimal.valueOf(outboundProduct.getPraOutboundMainNum()).
                        multiply(outboundProduct.getPraTaxPurchaseAmount()).setScale(4, BigDecimal.ROUND_HALF_UP));
                detailLists.add(allocationProduct);

                Integer count = outboundProductDao.update(outboundProduct);
                LOGGER.info("更新出库单商品信息：{}", count);

                praOutboundCount += outbound.getPraOutboundNum();
                praTotalOutboundCount += outbound.getPraMainUnitNum();
                praTotalOutboundAmount = outbound.getPraTaxAmount().add(outboundProduct.getPraTaxAmount());
                praOutboundAmount = outbound.getPraAmount().add(Calculate.computeNoTaxPrice(outboundProduct.getPraTaxPurchaseAmount(), outboundProduct.getTax())
                        .multiply(BigDecimal.valueOf(outboundProduct.getPraOutboundMainNum())));
            }
        }

        // 更新调拨批次表
        // 查询批次数据
        OutboundBatch outboundBatch;
        List<OutboundBatch> outboundBatches = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(request.getBatchList()) && request.getBatchList().size() > 0){
            for (MovementBatchWmsReq batch : request.getBatchList()) {
                List<StockBatch> batchList = stockBatchDao.stockBatchByOutbound(batch.getSkuCode(), allocation1.getCallOutWarehouseCode(), batch.getBatchCode());
                if (CollectionUtils.isEmpty(batchList) && batchList.size() <= 0) {
                    LOGGER.info("wms回传出库单，未查询到sku的批次库存信息:{}", batch.getSkuCode());
                }else {
                    Integer count1 = allocationProductBatchMapper.selectCountByCode(allocation1.getAllocationCode(), batch.getSkuCode(), batch.getBatchCode());
                    if(count1 > 0){
                        // 更新.
                        AllocationBatchRequest detail = new AllocationBatchRequest();
                        detail.setAllocationCode(allocation1.getAllocationCode());
                        detail.setActualTotalCount(batch.getQuantity());
                        detail.setBatchInfoCode(batchList.get(0).getBatchInfoCode());
                        allocationProductBatchMapper.updateByBatch(detail);
                    }else {
                        AllocationProductBatch allocationProductBatch = new AllocationProductBatch();
                        allocationProductBatch.setAllocationCode(allocation1.getAllocationCode());
                        allocationProductBatch.setCallOutBatchNumber(batchList.get(0).getBatchCode());
                        allocationProductBatch.setCallOutBatchInfoCode(batchList.get(0).getBatchInfoCode());
                        allocationProductBatch.setSupplierCode(batchList.get(0).getSupplierCode());
                        allocationProductBatch.setSkuCode(batch.getSkuCode());
                        allocationProductBatch.setSkuName(batch.getSkuName());
                        allocationProductBatch.setProductDate(batch.getProductDate());
                        allocationProductBatch.setCallOutActualTotalCount(batch.getQuantity());
                        allocationProductBatch.setQuantity(batch.getQuantity());
                        allocationProductBatch.setLineNum(Long.valueOf(batch.getLineCode()));
                        allocationProductBatch.setTaxPrice(batchList.get(0).getTaxCost() == null ? BigDecimal.ZERO : batchList.get(0).getTaxCost());
                        allocationProductBatch.setTax(batchList.get(0).getTaxRate() == null ? BigDecimal.ZERO : batchList.get(0).getTaxRate());
                        allocationProductBatchMapper.insertSelective(allocationProductBatch);

                        outboundBatch = new OutboundBatch();
                        outboundBatch.setOutboundOderCode(outbound.getOutboundOderCode());
                        outboundBatch.setBatchCode(allocationProductBatch.getCallOutBatchNumber());
                        outboundBatch.setBatchInfoCode(allocationProductBatch.getCallOutBatchInfoCode());
                        outboundBatch.setSkuCode(batch.getSkuCode());
                        outboundBatch.setSkuName(batch.getSkuName());
                        outboundBatch.setSupplierCode(outbound.getSupplierCode());
                        outboundBatch.setSupplierName(outbound.getSupplierName());
                        outboundBatch.setProductDate(DateUtils.currentDate());
                        outboundBatch.setTotalCount(batch.getQuantity());
                        outboundBatch.setActualTotalCount(batch.getQuantity());
                        outboundBatch.setLineCode(batch.getLineCode());
                        outboundBatches.add(outboundBatch);

                        detailBatchList.add(allocationProductBatch);
                    }
                }
            }
        }
        allocation1.setDetailList(detailLists);
        allocation1.setDetailBatchList(detailBatchList);

        outbound.setPraOutboundNum(praOutboundCount);
        outbound.setPraMainUnitNum(praTotalOutboundCount);
        outbound.setPraTaxAmount(praTotalOutboundAmount);
        outbound.setPraAmount(praOutboundAmount);
        outbound.setPraTax(praTotalOutboundAmount.subtract(praOutboundAmount));
        outboundDao.update(outbound);
        if(CollectionUtils.isNotEmpty(outboundBatches) && outboundBatches.size() > 0){
            Integer count = outboundBatchDao.insertAll(outboundBatches);
            LOGGER.error("wms回传出库单，新增出库单批次信息：{}", count);
        }
        // 保存出库单日志
        productCommonService.instanceThreeParty(allocation1.getOutboundOderCode(), HandleTypeCoce.RETURN_OUTBOUND_ODER.getStatus(),
                ObjectTypeCode.OUTBOUND_ODER.getStatus(), outbound, HandleTypeCoce.RETURN_OUTBOUND_ODER.getName(), new Date(),
                outbound.getCreateBy(), null);
        return false;
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
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isNotEmptyCollection(allocation.getDetailBatchList())){
            allocationProductBatchMapper.saveList(allocation.getDetailBatchList());
        }
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
           if(changeStockRequest.getOperationType() == 2){
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
            }
            //预计出库数量
//            outboundProduct.setPreOutboundNum(item.getQuantity());
            //预计出库主数量
//            outboundProduct.setPreOutboundMainNum(item.getQuantity());
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
        List<AllocationProduct> detailList = new ArrayList<>();
        Long quantity = 0L;
        for (MovementProductWmsReq movementProductWmsReq : request.getDetailList()) {
            AllocationProduct aProduct = new AllocationProduct();
            aProduct.setAllocationCode(request.getMovementCode());
            aProduct.setSkuCode(movementProductWmsReq.getSkuCode());
            aProduct.setLineNum(movementProductWmsReq.getLineCode());
            aProduct.setQuantity(movementProductWmsReq.getQuantity());
            aProduct.setActualTotalCount(movementProductWmsReq.getQuantity());
            quantity += movementProductWmsReq.getQuantity();
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
        if(request.getBatchList().size() > 0){
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
                allocationProductBatch.setCallOutActualTotalCount(batchList.getQuantity());
                allocationProductBatch.setCallInActualTotalCount(batchList.getQuantity());
                allocationProductBatch.setLineNum(batchList.getLineCode());
                detailBatchList.add(allocationProductBatch);
            }
        }
        Allocation allocation = new Allocation();
        BeanUtils.copyProperties(request, allocation);
        allocation.setAllocationCode(request.getMovementCode());
        allocation.setCompanyCode(COMPANY_CODE);
        allocation.setCompanyName(COMPANY_NAME);
        allocation.setQuantity(quantity);
        allocation.setCreateBy(request.getCreateByName());
//        allocation.setCreateTime(new DateTime(new Long(request.getCreateTime())).toDate());
        allocation.setCreateTime(request.getCreateTime());
        allocation.setUpdateBy(request.getUpdateByName());
        allocation.setPrincipal(request.getUpdateByName());
//        allocation.setUpdateTime(new DateTime(new Long(request.getReceiptTime())).toDate());
        allocation.setUpdateTime(request.getReceiptTime());
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
