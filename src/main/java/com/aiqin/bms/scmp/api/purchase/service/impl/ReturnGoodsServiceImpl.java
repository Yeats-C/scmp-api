package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnInspectionReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.ReturnOrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.ReturnGoodsService;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReturnGoodsServiceImpl implements ReturnGoodsService {

    @Autowired
    private ReturnOrderInfoMapper returnOrderInfoMapper;

    @Autowired
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;

    @Autowired
    private StockService stockService;

    @Autowired
    private WarehouseService warehouseService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(List<ReturnOrderInfoReqVO> reqVO) {
        //校验
        validateOrderData(reqVO);
        Date date = new Date();
        //数据处理
        List<ReturnOrderInfo> orders = Lists.newCopyOnWriteArrayList();
        List<ReturnOrderInfoItem> orderItems = Lists.newCopyOnWriteArrayList();
//        List<ReturnOrderInfoLog> logs = Lists.newCopyOnWriteArrayList();
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
//            OrderInfoLog log = new OrderInfoLog(null,info.getOrderCode(),info.getOrderStatus(), OrderStatus.getAllStatus().get(info.getOrderStatus()).getBackgroundOrderStatus(),OrderStatus.getAllStatus().get(info.getOrderStatus()).getStandardDescription(),null,info.getOperator(),date,info.getCompanyCode(),info.getCompanyName());
//            logs.add(log);
        });
        //保存
        saveData(orderItems, orders);
        //存日志
//        saveLog(logs);
        return true;
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
        List<QueryReturnOrderManagementReqVO> list = returnOrderInfoMapper.selectReturnOrderManagementList(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(), list);
    }

    @Override
    public ReturnOrderDetailRespVO returnOrderDetail(String code) {
        //todo 实体已完成，查询还没做，因为入库单的表还没有。这里需要查询入库的信息 sql未写
        ReturnOrderDetailRespVO respVO =  returnOrderInfoMapper.selectReturnOrderDetail(code);
        if(Objects.isNull(respVO)){
            throw new BizException(ResultCode.GET_RETURN_GOODS_DETAIL_FAILED);
        }
        return respVO;
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
        InspectionDetailRespVO respVO = returnOrderInfoMapper.selectInspectionDetail(code);//TODO sql 未写
        if(Objects.isNull(respVO)){
            throw new BizException(ResultCode.QUERY_INSPECTION_DETAIL_ERROR);
        }
        List<ReturnOrderInfoInspectionItemRespVO> inspectionItemRespVO = BeanCopyUtils.copyList(respVO.getItemList(), ReturnOrderInfoInspectionItemRespVO.class);
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
