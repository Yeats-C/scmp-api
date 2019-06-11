package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplyCompanyDetailDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComListRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 15:41
 */
public interface SupplyCompanyDao {
    /**
     * 分页查询供货单位列表
     * @param querySupplyComReqVO
     * @return
     */
    List<SupplyComListRespVO> getSupplyCompanyList(QuerySupplyComReqVO querySupplyComReqVO);

    /**
     * 根据ID获取供货单位及结算,收货信息
     * @param id
     * @return
     */
    SupplyCompanyDetailDTO getSupplyComDetail(Long id);

    /**
     * 根据编码名称校验是否重复
     * @param map
     * @return
     */
    int checkName(Map<String, Object> map);

    /**
     * 根据申请编码获取正式数据
     * @param applyCode
     * @return
     */
    SupplyCompany getSupplyComByApplyCode(String applyCode);


    /**
     * 根据编码获取正式数据
     * @param supplyCode
     * @return
     */
    SupplyCompany detailByCode(@Param("supplyCode") String supplyCode, @Param("companyCode") String companyCode);

}
