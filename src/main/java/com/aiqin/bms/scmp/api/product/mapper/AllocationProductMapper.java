package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductToOutboundVo;

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
   List<AllocationProduct>  selectByAllocationCode(String allocationCode);


    int updateByPrimaryKeySelective(AllocationProduct record);

    int updateByPrimaryKey(AllocationProduct record);


    /**
     * 通过编码查看出库单sku并有图片属性
     * @param allocationCode
     * @return
     */
    List<AllocationProductToOutboundVo>selectByPictureUrlAllocationCode(String allocationCode);
}

