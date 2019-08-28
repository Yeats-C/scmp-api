package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.ReturnReceiptReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnOrderInfoItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfoItem record);

    int insertSelective(ReturnOrderInfoItem record);

    ReturnOrderInfoItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfoItem record);

    int updateByPrimaryKey(ReturnOrderInfoItem record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/19
     * @param orderItems
     * @return int
     */
    int insertBatch(List<ReturnOrderInfoItem> orderItems);
    /**
     * 保存退货收货
     * @author NullPointException
     * @date 2019/7/4
     * @param reqVO
     * @param code
     * @return int
     */
    int updateActualInboundNumByIdAndReturnOrderCode(@Param("items") List<ReturnReceiptReqVO> reqVO);

    Integer insertList(List<ReturnOrderInfoItem> list);

    List<ReturnOrderInfoItem> listDetailForSap(SapOrderRequest sapOrderRequest);
}