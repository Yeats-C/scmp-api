package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplyCompanyAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplyCompanyAccount record);

    int insertSelective(SupplyCompanyAccount record);

    SupplyCompanyAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplyCompanyAccount record);

    int updateByPrimaryKey(SupplyCompanyAccount record);
    /**
     * 通过供货单位编码查询供货单位账户信息
     * @author zth
     * @date 2019/1/14
     * @param codes
     * @return com.aiqin.mgs.scmp.api.domain.pojo.SupplyCompanyAccount
     */
    List<SupplyCompanyAccount> selectByCode(@Param("codes") List<String> codes);
    /**
     * 通过申请编码查询正式数据
     * @author zth
     * @date 2019/1/24
     * @param applyCompanyAccountCode
     * @return com.aiqin.mgs.scmp.api.domain.pojo.SupplyCompanyAccount
     */
    SupplyCompanyAccount selectByApplyCode(@Param("applyCompanyAccountCode") String applyCompanyAccountCode);

    List<SupplyCompanyAccount> listForSap(SapOrderRequest sapOrderRequest);
}