package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.DeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.DeliveryInfoRespVO;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/23 0023 17:54
 */
public interface DeliveryInfoDao {
    /**
     * 根据申请编码查询发货正式数据
     * @param applyCode
     * @return
     */
    List<DeliveryInformation> getDeliveryInfoByApplyCode(String applyCode);

    int deleteDeliveryInfoByApplyCode(String applyCode);

    int insertBatch(List<DeliveryInformation> deliveryInformations);


    List<DeliveryInfoRespVO> getDeliveryInfoBySupplyCompanyCode(String supplyCompanyCode);

    int deleteDeliveryInfoBySupplyCompanyCode(String SupplyCompanyCode);
}
