package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyDeliveryInformation;

public interface ApplyDeliveryInformationMapper {

    ApplyDeliveryInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyDeliveryInformation record);

    int updateByPrimaryKey(ApplyDeliveryInformation record);
}