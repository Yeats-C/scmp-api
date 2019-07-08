package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuDisInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDisInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDistributionInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 14:52
 */
public interface ProductSkuDisInfoService {
    int insertDraft(ProductSkuDisInfoDraft productSkuDisInfoDraft);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int insertApplyList(List<ApplyProductSkuDisInfo> applyProductSkuDisInfos);

    int saveInfo(String skuCode, String applyCode);

    int insertList(List<ProductSkuDistributionInfo> productSkuDistributionInfos);

    int insert(ProductSkuDistributionInfo productSkuDistributionInfo);

    int update(ProductSkuDistributionInfo productSkuDistributionInfo);

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
     * 功能描述: 获取申请信息
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 22:32
     */
    List<PurchaseSaleStockRespVo> getApplyList(String skuCode, String applyCode);
}
