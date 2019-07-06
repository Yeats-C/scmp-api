package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturerDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuManufacturerRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:36
 */
public interface ProductSkuManufacturerService {
    int insertDraftList(List<ProductSkuManufacturerDraft> productSkuManufacturerDrafts);

    int insertList(List<ProductSkuManufacturer> productSkuManufacturers);

    int saveList(String skuCode, String applyCode);

    int insertApplyList(List<ApplyProductSkuManufacturer> applyProductSkuManufacturers);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    List<ProductSkuManufacturerRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     *
     * 功能描述: 申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:11
     */
    List<ProductSkuManufacturerRespVo> getApply(String skuCode, String applyCode);
}
