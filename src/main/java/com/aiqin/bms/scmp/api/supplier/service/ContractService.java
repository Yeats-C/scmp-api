package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ContractPlanType;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractPurchaseVolumeDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractByUsernameReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.QueryContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractPurchaseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.QueryContractResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryCodeResVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @description:
 * @author:曾兴旺
 * @date: 2018/11/30
 */
public interface ContractService {

    /**
     * 查询合同List
     * @param vo
     * @return
     */
   BasePage<QueryContractResVo> findContractList(QueryContractReqVo vo);

    /**
     * 新增合同
     * @param
     * @return
     */
    int saveContract(ContractReqVo contractReqVo);

 void savePlanTypeList(List<ContractPlanType> typeList);

 /**
     * 查看合同详情
     * @param id
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    ContractResVo findContractDetail(Long id) throws InvocationTargetException, IllegalAccessException;

    /**
     * 修改合同
     * @param contractReqVo
     * @return
     */
    int updateContract(ContractReqVo contractReqVo);


     int  deleteContract(Long id);
    /**
     * 保存合同主体
     * @param contractDTO
     * @return
     */
    int saveContractDetails(ContractDTO contractDTO);
    /**
     * 批量保存进货额
     * @param purchaseLists
     * @return
     */
    int saveList(List<ContractPurchaseVolumeDTO> purchaseLists);


    /**
     * 有选择的更新实体
     * @param contractDTO
     * @return
     */
    int updateByPrimaryKeySelective(ContractDTO contractDTO);

    /**
     * 无选择的修改合同主体
     * @param contractDTO
     * @return
     */
    int updateContractDetails(ContractDTO contractDTO);
    /**
     * 批量删除进货额
     * @param contractCode
     * @return
     */
    int deleteList(String contractCode);


   /**
    * 根据登陆人查询合同
    * @param reqVO
    * @return
    */
   List<ContractResVo>  getContractByUsername(ContractByUsernameReqVo reqVO);
   /**
    * 根据 编码删除
    * @author NullPointException
    * @date 2019/7/1
    * @param contractCode
    * @return void
    */
   void deletePlanTypeList(String contractCode);

    /**
     *
     * 功能描述: 限定采购组
     *
     * @return
     * @auther knight.xie
     * @date 2019/7/1 20:16
     */
    List<ContractPurchaseResVo> getContractByPurchaseGroup();


    /**
     *
     * 功能描述: 根据供应商查询合同
     *
     * @param supplierCode
     * @return
     * @auther knight.xie
     * @date 2019/8/3 12:11
     */
    List<ContractPurchaseResVo> getContractBySupplierCode(String supplierCode);


    /**
     *
     * 功能描述: 根据合同编码查询结算方式
     *
     * @param contractCode
     * @return 
     * @auther knight.xie
     * @date 2019/10/18 14:24
     */
    DictionaryCodeResVo getSettlementMethodByContractCode(String contractCode);
}
