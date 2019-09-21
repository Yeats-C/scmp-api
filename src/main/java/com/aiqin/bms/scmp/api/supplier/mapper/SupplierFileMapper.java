package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierFileReqVO;

import java.util.List;

public interface SupplierFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierFile record);

    int insertSelective(SupplierFile record);

    SupplierFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierFile record);

    int updateByPrimaryKey(SupplierFile record);

    List<SupplierFileReqVO> selectByCode(String oldCode);
}