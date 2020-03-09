package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductSkuSaleAreaInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSaleAreaInfo record);

    int insertSelective(ProductSkuSaleAreaInfo record);

    ProductSkuSaleAreaInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSaleAreaInfo record);

    int updateByPrimaryKey(ProductSkuSaleAreaInfo record);
    /**
     * 批量插入
     * @author NullPointException
     * @date 2019/6/5
     * @param areaList
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuSaleAreaInfo> areaList);
    /**
     * 批量删除
     * @author NullPointException
     * @date 2019/6/5
     * @param codes
     * @return int
     */
    int deleteByCodes(@Param("items") Set<String> codes);

    /**
     * 删除
     * @author NullPointException
     * @date 2019/6/5
     * @return int
     */
    int deleteByCode(@Param("mainCode") String mainCode);

    Integer selectAreaStatus(@Param("mainCode") String mainCode, @Param("code") String code,
                             @Param("status") Integer status,
                             @Param("type") Integer type);
}