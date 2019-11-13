package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuAssociatedGoods;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoods;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoodsDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuAssociatedGoodsRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuAssociatedGoodsService
 * @date 2019/5/8 16:05

 */
public interface ProductSkuAssociatedGoodsService {

    /**
     * 保存临时表数据
     * @param draftList
     * @return
     */
    int insertDraftList(List<ProductSkuAssociatedGoodsDraft> draftList);
    int insertDraftList(String applyCode);

    /**
     * 获取临时表数据
     * @param skuCode
     * @return
     */
    List<ProductSkuAssociatedGoodsRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     * 保存申请
     * @param applyProductSkus
     * @return
     */
    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    /**
     * 批量插入申请表
     * @param applyProductSkuAssociatedGoods
     * @return
     */
    int insertApplyList(List<ApplyProductSkuAssociatedGoods> applyProductSkuAssociatedGoods);

    /**
     *
     * 功能描述: 申请详情
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:06
     */
    List<ProductSkuAssociatedGoodsRespVo> getApply(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 正式详情
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:10
     */
    List<ProductSkuAssociatedGoodsRespVo> getList(String skuCode);

    /**
     *
     * 功能描述: 正式保存
     *
     * @param skuCode
     * @param applyCode
     * @return 
     * @auther knight.xie
     * @date 2019/7/8 22:13
     */
    int saveList(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 批量保存到数据库
     *
     * @param list
     * @return
     * @auther knight.xie
     * @date 2019/7/8 22:17
     */
    int insertBatch(List<ProductSkuAssociatedGoods> list);
}
