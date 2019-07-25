package com.aiqin.bms.scmp.api.supplier.service;


import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Manufacturer;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ManufacturerBrand;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.ManufacturerReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.ManufacturerUpdateReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.QueryManufacturerReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer.ManufacturerResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer.QueryManufacturerResVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述: 制造商service
 *
 * @Author: Kt.w
 * @Date: 2018/12/11
 * @Version 1.0
 * @since 1.0
 */
public interface ManufacturerService {

    /**
     * 制造商列表查询
     * @param vo
     * @return
     */
    BasePage<QueryManufacturerResVo> list(QueryManufacturerReqVo vo);

    /**
     * 制造商新增转化实体
     * @param vo
     * @return
     */
    HttpResponse<Integer> save(ManufacturerReqVo vo);

    /**
     * 查询制造商
     * @param id
     * @return
     */
    ManufacturerResVo  view(Long id);

    /**
     * 制造商修改转化实体
     * @param vo
     * @return
     */
    HttpResponse<Integer> update(ManufacturerUpdateReqVo vo);

    /**
     * 保存制造商主体
     * @param record
     * @return
     */
    int insert(Manufacturer record);

    /**
     * 批量插入制造商关联品牌
     * @return
     */
    int  saveList(List<ManufacturerBrand> list);

    /**
     * 修改制造商主体
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Manufacturer record);

    /**
     *批量更新制造商关联品牌
     * @param list
     * @return
     */
    int updateList(List<ManufacturerBrand> list);


    int  enable(String manufacturerCode, byte enable);

    /**
     * 通过名称查询
     * @param manufactureList
     * @param companyCode
     * @return
     */
    Map<String, Manufacturer> selectByManufactureNames(Set<String> manufactureList, String companyCode);
}
