package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyDeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplyDeliveryDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplyDeliveryInfoReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplyDeliveryInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplyDeliveryVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

import java.util.List;

/**
 * @功能说明: 供货单位收货信息申请
 * @author: wangxu
 * @date: 2018/12/4 0004 10:05
 */
public interface ApplyDeliveryService {
    /**
     * 新增申请收货信息
     * @param applyDeliveryInfoReq
     * @return
     */
    Long saveApply(ApplyDeliveryInfoReqVO applyDeliveryInfoReq);

    /**
     * 复制对象,注入参数
     * @param applyDeliveryVO
     * @return
     */
    int updateApply(ApplyDeliveryVO applyDeliveryVO);

    /**
     * 撤销赋值等
     * @param id
     * @return
     */
    int cancelApply(Long id);
    /**
     * 调用dao层修改数据申请收货信息
     * @param applyDeliveryInformation
     * @return
     */
    int update(ApplyDeliveryInformation applyDeliveryInformation);
    /**
     * 调用dao层插入数据申请收货信息
     * @param applyDeliveryInfoReqDTO
     * @return
     */
    Long insert(ApplyDeliveryInfoReqDTO applyDeliveryInfoReqDTO);

    /**
     * 非前端请求新增
     * @param applyDeliveryDTO
     * @return
     */
    Long insideSaveApply(ApplyDeliveryDTO applyDeliveryDTO);


    /**
     * 非前端请求新增
     * @param deliveryDTOList
     * @return
     */
    Long  insideSaveBatchApply(List<ApplyDeliveryDTO> deliveryDTOList);

    /**
     * 发货信息审批流
     * @param applyDeliveryInformations
     * @param workFlowCallbackVO
     * @return
     */
    String insideWorkFlowCallback(List<ApplyDeliveryInformation> applyDeliveryInformations, WorkFlowCallbackVO workFlowCallbackVO);
}
