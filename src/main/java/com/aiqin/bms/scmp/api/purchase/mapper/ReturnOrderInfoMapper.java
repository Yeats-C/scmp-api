package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnInspectionReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnOrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfo record);

    int insertSelective(ReturnOrderInfo record);

    ReturnOrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfo record);

    int updateByPrimaryKey(ReturnOrderInfo record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/19
     * @param orders
     * @return int
     */
    int insertBatch(List<ReturnOrderInfo> orders);
    /**
     * 退货管理列表
     * @author NullPointException
     * @date 2019/6/19
     * @param reqVO
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO>
     */
    List<QueryReturnOrderManagementRespVO> selectReturnOrderManagementList(QueryReturnOrderManagementReqVO reqVO);
    /**
     * 详情
     * @author NullPointException
     * @date 2019/6/19
     * @param code
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderDetailRespVO
     */
    ReturnOrderDetailRespVO selectReturnOrderDetail(String code);
    /**
     * 退货验货
     * @author NullPointException
     * @date 2019/6/20
     * @param reqVO
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO>
     */
    List<QueryReturnInspectionRespVO> selectreturnInspectionList(QueryReturnInspectionReqVO reqVO);
    /**
     * 验货
     * @author NullPointException
     * @date 2019/6/20
     * @param code
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.InspectionDetailRespVO
     */
    InspectionDetailRespVO selectInspectionDetail(String code);
    /**
     * 查需要验货的sku数据
     * @author NullPointException
     * @date 2019/6/24
     * @param code 退货单编码
     * @param orderCode 订单编码
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderInfoInspectionItemRespVO>
     */
    List<ReturnOrderInfoInspectionItemRespVO> selectInspectionItemList(@Param("code") String code, @Param("orderCode") String orderCode);
    /**
     * TODO
     * @author NullPointException
     * @date 2019/6/24
     * @param code
     * @return com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.InspectionViewRespVO
     */
    InspectionViewRespVO selectInspectionView(String code);
    /**
     * 根据编码查询
     * @author NullPointException
     * @date 2019/6/27
     * @param returnOrderCode
     * @return com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO
     */
    ReturnOrderInfoDTO selectByCode(String returnOrderCode);
    /**
     * 查入库信息
     * @author NullPointException
     * @date 2019/7/3
     * @param code
     * @return java.util.List<com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderInfoApplyInboundRespVO>
     */
    List<ReturnOrderInfoApplyInboundRespVO> selectInbound(String code);
    /**
     * 根据编码查
     * @author NullPointException
     * @date 2019/7/4
     * @param orderCode
     * @return com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo
     */
    ReturnOrderInfo selectByCode1(String orderCode);
    /**
     * 通过编码全量更新
     * @author NullPointException
     * @date 2019/7/4
     * @param order
     * @return int
     */
    int updateByOrderCode(ReturnOrderInfo order);
    /**
     * 更新退货单
     * @author NullPointException
     * @date 2019/7/8
     * @param returnOrderInfo
     * @return int
     */
    int updateByReturnOrderCodeSelective(ReturnOrderInfo returnOrderInfo);
}