package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 11:20
 * @Description:
 */
@Mapper
public interface ProductApplyPromotionMapper {
    /**
     * 更具id进行修改
     * @param priceApplyPromotionReqVo
     * @return
     */
     Integer updateById(PriceApplyPromotionReqVo priceApplyPromotionReqVo);

    /**
     *列表分页数据
     * @param priceApplyPromotionReqVo
     * @return
     */
    Page<PriceApplyPromotionRespVo> list(PriceApplyPromotionReqVo priceApplyPromotionReqVo);

    /**
     * 进行添加
     * @param priceApplyPromotionReqVo
     * @return
     */
    int insert(PriceApplyPromotionReqVo priceApplyPromotionReqVo);

    /**
     * 获取详情
     * @param id
     * @return
     */
    PriceApplyPromotionRespVo loadById(@Param("id") Long id);

    /**
     * 根据id来进行删除
     * @param id
     */
    void deteleById(Long id);

    List<PriceApplyPromotionRespVo> loadByPromotionNo(String promotionNo);
}
