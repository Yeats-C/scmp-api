package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface SupplyCompanyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplyCompany record);

    int insertSelective(SupplyCompany record);

    SupplyCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplyCompany record);

    int updateByPrimaryKey(SupplyCompany record);

    int updateStarScore(@Param("supplierCode") String supplierCode, @Param("startScore") BigDecimal startScore);
}