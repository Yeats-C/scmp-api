package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSub;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSub;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSubDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSubRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuSubService
 * @date 2019/7/6 16:06
 * @description TODO
 */
public interface ProductSkuSubService {

    /**
     *
     * 功能描述: 批量插入临时表
     *
     * @param draftList
     * @return
     * @auther knight.xie
     * @date 2019/7/6 16:07
     */
    int insertDraftList(List<ProductSkuSubDraft> draftList);

    /**
     *
     * 功能描述: 根据SkuCode查询临时表信息
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 18:02
     */
    List<ProductSkuSubRespVo> draftDetail(String skuCode);

    /**
     *
     * 功能描述: 申请批量保存
     *
     * @param applyProductSkus
     * @return
     * @auther knight.xie
     * @date 2019/7/6 18:42
     */
    Integer saveApplyList(List<ApplyProductSku> applyProductSkus);

    /**
     *
     * 功能描述: 申请数据批量保存到数据库
     *
     * @param applyList
     * @return
     * @auther knight.xie
     * @date 2019/7/6 18:43
     */
    Integer insertApplyList(List<ApplyProductSkuSub> applyList);

    /**
     *
     * 功能描述: 查询申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:40
     */
    List<ProductSkuSubRespVo> getApply(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:24
     */
    List<ProductSkuSubRespVo> getList(String skuCode);

    /**
     *
     * 功能描述: 保存到正式
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:28
     */
    int saveList(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 批量插入到数据库
     *
     * @param list
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:29
     */
    int insertBatch(List<ProductSkuSub> list);
}
