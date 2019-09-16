package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.abutment.domain.request.SapSupplier;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplyCompanyDetailDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComListRespVO;
import org.apache.ibatis.annotations.MapKey;
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
     * 根据ID获取供货单位及结算,收货信息
     * @param id
     * @return
     */
    SupplyCompanyDetailDTO getApplySupplyComDetail(Long id);

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

    /**
     * 根据供应商名称查询供应商信息 (退供导入使用)
     */
    SupplyCompany selectBySupplierName(String supplyName);
    /**
     * 根据供应商CODE查询供应商信息 (DL销售回调使用)
     */
    SupplyCompany selectBySupplierCode(String supplyCode);
    /**
     * 根据名称查询
     * @author NullPointException
     * @date 2019/7/16
     * @param companyNameList
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany>
     */
    @MapKey("supplyName")
    Map<String,SupplyCompany> selectByCompanyNameList(@Param("list") List<String> companyNameList, @Param("companyCode") String companyCode);

    @MapKey("supplyName")
    Map<String,SupplyCompany> selectOfficialByCompanyNameList(@Param("list") List<String> companyNameList, @Param("companyCode") String companyCode);

    @MapKey("supplyCode")
    Map<String, SupplyCompany> selectByCompanyCodeList(@Param("list") List<String> companyCodeList, @Param("companyCode") String companyCode);

    SupplyCompany selectAddress(String supplyCode);

    /**
     * 更新申请编码
     * @param supplyCompanyCode
     * @param applySupplyCompanyCode
     * @return
     */
    int updateApplyCode(@Param("supplyCompanyCode") String supplyCompanyCode, @Param("applySupplyCompanyCode") String applySupplyCompanyCode);

    List<SupplyCompany> listForSap(SapOrderRequest sapOrderRequest);
}
