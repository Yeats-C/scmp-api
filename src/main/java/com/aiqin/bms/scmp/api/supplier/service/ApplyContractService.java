package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.excel.ContractImportResp;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.*;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractPurchaseVolumeDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.QueryApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.UpdateApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.ApplyContractUpdateResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.ApplyContractViewResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.QueryApplyContractResVo;
import org.springframework.web.multipart.MultipartFile;

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

    void savePlanTypeList(List<ApplyContractPlanType> typeList);

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
      * 批量保存文件信息
      * @param files
      * @return
      */
     int saveFileList(List<ApplyContractFile> files);

     /**
      * 批量保存采购组
      * @param contractPurchaseGroups
      * @return
      */
     int savePurchaseGroupList(List<ApplyContractPurchaseGroup> contractPurchaseGroups);


    /**
     * 批量保存品牌
     * @param contractBrands
     * @return
     */
    int saveBrandList(List<ApplyContractBrand> contractBrands);


    /**
     * 批量保存品类
     * @param contractCategories
     * @return
     */
    int saveCategoryList(List<ApplyContractCategory> contractCategories);

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
     * 根据关联编码删除采购组
     * @param applyContractCode
     * @return
     */
    int deletePurchaseGroups(String applyContractCode);

    /**
     * 根据关联编码删除品牌
     * @param applyContractCode
     * @return
     */
    int deleteBrands(String applyContractCode);

    /**
     * 根据关联编码删除品类
     * @param applyContractCode
     * @return
     */
    int deleteCategories(String applyContractCode);

   /**
    * 审批流接口
    * @param id
    */
   void workFlow(Long id, String positionCode) ;

 /**
  * 查询供货单位账户列表
  * @param querySupplierReqVO
  * @return
  */
 List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);

    /**
     *
     * 功能描述: 返回详情接口请求参数
     *
     * @param formNo
     * @return
     * @auther knight.xie
     * @date 2019/8/9 14:41
     */
    DetailRequestRespVo getInfoByForm(String formNo);

    /**
     *
     * 功能描述: 导入处理
     *
     * @param file
     * @return
     * @auther knight.xie
     * @date 2019/9/2 15:50
     */
    ContractImportResp dealImport(MultipartFile file);

    /**
     *
     * 功能描述: 保存
     *
     * @param req
     * @return
     * @auther knight.xie
     * @date 2019/9/3 19:31
     */
    void saveImportData(ContractImportResp req);

    Boolean saveUpdateApply(UpdateApplyContractReqVo updateApplyContractReqVo);

    /**
     * 通过申请编码更新数据
     * @param applyContractDTO
     * @return
     */
    Integer updateByApplyId(ApplyContractDTO applyContractDTO);
}
