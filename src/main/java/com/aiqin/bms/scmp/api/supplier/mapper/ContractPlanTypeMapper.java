package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ContractPlanType;

import java.util.List;

public interface ContractPlanTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractPlanType record);

    int insertSelective(ContractPlanType record);

    ContractPlanType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractPlanType record);

    int updateByPrimaryKey(ContractPlanType record);
    /**
     * 批量插入
     * @author NullPointException
     * @date 2019/7/1
     * @param typeList
     * @return int
     */
    int insertBatch(List<ContractPlanType> typeList);

    int deletePlanTypeList(String contractCode);
}