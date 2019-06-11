package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplier;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplierDetailDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplierReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplierListRespVO;

import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 15:30
 */
public interface ApplySupplierDao {
    Long insertApply(ApplySupplierReqDTO applySupplierReqDTO);

    List<ApplySupplierListRespVO> getApplyList(QueryApplySupplierReqVO queryApplySupplierReqVO);

    /**
     *
     * @param applyCode
     * @return
     */
    ApplySupplierDetailDTO getApplySupplierDetail(String applyCode);

    /**
     *
     * @param applySupplierReqDTO
     * @return
     */
    int updateApply(ApplySupplierReqDTO applySupplierReqDTO);

    /**
     * 根据供应商code获取供应商申请信息
     * @param supplierCode
     * @return
     */
    ApplySupplier getApplySupplierBySupplierCode(String supplierCode);

    /**
     * 根据编码名称校验是否重复
     * @param map
     * @return
     */
    int checkName(Map<String, Object> map);

    /**
     * 根据审核表单编码查询申请记录
     * @param formNo
     * @return
     */
    ApplySupplier selectByFormNO(String formNo);

}
