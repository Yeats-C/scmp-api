package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleAreaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuSaleAreaInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuSaleAreaInfo record);

    int insertSelective(ApplyProductSkuSaleAreaInfo record);

    ApplyProductSkuSaleAreaInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuSaleAreaInfo record);

    int updateByPrimaryKey(ApplyProductSkuSaleAreaInfo record);
    /**
     * 批量保存
     * @author NullPointException
     * @date 2019/6/4
     * @param areaInfoList
     * @return int
     */
    int insertBatch(@Param("items") List<ApplyProductSkuSaleAreaInfo> areaInfoList);
}