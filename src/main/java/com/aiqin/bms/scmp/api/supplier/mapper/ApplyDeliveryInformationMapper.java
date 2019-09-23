package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyDeliveryInformation;

import java.util.List;

public interface ApplyDeliveryInformationMapper {

    ApplyDeliveryInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyDeliveryInformation record);

    int updateByPrimaryKey(ApplyDeliveryInformation record);

    int insertBatch(List<ApplyDeliveryInformation> list);

    /**
     * 根据申请编码查数据
     * @param applySupplyCompanyCode
     * @return
     */
    List<ApplyDeliveryInformation> selectByApplyCode(String applySupplyCompanyCode);

    int deleteByApplyCode(String applySupplyCompanyCode);
}