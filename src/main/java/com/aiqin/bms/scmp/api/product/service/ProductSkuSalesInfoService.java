package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:26
 */
public interface ProductSkuSalesInfoService {
    int insertDraftList(List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts);

    int insertList(List<ProductSkuSalesInfo> productSkuSalesInfos);

    int saveList(String skuCode, String applyCode);

    int insertApplyList(List<ApplyProductSkuSalesInfo> applyProductSkuSalesInfos);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    /**
     * 获取SKU销售配置信息-临时表
     * @param skuCode
     * @return
     */
    List<PurchaseSaleStockRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     *
     * 功能描述: 获取申请表数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:37
     */
    List<PurchaseSaleStockRespVo> getApplyList(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 获取正式数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:26
     */
    List<PurchaseSaleStockRespVo> getList(String skuCode);

    /**
     *
     * 功能描述: 检查销售条形码是否存在
     *
     * @param salesCodes
     * @return
     * @auther knight.xie
     * @date 2019/7/30 18:33
     */
    List<String> checkSalesCodes(List<String> salesCodes);
}
