package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractFile;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractPurchaseVolumeDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.QueryApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.UpdateApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.*;

import java.util.List;

/**
 * @description:
 * @author:曾兴旺
 * @date: 2018/11/30
 */
public interface ApplyContractService{

    /**
     * 查询合同申请List
     * @param vo
     * @return
     */
   BasePage<QueryApplyContractResVo> findApplyContractList(QueryApplyContractReqVo vo);

    /**
     * 新增申请合同转化实体
     * @param applyContractReqVo
     * @return
     */
    int saveApplyContract(ApplyContractReqVo applyContractReqVo);
    /**
     * 查看合同申请详情（新增）
     * @param id
     * @return
     */
    ApplyContractViewResVo findApplyContractDetail(Long id);

    /**
     * 通过编码获取修改合同详情
     * @param applyContractCode
     * @return
     */
    ApplyContractUpdateResVo findUpdateContractDetail(String applyContractCode);



    /**
     * 修改申请合同
     * @param updateApplyContractReqVo
     * @return
     */
     int updateApplyContract(UpdateApplyContractReqVo updateApplyContractReqVo);

    /**
     * 撤销
     * @param id
     * @return
     */
    int updateStatusApplyContract(Long id);
//
//    /**
//     * 逻辑删除
//     * @param
//     * @return
//     */
//    int delectApplyContract(Long id);
    /**
     * 保存申请合同主体
     * @param applyContractDTO
     * @return
     */
    Long  insertApplyContractDetails(ApplyContractDTO applyContractDTO);
    /**
     * 批量保存进货额
     * @param applyContractPurchaseVolumes
     * @return
     */
    int saveList(List<ApplyContractPurchaseVolumeDTO> applyContractPurchaseVolumes);


     /**
      * 批量保存进货额
      * @param files
      * @return
      */
     int saveFileList(List<ApplyContractFile> files);

    /**
     * 跟新申请合同主体
     * @param applyContractDTO
     * @return
     */
     int  updateApplyContractDetails(ApplyContractDTO applyContractDTO);

    /**
     * 根据关联编码删除进货额
     * @param applyContractCode
     * @return
     */
     int deleteByPrimaryKey(String applyContractCode);



     /**
      * 根据关联编码删除文件信息
      * @param applyContractCode
      * @return
      */
     int deleteFiles(String applyContractCode);

   /**
    * 审批流接口
    * @param id
    */
   void workFlow(Long id) ;

 /**
  * 查询供货单位账户列表
  * @param querySupplierReqVO
  * @return
  */
 List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);


}
