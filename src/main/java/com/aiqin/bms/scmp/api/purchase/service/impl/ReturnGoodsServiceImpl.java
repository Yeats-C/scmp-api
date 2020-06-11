package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.InboundBatchDao;
import com.aiqin.bms.scmp.api.product.dao.InboundDao;
import com.aiqin.bms.scmp.api.product.dao.InboundProductDao;
import com.aiqin.bms.scmp.api.product.domain.converter.returnorder.ReturnOrderToInboundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundBatchReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.ReturnReceiptReqVO;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ChangeOrderStatusReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoInspectionItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoLogMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
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
    private WarehouseService warehouseService;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(List<ReturnOrderInfoReqVO> reqVO) {
        //校验
        validateOrderData(reqVO);
        Date date = new Date();
        //数据处理
        List<ReturnOrderInfo> orders = Lists.newCopyOnWriteArrayList();
        List<ReturnOrderInfoItem> orderItems = Lists.newCopyOnWriteArrayList();
        List<ReturnOrderInfoLog> logs = Lists.newCopyOnWriteArrayList();
        reqVO.parallelStream().forEach(o -> {
            ReturnOrderInfo info = BeanCopyUtils.copy(o, ReturnOrderInfo.class);
            info.setCreateDate(date);
//            info.setOperator(CommonConstant.SYSTEM_AUTO);
//            info.setOperatorCode(CommonConstant.SYSTEM_AUTO_CODE);
//            info.setOperatorTime(date);
            orders.add(info);
            List<ReturnOrderInfoItem> orderItem = BeanCopyUtils.copyList(o.getItemReqVOList(), ReturnOrderInfoItem.class);
            orderItems.addAll(orderItem);
            //拼装日志信息
            ReturnOrderInfoLog log = new ReturnOrderInfoLog(null,info.getReturnOrderCode(),info.getOrderStatus(),
                    ReturnOrderStatus.getAllStatus().get(info.getOrderStatus()).getBackgroundOrderStatus(),
                    ReturnOrderStatus.getAllStatus().get(info.getOrderStatus()).getStandardDescription(),null,info.getUpdateByName(),date,info.getCompanyCode(),info.getCompanyName());
            logs.add(log);
        });
        //保存
        saveData(orderItems, orders);
        //存日志
        saveLog(logs);
        return true;
    }

    @Override
    public void saveLog(List<ReturnOrderInfoLog> logs) {
        if(CollectionUtils.isEmptyCollection(logs)){
            return;
        }
        int i = returnOrderInfoLogMapper.insertBatch(logs);
        if (i != logs.size()) {
            log.info("需要插入订单日志条数[{}]，实际插入订单日志的条数：[{}]",logs.size(),i);
//            throw new BizException(ResultCode.LOG_SAVE_ERROR);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(List<ReturnOrderInfoItem> orderItems, List<ReturnOrderInfo> orders) {
        if (CollectionUtils.isNotEmptyCollection(orders)) {
            int i = returnOrderInfoMapper.insertBatch(orders);
            if (i != orderItems.size()) {
                throw new BizException(ResultCode.SAVE_RETURN_ORDER_FAILED);
            }
        }
        if (CollectionUtils.isNotEmptyCollection(orderItems)) {
            int i = returnOrderInfoItemMapper.insertBatch(orderItems);
            if (i != orderItems.size()) {
                throw new BizException(ResultCode.SAVE_RETURN_ORDER_ITEM_FAILED);
            }
        }
    }

    @Override
    public HttpResponse<ReturnOrderDetailResponse> returnOrderDetail(String returnOrderCode) {
        LOGGER.info("查询退货单详情：", returnOrderCode);
        if(StringUtils.isBlank(returnOrderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode1(returnOrderCode);
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
    public HttpResponse inboundBatch(InboundBatchReqVo request){
        List<ReturnOrderInboundBatchResponse> list = returnOrderInfoInspectionItemMapper.inboundBatchByReturnOrderList(request);
        Integer count = returnOrderInfoInspectionItemMapper.inboundBatchByReturnOrderCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public List<ReturnOrderInfoApplyInboundRespVO> inboundInfo(String code) {
       return returnOrderInfoMapper.selectInbound(code);
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfo>> returnOrderList(ReturnGoodsRequest request) {
        List<ReturnOrderInfo> list = returnOrderInfoMapper.list(request);
        Integer count = returnOrderInfoMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfoItem>> returnOrderProductList(ReturnGoodsRequest request){
        List<ReturnOrderInfoItem> list = returnOrderInfoItemMapper.list(request);
        Integer count = returnOrderInfoItemMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public HttpResponse<PageResData<ReturnOrderInfoInspectionItem>> returnOrderBatchList(ReturnGoodsRequest request){
        List<ReturnOrderInfoInspectionItem> list = returnOrderInfoInspectionItemMapper.list(request);
        Integer count = returnOrderInfoInspectionItemMapper.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

    @Override
    public InspectionDetailRespVO inspectionDetail(String code) {
        //首先查出数据
        InspectionDetailRespVO respVO = returnOrderInfoMapper.selectInspectionDetail(code);
        if(Objects.isNull(respVO)){
            throw new BizException(ResultCode.QUERY_INSPECTION_DETAIL_ERROR);
        }
       List<ReturnOrderInfoInspectionItemRespVO> inspectionItemRespVO =  returnOrderInfoMapper.selectInspectionItemList(code,respVO.getOrderCode());
        //根据仓编码查询下面的库
        List<WarehouseResVo> warehouse = warehouseService.getWarehouseByLogisticsCenterCode(respVO.getTransportCenterCode());
        if(CollectionUtils.isEmptyCollection(warehouse)){
            throw new BizException(ResultCode.DATA_ERROR);
        }
        respVO.setWarehouseResVoList(warehouse);
        Map<Byte, WarehouseResVo> warehouseTypeMap = warehouse.stream().collect(Collectors.toMap(WarehouseResVo::getWarehouseTypeCode, Function.identity(), (k1, k2) -> k1));
        for (ReturnOrderInfoInspectionItemRespVO o : inspectionItemRespVO) {
            o.setOriginalLineNum(o.getProductLineNum().intValue());
            o.setProductLineNum(null);
            //根据批次判断需要入哪个仓
            //首先判断新品/残品
            if (Objects.equals(CommonConstant.NEW_PRODUCT, o.getProductStatus())) {
                o.setWarehouseCode(warehouseTypeMap.get((byte) 1).getWarehouseCode());
            } else if (Objects.equals(CommonConstant.DEFECTIVE, o.getProductStatus())) {
                o.setWarehouseCode(warehouseTypeMap.get((byte) 2).getWarehouseCode());
            } else {
                throw new BizException(ResultCode.DATA_ERROR);
            }
        }
        respVO.setInspectionItemList(inspectionItemRespVO);
        return respVO;
    }

    @Override
    public HttpResponse returnOrderCancel(String returnOrderCode){
       if(StringUtils.isBlank(returnOrderCode)){
           return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
       }
       ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
       returnOrderInfo.setUpdateByName(getUser().getPersonName());
       returnOrderInfo.setUpdateById(getUser().getPersonId());
       returnOrderInfo.setOrderStatus(ReturnOrderStatus.cancelled.getStatusCode());
       Integer count = returnOrderInfoMapper.update(returnOrderInfo);
       LOGGER.info("更改退货单异常终止：{}", count);
       return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse saveReturnInspection(ReturnInspectionRequest request) {
        if(request == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 保存退货验货
        ReturnOrderInfo returnOrder = new ReturnOrderInfo();
        returnOrder.setReturnOrderCode(request.getReturnOrderCode());
        returnOrder.setInspectionRemark(request.getInspectionRemark());
        returnOrder.setUpdateById(getUser().getPersonId());
        returnOrder.setUpdateByName(getUser().getPersonName());
        Integer orderCount = returnOrderInfoMapper.update(returnOrder);
        LOGGER.info("更新退货单保存验货信息：", orderCount);

        if(CollectionUtils.isEmptyCollection(request.getItemList())){
            LOGGER.info("退货验货单的商品信息为空：{}", JsonUtil.toJson(request.getItemList()));
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 500, "退货验货单的商品信息为空"));
        }
        Integer batchCount = returnOrderInfoInspectionItemMapper.insertBatch(request.getItemList());
        LOGGER.info("保存退货单验货商品信息：", batchCount);
//        //调用异步方法传入库单信息
//        ReturnGoodsServiceImpl returnGoodsService =  (ReturnGoodsServiceImpl)AopContext.currentProxy();
//        returnGoodsService.sendToInBound(items);

        // 调用生成入库单 并传送wms
        InboundReqSave inbound = getInboundReqSave(request.getReturnOrderCode());
        //回传入库单编号
        inboundService.saveInbound(inbound);
        return HttpResponse.success();
    }

    /**
     * 补充数据
     * @author NullPointException
     * @date 2019/6/27
     * @param items
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave>
     */
    private List<InboundReqSave> dealData(List<ReturnOrderInfoInspectionItem> items) {
        if (CollectionUtils.isEmptyCollection(items)) {
            throw new BizException(ResultCode.CAN_NOT_FIND_RETURN_ORDER);
        }
        //查数据
        ReturnOrderInfoDTO dto = returnOrderInfoMapper.selectByCode(items.get(0).getReturnOrderCode());
        if(Objects.isNull(dto)){
            throw new BizException(ResultCode.CAN_NOT_FIND_RETURN_ORDER);
        }
        dto.setItems(items);
        return new ReturnOrderToInboundConverter().convert(dto);
    }

    @Override
    public InspectionViewRespVO inspectionView(String code) {
        return returnOrderInfoMapper.selectInspectionView(code);
    }

    @Override
    public ReturnOrderDetailRespVO directReturnOrderDetail(String code) {
        ReturnOrderDetailRespVO respVO =  returnOrderInfoMapper.selectReturnOrderDetail(code);
        if(Objects.isNull(respVO)){
            throw new BizException(ResultCode.GET_RETURN_GOODS_DETAIL_FAILED);
        }
        return respVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse returnReceipt(List<ReturnOrderInfoItem> itemList) {
        if(CollectionUtils.isEmptyCollection(itemList)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        ReturnOrderInfoItem returnOrderInfoItem;
        for(ReturnOrderInfoItem item : itemList){
            returnOrderInfoItem = new ReturnOrderInfoItem();
            returnOrderInfoItem.setId(item.getId());
            returnOrderInfoItem.setActualPrice(item.getPrice());
            returnOrderInfoItem.setActualChannelUnitPrice(item.getChannelUnitPrice());
            Integer count = returnOrderInfoItemMapper.update(returnOrderInfoItem);
            LOGGER.info("直送退货单实退的商品数量：{}", count);
        }

        ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
        returnOrderInfo.setReturnOrderCode(itemList.get(0).getReturnOrderCode());
        returnOrderInfo.setUpdateById(getUser().getPersonId());
        returnOrderInfo.setUpdateByName(getUser().getPersonName());
        returnOrderInfo.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        Integer returnCount = returnOrderInfoMapper.update(returnOrderInfo);
        LOGGER.info("退货单退货收货完成变更退货单状态：{}", returnCount);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeStatus(ChangeOrderStatusReqVO reqVO) {
        Date date = new Date();
        //先查后改
        ReturnOrderInfo order = returnOrderInfoMapper.selectByCode1(reqVO.getOrderCode());
        if (Objects.isNull(order)) {
            throw new BizException(ResultCode.CAN_NOT_FIND_ORDER);
        }
        //校验 TODO
        order.setOrderStatus(reqVO.getOrderStatus());
//        order.setOperator(reqVO.getOperator());
//        order.setOperatorCode(reqVO.getOperatorCode());
//        order.setOperatorTime(date);
        order.setRemake(reqVO.getRemark());
        //更新
        updateByOrderCode(order);
        //存日志
        ReturnOrderInfoLog log = new ReturnOrderInfoLog(null,reqVO.getOrderCode(),reqVO.getOrderStatus(), ReturnOrderStatus.getAllStatus().get(reqVO.getOrderStatus()).getBackgroundOrderStatus(),ReturnOrderStatus.getAllStatus().get(reqVO.getOrderStatus()).getStandardDescription(),null,reqVO.getOperator(),date,order.getCompanyCode(),order.getCompanyName());
        List<ReturnOrderInfoLog> logs = Lists.newArrayList();
        logs.add(log);
        saveLog(logs);
        return Boolean.TRUE;
    }

    public void updateByOrderCode(ReturnOrderInfo order) {
       int i =  returnOrderInfoMapper.updateByOrderCode(order);
        if(i<1){
            throw new BizException(ResultCode.UPDATE_ORDER_STATUS_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveReturnReceipt(List<ReturnReceiptReqVO> reqVO) {
        int i = returnOrderInfoItemMapper.updateActualInboundNumByIdAndReturnOrderCode(reqVO);
        if (i!=reqVO.size()) {
            throw new BizException(ResultCode.SAVE_RETURN_RECEIPT_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeOrderStatus(String code, Integer status) {
        ChangeOrderStatusReqVO vo = new ChangeOrderStatusReqVO();
        vo.setOperatorCode(getUser().getPersonName());
        vo.setOperatorCode(getUser().getPersonId());
        vo.setOrderCode(code);
        vo.setOrderStatus(status);
        changeStatus(vo);
        return Boolean.TRUE;
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
        returnOrder.setOrderCode(returnOrderInfo.getOrderStoreCode());
        returnOrder.setCreateDate(returnOrderInfo.getCreateTime());
        returnOrder.setBeLock(returnOrderInfo.getReturnLock());
        returnOrder.setLockReason(returnOrderInfo.getReturnReason());
        returnOrder.setDetailAddress(returnOrderInfo.getReceiveAddress());
        returnOrder.setConsignee(returnOrderInfo.getReceivePerson());
        returnOrder.setConsigneePhone(returnOrderInfo.getReceivePerson());
        returnOrder.setDistributionMode(returnOrderInfo.getDistributionModeName());
        // 退货单状态
        if (returnOrderInfo.getOrderType().equals(Global.ORDER_TYPE_0)) {
            returnOrder.setOrderStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_THE_WAREHOUSE.getStatusCode());
        } else if (returnOrderInfo.getOrderType().equals(Global.ORDER_TYPE_1)) {
            returnOrder.setOrderStatus(ReturnOrderStatus.WAITING_FOR_RETURN_TO_INSPECTION.getStatusCode());
        }
        returnOrder.setPaymentTypeCode(returnOrderInfo.getPaymentCode());
        returnOrder.setPaymentType(returnOrderInfo.getPaymentName());
        returnOrder.setProductCount(returnOrderInfo.getProductCount());
        returnOrder.setProductTotalAmount(returnOrderInfo.getReturnOrderAmount());
        returnOrder.setWeight(returnOrderInfo.getTotalWeight());
        returnOrder.setVolume(returnOrderInfo.getTotalVolume());
        returnOrder.setCompanyCode(Global.COMPANY_09);
        returnOrder.setCompanyName(Global.COMPANY_09_NAME);
        Integer count = returnOrderInfoMapper.insert(returnOrder);
        LOGGER.info("添加退货单条数：", count);

        List<ReturnOrderDetailReq> detailList = request.getReturnOrderDetailReqList();
        List<ReturnOrderInfoItem> details = Lists.newArrayList();
        ReturnOrderInfoItem returnOrderInfoItem;
        for (ReturnOrderDetailReq returnOrderDetail : detailList) {
            returnOrderInfoItem = BeanCopyUtils.copy(returnOrderDetail, ReturnOrderInfoItem.class);
            returnOrderInfoItem.setSpec(returnOrderDetail.getProductSpec());
            returnOrderInfoItem.setModel(returnOrderDetail.getModelCode());
            returnOrderInfoItem.setBaseProductContent(returnOrderDetail.getBaseProductSpec().intValue());
            returnOrderInfoItem.setGivePromotion(returnOrderDetail.getProductType());
            returnOrderInfoItem.setPrice(returnOrderDetail.getProductAmount());
            returnOrderInfoItem.setNum(returnOrderDetail.getReturnProductCount());
            returnOrderInfoItem.setAmount(returnOrderDetail.getTotalProductAmount());
            returnOrderInfoItem.setProductLineNum(returnOrderDetail.getLineCode());
            returnOrderInfoItem.setProductStatus(returnOrderDetail.getProductStatus());
            returnOrderInfoItem.setCompanyCode(Global.COMPANY_09);
            returnOrderInfoItem.setCompanyName(Global.COMPANY_09_NAME);
            returnOrderInfoItem.setChannelUnitPrice(returnOrderDetail.getProductAmount());
            returnOrderInfoItem.setTax(returnOrderDetail.getTaxRate());
            details.add(returnOrderInfoItem);
        }
        Integer detailCount = returnOrderInfoItemMapper.insertList(details);
        LOGGER.info("添加退货单详情条数：", detailCount);

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
    public Boolean recordDL(ReturnDLReq reqVO) {
//        reqVO=test1();
        if(ObjectUtils.equals(null,reqVO)
                ||ObjectUtils.equals(null,reqVO.getReturnOrderInfoDLReq())
                ||CollectionUtils.isEmptyCollection(reqVO.getReturnOrderDetailDLReqList())){
         throw new BizException("有必填项为空");
        }
        //进行主表修改
        ReturnOrderInfoDLReq returnOrderInfoDLReq=reqVO.getReturnOrderInfoDLReq();
        if (ObjectUtils.equals(null,returnOrderInfoMapper.selectByCode(returnOrderInfoDLReq.getReturnOrderCode()))){
            throw new BizException("没有对应退货主单");
        }
        ReturnOrderInfo returnOrderInfo=new ReturnOrderInfo();
        returnOrderInfo.setReturnOrderCode(returnOrderInfoDLReq.getReturnOrderCode());
        returnOrderInfo.setActualProductCount(returnOrderInfoDLReq.getActualProductCount());
        returnOrderInfo.setUpdateById(returnOrderInfoDLReq.getReturnById());
        returnOrderInfo.setUpdateTime(returnOrderInfoDLReq.getReturnTime());
        returnOrderInfoMapper.updateByReturnOrderCodeSelective(returnOrderInfo);
        //进行验证
        List<ReturnOrderInfoItem> returnOrderInfoItems=returnOrderInfoItemMapper.selectByReturnOrderCode(returnOrderInfoDLReq.getReturnOrderCode());
        if(CollectionUtils.isEmptyCollection(returnOrderInfoItems)){
            throw new BizException("没有对应退货商品明细");
        }
        //进行商品修改
        List<ReturnOrderDetailDLReq> returnOrderDetailDLReqList= reqVO.getReturnOrderDetailDLReqList();
        for (ReturnOrderDetailDLReq returnOrderDetailDLReq:
        returnOrderDetailDLReqList  ) {
            ReturnOrderInfoItem returnOrderInfoItem=new ReturnOrderInfoItem();
            returnOrderInfoItem.setActualInboundNum(Math.toIntExact(returnOrderDetailDLReq.getActualReturnProductCount()));
            returnOrderInfoItem.setReturnOrderCode(returnOrderInfoDLReq.getReturnOrderCode());
            returnOrderInfoItem.setSkuCode(returnOrderDetailDLReq.getSkuCode());
            returnOrderInfoItem.setSkuName(returnOrderDetailDLReq.getSkuName());
            returnOrderInfoItem.setProductLineNum(returnOrderDetailDLReq.getLineCode());
            returnOrderInfoItemMapper.updateByReturnOrderCodeSelective(returnOrderInfoItem);
        }
      //进行批次的添加
      if(CollectionUtils.isNotEmptyCollection(reqVO.getReturnBatchDetailDLReqList())){
          for (ReturnBatchDetailDLReq returnBatchDetailDLReq:
          reqVO.getReturnBatchDetailDLReqList()) {
              ReturnOrderInfoInspectionItem returnOrderInfoInspectionItem=new ReturnOrderInfoInspectionItem();
              returnOrderInfoInspectionItem.setReturnOrderCode(returnOrderInfoDLReq.getReturnOrderCode());
              returnOrderInfoInspectionItem.setSkuCode(returnBatchDetailDLReq.getSkuCode());
              returnOrderInfoInspectionItem.setSkuName(returnBatchDetailDLReq.getSkuName());
              returnOrderInfoInspectionItem.setLineCode(returnBatchDetailDLReq.getLineCode());
              returnOrderInfoInspectionItem.setBatchCode(String.valueOf(returnBatchDetailDLReq.getBatchNum()));
              returnOrderInfoInspectionItemMapper.insert(returnOrderInfoInspectionItem);
          }

      }
      Boolean isok;
      //发送请求
        if (sendRecordDL(reqVO)){
            log.info("回调成功");
            isok=true;
        }else {
            log.info("回调失败");
            isok=false;
        }
        return isok;
    }

    @Override
    public HttpResponse recordWMS(String inboundOderCode) {
        // 查询入库单的信息
        Inbound inbound = inboundDao.selectByCode(inboundOderCode);
        if(inbound == null){
            LOGGER.info("退货单wms回传入库单的信息为空：{}", JsonUtil.toJson(inbound));
            return HttpResponse.failure(ResultCode.INBOUND_INFO_NULL);
        }
        ReturnOrderInfo returnOrder = new ReturnOrderInfo();
        returnOrder.setActualProductCount(inbound.getPraMainUnitNum());
        returnOrder.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        returnOrder.setUpdateByName(inbound.getUpdateBy());

        // 查询入库单的商品信息
        BigDecimal actualChannelAmount = BigDecimal.ZERO, actualProductAmount = BigDecimal.ZERO;
        ReturnOrderInfoItem returnOrderInfoItem;
        List<InboundProduct> inboundProducts = inboundProductDao.selectByInboundOderCode(inboundOderCode);

        for (InboundProduct product : inboundProducts) {
            // 查询对应的退货单商品信息
            returnOrderInfoItem = returnOrderInfoItemMapper.returnOrderOne(inbound.getSourceOderCode(), product.getSkuCode(), product.getLinenum());
            returnOrderInfoItem.setActualInboundNum(product.getPraInboundMainNum().intValue());
            returnOrderInfoItem.setActualChannelUnitPrice(returnOrderInfoItem.getChannelUnitPrice());
            BigDecimal count = BigDecimal.valueOf(product.getPraInboundMainNum());
            returnOrderInfoItem.setActualTotalChannelPrice(count.multiply(returnOrderInfoItem.getChannelUnitPrice()).setScale(4, BigDecimal.ROUND_HALF_UP));
            returnOrderInfoItem.setActualAmount(returnOrderInfoItem.getPrice());
            returnOrderInfoItem.setActualPrice(count.multiply(returnOrderInfoItem.getPrice()).setScale(4, BigDecimal.ROUND_HALF_UP));
            Integer returnInfoProduct = returnOrderInfoItemMapper.update(returnOrderInfoItem);
            log.info("更新退货单商品:", returnInfoProduct);

            actualChannelAmount.add(returnOrderInfoItem.getActualTotalChannelPrice());
            actualProductAmount.add(returnOrderInfoItem.getActualPrice());
        }

        returnOrder.setActualProductChannelTotalAmount(actualChannelAmount);
        returnOrder.setActualProductTotalAmount(actualProductAmount);
        returnOrder.setActualVolume(0L);
        returnOrder.setActualWeight(0L);
        // 计算实际体积/重量
        if(returnOrder.getVolume() > 0 && returnOrder.getActualProductCount() > 0){
            returnOrder.setActualVolume(returnOrder.getActualProductCount() / returnOrder.getVolume());
        }
        if(returnOrder.getWeight() > 0 && returnOrder.getActualProductCount() > 0){
            returnOrder.setActualWeight(returnOrder.getActualProductCount() / returnOrder.getWeight());
        }
        // 查询入库单的批次信息
        List<InboundBatch> inboundBatches = inboundBatchDao.selectInboundBatchList(inboundOderCode);
        List<ReturnOrderInfoInspectionItem> batchList = Lists.newArrayList();
        ReturnOrderInfoInspectionItem returnBatchItem;
        ReturnOrderInfoInspectionItem returnBatch;
        for (InboundBatch batch : inboundBatches) {
            // 根据批次号、sku、行号查询对应的批次
            returnBatchItem = returnOrderInfoInspectionItemMapper.returnOrderInfo(batch.getBatchInfoCode(),
                    inbound.getSourceOderCode(), batch.getLineCode());
            returnBatchItem.setActualProductCount(batch.getActualTotalCount());

            if(returnBatchItem == null){
                // 根据sku  行号查询对应的批次
                returnBatchItem = returnOrderInfoInspectionItemMapper.returnOrderInfo(batch.getBatchInfoCode(),
                        inbound.getSourceOderCode(), null);
                returnBatchItem.setActualProductCount(batch.getActualTotalCount() + returnBatchItem.getActualProductCount());
                if(returnBatchItem == null){
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
                    batchList.add(returnBatch);
                }
            }

           Integer i = returnOrderInfoInspectionItemMapper.update(returnBatchItem);
            LOGGER.info("更新退货单批次：", i);
        }
        Integer count = returnOrderInfoInspectionItemMapper.insertBatch(batchList);
        LOGGER.info("添加退货单批次：", count);

        Integer returnInfo = returnOrderInfoMapper.update(returnOrder);
        log.info("更新退货单主信息：", returnInfo);
        return HttpResponse.success();
    }


    public Boolean sendRecordDL(ReturnDLReq reqVO) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(urlConfig.Order_URL).append("/reject/info");
            HttpClient httpClient = HttpClient.post(String.valueOf(sb)).json(reqVO).timeout(100000);
            HttpResponse<Boolean> response = httpClient.action().result(new TypeReference<HttpResponse<Boolean>>(){});
            return response.getData();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BizException("查询出错");
        }
    }

    private InboundReqSave getInboundReqSave(String returnOrderCode) {
        LOGGER.info("根据运营中台退货单，开始生成耘链入库单：{}", returnOrderCode);
        // 查询退货单的信息
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode1(returnOrderCode);
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
        inbound.setWarehouseCode(returnOrderInfo.getWarehouseCode());
        //预计入库数量
        inbound.setPreInboundNum(returnOrderInfo.getProductCount());
        //预计主单位数量
        inbound.setPreMainUnitNum(returnOrderInfo.getProductCount());
        inbound.setPreTaxAmount(returnOrderInfo.getReturnOrderAmount());
        inbound.setCountyCode(returnOrderInfo.getDistrictCode());
        inbound.setCountyName(returnOrderInfo.getDistrictName());
        inbound.setCreateBy(returnOrderInfo.getUpdateByName());
        inbound.setUpdateBy(returnOrderInfo.getUpdateByName());

        //进行商品设置
        List<InboundProductReqVo> list = Lists.newArrayList();
        InboundProductReqVo inboundProductReqVo;
        BigDecimal preAmount = BigDecimal.ZERO;
        Long productCount =0L;
        // 查询退货单商品信息
        List<ReturnOrderInfoItem> infoItems = returnOrderInfoItemMapper.selectByReturnOrderCode(returnOrderCode);
        for (ReturnOrderInfoItem detail : infoItems) {
            inboundProductReqVo = BeanCopyUtils.copy(detail, InboundProductReqVo.class);
            inboundProductReqVo.setInboundOderCode(inbound.getInboundOderCode());
            inboundProductReqVo.setNorms(detail.getSpec());
            inboundProductReqVo.setModel(detail.getModelCode());
            inboundProductReqVo.setInboundNorms(detail.getSpec());
            inboundProductReqVo.setInboundBaseUnit(String.valueOf(detail.getZeroDisassemblyCoefficient()));
            inboundProductReqVo.setPreInboundNum(detail.getNum());
            inboundProductReqVo.setPreInboundMainNum(detail.getNum());
            inboundProductReqVo.setPreTaxPurchaseAmount(detail.getPrice());
            inboundProductReqVo.setPreTaxAmount(detail.getAmount());
            inboundProductReqVo.setCreateBy(returnOrderInfo.getUpdateByName());
            inboundProductReqVo.setUpdateBy(returnOrderInfo.getUpdateByName());
            list.add(inboundProductReqVo);
            // 计算预计无税金额、税额
            BigDecimal noTax = Calculate.computeNoTaxPrice(detail.getAmount(), detail.getTax());
            preAmount = preAmount.add(noTax);
            productCount += detail.getNum();
        }
        inbound.setList(list);
        inbound.setPreMainUnitNum(productCount);
        inbound.setPraAmount(preAmount);
        inbound.setPreTax(inbound.getPreTaxAmount().subtract(preAmount));

        // 查询退货单批次信息
        List<ReturnOrderInfoInspectionItem> items = returnOrderInfoInspectionItemMapper.returnOrderBatchList(returnOrderCode);
        if(CollectionUtils.isNotEmptyCollection(items)){
            InboundBatch inboundBatch;
            List<InboundBatch> batchList = Lists.newArrayList();
            for (ReturnOrderInfoInspectionItem item : items){
                inboundBatch = BeanCopyUtils.copy(item, InboundBatch.class);
                inboundBatch.setInboundOderCode(inbound.getInboundOderCode());
                inboundBatch.setTotalCount(item.getProductCount());
                inboundBatch.setActualTotalCount(item.getActualProductCount());
                inboundBatch.setCreateById(returnOrderInfo.getUpdateById());
                inboundBatch.setCreateByName(returnOrderInfo.getUpdateByName());
                inboundBatch.setUpdateById(returnOrderInfo.getUpdateById());
                inboundBatch.setUpdateByName(returnOrderInfo.getUpdateByName());
                batchList.add(inboundBatch);
            }
            inbound.setInboundBatchList(batchList);
        }
        LOGGER.info("根据运营中台退货单，转换生成耘链入库单参数：{}", JsonUtil.toJson(inbound));
        return inbound;
    }

    /**
     * 参数验证
     *
     * @param reqVO
     * @return void
     * @author NullPointException
     * @date 2019/6/19
     */
    private void validateOrderData(List<ReturnOrderInfoReqVO> reqVO) {

    }
}
