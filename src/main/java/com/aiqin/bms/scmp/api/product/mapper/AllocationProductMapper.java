package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationProductResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllocationProductMapper {

    int deleteByPrimaryKey(Long id);

    /**
     * 批量插入
     * @param record
     * @return
     */
    int saveList(List<AllocationProduct> record);

    int insertSelective(AllocationProduct record);


    /**
     * 通过调拨单编码查看sku
     * @param allocationCode
     * @return
     */
   List<AllocationProductResVo>  selectByAllocationCode(String allocationCode);


    int updateByPrimaryKeySelective(AllocationProduct record);

    int updateByPrimaryKey(AllocationProduct record);

    AllocationProductResVo selectQuantityBySkuCodeAndSource(@Param("allocationCode") String sourceOderCode,
                                                            @Param("skuCode") String skuCode,
                                                            @Param("lineCode") Integer lineCode);

    int updateQuantityBySkuCodeAndSource(AllocationProduct allocationProduct);

    int updateQuantityBySkuCodeAndSourceIn(AllocationProduct allocationProduct);

    AllocationProductResVo selectAllocationProductByLineCode(@Param("allocationCode") String allocationCode, @Param("lineCode") Integer lineCode);
}

