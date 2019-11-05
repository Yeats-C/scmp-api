package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuBoxPacking;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPacking;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPackingDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBoxPackingRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 14:55
 */
public interface ProductSkuBoxPackingService {
    int insertDraft(ProductSkuBoxPackingDraft productSkuBoxPackingDraft);

    int insertDraftList(List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts);
    int insertDraftList(String applyCode);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int insertApplyList(List<ApplyProductSkuBoxPacking> applyProductSkuBoxPackings);

    /**
     * 传入申请sku列表，新增对应正式数据
     * @param skuCode
     * @return
     */
    int saveInfo(String skuCode, String applyCode);

    int insertList(List<ProductSkuBoxPacking> productSkuBoxPackings);

    int insert(ProductSkuBoxPacking productSkuBoxPacking);

    int update(ProductSkuBoxPacking productSkuBoxPacking);

    /**
     * 获取整包装信息-临时表
     * @param skuCode
     * @return
     */
    List<ProductSkuBoxPackingRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     *
     * 功能描述: 获取申请信息
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:44
     */
    List<ProductSkuBoxPackingRespVo> getApply(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:03
     */
    List<ProductSkuBoxPackingRespVo> getList(String skuCode);
}
