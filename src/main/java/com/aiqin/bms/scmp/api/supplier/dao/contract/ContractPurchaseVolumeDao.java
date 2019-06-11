package com.aiqin.bms.scmp.api.supplier.dao.contract;


import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractPurchaseVolumeDTO;

import java.util.List;

public interface ContractPurchaseVolumeDao {

    /**
     * 批量插入
     * @param purchaselists
     * @return
     */
    int insertContractPurchaseVolume(List<ContractPurchaseVolumeDTO> purchaselists);

    /**
     * 通过申请合同关联编码查找申请合同进货额
     * @param contractCode
     * @return
     */
    List<ContractPurchaseVolumeDTO> selectByContractPurchaseVolume(String contractCode);

    /**
     * 通过关联编码去删除申请合同进货额
     * @param contractCode
     * @return
     */
    int deleteByPrimaryKey(String contractCode);
}