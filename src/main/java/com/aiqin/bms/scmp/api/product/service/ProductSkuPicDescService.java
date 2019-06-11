package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPicDesc;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDesc;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDescDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuPicDescRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:22
 */
public interface ProductSkuPicDescService {
    int insertDraftList(List<ProductSkuPicDescDraft> productSkuPicDescDrafts);

    int insertList(List<ProductSkuPicDesc> productSkuPicDescs);

    int saveList(String skuCode, String applyCode);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int insertApplyList(List<ApplyProductSkuPicDesc> applyProductSkuPicDescs);

    /**
     * 获取临时数据
     * @param skuCode
     * @return
     */
    List<ProductSkuPicDescRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
}
