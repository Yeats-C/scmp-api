package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplySupplyCompanyMapper {

    ApplySupplyCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySupplyCompany record);

    int updateByPrimaryKey(ApplySupplyCompany record);

    ApplySupplyCompany selectByCode(String applySupplyCode);

    int insert(ApplySupplyCompany applySupplyCompany);

    int delectById(Long id);

    /**
     * 查询审批中的数据
     * @param companyCodeList
     * @return
     */
    List<ApplySupplyCompany> selectBySupplyCode(@Param("list") List<String> companyCodeList, @Param("companyCode") String companyCode,@Param("applyStatus")Byte applyStatus);

    /**
     * 通过id删除
     * @param collect
     * @return
     */
    int delectByIds(List<Long> collect);
}