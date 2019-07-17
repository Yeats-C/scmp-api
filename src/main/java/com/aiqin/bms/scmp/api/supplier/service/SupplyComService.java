package com.aiqin.bms.scmp.api.supplier.service;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailByCodeRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComListRespVO;

import java.math.BigDecimal;
import java.util.List;


/**
 * @功能说明:供货单位Service
 * @author: wangxu
 * @date: 2018/12/1 0001 15:44
 */
public interface SupplyComService {
    /**
     * 供货单位分页查询
     * @param querySupplyComReqVO
     * @return
     */
    BasePage<SupplyComListRespVO> getSupplyCompanyInfoList(QuerySupplyComReqVO querySupplyComReqVO);

    /**
     * 供货单位列表查询
     * @return
     */
    List<SupplyComListRespVO> getAllSupplyComList(String name);
    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int logicDelete(Long id);

    /**
     * 调用dao层执行修改
     * @param supplyCompany
     * @return
     */
    int update(SupplyCompany supplyCompany);

    /**
     * 调用dao层执行新增
     * @param supplyCompany
     * @return
     */
    int insert(SupplyCompany supplyCompany);
    /**
     * 获取供货单位详情
     * @param id
     * @return
     */
    SupplyComDetailRespVO getSupplyComDetail(Long id, StatusTypeCode statusTypeCode);

    /**
     * 通过编码获取供货单位详情
     * @param supplyCode
     * @return
     */
    SupplyComDetailByCodeRespVO detailByCode(String supplyCode);

    /**
     * 更新评分
     * @param supplierCode
     * @param starScore
     * @return
     */
    Integer updateStarScore(String supplierCode, BigDecimal starScore);
}
