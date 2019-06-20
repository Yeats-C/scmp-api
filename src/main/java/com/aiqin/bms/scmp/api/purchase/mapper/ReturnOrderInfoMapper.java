package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnInspectionReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.returngoods.QueryReturnOrderManagementReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.InspectionDetailRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.QueryReturnInspectionRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.returngoods.ReturnOrderDetailRespVO;

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
    List<QueryReturnOrderManagementReqVO> selectReturnOrderManagementList(QueryReturnOrderManagementReqVO reqVO);
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
}