package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.OrderType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.ReturnOrderStatus;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.product.domain.converter.returnorder.ReturnOrderToInboundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnOrderInfoReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.ReturnReceiptReqVO;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ChangeOrderStatusReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnInspectionReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnInspectionReq;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnOrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoInspectionItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoLogMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
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

    @Autowired
    private ReturnOrderInfoMapper returnOrderInfoMapper;

    @Autowired
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private InboundService inboundService;

    @Autowired
    private ReturnOrderInfoInspectionItemMapper returnOrderInfoInspectionItemMapper;

    @Autowired
    private ReturnOrderInfoLogMapper returnOrderInfoLogMapper;

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
            info.setOperator(CommonConstant.SYSTEM_AUTO);
            info.setOperatorCode(CommonConstant.SYSTEM_AUTO_CODE);
            info.setOperatorTime(date);
            orders.add(info);
            List<ReturnOrderInfoItem> orderItem = BeanCopyUtils.copyList(o.getItemReqVOList(), ReturnOrderInfoItem.class);
            orderItems.addAll(orderItem);
            //拼装日志信息
            ReturnOrderInfoLog log = new ReturnOrderInfoLog(null,info.getReturnOrderCode(),info.getOrderStatus(), ReturnOrderStatus.getAllStatus().get(info.getOrderStatus()).getBackgroundOrderStatus(),ReturnOrderStatus.getAllStatus().get(info.getOrderStatus()).getStandardDescription(),null,info.getOperator(),date,info.getCompanyCode(),info.getCompanyName());
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
    public BasePage<QueryReturnOrderManagementRespVO> returnOrderManagement(QueryReturnOrderManagementReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
        reqVO.setCompanyCode(getUser().getCompanyCode());
        List<QueryReturnOrderManagementRespVO> list = returnOrderInfoMapper.selectReturnOrderManagementList(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(), list);
    }

    @Override
    public ReturnOrderDetailRespVO returnOrderDetail(String code) {
        ReturnOrderDetailRespVO respVO =  returnOrderInfoMapper.selectReturnOrderDetail(code);
        if(Objects.isNull(respVO)){
            throw new BizException(ResultCode.GET_RETURN_GOODS_DETAIL_FAILED);
        }
        respVO.setInboundList(inboundInfo(code));
        return respVO;
    }
    @Override
    public List<ReturnOrderInfoApplyInboundRespVO> inboundInfo(String code) {
       return returnOrderInfoMapper.selectInbound(code);
    }
    @Override
    public BasePage<QueryReturnInspectionRespVO> returnInspection(QueryReturnInspectionReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
        List<QueryReturnInspectionRespVO> list = returnOrderInfoMapper.selectreturnInspectionList(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(), list);
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveReturnInspection(List<ReturnInspectionReq> reqVO,String remark) {
        //首先保存传过来的数据
        List<ReturnOrderInfoInspectionItem> items = BeanCopyUtils.copyList(reqVO, ReturnOrderInfoInspectionItem.class);
        ReturnOrderInfo info = new ReturnOrderInfo();
        info.setReturnOrderCode(items.get(0).getReturnOrderCode());
        info.setInspectionRemark(remark);
        int i2 = returnOrderInfoMapper.updateByReturnOrderCodeSelective(info);
        if (i2<0){
            throw new BizException(ResultCode.UPDATE_RETURN_ORDER_INFO_FAILED);
        }
        for (int i = 0; i < items.size(); i++) {
            ReturnOrderInfoInspectionItem item = items.get(i);
            item.setProductLineNum((long)i*10);
        }
        int i = returnOrderInfoInspectionItemMapper.insertBatch(items);
        if(i!=items.size()){
            throw new BizException(ResultCode.SAVE_INSPECTION_DATA_FAILED);
        }
        //调用异步方法传入库单信息
        ReturnGoodsServiceImpl returnGoodsService =  (ReturnGoodsServiceImpl)AopContext.currentProxy();
        returnGoodsService.sendToInBound(items);
        return Boolean.TRUE;
    }
    @Override
    @Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public void sendToInBound(List<ReturnOrderInfoInspectionItem> items) {
        //TODO 调用入库接口
        //这里会有多个入库单的信息
        List<InboundReqSave> list = dealData(items);
        Boolean b = inboundService.saveList(list);
        if(b){
            //TODO 存日志
            //该状态
        }
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
    public BasePage<QueryReturnOrderManagementRespVO> directReturnOrderManagement(QueryReturnOrderManagementReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
        List<Integer> orderTypes = Lists.newArrayList();
        orderTypes.add(OrderType.DIRECT_DELIVERY.getNum());
        orderTypes.add(OrderType.DIRECT_DELIVERY_FUCAI.getNum());
        reqVO.setOrderTypeCode(orderTypes);
        reqVO.setCompanyCode(getUser().getCompanyCode());
        List<QueryReturnOrderManagementRespVO> list = returnOrderInfoMapper.selectReturnOrderManagementList(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(), list);
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
    public Boolean returnReceipt(List<ReturnReceiptReqVO> reqVO, String code) {
        //更新状态
        ChangeOrderStatusReqVO vo = new ChangeOrderStatusReqVO();
        vo.setOperatorCode(getUser().getPersonName());
        vo.setOperatorCode(getUser().getPersonId());
        vo.setOrderCode(code);
        vo.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        changeStatus(vo);
        //更新数量
        saveReturnReceipt(reqVO);
        return Boolean.TRUE;
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
        order.setOperator(reqVO.getOperator());
        order.setOperatorCode(reqVO.getOperatorCode());
        order.setOperatorTime(date);
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
    public Boolean record(ReturnReq reqVO) {
        Inbound inbound=new Inbound();
        ReturnOrderInfoReq returnOrderInfo=reqVO.getReturnOrderInfo();
        inbound.setCompanyCode(returnOrderInfo.getCompanyCode());
        inbound.setCompanyName(returnOrderInfo.getCompanyName());
        //进行第一次状态
        inbound.setInboundStatusCode(new Byte("1"));


        return null;
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
