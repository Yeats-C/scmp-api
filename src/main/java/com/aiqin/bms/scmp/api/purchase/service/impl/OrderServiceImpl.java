package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:36
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderInfoItemMapper orderInfoItemMapper;
    @Override
    public Boolean save(OrderInfoReqVO reqVO){
        OrderInfo info = BeanCopyUtils.copy(reqVO, OrderInfo.class);
        info.setCreateDate(new Date());
        info.setOperator(CommonConstant.SYSTEM_AUTO);
        info.setOperator(CommonConstant.SYSTEM_AUTO_CODE);
        info.setOperatorTime(new Date());
        //TODO 1.需要验证 2.需要根据订单类型传入采购或者库房
        saveData(reqVO, info);
        return true;
    }
    @Override
    public void saveData(OrderInfoReqVO reqVO, OrderInfo info){
        int insert = orderInfoMapper.insert(info);
        if (insert < 1) {
            log.error("订单主表插入影响条数：[{}]", insert);
            throw new BizException(ResultCode.ORDER_SAVE_FAILURE);
        }
        List<OrderInfoItem> infoItems = BeanCopyUtils.copyList(reqVO.getProductList(), OrderInfoItem.class);
        int i = orderInfoItemMapper.insertBatch(infoItems);
        if(i!=infoItems.size()){
            log.error("订单附表插入影响条数：[{}]", insert);
            throw new BizException(ResultCode.ORDER_SAVE_FAILURE);
        }
    }
}
