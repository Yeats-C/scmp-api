package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuConfigSpareWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuConfigSpareWarehouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuConfigSpareWarehouse record);

    int insertSelective(ApplyProductSkuConfigSpareWarehouse record);

    ApplyProductSkuConfigSpareWarehouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuConfigSpareWarehouse record);

    int updateByPrimaryKey(ApplyProductSkuConfigSpareWarehouse record);

    int insertBatch(@Param("list") List<ApplyProductSkuConfigSpareWarehouse> draftList, @Param("applyCode") String applyCode);

    List<ApplyProductSkuConfigSpareWarehouse> selectByApplyCode(String applyCode);
}