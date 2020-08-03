package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.DeliveryItemReqVo;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.DeliveryReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryOrderProductListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.QueryProductUniqueCodeListReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoItemRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfoItem record);

    int insertSelective(OrderInfoItem record);

    OrderInfoItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfoItem record);

    int updateByPrimaryKey(OrderInfoItem record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/13
     * @param items
     * @return int
     */
    int insertBatch(List<OrderInfoItem> items);
    /**
     * 商品唯一码查询
     * @author NullPointException
     * @date 2019/6/18
     * @param reqVO
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO>
     */
    List<QueryOrderProductListRespVO> selectproductUniqueCodeList(QueryProductUniqueCodeListReqVO reqVO);
    /**
     * 订单商品查询
     * @author NullPointException
     * @date 2019/6/17
     * @param reqVO
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO>
     */
    List<QueryOrderProductListRespVO> selectOrderProductList(QueryOrderProductListReqVO reqVO);
    /**
     * 通过id批量更新实发数量
     * @author NullPointException
     * @date 2019/6/24
     * @param reqVO
     * @return int
     */
    int updateBatchNumById(List<DeliveryItemReqVo> reqVO);

    Integer insertList(List<OrderInfoItem> list);

    List<OrderInfoItem> listDetailForSap(SapOrderRequest sapOrderRequest);

    List<QueryOrderInfoItemRespVO> productList(String orderCode);

    OrderInfoItem selectOrderByLine(@Param("orderCode") String orderCode, @Param("skuCode")String skuCode, @Param("lineCode")Long lineCode);

    Integer updateBatch(List<OrderInfoItem> itemList);

    List<OrderInfoItem> selectByIds(List<Long> itemIds);

    List<String> selectByOrderCodes(@Param("list") List<String> orderCodeList);

    List<OrderInfoItem> selectListByOrderCode(@Param("orderCode") String orderCode);

    Integer updateByReturnCount(OrderInfoItem orderInfoItem);
}