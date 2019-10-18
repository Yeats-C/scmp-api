package com.aiqin.bms.scmp.api.supplier.dao.contract;

import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractByUsernameReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.QueryContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractPurchaseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.QueryContractResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryCodeResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ContractDao {

    /**
     * 查询合同List  分页
     * @param vo
     * @return
     */
    List<QueryContractResVo> selectBySelectContract(QueryContractReqVo vo);

    /**
     * 插入合同实体
     * @param record
     * @return
     */
    int insertSelective(ContractDTO record);

    /**
     * 根据id去查询合同实体
     * @param id
     * @return
     */
    ContractDTO  selectByPrimaryKey(Long id);

    /**
     * 根据关联编码去查询
     * @param applyContractCode
     * @return
     */
    ContractDTO  selectByApplyCode(String applyContractCode);

    /**
     * 有选择的更新合同实体
     * @param contractDTO
     * @return
     */
    int updateByPrimaryKeySelective(ContractDTO contractDTO);

    /**
     * 无选择的更新合同
     * @param contractDTO
     * @return
     */
    int updateByPrimaryKey(ContractDTO contractDTO);


    /**
     * 根据公司，登陆人查询合同
     * @param reqVO
     * @return
     */
    List<ContractDTO> getContractByUsername(ContractByUsernameReqVo reqVO);

    /**
     *
     * @param applyContractCode
     * @param applyStatus
     * @return
     */

    int updateByCode(@Param("applyContractCode") String applyContractCode, @Param("applyStatus") byte applyStatus,@Param("newApplyCode") String newApplyCode);


    List<ContractPurchaseResVo> getContractByMap(Map<String,String> map);

    DictionaryCodeResVo getSettlementMethodByContractCode(String contractCode);
}