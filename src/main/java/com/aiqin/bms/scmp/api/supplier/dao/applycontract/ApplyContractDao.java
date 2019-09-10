package com.aiqin.bms.scmp.api.supplier.dao.applycontract;

import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.dto.ApplyContractDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.QueryApplyContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.QueryApplyContractResVo;

import java.util.List;

public interface ApplyContractDao {

    /**
     * 查询合同List  分页
     * @param vo
     * @return
     */
    List<QueryApplyContractResVo> selectBySelectApplyContract(QueryApplyContractReqVo vo);

    /**
     * 新增申请合同主体
     * @param applyContractDTO
     * @return
     */
    Long  insert(ApplyContractDTO applyContractDTO);


    /**
     * 根据主键去获取申请合同详情
     * @param id
     * @return
     */
      ApplyContractDTO selectByPrimaryKey(Long id);


    /**
     * 根据编码去获取申请合同详情
     * @param applyContractCode
     * @return
     */
     ApplyContractDTO selectApplyContractByApplyContractCode(String applyContractCode);

    /**
     * 有选择的更新
     * @param applyContractDTO
     * @return
     */
    int updateByPrimaryKeySelective(ApplyContractDTO applyContractDTO);

    /**
     * 无选择的更新
     * @param applyContractDTO
     * @return
     */
    int updateByPrimaryKey(ApplyContractDTO applyContractDTO);


    /**
     * 根据流程变成查询申请合同
     * @param FormNo
     * @return
     */
    ApplyContractDTO selectByFormNO(String FormNo);


    List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);
}