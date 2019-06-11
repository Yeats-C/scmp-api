package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplyCompanyAcctReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.QueryApplySupplierComAcctRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 16:35
 */
public interface ApplySupplyCompanyAcctDao {

    Long insertApply(ApplySupplyCompanyAcctReqDTO applySupplyCompanyAcctReqDTO);
    Long insertApplyOut(ApplySupplyCompanyAcctReqDTO applySupplyCompanyAcctReqDTO);
    /**
     * 供货单位账户列表条件查询
     * @author zth
     * @date 2018/12/7
     * @param vo
     * @return java.util.List<com.aiqin.mgs.scmp.api.domain.response.scmp.QueryApplySupplierRespVO>
     */
    List<QueryApplySupplierComAcctRespVo> selectListByQueryVO(QueryApplySupplierComAcctReqVo vo);

    int updateAndCode(@Param("name") String name, @Param("code") String code);
    /**
     * 通过条件更新实体
     * @author zth
     * @date 2018/12/15
     * @param account 要更新的实体
     * @param entity 条件
     * @return int
     */
    int updateByEntitySelective(@Param("account") ApplySupplyCompanyAccount account, @Param("entity") ApplySupplyCompanyAccount entity);
    /**
     * 通过条件查询实体
     * @author zth
     * @date 2018/12/15
     * @param entity 传入的条件
     * @return java.util.List<com.aiqin.mgs.scmp.api.domain.pojo.ApplySupplyCompanyAccount>
     */
    List<ApplySupplyCompanyAccount> selectByPojo(ApplySupplyCompanyAccount entity);

    /**
     * 根据申请供货单位编码查询申请供货单位账号信息
     * @param applySupplyComCode
     * @return
     */
    ApplySupplyCompanyAccount getApplySupplyComAcct(String applySupplyComCode);

    List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);
}
