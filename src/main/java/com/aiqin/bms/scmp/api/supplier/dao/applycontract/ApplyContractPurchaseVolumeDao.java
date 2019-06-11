package com.aiqin.bms.scmp.api.supplier.dao.applycontract;

import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractPurchaseVolumeDTO;

import java.util.List;

public interface ApplyContractPurchaseVolumeDao {


    /**
     * 批量插入
     * @param purchaselists
     * @return
     */
    int insertApplyContractPurchaseVolume(List<ApplyContractPurchaseVolumeDTO> purchaselists);

    /**
     * 通过申请合同关联编码查找申请合同进货额
     * @param applyContactCode
     * @return
     */
    List<ApplyContractPurchaseVolumeDTO> selectByApplyContractPurchaseVolume(String applyContactCode);

    /**
     * 通过关联编码去删除申请合同进货额
     * @param applyContractCode
     * @return
     */
   int deleteByPrimaryKey(String applyContractCode);
}