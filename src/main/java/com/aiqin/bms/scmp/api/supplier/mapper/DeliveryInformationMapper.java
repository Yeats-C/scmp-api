package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyDeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.DeliveryInformation;

import java.util.List;

public interface DeliveryInformationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeliveryInformation record);

    int insertSelective(DeliveryInformation record);

    DeliveryInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeliveryInformation record);

    int updateByPrimaryKey(DeliveryInformation record);

    List<ApplyDeliveryInformation> selectByCode(String oldCode);
}