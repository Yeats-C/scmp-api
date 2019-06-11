package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.SkuSaleVolume;
import com.aiqin.bms.scmp.api.product.domain.request.SkuSaleVolumeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuSaleVolumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkuSaleVolume record);

    int insertSelective(SkuSaleVolume record);

    SkuSaleVolume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkuSaleVolume record);

    int updateByPrimaryKey(SkuSaleVolume record);

    /**
     *
     * 功能描述: 根据Sku查询
     *
     * @param skuCode
     * @return SkuSaleVolume
     * @auther knight.xie
     * @date 2019/1/3 13:45
     */
    SkuSaleVolume selectBySkuCode(@Param("skuCode") String skuCode);

    /**
     *
     * 功能描述: 批量插入
     *
     * @param skuSaleVolumeVos
     * @return int
     * @auther knight.xie
     * @date 2019/1/3 13:55
     */
    int saveBatch(List<SkuSaleVolumeVo> skuSaleVolumeVos);

    /**
     *
     * 功能描述: 批量更新
     *
     * @param skuSaleVolumes
     * @return int
     * @auther knight.xie
     * @date 2019/1/3 13:56
     */
    int updateBatch(List<SkuSaleVolume> skuSaleVolumes);

}