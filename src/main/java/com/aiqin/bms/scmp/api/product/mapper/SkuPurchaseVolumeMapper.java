package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.SkuPurchaseVolume;
import com.aiqin.bms.scmp.api.product.domain.request.SkuPurchaseVolumeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuPurchaseVolumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkuPurchaseVolume record);

    int insertSelective(SkuPurchaseVolume record);

    SkuPurchaseVolume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkuPurchaseVolume record);

    int updateByPrimaryKey(SkuPurchaseVolume record);

    /**
     *
     * 功能描述: 根据Sku查询
     *
     * @param skuCode
     * @return SkuPurchaseVolume
     * @auther knight.xie
     * @date 2019/1/3 13:45
     */
    SkuPurchaseVolume selectBySkuCode(@Param("skuCode") String skuCode);

    /**
     *
     * 功能描述: 批量插入
     *
     * @param skuPurchaseVolumeVos
     * @return int
     * @auther knight.xie
     * @date 2019/1/3 13:55
     */
    int saveBatch(List<SkuPurchaseVolumeVo> skuPurchaseVolumeVos);

    /**
     *
     * 功能描述: 批量更新
     *
     * @param skuPurchaseVolumes
     * @return int
     * @auther knight.xie
     * @date 2019/1/3 13:56
     */
    int updateBatch(List<SkuPurchaseVolume> skuPurchaseVolumes);
}