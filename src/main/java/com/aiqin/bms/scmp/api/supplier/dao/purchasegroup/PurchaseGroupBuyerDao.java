package com.aiqin.bms.scmp.api.supplier.dao.purchasegroup;

import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupBuyerDTO;

import java.util.List;

public interface PurchaseGroupBuyerDao {


    /**
     * 通过采购组编码查询所有的采购组人员
     * @param purchaseGroupCode
     * @return
     */
    List<PurchaseGroupBuyerDTO> enableByPurchaseCode(String purchaseGroupCode);

    /**
     * 批量保存采购组人员
     * @param purchaseGroupBuyerDTOS
     * @return
     */
    int saveList(List<PurchaseGroupBuyerDTO> purchaseGroupBuyerDTOS);
    /**
     * 通过采购组编码查询未禁用的采购组人员
     * @param purchaseGroupCode
     * @return
     */
    List<PurchaseGroupBuyerDTO> selectByPurchaseCode(String purchaseGroupCode);


    /**
     * 批量更新采购组
     * @param purchaseGroupBuyerDTOS
     * @return
     */
    int  updateList(List<PurchaseGroupBuyerDTO> purchaseGroupBuyerDTOS);
//    int deleteByPrimaryKey(Long id);
//
//    /**
//     * 插入一条记录
//     * @param record
//     * @return
//     */
//    int insert(PurchaseGroupBuyerDTO record);
//
//    int insertSelective(PurchaseGroupBuyerDTO record);
//
//    /**
//     * 根据id去查询一条记录
//     * @param id
//     * @return
//     */
//    PurchaseGroupBuyerDTO selectByPrimaryKey(Long id);
//
//    int updateByPrimaryKeySelective(PurchaseGroupBuyerDTO record);
//
//    int updateByPrimaryKey(PurchaseGroupBuyerDTO record);
}