package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPriceInfo;

import java.util.List;

public interface ApplyProductSkuPriceInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuPriceInfo record);

    int insertSelective(ApplyProductSkuPriceInfo record);

    ApplyProductSkuPriceInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuPriceInfo record);

    int updateByPrimaryKey(ApplyProductSkuPriceInfo record);
    /**
     * 批量插入价格申请数据
     * @author NullPointException
     * @date 2019/6/17
     * @param applyList
     * @return int
     */
    int insertBatch(List<ApplyProductSkuPriceInfo> applyList);
    /**
     * 通过sku编码查询数据
     * @author NullPointException
     * @date 2019/6/17
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPriceInfo>
     */
    List<ApplyProductSkuPriceInfo> selectBySkuCodes(List<String> skuCode);
}