package com.aiqin.bms.scmp.api.supplier.dao.manufacturer;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.Manufacturer;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.QueryManufacturerReqVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ManufacturerDao {

    /**
     * 列表展示以及搜索
     * @param vo
     * @return
     */
    List<Manufacturer> list(QueryManufacturerReqVo vo);

    int deleteByPrimaryKey(Long id);

    /**
     * 保存制造商主体
     * @param record
     * @return
     */
    int insert(Manufacturer record);

    int insertSelective(Manufacturer record);

    Manufacturer selectByPrimaryKey(Long id);

    /**
     * 有选择的更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Manufacturer record);

    int updateByPrimaryKey(Manufacturer record);

    int  enable(@Param("manufacturerCode") String manufacturerCode, @Param("enable") byte enable);

    /**
     * 验证名字是否重复
     * @param name
     * @param id
     * @param companyCode
     * @return
     */
    Integer checkName(@Param("name") String name, @Param("id") Long id, @Param("companyCode") String companyCode);
    @MapKey("name")
    Map<String, Manufacturer> selectByManufactureNames(@Param("list") Set<String> manufactureList, @Param("companyCode") String companyCode);
}