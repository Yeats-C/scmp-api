package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.QuerySupplierComAcctRespVo;

import java.util.List;

public interface ApplySupplyCompanyAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplySupplyCompanyAccount record);

    int insertSelective(ApplySupplyCompanyAccount record);

    ApplySupplyCompanyAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySupplyCompanyAccount record);

    int updateByPrimaryKey(ApplySupplyCompanyAccount record);
    /**
     * 通过审批流单号查找申请供货单位账户数据
     * @author zth
     * @date 2019/1/18
     * @param formNo
     * @return com.aiqin.mgs.scmp.api.domain.pojo.ApplySupplyCompanyAccount
     */
    ApplySupplyCompanyAccount selectByFormNO(String formNo);

    List<QuerySupplierComAcctRespVo> selectListByQueryVO(QuerySupplierComAcctReqVo vo);
}