package com.aiqin.bms.scmp.api.supplier.dao.manufacturer;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.ManufacturerBrand;

import java.util.List;

public interface ManufacturerBrandDao {

    /**
     * 批量插入制造商关联品牌
     * @return
     */
    int  saveList(List<ManufacturerBrand> list);

    int deleteByPrimaryKey(Long id);

    int insert(ManufacturerBrand record);

    int insertSelective(ManufacturerBrand record);

   List<ManufacturerBrand>  selectByPrimaryKey(String manufacturerCode);

    /**
     *批量更新制造商关联品牌
     * @param list
     * @return
     */
    int updateByPrimaryKeySelective(List<ManufacturerBrand> list);

    int updateByPrimaryKey(ManufacturerBrand record);


}