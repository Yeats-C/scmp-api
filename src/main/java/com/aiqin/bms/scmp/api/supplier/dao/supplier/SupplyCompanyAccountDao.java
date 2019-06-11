package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.QuerySupplierComAcctRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应单位账号ExtDao
 * @author zth
 * @date 2018/12/7
 */
public interface SupplyCompanyAccountDao {
    /**
     * 供应单位账号列表+搜索
     * @author zth
     * @date 2018/12/7
     * @param vo
     * @return java.util.List<com.aiqin.mgs.scmp.api.domain.response.scmp.QuerySupplierComAcctRespVo>
     */
    List<QuerySupplierComAcctRespVo> selectListByQueryVO(QuerySupplierComAcctReqVo vo);
    /**
     * 通过条件更新数据
     * @author zth
     * @date 2018/12/15
     * @param account 数据
     * @param entity 条件
     * @return int
     */
    int updateByEntitySelective(@Param("account") SupplyCompanyAccount account, @Param("entity") SupplyCompanyAccount entity);

    /**
     * 根据申请编码获取供货单位账号信息
     * @param applyCode
     * @return
     */
    SupplyCompanyAccount getSupplyCompanyAccount(String applyCode);
}