package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.DeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.DeliveryInfoRespVO;

import java.util.List;

/**
 * @功能说明:供货单位下发货信息service
 * @author: wangxu
 * @date: 2018/12/15 0015 15:15
 */
public interface DeliveryInfoService {
    /**
     * 调用dao层执行新增
     * @param deliveryInformation
     * @return
     */
    int insert(DeliveryInformation deliveryInformation);

    /**
     * 调用dao层执行修改
     * @param deliveryInformation
     * @return
     */
    int update(DeliveryInformation deliveryInformation);

    /**
     * 根据供应商编码查询发货/退货信息
     * @param supplyCompanyCode
     * @return
     */
    List<DeliveryInfoRespVO> getDeliveryInfoBySupplyCompanyCode(String supplyCompanyCode);

    List<DeliveryInfoRespVO> getDeliveryInfoByApplyCompanyCode(String supplyCompanyCode);
}
