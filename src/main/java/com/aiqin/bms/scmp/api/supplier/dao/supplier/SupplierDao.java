package com.aiqin.bms.scmp.api.supplier.dao.supplier;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.Supplier;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplierListRespVO;

import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 15:31
 */
public interface SupplierDao {
    /**
     * 获取供应商列表
     * @param querySupplierReqVO
     * @return
     */
    List<SupplierListRespVO> getSupplierList(QuerySupplierReqVO querySupplierReqVO);

    /**
     * 根据编码名称校验是否重复
     * @param map
     * @return
     */
    int checkName(Map<String, Object> map);

    /**
     * 根据申请编码获取供应商正式数据
     * @param applyCode
     * @return
     */
    Supplier getSupplierByApplyCode(String applyCode);

    void updatetSupplierApplyStatusByCode(String supplierCode);

    List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);

}
