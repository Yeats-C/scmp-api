package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractPlanType;

import java.util.List;

public interface ApplyContractPlanTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyContractPlanType record);

    int insertSelective(ApplyContractPlanType record);

    ApplyContractPlanType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyContractPlanType record);

    int updateByPrimaryKey(ApplyContractPlanType record);
    /**
     * 批量保存
     * @author NullPointException
     * @date 2019/7/1
     * @param typeList
     * @return int
     */
    int insertBatch(List<ApplyContractPlanType> typeList);
    /**
     * 根据合同编码删除
     * @author NullPointException
     * @date 2019/7/1
     * @param applyContractCode
     * @return int
     */
    int deleteByContractCode(String applyContractCode);
    /**
     * 查询列表
     * @author NullPointException
     * @date 2019/7/1
     * @param applyContractCode
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractPlanType>
     */
    List<ApplyContractPlanType> selectByApplyContratCode(String applyContractCode);
}