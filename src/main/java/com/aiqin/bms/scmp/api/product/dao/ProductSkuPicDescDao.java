package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPicDesc;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDesc;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDescDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuPicDescRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 10:13
 */
public interface ProductSkuPicDescDao {
    /**
     * 批量新增sku图片描述草稿
     * @param productSkuPicDescDrafts
     * @return
     */
    int insertDraftList(List<ProductSkuPicDescDraft> productSkuPicDescDrafts);

    int insertList(List<ProductSkuPicDesc> productSkuPicDescs);

    int insertApplyList(List<ApplyProductSkuPicDesc> applyProductSkuPicDescs);

    List<ProductSkuPicDescRespVo> getDraft(String skuCode);

    List<ProductSkuPicDesc> getInfo(String skuCode);

    List<ProductSkuPicDescDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    List<ApplyProductSkuPicDesc> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ApplyProductSkuPicDesc> getApplys(@Param("applyProductSkus") List<ApplyProductSku> applyProductSkus);
}
