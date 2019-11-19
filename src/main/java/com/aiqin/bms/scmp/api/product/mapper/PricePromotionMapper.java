package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-06 11:00
 * @Description:
 */
@Mapper
public interface PricePromotionMapper {
    /**
     * 获取列表
     * @param priceApplyPromotionReqVo
     * @return
     */
    List<PricePromotionRespVo> list(PricePromotionReqVo priceApplyPromotionReqVo) ;

    /**
     * 批量生成促销单
     * @param
     */
     void insertList(List<PricePromotionReqVo> pricePromotionReqVoList);
    /**
     * 批量生成促销单
     * @param
     */
    void insert(PricePromotionReqVo pricePromotionReqVo);

    /**
     *生成促销单下属的所有详情
     * @param promotionNo
     * @return
     */
    PricePromotionRespVo loadById(Long id);

    /**
     * 取消
     * @param id
     */
    void delete(Long id);
    /**
     *查询对应相应form下面的促销
     * @param
     * @return
     */
    List<PricePromotionRespVo> selectByFormNo(String formNo);
    /**
     *根据回调来进行促销单状态的改变
     * @param
     * @return
     */
    void updateApplyInfoByVO(@Param("formNo") String formNo,@Param("status") Byte status);
}
